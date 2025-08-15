package nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint;

import nl.jixxed.eliteodysseymaterials.constants.horizons.ExperimentalEffectBlueprints;
import nl.jixxed.eliteodysseymaterials.constants.horizons.hardpoints.DumbfireMissileRackBlueprints;
import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DumbfireMissileRack extends HardpointModule {
    public static final DumbfireMissileRack MISSILE_RACK_1_B_F = new DumbfireMissileRack("MISSILE_RACK_1_B_F", HorizonsBlueprintName.MISSILE_RACK, ModuleSize.SIZE_1, ModuleClass.B, false, Mounting.FIXED, 32180, "Hpt_DumbfireMissileRack_Fixed_Small",                                                                                               Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.00), Map.entry(HorizonsModifier.INTEGRITY, 40.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.40), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 25.000), Map.entry(HorizonsModifier.DAMAGE, 50.000), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.240), Map.entry(HorizonsModifier.THERMAL_LOAD, 3.60), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0), Map.entry(HorizonsModifier.SHOT_SPEED, 750.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 0.500), Map.entry(HorizonsModifier.BURST_INTERVAL, 2.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 8.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 16.0),                                                       Map.entry(HorizonsModifier.RELOAD_TIME, 5.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.4), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 1.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.0), Map.entry(HorizonsModifier.AMMO_COST, 500.0), Map.entry(HorizonsModifier.JITTER, 0.0), Map.entry(HorizonsModifier.SHIELDED_TARGET_HEAT_INCREASED, false), Map.entry(HorizonsModifier.TARGET_SIGNATURE_INCREASED, false), Map.entry(HorizonsModifier.DAMAGE_PARTIALLY_THERMAL, false), Map.entry(HorizonsModifier.TARGET_FSD_REBOOTS, false), Map.entry(HorizonsModifier.TARGET_MODULE_DAMAGE, false)));
    public static final DumbfireMissileRack ADVANCED_MISSILE_RACK_1_B_F = new DumbfireMissileRack("ADVANCED_MISSILE_RACK_1_B_F", HorizonsBlueprintName.ADVANCED_MISSILE_RACK, ModuleSize.SIZE_1, ModuleClass.B, false, Mounting.FIXED, 32180, "Hpt_DumbfireMissileRack_Fixed_Small_Advanced",                                                           Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.00), Map.entry(HorizonsModifier.INTEGRITY, 40.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.40), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 25.000), Map.entry(HorizonsModifier.DAMAGE, 50.000), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.240), Map.entry(HorizonsModifier.THERMAL_LOAD, 3.60), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0), Map.entry(HorizonsModifier.SHOT_SPEED, 750.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 0.500), Map.entry(HorizonsModifier.BURST_INTERVAL, 2.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 8.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 64.0),                                                       Map.entry(HorizonsModifier.RELOAD_TIME, 5.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.4), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 1.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.0), Map.entry(HorizonsModifier.AMMO_COST, 500.0), Map.entry(HorizonsModifier.JITTER, 0.0), Map.entry(HorizonsModifier.SHIELDED_TARGET_HEAT_INCREASED, false), Map.entry(HorizonsModifier.TARGET_SIGNATURE_INCREASED, false), Map.entry(HorizonsModifier.DAMAGE_PARTIALLY_THERMAL, false), Map.entry(HorizonsModifier.TARGET_FSD_REBOOTS, false), Map.entry(HorizonsModifier.TARGET_MODULE_DAMAGE, false)));
    public static final DumbfireMissileRack MISSILE_RACK_2_B_F = new DumbfireMissileRack("MISSILE_RACK_2_B_F", HorizonsBlueprintName.MISSILE_RACK, ModuleSize.SIZE_2, ModuleClass.B, false, Mounting.FIXED, 240400, "Hpt_DumbfireMissileRack_Fixed_Medium",                                                                                             Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.00), Map.entry(HorizonsModifier.INTEGRITY, 51.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.20), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 25.000), Map.entry(HorizonsModifier.DAMAGE, 50.000), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.240), Map.entry(HorizonsModifier.THERMAL_LOAD, 3.60), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0), Map.entry(HorizonsModifier.SHOT_SPEED, 750.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 0.500), Map.entry(HorizonsModifier.BURST_INTERVAL, 2.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 12.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 48.0),                                                      Map.entry(HorizonsModifier.RELOAD_TIME, 5.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.4), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 1.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.0), Map.entry(HorizonsModifier.AMMO_COST, 500.0), Map.entry(HorizonsModifier.JITTER, 0.0), Map.entry(HorizonsModifier.SHIELDED_TARGET_HEAT_INCREASED, false), Map.entry(HorizonsModifier.TARGET_SIGNATURE_INCREASED, false), Map.entry(HorizonsModifier.DAMAGE_PARTIALLY_THERMAL, false), Map.entry(HorizonsModifier.TARGET_FSD_REBOOTS, false), Map.entry(HorizonsModifier.TARGET_MODULE_DAMAGE, false)));
    public static final DumbfireMissileRack ADVANCED_MISSILE_RACK_2_B_F = new DumbfireMissileRack("ADVANCED_MISSILE_RACK_2_B_F", HorizonsBlueprintName.ADVANCED_MISSILE_RACK, ModuleSize.SIZE_2, ModuleClass.B, false, Mounting.FIXED, 240400, "Hpt_DumbfireMissileRack_Fixed_Medium_Advanced",                                                         Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.00), Map.entry(HorizonsModifier.INTEGRITY, 51.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.20), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 25.000), Map.entry(HorizonsModifier.DAMAGE, 50.000), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.240), Map.entry(HorizonsModifier.THERMAL_LOAD, 3.60), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0), Map.entry(HorizonsModifier.SHOT_SPEED, 750.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 0.500), Map.entry(HorizonsModifier.BURST_INTERVAL, 2.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 12.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 64.0),                                                      Map.entry(HorizonsModifier.RELOAD_TIME, 5.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.4), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 1.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.0), Map.entry(HorizonsModifier.AMMO_COST, 500.0), Map.entry(HorizonsModifier.JITTER, 0.0), Map.entry(HorizonsModifier.SHIELDED_TARGET_HEAT_INCREASED, false), Map.entry(HorizonsModifier.TARGET_SIGNATURE_INCREASED, false), Map.entry(HorizonsModifier.DAMAGE_PARTIALLY_THERMAL, false), Map.entry(HorizonsModifier.TARGET_FSD_REBOOTS, false), Map.entry(HorizonsModifier.TARGET_MODULE_DAMAGE, false)));
    public static final DumbfireMissileRack MISSILE_RACK_3_A_F = new DumbfireMissileRack("MISSILE_RACK_3_A_F", HorizonsBlueprintName.MISSILE_RACK, ModuleSize.SIZE_3, ModuleClass.A, false, Mounting.FIXED, 1021500, "Hpt_DumbfireMissileRack_Fixed_Large",                                                                                             Map.ofEntries(Map.entry(HorizonsModifier.MASS, 8.00), Map.entry(HorizonsModifier.INTEGRITY, 64.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.62), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 25.000), Map.entry(HorizonsModifier.DAMAGE, 50.000), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.240), Map.entry(HorizonsModifier.THERMAL_LOAD, 3.60), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0), Map.entry(HorizonsModifier.SHOT_SPEED, 750.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 0.500), Map.entry(HorizonsModifier.BURST_INTERVAL, 2.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 12.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 96.0),                                                      Map.entry(HorizonsModifier.RELOAD_TIME, 5.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.4), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 1.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.0), Map.entry(HorizonsModifier.AMMO_COST, 500.0), Map.entry(HorizonsModifier.JITTER, 0.0), Map.entry(HorizonsModifier.SHIELDED_TARGET_HEAT_INCREASED, false), Map.entry(HorizonsModifier.TARGET_SIGNATURE_INCREASED, false), Map.entry(HorizonsModifier.DAMAGE_PARTIALLY_THERMAL, false), Map.entry(HorizonsModifier.TARGET_FSD_REBOOTS, false), Map.entry(HorizonsModifier.TARGET_MODULE_DAMAGE, false)));
    public static final DumbfireMissileRack ROCKET_PROPELLED_FSD_DISRUPTER_2_B_F = new DumbfireMissileRack("ROCKET_PROPELLED_FSD_DISRUPTER_2_B_F", HorizonsBlueprintName.ROCKET_PROPELLED_FSD_DISRUPTER_MISSILE_RACK, ModuleSize.SIZE_2, ModuleClass.B, Origin.POWERPLAY, false, Mounting.FIXED, 1951040, "Hpt_DumbfireMissileRack_Fixed_Medium_Lasso", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.00), Map.entry(HorizonsModifier.INTEGRITY, 51.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.20), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 13.333), Map.entry(HorizonsModifier.DAMAGE, 40.000), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.240), Map.entry(HorizonsModifier.THERMAL_LOAD, 3.60), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0), Map.entry(HorizonsModifier.SHOT_SPEED, 750.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 0.333), Map.entry(HorizonsModifier.BURST_INTERVAL, 3.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 12.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 48.0),                                                      Map.entry(HorizonsModifier.RELOAD_TIME, 5.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.4), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 1.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.0), Map.entry(HorizonsModifier.AMMO_COST, 500.0), Map.entry(HorizonsModifier.JITTER, 0.0), Map.entry(HorizonsModifier.SHIELDED_TARGET_HEAT_INCREASED, false), Map.entry(HorizonsModifier.TARGET_SIGNATURE_INCREASED, false), Map.entry(HorizonsModifier.DAMAGE_PARTIALLY_THERMAL, false), Map.entry(HorizonsModifier.TARGET_FSD_REBOOTS, false), Map.entry(HorizonsModifier.TARGET_MODULE_DAMAGE, false)));


    public static final List<DumbfireMissileRack> DUMBFIRE_MISSILE_RACKS = List.of(
            MISSILE_RACK_1_B_F,
            ADVANCED_MISSILE_RACK_1_B_F,
            MISSILE_RACK_2_B_F,
            ADVANCED_MISSILE_RACK_2_B_F,
            MISSILE_RACK_3_A_F,
            ROCKET_PROPELLED_FSD_DISRUPTER_2_B_F
    );

    public DumbfireMissileRack(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public DumbfireMissileRack(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public DumbfireMissileRack(DumbfireMissileRack dumbfireMissileRack) {
        super(dumbfireMissileRack);
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedBlueprints() {
        return DumbfireMissileRackBlueprints.BLUEPRINTS.keySet().stream().toList();
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedExperimentalEffects() {
        return ExperimentalEffectBlueprints.DUMBFIRE_MISSILE_RACK.keySet().stream().toList();
    }

    @Override
    public DumbfireMissileRack Clone() {
        return new DumbfireMissileRack(this);
    }

    @Override
    public boolean isAdvanced() {
        return ADVANCED_MISSILE_RACK_1_B_F.equals(this) || ADVANCED_MISSILE_RACK_2_B_F.equals(this);
    }

    public boolean isDumbfire() {
        return MISSILE_RACK_1_B_F.equals(this) || MISSILE_RACK_2_B_F.equals(this) || MISSILE_RACK_3_A_F.equals(this);
    }

    @Override
    public String getClarifier() {
        if (ROCKET_PROPELLED_FSD_DISRUPTER_2_B_F.equals(this)) {
            return " " + LocaleService.getLocalizedStringForCurrentLocale(getName().getLocalizationKey() + ".short");
        }
        return super.getClarifier();
    }

    @Override
    public int getGrouping() {
        return switch (getId()) {
            case "MISSILE_RACK_1_B_F", "ADVANCED_MISSILE_RACK_1_B_F" -> 1;
            case "MISSILE_RACK_2_B_F", "ADVANCED_MISSILE_RACK_2_B_F" -> 3;
            case "ROCKET_PROPELLED_FSD_DISRUPTER_2_B_F" -> 6;
            case "MISSILE_RACK_3_A_F" -> 7;
            default -> 0;
        };
    }
}
