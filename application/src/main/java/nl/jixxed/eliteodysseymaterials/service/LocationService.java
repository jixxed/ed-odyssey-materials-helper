package nl.jixxed.eliteodysseymaterials.service;

import nl.jixxed.eliteodysseymaterials.domain.FullLocation;
import nl.jixxed.eliteodysseymaterials.domain.Location;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.LocationEvent;
import nl.jixxed.eliteodysseymaterials.service.event.SimpleLocationEvent;

public class LocationService {
    private static Location currentLocation = new Location("Sol", 0, 0, 0);
    private static String body = "";
    private static String station = "";

    private LocationService() {
    }

    static {
        EventService.addStaticListener(LocationEvent.class, locationEvent -> currentLocation = locationEvent.getLocation());
        EventService.addStaticListener(SimpleLocationEvent.class, simpleLocationEvent -> {
            station = simpleLocationEvent.getStation().orElse("");
            switch (simpleLocationEvent.getLocationType()) {
                case LOCATION:
                    final String systemBody = simpleLocationEvent.getBody().orElse("");
                    final String systemStation = simpleLocationEvent.getStation().orElse("");
                    body = systemBody.equals(systemStation) ? "" : systemBody;
                    break;
                case DOCKED:
                    break;
                case UNDOCKED:
                    station = "";
                    break;
                default:
                    body = simpleLocationEvent.getBody().orElse("");
                    break;
            }
        });
    }

    public static Location getCurrentLocation() {
        return currentLocation;
    }

    public static FullLocation getCurrentFullLocation() {
        return new FullLocation(currentLocation, body, station);
    }

    public static Double calculateDistance(final Location currentLocation, final Location location) {
        return calculateDistance(currentLocation.getX(), currentLocation.getY(), currentLocation.getZ(), location.getX(), location.getY(), location.getZ());
    }

    private static Double calculateDistance(final double x1, final double y1, final double z1, final double x2, final double y2, final double z2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z2 - z1, 2));
    }
}
