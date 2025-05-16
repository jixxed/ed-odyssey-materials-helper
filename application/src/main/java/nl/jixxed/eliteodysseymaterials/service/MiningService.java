package nl.jixxed.eliteodysseymaterials.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.helper.DnsHelper;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Fileheader.Fileheader;
import nl.jixxed.eliteodysseymaterials.schemas.journal.ProspectedAsteroid.ProspectedAsteroid;
import nl.jixxed.eliteodysseymaterials.schemas.journal.SupercruiseDestinationDrop.SupercruiseDestinationDrop;
import nl.jixxed.eliteodysseymaterials.schemas.journal.SupercruiseExit.SupercruiseExit;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.TerminateApplicationEvent;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class MiningService {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final List<EventListener<?>> EVENT_LISTENERS = new ArrayList<>();
    private static final ThreadPoolExecutor EXECUTOR_SERVICE = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());
//    { "timestamp":"2025-05-14T18:17:34Z", "event":"SupercruiseDestinationDrop", "Type":"$MULTIPLAYER_SCENARIO79_TITLE;", "Type_Localised":"Resource Extraction Site [Hazardous]", "Threat":4 }
//    { "timestamp":"2025-05-14T18:17:36Z", "event":"SupercruiseExit", "Taxi":false, "Multicrew":false, "StarSystem":"LHS 3388", "SystemAddress":1732985787106, "Body":"LHS 3388 2 A Ring", "BodyID":19, "BodyType":"PlanetaryRing" }
//    { "timestamp":"2025-05-14T18:19:32Z", "event":"ProspectedAsteroid", "Materials":[ { "Name":"Platinum", "Proportion":64.052292 } ], "Content":"$AsteroidMaterialContent_Medium;", "Content_Localised":"Material Content: Medium", "Remaining":0.000000 }

    private static final List<ProspectedAsteroid> prospectedAsteroids = new ArrayList<>();
    @Setter
    private static SupercruiseDestinationDrop supercruiseDestinationDrop;
    @Setter
    private static SupercruiseExit supercruiseExit;

    static {
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_ABSENT);
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.registerModule(new Jdk8Module().configureAbsentsAsNulls(true));

        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        OBJECT_MAPPER.registerModule(module);

    }

    public static void init() {
        EVENT_LISTENERS.add(EventService.addStaticListener(TerminateApplicationEvent.class, terminateApplicationEvent -> {
            EXECUTOR_SERVICE.shutdown();
        }));
    }

    private static void reset() {
        supercruiseDestinationDrop = null;
        supercruiseExit = null;
        prospectedAsteroids.clear();
    }

    public static void addProspectedAsteroid(ProspectedAsteroid prospectedAsteroid) {
        prospectedAsteroids.add(prospectedAsteroid);
    }

    public static void sendMiningEventAndReset() {
        final String buildVersion = VersionService.getBuildVersion();
//        if (buildVersion != null) {
        if (!prospectedAsteroids.isEmpty()) {
            try {
                final String data = OBJECT_MAPPER.writeValueAsString(new Report(/*buildVersion*/ "2.166", ApplicationState.getInstance().getFileheader(), new MiningEvent(LocationService.getCurrentStarSystem(), supercruiseDestinationDrop, supercruiseExit, prospectedAsteroids)));
                final Runnable run = () -> {
                    try (final HttpClient httpClient = HttpClient.newHttpClient()) {
                        log.info(data);
                        final String domainName = DnsHelper.resolveCname("edmattracking.jixxed.nl");
                        final HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create("https://" + domainName + "/Prod/v2/submit-mining-event"))
                                .POST(HttpRequest.BodyPublishers.ofString(data))
                                .build();
                        final HttpResponse<String> send = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                        log.info(send.body());
                    } catch (final InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } catch (final Exception e) {
                        log.error("publish prospect error", e);
                    }
                };
                EXECUTOR_SERVICE.submit(run);
            } catch (Exception e) {
                log.error("publish prospect error", e);
            } finally {
                reset();
            }
        }
//        } else {
//            reset();
//        }
    }

    @Data
    @AllArgsConstructor
    public static class Report {
        String version;
        Fileheader fileheader;
        MiningEvent miningEvent;
    }

    @Data
    @AllArgsConstructor
    public static class MiningEvent {
        StarSystem system;
        SupercruiseDestinationDrop supercruiseDestinationDrop;
        SupercruiseExit supercruiseExit;
        List<ProspectedAsteroid> prospectedAsteroids;
    }
} 