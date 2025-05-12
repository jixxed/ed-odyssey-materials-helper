package nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals;

import nl.jixxed.eliteodysseymaterials.constants.horizons.optional_internals.FrameShiftDriveInterdictorBlueprints;
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

public class FrameShiftDriveInterdictor extends OptionalModule {
    public static final FrameShiftDriveInterdictor FRAME_SHIFT_DRIVE_INTERDICTOR_1_E = new FrameShiftDriveInterdictor("FRAME_SHIFT_DRIVE_INTERDICTOR_1_E", HorizonsBlueprintName.FRAME_SHIFT_DRIVE_INTERDICTOR, ModuleSize.SIZE_1, ModuleClass.E, false, 12000, "Int_FSDInterdictor_Size1_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 1.3), Map.entry(HorizonsModifier.INTEGRITY, 32.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.14), Map.entry(HorizonsModifier.BOOT_TIME, 15.0), Map.entry(HorizonsModifier.FSD_INTERDICTOR_RANGE, 3.0), Map.entry(HorizonsModifier.FSD_INTERDICTOR_FACING_LIMIT, 50.0)));
    public static final FrameShiftDriveInterdictor FRAME_SHIFT_DRIVE_INTERDICTOR_1_D = new FrameShiftDriveInterdictor("FRAME_SHIFT_DRIVE_INTERDICTOR_1_D", HorizonsBlueprintName.FRAME_SHIFT_DRIVE_INTERDICTOR, ModuleSize.SIZE_1, ModuleClass.D, false, 36000, "Int_FSDInterdictor_Size1_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 0.5), Map.entry(HorizonsModifier.INTEGRITY, 24.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.18), Map.entry(HorizonsModifier.BOOT_TIME, 15.0), Map.entry(HorizonsModifier.FSD_INTERDICTOR_RANGE, 4.0), Map.entry(HorizonsModifier.FSD_INTERDICTOR_FACING_LIMIT, 50.0)));
    public static final FrameShiftDriveInterdictor FRAME_SHIFT_DRIVE_INTERDICTOR_1_C = new FrameShiftDriveInterdictor("FRAME_SHIFT_DRIVE_INTERDICTOR_1_C", HorizonsBlueprintName.FRAME_SHIFT_DRIVE_INTERDICTOR, ModuleSize.SIZE_1, ModuleClass.C, false, 108000, "Int_FSDInterdictor_Size1_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 1.3), Map.entry(HorizonsModifier.INTEGRITY, 40.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.23), Map.entry(HorizonsModifier.BOOT_TIME, 15.0), Map.entry(HorizonsModifier.FSD_INTERDICTOR_RANGE, 5.0), Map.entry(HorizonsModifier.FSD_INTERDICTOR_FACING_LIMIT, 50.0)));
    public static final FrameShiftDriveInterdictor FRAME_SHIFT_DRIVE_INTERDICTOR_1_B = new FrameShiftDriveInterdictor("FRAME_SHIFT_DRIVE_INTERDICTOR_1_B", HorizonsBlueprintName.FRAME_SHIFT_DRIVE_INTERDICTOR, ModuleSize.SIZE_1, ModuleClass.B, false, 324000, "Int_FSDInterdictor_Size1_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.0), Map.entry(HorizonsModifier.INTEGRITY, 56.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.28), Map.entry(HorizonsModifier.BOOT_TIME, 15.0), Map.entry(HorizonsModifier.FSD_INTERDICTOR_RANGE, 6.0), Map.entry(HorizonsModifier.FSD_INTERDICTOR_FACING_LIMIT, 50.0)));
    public static final FrameShiftDriveInterdictor FRAME_SHIFT_DRIVE_INTERDICTOR_1_A = new FrameShiftDriveInterdictor("FRAME_SHIFT_DRIVE_INTERDICTOR_1_A", HorizonsBlueprintName.FRAME_SHIFT_DRIVE_INTERDICTOR, ModuleSize.SIZE_1, ModuleClass.A, false, 972000, "Int_FSDInterdictor_Size1_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 1.3), Map.entry(HorizonsModifier.INTEGRITY, 48.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.32), Map.entry(HorizonsModifier.BOOT_TIME, 15.0), Map.entry(HorizonsModifier.FSD_INTERDICTOR_RANGE, 7.0), Map.entry(HorizonsModifier.FSD_INTERDICTOR_FACING_LIMIT, 50.0)));
    public static final FrameShiftDriveInterdictor FRAME_SHIFT_DRIVE_INTERDICTOR_2_E = new FrameShiftDriveInterdictor("FRAME_SHIFT_DRIVE_INTERDICTOR_2_E", HorizonsBlueprintName.FRAME_SHIFT_DRIVE_INTERDICTOR, ModuleSize.SIZE_2, ModuleClass.E, false, 33600, "Int_FSDInterdictor_Size2_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.5), Map.entry(HorizonsModifier.INTEGRITY, 41.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.17), Map.entry(HorizonsModifier.BOOT_TIME, 15.0), Map.entry(HorizonsModifier.FSD_INTERDICTOR_RANGE, 6.0), Map.entry(HorizonsModifier.FSD_INTERDICTOR_FACING_LIMIT, 50.0)));
    public static final FrameShiftDriveInterdictor FRAME_SHIFT_DRIVE_INTERDICTOR_2_D = new FrameShiftDriveInterdictor("FRAME_SHIFT_DRIVE_INTERDICTOR_2_D", HorizonsBlueprintName.FRAME_SHIFT_DRIVE_INTERDICTOR, ModuleSize.SIZE_2, ModuleClass.D, false, 100800, "Int_FSDInterdictor_Size2_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 1.0), Map.entry(HorizonsModifier.INTEGRITY, 31.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.22), Map.entry(HorizonsModifier.BOOT_TIME, 15.0), Map.entry(HorizonsModifier.FSD_INTERDICTOR_RANGE, 7.0), Map.entry(HorizonsModifier.FSD_INTERDICTOR_FACING_LIMIT, 50.0)));
    public static final FrameShiftDriveInterdictor FRAME_SHIFT_DRIVE_INTERDICTOR_2_C = new FrameShiftDriveInterdictor("FRAME_SHIFT_DRIVE_INTERDICTOR_2_C", HorizonsBlueprintName.FRAME_SHIFT_DRIVE_INTERDICTOR, ModuleSize.SIZE_2, ModuleClass.C, false, 302400, "Int_FSDInterdictor_Size2_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.5), Map.entry(HorizonsModifier.INTEGRITY, 51.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.28), Map.entry(HorizonsModifier.BOOT_TIME, 15.0), Map.entry(HorizonsModifier.FSD_INTERDICTOR_RANGE, 8.0), Map.entry(HorizonsModifier.FSD_INTERDICTOR_FACING_LIMIT, 50.0)));
    public static final FrameShiftDriveInterdictor FRAME_SHIFT_DRIVE_INTERDICTOR_2_B = new FrameShiftDriveInterdictor("FRAME_SHIFT_DRIVE_INTERDICTOR_2_B", HorizonsBlueprintName.FRAME_SHIFT_DRIVE_INTERDICTOR, ModuleSize.SIZE_2, ModuleClass.B, false, 907200, "Int_FSDInterdictor_Size2_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.0), Map.entry(HorizonsModifier.INTEGRITY, 71.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.34), Map.entry(HorizonsModifier.BOOT_TIME, 15.0), Map.entry(HorizonsModifier.FSD_INTERDICTOR_RANGE, 9.0), Map.entry(HorizonsModifier.FSD_INTERDICTOR_FACING_LIMIT, 50.0)));
    public static final FrameShiftDriveInterdictor FRAME_SHIFT_DRIVE_INTERDICTOR_2_A = new FrameShiftDriveInterdictor("FRAME_SHIFT_DRIVE_INTERDICTOR_2_A", HorizonsBlueprintName.FRAME_SHIFT_DRIVE_INTERDICTOR, ModuleSize.SIZE_2, ModuleClass.A, false, 2721600, "Int_FSDInterdictor_Size2_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.5), Map.entry(HorizonsModifier.INTEGRITY, 61.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.39), Map.entry(HorizonsModifier.BOOT_TIME, 15.0), Map.entry(HorizonsModifier.FSD_INTERDICTOR_RANGE, 10.0), Map.entry(HorizonsModifier.FSD_INTERDICTOR_FACING_LIMIT, 50.0)));
    public static final FrameShiftDriveInterdictor FRAME_SHIFT_DRIVE_INTERDICTOR_3_E = new FrameShiftDriveInterdictor("FRAME_SHIFT_DRIVE_INTERDICTOR_3_E", HorizonsBlueprintName.FRAME_SHIFT_DRIVE_INTERDICTOR, ModuleSize.SIZE_3, ModuleClass.E, false, 94080, "Int_FSDInterdictor_Size3_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 5.0), Map.entry(HorizonsModifier.INTEGRITY, 51.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.2), Map.entry(HorizonsModifier.BOOT_TIME, 15.0), Map.entry(HorizonsModifier.FSD_INTERDICTOR_RANGE, 9.0), Map.entry(HorizonsModifier.FSD_INTERDICTOR_FACING_LIMIT, 50.0)));
    public static final FrameShiftDriveInterdictor FRAME_SHIFT_DRIVE_INTERDICTOR_3_D = new FrameShiftDriveInterdictor("FRAME_SHIFT_DRIVE_INTERDICTOR_3_D", HorizonsBlueprintName.FRAME_SHIFT_DRIVE_INTERDICTOR, ModuleSize.SIZE_3, ModuleClass.D, false, 282240, "Int_FSDInterdictor_Size3_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.0), Map.entry(HorizonsModifier.INTEGRITY, 38.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.27), Map.entry(HorizonsModifier.BOOT_TIME, 15.0), Map.entry(HorizonsModifier.FSD_INTERDICTOR_RANGE, 10.0), Map.entry(HorizonsModifier.FSD_INTERDICTOR_FACING_LIMIT, 50.0)));
    public static final FrameShiftDriveInterdictor FRAME_SHIFT_DRIVE_INTERDICTOR_3_C = new FrameShiftDriveInterdictor("FRAME_SHIFT_DRIVE_INTERDICTOR_3_C", HorizonsBlueprintName.FRAME_SHIFT_DRIVE_INTERDICTOR, ModuleSize.SIZE_3, ModuleClass.C, false, 846720, "Int_FSDInterdictor_Size3_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 5.0), Map.entry(HorizonsModifier.INTEGRITY, 64.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.34), Map.entry(HorizonsModifier.BOOT_TIME, 15.0), Map.entry(HorizonsModifier.FSD_INTERDICTOR_RANGE, 11.0), Map.entry(HorizonsModifier.FSD_INTERDICTOR_FACING_LIMIT, 50.0)));
    public static final FrameShiftDriveInterdictor FRAME_SHIFT_DRIVE_INTERDICTOR_3_B = new FrameShiftDriveInterdictor("FRAME_SHIFT_DRIVE_INTERDICTOR_3_B", HorizonsBlueprintName.FRAME_SHIFT_DRIVE_INTERDICTOR, ModuleSize.SIZE_3, ModuleClass.B, false, 2540160, "Int_FSDInterdictor_Size3_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 8.0), Map.entry(HorizonsModifier.INTEGRITY, 90.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.41), Map.entry(HorizonsModifier.BOOT_TIME, 15.0), Map.entry(HorizonsModifier.FSD_INTERDICTOR_RANGE, 12.0), Map.entry(HorizonsModifier.FSD_INTERDICTOR_FACING_LIMIT, 50.0)));
    public static final FrameShiftDriveInterdictor FRAME_SHIFT_DRIVE_INTERDICTOR_3_A = new FrameShiftDriveInterdictor("FRAME_SHIFT_DRIVE_INTERDICTOR_3_A", HorizonsBlueprintName.FRAME_SHIFT_DRIVE_INTERDICTOR, ModuleSize.SIZE_3, ModuleClass.A, false, 7620480, "Int_FSDInterdictor_Size3_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 5.0), Map.entry(HorizonsModifier.INTEGRITY, 77.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.48), Map.entry(HorizonsModifier.BOOT_TIME, 15.0), Map.entry(HorizonsModifier.FSD_INTERDICTOR_RANGE, 13.0), Map.entry(HorizonsModifier.FSD_INTERDICTOR_FACING_LIMIT, 50.0)));
    public static final FrameShiftDriveInterdictor FRAME_SHIFT_DRIVE_INTERDICTOR_4_E = new FrameShiftDriveInterdictor("FRAME_SHIFT_DRIVE_INTERDICTOR_4_E", HorizonsBlueprintName.FRAME_SHIFT_DRIVE_INTERDICTOR, ModuleSize.SIZE_4, ModuleClass.E, false, 263420, "Int_FSDInterdictor_Size4_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 10.0), Map.entry(HorizonsModifier.INTEGRITY, 64.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.25), Map.entry(HorizonsModifier.BOOT_TIME, 15.0), Map.entry(HorizonsModifier.FSD_INTERDICTOR_RANGE, 12.0), Map.entry(HorizonsModifier.FSD_INTERDICTOR_FACING_LIMIT, 50.0)));
    public static final FrameShiftDriveInterdictor FRAME_SHIFT_DRIVE_INTERDICTOR_4_D = new FrameShiftDriveInterdictor("FRAME_SHIFT_DRIVE_INTERDICTOR_4_D", HorizonsBlueprintName.FRAME_SHIFT_DRIVE_INTERDICTOR, ModuleSize.SIZE_4, ModuleClass.D, false, 790270, "Int_FSDInterdictor_Size4_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.0), Map.entry(HorizonsModifier.INTEGRITY, 48.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.33), Map.entry(HorizonsModifier.BOOT_TIME, 15.0), Map.entry(HorizonsModifier.FSD_INTERDICTOR_RANGE, 13.0), Map.entry(HorizonsModifier.FSD_INTERDICTOR_FACING_LIMIT, 50.0)));
    public static final FrameShiftDriveInterdictor FRAME_SHIFT_DRIVE_INTERDICTOR_4_C = new FrameShiftDriveInterdictor("FRAME_SHIFT_DRIVE_INTERDICTOR_4_C", HorizonsBlueprintName.FRAME_SHIFT_DRIVE_INTERDICTOR, ModuleSize.SIZE_4, ModuleClass.C, false, 2370820, "Int_FSDInterdictor_Size4_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 10.0), Map.entry(HorizonsModifier.INTEGRITY, 80.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.41), Map.entry(HorizonsModifier.BOOT_TIME, 15.0), Map.entry(HorizonsModifier.FSD_INTERDICTOR_RANGE, 14.0), Map.entry(HorizonsModifier.FSD_INTERDICTOR_FACING_LIMIT, 50.0)));
    public static final FrameShiftDriveInterdictor FRAME_SHIFT_DRIVE_INTERDICTOR_4_B = new FrameShiftDriveInterdictor("FRAME_SHIFT_DRIVE_INTERDICTOR_4_B", HorizonsBlueprintName.FRAME_SHIFT_DRIVE_INTERDICTOR, ModuleSize.SIZE_4, ModuleClass.B, false, 7112450, "Int_FSDInterdictor_Size4_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 16.0), Map.entry(HorizonsModifier.INTEGRITY, 112.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.49), Map.entry(HorizonsModifier.BOOT_TIME, 15.0), Map.entry(HorizonsModifier.FSD_INTERDICTOR_RANGE, 15.0), Map.entry(HorizonsModifier.FSD_INTERDICTOR_FACING_LIMIT, 50.0)));
    public static final FrameShiftDriveInterdictor FRAME_SHIFT_DRIVE_INTERDICTOR_4_A = new FrameShiftDriveInterdictor("FRAME_SHIFT_DRIVE_INTERDICTOR_4_A", HorizonsBlueprintName.FRAME_SHIFT_DRIVE_INTERDICTOR, ModuleSize.SIZE_4, ModuleClass.A, false, 21337340, "Int_FSDInterdictor_Size4_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 10.0), Map.entry(HorizonsModifier.INTEGRITY, 96.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.57), Map.entry(HorizonsModifier.BOOT_TIME, 15.0), Map.entry(HorizonsModifier.FSD_INTERDICTOR_RANGE, 16.0), Map.entry(HorizonsModifier.FSD_INTERDICTOR_FACING_LIMIT, 50.0)));
    public static final List<FrameShiftDriveInterdictor> FRAME_SHIFT_DRIVE_INTERDICTORS = List.of(
            FRAME_SHIFT_DRIVE_INTERDICTOR_1_E,
            FRAME_SHIFT_DRIVE_INTERDICTOR_1_D,
            FRAME_SHIFT_DRIVE_INTERDICTOR_1_C,
            FRAME_SHIFT_DRIVE_INTERDICTOR_1_B,
            FRAME_SHIFT_DRIVE_INTERDICTOR_1_A,
            FRAME_SHIFT_DRIVE_INTERDICTOR_2_E,
            FRAME_SHIFT_DRIVE_INTERDICTOR_2_D,
            FRAME_SHIFT_DRIVE_INTERDICTOR_2_C,
            FRAME_SHIFT_DRIVE_INTERDICTOR_2_B,
            FRAME_SHIFT_DRIVE_INTERDICTOR_2_A,
            FRAME_SHIFT_DRIVE_INTERDICTOR_3_E,
            FRAME_SHIFT_DRIVE_INTERDICTOR_3_D,
            FRAME_SHIFT_DRIVE_INTERDICTOR_3_C,
            FRAME_SHIFT_DRIVE_INTERDICTOR_3_B,
            FRAME_SHIFT_DRIVE_INTERDICTOR_3_A,
            FRAME_SHIFT_DRIVE_INTERDICTOR_4_E,
            FRAME_SHIFT_DRIVE_INTERDICTOR_4_D,
            FRAME_SHIFT_DRIVE_INTERDICTOR_4_C,
            FRAME_SHIFT_DRIVE_INTERDICTOR_4_B,
            FRAME_SHIFT_DRIVE_INTERDICTOR_4_A
    );

    public FrameShiftDriveInterdictor(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, basePrice, internalName, attributes);
    }

    public FrameShiftDriveInterdictor(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, basePrice, internalName, attributes);
    }

    public FrameShiftDriveInterdictor(FrameShiftDriveInterdictor frameShiftDriveInterdictor) {
        super(frameShiftDriveInterdictor);
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedBlueprints() {
        return FrameShiftDriveInterdictorBlueprints.BLUEPRINTS.keySet().stream().toList();
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedExperimentalEffects() {
        return Collections.emptyList();
    }

    @Override
    public FrameShiftDriveInterdictor Clone() {
        return new FrameShiftDriveInterdictor(this);
    }

    @Override
    public int getModuleLimit() {
        return 1;
    }
}
