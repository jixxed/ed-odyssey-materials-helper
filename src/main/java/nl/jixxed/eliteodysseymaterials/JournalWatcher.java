package nl.jixxed.eliteodysseymaterials;

import nl.jixxed.eliteodysseymaterials.filewatcher.FileAdapter;
import nl.jixxed.eliteodysseymaterials.filewatcher.FileEvent;
import nl.jixxed.eliteodysseymaterials.filewatcher.FileWatcher;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public class JournalWatcher {
    private Optional<File> watchedFile = Optional.empty();

    protected void watch(File folder, Consumer<File> fileModifiedProcessor, Consumer<File> fileCreatedProcessor) {
        findLatestFile(folder);
        watchedFile.ifPresent(fileCreatedProcessor);
        new FileWatcher(folder).addListener(new FileAdapter() {
            @Override
            public void onCreated(FileEvent event) {
                File file = event.getFile();
                if (file.isFile()) {
                    if (file.getName().startsWith("Journal.")) {
                        System.out.println("File created: " + file.getName());
                        watchedFile = Optional.of(file);
                        fileCreatedProcessor.accept(file);
                    }
                }
            }

            @Override
            public void onModified(FileEvent event) {
                File file = event.getFile();
                if (watchedFile.isPresent() && file.equals(watchedFile.get())) {
                    System.out.println("File modified: " + file.getName());
                    fileModifiedProcessor.accept(file);
                }
            }
        }).watch();
    }

    private void findLatestFile(File folder) {
        watchedFile = Arrays.stream(Objects.requireNonNull(folder.listFiles()))
                .filter(file -> file.getName().startsWith("Journal."))
                .max(Comparator.comparingLong(file -> Long.parseLong(file.getName().substring(8, 20))));

        System.out.println("Registered watched file: " + watchedFile.map(File::getName).orElse("No file"));
    }
}
