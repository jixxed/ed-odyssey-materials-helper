package nl.jixxed.eliteodysseymaterials.watchdog;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

@Slf4j
public class StatusWatcher {
    private Optional<File> watchedFile = Optional.empty();
    private final FileWatcher fileWatcher;

    public StatusWatcher(final File folder, final Consumer<File> fileProcessor, final String filename) {
        findFile(folder, filename);
        this.watchedFile.ifPresent(fileProcessor);
        this.fileWatcher = new FileWatcher("Status Watcher Thread", true).withListener(new FileListener() {
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
                    StatusWatcher.this.watchedFile = Optional.of(file);
                    fileProcessor.accept(file);
                }

            }

            @Override
            public void onDeleted(final FileEvent event) {

            }
        }).watch(folder);
    }

    private void findFile(final File folder, final String filename) {
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
