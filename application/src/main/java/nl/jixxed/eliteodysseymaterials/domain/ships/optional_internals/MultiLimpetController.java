package nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals;

import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleClass;
import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleSize;
import nl.jixxed.eliteodysseymaterials.domain.ships.OptionalModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.Origin;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MultiLimpetController extends OptionalModule {
//    19351 : { mtype:'imlc', cost:    15000, name:'Mining Multi Limpet Controller',    class:3, rating:'E', mlctype:'M', mass: 12, integ: 45, pwrdraw:0.50, boottime:6, maxlimpet: 4, lpactrng: 3300, limpettime:1/0, maxspd:200, multispd:60,                                                                    limit:'imlc', fdid:129001921, fdname:'Int_MultiDroneControl_Mining_Size3_Class1', eddbid:1816 },
//            19331 : { mtype:'imlc', cost:    50000, name:'Mining Multi Limpet Controller',    class:3, rating:'C', mlctype:'M', mass: 10, integ: 68, pwrdraw:0.35, boottime:6, maxlimpet: 4, lpactrng: 5000, limpettime:1/0, maxspd:200, multispd:60,                                                                    limit:'imlc', fdid:129001922, fdname:'Int_MultiDroneControl_Mining_Size3_Class3', eddbid:1817 },
//            19332 : { mtype:'imlc', cost:    50000, name:'Operations Limpet Controller',      class:3, rating:'C', mlctype:'O', mass: 10, integ: 68, pwrdraw:0.35, boottime:6, maxlimpet: 4, lpactrng: 2600, limpettime:510, maxspd:500, multispd:60,                              hacktime:16, mincargo:3, maxcargo: 8, limit:'imlc', fdid:129001923, fdname:'Int_MultiDroneControl_Operations_Size3_Class3', eddbid:1818 },
//            19322 : { mtype:'imlc', cost:    80000, name:'Operations Limpet Controller',      class:3, rating:'B', mlctype:'O', mass: 15, integ: 80, pwrdraw:0.30, boottime:6, maxlimpet: 4, lpactrng: 3100, limpettime:420, maxspd:500, multispd:60,                              hacktime:22, mincargo:4, maxcargo: 9, limit:'imlc', fdid:129001924, fdname:'Int_MultiDroneControl_Operations_Size3_Class4', eddbid:1819 },
//            19343 : { mtype:'imlc', cost:    30000, name:'Rescue Limpet Controller',          class:3, rating:'D', mlctype:'R', mass:  8, integ: 58, pwrdraw:0.40, boottime:6, maxlimpet: 4, lpactrng: 2100, limpettime:300, maxspd:500,              fuelxfer:1.0, lmprepcap: 60, hacktime:19, mincargo:2, maxcargo: 7, limit:'imlc', fdid:129001925, fdname:'Int_MultiDroneControl_Rescue_Size3_Class2', eddbid:1820 },
//            19333 : { mtype:'imlc', cost:    50000, name:'Rescue Limpet Controller',          class:3, rating:'C', mlctype:'R', mass: 10, integ: 68, pwrdraw:0.35, boottime:6, maxlimpet: 4, lpactrng: 2600, limpettime:300, maxspd:500,              fuelxfer:1.0, lmprepcap: 60, hacktime:16, mincargo:3, maxcargo: 8, limit:'imlc', fdid:129001926, fdname:'Int_MultiDroneControl_Rescue_Size3_Class3', eddbid:1821 },
//            19334 : { mtype:'imlc', cost:    50000, name:'Xeno Limpet Controller',            class:3, rating:'C', mlctype:'X', mass: 10, integ: 68, pwrdraw:0.35, boottime:6, maxlimpet: 4, lpactrng: 5000, limpettime:300, maxspd:200,                            lmprepcap: 70,                                       limit:'imlc', fdid:129001927, fdname:'Int_MultiDroneControl_Xeno_Size3_Class3', eddbid:1822 },
//            19324 : { mtype:'imlc', cost:    80000, name:'Xeno Limpet Controller',            class:3, rating:'B', mlctype:'X', mass: 15, integ: 80, pwrdraw:0.30, boottime:6, maxlimpet: 4, lpactrng: 5000, limpettime:300, maxspd:200,                            lmprepcap: 70,                                       limit:'imlc', fdid:129001928, fdname:'Int_MultiDroneControl_Xeno_Size3_Class4', eddbid:1823 },
//            19730 : { mtype:'imlc', cost:  4000000, name:'Universal Multi Limpet Controller', class:7, rating:'C', mlctype:'U', mass:125, integ:150, pwrdraw:0.80, boottime:6, maxlimpet: 8, lpactrng: 6500, limpettime:1/0, maxspd:500, multispd:60, fuelxfer:1.0, lmprepcap:310, hacktime: 8, mincargo:3, maxcargo: 8, limit:'imlc', fdid:129001929, fdname:'Int_MultiDroneControl_Universal_Size7_Class3', eddbid:1824 },
//            19710 : { mtype:'imlc', cost:  8000000, name:'Universal Multi Limpet Controller', class:7, rating:'A', mlctype:'U', mass:140, integ:200, pwrdraw:1.10, boottime:6, maxlimpet: 8, lpactrng: 9100, limpettime:1/0, maxspd:500, multispd:60, fuelxfer:1.0, lmprepcap:310, hacktime: 5, mincargo:5, maxcargo:10, limit:'imlc', fdid:129001930, fdname:'Int_MultiDroneControl_Universal_Size7_Class5', eddbid:1825 },

    public static final MultiLimpetController MINING_MULTI_LIMPET_CONTROLLER_3_E = new MultiLimpetController("MINING_MULTI_LIMPET_CONTROLLER_3_E", HorizonsBlueprintName.MINING_MULTI_LIMPET_CONTROLLER, ModuleSize.SIZE_3, ModuleClass.E, true, 15000, "Int_MultiDroneControl_Mining_Size3_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  12.0), Map.entry(HorizonsModifier.INTEGRITY,  45.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.5), Map.entry(HorizonsModifier.BOOT_TIME,  6.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  4.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  3300.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME, Double.POSITIVE_INFINITY), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.DRONE_MULTI_TARGET_SPEED,  60.0)));
    public static final MultiLimpetController MINING_MULTI_LIMPET_CONTROLLER_3_C = new MultiLimpetController("MINING_MULTI_LIMPET_CONTROLLER_3_C", HorizonsBlueprintName.MINING_MULTI_LIMPET_CONTROLLER, ModuleSize.SIZE_3, ModuleClass.C, true, 50000, "Int_MultiDroneControl_Mining_Size3_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  10.0), Map.entry(HorizonsModifier.INTEGRITY,  68.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.35), Map.entry(HorizonsModifier.BOOT_TIME,  6.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  4.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  5000.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME, Double.POSITIVE_INFINITY), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.DRONE_MULTI_TARGET_SPEED,  60.0)));
    public static final MultiLimpetController OPERATIONS_LIMPET_CONTROLLER_3_C = new MultiLimpetController("OPERATIONS_LIMPET_CONTROLLER_3_C", HorizonsBlueprintName.OPERATIONS_LIMPET_CONTROLLER, ModuleSize.SIZE_3, ModuleClass.C, true, 50000, "Int_MultiDroneControl_Operations_Size3_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  10.0), Map.entry(HorizonsModifier.INTEGRITY,  68.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.35), Map.entry(HorizonsModifier.BOOT_TIME,  6.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  4.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  2600.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME,  510.0), Map.entry(HorizonsModifier.DRONE_SPEED,  500.0), Map.entry(HorizonsModifier.DRONE_MULTI_TARGET_SPEED,  60.0), Map.entry(HorizonsModifier.DRONE_HACKING_TIME,  16.0), Map.entry(HorizonsModifier.DRONE_MIN_JETTISONED_CARGO,  3.0), Map.entry(HorizonsModifier.DRONE_MAX_JETTISONED_CARGO,  8.0)));
    public static final MultiLimpetController OPERATIONS_LIMPET_CONTROLLER_3_B = new MultiLimpetController("OPERATIONS_LIMPET_CONTROLLER_3_B", HorizonsBlueprintName.OPERATIONS_LIMPET_CONTROLLER, ModuleSize.SIZE_3, ModuleClass.B, true, 80000, "Int_MultiDroneControl_Operations_Size3_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  15.0), Map.entry(HorizonsModifier.INTEGRITY,  80.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.3), Map.entry(HorizonsModifier.BOOT_TIME,  6.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  4.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  3100.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME,  420.0), Map.entry(HorizonsModifier.DRONE_SPEED,  500.0), Map.entry(HorizonsModifier.DRONE_MULTI_TARGET_SPEED,  60.0), Map.entry(HorizonsModifier.DRONE_HACKING_TIME,  22.0), Map.entry(HorizonsModifier.DRONE_MIN_JETTISONED_CARGO,  4.0), Map.entry(HorizonsModifier.DRONE_MAX_JETTISONED_CARGO,  9.0)));
    public static final MultiLimpetController RESCUE_LIMPET_CONTROLLER_3_D = new MultiLimpetController("RESCUE_LIMPET_CONTROLLER_3_D", HorizonsBlueprintName.RESCUE_LIMPET_CONTROLLER, ModuleSize.SIZE_3, ModuleClass.D, true, 30000, "Int_MultiDroneControl_Rescue_Size3_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  8.0), Map.entry(HorizonsModifier.INTEGRITY,  58.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.4), Map.entry(HorizonsModifier.BOOT_TIME,  6.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  4.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  2100.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME,  300.0), Map.entry(HorizonsModifier.DRONE_SPEED,  500.0), Map.entry(HorizonsModifier.DRONE_FUEL_CAPACITY,  1.0), Map.entry(HorizonsModifier.DRONE_REPAIR_CAPACITY,  60.0), Map.entry(HorizonsModifier.DRONE_HACKING_TIME,  19.0), Map.entry(HorizonsModifier.DRONE_MIN_JETTISONED_CARGO,  2.0), Map.entry(HorizonsModifier.DRONE_MAX_JETTISONED_CARGO,  7.0)));
    public static final MultiLimpetController RESCUE_LIMPET_CONTROLLER_3_C = new MultiLimpetController("RESCUE_LIMPET_CONTROLLER_3_C", HorizonsBlueprintName.RESCUE_LIMPET_CONTROLLER, ModuleSize.SIZE_3, ModuleClass.C, true, 50000, "Int_MultiDroneControl_Rescue_Size3_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  10.0), Map.entry(HorizonsModifier.INTEGRITY,  68.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.35), Map.entry(HorizonsModifier.BOOT_TIME,  6.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  4.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  2600.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME,  300.0), Map.entry(HorizonsModifier.DRONE_SPEED,  500.0), Map.entry(HorizonsModifier.DRONE_FUEL_CAPACITY,  1.0), Map.entry(HorizonsModifier.DRONE_REPAIR_CAPACITY,  60.0), Map.entry(HorizonsModifier.DRONE_HACKING_TIME,  16.0), Map.entry(HorizonsModifier.DRONE_MIN_JETTISONED_CARGO,  3.0), Map.entry(HorizonsModifier.DRONE_MAX_JETTISONED_CARGO,  8.0)));
    public static final MultiLimpetController XENO_LIMPET_CONTROLLER_3_C = new MultiLimpetController("XENO_LIMPET_CONTROLLER_3_C", HorizonsBlueprintName.XENO_LIMPET_CONTROLLER, ModuleSize.SIZE_3, ModuleClass.C, true, 50000, "Int_MultiDroneControl_Xeno_Size3_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  10.0), Map.entry(HorizonsModifier.INTEGRITY,  68.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.35), Map.entry(HorizonsModifier.BOOT_TIME,  6.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  4.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  5000.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME,  300.0), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.DRONE_REPAIR_CAPACITY,  70.0)));
    public static final MultiLimpetController XENO_LIMPET_CONTROLLER_3_B = new MultiLimpetController("XENO_LIMPET_CONTROLLER_3_B", HorizonsBlueprintName.XENO_LIMPET_CONTROLLER, ModuleSize.SIZE_3, ModuleClass.B, true, 80000, "Int_MultiDroneControl_Xeno_Size3_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  15.0), Map.entry(HorizonsModifier.INTEGRITY,  80.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.3), Map.entry(HorizonsModifier.BOOT_TIME,  6.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  4.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  5000.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME,  300.0), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.DRONE_REPAIR_CAPACITY,  70.0)));
    public static final MultiLimpetController UNIVERSAL_MULTI_LIMPET_CONTROLLER_7_C = new MultiLimpetController("UNIVERSAL_MULTI_LIMPET_CONTROLLER_7_C", HorizonsBlueprintName.UNIVERSAL_MULTI_LIMPET_CONTROLLER, ModuleSize.SIZE_7, ModuleClass.C, true, 4000000, "Int_MultiDroneControl_Universal_Size7_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  125.0), Map.entry(HorizonsModifier.INTEGRITY,  150.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.8), Map.entry(HorizonsModifier.BOOT_TIME,  6.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  8.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  6500.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME, Double.POSITIVE_INFINITY), Map.entry(HorizonsModifier.DRONE_SPEED,  500.0), Map.entry(HorizonsModifier.DRONE_MULTI_TARGET_SPEED,  60.0), Map.entry(HorizonsModifier.DRONE_FUEL_CAPACITY,  1.0), Map.entry(HorizonsModifier.DRONE_REPAIR_CAPACITY,  310.0), Map.entry(HorizonsModifier.DRONE_HACKING_TIME,  8.0), Map.entry(HorizonsModifier.DRONE_MIN_JETTISONED_CARGO,  3.0), Map.entry(HorizonsModifier.DRONE_MAX_JETTISONED_CARGO,  8.0)));
    public static final MultiLimpetController UNIVERSAL_MULTI_LIMPET_CONTROLLER_7_A = new MultiLimpetController("UNIVERSAL_MULTI_LIMPET_CONTROLLER_7_A", HorizonsBlueprintName.UNIVERSAL_MULTI_LIMPET_CONTROLLER, ModuleSize.SIZE_7, ModuleClass.A, true, 8000000, "Int_MultiDroneControl_Universal_Size7_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  140.0), Map.entry(HorizonsModifier.INTEGRITY,  200.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.1), Map.entry(HorizonsModifier.BOOT_TIME,  6.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  8.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  9100.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME, Double.POSITIVE_INFINITY), Map.entry(HorizonsModifier.DRONE_SPEED,  500.0), Map.entry(HorizonsModifier.DRONE_MULTI_TARGET_SPEED,  60.0), Map.entry(HorizonsModifier.DRONE_FUEL_CAPACITY,  1.0), Map.entry(HorizonsModifier.DRONE_REPAIR_CAPACITY,  310.0), Map.entry(HorizonsModifier.DRONE_HACKING_TIME,  5.0), Map.entry(HorizonsModifier.DRONE_MIN_JETTISONED_CARGO,  5.0), Map.entry(HorizonsModifier.DRONE_MAX_JETTISONED_CARGO,  10.0)));

    public static final List<MultiLimpetController> MULTI_LIMPET_CONTROLLERS = List.of(
            MINING_MULTI_LIMPET_CONTROLLER_3_E,
            MINING_MULTI_LIMPET_CONTROLLER_3_C,
            OPERATIONS_LIMPET_CONTROLLER_3_C,
            OPERATIONS_LIMPET_CONTROLLER_3_B,
            RESCUE_LIMPET_CONTROLLER_3_D,
            RESCUE_LIMPET_CONTROLLER_3_C,
            XENO_LIMPET_CONTROLLER_3_C,
            XENO_LIMPET_CONTROLLER_3_B,
            UNIVERSAL_MULTI_LIMPET_CONTROLLER_7_C,
            UNIVERSAL_MULTI_LIMPET_CONTROLLER_7_A
    );
    public MultiLimpetController(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, basePrice, internalName, attributes);
    }

    public MultiLimpetController(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, basePrice, internalName, attributes);
    }

    public MultiLimpetController(MultiLimpetController multiLimpetController) {
        super(multiLimpetController);
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
    public MultiLimpetController Clone() {
        return new MultiLimpetController(this);
    }
    @Override
    public String getNonSortingClarifier() {
        return " " + LocaleService.getLocalizedStringForCurrentLocale(getName().getLocalizationKey());
    }
    @Override
    public boolean groupOnName(){
        return true;
    }
}
