package nl.jixxed.eliteodysseymaterials.watchdog;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public class JournalWatcher {
    private Optional<File> watchedFile = Optional.empty();

    public void watch(final File folder, final Consumer<File> fileModifiedProcessor, final Consumer<File> fileCreatedProcessor) {
        findLatestFile(folder);
        this.watchedFile.ifPresent(fileCreatedProcessor);
        new FileWatcher(folder).addListener(new FileAdapter() {
            @Override
            public void onCreated(final FileEvent event) {
                final File file = event.getFile();
                if (file.isFile()) {
                    if (file.getName().startsWith("Journal.")) {
                        JournalWatcher.this.watchedFile = Optional.of(file);
                        fileCreatedProcessor.accept(file);
                    }
                }
            }

            @Override
            public void onModified(final FileEvent event) {
                final File file = event.getFile();
                JournalWatcher.this.watchedFile
                        .filter(file::equals)
                        .ifPresent(fileModifiedProcessor);
            }
        }).watch();
    }

    private void findLatestFile(final File folder) {
        this.watchedFile = Arrays.stream(Objects.requireNonNull(folder.listFiles()))
                .filter(file -> file.getName().startsWith("Journal."))
                .max(Comparator.comparingLong(file -> Long.parseLong(file.getName().substring(8, 20))));

        System.out.println("Registered watched file: " + this.watchedFile.map(File::getName).orElse("No file"));
    }
}
