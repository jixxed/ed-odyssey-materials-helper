package nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals;

import nl.jixxed.eliteodysseymaterials.constants.horizons.optional_internals.FuelScoopBlueprints;
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

public class FuelScoop extends OptionalModule {
    public static final FuelScoop FUEL_SCOOP_1_E = new FuelScoop("FUEL_SCOOP_1_E", HorizonsBlueprintName.FUEL_SCOOP, ModuleSize.SIZE_1, ModuleClass.E, false, 310, "Int_FuelScoop_Size1_Class1", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  32.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.14), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.SCOOP_RATE,  0.018)));
    public static final FuelScoop FUEL_SCOOP_1_D = new FuelScoop("FUEL_SCOOP_1_D", HorizonsBlueprintName.FUEL_SCOOP, ModuleSize.SIZE_1, ModuleClass.D, false, 1290, "Int_FuelScoop_Size1_Class2", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  24.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.18), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.SCOOP_RATE,  0.024)));
    public static final FuelScoop FUEL_SCOOP_1_C = new FuelScoop("FUEL_SCOOP_1_C", HorizonsBlueprintName.FUEL_SCOOP, ModuleSize.SIZE_1, ModuleClass.C, false, 5140, "Int_FuelScoop_Size1_Class3", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  40.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.23), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.SCOOP_RATE,  0.03)));
    public static final FuelScoop FUEL_SCOOP_1_B = new FuelScoop("FUEL_SCOOP_1_B", HorizonsBlueprintName.FUEL_SCOOP, ModuleSize.SIZE_1, ModuleClass.B, false, 20570, "Int_FuelScoop_Size1_Class4", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  56.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.28), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.SCOOP_RATE,  0.036)));
    public static final FuelScoop FUEL_SCOOP_1_A = new FuelScoop("FUEL_SCOOP_1_A", HorizonsBlueprintName.FUEL_SCOOP, ModuleSize.SIZE_1, ModuleClass.A, false, 82270, "Int_FuelScoop_Size1_Class5", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  48.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.32), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.SCOOP_RATE,  0.042)));
    public static final FuelScoop FUEL_SCOOP_2_E = new FuelScoop("FUEL_SCOOP_2_E", HorizonsBlueprintName.FUEL_SCOOP, ModuleSize.SIZE_2, ModuleClass.E, false, 1070, "Int_FuelScoop_Size2_Class1", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  41.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.17), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.SCOOP_RATE,  0.032)));
    public static final FuelScoop FUEL_SCOOP_2_D = new FuelScoop("FUEL_SCOOP_2_D", HorizonsBlueprintName.FUEL_SCOOP, ModuleSize.SIZE_2, ModuleClass.D, false, 4450, "Int_FuelScoop_Size2_Class2", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  31.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.22), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.SCOOP_RATE,  0.043)));
    public static final FuelScoop FUEL_SCOOP_2_C = new FuelScoop("FUEL_SCOOP_2_C", HorizonsBlueprintName.FUEL_SCOOP, ModuleSize.SIZE_2, ModuleClass.C, false, 17800, "Int_FuelScoop_Size2_Class3", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  51.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.28), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.SCOOP_RATE,  0.054)));
    public static final FuelScoop FUEL_SCOOP_2_B = new FuelScoop("FUEL_SCOOP_2_B", HorizonsBlueprintName.FUEL_SCOOP, ModuleSize.SIZE_2, ModuleClass.B, false, 71210, "Int_FuelScoop_Size2_Class4", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  70.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.34), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.SCOOP_RATE,  0.065)));
    public static final FuelScoop FUEL_SCOOP_2_A = new FuelScoop("FUEL_SCOOP_2_A", HorizonsBlueprintName.FUEL_SCOOP, ModuleSize.SIZE_2, ModuleClass.A, false, 284840, "Int_FuelScoop_Size2_Class5", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  61.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.39), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.SCOOP_RATE,  0.075)));
    public static final FuelScoop FUEL_SCOOP_3_E = new FuelScoop("FUEL_SCOOP_3_E", HorizonsBlueprintName.FUEL_SCOOP, ModuleSize.SIZE_3, ModuleClass.E, false, 3390, "Int_FuelScoop_Size3_Class1", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  51.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.2), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.SCOOP_RATE,  0.075)));
    public static final FuelScoop FUEL_SCOOP_3_D = new FuelScoop("FUEL_SCOOP_3_D", HorizonsBlueprintName.FUEL_SCOOP, ModuleSize.SIZE_3, ModuleClass.D, false, 14110, "Int_FuelScoop_Size3_Class2", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  38.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.27), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.SCOOP_RATE,  0.1)));
    public static final FuelScoop FUEL_SCOOP_3_C = new FuelScoop("FUEL_SCOOP_3_C", HorizonsBlueprintName.FUEL_SCOOP, ModuleSize.SIZE_3, ModuleClass.C, false, 56440, "Int_FuelScoop_Size3_Class3", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  64.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.34), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.SCOOP_RATE,  0.126)));
    public static final FuelScoop FUEL_SCOOP_3_B = new FuelScoop("FUEL_SCOOP_3_B", HorizonsBlueprintName.FUEL_SCOOP, ModuleSize.SIZE_3, ModuleClass.B, false, 225740, "Int_FuelScoop_Size3_Class4", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  90.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.41), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.SCOOP_RATE,  0.151)));
    public static final FuelScoop FUEL_SCOOP_3_A = new FuelScoop("FUEL_SCOOP_3_A", HorizonsBlueprintName.FUEL_SCOOP, ModuleSize.SIZE_3, ModuleClass.A, false, 902950, "Int_FuelScoop_Size3_Class5", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  77.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.48), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.SCOOP_RATE,  0.176)));
    public static final FuelScoop FUEL_SCOOP_4_E = new FuelScoop("FUEL_SCOOP_4_E", HorizonsBlueprintName.FUEL_SCOOP, ModuleSize.SIZE_4, ModuleClass.E, false, 10730, "Int_FuelScoop_Size4_Class1", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  64.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.25), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.SCOOP_RATE,  0.147)));
    public static final FuelScoop FUEL_SCOOP_4_D = new FuelScoop("FUEL_SCOOP_4_D", HorizonsBlueprintName.FUEL_SCOOP, ModuleSize.SIZE_4, ModuleClass.D, false, 44720, "Int_FuelScoop_Size4_Class2", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  48.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.33), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.SCOOP_RATE,  0.196)));
    public static final FuelScoop FUEL_SCOOP_4_C = new FuelScoop("FUEL_SCOOP_4_C", HorizonsBlueprintName.FUEL_SCOOP, ModuleSize.SIZE_4, ModuleClass.C, false, 178900, "Int_FuelScoop_Size4_Class3", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  80.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.41), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.SCOOP_RATE,  0.245)));
    public static final FuelScoop FUEL_SCOOP_4_B = new FuelScoop("FUEL_SCOOP_4_B", HorizonsBlueprintName.FUEL_SCOOP, ModuleSize.SIZE_4, ModuleClass.B, false, 715590, "Int_FuelScoop_Size4_Class4", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  112.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.49), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.SCOOP_RATE,  0.294)));
    public static final FuelScoop FUEL_SCOOP_4_A = new FuelScoop("FUEL_SCOOP_4_A", HorizonsBlueprintName.FUEL_SCOOP, ModuleSize.SIZE_4, ModuleClass.A, false, 2862360, "Int_FuelScoop_Size4_Class5", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  96.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.57), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.SCOOP_RATE,  0.342)));
    public static final FuelScoop FUEL_SCOOP_5_E = new FuelScoop("FUEL_SCOOP_5_E", HorizonsBlueprintName.FUEL_SCOOP, ModuleSize.SIZE_5, ModuleClass.E, false, 34030, "Int_FuelScoop_Size5_Class1", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  77.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.3), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.SCOOP_RATE,  0.247)));
    public static final FuelScoop FUEL_SCOOP_5_D = new FuelScoop("FUEL_SCOOP_5_D", HorizonsBlueprintName.FUEL_SCOOP, ModuleSize.SIZE_5, ModuleClass.D, false, 141780, "Int_FuelScoop_Size5_Class2", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  58.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.4), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.SCOOP_RATE,  0.33)));
    public static final FuelScoop FUEL_SCOOP_5_C = new FuelScoop("FUEL_SCOOP_5_C", HorizonsBlueprintName.FUEL_SCOOP, ModuleSize.SIZE_5, ModuleClass.C, false, 567110, "Int_FuelScoop_Size5_Class3", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  96.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.5), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.SCOOP_RATE,  0.412)));
    public static final FuelScoop FUEL_SCOOP_5_B = new FuelScoop("FUEL_SCOOP_5_B", HorizonsBlueprintName.FUEL_SCOOP, ModuleSize.SIZE_5, ModuleClass.B, false, 2268420, "Int_FuelScoop_Size5_Class4", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  134.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.6), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.SCOOP_RATE,  0.494)));
    public static final FuelScoop FUEL_SCOOP_5_A = new FuelScoop("FUEL_SCOOP_5_A", HorizonsBlueprintName.FUEL_SCOOP, ModuleSize.SIZE_5, ModuleClass.A, false, 9073690, "Int_FuelScoop_Size5_Class5", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  115.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.7), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.SCOOP_RATE,  0.577)));
    public static final FuelScoop FUEL_SCOOP_6_E = new FuelScoop("FUEL_SCOOP_6_E", HorizonsBlueprintName.FUEL_SCOOP, ModuleSize.SIZE_6, ModuleClass.E, false, 107860, "Int_FuelScoop_Size6_Class1", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  90.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.35), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.SCOOP_RATE,  0.376)));
    public static final FuelScoop FUEL_SCOOP_6_D = new FuelScoop("FUEL_SCOOP_6_D", HorizonsBlueprintName.FUEL_SCOOP, ModuleSize.SIZE_6, ModuleClass.D, false, 449430, "Int_FuelScoop_Size6_Class2", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  68.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.47), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.SCOOP_RATE,  0.502)));
    public static final FuelScoop FUEL_SCOOP_6_C = new FuelScoop("FUEL_SCOOP_6_C", HorizonsBlueprintName.FUEL_SCOOP, ModuleSize.SIZE_6, ModuleClass.C, false, 1797730, "Int_FuelScoop_Size6_Class3", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  113.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.59), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.SCOOP_RATE,  0.627)));
    public static final FuelScoop FUEL_SCOOP_6_B = new FuelScoop("FUEL_SCOOP_6_B", HorizonsBlueprintName.FUEL_SCOOP, ModuleSize.SIZE_6, ModuleClass.B, false, 7190900, "Int_FuelScoop_Size6_Class4", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  158.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.71), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.SCOOP_RATE,  0.752)));
    public static final FuelScoop FUEL_SCOOP_6_A = new FuelScoop("FUEL_SCOOP_6_A", HorizonsBlueprintName.FUEL_SCOOP, ModuleSize.SIZE_6, ModuleClass.A, false, 28763610, "Int_FuelScoop_Size6_Class5", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  136.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.83), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.SCOOP_RATE,  0.878)));
    public static final FuelScoop FUEL_SCOOP_7_E = new FuelScoop("FUEL_SCOOP_7_E", HorizonsBlueprintName.FUEL_SCOOP, ModuleSize.SIZE_7, ModuleClass.E, false, 341930, "Int_FuelScoop_Size7_Class1", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  105.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.41), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.SCOOP_RATE,  0.534)));
    public static final FuelScoop FUEL_SCOOP_7_D = new FuelScoop("FUEL_SCOOP_7_D", HorizonsBlueprintName.FUEL_SCOOP, ModuleSize.SIZE_7, ModuleClass.D, false, 1424700, "Int_FuelScoop_Size7_Class2", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  79.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.55), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.SCOOP_RATE,  0.712)));
    public static final FuelScoop FUEL_SCOOP_7_C = new FuelScoop("FUEL_SCOOP_7_C", HorizonsBlueprintName.FUEL_SCOOP, ModuleSize.SIZE_7, ModuleClass.C, false, 5698790, "Int_FuelScoop_Size7_Class3", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  131.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.69), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.SCOOP_RATE,  0.89)));
    public static final FuelScoop FUEL_SCOOP_7_B = new FuelScoop("FUEL_SCOOP_7_B", HorizonsBlueprintName.FUEL_SCOOP, ModuleSize.SIZE_7, ModuleClass.B, false, 22795160, "Int_FuelScoop_Size7_Class4", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  183.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.83), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.SCOOP_RATE,  1.068)));
    public static final FuelScoop FUEL_SCOOP_7_A = new FuelScoop("FUEL_SCOOP_7_A", HorizonsBlueprintName.FUEL_SCOOP, ModuleSize.SIZE_7, ModuleClass.A, false, 91180640, "Int_FuelScoop_Size7_Class5", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  157.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.97), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.SCOOP_RATE,  1.245)));
    public static final FuelScoop FUEL_SCOOP_8_E = new FuelScoop("FUEL_SCOOP_8_E", HorizonsBlueprintName.FUEL_SCOOP, ModuleSize.SIZE_8, ModuleClass.E, false, 1083910, "Int_FuelScoop_Size8_Class1", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  120.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.48), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.SCOOP_RATE,  0.72)));
    public static final FuelScoop FUEL_SCOOP_8_D = new FuelScoop("FUEL_SCOOP_8_D", HorizonsBlueprintName.FUEL_SCOOP, ModuleSize.SIZE_8, ModuleClass.D, false, 4516290, "Int_FuelScoop_Size8_Class2", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  90.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.64), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.SCOOP_RATE,  0.96)));
    public static final FuelScoop FUEL_SCOOP_8_C = new FuelScoop("FUEL_SCOOP_8_C", HorizonsBlueprintName.FUEL_SCOOP, ModuleSize.SIZE_8, ModuleClass.C, false, 18065170, "Int_FuelScoop_Size8_Class3", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  150.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.8), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.SCOOP_RATE,  1.2)));
    public static final FuelScoop FUEL_SCOOP_8_B = new FuelScoop("FUEL_SCOOP_8_B", HorizonsBlueprintName.FUEL_SCOOP, ModuleSize.SIZE_8, ModuleClass.B, false, 72260660, "Int_FuelScoop_Size8_Class4", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  210.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.96), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.SCOOP_RATE,  1.44)));
    public static final FuelScoop FUEL_SCOOP_8_A = new FuelScoop("FUEL_SCOOP_8_A", HorizonsBlueprintName.FUEL_SCOOP, ModuleSize.SIZE_8, ModuleClass.A, false, 289042640, "Int_FuelScoop_Size8_Class5", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  180.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.12), Map.entry(HorizonsModifier.BOOT_TIME,  4.0), Map.entry(HorizonsModifier.SCOOP_RATE,  1.68)));

    public static final List<FuelScoop> FUEL_SCOOPS = List.of(
            FUEL_SCOOP_1_E,
            FUEL_SCOOP_1_D,
            FUEL_SCOOP_1_C,
            FUEL_SCOOP_1_B,
            FUEL_SCOOP_1_A,
            FUEL_SCOOP_2_E,
            FUEL_SCOOP_2_D,
            FUEL_SCOOP_2_C,
            FUEL_SCOOP_2_B,
            FUEL_SCOOP_2_A,
            FUEL_SCOOP_3_E,
            FUEL_SCOOP_3_D,
            FUEL_SCOOP_3_C,
            FUEL_SCOOP_3_B,
            FUEL_SCOOP_3_A,
            FUEL_SCOOP_4_E,
            FUEL_SCOOP_4_D,
            FUEL_SCOOP_4_C,
            FUEL_SCOOP_4_B,
            FUEL_SCOOP_4_A,
            FUEL_SCOOP_5_E,
            FUEL_SCOOP_5_D,
            FUEL_SCOOP_5_C,
            FUEL_SCOOP_5_B,
            FUEL_SCOOP_5_A,
            FUEL_SCOOP_6_E,
            FUEL_SCOOP_6_D,
            FUEL_SCOOP_6_C,
            FUEL_SCOOP_6_B,
            FUEL_SCOOP_6_A,
            FUEL_SCOOP_7_E,
            FUEL_SCOOP_7_D,
            FUEL_SCOOP_7_C,
            FUEL_SCOOP_7_B,
            FUEL_SCOOP_7_A,
            FUEL_SCOOP_8_E,
            FUEL_SCOOP_8_D,
            FUEL_SCOOP_8_C,
            FUEL_SCOOP_8_B,
            FUEL_SCOOP_8_A
    );
    public FuelScoop(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, basePrice, internalName, attributes);
    }

    public FuelScoop(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, basePrice, internalName, attributes);
    }

    public FuelScoop(FuelScoop fuelScoop) {
        super(fuelScoop);
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedBlueprints() {
        return FuelScoopBlueprints.BLUEPRINTS.keySet().stream().toList();
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedExperimentalEffects() {
        return Collections.emptyList();
    }

    @Override
    public FuelScoop Clone() {
        return new FuelScoop(this);
    }
}
