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

//		 2150 : { mtype:'ir', cost:    6000, name:'Refinery', class:1, rating:'E', integ: 32, pwrdraw:0.14, boottime:10, bins: 1, limit:'ir', fdid:128666684, fdname:'Int_Refinery_Size1_Class1', eddbid:1286 },
//            2140 : { mtype:'ir', cost:   18000, name:'Refinery', class:1, rating:'D', integ: 24, pwrdraw:0.18, boottime:10, bins: 1, limit:'ir', fdid:128666688, fdname:'Int_Refinery_Size1_Class2', eddbid:1290 },
//            2130 : { mtype:'ir', cost:   54000, name:'Refinery', class:1, rating:'C', integ: 40, pwrdraw:0.23, boottime:10, bins: 2, limit:'ir', fdid:128666692, fdname:'Int_Refinery_Size1_Class3', eddbid:1294 },
//            2120 : { mtype:'ir', cost:  162000, name:'Refinery', class:1, rating:'B', integ: 56, pwrdraw:0.28, boottime:10, bins: 3, limit:'ir', fdid:128666696, fdname:'Int_Refinery_Size1_Class4', eddbid:1298 },
//            2110 : { mtype:'ir', cost:  486000, name:'Refinery', class:1, rating:'A', integ: 48, pwrdraw:0.32, boottime:10, bins: 4, limit:'ir', fdid:128666700, fdname:'Int_Refinery_Size1_Class5', eddbid:1302 },
//
//            2250 : { mtype:'ir', cost:   12600, name:'Refinery', class:2, rating:'E', integ: 41, pwrdraw:0.17, boottime:10, bins: 2, limit:'ir', fdid:128666685, fdname:'Int_Refinery_Size2_Class1', eddbid:1287 },
//            2240 : { mtype:'ir', cost:   37800, name:'Refinery', class:2, rating:'D', integ: 31, pwrdraw:0.22, boottime:10, bins: 3, limit:'ir', fdid:128666689, fdname:'Int_Refinery_Size2_Class2', eddbid:1291 },
//            2230 : { mtype:'ir', cost:  113400, name:'Refinery', class:2, rating:'C', integ: 51, pwrdraw:0.28, boottime:10, bins: 4, limit:'ir', fdid:128666693, fdname:'Int_Refinery_Size2_Class3', eddbid:1295 },
//            2220 : { mtype:'ir', cost:  340200, name:'Refinery', class:2, rating:'B', integ: 71, pwrdraw:0.34, boottime:10, bins: 5, limit:'ir', fdid:128666697, fdname:'Int_Refinery_Size2_Class4', eddbid:1299 },
//            2210 : { mtype:'ir', cost: 1020600, name:'Refinery', class:2, rating:'A', integ: 61, pwrdraw:0.39, boottime:10, bins: 6, limit:'ir', fdid:128666701, fdname:'Int_Refinery_Size2_Class5', eddbid:1303 },
//
//            2350 : { mtype:'ir', cost:   26460, name:'Refinery', class:3, rating:'E', integ: 51, pwrdraw:0.20, boottime:10, bins: 3, limit:'ir', fdid:128666686, fdname:'Int_Refinery_Size3_Class1', eddbid:1288 },
//            2340 : { mtype:'ir', cost:   79380, name:'Refinery', class:3, rating:'D', integ: 38, pwrdraw:0.27, boottime:10, bins: 4, limit:'ir', fdid:128666690, fdname:'Int_Refinery_Size3_Class2', eddbid:1292 },
//            2330 : { mtype:'ir', cost:  238140, name:'Refinery', class:3, rating:'C', integ: 64, pwrdraw:0.34, boottime:10, bins: 6, limit:'ir', fdid:128666694, fdname:'Int_Refinery_Size3_Class3', eddbid:1296 },
//            2320 : { mtype:'ir', cost:  714420, name:'Refinery', class:3, rating:'B', integ: 90, pwrdraw:0.41, boottime:10, bins: 7, limit:'ir', fdid:128666698, fdname:'Int_Refinery_Size3_Class4', eddbid:1300 },
//            2310 : { mtype:'ir', cost: 2143260, name:'Refinery', class:3, rating:'A', integ: 77, pwrdraw:0.48, boottime:10, bins: 8, limit:'ir', fdid:128666702, fdname:'Int_Refinery_Size3_Class5', eddbid:1304 },
//
//            2450 : { mtype:'ir', cost:   55570, name:'Refinery', class:4, rating:'E', integ: 64, pwrdraw:0.25, boottime:10, bins: 4, limit:'ir', fdid:128666687, fdname:'Int_Refinery_Size4_Class1', eddbid:1289 },
//            2440 : { mtype:'ir', cost:  166700, name:'Refinery', class:4, rating:'D', integ: 48, pwrdraw:0.33, boottime:10, bins: 5, limit:'ir', fdid:128666691, fdname:'Int_Refinery_Size4_Class2', eddbid:1293 },
//            2430 : { mtype:'ir', cost:  500090, name:'Refinery', class:4, rating:'C', integ: 80, pwrdraw:0.41, boottime:10, bins: 7, limit:'ir', fdid:128666695, fdname:'Int_Refinery_Size4_Class3', eddbid:1297 },
//            2420 : { mtype:'ir', cost: 1500280, name:'Refinery', class:4, rating:'B', integ:112, pwrdraw:0.49, boottime:10, bins: 9, limit:'ir', fdid:128666699, fdname:'Int_Refinery_Size4_Class4', eddbid:1301 },
//            2410 : { mtype:'ir', cost: 4500850, name:'Refinery', class:4, rating:'A', integ: 96, pwrdraw:0.57, boottime:10, bins:10, limit:'ir', fdid:128666703, fdname:'Int_Refinery_Size4_Class5', eddbid:1305 },
//
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
    public Refinery(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, basePrice, internalName, attributes);
    }

    public Refinery(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
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
