package nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals;

import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class FrameShiftDriveBooster extends OptionalModule {
//            12180 : { mtype:'ifsdb', cost: 405020, name:'Guardian Frame Shift Drive Booster', tag:'G', class:1, rating:'H', mass:1.30, integ:32, pwrdraw:0.75, boottime:15, jumpbst: 4.00, limit:'ifsdb', fdid:128833975, fdname:'Int_GuardianFSDBooster_Size1', eddbid:1730 }, // guardian tech broker
//            12280 : { mtype:'ifsdb', cost: 810520, name:'Guardian Frame Shift Drive Booster', tag:'G', class:2, rating:'H', mass:1.30, integ:32, pwrdraw:0.98, boottime:15, jumpbst: 6.00, limit:'ifsdb', fdid:128833976, fdname:'Int_GuardianFSDBooster_Size2', eddbid:1731 }, // guardian tech broker
//            12380 : { mtype:'ifsdb', cost:1620430, name:'Guardian Frame Shift Drive Booster', tag:'G', class:3, rating:'H', mass:1.30, integ:32, pwrdraw:1.27, boottime:15, jumpbst: 7.75, limit:'ifsdb', fdid:128833977, fdname:'Int_GuardianFSDBooster_Size3', eddbid:1732 }, // guardian tech broker
//            12480 : { mtype:'ifsdb', cost:3245010, name:'Guardian Frame Shift Drive Booster', tag:'G', class:4, rating:'H', mass:1.30, integ:32, pwrdraw:1.65, boottime:15, jumpbst: 9.25, limit:'ifsdb', fdid:128833978, fdname:'Int_GuardianFSDBooster_Size4', eddbid:1733 }, // guardian tech broker
//            12580 : { mtype:'ifsdb', cost:6483100, name:'Guardian Frame Shift Drive Booster', tag:'G', class:5, rating:'H', mass:1.30, integ:32, pwrdraw:2.14, boottime:15, jumpbst:10.50, limit:'ifsdb', fdid:128833979, fdname:'Int_GuardianFSDBooster_Size5', eddbid:1734 }, // guardian tech broker

    public static final FrameShiftDriveBooster FRAME_SHIFT_DRIVE_BOOSTER_1_H = new FrameShiftDriveBooster("FRAME_SHIFT_DRIVE_BOOSTER_1_H", HorizonsBlueprintName.FRAME_SHIFT_DRIVE_BOOSTER, ModuleSize.SIZE_1, ModuleClass.H, Origin.GUARDIAN, 405020, "Int_GuardianFSDBooster_Size1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 1.30), Map.entry(HorizonsModifier.INTEGRITY, 32.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.75), Map.entry(HorizonsModifier.BOOT_TIME, 15.0), Map.entry(HorizonsModifier.JUMP_RANGE_INCREASE, 4.00)));
    public static final FrameShiftDriveBooster FRAME_SHIFT_DRIVE_BOOSTER_2_H = new FrameShiftDriveBooster("FRAME_SHIFT_DRIVE_BOOSTER_2_H", HorizonsBlueprintName.FRAME_SHIFT_DRIVE_BOOSTER, ModuleSize.SIZE_2, ModuleClass.H, Origin.GUARDIAN, 810520, "Int_GuardianFSDBooster_Size2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 1.30), Map.entry(HorizonsModifier.INTEGRITY, 32.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.98), Map.entry(HorizonsModifier.BOOT_TIME, 15.0), Map.entry(HorizonsModifier.JUMP_RANGE_INCREASE, 6.00)));
    public static final FrameShiftDriveBooster FRAME_SHIFT_DRIVE_BOOSTER_3_H = new FrameShiftDriveBooster("FRAME_SHIFT_DRIVE_BOOSTER_3_H", HorizonsBlueprintName.FRAME_SHIFT_DRIVE_BOOSTER, ModuleSize.SIZE_3, ModuleClass.H, Origin.GUARDIAN, 1620430, "Int_GuardianFSDBooster_Size3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 1.30), Map.entry(HorizonsModifier.INTEGRITY, 32.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.27), Map.entry(HorizonsModifier.BOOT_TIME, 15.0), Map.entry(HorizonsModifier.JUMP_RANGE_INCREASE, 7.75)));
    public static final FrameShiftDriveBooster FRAME_SHIFT_DRIVE_BOOSTER_4_H = new FrameShiftDriveBooster("FRAME_SHIFT_DRIVE_BOOSTER_4_H", HorizonsBlueprintName.FRAME_SHIFT_DRIVE_BOOSTER, ModuleSize.SIZE_4, ModuleClass.H, Origin.GUARDIAN, 3245010, "Int_GuardianFSDBooster_Size4", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 1.30), Map.entry(HorizonsModifier.INTEGRITY, 32.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.65), Map.entry(HorizonsModifier.BOOT_TIME, 15.0), Map.entry(HorizonsModifier.JUMP_RANGE_INCREASE, 9.25)));
    public static final FrameShiftDriveBooster FRAME_SHIFT_DRIVE_BOOSTER_5_H = new FrameShiftDriveBooster("FRAME_SHIFT_DRIVE_BOOSTER_5_H", HorizonsBlueprintName.FRAME_SHIFT_DRIVE_BOOSTER, ModuleSize.SIZE_5, ModuleClass.H, Origin.GUARDIAN, 6483100, "Int_GuardianFSDBooster_Size5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 1.30), Map.entry(HorizonsModifier.INTEGRITY, 32.0), Map.entry(HorizonsModifier.POWER_DRAW, 2.14), Map.entry(HorizonsModifier.BOOT_TIME, 15.0), Map.entry(HorizonsModifier.JUMP_RANGE_INCREASE, 10.50)));
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

    public FrameShiftDriveBooster(final String id, final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final Origin origin, final int basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, false, basePrice, internalName, attributes);
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
    public ShipModule Clone() {
        return new FrameShiftDriveBooster(this);
    }
}
