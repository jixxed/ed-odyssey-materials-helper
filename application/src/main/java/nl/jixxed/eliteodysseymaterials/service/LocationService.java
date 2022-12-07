package nl.jixxed.eliteodysseymaterials.service;

import nl.jixxed.eliteodysseymaterials.domain.Location;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.service.event.*;

import java.util.Objects;

public class LocationService {
    private static final Double DEFAULT_LATITUDE = 999.9;
    private static final Double DEFAULT_LONGITUDE = 999.9;
    private static StarSystem currentStarSystem = new StarSystem("Sol", 0, 0, 0);
    private static String body = "";
    private static String station = "";
    private static Double latitude = DEFAULT_LATITUDE;
    private static Double longitude = DEFAULT_LONGITUDE;

    private LocationService() {
    }

    static {
        EventService.addStaticListener(ApproachBodyJournalEvent.class, event -> {//When approaching body
            body = event.getApproachBody().getBody();
            station = "";
            notifyListeners();
        });
        EventService.addStaticListener(ApproachSettlementJournalEvent.class, event -> {//when approaching settlement, also on startup if at settlement
            body = event.getApproachSettlement().getBodyName();
            station = "";
            notifyListeners();
        });
        EventService.addStaticListener(DockedJournalEvent.class, event -> {//Always player controlled
            station = event.getDocked().getStationName();
            notifyListeners();
        });
        EventService.addStaticListener(FSDJumpJournalEvent.class, event -> {//After jump to other system
            currentStarSystem = event.getStarSystem();
            body = event.getBody();
            notifyListeners();
        });
        EventService.addStaticListener(LeaveBodyJournalEvent.class, event -> {//on leaving body
            body = "";
            station = "";
            notifyListeners();
        });
        EventService.addStaticListener(LiftOffJournalEvent.class, event -> {//can be either player or AI controlled
            if (event.getLiftoff().getPlayerControlled() || event.getLiftoff().getTaxi().orElse(false)) {// both false means ship sent away
                station = "";
                latitude = DEFAULT_LATITUDE;
                longitude = DEFAULT_LONGITUDE;
            }
            notifyListeners();
        });
        EventService.addStaticListener(LocationJournalEvent.class, event -> {//at startup or upon respawn
            currentStarSystem = event.getStarSystem();
            body = event.getBody();
            //on relog station is empty for POI's.
            //on respawn after death there is a station?
            //if we already have a station from before the relog, we keep the station
            if (!Objects.equals(event.getStationName(), "")) {
                station = event.getStationName();
            }
            notifyListeners();
        });
        EventService.addStaticListener(SupercruiseEntryJournalEvent.class, event -> {//on entering SC
            station = "";
            latitude = DEFAULT_LATITUDE;
            longitude = DEFAULT_LONGITUDE;
            notifyListeners();
        });
        EventService.addStaticListener(TouchdownJournalEvent.class, event -> {//can be either player or AI controlled
            station = event.getTouchdown().getNearestDestination().orElse("");
            latitude = event.getTouchdown().getLatitude().orElse( DEFAULT_LATITUDE);
            longitude = event.getTouchdown().getLongitude().orElse( DEFAULT_LONGITUDE);

            notifyListeners();
        });
        EventService.addStaticListener(UndockedJournalEvent.class, event -> {//Always player controlled
            station = "";
            latitude = DEFAULT_LATITUDE;
            longitude = DEFAULT_LONGITUDE;
            notifyListeners();
        });
    }

    public static StarSystem getCurrentStarSystem() {
        return currentStarSystem;
    }

    private static void notifyListeners() {
        EventService.publish(new LocationChangedEvent(currentStarSystem, body, station, latitude, longitude));
    }

    public static Location getCurrentLocation() {
        return new Location(currentStarSystem, body, station, latitude, longitude);
    }

    public static Double calculateDistance(final StarSystem currentStarSystem, final StarSystem starSystem) {
        return calculateDistance(currentStarSystem.getX(), currentStarSystem.getY(), currentStarSystem.getZ(), starSystem.getX(), starSystem.getY(), starSystem.getZ());
    }

    private static Double calculateDistance(final double x1, final double y1, final double z1, final double x2, final double y2, final double z2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z2 - z1, 2));
    }

    public static String getCurrentStarSystemName() {
        return currentStarSystem.getName();
    }
}
