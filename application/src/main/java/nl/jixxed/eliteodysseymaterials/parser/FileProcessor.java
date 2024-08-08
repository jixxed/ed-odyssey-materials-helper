package nl.jixxed.eliteodysseymaterials.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.io.CountingInputStream;
import javafx.application.Platform;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ShipConfig;
import nl.jixxed.eliteodysseymaterials.enums.JournalEventType;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Event;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Status.Status;
import nl.jixxed.eliteodysseymaterials.service.LocationService;
import nl.jixxed.eliteodysseymaterials.service.ReportService;
import nl.jixxed.eliteodysseymaterials.service.event.EventProcessedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.JournalInitEvent;
import nl.jixxed.eliteodysseymaterials.service.event.StatusEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileProcessor {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final ObjectMapper OBJECT_MAPPER2 = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER2.registerModule(new JavaTimeModule());
    }

    private static final String EVENT = "event";
    private static long position = 0L;

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

    public static synchronized void resetAndProcessJournal(final File file) {
        position = 0L;
        processJournalFast(file);
    }

    @SuppressWarnings("java:S2674")
    private static synchronized void processJournalFast(final File file) {
        EventService.publish(new JournalInitEvent(false));
        try (final CountingInputStream is = new CountingInputStream(Files.newInputStream(Paths.get(file.toURI()), StandardOpenOption.READ))) {
            final List<String> messages = new ArrayList<>();
            if (file.length() >= position) {
                // Skip over already processed bytes.
                is.skip(position);
            }
            final InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            final BufferedReader lineReader = new BufferedReader(reader);

            // Process all lines.
            String line;
            while ((line = lineReader.readLine()) != null) {
                //try to read line as json, if exception occurs we get JsonProcessingException and can try to read again later
                JsonNode jsonNode = OBJECT_MAPPER.readTree(line);
                if (jsonNode.get(EVENT) != null) {
                    final String newLine = removeBugs(jsonNode);
                    testAndReport(newLine, JournalEventTypes.EVENT_TYPES.get(jsonNode.get(EVENT).asText()));
                    final JournalEventType journalEventType = JournalEventType.forName(jsonNode.get(EVENT).asText());
                    messages.add(newLine);
                }
                position = is.getCount();
            }

            AtomicInteger index = new AtomicInteger(1);
            Integer total = messages.size();
            messages.stream()
                    .sorted(Comparator.comparing(event -> {
                        try {
                            final JsonNode jsonNode = OBJECT_MAPPER.readTree(event);
                            //force fileheader to be first because timestamp is local time vs server time and can be ahead
                            if (jsonNode.get("event").asText().equals(JournalEventType.FILEHEADER.friendlyName())) {
                                return "0";
                            }
                            return jsonNode.get("timestamp").asText();
                        } catch (final JsonProcessingException e) {
                            log.error("Failed to read timestamp for event", e);
                        }
                        return "";
                    }))
                    .filter(event -> {
                        try {
                            return
                                    (!OBJECT_MAPPER.readTree(event).get(EVENT).asText().equalsIgnoreCase(JournalEventType.BACKPACKCHANGE.name()) && !OBJECT_MAPPER.readTree(event).get(EVENT).asText().equalsIgnoreCase(JournalEventType.BACKPACK.name()) || backpackAfterShiplocker(messages, event));
                        } catch (final JsonProcessingException e) {
                            return false;
                        }
                    })
                    .forEach(message -> {
                        Platform.runLater(() -> MessageHandler.handleMessage(message, file));
                        Platform.runLater(() -> EventService.publish(new EventProcessedEvent(index.getAndIncrement(), total)));
                    });

        } catch (final JsonProcessingException e) {
            log.error("Read error", e);
        } catch (final IOException e) {
            log.error("Error processing journal", e);
        }
        Platform.runLater(() -> EventService.publish(new JournalInitEvent(true)));
    }

    public static String removeBugs(JsonNode jsonNode) {
        String event = jsonNode.get(EVENT).asText();
        String[] buggedEvents = {"MissionFailed", "MissionAbandoned", "MissionRedirected"};

        if (event != null && Arrays.asList(buggedEvents).contains(event) && jsonNode.get("") != null) {
            ObjectNode object = (ObjectNode) jsonNode;
            object.set("LocalisedName", jsonNode.get(""));
            object.remove("");
        }
        return jsonNode.toString();
    }

    private static boolean backpackAfterShiplocker(final List<String> messages, final String event) {
        final Optional<String> shiplockerTimestamp = messages.stream().filter(ev -> {
            try {
                return
                        (OBJECT_MAPPER.readTree(event).get(EVENT).asText().equalsIgnoreCase(JournalEventType.SHIPLOCKER.name()));
            } catch (final JsonProcessingException e) {
                return false;
            }
        }).findFirst();
        return shiplockerTimestamp.map(timestamp -> {
            try {
                return ZonedDateTime.parse(OBJECT_MAPPER.readTree(event).get("timestamp").asText()).isAfter(ZonedDateTime.parse(OBJECT_MAPPER.readTree(timestamp).get("timestamp").asText()));
            } catch (final JsonProcessingException e) {
                return false;
            }
        }).orElse(false);
    }

    private static void testAndReport(String message, Class<? extends Event> messageClass) {
        try {
            OBJECT_MAPPER2.readValue(message, messageClass);
        } catch (final Exception e) {
            //report
            log.error("unknown journal event", e);
            ReportService.reportJournal("journal", message, e.getMessage());
        }
    }

    @SuppressWarnings("java:S2674")
    public static synchronized void processJournal(final File file) {
        try (final CountingInputStream is = new CountingInputStream(Files.newInputStream(Paths.get(file.toURI()), StandardOpenOption.READ))) {
            if (file.length() >= position) {
                // Skip over already processed bytes.
                is.skip(position);
            }
            final InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            final BufferedReader lineReader = new BufferedReader(reader);

            // Process all lines.
            String line;
            while ((line = lineReader.readLine()) != null) {
                final String finalLine = line;
                //try to read line as json, if exception occurs we get JsonProcessingException and can try to read again later
                OBJECT_MAPPER.readTree(finalLine);
                position = is.getCount();
                Platform.runLater(() -> MessageHandler.handleMessage(finalLine, file));
            }
        } catch (final JsonProcessingException e) {
            log.error("Read error", e);
        } catch (final IOException e) {
            log.error("Error processing journal", e);
        }
    }

    public static synchronized void processCargoStateFile(final File file, final JournalEventType journalEventType) {
        Platform.runLater(() -> MessageHandler.handleMessage(file, journalEventType));
    }

    public static synchronized void processOtherStateFile(final File file) {
        Platform.runLater(() -> MessageHandler.handleStateFileMessage(file));
    }

    public static synchronized void processCapiFile(final File file, final JournalEventType journalEventType) {
        if (journalEventType.equals(JournalEventType.CAPIFLEETCARRIER)) {
            ApplicationState.getInstance().getFcMaterials().set(true);
        }
        Platform.runLater(() -> MessageHandler.handleCapiMessage(file, journalEventType));
    }

    public static synchronized void processStatusFile(final File file) {
        try {
            final String statusFileContents = Files.readString(file.toPath());
//            log.info(statusFileContents);
            if (!statusFileContents.isBlank()) {//status file can be empty
                final Status status = OBJECT_MAPPER.readValue(statusFileContents, Status.class);
                Platform.runLater(() -> {
                    if (status.getFlags2().isPresent()) {
                        ApplicationState.getInstance().updateWithFlags(status.getFlags().intValue(), status.getFlags2().get().intValue());
                    }
                    Optional<ShipConfig> shipConfiguration = status.getFuel().map(fuel -> {
                        final BigDecimal fuelReservoir = fuel.getFuelReservoir();
                        final BigDecimal fuelMain = fuel.getFuelMain();
                        final BigDecimal cargo = status.getCargo().orElse(BigDecimal.ZERO);
                        final List<BigInteger> pips = (List<BigInteger>) status.getPips().orElse(Collections.EMPTY_LIST);
                        Integer systemPips = !pips.isEmpty() ? pips.get(0).intValue() : 8;
                        Integer enginePips = !pips.isEmpty() ? pips.get(1).intValue() : 8;
                        Integer weaponPips = !pips.isEmpty() ? pips.get(2).intValue() : 8;
                        return new ShipConfig(
                                fuelReservoir,
                                fuelMain,
                                cargo,
                                systemPips,
                                enginePips,
                                weaponPips
                        );
                    });
                    shipConfiguration.ifPresent(shipConfig -> EventService.publish(
                            new StatusEvent(
                                    shipConfig.getFuelReserve(),
                                    shipConfig.getFuelCapacity(),
                                    shipConfig.getCargoCapacity(),
                                    shipConfig.getSystemPips(),
                                    shipConfig.getEnginePips(),
                                    shipConfig.getWeaponPips()
                            )));
                    ApplicationState.getInstance().setShipConfig(shipConfiguration);
                    LocationService.setStatusBodyName(status.getBodyName());
                });
            }
        } catch (final IOException e) {
            log.error("Read error", e);
        }
    }
}


