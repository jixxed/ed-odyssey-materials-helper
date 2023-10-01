package nl.jixxed.eliteodysseymaterials.enums;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum HorizonsModifier {

    TOP_SPEED(""),//ship
    BOOST_SPEED(""),//ship
    SHIELDS(""),//ship
    ARMOUR(""),//ship
    BOOST_COST(""),//ship
    PITCH_SPEED(""),//ship
    YAW_SPEED(""),//ship
    ROLL_SPEED(""),//ship
    MIN_PITCH_SPEED(""),//ship
    HEAT_CAPACITY(""),//ship
    HEAT_DISSIPATION_MIN(""),//ship
    HEAT_DISSIPATION_MAX(""),//ship
    FUEL_COST(""),//ship
    ARMOUR_HARDNESS(""),//ship
    MASS_LOCK(""),//ship
    CREW(""),//ship
    DAMAGE("Damage"),
    THERMAL_LOAD("ThermalLoad"),
    ENGINE_THERMAL_LOAD("EngineHeatRate"),//new
    RELOAD_TIME("ReloadTime"),
    BREACH_DAMAGE("BreachDamage"),//new
    MIN_BREACH_CHANCE("MinBreachChance"),//new
    MAX_BREACH_CHANCE("MaxBreachChance"),//new
    AMMO_MAXIMUM("AmmoMaximum"),
//    AMMO_MAXIMUM("AmmoMaximum"),
    MASS("Mass"),
    ENGINE_OPTIMAL_MASS("EngineOptimalMass"),
    INTEGRITY("Integrity"),
    BOOT_TIME("BootTime"),
    POWER_DRAW("PowerDraw"),
    //edengineer
    MAX_RANGE("MaxRange"),//rename -sensors
    SHIELDBANK_SPIN_UP("ShieldBankSpinUp"),//rename
    SHIELDBANK_HEAT("ShieldBankHeat"),//new
    RATE_OF_FIRE("RateOfFire"),
    SHIELD_BOOST("DefenceModifierShieldMultiplier"),
    BURST_SIZE("BurstSize"),
    BURST_RATE_OF_FIRE("BurstRateOfFire"),
    FSD_INTERDICTOR_FACING_LIMIT("FSDInterdictorFacingLimit"),//rename
    HEATSINK_DURATION("HeatSinkDuration"),
    SHIELDBANK_DURATION("ShieldBankDuration"),//new
    SHIELDBANK_REINFORCEMENT("ShieldBankReinforcement"),//rename
    SHIELDGEN_MINIMUM_STRENGTH("ShieldGenMinStrength"),//rename
    SHIELDGEN_OPTIMAL_STRENGTH("ShieldGenStrength"),//rename
    BROKEN_REGEN_RATE("BrokenRegenRate"),
    SCAN_ANGLE("SensorTargetScanAngle"),
    SCANNER_RANGE("ScannerRange"),//rename
    SCAN_TIME("ScannerTimeToScan"),
    DSS_PATCH_RADIUS("DSS_PatchRadius"),
    MAXIMUM_RANGE("MaximumRange"),
    ALL_RESISTANCES(""),//todo - split in blueprints
    HULL_REINFORCEMENT("DefenceModifierHealthAddition"),
    SHIELD_REINFORCEMENT("DefenceModifierShieldAddition"),//new guardian
    DISTRIBUTOR_DRAW("DistributorDraw"),
    EXPLOSIVE_RESISTANCE("ExplosiveResistance"),
    KINETIC_RESISTANCE("KineticResistance"),
    THERMAL_RESISTANCE("ThermicResistance"),
    HULL_BOOST("DefenceModifierHealthMultiplier"),
    POWER_CAPACITY("PowerCapacity"),
    POWER_RECHARGE(""),//todo remove
    WEAPONS_CAPACITY("WeaponsCapacity"),
    WEAPONS_RECHARGE("WeaponsRecharge"),
    SYSTEMS_CAPACITY("SystemsCapacity"),
    SYSTEMS_RECHARGE("SystemsRecharge"),
    ENGINES_CAPACITY("EnginesCapacity"),
    ENGINES_RECHARGE("EnginesRecharge"),
    HEAT_EFFICIENCY("HeatEfficiency"),
    DAMAGE_FALLOFF_START("DamageFalloffRange"),
    ARMOUR_PIERCING("ArmourPenetration"),
    SHOT_SPEED("ShotSpeed"),
    ROUNDS_PER_SHOT("RoundsPerShot"),//new
    JITTER("Jitter"),
    AMMO_CLIP_SIZE("AmmoClipSize"),
//    AMMO_CLIP_SIZE("AmmoClipSize"),
    DAMAGE_BOOST(""),//synth
    REFILL(""),//synth
    JUMP_RANGE(""),//synth
//    REPAIR_SPEED(""),
    HULL_STRENGTH(""),//synth
    FUEL_EFFICIENCY(""),//synth
    HEAT_DISSIPATION(""),//synth
    LIMPETS(""),//synth
    WING_SHIELD_REGENERATION_INCREASED(""),//bool val
    TARGET_WING_SHIELDS_REGENERATED(""),//bool val
    DAMAGE_INCREASES_WITH_HEAT_LEVEL(""),//bool val
    TARGET_HEAT_INCREASED(""),//bool val
    HEAT_REDUCED_WHEN_STRIKING_A_TARGET(""),//bool val
    DAMAGE_PARTIALLY_KINETIC(""),//bool val
    PART_OF_DAMAGE_THROUGH_SHIELDS(""),//bool val
    TARGET_MODULES_MALFUNCTIONS(""),//bool val
    TARGET_SIGNATURE_INCREASED(""),//bool val
    DAMAGE_PARTIALLY_THERMAL(""),//bool val
    NO_DAMAGE_TO_UNTARGETED_SHIPS(""),//bool val
    AUTO_RELOAD_WHILE_FIRING(""),//bool val
    TARGET_ARMOR_HARDNESS_REDUCED(""),//bool val
    TARGET_PUSHED_OFF_COURSE(""),//bool val
    DAMAGE_PARTIALLY_EXPLOSIVE(""),//bool val
    SHIELDED_TARGET_HEAT_INCREASED(""),//bool val
    TARGET_GIMBAL_TURRET_TRACKING_REDUCED(""),//bool val
    TARGET_SENSOR_ACUITY_REDUCED(""),//bool val
    EFFECTIVENESS_INCREASE_AGAINST_MUNITIONS(""),//bool val
    TARGET_MODULE_DAMAGE(""),//bool val
    TARGET_FSD_REBOOTS(""),//bool val
    TARGET_FSD_INHIBITED(""),//bool val
    TARGET_SHIELD_GENERATOR_DAMAGED(""),//bool val
    TARGET_THRUSTERS_REBOOT(""),//bool val
    AREA_HEAT_INCREASED_SENSORS_DISRUPTED(""),//bool val
    AREA_FSDS_REBOOT(""),//bool val
    TARGET_LOSES_TARGET_LOCK(""),//bool val
    RELOAD_FROM_SHIP_FUEL(""),//bool val
    TARGET_SHIELD_CELL_DISRUPTED(""),//bool val
    TARGET_SPEED_REDUCED(""),//bool val
    REGEN_RATE("RegenRate"),
    ENERGY_PER_REGEN("EnergyPerRegen"),//new
    ANTI_XENO_DAMAGE(""),//bool val
    CHARGE_TIME(""),//todo remove
    DAMAGE_MULTIPLIER_AT_FULL_CHARGE(""),//todo remove
    DAMAGE_PER_SECOND("DamagePerSecond"),
    ECM_ACTIVE_POWER_CONSUMPTION("ECMActivePowerConsumption"),//rename
    ECM_HEAT("ECMHeat"),//new - thermal laod
    ECM_COOLDOWN("ECMCooldown"),//new
    ENGINE_MINIMUM_MASS("EngineMinimumMass"),//rename
    FSD_OPTIMISED_MASS("FSDOptimalMass"),//rename
    FSD_HEAT_RATE("FSDHeatRate"),//new
    MAXIMUM_MASS("MaximumMass"),
    SHIELDGEN_MINIMUM_MASS("ShieldGenMinimumMass"),//new?
    SHIELDGEN_OPTIMAL_MASS("ShieldGenOptimalMass"),//new?
    SHIELDGEN_MAXIMUM_MASS("ShieldGenMaximumMass"),//new?
    OPTIMAL_HULL_MASS(""),//todo remove
    MAXIMUM_HULL_MASS(""),//todo remove
    MINIMUM_MULTIPLIER("EngineMinPerformance"),
    OPTIMAL_MULTIPLIER("EngineOptPerformance"),
    MAXIMUM_MULIPLIER("EngineMaxPerformance"),
    COOLDOWN(""),//todo remove?
    MOUNTING(""),//todo remove?
    MULTICREW(""),//todo remove?
    DAMAGE_TYPE("DamageType"),
    //    DAMAGE_FALLOFF_START(""),
    JAM_DURATION("ChaffJamDuration"),
    ECM_RANGE("ECMRange"),
    ECM_TIME_TO_CHARGE("ECMTimeToCharge"),
    THERMAL_DRAIN("ThermalDrain"),
    MAX_ANGLE("MaxAngle"),
    CAUSTIC_RESISTANCE("CausticResistance"),
    COLLISION_RESISTANCE("CollisionResistance"),//Absolute Resistance - unused?
    MAX_FUEL_PER_JUMP("MaxFuelPerJump"),//rename
    EMERGENCY_LIFE_SUPPORT("OxygenTimeCapacity"),
    TARGET_SCAN_ANGLE(""),//todo remove
    TYPICAL_EMISSION_RANGE("Range"),
    FUEL_CAPACITY("FuelCapacity"),
    REPAIR_CAPACITY("AFMRepairCapacity"),
    CONSUMPTION("AFMRepairConsumption"),
    REPAIR_RATING("AFMRepairPerAmmo"),
    CARGO_CAPACITY("CargoCapacity"),
    MAX_ACTIVE_LIMPETS("MaxActiveDrones"),
    DRONE_REPAIR_CAPACITY("DroneRepairCapacity"),
    DISCOVERY_SCANNER_RANGE("DiscoveryScannerRange"),//rename
    DISCOVERY_SCANNER_PASSIVE_RANGE("DiscoveryScannerPassiveRange"),//new
    DRONE_TARGET_RANGE("DroneTargetRange"),//new
    DRONE_ACTIVE_RANGE(""),
    DRONE_LIFE_TIME("DroneLifeTime"),
    DRONE_SPEED("DroneSpeed"),
    DRONE_MULTI_TARGET_SPEED("DroneMultiTargetSpeed"),
    JUMP_RANGE_INCREASE("FSDJumpRangeBoost"),
    SCOOP_RATE("FuelScoopRate"),
    DRONE_FUEL_CAPACITY("DroneFuelCapacity"),
    DRONE_HACKING_TIME("DroneHackingTime"),
    DRONE_MIN_JETTISONED_CARGO("DroneMinJettisonedCargo"),
    DRONE_MAX_JETTISONED_CARGO("DroneMaxJettisonedCargo"),
    DAMAGE_PROTECTION(""),//todo remove for ModuleDefenceAbsorption
    CABIN_CAPACITY("CabinCapacity"),
    CABIN_CLASS("CabinClass"),
    VEHICLE_SLOTS("NumBuggySlots"),
    VEHICLE_COUNT(""),
    BIN_COUNT("RefineryBins"),
    SHIELDGEN_MAXIMUM_STRENGTH("ShieldGenMaxStrength"),//rename
    POWER_BOOST(""),//might be needed in some calculation, not official?
    //    PROBE_RADIUS
//hidden
    FUEL_MULTIPLIER(""),//might be needed in some calculation, not official?
    FUEL_POWER(""),//might be needed in some calculation, not official?
    FSD_INTERDICTOR_RANGE("FSDInterdictorRange"),
    FUEL_RESERVE(""),//ship
    //todo new
    VEHICLE_CARGO_CAPACITY("VehicleCargoCapacity"),
    VEHICLE_HULL_MASS("VehicleHullMass"),
    VEHICLE_FUEL_CAPACITY("VehicleFuelCapacity"),
    VEHICLE_ARMOUR_HEALTH("VehicleArmourHealth"),
    VEHICLE_SHIELD_HEALTH("VehicleShieldHealth"),
    FIGHTER_MAX_SPEED("FighterMaxSpeed"),
    FIGHTER_BOOST_SPEED("FighterBoostSpeed"),
    FIGHTER_PITCH_RATE("FighterPitchRate"),
    FIGHTER_DPS("FighterDPS"),
    FIGHTER_YAW_RATE("FighterYawRate"),
    FIGHTER_ROLL_RATE("FighterRollRate"),
    DISRUPTION_BARRIER_RANGE("DisruptionBarrierRange"),//xeno
    DISRUPTION_BARRIER_CHARGE_DURATION("DisruptionBarrierChargeDuration"),//xeno
    DISRUPTION_BARRIER_ACTIVE_POWER("DisruptionBarrierActivePower"),//xeno
    DISRUPTION_BARRIER_COOLDOWN("DisruptionBarrierCooldown"),//xeno
    WING_DAMAGE_REDUCTION("WingDamageReduction"),
    WING_MIN_DURATION("WingMinDuration"),
    WING_MAX_DURATION("WingMaxDuration"),
    SHIELD_SACRIFICE_AMOUNT_REMOVED("ShieldSacrificeAmountRemoved"),
    SHIELD_SACRIFICE_AMOUNT_GIVEN("ShieldSacrificeAmountGiven"),
    FSD_FUEL_USE_INCREASE("FSDFuelUseIncrease"),
    BOOST_SPEED_MULTIPLIER("BoostSpeedMultiplier"),
    BOOST_AUGMENTER_POWER_USE("BoostAugmenterPowerUse"),
    MODULE_DEFENCE_ABSORPTION("ModuleDefenceAbsorption"),//module reinf
    DSS_RANGE_MULT("DSS_RangeMult"),// TODO: delete?
    DSS_ANGLE_MULT("DSS_AngleMult"),// TODO: delete?
    DSS_RATE_MULT("DSS_RateMult"),// TODO: delete?
    WEAPON_MODE("WeaponMode"),// TODO: delete?
    THERMAL_DAMAGE_RATIO(""),
    KINETIC_DAMAGE_RATIO(""),
    EXPLOSIVE_DAMAGE_RATIO(""),
    CAUSTIC_DAMAGE_RATIO(""),
    ABSOLUTE_DAMAGE_RATIO(""),
    ANTI_XENO_DAMAGE_RATIO(""),
    BREACH_CHANCE_MIN(""),
    BREACH_CHANCE_MAX(""),
    BURST_INTERVAL(""),
    AMMO_COST(""),
    DURATION(""),
    DAMAGE_MULTIPLIER(""),
    MINE_BONUS(""),
    ;
    private final String internalName;

    public static HorizonsModifier forName(final String name) {
        try {
            return HorizonsModifier.valueOf(name.toUpperCase());
        } catch (final IllegalArgumentException ex) {
            return null;
        }
    }

    public static HorizonsModifier forInternalName(String internalName) {
        return Arrays.stream(HorizonsModifier.values())
                .filter(horizonsModifier -> horizonsModifier.internalName.equalsIgnoreCase(internalName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(internalName + " unknown"));
    }

    public String getLocalizationKey() {
        return "modifier.horizons.name." + this.name().toLowerCase();
    }
}
