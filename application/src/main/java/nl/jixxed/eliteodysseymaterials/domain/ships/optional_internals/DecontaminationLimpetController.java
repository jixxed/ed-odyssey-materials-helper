package nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals;

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

public class DecontaminationLimpetController extends OptionalModule {
    public static final DecontaminationLimpetController DECONTAMINATION_LIMPET_CONTROLLER_1_E = new DecontaminationLimpetController("DECONTAMINATION_LIMPET_CONTROLLER_1_E", HorizonsBlueprintName.DECONTAMINATION_LIMPET_CONTROLLER, ModuleSize.SIZE_1, ModuleClass.E, true, 3600, "Int_DroneControl_Decontamination_Size1_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 1.3), Map.entry(HorizonsModifier.INTEGRITY, 24.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.18), Map.entry(HorizonsModifier.BOOT_TIME, 10.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS, 1.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE, 600.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME, 300.0), Map.entry(HorizonsModifier.DRONE_SPEED, 200.0), Map.entry(HorizonsModifier.DRONE_REPAIR_CAPACITY, 30.0)));
    public static final DecontaminationLimpetController DECONTAMINATION_LIMPET_CONTROLLER_3_E = new DecontaminationLimpetController("DECONTAMINATION_LIMPET_CONTROLLER_3_E", HorizonsBlueprintName.DECONTAMINATION_LIMPET_CONTROLLER, ModuleSize.SIZE_3, ModuleClass.E, true, 16200, "Int_DroneControl_Decontamination_Size3_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.0), Map.entry(HorizonsModifier.INTEGRITY, 51.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.2), Map.entry(HorizonsModifier.BOOT_TIME, 10.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS, 2.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE, 880.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME, 300.0), Map.entry(HorizonsModifier.DRONE_SPEED, 200.0), Map.entry(HorizonsModifier.DRONE_REPAIR_CAPACITY, 70.0)));
    public static final DecontaminationLimpetController DECONTAMINATION_LIMPET_CONTROLLER_5_E = new DecontaminationLimpetController("DECONTAMINATION_LIMPET_CONTROLLER_5_E", HorizonsBlueprintName.DECONTAMINATION_LIMPET_CONTROLLER, ModuleSize.SIZE_5, ModuleClass.E, true, 145800, "Int_DroneControl_Decontamination_Size5_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 20.0), Map.entry(HorizonsModifier.INTEGRITY, 96.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.5), Map.entry(HorizonsModifier.BOOT_TIME, 10.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS, 3.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE, 1300.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME, 300.0), Map.entry(HorizonsModifier.DRONE_SPEED, 200.0), Map.entry(HorizonsModifier.DRONE_REPAIR_CAPACITY, 120.0)));
    public static final DecontaminationLimpetController DECONTAMINATION_LIMPET_CONTROLLER_7_E = new DecontaminationLimpetController("DECONTAMINATION_LIMPET_CONTROLLER_7_E", HorizonsBlueprintName.DECONTAMINATION_LIMPET_CONTROLLER, ModuleSize.SIZE_7, ModuleClass.E, true, 1312200, "Int_DroneControl_Decontamination_Size7_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 128.0), Map.entry(HorizonsModifier.INTEGRITY, 157.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.97), Map.entry(HorizonsModifier.BOOT_TIME, 10.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS, 4.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE, 2040.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME, 300.0), Map.entry(HorizonsModifier.DRONE_SPEED, 200.0), Map.entry(HorizonsModifier.DRONE_REPAIR_CAPACITY, 180.0)));

    public static final List<DecontaminationLimpetController> DECONTAMINATION_LIMPET_CONTROLLERS = List.of(
            DECONTAMINATION_LIMPET_CONTROLLER_1_E,
            DECONTAMINATION_LIMPET_CONTROLLER_3_E,
            DECONTAMINATION_LIMPET_CONTROLLER_5_E,
            DECONTAMINATION_LIMPET_CONTROLLER_7_E
    );

    public DecontaminationLimpetController(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, basePrice, internalName, attributes);
    }

    public DecontaminationLimpetController(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, basePrice, internalName, attributes);
    }

    public DecontaminationLimpetController(DecontaminationLimpetController decontaminationLimpetController) {
        super(decontaminationLimpetController);
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
    public DecontaminationLimpetController Clone() {
        return new DecontaminationLimpetController(this);
    }
    @Override
    public int getGrouping() {
        return 1;
    }
}
