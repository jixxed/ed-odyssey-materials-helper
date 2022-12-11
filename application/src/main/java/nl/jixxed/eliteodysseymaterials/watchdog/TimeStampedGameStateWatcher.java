package nl.jixxed.eliteodysseymaterials.watchdog;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.JournalEventType;
import nl.jixxed.eliteodysseymaterials.service.event.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

@Slf4j
public class TimeStampedGameStateWatcher {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final GameStateWatcher gameStateWatcher = new GameStateWatcher();
    private final AtomicReference<String> timeStamp = new AtomicReference<>("");
    private final JournalEventType[] eventType;
    private final Consumer<File> fileProcessor;
    private File file = null;
    private final Map<JournalEventType,Consumer<? extends Event>> eventConsumers = new HashMap<>();

    public TimeStampedGameStateWatcher(final File folder, final Consumer<File> fileProcessor, final String filename, final JournalEventType... eventType) {
        this.eventType = eventType;
        this.fileProcessor = fileProcessor;
        this.gameStateWatcher.watch(folder, this::process, filename, eventType);
        if (Arrays.asList(eventType).contains(JournalEventType.SHIPLOCKER)) {
            final Consumer<ShipLockerEvent> consumer = (ShipLockerEvent shipLockerEvent) -> {
                this.timeStamp.set(shipLockerEvent.getTimestamp());
                this.process(this.file);
            };
            this.eventConsumers.put(JournalEventType.SHIPLOCKER,consumer);
            EventService.addListener(this, ShipLockerEvent.class, consumer);
        }
        if (Arrays.asList(eventType).contains(JournalEventType.BACKPACK)) {
            final Consumer<BackpackEvent> consumer = (BackpackEvent backpackEvent) -> {
                this.timeStamp.set(backpackEvent.getTimestamp());
                this.process(this.file);
            };
            this.eventConsumers.put(JournalEventType.BACKPACK,consumer);
            EventService.addListener(this, BackpackEvent.class, consumer);
        }
        if (Arrays.asList(eventType).contains(JournalEventType.CARGO)) {
            final Consumer<CargoEvent> consumer = (CargoEvent cargoEvent) -> {
                this.timeStamp.set(cargoEvent.getTimestamp());
                this.process(this.file);
            };
            this.eventConsumers.put(JournalEventType.CARGO,consumer);
            EventService.addListener(this, CargoEvent.class, consumer);
        }
        if (Arrays.asList(eventType).contains(JournalEventType.MARKET)) {
            final Consumer<MarketEvent> consumer = (MarketEvent marketEvent) -> {
                this.timeStamp.set(marketEvent.getTimestamp());
                this.process(this.file);
            };
            this.eventConsumers.put(JournalEventType.MARKET,consumer);
            EventService.addListener(this, MarketEvent.class, consumer);
        }
        if (Arrays.asList(eventType).contains(JournalEventType.NAVROUTE)) {
            final Consumer<NavRouteEvent> consumer = (NavRouteEvent navRouteEvent) -> {
                this.timeStamp.set(navRouteEvent.getTimestamp());
                this.process(this.file);
            };
            this.eventConsumers.put(JournalEventType.NAVROUTE,consumer);
            EventService.addListener(this, NavRouteEvent.class, consumer);
        }
        if (Arrays.asList(eventType).contains(JournalEventType.NAVROUTECLEAR)) {
            final Consumer<NavRouteClearEvent> consumer = (NavRouteClearEvent navRouteClearEvent) -> {
                this.timeStamp.set(navRouteClearEvent.getTimestamp());
                this.process(this.file);
            };
            this.eventConsumers.put(JournalEventType.NAVROUTECLEAR,consumer);
            EventService.addListener(this, NavRouteClearEvent.class, consumer);
        }
        if (Arrays.asList(eventType).contains(JournalEventType.OUTFITTING)) {
            final Consumer<OutfittingEvent> consumer = (OutfittingEvent outfittingEvent) -> {
                this.timeStamp.set(outfittingEvent.getTimestamp());
                this.process(this.file);
            };
            this.eventConsumers.put(JournalEventType.OUTFITTING,consumer);
            EventService.addListener(this, OutfittingEvent.class, consumer);
        }
        if (Arrays.asList(eventType).contains(JournalEventType.SHIPYARD)) {
            final Consumer<ShipyardEvent> consumer = (ShipyardEvent shipyardEvent) -> {
                this.timeStamp.set(shipyardEvent.getTimestamp());
                this.process(this.file);
            };
            this.eventConsumers.put(JournalEventType.SHIPYARD,consumer);
            EventService.addListener(this, ShipyardEvent.class, consumer);
        }
        if (Arrays.asList(eventType).contains(JournalEventType.MODULEINFO)) {
            final Consumer<ModuleInfoEvent> consumer = (ModuleInfoEvent moduleInfoEvent) -> {
                this.timeStamp.set(moduleInfoEvent.getTimestamp());
                this.process(this.file);
            };
            this.eventConsumers.put(JournalEventType.MODULEINFO,consumer);
            EventService.addListener(this, ModuleInfoEvent.class, consumer);
        }
        if (Arrays.asList(eventType).contains(JournalEventType.FCMATERIALS)) {
            final Consumer<FCMaterialsEvent> consumer = (FCMaterialsEvent fcMaterialsEvent) -> {
                this.timeStamp.set(fcMaterialsEvent.getTimestamp());
                this.process(this.file);
            };
            this.eventConsumers.put(JournalEventType.FCMATERIALS,consumer);
            EventService.addListener(this, FCMaterialsEvent.class, consumer);
        }
    }

    public void stop() {
        this.gameStateWatcher.stop();
        if (Arrays.asList(this.eventType).contains(JournalEventType.SHIPLOCKER)) {
            EventService.removeListener(this.eventConsumers.get(JournalEventType.SHIPLOCKER), ShipLockerEvent.class);
        }
        if (Arrays.asList(this.eventType).contains(JournalEventType.BACKPACK)) {
            EventService.removeListener(this.eventConsumers.get(JournalEventType.BACKPACK), BackpackEvent.class);
        }
        if (Arrays.asList(this.eventType).contains(JournalEventType.CARGO)) {
            EventService.removeListener(this.eventConsumers.get(JournalEventType.CARGO), CargoEvent.class);
        }
        if (Arrays.asList(this.eventType).contains(JournalEventType.MARKET)) {
            EventService.removeListener(this.eventConsumers.get(JournalEventType.MARKET), MarketEvent.class);
        }
        if (Arrays.asList(this.eventType).contains(JournalEventType.NAVROUTE)) {
            EventService.removeListener(this.eventConsumers.get(JournalEventType.NAVROUTE), NavRouteEvent.class);
        }
        if (Arrays.asList(this.eventType).contains(JournalEventType.NAVROUTECLEAR)) {
            EventService.removeListener(this.eventConsumers.get(JournalEventType.NAVROUTECLEAR), NavRouteClearEvent.class);
        }
        if (Arrays.asList(this.eventType).contains(JournalEventType.OUTFITTING)) {
            EventService.removeListener(this.eventConsumers.get(JournalEventType.OUTFITTING), OutfittingEvent.class);
        }
        if (Arrays.asList(this.eventType).contains(JournalEventType.SHIPYARD)) {
            EventService.removeListener(this.eventConsumers.get(JournalEventType.SHIPYARD), ShipyardEvent.class);
        }
        if (Arrays.asList(this.eventType).contains(JournalEventType.MODULEINFO)) {
            EventService.removeListener(this.eventConsumers.get(JournalEventType.MODULEINFO), ModuleInfoEvent.class);
        }
        if (Arrays.asList(this.eventType).contains(JournalEventType.FCMATERIALS)) {
            EventService.removeListener(this.eventConsumers.get(JournalEventType.FCMATERIALS), FCMaterialsEvent.class);
        }
    }

    @SuppressWarnings("java:S1192")
    private synchronized void process(final File file) {
        this.file = file;
        if (this.file != null) {
            try {
                final String message = Files.readString(file.toPath());
                final JsonNode jsonNode = OBJECT_MAPPER.readTree(message);
                if (jsonNode.get("event") != null
                        && Arrays.stream(this.eventType).anyMatch(event -> jsonNode.get("event").asText().equalsIgnoreCase(event.name()))
                        && jsonNode.get("timestamp") != null
                        && jsonNode.get("timestamp").asText().equals(this.timeStamp.get())) {
                    this.fileProcessor.accept(file);
                    this.file = null;
                    log.info("Process " + jsonNode.get("event").asText());
                }
            } catch (final IOException e) {
                log.error("Error processing state file message", e);
            }
        }
    }
}
