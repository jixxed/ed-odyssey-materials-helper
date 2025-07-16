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

public class LargeCargoRack extends CargoOptionalModule {
    public static final LargeCargoRack LARGE_CARGO_RACK_7_D = new LargeCargoRack("LARGE_CARGO_RACK_7_D", HorizonsBlueprintName.LARGE_CARGO_RACK, ModuleSize.SIZE_7, ModuleClass.D, 0, "Int_LargeCargoRack_Size7_Class1", Map.ofEntries(Map.entry(HorizonsModifier.CARGO_CAPACITY, 192.0)));
    public static final LargeCargoRack LARGE_CARGO_RACK_8_D = new LargeCargoRack("LARGE_CARGO_RACK_8_D", HorizonsBlueprintName.LARGE_CARGO_RACK, ModuleSize.SIZE_8, ModuleClass.D, 0, "Int_LargeCargoRack_Size8_Class1", Map.ofEntries(Map.entry(HorizonsModifier.CARGO_CAPACITY, 384.0)));

    public static final List<LargeCargoRack> LARGE_CARGO_RACKS = List.of(
            LARGE_CARGO_RACK_7_D,
            LARGE_CARGO_RACK_8_D
    );

    public LargeCargoRack(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, basePrice, internalName, attributes);
    }

    private LargeCargoRack(final LargeCargoRack cargoRack) {
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
    public LargeCargoRack Clone() {
        return new LargeCargoRack(this);
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
