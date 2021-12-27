package nl.jixxed.eliteodysseymaterials.watchdog;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.AppConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.Commander;
import nl.jixxed.eliteodysseymaterials.service.event.CommanderAllListedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Consumer;

@Slf4j
public class JournalWatcher {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Optional<File> currentlyWatchedFile = Optional.empty();
    private File watchedFolder;
    private FileWatcher fileWatcher;
    public static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    public void watch(final File folder, final Consumer<File> fileModifiedProcessor, final Consumer<File> fileSwitchedProcessor) {
        Platform.runLater(() -> {
            this.watchedFolder = folder;
            listCommanders(folder);
            findLatestFile(folder);
            this.currentlyWatchedFile.ifPresent(fileSwitchedProcessor);
            this.fileWatcher = new FileWatcher("Journal Watcher Thread").withListener(new FileAdapter() {
                @Override
                public void onModified(final FileEvent event) {
                    final File file = event.getFile();
                    final String currentFilePath = getCurrentFilePath();
                    final boolean isSameFile = currentFilePath.equals(file.getAbsolutePath());
                    if (isSameFile || isValidOdysseyJournal(file)) {
                        if (isSameFile) {
                            fileModifiedProcessor.accept(file);
                        } else if (isNewerJournal(file)) {
                            setCurrentlyWatchedFile(file);
                            fileSwitchedProcessor.accept(file);
                            log.info("Switched to journal: " + file.getAbsolutePath());
                        } else {
                            log.info("Rejected journal: " + file.getAbsolutePath());
                        }
                    }
                }

                private boolean isValidOdysseyJournal(final File file) {
                    return file.isFile() && file.getName().startsWith(AppConstants.JOURNAL_FILE_PREFIX) && hasFileHeader(file) && isOdysseyJournal(file) && hasCommanderHeader(file) && isSelectedCommander(file);
                }
            }).watch(folder);
        });
    }

    private synchronized String getCurrentFilePath() {
        return this.currentlyWatchedFile.map(File::getAbsolutePath).orElse("");
    }

    private synchronized void setCurrentlyWatchedFile(final File file) {
        this.currentlyWatchedFile = Optional.of(file);
    }

    private void listCommanders(final File folder) {
        try {
            Arrays.stream(Objects.requireNonNull(folder.listFiles()))
                    .filter(file -> file.getName().startsWith(AppConstants.JOURNAL_FILE_PREFIX))
                    .filter(this::hasFileHeader)
                    .filter(this::isOdysseyJournal)
                    .filter(this::hasCommanderHeader)
                    .forEach(this::listCommander);
            EventService.publish(new CommanderAllListedEvent());
        } catch (final NullPointerException ex) {
            log.error("Failed to list commanders at " + folder.getAbsolutePath(), ex);
        }
    }

    @SuppressWarnings("java:S1192")
    private void listCommander(final File file) {
        try (final Scanner scanner = new Scanner(file, StandardCharsets.UTF_8)) {
            while (scanner.hasNext()) {
                final String line = scanner.nextLine();

                final JsonNode journalMessage = this.objectMapper.readTree(line);
                final JsonNode eventNode = journalMessage.get("event");
                if (eventNode.asText().equals("Commander")) {
                    final JsonNode nameNode = journalMessage.get("Name");
                    final JsonNode fidNode = journalMessage.get("FID");
                    APPLICATION_STATE.addCommander(nameNode.asText(), fidNode.asText());
                    break;
                }
            }
        } catch (final IOException e) {
            log.error("Error listing commander", e);
        }
    }

    private void findLatestFile(final File folder) {
        try {
            this.currentlyWatchedFile = Arrays.stream(Objects.requireNonNull(folder.listFiles()))
                    .filter(file -> file.getName().startsWith(AppConstants.JOURNAL_FILE_PREFIX))
                    .filter(this::hasFileHeader)
                    .filter(this::isOdysseyJournal)
                    .filter(this::isSelectedCommander)
                    .max(Comparator.comparingLong(file -> Long.parseLong(file.getName().substring(8, 20) + file.getName().substring(21, 23))));
            log.info("Registered watched file: " + this.currentlyWatchedFile.map(File::getName).orElse("No file"));
        } catch (final NullPointerException ex) {
            log.error("Failed to Registered watched file at " + folder.getAbsolutePath());
        }
    }


    private synchronized boolean isNewerJournal(final File file) {
        final long fileTimestamp = Long.parseLong(file.getName().substring(8, 20) + file.getName().substring(21, 23));
        final long currentFileTimestamp = this.currentlyWatchedFile.map(currentFile -> Long.parseLong(currentFile.getName().substring(8, 20) + currentFile.getName().substring(21, 23))).orElse(0L);
        return fileTimestamp > currentFileTimestamp;
    }


    private synchronized boolean isSelectedCommander(final File file) {
        final Optional<Commander> preferredCommander = APPLICATION_STATE.getPreferredCommander();
        return preferredCommander.map(commander -> {
            try (final Scanner scanner = new Scanner(file, StandardCharsets.UTF_8)) {
                while (scanner.hasNext()) {
                    final String line = scanner.nextLine();
                    final JsonNode journalMessage = this.objectMapper.readTree(line);
                    final JsonNode eventNode = journalMessage.get("event");
                    if (eventNode.asText().equals("Commander")) {
                        final JsonNode nameNode = journalMessage.get("Name");
                        return nameNode.asText().equals(commander.getName());
                    }
                }
            } catch (final IOException e) {
                log.error("Error checking for selected commander", e);
            }
            return false;
        }).orElse(true);
    }

    private synchronized boolean isOdysseyJournal(final File file) {
        try (final Scanner scanner = new Scanner(file, StandardCharsets.UTF_8)) {
            final String firstLine = scanner.nextLine();

            final JsonNode journalMessage = this.objectMapper.readTree(firstLine);
            final JsonNode isOdysseyNode = journalMessage.get("Odyssey");
            return isOdysseyNode != null && isOdysseyNode.asBoolean(false);

        } catch (final IOException e) {
            log.error("Error checking if journal is odyssey", e);
        }
        return false;
    }

    private synchronized boolean hasCommanderHeader(final File file) {
        try (final Scanner scanner = new Scanner(file, StandardCharsets.UTF_8)) {

            while (scanner.hasNext()) {
                final String line = scanner.nextLine();

                final JsonNode journalMessage = this.objectMapper.readTree(line);
                final JsonNode eventNode = journalMessage.get("event");
                if (eventNode.asText().equals("Commander")) {
                    return true;
                }
            }

        } catch (final IOException e) {
            log.error("Error checking for commander header", e);
        }
        return false;
    }

    private synchronized boolean hasFileHeader(final File file) {
        try (final Scanner scanner = new Scanner(file, StandardCharsets.UTF_8)) {
            return scanner.hasNext();
        } catch (final NoSuchElementException | IOException e) {
            log.error("Error checking for file header", e);
        }
        return false;
    }

    public void stop() {
        this.fileWatcher.stop();
    }

    public File getWatchedFolder() {
        return this.watchedFolder;
    }
}
