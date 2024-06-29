package nl.jixxed.eliteodysseymaterials.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.KeyStoreOptions;
import io.vertx.ext.stomp.StompClient;
import io.vertx.ext.stomp.StompClientConnection;
import io.vertx.ext.stomp.StompClientOptions;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.Commander;
import nl.jixxed.eliteodysseymaterials.domain.Location;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterialType;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.enums.SystemAllegiance;
import nl.jixxed.eliteodysseymaterials.enums.SystemEconomy;
import nl.jixxed.eliteodysseymaterials.helper.CryptoHelper;
import nl.jixxed.eliteodysseymaterials.schemas.journal.FSSSignalDiscovered.FSSSignalDiscovered;
import nl.jixxed.eliteodysseymaterials.schemas.journal.MaterialCollected.MaterialCollected;
import nl.jixxed.eliteodysseymaterials.schemas.journal.USSDrop.USSDrop;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.service.hge.ExpiringMessage;
import nl.jixxed.eliteodysseymaterials.service.hge.FixedSizeCircularReference;
import nl.jixxed.eliteodysseymaterials.service.hge.Message;

import java.io.FileOutputStream;
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
    private static Map<HorizonsMaterialType, FixedSizeCircularReference<StarSystem>> lastFoundSystems = new HashMap<>();
    static{
        final HorizonsMaterialType[] horizonsMaterialTypes = {
                HorizonsMaterialType.CHEMICAL,
                HorizonsMaterialType.THERMIC,
                HorizonsMaterialType.CAPACITORS,
                HorizonsMaterialType.HEAT,
                HorizonsMaterialType.ALLOYS,
                HorizonsMaterialType.MECHANICAL_COMPONENTS,
                HorizonsMaterialType.SHIELDING,
                HorizonsMaterialType.COMPOSITE
        };
        for (HorizonsMaterialType materialType : horizonsMaterialTypes) {
            lastFoundSystems.put(materialType, new FixedSizeCircularReference<>(3));
        }
    }
    @Getter
    private static Collection<ExpiringMessage> messages = Collections.synchronizedCollection(new ArrayList<>());

    private static String getUniqueId(Commander commander) {
        return CryptoHelper.sha256("afrev#$Fv123a", commander.getName() + commander.getFid());
    }

    private static void createClient() {
        String trustStorePath = ((VersionService.getBuildVersion() == null) ? System.getProperty("java.home") + "\\" : RegistryService.CURRENT_DIR_SINGLE_SLASHED + "runtime/") + "lib/security/cacerts";
        log.info("trustStorePath: " + trustStorePath);
        StompClientOptions options = new StompClientOptions()
                .setHost("elite-hge.jixxed.nl")
                .setPort(6001).setSsl(true)
//                .setHost("127.0.0.1")
//                .setPort(8080).setSsl(false)
                .setHeartbeat(new JsonObject().put("x", 30000).put("y", 30000));
        //actual heartbeat is lowest configuration between server and client
        //x outgoing ping
        //y incoming ping
        options.setHostnameVerificationAlgorithm("HTTPS")
                .setKeyCertOptions(new KeyStoreOptions().setType("JKS").setPath(trustStorePath).setPassword("changeit"))
                .setReconnectAttempts(-1)
                .setReconnectInterval(5000);
        log.info("create client");
        client = StompClient.create(vertx, options);
        client.receivedFrameHandler(frame -> {
            log.info("Received frame: {}", frame);
        });
//        log.info("connect client");
//        connect();

    }
    private static String currentSubscription;

    private static void resubscribe() {
        if (currentSubscription != null) {
            try {
                HighGradeEmissionService.connection.unsubscribe(currentSubscription).result();
            } catch (Exception e) {
                log.error("Failed to unsubscribe", e);
            }
        }
        if (connection == null) {
            connect().onSuccess(o -> subscribe());
        } else {
            subscribe();
        }
    }

    private static Future<StompClientConnection> connect() {
        return ApplicationState.getInstance().getPreferredCommander().map(commander ->
                        client.connect().onSuccess(connection -> {
                            connection.connectionDroppedHandler(con -> {
                                // The connection has been lost
                                // You can reconnect or switch to another server.
                                vertx.setTimer(3000, id -> connect());
                            }).errorHandler(throwable -> {
                                log.error("error received: {}", throwable);
                            }).exceptionHandler(throwable -> {
                                log.error("exception received:", throwable);
                            }).closeHandler(closeEvent -> {
//                                vertx.setTimer(1000, id -> connect());
                                log.info("connection closed: {}", closeEvent);
                            });
                            HighGradeEmissionService.connection = connection;
                            log.info("Connected to stomp server");
                        }).onFailure(throwable -> {
                            log.error("Failed to connect to stomp server", throwable);
                            vertx.setTimer(10000, id -> connect());
                        }))
                .orElse(Future.failedFuture("No commander selected"));
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
            final String text = MAPPER.writeValueAsString(message);
            writeToFile(text);
            connection.send("/post/hge", headers, Buffer.buffer(text),
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
            headers.put("user_id", id);
//            log.info("unsubscribe");
//            connection.unsubscribe("/subscription/hge/" + id)
//                    .onSuccess(frame -> log.info("Unsubscribed from /subscription/hge/" + id))
//                    .onFailure(throwable -> log.error("Failed to unsubscribe from /subscription/hge/" + id, throwable));
//            log.info("subscribe");
            connection.subscribe("/subscription/hge/" + id, headers, frame -> {
                        log.info("Message to /subscription/hge/{}: {}", id, frame.getBodyAsString());
                        writeToFile(frame.getBodyAsString());
                        try {
                            final Message message = MAPPER.readValue(frame.getBodyAsString(), Message.class);
                            if ("lastFound".equals(message.getEvent())) {
                                final StarSystem entry = new StarSystem(message.getSystem(), message.getStarPos()[0], message.getStarPos()[1], message.getStarPos()[2]);
                                entry.setAllegiance(SystemAllegiance.forKey(message.getSystemAllegiance()));
                                lastFoundSystems.get(HorizonsMaterialType.valueOf(message.getCategory())).add(entry);
                                EventService.publish(new HighGradeEmissionLastFoundEvent());
                            } else {
                                LocalDateTime expiration;
                                if (message.getEvent().equalsIgnoreCase("FSSSignalDiscovered")) {
                                    expiration = ZonedDateTime.parse(message.getTimestamp()).toLocalDateTime().plusSeconds((int) Math.ceil(message.getTimeRemaining()));
                                } else {
                                    expiration = LocalDateTime.now().plusMinutes(5);
                                }
                                final ExpiringMessage expiringMessage = ExpiringMessage.builder().message(message).expiration(expiration).build();
                                messages.add(expiringMessage);
                                EventService.publish(new HighGradeEmissionEvent(expiringMessage));
                            }
                        } catch (JsonProcessingException e) {
                            log.error("Failed to parse message", e);
                        }
                    })
                    .onSuccess(frame -> {
                        log.info("Subscribed to /subscription/hge/" + id);
                        currentSubscription = "/subscription/hge/" + id;
                    })
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

    private static void writeToFile(String text) {
        try {
            FileOutputStream fos = new FileOutputStream("hge.txt", true);
            fos.write((text + "\r\n").getBytes());
            fos.close();
        } catch (Exception e) {
            log.error("Failed to write to file", e);
        }
    }

    public static synchronized void initialize() {
        try {
            vertx = Vertx.vertx();
            createClient();
            creaeteCleaner();
        } catch (Throwable t) {
            log.error("Failed to create vertx", t);
        }
        EVENT_LISTENERS.add(EventService.addStaticListener(JournalInitEvent.class, event -> {
            processing = event.isInitialised();
            if (event.isInitialised()) {
//                resubscribe();
            }
        }));
        EVENT_LISTENERS.add(EventService.addStaticListener(FSDJumpJournalEvent.class, event -> registerFactions(event.getEvent().getFactions().map(list -> list.stream().map(HighGradeEmissionService::mapFaction).toList()).orElse(Collections.emptyList()))));
        EVENT_LISTENERS.add(EventService.addStaticListener(CarrierJumpJournalEvent.class, event -> registerFactions(event.getEvent().getFactions().map(list -> list.stream().map(HighGradeEmissionService::mapFaction).toList()).orElse(Collections.emptyList()))));
        EVENT_LISTENERS.add(EventService.addStaticListener(LocationJournalEvent.class, event -> registerFactions(event.getLocation().getFactions().map(list -> list.stream().map(HighGradeEmissionService::mapFaction).toList()).orElse(Collections.emptyList()))));
        EVENT_LISTENERS.add(EventService.addStaticListener(TerminateApplicationEvent.class, event -> {
            terminating = true;
            client.close();
            vertx.close();
        }));
    }

    private static void creaeteCleaner() {
        vertx.setPeriodic(1000 * 10, (l) -> messages.removeIf(message -> LocalDateTime.now().isAfter(message.getExpiration())));
    }


    private static Faction mapFaction(nl.jixxed.eliteodysseymaterials.schemas.journal.FSDJump.Faction faction) {
        return new Faction(faction.getName(), faction.getFactionState(), faction.getInfluence().doubleValue(), faction.getAllegiance(), faction.getGovernment(), faction.getActiveStates().map(list -> list.stream().map(nl.jixxed.eliteodysseymaterials.schemas.journal.FSDJump.ActiveState::getState).toList()).orElse(List.of("None")));
    }

    private static Faction mapFaction(nl.jixxed.eliteodysseymaterials.schemas.journal.CarrierJump.Faction faction) {
        return new Faction(faction.getName(), faction.getFactionState(), faction.getInfluence().doubleValue(), faction.getAllegiance(), faction.getGovernment(), faction.getActiveStates().map(list -> list.stream().map(nl.jixxed.eliteodysseymaterials.schemas.journal.CarrierJump.ActiveState::getState).toList()).orElse(List.of("None")));
    }

    private static Faction mapFaction(nl.jixxed.eliteodysseymaterials.schemas.journal.Location.Faction faction) {
        return new Faction(faction.getName(), faction.getFactionState(), faction.getInfluence().doubleValue(), faction.getAllegiance(), faction.getGovernment(), faction.getActiveStates().map(list -> list.stream().map(nl.jixxed.eliteodysseymaterials.schemas.journal.Location.ActiveState::getState).toList()).orElse(List.of("None")));
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
            final StarSystem starSystem = currentLocation.getStarSystem();
            final Set<String> economies = Stream.of(starSystem.getPrimaryEconomy(), starSystem.getSecondaryEconomy())
                    .filter(Predicate.not(SystemEconomy.UNKNOWN::equals))
                    .map(SystemEconomy::getFriendlyName)
                    .collect(Collectors.toSet());
            Message message = Message.builder()
                    .timestamp(timestamp)
                    .event(event.getEvent())
                    .system(starSystem.getName())
                    .starPos(new Double[]{starSystem.getX(), starSystem.getY(), starSystem.getZ()})
                    .systemAllegiance(starSystem.getAllegiance().getKey())
                    .faction(event.getSpawningFaction().orElse(null))
                    .state(faction != null ? faction.factionState() : null)
                    .influence(faction.influence())
                    .allegiance(faction != null ? faction.allegiance() : null)
                    .government(faction != null ? faction.government() : null)
                    .systemEconomies(economies)
                    .timeRemaining(event.getTimeRemaining().map(BigDecimal::doubleValue).orElse(null))
                    .build();
            message(message);
            notifyExpectedMaterial(faction, starSystem);
        }
    }

    private static void notifyExpectedMaterial(Faction faction, final StarSystem starSystem) {
        String material = "";
//        log.info("FACTION: " + faction.toString());
        if (faction.inState(List.of("Outbreak"))) {
            //  Pharmaceutical Isolators
            material += LocaleService.getLocalizedStringForCurrentLocale("notification.hge.outbreak");
        } else if (faction.inState(List.of("Civil War", "War"))) {
            // Military Grade Alloys
            // Military Supercapacitors
            material += LocaleService.getLocalizedStringForCurrentLocale("notification.hge.civilwar");
        } else if (faction.inState(List.of("Boom"))) {
            // Proto Heat Radiators
            // Proto Radiolic Alloys / Proto Light Alloys
            material += LocaleService.getLocalizedStringForCurrentLocale("notification.hge.boom");
        } else if (faction.inState(List.of("Civil Unrest"))) {
            //Improvised Components
            material += LocaleService.getLocalizedStringForCurrentLocale("notification.hge.civilunrest");
        }

        if (SystemAllegiance.EMPIRE.equals(starSystem.getAllegiance())) {
            //Imperial Shielding
            material += LocaleService.getLocalizedStringForCurrentLocale("notification.hge.empire");
        } else if (SystemAllegiance.FEDERATION.equals(starSystem.getAllegiance())) {
            //Core Dynamic Composites / Proprietary Composites
            material += LocaleService.getLocalizedStringForCurrentLocale("notification.hge.federation");
        }

        if (material.isEmpty()) {
            material += LocaleService.getLocalizedStringForCurrentLocale("notification.hge.unknown");
        }

        final LocaleService.LocaleString text = LocaleService.LocaleString.of("notification.hge.common.text", starSystem.getAllegiance().getKey(), faction.factionState(), material);
        //        log.debug(text);
        NotificationService.showInformation(NotificationType.HGE, LocaleService.LocaleString.of("notification.hge.title"), text);
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
            final StarSystem starSystem = currentLocation.getStarSystem();
            final Set<String> economies = Stream.of(starSystem.getPrimaryEconomy(), starSystem.getSecondaryEconomy())
                    .filter(Predicate.not(SystemEconomy.UNKNOWN::equals))
                    .map(SystemEconomy::getFriendlyName)
                    .collect(Collectors.toSet());
            Message message = Message.builder()
                    .timestamp(timestamp)
                    .event("USSDrop")
                    .system(starSystem.getName())
                    .starPos(new Double[]{starSystem.getX(), starSystem.getY(), starSystem.getZ()})
                    .systemAllegiance(starSystem.getAllegiance().getKey())
                    .systemEconomies(economies)
                    .materialsFound(trackingMaterials)
                    .build();
            message(message);
            trackingUSS = false;
        }
    }

    public static List<StarSystem> getLastFoundSystems(HorizonsMaterialType materialType) {
        return lastFoundSystems.entrySet().stream().filter(entry -> materialType.equals(entry.getKey())).map(entry-> entry.getValue().asList()).findFirst().orElse(Collections.emptyList());
    }

    record Faction(String name, String factionState, Double influence, String allegiance, String government,
                   List<String> activeStates) {
        boolean isAllied(List<String> allegiances) {
            return allegiances.contains(allegiance);
        }

        boolean inState(List<String> states) {
            return states.contains(factionState);
        }
    }
}
