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
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.helper.CryptoHelper;
import nl.jixxed.eliteodysseymaterials.schemas.journal.FSDJump.SystemFaction;
import nl.jixxed.eliteodysseymaterials.schemas.journal.FSSSignalDiscovered.FSSSignalDiscovered;
import nl.jixxed.eliteodysseymaterials.schemas.journal.MaterialCollected.MaterialCollected;
import nl.jixxed.eliteodysseymaterials.schemas.journal.USSDrop.USSDrop;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.service.hge.ExpiringMessage;
import nl.jixxed.eliteodysseymaterials.service.hge.FactionV2;
import nl.jixxed.eliteodysseymaterials.service.hge.FixedSizeCircularReference;
import nl.jixxed.eliteodysseymaterials.service.hge.MessageV2;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
    private static Set<HorizonsMaterial> trackingMaterials = new HashSet<>();
    private static Map<HorizonsMaterialType, FixedSizeCircularReference<HgeStarSystem>> lastFoundSystems = new HashMap<>();
    private static final List<HorizonsMaterialType> HORIZONS_MATERIAL_TYPES = List.of(
            HorizonsMaterialType.CHEMICAL,
            HorizonsMaterialType.THERMIC,
            HorizonsMaterialType.CAPACITORS,
            HorizonsMaterialType.HEAT,
            HorizonsMaterialType.ALLOYS,
            HorizonsMaterialType.MECHANICAL_COMPONENTS,
            HorizonsMaterialType.SHIELDING,
            HorizonsMaterialType.COMPOSITE
    );

    static {

        for (HorizonsMaterialType materialType : HORIZONS_MATERIAL_TYPES) {
            lastFoundSystems.put(materialType, new FixedSizeCircularReference<>(10));
        }
    }

    private static String getUniqueId(Commander commander) {
        return CryptoHelper.sha256("afrev#$Fv123a", commander.getName() + commander.getFid());
    }

    private static void createClient() {
        String trustStorePath = ((VersionService.getBuildVersion() == null) ? System.getProperty("java.home") + "/" : RegistryService.CURRENT_DIR_SINGLE_SLASHED + "runtime/") + "lib/security/cacerts";
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
//        client.receivedFrameHandler(frame -> {
//            log.info("Received frame: {}", frame);
//        });
//        log.info("connect client");
//        connect();

    }

    private static String currentSubscription;

    private static void resubscribe() {
        if (currentSubscription != null) {
            try {
                log.info("unsubscribe from {}", currentSubscription);
                Map<String, String> headers = new HashMap<>();
                headers.put("id", currentSubscription.substring(currentSubscription.lastIndexOf("/") + 1));
                HighGradeEmissionService.connection.unsubscribe(currentSubscription, headers).result();
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

    private static void message(MessageV2 message, Double timeRemaining, Set<HorizonsMaterial> materials){
        LocalDateTime expiration;
        if (message.getEvent().equalsIgnoreCase("FSSSignalDiscovered") && timeRemaining != null) {
            expiration = ZonedDateTime.parse(message.getTimestamp()).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime().plusSeconds((int) Math.ceil(timeRemaining));
        } else {
            expiration = LocalDateTime.now().plusHours(1);
        }
        final ExpiringMessage expiringMessage = ExpiringMessage.builder()
                .timestamp(message.getTimestamp())
                .event(message.getEvent())
                .system(message.getSystem())
                .starPos(message.getStarPos())
                .systemAllegiance(message.getSystemAllegiance())
                .population(message.getPopulation())
                .faction(message.getFaction())
                .systemEconomies(message.getSystemEconomies())
                .factions(message.getFactions())
                .category(message.getCategory())
                .expiration(expiration).build();
        EventService.publish(new HighGradeEmissionEvent(expiringMessage, materials));
        ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
            if (connection == null) {
                connect().onSuccess(o -> send(message, commander));
            } else {
                send(message, commander);
            }
        });
    }

    private static void send(MessageV2 message, Commander commander) {
        Map<String, String> headers = new HashMap<>();
        headers.put("id", getUniqueId(commander));
        try {
            final String text = MAPPER.writeValueAsString(message);
//            writeToFile(text);
            connection.send("/post/hge2", headers, Buffer.buffer(text),
                    ar -> {
                        if (ar.succeeded()) {
//                            log.info("Completed sending message: {}", ar.result());
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
//            connection.unsubscribe("/subscription/hge2/" + id)
//                    .onSuccess(frame -> log.info("Unsubscribed from /subscription/hge2/" + id))
//                    .onFailure(throwable -> log.error("Failed to unsubscribe from /subscription/hge2/" + id, throwable));
//            log.info("subscribe");
            connection.subscribe("/subscription/hge2/" + id, headers, frame -> {
//                        log.info("Message to /subscription/hge2/{}: {}", id, frame.getBodyAsString());
//                        writeToFile(frame.getBodyAsString());
                        try {
                            final MessageV2 message = MAPPER.readValue(frame.getBodyAsString(), MessageV2.class);
                            log.debug(frame.getBodyAsString());
                            if ("USSDrop".equalsIgnoreCase(message.getEvent())) {
                                final HorizonsMaterialType materialType = message.getCategory();
                                if (HORIZONS_MATERIAL_TYPES.contains(materialType)) {
                                    final StarSystem entry = new StarSystem(message.getSystem(), message.getStarPos()[0], message.getStarPos()[1], message.getStarPos()[2]);
                                    entry.setAllegiance(message.getSystemAllegiance());
                                    if(message.getSystemEconomies() != null) {
                                        int index = 0;
                                        for (Economy systemEconomy : message.getSystemEconomies()) {
                                            if (index == 0) {
                                                index = 1;
                                                entry.setPrimaryEconomy(systemEconomy);
                                            } else {
                                                entry.setSecondaryEconomy(systemEconomy);
                                            }
                                        }
                                    }else{
                                        entry.setPrimaryEconomy(message.getPrimaryEconomy() != null ? message.getPrimaryEconomy() : Economy.NONE);
                                        entry.setSecondaryEconomy(message.getSecondaryEconomy() != null ? message.getSecondaryEconomy() : Economy.NONE);
                                    }
                                    if(message.getPopulation() != null) {
                                        entry.setPopulation(BigInteger.valueOf(message.getPopulation()));
                                    }
                                    final FixedSizeCircularReference<HgeStarSystem> circularReference = lastFoundSystems.get(materialType);
                                    circularReference.add(new HgeStarSystem(entry, message.getFactions()));
                                }
                            }
                            if ("lastFound".equalsIgnoreCase(message.getEvent())) {
                                final StarSystem entry = new StarSystem(message.getSystem(), message.getStarPos()[0], message.getStarPos()[1], message.getStarPos()[2]);
                                entry.setAllegiance(message.getSystemAllegiance());
                                if(message.getSystemEconomies() != null) {
                                    int index = 0;
                                    for (Economy systemEconomy : message.getSystemEconomies()) {
                                        if (index == 0) {
                                            index = 1;
                                            entry.setPrimaryEconomy(systemEconomy);
                                        } else {
                                            entry.setSecondaryEconomy(systemEconomy);
                                        }
                                    }
                                }else{
                                    entry.setPrimaryEconomy(message.getPrimaryEconomy() != null ? message.getPrimaryEconomy() : Economy.NONE);
                                    entry.setSecondaryEconomy(message.getSecondaryEconomy() != null ? message.getSecondaryEconomy() : Economy.NONE);
                                }
                                if(message.getPopulation() != null) {
                                    entry.setPopulation(BigInteger.valueOf(message.getPopulation()));
                                }


                                final HorizonsMaterialType materialType = message.getCategory();
                                final FixedSizeCircularReference<HgeStarSystem> circularReference = lastFoundSystems.get(materialType);
                                circularReference.add(new HgeStarSystem(entry, message.getFactions()));
                                EventService.publish(new HighGradeEmissionLastFoundEvent());
                            } else {
                                EventService.publish(new HighGradeEmissionReceivedEvent(message));
                            }
                        } catch (JsonProcessingException e) {
                            log.error("Failed to parse message", e);
                        }
                    })
                    .onSuccess(frame -> {
//                        log.info("Subscribed to /subscription/hge2/" + id);
                        currentSubscription = "/subscription/hge2/" + id;
                    })
                    .onFailure(throwable -> log.error("Failed to subscribe to /subscription/hge2/" + id, throwable))
                    .onComplete(ar -> {
                        if (ar.succeeded()) {
                            log.info("Completed subscription to /subscription/hge2/{}: {}", id, ar.result());
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
//            createCleaner();
        } catch (Throwable t) {
            log.error("Failed to create vertx", t);
        }
        EVENT_LISTENERS.add(EventService.addStaticListener(JournalInitEvent.class, event -> {
            processing = event.isInitialised();
            if (event.isInitialised()) {
                resubscribe();
            }
        }));
        EVENT_LISTENERS.add(EventService.addStaticListener(FSDJumpJournalEvent.class, event -> registerFactions(event.getEvent().getFactions().map(list -> list.stream().map(faction -> mapFaction(faction, event.getEvent().getSystemFaction().map(SystemFaction::getName).orElse("").equalsIgnoreCase(faction.getName()))).toList()).orElse(Collections.emptyList()))));
        EVENT_LISTENERS.add(EventService.addStaticListener(CarrierJumpJournalEvent.class, event -> registerFactions(event.getEvent().getFactions().map(list -> list.stream().map(faction -> mapFaction(faction, event.getEvent().getSystemFaction().map(nl.jixxed.eliteodysseymaterials.schemas.journal.CarrierJump.SystemFaction::getName).orElse("").equalsIgnoreCase(faction.getName()))).toList()).orElse(Collections.emptyList()))));
        EVENT_LISTENERS.add(EventService.addStaticListener(LocationJournalEvent.class, event -> registerFactions(event.getLocation().getFactions().map(list -> list.stream().map(faction -> mapFaction(faction, event.getLocation().getSystemFaction().map(nl.jixxed.eliteodysseymaterials.schemas.journal.Location.SystemFaction::getName).orElse("").equalsIgnoreCase(faction.getName()))).toList()).orElse(Collections.emptyList()))));
        EVENT_LISTENERS.add(EventService.addStaticListener(TerminateApplicationEvent.class, event -> {
            terminating = true;
            try {
                log.info("unsubscribe from {}", currentSubscription);
                Map<String, String> headers = new HashMap<>();
                headers.put("id", currentSubscription.substring(currentSubscription.lastIndexOf("/") + 1));
                HighGradeEmissionService.connection.unsubscribe(currentSubscription, headers).result();
            } catch (Exception e) {
                log.error("Failed to unsubscribe", e);
            }
            client.close();
            vertx.close();
        }));
    }

//    private static void createCleaner() {
//        vertx.setPeriodic(1000 * 10, (l) -> messages.removeIf(message -> LocalDateTime.now().isAfter(message.getExpiration())));
//    }


    private static Faction mapFaction(nl.jixxed.eliteodysseymaterials.schemas.journal.FSDJump.Faction faction, boolean isLeading) {
        return new Faction(
                faction.getName(),
                State.forName(faction.getFactionState()),
                faction.getInfluence().doubleValue(),
                Allegiance.forKey(faction.getAllegiance()),
                Government.forKey(faction.getGovernment()),
                faction.getActiveStates().map(list -> list.stream().map(nl.jixxed.eliteodysseymaterials.schemas.journal.FSDJump.ActiveState::getState).map(State::forName).toList()).orElse(Collections.emptyList()),
                faction.getPendingStates().map(list -> list.stream().map(nl.jixxed.eliteodysseymaterials.schemas.journal.FSDJump.PendingState::getState).map(State::forName).toList()).orElse(Collections.emptyList()),
                faction.getRecoveringStates().map(list -> list.stream().map(nl.jixxed.eliteodysseymaterials.schemas.journal.FSDJump.RecoveringState::getState).map(State::forName).toList()).orElse(Collections.emptyList()),
                isLeading);
    }

    private static Faction mapFaction(nl.jixxed.eliteodysseymaterials.schemas.journal.CarrierJump.Faction faction, boolean isLeading) {
        return new Faction(
                faction.getName(),
                State.forName(faction.getFactionState()),
                faction.getInfluence().doubleValue(),
                Allegiance.forKey(faction.getAllegiance()),
                Government.forKey(faction.getGovernment()),
                faction.getActiveStates().map(list -> list.stream().map(nl.jixxed.eliteodysseymaterials.schemas.journal.CarrierJump.ActiveState::getState).map(State::forName).toList()).orElse(Collections.emptyList()),
                faction.getPendingStates().map(list -> list.stream().map(nl.jixxed.eliteodysseymaterials.schemas.journal.CarrierJump.PendingState::getState).map(State::forName).toList()).orElse(Collections.emptyList()),
                faction.getRecoveringStates().map(list -> list.stream().map(nl.jixxed.eliteodysseymaterials.schemas.journal.CarrierJump.RecoveringState::getState).map(State::forName).toList()).orElse(Collections.emptyList()),
                isLeading);
    }

    private static Faction mapFaction(nl.jixxed.eliteodysseymaterials.schemas.journal.Location.Faction faction, boolean isLeading) {
        return new Faction(
                faction.getName(),
                State.forName(faction.getFactionState()),
                faction.getInfluence().doubleValue(),
                Allegiance.forKey(faction.getAllegiance()),
                Government.forKey(faction.getGovernment()),
                faction.getActiveStates().map(list -> list.stream().map(nl.jixxed.eliteodysseymaterials.schemas.journal.Location.ActiveState::getState).map(State::forName).toList()).orElse(Collections.emptyList()),
                faction.getPendingStates().map(list -> list.stream().map(nl.jixxed.eliteodysseymaterials.schemas.journal.Location.PendingState::getState).map(State::forName).toList()).orElse(Collections.emptyList()),
                faction.getRecoveringStates().map(list -> list.stream().map(nl.jixxed.eliteodysseymaterials.schemas.journal.Location.RecoveringState::getState).map(State::forName).toList()).orElse(Collections.emptyList()),
                isLeading);
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


//    { "timestamp":"2024-07-02T19:00:46Z",
//    "event":"FSSSignalDiscovered",
//    "SystemAddress":7267755828649,
//    "SignalName":"$USS_HighGradeEmissions;",
//    "SignalName_Localised":"Unidentified signal source",
//    "SignalType":"USS",
//    "USSType":"$USS_Type_VeryValuableSalvage;",
//    "USSType_Localised":"High grade emissions",
//    "SpawningState":"$FactionState_None;",
//    "SpawningState_Localised":"None",
//    "SpawningFaction":"$faction_none;",
//    "SpawningFaction_Localised":"None",
//    "ThreatLevel":0,
//    "TimeRemaining":2386.907471 }

    public static void fssSignalDiscovered(FSSSignalDiscovered event) {
        LocalDateTime now = ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime();
        if (processing
                && event.getUSSType().map(type -> type.equals("$USS_Type_VeryValuableSalvage;")).orElse(false)
                && now.minusMinutes(3).isBefore(event.getTimestamp())
                && now.plusMinutes(3).isAfter(event.getTimestamp())) {
            final Location currentLocation = LocationService.getCurrentLocation();
            final String timestamp = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").format(event.getTimestamp());
            final Optional<Faction> faction = FACTIONS.stream()
                    .filter(f -> {
                        final String spawningFaction = event.getSpawningFaction().orElse("");
                        return spawningFaction.equalsIgnoreCase("$faction_none;") || f.name().equals(spawningFaction);
                    })
                    .findFirst();
            final StarSystem starSystem = currentLocation.getStarSystem();
            final Allegiance systemAllegiance = !Allegiance.NONE.equals(starSystem.getAllegiance()) ? starSystem.getAllegiance() : FACTIONS.stream().filter(Faction::leading).findFirst().map(Faction::allegiance).orElse(Allegiance.NONE);
            final Set<Economy> economies = Stream.of(starSystem.getPrimaryEconomy(), starSystem.getSecondaryEconomy())
                    .filter(Predicate.not(Economy.UNKNOWN::equals))
                    .collect(Collectors.toSet());
            MessageV2 message = MessageV2.builder()
                    .timestamp(timestamp)
                    .event(event.getEvent())
                    .system(starSystem.getName())
                    .starPos(new Double[]{starSystem.getX(), starSystem.getY(), starSystem.getZ()})
                    .systemAllegiance(systemAllegiance)
                    .faction(event.getSpawningFaction().orElse("None"))
                    .population(starSystem.getPopulation().longValue())
                    .factions(FACTIONS.stream().map(f -> FactionV2.builder()
                            .name(f.name())
                            .influence(f.influence())
                            .state(f.state())
                            .allegiance(f.allegiance())
                            .government(f.government())
                            .activeStates(new HashSet<>(f.activeStates()))
                            .pendingStates(new HashSet<>(f.pendingStates()))
                            .recoveringStates(new HashSet<>(f.recoveringStates()))
                            .leading(f.leading())
                            .build()).collect(Collectors.toSet()))
                    .primaryEconomy(starSystem.getPrimaryEconomy())
                    .secondaryEconomy(starSystem.getSecondaryEconomy())
                    .build();
            message(message, event.getTimeRemaining().map(BigDecimal::doubleValue).orElse(null), getPotentialMaterials(systemAllegiance, faction.map(f-> f.activeStates().stream().collect(Collectors.toSet())).orElse(Collections.emptySet())));
            notifyExpectedMaterial(faction, starSystem);
        }
    }
    private static Set<HorizonsMaterial> getPotentialMaterials(Allegiance allegiance, Set<State> states){
        Set<HorizonsMaterial> materials = new HashSet<>();
        if(states.stream().anyMatch(List.of(State.WAR,State.CIVILWAR)::contains)){
            materials.add(Manufactured.MILITARYGRADEALLOYS);
            materials.add(Manufactured.MILITARYSUPERCAPACITORS);
        }
        if(states.stream().anyMatch(List.of(State.BOOM,State.EXPANSION)::contains)){
            materials.add(Manufactured.PROTOLIGHTALLOYS);
            materials.add(Manufactured.PROTORADIOLICALLOYS);
            materials.add(Manufactured.PROTOHEATRADIATORS);
        }
        if(states.stream().anyMatch(List.of(State.CIVILUNREST)::contains)){
            materials.add(Manufactured.IMPROVISEDCOMPONENTS);
        }
        if(states.stream().anyMatch(List.of(State.OUTBREAK)::contains)){
            materials.add(Manufactured.PHARMACEUTICALISOLATORS);
        }
        if(Allegiance.FEDERATION.equals(allegiance)){
            materials.add(Manufactured.FEDCORECOMPOSITES);
            materials.add(Manufactured.FEDPROPRIETARYCOMPOSITES);
        }
        if(Allegiance.EMPIRE.equals(allegiance)){
            materials.add(Manufactured.IMPERIALSHIELDING);
        }
        return materials;
    }
    private static void notifyExpectedMaterial(Optional<Faction> faction, final StarSystem starSystem) {
        final Allegiance allegiance = Allegiance.NONE.equals(starSystem.getAllegiance()) ? faction.map(Faction::allegiance).orElse(Allegiance.NONE) : starSystem.getAllegiance();
        String material = "";
//        log.info("FACTION: " + faction.toString());
        if (faction.map(f -> f.inState(List.of(State.OUTBREAK))).orElse(false)) {
            //  Pharmaceutical Isolators
            material += LocaleService.getLocalizedStringForCurrentLocale("notification.hge.outbreak.text");
        } else if (faction.map(f -> f.inState(List.of(State.WAR,State.CIVILWAR))).orElse(false)) {
            // Military Grade Alloys
            // Military Supercapacitors
            material += LocaleService.getLocalizedStringForCurrentLocale("notification.hge.civilwar.text");
        } else if (faction.map(f -> f.inState(List.of(State.BOOM,State.EXPANSION))).orElse(false)) {
            // Proto Heat Radiators
            // Proto Radiolic Alloys / Proto Light Alloys
            material += LocaleService.getLocalizedStringForCurrentLocale("notification.hge.boom.text");
        } else  if (faction.map(f -> f.inState(List.of(State.CIVILUNREST))).orElse(false)) {
            //Improvised Components
            material += LocaleService.getLocalizedStringForCurrentLocale("notification.hge.civilunrest.text");
        }

        if (Allegiance.EMPIRE.equals(allegiance)) {
            //Imperial Shielding
            material += LocaleService.getLocalizedStringForCurrentLocale("notification.hge.empire.text");
        } else if (Allegiance.FEDERATION.equals(allegiance)) {
            //Core Dynamic Composites / Proprietary Composites
            material += LocaleService.getLocalizedStringForCurrentLocale("notification.hge.federation.text");
        }

        if (material.isEmpty()) {
            material += LocaleService.getLocalizedStringForCurrentLocale("notification.hge.unknown.text");
        }

        final LocaleService.LocaleString text = LocaleService.LocaleString.of("notification.hge.common.text", LocaleService.LocalizationKey.of(allegiance.getLocalizationKey()), faction.map(f -> LocaleService.LocalizationKey.of(f.state().getLocalizationKey())).orElse(LocaleService.LocalizationKey.of("state.none")), material);
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
            trackingMaterials.add(Manufactured.forName(event.getName()));
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
            final Set<Economy> economies = Stream.of(starSystem.getPrimaryEconomy(), starSystem.getSecondaryEconomy())
                    .filter(Predicate.not(Economy.UNKNOWN::equals))
                    .collect(Collectors.toSet());
            final Allegiance systemAllegiance = FACTIONS.stream().filter(Faction::leading).findFirst().map(Faction::allegiance).orElse(Allegiance.NONE);
            final Set<FactionV2> factions = FACTIONS.stream().map(f -> FactionV2.builder()
                    .name(f.name())
                    .influence(f.influence())
                    .state(f.state())
                    .allegiance(f.allegiance())
                    .government(f.government())
                    .activeStates(new HashSet<>(f.activeStates()))
                    .pendingStates(new HashSet<>(f.pendingStates()))
                    .recoveringStates(new HashSet<>(f.recoveringStates()))
                    .leading(f.leading())
                    .build()).collect(Collectors.toSet());
            MessageV2 message = MessageV2.builder()
                    .timestamp(timestamp)
                    .event("USSDrop")
                    .system(starSystem.getName())
                    .starPos(new Double[]{starSystem.getX(), starSystem.getY(), starSystem.getZ()})
                    .systemAllegiance(systemAllegiance)
                    .population(starSystem.getPopulation().longValue())
                    .factions(factions)
                    .primaryEconomy(starSystem.getPrimaryEconomy())
                    .secondaryEconomy(starSystem.getSecondaryEconomy())
                    .category(trackingMaterials.stream().findFirst().map(HorizonsMaterial::getMaterialType).orElse(HorizonsMaterialType.UNKNOWN))
                    .build();
            if(!HorizonsMaterialType.UNKNOWN.equals(message.getCategory())) {
                lastFoundSystems.get(message.getCategory()).add(new HgeStarSystem(starSystem, factions));
            }
            message(message, null, trackingMaterials);
            trackingUSS = false;
        }
    }


    public static List<HgeStarSystem> getLastFoundSystems(HorizonsMaterialType materialType) {
        return lastFoundSystems.entrySet().stream().filter(entry -> materialType.equals(entry.getKey())).map(entry -> entry.getValue().asList()).findFirst().orElse(Collections.emptyList());
        //TODO TEST
//        return List.of(
//                new HgeStarSystem(
//                        new StarSystem("LDS 413", Economy.AGRI,Economy.COLONY, Government.COMMUNISM, Security.HIGH, Allegiance.INDEPENDENT, BigInteger.valueOf(10000000L), State.NONE, 1.0, 2.0, 3.0),
//                        Set.of(
//                                new FactionV2("LDS 413 Resistance 1", 0.5D, State.BOOM, Allegiance.INDEPENDENT, Government.COOPERATIVE, Set.of(), Set.of(State.BOOM), Set.of(), true),
//                                new FactionV2("LDS 413 Resistance 2", 0.1D, State.ELECTION, Allegiance.INDEPENDENT, Government.COOPERATIVE, Set.of(), Set.of(State.ELECTION), Set.of(), true),
//                                new FactionV2("LDS 413 Resistance 3", 0.1D, State.WAR, Allegiance.INDEPENDENT, Government.COOPERATIVE, Set.of(), Set.of(State.WAR), Set.of(), true),
//                                new FactionV2("LDS 413 Resistance 4", 0.1D, State.WAR, Allegiance.INDEPENDENT, Government.COOPERATIVE, Set.of(), Set.of(State.WAR), Set.of(), true),
//                                new FactionV2("LDS 413 Resistance 5", 0.1D, State.EXPANSION, Allegiance.INDEPENDENT, Government.COOPERATIVE, Set.of(), Set.of(State.EXPANSION), Set.of(), true),
//                                new FactionV2("LDS 413 Resistance 6", 0.1D, State.CIVILUNREST, Allegiance.INDEPENDENT, Government.COOPERATIVE, Set.of(), Set.of(State.CIVILUNREST), Set.of(), true)
//                        )
//                ),
//                new HgeStarSystem(
//                        new StarSystem("SOL 413", Economy.AGRI,Economy.COLONY, Government.COMMUNISM, Security.HIGH, Allegiance.FEDERATION, BigInteger.valueOf(10000000L), State.NONE, 6.0, 5.0, 4.0),
//                        Set.of(
//                                new FactionV2("SOL 413 Resistance 1", 0.5D, State.BOOM, Allegiance.FEDERATION, Government.CORPORATE, Set.of(), Set.of(State.BOOM), Set.of(), true),
//                                new FactionV2("SOL 413 Resistance 2", 0.1D, State.ELECTION, Allegiance.FEDERATION, Government.CORPORATE, Set.of(), Set.of(State.OUTBREAK), Set.of(), true),
//                                new FactionV2("SOL 413 Resistance 3", 0.1D, State.WAR, Allegiance.FEDERATION, Government.CORPORATE, Set.of(), Set.of(State.CIVILWAR), Set.of(), true),
//                                new FactionV2("SOL 413 Resistance 4", 0.1D, State.WAR, Allegiance.FEDERATION, Government.CORPORATE, Set.of(), Set.of(State.CIVILWAR), Set.of(), true),
//                                new FactionV2("SOL 413 Resistance 5", 0.1D, State.EXPANSION, Allegiance.FEDERATION, Government.CORPORATE, Set.of(), Set.of(State.EXPANSION), Set.of(), true),
//                                new FactionV2("SOL 413 Resistance 6", 0.1D, State.CIVILUNREST, Allegiance.FEDERATION, Government.CORPORATE, Set.of(), Set.of(State.CIVILUNREST), Set.of(), true)
//                        )
//                ),
//                new HgeStarSystem(
//                        new StarSystem("BAN 413", Economy.AGRI,Economy.COLONY, Government.COMMUNISM, Security.HIGH, Allegiance.EMPIRE, BigInteger.valueOf(10000000L), State.NONE, 7.0, 8.0, 9.0),
//                        Set.of(
//                                new FactionV2("BAN 413 Resistance 1", 0.5D, State.BOOM, Allegiance.EMPIRE, Government.CORPORATE, Set.of(), Set.of(State.BOOM), Set.of(), true),
//                                new FactionV2("BAN 413 Resistance 2", 0.1D, State.OUTBREAK, Allegiance.EMPIRE, Government.CORPORATE, Set.of(), Set.of(State.OUTBREAK), Set.of(), true),
//                                new FactionV2("BAN 413 Resistance 3", 0.1D, State.CIVILWAR, Allegiance.EMPIRE, Government.CORPORATE, Set.of(), Set.of(State.CIVILWAR), Set.of(), true),
//                                new FactionV2("BAN 413 Resistance 4", 0.1D, State.CIVILWAR, Allegiance.EMPIRE, Government.CORPORATE, Set.of(), Set.of(State.CIVILWAR), Set.of(), true),
//                                new FactionV2("BAN 413 Resistance 5", 0.1D, State.EXPANSION, Allegiance.EMPIRE, Government.CORPORATE, Set.of(), Set.of(State.EXPANSION), Set.of(), true),
//                                new FactionV2("BAN 413 Resistance 6", 0.1D, State.CIVILUNREST, Allegiance.EMPIRE, Government.CORPORATE, Set.of(), Set.of(State.CIVILUNREST), Set.of(), true)
//                        )
//                )
//        );
    }

    record Faction(String name, State state, Double influence, Allegiance allegiance, Government government,
                   List<State> activeStates, List<State> pendingStates, List<State> recoveringStates, boolean leading) {

        boolean isAllied(List<Allegiance> allegiances) {
            return allegiances.contains(allegiance);
        }

        boolean inState(List<State> states) {
            return activeStates.stream().anyMatch(states::contains);
        }
    }
}
