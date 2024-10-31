package nl.jixxed.eliteodysseymaterials.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.json.JsonReader;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.Commander;
import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.enums.GameVersion;
import nl.jixxed.eliteodysseymaterials.enums.JournalEventType;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.approachsettlement.Approachsettlement;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.approachsettlement.Header;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.carrierjump.Carrierjump;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.commodity.Commodity;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.fcmaterialsjournal.Fcmaterials;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.fsdjump.Fsdjump;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.fssallbodiesfound.Fssallbodiesfound;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.fssbodysignals.Fssbodysignals;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.fssdiscoveryscan.Fssdiscoveryscan;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.fsssignaldiscovered.Fsssignaldiscovered;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.navbeaconscan.Navbeaconscan;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.navroute.Navroute;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.saasignalsfound.Saasignalsfound;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.scanbarycentre.Scanbarycentre;
import nl.jixxed.eliteodysseymaterials.schemas.journal.ApproachSettlement.ApproachSettlement;
import nl.jixxed.eliteodysseymaterials.schemas.journal.CarrierJump.CarrierJump;
import nl.jixxed.eliteodysseymaterials.schemas.journal.CodexEntry.CodexEntry;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Docked.Docked;
import nl.jixxed.eliteodysseymaterials.schemas.journal.DockingDenied.DockingDenied;
import nl.jixxed.eliteodysseymaterials.schemas.journal.DockingGranted.DockingGranted;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Event;
import nl.jixxed.eliteodysseymaterials.schemas.journal.FCMaterials.FCMaterials;
import nl.jixxed.eliteodysseymaterials.schemas.journal.FSDJump.FSDJump;
import nl.jixxed.eliteodysseymaterials.schemas.journal.FSSAllBodiesFound.FSSAllBodiesFound;
import nl.jixxed.eliteodysseymaterials.schemas.journal.FSSBodySignals.FSSBodySignals;
import nl.jixxed.eliteodysseymaterials.schemas.journal.FSSDiscoveryScan.FSSDiscoveryScan;
import nl.jixxed.eliteodysseymaterials.schemas.journal.FSSSignalDiscovered.FSSSignalDiscovered;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Location.Location;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Market.Market;
import nl.jixxed.eliteodysseymaterials.schemas.journal.NavBeaconScan.NavBeaconScan;
import nl.jixxed.eliteodysseymaterials.schemas.journal.NavRoute.NavRoute;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Outfitting.Outfitting;
import nl.jixxed.eliteodysseymaterials.schemas.journal.SAASignalsFound.SAASignalsFound;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Scan.Scan;
import nl.jixxed.eliteodysseymaterials.schemas.journal.ScanBaryCentre.ScanBaryCentre;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Shipyard.Shipyard;
import nl.jixxed.eliteodysseymaterials.service.eddn.*;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import org.apache.commons.io.input.CharSequenceInputStream;
import org.apache.poi.ss.formula.eval.NotImplementedException;
import org.leadpony.justify.api.JsonSchema;
import org.leadpony.justify.api.JsonValidatingException;
import org.leadpony.justify.api.JsonValidationService;
import org.leadpony.justify.api.ProblemHandler;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.stream.Stream;
import java.util.zip.Deflater;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EDDNService {
    private static final Semaphore SEMAPHORE = new Semaphore(1, true);
    private static final JsonValidationService JSON_VALIDATION_SERVICE = JsonValidationService.newInstance();
    private static final ProblemHandler PROBLEM_HANDLER = ProblemHandler.throwing();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final LocalDateTime MIN_DATETIME = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
    private static final List<EventListener<?>> EVENT_LISTENERS = new ArrayList<>();
    private static final ThreadPoolExecutor EXECUTOR_SERVICE = new ThreadPoolExecutor(1, 1,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>());
    public static final String SOFTWARE_NAME = "EDO Materials Helper";

    public static void init() {
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_ABSENT);
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.registerModule(new Jdk8Module().configureAbsentsAsNulls(true));
        JSON_VALIDATION_SERVICE.createProblemPrinter(log::error);
        EVENT_LISTENERS.add(EventService.addStaticListener(JournalInitEvent.class, journalInitEvent -> {
            if (!journalInitEvent.isInitialised()) {
                final LocalDateTime lastTimestamp = UserPreferencesService.getPreference(PreferenceConstants.USER_LATEST_EVENT, MIN_DATETIME);
                UserPreferencesService.setPreference(PreferenceConstants.USER_LATEST_EVENT, lastTimestamp.plusSeconds(1));
            }
        }));
        EVENT_LISTENERS.add(EventService.addStaticListener(TerminateApplicationEvent.class, terminateApplicationEvent -> {
            EXECUTOR_SERVICE.shutdown();
        }));
    }
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final List<FSSSignalDiscovered> fssSignalDiscoveredList = new ArrayList<>();

    public static void approachSettlement(final ApproachSettlement approachSettlement) {
        if (Objects.equals(approachSettlement.getSystemAddress(), LocationService.getCurrentSystemAddress())) {
            APPLICATION_STATE.getPreferredCommander().ifPresent(commander ->
                    approachSettlement.getLatitude().ifPresent(latitude ->
                            approachSettlement.getLongitude().ifPresent(longitude -> {
                                final Expansion expansion = APPLICATION_STATE.getExpansion();
                                send(new Approachsettlement.ApproachsettlementBuilder()
                                        .with$schemaRef("https://eddn.edcd.io/schemas/approachsettlement/1" + (isTestMode() ? "/test" : ""))
                                        .withHeader(buildHeader(commander))
                                        .withMessage(EDDNApproachSettlementMapper.mapToEDDN(approachSettlement, expansion))
                                        .build(), approachSettlement, "approachsettlement-v1.0.json");
                            })));
        }
    }

    public static void location(final Location location) {
        ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
            final Expansion expansion = APPLICATION_STATE.getExpansion();
            send(new nl.jixxed.eliteodysseymaterials.schemas.eddn.location.Location.LocationBuilder()
                    .with$schemaRef("https://eddn.edcd.io/schemas/journal/1" + (isTestMode() ? "/test" : ""))
                    .withHeader(buildHeader(commander))
                    .withMessage(EDDNLocationMapper.mapToEDDN(location, expansion))
                    .build(), location, "journal-v1.0.json");

        });
    }

    public static void codexEntry(final CodexEntry codexEntry) {
        ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
            final Expansion expansion = APPLICATION_STATE.getExpansion();
            send(new nl.jixxed.eliteodysseymaterials.schemas.eddn.codexentry.Codexentry.CodexentryBuilder()
                    .with$schemaRef("https://eddn.edcd.io/schemas/codexentry/1" + (isTestMode() ? "/test" : ""))
                    .withHeader(buildHeader(commander))
                    .withMessage(EDDNCodexEntryMapper.mapToEDDN(codexEntry, expansion))
                    .build(), codexEntry, "codexentry-v1.0.json");

        });
    }

    public static void docked(final Docked docked) {
        ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
            final Expansion expansion = APPLICATION_STATE.getExpansion();
            send(new nl.jixxed.eliteodysseymaterials.schemas.eddn.docked.Docked.DockedBuilder()
                    .with$schemaRef("https://eddn.edcd.io/schemas/journal/1" + (isTestMode() ? "/test" : ""))
                    .withHeader(buildHeader(commander))
                    .withMessage(EDDNDockedMapper.mapToEDDN(docked, expansion))
                    .build(), docked, "journal-v1.0.json");
        });
    }


    public static void carrierjump(final CarrierJump carrierjump) {
        ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
            final Expansion expansion = APPLICATION_STATE.getExpansion();
            send(new Carrierjump.CarrierjumpBuilder()
                    .with$schemaRef("https://eddn.edcd.io/schemas/journal/1" + (isTestMode() ? "/test" : ""))
                    .withHeader(buildHeader(commander))
                    .withMessage(EDDNCarrierJumpMapper.mapToEDDN(carrierjump, expansion))
                    .build(), carrierjump, "journal-v1.0.json");

        });
    }


    public static void commodity(final Market market) {
        ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
            final Expansion expansion = APPLICATION_STATE.getExpansion();
            send(new Commodity.CommodityBuilder()
                    .with$schemaRef("https://eddn.edcd.io/schemas/commodity/3" + (isTestMode() ? "/test" : ""))
                    .withHeader(buildHeader(commander))
                    .withMessage(EDDNCommodityMapper.mapToEDDN(market, expansion))
                    .build(), market, "commodity-v3.0.json", 15);//allow events to be delayed up to 15 seconds because of delayed writes to secondary file
        });
    }
    public static void dockingDenied(final DockingDenied dockingDenied) {
        ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
            final Expansion expansion = APPLICATION_STATE.getExpansion();
            send(new nl.jixxed.eliteodysseymaterials.schemas.eddn.dockingDenied.DockingDenied.DockingDeniedBuilder()
                    .with$schemaRef("https://eddn.edcd.io/schemas/dockingdenied/1" + (isTestMode() ? "/test" : ""))
                    .withHeader(buildHeader(commander))
                    .withMessage(EDDNDockingDeniedMapper.mapToEDDN(dockingDenied, expansion))
                    .build(), dockingDenied, "dockingdenied-v1.0.json");
        });
    }
    public static void dockingGranted(final DockingGranted dockingGranted) {
        ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
            final Expansion expansion = APPLICATION_STATE.getExpansion();
            send(new nl.jixxed.eliteodysseymaterials.schemas.eddn.dockingGranted.DockingGranted.DockingGrantedBuilder()
                    .with$schemaRef("https://eddn.edcd.io/schemas/dockinggranted/1" + (isTestMode() ? "/test" : ""))
                    .withHeader(buildHeader(commander))
                    .withMessage(EDDNDockingGrantedMapper.mapToEDDN(dockingGranted, expansion))
                    .build(), dockingGranted, "dockinggranted-v1.0.json");
        });
    }

    public static void fcmaterialscapi() {

        throw new NotImplementedException("");
    }

    public static void fcmaterialsjournal(final FCMaterials fcmaterialsjournal) {
        ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
            final Expansion expansion = APPLICATION_STATE.getExpansion();
            send(new Fcmaterials.FcmaterialsBuilder()
                    .with$schemaRef("https://eddn.edcd.io/schemas/fcmaterials_journal/1" + (isTestMode() ? "/test" : ""))
                    .withHeader(buildHeader(commander))
                    .withMessage(EDDNFCMaterialsJournalMapper.mapToEDDN(fcmaterialsjournal, expansion))
                    .build(), fcmaterialsjournal, "fcmaterials_journal-v1.0.json",15);//allow events to be delayed up to 15 seconds because of delayed writes to secondary file
        });
    }

    public static void fsdjump(final FSDJump fsdjump) {
        ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
            final Expansion expansion = APPLICATION_STATE.getExpansion();
            send(new Fsdjump.FsdjumpBuilder()
                    .with$schemaRef("https://eddn.edcd.io/schemas/journal/1" + (isTestMode() ? "/test" : ""))
                    .withHeader(buildHeader(commander))
                    .withMessage(EDDNFSDJumpMapper.mapToEDDN(fsdjump, expansion))
                    .build(), fsdjump, "journal-v1.0.json");

        });
    }

    public static void fssallbodiesfound(final FSSAllBodiesFound fssallbodiesfound) {
        ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
            final Expansion expansion = APPLICATION_STATE.getExpansion();
            send(new Fssallbodiesfound.FssallbodiesfoundBuilder()
                    .with$schemaRef("https://eddn.edcd.io/schemas/fssallbodiesfound/1" + (isTestMode() ? "/test" : ""))
                    .withHeader(buildHeader(commander))
                    .withMessage(EDDNFSSAllBodiesFoundMapper.mapToEDDN(fssallbodiesfound, expansion))
                    .build(), fssallbodiesfound, "fssallbodiesfound-v1.0.json");
        });
    }

    public static void fssbodysignals(final FSSBodySignals fssbodysignals) {
        ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
            final Expansion expansion = APPLICATION_STATE.getExpansion();
            send(new Fssbodysignals.FssbodysignalsBuilder()
                    .with$schemaRef("https://eddn.edcd.io/schemas/fssbodysignals/1" + (isTestMode() ? "/test" : ""))
                    .withHeader(buildHeader(commander))
                    .withMessage(EDDNFSSBodySignalsMapper.mapToEDDN(fssbodysignals, expansion))
                    .build(), fssbodysignals, "fssbodysignals-v1.0.json");
        });
    }

    public static void fssdiscoveryscan(final FSSDiscoveryScan fssdiscoveryscan) {
        ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
            final Expansion expansion = APPLICATION_STATE.getExpansion();
            send(new Fssdiscoveryscan.FssdiscoveryscanBuilder()
                    .with$schemaRef("https://eddn.edcd.io/schemas/fssdiscoveryscan/1" + (isTestMode() ? "/test" : ""))
                    .withHeader(buildHeader(commander))
                    .withMessage(EDDNFSSDiscoveryScanMapper.mapToEDDN(fssdiscoveryscan, expansion))
                    .build(), fssdiscoveryscan, "fssdiscoveryscan-v1.0.json");
        });
    }

    private static boolean isFlushTriggerEvent(final String event) {
        //don't flush on the following events
        return Stream.of("FSSSignalDiscovered", "Location", "FSDJump", "CarrierJump")
                .noneMatch(event::equalsIgnoreCase);
    }

    public static void fsssignaldiscovered(final FSSSignalDiscovered fsssignaldiscovered) {
        if (fsssignaldiscovered.getUSSType().isEmpty() || fsssignaldiscovered.getUSSType().stream().noneMatch(ussType -> ussType.equalsIgnoreCase("$USS_Type_MissionTarget;"))) {
            log.info("Buffer FSSSignalDiscovered event");
            fssSignalDiscoveredList.add(fsssignaldiscovered);
        }
    }

    public static void anyEvent(final JournalEventType journalEventType, final LocalDateTime timestamp) {
        if (EDDNService.isFlushTriggerEvent(journalEventType.friendlyName())) {
            if (!fssSignalDiscoveredList.isEmpty() && fssSignalDiscoveredList.stream().anyMatch(fssSignalDiscovered -> fssSignalDiscovered.getSystemAddress().equals(LocationService.getCurrentSystemAddress()))) {
                log.info("Flushing FSSSignalDiscovered events");
                ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
                    final Expansion expansion = APPLICATION_STATE.getExpansion();
                    send(new Fsssignaldiscovered.FsssignaldiscoveredBuilder()
                            .with$schemaRef("https://eddn.edcd.io/schemas/fsssignaldiscovered/1" + (isTestMode() ? "/test" : ""))
                            .withHeader(buildHeader(commander))
                            .withMessage(EDDNFSSSignalDiscoveredMapper.mapToEDDN(fssSignalDiscoveredList, expansion))
                            .build(), new FSSSignalDiscovered.FSSSignalDiscoveredBuilder().withEvent("FSSSignalDiscovered").withTimestamp(timestamp).build(), "fsssignaldiscovered-v1.0.json");
                });
                fssSignalDiscoveredList.clear();
            }
        }
        final LocalDateTime previousTimestamp = UserPreferencesService.getPreference(PreferenceConstants.USER_LATEST_EVENT, MIN_DATETIME);
        if(timestamp.isAfter(previousTimestamp)) {
            UserPreferencesService.setPreference(PreferenceConstants.USER_LATEST_EVENT, timestamp);
        }
    }

    public static void navbeaconscan(final NavBeaconScan navbeaconscan) {
        ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
            final Expansion expansion = APPLICATION_STATE.getExpansion();
            send(new Navbeaconscan.NavbeaconscanBuilder()
                    .with$schemaRef("https://eddn.edcd.io/schemas/navbeaconscan/1" + (isTestMode() ? "/test" : ""))
                    .withHeader(buildHeader(commander))
                    .withMessage(EDDNNavBeaconScanMapper.mapToEDDN(navbeaconscan, expansion))
                    .build(), navbeaconscan, "navbeaconscan-v1.0.json");
        });
    }

    public static void navroute(final NavRoute navroute) {
        if(navroute.getRoute().map(routes -> !routes.isEmpty()).orElse(false)) {
            ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
                final Expansion expansion = APPLICATION_STATE.getExpansion();
                send(new Navroute.NavrouteBuilder()
                        .with$schemaRef("https://eddn.edcd.io/schemas/navroute/1" + (isTestMode() ? "/test" : ""))
                        .withHeader(buildHeader(commander))
                        .withMessage(EDDNNavRouteMapper.mapToEDDN(navroute, expansion))
                        .build(), navroute, "navroute-v1.0.json",15);//allow events to be delayed up to 15 seconds because of delayed writes to secondary file
            });
        }
    }

    public static void outfitting(final Outfitting outfitting) {
        if(outfitting.getItems().map(items -> !items.isEmpty()).orElse(false)) {
            ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
                final Expansion expansion = APPLICATION_STATE.getExpansion();
                send(new nl.jixxed.eliteodysseymaterials.schemas.eddn.outfitting.Outfitting.OutfittingBuilder()
                        .with$schemaRef("https://eddn.edcd.io/schemas/outfitting/2" + (isTestMode() ? "/test" : ""))
                        .withHeader(buildHeader(commander))
                        .withMessage(EDDNOutfittingMapper.mapToEDDN(outfitting, expansion))
                        .build(), outfitting, "outfitting-v2.0.json",15);//allow events to be delayed up to 15 seconds because of delayed writes to secondary file
            });
        }
    }

    public static void saasignalsfound(final SAASignalsFound saasignalsfound) {
        ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
            final Expansion expansion = APPLICATION_STATE.getExpansion();
            send(new Saasignalsfound.SaasignalsfoundBuilder()
                    .with$schemaRef("https://eddn.edcd.io/schemas/journal/1" + (isTestMode() ? "/test" : ""))
                    .withHeader(buildHeader(commander))
                    .withMessage(EDDNSAASignalsFoundMapper.mapToEDDN(saasignalsfound, expansion))
                    .build(), saasignalsfound, "journal-v1.0.json");
        });
    }

    public static void scan(final Scan scan) {
        ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
            final Expansion expansion = APPLICATION_STATE.getExpansion();
            send(new nl.jixxed.eliteodysseymaterials.schemas.eddn.scan.Scan.ScanBuilder()
                    .with$schemaRef("https://eddn.edcd.io/schemas/journal/1" + (isTestMode() ? "/test" : ""))
                    .withHeader(buildHeader(commander))
                    .withMessage(EDDNScanMapper.mapToEDDN(scan, expansion))
                    .build(), scan, "journal-v1.0.json");

        });
    }

    public static void scanbarycentre(final ScanBaryCentre scanbarycentre) {
        ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
            final Expansion expansion = APPLICATION_STATE.getExpansion();
            send(new Scanbarycentre.ScanbarycentreBuilder()
                    .with$schemaRef("https://eddn.edcd.io/schemas/scanbarycentre/1" + (isTestMode() ? "/test" : ""))
                    .withHeader(buildHeader(commander))
                    .withMessage(EDDNScanBaryCentreMapper.mapToEDDN(scanbarycentre, expansion))
                    .build(), scanbarycentre, "scanbarycentre-v1.0.json");
        });
    }

    public static void shipyard(final Shipyard shipyard) {
        if(shipyard.getPriceList().map(prices -> !prices.isEmpty()).orElse(false)) {
            ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
                shipyard.getPriceList().ifPresent(prices -> {
                    if (!prices.isEmpty()) {
                        final Expansion expansion = APPLICATION_STATE.getExpansion();
                        send(new nl.jixxed.eliteodysseymaterials.schemas.eddn.shipyard.Shipyard.ShipyardBuilder()
                                .with$schemaRef("https://eddn.edcd.io/schemas/shipyard/2" + (isTestMode() ? "/test" : ""))
                                .withHeader(buildHeader(commander))
                                .withMessage(EDDNShipyardMapper.mapToEDDN(shipyard, expansion))
                                .build(), shipyard, "shipyard-v2.0.json",15);//allow events to be delayed up to 15 seconds because of delayed writes to secondary file
                    }
                });
            });
        }
    }

    private static Header buildHeader(final Commander commander) {
        return APPLICATION_STATE.getFileheader() != null ? new Header.HeaderBuilder()
                .withUploaderID(commander.getFid())
                .withGamebuild(APPLICATION_STATE.getFileheader().getBuild())
                .withGameversion(APPLICATION_STATE.getFileheader().getGameversion())
                .withSoftwareName(SOFTWARE_NAME)
                .withSoftwareVersion(getBuildVersion())
                .build() : null;
    }

    private static void send(final Object message, final Event event, final String schemaName) {
        send(message, event, schemaName, 0);
    }
    private static void send(final Object message, final Event event, final String schemaName, final int delaySecondsAllowed) {
        final boolean isLive = ApplicationState.getInstance().getGameVersion().equals(GameVersion.LIVE);
        final boolean isNew = isNew(event, delaySecondsAllowed);
        final Boolean isEnabled = PreferencesService.getPreference(PreferenceConstants.EDDN_ENABLED, Boolean.FALSE);
        if (isLive && isNew && isEnabled) {
            final Runnable run = () -> {
                try {
                    final String data = OBJECT_MAPPER.writeValueAsString(message);
                    final JsonSchema schema = JSON_VALIDATION_SERVICE.readSchema(EDDNService.class.getResourceAsStream("/schemavalidation/eddn/" + schemaName));
                    validateAndSend(data, schema, event.getEvent());
                } catch (final InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (final JsonProcessingException e) {
                    log.error("failed to convert to json string", e);
                } catch (final Exception e) {
                    log.error("publish to EDDN error", e);
                }
            };
            try{
                EXECUTOR_SERVICE.submit(run);
                EventService.publish(new EDDNQueueEvent());
            }catch (RejectedExecutionException | NullPointerException ex){
                log.debug("Failed to send data to EDDN", ex);
            }
        }
    }

    private static void validateAndSend(final String data, final JsonSchema schema, String event) throws IOException, InterruptedException {
        try (final JsonReader jsonReader = JSON_VALIDATION_SERVICE.createReader(new CharSequenceInputStream(data, StandardCharsets.UTF_8), schema, PROBLEM_HANDLER)) {
//            log.info("Attempt to send: " + data);
            jsonReader.readValue();
            final HttpClient httpClient = HttpClient.newHttpClient();
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://eddn.edcd.io:4430/upload/"))
                    .header("Content-Type", "application/json")
                    .header("Content-Encoding", "gzip")
                    .POST(HttpRequest.BodyPublishers.ofByteArray(convertJsonToCompressed(data)))
                    .build();
            SEMAPHORE.acquire(); // Acquire the permit before sending the request
            try {
                final HttpResponse<String> send = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                log.info(event + ": " + send.body() + " -> " + data);
            } finally {
                SEMAPHORE.release(); // Release the permit after the request is completed
            }
            // Do something useful here
        } catch (final JsonValidatingException ex) {
            log.error("Schema validation failed. Not sending Data to EDDN.", ex);
            log.error(data);
        }
    }

    private static boolean isNew(final Event event, final int delaySecondsAllowed) {
        final LocalDateTime lastTimestamp = UserPreferencesService.getPreference(PreferenceConstants.USER_LATEST_EVENT, MIN_DATETIME).minusSeconds(delaySecondsAllowed);
        return event.getTimestamp().isAfter(lastTimestamp) || event.getTimestamp().isEqual(lastTimestamp);
    }

    private static byte[] convertJsonToCompressed(final String data) {
        final Deflater def = new Deflater(9, true);
        def.setInput(data.getBytes(StandardCharsets.UTF_8));
        def.finish();
        final byte[] compressedBuffer = new byte[data.length() * 2];
        final int numberOfBytesAfterCompression = def.deflate(compressedBuffer, 0, compressedBuffer.length, Deflater.FULL_FLUSH);
        final byte[] compressedBytes = new byte[numberOfBytesAfterCompression];
        System.arraycopy(compressedBuffer, 0, compressedBytes, 0, numberOfBytesAfterCompression);
        return compressedBytes;
    }

    private static String getBuildVersion() {
        final String version = System.getProperty("app.version");
        return version != null ? version : "dev";
    }

    private static boolean isTestMode(){
        return "dev".equals(getBuildVersion());
    }

    public static int queueSize() {
        return (int) EXECUTOR_SERVICE.getQueue().stream().filter(t -> !((FutureTask) t).isDone()).count();
    }
}
