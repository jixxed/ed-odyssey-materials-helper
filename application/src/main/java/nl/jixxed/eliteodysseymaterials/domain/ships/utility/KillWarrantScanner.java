package nl.jixxed.eliteodysseymaterials.domain.ships.utility;

import nl.jixxed.eliteodysseymaterials.constants.horizons.utilitymounts.KillWarrantScannerBlueprints;
import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class KillWarrantScanner extends UtilityModule {
    public static final KillWarrantScanner KILL_WARRANT_SCANNER_0_E = new KillWarrantScanner("KILL_WARRANT_SCANNER_0_E", HorizonsBlueprintName.KILL_WARRANT_SCANNER, ModuleSize.SIZE_0, ModuleClass.E, false, Mounting.NA, 13540, "Hpt_CrimeScanner_Size0_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  32.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.2), Map.entry(HorizonsModifier.BOOT_TIME,  2.0), Map.entry(HorizonsModifier.SCANNER_RANGE,  2000.0), Map.entry(HorizonsModifier.MAX_ANGLE,  15.0), Map.entry(HorizonsModifier.SCAN_TIME,  10.0)));
    public static final KillWarrantScanner KILL_WARRANT_SCANNER_0_D = new KillWarrantScanner("KILL_WARRANT_SCANNER_0_D", HorizonsBlueprintName.KILL_WARRANT_SCANNER, ModuleSize.SIZE_0, ModuleClass.D, false, Mounting.NA, 40630, "Hpt_CrimeScanner_Size0_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  24.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.4), Map.entry(HorizonsModifier.BOOT_TIME,  2.0), Map.entry(HorizonsModifier.SCANNER_RANGE,  2500.0), Map.entry(HorizonsModifier.MAX_ANGLE,  15.0), Map.entry(HorizonsModifier.SCAN_TIME,  10.0)));
    public static final KillWarrantScanner KILL_WARRANT_SCANNER_0_C = new KillWarrantScanner("KILL_WARRANT_SCANNER_0_C", HorizonsBlueprintName.KILL_WARRANT_SCANNER, ModuleSize.SIZE_0, ModuleClass.C, false, Mounting.NA, 121900, "Hpt_CrimeScanner_Size0_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  40.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.8), Map.entry(HorizonsModifier.BOOT_TIME,  2.0), Map.entry(HorizonsModifier.SCANNER_RANGE,  3000.0), Map.entry(HorizonsModifier.MAX_ANGLE,  15.0), Map.entry(HorizonsModifier.SCAN_TIME,  10.0)));
    public static final KillWarrantScanner KILL_WARRANT_SCANNER_0_B = new KillWarrantScanner("KILL_WARRANT_SCANNER_0_B", HorizonsBlueprintName.KILL_WARRANT_SCANNER, ModuleSize.SIZE_0, ModuleClass.B, false, Mounting.NA, 365700, "Hpt_CrimeScanner_Size0_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  56.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.6), Map.entry(HorizonsModifier.BOOT_TIME,  2.0), Map.entry(HorizonsModifier.SCANNER_RANGE,  3500.0), Map.entry(HorizonsModifier.MAX_ANGLE,  15.0), Map.entry(HorizonsModifier.SCAN_TIME,  10.0)));
    public static final KillWarrantScanner KILL_WARRANT_SCANNER_0_A = new KillWarrantScanner("KILL_WARRANT_SCANNER_0_A", HorizonsBlueprintName.KILL_WARRANT_SCANNER, ModuleSize.SIZE_0, ModuleClass.A, false, Mounting.NA, 1097100, "Hpt_CrimeScanner_Size0_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  48.0), Map.entry(HorizonsModifier.POWER_DRAW,  3.2), Map.entry(HorizonsModifier.BOOT_TIME,  2.0), Map.entry(HorizonsModifier.SCANNER_RANGE,  4000.0), Map.entry(HorizonsModifier.MAX_ANGLE,  15.0), Map.entry(HorizonsModifier.SCAN_TIME,  10.0)));

    public static final KillWarrantScanner KILL_WARRANT_SCANNER_0_A_PRE = new KillWarrantScanner("KILL_WARRANT_SCANNER_0_A_PRE", HorizonsBlueprintName.KILL_WARRANT_SCANNER_PRE, ModuleSize.SIZE_0, ModuleClass.A, false, Mounting.NA, 0, "Hpt_CrimeScanner_Size0_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  48.0), Map.entry(HorizonsModifier.POWER_DRAW,  3.2), Map.entry(HorizonsModifier.BOOT_TIME,  2.0), Map.entry(HorizonsModifier.SCANNER_RANGE,  4000.0), Map.entry(HorizonsModifier.MAX_ANGLE,  15.0), Map.entry(HorizonsModifier.SCAN_TIME,  10.0)));

    static {
        KILL_WARRANT_SCANNER_0_A_PRE.getModifications().add(
                new Modification(HorizonsBlueprintType.FAST_SCANNER_LONG_RANGE_SCANNER, 1.0, HorizonsBlueprintGrade.GRADE_5)
        );
    }
    public static final List<KillWarrantScanner> KILL_WARRANT_SCANNERS = List.of(
            KILL_WARRANT_SCANNER_0_E,
            KILL_WARRANT_SCANNER_0_D,
            KILL_WARRANT_SCANNER_0_C,
            KILL_WARRANT_SCANNER_0_B,
            KILL_WARRANT_SCANNER_0_A,
            KILL_WARRANT_SCANNER_0_A_PRE
    );
    public KillWarrantScanner(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public KillWarrantScanner(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public KillWarrantScanner(KillWarrantScanner killWarrantScanner) {
        super(killWarrantScanner);
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedBlueprints() {
        if(isPreEngineered()){
            return Collections.emptyList();
        }
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
    @Override
    public boolean isPreEngineered() {
        return KILL_WARRANT_SCANNER_0_A_PRE.equals(this);
    }

    @Override
    public String getClarifier() {
        return isPreEngineered() ? " " + LocaleService.getLocalizedStringForCurrentLocale(getName().getLocalizationKey()) : "";
    }
    @Override
    public boolean isCGExclusive() {
        return isPreEngineered();
    }
    @Override
    public int getGrouping() {
        return isPreEngineered() ? 2 : 1;
    }
}
