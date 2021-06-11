package nl.jixxed.eliteodysseymaterials.watchdog;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public class GameStateWatcher {
    private Optional<File> watchedFile = Optional.empty();

    public void watch(final File folder, final Consumer<File> fileProcessor, final String filename) {
        findLatestFile(folder, filename);
        this.watchedFile.ifPresent(fileProcessor);
        new FileWatcher(folder).addListener(new FileAdapter() {
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
        }).watch();
    }

    private void findLatestFile(final File folder, final String filename) {
        try {
            this.watchedFile = Arrays.stream(Objects.requireNonNull(folder.listFiles()))
                    .filter(file -> file.getName().equals(filename))
                    .findFirst();
            System.out.println("Registered watched file: " + this.watchedFile.map(File::getName).orElse(filename + " not found"));
        } catch (final NullPointerException ex) {
            System.out.println("Failed to Registered watched file at " + folder.getAbsolutePath());
        }
    }
}
