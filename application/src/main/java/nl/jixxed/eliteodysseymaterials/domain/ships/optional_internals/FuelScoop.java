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

//		20150 : { mtype:'ifs', cost:      310, name:'Fuel Scoop', class:1, rating:'E', integ: 32, pwrdraw:0.14, boottime:4, scooprate:0.018, limit:'ifs', fdid:128666644, fdname:'Int_FuelScoop_Size1_Class1', eddbid:1246 },
//            20140 : { mtype:'ifs', cost:     1290, name:'Fuel Scoop', class:1, rating:'D', integ: 24, pwrdraw:0.18, boottime:4, scooprate:0.024, limit:'ifs', fdid:128666652, fdname:'Int_FuelScoop_Size1_Class2', eddbid:1254 },
//            20130 : { mtype:'ifs', cost:     5140, name:'Fuel Scoop', class:1, rating:'C', integ: 40, pwrdraw:0.23, boottime:4, scooprate:0.030, limit:'ifs', fdid:128666660, fdname:'Int_FuelScoop_Size1_Class3', eddbid:1262 },
//            20120 : { mtype:'ifs', cost:    20570, name:'Fuel Scoop', class:1, rating:'B', integ: 56, pwrdraw:0.28, boottime:4, scooprate:0.036, limit:'ifs', fdid:128666668, fdname:'Int_FuelScoop_Size1_Class4', eddbid:1270 },
//            20110 : { mtype:'ifs', cost:    82270, name:'Fuel Scoop', class:1, rating:'A', integ: 48, pwrdraw:0.32, boottime:4, scooprate:0.042, limit:'ifs', fdid:128666676, fdname:'Int_FuelScoop_Size1_Class5', eddbid:1278 },
//
//            20250 : { mtype:'ifs', cost:     1070, name:'Fuel Scoop', class:2, rating:'E', integ: 41, pwrdraw:0.17, boottime:4, scooprate:0.032, limit:'ifs', fdid:128666645, fdname:'Int_FuelScoop_Size2_Class1', eddbid:1247 },
//            20240 : { mtype:'ifs', cost:     4450, name:'Fuel Scoop', class:2, rating:'D', integ: 31, pwrdraw:0.22, boottime:4, scooprate:0.043, limit:'ifs', fdid:128666653, fdname:'Int_FuelScoop_Size2_Class2', eddbid:1255 },
//            20230 : { mtype:'ifs', cost:    17800, name:'Fuel Scoop', class:2, rating:'C', integ: 51, pwrdraw:0.28, boottime:4, scooprate:0.054, limit:'ifs', fdid:128666661, fdname:'Int_FuelScoop_Size2_Class3', eddbid:1263 },
//            20220 : { mtype:'ifs', cost:    71210, name:'Fuel Scoop', class:2, rating:'B', integ: 70, pwrdraw:0.34, boottime:4, scooprate:0.065, limit:'ifs', fdid:128666669, fdname:'Int_FuelScoop_Size2_Class4', eddbid:1271 },
//            20210 : { mtype:'ifs', cost:   284840, name:'Fuel Scoop', class:2, rating:'A', integ: 61, pwrdraw:0.39, boottime:4, scooprate:0.075, limit:'ifs', fdid:128666677, fdname:'Int_FuelScoop_Size2_Class5', eddbid:1279 },
//
//            20350 : { mtype:'ifs', cost:     3390, name:'Fuel Scoop', class:3, rating:'E', integ: 51, pwrdraw:0.20, boottime:4, scooprate:0.075, limit:'ifs', fdid:128666646, fdname:'Int_FuelScoop_Size3_Class1', eddbid:1248 },
//            20340 : { mtype:'ifs', cost:    14110, name:'Fuel Scoop', class:3, rating:'D', integ: 38, pwrdraw:0.27, boottime:4, scooprate:0.100, limit:'ifs', fdid:128666654, fdname:'Int_FuelScoop_Size3_Class2', eddbid:1256 },
//            20330 : { mtype:'ifs', cost:    56440, name:'Fuel Scoop', class:3, rating:'C', integ: 64, pwrdraw:0.34, boottime:4, scooprate:0.126, limit:'ifs', fdid:128666662, fdname:'Int_FuelScoop_Size3_Class3', eddbid:1264 },
//            20320 : { mtype:'ifs', cost:   225740, name:'Fuel Scoop', class:3, rating:'B', integ: 90, pwrdraw:0.41, boottime:4, scooprate:0.151, limit:'ifs', fdid:128666670, fdname:'Int_FuelScoop_Size3_Class4', eddbid:1272 },
//            20310 : { mtype:'ifs', cost:   902950, name:'Fuel Scoop', class:3, rating:'A', integ: 77, pwrdraw:0.48, boottime:4, scooprate:0.176, limit:'ifs', fdid:128666678, fdname:'Int_FuelScoop_Size3_Class5', eddbid:1280 },
//
//            20450 : { mtype:'ifs', cost:    10730, name:'Fuel Scoop', class:4, rating:'E', integ: 64, pwrdraw:0.25, boottime:4, scooprate:0.147, limit:'ifs', fdid:128666647, fdname:'Int_FuelScoop_Size4_Class1', eddbid:1249 },
//            20440 : { mtype:'ifs', cost:    44720, name:'Fuel Scoop', class:4, rating:'D', integ: 48, pwrdraw:0.33, boottime:4, scooprate:0.196, limit:'ifs', fdid:128666655, fdname:'Int_FuelScoop_Size4_Class2', eddbid:1257 },
//            20430 : { mtype:'ifs', cost:   178900, name:'Fuel Scoop', class:4, rating:'C', integ: 80, pwrdraw:0.41, boottime:4, scooprate:0.245, limit:'ifs', fdid:128666663, fdname:'Int_FuelScoop_Size4_Class3', eddbid:1265 },
//            20420 : { mtype:'ifs', cost:   715590, name:'Fuel Scoop', class:4, rating:'B', integ:112, pwrdraw:0.49, boottime:4, scooprate:0.294, limit:'ifs', fdid:128666671, fdname:'Int_FuelScoop_Size4_Class4', eddbid:1273 },
//            20410 : { mtype:'ifs', cost:  2862360, name:'Fuel Scoop', class:4, rating:'A', integ: 96, pwrdraw:0.57, boottime:4, scooprate:0.342, limit:'ifs', fdid:128666679, fdname:'Int_FuelScoop_Size4_Class5', eddbid:1281 },
//
//            20550 : { mtype:'ifs', cost:    34030, name:'Fuel Scoop', class:5, rating:'E', integ: 77, pwrdraw:0.30, boottime:4, scooprate:0.247, limit:'ifs', fdid:128666648, fdname:'Int_FuelScoop_Size5_Class1', eddbid:1250 },
//            20540 : { mtype:'ifs', cost:   141780, name:'Fuel Scoop', class:5, rating:'D', integ: 58, pwrdraw:0.40, boottime:4, scooprate:0.330, limit:'ifs', fdid:128666656, fdname:'Int_FuelScoop_Size5_Class2', eddbid:1258 },
//            20530 : { mtype:'ifs', cost:   567110, name:'Fuel Scoop', class:5, rating:'C', integ: 96, pwrdraw:0.50, boottime:4, scooprate:0.412, limit:'ifs', fdid:128666664, fdname:'Int_FuelScoop_Size5_Class3', eddbid:1266 },
//            20520 : { mtype:'ifs', cost:  2268420, name:'Fuel Scoop', class:5, rating:'B', integ:134, pwrdraw:0.60, boottime:4, scooprate:0.494, limit:'ifs', fdid:128666672, fdname:'Int_FuelScoop_Size5_Class4', eddbid:1274 },
//            20510 : { mtype:'ifs', cost:  9073690, name:'Fuel Scoop', class:5, rating:'A', integ:115, pwrdraw:0.70, boottime:4, scooprate:0.577, limit:'ifs', fdid:128666680, fdname:'Int_FuelScoop_Size5_Class5', eddbid:1282 },
//
//            20650 : { mtype:'ifs', cost:   107860, name:'Fuel Scoop', class:6, rating:'E', integ: 90, pwrdraw:0.35, boottime:4, scooprate:0.376, limit:'ifs', fdid:128666649, fdname:'Int_FuelScoop_Size6_Class1', eddbid:1251 },
//            20640 : { mtype:'ifs', cost:   449430, name:'Fuel Scoop', class:6, rating:'D', integ: 68, pwrdraw:0.47, boottime:4, scooprate:0.502, limit:'ifs', fdid:128666657, fdname:'Int_FuelScoop_Size6_Class2', eddbid:1259 },
//            20630 : { mtype:'ifs', cost:  1797730, name:'Fuel Scoop', class:6, rating:'C', integ:113, pwrdraw:0.59, boottime:4, scooprate:0.627, limit:'ifs', fdid:128666665, fdname:'Int_FuelScoop_Size6_Class3', eddbid:1267 },
//            20620 : { mtype:'ifs', cost:  7190900, name:'Fuel Scoop', class:6, rating:'B', integ:158, pwrdraw:0.71, boottime:4, scooprate:0.752, limit:'ifs', fdid:128666673, fdname:'Int_FuelScoop_Size6_Class4', eddbid:1275 },
//            20610 : { mtype:'ifs', cost: 28763610, name:'Fuel Scoop', class:6, rating:'A', integ:136, pwrdraw:0.83, boottime:4, scooprate:0.878, limit:'ifs', fdid:128666681, fdname:'Int_FuelScoop_Size6_Class5', eddbid:1283 },
//
//            20750 : { mtype:'ifs', cost:   341930, name:'Fuel Scoop', class:7, rating:'E', integ:105, pwrdraw:0.41, boottime:4, scooprate:0.534, limit:'ifs', fdid:128666650, fdname:'Int_FuelScoop_Size7_Class1', eddbid:1252 },
//            20740 : { mtype:'ifs', cost:  1424700, name:'Fuel Scoop', class:7, rating:'D', integ: 79, pwrdraw:0.55, boottime:4, scooprate:0.712, limit:'ifs', fdid:128666658, fdname:'Int_FuelScoop_Size7_Class2', eddbid:1260 },
//            20730 : { mtype:'ifs', cost:  5698790, name:'Fuel Scoop', class:7, rating:'C', integ:131, pwrdraw:0.69, boottime:4, scooprate:0.890, limit:'ifs', fdid:128666666, fdname:'Int_FuelScoop_Size7_Class3', eddbid:1268 },
//            20720 : { mtype:'ifs', cost: 22795160, name:'Fuel Scoop', class:7, rating:'B', integ:183, pwrdraw:0.83, boottime:4, scooprate:1.068, limit:'ifs', fdid:128666674, fdname:'Int_FuelScoop_Size7_Class4', eddbid:1276 },
//            20710 : { mtype:'ifs', cost: 91180640, name:'Fuel Scoop', class:7, rating:'A', integ:157, pwrdraw:0.97, boottime:4, scooprate:1.245, limit:'ifs', fdid:128666682, fdname:'Int_FuelScoop_Size7_Class5', eddbid:1284 },
//
//            20850 : { mtype:'ifs', cost:  1083910, name:'Fuel Scoop', class:8, rating:'E', integ:120, pwrdraw:0.48, boottime:4, scooprate:0.720, limit:'ifs', fdid:128666651, fdname:'Int_FuelScoop_Size8_Class1', eddbid:1253 },
//            20840 : { mtype:'ifs', cost:  4516290, name:'Fuel Scoop', class:8, rating:'D', integ: 90, pwrdraw:0.64, boottime:4, scooprate:0.960, limit:'ifs', fdid:128666659, fdname:'Int_FuelScoop_Size8_Class2', eddbid:1261 },
//            20830 : { mtype:'ifs', cost: 18065170, name:'Fuel Scoop', class:8, rating:'C', integ:150, pwrdraw:0.80, boottime:4, scooprate:1.200, limit:'ifs', fdid:128666667, fdname:'Int_FuelScoop_Size8_Class3', eddbid:1269 },
//            20820 : { mtype:'ifs', cost: 72260660, name:'Fuel Scoop', class:8, rating:'B', integ:210, pwrdraw:0.96, boottime:4, scooprate:1.440, limit:'ifs', fdid:128666675, fdname:'Int_FuelScoop_Size8_Class4', eddbid:1277 },
//            20810 : { mtype:'ifs', cost:289042640, name:'Fuel Scoop', class:8, rating:'A', integ:180, pwrdraw:1.12, boottime:4, scooprate:1.680, limit:'ifs', fdid:128666683, fdname:'Int_FuelScoop_Size8_Class5', eddbid:1285 },
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
