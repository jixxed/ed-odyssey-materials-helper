package nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals;

import nl.jixxed.eliteodysseymaterials.constants.horizons.optional_internals.AutoFieldMaintenanceUnitBlueprints;
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

public class AutoFieldMaintenanceUnit extends OptionalModule {
    public static final AutoFieldMaintenanceUnit AUTO_FIELD_MAINTENANCE_UNIT_1_E = new AutoFieldMaintenanceUnit("AUTO_FIELD_MAINTENANCE_UNIT_1_E", HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, ModuleSize.SIZE_1, ModuleClass.E, false, 10000, "Int_Repairer_Size1_Class1", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  32.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.54), Map.entry(HorizonsModifier.BOOT_TIME,  9.0), Map.entry(HorizonsModifier.REPAIR_CAPACITY,  1000.0), Map.entry(HorizonsModifier.CONSUMPTION,  10.0), Map.entry(HorizonsModifier.REPAIR_RATING,  0.012), Map.entry(HorizonsModifier.AMMO_COST,  1.0)));
    public static final AutoFieldMaintenanceUnit AUTO_FIELD_MAINTENANCE_UNIT_1_D = new AutoFieldMaintenanceUnit("AUTO_FIELD_MAINTENANCE_UNIT_1_D", HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, ModuleSize.SIZE_1, ModuleClass.D, false, 30000, "Int_Repairer_Size1_Class2", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  24.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.72), Map.entry(HorizonsModifier.BOOT_TIME,  9.0), Map.entry(HorizonsModifier.REPAIR_CAPACITY,  900.0), Map.entry(HorizonsModifier.CONSUMPTION,  10.0), Map.entry(HorizonsModifier.REPAIR_RATING,  0.016), Map.entry(HorizonsModifier.AMMO_COST,  1.0)));
    public static final AutoFieldMaintenanceUnit AUTO_FIELD_MAINTENANCE_UNIT_1_C = new AutoFieldMaintenanceUnit("AUTO_FIELD_MAINTENANCE_UNIT_1_C", HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, ModuleSize.SIZE_1, ModuleClass.C, false, 90000, "Int_Repairer_Size1_Class3", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  40.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.9), Map.entry(HorizonsModifier.BOOT_TIME,  9.0), Map.entry(HorizonsModifier.REPAIR_CAPACITY,  1000.0), Map.entry(HorizonsModifier.CONSUMPTION,  10.0), Map.entry(HorizonsModifier.REPAIR_RATING,  0.02), Map.entry(HorizonsModifier.AMMO_COST,  1.0)));
    public static final AutoFieldMaintenanceUnit AUTO_FIELD_MAINTENANCE_UNIT_1_B = new AutoFieldMaintenanceUnit("AUTO_FIELD_MAINTENANCE_UNIT_1_B", HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, ModuleSize.SIZE_1, ModuleClass.B, false, 270000, "Int_Repairer_Size1_Class4", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  56.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.04), Map.entry(HorizonsModifier.BOOT_TIME,  9.0), Map.entry(HorizonsModifier.REPAIR_CAPACITY,  1200.0), Map.entry(HorizonsModifier.CONSUMPTION,  10.0), Map.entry(HorizonsModifier.REPAIR_RATING,  0.023), Map.entry(HorizonsModifier.AMMO_COST,  1.0)));
    public static final AutoFieldMaintenanceUnit AUTO_FIELD_MAINTENANCE_UNIT_1_A = new AutoFieldMaintenanceUnit("AUTO_FIELD_MAINTENANCE_UNIT_1_A", HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, ModuleSize.SIZE_1, ModuleClass.A, false, 810000, "Int_Repairer_Size1_Class5", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  46.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.26), Map.entry(HorizonsModifier.BOOT_TIME,  9.0), Map.entry(HorizonsModifier.REPAIR_CAPACITY,  1100.0), Map.entry(HorizonsModifier.CONSUMPTION,  10.0), Map.entry(HorizonsModifier.REPAIR_RATING,  0.028), Map.entry(HorizonsModifier.AMMO_COST,  1.0)));
    public static final AutoFieldMaintenanceUnit AUTO_FIELD_MAINTENANCE_UNIT_2_E = new AutoFieldMaintenanceUnit("AUTO_FIELD_MAINTENANCE_UNIT_2_E", HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, ModuleSize.SIZE_2, ModuleClass.E, false, 18000, "Int_Repairer_Size2_Class1", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  41.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.68), Map.entry(HorizonsModifier.BOOT_TIME,  9.0), Map.entry(HorizonsModifier.REPAIR_CAPACITY,  2300.0), Map.entry(HorizonsModifier.CONSUMPTION,  10.0), Map.entry(HorizonsModifier.REPAIR_RATING,  0.012), Map.entry(HorizonsModifier.AMMO_COST,  1.0)));
    public static final AutoFieldMaintenanceUnit AUTO_FIELD_MAINTENANCE_UNIT_2_D = new AutoFieldMaintenanceUnit("AUTO_FIELD_MAINTENANCE_UNIT_2_D", HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, ModuleSize.SIZE_2, ModuleClass.D, false, 54000, "Int_Repairer_Size2_Class2", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  31.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.9), Map.entry(HorizonsModifier.BOOT_TIME,  9.0), Map.entry(HorizonsModifier.REPAIR_CAPACITY,  2100.0), Map.entry(HorizonsModifier.CONSUMPTION,  10.0), Map.entry(HorizonsModifier.REPAIR_RATING,  0.016), Map.entry(HorizonsModifier.AMMO_COST,  1.0)));
    public static final AutoFieldMaintenanceUnit AUTO_FIELD_MAINTENANCE_UNIT_2_C = new AutoFieldMaintenanceUnit("AUTO_FIELD_MAINTENANCE_UNIT_2_C", HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, ModuleSize.SIZE_2, ModuleClass.C, false, 162000, "Int_Repairer_Size2_Class3", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  51.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.13), Map.entry(HorizonsModifier.BOOT_TIME,  9.0), Map.entry(HorizonsModifier.REPAIR_CAPACITY,  2300.0), Map.entry(HorizonsModifier.CONSUMPTION,  10.0), Map.entry(HorizonsModifier.REPAIR_RATING,  0.02), Map.entry(HorizonsModifier.AMMO_COST,  1.0)));
    public static final AutoFieldMaintenanceUnit AUTO_FIELD_MAINTENANCE_UNIT_2_B = new AutoFieldMaintenanceUnit("AUTO_FIELD_MAINTENANCE_UNIT_2_B", HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, ModuleSize.SIZE_2, ModuleClass.B, false, 486000, "Int_Repairer_Size2_Class4", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  71.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.29), Map.entry(HorizonsModifier.BOOT_TIME,  9.0), Map.entry(HorizonsModifier.REPAIR_CAPACITY,  2800.0), Map.entry(HorizonsModifier.CONSUMPTION,  10.0), Map.entry(HorizonsModifier.REPAIR_RATING,  0.023), Map.entry(HorizonsModifier.AMMO_COST,  1.0)));
    public static final AutoFieldMaintenanceUnit AUTO_FIELD_MAINTENANCE_UNIT_2_A = new AutoFieldMaintenanceUnit("AUTO_FIELD_MAINTENANCE_UNIT_2_A", HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, ModuleSize.SIZE_2, ModuleClass.A, false, 1458000, "Int_Repairer_Size2_Class5", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  59.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.58), Map.entry(HorizonsModifier.BOOT_TIME,  9.0), Map.entry(HorizonsModifier.REPAIR_CAPACITY,  2500.0), Map.entry(HorizonsModifier.CONSUMPTION,  10.0), Map.entry(HorizonsModifier.REPAIR_RATING,  0.028), Map.entry(HorizonsModifier.AMMO_COST,  1.0)));
    public static final AutoFieldMaintenanceUnit AUTO_FIELD_MAINTENANCE_UNIT_3_E = new AutoFieldMaintenanceUnit("AUTO_FIELD_MAINTENANCE_UNIT_3_E", HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, ModuleSize.SIZE_3, ModuleClass.E, false, 32400, "Int_Repairer_Size3_Class1", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  51.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.81), Map.entry(HorizonsModifier.BOOT_TIME,  9.0), Map.entry(HorizonsModifier.REPAIR_CAPACITY,  3600.0), Map.entry(HorizonsModifier.CONSUMPTION,  10.0), Map.entry(HorizonsModifier.REPAIR_RATING,  0.012), Map.entry(HorizonsModifier.AMMO_COST,  1.0)));
    public static final AutoFieldMaintenanceUnit AUTO_FIELD_MAINTENANCE_UNIT_3_D = new AutoFieldMaintenanceUnit("AUTO_FIELD_MAINTENANCE_UNIT_3_D", HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, ModuleSize.SIZE_3, ModuleClass.D, false, 97200, "Int_Repairer_Size3_Class2", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  38.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.08), Map.entry(HorizonsModifier.BOOT_TIME,  9.0), Map.entry(HorizonsModifier.REPAIR_CAPACITY,  3200.0), Map.entry(HorizonsModifier.CONSUMPTION,  10.0), Map.entry(HorizonsModifier.REPAIR_RATING,  0.016), Map.entry(HorizonsModifier.AMMO_COST,  1.0)));
    public static final AutoFieldMaintenanceUnit AUTO_FIELD_MAINTENANCE_UNIT_3_C = new AutoFieldMaintenanceUnit("AUTO_FIELD_MAINTENANCE_UNIT_3_C", HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, ModuleSize.SIZE_3, ModuleClass.C, false, 291600, "Int_Repairer_Size3_Class3", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  64.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.35), Map.entry(HorizonsModifier.BOOT_TIME,  9.0), Map.entry(HorizonsModifier.REPAIR_CAPACITY,  3600.0), Map.entry(HorizonsModifier.CONSUMPTION,  10.0), Map.entry(HorizonsModifier.REPAIR_RATING,  0.02), Map.entry(HorizonsModifier.AMMO_COST,  1.0)));
    public static final AutoFieldMaintenanceUnit AUTO_FIELD_MAINTENANCE_UNIT_3_B = new AutoFieldMaintenanceUnit("AUTO_FIELD_MAINTENANCE_UNIT_3_B", HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, ModuleSize.SIZE_3, ModuleClass.B, false, 874800, "Int_Repairer_Size3_Class4", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  90.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.55), Map.entry(HorizonsModifier.BOOT_TIME,  9.0), Map.entry(HorizonsModifier.REPAIR_CAPACITY,  4300.0), Map.entry(HorizonsModifier.CONSUMPTION,  10.0), Map.entry(HorizonsModifier.REPAIR_RATING,  0.023), Map.entry(HorizonsModifier.AMMO_COST,  1.0)));
    public static final AutoFieldMaintenanceUnit AUTO_FIELD_MAINTENANCE_UNIT_3_A = new AutoFieldMaintenanceUnit("AUTO_FIELD_MAINTENANCE_UNIT_3_A", HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, ModuleSize.SIZE_3, ModuleClass.A, false, 2624400, "Int_Repairer_Size3_Class5", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  74.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.89), Map.entry(HorizonsModifier.BOOT_TIME,  9.0), Map.entry(HorizonsModifier.REPAIR_CAPACITY,  4000.0), Map.entry(HorizonsModifier.CONSUMPTION,  10.0), Map.entry(HorizonsModifier.REPAIR_RATING,  0.028), Map.entry(HorizonsModifier.AMMO_COST,  1.0)));
    public static final AutoFieldMaintenanceUnit AUTO_FIELD_MAINTENANCE_UNIT_4_E = new AutoFieldMaintenanceUnit("AUTO_FIELD_MAINTENANCE_UNIT_4_E", HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, ModuleSize.SIZE_4, ModuleClass.E, false, 58320, "Int_Repairer_Size4_Class1", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  64.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.99), Map.entry(HorizonsModifier.BOOT_TIME,  9.0), Map.entry(HorizonsModifier.REPAIR_CAPACITY,  4900.0), Map.entry(HorizonsModifier.CONSUMPTION,  10.0), Map.entry(HorizonsModifier.REPAIR_RATING,  0.012), Map.entry(HorizonsModifier.AMMO_COST,  1.0)));
    public static final AutoFieldMaintenanceUnit AUTO_FIELD_MAINTENANCE_UNIT_4_D = new AutoFieldMaintenanceUnit("AUTO_FIELD_MAINTENANCE_UNIT_4_D", HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, ModuleSize.SIZE_4, ModuleClass.D, false, 174960, "Int_Repairer_Size4_Class2", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  48.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.32), Map.entry(HorizonsModifier.BOOT_TIME,  9.0), Map.entry(HorizonsModifier.REPAIR_CAPACITY,  4400.0), Map.entry(HorizonsModifier.CONSUMPTION,  10.0), Map.entry(HorizonsModifier.REPAIR_RATING,  0.016), Map.entry(HorizonsModifier.AMMO_COST,  1.0)));
    public static final AutoFieldMaintenanceUnit AUTO_FIELD_MAINTENANCE_UNIT_4_C = new AutoFieldMaintenanceUnit("AUTO_FIELD_MAINTENANCE_UNIT_4_C", HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, ModuleSize.SIZE_4, ModuleClass.C, false, 524880, "Int_Repairer_Size4_Class3", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  80.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.65), Map.entry(HorizonsModifier.BOOT_TIME,  9.0), Map.entry(HorizonsModifier.REPAIR_CAPACITY,  4900.0), Map.entry(HorizonsModifier.CONSUMPTION,  10.0), Map.entry(HorizonsModifier.REPAIR_RATING,  0.02), Map.entry(HorizonsModifier.AMMO_COST,  1.0)));
    public static final AutoFieldMaintenanceUnit AUTO_FIELD_MAINTENANCE_UNIT_4_B = new AutoFieldMaintenanceUnit("AUTO_FIELD_MAINTENANCE_UNIT_4_B", HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, ModuleSize.SIZE_4, ModuleClass.B, false, 1574640, "Int_Repairer_Size4_Class4", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  112.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.9), Map.entry(HorizonsModifier.BOOT_TIME,  9.0), Map.entry(HorizonsModifier.REPAIR_CAPACITY,  5900.0), Map.entry(HorizonsModifier.CONSUMPTION,  10.0), Map.entry(HorizonsModifier.REPAIR_RATING,  0.023), Map.entry(HorizonsModifier.AMMO_COST,  1.0)));
    public static final AutoFieldMaintenanceUnit AUTO_FIELD_MAINTENANCE_UNIT_4_A = new AutoFieldMaintenanceUnit("AUTO_FIELD_MAINTENANCE_UNIT_4_A", HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, ModuleSize.SIZE_4, ModuleClass.A, false, 4723920, "Int_Repairer_Size4_Class5", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  92.0), Map.entry(HorizonsModifier.POWER_DRAW,  2.31), Map.entry(HorizonsModifier.BOOT_TIME,  9.0), Map.entry(HorizonsModifier.REPAIR_CAPACITY,  5400.0), Map.entry(HorizonsModifier.CONSUMPTION,  10.0), Map.entry(HorizonsModifier.REPAIR_RATING,  0.028), Map.entry(HorizonsModifier.AMMO_COST,  1.0)));
    public static final AutoFieldMaintenanceUnit AUTO_FIELD_MAINTENANCE_UNIT_5_E = new AutoFieldMaintenanceUnit("AUTO_FIELD_MAINTENANCE_UNIT_5_E", HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, ModuleSize.SIZE_5, ModuleClass.E, false, 104980, "Int_Repairer_Size5_Class1", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  77.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.17), Map.entry(HorizonsModifier.BOOT_TIME,  9.0), Map.entry(HorizonsModifier.REPAIR_CAPACITY,  6100.0), Map.entry(HorizonsModifier.CONSUMPTION,  10.0), Map.entry(HorizonsModifier.REPAIR_RATING,  0.012), Map.entry(HorizonsModifier.AMMO_COST,  1.0)));
    public static final AutoFieldMaintenanceUnit AUTO_FIELD_MAINTENANCE_UNIT_5_D = new AutoFieldMaintenanceUnit("AUTO_FIELD_MAINTENANCE_UNIT_5_D", HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, ModuleSize.SIZE_5, ModuleClass.D, false, 314930, "Int_Repairer_Size5_Class2", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  58.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.56), Map.entry(HorizonsModifier.BOOT_TIME,  9.0), Map.entry(HorizonsModifier.REPAIR_CAPACITY,  5500.0), Map.entry(HorizonsModifier.CONSUMPTION,  10.0), Map.entry(HorizonsModifier.REPAIR_RATING,  0.016), Map.entry(HorizonsModifier.AMMO_COST,  1.0)));
    public static final AutoFieldMaintenanceUnit AUTO_FIELD_MAINTENANCE_UNIT_5_C = new AutoFieldMaintenanceUnit("AUTO_FIELD_MAINTENANCE_UNIT_5_C", HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, ModuleSize.SIZE_5, ModuleClass.C, false, 944780, "Int_Repairer_Size5_Class3", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  96.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.95), Map.entry(HorizonsModifier.BOOT_TIME,  9.0), Map.entry(HorizonsModifier.REPAIR_CAPACITY,  6100.0), Map.entry(HorizonsModifier.CONSUMPTION,  10.0), Map.entry(HorizonsModifier.REPAIR_RATING,  0.02), Map.entry(HorizonsModifier.AMMO_COST,  1.0)));
    public static final AutoFieldMaintenanceUnit AUTO_FIELD_MAINTENANCE_UNIT_5_B = new AutoFieldMaintenanceUnit("AUTO_FIELD_MAINTENANCE_UNIT_5_B", HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, ModuleSize.SIZE_5, ModuleClass.B, false, 2834350, "Int_Repairer_Size5_Class4", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  134.0), Map.entry(HorizonsModifier.POWER_DRAW,  2.24), Map.entry(HorizonsModifier.BOOT_TIME,  9.0), Map.entry(HorizonsModifier.REPAIR_CAPACITY,  7300.0), Map.entry(HorizonsModifier.CONSUMPTION,  10.0), Map.entry(HorizonsModifier.REPAIR_RATING,  0.023), Map.entry(HorizonsModifier.AMMO_COST,  1.0)));
    public static final AutoFieldMaintenanceUnit AUTO_FIELD_MAINTENANCE_UNIT_5_A = new AutoFieldMaintenanceUnit("AUTO_FIELD_MAINTENANCE_UNIT_5_A", HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, ModuleSize.SIZE_5, ModuleClass.A, false, 8503060, "Int_Repairer_Size5_Class5", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  110.0), Map.entry(HorizonsModifier.POWER_DRAW,  2.73), Map.entry(HorizonsModifier.BOOT_TIME,  9.0), Map.entry(HorizonsModifier.REPAIR_CAPACITY,  6700.0), Map.entry(HorizonsModifier.CONSUMPTION,  10.0), Map.entry(HorizonsModifier.REPAIR_RATING,  0.028), Map.entry(HorizonsModifier.AMMO_COST,  1.0)));
    public static final AutoFieldMaintenanceUnit AUTO_FIELD_MAINTENANCE_UNIT_6_E = new AutoFieldMaintenanceUnit("AUTO_FIELD_MAINTENANCE_UNIT_6_E", HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, ModuleSize.SIZE_6, ModuleClass.E, false, 188960, "Int_Repairer_Size6_Class1", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  90.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.4), Map.entry(HorizonsModifier.BOOT_TIME,  9.0), Map.entry(HorizonsModifier.REPAIR_CAPACITY,  7400.0), Map.entry(HorizonsModifier.CONSUMPTION,  10.0), Map.entry(HorizonsModifier.REPAIR_RATING,  0.012), Map.entry(HorizonsModifier.AMMO_COST,  1.0)));
    public static final AutoFieldMaintenanceUnit AUTO_FIELD_MAINTENANCE_UNIT_6_D = new AutoFieldMaintenanceUnit("AUTO_FIELD_MAINTENANCE_UNIT_6_D", HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, ModuleSize.SIZE_6, ModuleClass.D, false, 566870, "Int_Repairer_Size6_Class2", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  68.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.86), Map.entry(HorizonsModifier.BOOT_TIME,  9.0), Map.entry(HorizonsModifier.REPAIR_CAPACITY,  6700.0), Map.entry(HorizonsModifier.CONSUMPTION,  10.0), Map.entry(HorizonsModifier.REPAIR_RATING,  0.016), Map.entry(HorizonsModifier.AMMO_COST,  1.0)));
    public static final AutoFieldMaintenanceUnit AUTO_FIELD_MAINTENANCE_UNIT_6_C = new AutoFieldMaintenanceUnit("AUTO_FIELD_MAINTENANCE_UNIT_6_C", HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, ModuleSize.SIZE_6, ModuleClass.C, false, 1700610, "Int_Repairer_Size6_Class3", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  113.0), Map.entry(HorizonsModifier.POWER_DRAW,  2.33), Map.entry(HorizonsModifier.BOOT_TIME,  9.0), Map.entry(HorizonsModifier.REPAIR_CAPACITY,  7400.0), Map.entry(HorizonsModifier.CONSUMPTION,  10.0), Map.entry(HorizonsModifier.REPAIR_RATING,  0.02), Map.entry(HorizonsModifier.AMMO_COST,  1.0)));
    public static final AutoFieldMaintenanceUnit AUTO_FIELD_MAINTENANCE_UNIT_6_B = new AutoFieldMaintenanceUnit("AUTO_FIELD_MAINTENANCE_UNIT_6_B", HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, ModuleSize.SIZE_6, ModuleClass.B, false, 5101830, "Int_Repairer_Size6_Class4", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  158.0), Map.entry(HorizonsModifier.POWER_DRAW,  2.67), Map.entry(HorizonsModifier.BOOT_TIME,  9.0), Map.entry(HorizonsModifier.REPAIR_CAPACITY,  8900.0), Map.entry(HorizonsModifier.CONSUMPTION,  10.0), Map.entry(HorizonsModifier.REPAIR_RATING,  0.023), Map.entry(HorizonsModifier.AMMO_COST,  1.0)));
    public static final AutoFieldMaintenanceUnit AUTO_FIELD_MAINTENANCE_UNIT_6_A = new AutoFieldMaintenanceUnit("AUTO_FIELD_MAINTENANCE_UNIT_6_A", HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, ModuleSize.SIZE_6, ModuleClass.A, false, 15305500, "Int_Repairer_Size6_Class5", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  130.0), Map.entry(HorizonsModifier.POWER_DRAW,  3.26), Map.entry(HorizonsModifier.BOOT_TIME,  9.0), Map.entry(HorizonsModifier.REPAIR_CAPACITY,  8100.0), Map.entry(HorizonsModifier.CONSUMPTION,  10.0), Map.entry(HorizonsModifier.REPAIR_RATING,  0.028), Map.entry(HorizonsModifier.AMMO_COST,  1.0)));
    public static final AutoFieldMaintenanceUnit AUTO_FIELD_MAINTENANCE_UNIT_7_E = new AutoFieldMaintenanceUnit("AUTO_FIELD_MAINTENANCE_UNIT_7_E", HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, ModuleSize.SIZE_7, ModuleClass.E, false, 340120, "Int_Repairer_Size7_Class1", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  105.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.58), Map.entry(HorizonsModifier.BOOT_TIME,  9.0), Map.entry(HorizonsModifier.REPAIR_CAPACITY,  8700.0), Map.entry(HorizonsModifier.CONSUMPTION,  10.0), Map.entry(HorizonsModifier.REPAIR_RATING,  0.012), Map.entry(HorizonsModifier.AMMO_COST,  1.0)));
    public static final AutoFieldMaintenanceUnit AUTO_FIELD_MAINTENANCE_UNIT_7_D = new AutoFieldMaintenanceUnit("AUTO_FIELD_MAINTENANCE_UNIT_7_D", HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, ModuleSize.SIZE_7, ModuleClass.D, false, 1020370, "Int_Repairer_Size7_Class2", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  79.0), Map.entry(HorizonsModifier.POWER_DRAW,  2.1), Map.entry(HorizonsModifier.BOOT_TIME,  9.0), Map.entry(HorizonsModifier.REPAIR_CAPACITY,  7800.0), Map.entry(HorizonsModifier.CONSUMPTION,  10.0), Map.entry(HorizonsModifier.REPAIR_RATING,  0.016), Map.entry(HorizonsModifier.AMMO_COST,  1.0)));
    public static final AutoFieldMaintenanceUnit AUTO_FIELD_MAINTENANCE_UNIT_7_C = new AutoFieldMaintenanceUnit("AUTO_FIELD_MAINTENANCE_UNIT_7_C", HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, ModuleSize.SIZE_7, ModuleClass.C, false, 3061100, "Int_Repairer_Size7_Class3", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  131.0), Map.entry(HorizonsModifier.POWER_DRAW,  2.63), Map.entry(HorizonsModifier.BOOT_TIME,  9.0), Map.entry(HorizonsModifier.REPAIR_CAPACITY,  8700.0), Map.entry(HorizonsModifier.CONSUMPTION,  10.0), Map.entry(HorizonsModifier.REPAIR_RATING,  0.02), Map.entry(HorizonsModifier.AMMO_COST,  1.0)));
    public static final AutoFieldMaintenanceUnit AUTO_FIELD_MAINTENANCE_UNIT_7_B = new AutoFieldMaintenanceUnit("AUTO_FIELD_MAINTENANCE_UNIT_7_B", HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, ModuleSize.SIZE_7, ModuleClass.B, false, 9183300, "Int_Repairer_Size7_Class4", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  183.0), Map.entry(HorizonsModifier.POWER_DRAW,  3.02), Map.entry(HorizonsModifier.BOOT_TIME,  9.0), Map.entry(HorizonsModifier.REPAIR_CAPACITY,  10400.0), Map.entry(HorizonsModifier.CONSUMPTION,  10.0), Map.entry(HorizonsModifier.REPAIR_RATING,  0.023), Map.entry(HorizonsModifier.AMMO_COST,  1.0)));
    public static final AutoFieldMaintenanceUnit AUTO_FIELD_MAINTENANCE_UNIT_7_A = new AutoFieldMaintenanceUnit("AUTO_FIELD_MAINTENANCE_UNIT_7_A", HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, ModuleSize.SIZE_7, ModuleClass.A, false, 27549900, "Int_Repairer_Size7_Class5", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  151.0), Map.entry(HorizonsModifier.POWER_DRAW,  3.68), Map.entry(HorizonsModifier.BOOT_TIME,  9.0), Map.entry(HorizonsModifier.REPAIR_CAPACITY,  9600.0), Map.entry(HorizonsModifier.CONSUMPTION,  10.0), Map.entry(HorizonsModifier.REPAIR_RATING,  0.028), Map.entry(HorizonsModifier.AMMO_COST,  1.0)));
    public static final AutoFieldMaintenanceUnit AUTO_FIELD_MAINTENANCE_UNIT_8_E = new AutoFieldMaintenanceUnit("AUTO_FIELD_MAINTENANCE_UNIT_8_E", HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, ModuleSize.SIZE_8, ModuleClass.E, false, 612220, "Int_Repairer_Size8_Class1", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  120.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.8), Map.entry(HorizonsModifier.BOOT_TIME,  9.0), Map.entry(HorizonsModifier.REPAIR_CAPACITY,  10000.0), Map.entry(HorizonsModifier.CONSUMPTION,  10.0), Map.entry(HorizonsModifier.REPAIR_RATING,  0.012), Map.entry(HorizonsModifier.AMMO_COST,  1.0)));
    public static final AutoFieldMaintenanceUnit AUTO_FIELD_MAINTENANCE_UNIT_8_D = new AutoFieldMaintenanceUnit("AUTO_FIELD_MAINTENANCE_UNIT_8_D", HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, ModuleSize.SIZE_8, ModuleClass.D, false, 1836660, "Int_Repairer_Size8_Class2", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  90.0), Map.entry(HorizonsModifier.POWER_DRAW,  2.4), Map.entry(HorizonsModifier.BOOT_TIME,  9.0), Map.entry(HorizonsModifier.REPAIR_CAPACITY,  9000.0), Map.entry(HorizonsModifier.CONSUMPTION,  10.0), Map.entry(HorizonsModifier.REPAIR_RATING,  0.016), Map.entry(HorizonsModifier.AMMO_COST,  1.0)));
    public static final AutoFieldMaintenanceUnit AUTO_FIELD_MAINTENANCE_UNIT_8_C = new AutoFieldMaintenanceUnit("AUTO_FIELD_MAINTENANCE_UNIT_8_C", HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, ModuleSize.SIZE_8, ModuleClass.C, false, 5509980, "Int_Repairer_Size8_Class3", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  150.0), Map.entry(HorizonsModifier.POWER_DRAW,  3.0), Map.entry(HorizonsModifier.BOOT_TIME,  9.0), Map.entry(HorizonsModifier.REPAIR_CAPACITY,  10000.0), Map.entry(HorizonsModifier.CONSUMPTION,  10.0), Map.entry(HorizonsModifier.REPAIR_RATING,  0.02), Map.entry(HorizonsModifier.AMMO_COST,  1.0)));
    public static final AutoFieldMaintenanceUnit AUTO_FIELD_MAINTENANCE_UNIT_8_B = new AutoFieldMaintenanceUnit("AUTO_FIELD_MAINTENANCE_UNIT_8_B", HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, ModuleSize.SIZE_8, ModuleClass.B, false, 16529940, "Int_Repairer_Size8_Class4", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  210.0), Map.entry(HorizonsModifier.POWER_DRAW,  3.45), Map.entry(HorizonsModifier.BOOT_TIME,  9.0), Map.entry(HorizonsModifier.REPAIR_CAPACITY,  12000.0), Map.entry(HorizonsModifier.CONSUMPTION,  10.0), Map.entry(HorizonsModifier.REPAIR_RATING,  0.023), Map.entry(HorizonsModifier.AMMO_COST,  1.0)));
    public static final AutoFieldMaintenanceUnit AUTO_FIELD_MAINTENANCE_UNIT_8_A = new AutoFieldMaintenanceUnit("AUTO_FIELD_MAINTENANCE_UNIT_8_A", HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, ModuleSize.SIZE_8, ModuleClass.A, false, 49589820, "Int_Repairer_Size8_Class5", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  173.0), Map.entry(HorizonsModifier.POWER_DRAW,  4.2), Map.entry(HorizonsModifier.BOOT_TIME,  9.0), Map.entry(HorizonsModifier.REPAIR_CAPACITY,  11000.0), Map.entry(HorizonsModifier.CONSUMPTION,  10.0), Map.entry(HorizonsModifier.REPAIR_RATING,  0.028), Map.entry(HorizonsModifier.AMMO_COST,  1.0)));
    public static final List<AutoFieldMaintenanceUnit> AUTO_FIELD_MAINTENANCE_UNITS = List.of(
            AUTO_FIELD_MAINTENANCE_UNIT_1_E,
            AUTO_FIELD_MAINTENANCE_UNIT_1_D,
            AUTO_FIELD_MAINTENANCE_UNIT_1_C,
            AUTO_FIELD_MAINTENANCE_UNIT_1_B,
            AUTO_FIELD_MAINTENANCE_UNIT_1_A,
            AUTO_FIELD_MAINTENANCE_UNIT_2_E,
            AUTO_FIELD_MAINTENANCE_UNIT_2_D,
            AUTO_FIELD_MAINTENANCE_UNIT_2_C,
            AUTO_FIELD_MAINTENANCE_UNIT_2_B,
            AUTO_FIELD_MAINTENANCE_UNIT_2_A,
            AUTO_FIELD_MAINTENANCE_UNIT_3_E,
            AUTO_FIELD_MAINTENANCE_UNIT_3_D,
            AUTO_FIELD_MAINTENANCE_UNIT_3_C,
            AUTO_FIELD_MAINTENANCE_UNIT_3_B,
            AUTO_FIELD_MAINTENANCE_UNIT_3_A,
            AUTO_FIELD_MAINTENANCE_UNIT_4_E,
            AUTO_FIELD_MAINTENANCE_UNIT_4_D,
            AUTO_FIELD_MAINTENANCE_UNIT_4_C,
            AUTO_FIELD_MAINTENANCE_UNIT_4_B,
            AUTO_FIELD_MAINTENANCE_UNIT_4_A,
            AUTO_FIELD_MAINTENANCE_UNIT_5_E,
            AUTO_FIELD_MAINTENANCE_UNIT_5_D,
            AUTO_FIELD_MAINTENANCE_UNIT_5_C,
            AUTO_FIELD_MAINTENANCE_UNIT_5_B,
            AUTO_FIELD_MAINTENANCE_UNIT_5_A,
            AUTO_FIELD_MAINTENANCE_UNIT_6_E,
            AUTO_FIELD_MAINTENANCE_UNIT_6_D,
            AUTO_FIELD_MAINTENANCE_UNIT_6_C,
            AUTO_FIELD_MAINTENANCE_UNIT_6_B,
            AUTO_FIELD_MAINTENANCE_UNIT_6_A,
            AUTO_FIELD_MAINTENANCE_UNIT_7_E,
            AUTO_FIELD_MAINTENANCE_UNIT_7_D,
            AUTO_FIELD_MAINTENANCE_UNIT_7_C,
            AUTO_FIELD_MAINTENANCE_UNIT_7_B,
            AUTO_FIELD_MAINTENANCE_UNIT_7_A,
            AUTO_FIELD_MAINTENANCE_UNIT_8_E,
            AUTO_FIELD_MAINTENANCE_UNIT_8_D,
            AUTO_FIELD_MAINTENANCE_UNIT_8_C,
            AUTO_FIELD_MAINTENANCE_UNIT_8_B,
            AUTO_FIELD_MAINTENANCE_UNIT_8_A
    );
    public AutoFieldMaintenanceUnit(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, basePrice, internalName, attributes);
    }

    public AutoFieldMaintenanceUnit(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, basePrice, internalName, attributes);
    }

    public AutoFieldMaintenanceUnit(AutoFieldMaintenanceUnit autoFieldMaintenanceUnit) {
        super(autoFieldMaintenanceUnit);
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedBlueprints() {
        return AutoFieldMaintenanceUnitBlueprints.BLUEPRINTS.keySet().stream().toList();
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedExperimentalEffects() {
        return Collections.emptyList();
    }

    @Override
    public AutoFieldMaintenanceUnit Clone() {
        return new AutoFieldMaintenanceUnit(this);
    }
}