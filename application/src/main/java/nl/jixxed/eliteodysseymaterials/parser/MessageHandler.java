package nl.jixxed.eliteodysseymaterials.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.JournalEventType;
import nl.jixxed.eliteodysseymaterials.parser.messageprocessor.*;
import nl.jixxed.eliteodysseymaterials.parser.messageprocessor.capi.CapiFleetCarrierMessageProcessor;
import nl.jixxed.eliteodysseymaterials.parser.messageprocessor.capi.CapiMessageProcessor;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Event;
import nl.jixxed.eliteodysseymaterials.service.EDDNService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.JournalLineProcessedEvent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class MessageHandler {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final ObjectMapper OBJECT_MAPPER2 = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER2.registerModule(new JavaTimeModule());
    }

    private static final Map<JournalEventType, MessageProcessor<? extends Event>> messageProcessors = Map.ofEntries(
            Map.entry(JournalEventType.FILEHEADER, new FileHeaderMessageProcessor()),
            Map.entry(JournalEventType.CARRIERJUMP, new CarrierJumpMessageProcessor()),
            Map.entry(JournalEventType.CODEXENTRY, new CodexEntryMessageProcessor()),
            Map.entry(JournalEventType.SHIPYARD, new ShipyardMessageProcessor()),
            Map.entry(JournalEventType.SCAN, new ScanMessageProcessor()),
            Map.entry(JournalEventType.SAASIGNALSFOUND, new SAASignalsFoundMessageProcessor()),
            Map.entry(JournalEventType.MARKET, new MarketMessageProcessor()),
            Map.entry(JournalEventType.FSSALLBODIESFOUND, new FSSAllBodiesFoundMessageProcessor()),
            Map.entry(JournalEventType.FSSSIGNALDISCOVERED, new FSSSignalDiscoveredMessageProcessor()),
            Map.entry(JournalEventType.USSDROP, new USSDropMessageProcessor()),

            Map.entry(JournalEventType.FSSBODYSIGNALS, new FSSBodySignalsMessageProcessor()),
            Map.entry(JournalEventType.FSSDISCOVERYSCAN, new FSSDiscoveryScanMessageProcessor()),
            Map.entry(JournalEventType.NAVBEACONSCAN, new NavBeaconScanMessageProcessor()),
            Map.entry(JournalEventType.SCANBARYCENTRE, new ScanBaryCentreMessageProcessor()),

            Map.entry(JournalEventType.NAVROUTE, new NavRouteMessageProcessor()),
            Map.entry(JournalEventType.NAVROUTECLEAR, new NavRouteClearMessageProcessor()),
            Map.entry(JournalEventType.OUTFITTING, new OutfittingMessageProcessor()),
            Map.entry(JournalEventType.MODULEINFO, new ModuleInfoMessageProcessor()),
            Map.entry(JournalEventType.FCMATERIALS, new FCMaterialsMessageProcessor()),

            Map.entry(JournalEventType.COMMANDER, new CommanderMessageProcessor()),
            Map.entry(JournalEventType.ENGINEERPROGRESS, new EngineerProgressMessageProcessor()),
            Map.entry(JournalEventType.EMBARK, new EmbarkMessageProcessor()),
            Map.entry(JournalEventType.SHIPLOCKER, new ShipLockerMessageProcessor()),
            Map.entry(JournalEventType.BACKPACK, new BackpackMessageProcessor()),
            Map.entry(JournalEventType.BACKPACKCHANGE, new BackpackChangeMessageProcessor()),
            Map.entry(JournalEventType.RESUPPLY, new ResupplyMessageProcessor()),
            Map.entry(JournalEventType.FSDJUMP, new FSDJumpMessageProcessor()),
            Map.entry(JournalEventType.LOADOUT, new LoadoutMessageProcessor()),
            Map.entry(JournalEventType.LOCATION, new LocationMessageProcessor()),
            Map.entry(JournalEventType.TOUCHDOWN, new TouchdownMessageProcessor()),
            Map.entry(JournalEventType.SCIENTIFICRESEARCH, new ScientificResearchMessageProcessor()),

            Map.entry(JournalEventType.UNDOCKED, new UndockedMessageProcessor()),
            Map.entry(JournalEventType.LIFTOFF, new LiftOffMessageProcessor()),
            Map.entry(JournalEventType.APPROACHBODY, new ApproachBodyMessageProcessor()),
            Map.entry(JournalEventType.APPROACHSETTLEMENT, new ApproachSettlementMessageProcessor()),
            Map.entry(JournalEventType.SUPERCRUISEENTRY, new SupercruiseEntryMessageProcessor()),
            Map.entry(JournalEventType.LEAVEBODY, new LeaveBodyMessageProcessor()),
            Map.entry(JournalEventType.DOCKED, new DockedMessageProcessor()),
            Map.entry(JournalEventType.DOCKINGDENIED, new DockingDeniedMessageProcessor()),
            Map.entry(JournalEventType.DOCKINGGRANTED, new DockingGrantedMessageProcessor()),
            Map.entry(JournalEventType.MATERIALS, new MaterialsMessageProcessor()),
            Map.entry(JournalEventType.CARGO, new CargoMessageProcessor()),
            Map.entry(JournalEventType.CARGOTRANSFER, new CargoTransferMessageProcessor()),

            Map.entry(JournalEventType.SUITLOADOUT, new SuitLoadoutMessageProcessor()),
            Map.entry(JournalEventType.SWITCHSUITLOADOUT, new SuitLoadoutMessageProcessor()),

            Map.entry(JournalEventType.MATERIALCOLLECTED, new MaterialCollectedMessageProcessor()),
            Map.entry(JournalEventType.MATERIALTRADE, new MaterialTradeMessageProcessor()),
            Map.entry(JournalEventType.MATERIALDISCARDED, new MaterialDiscardedMessageProcessor()),
            Map.entry(JournalEventType.ENGINEERCONTRIBUTION, new EngineerContributionMessageProcessor()),
            Map.entry(JournalEventType.ENGINEERCRAFT, new EngineerCraftMessageProcessor()),
            Map.entry(JournalEventType.MISSIONCOMPLETED, new MissionCompletedMessageProcessor()),
            Map.entry(JournalEventType.SYNTHESIS, new SynthesisMessageProcessor()),
            Map.entry(JournalEventType.TECHNOLOGYBROKER, new TechnologyBrokerMessageProcessor()),
            Map.entry(JournalEventType.UPGRADESUIT, new UpgradeSuitMessageProcessor()),
            Map.entry(JournalEventType.UPGRADEWEAPON, new UpgradeWeaponMessageProcessor()),
            Map.entry(JournalEventType.RECEIVETEXT, new ReceiveTextMessageProcessor()),
            Map.entry(JournalEventType.SENDTEXT, new SendTextMessageProcessor()),
            Map.entry(JournalEventType.BUYMICRORESOURCES, new BuyMicroResourcesMessageProcessor()),
            Map.entry(JournalEventType.MARKETBUY, new MarketBuyMessageProcessor()),
            Map.entry(JournalEventType.MARKETSELL, new MarketSellMessageProcessor()),
            Map.entry(JournalEventType.SHUTDOWN, new ShutdownMessageProcessor()),
            Map.entry(JournalEventType.STARTJUMP, new StartJumpMessageProcessor()),
            Map.entry(JournalEventType.MUSIC, new MusicMessageProcessor()),


            Map.entry(JournalEventType.LOADGAME, new LoadGameMessageProcessor())
    );
    private static final Map<JournalEventType, CapiMessageProcessor> capiMessageProcessors = Map.ofEntries(
            Map.entry(JournalEventType.CAPIFLEETCARRIER, new CapiFleetCarrierMessageProcessor())
    );
    private static final String EVENT = "event";
    private static final String TIMESTAMP = "timestamp";

    static void handleMessage(final String message, final File file) {
        try {
            final JsonNode jsonNode = OBJECT_MAPPER.readTree(message);
            if (jsonNode.get(EVENT) != null) {
                final String eventName = jsonNode.get(EVENT).asText();
                log.info("event: " + eventName + "(" + jsonNode.get(TIMESTAMP).asText() + ")");
                final JournalEventType journalEventType = JournalEventType.forName(jsonNode.get(EVENT).asText());
                final MessageProcessor<Event> messageProcessor = (MessageProcessor<Event>) messageProcessors.get(journalEventType);
                if (messageProcessor != null) {
                    final Class<? extends Event> messageClass = messageProcessor.getMessageClass();
                    final Event event = OBJECT_MAPPER.readValue(message, messageClass);
                    messageProcessor.process(event);
                    EventService.publish(new JournalLineProcessedEvent(jsonNode.get("timestamp").asText(), journalEventType, file));
                    ApplicationState.getInstance().setWatchedFile(file.getName());
                }
                final LocalDateTime timestamp = LocalDateTime.parse(jsonNode.get(TIMESTAMP).asText(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
                EDDNService.anyEvent(journalEventType, timestamp);

            } else {
                log.warn("EVENT NULL: " + jsonNode.toPrettyString());
            }
        } catch (final JsonProcessingException e) {
            log.error("Error processing json message from: " + file.getName(), e);
        }
    }

    /**
     * Cargo/Shiplocker/Backpack state file
     * @param file
     * @param journalEventType
     */
    static void handleMessage(final File file, final JournalEventType journalEventType) {
        try {
            final String message = Files.readString(file.toPath());
            final JsonNode jsonNode = OBJECT_MAPPER.readTree(message);
            if(jsonNode.has(EVENT) && jsonNode.has(TIMESTAMP)) {
                final String eventName = jsonNode.get(EVENT).asText();
                log.info("event: " + eventName + "("+jsonNode.get(TIMESTAMP).asText()+")");
                final MessageProcessor<Event> messageProcessor = (MessageProcessor<Event>) messageProcessors.get(journalEventType);
                if (messageProcessor != null) {
                    final Class<? extends Event> messageClass = messageProcessor.getMessageClass();
                    final Event event = OBJECT_MAPPER.readValue(message, messageClass);
                    messageProcessor.process(event);
                    EventService.publish(new JournalLineProcessedEvent("now", journalEventType, file));
                }
                final LocalDateTime timestamp = LocalDateTime.parse(jsonNode.get(TIMESTAMP).asText(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
                EDDNService.anyEvent(journalEventType, timestamp);
            }
        } catch (final JsonProcessingException e) {
            log.error("Error processing json message", e);
        } catch (final IOException e) {
            log.error("Error processing Cargo file: " + file.getName(), e);
        }
    }

    static void handleStateFileMessage(final File file) {
        try {
            final String message = Files.readString(file.toPath());
            final JsonNode jsonNode = OBJECT_MAPPER.readTree(message);
            if(jsonNode.has(EVENT) && jsonNode.has(TIMESTAMP)) {
                final String eventName = jsonNode.get(EVENT).asText();
                log.info("event: " + eventName + "("+jsonNode.get(TIMESTAMP).asText()+")");
                final JournalEventType journalEventType = JournalEventType.forName(jsonNode.get(EVENT).asText());
                final MessageProcessor<Event> messageProcessor = (MessageProcessor<Event>) messageProcessors.get(journalEventType);
                if (messageProcessor != null) {
                    final Class<? extends Event> messageClass = messageProcessor.getMessageClass();
                    final Event event = OBJECT_MAPPER.readValue(message, messageClass);
                    messageProcessor.process(event);
                    EventService.publish(new JournalLineProcessedEvent("now", JournalEventType.forName(eventName), file));
                }
                final LocalDateTime timestamp = LocalDateTime.parse(jsonNode.get(TIMESTAMP).asText(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
                EDDNService.anyEvent(journalEventType, timestamp);
            }
        } catch (final JsonProcessingException e) {
            log.error("Error processing json message", e);
        } catch (final IOException e) {
            log.error("Error processing State file: " + file.getName(), e);
        }
    }

    static void handleCapiMessage(final File file, final JournalEventType journalEventType) {
        try {
            final String message = Files.readString(file.toPath());
            final JsonNode jsonNode = OBJECT_MAPPER.readTree(message);
            log.info("event: " + journalEventType);
            final CapiMessageProcessor messageProcessor = capiMessageProcessors.get(journalEventType);
            if (messageProcessor != null) {
                messageProcessor.process(jsonNode);
                EventService.publish(new JournalLineProcessedEvent("now", journalEventType, file));
            }
        } catch (final JsonProcessingException e) {
            log.error("Error processing json message", e);
        } catch (final IOException e) {
            log.error("Error processing CAPI", e);
        }
    }
}
