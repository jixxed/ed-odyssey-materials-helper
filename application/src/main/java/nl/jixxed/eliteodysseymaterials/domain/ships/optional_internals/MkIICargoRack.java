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

public class MkIICargoRack extends CargoOptionalModule {
    public static final MkIICargoRack MK_II_CARGO_RACK_7_D = new MkIICargoRack("MK_II_CARGO_RACK_7_D", HorizonsBlueprintName.MK_II_CARGO_RACK, ModuleSize.SIZE_7, ModuleClass.D, 0, "Int_MkIICargoRack_Size7_Class2", Map.ofEntries(Map.entry(HorizonsModifier.CARGO_CAPACITY, 192.0)));
    public static final MkIICargoRack MK_II_CARGO_RACK_8_D = new MkIICargoRack("MK_II_CARGO_RACK_8_D", HorizonsBlueprintName.MK_II_CARGO_RACK, ModuleSize.SIZE_8, ModuleClass.D, 0, "Int_MkIICargoRack_Size8_Class2", Map.ofEntries(Map.entry(HorizonsModifier.CARGO_CAPACITY, 384.0)));

    public static final List<MkIICargoRack> MK_II_CARGO_RACKS = List.of(
            MK_II_CARGO_RACK_7_D,
            MK_II_CARGO_RACK_8_D
    );

    public MkIICargoRack(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, basePrice, internalName, attributes);
    }

    private MkIICargoRack(final MkIICargoRack cargoRack) {
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
    public MkIICargoRack Clone() {
        return new MkIICargoRack(this);
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
