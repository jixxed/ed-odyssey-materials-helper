package nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals;

import nl.jixxed.eliteodysseymaterials.constants.horizons.SynthesisBlueprints;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsSynthesisBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleClass;
import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleSize;
import nl.jixxed.eliteodysseymaterials.domain.ships.OptionalModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.Origin;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RepairLimpetController extends OptionalModule {
    public static final RepairLimpetController REPAIR_LIMPET_CONTROLLER_1_E = new RepairLimpetController("REPAIR_LIMPET_CONTROLLER_1_E", HorizonsBlueprintName.REPAIR_LIMPET_CONTROLLER, ModuleSize.SIZE_1, ModuleClass.E, true, 600, "Int_DroneControl_Repair_Size1_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  24.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.18), Map.entry(HorizonsModifier.BOOT_TIME,  10.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  1.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  600.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME,  300.0), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.DRONE_REPAIR_CAPACITY,  60.0)));
    public static final RepairLimpetController REPAIR_LIMPET_CONTROLLER_1_D = new RepairLimpetController("REPAIR_LIMPET_CONTROLLER_1_D", HorizonsBlueprintName.REPAIR_LIMPET_CONTROLLER, ModuleSize.SIZE_1, ModuleClass.D, true, 1200, "Int_DroneControl_Repair_Size1_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  0.5), Map.entry(HorizonsModifier.INTEGRITY,  32.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.14), Map.entry(HorizonsModifier.BOOT_TIME,  10.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  1.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  800.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME,  300.0), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.DRONE_REPAIR_CAPACITY,  60.0)));
    public static final RepairLimpetController REPAIR_LIMPET_CONTROLLER_1_C = new RepairLimpetController("REPAIR_LIMPET_CONTROLLER_1_C", HorizonsBlueprintName.REPAIR_LIMPET_CONTROLLER, ModuleSize.SIZE_1, ModuleClass.C, true, 2400, "Int_DroneControl_Repair_Size1_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  40.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.23), Map.entry(HorizonsModifier.BOOT_TIME,  10.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  1.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  1000.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME,  300.0), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.DRONE_REPAIR_CAPACITY,  60.0)));
    public static final RepairLimpetController REPAIR_LIMPET_CONTROLLER_1_B = new RepairLimpetController("REPAIR_LIMPET_CONTROLLER_1_B", HorizonsBlueprintName.REPAIR_LIMPET_CONTROLLER, ModuleSize.SIZE_1, ModuleClass.B, true, 4800, "Int_DroneControl_Repair_Size1_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.0), Map.entry(HorizonsModifier.INTEGRITY,  48.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.32), Map.entry(HorizonsModifier.BOOT_TIME,  10.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  1.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  1200.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME,  300.0), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.DRONE_REPAIR_CAPACITY,  60.0)));
    public static final RepairLimpetController REPAIR_LIMPET_CONTROLLER_1_A = new RepairLimpetController("REPAIR_LIMPET_CONTROLLER_1_A", HorizonsBlueprintName.REPAIR_LIMPET_CONTROLLER, ModuleSize.SIZE_1, ModuleClass.A, true, 9600, "Int_DroneControl_Repair_Size1_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  56.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.28), Map.entry(HorizonsModifier.BOOT_TIME,  10.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  1.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  1400.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME,  300.0), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.DRONE_REPAIR_CAPACITY,  60.0)));
    public static final RepairLimpetController REPAIR_LIMPET_CONTROLLER_3_E = new RepairLimpetController("REPAIR_LIMPET_CONTROLLER_3_E", HorizonsBlueprintName.REPAIR_LIMPET_CONTROLLER, ModuleSize.SIZE_3, ModuleClass.E, true, 5400, "Int_DroneControl_Repair_Size3_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  5.0), Map.entry(HorizonsModifier.INTEGRITY,  38.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.27), Map.entry(HorizonsModifier.BOOT_TIME,  10.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  2.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  660.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME,  300.0), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.DRONE_REPAIR_CAPACITY,  180.0)));
    public static final RepairLimpetController REPAIR_LIMPET_CONTROLLER_3_D = new RepairLimpetController("REPAIR_LIMPET_CONTROLLER_3_D", HorizonsBlueprintName.REPAIR_LIMPET_CONTROLLER, ModuleSize.SIZE_3, ModuleClass.D, true, 10800, "Int_DroneControl_Repair_Size3_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.0), Map.entry(HorizonsModifier.INTEGRITY,  51.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.2), Map.entry(HorizonsModifier.BOOT_TIME,  10.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  2.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  880.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME,  300.0), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.DRONE_REPAIR_CAPACITY,  180.0)));
    public static final RepairLimpetController REPAIR_LIMPET_CONTROLLER_3_C = new RepairLimpetController("REPAIR_LIMPET_CONTROLLER_3_C", HorizonsBlueprintName.REPAIR_LIMPET_CONTROLLER, ModuleSize.SIZE_3, ModuleClass.C, true, 21600, "Int_DroneControl_Repair_Size3_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  5.0), Map.entry(HorizonsModifier.INTEGRITY,  64.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.34), Map.entry(HorizonsModifier.BOOT_TIME,  10.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  2.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  1100.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME,  300.0), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.DRONE_REPAIR_CAPACITY,  180.0)));
    public static final RepairLimpetController REPAIR_LIMPET_CONTROLLER_3_B = new RepairLimpetController("REPAIR_LIMPET_CONTROLLER_3_B", HorizonsBlueprintName.REPAIR_LIMPET_CONTROLLER, ModuleSize.SIZE_3, ModuleClass.B, true, 43200, "Int_DroneControl_Repair_Size3_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  8.0), Map.entry(HorizonsModifier.INTEGRITY,  77.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.48), Map.entry(HorizonsModifier.BOOT_TIME,  10.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  2.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  1320.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME,  300.0), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.DRONE_REPAIR_CAPACITY,  180.0)));
    public static final RepairLimpetController REPAIR_LIMPET_CONTROLLER_3_A = new RepairLimpetController("REPAIR_LIMPET_CONTROLLER_3_A", HorizonsBlueprintName.REPAIR_LIMPET_CONTROLLER, ModuleSize.SIZE_3, ModuleClass.A, true, 86400, "Int_DroneControl_Repair_Size3_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  5.0), Map.entry(HorizonsModifier.INTEGRITY,  90.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.41), Map.entry(HorizonsModifier.BOOT_TIME,  10.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  2.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  1540.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME,  300.0), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.DRONE_REPAIR_CAPACITY,  180.0)));
    public static final RepairLimpetController REPAIR_LIMPET_CONTROLLER_5_E = new RepairLimpetController("REPAIR_LIMPET_CONTROLLER_5_E", HorizonsBlueprintName.REPAIR_LIMPET_CONTROLLER, ModuleSize.SIZE_5, ModuleClass.E, true, 48600, "Int_DroneControl_Repair_Size5_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  20.0), Map.entry(HorizonsModifier.INTEGRITY,  58.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.4), Map.entry(HorizonsModifier.BOOT_TIME,  10.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  3.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  780.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME,  300.0), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.DRONE_REPAIR_CAPACITY,  310.0)));
    public static final RepairLimpetController REPAIR_LIMPET_CONTROLLER_5_D = new RepairLimpetController("REPAIR_LIMPET_CONTROLLER_5_D", HorizonsBlueprintName.REPAIR_LIMPET_CONTROLLER, ModuleSize.SIZE_5, ModuleClass.D, true, 97200, "Int_DroneControl_Repair_Size5_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  8.0), Map.entry(HorizonsModifier.INTEGRITY,  77.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.3), Map.entry(HorizonsModifier.BOOT_TIME,  10.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  3.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  1040.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME,  300.0), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.DRONE_REPAIR_CAPACITY,  310.0)));
    public static final RepairLimpetController REPAIR_LIMPET_CONTROLLER_5_C = new RepairLimpetController("REPAIR_LIMPET_CONTROLLER_5_C", HorizonsBlueprintName.REPAIR_LIMPET_CONTROLLER, ModuleSize.SIZE_5, ModuleClass.C, true, 194400, "Int_DroneControl_Repair_Size5_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  20.0), Map.entry(HorizonsModifier.INTEGRITY,  96.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.5), Map.entry(HorizonsModifier.BOOT_TIME,  10.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  3.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  1300.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME,  300.0), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.DRONE_REPAIR_CAPACITY,  310.0)));
    public static final RepairLimpetController REPAIR_LIMPET_CONTROLLER_5_B = new RepairLimpetController("REPAIR_LIMPET_CONTROLLER_5_B", HorizonsBlueprintName.REPAIR_LIMPET_CONTROLLER, ModuleSize.SIZE_5, ModuleClass.B, true, 388800, "Int_DroneControl_Repair_Size5_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  32.0), Map.entry(HorizonsModifier.INTEGRITY,  115.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.72), Map.entry(HorizonsModifier.BOOT_TIME,  10.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  3.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  1560.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME,  300.0), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.DRONE_REPAIR_CAPACITY,  310.0)));
    public static final RepairLimpetController REPAIR_LIMPET_CONTROLLER_5_A = new RepairLimpetController("REPAIR_LIMPET_CONTROLLER_5_A", HorizonsBlueprintName.REPAIR_LIMPET_CONTROLLER, ModuleSize.SIZE_5, ModuleClass.A, true, 777600, "Int_DroneControl_Repair_Size5_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  20.0), Map.entry(HorizonsModifier.INTEGRITY,  134.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.6), Map.entry(HorizonsModifier.BOOT_TIME,  10.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  3.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  1820.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME,  300.0), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.DRONE_REPAIR_CAPACITY,  310.0)));
    public static final RepairLimpetController REPAIR_LIMPET_CONTROLLER_7_E = new RepairLimpetController("REPAIR_LIMPET_CONTROLLER_7_E", HorizonsBlueprintName.REPAIR_LIMPET_CONTROLLER, ModuleSize.SIZE_7, ModuleClass.E, true, 437400, "Int_DroneControl_Repair_Size7_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  80.0), Map.entry(HorizonsModifier.INTEGRITY,  79.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.55), Map.entry(HorizonsModifier.BOOT_TIME,  10.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  4.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  1020.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME,  300.0), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.DRONE_REPAIR_CAPACITY,  450.0)));
    public static final RepairLimpetController REPAIR_LIMPET_CONTROLLER_7_D = new RepairLimpetController("REPAIR_LIMPET_CONTROLLER_7_D", HorizonsBlueprintName.REPAIR_LIMPET_CONTROLLER, ModuleSize.SIZE_7, ModuleClass.D, true, 874800, "Int_DroneControl_Repair_Size7_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  32.0), Map.entry(HorizonsModifier.INTEGRITY,  105.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.41), Map.entry(HorizonsModifier.BOOT_TIME,  10.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  4.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  1360.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME,  300.0), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.DRONE_REPAIR_CAPACITY,  450.0)));
    public static final RepairLimpetController REPAIR_LIMPET_CONTROLLER_7_C = new RepairLimpetController("REPAIR_LIMPET_CONTROLLER_7_C", HorizonsBlueprintName.REPAIR_LIMPET_CONTROLLER, ModuleSize.SIZE_7, ModuleClass.C, true, 1749600, "Int_DroneControl_Repair_Size7_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  80.0), Map.entry(HorizonsModifier.INTEGRITY,  131.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.69), Map.entry(HorizonsModifier.BOOT_TIME,  10.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  4.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  1700.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME,  300.0), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.DRONE_REPAIR_CAPACITY,  450.0)));
    public static final RepairLimpetController REPAIR_LIMPET_CONTROLLER_7_B = new RepairLimpetController("REPAIR_LIMPET_CONTROLLER_7_B", HorizonsBlueprintName.REPAIR_LIMPET_CONTROLLER, ModuleSize.SIZE_7, ModuleClass.B, true, 3499200, "Int_DroneControl_Repair_Size7_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  128.0), Map.entry(HorizonsModifier.INTEGRITY,  157.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.97), Map.entry(HorizonsModifier.BOOT_TIME,  10.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  4.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  2040.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME,  300.0), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.DRONE_REPAIR_CAPACITY,  450.0)));
    public static final RepairLimpetController REPAIR_LIMPET_CONTROLLER_7_A = new RepairLimpetController("REPAIR_LIMPET_CONTROLLER_7_A", HorizonsBlueprintName.REPAIR_LIMPET_CONTROLLER, ModuleSize.SIZE_7, ModuleClass.A, true, 6998400, "Int_DroneControl_Repair_Size7_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  80.0), Map.entry(HorizonsModifier.INTEGRITY,  183.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.83), Map.entry(HorizonsModifier.BOOT_TIME,  10.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  4.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  2380.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME,  300.0), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.DRONE_REPAIR_CAPACITY,  450.0)));

    public static final List<RepairLimpetController> REPAIR_LIMPET_CONTROLLERS = List.of(
            REPAIR_LIMPET_CONTROLLER_1_E,
            REPAIR_LIMPET_CONTROLLER_1_D,
            REPAIR_LIMPET_CONTROLLER_1_C,
            REPAIR_LIMPET_CONTROLLER_1_B,
            REPAIR_LIMPET_CONTROLLER_1_A,
            REPAIR_LIMPET_CONTROLLER_3_E,
            REPAIR_LIMPET_CONTROLLER_3_D,
            REPAIR_LIMPET_CONTROLLER_3_C,
            REPAIR_LIMPET_CONTROLLER_3_B,
            REPAIR_LIMPET_CONTROLLER_3_A,
            REPAIR_LIMPET_CONTROLLER_5_E,
            REPAIR_LIMPET_CONTROLLER_5_D,
            REPAIR_LIMPET_CONTROLLER_5_C,
            REPAIR_LIMPET_CONTROLLER_5_B,
            REPAIR_LIMPET_CONTROLLER_5_A,
            REPAIR_LIMPET_CONTROLLER_7_E,
            REPAIR_LIMPET_CONTROLLER_7_D,
            REPAIR_LIMPET_CONTROLLER_7_C,
            REPAIR_LIMPET_CONTROLLER_7_B,
            REPAIR_LIMPET_CONTROLLER_7_A
    );
    public RepairLimpetController(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, basePrice, internalName, attributes);
    }

    public RepairLimpetController(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, basePrice, internalName, attributes);
    }

    public RepairLimpetController(RepairLimpetController repairLimpetController) {
        super(repairLimpetController);
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
    public RepairLimpetController Clone() {
        return new RepairLimpetController(this);
    }

    @Override
    public Collection<HorizonsSynthesisBlueprint> synthesisBlueprints() {
        return SynthesisBlueprints.LIMPETS.values();
    }
}
