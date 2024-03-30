package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;

@AllArgsConstructor
@Getter
public class ModuleHighlightEvent implements Event {
    private final ShipModule shipModule;
}
