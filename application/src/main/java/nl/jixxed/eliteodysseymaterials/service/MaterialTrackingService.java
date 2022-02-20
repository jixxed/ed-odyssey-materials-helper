package nl.jixxed.eliteodysseymaterials.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.MaterialStatistic;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.helper.DnsHelper;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.service.message.MaterialTrackingItem;
import nl.jixxed.eliteodysseymaterials.service.message.MaterialTrackingMessage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
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
    private static final List<EventListener<?>> eventListeners = new ArrayList<>();
    private static final Map<Material, MaterialStatistic> MATERIAL_STATISTICS = new ConcurrentHashMap<>();

    static {
        Material.getAllMaterials().forEach(material ->
                MATERIAL_STATISTICS.put(material, new MaterialStatistic())
        );
    }

    public static synchronized void initialize() {
        log.debug("Initialize MaterialTrackingService");
        close();
        BACKPACK_CHANGE_EVENTS.clear();
        eventListeners.add(EventService.addStaticListener(BackpackChangeEvent.class, MaterialTrackingService::processEvent));
        eventListeners.add(EventService.addStaticListener(SupercruiseEntryJournalEvent.class, superCruiseEntryJournalEvent -> publish()));//send on SC entry
        eventListeners.add(EventService.addStaticListener(LocationJournalEvent.class, locationJournalEvent -> publish()));//send on relog or respawn
        eventListeners.add(EventService.addStaticListener(CommanderSelectedEvent.class, commanderSelectedEvent -> publish()));
        eventListeners.add(EventService.addStaticListener(ShipLockerEvent.class, shipLockerEvent -> clearChanges(shipLockerEvent.getTimestamp())));
        eventListeners.add(EventService.addStaticListener(JournalInitEvent.class, journalInitEvent -> isEnabled = journalInitEvent.isInitialised()));

        //check if statistics file exists
        new Thread(() -> {
            log.info("Start load material statistics");
            final File statisticsFile = new File(OsConstants.STATISTICS);
            try {
                if (!statisticsFile.exists() || ZonedDateTime.now().minus(1, ChronoUnit.DAYS).isAfter(ZonedDateTime.ofInstant(Instant.ofEpochMilli(statisticsFile.lastModified()), ZoneId.systemDefault()))) {

                    log.info("Start download of material statistics");
                    final URL url = new URL("https://material-tracking-report.s3.eu-west-1.amazonaws.com/material-report.json");
                    try (final ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream()); final FileOutputStream fileOutputStream = new FileOutputStream(statisticsFile)) {
                        fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
                    }
                }
                log.info("Load material statistics from file");
                //map file to MATERIAL_STATISTICS
                final JsonNode jsonNode = OBJECT_MAPPER.readTree(Files.readString(statisticsFile.toPath()));
                for (final Material material : MATERIAL_STATISTICS.keySet()) {
                    final JsonNode materialStat = jsonNode.get(material.name());
                    MATERIAL_STATISTICS.put(material, OBJECT_MAPPER.readValue(materialStat.toString(), MaterialStatistic.class));
                }
                log.info("Load material statistics finished");
            } catch (final IOException ex) {
                log.error("Load material statistics failed", ex);
                Platform.runLater(() ->
                        NotificationService.showError("Error", "Failed to download material statistics.")
                );
            }
        }, "Material Statistics Loader Thread").start();
    }

    static MaterialStatistic getMaterialStatistic(final Material material) {
        return MATERIAL_STATISTICS.get(material);
    }

    public static synchronized void close() {
        log.debug("Close MaterialTrackingService");
        eventListeners.forEach(EventService::removeListener);
        publish();
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
        if (isEnabled && GameMode.SOLO.equals(APPLICATION_STATE.getGameMode()) && (backpackChangeEvent.getMaterial() instanceof Data || backpackChangeEvent.getMaterial() instanceof Good || backpackChangeEvent.getMaterial() instanceof Asset)) {
            log.debug("Process backpackchange event: " + backpackChangeEvent.getTimestamp());
            BACKPACK_CHANGE_EVENTS.add(backpackChangeEvent);
        }
    }

    private static synchronized void publish() {
        if (!BACKPACK_CHANGE_EVENTS.isEmpty()) {
            log.debug("Publish to material tracking server");
            publishMaterialTracking(new ArrayList<>(BACKPACK_CHANGE_EVENTS));
            BACKPACK_CHANGE_EVENTS.clear();
        }
        resetSession();
    }

    @SuppressWarnings("java:S5411")
    private static synchronized void publishMaterialTracking(final List<BackpackChangeEvent> backpackChangeEvents) {
        if (isEnabled && GameMode.SOLO.equals(APPLICATION_STATE.getGameMode()) && !PreferencesService.getPreference(PreferenceConstants.TRACKING_OPT_OUT, Boolean.FALSE)) {
            final String appVersion = PreferencesService.getPreference(PreferenceConstants.APP_SETTINGS_VERSION, "");
            final ArrayList<MaterialTrackingItem> items = backpackChangeEvents.stream()
                    .map(backpackChangeEvent -> MaterialTrackingItem.builder()
                            .material(backpackChangeEvent.getMaterial())
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
                    final HttpClient httpClient = HttpClient.newHttpClient();
                    final String domainName = DnsHelper.resolveCname("edmattracking.jixxed.nl");
                    final HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create("https://" + domainName + "/Prod/submit"))
                            .POST(HttpRequest.BodyPublishers.ofString(data))
                            .build();
                    final HttpResponse<String> send = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
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
}
