package nl.jixxed.eliteodysseymaterials.domain.ships.utility;

import nl.jixxed.eliteodysseymaterials.constants.horizons.utilitymounts.FrameShiftWakeScannerBlueprints;
import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class FrameShiftWakeScanner extends UtilityModule {
    public static final FrameShiftWakeScanner FRAME_SHIFT_WAKE_SCANNER_0_E = new FrameShiftWakeScanner("FRAME_SHIFT_WAKE_SCANNER_0_E", HorizonsBlueprintName.FRAME_SHIFT_WAKE_SCANNER, ModuleSize.SIZE_0, ModuleClass.E, false, Mounting.NA, 13540, "Hpt_CloudScanner_Size0_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  32.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.2), Map.entry(HorizonsModifier.BOOT_TIME,  1.0), Map.entry(HorizonsModifier.SCANNER_RANGE,  2000.0), Map.entry(HorizonsModifier.MAX_ANGLE,  15.0), Map.entry(HorizonsModifier.SCAN_TIME,  10.0)));
    public static final FrameShiftWakeScanner FRAME_SHIFT_WAKE_SCANNER_0_D = new FrameShiftWakeScanner("FRAME_SHIFT_WAKE_SCANNER_0_D", HorizonsBlueprintName.FRAME_SHIFT_WAKE_SCANNER, ModuleSize.SIZE_0, ModuleClass.D, false, Mounting.NA, 40630, "Hpt_CloudScanner_Size0_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  24.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.4), Map.entry(HorizonsModifier.BOOT_TIME,  1.0), Map.entry(HorizonsModifier.SCANNER_RANGE,  2500.0), Map.entry(HorizonsModifier.MAX_ANGLE,  15.0), Map.entry(HorizonsModifier.SCAN_TIME,  10.0)));
    public static final FrameShiftWakeScanner FRAME_SHIFT_WAKE_SCANNER_0_C = new FrameShiftWakeScanner("FRAME_SHIFT_WAKE_SCANNER_0_C", HorizonsBlueprintName.FRAME_SHIFT_WAKE_SCANNER, ModuleSize.SIZE_0, ModuleClass.C, false, Mounting.NA, 121900, "Hpt_CloudScanner_Size0_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  40.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.8), Map.entry(HorizonsModifier.BOOT_TIME,  1.0), Map.entry(HorizonsModifier.SCANNER_RANGE,  3000.0), Map.entry(HorizonsModifier.MAX_ANGLE,  15.0), Map.entry(HorizonsModifier.SCAN_TIME,  10.0)));
    public static final FrameShiftWakeScanner FRAME_SHIFT_WAKE_SCANNER_0_B = new FrameShiftWakeScanner("FRAME_SHIFT_WAKE_SCANNER_0_B", HorizonsBlueprintName.FRAME_SHIFT_WAKE_SCANNER, ModuleSize.SIZE_0, ModuleClass.B, false, Mounting.NA, 365700, "Hpt_CloudScanner_Size0_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  56.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.6), Map.entry(HorizonsModifier.BOOT_TIME,  1.0), Map.entry(HorizonsModifier.SCANNER_RANGE,  3500.0), Map.entry(HorizonsModifier.MAX_ANGLE,  15.0), Map.entry(HorizonsModifier.SCAN_TIME,  10.0)));
    public static final FrameShiftWakeScanner FRAME_SHIFT_WAKE_SCANNER_0_A = new FrameShiftWakeScanner("FRAME_SHIFT_WAKE_SCANNER_0_A", HorizonsBlueprintName.FRAME_SHIFT_WAKE_SCANNER, ModuleSize.SIZE_0, ModuleClass.A, false, Mounting.NA, 1097100, "Hpt_CloudScanner_Size0_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  48.0), Map.entry(HorizonsModifier.POWER_DRAW,  3.2), Map.entry(HorizonsModifier.BOOT_TIME,  1.0), Map.entry(HorizonsModifier.SCANNER_RANGE,  4000.0), Map.entry(HorizonsModifier.MAX_ANGLE,  15.0), Map.entry(HorizonsModifier.SCAN_TIME,  10.0)));

    public static final List<FrameShiftWakeScanner> FRAME_SHIFT_WAKE_SCANNERS = List.of(
            FRAME_SHIFT_WAKE_SCANNER_0_E,
            FRAME_SHIFT_WAKE_SCANNER_0_D,
            FRAME_SHIFT_WAKE_SCANNER_0_C,
            FRAME_SHIFT_WAKE_SCANNER_0_B,
            FRAME_SHIFT_WAKE_SCANNER_0_A
    );
    public FrameShiftWakeScanner(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, Mounting mounting, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public FrameShiftWakeScanner(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, Mounting mounting, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public FrameShiftWakeScanner(FrameShiftWakeScanner frameShiftWakeScanner) {
        super(frameShiftWakeScanner);
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedBlueprints() {
        return FrameShiftWakeScannerBlueprints.BLUEPRINTS.keySet().stream().toList();
    }
    @Override
    public List<HorizonsBlueprintType> getAllowedExperimentalEffects() {
        return Collections.emptyList();
    }

    @Override
    public FrameShiftWakeScanner Clone() {
        return new FrameShiftWakeScanner(this);
    }
}
