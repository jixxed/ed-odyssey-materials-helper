package nl.jixxed.eliteodysseymaterials.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.helper.DnsHelper;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.service.message.MaterialTrackingItem;
import nl.jixxed.eliteodysseymaterials.service.message.MaterialTrackingMessage;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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

    private static synchronized void publishMaterialTracking(final List<BackpackChangeEvent> backpackChangeEvents) {
        if (isEnabled && GameMode.SOLO.equals(APPLICATION_STATE.getGameMode())) {
            final String appVersion = PreferencesService.getPreference(PreferenceConstants.APP_SETTINGS_VERSION, "");
            final ArrayList<MaterialTrackingItem> items = backpackChangeEvents.stream()
                    .map(backpackChangeEvent -> MaterialTrackingItem.builder()
                            .material(backpackChangeEvent.getMaterial())
                            .amount(Operation.ADDED.equals(backpackChangeEvent.getOperation()) ? backpackChangeEvent.getAmount() : -backpackChangeEvent.getAmount())
                            .timestamp(backpackChangeEvent.getTimestamp())
                            .commander(backpackChangeEvent.getCommander())
                            .system(backpackChangeEvent.getSystem())
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
                } catch (final Exception e) {
                    log.error("publish material tracking error", e);
                }
            };
            new Thread(run).start();
        }
    }
}
