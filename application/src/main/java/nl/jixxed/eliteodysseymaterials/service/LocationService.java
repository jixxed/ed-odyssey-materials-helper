package nl.jixxed.eliteodysseymaterials.service;

import nl.jixxed.eliteodysseymaterials.domain.Location;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.service.event.*;

public class LocationService {
    private static StarSystem currentStarSystem = new StarSystem("Sol", 0, 0, 0);
    private static String body = "";
    private static String station = "";

    private LocationService() {
    }

    static {
        EventService.addStaticListener(ApproachBodyJournalEvent.class, event -> {//When approaching body
            body = event.getBody();
            station = "";
            notifyListeners();
        });
        EventService.addStaticListener(ApproachSettlementJournalEvent.class, event -> {//when approaching settlement, also on startup if at settlement
            body = event.getBody();
            station = "";
            notifyListeners();
        });
        EventService.addStaticListener(DockedJournalEvent.class, event -> {//Always player controlled
            station = event.getStationName();
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
            if (event.getPlayerControlled()) {
                station = "";
            }
            notifyListeners();
        });
        EventService.addStaticListener(LocationJournalEvent.class, event -> {//at startup or upon respawn
            currentStarSystem = event.getStarSystem();
            body = event.getBody();
            station = event.getStationName();
            notifyListeners();
        });
        EventService.addStaticListener(SupercruiseEntryJournalEvent.class, event -> {//on entering SC
            station = "";
            notifyListeners();
        });
        EventService.addStaticListener(TouchdownJournalEvent.class, event -> {//can be either player or AI controlled
            if (event.getPlayerControlled()) {
                station = event.getNearestDestination();
            }
            notifyListeners();
        });
        EventService.addStaticListener(UndockedJournalEvent.class, event -> {//Always player controlled
            station = "";
            notifyListeners();
        });
    }

    public static StarSystem getCurrentStarSystem() {
        return currentStarSystem;
    }

    private static void notifyListeners() {
        EventService.publish(new LocationChangedEvent(currentStarSystem, body, station));
    }

    public static Location getCurrentLocation() {
        return new Location(currentStarSystem, body, station);
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
