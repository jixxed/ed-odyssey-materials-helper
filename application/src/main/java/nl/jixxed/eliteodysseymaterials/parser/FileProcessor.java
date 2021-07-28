package nl.jixxed.eliteodysseymaterials.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.enums.EngineerState;
import nl.jixxed.eliteodysseymaterials.enums.JournalEventType;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.service.event.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.Optional;
import java.util.Scanner;

@Slf4j
public class FileProcessor {

    private static int lineNumber = 0;
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final AssetParser ASSET_PARSER = new AssetParser();
    private static final DataParser DATA_PARSER = new DataParser();
    private static final GoodParser GOOD_PARSER = new GoodParser();

    public static synchronized void resetAndProcessJournal(final File file) {
        lineNumber = 0;
        processJournal(file);
    }

    public static synchronized JsonNode processJournal(final File file) {
        JsonNode jsonNode = null;
        try (final Scanner scanner = new Scanner(file, StandardCharsets.UTF_8)) {
            int cursor = 0;
            while (scanner.hasNextLine()) {
                final String line = scanner.nextLine();
                cursor++;
                if (line.isBlank()) {
                    break;
                }
                if (cursor > lineNumber) {
                    lineNumber++;
                    jsonNode = processJournalMessage(line, file);
                }
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return jsonNode;
    }

    public static synchronized JsonNode processShipLockerBackPack(final File file) {
        JsonNode jsonNode = null;
        try {
            final String shipLocker = Files.readString(file.toPath());
            jsonNode = processJournalMessage(shipLocker, file);
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return jsonNode;

    }


    protected static JsonNode processJournalMessage(final String message, final File file) {
        JsonNode jsonNode = null;
        try {
            jsonNode = OBJECT_MAPPER.readTree(message);
            if (jsonNode.get("event") != null) {
                log.info("event: " + jsonNode.get("event").asText());
                final JournalEventType journalEventType = JournalEventType.forName(jsonNode.get("event").asText());
                switch (journalEventType) {
                    case COMMANDER -> processCommander(jsonNode);
                    case ENGINEERPROGRESS -> processEngineerProgressMessage(jsonNode);
                    case EMBARK -> APPLICATION_STATE.resetBackPackCounts();
                    case SHIPLOCKER -> processShipLockerMaterialsMessage(jsonNode, StoragePool.SHIPLOCKER);
                    case BACKPACK -> processShipLockerMaterialsMessage(jsonNode, StoragePool.BACKPACK);
                    case BACKPACKCHANGE, RESUPPLY -> processBackpackChangeMessage(jsonNode);
                    case FSDJUMP, LOCATION -> processLocationMessage(jsonNode);
                    case DOCKED -> processDockedMessage(jsonNode);
                    case TOUCHDOWN -> processTouchdownMessage(jsonNode);
                    case UNDOCKED -> processUndockedMessage();
                    case LIFTOFF -> processLiftOffMessage(jsonNode);
                    case APPROACHBODY -> processApproachBodyMessage(jsonNode);
                    case LEAVEBODY -> processLeaveBodyMessage(jsonNode);
                }
                if (!JournalEventType.UNKNOWN.equals(journalEventType)) {
                    EventService.publish(new JournalProcessedEvent(jsonNode.get("timestamp").asText(), journalEventType, file));
                }
            } else {
                log.warn("EVENT NULL: " + jsonNode.toPrettyString());
            }
        } catch (final JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonNode;
    }

    private static void processLeaveBodyMessage(final JsonNode journalMessage) {
        final String starSystem = journalMessage.get("StarSystem").asText();
        EventService.publish(new SimpleLocationEvent(Optional.of(starSystem), Optional.empty(), Optional.empty(), JournalEventType.LEAVEBODY));
    }

    private static void processApproachBodyMessage(final JsonNode journalMessage) {
        final String starSystem = journalMessage.get("StarSystem").asText();
        final String body = journalMessage.get("Body") != null ? journalMessage.get("Body").asText() : null;
        EventService.publish(new SimpleLocationEvent(Optional.of(starSystem), Optional.ofNullable(body), Optional.empty(), JournalEventType.APPROACHBODY));
    }

    private static void processLiftOffMessage(final JsonNode journalMessage) {
        final String starSystem = journalMessage.get("StarSystem").asText();
        final String body = journalMessage.get("Body") != null ? journalMessage.get("Body").asText() : null;
        EventService.publish(new SimpleLocationEvent(Optional.of(starSystem), Optional.ofNullable(body), Optional.empty(), JournalEventType.LIFTOFF));
    }

    private static void processUndockedMessage() {
        EventService.publish(new SimpleLocationEvent(Optional.empty(), Optional.empty(), Optional.empty(), JournalEventType.UNDOCKED));
    }

    private static void processTouchdownMessage(final JsonNode journalMessage) {
        final String starSystem = journalMessage.get("StarSystem").asText();
        final String body = journalMessage.get("Body") != null ? journalMessage.get("Body").asText() : null;
        final String station = journalMessage.get("NearestDestination") != null ? journalMessage.get("NearestDestination").asText() : null;
        EventService.publish(new SimpleLocationEvent(Optional.of(starSystem), Optional.ofNullable(body), Optional.ofNullable(station), JournalEventType.TOUCHDOWN));
    }

    private static void processDockedMessage(final JsonNode journalMessage) {
        final String starSystem = journalMessage.get("StarSystem").asText();
        final String station = journalMessage.get("StationName") != null ? journalMessage.get("StationName").asText() : null;

        EventService.publish(new SimpleLocationEvent(Optional.of(starSystem), Optional.empty(), Optional.ofNullable(station), JournalEventType.DOCKED));
    }

    private static void processLocationMessage(final JsonNode journalMessage) {
        if (journalMessage.get("StarSystem") != null && journalMessage.get("StarPos") != null) {
            final String system = journalMessage.get("StarSystem").asText();
            final Iterator<JsonNode> starPos = journalMessage.get("StarPos").elements();
            final double x = starPos.next().asDouble();
            final double y = starPos.next().asDouble();
            final double z = starPos.next().asDouble();
            EventService.publish(new LocationEvent(system, x, y, z));
            final String body = journalMessage.get("Body") != null ? journalMessage.get("Body").asText() : null;
            final String station = journalMessage.get("StationName") != null ? journalMessage.get("StationName").asText() : null;
            EventService.publish(new SimpleLocationEvent(Optional.of(system), Optional.ofNullable(body), Optional.ofNullable(station), JournalEventType.LOCATION));
        }
    }

    private static void processCommander(final JsonNode journalMessage) {
        if (journalMessage.get("Name") != null) {
            APPLICATION_STATE.addCommander(journalMessage.get("Name").asText(), journalMessage.get("FID").asText());
        }
    }

    protected static void processEngineerProgressMessage(final JsonNode journalMessage) {
        if (journalMessage.get("Engineers") != null) {
            journalMessage.get("Engineers").elements().forEachRemaining(FileProcessor::processEngineerProgressItem);
        } else if (journalMessage.get("Engineer") != null) {
            processEngineerProgressItem(journalMessage);
        }


    }

    private static void processEngineerProgressItem(final JsonNode item) {
        if (item.get("Engineer") != null && item.get("Progress") != null) {
            final String engineer = item.get("Engineer").asText();
            final EngineerState engineerState = EngineerState.forName(item.get("Progress").asText());
            switch (engineer) {
                case "Domino Green" -> APPLICATION_STATE.setEngineerState(Engineer.DOMINO_GREEN, engineerState);
                case "Hero Ferrari" -> APPLICATION_STATE.setEngineerState(Engineer.HERO_FERRARI, engineerState);
                case "Jude Navarro" -> APPLICATION_STATE.setEngineerState(Engineer.JUDE_NAVARRO, engineerState);
                case "Kit Fowler" -> APPLICATION_STATE.setEngineerState(Engineer.KIT_FOWLER, engineerState);
                case "Oden Geiger" -> APPLICATION_STATE.setEngineerState(Engineer.ODEN_GEIGER, engineerState);
                case "Terra Velasquez" -> APPLICATION_STATE.setEngineerState(Engineer.TERRA_VELASQUEZ, engineerState);
                case "Uma Laszlo" -> APPLICATION_STATE.setEngineerState(Engineer.UMA_LASZLO, engineerState);
                case "Wellington Beck" -> APPLICATION_STATE.setEngineerState(Engineer.WELLINGTON_BECK, engineerState);
                case "Yarden Bond" -> APPLICATION_STATE.setEngineerState(Engineer.YARDEN_BOND, engineerState);
                default -> {
                }
            }
            EventService.publish(new EngineerEvent());
        }
    }

    protected static void processBackpackChangeMessage(final JsonNode journalMessage) {
        EventService.publish(new BackpackEvent(journalMessage.get("timestamp").asText()));
    }

    protected static void processShipLockerMaterialsMessage(final JsonNode journalMessage,
                                                            final StoragePool storagePool) {
        if (journalMessage.get("Items") == null || journalMessage.get("Components") == null || journalMessage.get("Data") == null) {
            switch (storagePool) {
                case SHIPLOCKER -> EventService.publish(new ShipLockerEvent(journalMessage.get("timestamp").asText()));
                case BACKPACK -> EventService.publish(new BackpackEvent(journalMessage.get("timestamp").asText()));
            }
            return;
        }
        switch (storagePool) {
            case SHIPLOCKER -> APPLICATION_STATE.resetShipLockerCounts();
            case BACKPACK -> APPLICATION_STATE.resetBackPackCounts();
        }
        ASSET_PARSER.parse(journalMessage.get("Components").elements(), storagePool, APPLICATION_STATE.getAssets(), null);
        GOOD_PARSER.parse(journalMessage.get("Items").elements(), storagePool, APPLICATION_STATE.getGoods(), APPLICATION_STATE.getUnknownGoods());
        DATA_PARSER.parse(journalMessage.get("Data").elements(), storagePool, APPLICATION_STATE.getData(), APPLICATION_STATE.getUnknownData());
        EventService.publish(new StorageEvent(storagePool));
    }


}
