package nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint;

import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.constants.horizons.ExperimentalEffectBlueprints;
import nl.jixxed.eliteodysseymaterials.constants.horizons.SynthesisBlueprints;
import nl.jixxed.eliteodysseymaterials.constants.horizons.hardpoints.SeekerMissileRackBlueprints;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsSynthesisBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsTechBrokerBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.*;

public class SeekerMissileRack extends HardpointModule {
    public static final SeekerMissileRack SEEKER_MISSILE_RACK_1_B_F = new SeekerMissileRack("SEEKER_MISSILE_RACK_1_B_F", HorizonsBlueprintName.SEEKER_MISSILE_RACK_REG, ModuleSize.SIZE_1, ModuleClass.B, true, Mounting.FIXED, 72600, "Hpt_BasicMissileRack_Fixed_Small",                                                                              Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.00), Map.entry(HorizonsModifier.INTEGRITY, 40.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.60), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 13.333), Map.entry(HorizonsModifier.DAMAGE, 40.000), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.240), Map.entry(HorizonsModifier.THERMAL_LOAD, 3.60), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0), Map.entry(HorizonsModifier.SHOT_SPEED, 625.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 0.333), Map.entry(HorizonsModifier.BURST_INTERVAL, 3.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 6.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 6.0),                                                        Map.entry(HorizonsModifier.RELOAD_TIME, 12.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.4), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 0.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 0.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 1.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.0), Map.entry(HorizonsModifier.AMMO_COST, 500.0), Map.entry(HorizonsModifier.JITTER, 0.0), Map.entry(HorizonsModifier.SHIELDED_TARGET_HEAT_INCREASED, false), Map.entry(HorizonsModifier.TARGET_SIGNATURE_INCREASED, false), Map.entry(HorizonsModifier.DAMAGE_PARTIALLY_THERMAL, false), Map.entry(HorizonsModifier.TARGET_SPEED_REDUCED, false)));
    public static final SeekerMissileRack SEEKER_MISSILE_RACK_2_B_F = new SeekerMissileRack("SEEKER_MISSILE_RACK_2_B_F", HorizonsBlueprintName.SEEKER_MISSILE_RACK_REG, ModuleSize.SIZE_2, ModuleClass.B, true, Mounting.FIXED, 512400, "Hpt_BasicMissileRack_Fixed_Medium",                                                                            Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.00), Map.entry(HorizonsModifier.INTEGRITY, 51.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.20), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 13.333), Map.entry(HorizonsModifier.DAMAGE, 40.000), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.240), Map.entry(HorizonsModifier.THERMAL_LOAD, 3.60), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0), Map.entry(HorizonsModifier.SHOT_SPEED, 625.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 0.333), Map.entry(HorizonsModifier.BURST_INTERVAL, 3.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 6.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 18.0),                                                       Map.entry(HorizonsModifier.RELOAD_TIME, 12.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.4), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 0.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 0.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 1.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.0), Map.entry(HorizonsModifier.AMMO_COST, 500.0), Map.entry(HorizonsModifier.JITTER, 0.0), Map.entry(HorizonsModifier.SHIELDED_TARGET_HEAT_INCREASED, false), Map.entry(HorizonsModifier.TARGET_SIGNATURE_INCREASED, false), Map.entry(HorizonsModifier.DAMAGE_PARTIALLY_THERMAL, false), Map.entry(HorizonsModifier.TARGET_SPEED_REDUCED, false)));
    public static final SeekerMissileRack SEEKER_MISSILE_RACK_3_A_F = new SeekerMissileRack("SEEKER_MISSILE_RACK_3_A_F", HorizonsBlueprintName.SEEKER_MISSILE_RACK_REG, ModuleSize.SIZE_3, ModuleClass.A, true, Mounting.FIXED, 1471030, "Hpt_BasicMissileRack_Fixed_Large",                                                                            Map.ofEntries(Map.entry(HorizonsModifier.MASS, 8.00), Map.entry(HorizonsModifier.INTEGRITY, 64.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.62), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 13.333), Map.entry(HorizonsModifier.DAMAGE, 40.000), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.240), Map.entry(HorizonsModifier.THERMAL_LOAD, 3.60), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0), Map.entry(HorizonsModifier.SHOT_SPEED, 625.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 0.333), Map.entry(HorizonsModifier.BURST_INTERVAL, 3.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 6.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 36.0),                                                       Map.entry(HorizonsModifier.RELOAD_TIME, 12.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.4), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 0.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 0.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 1.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.0), Map.entry(HorizonsModifier.AMMO_COST, 500.0), Map.entry(HorizonsModifier.JITTER, 0.0), Map.entry(HorizonsModifier.SHIELDED_TARGET_HEAT_INCREASED, false), Map.entry(HorizonsModifier.TARGET_SIGNATURE_INCREASED, false), Map.entry(HorizonsModifier.DAMAGE_PARTIALLY_THERMAL, false), Map.entry(HorizonsModifier.TARGET_SPEED_REDUCED, false)));
    public static final SeekerMissileRack PACK_HOUND_MISSILE_RACK_2_B_F = new SeekerMissileRack("PACK_HOUND_MISSILE_RACK_2_B_F", HorizonsBlueprintName.PACK_HOUND_MISSILE_RACK, ModuleSize.SIZE_2, ModuleClass.B, Origin.POWERPLAY, true, Mounting.FIXED, 768600, "Hpt_DrunkMissileRack_Fixed_Medium",                                              Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.00), Map.entry(HorizonsModifier.INTEGRITY, 51.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.20), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 60.000), Map.entry(HorizonsModifier.DAMAGE, 7.500), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.240), Map.entry(HorizonsModifier.THERMAL_LOAD, 3.60), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0), Map.entry(HorizonsModifier.SHOT_SPEED, 600.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 2.000), Map.entry(HorizonsModifier.BURST_INTERVAL, 0.5), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 12.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 120.0), Map.entry(HorizonsModifier.ROUNDS_PER_SHOT, 4.0), Map.entry(HorizonsModifier.RELOAD_TIME, 5.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.4), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 0.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 0.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 1.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.0), Map.entry(HorizonsModifier.AMMO_COST, 500.0), Map.entry(HorizonsModifier.JITTER, 0.0), Map.entry(HorizonsModifier.SHIELDED_TARGET_HEAT_INCREASED, false), Map.entry(HorizonsModifier.TARGET_SIGNATURE_INCREASED, false), Map.entry(HorizonsModifier.DAMAGE_PARTIALLY_THERMAL, false), Map.entry(HorizonsModifier.TARGET_SPEED_REDUCED, false)));
    public static final SeekerMissileRack SEEKER_MISSILE_RACK_2_B_F_PRE = new SeekerMissileRack("SEEKER_MISSILE_RACK_2_B_F_PRE", HorizonsBlueprintName.SEEKER_MISSILE_RACK_PRE, ModuleSize.SIZE_2, ModuleClass.B, Origin.EURYBIA_BLUE_MAFIA, true, Mounting.FIXED, 0, "Hpt_BasicMissileRack_Fixed_Medium",                                          Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.00), Map.entry(HorizonsModifier.INTEGRITY, 51.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.20), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 13.333), Map.entry(HorizonsModifier.DAMAGE, 40.000), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.240), Map.entry(HorizonsModifier.THERMAL_LOAD, 3.60), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0), Map.entry(HorizonsModifier.SHOT_SPEED, 625.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 0.333), Map.entry(HorizonsModifier.BURST_INTERVAL, 3.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 6.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 18.0),                                                       Map.entry(HorizonsModifier.RELOAD_TIME, 12.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.4), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 0.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 0.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 1.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.0), Map.entry(HorizonsModifier.AMMO_COST, 500.0), Map.entry(HorizonsModifier.JITTER, 0.0), Map.entry(HorizonsModifier.SHIELDED_TARGET_HEAT_INCREASED, false), Map.entry(HorizonsModifier.TARGET_SIGNATURE_INCREASED, false), Map.entry(HorizonsModifier.DAMAGE_PARTIALLY_THERMAL, false), Map.entry(HorizonsModifier.TARGET_SPEED_REDUCED, false)));
    public static final SeekerMissileRack SEEKER_MISSILE_RACK_2_B_F_PRE_2 = new SeekerMissileRack("SEEKER_MISSILE_RACK_2_B_F_PRE_2", HorizonsBlueprintName.SEEKER_MISSILE_RACK_PRE, ModuleSize.SIZE_2, ModuleClass.B, true, Mounting.FIXED, 0, "Hpt_BasicMissileRack_Fixed_Medium",                                                                 Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.00), Map.entry(HorizonsModifier.INTEGRITY, 51.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.20), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 13.333), Map.entry(HorizonsModifier.DAMAGE, 40.000), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.240), Map.entry(HorizonsModifier.THERMAL_LOAD, 3.60), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0), Map.entry(HorizonsModifier.SHOT_SPEED, 625.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 0.333), Map.entry(HorizonsModifier.BURST_INTERVAL, 3.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 6.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 18.0),                                                       Map.entry(HorizonsModifier.RELOAD_TIME, 12.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.4), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 0.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 0.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 1.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.0), Map.entry(HorizonsModifier.AMMO_COST, 500.0), Map.entry(HorizonsModifier.JITTER, 0.0), Map.entry(HorizonsModifier.SHIELDED_TARGET_HEAT_INCREASED, false), Map.entry(HorizonsModifier.TARGET_SIGNATURE_INCREASED, false), Map.entry(HorizonsModifier.DAMAGE_PARTIALLY_THERMAL, false), Map.entry(HorizonsModifier.TARGET_FSD_REBOOTS, false), Map.entry(HorizonsModifier.TARGET_SPEED_REDUCED, false)));
    public static final SeekerMissileRack SEEKER_MISSILE_RACK_3_A_F_PRE = new SeekerMissileRack("SEEKER_MISSILE_RACK_3_A_F_PRE", HorizonsBlueprintName.SEEKER_MISSILE_RACK_PRE, ModuleSize.SIZE_3, ModuleClass.A, true, Mounting.FIXED, 0, "Hpt_BasicMissileRack_Fixed_Large",                                                                      Map.ofEntries(Map.entry(HorizonsModifier.MASS, 8.00), Map.entry(HorizonsModifier.INTEGRITY, 64.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.62), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 13.333), Map.entry(HorizonsModifier.DAMAGE, 40.000), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.240), Map.entry(HorizonsModifier.THERMAL_LOAD, 3.60), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0), Map.entry(HorizonsModifier.SHOT_SPEED, 625.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 0.333), Map.entry(HorizonsModifier.BURST_INTERVAL, 3.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 6.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 36.0),                                                       Map.entry(HorizonsModifier.RELOAD_TIME, 12.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.4), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 0.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 0.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 1.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.0), Map.entry(HorizonsModifier.AMMO_COST, 500.0), Map.entry(HorizonsModifier.JITTER, 0.0), Map.entry(HorizonsModifier.SHIELDED_TARGET_HEAT_INCREASED, false), Map.entry(HorizonsModifier.TARGET_SIGNATURE_INCREASED, false), Map.entry(HorizonsModifier.DAMAGE_PARTIALLY_THERMAL, false), Map.entry(HorizonsModifier.TARGET_FSD_REBOOTS, false), Map.entry(HorizonsModifier.TARGET_SPEED_REDUCED, false)));

    static {
        SEEKER_MISSILE_RACK_2_B_F_PRE.getModifications().add(
                new Modification(HorizonsBlueprintType.HIGH_CAPACITY_MAGAZINE_THERMAL_CASCADE, 1.0, HorizonsBlueprintGrade.GRADE_5)
        );
        SEEKER_MISSILE_RACK_2_B_F_PRE_2.getModifications().add(
                new Modification(HorizonsBlueprintType.HIGH_CAPACITY_MAGAZINE_STURDY_FSD_INTERRUPT, 1.0, HorizonsBlueprintGrade.GRADE_5)
        );
        SEEKER_MISSILE_RACK_3_A_F_PRE.getModifications().add(
                new Modification(HorizonsBlueprintType.HIGH_CAPACITY_MAGAZINE_STURDY_FSD_INTERRUPT, 1.0, HorizonsBlueprintGrade.GRADE_5)
        );
    }

    public static final List<SeekerMissileRack> SEEKER_MISSILE_RACKS = List.of(
            SEEKER_MISSILE_RACK_1_B_F,
            SEEKER_MISSILE_RACK_2_B_F,
            SEEKER_MISSILE_RACK_3_A_F,
            PACK_HOUND_MISSILE_RACK_2_B_F,
            SEEKER_MISSILE_RACK_2_B_F_PRE,
            SEEKER_MISSILE_RACK_2_B_F_PRE_2,
            SEEKER_MISSILE_RACK_3_A_F_PRE
    );

    public SeekerMissileRack(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public SeekerMissileRack(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public SeekerMissileRack(SeekerMissileRack missileRack) {
        super(missileRack);
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedBlueprints() {
        if (isPreEngineered()) {
            return Collections.emptyList();
        }
        return SeekerMissileRackBlueprints.BLUEPRINTS.keySet().stream().toList();
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedExperimentalEffects() {
        if (isPreEngineered()) {
            return Collections.emptyList();
        }
        return ExperimentalEffectBlueprints.SEEKER_MISSILE_RACK.keySet().stream().toList();
    }

    @Override
    public SeekerMissileRack Clone() {
        return new SeekerMissileRack(this);
    }


    @Override
    public boolean isPreEngineered() {
        return SEEKER_MISSILE_RACK_2_B_F_PRE.equals(this) || SEEKER_MISSILE_RACK_2_B_F_PRE_2.equals(this) || SEEKER_MISSILE_RACK_3_A_F_PRE.equals(this);
    }

    public boolean isSeeker() {
        return SEEKER_MISSILE_RACK_1_B_F.equals(this) || SEEKER_MISSILE_RACK_2_B_F.equals(this) || SEEKER_MISSILE_RACK_3_A_F.equals(this) || SEEKER_MISSILE_RACK_2_B_F_PRE.equals(this) || SEEKER_MISSILE_RACK_2_B_F_PRE_2.equals(this) || SEEKER_MISSILE_RACK_3_A_F_PRE.equals(this);
    }

    @Override
    public boolean isCGExclusive() {
        return SEEKER_MISSILE_RACK_2_B_F_PRE_2.equals(this) || SEEKER_MISSILE_RACK_3_A_F_PRE.equals(this);
    }

    @Override
    public String getClarifier() {
        if (PACK_HOUND_MISSILE_RACK_2_B_F.equals(this)) {
            return " " + LocaleService.getLocalizedStringForCurrentLocale(getName().getLocalizationKey() + ".short");
        }
        return super.getClarifier();
    }

    @Override
    public int getGrouping() {
        return switch (getId()) {
            case "SEEKER_MISSILE_RACK_1_B_F" -> 1;
            case "SEEKER_MISSILE_RACK_2_B_F" -> 3;
            case "SEEKER_MISSILE_RACK_2_B_F_PRE", "SEEKER_MISSILE_RACK_2_B_F_PRE_2" -> 4;
            case "PACK_HOUND_MISSILE_RACK_2_B_F" -> 5;
            case "SEEKER_MISSILE_RACK_3_A_F" -> 7;
            case "SEEKER_MISSILE_RACK_3_A_F_PRE" -> 8;
            default -> 0;
        };
    }

    @Override
    public Optional<ShipModule> findHigherSize() {
        if (SEEKER_MISSILE_RACK_2_B_F_PRE.equals(this)) {
            return Optional.empty();
        } else if (SEEKER_MISSILE_RACK_2_B_F_PRE_2.equals(this)) {
            return Optional.of(SEEKER_MISSILE_RACK_3_A_F_PRE);
        }
        return super.findHigherSize();
    }

    @Override
    public Optional<ShipModule> findLowerSize() {
        if (SEEKER_MISSILE_RACK_3_A_F_PRE.equals(this)) {
            return Optional.of(SEEKER_MISSILE_RACK_2_B_F_PRE_2);
        }
        return super.findLowerSize();
    }

    @Override
    public HorizonsTechBrokerBlueprint techBrokerBlueprint() {
        if (SEEKER_MISSILE_RACK_2_B_F_PRE.equals(this)) {
            return (HorizonsTechBrokerBlueprint) HorizonsBlueprintConstants.getRecipe(HorizonsBlueprintName.HUMAN_WEAPONS, HorizonsBlueprintType.ENGINEERED_MISSILE_RACK_V1, null);
        }
        return super.techBrokerBlueprint();
    }

    @Override
    public Collection<HorizonsSynthesisBlueprint> synthesisBlueprints() {
        return SynthesisBlueprints.EXPLOSIVE_MUNITIONS.values();
    }
}
