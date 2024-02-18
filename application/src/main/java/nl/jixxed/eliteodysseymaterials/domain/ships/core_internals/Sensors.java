package nl.jixxed.eliteodysseymaterials.domain.ships.core_internals;

import nl.jixxed.eliteodysseymaterials.constants.horizons.core_internals.SensorBlueprints;
import nl.jixxed.eliteodysseymaterials.domain.ships.CoreModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleClass;
import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleSize;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Sensors extends CoreModule {
    public static final Sensors SENSORS_1_E = new Sensors("SENSORS_1_E", HorizonsBlueprintName.SENSORS, ModuleSize.SIZE_1, ModuleClass.E, 520, "Int_Sensors_Size1_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 1.30), Map.entry(HorizonsModifier.INTEGRITY, 36.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.16), Map.entry(HorizonsModifier.BOOT_TIME, 5.0), Map.entry(HorizonsModifier.MAX_RANGE, 8.0), Map.entry(HorizonsModifier.SCAN_ANGLE, 30.0), Map.entry(HorizonsModifier.TYPICAL_EMISSION_RANGE, 4000.0)));
    public static final Sensors SENSORS_1_D = new Sensors("SENSORS_1_D", HorizonsBlueprintName.SENSORS, ModuleSize.SIZE_1, ModuleClass.D, 1290, "Int_Sensors_Size1_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 0.50), Map.entry(HorizonsModifier.INTEGRITY, 32.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.18), Map.entry(HorizonsModifier.BOOT_TIME, 5.0), Map.entry(HorizonsModifier.MAX_RANGE, 8.0), Map.entry(HorizonsModifier.SCAN_ANGLE, 30.0), Map.entry(HorizonsModifier.TYPICAL_EMISSION_RANGE, 4500.0)));
    public static final Sensors SENSORS_1_C = new Sensors("SENSORS_1_C", HorizonsBlueprintName.SENSORS, ModuleSize.SIZE_1, ModuleClass.C, 3230, "Int_Sensors_Size1_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 1.30), Map.entry(HorizonsModifier.INTEGRITY, 40.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.20), Map.entry(HorizonsModifier.BOOT_TIME, 5.0), Map.entry(HorizonsModifier.MAX_RANGE, 8.0), Map.entry(HorizonsModifier.SCAN_ANGLE, 30.0), Map.entry(HorizonsModifier.TYPICAL_EMISSION_RANGE, 5000.0)));
    public static final Sensors SENSORS_1_B = new Sensors("SENSORS_1_B", HorizonsBlueprintName.SENSORS, ModuleSize.SIZE_1, ModuleClass.B, 8080, "Int_Sensors_Size1_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.00), Map.entry(HorizonsModifier.INTEGRITY, 48.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.33), Map.entry(HorizonsModifier.BOOT_TIME, 5.0), Map.entry(HorizonsModifier.MAX_RANGE, 8.0), Map.entry(HorizonsModifier.SCAN_ANGLE, 30.0), Map.entry(HorizonsModifier.TYPICAL_EMISSION_RANGE, 5500.0)));
    public static final Sensors SENSORS_1_A = new Sensors("SENSORS_1_A", HorizonsBlueprintName.SENSORS, ModuleSize.SIZE_1, ModuleClass.A, 20200, "Int_Sensors_Size1_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 1.30), Map.entry(HorizonsModifier.INTEGRITY, 44.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.60), Map.entry(HorizonsModifier.BOOT_TIME, 5.0), Map.entry(HorizonsModifier.MAX_RANGE, 8.0), Map.entry(HorizonsModifier.SCAN_ANGLE, 30.0), Map.entry(HorizonsModifier.TYPICAL_EMISSION_RANGE, 6000.0)));
    public static final Sensors SENSORS_2_E = new Sensors("SENSORS_2_E", HorizonsBlueprintName.SENSORS, ModuleSize.SIZE_2, ModuleClass.E, 1450, "Int_Sensors_Size2_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.50), Map.entry(HorizonsModifier.INTEGRITY, 46.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.18), Map.entry(HorizonsModifier.BOOT_TIME, 5.0), Map.entry(HorizonsModifier.MAX_RANGE, 8.0), Map.entry(HorizonsModifier.SCAN_ANGLE, 30.0), Map.entry(HorizonsModifier.TYPICAL_EMISSION_RANGE, 4160.0)));
    public static final Sensors SENSORS_2_D = new Sensors("SENSORS_2_D", HorizonsBlueprintName.SENSORS, ModuleSize.SIZE_2, ModuleClass.D, 3620, "Int_Sensors_Size2_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 1.00), Map.entry(HorizonsModifier.INTEGRITY, 41.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.21), Map.entry(HorizonsModifier.BOOT_TIME, 5.0), Map.entry(HorizonsModifier.MAX_RANGE, 8.0), Map.entry(HorizonsModifier.SCAN_ANGLE, 30.0), Map.entry(HorizonsModifier.TYPICAL_EMISSION_RANGE, 4680.0)));
    public static final Sensors SENSORS_2_C = new Sensors("SENSORS_2_C", HorizonsBlueprintName.SENSORS, ModuleSize.SIZE_2, ModuleClass.C, 9050, "Int_Sensors_Size2_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.50), Map.entry(HorizonsModifier.INTEGRITY, 51.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.23), Map.entry(HorizonsModifier.BOOT_TIME, 5.0), Map.entry(HorizonsModifier.MAX_RANGE, 8.0), Map.entry(HorizonsModifier.SCAN_ANGLE, 30.0), Map.entry(HorizonsModifier.TYPICAL_EMISSION_RANGE, 5200.0)));
    public static final Sensors SENSORS_2_B = new Sensors("SENSORS_2_B", HorizonsBlueprintName.SENSORS, ModuleSize.SIZE_2, ModuleClass.B, 22620, "Int_Sensors_Size2_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.00), Map.entry(HorizonsModifier.INTEGRITY, 61.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.38), Map.entry(HorizonsModifier.BOOT_TIME, 5.0), Map.entry(HorizonsModifier.MAX_RANGE, 8.0), Map.entry(HorizonsModifier.SCAN_ANGLE, 30.0), Map.entry(HorizonsModifier.TYPICAL_EMISSION_RANGE, 5720.0)));
    public static final Sensors SENSORS_2_A = new Sensors("SENSORS_2_A", HorizonsBlueprintName.SENSORS, ModuleSize.SIZE_2, ModuleClass.A, 56550, "Int_Sensors_Size2_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.50), Map.entry(HorizonsModifier.INTEGRITY, 56.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.69), Map.entry(HorizonsModifier.BOOT_TIME, 5.0), Map.entry(HorizonsModifier.MAX_RANGE, 8.0), Map.entry(HorizonsModifier.SCAN_ANGLE, 30.0), Map.entry(HorizonsModifier.TYPICAL_EMISSION_RANGE, 6240.0)));
    public static final Sensors SENSORS_3_E = new Sensors("SENSORS_3_E", HorizonsBlueprintName.SENSORS, ModuleSize.SIZE_3, ModuleClass.E, 4050, "Int_Sensors_Size3_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 5.00), Map.entry(HorizonsModifier.INTEGRITY, 58.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.22), Map.entry(HorizonsModifier.BOOT_TIME, 5.0), Map.entry(HorizonsModifier.MAX_RANGE, 8.0), Map.entry(HorizonsModifier.SCAN_ANGLE, 30.0), Map.entry(HorizonsModifier.TYPICAL_EMISSION_RANGE, 4320.0)));
    public static final Sensors SENSORS_3_D = new Sensors("SENSORS_3_D", HorizonsBlueprintName.SENSORS, ModuleSize.SIZE_3, ModuleClass.D, 10130, "Int_Sensors_Size3_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.00), Map.entry(HorizonsModifier.INTEGRITY, 51.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.25), Map.entry(HorizonsModifier.BOOT_TIME, 5.0), Map.entry(HorizonsModifier.MAX_RANGE, 8.0), Map.entry(HorizonsModifier.SCAN_ANGLE, 30.0), Map.entry(HorizonsModifier.TYPICAL_EMISSION_RANGE, 4860.0)));
    public static final Sensors SENSORS_3_C = new Sensors("SENSORS_3_C", HorizonsBlueprintName.SENSORS, ModuleSize.SIZE_3, ModuleClass.C, 25330, "Int_Sensors_Size3_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 5.00), Map.entry(HorizonsModifier.INTEGRITY, 64.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.28), Map.entry(HorizonsModifier.BOOT_TIME, 5.0), Map.entry(HorizonsModifier.MAX_RANGE, 8.0), Map.entry(HorizonsModifier.SCAN_ANGLE, 30.0), Map.entry(HorizonsModifier.TYPICAL_EMISSION_RANGE, 5400.0)));
    public static final Sensors SENSORS_3_B = new Sensors("SENSORS_3_B", HorizonsBlueprintName.SENSORS, ModuleSize.SIZE_3, ModuleClass.B, 63330, "Int_Sensors_Size3_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 8.00), Map.entry(HorizonsModifier.INTEGRITY, 77.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.46), Map.entry(HorizonsModifier.BOOT_TIME, 5.0), Map.entry(HorizonsModifier.MAX_RANGE, 8.0), Map.entry(HorizonsModifier.SCAN_ANGLE, 30.0), Map.entry(HorizonsModifier.TYPICAL_EMISSION_RANGE, 5940.0)));
    public static final Sensors SENSORS_3_A = new Sensors("SENSORS_3_A", HorizonsBlueprintName.SENSORS, ModuleSize.SIZE_3, ModuleClass.A, 158330, "Int_Sensors_Size3_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 5.00), Map.entry(HorizonsModifier.INTEGRITY, 70.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.84), Map.entry(HorizonsModifier.BOOT_TIME, 5.0), Map.entry(HorizonsModifier.MAX_RANGE, 8.0), Map.entry(HorizonsModifier.SCAN_ANGLE, 30.0), Map.entry(HorizonsModifier.TYPICAL_EMISSION_RANGE, 6480.0)));
    public static final Sensors SENSORS_4_E = new Sensors("SENSORS_4_E", HorizonsBlueprintName.SENSORS, ModuleSize.SIZE_4, ModuleClass.E, 11350, "Int_Sensors_Size4_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 10.00), Map.entry(HorizonsModifier.INTEGRITY, 72.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.27), Map.entry(HorizonsModifier.BOOT_TIME, 5.0), Map.entry(HorizonsModifier.MAX_RANGE, 8.0), Map.entry(HorizonsModifier.SCAN_ANGLE, 30.0), Map.entry(HorizonsModifier.TYPICAL_EMISSION_RANGE, 4480.0)));
    public static final Sensors SENSORS_4_D = new Sensors("SENSORS_4_D", HorizonsBlueprintName.SENSORS, ModuleSize.SIZE_4, ModuleClass.D, 28370, "Int_Sensors_Size4_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.00), Map.entry(HorizonsModifier.INTEGRITY, 64.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.31), Map.entry(HorizonsModifier.BOOT_TIME, 5.0), Map.entry(HorizonsModifier.MAX_RANGE, 8.0), Map.entry(HorizonsModifier.SCAN_ANGLE, 30.0), Map.entry(HorizonsModifier.TYPICAL_EMISSION_RANGE, 5040.0)));
    public static final Sensors SENSORS_4_C = new Sensors("SENSORS_4_C", HorizonsBlueprintName.SENSORS, ModuleSize.SIZE_4, ModuleClass.C, 70930, "Int_Sensors_Size4_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 10.00), Map.entry(HorizonsModifier.INTEGRITY, 80.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.34), Map.entry(HorizonsModifier.BOOT_TIME, 5.0), Map.entry(HorizonsModifier.MAX_RANGE, 8.0), Map.entry(HorizonsModifier.SCAN_ANGLE, 30.0), Map.entry(HorizonsModifier.TYPICAL_EMISSION_RANGE, 5600.0)));
    public static final Sensors SENSORS_4_B = new Sensors("SENSORS_4_B", HorizonsBlueprintName.SENSORS, ModuleSize.SIZE_4, ModuleClass.B, 177330, "Int_Sensors_Size4_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 16.00), Map.entry(HorizonsModifier.INTEGRITY, 96.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.56), Map.entry(HorizonsModifier.BOOT_TIME, 5.0), Map.entry(HorizonsModifier.MAX_RANGE, 8.0), Map.entry(HorizonsModifier.SCAN_ANGLE, 30.0), Map.entry(HorizonsModifier.TYPICAL_EMISSION_RANGE, 6160.0)));
    public static final Sensors SENSORS_4_A = new Sensors("SENSORS_4_A", HorizonsBlueprintName.SENSORS, ModuleSize.SIZE_4, ModuleClass.A, 443330, "Int_Sensors_Size4_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 10.00), Map.entry(HorizonsModifier.INTEGRITY, 88.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.02), Map.entry(HorizonsModifier.BOOT_TIME, 5.0), Map.entry(HorizonsModifier.MAX_RANGE, 8.0), Map.entry(HorizonsModifier.SCAN_ANGLE, 30.0), Map.entry(HorizonsModifier.TYPICAL_EMISSION_RANGE, 6720.0)));
    public static final Sensors SENSORS_5_E = new Sensors("SENSORS_5_E", HorizonsBlueprintName.SENSORS, ModuleSize.SIZE_5, ModuleClass.E, 31780, "Int_Sensors_Size5_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 20.00), Map.entry(HorizonsModifier.INTEGRITY, 86.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.33), Map.entry(HorizonsModifier.BOOT_TIME, 5.0), Map.entry(HorizonsModifier.MAX_RANGE, 8.0), Map.entry(HorizonsModifier.SCAN_ANGLE, 30.0), Map.entry(HorizonsModifier.TYPICAL_EMISSION_RANGE, 4640.0)));
    public static final Sensors SENSORS_5_D = new Sensors("SENSORS_5_D", HorizonsBlueprintName.SENSORS, ModuleSize.SIZE_5, ModuleClass.D, 79440, "Int_Sensors_Size5_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 8.00), Map.entry(HorizonsModifier.INTEGRITY, 77.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.37), Map.entry(HorizonsModifier.BOOT_TIME, 5.0), Map.entry(HorizonsModifier.MAX_RANGE, 8.0), Map.entry(HorizonsModifier.SCAN_ANGLE, 30.0), Map.entry(HorizonsModifier.TYPICAL_EMISSION_RANGE, 5220.0)));
    public static final Sensors SENSORS_5_C = new Sensors("SENSORS_5_C", HorizonsBlueprintName.SENSORS, ModuleSize.SIZE_5, ModuleClass.C, 198610, "Int_Sensors_Size5_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 20.00), Map.entry(HorizonsModifier.INTEGRITY, 96.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.41), Map.entry(HorizonsModifier.BOOT_TIME, 5.0), Map.entry(HorizonsModifier.MAX_RANGE, 8.0), Map.entry(HorizonsModifier.SCAN_ANGLE, 30.0), Map.entry(HorizonsModifier.TYPICAL_EMISSION_RANGE, 5800.0)));
    public static final Sensors SENSORS_5_B = new Sensors("SENSORS_5_B", HorizonsBlueprintName.SENSORS, ModuleSize.SIZE_5, ModuleClass.B, 496530, "Int_Sensors_Size5_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 32.00), Map.entry(HorizonsModifier.INTEGRITY, 115.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.68), Map.entry(HorizonsModifier.BOOT_TIME, 5.0), Map.entry(HorizonsModifier.MAX_RANGE, 8.0), Map.entry(HorizonsModifier.SCAN_ANGLE, 30.0), Map.entry(HorizonsModifier.TYPICAL_EMISSION_RANGE, 6380.0)));
    public static final Sensors SENSORS_5_A = new Sensors("SENSORS_5_A", HorizonsBlueprintName.SENSORS, ModuleSize.SIZE_5, ModuleClass.A, 1241320, "Int_Sensors_Size5_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 20.00), Map.entry(HorizonsModifier.INTEGRITY, 106.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.23), Map.entry(HorizonsModifier.BOOT_TIME, 5.0), Map.entry(HorizonsModifier.MAX_RANGE, 8.0), Map.entry(HorizonsModifier.SCAN_ANGLE, 30.0), Map.entry(HorizonsModifier.TYPICAL_EMISSION_RANGE, 6960.0)));
    public static final Sensors SENSORS_6_E = new Sensors("SENSORS_6_E", HorizonsBlueprintName.SENSORS, ModuleSize.SIZE_6, ModuleClass.E, 88980, "Int_Sensors_Size6_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 40.00), Map.entry(HorizonsModifier.INTEGRITY, 102.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.40), Map.entry(HorizonsModifier.BOOT_TIME, 5.0), Map.entry(HorizonsModifier.MAX_RANGE, 8.0), Map.entry(HorizonsModifier.SCAN_ANGLE, 30.0), Map.entry(HorizonsModifier.TYPICAL_EMISSION_RANGE, 4800.0)));
    public static final Sensors SENSORS_6_D = new Sensors("SENSORS_6_D", HorizonsBlueprintName.SENSORS, ModuleSize.SIZE_6, ModuleClass.D, 222440, "Int_Sensors_Size6_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 16.00), Map.entry(HorizonsModifier.INTEGRITY, 90.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.45), Map.entry(HorizonsModifier.BOOT_TIME, 5.0), Map.entry(HorizonsModifier.MAX_RANGE, 8.0), Map.entry(HorizonsModifier.SCAN_ANGLE, 30.0), Map.entry(HorizonsModifier.TYPICAL_EMISSION_RANGE, 5400.0)));
    public static final Sensors SENSORS_6_C = new Sensors("SENSORS_6_C", HorizonsBlueprintName.SENSORS, ModuleSize.SIZE_6, ModuleClass.C, 556110, "Int_Sensors_Size6_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 40.00), Map.entry(HorizonsModifier.INTEGRITY, 113.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.50), Map.entry(HorizonsModifier.BOOT_TIME, 5.0), Map.entry(HorizonsModifier.MAX_RANGE, 8.0), Map.entry(HorizonsModifier.SCAN_ANGLE, 30.0), Map.entry(HorizonsModifier.TYPICAL_EMISSION_RANGE, 6000.0)));
    public static final Sensors SENSORS_6_B = new Sensors("SENSORS_6_B", HorizonsBlueprintName.SENSORS, ModuleSize.SIZE_6, ModuleClass.B, 1390280, "Int_Sensors_Size6_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 64.00), Map.entry(HorizonsModifier.INTEGRITY, 136.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.83), Map.entry(HorizonsModifier.BOOT_TIME, 5.0), Map.entry(HorizonsModifier.MAX_RANGE, 8.0), Map.entry(HorizonsModifier.SCAN_ANGLE, 30.0), Map.entry(HorizonsModifier.TYPICAL_EMISSION_RANGE, 6600.0)));
    public static final Sensors SENSORS_6_A = new Sensors("SENSORS_6_A", HorizonsBlueprintName.SENSORS, ModuleSize.SIZE_6, ModuleClass.A, 3475690, "Int_Sensors_Size6_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 40.00), Map.entry(HorizonsModifier.INTEGRITY, 124.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.50), Map.entry(HorizonsModifier.BOOT_TIME, 5.0), Map.entry(HorizonsModifier.MAX_RANGE, 8.0), Map.entry(HorizonsModifier.SCAN_ANGLE, 30.0), Map.entry(HorizonsModifier.TYPICAL_EMISSION_RANGE, 7200.0)));
    public static final Sensors SENSORS_7_E = new Sensors("SENSORS_7_E", HorizonsBlueprintName.SENSORS, ModuleSize.SIZE_7, ModuleClass.E, 249140, "Int_Sensors_Size7_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 80.00), Map.entry(HorizonsModifier.INTEGRITY, 118.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.47), Map.entry(HorizonsModifier.BOOT_TIME, 5.0), Map.entry(HorizonsModifier.MAX_RANGE, 8.0), Map.entry(HorizonsModifier.SCAN_ANGLE, 30.0), Map.entry(HorizonsModifier.TYPICAL_EMISSION_RANGE, 4960.0)));
    public static final Sensors SENSORS_7_D = new Sensors("SENSORS_7_D", HorizonsBlueprintName.SENSORS, ModuleSize.SIZE_7, ModuleClass.D, 622840, "Int_Sensors_Size7_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 32.00), Map.entry(HorizonsModifier.INTEGRITY, 105.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.53), Map.entry(HorizonsModifier.BOOT_TIME, 5.0), Map.entry(HorizonsModifier.MAX_RANGE, 8.0), Map.entry(HorizonsModifier.SCAN_ANGLE, 30.0), Map.entry(HorizonsModifier.TYPICAL_EMISSION_RANGE, 5580.0)));
    public static final Sensors SENSORS_7_C = new Sensors("SENSORS_7_C", HorizonsBlueprintName.SENSORS, ModuleSize.SIZE_7, ModuleClass.C, 1557110, "Int_Sensors_Size7_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 80.00), Map.entry(HorizonsModifier.INTEGRITY, 131.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.59), Map.entry(HorizonsModifier.BOOT_TIME, 5.0), Map.entry(HorizonsModifier.MAX_RANGE, 8.0), Map.entry(HorizonsModifier.SCAN_ANGLE, 30.0), Map.entry(HorizonsModifier.TYPICAL_EMISSION_RANGE, 6200.0)));
    public static final Sensors SENSORS_7_B = new Sensors("SENSORS_7_B", HorizonsBlueprintName.SENSORS, ModuleSize.SIZE_7, ModuleClass.B, 3892770, "Int_Sensors_Size7_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 128.00), Map.entry(HorizonsModifier.INTEGRITY, 157.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.97), Map.entry(HorizonsModifier.BOOT_TIME, 5.0), Map.entry(HorizonsModifier.MAX_RANGE, 8.0), Map.entry(HorizonsModifier.SCAN_ANGLE, 30.0), Map.entry(HorizonsModifier.TYPICAL_EMISSION_RANGE, 6820.0)));
    public static final Sensors SENSORS_7_A = new Sensors("SENSORS_7_A", HorizonsBlueprintName.SENSORS, ModuleSize.SIZE_7, ModuleClass.A, 9731930, "Int_Sensors_Size7_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 80.00), Map.entry(HorizonsModifier.INTEGRITY, 144.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.77), Map.entry(HorizonsModifier.BOOT_TIME, 5.0), Map.entry(HorizonsModifier.MAX_RANGE, 8.0), Map.entry(HorizonsModifier.SCAN_ANGLE, 30.0), Map.entry(HorizonsModifier.TYPICAL_EMISSION_RANGE, 7440.0)));
    public static final Sensors SENSORS_8_E = new Sensors("SENSORS_8_E", HorizonsBlueprintName.SENSORS, ModuleSize.SIZE_8, ModuleClass.E, 697580, "Int_Sensors_Size8_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 160.00), Map.entry(HorizonsModifier.INTEGRITY, 135.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.55), Map.entry(HorizonsModifier.BOOT_TIME, 5.0), Map.entry(HorizonsModifier.MAX_RANGE, 8.0), Map.entry(HorizonsModifier.SCAN_ANGLE, 30.0), Map.entry(HorizonsModifier.TYPICAL_EMISSION_RANGE, 5120.0)));
    public static final Sensors SENSORS_8_D = new Sensors("SENSORS_8_D", HorizonsBlueprintName.SENSORS, ModuleSize.SIZE_8, ModuleClass.D, 1743960, "Int_Sensors_Size8_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 64.00), Map.entry(HorizonsModifier.INTEGRITY, 120.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.62), Map.entry(HorizonsModifier.BOOT_TIME, 5.0), Map.entry(HorizonsModifier.MAX_RANGE, 8.0), Map.entry(HorizonsModifier.SCAN_ANGLE, 30.0), Map.entry(HorizonsModifier.TYPICAL_EMISSION_RANGE, 5760.0)));
    public static final Sensors SENSORS_8_C = new Sensors("SENSORS_8_C", HorizonsBlueprintName.SENSORS, ModuleSize.SIZE_8, ModuleClass.C, 4359900, "Int_Sensors_Size8_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 160.00), Map.entry(HorizonsModifier.INTEGRITY, 150.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.69), Map.entry(HorizonsModifier.BOOT_TIME, 5.0), Map.entry(HorizonsModifier.MAX_RANGE, 8.0), Map.entry(HorizonsModifier.SCAN_ANGLE, 30.0), Map.entry(HorizonsModifier.TYPICAL_EMISSION_RANGE, 6400.0)));
    public static final Sensors SENSORS_8_B = new Sensors("SENSORS_8_B", HorizonsBlueprintName.SENSORS, ModuleSize.SIZE_8, ModuleClass.B, 10899760, "Int_Sensors_Size8_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 256.00), Map.entry(HorizonsModifier.INTEGRITY, 180.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.14), Map.entry(HorizonsModifier.BOOT_TIME, 5.0), Map.entry(HorizonsModifier.MAX_RANGE, 8.0), Map.entry(HorizonsModifier.SCAN_ANGLE, 30.0), Map.entry(HorizonsModifier.TYPICAL_EMISSION_RANGE, 7040.0)));
    public static final Sensors SENSORS_8_A = new Sensors("SENSORS_8_A", HorizonsBlueprintName.SENSORS, ModuleSize.SIZE_8, ModuleClass.A, 27249390, "Int_Sensors_Size8_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 160.00), Map.entry(HorizonsModifier.INTEGRITY, 165.0), Map.entry(HorizonsModifier.POWER_DRAW, 2.07), Map.entry(HorizonsModifier.BOOT_TIME, 5.0), Map.entry(HorizonsModifier.MAX_RANGE, 8.0), Map.entry(HorizonsModifier.SCAN_ANGLE, 30.0), Map.entry(HorizonsModifier.TYPICAL_EMISSION_RANGE, 7680.0)));
    public static final List<Sensors> SENSORS = List.of(
            SENSORS_1_E,
            SENSORS_1_D,
            SENSORS_1_C,
            SENSORS_1_B,
            SENSORS_1_A,
            SENSORS_2_E,
            SENSORS_2_D,
            SENSORS_2_C,
            SENSORS_2_B,
            SENSORS_2_A,
            SENSORS_3_E,
            SENSORS_3_D,
            SENSORS_3_C,
            SENSORS_3_B,
            SENSORS_3_A,
            SENSORS_4_E,
            SENSORS_4_D,
            SENSORS_4_C,
            SENSORS_4_B,
            SENSORS_4_A,
            SENSORS_5_E,
            SENSORS_5_D,
            SENSORS_5_C,
            SENSORS_5_B,
            SENSORS_5_A,
            SENSORS_6_E,
            SENSORS_6_D,
            SENSORS_6_C,
            SENSORS_6_B,
            SENSORS_6_A,
            SENSORS_7_E,
            SENSORS_7_D,
            SENSORS_7_C,
            SENSORS_7_B,
            SENSORS_7_A,
            SENSORS_8_E,
            SENSORS_8_D,
            SENSORS_8_C,
            SENSORS_8_B,
            SENSORS_8_A
    );

    private Sensors(final String id, final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final int basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, basePrice, internalName, attributes);
    }

    public Sensors(final Sensors thrusters) {
        super(thrusters);
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedBlueprints() {
        return SensorBlueprints.BLUEPRINTS.keySet().stream().toList();
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedExperimentalEffects() {
        return Collections.emptyList();
    }

    @Override
    public Sensors Clone() {
        return new Sensors(this);
    }
}
