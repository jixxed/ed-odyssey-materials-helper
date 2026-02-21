package nl.jixxed.eliteodysseymaterials.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.Location;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.helper.DnsHelper;
import nl.jixxed.eliteodysseymaterials.schemas.system.SystemInfo;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;

import javax.naming.NamingException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
public class LocationService {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final Double DEFAULT_LATITUDE = 999.9;
    private static final Double DEFAULT_LONGITUDE = 999.9;
    @Getter
    private static StarSystem currentStarSystem = new StarSystem("", 0, 0, 0);
    @Getter
    private static BigInteger currentSystemAddress = new BigInteger("0");
    private static String body = "";
    private static String station = "";
    @Getter
    private static BigInteger marketID = BigInteger.ZERO;//default to 0 when not docked at a station
    private static Double latitude = DEFAULT_LATITUDE;
    private static Double longitude = DEFAULT_LONGITUDE;
    private static Long bodyID;
    @Getter
    @Setter
    private static Optional<String> statusBodyName;
    private static final List<EventListener<?>> EVENT_LISTENERS = new ArrayList<>();

    private static final PublishSubject<BigInteger> modifyFleetCarrier = PublishSubject.create();
    private static Disposable subscriptionFleetCarrier;
    private static final PublishSubject<BigInteger> modifySquadronCarrier = PublishSubject.create();
    private static Disposable subscriptionSquadronCarrier;

    private static Map<BigInteger, StarSystem> SYSTEM_CACHE = new ConcurrentHashMap<>();
    @Getter
    @Setter
    private static Optional<StarSystem> fleetCarrierLocation = Optional.empty();
    @Getter
    @Setter
    private static Optional<StarSystem> squadronCarrierLocation = Optional.empty();

    static {
        //ignore unknown properties to avoid crashes on api changes
        OBJECT_MAPPER.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_ABSENT);
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.registerModule(new Jdk8Module().configureAbsentsAsNulls(true));

        EVENT_LISTENERS.add(EventService.addStaticListener(TerminateApplicationEvent.class, event -> {
            destroy();
        }));

        Observable<BigInteger> debouncedFleetCarrier = modifyFleetCarrier.debounce(2500, TimeUnit.MILLISECONDS);
        subscriptionFleetCarrier = debouncedFleetCarrier.subscribe((BigInteger systemAddress) -> LocationService.setFleetCarrierLocation(Optional.ofNullable(getStarSystem(systemAddress))), throwable ->
                log.error("Error updating fleet carrier location", throwable));
        Observable<BigInteger> debouncedSquadronCarrier = modifySquadronCarrier.debounce(2500, TimeUnit.MILLISECONDS);
        subscriptionSquadronCarrier = debouncedSquadronCarrier.subscribe((BigInteger systemAddress) -> LocationService.setSquadronCarrierLocation(Optional.ofNullable(getStarSystem(systemAddress))), throwable ->
                log.error("Error updating squadron carrier location", throwable));
    }

    private LocationService() {
    }

    static {
        EVENT_LISTENERS.add(EventService.addStaticListener(ApproachBodyJournalEvent.class, event -> {//When approaching body
            body = event.getApproachBody().getBody();
            bodyID = event.getApproachBody().getBodyID().longValue();
            station = "";
            marketID = BigInteger.ZERO;
            notifyListeners();
        }));
        EVENT_LISTENERS.add(EventService.addStaticListener(ApproachSettlementJournalEvent.class, event -> {//when approaching settlement, also on startup if at settlement
            body = event.getApproachSettlement().getBodyName();
            bodyID = event.getApproachSettlement().getBodyID().longValue();
            station = "";
            marketID = BigInteger.ZERO;
            notifyListeners();
        }));
        EVENT_LISTENERS.add(EventService.addStaticListener(DockedJournalEvent.class, event -> {//Always player controlled
            station = event.getDocked().getStationName_Localised().orElseGet(() -> event.getDocked().getStationName());
            marketID = event.getDocked().getMarketID().orElse(BigInteger.ZERO);
            notifyListeners();
        }));
        EVENT_LISTENERS.add(EventService.addStaticListener(FSDJumpJournalEvent.class, event -> {//After jump to other system
            currentStarSystem = event.getStarSystem();
            currentSystemAddress = event.getEvent().getSystemAddress();
            body = event.getBody();
            bodyID = event.getEvent().getBodyID().longValue();
            notifyListeners();
        }));
        EVENT_LISTENERS.add(EventService.addStaticListener(CarrierJumpJournalEvent.class, event -> {//After jump to other system
            if (event.getEvent().getDocked()) {//should always be true, but let's be safe
                currentStarSystem = event.getStarSystem();
                currentSystemAddress = event.getEvent().getSystemAddress();
                body = event.getBody();
                bodyID = event.getEvent().getBodyID().longValue();
                notifyListeners();
            }
        }));
        EVENT_LISTENERS.add(EventService.addStaticListener(LeaveBodyJournalEvent.class, event -> {//on leaving body
            body = "";
            bodyID = null;
            station = "";
            marketID = BigInteger.ZERO;
            notifyListeners();
        }));
        EVENT_LISTENERS.add(EventService.addStaticListener(LiftOffJournalEvent.class, event -> {//can be either player or AI controlled
            if (event.getLiftoff().getPlayerControlled() || event.getLiftoff().getTaxi().orElse(false)) {// both false means ship sent away
                station = "";
                marketID = BigInteger.ZERO;
                latitude = DEFAULT_LATITUDE;
                longitude = DEFAULT_LONGITUDE;
            }
            notifyListeners();
        }));
        EVENT_LISTENERS.add(EventService.addStaticListener(LocationJournalEvent.class, event -> {//at startup or upon respawn
            currentStarSystem = event.getStarSystem();
            currentSystemAddress = event.getLocation().getSystemAddress();
            body = event.getBody();
            bodyID = event.getLocation().getBodyID().longValue();
            //on relog station is empty for POI's.
            //on respawn after death there is a station?
            //if we already have a station from before the relog, we keep the station
            if (!Objects.equals(event.getStationName(), "")) {
                station = event.getStationName();
                marketID = event.getLocation().getMarketID().orElse(BigInteger.ZERO);
            }
            notifyListeners();
        }));
        EVENT_LISTENERS.add(EventService.addStaticListener(SupercruiseEntryJournalEvent.class, event -> {//on entering SC
            station = "";
            marketID = BigInteger.ZERO;
            latitude = DEFAULT_LATITUDE;
            longitude = DEFAULT_LONGITUDE;
            notifyListeners();
        }));
        EVENT_LISTENERS.add(EventService.addStaticListener(TouchdownJournalEvent.class, event -> {//can be either player or AI controlled
            station = event.getTouchdown().getNearestDestination().orElse("");
            latitude = event.getTouchdown().getLatitude().map(BigDecimal::doubleValue).orElse(DEFAULT_LATITUDE);
            longitude = event.getTouchdown().getLongitude().map(BigDecimal::doubleValue).orElse(DEFAULT_LONGITUDE);

            notifyListeners();
        }));
        EVENT_LISTENERS.add(EventService.addStaticListener(UndockedJournalEvent.class, event -> {//Always player controlled
            station = "";
            marketID = BigInteger.ZERO;
            latitude = DEFAULT_LATITUDE;
            longitude = DEFAULT_LONGITUDE;
            notifyListeners();
        }));
        EVENT_LISTENERS.add(EventService.addStaticListener(JournalInitEvent.class, event -> {//Always player controlled
            if (!event.isInitialised()) {
                reset();
            }
        }));
    }

    private static void reset() {
        currentStarSystem = new StarSystem("", 0, 0, 0);
        currentSystemAddress = new BigInteger("0");
        body = "";
        station = "";
        marketID = BigInteger.ZERO;//default to 0 when no
        latitude = DEFAULT_LATITUDE;
        longitude = DEFAULT_LONGITUDE;
        bodyID = null;
    }

    public static void init() {
        //empty method to trigger static block
    }

    private static void notifyListeners() {
        EventService.publish(new LocationChangedEvent(currentStarSystem, body, station, latitude, longitude));
    }

    public static Location getCurrentLocation() {
        return new Location(currentStarSystem, body, bodyID, station, latitude, longitude);
    }

    public static Double calculateDistance(final StarSystem currentStarSystem, final StarSystem starSystem) {
        return calculateDistance(currentStarSystem.getX(), currentStarSystem.getY(), currentStarSystem.getZ(), starSystem.getX(), starSystem.getY(), starSystem.getZ());
    }

    private static Double calculateDistance(final double x1, final double y1, final double z1, final double x2, final double y2, final double z2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z2 - z1, 2));
    }

    public static String getCurrentStarSystemName() {
        return currentStarSystem.getName();
    }

    public static List<Double> getCurrentStarPos() {
        return List.of(getCurrentStarSystem().getX(),
                getCurrentStarSystem().getY(),
                getCurrentStarSystem().getZ()
        );
    }

    public static List<Double> getCurrentStarPos(final BigInteger address) {
        if (currentSystemAddress.equals(address)) {
            return List.of(getCurrentStarSystem().getX(),
                    getCurrentStarSystem().getY(),
                    getCurrentStarSystem().getZ()
            );
        }
        return null;
    }

    public static String getCurrentStarSystemName(final BigInteger address) {
        if (currentSystemAddress.equals(address)) {
            return currentStarSystem.getName();
        }
        return null;
    }

    public static void setFleetCarrierLocationByAddress(BigInteger systemAddress) {
        modifyFleetCarrier.onNext(systemAddress);
    }

    public static void setSquadronCarrierLocationByAddress(BigInteger systemAddress) {
        modifySquadronCarrier.onNext(systemAddress);
    }

    public static boolean isAtSquadronCarrier() {
        String squadronCarrierID = UserPreferencesService.getPreference(PreferenceConstants.SQUADRON_CARRIER_ID, "-1");
        return squadronCarrierID.equals(marketID.toString());
    }

    public static boolean isAtFleetCarrier() {
        String fleetCarrierID = UserPreferencesService.getPreference(PreferenceConstants.FLEET_CARRIER_ID, "-1");
        return fleetCarrierID.equals(marketID.toString());
    }

    private static StarSystem getStarSystem(BigInteger systemAddress) {
        if (SYSTEM_CACHE.containsKey(systemAddress)) {
            return SYSTEM_CACHE.get(systemAddress);
        }
        //{"systemAddress":1732985787106,"systemName":"LHS 3388","systemX":-72.9375,"systemY":18.59375,"systemZ":92.9375,"systemSector":"5e1c706716a61e41","updatedAt":"2023-04-29T21:58:50.000Z"}
        //{"error":"Not Found","message":"System not found"}
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            log.debug("Fetching system data for systemAddress {}", systemAddress);
            final String systemDomainName = DnsHelper.resolveCname(Secrets.getOrDefault("system.host", "localhost"));
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://" + systemDomainName + "/v2/system/address/" + systemAddress.toString()))
                    .header("User-Agent", VersionService.getUserAgent())
                    .GET()
                    .build();
            final HttpResponse<String> send = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (send.statusCode() == 200) {
                SystemInfo systemInfo = OBJECT_MAPPER.readValue(send.body(), SystemInfo.class);
                StarSystem starSystem = new StarSystem(systemInfo.getSystemName(), systemInfo.getSystemX().doubleValue(), systemInfo.getSystemY().doubleValue(), systemInfo.getSystemZ().doubleValue());
                SYSTEM_CACHE.put(systemAddress, starSystem);
                return starSystem;
            } else {
                log.error("Failed fetching system data for systemAddress {}", systemAddress);
            }
        } catch (InterruptedException | IOException | NamingException | IllegalArgumentException e) {
            log.error(e.getMessage(), e);
        }
        return null;

    }

    private static void destroy() {
        subscriptionFleetCarrier.dispose();
        subscriptionSquadronCarrier.dispose();
    }
}
