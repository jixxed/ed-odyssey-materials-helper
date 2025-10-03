package nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint;

import nl.jixxed.eliteodysseymaterials.constants.horizons.SynthesisBlueprints;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsSynthesisBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SubSurfaceExtractionMissile extends HardpointModule {
    public static final SubSurfaceExtractionMissile SUB_SURFACE_EXTRACTION_MISSILE_2_B_F = new SubSurfaceExtractionMissile("SUB_SURFACE_EXTRACTION_MISSILE_2_B_F", HorizonsBlueprintName.SUB_SURFACE_EXTRACTION_MISSILE, ModuleSize.SIZE_2, ModuleClass.B, false, Mounting.FIXED, 843170, "Hpt_Human_Extraction_Fixed_Medium", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  4.0), Map.entry(HorizonsModifier.INTEGRITY,  50.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.00), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  2.5), Map.entry(HorizonsModifier.DAMAGE,  5.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  0.21), Map.entry(HorizonsModifier.THERMAL_LOAD,  2.9), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  25.0), Map.entry(HorizonsModifier.SHOT_SPEED,  550.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  0.5), Map.entry(HorizonsModifier.BURST_INTERVAL,  2.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  96.0), Map.entry(HorizonsModifier.RELOAD_TIME,  2.0),                  Map.entry(HorizonsModifier.BREACH_DAMAGE,  0.1), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.10), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.20), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO,  1.0)));
    public static final List<SubSurfaceExtractionMissile> SUB_SURFACE_EXTRACTION_MISSILES = List.of(
            SUB_SURFACE_EXTRACTION_MISSILE_2_B_F
    );

    public SubSurfaceExtractionMissile(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public SubSurfaceExtractionMissile(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public SubSurfaceExtractionMissile(SubSurfaceExtractionMissile hardpointModule) {
        super(hardpointModule);
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedBlueprints() {
        return Collections.emptyList();
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedExperimentalEffects() {
        return Collections.emptyList();
    }

    @Override
    public SubSurfaceExtractionMissile Clone() {
        return new SubSurfaceExtractionMissile(this);
    }

}
