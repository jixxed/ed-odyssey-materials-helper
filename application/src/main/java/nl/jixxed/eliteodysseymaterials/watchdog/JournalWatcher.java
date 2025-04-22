package nl.jixxed.eliteodysseymaterials.watchdog;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.AppConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.Commander;
import nl.jixxed.eliteodysseymaterials.enums.GameVersion;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.event.CommanderAllListedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.JournalInitEvent;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class JournalWatcher {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Optional<File> currentlyWatchedFile = Optional.empty();
    @Getter
    private File watchedFolder;
    private FileWatcher fileWatcher;
    public static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private final Pattern journalPatternTimestamp = Pattern.compile("Journal\\.(\\d+)\\.(\\d{2})\\.log");
    private final Pattern journalPatternDate = Pattern.compile("Journal\\.(\\d{4})-(\\d{2})-(\\d{2})T(\\d{6})\\.(\\d{2})\\.log");

    public void watch(final File folder, final Consumer<File> fileModifiedProcessor, final Consumer<File> fileSwitchedProcessor) {
        Platform.runLater(() -> {
        try {
            this.watchedFolder = folder;
            if (!folder.exists()) {
                EventService.publish(new JournalInitEvent(true));
                return;
            }
            listCommanders(folder);
            findLatestFile(folder);
            if (this.currentlyWatchedFile.isEmpty()) {
                EventService.publish(new JournalInitEvent(true));
                return;
            }
            this.currentlyWatchedFile.ifPresent(fileSwitchedProcessor);
            this.fileWatcher = new FileWatcher(true).withListener(new FileAdapter() {
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
                    return file.isFile()
                            && file.getName().startsWith(AppConstants.JOURNAL_FILE_PREFIX)
                            && file.getName().endsWith(AppConstants.JOURNAL_FILE_SUFFIX)
                            && isNewerThanTwoYears(file)
                            && hasFileHeader(file)
                            && hasCommanderHeader(file)
                            && isSelectedCommander(file);
                }
            }).watch(folder);
        } catch (Exception ex) {
            log.error("failed to initialize journal", ex);
            Platform.runLater(() -> {
                NotificationService.showError(NotificationType.ERROR, LocaleService.LocaleString.of("notification.journal.init.error.title"), LocaleService.LocaleString.of("notification.journal.init.error.text"));
            });
            EventService.publish(new JournalInitEvent(true));
        }
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
            FileService.listFiles(folder, true).stream()
                    .filter(file -> file.getName().startsWith(AppConstants.JOURNAL_FILE_PREFIX))
                    .filter(file -> file.getName().endsWith(AppConstants.JOURNAL_FILE_SUFFIX))
                    .filter(this::isNewerThanTwoYears)
                    .filter(this::hasFileHeader)
                    .filter(this::hasCommanderHeader)
                    .forEach(this::listCommander);
            EventService.publish(new CommanderAllListedEvent());
//            Platform.runLater(() -> EventService.publish(new CommanderAllListedEvent()));
        } catch (final NullPointerException ex) {
            log.error("Failed to list commanders at " + folder.getAbsolutePath(), ex);
        }
    }

    @SuppressWarnings("java:S1192")
    private void listCommander(final File file) {
        try (final Scanner scanner = new Scanner(file, StandardCharsets.UTF_8)) {
            GameVersion gameVersion = GameVersion.UNKNOWN;
            while (scanner.hasNext()) {
                final String line = scanner.nextLine();
                final JsonNode journalMessage = this.objectMapper.readTree(line);
                final JsonNode eventNode = journalMessage.get("event");

                if (eventNode.asText().equalsIgnoreCase("Fileheader")) {
                    final String gameversion = journalMessage.get("gameversion").asText("");
                    if (gameversion.startsWith("3")) {
                        gameVersion = GameVersion.LEGACY;
                    } else if (gameversion.startsWith("4")) {
                        gameVersion = GameVersion.LIVE;
                    }
                } else if (eventNode.asText().equals("Commander") && !gameVersion.equals(GameVersion.UNKNOWN)) {
                    final JsonNode nameNode = journalMessage.get("Name");
                    final JsonNode fidNode = journalMessage.get("FID");
                    APPLICATION_STATE.addCommander(nameNode.asText(), fidNode.asText(), gameVersion);
                    break;
                }
            }
        } catch (final IOException e) {
            log.error("Error listing commander", e);
        }
    }

    private void findLatestFile(final File folder) {
        try {
            this.currentlyWatchedFile = FileService.listFiles(folder, true).stream()
                    .filter(file -> file.getName().startsWith(AppConstants.JOURNAL_FILE_PREFIX))
                    .filter(file -> file.getName().endsWith(AppConstants.JOURNAL_FILE_SUFFIX))
                    .filter(this::isNewerThanTwoYears)
                    .filter(this::hasFileHeader)
                    .filter(this::hasCommanderHeader)
                    .filter(this::isSelectedCommander)
                    .max(Comparator.comparingLong(this::getFileTimestamp));
            log.info("Registered watched file: " + this.currentlyWatchedFile.map(File::getName).orElse("No file"));
        } catch (final NullPointerException ex) {
            log.error("Failed to Registered watched file at " + folder.getAbsolutePath(), ex);
        }
    }


    private synchronized boolean isNewerJournal(final File file) {
        final long fileTimestamp = getFileTimestamp(file);
        final long currentFileTimestamp = this.currentlyWatchedFile.map(this::getFileTimestamp).orElse(0L);
        return fileTimestamp > currentFileTimestamp;
    }


    Long getFileTimestamp(final File file) {
        final Matcher matcher = this.journalPatternTimestamp.matcher(file.getName());
        if (matcher.matches()) {
            return Long.parseLong(matcher.group(1) + matcher.group(2));
        }
        final Matcher matcher2 = this.journalPatternDate.matcher(file.getName());
        if (matcher2.matches()) {
            //group 1 - year - only last 2 digits to match other pattern
            final String date = matcher2.group(1).substring(2) + matcher2.group(2) + matcher2.group(3) + matcher2.group(4) + matcher2.group(5);
            return Long.parseLong(date);
        }
        return 0L;
    }


    private synchronized boolean isSelectedCommander(final File file) {
        final Optional<Commander> preferredCommander = APPLICATION_STATE.getPreferredCommander();
        return preferredCommander.map(commander -> {
            try (final Scanner scanner = new Scanner(file, StandardCharsets.UTF_8)) {
                GameVersion gameVersion = GameVersion.UNKNOWN;

                while (scanner.hasNext()) {
                    final String line = scanner.nextLine();
                    final JsonNode journalMessage = this.objectMapper.readTree(line);
                    final JsonNode eventNode = journalMessage.get("event");
                    if (eventNode.asText().equalsIgnoreCase("Fileheader")) {
                        final String gameversion = journalMessage.get("gameversion").asText("");
                        if (gameversion.startsWith("3")) {
                            gameVersion = GameVersion.LEGACY;
                        } else if (gameversion.startsWith("4")) {
                            gameVersion = GameVersion.LIVE;
                        }
                    } else if (eventNode.asText().equals("Commander")) {
                        final JsonNode nameNode = journalMessage.get("Name");
                        final JsonNode fidNode = journalMessage.get("FID");
                        return gameVersion.equals(commander.getGameVersion())
                                && nameNode.asText().equals(commander.getName())
                                && fidNode.asText().equals(commander.getFid());
                    }
                }
            } catch (final IOException e) {
                log.error("Error checking for selected commander", e);
            }
            return false;
        }).orElse(true);
    }

    synchronized boolean isNewerThanTwoYears(final File file) {
        final int twoYearsAgo = Integer.parseInt(Year.now().format(DateTimeFormatter.ofPattern("uu"))) - 2;
        final Matcher matcher = this.journalPatternTimestamp.matcher(file.getName());
        if (matcher.matches()) {
            return Integer.parseInt(matcher.group(1).substring(0, 2)) >= twoYearsAgo;
        }
        final Matcher matcher2 = this.journalPatternDate.matcher(file.getName());
        if (matcher2.matches()) {
            return Integer.parseInt(matcher2.group(1).substring(2, 4)) >= twoYearsAgo;
        }
        return true;

    }

    private synchronized boolean hasCommanderHeader(final File file) {
        try (final Scanner scanner = new Scanner(file, StandardCharsets.UTF_8)) {

            while (scanner.hasNext()) {
                final String line = scanner.nextLine();

                final JsonNode journalMessage = this.objectMapper.readTree(line);
                final JsonNode eventNode = journalMessage.get("event");
                if (eventNode != null && eventNode.asText().equals("Commander")) {
                    return true;
                }
            }

        } catch (final Exception e) {
            log.error("Error checking for commander header in file: " + file.getName(), e);
        }
        return false;
    }

    private synchronized boolean hasFileHeader(final File file) {
        try (final Scanner scanner = new Scanner(file, StandardCharsets.UTF_8)) {

            if (scanner.hasNext()) {
                final String line = scanner.nextLine();

                final JsonNode journalMessage = this.objectMapper.readTree(line);
                final JsonNode eventNode = journalMessage.get("event");
                if (eventNode != null && eventNode.asText().equals("Fileheader")) {
                    return true;
                }
            }

        } catch (final Exception e) {
            log.error("Error checking for file header in file: " + file.getName(), e);
        }
        return false;
    }

    public void stop() {
        if (this.fileWatcher != null) {
            this.fileWatcher.stop();
        }
    }

}
