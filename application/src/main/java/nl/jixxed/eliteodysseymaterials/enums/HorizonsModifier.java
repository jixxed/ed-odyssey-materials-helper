package nl.jixxed.eliteodysseymaterials.enums;

import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.constants.UTF8Constants;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.text.NumberFormat;
import java.util.Arrays;

@RequiredArgsConstructor
public enum HorizonsModifier {

    ABSOLUTE_DAMAGE_RATIO(""),
    AMMO_CLIP_SIZE("AmmoClipSize"),
    AMMO_COST(""),
    AMMO_MAXIMUM("AmmoMaximum"),
    ANTI_XENO_DAMAGE(""),//bool val
    ANTI_XENO_DAMAGE_RATIO(""),
    AREA_FSDS_REBOOT(""),//bool val
    AREA_HEAT_INCREASED_SENSORS_DISRUPTED(""),//bool val
    ARMOUR(""),
    ARMOUR_HARDNESS(""),
    ARMOUR_PIERCING("ArmourPenetration"),
    AUTO_RELOAD_WHILE_FIRING(""),//bool val
    BIN_COUNT("RefineryBins"),
    BOOST_AUGMENTER_POWER_USE("BoostAugmenterPowerUse"),
    BOOST_COST(""),
    BOOST_SPEED(""),
    BOOST_SPEED_MULTIPLIER("BoostSpeedMultiplier"),
    BOOT_TIME("BootTime"),
    BREACH_CHANCE_MAX(""),
    BREACH_CHANCE_MIN(""),
    BREACH_DAMAGE("BreachDamage"),
    BROKEN_REGEN_RATE("BrokenRegenRate"),
    BURST_INTERVAL(""),
    BURST_RATE_OF_FIRE("BurstRateOfFire"),
    BURST_SIZE("BurstSize"),
    CABIN_CAPACITY("CabinCapacity"),
    CABIN_CLASS("CabinClass"),
    CARGO_CAPACITY("CargoCapacity"),
    CAUSTIC_DAMAGE_RATIO(""),
    CAUSTIC_RESISTANCE("CausticResistance"),
    COLLISION_RESISTANCE("CollisionResistance"),//Absolute Resistance - unused?
    CONSUMPTION("AFMRepairConsumption"),
    CREW(""),
    DAMAGE("Damage"),
    DAMAGE_BOOST(""),//synth
    DAMAGE_FALLOFF_START("DamageFalloffRange"),
    DAMAGE_INCREASES_WITH_HEAT_LEVEL(""),//bool val
    DAMAGE_MULTIPLIER(""),
    DAMAGE_PARTIALLY_EXPLOSIVE(""),//bool val
    DAMAGE_PARTIALLY_KINETIC(""),//bool val
    DAMAGE_PARTIALLY_THERMAL(""),//bool val
    DAMAGE_PER_SECOND("DamagePerSecond"),
    DAMAGE_TYPE("DamageType"),
    DISCOVERY_SCANNER_PASSIVE_RANGE("DiscoveryScannerPassiveRange"),//new
    DISCOVERY_SCANNER_RANGE("DiscoveryScannerRange"),//rename
    DISRUPTION_BARRIER_ACTIVE_POWER("DisruptionBarrierActivePower"),//xeno
    DISRUPTION_BARRIER_CHARGE_DURATION("DisruptionBarrierChargeDuration"),//xeno
    DISRUPTION_BARRIER_COOLDOWN("DisruptionBarrierCooldown"),//xeno
    DISRUPTION_BARRIER_RANGE("DisruptionBarrierRange"),//xeno
    DISTRIBUTOR_DRAW("DistributorDraw"),
    DRONE_ACTIVE_RANGE(""),
    DRONE_FUEL_CAPACITY("DroneFuelCapacity"),
    DRONE_HACKING_TIME("DroneHackingTime"),
    DRONE_LIFE_TIME("DroneLifeTime"),
    DRONE_MAX_JETTISONED_CARGO("DroneMaxJettisonedCargo"),
    DRONE_MIN_JETTISONED_CARGO("DroneMinJettisonedCargo"),
    DRONE_MULTI_TARGET_SPEED("DroneMultiTargetSpeed"),
    DRONE_REPAIR_CAPACITY("DroneRepairCapacity"),
    DRONE_SPEED("DroneSpeed"),
    DRONE_TARGET_RANGE("DroneTargetRange"),//new
    DSS_PATCH_RADIUS("DSS_PatchRadius"),
    DURATION(""),
    ECM_ACTIVE_POWER_CONSUMPTION("ECMActivePowerConsumption"),//rename
    ECM_COOLDOWN("ECMCooldown"),//new
    ECM_HEAT("ECMHeat"),//new - thermal laod
    ECM_RANGE("ECMRange"),
    ECM_TIME_TO_CHARGE("ECMTimeToCharge"),
    EFFECTIVENESS_INCREASE_AGAINST_MUNITIONS(""),//bool val
    EMERGENCY_LIFE_SUPPORT("OxygenTimeCapacity"),
    ENERGY_PER_REGEN("EnergyPerRegen"),//new
    ENGINE_MINIMUM_MASS("EngineMinimumMass"),//rename
    ENGINE_OPTIMAL_MASS("EngineOptimalMass"),
    ENGINE_THERMAL_LOAD("EngineHeatRate"),
    ENGINES_CAPACITY("EnginesCapacity"),
    ENGINES_RECHARGE("EnginesRecharge"),
    EXPLOSIVE_DAMAGE_RATIO(""),
    EXPLOSIVE_RESISTANCE("ExplosiveResistance"),
    FIGHTER_BOOST_SPEED("FighterBoostSpeed"),
    FIGHTER_DPS("FighterDPS"),
    FIGHTER_MAX_SPEED("FighterMaxSpeed"),
    FIGHTER_PITCH_RATE("FighterPitchRate"),
    FIGHTER_ROLL_RATE("FighterRollRate"),
    FIGHTER_YAW_RATE("FighterYawRate"),
    FSD_FUEL_USE_INCREASE("FSDFuelUseIncrease"),
    FSD_HEAT_RATE("FSDHeatRate"),//new
    FSD_INTERDICTOR_FACING_LIMIT("FSDInterdictorFacingLimit"),
    FSD_INTERDICTOR_RANGE("FSDInterdictorRange"),
    FSD_OPTIMISED_MASS("FSDOptimalMass"),//rename
    FUEL_CAPACITY("FuelCapacity"),
    FUEL_COST(""),
    FUEL_EFFICIENCY(""),//synth
    FUEL_MULTIPLIER(""),
    FUEL_POWER(""),
    FUEL_RESERVE(""),//ship
    HEAT_CAPACITY(""),
    HEAT_DISSIPATION(""),//synth
    HEAT_DISSIPATION_MAX(""),
    HEAT_DISSIPATION_MIN(""),
    HEAT_EFFICIENCY("HeatEfficiency"),
    HEAT_REDUCED_WHEN_STRIKING_A_TARGET(""),//bool val
    HEATSINK_DURATION("HeatSinkDuration"),
    HULL_BOOST("DefenceModifierHealthMultiplier"),
    HULL_REINFORCEMENT("DefenceModifierHealthAddition"),
    HULL_STRENGTH(""),//synth
    INTEGRITY("Integrity"),
    JAM_DURATION("ChaffJamDuration"),
    JITTER("Jitter"),//degreem
    JUMP_RANGE(""),//synth
    JUMP_RANGE_INCREASE("FSDJumpRangeBoost"),
    KINETIC_DAMAGE_RATIO(""),
    KINETIC_RESISTANCE("KineticResistance"),
    LIMPETS(""),//synth
    MASS("Mass"),
    MASS_LOCK(""),
    MAX_ACTIVE_LIMPETS("MaxActiveDrones"),
    MAX_ANGLE("MaxAngle"),
    MAX_BREACH_CHANCE("MaxBreachChance"),
    MAX_FUEL_PER_JUMP("MaxFuelPerJump"),//rename
    MAX_RANGE("MaxRange"),
    MAXIMUM_MASS("MaximumMass"),
    MAXIMUM_MULIPLIER("EngineMaxPerformance"),
    MAXIMUM_RANGE("MaximumRange"),
    MIN_BREACH_CHANCE("MinBreachChance"),
    MIN_PITCH_SPEED(""),
    MINE_BONUS(""),
    MINIMUM_MULTIPLIER("EngineMinPerformance"),
    MODULE_DEFENCE_ABSORPTION("ModuleDefenceAbsorption"),//module reinf
    NO_DAMAGE_TO_UNTARGETED_SHIPS(""),//bool val
    OPTIMAL_MULTIPLIER("EngineOptPerformance"),
    PART_OF_DAMAGE_THROUGH_SHIELDS(""),//bool val
    PITCH_SPEED(""),
    POWER_BOOST(""),//might be needed in some calculation, not official?
    POWER_CAPACITY("PowerCapacity"),
    POWER_DRAW("PowerDraw"),
    RATE_OF_FIRE("RateOfFire"),
    REFILL(""),//synth
    REGEN_RATE("RegenRate"),
    RELOAD_FROM_SHIP_FUEL(""),//bool val
    RELOAD_TIME("ReloadTime"),
    REPAIR_CAPACITY("AFMRepairCapacity"),
    REPAIR_RATING("AFMRepairPerAmmo"),
    ROLL_SPEED(""),
    ROUNDS_PER_SHOT("RoundsPerShot"),//new
    SCAN_ANGLE("SensorTargetScanAngle"),
    SCAN_TIME("ScannerTimeToScan"),
    SCANNER_RANGE("ScannerRange"),
    SCOOP_RATE("FuelScoopRate"),
    SHIELD_BOOST("DefenceModifierShieldMultiplier"),
    SHIELD_REINFORCEMENT("DefenceModifierShieldAddition"),
    SHIELD_SACRIFICE_AMOUNT_GIVEN("ShieldSacrificeAmountGiven"),
    SHIELD_SACRIFICE_AMOUNT_REMOVED("ShieldSacrificeAmountRemoved"),
    SHIELDBANK_DURATION("ShieldBankDuration"),
    SHIELDBANK_HEAT("ShieldBankHeat"),
    SHIELDBANK_REINFORCEMENT("ShieldBankReinforcement"),
    SHIELDBANK_SPIN_UP("ShieldBankSpinUp"),
    SHIELDED_TARGET_HEAT_INCREASED(""),//bool val
    SHIELDGEN_MAXIMUM_MASS("ShieldGenMaximumMass"),//new?
    SHIELDGEN_MAXIMUM_STRENGTH("ShieldGenMaxStrength"),//rename
    SHIELDGEN_MINIMUM_MASS("ShieldGenMinimumMass"),//new?
    SHIELDGEN_MINIMUM_STRENGTH("ShieldGenMinStrength"),
    SHIELDGEN_OPTIMAL_MASS("ShieldGenOptimalMass"),//new?
    SHIELDGEN_OPTIMAL_STRENGTH("ShieldGenStrength"),
    SHIELDS(""),
    SHOT_SPEED("ShotSpeed"),
    SYSTEMS_CAPACITY("SystemsCapacity"),
    SYSTEMS_RECHARGE("SystemsRecharge"),
    TARGET_ARMOR_HARDNESS_REDUCED(""),//bool val
    TARGET_FSD_INHIBITED(""),//bool val
    TARGET_FSD_REBOOTS(""),//bool val
    TARGET_GIMBAL_TURRET_TRACKING_REDUCED(""),//bool val
    TARGET_HEAT_INCREASED(""),//bool val
    TARGET_LOSES_TARGET_LOCK(""),//bool val
    TARGET_MODULE_DAMAGE(""),//bool val
    TARGET_MODULES_MALFUNCTIONS(""),//bool val
    TARGET_PUSHED_OFF_COURSE(""),//bool val
    TARGET_SENSOR_ACUITY_REDUCED(""),//bool val
    TARGET_SHIELD_CELL_DISRUPTED(""),//bool val
    TARGET_SHIELD_GENERATOR_DAMAGED(""),//bool val
    TARGET_SIGNATURE_INCREASED(""),//bool val
    TARGET_SPEED_REDUCED(""),//bool val
    TARGET_THRUSTERS_REBOOT(""),//bool val
    TARGET_WING_SHIELDS_REGENERATED(""),//bool val
    THERMAL_DAMAGE_RATIO(""),
    THERMAL_DRAIN("ThermalDrain"),
    THERMAL_LOAD("ThermalLoad"),
    THERMAL_RESISTANCE("ThermicResistance"),
    TOP_SPEED(""),
    TYPICAL_EMISSION_RANGE("Range"),
    VEHICLE_ARMOUR_HEALTH("VehicleArmourHealth"),
    VEHICLE_CARGO_CAPACITY("VehicleCargoCapacity"),
    VEHICLE_COUNT(""),
    VEHICLE_FUEL_CAPACITY("VehicleFuelCapacity"),
    VEHICLE_HULL_MASS("VehicleHullMass"),
    VEHICLE_SHIELD_HEALTH("VehicleShieldHealth"),
    VEHICLE_SLOTS("NumBuggySlots"),
    WEAPONS_CAPACITY("WeaponsCapacity"),
    WEAPONS_RECHARGE("WeaponsRecharge"),
    WING_DAMAGE_REDUCTION("WingDamageReduction"),
    WING_MAX_DURATION("WingMaxDuration"),
    WING_MIN_DURATION("WingMinDuration"),
    WING_SHIELD_REGENERATION_INCREASED(""),//bool val
    YAW_SPEED(""),
    ;
    private final String internalName;

    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance();
    private static final NumberFormat NUMBER_FORMAT_2 = NumberFormat.getNumberInstance();
    static {
        NUMBER_FORMAT.setMaximumFractionDigits(0);
        NUMBER_FORMAT_2.setMaximumFractionDigits(2);
    }
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

    public String format(Object value) {
        final String formatString = getFormatString();
        if(value instanceof Double d){
            if(formatString.endsWith("%")){
                return String.format(formatString, NUMBER_FORMAT.format(d * 100));
            }
            return String.format(formatString, NUMBER_FORMAT_2.format(d));
        }
        if(value instanceof Boolean b){
            return UTF8Constants.forBool(b);
        }

        return value.toString();
    }

    private String getFormatString() {
        return "%s " + LocaleService.getLocalizedStringForCurrentLocale("modifier.horizons.unit." + this.name().toLowerCase()).replace("%","%%");
    }
}
