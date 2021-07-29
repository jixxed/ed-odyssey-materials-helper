package nl.jixxed.eliteodysseymaterials.watchdog;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.service.event.BackpackEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ShipLockerEvent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.function.Consumer;

@Slf4j
public class TimeStampedGameStateWatcher {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final GameStateWatcher gameStateWatcher = new GameStateWatcher();
    private final Consumer<File> fileProcessor;
    private String timeStamp = "";
    private File file = null;
    private final StoragePool storagePool;

    public TimeStampedGameStateWatcher(final File folder, final Consumer<File> fileProcessor, final String filename, final StoragePool storagePool) {
        this.storagePool = storagePool;
        this.fileProcessor = fileProcessor;
        this.gameStateWatcher.watch(folder, this::process, filename);
        switch (storagePool) {
            case SHIPLOCKER -> EventService.addListener(ShipLockerEvent.class, shipLockerEvent -> {
                this.timeStamp = shipLockerEvent.getTimestamp();
                this.process(this.file);
            });
            case BACKPACK -> EventService.addListener(BackpackEvent.class, backpackEvent -> {
                this.timeStamp = backpackEvent.getTimestamp();
                this.process(this.file);
            });
        }
    }

    public void stop() {
        this.gameStateWatcher.stop();
    }

    private synchronized void process(final File file) {
        this.file = file;
        if (this.file != null) {
            try {
                final String message = Files.readString(file.toPath());

                final JsonNode jsonNode = OBJECT_MAPPER.readTree(message);
                if (jsonNode.get("event") != null
                        && jsonNode.get("event").asText().equalsIgnoreCase(this.storagePool.name())
                        && jsonNode.get("timestamp") != null
                        && jsonNode.get("timestamp").asText().equals(this.timeStamp)) {
                    this.fileProcessor.accept(file);
                    this.file = null;
                    log.info("Process " + jsonNode.get("event").asText());
                }
            } catch (final IOException e) {
                log.error("Error processing journal message", e);
            }
        }
    }
}
