package nl.jixxed.eliteodysseymaterials.watchdog;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.JournalEventType;

import java.io.File;
import java.util.Optional;
import java.util.function.Consumer;

@Slf4j
public class GameStateWatcher {
    private Optional<File> watchedFile = Optional.empty();
    private FileWatcher fileWatcher;
    private boolean allowPolling;

    public void watch(final File folder, final Consumer<Optional<File>> fileProcessor, final String filename, final boolean allowPolling, final JournalEventType... eventType) {
        findLatestFile(folder, filename);
        this.allowPolling = allowPolling;
        this.watchedFile.ifPresent((f) -> fileProcessor.accept(this.watchedFile));
        this.fileWatcher = new FileWatcher(allowPolling).withListener(new FileAdapter() {
            @Override
            public void onCreated(final FileEvent event) {
                handleFile(event, fileProcessor);
            }

            @Override
            public void onModified(final FileEvent event) {
                handleFile(event, fileProcessor);
            }

            @Override
            public void onDeleted(FileEvent event) {
                handleDelete(event, fileProcessor);
            }

            private void handleFile(final FileEvent event, final Consumer<Optional<File>> fileProcessor) {
                final File file = event.getFile();
                if (file.isFile() && file.getName().equals(filename)) {
                    GameStateWatcher.this.watchedFile = Optional.of(file);
                    fileProcessor.accept(Optional.of(file));
                }
            }
            private void handleDelete(final FileEvent event, final Consumer<Optional<File>> fileProcessor) {
                final File file = event.getFile();
                if (file.getName().equals(filename)) {
                    fileProcessor.accept(Optional.empty());
                }
            }
        }).watch(folder);
    }

    private void findLatestFile(final File folder, final String filename) {
        try {
            this.watchedFile = FileService.listFiles(folder, this.allowPolling).stream()
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
