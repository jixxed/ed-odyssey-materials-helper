package nl.jixxed.eliteodysseymaterials.service;

import nl.jixxed.eliteodysseymaterials.domain.Location;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.LocationEvent;

class LocationService {
    private static Location currentLocation = new Location("Sol", 0, 0, 0);

    private LocationService() {
    }

    static {
        EventService.addListener(LocationEvent.class, locationEvent -> {
            currentLocation = locationEvent.getLocation();
        });
    }

    static Location getCurrentLocation() {
        return currentLocation;
    }
}
