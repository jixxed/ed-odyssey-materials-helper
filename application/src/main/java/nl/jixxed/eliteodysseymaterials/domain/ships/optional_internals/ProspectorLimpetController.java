package nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals;

import nl.jixxed.eliteodysseymaterials.constants.horizons.optional_internals.ProspectorLimpetControllerBlueprints;
import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleClass;
import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleSize;
import nl.jixxed.eliteodysseymaterials.domain.ships.OptionalModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.Origin;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ProspectorLimpetController extends OptionalModule {
    public static final ProspectorLimpetController PROSPECTOR_LIMPET_CONTROLLER_1_E = new ProspectorLimpetController("PROSPECTOR_LIMPET_CONTROLLER_1_E", HorizonsBlueprintName.PROSPECTOR_LIMPET_CONTROLLER, ModuleSize.SIZE_1, ModuleClass.E, false, 600, "Int_DroneControl_Prospector_Size1_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  24.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.18), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  1.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  3000.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME, Double.POSITIVE_INFINITY), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.MINE_BONUS,  1.0)));
    public static final ProspectorLimpetController PROSPECTOR_LIMPET_CONTROLLER_1_D = new ProspectorLimpetController("PROSPECTOR_LIMPET_CONTROLLER_1_D", HorizonsBlueprintName.PROSPECTOR_LIMPET_CONTROLLER, ModuleSize.SIZE_1, ModuleClass.D, false, 1200, "Int_DroneControl_Prospector_Size1_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  0.5), Map.entry(HorizonsModifier.INTEGRITY,  32.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.14), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  1.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  4000.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME, Double.POSITIVE_INFINITY), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.MINE_BONUS,  2.0)));
    public static final ProspectorLimpetController PROSPECTOR_LIMPET_CONTROLLER_1_C = new ProspectorLimpetController("PROSPECTOR_LIMPET_CONTROLLER_1_C", HorizonsBlueprintName.PROSPECTOR_LIMPET_CONTROLLER, ModuleSize.SIZE_1, ModuleClass.C, false, 2400, "Int_DroneControl_Prospector_Size1_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  40.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.23), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  1.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  5000.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME, Double.POSITIVE_INFINITY), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.MINE_BONUS,  2.5)));
    public static final ProspectorLimpetController PROSPECTOR_LIMPET_CONTROLLER_1_B = new ProspectorLimpetController("PROSPECTOR_LIMPET_CONTROLLER_1_B", HorizonsBlueprintName.PROSPECTOR_LIMPET_CONTROLLER, ModuleSize.SIZE_1, ModuleClass.B, false, 4800, "Int_DroneControl_Prospector_Size1_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.0), Map.entry(HorizonsModifier.INTEGRITY,  48.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.32), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  1.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  6000.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME, Double.POSITIVE_INFINITY), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.MINE_BONUS,  3.0)));
    public static final ProspectorLimpetController PROSPECTOR_LIMPET_CONTROLLER_1_A = new ProspectorLimpetController("PROSPECTOR_LIMPET_CONTROLLER_1_A", HorizonsBlueprintName.PROSPECTOR_LIMPET_CONTROLLER, ModuleSize.SIZE_1, ModuleClass.A, false, 9600, "Int_DroneControl_Prospector_Size1_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  56.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.28), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  1.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  7000.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME, Double.POSITIVE_INFINITY), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.MINE_BONUS,  3.5)));
    public static final ProspectorLimpetController PROSPECTOR_LIMPET_CONTROLLER_3_E = new ProspectorLimpetController("PROSPECTOR_LIMPET_CONTROLLER_3_E", HorizonsBlueprintName.PROSPECTOR_LIMPET_CONTROLLER, ModuleSize.SIZE_3, ModuleClass.E, false, 5400, "Int_DroneControl_Prospector_Size3_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  5.0), Map.entry(HorizonsModifier.INTEGRITY,  38.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.27), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  2.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  3300.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME, Double.POSITIVE_INFINITY), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.MINE_BONUS,  1.0)));
    public static final ProspectorLimpetController PROSPECTOR_LIMPET_CONTROLLER_3_D = new ProspectorLimpetController("PROSPECTOR_LIMPET_CONTROLLER_3_D", HorizonsBlueprintName.PROSPECTOR_LIMPET_CONTROLLER, ModuleSize.SIZE_3, ModuleClass.D, false, 10800, "Int_DroneControl_Prospector_Size3_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.0), Map.entry(HorizonsModifier.INTEGRITY,  51.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.2), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  2.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  4400.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME, Double.POSITIVE_INFINITY), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.MINE_BONUS,  2.0)));
    public static final ProspectorLimpetController PROSPECTOR_LIMPET_CONTROLLER_3_C = new ProspectorLimpetController("PROSPECTOR_LIMPET_CONTROLLER_3_C", HorizonsBlueprintName.PROSPECTOR_LIMPET_CONTROLLER, ModuleSize.SIZE_3, ModuleClass.C, false, 21600, "Int_DroneControl_Prospector_Size3_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  5.0), Map.entry(HorizonsModifier.INTEGRITY,  64.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.34), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  2.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  5500.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME, Double.POSITIVE_INFINITY), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.MINE_BONUS,  2.5)));
    public static final ProspectorLimpetController PROSPECTOR_LIMPET_CONTROLLER_3_B = new ProspectorLimpetController("PROSPECTOR_LIMPET_CONTROLLER_3_B", HorizonsBlueprintName.PROSPECTOR_LIMPET_CONTROLLER, ModuleSize.SIZE_3, ModuleClass.B, false, 43200, "Int_DroneControl_Prospector_Size3_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  8.0), Map.entry(HorizonsModifier.INTEGRITY,  77.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.48), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  2.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  6600.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME, Double.POSITIVE_INFINITY), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.MINE_BONUS,  3.0)));
    public static final ProspectorLimpetController PROSPECTOR_LIMPET_CONTROLLER_3_A = new ProspectorLimpetController("PROSPECTOR_LIMPET_CONTROLLER_3_A", HorizonsBlueprintName.PROSPECTOR_LIMPET_CONTROLLER, ModuleSize.SIZE_3, ModuleClass.A, false, 86400, "Int_DroneControl_Prospector_Size3_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  5.0), Map.entry(HorizonsModifier.INTEGRITY,  90.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.41), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  2.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  7700.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME, Double.POSITIVE_INFINITY), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.MINE_BONUS,  3.5)));
    public static final ProspectorLimpetController PROSPECTOR_LIMPET_CONTROLLER_5_E = new ProspectorLimpetController("PROSPECTOR_LIMPET_CONTROLLER_5_E", HorizonsBlueprintName.PROSPECTOR_LIMPET_CONTROLLER, ModuleSize.SIZE_5, ModuleClass.E, false, 48600, "Int_DroneControl_Prospector_Size5_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  20.0), Map.entry(HorizonsModifier.INTEGRITY,  58.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.4), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  4.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  3900.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME, Double.POSITIVE_INFINITY), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.MINE_BONUS,  1.0)));
    public static final ProspectorLimpetController PROSPECTOR_LIMPET_CONTROLLER_5_D = new ProspectorLimpetController("PROSPECTOR_LIMPET_CONTROLLER_5_D", HorizonsBlueprintName.PROSPECTOR_LIMPET_CONTROLLER, ModuleSize.SIZE_5, ModuleClass.D, false, 97200, "Int_DroneControl_Prospector_Size5_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  8.0), Map.entry(HorizonsModifier.INTEGRITY,  77.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.3), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  4.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  5200.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME, Double.POSITIVE_INFINITY), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.MINE_BONUS,  2.0)));
    public static final ProspectorLimpetController PROSPECTOR_LIMPET_CONTROLLER_5_C = new ProspectorLimpetController("PROSPECTOR_LIMPET_CONTROLLER_5_C", HorizonsBlueprintName.PROSPECTOR_LIMPET_CONTROLLER, ModuleSize.SIZE_5, ModuleClass.C, false, 194400, "Int_DroneControl_Prospector_Size5_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  20.0), Map.entry(HorizonsModifier.INTEGRITY,  96.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.5), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  4.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  6500.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME, Double.POSITIVE_INFINITY), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.MINE_BONUS,  2.5)));
    public static final ProspectorLimpetController PROSPECTOR_LIMPET_CONTROLLER_5_B = new ProspectorLimpetController("PROSPECTOR_LIMPET_CONTROLLER_5_B", HorizonsBlueprintName.PROSPECTOR_LIMPET_CONTROLLER, ModuleSize.SIZE_5, ModuleClass.B, false, 388800, "Int_DroneControl_Prospector_Size5_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  32.0), Map.entry(HorizonsModifier.INTEGRITY,  115.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.72), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  4.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  7800.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME, Double.POSITIVE_INFINITY), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.MINE_BONUS,  3.0)));
    public static final ProspectorLimpetController PROSPECTOR_LIMPET_CONTROLLER_5_A = new ProspectorLimpetController("PROSPECTOR_LIMPET_CONTROLLER_5_A", HorizonsBlueprintName.PROSPECTOR_LIMPET_CONTROLLER, ModuleSize.SIZE_5, ModuleClass.A, false, 777600, "Int_DroneControl_Prospector_Size5_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  20.0), Map.entry(HorizonsModifier.INTEGRITY,  134.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.6), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  4.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  9100.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME, Double.POSITIVE_INFINITY), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.MINE_BONUS,  3.5)));
    public static final ProspectorLimpetController PROSPECTOR_LIMPET_CONTROLLER_7_E = new ProspectorLimpetController("PROSPECTOR_LIMPET_CONTROLLER_7_E", HorizonsBlueprintName.PROSPECTOR_LIMPET_CONTROLLER, ModuleSize.SIZE_7, ModuleClass.E, false, 437400, "Int_DroneControl_Prospector_Size7_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  80.0), Map.entry(HorizonsModifier.INTEGRITY,  79.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.55), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  8.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  5100.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME, Double.POSITIVE_INFINITY), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.MINE_BONUS,  1.0)));
    public static final ProspectorLimpetController PROSPECTOR_LIMPET_CONTROLLER_7_D = new ProspectorLimpetController("PROSPECTOR_LIMPET_CONTROLLER_7_D", HorizonsBlueprintName.PROSPECTOR_LIMPET_CONTROLLER, ModuleSize.SIZE_7, ModuleClass.D, false, 874800, "Int_DroneControl_Prospector_Size7_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  32.0), Map.entry(HorizonsModifier.INTEGRITY,  105.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.41), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  8.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  6800.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME, Double.POSITIVE_INFINITY), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.MINE_BONUS,  2.0)));
    public static final ProspectorLimpetController PROSPECTOR_LIMPET_CONTROLLER_7_C = new ProspectorLimpetController("PROSPECTOR_LIMPET_CONTROLLER_7_C", HorizonsBlueprintName.PROSPECTOR_LIMPET_CONTROLLER, ModuleSize.SIZE_7, ModuleClass.C, false, 1749600, "Int_DroneControl_Prospector_Size7_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  80.0), Map.entry(HorizonsModifier.INTEGRITY,  131.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.69), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  8.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  8500.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME, Double.POSITIVE_INFINITY), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.MINE_BONUS,  2.5)));
    public static final ProspectorLimpetController PROSPECTOR_LIMPET_CONTROLLER_7_B = new ProspectorLimpetController("PROSPECTOR_LIMPET_CONTROLLER_7_B", HorizonsBlueprintName.PROSPECTOR_LIMPET_CONTROLLER, ModuleSize.SIZE_7, ModuleClass.B, false, 3499200, "Int_DroneControl_Prospector_Size7_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  128.0), Map.entry(HorizonsModifier.INTEGRITY,  157.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.97), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  8.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  10200.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME, Double.POSITIVE_INFINITY), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.MINE_BONUS,  3.0)));
    public static final ProspectorLimpetController PROSPECTOR_LIMPET_CONTROLLER_7_A = new ProspectorLimpetController("PROSPECTOR_LIMPET_CONTROLLER_7_A", HorizonsBlueprintName.PROSPECTOR_LIMPET_CONTROLLER, ModuleSize.SIZE_7, ModuleClass.A, false, 6998400, "Int_DroneControl_Prospector_Size7_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  80.0), Map.entry(HorizonsModifier.INTEGRITY,  183.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.83), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  8.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  11900.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME, Double.POSITIVE_INFINITY), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0), Map.entry(HorizonsModifier.MINE_BONUS,  3.5)));

    public static final List<ProspectorLimpetController> PROSPECTOR_LIMPET_CONTROLLERS = List.of(
            PROSPECTOR_LIMPET_CONTROLLER_1_E,
            PROSPECTOR_LIMPET_CONTROLLER_1_D,
            PROSPECTOR_LIMPET_CONTROLLER_1_C,
            PROSPECTOR_LIMPET_CONTROLLER_1_B,
            PROSPECTOR_LIMPET_CONTROLLER_1_A,
            PROSPECTOR_LIMPET_CONTROLLER_3_E,
            PROSPECTOR_LIMPET_CONTROLLER_3_D,
            PROSPECTOR_LIMPET_CONTROLLER_3_C,
            PROSPECTOR_LIMPET_CONTROLLER_3_B,
            PROSPECTOR_LIMPET_CONTROLLER_3_A,
            PROSPECTOR_LIMPET_CONTROLLER_5_E,
            PROSPECTOR_LIMPET_CONTROLLER_5_D,
            PROSPECTOR_LIMPET_CONTROLLER_5_C,
            PROSPECTOR_LIMPET_CONTROLLER_5_B,
            PROSPECTOR_LIMPET_CONTROLLER_5_A,
            PROSPECTOR_LIMPET_CONTROLLER_7_E,
            PROSPECTOR_LIMPET_CONTROLLER_7_D,
            PROSPECTOR_LIMPET_CONTROLLER_7_C,
            PROSPECTOR_LIMPET_CONTROLLER_7_B,
            PROSPECTOR_LIMPET_CONTROLLER_7_A
    );
    public ProspectorLimpetController(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, basePrice, internalName, attributes);
    }

    public ProspectorLimpetController(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, basePrice, internalName, attributes);
    }

    public ProspectorLimpetController(ProspectorLimpetController prospectorLimpetController) {
        super(prospectorLimpetController);
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedBlueprints() {
        return ProspectorLimpetControllerBlueprints.BLUEPRINTS.keySet().stream().toList();
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedExperimentalEffects() {
        return Collections.emptyList();
    }

    @Override
    public ProspectorLimpetController Clone() {
        return new ProspectorLimpetController(this);
    }
}