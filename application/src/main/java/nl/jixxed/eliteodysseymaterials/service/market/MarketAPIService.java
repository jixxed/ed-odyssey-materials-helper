package nl.jixxed.eliteodysseymaterials.service.market;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.ShipConfiguration;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipSize;
import nl.jixxed.eliteodysseymaterials.enums.Commodity;
import nl.jixxed.eliteodysseymaterials.helper.DnsHelper;
import nl.jixxed.eliteodysseymaterials.schemas.market.save.request.*;
import nl.jixxed.eliteodysseymaterials.schemas.market.save.response.MarketSaveResponse;
import nl.jixxed.eliteodysseymaterials.schemas.market.search.MarketSearchResponse;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.Secrets;
import nl.jixxed.eliteodysseymaterials.service.UserPreferencesService;
import nl.jixxed.eliteodysseymaterials.service.VersionService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.TerminateApplicationEvent;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Slf4j
public class MarketAPIService {
    private static final Semaphore SEMAPHORE = new Semaphore(1, true);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Map<Search, Result> CACHE = new HashMap<>();
    private static StarSystem currentSystem;
    private static boolean largeShip = false;
    private static final ThreadPoolExecutor EXECUTOR_SERVICE = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>());
    private static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();
    private static final List<EventListener<?>> EVENT_LISTENERS = new ArrayList<>();

    static {
        //ignore unknown properties to avoid crashes on api changes
        OBJECT_MAPPER.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_ABSENT);
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.registerModule(new Jdk8Module().configureAbsentsAsNulls(true));

        EVENT_LISTENERS.add(EventService.addStaticListener(TerminateApplicationEvent.class, _ -> {
            EXECUTOR_SERVICE.shutdown();
            SCHEDULED_EXECUTOR_SERVICE.shutdown();
        }));
    }

    @SuppressWarnings("unchecked")
    public static void getNearbyStations(Commodity commodity, int amountRequired, StarSystem origin, Consumer<List<MarketStation>> resultConsumer, Supplier<Boolean> stillValid) {
        //clear cache if we are in a different system or switch to/from a large ship
        String prefPad = UserPreferencesService.getPreference(PreferenceConstants.PREFERRED_LANDING_PAD, "auto");
        boolean isCurrentShipLarge = "large".equals(prefPad) || ("auto".equals(prefPad) && ShipConfiguration.CURRENT.getShipType() != null && ShipConfiguration.CURRENT.getShipType().getShipSize().equals(ShipSize.L));
        if (currentSystem != null && !currentSystem.equals(origin)) {
            log.info("Cleared Market cache, new system: {}", origin.getName());
            CACHE.clear();
        }
        if (isCurrentShipLarge != largeShip) {
            log.info("Cleared Market cache, ship size changed.");
            CACHE.clear();
        }
        largeShip = isCurrentShipLarge;
        currentSystem = origin;
        //check cache
        Result result = CACHE.get(new Search(commodity, amountRequired));
        if (result != null) {
            log.info("Cache hit for {}", commodity);
            resultConsumer.accept(result.results());
            return;
        }
        final Runnable run = () -> {
            boolean releasedEarly = false;
            try {
                SEMAPHORE.acquire(); // Acquire the permit before sending the request
                if (stillValid.get()) {
                    var sort = Sort.builder()
                            .withDistance(Distance__1.builder()
                                    .withDirection("asc")
                                    .build())
                            .build();
                    var referenceCoords = Reference_coords.builder()
                            .withX(BigDecimal.valueOf(origin.getX()))
                            .withY(BigDecimal.valueOf(origin.getY()))
                            .withZ(BigDecimal.valueOf(origin.getZ()))
                            .build();
                    var distance = Distance.builder()
                            .withMin("0")
                            .withMax("1000")
                            .build();
                    var market = Market.builder()
                            .withName(LocaleService.getLocalizedStringForLocale(Locale.ENGLISH, commodity.getLocalizationKey()))
                            .withSupply(Supply.builder()
                                    .withComparison("<=>")
                                    .withValue(List.of(amountRequired, 99999999))
                                    .build())
                            .build();
                    Primary_economy economy = Primary_economy.builder()
                            .withValue(List.of("Agriculture", "Colony", "Extraction", "High Tech", "Industrial", "Military", "None", "Refinery", "Service", "Terraforming", "Tourism"))
                            .build();
                    Filters.FiltersBuilderBase filtersBuilder = Filters.builder()
                            .withDistance(distance)
                            .withMarket(List.of(market))
                            .withPrimary_economy(economy);
                    //add large landing pad filter if we are in a large ship
                    if (largeShip) {
                        filtersBuilder.withLarge_pads(Large_pads.builder()
                                .withComparison("<=>")
                                .withValue(List.of(1, 100))
                                .build());
                    }
                    var filters = filtersBuilder.build();
                    log.info("Get market data for {}", commodity);
                    MarketSaveRequest saveRequest = new MarketSaveRequest.MarketSaveRequestBuilder()
                            .withFilters(filters)
                            .withPage(BigDecimal.valueOf(0))
                            .withSize(BigDecimal.valueOf(10))
                            .withReference_coords(referenceCoords)
                            .withSort(List.of(sort))
                            .build();
                    final String data = OBJECT_MAPPER.writeValueAsString(saveRequest);
                    final HttpResponse<String> recall;
                    try (HttpClient httpClient = HttpClient.newHttpClient()) {
                        final String domainName = DnsHelper.resolveCname(Secrets.getOrDefault("market.services.host", "localhost"));
                        final HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create("https://" + domainName + "/api/stations/search/save"))
                                .header("User-Agent", VersionService.getUserAgent())
                                .POST(HttpRequest.BodyPublishers.ofString(data))
                                .build();
                        final HttpResponse<String> send = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                        MarketSaveResponse MarketSaveResponse = OBJECT_MAPPER.readValue(send.body(), MarketSaveResponse.class);
                        String recallUrl = "https://" + domainName + "/api/stations/search/recall/" + MarketSaveResponse.getSearch_reference();
                        final HttpRequest request2 = HttpRequest.newBuilder()
                                .uri(URI.create(recallUrl))
                                .header("User-Agent", VersionService.getUserAgent())
                                .GET()
                                .build();
                        recall = httpClient.send(request2, HttpResponse.BodyHandlers.ofString());
                    }
                    if (recall.statusCode() == 200) {
                        MarketSearchResponse MarketSearchResponse = OBJECT_MAPPER.readValue(recall.body(), MarketSearchResponse.class);
                        List<MarketStation> MarketStations = MarketSearchResponseMapper.mapToStations(MarketSearchResponse);
                        CACHE.put(new Search(commodity, amountRequired), new Result(MarketStations));
                        resultConsumer.accept(MarketStations);
                    }
                } else {
                    SEMAPHORE.release(); // Release the permit if the request is no longer valid
                    releasedEarly = true;
                }
            } catch (final InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (final Exception e) {
                log.error("Market request error", e);
            } finally {
                if (!releasedEarly) {
                    // Release the permit after the request is completed
                    SCHEDULED_EXECUTOR_SERVICE.schedule(() -> SEMAPHORE.release(), 5, TimeUnit.SECONDS);
                }
            }
        };
        EXECUTOR_SERVICE.submit(run);
    }

    record Search(Commodity commodity, Integer quantity) {
    }

    record Result(List<MarketStation> results) {
    }
}
