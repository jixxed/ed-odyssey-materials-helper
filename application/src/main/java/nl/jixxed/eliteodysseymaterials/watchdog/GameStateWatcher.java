package nl.jixxed.eliteodysseymaterials.watchdog;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

@Slf4j
public class GameStateWatcher {
    private Optional<File> watchedFile = Optional.empty();
    private FileWatcher fileWatcher;

    public void watch(final File folder, final Consumer<File> fileProcessor, final String filename) {
        findLatestFile(folder, filename);
        this.watchedFile.ifPresent(fileProcessor);
        this.fileWatcher = new FileWatcher(folder);
        this.fileWatcher.addListener(new FileAdapter() {
            @Override
            public void onCreated(final FileEvent event) {
                handleFile(event, fileProcessor);
            }

            @Override
            public void onModified(final FileEvent event) {
                handleFile(event, fileProcessor);
            }

            private void handleFile(final FileEvent event, final Consumer<File> shipLockerFileProcessor) {
                final File file = event.getFile();
                if (file.isFile()) {
                    if (file.getName().equals(filename)) {
                        GameStateWatcher.this.watchedFile = Optional.of(file);
                        shipLockerFileProcessor.accept(file);
                    }
                }
            }
        }).watch(filename + " Watcher Thread");
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
        this.fileWatcher.stop();
    }
}
