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
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MultiLimpetController extends OptionalModule {
    public static final MultiLimpetController MINING_MULTI_LIMPET_CONTROLLER_3_E = new MultiLimpetController("MINING_MULTI_LIMPET_CONTROLLER_3_E", HorizonsBlueprintName.MINING_MULTI_LIMPET_CONTROLLER, ModuleSize.SIZE_3, ModuleClass.E, true, 15000, "Int_MultiDroneControl_Mining_Size3_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 12.0), Map.entry(HorizonsModifier.INTEGRITY, 45.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.5), Map.entry(HorizonsModifier.BOOT_TIME, 6.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS, 4.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE, 3300.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME, Double.POSITIVE_INFINITY), Map.entry(HorizonsModifier.DRONE_SPEED, 200.0), Map.entry(HorizonsModifier.DRONE_MULTI_TARGET_SPEED, 60.0)));
    public static final MultiLimpetController MINING_MULTI_LIMPET_CONTROLLER_3_C = new MultiLimpetController("MINING_MULTI_LIMPET_CONTROLLER_3_C", HorizonsBlueprintName.MINING_MULTI_LIMPET_CONTROLLER, ModuleSize.SIZE_3, ModuleClass.C, true, 50000, "Int_MultiDroneControl_Mining_Size3_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 10.0), Map.entry(HorizonsModifier.INTEGRITY, 68.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.35), Map.entry(HorizonsModifier.BOOT_TIME, 6.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS, 4.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE, 5000.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME, Double.POSITIVE_INFINITY), Map.entry(HorizonsModifier.DRONE_SPEED, 200.0), Map.entry(HorizonsModifier.DRONE_MULTI_TARGET_SPEED, 60.0)));
    public static final MultiLimpetController OPERATIONS_LIMPET_CONTROLLER_3_C = new MultiLimpetController("OPERATIONS_LIMPET_CONTROLLER_3_C", HorizonsBlueprintName.OPERATIONS_LIMPET_CONTROLLER, ModuleSize.SIZE_3, ModuleClass.C, true, 50000, "Int_MultiDroneControl_Operations_Size3_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 10.0), Map.entry(HorizonsModifier.INTEGRITY, 68.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.35), Map.entry(HorizonsModifier.BOOT_TIME, 6.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS, 4.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE, 2600.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME, 510.0), Map.entry(HorizonsModifier.DRONE_SPEED, 500.0), Map.entry(HorizonsModifier.DRONE_MULTI_TARGET_SPEED, 60.0), Map.entry(HorizonsModifier.DRONE_HACKING_TIME, 16.0), Map.entry(HorizonsModifier.DRONE_MIN_JETTISONED_CARGO, 3.0), Map.entry(HorizonsModifier.DRONE_MAX_JETTISONED_CARGO, 8.0)));
    public static final MultiLimpetController OPERATIONS_LIMPET_CONTROLLER_3_B = new MultiLimpetController("OPERATIONS_LIMPET_CONTROLLER_3_B", HorizonsBlueprintName.OPERATIONS_LIMPET_CONTROLLER, ModuleSize.SIZE_3, ModuleClass.B, true, 80000, "Int_MultiDroneControl_Operations_Size3_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 15.0), Map.entry(HorizonsModifier.INTEGRITY, 80.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.3), Map.entry(HorizonsModifier.BOOT_TIME, 6.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS, 4.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE, 3100.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME, 420.0), Map.entry(HorizonsModifier.DRONE_SPEED, 500.0), Map.entry(HorizonsModifier.DRONE_MULTI_TARGET_SPEED, 60.0), Map.entry(HorizonsModifier.DRONE_HACKING_TIME, 22.0), Map.entry(HorizonsModifier.DRONE_MIN_JETTISONED_CARGO, 4.0), Map.entry(HorizonsModifier.DRONE_MAX_JETTISONED_CARGO, 9.0)));
    public static final MultiLimpetController RESCUE_LIMPET_CONTROLLER_3_D = new MultiLimpetController("RESCUE_LIMPET_CONTROLLER_3_D", HorizonsBlueprintName.RESCUE_LIMPET_CONTROLLER, ModuleSize.SIZE_3, ModuleClass.D, true, 30000, "Int_MultiDroneControl_Rescue_Size3_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 8.0), Map.entry(HorizonsModifier.INTEGRITY, 58.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.4), Map.entry(HorizonsModifier.BOOT_TIME, 6.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS, 4.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE, 2100.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME, 300.0), Map.entry(HorizonsModifier.DRONE_SPEED, 500.0), Map.entry(HorizonsModifier.DRONE_FUEL_CAPACITY, 1.0), Map.entry(HorizonsModifier.DRONE_REPAIR_CAPACITY, 60.0), Map.entry(HorizonsModifier.DRONE_HACKING_TIME, 19.0), Map.entry(HorizonsModifier.DRONE_MIN_JETTISONED_CARGO, 2.0), Map.entry(HorizonsModifier.DRONE_MAX_JETTISONED_CARGO, 7.0)));
    public static final MultiLimpetController RESCUE_LIMPET_CONTROLLER_3_C = new MultiLimpetController("RESCUE_LIMPET_CONTROLLER_3_C", HorizonsBlueprintName.RESCUE_LIMPET_CONTROLLER, ModuleSize.SIZE_3, ModuleClass.C, true, 50000, "Int_MultiDroneControl_Rescue_Size3_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 10.0), Map.entry(HorizonsModifier.INTEGRITY, 68.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.35), Map.entry(HorizonsModifier.BOOT_TIME, 6.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS, 4.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE, 2600.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME, 300.0), Map.entry(HorizonsModifier.DRONE_SPEED, 500.0), Map.entry(HorizonsModifier.DRONE_FUEL_CAPACITY, 1.0), Map.entry(HorizonsModifier.DRONE_REPAIR_CAPACITY, 60.0), Map.entry(HorizonsModifier.DRONE_HACKING_TIME, 16.0), Map.entry(HorizonsModifier.DRONE_MIN_JETTISONED_CARGO, 3.0), Map.entry(HorizonsModifier.DRONE_MAX_JETTISONED_CARGO, 8.0)));
    public static final MultiLimpetController XENO_LIMPET_CONTROLLER_3_C = new MultiLimpetController("XENO_LIMPET_CONTROLLER_3_C", HorizonsBlueprintName.XENO_LIMPET_CONTROLLER, ModuleSize.SIZE_3, ModuleClass.C, true, 50000, "Int_MultiDroneControl_Xeno_Size3_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 10.0), Map.entry(HorizonsModifier.INTEGRITY, 68.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.35), Map.entry(HorizonsModifier.BOOT_TIME, 6.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS, 4.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE, 5000.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME, 300.0), Map.entry(HorizonsModifier.DRONE_SPEED, 200.0), Map.entry(HorizonsModifier.DRONE_REPAIR_CAPACITY, 70.0)));
    public static final MultiLimpetController XENO_LIMPET_CONTROLLER_3_B = new MultiLimpetController("XENO_LIMPET_CONTROLLER_3_B", HorizonsBlueprintName.XENO_LIMPET_CONTROLLER, ModuleSize.SIZE_3, ModuleClass.B, true, 80000, "Int_MultiDroneControl_Xeno_Size3_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 15.0), Map.entry(HorizonsModifier.INTEGRITY, 80.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.3), Map.entry(HorizonsModifier.BOOT_TIME, 6.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS, 4.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE, 5000.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME, 300.0), Map.entry(HorizonsModifier.DRONE_SPEED, 200.0), Map.entry(HorizonsModifier.DRONE_REPAIR_CAPACITY, 70.0)));
    public static final MultiLimpetController UNIVERSAL_MULTI_LIMPET_CONTROLLER_7_C = new MultiLimpetController("UNIVERSAL_MULTI_LIMPET_CONTROLLER_7_C", HorizonsBlueprintName.UNIVERSAL_MULTI_LIMPET_CONTROLLER, ModuleSize.SIZE_7, ModuleClass.C, true, 4000000, "Int_MultiDroneControl_Universal_Size7_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 125.0), Map.entry(HorizonsModifier.INTEGRITY, 150.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.8), Map.entry(HorizonsModifier.BOOT_TIME, 6.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS, 8.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE, 6500.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME, Double.POSITIVE_INFINITY), Map.entry(HorizonsModifier.DRONE_SPEED, 500.0), Map.entry(HorizonsModifier.DRONE_MULTI_TARGET_SPEED, 60.0), Map.entry(HorizonsModifier.DRONE_FUEL_CAPACITY, 1.0), Map.entry(HorizonsModifier.DRONE_REPAIR_CAPACITY, 310.0), Map.entry(HorizonsModifier.DRONE_HACKING_TIME, 8.0), Map.entry(HorizonsModifier.DRONE_MIN_JETTISONED_CARGO, 3.0), Map.entry(HorizonsModifier.DRONE_MAX_JETTISONED_CARGO, 8.0)));
    public static final MultiLimpetController UNIVERSAL_MULTI_LIMPET_CONTROLLER_7_A = new MultiLimpetController("UNIVERSAL_MULTI_LIMPET_CONTROLLER_7_A", HorizonsBlueprintName.UNIVERSAL_MULTI_LIMPET_CONTROLLER, ModuleSize.SIZE_7, ModuleClass.A, true, 8000000, "Int_MultiDroneControl_Universal_Size7_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 140.0), Map.entry(HorizonsModifier.INTEGRITY, 200.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.1), Map.entry(HorizonsModifier.BOOT_TIME, 6.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS, 8.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE, 9100.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME, Double.POSITIVE_INFINITY), Map.entry(HorizonsModifier.DRONE_SPEED, 500.0), Map.entry(HorizonsModifier.DRONE_MULTI_TARGET_SPEED, 60.0), Map.entry(HorizonsModifier.DRONE_FUEL_CAPACITY, 1.0), Map.entry(HorizonsModifier.DRONE_REPAIR_CAPACITY, 310.0), Map.entry(HorizonsModifier.DRONE_HACKING_TIME, 5.0), Map.entry(HorizonsModifier.DRONE_MIN_JETTISONED_CARGO, 5.0), Map.entry(HorizonsModifier.DRONE_MAX_JETTISONED_CARGO, 10.0)));

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

    public MultiLimpetController(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, basePrice, internalName, attributes);
    }

    public MultiLimpetController(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
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
        return " " + LocaleService.getLocalizedStringForCurrentLocale(getName().getLocalizationKey() + ".short");
    }

    @Override
    public int getGrouping() {
        return switch (getId()) {
            case "MINING_MULTI_LIMPET_CONTROLLER_3_E", "MINING_MULTI_LIMPET_CONTROLLER_3_C" -> 1;
            case "OPERATIONS_LIMPET_CONTROLLER_3_C", "OPERATIONS_LIMPET_CONTROLLER_3_B" -> 2;
            case "RESCUE_LIMPET_CONTROLLER_3_D", "RESCUE_LIMPET_CONTROLLER_3_C" -> 3;
            case "XENO_LIMPET_CONTROLLER_3_C", "XENO_LIMPET_CONTROLLER_3_B" -> 4;
            case "UNIVERSAL_MULTI_LIMPET_CONTROLLER_7_C", "UNIVERSAL_MULTI_LIMPET_CONTROLLER_7_A" -> 5;
            default -> 0;
        };
    }

    @Override
    public int getModuleLimit() {
        return 1;
    }

    @Override
    public Collection<HorizonsSynthesisBlueprint> synthesisBlueprints() {
        return SynthesisBlueprints.LIMPETS.values();
    }
}
