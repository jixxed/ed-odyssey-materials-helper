package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.SlotBox;

@AllArgsConstructor
@Getter
@ToString
public class SlotboxEngineeringEvent implements Event {
    private final SlotBox slotBox;
    private final ShipModule shipModule;
}
