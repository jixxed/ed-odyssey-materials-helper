package nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals;

import nl.jixxed.eliteodysseymaterials.domain.ships.CargoOptionalModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleClass;
import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleSize;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class OptimisedCargoRack extends CargoOptionalModule {
    public static final OptimisedCargoRack OPTIMISED_CARGO_RACK_7_E = new OptimisedCargoRack("OPTIMISED_CARGO_RACK_7_E", HorizonsBlueprintName.OPTIMISED_CARGO_RACK, ModuleSize.SIZE_7, ModuleClass.E, 0, "Int_OptimisedCargoRack_Size7_Class1", Map.ofEntries(Map.entry(HorizonsModifier.CARGO_CAPACITY, 192.0)));
    public static final OptimisedCargoRack OPTIMISED_CARGO_RACK_8_E = new OptimisedCargoRack("OPTIMISED_CARGO_RACK_8_E", HorizonsBlueprintName.OPTIMISED_CARGO_RACK, ModuleSize.SIZE_8, ModuleClass.E, 0, "Int_OptimisedCargoRack_Size8_Class1", Map.ofEntries(Map.entry(HorizonsModifier.CARGO_CAPACITY, 384.0)));

    public static final List<OptimisedCargoRack> OPTIMISED_CARGO_RACKS = List.of(
            OPTIMISED_CARGO_RACK_7_E,
            OPTIMISED_CARGO_RACK_8_E
    );

    public OptimisedCargoRack(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, basePrice, internalName, attributes);
    }

    private OptimisedCargoRack(final OptimisedCargoRack cargoRack) {
        super(cargoRack);
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
    public OptimisedCargoRack Clone() {
        return new OptimisedCargoRack(this);
    }

    @Override
    public boolean isHiddenStat(HorizonsModifier modifier) {
        if (HorizonsModifier.POWER_DRAW.equals(modifier)) {
            return true;
        }
        return super.isHiddenStat(modifier);
    }

    @Override
    public int getGrouping() {
        return 1;
    }
}
