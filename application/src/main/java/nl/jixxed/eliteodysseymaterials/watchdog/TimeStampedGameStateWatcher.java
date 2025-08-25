package nl.jixxed.eliteodysseymaterials.watchdog;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.JournalEventType;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

@Slf4j
public class TimeStampedGameStateWatcher {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final GameStateWatcher gameStateWatcher = new GameStateWatcher();
    private final AtomicReference<String> timeStamp = new AtomicReference<>("");
    private final List<EventListener<?>> eventListeners = new ArrayList<>();
    private final JournalEventType[] eventType;
    private final Consumer<Optional<File>> fileProcessor;
    private File watchedFile = null;
    private final Map<JournalEventType, Consumer<? extends TimestampedEvent>> eventConsumers = new HashMap<>();

    public TimeStampedGameStateWatcher(final File folder, final Consumer<Optional<File>> fileProcessor, final String filename, final boolean allowPolling, final JournalEventType... eventType) {
        this.eventType = eventType;
        this.fileProcessor = fileProcessor;
        this.gameStateWatcher.watch(folder, this::process, filename, allowPolling, 100, eventType);

        for (JournalEventType type : eventType) {
            switch (type) {
                case SHIPLOCKER -> addConsumer(type, ShipLockerEvent.class);
                case BACKPACK -> addConsumer(type, BackpackEvent.class);
                case CARGO -> addConsumer(type, CargoEvent.class);
                case MARKET -> addConsumer(type, MarketEvent.class);
                case NAVROUTE -> addConsumer(type, NavRouteEvent.class);
                case NAVROUTECLEAR -> addConsumer(type, NavRouteClearEvent.class);
                case OUTFITTING -> addConsumer(type, OutfittingEvent.class);
                case SHIPYARD -> addConsumer(type, ShipyardEvent.class);
                case MODULEINFO -> addConsumer(type, ModuleInfoEvent.class);
                case FCMATERIALS -> addConsumer(type, FCMaterialsEvent.class);
            }
        }
    }

    private <T extends TimestampedEvent> void addConsumer(final JournalEventType eventType, final Class<T> type) {
        Consumer<? extends TimestampedEvent> consumer = (Consumer<T>) this::handleEvent;
        this.eventConsumers.put(eventType, consumer);
        this.eventListeners.add(EventService.addListener(this, type, (Consumer<T>) consumer));
    }

    private <T extends TimestampedEvent> void handleEvent(T event) {
        this.timeStamp.set(event.getTimestamp());
        this.process(Optional.ofNullable(this.watchedFile));
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
    private synchronized void process(final Optional<File> fileToProcess) {
        this.watchedFile = fileToProcess.orElse(null);
        fileToProcess.ifPresent(file -> {
            try {
                final String message = Files.readString(file.toPath());
                final JsonNode jsonNode = OBJECT_MAPPER.readTree(message);
                if (jsonNode.get("event") != null
                        && Arrays.stream(this.eventType).anyMatch(event -> jsonNode.get("event").asText().equalsIgnoreCase(event.name()))
                        && jsonNode.get("timestamp") != null
                        && jsonNode.get("timestamp").asText().equals(this.timeStamp.get())) {
                    this.fileProcessor.accept(fileToProcess);
                    this.watchedFile = null;
                    log.info("Process " + jsonNode.get("event").asText());
                }
            } catch (final IOException e) {
                log.error("Error processing state file message", e);
            }
        });
    }
}
