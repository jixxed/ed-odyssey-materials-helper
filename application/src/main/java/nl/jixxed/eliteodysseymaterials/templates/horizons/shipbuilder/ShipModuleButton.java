package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableButton;

@AllArgsConstructor
@Getter
public class ShipModuleButton extends DestroyableButton {
    private ShipModule shipModule;

}
