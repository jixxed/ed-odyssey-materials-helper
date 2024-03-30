package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
@AllArgsConstructor
public class ShipModuleButton extends Button {
    @Getter
    private ShipModule shipModule;

}
