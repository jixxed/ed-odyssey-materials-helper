/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.watchdog;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.application.Platform;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.AppConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.GameVersion;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Commander.Commander;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.event.CommanderAllListedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@Slf4j
public class CommanderWatcher {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Getter
    private File watchedFolder;
    private FileWatcher fileWatcher;
    public static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    public CommanderWatcher() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    public void watch(final File folder) {
        try {
            this.watchedFolder = folder;
            if (!folder.exists()) {
                return;
            }
            listCommanders(folder);
            this.fileWatcher = new FileWatcher(true).withListener(new FileAdapter() {
                @Override
                public void onCreated(final FileEvent event) {
                    listCommanders(folder);
                }
            }).watch(folder);
        } catch (Exception ex) {
            log.error("failed to list commanders", ex);
            Platform.runLater(() -> {
                NotificationService.showError(NotificationType.ERROR, LocaleService.LocaleString.of("notification.journal.init.error.title"), LocaleService.LocaleString.of("notification.journal.init.error.text"));
            });
        }
    }

    private void listCommanders(final File folder) {
        try {
            FileService.listFiles(folder, true).stream()
                    .filter(file -> file.getName().startsWith(AppConstants.JOURNAL_FILE_PREFIX))
                    .filter(file -> file.getName().endsWith(AppConstants.JOURNAL_FILE_SUFFIX))
                    .filter(JournalUtils::isNewerThanTwoYears)
                    .filter(JournalUtils::hasFileHeader)
                    .filter(JournalUtils::hasCommanderHeader)
                    .forEach(this::listCommander);
            EventService.publish(new CommanderAllListedEvent());
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
                    Commander commander = objectMapper.readValue(line, Commander.class);
                    APPLICATION_STATE.addCommander(commander.getName(), commander.getFid(), gameVersion, commander.getTimestamp());
                    break;
                }
            }
        } catch (final IOException e) {
            log.error("Error listing commander", e);
        }
    }

    public void stop() {
        if (this.fileWatcher != null) {
            this.fileWatcher.stop();
        }
    }

}
