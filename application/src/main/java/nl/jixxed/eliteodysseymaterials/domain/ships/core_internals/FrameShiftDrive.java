package nl.jixxed.eliteodysseymaterials.domain.ships.core_internals;

import nl.jixxed.eliteodysseymaterials.constants.horizons.ExperimentalEffectBlueprints;
import nl.jixxed.eliteodysseymaterials.constants.horizons.core_internals.FSDBlueprints;
import nl.jixxed.eliteodysseymaterials.domain.ships.CoreModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.Modification;
import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleClass;
import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleSize;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class FrameShiftDrive extends CoreModule {
    public static final FrameShiftDrive FRAME_SHIFT_DRIVE_2_E = new FrameShiftDrive("FRAME_SHIFT_DRIVE_2_E", HorizonsBlueprintName.FRAME_SHIFT_DRIVE, ModuleSize.SIZE_2, ModuleClass.E, 1980, "Int_Hyperdrive_Size2_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.50), Map.entry(HorizonsModifier.INTEGRITY, 46.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.16), Map.entry(HorizonsModifier.BOOT_TIME, 10.0), Map.entry(HorizonsModifier.FSD_OPTIMISED_MASS, 48.0), Map.entry(HorizonsModifier.FSD_HEAT_RATE, 10.0), Map.entry(HorizonsModifier.MAX_FUEL_PER_JUMP, 0.60), Map.entry(HorizonsModifier.FUEL_MULTIPLIER, 0.011), Map.entry(HorizonsModifier.FUEL_POWER, 2.00)));
    public static final FrameShiftDrive FRAME_SHIFT_DRIVE_2_D = new FrameShiftDrive("FRAME_SHIFT_DRIVE_2_D", HorizonsBlueprintName.FRAME_SHIFT_DRIVE, ModuleSize.SIZE_2, ModuleClass.D, 5930, "Int_Hyperdrive_Size2_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 1.00), Map.entry(HorizonsModifier.INTEGRITY, 41.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.18), Map.entry(HorizonsModifier.BOOT_TIME, 10.0), Map.entry(HorizonsModifier.FSD_OPTIMISED_MASS, 54.0), Map.entry(HorizonsModifier.FSD_HEAT_RATE, 10.0), Map.entry(HorizonsModifier.MAX_FUEL_PER_JUMP, 0.60), Map.entry(HorizonsModifier.FUEL_MULTIPLIER, 0.010), Map.entry(HorizonsModifier.FUEL_POWER, 2.00)));
    public static final FrameShiftDrive FRAME_SHIFT_DRIVE_2_C = new FrameShiftDrive("FRAME_SHIFT_DRIVE_2_C", HorizonsBlueprintName.FRAME_SHIFT_DRIVE, ModuleSize.SIZE_2, ModuleClass.C, 17800, "Int_Hyperdrive_Size2_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.50), Map.entry(HorizonsModifier.INTEGRITY, 51.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.20), Map.entry(HorizonsModifier.BOOT_TIME, 10.0), Map.entry(HorizonsModifier.FSD_OPTIMISED_MASS, 60.0), Map.entry(HorizonsModifier.FSD_HEAT_RATE, 10.0), Map.entry(HorizonsModifier.MAX_FUEL_PER_JUMP, 0.60), Map.entry(HorizonsModifier.FUEL_MULTIPLIER, 0.008), Map.entry(HorizonsModifier.FUEL_POWER, 2.00)));
    public static final FrameShiftDrive FRAME_SHIFT_DRIVE_2_B = new FrameShiftDrive("FRAME_SHIFT_DRIVE_2_B", HorizonsBlueprintName.FRAME_SHIFT_DRIVE, ModuleSize.SIZE_2, ModuleClass.B, 53410, "Int_Hyperdrive_Size2_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.00), Map.entry(HorizonsModifier.INTEGRITY, 77.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.25), Map.entry(HorizonsModifier.BOOT_TIME, 10.0), Map.entry(HorizonsModifier.FSD_OPTIMISED_MASS, 75.0), Map.entry(HorizonsModifier.FSD_HEAT_RATE, 10.0), Map.entry(HorizonsModifier.MAX_FUEL_PER_JUMP, 0.80), Map.entry(HorizonsModifier.FUEL_MULTIPLIER, 0.010), Map.entry(HorizonsModifier.FUEL_POWER, 2.00)));
    public static final FrameShiftDrive FRAME_SHIFT_DRIVE_2_A = new FrameShiftDrive("FRAME_SHIFT_DRIVE_2_A", HorizonsBlueprintName.FRAME_SHIFT_DRIVE, ModuleSize.SIZE_2, ModuleClass.A, 160220, "Int_Hyperdrive_Size2_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.50), Map.entry(HorizonsModifier.INTEGRITY, 64.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.30), Map.entry(HorizonsModifier.BOOT_TIME, 10.0), Map.entry(HorizonsModifier.FSD_OPTIMISED_MASS, 90.0), Map.entry(HorizonsModifier.FSD_HEAT_RATE, 10.0), Map.entry(HorizonsModifier.MAX_FUEL_PER_JUMP, 0.90), Map.entry(HorizonsModifier.FUEL_MULTIPLIER, 0.012), Map.entry(HorizonsModifier.FUEL_POWER, 2.00)));
    public static final FrameShiftDrive FRAME_SHIFT_DRIVE_3_E = new FrameShiftDrive("FRAME_SHIFT_DRIVE_3_E", HorizonsBlueprintName.FRAME_SHIFT_DRIVE, ModuleSize.SIZE_3, ModuleClass.E, 6270, "Int_Hyperdrive_Size3_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 5.00), Map.entry(HorizonsModifier.INTEGRITY, 58.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.24), Map.entry(HorizonsModifier.BOOT_TIME, 10.0), Map.entry(HorizonsModifier.FSD_OPTIMISED_MASS, 80.0), Map.entry(HorizonsModifier.FSD_HEAT_RATE, 14.0), Map.entry(HorizonsModifier.MAX_FUEL_PER_JUMP, 1.20), Map.entry(HorizonsModifier.FUEL_MULTIPLIER, 0.011), Map.entry(HorizonsModifier.FUEL_POWER, 2.15)));
    public static final FrameShiftDrive FRAME_SHIFT_DRIVE_3_D = new FrameShiftDrive("FRAME_SHIFT_DRIVE_3_D", HorizonsBlueprintName.FRAME_SHIFT_DRIVE, ModuleSize.SIZE_3, ModuleClass.D, 18810, "Int_Hyperdrive_Size3_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.00), Map.entry(HorizonsModifier.INTEGRITY, 51.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.27), Map.entry(HorizonsModifier.BOOT_TIME, 10.0), Map.entry(HorizonsModifier.FSD_OPTIMISED_MASS, 90.0), Map.entry(HorizonsModifier.FSD_HEAT_RATE, 14.0), Map.entry(HorizonsModifier.MAX_FUEL_PER_JUMP, 1.20), Map.entry(HorizonsModifier.FUEL_MULTIPLIER, 0.010), Map.entry(HorizonsModifier.FUEL_POWER, 2.15)));
    public static final FrameShiftDrive FRAME_SHIFT_DRIVE_3_C = new FrameShiftDrive("FRAME_SHIFT_DRIVE_3_C", HorizonsBlueprintName.FRAME_SHIFT_DRIVE, ModuleSize.SIZE_3, ModuleClass.C, 56440, "Int_Hyperdrive_Size3_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 5.00), Map.entry(HorizonsModifier.INTEGRITY, 64.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.30), Map.entry(HorizonsModifier.BOOT_TIME, 10.0), Map.entry(HorizonsModifier.FSD_OPTIMISED_MASS, 100.0), Map.entry(HorizonsModifier.FSD_HEAT_RATE, 14.0), Map.entry(HorizonsModifier.MAX_FUEL_PER_JUMP, 1.20), Map.entry(HorizonsModifier.FUEL_MULTIPLIER, 0.008), Map.entry(HorizonsModifier.FUEL_POWER, 2.15)));
    public static final FrameShiftDrive FRAME_SHIFT_DRIVE_3_B = new FrameShiftDrive("FRAME_SHIFT_DRIVE_3_B", HorizonsBlueprintName.FRAME_SHIFT_DRIVE, ModuleSize.SIZE_3, ModuleClass.B, 169300, "Int_Hyperdrive_Size3_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 8.00), Map.entry(HorizonsModifier.INTEGRITY, 96.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.38), Map.entry(HorizonsModifier.BOOT_TIME, 10.0), Map.entry(HorizonsModifier.FSD_OPTIMISED_MASS, 125.0), Map.entry(HorizonsModifier.FSD_HEAT_RATE, 14.0), Map.entry(HorizonsModifier.MAX_FUEL_PER_JUMP, 1.50), Map.entry(HorizonsModifier.FUEL_MULTIPLIER, 0.010), Map.entry(HorizonsModifier.FUEL_POWER, 2.15)));
    public static final FrameShiftDrive FRAME_SHIFT_DRIVE_3_A = new FrameShiftDrive("FRAME_SHIFT_DRIVE_3_A", HorizonsBlueprintName.FRAME_SHIFT_DRIVE, ModuleSize.SIZE_3, ModuleClass.A, 507910, "Int_Hyperdrive_Size3_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 5.00), Map.entry(HorizonsModifier.INTEGRITY, 80.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.45), Map.entry(HorizonsModifier.BOOT_TIME, 10.0), Map.entry(HorizonsModifier.FSD_OPTIMISED_MASS, 150.0), Map.entry(HorizonsModifier.FSD_HEAT_RATE, 14.0), Map.entry(HorizonsModifier.MAX_FUEL_PER_JUMP, 1.80), Map.entry(HorizonsModifier.FUEL_MULTIPLIER, 0.012), Map.entry(HorizonsModifier.FUEL_POWER, 2.15)));
    public static final FrameShiftDrive FRAME_SHIFT_DRIVE_4_E = new FrameShiftDrive("FRAME_SHIFT_DRIVE_4_E", HorizonsBlueprintName.FRAME_SHIFT_DRIVE, ModuleSize.SIZE_4, ModuleClass.E, 19880, "Int_Hyperdrive_Size4_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 10.00), Map.entry(HorizonsModifier.INTEGRITY, 72.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.24), Map.entry(HorizonsModifier.BOOT_TIME, 10.0), Map.entry(HorizonsModifier.FSD_OPTIMISED_MASS, 280.0), Map.entry(HorizonsModifier.FSD_HEAT_RATE, 18.0), Map.entry(HorizonsModifier.MAX_FUEL_PER_JUMP, 2.00), Map.entry(HorizonsModifier.FUEL_MULTIPLIER, 0.011), Map.entry(HorizonsModifier.FUEL_POWER, 2.30)));
    public static final FrameShiftDrive FRAME_SHIFT_DRIVE_4_D = new FrameShiftDrive("FRAME_SHIFT_DRIVE_4_D", HorizonsBlueprintName.FRAME_SHIFT_DRIVE, ModuleSize.SIZE_4, ModuleClass.D, 59630, "Int_Hyperdrive_Size4_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.00), Map.entry(HorizonsModifier.INTEGRITY, 64.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.27), Map.entry(HorizonsModifier.BOOT_TIME, 10.0), Map.entry(HorizonsModifier.FSD_OPTIMISED_MASS, 315.0), Map.entry(HorizonsModifier.FSD_HEAT_RATE, 18.0), Map.entry(HorizonsModifier.MAX_FUEL_PER_JUMP, 2.00), Map.entry(HorizonsModifier.FUEL_MULTIPLIER, 0.010), Map.entry(HorizonsModifier.FUEL_POWER, 2.30)));
    public static final FrameShiftDrive FRAME_SHIFT_DRIVE_4_C = new FrameShiftDrive("FRAME_SHIFT_DRIVE_4_C", HorizonsBlueprintName.FRAME_SHIFT_DRIVE, ModuleSize.SIZE_4, ModuleClass.C, 178900, "Int_Hyperdrive_Size4_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 10.00), Map.entry(HorizonsModifier.INTEGRITY, 80.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.30), Map.entry(HorizonsModifier.BOOT_TIME, 10.0), Map.entry(HorizonsModifier.FSD_OPTIMISED_MASS, 350.0), Map.entry(HorizonsModifier.FSD_HEAT_RATE, 18.0), Map.entry(HorizonsModifier.MAX_FUEL_PER_JUMP, 2.00), Map.entry(HorizonsModifier.FUEL_MULTIPLIER, 0.008), Map.entry(HorizonsModifier.FUEL_POWER, 2.30)));
    public static final FrameShiftDrive FRAME_SHIFT_DRIVE_4_B = new FrameShiftDrive("FRAME_SHIFT_DRIVE_4_B", HorizonsBlueprintName.FRAME_SHIFT_DRIVE, ModuleSize.SIZE_4, ModuleClass.B, 536690, "Int_Hyperdrive_Size4_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 16.00), Map.entry(HorizonsModifier.INTEGRITY, 120.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.38), Map.entry(HorizonsModifier.BOOT_TIME, 10.0), Map.entry(HorizonsModifier.FSD_OPTIMISED_MASS, 438.0), Map.entry(HorizonsModifier.FSD_HEAT_RATE, 18.0), Map.entry(HorizonsModifier.MAX_FUEL_PER_JUMP, 2.50), Map.entry(HorizonsModifier.FUEL_MULTIPLIER, 0.010), Map.entry(HorizonsModifier.FUEL_POWER, 2.30)));
    public static final FrameShiftDrive FRAME_SHIFT_DRIVE_4_A = new FrameShiftDrive("FRAME_SHIFT_DRIVE_4_A", HorizonsBlueprintName.FRAME_SHIFT_DRIVE, ModuleSize.SIZE_4, ModuleClass.A, 1610080, "Int_Hyperdrive_Size4_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 10.00), Map.entry(HorizonsModifier.INTEGRITY, 100.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.45), Map.entry(HorizonsModifier.BOOT_TIME, 10.0), Map.entry(HorizonsModifier.FSD_OPTIMISED_MASS, 525.0), Map.entry(HorizonsModifier.FSD_HEAT_RATE, 18.0), Map.entry(HorizonsModifier.MAX_FUEL_PER_JUMP, 3.00), Map.entry(HorizonsModifier.FUEL_MULTIPLIER, 0.012), Map.entry(HorizonsModifier.FUEL_POWER, 2.30)));
    public static final FrameShiftDrive FRAME_SHIFT_DRIVE_5_E = new FrameShiftDrive("FRAME_SHIFT_DRIVE_5_E", HorizonsBlueprintName.FRAME_SHIFT_DRIVE, ModuleSize.SIZE_5, ModuleClass.E, 63010, "Int_Hyperdrive_Size5_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 20.00), Map.entry(HorizonsModifier.INTEGRITY, 86.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.32), Map.entry(HorizonsModifier.BOOT_TIME, 10.0), Map.entry(HorizonsModifier.FSD_OPTIMISED_MASS, 560.0), Map.entry(HorizonsModifier.FSD_HEAT_RATE, 27.0), Map.entry(HorizonsModifier.MAX_FUEL_PER_JUMP, 3.30), Map.entry(HorizonsModifier.FUEL_MULTIPLIER, 0.011), Map.entry(HorizonsModifier.FUEL_POWER, 2.45)));
    public static final FrameShiftDrive FRAME_SHIFT_DRIVE_5_D = new FrameShiftDrive("FRAME_SHIFT_DRIVE_5_D", HorizonsBlueprintName.FRAME_SHIFT_DRIVE, ModuleSize.SIZE_5, ModuleClass.D, 189040, "Int_Hyperdrive_Size5_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 8.00), Map.entry(HorizonsModifier.INTEGRITY, 77.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.36), Map.entry(HorizonsModifier.BOOT_TIME, 10.0), Map.entry(HorizonsModifier.FSD_OPTIMISED_MASS, 630.0), Map.entry(HorizonsModifier.FSD_HEAT_RATE, 27.0), Map.entry(HorizonsModifier.MAX_FUEL_PER_JUMP, 3.30), Map.entry(HorizonsModifier.FUEL_MULTIPLIER, 0.010), Map.entry(HorizonsModifier.FUEL_POWER, 2.45)));
    public static final FrameShiftDrive FRAME_SHIFT_DRIVE_5_C = new FrameShiftDrive("FRAME_SHIFT_DRIVE_5_C", HorizonsBlueprintName.FRAME_SHIFT_DRIVE, ModuleSize.SIZE_5, ModuleClass.C, 567110, "Int_Hyperdrive_Size5_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 20.00), Map.entry(HorizonsModifier.INTEGRITY, 96.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.40), Map.entry(HorizonsModifier.BOOT_TIME, 10.0), Map.entry(HorizonsModifier.FSD_OPTIMISED_MASS, 700.0), Map.entry(HorizonsModifier.FSD_HEAT_RATE, 27.0), Map.entry(HorizonsModifier.MAX_FUEL_PER_JUMP, 3.30), Map.entry(HorizonsModifier.FUEL_MULTIPLIER, 0.008), Map.entry(HorizonsModifier.FUEL_POWER, 2.45)));
    public static final FrameShiftDrive FRAME_SHIFT_DRIVE_5_B = new FrameShiftDrive("FRAME_SHIFT_DRIVE_5_B", HorizonsBlueprintName.FRAME_SHIFT_DRIVE, ModuleSize.SIZE_5, ModuleClass.B, 1701320, "Int_Hyperdrive_Size5_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 32.00), Map.entry(HorizonsModifier.INTEGRITY, 144.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.50), Map.entry(HorizonsModifier.BOOT_TIME, 10.0), Map.entry(HorizonsModifier.FSD_OPTIMISED_MASS, 875.0), Map.entry(HorizonsModifier.FSD_HEAT_RATE, 27.0), Map.entry(HorizonsModifier.MAX_FUEL_PER_JUMP, 4.10), Map.entry(HorizonsModifier.FUEL_MULTIPLIER, 0.010), Map.entry(HorizonsModifier.FUEL_POWER, 2.45)));
    public static final FrameShiftDrive FRAME_SHIFT_DRIVE_5_A = new FrameShiftDrive("FRAME_SHIFT_DRIVE_5_A", HorizonsBlueprintName.FRAME_SHIFT_DRIVE, ModuleSize.SIZE_5, ModuleClass.A, 5103950, "Int_Hyperdrive_Size5_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 20.00), Map.entry(HorizonsModifier.INTEGRITY, 120.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.60), Map.entry(HorizonsModifier.BOOT_TIME, 10.0), Map.entry(HorizonsModifier.FSD_OPTIMISED_MASS, 1050.0), Map.entry(HorizonsModifier.FSD_HEAT_RATE, 27.0), Map.entry(HorizonsModifier.MAX_FUEL_PER_JUMP, 5.00), Map.entry(HorizonsModifier.FUEL_MULTIPLIER, 0.012), Map.entry(HorizonsModifier.FUEL_POWER, 2.45)));
    public static final FrameShiftDrive FRAME_SHIFT_DRIVE_6_E = new FrameShiftDrive("FRAME_SHIFT_DRIVE_6_E", HorizonsBlueprintName.FRAME_SHIFT_DRIVE, ModuleSize.SIZE_6, ModuleClass.E, 199750, "Int_Hyperdrive_Size6_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 40.00), Map.entry(HorizonsModifier.INTEGRITY, 102.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.40), Map.entry(HorizonsModifier.BOOT_TIME, 10.0), Map.entry(HorizonsModifier.FSD_OPTIMISED_MASS, 960.0), Map.entry(HorizonsModifier.FSD_HEAT_RATE, 37.0), Map.entry(HorizonsModifier.MAX_FUEL_PER_JUMP, 5.30), Map.entry(HorizonsModifier.FUEL_MULTIPLIER, 0.011), Map.entry(HorizonsModifier.FUEL_POWER, 2.60)));
    public static final FrameShiftDrive FRAME_SHIFT_DRIVE_6_D = new FrameShiftDrive("FRAME_SHIFT_DRIVE_6_D", HorizonsBlueprintName.FRAME_SHIFT_DRIVE, ModuleSize.SIZE_6, ModuleClass.D, 599240, "Int_Hyperdrive_Size6_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 16.00), Map.entry(HorizonsModifier.INTEGRITY, 90.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.45), Map.entry(HorizonsModifier.BOOT_TIME, 10.0), Map.entry(HorizonsModifier.FSD_OPTIMISED_MASS, 1080.0), Map.entry(HorizonsModifier.FSD_HEAT_RATE, 37.0), Map.entry(HorizonsModifier.MAX_FUEL_PER_JUMP, 5.30), Map.entry(HorizonsModifier.FUEL_MULTIPLIER, 0.010), Map.entry(HorizonsModifier.FUEL_POWER, 2.60)));
    public static final FrameShiftDrive FRAME_SHIFT_DRIVE_6_C = new FrameShiftDrive("FRAME_SHIFT_DRIVE_6_C", HorizonsBlueprintName.FRAME_SHIFT_DRIVE, ModuleSize.SIZE_6, ModuleClass.C, 1797730, "Int_Hyperdrive_Size6_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 40.00), Map.entry(HorizonsModifier.INTEGRITY, 113.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.50), Map.entry(HorizonsModifier.BOOT_TIME, 10.0), Map.entry(HorizonsModifier.FSD_OPTIMISED_MASS, 1200.0), Map.entry(HorizonsModifier.FSD_HEAT_RATE, 37.0), Map.entry(HorizonsModifier.MAX_FUEL_PER_JUMP, 5.30), Map.entry(HorizonsModifier.FUEL_MULTIPLIER, 0.008), Map.entry(HorizonsModifier.FUEL_POWER, 2.60)));
    public static final FrameShiftDrive FRAME_SHIFT_DRIVE_6_B = new FrameShiftDrive("FRAME_SHIFT_DRIVE_6_B", HorizonsBlueprintName.FRAME_SHIFT_DRIVE, ModuleSize.SIZE_6, ModuleClass.B, 5393180, "Int_Hyperdrive_Size6_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 64.00), Map.entry(HorizonsModifier.INTEGRITY, 170.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.63), Map.entry(HorizonsModifier.BOOT_TIME, 10.0), Map.entry(HorizonsModifier.FSD_OPTIMISED_MASS, 1500.0), Map.entry(HorizonsModifier.FSD_HEAT_RATE, 37.0), Map.entry(HorizonsModifier.MAX_FUEL_PER_JUMP, 6.60), Map.entry(HorizonsModifier.FUEL_MULTIPLIER, 0.010), Map.entry(HorizonsModifier.FUEL_POWER, 2.60)));
    public static final FrameShiftDrive FRAME_SHIFT_DRIVE_6_A = new FrameShiftDrive("FRAME_SHIFT_DRIVE_6_A", HorizonsBlueprintName.FRAME_SHIFT_DRIVE, ModuleSize.SIZE_6, ModuleClass.A, 16179530, "Int_Hyperdrive_Size6_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 40.00), Map.entry(HorizonsModifier.INTEGRITY, 141.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.75), Map.entry(HorizonsModifier.BOOT_TIME, 10.0), Map.entry(HorizonsModifier.FSD_OPTIMISED_MASS, 1800.0), Map.entry(HorizonsModifier.FSD_HEAT_RATE, 37.0), Map.entry(HorizonsModifier.MAX_FUEL_PER_JUMP, 8.00), Map.entry(HorizonsModifier.FUEL_MULTIPLIER, 0.012), Map.entry(HorizonsModifier.FUEL_POWER, 2.60)));
    public static final FrameShiftDrive FRAME_SHIFT_DRIVE_7_E = new FrameShiftDrive("FRAME_SHIFT_DRIVE_7_E", HorizonsBlueprintName.FRAME_SHIFT_DRIVE, ModuleSize.SIZE_7, ModuleClass.E, 633200, "Int_Hyperdrive_Size7_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 80.00), Map.entry(HorizonsModifier.INTEGRITY, 118.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.48), Map.entry(HorizonsModifier.BOOT_TIME, 10.0), Map.entry(HorizonsModifier.FSD_OPTIMISED_MASS, 1440.0), Map.entry(HorizonsModifier.FSD_HEAT_RATE, 43.0), Map.entry(HorizonsModifier.MAX_FUEL_PER_JUMP, 8.50), Map.entry(HorizonsModifier.FUEL_MULTIPLIER, 0.011), Map.entry(HorizonsModifier.FUEL_POWER, 2.75)));
    public static final FrameShiftDrive FRAME_SHIFT_DRIVE_7_D = new FrameShiftDrive("FRAME_SHIFT_DRIVE_7_D", HorizonsBlueprintName.FRAME_SHIFT_DRIVE, ModuleSize.SIZE_7, ModuleClass.D, 1899600, "Int_Hyperdrive_Size7_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 32.00), Map.entry(HorizonsModifier.INTEGRITY, 105.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.54), Map.entry(HorizonsModifier.BOOT_TIME, 10.0), Map.entry(HorizonsModifier.FSD_OPTIMISED_MASS, 1620.0), Map.entry(HorizonsModifier.FSD_HEAT_RATE, 43.0), Map.entry(HorizonsModifier.MAX_FUEL_PER_JUMP, 8.50), Map.entry(HorizonsModifier.FUEL_MULTIPLIER, 0.010), Map.entry(HorizonsModifier.FUEL_POWER, 2.75)));
    public static final FrameShiftDrive FRAME_SHIFT_DRIVE_7_C = new FrameShiftDrive("FRAME_SHIFT_DRIVE_7_C", HorizonsBlueprintName.FRAME_SHIFT_DRIVE, ModuleSize.SIZE_7, ModuleClass.C, 5698790, "Int_Hyperdrive_Size7_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 80.00), Map.entry(HorizonsModifier.INTEGRITY, 131.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.60), Map.entry(HorizonsModifier.BOOT_TIME, 10.0), Map.entry(HorizonsModifier.FSD_OPTIMISED_MASS, 1800.0), Map.entry(HorizonsModifier.FSD_HEAT_RATE, 43.0), Map.entry(HorizonsModifier.MAX_FUEL_PER_JUMP, 8.50), Map.entry(HorizonsModifier.FUEL_MULTIPLIER, 0.008), Map.entry(HorizonsModifier.FUEL_POWER, 2.75)));
    public static final FrameShiftDrive FRAME_SHIFT_DRIVE_7_B = new FrameShiftDrive("FRAME_SHIFT_DRIVE_7_B", HorizonsBlueprintName.FRAME_SHIFT_DRIVE, ModuleSize.SIZE_7, ModuleClass.B, 17096370, "Int_Hyperdrive_Size7_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 128.00), Map.entry(HorizonsModifier.INTEGRITY, 197.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.75), Map.entry(HorizonsModifier.BOOT_TIME, 10.0), Map.entry(HorizonsModifier.FSD_OPTIMISED_MASS, 2250.0), Map.entry(HorizonsModifier.FSD_HEAT_RATE, 43.0), Map.entry(HorizonsModifier.MAX_FUEL_PER_JUMP, 10.60), Map.entry(HorizonsModifier.FUEL_MULTIPLIER, 0.010), Map.entry(HorizonsModifier.FUEL_POWER, 2.75)));
    public static final FrameShiftDrive FRAME_SHIFT_DRIVE_7_A = new FrameShiftDrive("FRAME_SHIFT_DRIVE_7_A", HorizonsBlueprintName.FRAME_SHIFT_DRIVE, ModuleSize.SIZE_7, ModuleClass.A, 51289110, "Int_Hyperdrive_Size7_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 80.00), Map.entry(HorizonsModifier.INTEGRITY, 164.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.90), Map.entry(HorizonsModifier.BOOT_TIME, 10.0), Map.entry(HorizonsModifier.FSD_OPTIMISED_MASS, 2700.0), Map.entry(HorizonsModifier.FSD_HEAT_RATE, 43.0), Map.entry(HorizonsModifier.MAX_FUEL_PER_JUMP, 12.80), Map.entry(HorizonsModifier.FUEL_MULTIPLIER, 0.012), Map.entry(HorizonsModifier.FUEL_POWER, 2.75)));
    public static final FrameShiftDrive FRAME_SHIFT_DRIVE_3_A_V1_PRE = new FrameShiftDrive("FRAME_SHIFT_DRIVE_3_A_V1_PRE", HorizonsBlueprintName.FRAME_SHIFT_DRIVE_PRE, ModuleSize.SIZE_3, ModuleClass.A, 0, "Int_Hyperdrive_Size3_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 5.00), Map.entry(HorizonsModifier.INTEGRITY, 80.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.45), Map.entry(HorizonsModifier.BOOT_TIME, 10.0), Map.entry(HorizonsModifier.FSD_OPTIMISED_MASS, 150.0), Map.entry(HorizonsModifier.FSD_HEAT_RATE, 14.0), Map.entry(HorizonsModifier.MAX_FUEL_PER_JUMP, 1.80), Map.entry(HorizonsModifier.FUEL_MULTIPLIER, 0.012), Map.entry(HorizonsModifier.FUEL_POWER, 2.15)));
    public static final FrameShiftDrive FRAME_SHIFT_DRIVE_4_A_V1_PRE = new FrameShiftDrive("FRAME_SHIFT_DRIVE_4_A_V1_PRE", HorizonsBlueprintName.FRAME_SHIFT_DRIVE_PRE, ModuleSize.SIZE_4, ModuleClass.A, 0, "Int_Hyperdrive_Size4_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 10.00), Map.entry(HorizonsModifier.INTEGRITY, 100.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.45), Map.entry(HorizonsModifier.BOOT_TIME, 10.0), Map.entry(HorizonsModifier.FSD_OPTIMISED_MASS, 525.0), Map.entry(HorizonsModifier.FSD_HEAT_RATE, 18.0), Map.entry(HorizonsModifier.MAX_FUEL_PER_JUMP, 3.00), Map.entry(HorizonsModifier.FUEL_MULTIPLIER, 0.012), Map.entry(HorizonsModifier.FUEL_POWER, 2.30)));
    public static final FrameShiftDrive FRAME_SHIFT_DRIVE_5_A_V1_PRE = new FrameShiftDrive("FRAME_SHIFT_DRIVE_5_A_V1_PRE", HorizonsBlueprintName.FRAME_SHIFT_DRIVE_PRE, ModuleSize.SIZE_5, ModuleClass.A, 0, "Int_Hyperdrive_Size5_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 20.00), Map.entry(HorizonsModifier.INTEGRITY, 120.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.60), Map.entry(HorizonsModifier.BOOT_TIME, 10.0), Map.entry(HorizonsModifier.FSD_OPTIMISED_MASS, 1050.0), Map.entry(HorizonsModifier.FSD_HEAT_RATE, 27.0), Map.entry(HorizonsModifier.MAX_FUEL_PER_JUMP, 5.00), Map.entry(HorizonsModifier.FUEL_MULTIPLIER, 0.012), Map.entry(HorizonsModifier.FUEL_POWER, 2.45)));
    public static final FrameShiftDrive FRAME_SHIFT_DRIVE_6_A_V1_PRE = new FrameShiftDrive("FRAME_SHIFT_DRIVE_6_A_V1_PRE", HorizonsBlueprintName.FRAME_SHIFT_DRIVE_PRE, ModuleSize.SIZE_6, ModuleClass.A, 0, "Int_Hyperdrive_Size6_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 40.00), Map.entry(HorizonsModifier.INTEGRITY, 141.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.75), Map.entry(HorizonsModifier.BOOT_TIME, 10.0), Map.entry(HorizonsModifier.FSD_OPTIMISED_MASS, 1800.0), Map.entry(HorizonsModifier.FSD_HEAT_RATE, 37.0), Map.entry(HorizonsModifier.MAX_FUEL_PER_JUMP, 8.00), Map.entry(HorizonsModifier.FUEL_MULTIPLIER, 0.012), Map.entry(HorizonsModifier.FUEL_POWER, 2.60)));

    static {
        FRAME_SHIFT_DRIVE_3_A_V1_PRE.getModifications().add(
                new Modification(HorizonsBlueprintType.INCREASED_FSD_RANGE_FASTER_FSD_BOOT_SEQUENCE, 1.0, HorizonsBlueprintGrade.GRADE_5)
        );
        FRAME_SHIFT_DRIVE_4_A_V1_PRE.getModifications().add(
                new Modification(HorizonsBlueprintType.INCREASED_FSD_RANGE_FASTER_FSD_BOOT_SEQUENCE, 1.0, HorizonsBlueprintGrade.GRADE_5)
        );
        FRAME_SHIFT_DRIVE_5_A_V1_PRE.getModifications().add(
                new Modification(HorizonsBlueprintType.INCREASED_FSD_RANGE_FASTER_FSD_BOOT_SEQUENCE, 1.0, HorizonsBlueprintGrade.GRADE_5)
        );
        FRAME_SHIFT_DRIVE_6_A_V1_PRE.getModifications().add(
                new Modification(HorizonsBlueprintType.INCREASED_FSD_RANGE_FASTER_FSD_BOOT_SEQUENCE, 1.0, HorizonsBlueprintGrade.GRADE_5)
        );
    }

    public static final List<FrameShiftDrive> FRAME_SHIFT_DRIVES = List.of(
            FRAME_SHIFT_DRIVE_2_E,
            FRAME_SHIFT_DRIVE_2_D,
            FRAME_SHIFT_DRIVE_2_C,
            FRAME_SHIFT_DRIVE_2_B,
            FRAME_SHIFT_DRIVE_2_A,
            FRAME_SHIFT_DRIVE_3_E,
            FRAME_SHIFT_DRIVE_3_D,
            FRAME_SHIFT_DRIVE_3_C,
            FRAME_SHIFT_DRIVE_3_B,
            FRAME_SHIFT_DRIVE_3_A,
            FRAME_SHIFT_DRIVE_4_E,
            FRAME_SHIFT_DRIVE_4_D,
            FRAME_SHIFT_DRIVE_4_C,
            FRAME_SHIFT_DRIVE_4_B,
            FRAME_SHIFT_DRIVE_4_A,
            FRAME_SHIFT_DRIVE_5_E,
            FRAME_SHIFT_DRIVE_5_D,
            FRAME_SHIFT_DRIVE_5_C,
            FRAME_SHIFT_DRIVE_5_B,
            FRAME_SHIFT_DRIVE_5_A,
            FRAME_SHIFT_DRIVE_6_E,
            FRAME_SHIFT_DRIVE_6_D,
            FRAME_SHIFT_DRIVE_6_C,
            FRAME_SHIFT_DRIVE_6_B,
            FRAME_SHIFT_DRIVE_6_A,
            FRAME_SHIFT_DRIVE_7_E,
            FRAME_SHIFT_DRIVE_7_D,
            FRAME_SHIFT_DRIVE_7_C,
            FRAME_SHIFT_DRIVE_7_B,
            FRAME_SHIFT_DRIVE_7_A,
            FRAME_SHIFT_DRIVE_3_A_V1_PRE,
            FRAME_SHIFT_DRIVE_4_A_V1_PRE,
            FRAME_SHIFT_DRIVE_5_A_V1_PRE,
            FRAME_SHIFT_DRIVE_6_A_V1_PRE
    );

    public static final List<HorizonsModifier> HIDDEN_STATS = List.of(HorizonsModifier.FUEL_MULTIPLIER, HorizonsModifier.FUEL_POWER);
    private FrameShiftDrive(final String id, final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final long basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, basePrice, internalName, attributes);
    }

    private FrameShiftDrive(final FrameShiftDrive frameShiftDrive) {
        super(frameShiftDrive);
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedBlueprints() {
        if (isPreEngineered()) {
            return Collections.emptyList();
        }
        return FSDBlueprints.BLUEPRINTS.keySet().stream().toList();
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedExperimentalEffects() {
        return ExperimentalEffectBlueprints.FRAME_SHIFT_DRIVE.keySet().stream().toList();
    }

    @Override
    public FrameShiftDrive Clone() {
        return new FrameShiftDrive(this);
    }

    @Override
    public String getClarifier() {
        return isPreEngineered() ? " " + LocaleService.getLocalizedStringForCurrentLocale(getName().getLocalizationKey()) : "";
    }

    @Override
    public boolean isPreEngineered() {
        return HorizonsBlueprintName.FRAME_SHIFT_DRIVE_PRE.equals(this.getName());
    }


    @Override
    public boolean isHiddenStat(HorizonsModifier modifier) {
        if(HIDDEN_STATS.contains(modifier)){
            return true;
        }
        return super.isHiddenStat(modifier);
    }
    @Override
    public int getGrouping() {
        return getModuleSize().intValue() * 10 + (isPreEngineered() ? 1 : 0);
    }
}
