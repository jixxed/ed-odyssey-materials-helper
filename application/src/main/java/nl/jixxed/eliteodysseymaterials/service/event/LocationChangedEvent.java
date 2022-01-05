package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;

@RequiredArgsConstructor
@Getter
public class LocationChangedEvent implements Event {
    private final StarSystem currentStarSystem;
    private final String currentBody;
    private final String currentSettlement;
    private final Double currentLatitude;
    private final Double currentLongitude;
}
