package nl.jixxed.eliteodysseymaterials.service;

import nl.jixxed.eliteodysseymaterials.domain.Location;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.LocationEvent;

public class LocationService {
    private static Location currentLocation = new Location("Sol", 0, 0, 0);

    public LocationService() {
        EventService.addListener(LocationEvent.class, locationEvent -> {
            currentLocation = locationEvent.getLocation();
        });
    }

    public static Location getCurrentLocation() {
        return currentLocation;
    }
}
