package nl.jixxed.eliteodysseymaterials.watchdog;

import javafx.application.Platform;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

@Slf4j
public class DeeplinkWatcher {

    private Optional<File> watchedFile = Optional.empty();
    private FileWatcher fileWatcher;

    public void watch(final File folder, final Consumer<String> fileProcessor, final String filename) {
        findLatestFile(folder, filename);
        this.watchedFile.ifPresent(file -> {
            try {
                fileProcessor.accept(Files.readString(file.getAbsoluteFile().toPath()));
            } catch (final IOException e) {
                log.error(e.getMessage(), e);
            }
        });
        this.watchedFile.ifPresent(File::delete);
        this.fileWatcher = new FileWatcher("Deeplink Watcher Thread").withListener(new FileAdapter() {
            @Override
            public void onCreated(final FileEvent event) {
                if (filename.equals(event.getFile().getName())) {
                    log.debug("Created: " + event.getFile().toString());
                }
                handleFile(event, fileProcessor);

            }

            @Override
            public void onModified(final FileEvent event) {
                if (filename.equals(event.getFile().getName())) {
                    log.debug("Modified: " + event.getFile().toString());
                }
                handleFile(event, fileProcessor);

            }

            @Override
            public void onDeleted(final FileEvent event) {
                if (filename.equals(event.getFile().getName())) {
                    log.debug("Deleted: " + event.getFile().toString());
                }
            }

            private void handleFile(final FileEvent event, final Consumer<String> fileProcessor) {
                try {
                    if (event.getFile().isFile() && event.getFile().getName().equals(filename) && event.getFile().exists() && !Files.readString(event.getFile().getAbsoluteFile().toPath()).isEmpty()) {
                        final File file = event.getFile();
                        DeeplinkWatcher.this.watchedFile = Optional.of(file);
                        final File procFile = new File(OsConstants.DEEPLINK + ".proc");
                        final Path procPath = procFile.toPath();
                        Files.move(Path.of(OsConstants.DEEPLINK), procPath, StandardCopyOption.REPLACE_EXISTING);
                        final String content = Files.readString(procFile.getAbsoluteFile().toPath());
                        log.debug(content);
                        Platform.runLater(() -> {
                            fileProcessor.accept(content);
                            try {
                                Files.delete(procFile.getAbsoluteFile().toPath());
                            } catch (final IOException e) {
                                log.error(e.getMessage(), e);
                            }
                        });
                    }
                } catch (final IOException e) {
                    log.error(e.getMessage(), e);
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
