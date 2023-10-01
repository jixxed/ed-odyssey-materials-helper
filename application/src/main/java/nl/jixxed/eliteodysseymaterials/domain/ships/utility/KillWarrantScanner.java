package nl.jixxed.eliteodysseymaterials.domain.ships.utility;

import nl.jixxed.eliteodysseymaterials.constants.horizons.utilitymounts.KillWarrantScannerBlueprints;
import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class KillWarrantScanner extends UtilityModule {

//		57050 : { mtype:'ukws', cost:  13540, name:'Kill Warrant Scanner',      class:0, rating:'E', mass:1.30, integ:32, pwrdraw:0.20,            boottime:2, scanrng:2000, maxangle:15.00, scantime:10, limit:'ukws', fdid:128662530, fdname:'Hpt_CrimeScanner_Size0_Class1', eddbid:1237 },
//            57040 : { mtype:'ukws', cost:  40630, name:'Kill Warrant Scanner',      class:0, rating:'D', mass:1.30, integ:24, pwrdraw:0.40,            boottime:2, scanrng:2500, maxangle:15.00, scantime:10, limit:'ukws', fdid:128662531, fdname:'Hpt_CrimeScanner_Size0_Class2', eddbid:1238 },
//            57030 : { mtype:'ukws', cost: 121900, name:'Kill Warrant Scanner',      class:0, rating:'C', mass:1.30, integ:40, pwrdraw:0.80,            boottime:2, scanrng:3000, maxangle:15.00, scantime:10, limit:'ukws', fdid:128662532, fdname:'Hpt_CrimeScanner_Size0_Class3', eddbid:1239 },
//            57020 : { mtype:'ukws', cost: 365700, name:'Kill Warrant Scanner',      class:0, rating:'B', mass:1.30, integ:56, pwrdraw:1.60,            boottime:2, scanrng:3500, maxangle:15.00, scantime:10, limit:'ukws', fdid:128662533, fdname:'Hpt_CrimeScanner_Size0_Class4', eddbid:1240 },
//            57010 : { mtype:'ukws', cost:1097100, name:'Kill Warrant Scanner',      class:0, rating:'A', mass:1.30, integ:48, pwrdraw:3.20,            boottime:2, scanrng:4000, maxangle:15.00, scantime:10, limit:'ukws', fdid:128662534, fdname:'Hpt_CrimeScanner_Size0_Class5', eddbid:1241 },
    public static final KillWarrantScanner KILL_WARRANT_SCANNER_0_E = new KillWarrantScanner("KILL_WARRANT_SCANNER_0_E", HorizonsBlueprintName.KILL_WARRANT_SCANNER, ModuleSize.SIZE_0, ModuleClass.E, false, Mounting.NA, 13540, "Hpt_CrimeScanner_Size0_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  32.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.2), Map.entry(HorizonsModifier.BOOT_TIME,  2.0), Map.entry(HorizonsModifier.SCANNER_RANGE,  2000.0), Map.entry(HorizonsModifier.MAX_ANGLE,  15.0), Map.entry(HorizonsModifier.SCAN_TIME,  10.0)));
    public static final KillWarrantScanner KILL_WARRANT_SCANNER_0_D = new KillWarrantScanner("KILL_WARRANT_SCANNER_0_D", HorizonsBlueprintName.KILL_WARRANT_SCANNER, ModuleSize.SIZE_0, ModuleClass.D, false, Mounting.NA, 40630, "Hpt_CrimeScanner_Size0_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  24.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.4), Map.entry(HorizonsModifier.BOOT_TIME,  2.0), Map.entry(HorizonsModifier.SCANNER_RANGE,  2500.0), Map.entry(HorizonsModifier.MAX_ANGLE,  15.0), Map.entry(HorizonsModifier.SCAN_TIME,  10.0)));
    public static final KillWarrantScanner KILL_WARRANT_SCANNER_0_C = new KillWarrantScanner("KILL_WARRANT_SCANNER_0_C", HorizonsBlueprintName.KILL_WARRANT_SCANNER, ModuleSize.SIZE_0, ModuleClass.C, false, Mounting.NA, 121900, "Hpt_CrimeScanner_Size0_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  40.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.8), Map.entry(HorizonsModifier.BOOT_TIME,  2.0), Map.entry(HorizonsModifier.SCANNER_RANGE,  3000.0), Map.entry(HorizonsModifier.MAX_ANGLE,  15.0), Map.entry(HorizonsModifier.SCAN_TIME,  10.0)));
    public static final KillWarrantScanner KILL_WARRANT_SCANNER_0_B = new KillWarrantScanner("KILL_WARRANT_SCANNER_0_B", HorizonsBlueprintName.KILL_WARRANT_SCANNER, ModuleSize.SIZE_0, ModuleClass.B, false, Mounting.NA, 365700, "Hpt_CrimeScanner_Size0_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  56.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.6), Map.entry(HorizonsModifier.BOOT_TIME,  2.0), Map.entry(HorizonsModifier.SCANNER_RANGE,  3500.0), Map.entry(HorizonsModifier.MAX_ANGLE,  15.0), Map.entry(HorizonsModifier.SCAN_TIME,  10.0)));
    public static final KillWarrantScanner KILL_WARRANT_SCANNER_0_A = new KillWarrantScanner("KILL_WARRANT_SCANNER_0_A", HorizonsBlueprintName.KILL_WARRANT_SCANNER, ModuleSize.SIZE_0, ModuleClass.A, false, Mounting.NA, 1097100, "Hpt_CrimeScanner_Size0_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  48.0), Map.entry(HorizonsModifier.POWER_DRAW,  3.2), Map.entry(HorizonsModifier.BOOT_TIME,  2.0), Map.entry(HorizonsModifier.SCANNER_RANGE,  4000.0), Map.entry(HorizonsModifier.MAX_ANGLE,  15.0), Map.entry(HorizonsModifier.SCAN_TIME,  10.0)));

    public static final List<KillWarrantScanner> KILL_WARRANT_SCANNERS = List.of(
            KILL_WARRANT_SCANNER_0_E,
            KILL_WARRANT_SCANNER_0_D,
            KILL_WARRANT_SCANNER_0_C,
            KILL_WARRANT_SCANNER_0_B,
            KILL_WARRANT_SCANNER_0_A
    );
    public KillWarrantScanner(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, Mounting mounting, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public KillWarrantScanner(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, Mounting mounting, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public KillWarrantScanner(KillWarrantScanner killWarrantScanner) {
        super(killWarrantScanner);
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedBlueprints() {
        return KillWarrantScannerBlueprints.BLUEPRINTS.keySet().stream().toList();
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedExperimentalEffects() {
        return Collections.emptyList();
    }

    @Override
    public KillWarrantScanner Clone() {
        return new KillWarrantScanner(this);
    }
}
