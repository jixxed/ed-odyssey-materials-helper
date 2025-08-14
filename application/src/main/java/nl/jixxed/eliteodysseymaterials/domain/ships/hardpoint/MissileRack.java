package nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint;

import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.constants.horizons.ExperimentalEffectBlueprints;
import nl.jixxed.eliteodysseymaterials.constants.horizons.hardpoints.MissileRackBlueprints;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsTechBrokerBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MissileRack extends HardpointModule {
    public static final MissileRack MISSILE_RACK_1_B_F = new MissileRack("MISSILE_RACK_1_B_F", HorizonsBlueprintName.MISSILE_RACK, ModuleSize.SIZE_1, ModuleClass.B, false, Mounting.FIXED, 32180, "Hpt_DumbfireMissileRack_Fixed_Small",                                                                                               Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.00), Map.entry(HorizonsModifier.INTEGRITY, 40.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.40), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 25.000), Map.entry(HorizonsModifier.DAMAGE, 50.000), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.240), Map.entry(HorizonsModifier.THERMAL_LOAD, 3.60), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0), Map.entry(HorizonsModifier.SHOT_SPEED, 750.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 0.500), Map.entry(HorizonsModifier.BURST_INTERVAL, 2.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 8.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 16.0),                                                       Map.entry(HorizonsModifier.RELOAD_TIME, 5.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.4), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 1.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.0), Map.entry(HorizonsModifier.AMMO_COST, 500.0), Map.entry(HorizonsModifier.JITTER, 0.0), Map.entry(HorizonsModifier.SHIELDED_TARGET_HEAT_INCREASED, false), Map.entry(HorizonsModifier.TARGET_SIGNATURE_INCREASED, false), Map.entry(HorizonsModifier.DAMAGE_PARTIALLY_THERMAL, false), Map.entry(HorizonsModifier.TARGET_FSD_REBOOTS, false), Map.entry(HorizonsModifier.TARGET_MODULE_DAMAGE, false)));
    public static final MissileRack SEEKER_MISSILE_RACK_1_B_F = new MissileRack("SEEKER_MISSILE_RACK_1_B_F", HorizonsBlueprintName.SEEKER_MISSILE_RACK, ModuleSize.SIZE_1, ModuleClass.B, true, Mounting.FIXED, 72600, "Hpt_BasicMissileRack_Fixed_Small",                                                                              Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.00), Map.entry(HorizonsModifier.INTEGRITY, 40.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.60), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 13.333), Map.entry(HorizonsModifier.DAMAGE, 40.000), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.240), Map.entry(HorizonsModifier.THERMAL_LOAD, 3.60), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0), Map.entry(HorizonsModifier.SHOT_SPEED, 625.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 0.333), Map.entry(HorizonsModifier.BURST_INTERVAL, 3.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 6.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 6.0),                                                        Map.entry(HorizonsModifier.RELOAD_TIME, 12.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.4), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 0.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 0.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 1.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.0), Map.entry(HorizonsModifier.AMMO_COST, 500.0), Map.entry(HorizonsModifier.JITTER, 0.0), Map.entry(HorizonsModifier.SHIELDED_TARGET_HEAT_INCREASED, false), Map.entry(HorizonsModifier.TARGET_SIGNATURE_INCREASED, false), Map.entry(HorizonsModifier.DAMAGE_PARTIALLY_THERMAL, false), Map.entry(HorizonsModifier.TARGET_SPEED_REDUCED, false)));
    public static final MissileRack ADVANCED_MISSILE_RACK_1_B_F = new MissileRack("ADVANCED_MISSILE_RACK_1_B_F", HorizonsBlueprintName.ADVANCED_MISSILE_RACK, ModuleSize.SIZE_1, ModuleClass.B, false, Mounting.FIXED, 32180, "Hpt_DumbfireMissileRack_Fixed_Small_Advanced",                                                           Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.00), Map.entry(HorizonsModifier.INTEGRITY, 40.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.40), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 25.000), Map.entry(HorizonsModifier.DAMAGE, 50.000), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.240), Map.entry(HorizonsModifier.THERMAL_LOAD, 3.60), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0), Map.entry(HorizonsModifier.SHOT_SPEED, 750.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 0.500), Map.entry(HorizonsModifier.BURST_INTERVAL, 2.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 8.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 64.0),                                                       Map.entry(HorizonsModifier.RELOAD_TIME, 5.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.4), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 1.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.0), Map.entry(HorizonsModifier.AMMO_COST, 500.0), Map.entry(HorizonsModifier.JITTER, 0.0), Map.entry(HorizonsModifier.SHIELDED_TARGET_HEAT_INCREASED, false), Map.entry(HorizonsModifier.TARGET_SIGNATURE_INCREASED, false), Map.entry(HorizonsModifier.DAMAGE_PARTIALLY_THERMAL, false), Map.entry(HorizonsModifier.TARGET_FSD_REBOOTS, false), Map.entry(HorizonsModifier.TARGET_MODULE_DAMAGE, false)));
    public static final MissileRack MISSILE_RACK_2_B_F = new MissileRack("MISSILE_RACK_2_B_F", HorizonsBlueprintName.MISSILE_RACK, ModuleSize.SIZE_2, ModuleClass.B, false, Mounting.FIXED, 240400, "Hpt_DumbfireMissileRack_Fixed_Medium",                                                                                             Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.00), Map.entry(HorizonsModifier.INTEGRITY, 51.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.20), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 25.000), Map.entry(HorizonsModifier.DAMAGE, 50.000), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.240), Map.entry(HorizonsModifier.THERMAL_LOAD, 3.60), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0), Map.entry(HorizonsModifier.SHOT_SPEED, 750.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 0.500), Map.entry(HorizonsModifier.BURST_INTERVAL, 2.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 12.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 48.0),                                                      Map.entry(HorizonsModifier.RELOAD_TIME, 5.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.4), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 1.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.0), Map.entry(HorizonsModifier.AMMO_COST, 500.0), Map.entry(HorizonsModifier.JITTER, 0.0), Map.entry(HorizonsModifier.SHIELDED_TARGET_HEAT_INCREASED, false), Map.entry(HorizonsModifier.TARGET_SIGNATURE_INCREASED, false), Map.entry(HorizonsModifier.DAMAGE_PARTIALLY_THERMAL, false), Map.entry(HorizonsModifier.TARGET_FSD_REBOOTS, false), Map.entry(HorizonsModifier.TARGET_MODULE_DAMAGE, false)));
    public static final MissileRack SEEKER_MISSILE_RACK_2_B_F = new MissileRack("SEEKER_MISSILE_RACK_2_B_F", HorizonsBlueprintName.SEEKER_MISSILE_RACK, ModuleSize.SIZE_2, ModuleClass.B, true, Mounting.FIXED, 512400, "Hpt_BasicMissileRack_Fixed_Medium",                                                                            Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.00), Map.entry(HorizonsModifier.INTEGRITY, 51.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.20), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 13.333), Map.entry(HorizonsModifier.DAMAGE, 40.000), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.240), Map.entry(HorizonsModifier.THERMAL_LOAD, 3.60), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0), Map.entry(HorizonsModifier.SHOT_SPEED, 625.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 0.333), Map.entry(HorizonsModifier.BURST_INTERVAL, 3.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 6.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 18.0),                                                       Map.entry(HorizonsModifier.RELOAD_TIME, 12.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.4), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 0.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 0.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 1.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.0), Map.entry(HorizonsModifier.AMMO_COST, 500.0), Map.entry(HorizonsModifier.JITTER, 0.0), Map.entry(HorizonsModifier.SHIELDED_TARGET_HEAT_INCREASED, false), Map.entry(HorizonsModifier.TARGET_SIGNATURE_INCREASED, false), Map.entry(HorizonsModifier.DAMAGE_PARTIALLY_THERMAL, false), Map.entry(HorizonsModifier.TARGET_SPEED_REDUCED, false)));
    public static final MissileRack ADVANCED_MISSILE_RACK_2_B_F = new MissileRack("ADVANCED_MISSILE_RACK_2_B_F", HorizonsBlueprintName.ADVANCED_MISSILE_RACK, ModuleSize.SIZE_2, ModuleClass.B, false, Mounting.FIXED, 240400, "Hpt_DumbfireMissileRack_Fixed_Medium_Advanced",                                                         Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.00), Map.entry(HorizonsModifier.INTEGRITY, 51.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.20), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 25.000), Map.entry(HorizonsModifier.DAMAGE, 50.000), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.240), Map.entry(HorizonsModifier.THERMAL_LOAD, 3.60), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0), Map.entry(HorizonsModifier.SHOT_SPEED, 750.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 0.500), Map.entry(HorizonsModifier.BURST_INTERVAL, 2.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 12.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 64.0),                                                      Map.entry(HorizonsModifier.RELOAD_TIME, 5.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.4), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 1.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.0), Map.entry(HorizonsModifier.AMMO_COST, 500.0), Map.entry(HorizonsModifier.JITTER, 0.0), Map.entry(HorizonsModifier.SHIELDED_TARGET_HEAT_INCREASED, false), Map.entry(HorizonsModifier.TARGET_SIGNATURE_INCREASED, false), Map.entry(HorizonsModifier.DAMAGE_PARTIALLY_THERMAL, false), Map.entry(HorizonsModifier.TARGET_FSD_REBOOTS, false), Map.entry(HorizonsModifier.TARGET_MODULE_DAMAGE, false)));
    public static final MissileRack MISSILE_RACK_3_A_F = new MissileRack("MISSILE_RACK_3_A_F", HorizonsBlueprintName.MISSILE_RACK, ModuleSize.SIZE_3, ModuleClass.A, false, Mounting.FIXED, 1021500, "Hpt_DumbfireMissileRack_Fixed_Large",                                                                                             Map.ofEntries(Map.entry(HorizonsModifier.MASS, 8.00), Map.entry(HorizonsModifier.INTEGRITY, 64.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.62), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 25.000), Map.entry(HorizonsModifier.DAMAGE, 50.000), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.240), Map.entry(HorizonsModifier.THERMAL_LOAD, 3.60), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0), Map.entry(HorizonsModifier.SHOT_SPEED, 750.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 0.500), Map.entry(HorizonsModifier.BURST_INTERVAL, 2.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 12.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 96.0),                                                      Map.entry(HorizonsModifier.RELOAD_TIME, 5.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.4), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 1.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.0), Map.entry(HorizonsModifier.AMMO_COST, 500.0), Map.entry(HorizonsModifier.JITTER, 0.0), Map.entry(HorizonsModifier.SHIELDED_TARGET_HEAT_INCREASED, false), Map.entry(HorizonsModifier.TARGET_SIGNATURE_INCREASED, false), Map.entry(HorizonsModifier.DAMAGE_PARTIALLY_THERMAL, false), Map.entry(HorizonsModifier.TARGET_FSD_REBOOTS, false), Map.entry(HorizonsModifier.TARGET_MODULE_DAMAGE, false)));
    public static final MissileRack SEEKER_MISSILE_RACK_3_A_F = new MissileRack("SEEKER_MISSILE_RACK_3_A_F", HorizonsBlueprintName.SEEKER_MISSILE_RACK, ModuleSize.SIZE_3, ModuleClass.A, true, Mounting.FIXED, 1471030, "Hpt_BasicMissileRack_Fixed_Large",                                                                            Map.ofEntries(Map.entry(HorizonsModifier.MASS, 8.00), Map.entry(HorizonsModifier.INTEGRITY, 64.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.62), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 13.333), Map.entry(HorizonsModifier.DAMAGE, 40.000), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.240), Map.entry(HorizonsModifier.THERMAL_LOAD, 3.60), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0), Map.entry(HorizonsModifier.SHOT_SPEED, 625.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 0.333), Map.entry(HorizonsModifier.BURST_INTERVAL, 3.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 6.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 36.0),                                                       Map.entry(HorizonsModifier.RELOAD_TIME, 12.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.4), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 0.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 0.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 1.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.0), Map.entry(HorizonsModifier.AMMO_COST, 500.0), Map.entry(HorizonsModifier.JITTER, 0.0), Map.entry(HorizonsModifier.SHIELDED_TARGET_HEAT_INCREASED, false), Map.entry(HorizonsModifier.TARGET_SIGNATURE_INCREASED, false), Map.entry(HorizonsModifier.DAMAGE_PARTIALLY_THERMAL, false), Map.entry(HorizonsModifier.TARGET_SPEED_REDUCED, false)));
    public static final MissileRack PACK_HOUND_MISSILE_RACK_2_B_F = new MissileRack("PACK_HOUND_MISSILE_RACK_2_B_F", HorizonsBlueprintName.PACK_HOUND_MISSILE_RACK, ModuleSize.SIZE_2, ModuleClass.B, Origin.POWERPLAY, true, Mounting.FIXED, 768600, "Hpt_DrunkMissileRack_Fixed_Medium",                                              Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.00), Map.entry(HorizonsModifier.INTEGRITY, 51.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.20), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 60.000), Map.entry(HorizonsModifier.DAMAGE, 7.500), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.240), Map.entry(HorizonsModifier.THERMAL_LOAD, 3.60), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0), Map.entry(HorizonsModifier.SHOT_SPEED, 600.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 2.000), Map.entry(HorizonsModifier.BURST_INTERVAL, 0.5), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 12.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 120.0), Map.entry(HorizonsModifier.ROUNDS_PER_SHOT, 4.0), Map.entry(HorizonsModifier.RELOAD_TIME, 5.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.4), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 0.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 0.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 1.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.0), Map.entry(HorizonsModifier.AMMO_COST, 500.0), Map.entry(HorizonsModifier.JITTER, 0.0), Map.entry(HorizonsModifier.SHIELDED_TARGET_HEAT_INCREASED, false), Map.entry(HorizonsModifier.TARGET_SIGNATURE_INCREASED, false), Map.entry(HorizonsModifier.DAMAGE_PARTIALLY_THERMAL, false), Map.entry(HorizonsModifier.TARGET_SPEED_REDUCED, false)));
    public static final MissileRack ROCKET_PROPELLED_FSD_DISRUPTER_2_B_F = new MissileRack("ROCKET_PROPELLED_FSD_DISRUPTER_2_B_F", HorizonsBlueprintName.ROCKET_PROPELLED_FSD_DISRUPTER_MISSILE_RACK, ModuleSize.SIZE_2, ModuleClass.B, Origin.POWERPLAY, false, Mounting.FIXED, 1951040, "Hpt_DumbfireMissileRack_Fixed_Medium_Lasso", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.00), Map.entry(HorizonsModifier.INTEGRITY, 51.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.20), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 13.333), Map.entry(HorizonsModifier.DAMAGE, 40.000), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.240), Map.entry(HorizonsModifier.THERMAL_LOAD, 3.60), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0), Map.entry(HorizonsModifier.SHOT_SPEED, 750.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 0.333), Map.entry(HorizonsModifier.BURST_INTERVAL, 3.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 12.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 48.0),                                                      Map.entry(HorizonsModifier.RELOAD_TIME, 5.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.4), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 1.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.0), Map.entry(HorizonsModifier.AMMO_COST, 500.0), Map.entry(HorizonsModifier.JITTER, 0.0), Map.entry(HorizonsModifier.SHIELDED_TARGET_HEAT_INCREASED, false), Map.entry(HorizonsModifier.TARGET_SIGNATURE_INCREASED, false), Map.entry(HorizonsModifier.DAMAGE_PARTIALLY_THERMAL, false), Map.entry(HorizonsModifier.TARGET_FSD_REBOOTS, false), Map.entry(HorizonsModifier.TARGET_MODULE_DAMAGE, false)));
    public static final MissileRack SEEKER_MISSILE_RACK_2_B_F_PRE = new MissileRack("SEEKER_MISSILE_RACK_2_B_F_PRE", HorizonsBlueprintName.SEEKER_MISSILE_RACK_PRE, ModuleSize.SIZE_2, ModuleClass.B, Origin.EURYBIA_BLUE_MAFIA, true, Mounting.FIXED, 0, "Hpt_BasicMissileRack_Fixed_Medium",                                          Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.00), Map.entry(HorizonsModifier.INTEGRITY, 51.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.20), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 13.333), Map.entry(HorizonsModifier.DAMAGE, 40.000), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.240), Map.entry(HorizonsModifier.THERMAL_LOAD, 3.60), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0), Map.entry(HorizonsModifier.SHOT_SPEED, 625.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 0.333), Map.entry(HorizonsModifier.BURST_INTERVAL, 3.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 6.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 18.0),                                                       Map.entry(HorizonsModifier.RELOAD_TIME, 12.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.4), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 0.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 0.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 1.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.0), Map.entry(HorizonsModifier.AMMO_COST, 500.0), Map.entry(HorizonsModifier.JITTER, 0.0), Map.entry(HorizonsModifier.SHIELDED_TARGET_HEAT_INCREASED, false), Map.entry(HorizonsModifier.TARGET_SIGNATURE_INCREASED, false), Map.entry(HorizonsModifier.DAMAGE_PARTIALLY_THERMAL, false), Map.entry(HorizonsModifier.TARGET_SPEED_REDUCED, false)));
    public static final MissileRack SEEKER_MISSILE_RACK_2_B_F_PRE_2 = new MissileRack("SEEKER_MISSILE_RACK_2_B_F_PRE_2", HorizonsBlueprintName.SEEKER_MISSILE_RACK_PRE, ModuleSize.SIZE_2, ModuleClass.B, true, Mounting.FIXED, 0, "Hpt_BasicMissileRack_Fixed_Medium",                                                                 Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.00), Map.entry(HorizonsModifier.INTEGRITY, 51.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.20), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 13.333), Map.entry(HorizonsModifier.DAMAGE, 40.000), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.240), Map.entry(HorizonsModifier.THERMAL_LOAD, 3.60), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0), Map.entry(HorizonsModifier.SHOT_SPEED, 625.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 0.333), Map.entry(HorizonsModifier.BURST_INTERVAL, 3.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 6.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 18.0),                                                       Map.entry(HorizonsModifier.RELOAD_TIME, 12.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.4), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 0.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 0.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 1.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.0), Map.entry(HorizonsModifier.AMMO_COST, 500.0), Map.entry(HorizonsModifier.JITTER, 0.0), Map.entry(HorizonsModifier.SHIELDED_TARGET_HEAT_INCREASED, false), Map.entry(HorizonsModifier.TARGET_SIGNATURE_INCREASED, false), Map.entry(HorizonsModifier.DAMAGE_PARTIALLY_THERMAL, false), Map.entry(HorizonsModifier.TARGET_FSD_REBOOTS, false), Map.entry(HorizonsModifier.TARGET_SPEED_REDUCED, false)));
    public static final MissileRack SEEKER_MISSILE_RACK_3_A_F_PRE = new MissileRack("SEEKER_MISSILE_RACK_3_A_F_PRE", HorizonsBlueprintName.SEEKER_MISSILE_RACK_PRE, ModuleSize.SIZE_3, ModuleClass.A, true, Mounting.FIXED, 0, "Hpt_BasicMissileRack_Fixed_Large",                                                                      Map.ofEntries(Map.entry(HorizonsModifier.MASS, 8.00), Map.entry(HorizonsModifier.INTEGRITY, 64.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.62), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 13.333), Map.entry(HorizonsModifier.DAMAGE, 40.000), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.240), Map.entry(HorizonsModifier.THERMAL_LOAD, 3.60), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0), Map.entry(HorizonsModifier.SHOT_SPEED, 625.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 0.333), Map.entry(HorizonsModifier.BURST_INTERVAL, 3.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 6.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 36.0),                                                       Map.entry(HorizonsModifier.RELOAD_TIME, 12.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.4), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 0.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 0.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 1.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.0), Map.entry(HorizonsModifier.AMMO_COST, 500.0), Map.entry(HorizonsModifier.JITTER, 0.0), Map.entry(HorizonsModifier.SHIELDED_TARGET_HEAT_INCREASED, false), Map.entry(HorizonsModifier.TARGET_SIGNATURE_INCREASED, false), Map.entry(HorizonsModifier.DAMAGE_PARTIALLY_THERMAL, false), Map.entry(HorizonsModifier.TARGET_FSD_REBOOTS, false), Map.entry(HorizonsModifier.TARGET_SPEED_REDUCED, false)));

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

    public static final List<MissileRack> MISSILE_RACKS = List.of(
            MISSILE_RACK_1_B_F,
            SEEKER_MISSILE_RACK_1_B_F,
            ADVANCED_MISSILE_RACK_1_B_F,
            MISSILE_RACK_2_B_F,
            SEEKER_MISSILE_RACK_2_B_F,
            ADVANCED_MISSILE_RACK_2_B_F,
            MISSILE_RACK_3_A_F,
            SEEKER_MISSILE_RACK_3_A_F,
            PACK_HOUND_MISSILE_RACK_2_B_F,
            ROCKET_PROPELLED_FSD_DISRUPTER_2_B_F,
            SEEKER_MISSILE_RACK_2_B_F_PRE,
            SEEKER_MISSILE_RACK_2_B_F_PRE_2,
            SEEKER_MISSILE_RACK_3_A_F_PRE
    );

    public MissileRack(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public MissileRack(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public MissileRack(MissileRack missileRack) {
        super(missileRack);
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedBlueprints() {
        if (isPreEngineered()) {
            return Collections.emptyList();
        }
        return MissileRackBlueprints.BLUEPRINTS.keySet().stream().toList();
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedExperimentalEffects() {
        if (isPreEngineered()) {
            return Collections.emptyList();
        }
        if (this.isMultiCrew()) {//seeker, pack-hound
            return ExperimentalEffectBlueprints.MISSILE_RACK.keySet().stream().filter(horizonsBlueprintType -> !HorizonsBlueprintType.FSD_INTERRUPT.equals(horizonsBlueprintType) && !HorizonsBlueprintType.PENETRATOR_MUNITIONS.equals(horizonsBlueprintType)).toList();
        }//missile, advanced, disruptor
        return ExperimentalEffectBlueprints.MISSILE_RACK.keySet().stream().filter(horizonsBlueprintType -> !HorizonsBlueprintType.DRAG_MUNITION.equals(horizonsBlueprintType)).toList();
    }

    @Override
    public MissileRack Clone() {
        return new MissileRack(this);
    }


    @Override
    public boolean isPreEngineered() {
        return SEEKER_MISSILE_RACK_2_B_F_PRE.equals(this) || SEEKER_MISSILE_RACK_2_B_F_PRE_2.equals(this) || SEEKER_MISSILE_RACK_3_A_F_PRE.equals(this);
    }

    @Override
    public boolean isAdvanced() {
        return ADVANCED_MISSILE_RACK_1_B_F.equals(this) || ADVANCED_MISSILE_RACK_2_B_F.equals(this);
    }

    public boolean isDumbfire() {
        return MISSILE_RACK_1_B_F.equals(this) || MISSILE_RACK_2_B_F.equals(this) || MISSILE_RACK_3_A_F.equals(this);
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
        if (ROCKET_PROPELLED_FSD_DISRUPTER_2_B_F.equals(this)) {
            return " " + LocaleService.getLocalizedStringForCurrentLocale(getName().getLocalizationKey() + ".short");
        }
        return super.getClarifier();
    }

    @Override
    public int getGrouping() {
        return switch (getId()) {
            case "MISSILE_RACK_1_B_F", "SEEKER_MISSILE_RACK_1_B_F", "ADVANCED_MISSILE_RACK_1_B_F" -> 1;
            case "MISSILE_RACK_2_B_F", "SEEKER_MISSILE_RACK_2_B_F", "ADVANCED_MISSILE_RACK_2_B_F" -> 3;
            case "SEEKER_MISSILE_RACK_2_B_F_PRE", "SEEKER_MISSILE_RACK_2_B_F_PRE_2" -> 4;
            case "PACK_HOUND_MISSILE_RACK_2_B_F" -> 5;
            case "ROCKET_PROPELLED_FSD_DISRUPTER_2_B_F" -> 6;
            case "MISSILE_RACK_3_A_F", "SEEKER_MISSILE_RACK_3_A_F" -> 7;
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
}
