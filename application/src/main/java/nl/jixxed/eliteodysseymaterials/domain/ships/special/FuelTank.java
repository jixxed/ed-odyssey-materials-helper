package nl.jixxed.eliteodysseymaterials.domain.ships.special;

import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleClass;
import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleSize;
import nl.jixxed.eliteodysseymaterials.domain.ships.OptionalModule;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class FuelTank extends OptionalModule {

    public static final FuelTank FUEL_TANK_1_C_FREE = new FuelTank("FUEL_TANK_1_C_FREE", HorizonsBlueprintName.FUEL_TANK, ModuleSize.SIZE_1, ModuleClass.C,    0, "Int_FuelTank_Size1_Class3_free", Map.ofEntries(Map.entry(HorizonsModifier.FUEL_CAPACITY,   2.00)));
    public static final FuelTank FUEL_TANK_1_C = new FuelTank("FUEL_TANK_1_C", HorizonsBlueprintName.FUEL_TANK, ModuleSize.SIZE_1, ModuleClass.C,    1000, "Int_FuelTank_Size1_Class3", Map.ofEntries(Map.entry(HorizonsModifier.FUEL_CAPACITY,   2.00)));
    public static final FuelTank FUEL_TANK_2_C = new FuelTank("FUEL_TANK_2_C", HorizonsBlueprintName.FUEL_TANK, ModuleSize.SIZE_2, ModuleClass.C,    3750, "Int_FuelTank_Size2_Class3", Map.ofEntries(Map.entry(HorizonsModifier.FUEL_CAPACITY,   4.00)));
    public static final FuelTank FUEL_TANK_3_C = new FuelTank("FUEL_TANK_3_C", HorizonsBlueprintName.FUEL_TANK, ModuleSize.SIZE_3, ModuleClass.C,    7060, "Int_FuelTank_Size3_Class3", Map.ofEntries(Map.entry(HorizonsModifier.FUEL_CAPACITY,   8.00)));
    public static final FuelTank FUEL_TANK_4_C = new FuelTank("FUEL_TANK_4_C", HorizonsBlueprintName.FUEL_TANK, ModuleSize.SIZE_4, ModuleClass.C,   24730, "Int_FuelTank_Size4_Class3", Map.ofEntries(Map.entry(HorizonsModifier.FUEL_CAPACITY,  16.00)));
    public static final FuelTank FUEL_TANK_5_C = new FuelTank("FUEL_TANK_5_C", HorizonsBlueprintName.FUEL_TANK, ModuleSize.SIZE_5, ModuleClass.C,   97750, "Int_FuelTank_Size5_Class3", Map.ofEntries(Map.entry(HorizonsModifier.FUEL_CAPACITY,  32.00)));
    public static final FuelTank FUEL_TANK_6_C = new FuelTank("FUEL_TANK_6_C", HorizonsBlueprintName.FUEL_TANK, ModuleSize.SIZE_6, ModuleClass.C,  341580, "Int_FuelTank_Size6_Class3", Map.ofEntries(Map.entry(HorizonsModifier.FUEL_CAPACITY,  64.00)));
    public static final FuelTank FUEL_TANK_7_C = new FuelTank("FUEL_TANK_7_C", HorizonsBlueprintName.FUEL_TANK, ModuleSize.SIZE_7, ModuleClass.C, 1780910, "Int_FuelTank_Size7_Class3", Map.ofEntries(Map.entry(HorizonsModifier.FUEL_CAPACITY, 128.00)));
    public static final FuelTank FUEL_TANK_8_C = new FuelTank("FUEL_TANK_8_C", HorizonsBlueprintName.FUEL_TANK, ModuleSize.SIZE_8, ModuleClass.C, 5428430, "Int_FuelTank_Size8_Class3", Map.ofEntries(Map.entry(HorizonsModifier.FUEL_CAPACITY, 256.00)));
    public static final List<FuelTank> FUEL_TANKS  = List.of(
            FUEL_TANK_1_C_FREE,
            FUEL_TANK_1_C,
            FUEL_TANK_2_C,
            FUEL_TANK_3_C,
            FUEL_TANK_4_C,
            FUEL_TANK_5_C,
            FUEL_TANK_6_C,
            FUEL_TANK_7_C,
            FUEL_TANK_8_C
    );
    private FuelTank(final String id, final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final long basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, false, basePrice, internalName, attributes);
    }
    private FuelTank(final FuelTank fuelTank) {
        super(fuelTank);
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
    public FuelTank Clone() {
        return new FuelTank(this);
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
