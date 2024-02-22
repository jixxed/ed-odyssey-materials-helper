package nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals;

import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleClass;
import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleSize;
import nl.jixxed.eliteodysseymaterials.domain.ships.OptionalModule;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CargoRack extends OptionalModule {
    public static final CargoRack CARGO_RACK_1_E = new CargoRack("CARGO_RACK_1_E", HorizonsBlueprintName.CARGO_RACK, ModuleSize.SIZE_1, ModuleClass.E, false, 1000, "Int_CargoRack_Size1_Class1", Map.ofEntries(Map.entry(HorizonsModifier.CARGO_CAPACITY,  2.0)));
    public static final CargoRack CARGO_RACK_2_E = new CargoRack("CARGO_RACK_2_E", HorizonsBlueprintName.CARGO_RACK, ModuleSize.SIZE_2, ModuleClass.E, false, 3250, "Int_CargoRack_Size2_Class1", Map.ofEntries(Map.entry(HorizonsModifier.CARGO_CAPACITY,  4.0)));
    public static final CargoRack CARGO_RACK_3_E = new CargoRack("CARGO_RACK_3_E", HorizonsBlueprintName.CARGO_RACK, ModuleSize.SIZE_3, ModuleClass.E, false, 10560, "Int_CargoRack_Size3_Class1", Map.ofEntries(Map.entry(HorizonsModifier.CARGO_CAPACITY,  8.0)));
    public static final CargoRack CARGO_RACK_4_E = new CargoRack("CARGO_RACK_4_E", HorizonsBlueprintName.CARGO_RACK, ModuleSize.SIZE_4, ModuleClass.E, false, 34330, "Int_CargoRack_Size4_Class1", Map.ofEntries(Map.entry(HorizonsModifier.CARGO_CAPACITY,  16.0)));
    public static final CargoRack CARGO_RACK_5_E = new CargoRack("CARGO_RACK_5_E", HorizonsBlueprintName.CARGO_RACK, ModuleSize.SIZE_5, ModuleClass.E, false, 111570, "Int_CargoRack_Size5_Class1", Map.ofEntries(Map.entry(HorizonsModifier.CARGO_CAPACITY,  32.0)));
    public static final CargoRack CARGO_RACK_6_E = new CargoRack("CARGO_RACK_6_E", HorizonsBlueprintName.CARGO_RACK, ModuleSize.SIZE_6, ModuleClass.E, false, 362590, "Int_CargoRack_Size6_Class1", Map.ofEntries(Map.entry(HorizonsModifier.CARGO_CAPACITY,  64.0)));
    public static final CargoRack CARGO_RACK_7_E = new CargoRack("CARGO_RACK_7_E", HorizonsBlueprintName.CARGO_RACK, ModuleSize.SIZE_7, ModuleClass.E, false, 1178420, "Int_CargoRack_Size7_Class1", Map.ofEntries(Map.entry(HorizonsModifier.CARGO_CAPACITY,  128.0)));
    public static final CargoRack CARGO_RACK_8_E = new CargoRack("CARGO_RACK_8_E", HorizonsBlueprintName.CARGO_RACK, ModuleSize.SIZE_8, ModuleClass.E, false, 3829870, "Int_CargoRack_Size8_Class1", Map.ofEntries(Map.entry(HorizonsModifier.CARGO_CAPACITY,  256.0)));

    public static final List<CargoRack> CARGO_RACKS = List.of(
             CARGO_RACK_1_E,
             CARGO_RACK_2_E,
             CARGO_RACK_3_E,
             CARGO_RACK_4_E,
             CARGO_RACK_5_E,
             CARGO_RACK_6_E,
             CARGO_RACK_7_E,
             CARGO_RACK_8_E
    );

    public CargoRack(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, basePrice, internalName, attributes);
    }

    private CargoRack(final CargoRack cargoRack) {
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
    public CargoRack Clone() {
        return new CargoRack(this);
    }

    @Override
    public boolean isHiddenStat(HorizonsModifier modifier) {
        if(HorizonsModifier.POWER_DRAW.equals(modifier)){
            return true;
        }
        return super.isHiddenStat(modifier);
    }
    @Override
    public int getGrouping() {
        return 1;
    }
}
