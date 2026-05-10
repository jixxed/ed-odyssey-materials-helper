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
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.Commander;
import nl.jixxed.eliteodysseymaterials.enums.GameVersion;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class JournalUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Pattern journalPatternTimestamp = Pattern.compile("Journal\\.(\\d+)\\.(\\d{2})\\.log");
    private static final Pattern journalPatternDate = Pattern.compile("Journal\\.(\\d{4})-(\\d{2})-(\\d{2})T(\\d{6})\\.(\\d{2})\\.log");
    private static final LocalDateTime ODYSSEY_RELEASE = LocalDateTime.of(2021, 6, 20, 0, 0);//+1 day

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

    public static synchronized boolean isSelectedCommander(final File file) {
        final Optional<Commander> preferredCommander = ApplicationState.getInstance().getPreferredCommander();
        return preferredCommander.map(commander -> {
            try (final Scanner scanner = new Scanner(file, StandardCharsets.UTF_8)) {
                GameVersion gameVersion = GameVersion.UNKNOWN;

                while (scanner.hasNext()) {
                    final String line = scanner.nextLine();
                    final JsonNode journalMessage = OBJECT_MAPPER.readTree(line);
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

    public static synchronized boolean isNewerThanTwoYears(final File file) {
        final int twoYearsAgo = Integer.parseInt(Year.now().format(DateTimeFormatter.ofPattern("uu"))) - 2;
        final Matcher matcher = journalPatternTimestamp.matcher(file.getName());
        if (matcher.matches()) {
            return Integer.parseInt(matcher.group(1).substring(0, 2)) >= twoYearsAgo;
        }
        final Matcher matcher2 = journalPatternDate.matcher(file.getName());
        if (matcher2.matches()) {
            return Integer.parseInt(matcher2.group(1).substring(2, 4)) >= twoYearsAgo;
        }
        return true;

    }

    public static synchronized boolean isSinceOdysseyRelease(final File file) {
        return getFileDate(file).isAfter(ODYSSEY_RELEASE);
    }

    public static synchronized boolean hasCommanderHeader(final File file) {
        try (final Scanner scanner = new Scanner(file, StandardCharsets.UTF_8)) {

            while (scanner.hasNext()) {
                final String line = scanner.nextLine();

                final JsonNode journalMessage = OBJECT_MAPPER.readTree(line);
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

    public static synchronized boolean hasFileHeader(final File file) {
        try (final Scanner scanner = new Scanner(file, StandardCharsets.UTF_8)) {

            if (scanner.hasNext()) {
                final String line = scanner.nextLine();

                final JsonNode journalMessage = OBJECT_MAPPER.readTree(line);
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

    public static Long getFileTimestamp(final File file) {
        return getFileTimestamp(file, true);
    }

    private static Long getFileTimestamp(final File file, boolean includeSequence) {
        final Matcher matcher = journalPatternTimestamp.matcher(file.getName());
        if (matcher.matches()) {
            return Long.parseLong(matcher.group(1) + ((includeSequence) ? matcher.group(2) : ""));
        }
        final Matcher matcher2 = journalPatternDate.matcher(file.getName());
        if (matcher2.matches()) {
            //group 1 - year - only last 2 digits to match other pattern
            final String date = matcher2.group(1).substring(2) + matcher2.group(2) + matcher2.group(3) + matcher2.group(4) + ((includeSequence) ? matcher2.group(5) : "");
            return Long.parseLong(date);
        }
        return 0L;
    }

    /**
     * gets the timestamp of a journal file normalized to UTC, same as timestamp entries inside the file
     *
     * @param file journal file
     * @return LocalDateTime in UTC
     */
    public static LocalDateTime getFileDate(final File file) {
        Long timestamp = getFileTimestamp(file, false);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyMMddHHmmss");

        LocalDateTime localDateTime = LocalDateTime.parse(String.format("%012d", timestamp), format);// 12 digit format, pad with leading zeros
        ZoneId systemZone = ZoneId.systemDefault();

        // Attach timezone and convert to UTC
        return localDateTime
                .atZone(systemZone)
                .withZoneSameInstant(ZoneOffset.UTC)
                .toLocalDateTime();
    }

    public static LocalDateTime getLatestEntryDate(final File file) {
        //get last line from file
        //convert to jsonnode
        //get timestamp field, format "timestamp": "2026-04-26T20:15:48Z"
        //convert to LocalDateTime and return
        try (final Scanner scanner = new Scanner(file, StandardCharsets.UTF_8)) {
            String lastLine = null;
            while (scanner.hasNext()) {
                lastLine = scanner.nextLine();
            }
            if (lastLine != null) {
                final JsonNode journalMessage = OBJECT_MAPPER.readTree(lastLine);
                final JsonNode timestampNode = journalMessage.get("timestamp");
                if (timestampNode != null) {
                    return LocalDateTime.parse(timestampNode.asText(), DateTimeFormatter.ISO_DATE_TIME);
                }
            }
        } catch (final Exception e) {
            log.error("Error getting latest entry date from file: " + file.getName(), e);
        }
        throw new IllegalArgumentException("Could not get latest entry date from file: " + file.getName());
    }

}
