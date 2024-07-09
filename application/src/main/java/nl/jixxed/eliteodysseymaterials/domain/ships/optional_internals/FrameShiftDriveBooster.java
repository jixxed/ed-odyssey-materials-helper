package nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals;

import nl.jixxed.eliteodysseymaterials.constants.horizons.optional_internals.FrameShiftDriveBoosterBlueprints;
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

public class FrameShiftDriveBooster extends OptionalModule {
    public static final FrameShiftDriveBooster FRAME_SHIFT_DRIVE_BOOSTER_1_H = new FrameShiftDriveBooster("FRAME_SHIFT_DRIVE_BOOSTER_1_H", HorizonsBlueprintName.FRAME_SHIFT_DRIVE_BOOSTER, ModuleSize.SIZE_1, ModuleClass.H, Origin.GUARDIAN, 405022, "Int_GuardianFSDBooster_Size1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 1.30), Map.entry(HorizonsModifier.INTEGRITY, 32.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.75), Map.entry(HorizonsModifier.BOOT_TIME, 15.0), Map.entry(HorizonsModifier.JUMP_RANGE_INCREASE, 4.00), Map.entry(HorizonsModifier.ANTI_GUARDIAN_ZONE_RESISTANCE,  false)));
    public static final FrameShiftDriveBooster FRAME_SHIFT_DRIVE_BOOSTER_2_H = new FrameShiftDriveBooster("FRAME_SHIFT_DRIVE_BOOSTER_2_H", HorizonsBlueprintName.FRAME_SHIFT_DRIVE_BOOSTER, ModuleSize.SIZE_2, ModuleClass.H, Origin.GUARDIAN, 810520, "Int_GuardianFSDBooster_Size2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 1.30), Map.entry(HorizonsModifier.INTEGRITY, 32.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.98), Map.entry(HorizonsModifier.BOOT_TIME, 15.0), Map.entry(HorizonsModifier.JUMP_RANGE_INCREASE, 6.00), Map.entry(HorizonsModifier.ANTI_GUARDIAN_ZONE_RESISTANCE,  false)));
    public static final FrameShiftDriveBooster FRAME_SHIFT_DRIVE_BOOSTER_3_H = new FrameShiftDriveBooster("FRAME_SHIFT_DRIVE_BOOSTER_3_H", HorizonsBlueprintName.FRAME_SHIFT_DRIVE_BOOSTER, ModuleSize.SIZE_3, ModuleClass.H, Origin.GUARDIAN, 1620430, "Int_GuardianFSDBooster_Size3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 1.30), Map.entry(HorizonsModifier.INTEGRITY, 32.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.27), Map.entry(HorizonsModifier.BOOT_TIME, 15.0), Map.entry(HorizonsModifier.JUMP_RANGE_INCREASE, 7.75), Map.entry(HorizonsModifier.ANTI_GUARDIAN_ZONE_RESISTANCE,  false)));
    public static final FrameShiftDriveBooster FRAME_SHIFT_DRIVE_BOOSTER_4_H = new FrameShiftDriveBooster("FRAME_SHIFT_DRIVE_BOOSTER_4_H", HorizonsBlueprintName.FRAME_SHIFT_DRIVE_BOOSTER, ModuleSize.SIZE_4, ModuleClass.H, Origin.GUARDIAN, 3245012, "Int_GuardianFSDBooster_Size4", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 1.30), Map.entry(HorizonsModifier.INTEGRITY, 32.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.65), Map.entry(HorizonsModifier.BOOT_TIME, 15.0), Map.entry(HorizonsModifier.JUMP_RANGE_INCREASE, 9.25), Map.entry(HorizonsModifier.ANTI_GUARDIAN_ZONE_RESISTANCE,  false)));
    public static final FrameShiftDriveBooster FRAME_SHIFT_DRIVE_BOOSTER_5_H = new FrameShiftDriveBooster("FRAME_SHIFT_DRIVE_BOOSTER_5_H", HorizonsBlueprintName.FRAME_SHIFT_DRIVE_BOOSTER, ModuleSize.SIZE_5, ModuleClass.H, Origin.GUARDIAN, 6483100, "Int_GuardianFSDBooster_Size5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 1.30), Map.entry(HorizonsModifier.INTEGRITY, 32.0), Map.entry(HorizonsModifier.POWER_DRAW, 2.14), Map.entry(HorizonsModifier.BOOT_TIME, 15.0), Map.entry(HorizonsModifier.JUMP_RANGE_INCREASE, 10.50), Map.entry(HorizonsModifier.ANTI_GUARDIAN_ZONE_RESISTANCE,  false)));
    public static final List<FrameShiftDriveBooster> FRAME_SHIFT_DRIVE_BOOSTERS = List.of(
            FRAME_SHIFT_DRIVE_BOOSTER_1_H,
            FRAME_SHIFT_DRIVE_BOOSTER_2_H,
            FRAME_SHIFT_DRIVE_BOOSTER_3_H,
            FRAME_SHIFT_DRIVE_BOOSTER_4_H,
            FRAME_SHIFT_DRIVE_BOOSTER_5_H
    );

    private FrameShiftDriveBooster(final FrameShiftDriveBooster frameShiftDriveBooster) {
        super(frameShiftDriveBooster);
    }

    public FrameShiftDriveBooster(final String id, final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final Origin origin, final long basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, false, basePrice, internalName, attributes);
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedBlueprints() {
        return FrameShiftDriveBoosterBlueprints.BLUEPRINTS.keySet().stream().toList();
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedExperimentalEffects() {
        return Collections.emptyList();
    }

    @Override
    public FrameShiftDriveBooster Clone() {
        return new FrameShiftDriveBooster(this);
    }
}
