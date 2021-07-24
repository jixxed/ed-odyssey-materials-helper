package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LocationEvent extends Event {
    private final String starSystem;
    private final double x;
    private final double y;
    private final double z;
}
