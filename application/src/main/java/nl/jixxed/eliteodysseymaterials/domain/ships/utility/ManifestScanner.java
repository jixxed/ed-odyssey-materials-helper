package nl.jixxed.eliteodysseymaterials.domain.ships.utility;

import nl.jixxed.eliteodysseymaterials.constants.horizons.utilitymounts.ManifestScannerBlueprints;
import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ManifestScanner extends UtilityModule {
    public static final ManifestScanner MANIFEST_SCANNER_0_E = new ManifestScanner("MANIFEST_SCANNER_0_E", HorizonsBlueprintName.MANIFEST_SCANNER, ModuleSize.SIZE_0, ModuleClass.E, false, Mounting.NA, 13540, "Hpt_CargoScanner_Size0_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  32.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.2), Map.entry(HorizonsModifier.BOOT_TIME,  3.0), Map.entry(HorizonsModifier.SCANNER_RANGE,  2000.0), Map.entry(HorizonsModifier.MAX_ANGLE,  15.0), Map.entry(HorizonsModifier.SCAN_TIME,  10.0)));
    public static final ManifestScanner MANIFEST_SCANNER_0_D = new ManifestScanner("MANIFEST_SCANNER_0_D", HorizonsBlueprintName.MANIFEST_SCANNER, ModuleSize.SIZE_0, ModuleClass.D, false, Mounting.NA, 40630, "Hpt_CargoScanner_Size0_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  24.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.4), Map.entry(HorizonsModifier.BOOT_TIME,  3.0), Map.entry(HorizonsModifier.SCANNER_RANGE,  2500.0), Map.entry(HorizonsModifier.MAX_ANGLE,  15.0), Map.entry(HorizonsModifier.SCAN_TIME,  10.0)));
    public static final ManifestScanner MANIFEST_SCANNER_0_C = new ManifestScanner("MANIFEST_SCANNER_0_C", HorizonsBlueprintName.MANIFEST_SCANNER, ModuleSize.SIZE_0, ModuleClass.C, false, Mounting.NA, 121900, "Hpt_CargoScanner_Size0_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  40.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.8), Map.entry(HorizonsModifier.BOOT_TIME,  3.0), Map.entry(HorizonsModifier.SCANNER_RANGE,  3000.0), Map.entry(HorizonsModifier.MAX_ANGLE,  15.0), Map.entry(HorizonsModifier.SCAN_TIME,  10.0)));
    public static final ManifestScanner MANIFEST_SCANNER_0_B = new ManifestScanner("MANIFEST_SCANNER_0_B", HorizonsBlueprintName.MANIFEST_SCANNER, ModuleSize.SIZE_0, ModuleClass.B, false, Mounting.NA, 365700, "Hpt_CargoScanner_Size0_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  56.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.6), Map.entry(HorizonsModifier.BOOT_TIME,  3.0), Map.entry(HorizonsModifier.SCANNER_RANGE,  3500.0), Map.entry(HorizonsModifier.MAX_ANGLE,  15.0), Map.entry(HorizonsModifier.SCAN_TIME,  10.0)));
    public static final ManifestScanner MANIFEST_SCANNER_0_A = new ManifestScanner("MANIFEST_SCANNER_0_A", HorizonsBlueprintName.MANIFEST_SCANNER, ModuleSize.SIZE_0, ModuleClass.A, false, Mounting.NA, 1097100, "Hpt_CargoScanner_Size0_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  48.0), Map.entry(HorizonsModifier.POWER_DRAW,  3.2), Map.entry(HorizonsModifier.BOOT_TIME,  3.0), Map.entry(HorizonsModifier.SCANNER_RANGE,  4000.0), Map.entry(HorizonsModifier.MAX_ANGLE,  15.0), Map.entry(HorizonsModifier.SCAN_TIME,  10.0)));

    public static final List<ManifestScanner> MANIFEST_SCANNERS = List.of(
            MANIFEST_SCANNER_0_E,
            MANIFEST_SCANNER_0_D,
            MANIFEST_SCANNER_0_C,
            MANIFEST_SCANNER_0_B,
            MANIFEST_SCANNER_0_A
    );

    public ManifestScanner(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public ManifestScanner(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public ManifestScanner(ManifestScanner manifestScanner) {
        super(manifestScanner);
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedBlueprints() {
        return ManifestScannerBlueprints.BLUEPRINTS.keySet().stream().toList();
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedExperimentalEffects() {
        return Collections.emptyList();
    }

    @Override
    public ManifestScanner Clone() {
        return new ManifestScanner(this);
    }
}
