package nl.jixxed.eliteodysseymaterials.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.Commander;
import nl.jixxed.eliteodysseymaterials.domain.Terminal;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.helper.DnsHelper;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.service.message.DataTrackingItem;
import nl.jixxed.eliteodysseymaterials.service.message.DataTrackingMessage;
import nl.jixxed.eliteodysseymaterials.service.message.MaterialTrackingItem;
import nl.jixxed.eliteodysseymaterials.service.message.MaterialTrackingMessage;

import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MaterialTrackingService {
    private static UUID session = UUID.randomUUID();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    static final List<BackpackChangeEvent> BACKPACK_CHANGE_EVENTS = new ArrayList<>();
    private static boolean isEnabled = false;
    private static final List<EventListener<?>> EVENT_LISTENERS = new ArrayList<>();
    //    private static final Map<OdysseyMaterial, MaterialStatistic> MATERIAL_STATISTICS = new ConcurrentHashMap<>();
    private static final Map<String, Terminal> TERMINAL_DATAS = new ConcurrentHashMap<>();
    private static Thread thread;

//    static {
//        OdysseyMaterial.getAllMaterials().forEach(material ->
//                MATERIAL_STATISTICS.put(material, new MaterialStatistic())
//        );
//    }

    public static synchronized void initialize() {
        log.debug("Initialize MaterialTrackingService");
        close();
        BACKPACK_CHANGE_EVENTS.clear();
        EVENT_LISTENERS.add(EventService.addStaticListener(BackpackChangeEvent.class, MaterialTrackingService::processEvent));
        EVENT_LISTENERS.add(EventService.addStaticListener(SupercruiseEntryJournalEvent.class, superCruiseEntryJournalEvent -> publish()));//send on SC entry
        EVENT_LISTENERS.add(EventService.addStaticListener(LocationJournalEvent.class, locationJournalEvent -> publish()));//send on relog or respawn
        EVENT_LISTENERS.add(EventService.addStaticListener(CommanderSelectedEvent.class, commanderSelectedEvent -> publish()));
        EVENT_LISTENERS.add(EventService.addStaticListener(ShipLockerEvent.class, shipLockerEvent -> clearChanges(shipLockerEvent.getTimestamp())));
        EVENT_LISTENERS.add(EventService.addStaticListener(JournalInitEvent.class, journalInitEvent -> isEnabled = journalInitEvent.isInitialised()));
        EVENT_LISTENERS.add(EventService.addStaticListener(TerminateApplicationEvent.class, event -> {
            close();
        }));
//        //check if statistics file exists
//        thread = new Thread(() -> {
//            log.info("Start load material statistics");
//            final File statisticsFile = new File(OsConstants.STATISTICS);
//            try {
//                if (!statisticsFile.exists() || modifiedBeforeMonday(statisticsFile)) {
//                    log.info("Start download of material statistics");
//                    final URL url = new URL("https://material-tracking-report.s3.eu-west-1.amazonaws.com/material-report.json");
//                    try (final ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream()); final FileOutputStream fileOutputStream = new FileOutputStream(statisticsFile)) {
//                        fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
//                    }
//                }
//                log.info("Load material statistics from file");
//                //map file to MATERIAL_STATISTICS
//                final JsonNode jsonNode = OBJECT_MAPPER.readTree(Files.readString(statisticsFile.toPath()));
//                for (final OdysseyMaterial odysseyMaterial : MATERIAL_STATISTICS.keySet()) {
//                    final JsonNode materialStat = jsonNode.get(odysseyMaterial.name());
//                    if(materialStat != null) {
//                        MATERIAL_STATISTICS.put(odysseyMaterial, OBJECT_MAPPER.readValue(materialStat.toString(), MaterialStatistic.class));
//                    }
//                }
//                log.info("Load material statistics finished");
//            } catch (final IOException ex) {
//                log.error("Load material statistics failed", ex);
//                log.info("Deleted file due to suspected corruption: " + statisticsFile.delete());
//                Platform.runLater(() ->
//                        NotificationService.showError(NotificationType.ERROR, "Error", "Failed to download material statistics.")
//                );
//            }
//        }, "Material Statistics Loader Thread");
//        thread.start();
    }

    static void registerData(final String dataPortName, final DataPortType dataPortType, final Data data, final Integer id) {
        final Terminal terminal = TERMINAL_DATAS.getOrDefault(dataPortName, new Terminal());
        terminal.setType(dataPortType);
        terminal.getDatas().put(id, data);
        TERMINAL_DATAS.put(dataPortName, terminal);
    }

    static boolean modifiedBeforeMonday(final File statisticsFile) {
        final ZonedDateTime date = LocalDate.now().atStartOfDay(ZoneId.systemDefault());
        final DayOfWeek todayAsDayOfWeek = date.getDayOfWeek();
        final ZonedDateTime previousMonday = todayAsDayOfWeek == DayOfWeek.MONDAY ? date : date.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        return previousMonday.isAfter(ZonedDateTime.ofInstant(Instant.ofEpochMilli(statisticsFile.lastModified()), ZoneId.systemDefault()));
    }

//    static MaterialStatistic getMaterialStatistic(final OdysseyMaterial odysseyMaterial) {
//        return MATERIAL_STATISTICS.get(odysseyMaterial);
//    }

    public static synchronized void close() {
        log.debug("Close MaterialTrackingService");
        EVENT_LISTENERS.forEach(EventService::removeListener);
        publish();
        if (thread != null) {
            thread.interrupt();
        }
    }

    private static synchronized void clearChanges(final String timestamp) {
        log.debug("Clear changes for (-2s, +2s): " + timestamp);
        final ZonedDateTime time = ZonedDateTime.parse(timestamp);
        //remove items within range of a timestamp
        BACKPACK_CHANGE_EVENTS.removeIf(backpackChangeEvent -> ZonedDateTime.parse(backpackChangeEvent.getTimestamp()).isAfter(time.minusSeconds(3))
                && ZonedDateTime.parse(backpackChangeEvent.getTimestamp()).isBefore(time.plusSeconds(3))
        );
    }

    private static synchronized void resetSession() {
        session = UUID.randomUUID();
        log.debug("Resetting tracking session: " + session);
    }

    private static synchronized void processEvent(final BackpackChangeEvent backpackChangeEvent) {
        if (isEnabled && GameMode.SOLO.equals(APPLICATION_STATE.getGameMode()) && (backpackChangeEvent.getOdysseyMaterial() instanceof Data || backpackChangeEvent.getOdysseyMaterial() instanceof Good || backpackChangeEvent.getOdysseyMaterial() instanceof Asset)) {
            log.debug("Process backpackchange event: " + backpackChangeEvent.getTimestamp());
            BACKPACK_CHANGE_EVENTS.add(backpackChangeEvent);
        }
    }

    private static synchronized void publish() {
        if (!BACKPACK_CHANGE_EVENTS.isEmpty()) {
            log.debug("Publish to material tracking server");
            //publishMaterialTracking(new ArrayList<>(BACKPACK_CHANGE_EVENTS));
            //publishDataTracking(new HashMap<>(TERMINAL_DATAS));
            BACKPACK_CHANGE_EVENTS.clear();
            TERMINAL_DATAS.clear();
        }
        resetSession();
    }

    @SuppressWarnings("java:S5411")
    private static synchronized void publishMaterialTracking(final List<BackpackChangeEvent> backpackChangeEvents) {
        if (isEnabled && GameMode.SOLO.equals(APPLICATION_STATE.getGameMode()) && !PreferencesService.getPreference(PreferenceConstants.TRACKING_OPT_OUT, Boolean.FALSE)) {
            final String appVersion = PreferencesService.getPreference(PreferenceConstants.APP_SETTINGS_VERSION, "");
            final ArrayList<MaterialTrackingItem> items = backpackChangeEvents.stream()
                    .map(backpackChangeEvent -> MaterialTrackingItem.builder()
                            .odysseyMaterial(backpackChangeEvent.getOdysseyMaterial())
                            .amount(Operation.ADDED.equals(backpackChangeEvent.getOperation()) ? backpackChangeEvent.getAmount() : -backpackChangeEvent.getAmount())
                            .timestamp(backpackChangeEvent.getTimestamp())
                            .commander(backpackChangeEvent.getCommander())
                            .system(backpackChangeEvent.getSystem())
                            .primaryEconomy(backpackChangeEvent.getPrimaryEconomy())
                            .secondaryEconomy(backpackChangeEvent.getSecondaryEconomy())
                            .government(backpackChangeEvent.getGovernment())
                            .security(backpackChangeEvent.getSecurity())
                            .state(backpackChangeEvent.getState())
                            .body(backpackChangeEvent.getBody())
                            .settlement(backpackChangeEvent.getSettlement())
                            .x(backpackChangeEvent.getX())
                            .y(backpackChangeEvent.getY())
                            .z(backpackChangeEvent.getZ())
                            .latitude(backpackChangeEvent.getLatitude())
                            .longitude(backpackChangeEvent.getLongitude())
                            .z(backpackChangeEvent.getZ())
                            .session(session.toString())
                            .version(appVersion)
                            .build())
                    .collect(Collectors.toCollection(ArrayList::new));
            final Runnable run = () -> {
                try {
                    final String data = OBJECT_MAPPER.writeValueAsString(new MaterialTrackingMessage(items));
                    log.info(data);
                    final HttpResponse<String> send;
                    try (HttpClient httpClient = HttpClient.newHttpClient()) {
                        final String domainName = DnsHelper.resolveCname(Secrets.getOrDefault("api.services.host", "localhost"));
                        final HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create("https://" + domainName + "/Prod/v2/submit-material-tracking"))
                                .POST(HttpRequest.BodyPublishers.ofString(data))
                                .build();
                        send = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                    }
                    log.info(send.body());
                } catch (final InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (final Exception e) {
                    log.error("publish material tracking error", e);
                }
            };
            new Thread(run).start();
        }
    }

    private static synchronized void publishDataTracking(final Map<String, Terminal> datas) {
        if (isEnabled && !PreferencesService.getPreference(PreferenceConstants.TRACKING_OPT_OUT, Boolean.FALSE)) {
            final String appVersion = PreferencesService.getPreference(PreferenceConstants.APP_SETTINGS_VERSION, "");
            final String timestamp = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").format(ZonedDateTime.now().toLocalDateTime().atOffset(ZoneOffset.UTC));
            final ArrayList<DataTrackingItem> items = datas.entrySet().stream()
                    .flatMap(entry -> entry.getValue().getDatas().values().stream()
                            .collect(Collectors.groupingBy(data -> data, Collectors.counting()))
                            .entrySet().stream().map(dataAmount -> DataTrackingItem.builder()
                                    .data(dataAmount.getKey())
                                    .amount(Math.toIntExact(dataAmount.getValue()))
                                    .dataPortName(entry.getKey())
                                    .type(entry.getValue().getType())
                                    .timestamp(timestamp)//2022-10-02T08:50:16Z
                                    .commander(APPLICATION_STATE.getPreferredCommander().map(Commander::getName).orElse("UNKNOWN"))
                                    .session(session.toString())
                                    .version(appVersion)
                                    .build()))
                    .collect(Collectors.toCollection(ArrayList::new));
            final Runnable run = () -> {
                try {
                    final String datax = OBJECT_MAPPER.writeValueAsString(new DataTrackingMessage(items));
                    log.info(datax);
                    final HttpResponse<String> send;
                    try (HttpClient httpClient = HttpClient.newHttpClient()) {
                        final String domainName = DnsHelper.resolveCname(Secrets.getOrDefault("api.services.host", "localhost"));
                        final HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create("https://" + domainName + "/Prod/v2/submit-data-tracking"))
                                .POST(HttpRequest.BodyPublishers.ofString(datax))
                                .build();
                        send = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                    }
                    log.info(send.body());
                } catch (final InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (final Exception e) {
                    log.error("publish data tracking error", e);
                }
            };
            new Thread(run).start();
        }
    }
}
