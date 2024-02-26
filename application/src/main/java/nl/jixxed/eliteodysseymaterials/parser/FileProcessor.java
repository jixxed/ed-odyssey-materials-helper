package nl.jixxed.eliteodysseymaterials.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            final Map<JournalEventType, String> messages = new EnumMap<>(JournalEventType.class);
            final List<String> alwaysProcessMessages = new ArrayList<>();
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
                final JsonNode jsonNode = OBJECT_MAPPER.readTree(line);
                final List<JournalEventType> alwaysTrackMaterialEventTypes = List.of(
                        JournalEventType.MISSIONCOMPLETED,
                        JournalEventType.MATERIALCOLLECTED,
                        JournalEventType.MATERIALTRADE,
                        JournalEventType.ENGINEERCRAFT,
                        JournalEventType.LOCATION,
                        JournalEventType.FSDJUMP,
                        JournalEventType.CARRIERJUMP,
                        JournalEventType.APPROACHSETTLEMENT,
                        JournalEventType.CODEXENTRY,
                        JournalEventType.DOCKED,
                        JournalEventType.FSSALLBODIESFOUND,
                        JournalEventType.FSSSIGNALDISCOVERED,
                        JournalEventType.FSSBODYSIGNALS,
                        JournalEventType.FSSDISCOVERYSCAN,
                        JournalEventType.NAVBEACONSCAN,
                        JournalEventType.SAASIGNALSFOUND,
                        JournalEventType.SCANBARYCENTRE,
                        JournalEventType.SCAN
                );
                if (jsonNode.get(EVENT) != null) {
                    testAndReport(line, JournalEventTypes.EVENT_TYPES.get(jsonNode.get(EVENT).asText()));
                    final JournalEventType journalEventType = JournalEventType.forName(jsonNode.get(EVENT).asText());
                    if (alwaysTrackMaterialEventTypes.contains(journalEventType)) {
                        alwaysProcessMessages.add(line);
                    } else if (!JournalEventType.UNKNOWN.equals(journalEventType)) {
                        if (JournalEventType.MATERIALS.equals(journalEventType)) {
                            alwaysProcessMessages.removeIf(lineA -> {
                                try {
                                    return alwaysTrackMaterialEventTypes.contains(JournalEventType.forName(OBJECT_MAPPER.readTree(lineA).get(EVENT).asText()));
                                } catch (final JsonProcessingException e) {
                                    e.printStackTrace();
                                }
                                return false;
                            });
                            messages.put(journalEventType, line);
                        } else if (JournalEventType.ENGINEERPROGRESS.equals(journalEventType)) {
                            if (messages.containsKey(JournalEventType.ENGINEERPROGRESS) && jsonNode.has("Engineer")) {
                                alwaysProcessMessages.add(line);//add additional engineerprogress messages to alwaysProcessMessages instead
                            } else if (jsonNode.has("Engineers")) {
                                alwaysProcessMessages.removeIf(line2 -> { //clear any additional engineerprogress messages if we get a full message (again)
                                    try {
                                        return JournalEventType.ENGINEERPROGRESS.equals(OBJECT_MAPPER.readTree(line2).get(EVENT));
                                    } catch (final JsonProcessingException e) {
                                        return false;
                                    }
                                });
                                messages.put(journalEventType, line);//set the full message (again)
                            }
                        } else {
                            messages.put(journalEventType, line);
                        }

                    }
                }
                position = is.getCount();
            }

            final List<String> singleMessages = messages.entrySet().stream()
                    .sorted(Comparator.comparing(entry -> {
                        try {
                            return OBJECT_MAPPER.readTree(entry.getValue()).get("timestamp").asText();
                        } catch (final JsonProcessingException e) {
                            log.error("Failed to read timestamp for event", e);
                        }
                        return "";
                    }))
                    .filter(entry -> (!entry.getKey().equals(JournalEventType.BACKPACKCHANGE) && !entry.getKey().equals(JournalEventType.BACKPACK)) || backpackAfterShiplocker(messages, entry))
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());
            AtomicInteger index = new AtomicInteger(1);
            Integer total = singleMessages.size() + alwaysProcessMessages.size();
            Stream.concat(singleMessages.stream(), alwaysProcessMessages.stream())
                    .sorted(Comparator.comparing(event -> {
                        try {
                            return OBJECT_MAPPER.readTree(event).get("timestamp").asText();
                        } catch (final JsonProcessingException e) {
                            log.error("Failed to read timestamp for event", e);
                        }
                        return "";
                    }))
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

    private static boolean backpackAfterShiplocker(final Map<JournalEventType, String> messages, final Map.Entry<JournalEventType, String> entry) {
        try {
            return ZonedDateTime.parse(OBJECT_MAPPER.readTree(entry.getValue()).get("timestamp").asText()).isAfter(ZonedDateTime.parse(OBJECT_MAPPER.readTree(messages.get(JournalEventType.SHIPLOCKER)).get("timestamp").asText()));
        } catch (final JsonProcessingException e) {
            return false;
        }
    }

    private static void testAndReport(String message, Class<? extends Event> messageClass) {
        try {
            OBJECT_MAPPER2.readValue(message, messageClass);
        } catch (final Exception e) {
            //report
            log.error("unknown journal event", e);
            ReportService.reportJournal(message, e.getMessage());
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
        if (journalEventType.equals(JournalEventType.CAPI_FLEETCARRIER)) {
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


