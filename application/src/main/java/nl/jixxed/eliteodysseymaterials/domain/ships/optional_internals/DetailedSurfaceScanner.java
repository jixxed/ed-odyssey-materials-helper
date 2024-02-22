package nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals;

import nl.jixxed.eliteodysseymaterials.constants.horizons.optional_internals.DetailedSurfaceScannerBlueprints;
import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DetailedSurfaceScanner extends OptionalModule {
    public static final DetailedSurfaceScanner DETAILED_SURFACE_SCANNER_1_I = new DetailedSurfaceScanner("DETAILED_SURFACE_SCANNER_1_I", HorizonsBlueprintName.DETAILED_SURFACE_SCANNER, ModuleSize.SIZE_1, ModuleClass.I, false, 250000, "Int_DetailedSurfaceScanner_Tiny", Map.ofEntries( Map.entry(HorizonsModifier.INTEGRITY, 20.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 3.0), Map.entry(HorizonsModifier.DSS_PATCH_RADIUS, 0.20)));
    public static final DetailedSurfaceScanner DETAILED_SURFACE_SCANNER_1_I_V1_PRE = new DetailedSurfaceScanner("DETAILED_SURFACE_SCANNER_1_I_V1_PRE", HorizonsBlueprintName.DETAILED_SURFACE_SCANNER_PRE, ModuleSize.SIZE_1, ModuleClass.I, false, 250000, "Int_DetailedSurfaceScanner_Tiny", Map.ofEntries( Map.entry(HorizonsModifier.INTEGRITY, 20.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 3.0), Map.entry(HorizonsModifier.DSS_PATCH_RADIUS, 0.20)));
    static {
        DETAILED_SURFACE_SCANNER_1_I_V1_PRE.getModifications().add(
                    new Modification(HorizonsBlueprintType.EXPANDED_PROBE_SCANNING_RADIUS_X2, 1.0, HorizonsBlueprintGrade.GRADE_5)
        );
    }
    public static final List<DetailedSurfaceScanner> DETAILED_SURFACE_SCANNERS = List.of(
            DETAILED_SURFACE_SCANNER_1_I,
            DETAILED_SURFACE_SCANNER_1_I_V1_PRE
    );
    public DetailedSurfaceScanner(final String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, basePrice, internalName, attributes);
    }

    public DetailedSurfaceScanner(final String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, basePrice, internalName, attributes);
    }

    public DetailedSurfaceScanner(DetailedSurfaceScanner detailedSurfaceScanner) {
        super(detailedSurfaceScanner);
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedBlueprints() {
        if(DETAILED_SURFACE_SCANNER_1_I_V1_PRE.equals(this)){
            return Collections.emptyList();
        }
        return DetailedSurfaceScannerBlueprints.BLUEPRINTS.keySet().stream().toList();
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedExperimentalEffects() {
        return Collections.emptyList();
    }

    @Override
    public ShipModule Clone() {
        return new DetailedSurfaceScanner(this);
    }

    @Override
    public boolean isPreEngineered() {
        return DETAILED_SURFACE_SCANNER_1_I_V1_PRE.equals(this);
    }
    @Override
    public int getGrouping() {
        return 1;
    }
}
