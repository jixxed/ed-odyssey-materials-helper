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


//		      47130 : { mtype:'cft', cost:   1000, name:'Fuel Tank (Cap: 2)',   class:1, rating:'C', fuelcap:  2.0, fdid:128064346, fdname:'Int_FuelTank_Size1_Class3', eddbid:1199 },
//            47230 : { mtype:'cft', cost:   3750, name:'Fuel Tank (Cap: 4)',   class:2, rating:'C', fuelcap:  4.0, fdid:128064347, fdname:'Int_FuelTank_Size2_Class3', eddbid:1200 },
//            47330 : { mtype:'cft', cost:   7060, name:'Fuel Tank (Cap: 8)',   class:3, rating:'C', fuelcap:  8.0, fdid:128064348, fdname:'Int_FuelTank_Size3_Class3', eddbid:1201 },
//            47430 : { mtype:'cft', cost:  24730, name:'Fuel Tank (Cap: 16)',  class:4, rating:'C', fuelcap: 16.0, fdid:128064349, fdname:'Int_FuelTank_Size4_Class3', eddbid:1202 },
//            47530 : { mtype:'cft', cost:  97750, name:'Fuel Tank (Cap: 32)',  class:5, rating:'C', fuelcap: 32.0, fdid:128064350, fdname:'Int_FuelTank_Size5_Class3', eddbid:1203 },
//            47630 : { mtype:'cft', cost: 341580, name:'Fuel Tank (Cap: 64)',  class:6, rating:'C', fuelcap: 64.0, fdid:128064351, fdname:'Int_FuelTank_Size6_Class3', eddbid:1204 },
//            47730 : { mtype:'cft', cost:1780910, name:'Fuel Tank (Cap: 128)', class:7, rating:'C', fuelcap:128.0, fdid:128064352, fdname:'Int_FuelTank_Size7_Class3', eddbid:1205 },
//            47830 : { mtype:'cft', cost:5428430, name:'Fuel Tank (Cap: 256)', class:8, rating:'C', fuelcap:256.0, fdid:128064353, fdname:'Int_FuelTank_Size8_Class3', eddbid:1206 },
    public static final FuelTank FUEL_TANK_1_C = new FuelTank(HorizonsBlueprintName.FUEL_TANK, ModuleSize.SIZE_1, ModuleClass.C,    1000, "Int_FuelTank_Size1_Class3", Map.ofEntries(Map.entry(HorizonsModifier.FUEL_CAPACITY,   2.00)));
    public static final FuelTank FUEL_TANK_2_C = new FuelTank(HorizonsBlueprintName.FUEL_TANK, ModuleSize.SIZE_2, ModuleClass.C,    3750, "Int_FuelTank_Size2_Class3", Map.ofEntries(Map.entry(HorizonsModifier.FUEL_CAPACITY,   4.00)));
    public static final FuelTank FUEL_TANK_3_C = new FuelTank(HorizonsBlueprintName.FUEL_TANK, ModuleSize.SIZE_3, ModuleClass.C,    7060, "Int_FuelTank_Size3_Class3", Map.ofEntries(Map.entry(HorizonsModifier.FUEL_CAPACITY,   8.00)));
    public static final FuelTank FUEL_TANK_4_C = new FuelTank(HorizonsBlueprintName.FUEL_TANK, ModuleSize.SIZE_4, ModuleClass.C,   24730, "Int_FuelTank_Size4_Class3", Map.ofEntries(Map.entry(HorizonsModifier.FUEL_CAPACITY,  16.00)));
    public static final FuelTank FUEL_TANK_5_C = new FuelTank(HorizonsBlueprintName.FUEL_TANK, ModuleSize.SIZE_5, ModuleClass.C,   97750, "Int_FuelTank_Size5_Class3", Map.ofEntries(Map.entry(HorizonsModifier.FUEL_CAPACITY,  32.00)));
    public static final FuelTank FUEL_TANK_6_C = new FuelTank(HorizonsBlueprintName.FUEL_TANK, ModuleSize.SIZE_6, ModuleClass.C,  341580, "Int_FuelTank_Size6_Class3", Map.ofEntries(Map.entry(HorizonsModifier.FUEL_CAPACITY,  64.00)));
    public static final FuelTank FUEL_TANK_7_C = new FuelTank(HorizonsBlueprintName.FUEL_TANK, ModuleSize.SIZE_7, ModuleClass.C, 1780910, "Int_FuelTank_Size7_Class3", Map.ofEntries(Map.entry(HorizonsModifier.FUEL_CAPACITY, 128.00)));
    public static final FuelTank FUEL_TANK_8_C = new FuelTank(HorizonsBlueprintName.FUEL_TANK, ModuleSize.SIZE_8, ModuleClass.C, 5428430, "Int_FuelTank_Size8_Class3", Map.ofEntries(Map.entry(HorizonsModifier.FUEL_CAPACITY, 256.00)));
    public static final List<FuelTank> FUEL_TANKS  = List.of(
            FUEL_TANK_1_C,
            FUEL_TANK_2_C,
            FUEL_TANK_3_C,
            FUEL_TANK_4_C,
            FUEL_TANK_5_C,
            FUEL_TANK_6_C,
            FUEL_TANK_7_C,
            FUEL_TANK_8_C
            );
    private FuelTank(final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final int basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        super(name, moduleSize, moduleClass, false, basePrice, internalName, attributes);
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
}
