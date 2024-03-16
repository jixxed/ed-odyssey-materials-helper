package nl.jixxed.eliteodysseymaterials.domain.ships.special;

import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleClass;
import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleSize;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CargoHatch extends ShipModule {
    public static final CargoHatch CARGO_HATCH = new CargoHatch("CARGO_HATCH",HorizonsBlueprintName.CARGO_HATCH, ModuleSize.SIZE_0, ModuleClass.A, 0, "ModularCargoBayDoor", Map.of(HorizonsModifier.POWER_DRAW, 0.6));
    public static final List<CargoHatch> CARGO_HATCHES  = List.of(
            CARGO_HATCH
    );
    public CargoHatch(CargoHatch cargoHatch) {
        super(cargoHatch);
    }
    private CargoHatch(final String id, final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final long basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, basePrice, internalName, attributes);
    }
    @Override
    public List<HorizonsBlueprintType> getAllowedBlueprints() {
        return Collections.emptyList();
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedExperimentalEffects() {
        return Collections.emptyList();
    }

    @Override
    public ShipModule Clone() {
        return new CargoHatch(this);
    }
}
