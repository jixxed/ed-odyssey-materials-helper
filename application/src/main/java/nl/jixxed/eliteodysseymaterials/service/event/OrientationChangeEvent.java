package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.enums.MaterialOrientation;

@AllArgsConstructor
@Getter
public class OrientationChangeEvent implements Event {
    private final MaterialOrientation materialOrientation;
}
