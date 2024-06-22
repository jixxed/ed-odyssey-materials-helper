package nl.jixxed.eliteodysseymaterials.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.KeyStoreOptions;
import io.vertx.ext.stomp.StompClient;
import io.vertx.ext.stomp.StompClientConnection;
import io.vertx.ext.stomp.StompClientOptions;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.Commander;
import nl.jixxed.eliteodysseymaterials.domain.Location;
import nl.jixxed.eliteodysseymaterials.enums.SystemEconomy;
import nl.jixxed.eliteodysseymaterials.helper.CryptoHelper;
import nl.jixxed.eliteodysseymaterials.schemas.journal.FSSSignalDiscovered.FSSSignalDiscovered;
import nl.jixxed.eliteodysseymaterials.schemas.journal.MaterialCollected.MaterialCollected;
import nl.jixxed.eliteodysseymaterials.schemas.journal.USSDrop.USSDrop;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class HighGradeEmissionService {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final List<EventListener<?>> EVENT_LISTENERS = new ArrayList<>();

    private static final List<Faction> FACTIONS = new ArrayList<>();

    private static StompClientConnection connection;
    private static StompClient client;
    private static Vertx vertx;

    private static boolean terminating = false;
    private static boolean processing = false;
    private static boolean trackingUSS = false;
    private static Set<String> trackingMaterials = new HashSet<>();

    private static String getUniqueId(Commander commander) {
        return CryptoHelper.sha256("afrev#$Fv123a", commander.getName() + commander.getFid());
    }

    private static void createClient() {
        String trustStorePath = ((VersionService.getBuildVersion() == null) ? System.getProperty("java.home") + "\\" : RegistryService.CURRENT_DIR_SINGLE_SLASHED + "runtime/") + "lib/security/cacerts";
        log.info("trustStorePath: " + trustStorePath);
        StompClientOptions options = new StompClientOptions()
                .setHost("elite-hge.jixxed.nl")
                .setPort(443).setSsl(true);
//                .setHost("127.0.0.1")
//                .setPort(8080).setSsl(false);
        options.setHostnameVerificationAlgorithm("HTTPS")
                .setKeyCertOptions(new KeyStoreOptions().setType("JKS").setPath(trustStorePath).setPassword("changeit"))
                .setReconnectAttempts(-1)
                .setReconnectInterval(5000);
        client = StompClient.create(vertx, options);
        connect();

    }

    private static void reconnect() {
        connection.close();
        connection = null;
        if (!terminating) {
            connect();
        }
    }

    private static Future<StompClientConnection> connect() {
        return ApplicationState.getInstance().getPreferredCommander().map(commander ->
                        client.connect().onSuccess(connection -> {
                            connection.errorHandler(throwable -> {
                                log.error("error received: {}", throwable);
                            }).exceptionHandler(throwable -> {
                                log.error("exception received:", throwable);
                            }).closeHandler(closeEvent -> {
                                vertx.setTimer(1000, id -> reconnect());
                                log.info("connection closed: {}", closeEvent);
                            });
                            HighGradeEmissionService.connection = connection;
                            log.info("Connected to stomp server");
//            subscribe();
                        }).onFailure(throwable -> {
                            log.error("Failed to connect to stomp server", throwable);
                        })
        ).orElse(Future.failedFuture("No commander selected"));
    }

    private static void message(Message message) {
        ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
            if (connection == null) {
                connect().onSuccess(o -> send(message, commander));
            } else {
                send(message, commander);
            }
        });
    }

    private static void send(Message message, Commander commander) {
        Map<String, String> headers = new HashMap<>();
        headers.put("id", getUniqueId(commander));
        try {
            connection.send("/post/hge", headers, Buffer.buffer(MAPPER.writeValueAsString(message)),
                    ar -> {
                        if (ar.succeeded()) {
                            log.info("Completed sending message: {}", ar.result());
                        } else {
                            log.error("Failed to send message", ar.cause());
                        }
                        ar.result();
                    });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static void subscribe() {
        ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
            Map<String, String> headers = new HashMap<>();
            final String id = getUniqueId(commander);
            headers.put("id", id);

            connection.subscribe("/subscription/hge/" + id, frame -> {
                        log.info("Message to /subscription/hge/{}: {}", id, frame.getBodyAsString());
                    })
                    .onSuccess(frame -> log.info("Subscribed to /subscription/hge/" + id))
                    .onFailure(throwable -> log.error("Failed to subscribe to /subscription/hge/" + id, throwable))
                    .onComplete(ar -> {
                        if (ar.succeeded()) {
                            log.info("Completed subscription to /subscription/hge/{}: {}", id, ar.result());
                        } else {
                            log.error("Failed to complete subscription", ar.cause());
                        }
                    });
        });
    }

    public static synchronized void initialize() {
        vertx = Vertx.vertx();
        createClient();
        EVENT_LISTENERS.add(EventService.addStaticListener(JournalInitEvent.class, event -> processing = event.isInitialised()));
        EVENT_LISTENERS.add(EventService.addStaticListener(CommanderSelectedEvent.class, event -> reconnect()));
        EVENT_LISTENERS.add(EventService.addStaticListener(FSDJumpJournalEvent.class, event -> registerFactions(event.getEvent().getFactions().map(list -> list.stream().map(HighGradeEmissionService::mapFaction).toList()).orElse(Collections.emptyList()))));
        EVENT_LISTENERS.add(EventService.addStaticListener(CarrierJumpJournalEvent.class, event -> registerFactions(event.getEvent().getFactions().map(list -> list.stream().map(HighGradeEmissionService::mapFaction).toList()).orElse(Collections.emptyList()))));
        EVENT_LISTENERS.add(EventService.addStaticListener(LocationJournalEvent.class, event -> registerFactions(event.getLocation().getFactions().map(list -> list.stream().map(HighGradeEmissionService::mapFaction).toList()).orElse(Collections.emptyList()))));
        EVENT_LISTENERS.add(EventService.addStaticListener(TerminateApplicationEvent.class, event -> {
            terminating = true;
            client.close();
            vertx.close();
        }));
    }


    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Message {
        private String timestamp;//always
        private String event;//always
        //location
        private String system;//always
        private Double[] starPos;//always
        //faction
        private String faction;//fss
        private String state;//fss
        private String allegiance;//fss
        private String government;//fss
        //system
        private Set<String> systemEconomies;//always
        //data
        private Set<String> materialsFound; //ussdrop
        private Double timeRemaining;//fss
    }

    private static Faction mapFaction(nl.jixxed.eliteodysseymaterials.schemas.journal.FSDJump.Faction faction) {
        return new Faction(faction.getName(), faction.getFactionState(), faction.getAllegiance(), faction.getGovernment(), faction.getActiveStates().map(list -> list.stream().map(nl.jixxed.eliteodysseymaterials.schemas.journal.FSDJump.ActiveState::getState).toList()).orElse(null));
    }

    private static Faction mapFaction(nl.jixxed.eliteodysseymaterials.schemas.journal.CarrierJump.Faction faction) {
        return new Faction(faction.getName(), faction.getFactionState(), faction.getAllegiance(), faction.getGovernment(), faction.getActiveStates().map(list -> list.stream().map(nl.jixxed.eliteodysseymaterials.schemas.journal.CarrierJump.ActiveState::getState).toList()).orElse(null));
    }

    private static Faction mapFaction(nl.jixxed.eliteodysseymaterials.schemas.journal.Location.Faction faction) {
        return new Faction(faction.getName(), faction.getFactionState(), faction.getAllegiance(), faction.getGovernment(), faction.getActiveStates().map(list -> list.stream().map(nl.jixxed.eliteodysseymaterials.schemas.journal.Location.ActiveState::getState).toList()).orElse(null));
    }

    private static void registerFactions(List<Faction> factions) {
        FACTIONS.clear();
        FACTIONS.addAll(factions);
    }

    //{ "timestamp":"2024-06-09T09:32:55Z",
// "event":"FSSSignalDiscovered",
// "SystemAddress":2557820834522,
// "SignalName":"$USS_HighGradeEmissions;",
// "SignalName_Localised":"Unidentified signal source",
// "SignalType":"USS",
// "USSType":"$USS_Type_VeryValuableSalvage;",
// "USSType_Localised":"High grade emissions",
// "SpawningState":"$FactionState_Election_desc;", OR
// "SpawningState":"",
// "SpawningFaction":"LDS 413 Resistance",
// "ThreatLevel":0,
// "TimeRemaining":660.575867 }
    public static void fssSignalDiscovered(FSSSignalDiscovered event) {
        LocalDateTime now = ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime();
        if (processing
                && event.getUSSType().map(type -> type.equals("$USS_Type_VeryValuableSalvage;")).orElse(false)
                && now.minusMinutes(3).isBefore(event.getTimestamp())
                && now.plusMinutes(3).isAfter(event.getTimestamp())) {
            final Location currentLocation = LocationService.getCurrentLocation();
            final String timestamp = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").format(event.getTimestamp());
            final Faction faction = FACTIONS.stream()
                    .filter(f -> f.name().equals(event.getSpawningFaction().orElse(null)))
                    .findFirst()
                    .orElse(null);
            final Set<String> economies = Stream.of(currentLocation.getStarSystem().getPrimaryEconomy(), currentLocation.getStarSystem().getSecondaryEconomy())
                    .filter(Predicate.not(SystemEconomy.UNKNOWN::equals))
                    .map(SystemEconomy::name)
                    .collect(Collectors.toSet());
            Message message = new Message.MessageBuilder()
                    .timestamp(timestamp)
                    .event(event.getEvent())
                    .system(currentLocation.getStarSystem().getName())
                    .starPos(new Double[]{currentLocation.getStarSystem().getX(), currentLocation.getStarSystem().getY(), currentLocation.getStarSystem().getZ()})
                    .faction(event.getSpawningFaction().orElse(null))
                    .state(event.getSpawningState().orElse(null))
                    .allegiance(faction != null ? faction.allegiance() : null)
                    .government(faction != null ? faction.government() : null)
                    .systemEconomies(economies)
                    .timeRemaining(event.getTimeRemaining().map(BigDecimal::doubleValue).orElse(null))
                    .build();
            message(message);
        }
    }

    // { "timestamp":"2024-06-09T09:46:20Z", "event":"USSDrop", "USSType":"$USS_Type_VeryValuableSalvage;", "USSType_Localised":"High grade emissions", "USSThreat":0 }
    //   { "timestamp":"2024-06-09T09:51:28Z", "event":"MaterialCollected", "Category":"Manufactured", "Name":"fedproprietarycomposites", "Name_Localised":"Proprietary Composites", "Count":3 }
    //   { "timestamp":"2024-06-09T09:52:35Z", "event":"SupercruiseEntry", "Taxi":false, "Multicrew":false, "StarSystem":"LDS 413", "SystemAddress":2557820834522 }
    //   { "timestamp":"2024-06-09T22:07:21Z", "event":"Shutdown" }
    //   { "timestamp":"2024-06-11T18:56:41Z", "event":"Music", "MusicTrack":"MainMenu" }
    public static void ussDrop(USSDrop event) {
        if (processing && event.getUSSType().equals("$USS_Type_VeryValuableSalvage;")) {
            trackingUSS = true;
            trackingMaterials = new HashSet<>();
        }
    }

    public static void materialCollected(MaterialCollected event) {
        if (processing && trackingUSS) {
            trackingMaterials.add(event.getName());
        }
    }

    public static void submitUss(LocalDateTime datetime) {
        LocalDateTime now = ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime();
        if (processing
                && trackingUSS
                && now.minusMinutes(3).isBefore(datetime)
                && now.plusMinutes(3).isAfter(datetime)) {
            final Location currentLocation = LocationService.getCurrentLocation();
            final String timestamp = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").format(datetime);
            final Set<String> economies = Stream.of(currentLocation.getStarSystem().getPrimaryEconomy(), currentLocation.getStarSystem().getSecondaryEconomy())
                    .filter(Predicate.not(SystemEconomy.UNKNOWN::equals))
                    .map(SystemEconomy::name)
                    .collect(Collectors.toSet());
            Message message = new Message.MessageBuilder()
                    .timestamp(timestamp)
                    .event("USSDrop")
                    .system(currentLocation.getStarSystem().getName())
                    .starPos(new Double[]{currentLocation.getStarSystem().getX(), currentLocation.getStarSystem().getY(), currentLocation.getStarSystem().getZ()})
                    .systemEconomies(economies)
                    .materialsFound(trackingMaterials)
                    .build();
            message(message);
            trackingUSS = false;
        }
    }

    record Faction(String name, String factionState, String allegiance, String government, List<String> activeStates) {
    }
}
