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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.io.CountingInputStream;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import javafx.application.Platform;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.AppConstants;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.JournalEventType;
import nl.jixxed.eliteodysseymaterials.enums.LoadingStage;
import nl.jixxed.eliteodysseymaterials.parser.BatchMessageHandler;
import nl.jixxed.eliteodysseymaterials.parser.JournalEventTypes;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Event;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static java.nio.file.Files.newInputStream;

@Slf4j
public class JournalScraper {
    private static final List<String> EVENTS_TO_SCRAPE = List.of("CommunityGoal");
    private static final LocalDateTime MIN_DATETIME = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final ObjectMapper OBJECT_MAPPER2 = new ObjectMapper();
    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private static final List<EventListener<?>> EVENT_LISTENERS = new ArrayList<>();
    private static final String EVENT = "event";
    private static final LocalDateTime LATEST_TIMESTAMP = LocalDateTime.of(1970, 1, 1, 0, 0, 0);

    static {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER2.registerModule(new JavaTimeModule());
        EVENT_LISTENERS.add(EventService.addStaticListener(TerminateApplicationEvent.class, event -> {
            executorService.shutdownNow();
        }));
    }

    public static void scrape() {
        log.debug("Starting scrape");
        EventService.publish(new ScrapeEvent(false));
        executorService.execute(() -> {
            final File watchedFolder = new File(PreferencesService.getPreference(PreferenceConstants.JOURNAL_FOLDER, OsConstants.getDefaultWatchedFolder()));


            Map<File, Long> files = countTotalLines(findFilesToScrape(watchedFolder));//TODO slow, filter files out before latest timestamp
            long totalLines = files.values().stream().mapToLong(Long::longValue).sum();
            log.debug("{} files to scrape with a total of {} lines", files.size(), totalLines);
            AtomicLong position = new AtomicLong(0);
            Semaphore semaphore = new Semaphore(0);
            EventService.publish(new LoadingEvent(LoadingStage.DATABASE));
            Platform.runLater(semaphore::release);
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                files.entrySet().stream()
                        .sorted(Comparator.comparingLong(e -> JournalUtils.getFileTimestamp(e.getKey())))
                        .forEach(entry -> position.set(scrape(entry.getKey(), position.get(), entry.getValue(), totalLines)));
            } finally {
                EventService.publish(new ScrapeEvent(true));
            }
        });
    }

    private static long scrape(final File file, long position, long fileLines, long total) {

        log.debug("Start events processing: " + file.getName());
        LocalDateTime fileDate = JournalUtils.getFileDate(file);
        ScrapeState.updateAllLatestTimestamp(fileDate);
        try (final CountingInputStream is = new CountingInputStream(newInputStream(Paths.get(file.toURI()), StandardOpenOption.READ))) {
            final InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            final BufferedReader lineReader = new BufferedReader(reader);

            // Process all lines.
            String line;
            Semaphore semaphore = new Semaphore((int) Math.max(1, total / 25));
            long count = position;
            Map<JournalEventType, List<String>> messages = new HashMap<>();
            while ((line = lineReader.readLine()) != null) {
                count++;
                try {
                    JsonNode jsonNode = OBJECT_MAPPER.readTree(line);
                    if (jsonNode.get(EVENT) != null) {
                        String eventType = jsonNode.get(EVENT).asText();
                        JournalEventType journalEventType = JournalEventType.forName(eventType);
                        if (ScrapeState.isScrapableEvent(journalEventType)) {
                            final String newLine = removeBugs(jsonNode);
                            if (test(newLine, JournalEventTypes.EVENT_TYPES.get(eventType))) {
                                try {
                                    LocalDateTime timestamp = getTimeStamp(jsonNode);
                                    if (timestamp.isAfter(ScrapeState.getLatestTimestamp(journalEventType)) || timestamp.isEqual(ScrapeState.getLatestTimestamp(journalEventType))) {
                                        ScrapeState.updateLatestTimestamp(journalEventType, timestamp);
                                        messages.computeIfAbsent(journalEventType, _ -> new ArrayList<>()).add(newLine);
                                    }

                                } catch (IllegalArgumentException ex) {
                                    log.error("Error processing event " + eventType, ex);
                                }
                                Platform.runLater(semaphore::release);
                                try {
                                    semaphore.acquire();
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                                EventService.publish(new EventProcessedEvent(count, total));
                            }
                        }
                    }
                } catch (final JsonProcessingException e) {
                    log.error("Read error", e);
                }
            }
            messages.forEach((type, messageList) -> BatchMessageHandler.handleMessages(type, messageList, file));

        } catch (final Exception e) {
            log.error("Error processing journal", e);
        }
        return position + fileLines;
    }

    private static LocalDateTime getTimeStamp(JsonNode journalMessage) {
        final JsonNode timestampNode = journalMessage.get("timestamp");
        if (timestampNode != null) {
            try {
                LocalDateTime parse = LocalDateTime.parse(timestampNode.asText(), DateTimeFormatter.ISO_DATE_TIME);
                //test for acceptable date range
                if (parse.isAfter(LocalDateTime.now().plusDays(1)) || parse.isBefore(LocalDateTime.of(2010, 1, 1, 0, 0, 0))) {
                    throw new IllegalArgumentException("Invalid timestamp: " + timestampNode.asText());
                }
                return parse;
            } catch (DateTimeException ex) {
                throw new IllegalArgumentException(ex);
            }
        }
        throw new IllegalArgumentException("No timestamp found in journal message");
    }

    private static Map<File, Long> countTotalLines(List<File> files) {
        return files.stream()
                .map(file -> Map.entry(file, JournalScraper.countLines(file)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static long countLines(File file) {
        try (var reader = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {
            return reader.lines().count();
        } catch (Exception e) {
            System.err.println("Error reading file " + file.getName() + ": " + e.getMessage());
            return 0L;
        }
    }

    private static List<File> findFilesToScrape(final File folder) {
        LocalDateTime latest = findLatestFileDate(folder);
        return FileService.listFiles(folder, true).stream()
                .filter(file -> file.getName().startsWith(AppConstants.JOURNAL_FILE_PREFIX))
                .filter(file -> file.getName().endsWith(AppConstants.JOURNAL_FILE_SUFFIX))
                .filter(JournalUtils::isSinceOdysseyRelease)
                .filter(JournalUtils::hasFileHeader)
                .filter(JournalUtils::hasCommanderHeader)
                .filter(JournalUtils::isSelectedCommander)
                .filter(file -> ScrapeState.hasEntriesToScrape(file, latest))
                .sorted(Comparator.comparingLong(JournalUtils::getFileTimestamp))
                .toList();
    }

    /**
     * Find the latest file date before the oldest scraped date
     * Defaults to now if there is no file
     * @param folder
     * @return
     */
    private static LocalDateTime findLatestFileDate(File folder) {
        return FileService.listFiles(folder, true).stream()
                .filter(file -> file.getName().startsWith(AppConstants.JOURNAL_FILE_PREFIX))
                .filter(file -> file.getName().endsWith(AppConstants.JOURNAL_FILE_SUFFIX))
                .filter(JournalUtils::isSinceOdysseyRelease)
                .filter(JournalUtils::hasFileHeader)
                .filter(JournalUtils::hasCommanderHeader)
                .filter(JournalUtils::isSelectedCommander)
                .filter(file -> JournalUtils.getFileDate(file).isBefore(ScrapeState.getOldestDate()))//only files before the late
                .max(Comparator.comparingLong(JournalUtils::getFileTimestamp))
                .map(JournalUtils::getFileDate)
                .orElse(LocalDateTime.now());
    }


    public static String removeBugs(JsonNode jsonNode) {
        String event = jsonNode.get(EVENT).asText();
        String[] buggedEvents = {"MissionFailed", "MissionAbandoned", "MissionRedirected"};

        if (event != null && Arrays.asList(buggedEvents).contains(event) && jsonNode.get("") != null) {
            ObjectNode object = (ObjectNode) jsonNode;
            object.set("LocalisedName", jsonNode.get(""));
            object.remove("");
        }
        if ("CarrierNameChange".equals(event) && jsonNode.get("") != null) {
            ObjectNode object = (ObjectNode) jsonNode;
            object.set("CarrierType", jsonNode.get(""));
            object.remove("");
        }
        return jsonNode.toString();
    }

    private static boolean test(String message, Class<? extends Event> messageClass) {
        try {
            return validator.validate(OBJECT_MAPPER2.readValue(message, messageClass)).isEmpty();
        } catch (final Exception e) {
            return false;
        }
    }
}
