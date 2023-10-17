package nl.jixxed.eliteodysseymaterials.domain.ships.core_internals;

import nl.jixxed.eliteodysseymaterials.constants.horizons.ExperimentalEffectBlueprints;
import nl.jixxed.eliteodysseymaterials.constants.horizons.core_internals.PowerPlantBlueprints;
import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PowerPlant extends CoreModule {

    public static final PowerPlant POWER_PLANT_2_E = new PowerPlant("POWER_PLANT_2_E", HorizonsBlueprintName.POWER_PLANT, ModuleSize.SIZE_2, ModuleClass.E, 1980, "Int_Powerplant_Size2_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.50), Map.entry(HorizonsModifier.INTEGRITY, 46.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 6.40), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 1.00)));
    public static final PowerPlant POWER_PLANT_2_D = new PowerPlant("POWER_PLANT_2_D", HorizonsBlueprintName.POWER_PLANT, ModuleSize.SIZE_2, ModuleClass.D, 5930, "Int_Powerplant_Size2_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 1.00), Map.entry(HorizonsModifier.INTEGRITY, 41.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 7.20), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 0.75)));
    public static final PowerPlant POWER_PLANT_2_C = new PowerPlant("POWER_PLANT_2_C", HorizonsBlueprintName.POWER_PLANT, ModuleSize.SIZE_2, ModuleClass.C, 17790, "Int_Powerplant_Size2_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 1.30), Map.entry(HorizonsModifier.INTEGRITY, 51.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 8.00), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 0.50)));
    public static final PowerPlant POWER_PLANT_2_B = new PowerPlant("POWER_PLANT_2_B", HorizonsBlueprintName.POWER_PLANT, ModuleSize.SIZE_2, ModuleClass.B, 53380, "Int_Powerplant_Size2_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.00), Map.entry(HorizonsModifier.INTEGRITY, 61.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 8.80), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 0.45)));
    public static final PowerPlant POWER_PLANT_2_A = new PowerPlant("POWER_PLANT_2_A", HorizonsBlueprintName.POWER_PLANT, ModuleSize.SIZE_2, ModuleClass.A, 160140, "Int_Powerplant_Size2_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 1.30), Map.entry(HorizonsModifier.INTEGRITY, 56.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 9.60), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 0.40)));
    public static final PowerPlant POWER_PLANT_3_E = new PowerPlant("POWER_PLANT_3_E", HorizonsBlueprintName.POWER_PLANT, ModuleSize.SIZE_3, ModuleClass.E, 5930, "Int_Powerplant_Size3_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 5.00), Map.entry(HorizonsModifier.INTEGRITY, 58.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 8.00), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 1.00)));
    public static final PowerPlant POWER_PLANT_3_D = new PowerPlant("POWER_PLANT_3_D", HorizonsBlueprintName.POWER_PLANT, ModuleSize.SIZE_3, ModuleClass.D, 17790, "Int_Powerplant_Size3_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.00), Map.entry(HorizonsModifier.INTEGRITY, 51.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 9.00), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 0.75)));
    public static final PowerPlant POWER_PLANT_3_C = new PowerPlant("POWER_PLANT_3_C", HorizonsBlueprintName.POWER_PLANT, ModuleSize.SIZE_3, ModuleClass.C, 53380, "Int_Powerplant_Size3_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.50), Map.entry(HorizonsModifier.INTEGRITY, 64.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 10.00), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 0.50)));
    public static final PowerPlant POWER_PLANT_3_B = new PowerPlant("POWER_PLANT_3_B", HorizonsBlueprintName.POWER_PLANT, ModuleSize.SIZE_3, ModuleClass.B, 160140, "Int_Powerplant_Size3_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.00), Map.entry(HorizonsModifier.INTEGRITY, 77.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 11.00), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 0.45)));
    public static final PowerPlant POWER_PLANT_3_A = new PowerPlant("POWER_PLANT_3_A", HorizonsBlueprintName.POWER_PLANT, ModuleSize.SIZE_3, ModuleClass.A, 480410, "Int_Powerplant_Size3_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.50), Map.entry(HorizonsModifier.INTEGRITY, 70.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 12.00), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 0.40)));
    public static final PowerPlant POWER_PLANT_4_E = new PowerPlant("POWER_PLANT_4_E", HorizonsBlueprintName.POWER_PLANT, ModuleSize.SIZE_4, ModuleClass.E, 17790, "Int_Powerplant_Size4_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 10.00), Map.entry(HorizonsModifier.INTEGRITY, 72.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 10.40), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 1.00)));
    public static final PowerPlant POWER_PLANT_4_D = new PowerPlant("POWER_PLANT_4_D", HorizonsBlueprintName.POWER_PLANT, ModuleSize.SIZE_4, ModuleClass.D, 53380, "Int_Powerplant_Size4_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.00), Map.entry(HorizonsModifier.INTEGRITY, 64.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 11.70), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 0.75)));
    public static final PowerPlant POWER_PLANT_4_C = new PowerPlant("POWER_PLANT_4_C", HorizonsBlueprintName.POWER_PLANT, ModuleSize.SIZE_4, ModuleClass.C, 160140, "Int_Powerplant_Size4_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 5.00), Map.entry(HorizonsModifier.INTEGRITY, 80.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 13.00), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 0.50)));
    public static final PowerPlant POWER_PLANT_4_B = new PowerPlant("POWER_PLANT_4_B", HorizonsBlueprintName.POWER_PLANT, ModuleSize.SIZE_4, ModuleClass.B, 480410, "Int_Powerplant_Size4_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 8.00), Map.entry(HorizonsModifier.INTEGRITY, 96.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 14.30), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 0.45)));
    public static final PowerPlant POWER_PLANT_4_A = new PowerPlant("POWER_PLANT_4_A", HorizonsBlueprintName.POWER_PLANT, ModuleSize.SIZE_4, ModuleClass.A, 1441230, "Int_Powerplant_Size4_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 5.00), Map.entry(HorizonsModifier.INTEGRITY, 88.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 15.60), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 0.40)));
    public static final PowerPlant POWER_PLANT_5_E = new PowerPlant("POWER_PLANT_5_E", HorizonsBlueprintName.POWER_PLANT, ModuleSize.SIZE_5, ModuleClass.E, 53380, "Int_Powerplant_Size5_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 20.00), Map.entry(HorizonsModifier.INTEGRITY, 86.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 13.60), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 1.00)));
    public static final PowerPlant POWER_PLANT_5_D = new PowerPlant("POWER_PLANT_5_D", HorizonsBlueprintName.POWER_PLANT, ModuleSize.SIZE_5, ModuleClass.D, 160140, "Int_Powerplant_Size5_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 8.00), Map.entry(HorizonsModifier.INTEGRITY, 77.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 15.30), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 0.75)));
    public static final PowerPlant POWER_PLANT_5_C = new PowerPlant("POWER_PLANT_5_C", HorizonsBlueprintName.POWER_PLANT, ModuleSize.SIZE_5, ModuleClass.C, 480410, "Int_Powerplant_Size5_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 10.00), Map.entry(HorizonsModifier.INTEGRITY, 96.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 17.00), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 0.50)));
    public static final PowerPlant POWER_PLANT_5_B = new PowerPlant("POWER_PLANT_5_B", HorizonsBlueprintName.POWER_PLANT, ModuleSize.SIZE_5, ModuleClass.B, 1441230, "Int_Powerplant_Size5_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 16.00), Map.entry(HorizonsModifier.INTEGRITY, 115.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 18.70), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 0.45)));
    public static final PowerPlant POWER_PLANT_5_A = new PowerPlant("POWER_PLANT_5_A", HorizonsBlueprintName.POWER_PLANT, ModuleSize.SIZE_5, ModuleClass.A, 4323700, "Int_Powerplant_Size5_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 10.00), Map.entry(HorizonsModifier.INTEGRITY, 106.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 20.40), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 0.40)));
    public static final PowerPlant POWER_PLANT_6_E = new PowerPlant("POWER_PLANT_6_E", HorizonsBlueprintName.POWER_PLANT, ModuleSize.SIZE_6, ModuleClass.E, 160140, "Int_Powerplant_Size6_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 40.00), Map.entry(HorizonsModifier.INTEGRITY, 102.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 16.80), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 1.00)));
    public static final PowerPlant POWER_PLANT_6_D = new PowerPlant("POWER_PLANT_6_D", HorizonsBlueprintName.POWER_PLANT, ModuleSize.SIZE_6, ModuleClass.D, 480410, "Int_Powerplant_Size6_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 16.00), Map.entry(HorizonsModifier.INTEGRITY, 90.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 18.90), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 0.75)));
    public static final PowerPlant POWER_PLANT_6_C = new PowerPlant("POWER_PLANT_6_C", HorizonsBlueprintName.POWER_PLANT, ModuleSize.SIZE_6, ModuleClass.C, 1441230, "Int_Powerplant_Size6_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 20.00), Map.entry(HorizonsModifier.INTEGRITY, 113.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 21.00), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 0.50)));
    public static final PowerPlant POWER_PLANT_6_B = new PowerPlant("POWER_PLANT_6_B", HorizonsBlueprintName.POWER_PLANT, ModuleSize.SIZE_6, ModuleClass.B, 4323700, "Int_Powerplant_Size6_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 32.00), Map.entry(HorizonsModifier.INTEGRITY, 136.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 23.10), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 0.45)));
    public static final PowerPlant POWER_PLANT_6_A = new PowerPlant("POWER_PLANT_6_A", HorizonsBlueprintName.POWER_PLANT, ModuleSize.SIZE_6, ModuleClass.A, 12971100, "Int_Powerplant_Size6_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 20.00), Map.entry(HorizonsModifier.INTEGRITY, 124.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 25.20), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 0.40)));
    public static final PowerPlant POWER_PLANT_7_E = new PowerPlant("POWER_PLANT_7_E", HorizonsBlueprintName.POWER_PLANT, ModuleSize.SIZE_7, ModuleClass.E, 480410, "Int_Powerplant_Size7_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 80.00), Map.entry(HorizonsModifier.INTEGRITY, 118.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 20.00), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 1.00)));
    public static final PowerPlant POWER_PLANT_7_D = new PowerPlant("POWER_PLANT_7_D", HorizonsBlueprintName.POWER_PLANT, ModuleSize.SIZE_7, ModuleClass.D, 1441230, "Int_Powerplant_Size7_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 32.00), Map.entry(HorizonsModifier.INTEGRITY, 105.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 22.50), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 0.75)));
    public static final PowerPlant POWER_PLANT_7_C = new PowerPlant("POWER_PLANT_7_C", HorizonsBlueprintName.POWER_PLANT, ModuleSize.SIZE_7, ModuleClass.C, 4323700, "Int_Powerplant_Size7_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 40.00), Map.entry(HorizonsModifier.INTEGRITY, 131.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 25.00), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 0.50)));
    public static final PowerPlant POWER_PLANT_7_B = new PowerPlant("POWER_PLANT_7_B", HorizonsBlueprintName.POWER_PLANT, ModuleSize.SIZE_7, ModuleClass.B, 12971100, "Int_Powerplant_Size7_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 64.00), Map.entry(HorizonsModifier.INTEGRITY, 157.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 27.50), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 0.45)));
    public static final PowerPlant POWER_PLANT_7_A = new PowerPlant("POWER_PLANT_7_A", HorizonsBlueprintName.POWER_PLANT, ModuleSize.SIZE_7, ModuleClass.A, 38913290, "Int_Powerplant_Size7_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 40.00), Map.entry(HorizonsModifier.INTEGRITY, 144.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 30.00), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 0.40)));
    public static final PowerPlant POWER_PLANT_8_E = new PowerPlant("POWER_PLANT_8_E", HorizonsBlueprintName.POWER_PLANT, ModuleSize.SIZE_8, ModuleClass.E, 1441230, "Int_Powerplant_Size8_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 160.00), Map.entry(HorizonsModifier.INTEGRITY, 135.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 24.00), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 1.00)));
    public static final PowerPlant POWER_PLANT_8_D = new PowerPlant("POWER_PLANT_8_D", HorizonsBlueprintName.POWER_PLANT, ModuleSize.SIZE_8, ModuleClass.D, 4323700, "Int_Powerplant_Size8_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 64.00), Map.entry(HorizonsModifier.INTEGRITY, 120.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 27.00), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 0.75)));
    public static final PowerPlant POWER_PLANT_8_C = new PowerPlant("POWER_PLANT_8_C", HorizonsBlueprintName.POWER_PLANT, ModuleSize.SIZE_8, ModuleClass.C, 12971100, "Int_Powerplant_Size8_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 80.00), Map.entry(HorizonsModifier.INTEGRITY, 150.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 30.00), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 0.50)));
    public static final PowerPlant POWER_PLANT_8_B = new PowerPlant("POWER_PLANT_8_B", HorizonsBlueprintName.POWER_PLANT, ModuleSize.SIZE_8, ModuleClass.B, 38913290, "Int_Powerplant_Size8_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 128.00), Map.entry(HorizonsModifier.INTEGRITY, 180.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 33.00), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 0.45)));
    public static final PowerPlant POWER_PLANT_8_A = new PowerPlant("POWER_PLANT_8_A", HorizonsBlueprintName.POWER_PLANT, ModuleSize.SIZE_8, ModuleClass.A, 116739870, "Int_Powerplant_Size8_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 80.00), Map.entry(HorizonsModifier.INTEGRITY, 165.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 36.00), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 0.40)));
    public static final PowerPlant GUARDIAN_POWER_PLANT_2_A = new PowerPlant("GUARDIAN_POWER_PLANT_2_A", HorizonsBlueprintName.GUARDIAN_POWER_PLANT, ModuleSize.SIZE_2, ModuleClass.A, Origin.GUARDIAN, 192170, "Int_GuardianPowerplant_Size2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 1.50), Map.entry(HorizonsModifier.INTEGRITY, 56.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 12.70), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 0.5)));
    public static final PowerPlant GUARDIAN_POWER_PLANT_3_A = new PowerPlant("GUARDIAN_POWER_PLANT_3_A", HorizonsBlueprintName.GUARDIAN_POWER_PLANT, ModuleSize.SIZE_3, ModuleClass.A, Origin.GUARDIAN, 576490, "Int_GuardianPowerplant_Size3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.90), Map.entry(HorizonsModifier.INTEGRITY, 70.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 15.80), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 0.5)));
    public static final PowerPlant GUARDIAN_POWER_PLANT_4_A = new PowerPlant("GUARDIAN_POWER_PLANT_4_A", HorizonsBlueprintName.GUARDIAN_POWER_PLANT, ModuleSize.SIZE_4, ModuleClass.A, Origin.GUARDIAN, 1729480, "Int_GuardianPowerplant_Size4", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 5.90), Map.entry(HorizonsModifier.INTEGRITY, 88.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 20.60), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 0.5)));
    public static final PowerPlant GUARDIAN_POWER_PLANT_5_A = new PowerPlant("GUARDIAN_POWER_PLANT_5_A", HorizonsBlueprintName.GUARDIAN_POWER_PLANT, ModuleSize.SIZE_5, ModuleClass.A, Origin.GUARDIAN, 5188440, "Int_GuardianPowerplant_Size5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 11.70), Map.entry(HorizonsModifier.INTEGRITY, 106.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 26.90), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 0.5)));
    public static final PowerPlant GUARDIAN_POWER_PLANT_6_A = new PowerPlant("GUARDIAN_POWER_PLANT_6_A", HorizonsBlueprintName.GUARDIAN_POWER_PLANT, ModuleSize.SIZE_6, ModuleClass.A, Origin.GUARDIAN, 15565320, "Int_GuardianPowerplant_Size6", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 23.40), Map.entry(HorizonsModifier.INTEGRITY, 124.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 33.30), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 0.5)));
    public static final PowerPlant GUARDIAN_POWER_PLANT_7_A = new PowerPlant("GUARDIAN_POWER_PLANT_7_A", HorizonsBlueprintName.GUARDIAN_POWER_PLANT, ModuleSize.SIZE_7, ModuleClass.A, Origin.GUARDIAN, 46695950, "Int_GuardianPowerplant_Size7", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 46.80), Map.entry(HorizonsModifier.INTEGRITY, 144.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 39.60), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 0.5)));
    public static final PowerPlant GUARDIAN_POWER_PLANT_8_A = new PowerPlant("GUARDIAN_POWER_PLANT_8_A", HorizonsBlueprintName.GUARDIAN_POWER_PLANT, ModuleSize.SIZE_8, ModuleClass.A, Origin.GUARDIAN, 140087850, "Int_GuardianPowerplant_Size8", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 93.60), Map.entry(HorizonsModifier.INTEGRITY, 165.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 47.50), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 0.5)));
    public static final PowerPlant POWER_PLANT_3_A_ARMOURED_OVERCHARGED = new PowerPlant("POWER_PLANT_3_A_ARMOURED_OVERCHARGED", HorizonsBlueprintName.POWER_PLANT_ARMOURED_OVERCHARGED, ModuleSize.SIZE_3, ModuleClass.A, 480410, "Int_Powerplant_Size3_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.50), Map.entry(HorizonsModifier.INTEGRITY, 70.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 12.00), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 0.40)));
    public static final PowerPlant POWER_PLANT_3_A_OVERCHARGED_OVERCHARGED = new PowerPlant("POWER_PLANT_3_A_OVERCHARGED_OVERCHARGED", HorizonsBlueprintName.POWER_PLANT_OVERCHARGED_OVERCHARGED, ModuleSize.SIZE_3, ModuleClass.A, 480410, "Int_Powerplant_Size3_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.50), Map.entry(HorizonsModifier.INTEGRITY, 70.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 12.00), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 0.40)));
    public static final PowerPlant POWER_PLANT_4_A_OVERCHARGED_OVERCHARGED = new PowerPlant("POWER_PLANT_4_A_OVERCHARGED_OVERCHARGED", HorizonsBlueprintName.POWER_PLANT_OVERCHARGED_OVERCHARGED, ModuleSize.SIZE_4, ModuleClass.A, 1441230, "Int_Powerplant_Size4_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 5.00), Map.entry(HorizonsModifier.INTEGRITY, 88.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 15.60), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 0.40)));
    public static final PowerPlant POWER_PLANT_5_A_OVERCHARGED_OVERCHARGED = new PowerPlant("POWER_PLANT_5_A_OVERCHARGED_OVERCHARGED", HorizonsBlueprintName.POWER_PLANT_OVERCHARGED_OVERCHARGED, ModuleSize.SIZE_5, ModuleClass.A, 4323700, "Int_Powerplant_Size5_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 10.00), Map.entry(HorizonsModifier.INTEGRITY, 106.0), Map.entry(HorizonsModifier.POWER_CAPACITY, 20.40), Map.entry(HorizonsModifier.HEAT_EFFICIENCY, 0.40)));

    static {
        POWER_PLANT_3_A_ARMOURED_OVERCHARGED.getModifications().add(
                new Modification(HorizonsBlueprintType.ARMOURED_OVERCHARGED, 1.0, HorizonsBlueprintGrade.GRADE_5)
        );
        POWER_PLANT_3_A_OVERCHARGED_OVERCHARGED.getModifications().add(
                new Modification(HorizonsBlueprintType.OVERCHARGED_OVERCHARGED, 1.0, HorizonsBlueprintGrade.GRADE_5)
        );
        POWER_PLANT_4_A_OVERCHARGED_OVERCHARGED.getModifications().add(
                new Modification(HorizonsBlueprintType.OVERCHARGED_OVERCHARGED, 1.0, HorizonsBlueprintGrade.GRADE_5)
        );
        POWER_PLANT_5_A_OVERCHARGED_OVERCHARGED.getModifications().add(
                new Modification(HorizonsBlueprintType.OVERCHARGED_OVERCHARGED, 1.0, HorizonsBlueprintGrade.GRADE_5)
        );
    }

    public static final List<PowerPlant> POWER_PLANTS = List.of(
            POWER_PLANT_2_E,
            POWER_PLANT_2_D,
            POWER_PLANT_2_C,
            POWER_PLANT_2_B,
            POWER_PLANT_2_A,
            POWER_PLANT_3_E,
            POWER_PLANT_3_D,
            POWER_PLANT_3_C,
            POWER_PLANT_3_B,
            POWER_PLANT_3_A,
            POWER_PLANT_4_E,
            POWER_PLANT_4_D,
            POWER_PLANT_4_C,
            POWER_PLANT_4_B,
            POWER_PLANT_4_A,
            POWER_PLANT_5_E,
            POWER_PLANT_5_D,
            POWER_PLANT_5_C,
            POWER_PLANT_5_B,
            POWER_PLANT_5_A,
            POWER_PLANT_6_E,
            POWER_PLANT_6_D,
            POWER_PLANT_6_C,
            POWER_PLANT_6_B,
            POWER_PLANT_6_A,
            POWER_PLANT_7_E,
            POWER_PLANT_7_D,
            POWER_PLANT_7_C,
            POWER_PLANT_7_B,
            POWER_PLANT_7_A,
            POWER_PLANT_8_E,
            POWER_PLANT_8_D,
            POWER_PLANT_8_C,
            POWER_PLANT_8_B,
            POWER_PLANT_8_A,
            GUARDIAN_POWER_PLANT_2_A,
            GUARDIAN_POWER_PLANT_3_A,
            GUARDIAN_POWER_PLANT_4_A,
            GUARDIAN_POWER_PLANT_5_A,
            GUARDIAN_POWER_PLANT_6_A,
            GUARDIAN_POWER_PLANT_7_A,
            GUARDIAN_POWER_PLANT_8_A,
            POWER_PLANT_3_A_ARMOURED_OVERCHARGED,
            POWER_PLANT_3_A_OVERCHARGED_OVERCHARGED,
            POWER_PLANT_4_A_OVERCHARGED_OVERCHARGED,
            POWER_PLANT_5_A_OVERCHARGED_OVERCHARGED
    );

    private PowerPlant(final String id, final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final int basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, basePrice, internalName, attributes);
    }

    private PowerPlant(final String id, final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final Origin origin, final int basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, basePrice, internalName, attributes);
    }

    private PowerPlant(final PowerPlant powerPlant) {
        super(powerPlant);
    }


    @Override
    public List<HorizonsBlueprintType> getAllowedBlueprints() {
        if (isPreEngineered()) {
            return Collections.emptyList();
        }
        if (this.getOrigin() == Origin.GUARDIAN) {
            return Collections.emptyList();
        }
        return PowerPlantBlueprints.BLUEPRINTS.keySet().stream().toList();
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedExperimentalEffects() {
        if (isPreEngineered()) {
            return Collections.emptyList();
        }
        if (this.getOrigin() == Origin.GUARDIAN) {
            return Collections.emptyList();
        }
        return ExperimentalEffectBlueprints.POWER_PLANT.keySet().stream().toList();
    }

    @Override
    public PowerPlant Clone() {
        return new PowerPlant(this);
    }


    @Override
    public boolean isPreEngineered() {
        return HorizonsBlueprintName.POWER_PLANT_ARMOURED_OVERCHARGED.equals(this.getName()) || HorizonsBlueprintName.POWER_PLANT_OVERCHARGED_OVERCHARGED.equals(this.getName());
    }

    @Override
    public String getClarifier() {
        return this.getOrigin() == Origin.GUARDIAN || isPreEngineered() ? " " + LocaleService.getLocalizedStringForCurrentLocale(getName().getLocalizationKey()) : "";
    }

    @Override
    public boolean isCGExclusive() {
        return isPreEngineered();
    }
}
