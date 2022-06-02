package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;

@AllArgsConstructor
@Getter
public class PointToOdysseyResourceEvent implements Event {
    private final OdysseyMaterial odysseyMaterial;
}
