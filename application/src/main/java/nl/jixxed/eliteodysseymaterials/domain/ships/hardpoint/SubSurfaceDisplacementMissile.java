package nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint;

import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SubSurfaceDisplacementMissile extends HardpointModule {
    public static final SubSurfaceDisplacementMissile SUB_SURFACE_DISPLACEMENT_MISSILE_1_B_F = new SubSurfaceDisplacementMissile("SUB_SURFACE_DISPLACEMENT_MISSILE_1_B_F", HorizonsBlueprintName.SUB_SURFACE_DISPLACEMENT_MISSILE, ModuleSize.SIZE_1, ModuleClass.B, false, Mounting.FIXED, 12600, "Hpt_Mining_SubSurfDispMisle_Fixed_Small", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.0), Map.entry(HorizonsModifier.INTEGRITY,  40.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.42), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  2.5), Map.entry(HorizonsModifier.DAMAGE,  5.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  0.18), Map.entry(HorizonsModifier.THERMAL_LOAD,  2.25), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  25.0), Map.entry(HorizonsModifier.SHOT_SPEED,  550.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  0.5), Map.entry(HorizonsModifier.BURST_INTERVAL,  2.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  32.0), Map.entry(HorizonsModifier.RELOAD_TIME,  2.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  0.5), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.10), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.20), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO,  1.0)));
    public static final SubSurfaceDisplacementMissile SUB_SURFACE_DISPLACEMENT_MISSILE_1_B_T = new SubSurfaceDisplacementMissile("SUB_SURFACE_DISPLACEMENT_MISSILE_1_B_T", HorizonsBlueprintName.SUB_SURFACE_DISPLACEMENT_MISSILE, ModuleSize.SIZE_1, ModuleClass.B, true, Mounting.TURRETED, 38750, "Hpt_Mining_SubSurfDispMisle_Turret_Small", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.0), Map.entry(HorizonsModifier.INTEGRITY,  40.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.53), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  2.5), Map.entry(HorizonsModifier.DAMAGE,  5.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  0.16), Map.entry(HorizonsModifier.THERMAL_LOAD,  2.25), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  25.0), Map.entry(HorizonsModifier.SHOT_SPEED,  550.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  0.5), Map.entry(HorizonsModifier.BURST_INTERVAL,  2.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  32.0), Map.entry(HorizonsModifier.RELOAD_TIME,  2.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  0.5), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.10), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.20), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO,  1.0)));
    public static final SubSurfaceDisplacementMissile SUB_SURFACE_DISPLACEMENT_MISSILE_2_B_F = new SubSurfaceDisplacementMissile("SUB_SURFACE_DISPLACEMENT_MISSILE_2_B_F", HorizonsBlueprintName.SUB_SURFACE_DISPLACEMENT_MISSILE, ModuleSize.SIZE_2, ModuleClass.B, false, Mounting.FIXED, 122170, "Hpt_Mining_SubSurfDispMisle_Fixed_Medium", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  4.0), Map.entry(HorizonsModifier.INTEGRITY,  51.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.01), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  2.5), Map.entry(HorizonsModifier.DAMAGE,  5.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  0.21), Map.entry(HorizonsModifier.THERMAL_LOAD,  2.9), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  25.0), Map.entry(HorizonsModifier.SHOT_SPEED,  550.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  0.5), Map.entry(HorizonsModifier.BURST_INTERVAL,  2.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  96.0), Map.entry(HorizonsModifier.RELOAD_TIME,  2.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  0.5), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.10), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.20), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO,  1.0)));
    public static final SubSurfaceDisplacementMissile SUB_SURFACE_DISPLACEMENT_MISSILE_2_B_T = new SubSurfaceDisplacementMissile("SUB_SURFACE_DISPLACEMENT_MISSILE_2_B_T", HorizonsBlueprintName.SUB_SURFACE_DISPLACEMENT_MISSILE, ModuleSize.SIZE_2, ModuleClass.B, true, Mounting.TURRETED, 381750, "Hpt_Mining_SubSurfDispMisle_Turret_Medium", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  4.0), Map.entry(HorizonsModifier.INTEGRITY,  51.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.93), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  2.5), Map.entry(HorizonsModifier.DAMAGE,  5.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  0.18), Map.entry(HorizonsModifier.THERMAL_LOAD,  2.9), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  25.0), Map.entry(HorizonsModifier.SHOT_SPEED,  550.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  0.5), Map.entry(HorizonsModifier.BURST_INTERVAL,  2.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  96.0), Map.entry(HorizonsModifier.RELOAD_TIME,  2.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  0.5), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.10), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.20), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO,  1.0)));
    public static final SubSurfaceDisplacementMissile SUB_SURFACE_EXTRACTION_MISSILE_2_B_F = new SubSurfaceDisplacementMissile("SUB_SURFACE_EXTRACTION_MISSILE_2_B_F", HorizonsBlueprintName.SUB_SURFACE_EXTRACTION_MISSILE, ModuleSize.SIZE_2, ModuleClass.B, false, Mounting.FIXED, 865359, "Hpt_Human_Extraction_Fixed_Medium", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  4.0), Map.entry(HorizonsModifier.INTEGRITY,  50.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.00), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  2.5), Map.entry(HorizonsModifier.DAMAGE,  5.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  0.21), Map.entry(HorizonsModifier.THERMAL_LOAD,  2.9), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  25.0), Map.entry(HorizonsModifier.SHOT_SPEED,  550.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  0.5), Map.entry(HorizonsModifier.BURST_INTERVAL,  2.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  96.0), Map.entry(HorizonsModifier.RELOAD_TIME,  2.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  0.5), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.10), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.20), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO,  1.0)));
    public static final List<SubSurfaceDisplacementMissile> SUB_SURFACE_DISPLACEMENT_MISSILES = List.of(
            SUB_SURFACE_DISPLACEMENT_MISSILE_1_B_F,
            SUB_SURFACE_DISPLACEMENT_MISSILE_1_B_T,
            SUB_SURFACE_DISPLACEMENT_MISSILE_2_B_F,
            SUB_SURFACE_DISPLACEMENT_MISSILE_2_B_T,
            SUB_SURFACE_EXTRACTION_MISSILE_2_B_F
    );
    public SubSurfaceDisplacementMissile(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public SubSurfaceDisplacementMissile(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public SubSurfaceDisplacementMissile(SubSurfaceDisplacementMissile hardpointModule) {
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
    public SubSurfaceDisplacementMissile Clone() {
        return new SubSurfaceDisplacementMissile(this);
    }
    @Override
    public int getGrouping() {
        return switch (getId()) {
            case "SUB_SURFACE_DISPLACEMENT_MISSILE_1_B_F", "SUB_SURFACE_DISPLACEMENT_MISSILE_1_B_T" -> 1;
            case "SUB_SURFACE_DISPLACEMENT_MISSILE_2_B_F", "SUB_SURFACE_DISPLACEMENT_MISSILE_2_B_T", "SUB_SURFACE_EXTRACTION_MISSILE_2_B_F" -> 2;
            default -> 0;
        };
    }
}
