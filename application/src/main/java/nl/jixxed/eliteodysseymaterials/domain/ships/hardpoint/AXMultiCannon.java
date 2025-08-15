package nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint;

import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsTechBrokerBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class AXMultiCannon extends HardpointModule {

    public static final AXMultiCannon AX_MULTI_CANNON_2_E_F             = new AXMultiCannon("AX_MULTI_CANNON_2_E_F", HorizonsBlueprintName.AX_MULTI_CANNON, ModuleSize.SIZE_2, ModuleClass.E, false, Mounting.FIXED, 379000, "Hpt_ATMultiCannon_Fixed_Medium",                          Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.00), Map.entry(HorizonsModifier.INTEGRITY, 51.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.46), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 9.6), Map.entry(HorizonsModifier.DAMAGE, 1.3), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.110), Map.entry(HorizonsModifier.THERMAL_LOAD, 0.18), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 17.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE, 4000.0), Map.entry(HorizonsModifier.SHOT_SPEED, 1600.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 7.143), Map.entry(HorizonsModifier.BURST_INTERVAL, 0.140), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 100.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 2100.0), Map.entry(HorizonsModifier.RELOAD_TIME, 4.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.85), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 0.50), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 0.80), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.666), Map.entry(HorizonsModifier.KINETIC_DAMAGE_RATIO, 0.334), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START, 2000.0), Map.entry(HorizonsModifier.AMMO_COST, 1.0)));
    public static final AXMultiCannon AX_MULTI_CANNON_2_F_T             = new AXMultiCannon("AX_MULTI_CANNON_2_F_T", HorizonsBlueprintName.AX_MULTI_CANNON, ModuleSize.SIZE_2, ModuleClass.F, true, Mounting.TURRETED, 1826500, "Hpt_ATMultiCannon_Turret_Medium",                      Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.00), Map.entry(HorizonsModifier.INTEGRITY, 51.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.50), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 4.2), Map.entry(HorizonsModifier.DAMAGE, 0.7), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.060), Map.entry(HorizonsModifier.THERMAL_LOAD, 0.09), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 17.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE, 4000.0), Map.entry(HorizonsModifier.SHOT_SPEED, 1600.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 6.250), Map.entry(HorizonsModifier.BURST_INTERVAL, 0.160), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 90.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 2100.0), Map.entry(HorizonsModifier.RELOAD_TIME, 4.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.25), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 0.50), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 0.50), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.666), Map.entry(HorizonsModifier.KINETIC_DAMAGE_RATIO, 0.334), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START, 2000.0), Map.entry(HorizonsModifier.AMMO_COST, 1.0)));
    public static final AXMultiCannon AX_MULTI_CANNON_3_C_F             = new AXMultiCannon("AX_MULTI_CANNON_3_C_F", HorizonsBlueprintName.AX_MULTI_CANNON, ModuleSize.SIZE_3, ModuleClass.C, false, Mounting.FIXED, 1181500, "Hpt_ATMultiCannon_Fixed_Large",                          Map.ofEntries(Map.entry(HorizonsModifier.MASS, 8.00), Map.entry(HorizonsModifier.INTEGRITY, 64.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.64), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 15.2), Map.entry(HorizonsModifier.DAMAGE, 2.6), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.180), Map.entry(HorizonsModifier.THERMAL_LOAD, 0.28), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 33.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE, 4000.0), Map.entry(HorizonsModifier.SHOT_SPEED, 1600.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 5.882), Map.entry(HorizonsModifier.BURST_INTERVAL, 0.170), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 100.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 2100.0), Map.entry(HorizonsModifier.RELOAD_TIME, 4.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.85), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 0.50), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 0.80), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.666), Map.entry(HorizonsModifier.KINETIC_DAMAGE_RATIO, 0.334), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START, 2000.0), Map.entry(HorizonsModifier.AMMO_COST, 1.0)));
    public static final AXMultiCannon AX_MULTI_CANNON_3_E_T             = new AXMultiCannon("AX_MULTI_CANNON_3_E_T", HorizonsBlueprintName.AX_MULTI_CANNON, ModuleSize.SIZE_3, ModuleClass.E, true, Mounting.TURRETED, 3821600, "Hpt_ATMultiCannon_Turret_Large",                       Map.ofEntries(Map.entry(HorizonsModifier.MASS, 8.00), Map.entry(HorizonsModifier.INTEGRITY, 64.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.64), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 8.4), Map.entry(HorizonsModifier.DAMAGE, 1.3), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.060), Map.entry(HorizonsModifier.THERMAL_LOAD, 0.09), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 33.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE, 4000.0), Map.entry(HorizonsModifier.SHOT_SPEED, 1600.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 6.250), Map.entry(HorizonsModifier.BURST_INTERVAL, 0.160), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 90.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 2100.0), Map.entry(HorizonsModifier.RELOAD_TIME, 4.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.25), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 0.50), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 0.50), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.666), Map.entry(HorizonsModifier.KINETIC_DAMAGE_RATIO, 0.334), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START, 2000.0), Map.entry(HorizonsModifier.AMMO_COST, 1.0)));
    public static final AXMultiCannon ENHANCED_AX_MULTI_CANNON_2_D_F    = new AXMultiCannon("ENHANCED_AX_MULTI_CANNON_2_D_F", HorizonsBlueprintName.ENHANCED_AX_MULTI_CANNON, ModuleSize.SIZE_2, ModuleClass.D, false, Mounting.FIXED, 455080, "Hpt_ATMultiCannon_Fixed_Medium_V2",     Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.00), Map.entry(HorizonsModifier.INTEGRITY, 51.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.48), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 10.0), Map.entry(HorizonsModifier.DAMAGE, 1.4), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.11), Map.entry(HorizonsModifier.THERMAL_LOAD, 0.2), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 17.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE, 4000.0), Map.entry(HorizonsModifier.SHOT_SPEED, 4000.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 7.1), Map.entry(HorizonsModifier.BURST_INTERVAL, 0.14), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 100.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 2100.0), Map.entry(HorizonsModifier.RELOAD_TIME, 4.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.85), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 0.50), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 0.80), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.666), Map.entry(HorizonsModifier.KINETIC_DAMAGE_RATIO, 0.334), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START, 2000.0), Map.entry(HorizonsModifier.AMMO_COST, 1.0)));
    public static final AXMultiCannon ENHANCED_AX_MULTI_CANNON_2_E_G    = new AXMultiCannon("ENHANCED_AX_MULTI_CANNON_2_E_G", HorizonsBlueprintName.ENHANCED_AX_MULTI_CANNON, ModuleSize.SIZE_2, ModuleClass.E, false, Mounting.GIMBALLED, 1197640, "Hpt_ATMultiCannon_Gimbal_Medium",  Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.00), Map.entry(HorizonsModifier.INTEGRITY, 51.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.46), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 9.6), Map.entry(HorizonsModifier.DAMAGE, 1.3), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.11), Map.entry(HorizonsModifier.THERMAL_LOAD, 0.2), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 17.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE, 4000.0), Map.entry(HorizonsModifier.SHOT_SPEED, 4000.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 7.1), Map.entry(HorizonsModifier.BURST_INTERVAL, 0.14), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 100.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 2100.0), Map.entry(HorizonsModifier.RELOAD_TIME, 4.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.85), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 0.50), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 0.80), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.666), Map.entry(HorizonsModifier.KINETIC_DAMAGE_RATIO, 0.334), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START, 2000.0), Map.entry(HorizonsModifier.AMMO_COST, 1.0)));
    public static final AXMultiCannon ENHANCED_AX_MULTI_CANNON_2_E_T    = new AXMultiCannon("ENHANCED_AX_MULTI_CANNON_2_E_T", HorizonsBlueprintName.ENHANCED_AX_MULTI_CANNON, ModuleSize.SIZE_2, ModuleClass.E, true, Mounting.TURRETED, 2193300, "Hpt_ATMultiCannon_Turret_Medium_V2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.00), Map.entry(HorizonsModifier.INTEGRITY, 51.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.52), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 4.4), Map.entry(HorizonsModifier.DAMAGE, 0.7), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.06), Map.entry(HorizonsModifier.THERMAL_LOAD, 0.1), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 17.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE, 4000.0), Map.entry(HorizonsModifier.SHOT_SPEED, 4000.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 6.2), Map.entry(HorizonsModifier.BURST_INTERVAL, 0.16), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 90.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 2100.0), Map.entry(HorizonsModifier.RELOAD_TIME, 4.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.25), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 0.50), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 0.50), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.666), Map.entry(HorizonsModifier.KINETIC_DAMAGE_RATIO, 0.334), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START, 2000.0), Map.entry(HorizonsModifier.AMMO_COST, 1.0)));
    public static final AXMultiCannon ENHANCED_AX_MULTI_CANNON_3_B_F    = new AXMultiCannon("ENHANCED_AX_MULTI_CANNON_3_B_F", HorizonsBlueprintName.ENHANCED_AX_MULTI_CANNON, ModuleSize.SIZE_3, ModuleClass.B, false, Mounting.FIXED, 1360320, "Hpt_ATMultiCannon_Fixed_Large_V2",     Map.ofEntries(Map.entry(HorizonsModifier.MASS, 8.00), Map.entry(HorizonsModifier.INTEGRITY, 64.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.69), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 15.6), Map.entry(HorizonsModifier.DAMAGE, 2.7), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.18), Map.entry(HorizonsModifier.THERMAL_LOAD, 0.3), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 33.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE, 4000.0), Map.entry(HorizonsModifier.SHOT_SPEED, 4000.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 5.9), Map.entry(HorizonsModifier.BURST_INTERVAL, 0.17), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 100.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 2100.0), Map.entry(HorizonsModifier.RELOAD_TIME, 4.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.85), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 0.50), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 0.80), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.666), Map.entry(HorizonsModifier.KINETIC_DAMAGE_RATIO, 0.334), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START, 2000.0), Map.entry(HorizonsModifier.AMMO_COST, 1.0)));
    public static final AXMultiCannon ENHANCED_AX_MULTI_CANNON_3_C_G    = new AXMultiCannon("ENHANCED_AX_MULTI_CANNON_3_C_G", HorizonsBlueprintName.ENHANCED_AX_MULTI_CANNON, ModuleSize.SIZE_3, ModuleClass.C, false, Mounting.GIMBALLED, 2390460, "Hpt_ATMultiCannon_Gimbal_Large",   Map.ofEntries(Map.entry(HorizonsModifier.MASS, 8.00), Map.entry(HorizonsModifier.INTEGRITY, 64.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.64), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 15.2), Map.entry(HorizonsModifier.DAMAGE, 2.6), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.18), Map.entry(HorizonsModifier.THERMAL_LOAD, 0.3), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 33.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE, 4000.0), Map.entry(HorizonsModifier.SHOT_SPEED, 4000.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 5.9), Map.entry(HorizonsModifier.BURST_INTERVAL, 0.17), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 100.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 2100.0), Map.entry(HorizonsModifier.RELOAD_TIME, 4.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.85), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 0.50), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 0.80), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.666), Map.entry(HorizonsModifier.KINETIC_DAMAGE_RATIO, 0.334), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START, 2000.0), Map.entry(HorizonsModifier.AMMO_COST, 1.0)));
    public static final AXMultiCannon ENHANCED_AX_MULTI_CANNON_3_D_T    = new AXMultiCannon("ENHANCED_AX_MULTI_CANNON_3_D_T", HorizonsBlueprintName.ENHANCED_AX_MULTI_CANNON, ModuleSize.SIZE_3, ModuleClass.D, true, Mounting.TURRETED, 4588710, "Hpt_ATMultiCannon_Turret_Large_V2",  Map.ofEntries(Map.entry(HorizonsModifier.MASS, 8.00), Map.entry(HorizonsModifier.INTEGRITY, 64.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.69), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 8.7), Map.entry(HorizonsModifier.DAMAGE, 1.4), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.06), Map.entry(HorizonsModifier.THERMAL_LOAD, 0.1), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 33.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE, 4000.0), Map.entry(HorizonsModifier.SHOT_SPEED, 4000.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 6.2), Map.entry(HorizonsModifier.BURST_INTERVAL, 0.16), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 90.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 2100.0), Map.entry(HorizonsModifier.RELOAD_TIME, 4.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.25), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 0.50), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 0.50), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.666), Map.entry(HorizonsModifier.KINETIC_DAMAGE_RATIO, 0.334), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START, 2000.0), Map.entry(HorizonsModifier.AMMO_COST, 1.0)));
    public static final AXMultiCannon AX_MULTI_CANNON_2_E_F_PRE         = new AXMultiCannon("AX_MULTI_CANNON_2_E_F_PRE", HorizonsBlueprintName.AX_MULTI_CANNON_PRE, ModuleSize.SIZE_2, ModuleClass.E, Origin.AZIMUTH, false, Mounting.GIMBALLED, 0, "Hpt_ATMultiCannon_Gimbal_Medium",  Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.00), Map.entry(HorizonsModifier.INTEGRITY, 51.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.46), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 9.564285), Map.entry(HorizonsModifier.DAMAGE, 1.339000), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.110), Map.entry(HorizonsModifier.THERMAL_LOAD, 0.18), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 17.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE, 4000.0), Map.entry(HorizonsModifier.SHOT_SPEED, 4000.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 7.143), Map.entry(HorizonsModifier.BURST_INTERVAL, 0.140), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 100.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 2100.0), Map.entry(HorizonsModifier.RELOAD_TIME, 4.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.85), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 0.50), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 0.80), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.666), Map.entry(HorizonsModifier.KINETIC_DAMAGE_RATIO, 0.334), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START, 2000.0), Map.entry(HorizonsModifier.AMMO_COST, 1.0)));
    public static final AXMultiCannon AX_MULTI_CANNON_3_C_F_PRE         = new AXMultiCannon("AX_MULTI_CANNON_3_C_F_PRE", HorizonsBlueprintName.AX_MULTI_CANNON_PRE, ModuleSize.SIZE_3, ModuleClass.C, Origin.AZIMUTH, false, Mounting.GIMBALLED, 0, "Hpt_ATMultiCannon_Gimbal_Large",   Map.ofEntries(Map.entry(HorizonsModifier.MASS, 8.00), Map.entry(HorizonsModifier.INTEGRITY, 64.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.64), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 15.191176), Map.entry(HorizonsModifier.DAMAGE, 2.582500), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.180), Map.entry(HorizonsModifier.THERMAL_LOAD, 0.28), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 33.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE, 4000.0), Map.entry(HorizonsModifier.SHOT_SPEED, 4000.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 5.882), Map.entry(HorizonsModifier.BURST_INTERVAL, 0.170), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 100.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 2100.0), Map.entry(HorizonsModifier.RELOAD_TIME, 4.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.85), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 0.50), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 0.80), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.666), Map.entry(HorizonsModifier.KINETIC_DAMAGE_RATIO, 0.334), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START, 2000.0), Map.entry(HorizonsModifier.AMMO_COST, 1.0)));

    static {
        AX_MULTI_CANNON_2_E_F_PRE.getModifications().add(
                new Modification(HorizonsBlueprintType.OVERCHARGED_WEAPON_AUTO_LOADER, 1.0, HorizonsBlueprintGrade.GRADE_5)
        );
        AX_MULTI_CANNON_3_C_F_PRE.getModifications().add(
                new Modification(HorizonsBlueprintType.OVERCHARGED_WEAPON_AUTO_LOADER, 1.0, HorizonsBlueprintGrade.GRADE_5)
        );
    }

    public static final List<AXMultiCannon> AX_MULTI_CANNONS = List.of(
            AX_MULTI_CANNON_2_E_F,
            AX_MULTI_CANNON_2_F_T,
            AX_MULTI_CANNON_3_C_F,
            AX_MULTI_CANNON_3_E_T,
            ENHANCED_AX_MULTI_CANNON_2_D_F,
            ENHANCED_AX_MULTI_CANNON_2_E_G,
            ENHANCED_AX_MULTI_CANNON_2_E_T,
            ENHANCED_AX_MULTI_CANNON_3_B_F,
            ENHANCED_AX_MULTI_CANNON_3_C_G,
            ENHANCED_AX_MULTI_CANNON_3_D_T,
            AX_MULTI_CANNON_2_E_F_PRE,
            AX_MULTI_CANNON_3_C_F_PRE
    );

    public AXMultiCannon(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public AXMultiCannon(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public AXMultiCannon(AXMultiCannon axMultiCannon) {
        super(axMultiCannon);
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
    public AXMultiCannon Clone() {
        return new AXMultiCannon(this);
    }

    @Override
    public boolean isPreEngineered() {
        return AX_MULTI_CANNON_2_E_F_PRE.equals(this) || AX_MULTI_CANNON_3_C_F_PRE.equals(this);
    }

    @Override
    public boolean isEnhanced() {
        return ENHANCED_AX_MULTI_CANNON_2_D_F.equals(this) || ENHANCED_AX_MULTI_CANNON_2_E_G.equals(this) || ENHANCED_AX_MULTI_CANNON_2_E_T.equals(this) || ENHANCED_AX_MULTI_CANNON_3_B_F.equals(this) || ENHANCED_AX_MULTI_CANNON_3_C_G.equals(this) || ENHANCED_AX_MULTI_CANNON_3_D_T.equals(this);
    }

    @Override
    public int getGrouping() {
        return switch (getId()) {
            case "AX_MULTI_CANNON_2_E_F", "AX_MULTI_CANNON_2_F_T", "AX_MULTI_CANNON_2_E_F_PRE" -> 1;
            case "ENHANCED_AX_MULTI_CANNON_2_D_F", "ENHANCED_AX_MULTI_CANNON_2_E_G", "ENHANCED_AX_MULTI_CANNON_2_E_T" ->
                    2;
            case "AX_MULTI_CANNON_3_C_F", "AX_MULTI_CANNON_3_E_T", "AX_MULTI_CANNON_3_C_F_PRE" -> 4;
            case "ENHANCED_AX_MULTI_CANNON_3_B_F", "ENHANCED_AX_MULTI_CANNON_3_C_G", "ENHANCED_AX_MULTI_CANNON_3_D_T" ->
                    5;
            default -> 0;
        };
    }

    @Override
    public int getModuleLimit() {
        return 4;
    }

    @Override
    public HorizonsTechBrokerBlueprint techBrokerBlueprint() {
        if (AX_MULTI_CANNON_2_E_F_PRE.equals(this)){
            return (HorizonsTechBrokerBlueprint) HorizonsBlueprintConstants.getRecipe(HorizonsBlueprintName.HUMAN_WEAPONS, HorizonsBlueprintType.E2_AZIMUTH_ENHANCED_AX_MULTICANNON, null);
        }
        if (AX_MULTI_CANNON_3_C_F_PRE.equals(this)){
            return (HorizonsTechBrokerBlueprint) HorizonsBlueprintConstants.getRecipe(HorizonsBlueprintName.HUMAN_WEAPONS, HorizonsBlueprintType.C3_AZIMUTH_ENHANCED_AX_MULTICANNON, null);
        }
        return super.techBrokerBlueprint();
    }
}
