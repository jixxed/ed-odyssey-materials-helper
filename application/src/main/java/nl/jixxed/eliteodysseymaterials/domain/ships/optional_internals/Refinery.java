package nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals;

import nl.jixxed.eliteodysseymaterials.constants.horizons.optional_internals.RefineryBlueprints;
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

public class Refinery extends OptionalModule {
    public static final Refinery REFINERY_1_E = new Refinery("REFINERY_1_E", HorizonsBlueprintName.REFINERY, ModuleSize.SIZE_1, ModuleClass.E, false, 6000, "Int_Refinery_Size1_Class1", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  32.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.14), Map.entry(HorizonsModifier.BOOT_TIME,  10.0), Map.entry(HorizonsModifier.BIN_COUNT,  1.0)));
    public static final Refinery REFINERY_1_D = new Refinery("REFINERY_1_D", HorizonsBlueprintName.REFINERY, ModuleSize.SIZE_1, ModuleClass.D, false, 18000, "Int_Refinery_Size1_Class2", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  24.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.18), Map.entry(HorizonsModifier.BOOT_TIME,  10.0), Map.entry(HorizonsModifier.BIN_COUNT,  1.0)));
    public static final Refinery REFINERY_1_C = new Refinery("REFINERY_1_C", HorizonsBlueprintName.REFINERY, ModuleSize.SIZE_1, ModuleClass.C, false, 54000, "Int_Refinery_Size1_Class3", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  40.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.23), Map.entry(HorizonsModifier.BOOT_TIME,  10.0), Map.entry(HorizonsModifier.BIN_COUNT,  2.0)));
    public static final Refinery REFINERY_1_B = new Refinery("REFINERY_1_B", HorizonsBlueprintName.REFINERY, ModuleSize.SIZE_1, ModuleClass.B, false, 162000, "Int_Refinery_Size1_Class4", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  56.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.28), Map.entry(HorizonsModifier.BOOT_TIME,  10.0), Map.entry(HorizonsModifier.BIN_COUNT,  3.0)));
    public static final Refinery REFINERY_1_A = new Refinery("REFINERY_1_A", HorizonsBlueprintName.REFINERY, ModuleSize.SIZE_1, ModuleClass.A, false, 486000, "Int_Refinery_Size1_Class5", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  48.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.32), Map.entry(HorizonsModifier.BOOT_TIME,  10.0), Map.entry(HorizonsModifier.BIN_COUNT,  4.0)));
    public static final Refinery REFINERY_2_E = new Refinery("REFINERY_2_E", HorizonsBlueprintName.REFINERY, ModuleSize.SIZE_2, ModuleClass.E, false, 12600, "Int_Refinery_Size2_Class1", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  41.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.17), Map.entry(HorizonsModifier.BOOT_TIME,  10.0), Map.entry(HorizonsModifier.BIN_COUNT,  2.0)));
    public static final Refinery REFINERY_2_D = new Refinery("REFINERY_2_D", HorizonsBlueprintName.REFINERY, ModuleSize.SIZE_2, ModuleClass.D, false, 37800, "Int_Refinery_Size2_Class2", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  31.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.22), Map.entry(HorizonsModifier.BOOT_TIME,  10.0), Map.entry(HorizonsModifier.BIN_COUNT,  3.0)));
    public static final Refinery REFINERY_2_C = new Refinery("REFINERY_2_C", HorizonsBlueprintName.REFINERY, ModuleSize.SIZE_2, ModuleClass.C, false, 113400, "Int_Refinery_Size2_Class3", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  51.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.28), Map.entry(HorizonsModifier.BOOT_TIME,  10.0), Map.entry(HorizonsModifier.BIN_COUNT,  4.0)));
    public static final Refinery REFINERY_2_B = new Refinery("REFINERY_2_B", HorizonsBlueprintName.REFINERY, ModuleSize.SIZE_2, ModuleClass.B, false, 340200, "Int_Refinery_Size2_Class4", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  71.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.34), Map.entry(HorizonsModifier.BOOT_TIME,  10.0), Map.entry(HorizonsModifier.BIN_COUNT,  5.0)));
    public static final Refinery REFINERY_2_A = new Refinery("REFINERY_2_A", HorizonsBlueprintName.REFINERY, ModuleSize.SIZE_2, ModuleClass.A, false, 1020600, "Int_Refinery_Size2_Class5", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  61.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.39), Map.entry(HorizonsModifier.BOOT_TIME,  10.0), Map.entry(HorizonsModifier.BIN_COUNT,  6.0)));
    public static final Refinery REFINERY_3_E = new Refinery("REFINERY_3_E", HorizonsBlueprintName.REFINERY, ModuleSize.SIZE_3, ModuleClass.E, false, 26460, "Int_Refinery_Size3_Class1", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  51.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.2), Map.entry(HorizonsModifier.BOOT_TIME,  10.0), Map.entry(HorizonsModifier.BIN_COUNT,  3.0)));
    public static final Refinery REFINERY_3_D = new Refinery("REFINERY_3_D", HorizonsBlueprintName.REFINERY, ModuleSize.SIZE_3, ModuleClass.D, false, 79380, "Int_Refinery_Size3_Class2", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  38.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.27), Map.entry(HorizonsModifier.BOOT_TIME,  10.0), Map.entry(HorizonsModifier.BIN_COUNT,  4.0)));
    public static final Refinery REFINERY_3_C = new Refinery("REFINERY_3_C", HorizonsBlueprintName.REFINERY, ModuleSize.SIZE_3, ModuleClass.C, false, 238140, "Int_Refinery_Size3_Class3", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  64.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.34), Map.entry(HorizonsModifier.BOOT_TIME,  10.0), Map.entry(HorizonsModifier.BIN_COUNT,  6.0)));
    public static final Refinery REFINERY_3_B = new Refinery("REFINERY_3_B", HorizonsBlueprintName.REFINERY, ModuleSize.SIZE_3, ModuleClass.B, false, 714420, "Int_Refinery_Size3_Class4", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  90.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.41), Map.entry(HorizonsModifier.BOOT_TIME,  10.0), Map.entry(HorizonsModifier.BIN_COUNT,  7.0)));
    public static final Refinery REFINERY_3_A = new Refinery("REFINERY_3_A", HorizonsBlueprintName.REFINERY, ModuleSize.SIZE_3, ModuleClass.A, false, 2143260, "Int_Refinery_Size3_Class5", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  77.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.48), Map.entry(HorizonsModifier.BOOT_TIME,  10.0), Map.entry(HorizonsModifier.BIN_COUNT,  8.0)));
    public static final Refinery REFINERY_4_E = new Refinery("REFINERY_4_E", HorizonsBlueprintName.REFINERY, ModuleSize.SIZE_4, ModuleClass.E, false, 55570, "Int_Refinery_Size4_Class1", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  64.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.25), Map.entry(HorizonsModifier.BOOT_TIME,  10.0), Map.entry(HorizonsModifier.BIN_COUNT,  4.0)));
    public static final Refinery REFINERY_4_D = new Refinery("REFINERY_4_D", HorizonsBlueprintName.REFINERY, ModuleSize.SIZE_4, ModuleClass.D, false, 166700, "Int_Refinery_Size4_Class2", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  48.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.33), Map.entry(HorizonsModifier.BOOT_TIME,  10.0), Map.entry(HorizonsModifier.BIN_COUNT,  5.0)));
    public static final Refinery REFINERY_4_C = new Refinery("REFINERY_4_C", HorizonsBlueprintName.REFINERY, ModuleSize.SIZE_4, ModuleClass.C, false, 500090, "Int_Refinery_Size4_Class3", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  80.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.41), Map.entry(HorizonsModifier.BOOT_TIME,  10.0), Map.entry(HorizonsModifier.BIN_COUNT,  7.0)));
    public static final Refinery REFINERY_4_B = new Refinery("REFINERY_4_B", HorizonsBlueprintName.REFINERY, ModuleSize.SIZE_4, ModuleClass.B, false, 1500280, "Int_Refinery_Size4_Class4", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  112.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.49), Map.entry(HorizonsModifier.BOOT_TIME,  10.0), Map.entry(HorizonsModifier.BIN_COUNT,  9.0)));
    public static final Refinery REFINERY_4_A = new Refinery("REFINERY_4_A", HorizonsBlueprintName.REFINERY, ModuleSize.SIZE_4, ModuleClass.A, false, 4500850, "Int_Refinery_Size4_Class5", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  96.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.57), Map.entry(HorizonsModifier.BOOT_TIME,  10.0), Map.entry(HorizonsModifier.BIN_COUNT,  10.0)));

    public static final List<Refinery> REFINERIES = List.of(
            REFINERY_1_E,
            REFINERY_1_D,
            REFINERY_1_C,
            REFINERY_1_B,
            REFINERY_1_A,
            REFINERY_2_E,
            REFINERY_2_D,
            REFINERY_2_C,
            REFINERY_2_B,
            REFINERY_2_A,
            REFINERY_3_E,
            REFINERY_3_D,
            REFINERY_3_C,
            REFINERY_3_B,
            REFINERY_3_A,
            REFINERY_4_E,
            REFINERY_4_D,
            REFINERY_4_C,
            REFINERY_4_B,
            REFINERY_4_A
    );
    public Refinery(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, basePrice, internalName, attributes);
    }

    public Refinery(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, basePrice, internalName, attributes);
    }

    public Refinery(Refinery refinery) {
        super(refinery);
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedBlueprints() {
        return RefineryBlueprints.BLUEPRINTS.keySet().stream().toList();
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedExperimentalEffects() {
        return Collections.emptyList();
    }

    @Override
    public Refinery Clone() {
        return new Refinery(this);
    }
}
