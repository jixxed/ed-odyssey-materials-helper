/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.JournalEventType;
import nl.jixxed.eliteodysseymaterials.parser.messageprocessor.*;
import nl.jixxed.eliteodysseymaterials.parser.messageprocessor.capi.CapiArxMessageProcessor;
import nl.jixxed.eliteodysseymaterials.parser.messageprocessor.capi.CapiFleetCarrierMessageProcessor;
import nl.jixxed.eliteodysseymaterials.parser.messageprocessor.capi.CapiMessageProcessor;
import nl.jixxed.eliteodysseymaterials.parser.messageprocessor.capi.CapiSquadronMessageProcessor;
import nl.jixxed.eliteodysseymaterials.parser.messageprocessor.journal.*;
import nl.jixxed.eliteodysseymaterials.schemas.capi.fleetcarrier.CapiFleetcarrier;
import nl.jixxed.eliteodysseymaterials.schemas.capi.squadron.CapiSquadron;
import nl.jixxed.eliteodysseymaterials.schemas.capi.squadron.ShipItem;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Event;
import nl.jixxed.eliteodysseymaterials.service.EDDNService;
import nl.jixxed.eliteodysseymaterials.service.ReportService;
import nl.jixxed.eliteodysseymaterials.service.ShipsDeserializer;
import nl.jixxed.eliteodysseymaterials.service.VersionService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.JournalLineProcessedEvent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class MessageHandler {
    private static final Validator validator =  Validation.buildDefaultValidatorFactory().getValidator();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final ObjectMapper OBJECT_MAPPER_CAPI = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER_CAPI.registerModule(new JavaTimeModule());
        OBJECT_MAPPER_CAPI.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
        SimpleModule shipsModule = new SimpleModule();
        shipsModule.setDeserializerModifier(new BeanDeserializerModifier() {
            @Override
            public JsonDeserializer<?> modifyMapDeserializer(
                    DeserializationConfig config,
                    MapType type,
                    BeanDescription beanDesc,
                    JsonDeserializer<?> deserializer
            ) {
                if (type.getKeyType().getRawClass() == String.class
                        && type.getContentType().getRawClass() == ShipItem[].class) {
                    return new ShipsDeserializer();
                }
                return deserializer;
            }
        });
        OBJECT_MAPPER_CAPI.registerModule(shipsModule);

    }

    private static final Map<JournalEventType, SingleMessageProcessor<? extends Event>> messageProcessors = Map.ofEntries(
            Map.entry(JournalEventType.FILEHEADER, new FileHeaderSingleMessageProcessor()),
            Map.entry(JournalEventType.CARRIERJUMP, new CarrierJumpSingleMessageProcessor()),
            Map.entry(JournalEventType.CODEXENTRY, new CodexEntrySingleMessageProcessor()),
            Map.entry(JournalEventType.SHIPYARD, new ShipyardSingleMessageProcessor()),
            Map.entry(JournalEventType.SCAN, new ScanSingleMessageProcessor()),
            Map.entry(JournalEventType.SAASIGNALSFOUND, new SAASignalsFoundSingleMessageProcessor()),
            Map.entry(JournalEventType.MARKET, new MarketSingleMessageProcessor()),
            Map.entry(JournalEventType.FSSALLBODIESFOUND, new FSSAllBodiesFoundSingleMessageProcessor()),
            Map.entry(JournalEventType.FSSSIGNALDISCOVERED, new FSSSignalDiscoveredSingleMessageProcessor()),
            Map.entry(JournalEventType.USSDROP, new USSDropSingleMessageProcessor()),

            Map.entry(JournalEventType.FSSBODYSIGNALS, new FSSBodySignalsSingleMessageProcessor()),
            Map.entry(JournalEventType.FSSDISCOVERYSCAN, new FSSDiscoveryScanSingleMessageProcessor()),
            Map.entry(JournalEventType.NAVBEACONSCAN, new NavBeaconScanSingleMessageProcessor()),
            Map.entry(JournalEventType.SCANBARYCENTRE, new ScanBaryCentreSingleMessageProcessor()),

            Map.entry(JournalEventType.NAVROUTE, new NavRouteSingleMessageProcessor()),
            Map.entry(JournalEventType.NAVROUTECLEAR, new NavRouteClearSingleMessageProcessor()),
            Map.entry(JournalEventType.OUTFITTING, new OutfittingSingleMessageProcessor()),
            Map.entry(JournalEventType.MODULEINFO, new ModuleInfoSingleMessageProcessor()),
            Map.entry(JournalEventType.FCMATERIALS, new FCMaterialsSingleMessageProcessor()),

//            Map.entry(JournalEventType.COMMANDER, new CommanderMessageProcessor()),
            Map.entry(JournalEventType.ENGINEERPROGRESS, new EngineerProgressSingleMessageProcessor()),
            Map.entry(JournalEventType.EMBARK, new EmbarkSingleMessageProcessor()),
            Map.entry(JournalEventType.SHIPLOCKER, new ShipLockerSingleMessageProcessor()),
            Map.entry(JournalEventType.BACKPACK, new BackpackSingleMessageProcessor()),
            Map.entry(JournalEventType.BACKPACKCHANGE, new BackpackChangeSingleMessageProcessor()),
            Map.entry(JournalEventType.RESUPPLY, new ResupplySingleMessageProcessor()),
            Map.entry(JournalEventType.FSDJUMP, new FSDJumpSingleMessageProcessor()),
            Map.entry(JournalEventType.LOADOUT, new LoadoutSingleMessageProcessor()),
            Map.entry(JournalEventType.LOCATION, new LocationSingleMessageProcessor()),
            Map.entry(JournalEventType.TOUCHDOWN, new TouchdownSingleMessageProcessor()),
            Map.entry(JournalEventType.SCIENTIFICRESEARCH, new ScientificResearchSingleMessageProcessor()),

            Map.entry(JournalEventType.UNDOCKED, new UndockedSingleMessageProcessor()),
            Map.entry(JournalEventType.LIFTOFF, new LiftOffSingleMessageProcessor()),
            Map.entry(JournalEventType.APPROACHBODY, new ApproachBodySingleMessageProcessor()),
            Map.entry(JournalEventType.APPROACHSETTLEMENT, new ApproachSettlementSingleMessageProcessor()),
            Map.entry(JournalEventType.SUPERCRUISEENTRY, new SupercruiseEntrySingleMessageProcessor()),
            Map.entry(JournalEventType.LEAVEBODY, new LeaveBodySingleMessageProcessor()),
            Map.entry(JournalEventType.DOCKED, new DockedSingleMessageProcessor()),
            Map.entry(JournalEventType.DOCKINGDENIED, new DockingDeniedSingleMessageProcessor()),
            Map.entry(JournalEventType.DOCKINGGRANTED, new DockingGrantedSingleMessageProcessor()),
            Map.entry(JournalEventType.MATERIALS, new MaterialsSingleMessageProcessor()),
            Map.entry(JournalEventType.CARGO, new CargoSingleMessageProcessor()),
            Map.entry(JournalEventType.CARGOTRANSFER, new CargoTransferSingleMessageProcessor()),
            Map.entry(JournalEventType.EJECTCARGO, new EjectCargoSingleMessageProcessor()),

            Map.entry(JournalEventType.SUITLOADOUT, new SuitLoadoutSingleMessageProcessor()),
            Map.entry(JournalEventType.SWITCHSUITLOADOUT, new SuitLoadoutSingleMessageProcessor()),

            Map.entry(JournalEventType.MATERIALCOLLECTED, new MaterialCollectedSingleMessageProcessor()),
            Map.entry(JournalEventType.MATERIALTRADE, new MaterialTradeSingleMessageProcessor()),
            Map.entry(JournalEventType.MATERIALDISCARDED, new MaterialDiscardedSingleMessageProcessor()),
            Map.entry(JournalEventType.ENGINEERCONTRIBUTION, new EngineerContributionSingleMessageProcessor()),
            Map.entry(JournalEventType.ENGINEERCRAFT, new EngineerCraftSingleMessageProcessor()),
            Map.entry(JournalEventType.MISSIONCOMPLETED, new MissionCompletedSingleMessageProcessor()),
            Map.entry(JournalEventType.SYNTHESIS, new SynthesisSingleMessageProcessor()),
            Map.entry(JournalEventType.TECHNOLOGYBROKER, new TechnologyBrokerSingleMessageProcessor()),
            Map.entry(JournalEventType.UPGRADESUIT, new UpgradeSuitSingleMessageProcessor()),
            Map.entry(JournalEventType.UPGRADEWEAPON, new UpgradeWeaponSingleMessageProcessor()),
            Map.entry(JournalEventType.RECEIVETEXT, new ReceiveTextSingleMessageProcessor()),
            Map.entry(JournalEventType.SENDTEXT, new SendTextSingleMessageProcessor()),
            Map.entry(JournalEventType.BUYMICRORESOURCES, new BuyMicroResourcesSingleMessageProcessor()),
            Map.entry(JournalEventType.MARKETBUY, new MarketBuySingleMessageProcessor()),
            Map.entry(JournalEventType.MARKETSELL, new MarketSellSingleMessageProcessor()),
            Map.entry(JournalEventType.SHUTDOWN, new ShutdownSingleMessageProcessor()),
            Map.entry(JournalEventType.STARTJUMP, new StartJumpSingleMessageProcessor()),
            Map.entry(JournalEventType.POWERPLAY, new PowerplaySingleMessageProcessor()),
            Map.entry(JournalEventType.POWERPLAYLEAVE, new PowerplayLeaveSingleMessageProcessor()),
            Map.entry(JournalEventType.POWERPLAYRANK, new PowerplayRankSingleMessageProcessor()),
            Map.entry(JournalEventType.POWERPLAYMERITS, new PowerplayMeritsSingleMessageProcessor()),
            Map.entry(JournalEventType.MUSIC, new MusicSingleMessageProcessor()),
            Map.entry(JournalEventType.DELIVERPOWERMICRORESOURCES, new DeliverPowerMicroResourcesSingleMessageProcessor()),
            Map.entry(JournalEventType.COLONISATIONCONTRIBUTION, new ColonisationContributionSingleMessageProcessor()),
            Map.entry(JournalEventType.COLONISATIONCONSTRUCTIONDEPOT, new ColonisationConstructionDepotSingleMessageProcessor()),
            Map.entry(JournalEventType.SUPERCRUISEDESTINATIONDROP, new SupercruiseDestinationDropSingleMessageProcessor()),
            Map.entry(JournalEventType.SUPERCRUISEEXIT, new SupercruiseExitSingleMessageProcessor()),
            Map.entry(JournalEventType.PROSPECTEDASTEROID, new ProspectedAsteroidSingleMessageProcessor()),
            Map.entry(JournalEventType.CARRIERLOCATION, new CarrierLocationSingleMessageProcessor()),
            Map.entry(JournalEventType.CARRIERSTATS, new CarrierStatsSingleMessageProcessor()),
            Map.entry(JournalEventType.CARRIERTRADEORDER, new CarrierTradeOrderSingleMessageProcessor()),
            Map.entry(JournalEventType.MODULEBUY, new ModuleBuySingleMessageProcessor()),
            Map.entry(JournalEventType.MODULERETRIEVE, new ModuleRetrieveSingleMessageProcessor()),
            Map.entry(JournalEventType.MODULESELL, new ModuleSellSingleMessageProcessor()),
            Map.entry(JournalEventType.MODULESTORE, new ModuleStoreSingleMessageProcessor()),
            Map.entry(JournalEventType.MASSMODULESTORE, new MassModuleStoreSingleMessageProcessor()),

            Map.entry(JournalEventType.MODULESWAP, new ModuleSwapSingleMessageProcessor()),
            Map.entry(JournalEventType.REPUTATION, new ReputationSingleMessageProcessor()),
            Map.entry(JournalEventType.RANK, new RankSingleMessageProcessor()),
            Map.entry(JournalEventType.PROGRESS, new ProgressSingleMessageProcessor()),
            Map.entry(JournalEventType.COMMUNITYGOAL, new CommunityGoalSingleMessageProcessor()),

            Map.entry(JournalEventType.LOADGAME, new LoadGameSingleMessageProcessor())
    );
    private static final Map<JournalEventType, CapiMessageProcessor<?>> capiMessageProcessors = Map.ofEntries(
            Map.entry(JournalEventType.CAPIFLEETCARRIER, new CapiFleetCarrierMessageProcessor()),
            Map.entry(JournalEventType.CAPISQUADRON, new CapiSquadronMessageProcessor()),
            Map.entry(JournalEventType.CAPIARX, new CapiArxMessageProcessor())
    );
    private static final String EVENT = "event";
    private static final String TIMESTAMP = "timestamp";

    static synchronized void handleMessage(final String message, final File file) {
        try {
            final JsonNode jsonNode = OBJECT_MAPPER.readTree(message);
            if (jsonNode.get(EVENT) != null) {
                final String eventName = jsonNode.get(EVENT).asText();
                log.info("event: " + eventName + "(" + jsonNode.get(TIMESTAMP).asText() + ")");
                final JournalEventType journalEventType = JournalEventType.forName(jsonNode.get(EVENT).asText());
                final SingleMessageProcessor<Event> singleMessageProcessor = (SingleMessageProcessor<Event>) messageProcessors.get(journalEventType);
                if (singleMessageProcessor != null) {
                    final Class<? extends Event> messageClass = singleMessageProcessor.getMessageClass();
                    final Event event = OBJECT_MAPPER.readValue(message, messageClass);
                    singleMessageProcessor.process(event);
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
     *
     * @param file
     * @param journalEventType
     */
    static synchronized void handleMessage(final File file, final JournalEventType journalEventType) {
        try {
            final String message = Files.readString(file.toPath());
            final JsonNode jsonNode = OBJECT_MAPPER.readTree(message);
            if (jsonNode.has(EVENT) && jsonNode.has(TIMESTAMP)) {
                final String eventName = jsonNode.get(EVENT).asText();
                log.info("event: " + eventName + "(" + jsonNode.get(TIMESTAMP).asText() + ")");
                final SingleMessageProcessor<Event> singleMessageProcessor = (SingleMessageProcessor<Event>) messageProcessors.get(journalEventType);
                if (singleMessageProcessor != null) {
                    final Class<? extends Event> messageClass = singleMessageProcessor.getMessageClass();
                    final Event event = OBJECT_MAPPER.readValue(message, messageClass);
                    singleMessageProcessor.process(event);
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
            if (jsonNode.has(EVENT) && jsonNode.has(TIMESTAMP)) {
                final String eventName = jsonNode.get(EVENT).asText();
                log.info("event: " + eventName + "(" + jsonNode.get(TIMESTAMP).asText() + ")");
                final JournalEventType journalEventType = JournalEventType.forName(jsonNode.get(EVENT).asText());
                final SingleMessageProcessor<Event> singleMessageProcessor = (SingleMessageProcessor<Event>) messageProcessors.get(journalEventType);
                if (singleMessageProcessor != null) {
                    final Class<? extends Event> messageClass = singleMessageProcessor.getMessageClass();
                    final Event event = OBJECT_MAPPER.readValue(message, messageClass);
                    singleMessageProcessor.process(event);
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
            if(testAndReportCapi(message, journalEventType) || true){
                log.info("event: " + journalEventType);
                final CapiMessageProcessor messageProcessor = capiMessageProcessors.get(journalEventType);
                if (messageProcessor != null) {
                    var event = OBJECT_MAPPER_CAPI.readValue(message, messageProcessor.getMessageClass());
                    messageProcessor.process(event);
                    EventService.publish(new JournalLineProcessedEvent("now", journalEventType, file));
                }
            }
        } catch (final JsonProcessingException e) {
            log.error("Error processing json message", e);
        } catch (final IOException e) {
            log.error("Error processing CAPI", e);
        }
    }

    private static boolean testAndReportCapi(String message, final JournalEventType journalEventType) {
        try {
            if (message.isEmpty()) {
                return false;
            }
            if (JournalEventType.CAPIFLEETCARRIER.equals(journalEventType)) {
                validate(OBJECT_MAPPER_CAPI.readValue(message, CapiFleetcarrier.class));
            } else if (JournalEventType.CAPISQUADRON.equals(journalEventType)) {
                validate(OBJECT_MAPPER_CAPI.readValue(message, CapiSquadron.class));
            }
            return true;
        } catch (final Exception e) {
            //report
            log.error("Error processing CAPI message", e);
            if (!e.getMessage().startsWith("Unexpected end-of-input")) {
                String channel = JournalEventType.CAPIFLEETCARRIER.equals(journalEventType) ? "capi" : "squadron";
                ReportService.reportJournal(channel, message, "App version: " + VersionService.getBuildVersion() + ". Unknown " + channel + " event: " + e.getMessage());
            }
        }
        return false;
    }


    private static <T> void validate(T object) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException(
                    violations.stream()
                            .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                            .collect(Collectors.joining(", "))
            );
        }
    }

    static void clearCapi(final JournalEventType journalEventType) {
        final CapiMessageProcessor messageProcessor = capiMessageProcessors.get(journalEventType);
        if (messageProcessor != null) {
            messageProcessor.clear();
        }
    }
}
