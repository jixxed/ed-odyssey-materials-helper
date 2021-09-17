package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.Location;

@AllArgsConstructor
@Getter
public class LocationEvent implements Event {
    private final Location location;
}
