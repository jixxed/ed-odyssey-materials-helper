package nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint;

import nl.jixxed.eliteodysseymaterials.constants.horizons.hardpoints.GuardianShardCannonBlueprints;
import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GuardianShardCannon extends HardpointModule {
    public static final GuardianShardCannon GUARDIAN_SHARD_CANNON_1_D_F = new GuardianShardCannon("GUARDIAN_SHARD_CANNON_1_D_F", HorizonsBlueprintName.GUARDIAN_SHARD_CANNON, ModuleSize.SIZE_1, ModuleClass.D, Origin.GUARDIAN, false, Mounting.FIXED, 151650, "Hpt_Guardian_ShardCannon_Fixed_Small", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.0), Map.entry(HorizonsModifier.INTEGRITY,  34.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.87), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  40.0), Map.entry(HorizonsModifier.DAMAGE,  2.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  0.42), Map.entry(HorizonsModifier.THERMAL_LOAD,  0.69), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  30.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  1700.0), Map.entry(HorizonsModifier.SHOT_SPEED,  1133.333374), Map.entry(HorizonsModifier.RATE_OF_FIRE,  1.667), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.6), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  5.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  180.0), Map.entry(HorizonsModifier.ROUNDS_PER_SHOT,  12.0), Map.entry(HorizonsModifier.RELOAD_TIME,  5.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  2.9), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.60), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.80), Map.entry(HorizonsModifier.JITTER,  5.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  1700.0), Map.entry(HorizonsModifier.AMMO_COST,  9.0), Map.entry(HorizonsModifier.ANTI_GUARDIAN_ZONE_RESISTANCE,  false)));
    public static final GuardianShardCannon GUARDIAN_SHARD_CANNON_1_F_T = new GuardianShardCannon("GUARDIAN_SHARD_CANNON_1_F_T", HorizonsBlueprintName.GUARDIAN_SHARD_CANNON, ModuleSize.SIZE_1, ModuleClass.F, Origin.GUARDIAN, true, Mounting.TURRETED, 502000, "Hpt_Guardian_ShardCannon_Turret_Small", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.0), Map.entry(HorizonsModifier.INTEGRITY,  34.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.72), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  22.2), Map.entry(HorizonsModifier.DAMAGE,  1.1), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  0.36), Map.entry(HorizonsModifier.THERMAL_LOAD,  0.58), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  30.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  1700.0), Map.entry(HorizonsModifier.SHOT_SPEED,  1133.333374), Map.entry(HorizonsModifier.RATE_OF_FIRE,  1.667), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.6), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  5.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  180.0), Map.entry(HorizonsModifier.ROUNDS_PER_SHOT,  12.0), Map.entry(HorizonsModifier.RELOAD_TIME,  5.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  1.6), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.60), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.80), Map.entry(HorizonsModifier.JITTER,  5.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  1700.0), Map.entry(HorizonsModifier.AMMO_COST,  9.0), Map.entry(HorizonsModifier.ANTI_GUARDIAN_ZONE_RESISTANCE,  false)));
    public static final GuardianShardCannon GUARDIAN_SHARD_CANNON_2_A_F = new GuardianShardCannon("GUARDIAN_SHARD_CANNON_2_A_F", HorizonsBlueprintName.GUARDIAN_SHARD_CANNON, ModuleSize.SIZE_2, ModuleClass.A, Origin.GUARDIAN, false, Mounting.FIXED, 507760, "Hpt_Guardian_ShardCannon_Fixed_Medium", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  4.0), Map.entry(HorizonsModifier.INTEGRITY,  42.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.21), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  74.5), Map.entry(HorizonsModifier.DAMAGE,  3.7), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  0.65), Map.entry(HorizonsModifier.THERMAL_LOAD,  1.2), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  45.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  1700.0), Map.entry(HorizonsModifier.SHOT_SPEED,  1133.333374), Map.entry(HorizonsModifier.RATE_OF_FIRE,  1.667), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.6), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  5.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  180.0), Map.entry(HorizonsModifier.ROUNDS_PER_SHOT,  12.0), Map.entry(HorizonsModifier.RELOAD_TIME,  5.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  5.4), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.60), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.80), Map.entry(HorizonsModifier.JITTER,  5.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  1700.0), Map.entry(HorizonsModifier.AMMO_COST,  9.0), Map.entry(HorizonsModifier.ANTI_GUARDIAN_ZONE_RESISTANCE,  false)));
    public static final GuardianShardCannon GUARDIAN_SHARD_CANNON_2_D_T = new GuardianShardCannon("GUARDIAN_SHARD_CANNON_2_A_T", HorizonsBlueprintName.GUARDIAN_SHARD_CANNON, ModuleSize.SIZE_2, ModuleClass.D, Origin.GUARDIAN, true, Mounting.TURRETED, 1767000, "Hpt_Guardian_ShardCannon_Turret_Medium", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  4.0), Map.entry(HorizonsModifier.INTEGRITY,  42.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.16), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  47.4), Map.entry(HorizonsModifier.DAMAGE, 2.4), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  0.57), Map.entry(HorizonsModifier.THERMAL_LOAD,  1.09), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  45.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  1700.0), Map.entry(HorizonsModifier.SHOT_SPEED,  1133.333374), Map.entry(HorizonsModifier.RATE_OF_FIRE,  1.667), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.6), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  5.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  180.0), Map.entry(HorizonsModifier.ROUNDS_PER_SHOT,  12.0), Map.entry(HorizonsModifier.RELOAD_TIME,  5.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  3.5), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.60), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.80), Map.entry(HorizonsModifier.JITTER,  5.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  1700.0), Map.entry(HorizonsModifier.AMMO_COST,  9.0), Map.entry(HorizonsModifier.ANTI_GUARDIAN_ZONE_RESISTANCE,  false)));
    public static final GuardianShardCannon GUARDIAN_SHARD_CANNON_3_C_F = new GuardianShardCannon("GUARDIAN_SHARD_CANNON_3_C_F", HorizonsBlueprintName.GUARDIAN_SHARD_CANNON, ModuleSize.SIZE_3, ModuleClass.C, Origin.GUARDIAN, false, Mounting.FIXED, 1461350, "Hpt_Guardian_ShardCannon_Fixed_Large", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  8.0), Map.entry(HorizonsModifier.INTEGRITY,  51.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.68), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  104.5), Map.entry(HorizonsModifier.DAMAGE,  5.2), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  1.4), Map.entry(HorizonsModifier.THERMAL_LOAD,  2.2), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  60.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  1700.0), Map.entry(HorizonsModifier.SHOT_SPEED,  1133.333374), Map.entry(HorizonsModifier.RATE_OF_FIRE,  1.667), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.6), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  5.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  180.0), Map.entry(HorizonsModifier.ROUNDS_PER_SHOT,  12.0), Map.entry(HorizonsModifier.RELOAD_TIME,  5.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  7.6), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.60), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.80), Map.entry(HorizonsModifier.JITTER,  5.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  1700.0), Map.entry(HorizonsModifier.AMMO_COST,  9.0), Map.entry(HorizonsModifier.ANTI_GUARDIAN_ZONE_RESISTANCE,  false)));
    public static final GuardianShardCannon GUARDIAN_SHARD_CANNON_3_D_T = new GuardianShardCannon("GUARDIAN_SHARD_CANNON_3_D_T", HorizonsBlueprintName.GUARDIAN_SHARD_CANNON, ModuleSize.SIZE_3, ModuleClass.D, Origin.GUARDIAN, true, Mounting.TURRETED, 5865025, "Hpt_Guardian_ShardCannon_Turret_Large", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  8.0), Map.entry(HorizonsModifier.INTEGRITY,  51.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.39), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  68.2), Map.entry(HorizonsModifier.DAMAGE,  3.4), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  1.2), Map.entry(HorizonsModifier.THERMAL_LOAD,  1.98), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  60.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  1700.0), Map.entry(HorizonsModifier.SHOT_SPEED,  1133.333374), Map.entry(HorizonsModifier.RATE_OF_FIRE,  1.667), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.6), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  5.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  180.0), Map.entry(HorizonsModifier.ROUNDS_PER_SHOT,  12.0), Map.entry(HorizonsModifier.RELOAD_TIME,  5.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  5.0), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.60), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.80), Map.entry(HorizonsModifier.JITTER,  5.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  1700.0), Map.entry(HorizonsModifier.AMMO_COST,  9.0), Map.entry(HorizonsModifier.ANTI_GUARDIAN_ZONE_RESISTANCE,  false)));
    public static final GuardianShardCannon GUARDIAN_SHARD_CANNON_1_D_F_PRE = new GuardianShardCannon("GUARDIAN_SHARD_CANNON_1_D_F_PRE", HorizonsBlueprintName.GUARDIAN_SHARD_CANNON_PRE, ModuleSize.SIZE_1, ModuleClass.D, Origin.GUARDIAN, false, Mounting.FIXED, 0, "Hpt_Guardian_ShardCannon_Fixed_Small", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.0), Map.entry(HorizonsModifier.INTEGRITY,  34.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.87), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  72.8), Map.entry(HorizonsModifier.DAMAGE,  3.64), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  0.42), Map.entry(HorizonsModifier.THERMAL_LOAD,  0.69), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  30.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  1700.0), Map.entry(HorizonsModifier.SHOT_SPEED,  1133.333374), Map.entry(HorizonsModifier.RATE_OF_FIRE,  1.667), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.6), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  5.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  180.0), Map.entry(HorizonsModifier.ROUNDS_PER_SHOT,  12.0), Map.entry(HorizonsModifier.RELOAD_TIME,  5.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  2.9), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.60), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.80), Map.entry(HorizonsModifier.JITTER,  5.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  1700.0), Map.entry(HorizonsModifier.AMMO_COST,  9.0), Map.entry(HorizonsModifier.ANTI_GUARDIAN_ZONE_RESISTANCE,  false)));
    public static final GuardianShardCannon GUARDIAN_SHARD_CANNON_2_A_F_PRE = new GuardianShardCannon("GUARDIAN_SHARD_CANNON_2_A_F_PRE", HorizonsBlueprintName.GUARDIAN_SHARD_CANNON_PRE, ModuleSize.SIZE_2, ModuleClass.A, Origin.GUARDIAN, false, Mounting.FIXED, 0, "Hpt_Guardian_ShardCannon_Fixed_Medium", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  4.0), Map.entry(HorizonsModifier.INTEGRITY,  42.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.21), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  135.4), Map.entry(HorizonsModifier.DAMAGE,  6.77), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  0.65), Map.entry(HorizonsModifier.THERMAL_LOAD,  1.2), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  45.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  1700.0), Map.entry(HorizonsModifier.SHOT_SPEED,  1133.333374), Map.entry(HorizonsModifier.RATE_OF_FIRE,  1.667), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.6), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  5.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  180.0), Map.entry(HorizonsModifier.ROUNDS_PER_SHOT,  12.0), Map.entry(HorizonsModifier.RELOAD_TIME,  5.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  5.4), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.60), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.80), Map.entry(HorizonsModifier.JITTER,  5.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  1700.0), Map.entry(HorizonsModifier.AMMO_COST,  9.0), Map.entry(HorizonsModifier.ANTI_GUARDIAN_ZONE_RESISTANCE,  false)));
    static {
        GUARDIAN_SHARD_CANNON_1_D_F_PRE.getModifications().add(
                new Modification(HorizonsBlueprintType.LONG_RANGE_WEAPON_FOCUSED_WEAPON_PENETRATOR_MUNITIONS, 1.0, HorizonsBlueprintGrade.GRADE_5)
        );
        GUARDIAN_SHARD_CANNON_2_A_F_PRE.getModifications().add(
                new Modification(HorizonsBlueprintType.LONG_RANGE_WEAPON_FOCUSED_WEAPON_PENETRATOR_MUNITIONS, 1.0, HorizonsBlueprintGrade.GRADE_5)
        );
    }
    public static final List<GuardianShardCannon> GUARDIAN_SHARD_CANNONS = List.of(
        GUARDIAN_SHARD_CANNON_1_D_F,
        GUARDIAN_SHARD_CANNON_1_F_T,
        GUARDIAN_SHARD_CANNON_2_A_F,
        GUARDIAN_SHARD_CANNON_2_D_T,
        GUARDIAN_SHARD_CANNON_3_C_F,
        GUARDIAN_SHARD_CANNON_3_D_T,
        GUARDIAN_SHARD_CANNON_1_D_F_PRE,
        GUARDIAN_SHARD_CANNON_2_A_F_PRE
    );
    public GuardianShardCannon(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public GuardianShardCannon(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public GuardianShardCannon(GuardianShardCannon guardianShardCannon) {
        super(guardianShardCannon);
    }


    @Override
    public List<HorizonsBlueprintType> getAllowedBlueprints() {
        if (isPreEngineered()) {
            return Collections.emptyList();
        }
        return GuardianShardCannonBlueprints.BLUEPRINTS.keySet().stream().toList();
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedExperimentalEffects() {
        return Collections.emptyList();
    }

    @Override
    public GuardianShardCannon Clone() {
        return new GuardianShardCannon(this);
    }

    @Override
    public boolean isPreEngineered() {
        return HorizonsBlueprintName.GUARDIAN_SHARD_CANNON_PRE.equals(this.getName());
    }
    @Override
    public int getGrouping() {
        return switch (getId()) {
            case "GUARDIAN_SHARD_CANNON_1_D_F", "GUARDIAN_SHARD_CANNON_1_F_T" -> 1;
            case "GUARDIAN_SHARD_CANNON_1_D_F_PRE" -> 2;
            case "GUARDIAN_SHARD_CANNON_2_A_F", "GUARDIAN_SHARD_CANNON_2_A_T" -> 3;
            case "GUARDIAN_SHARD_CANNON_2_A_F_PRE" -> 4;
            case "GUARDIAN_SHARD_CANNON_3_C_F", "GUARDIAN_SHARD_CANNON_3_D_T" -> 5;
            default -> 0;
        };
    }
}
