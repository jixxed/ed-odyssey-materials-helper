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

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.JournalEventType;
import nl.jixxed.eliteodysseymaterials.service.UserPreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.CommanderAllListedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ScrapeState {
    private static final LocalDateTime MIN_DATETIME = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
    private static final Map<JournalEventType, LocalDateTime> TIMESTAMPS = new HashMap<>();
    private static final List<EventListener<?>> EVENT_LISTENERS = new ArrayList<>();

    public static void init() {
        //UserPreferences reloads at prio 0
        //database reloads at prio 1
        EVENT_LISTENERS.add(EventService.addStaticListener(2, CommanderAllListedEvent.class, event -> {
            loadTimestamps();
        }));
    }

    private static void loadTimestamps() {
        TIMESTAMPS.put(JournalEventType.COMMUNITYGOAL, getLatestTimestamp(JournalEventType.COMMUNITYGOAL));
        TIMESTAMPS.put(JournalEventType.LOCATION, getLatestTimestamp(JournalEventType.LOCATION));
        TIMESTAMPS.put(JournalEventType.FSDJUMP, getLatestTimestamp(JournalEventType.FSDJUMP));
        TIMESTAMPS.put(JournalEventType.CARRIERJUMP, getLatestTimestamp(JournalEventType.CARRIERJUMP));
    }

    public static LocalDateTime getLatestTimestamp(JournalEventType eventType) {
        String preference = getPreferenceKey(eventType);
        return UserPreferencesService.getPreference(preference, MIN_DATETIME);
    }

    public static void updateLatestTimestamp(JournalEventType eventType, LocalDateTime newTimestamp) {
        String preference = getPreferenceKey(eventType);
        UserPreferencesService.setPreference(preference, newTimestamp);
        TIMESTAMPS.put(eventType, newTimestamp);
    }

    public static void updateAllLatestTimestamp(LocalDateTime fileDate) {
        TIMESTAMPS.keySet().forEach(eventType -> {
            updateLatestTimestamp(eventType, fileDate);
        });
    }

    public static String getPreferenceKey(JournalEventType eventType) {
        return PreferenceConstants.SCRAPE_LATEST_PREFIX + eventType.name();
    }

    public static LocalDateTime getOldestDate() {
        return TIMESTAMPS.values().stream().min(LocalDateTime::compareTo).orElse(MIN_DATETIME);
    }

    /**
     * Based on the latestTimestamp it is considered whether a file should be scraped.
     * Any newer files are included
     * Older files are excluded except the latest file before latestTimestamp when the latest file was not full scanned
     * <p>
     * latest      latestTimestamp
     * v               v
     * ---------------------------------------------------------------------
     * files:    |     N        |         Y         |          Y           |    Y    |
     * ---------------------------------------------------------------------
     *
     * @param file   the file considered for scraping
     * @param latest latest file date that exists before latestTimestamp
     * @return whether the file should be scraped
     */
    public static boolean hasEntriesToScrape(File file, LocalDateTime latest) {
        LocalDateTime latestTimestamp = getOldestDate();
        try {
            LocalDateTime fileDate = JournalUtils.getFileDate(file);
            //the file date is later than our registered date
            if (fileDate.isAfter(latestTimestamp)) {
                return true;
            }
            try {
                //if the file is older than the latest old file
                if (fileDate.isBefore(latest)) {
                    return false;
                }
                //the latest entry in the file is later than our registered date
                LocalDateTime latestEntryDate = JournalUtils.getLatestEntryDate(file);
                return latestTimestamp.isBefore(latestEntryDate);
            } catch (IllegalArgumentException ex) {
                return false;
            }

        } catch (DateTimeParseException ex) {
            log.error("Failed to parse file, skip", ex);
            return false;
        }
    }

    public static boolean isScrapableEvent(JournalEventType journalEventType) {
        return TIMESTAMPS.containsKey(journalEventType);
    }

}
