package nl.jixxed.eliteodysseymaterials.domain.ships.core_internals;

import nl.jixxed.eliteodysseymaterials.constants.horizons.core_internals.LifeSupportBlueprints;
import nl.jixxed.eliteodysseymaterials.domain.ships.CoreModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleClass;
import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleSize;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class LifeSupport extends CoreModule {
    public static final LifeSupport LIFE_SUPPORT_1_E = new LifeSupport("LIFE_SUPPORT_1_E", HorizonsBlueprintName.LIFE_SUPPORT, ModuleSize.SIZE_1, ModuleClass.E, 520, "Int_LifeSupport_Size1_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 1.30), Map.entry(HorizonsModifier.INTEGRITY, 32.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.32), Map.entry(HorizonsModifier.BOOT_TIME, 1.0), Map.entry(HorizonsModifier.EMERGENCY_LIFE_SUPPORT, 300.0)));
    public static final LifeSupport LIFE_SUPPORT_1_D = new LifeSupport("LIFE_SUPPORT_1_D", HorizonsBlueprintName.LIFE_SUPPORT, ModuleSize.SIZE_1, ModuleClass.D, 1290, "Int_LifeSupport_Size1_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 0.50), Map.entry(HorizonsModifier.INTEGRITY, 36.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.36), Map.entry(HorizonsModifier.BOOT_TIME, 1.0), Map.entry(HorizonsModifier.EMERGENCY_LIFE_SUPPORT, 450.0)));
    public static final LifeSupport LIFE_SUPPORT_1_C = new LifeSupport("LIFE_SUPPORT_1_C", HorizonsBlueprintName.LIFE_SUPPORT, ModuleSize.SIZE_1, ModuleClass.C, 3230, "Int_LifeSupport_Size1_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 1.30), Map.entry(HorizonsModifier.INTEGRITY, 40.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.40), Map.entry(HorizonsModifier.BOOT_TIME, 1.0), Map.entry(HorizonsModifier.EMERGENCY_LIFE_SUPPORT, 600.0)));
    public static final LifeSupport LIFE_SUPPORT_1_B = new LifeSupport("LIFE_SUPPORT_1_B", HorizonsBlueprintName.LIFE_SUPPORT, ModuleSize.SIZE_1, ModuleClass.B, 8080, "Int_LifeSupport_Size1_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.00), Map.entry(HorizonsModifier.INTEGRITY, 44.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.44), Map.entry(HorizonsModifier.BOOT_TIME, 1.0), Map.entry(HorizonsModifier.EMERGENCY_LIFE_SUPPORT, 900.0)));
    public static final LifeSupport LIFE_SUPPORT_1_A = new LifeSupport("LIFE_SUPPORT_1_A", HorizonsBlueprintName.LIFE_SUPPORT, ModuleSize.SIZE_1, ModuleClass.A, 20200, "Int_LifeSupport_Size1_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 1.30), Map.entry(HorizonsModifier.INTEGRITY, 48.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.48), Map.entry(HorizonsModifier.BOOT_TIME, 1.0), Map.entry(HorizonsModifier.EMERGENCY_LIFE_SUPPORT, 1500.0)));
    public static final LifeSupport LIFE_SUPPORT_2_E = new LifeSupport("LIFE_SUPPORT_2_E", HorizonsBlueprintName.LIFE_SUPPORT, ModuleSize.SIZE_2, ModuleClass.E, 1450, "Int_LifeSupport_Size2_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.50), Map.entry(HorizonsModifier.INTEGRITY, 41.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.37), Map.entry(HorizonsModifier.BOOT_TIME, 1.0), Map.entry(HorizonsModifier.EMERGENCY_LIFE_SUPPORT, 300.0)));
    public static final LifeSupport LIFE_SUPPORT_2_D = new LifeSupport("LIFE_SUPPORT_2_D", HorizonsBlueprintName.LIFE_SUPPORT, ModuleSize.SIZE_2, ModuleClass.D, 3620, "Int_LifeSupport_Size2_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 1.00), Map.entry(HorizonsModifier.INTEGRITY, 46.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.41), Map.entry(HorizonsModifier.BOOT_TIME, 1.0), Map.entry(HorizonsModifier.EMERGENCY_LIFE_SUPPORT, 450.0)));
    public static final LifeSupport LIFE_SUPPORT_2_C = new LifeSupport("LIFE_SUPPORT_2_C", HorizonsBlueprintName.LIFE_SUPPORT, ModuleSize.SIZE_2, ModuleClass.C, 9050, "Int_LifeSupport_Size2_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.50), Map.entry(HorizonsModifier.INTEGRITY, 51.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.46), Map.entry(HorizonsModifier.BOOT_TIME, 1.0), Map.entry(HorizonsModifier.EMERGENCY_LIFE_SUPPORT, 600.0)));
    public static final LifeSupport LIFE_SUPPORT_2_B = new LifeSupport("LIFE_SUPPORT_2_B", HorizonsBlueprintName.LIFE_SUPPORT, ModuleSize.SIZE_2, ModuleClass.B, 22620, "Int_LifeSupport_Size2_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.00), Map.entry(HorizonsModifier.INTEGRITY, 56.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.51), Map.entry(HorizonsModifier.BOOT_TIME, 1.0), Map.entry(HorizonsModifier.EMERGENCY_LIFE_SUPPORT, 900.0)));
    public static final LifeSupport LIFE_SUPPORT_2_A = new LifeSupport("LIFE_SUPPORT_2_A", HorizonsBlueprintName.LIFE_SUPPORT, ModuleSize.SIZE_2, ModuleClass.A, 56550, "Int_LifeSupport_Size2_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.50), Map.entry(HorizonsModifier.INTEGRITY, 61.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.55), Map.entry(HorizonsModifier.BOOT_TIME, 1.0), Map.entry(HorizonsModifier.EMERGENCY_LIFE_SUPPORT, 1500.0)));
    public static final LifeSupport LIFE_SUPPORT_3_E = new LifeSupport("LIFE_SUPPORT_3_E", HorizonsBlueprintName.LIFE_SUPPORT, ModuleSize.SIZE_3, ModuleClass.E, 4050, "Int_LifeSupport_Size3_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 5.00), Map.entry(HorizonsModifier.INTEGRITY, 51.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.42), Map.entry(HorizonsModifier.BOOT_TIME, 1.0), Map.entry(HorizonsModifier.EMERGENCY_LIFE_SUPPORT, 300.0)));
    public static final LifeSupport LIFE_SUPPORT_3_D = new LifeSupport("LIFE_SUPPORT_3_D", HorizonsBlueprintName.LIFE_SUPPORT, ModuleSize.SIZE_3, ModuleClass.D, 10130, "Int_LifeSupport_Size3_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.00), Map.entry(HorizonsModifier.INTEGRITY, 58.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.48), Map.entry(HorizonsModifier.BOOT_TIME, 1.0), Map.entry(HorizonsModifier.EMERGENCY_LIFE_SUPPORT, 450.0)));
    public static final LifeSupport LIFE_SUPPORT_3_C = new LifeSupport("LIFE_SUPPORT_3_C", HorizonsBlueprintName.LIFE_SUPPORT, ModuleSize.SIZE_3, ModuleClass.C, 25330, "Int_LifeSupport_Size3_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 5.00), Map.entry(HorizonsModifier.INTEGRITY, 64.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.53), Map.entry(HorizonsModifier.BOOT_TIME, 1.0), Map.entry(HorizonsModifier.EMERGENCY_LIFE_SUPPORT, 600.0)));
    public static final LifeSupport LIFE_SUPPORT_3_B = new LifeSupport("LIFE_SUPPORT_3_B", HorizonsBlueprintName.LIFE_SUPPORT, ModuleSize.SIZE_3, ModuleClass.B, 63330, "Int_LifeSupport_Size3_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 8.00), Map.entry(HorizonsModifier.INTEGRITY, 70.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.58), Map.entry(HorizonsModifier.BOOT_TIME, 1.0), Map.entry(HorizonsModifier.EMERGENCY_LIFE_SUPPORT, 900.0)));
    public static final LifeSupport LIFE_SUPPORT_3_A = new LifeSupport("LIFE_SUPPORT_3_A", HorizonsBlueprintName.LIFE_SUPPORT, ModuleSize.SIZE_3, ModuleClass.A, 158330, "Int_LifeSupport_Size3_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 5.00), Map.entry(HorizonsModifier.INTEGRITY, 77.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.64), Map.entry(HorizonsModifier.BOOT_TIME, 1.0), Map.entry(HorizonsModifier.EMERGENCY_LIFE_SUPPORT, 1500.0)));
    public static final LifeSupport LIFE_SUPPORT_4_E = new LifeSupport("LIFE_SUPPORT_4_E", HorizonsBlueprintName.LIFE_SUPPORT, ModuleSize.SIZE_4, ModuleClass.E, 11350, "Int_LifeSupport_Size4_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 10.00), Map.entry(HorizonsModifier.INTEGRITY, 64.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.50), Map.entry(HorizonsModifier.BOOT_TIME, 1.0), Map.entry(HorizonsModifier.EMERGENCY_LIFE_SUPPORT, 300.0)));
    public static final LifeSupport LIFE_SUPPORT_4_D = new LifeSupport("LIFE_SUPPORT_4_D", HorizonsBlueprintName.LIFE_SUPPORT, ModuleSize.SIZE_4, ModuleClass.D, 28370, "Int_LifeSupport_Size4_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.00), Map.entry(HorizonsModifier.INTEGRITY, 72.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.56), Map.entry(HorizonsModifier.BOOT_TIME, 1.0), Map.entry(HorizonsModifier.EMERGENCY_LIFE_SUPPORT, 450.0)));
    public static final LifeSupport LIFE_SUPPORT_4_C = new LifeSupport("LIFE_SUPPORT_4_C", HorizonsBlueprintName.LIFE_SUPPORT, ModuleSize.SIZE_4, ModuleClass.C, 70930, "Int_LifeSupport_Size4_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 10.00), Map.entry(HorizonsModifier.INTEGRITY, 80.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.62), Map.entry(HorizonsModifier.BOOT_TIME, 1.0), Map.entry(HorizonsModifier.EMERGENCY_LIFE_SUPPORT, 600.0)));
    public static final LifeSupport LIFE_SUPPORT_4_B = new LifeSupport("LIFE_SUPPORT_4_B", HorizonsBlueprintName.LIFE_SUPPORT, ModuleSize.SIZE_4, ModuleClass.B, 177330, "Int_LifeSupport_Size4_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 16.00), Map.entry(HorizonsModifier.INTEGRITY, 88.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.68), Map.entry(HorizonsModifier.BOOT_TIME, 1.0), Map.entry(HorizonsModifier.EMERGENCY_LIFE_SUPPORT, 900.0)));
    public static final LifeSupport LIFE_SUPPORT_4_A = new LifeSupport("LIFE_SUPPORT_4_A", HorizonsBlueprintName.LIFE_SUPPORT, ModuleSize.SIZE_4, ModuleClass.A, 443330, "Int_LifeSupport_Size4_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 10.00), Map.entry(HorizonsModifier.INTEGRITY, 96.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.74), Map.entry(HorizonsModifier.BOOT_TIME, 1.0), Map.entry(HorizonsModifier.EMERGENCY_LIFE_SUPPORT, 1500.0)));
    public static final LifeSupport LIFE_SUPPORT_5_E = new LifeSupport("LIFE_SUPPORT_5_E", HorizonsBlueprintName.LIFE_SUPPORT, ModuleSize.SIZE_5, ModuleClass.E, 31780, "Int_LifeSupport_Size5_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 20.00), Map.entry(HorizonsModifier.INTEGRITY, 77.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.57), Map.entry(HorizonsModifier.BOOT_TIME, 1.0), Map.entry(HorizonsModifier.EMERGENCY_LIFE_SUPPORT, 300.0)));
    public static final LifeSupport LIFE_SUPPORT_5_D = new LifeSupport("LIFE_SUPPORT_5_D", HorizonsBlueprintName.LIFE_SUPPORT, ModuleSize.SIZE_5, ModuleClass.D, 79440, "Int_LifeSupport_Size5_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 8.00), Map.entry(HorizonsModifier.INTEGRITY, 86.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.64), Map.entry(HorizonsModifier.BOOT_TIME, 1.0), Map.entry(HorizonsModifier.EMERGENCY_LIFE_SUPPORT, 450.0)));
    public static final LifeSupport LIFE_SUPPORT_5_C = new LifeSupport("LIFE_SUPPORT_5_C", HorizonsBlueprintName.LIFE_SUPPORT, ModuleSize.SIZE_5, ModuleClass.C, 198610, "Int_LifeSupport_Size5_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 20.00), Map.entry(HorizonsModifier.INTEGRITY, 96.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.71), Map.entry(HorizonsModifier.BOOT_TIME, 1.0), Map.entry(HorizonsModifier.EMERGENCY_LIFE_SUPPORT, 600.0)));
    public static final LifeSupport LIFE_SUPPORT_5_B = new LifeSupport("LIFE_SUPPORT_5_B", HorizonsBlueprintName.LIFE_SUPPORT, ModuleSize.SIZE_5, ModuleClass.B, 496530, "Int_LifeSupport_Size5_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 32.00), Map.entry(HorizonsModifier.INTEGRITY, 106.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.78), Map.entry(HorizonsModifier.BOOT_TIME, 1.0), Map.entry(HorizonsModifier.EMERGENCY_LIFE_SUPPORT, 900.0)));
    public static final LifeSupport LIFE_SUPPORT_5_A = new LifeSupport("LIFE_SUPPORT_5_A", HorizonsBlueprintName.LIFE_SUPPORT, ModuleSize.SIZE_5, ModuleClass.A, 1241320, "Int_LifeSupport_Size5_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 20.00), Map.entry(HorizonsModifier.INTEGRITY, 115.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.85), Map.entry(HorizonsModifier.BOOT_TIME, 1.0), Map.entry(HorizonsModifier.EMERGENCY_LIFE_SUPPORT, 1500.0)));
    public static final LifeSupport LIFE_SUPPORT_6_E = new LifeSupport("LIFE_SUPPORT_6_E", HorizonsBlueprintName.LIFE_SUPPORT, ModuleSize.SIZE_6, ModuleClass.E, 88980, "Int_LifeSupport_Size6_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 40.00), Map.entry(HorizonsModifier.INTEGRITY, 90.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.64), Map.entry(HorizonsModifier.BOOT_TIME, 1.0), Map.entry(HorizonsModifier.EMERGENCY_LIFE_SUPPORT, 300.0)));
    public static final LifeSupport LIFE_SUPPORT_6_D = new LifeSupport("LIFE_SUPPORT_6_D", HorizonsBlueprintName.LIFE_SUPPORT, ModuleSize.SIZE_6, ModuleClass.D, 222440, "Int_LifeSupport_Size6_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 16.00), Map.entry(HorizonsModifier.INTEGRITY, 102.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.72), Map.entry(HorizonsModifier.BOOT_TIME, 1.0), Map.entry(HorizonsModifier.EMERGENCY_LIFE_SUPPORT, 450.0)));
    public static final LifeSupport LIFE_SUPPORT_6_C = new LifeSupport("LIFE_SUPPORT_6_C", HorizonsBlueprintName.LIFE_SUPPORT, ModuleSize.SIZE_6, ModuleClass.C, 556110, "Int_LifeSupport_Size6_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 40.00), Map.entry(HorizonsModifier.INTEGRITY, 113.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.80), Map.entry(HorizonsModifier.BOOT_TIME, 1.0), Map.entry(HorizonsModifier.EMERGENCY_LIFE_SUPPORT, 600.0)));
    public static final LifeSupport LIFE_SUPPORT_6_B = new LifeSupport("LIFE_SUPPORT_6_B", HorizonsBlueprintName.LIFE_SUPPORT, ModuleSize.SIZE_6, ModuleClass.B, 1390280, "Int_LifeSupport_Size6_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 64.00), Map.entry(HorizonsModifier.INTEGRITY, 124.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.88), Map.entry(HorizonsModifier.BOOT_TIME, 1.0), Map.entry(HorizonsModifier.EMERGENCY_LIFE_SUPPORT, 900.0)));
    public static final LifeSupport LIFE_SUPPORT_6_A = new LifeSupport("LIFE_SUPPORT_6_A", HorizonsBlueprintName.LIFE_SUPPORT, ModuleSize.SIZE_6, ModuleClass.A, 3475690, "Int_LifeSupport_Size6_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 40.00), Map.entry(HorizonsModifier.INTEGRITY, 136.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.96), Map.entry(HorizonsModifier.BOOT_TIME, 1.0), Map.entry(HorizonsModifier.EMERGENCY_LIFE_SUPPORT, 1500.0)));
    public static final LifeSupport LIFE_SUPPORT_7_E = new LifeSupport("LIFE_SUPPORT_7_E", HorizonsBlueprintName.LIFE_SUPPORT, ModuleSize.SIZE_7, ModuleClass.E, 249140, "Int_LifeSupport_Size7_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 80.00), Map.entry(HorizonsModifier.INTEGRITY, 105.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.72), Map.entry(HorizonsModifier.BOOT_TIME, 1.0), Map.entry(HorizonsModifier.EMERGENCY_LIFE_SUPPORT, 300.0)));
    public static final LifeSupport LIFE_SUPPORT_7_D = new LifeSupport("LIFE_SUPPORT_7_D", HorizonsBlueprintName.LIFE_SUPPORT, ModuleSize.SIZE_7, ModuleClass.D, 622840, "Int_LifeSupport_Size7_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 32.00), Map.entry(HorizonsModifier.INTEGRITY, 118.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.81), Map.entry(HorizonsModifier.BOOT_TIME, 1.0), Map.entry(HorizonsModifier.EMERGENCY_LIFE_SUPPORT, 450.0)));
    public static final LifeSupport LIFE_SUPPORT_7_C = new LifeSupport("LIFE_SUPPORT_7_C", HorizonsBlueprintName.LIFE_SUPPORT, ModuleSize.SIZE_7, ModuleClass.C, 1557110, "Int_LifeSupport_Size7_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 80.00), Map.entry(HorizonsModifier.INTEGRITY, 131.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.90), Map.entry(HorizonsModifier.BOOT_TIME, 1.0), Map.entry(HorizonsModifier.EMERGENCY_LIFE_SUPPORT, 600.0)));
    public static final LifeSupport LIFE_SUPPORT_7_B = new LifeSupport("LIFE_SUPPORT_7_B", HorizonsBlueprintName.LIFE_SUPPORT, ModuleSize.SIZE_7, ModuleClass.B, 3892770, "Int_LifeSupport_Size7_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 128.00), Map.entry(HorizonsModifier.INTEGRITY, 144.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.99), Map.entry(HorizonsModifier.BOOT_TIME, 1.0), Map.entry(HorizonsModifier.EMERGENCY_LIFE_SUPPORT, 900.0)));
    public static final LifeSupport LIFE_SUPPORT_7_A = new LifeSupport("LIFE_SUPPORT_7_A", HorizonsBlueprintName.LIFE_SUPPORT, ModuleSize.SIZE_7, ModuleClass.A, 9731930, "Int_LifeSupport_Size7_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 80.00), Map.entry(HorizonsModifier.INTEGRITY, 157.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.08), Map.entry(HorizonsModifier.BOOT_TIME, 1.0), Map.entry(HorizonsModifier.EMERGENCY_LIFE_SUPPORT, 1500.0)));
    public static final LifeSupport LIFE_SUPPORT_8_E = new LifeSupport("LIFE_SUPPORT_8_E", HorizonsBlueprintName.LIFE_SUPPORT, ModuleSize.SIZE_8, ModuleClass.E, 697590, "Int_LifeSupport_Size8_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 160.00), Map.entry(HorizonsModifier.INTEGRITY, 120.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.80), Map.entry(HorizonsModifier.BOOT_TIME, 1.0), Map.entry(HorizonsModifier.EMERGENCY_LIFE_SUPPORT, 300.0)));
    public static final LifeSupport LIFE_SUPPORT_8_D = new LifeSupport("LIFE_SUPPORT_8_D", HorizonsBlueprintName.LIFE_SUPPORT, ModuleSize.SIZE_8, ModuleClass.D, 1743970, "Int_LifeSupport_Size8_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 64.00), Map.entry(HorizonsModifier.INTEGRITY, 135.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.90), Map.entry(HorizonsModifier.BOOT_TIME, 1.0), Map.entry(HorizonsModifier.EMERGENCY_LIFE_SUPPORT, 450.0)));
    public static final LifeSupport LIFE_SUPPORT_8_C = new LifeSupport("LIFE_SUPPORT_8_C", HorizonsBlueprintName.LIFE_SUPPORT, ModuleSize.SIZE_8, ModuleClass.C, 4359900, "Int_LifeSupport_Size8_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 160.00), Map.entry(HorizonsModifier.INTEGRITY, 150.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.00), Map.entry(HorizonsModifier.BOOT_TIME, 1.0), Map.entry(HorizonsModifier.EMERGENCY_LIFE_SUPPORT, 600.0)));
    public static final LifeSupport LIFE_SUPPORT_8_B = new LifeSupport("LIFE_SUPPORT_8_B", HorizonsBlueprintName.LIFE_SUPPORT, ModuleSize.SIZE_8, ModuleClass.B, 10899770, "Int_LifeSupport_Size8_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 256.00), Map.entry(HorizonsModifier.INTEGRITY, 165.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.10), Map.entry(HorizonsModifier.BOOT_TIME, 1.0), Map.entry(HorizonsModifier.EMERGENCY_LIFE_SUPPORT, 900.0)));
    public static final LifeSupport LIFE_SUPPORT_8_A = new LifeSupport("LIFE_SUPPORT_8_A", HorizonsBlueprintName.LIFE_SUPPORT, ModuleSize.SIZE_8, ModuleClass.A, 27249400, "Int_LifeSupport_Size8_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 160.00), Map.entry(HorizonsModifier.INTEGRITY, 180.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.20), Map.entry(HorizonsModifier.BOOT_TIME, 1.0), Map.entry(HorizonsModifier.EMERGENCY_LIFE_SUPPORT, 1500.0)));
    public static final List<LifeSupport> LIFE_SUPPORTS = List.of(
            LIFE_SUPPORT_1_E,
            LIFE_SUPPORT_1_D,
            LIFE_SUPPORT_1_C,
            LIFE_SUPPORT_1_B,
            LIFE_SUPPORT_1_A,
            LIFE_SUPPORT_2_E,
            LIFE_SUPPORT_2_D,
            LIFE_SUPPORT_2_C,
            LIFE_SUPPORT_2_B,
            LIFE_SUPPORT_2_A,
            LIFE_SUPPORT_3_E,
            LIFE_SUPPORT_3_D,
            LIFE_SUPPORT_3_C,
            LIFE_SUPPORT_3_B,
            LIFE_SUPPORT_3_A,
            LIFE_SUPPORT_4_E,
            LIFE_SUPPORT_4_D,
            LIFE_SUPPORT_4_C,
            LIFE_SUPPORT_4_B,
            LIFE_SUPPORT_4_A,
            LIFE_SUPPORT_5_E,
            LIFE_SUPPORT_5_D,
            LIFE_SUPPORT_5_C,
            LIFE_SUPPORT_5_B,
            LIFE_SUPPORT_5_A,
            LIFE_SUPPORT_6_E,
            LIFE_SUPPORT_6_D,
            LIFE_SUPPORT_6_C,
            LIFE_SUPPORT_6_B,
            LIFE_SUPPORT_6_A,
            LIFE_SUPPORT_7_E,
            LIFE_SUPPORT_7_D,
            LIFE_SUPPORT_7_C,
            LIFE_SUPPORT_7_B,
            LIFE_SUPPORT_7_A,
            LIFE_SUPPORT_8_E,
            LIFE_SUPPORT_8_D,
            LIFE_SUPPORT_8_C,
            LIFE_SUPPORT_8_B,
            LIFE_SUPPORT_8_A
    );

    private LifeSupport(final String id, final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final long basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, basePrice, internalName, attributes);
    }

    private LifeSupport(final LifeSupport lifeSupport) {
        super(lifeSupport);
    }


    @Override
    public List<HorizonsBlueprintType> getAllowedBlueprints() {
        return LifeSupportBlueprints.BLUEPRINTS.keySet().stream().toList();
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedExperimentalEffects() {
        return Collections.emptyList();
    }

    @Override
    public LifeSupport Clone() {
        return new LifeSupport(this);
    }
}
