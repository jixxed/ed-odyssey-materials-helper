package nl.jixxed.eliteodysseymaterials.watchdog;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.JournalEventType;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Slf4j
public class GameStateWatcher {
    private Optional<File> watchedFile = Optional.empty();
    private FileWatcher fileWatcher;

    public void watch(final File folder, final Consumer<File> fileProcessor, final String filename, final JournalEventType... eventType) {
        findLatestFile(folder, filename);
        this.watchedFile.ifPresent(fileProcessor);
        this.fileWatcher = new FileWatcher(Arrays.stream(eventType).map(Enum::name).collect(Collectors.joining("&")) + " Watcher Thread", true).withListener(new FileAdapter() {
            @Override
            public void onCreated(final FileEvent event) {
                handleFile(event, fileProcessor);
            }

            @Override
            public void onModified(final FileEvent event) {
                handleFile(event, fileProcessor);
            }

            private void handleFile(final FileEvent event, final Consumer<File> fileProcessor) {
                final File file = event.getFile();
                if (file.isFile() && file.getName().equals(filename)) {
                    GameStateWatcher.this.watchedFile = Optional.of(file);
                    fileProcessor.accept(file);
                }

            }
        }).watch(folder);
    }

    private void findLatestFile(final File folder, final String filename) {
        try {
            this.watchedFile = Arrays.stream(Objects.requireNonNull(folder.listFiles()))
                    .filter(file -> file.getName().equals(filename))
                    .findFirst();
            log.info("Registered watched file: " + this.watchedFile.map(File::getName).orElse(filename + " not found"));
        } catch (final NullPointerException ex) {
            log.error("Failed to Registered watched file at " + folder.getAbsolutePath());
        }
    }

    public void stop() {
        if (this.fileWatcher != null) {
            this.fileWatcher.stop();
        }
    }
}
