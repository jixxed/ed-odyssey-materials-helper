package nl.jixxed.eliteodysseymaterials.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.constants.UTF8Constants;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.text.NumberFormat;
import java.util.Arrays;

@RequiredArgsConstructor
@AllArgsConstructor
public enum HorizonsModifier {

    MASS("Mass", 0, false),
    INTEGRITY("Integrity", 1),
    POWER_DRAW("PowerDraw", 2, false),
    BOOT_TIME("BootTime", 3, false),
    SHIELDBANK_SPIN_UP("ShieldBankSpinUp", 4, false),
    CHARGE_TIME("", 5, false),
    DISRUPTION_BARRIER_CHARGE_DURATION("DisruptionBarrierChargeDuration", 6, false),
    DAMAGE_MULTIPLIER("", 7),
    DAMAGE("Damage", 8),
    DAMAGE_PER_SECOND("DamagePerSecond", 9),
    DISTRIBUTOR_DRAW("DistributorDraw", 10, false),
    DISRUPTION_BARRIER_RANGE("DisruptionBarrierRange", 11),
    ECM_RANGE("ECMRange", 12),
    FSD_INTERDICTOR_RANGE("FSDInterdictorRange", 13),
    ECM_TIME_TO_CHARGE("ECMTimeToCharge", 14, false),
    HEATSINK_DURATION("HeatSinkDuration", 15, false),
    SHIELDBANK_DURATION("ShieldBankDuration", 16),
    DISRUPTION_BARRIER_ACTIVE_POWER("DisruptionBarrierActivePower", 17, false),
    ECM_ACTIVE_POWER_CONSUMPTION("ECMActivePowerConsumption", 18, false),
    FSD_OPTIMISED_MASS("FSDOptimalMass", 19),
    ENGINE_MINIMUM_MASS("EngineMinimumMass", 20),
    ENGINE_OPTIMAL_MASS("EngineOptimalMass", 21),
    MAXIMUM_MASS("MaximumMass", 22),
    MINIMUM_MULTIPLIER("EngineMinPerformance", 23),
    OPTIMAL_MULTIPLIER("EngineOptPerformance", 24),
    MAXIMUM_MULIPLIER("EngineMaxPerformance", 25),
    MINIMUM_MULTIPLIER_SPEED("", 26),
    OPTIMAL_MULTIPLIER_SPEED("", 27),
    MAXIMUM_MULTIPLIER_SPEED("", 28),
    MINIMUM_MULTIPLIER_ACCELERATION("", 29),
    OPTIMAL_MULTIPLIER_ACCELERATION("", 30),
    MAXIMUM_MULTIPLIER_ACCELERATION("", 31),
    MINIMUM_MULTIPLIER_ROTATION("", 32),
    OPTIMAL_MULTIPLIER_ROTATION("", 33),
    MAXIMUM_MULTIPLIER_ROTATION("", 34),
    SHIELDGEN_MINIMUM_MASS("ShieldGenMinimumMass", 35),
    SHIELDGEN_OPTIMAL_MASS("ShieldGenOptimalMass", 36),
    SHIELDGEN_MAXIMUM_MASS("ShieldGenMaximumMass", 37),
    ECM_HEAT("ECMHeat", 38),
    ENGINE_THERMAL_LOAD("EngineHeatRate", 39, false),
    FSD_HEAT_RATE("FSDHeatRate", 40, false),
    SHIELDBANK_HEAT("ShieldBankHeat", 41, false),
    THERMAL_LOAD("ThermalLoad", 42, false),
    DISRUPTION_BARRIER_COOLDOWN("DisruptionBarrierCooldown", 43),
    ECM_COOLDOWN("ECMCooldown", 44, false),
    ARMOUR_PIERCING("ArmourPenetration", 45),
    MAXIMUM_RANGE("MaximumRange", 46),
    SHOT_SPEED("ShotSpeed", 47),
    RATE_OF_FIRE("RateOfFire", 48),
    ROUNDS_PER_SHOT("RoundsPerShot", 49),
    BURST_RATE_OF_FIRE("BurstRateOfFire", 50),
    BURST_SIZE("BurstSize", 51),
    BURST_INTERVAL("", 52, false),
    AMMO_CLIP_SIZE("AmmoClipSize", 53),
    AMMO_MAXIMUM("AmmoMaximum", 54),
    AMMO_COST("", 55, false),
    RELOAD_TIME("ReloadTime", 56, false),
    DAMAGE_TYPE("DamageType", 57),
    JITTER("Jitter", 58, false),
    DAMAGE_FALLOFF_START("DamageFalloffRange", 59),
    JAM_DURATION("ChaffJamDuration", 60),
    BREACH_DAMAGE("BreachDamage", 61),
    MIN_BREACH_CHANCE("MinBreachChance", 62),
    MAX_BREACH_CHANCE("MaxBreachChance", 63),
    DISCOVERY_SCANNER_PASSIVE_RANGE("DiscoveryScannerPassiveRange", 64),
    THERMAL_DRAIN("ThermalDrain", 65),
    DISCOVERY_SCANNER_RANGE("DiscoveryScannerRange", 66),
    MAX_RANGE("MaxRange", 67),
    SCANNER_RANGE("ScannerRange", 68),
    FSD_INTERDICTOR_FACING_LIMIT("FSDInterdictorFacingLimit", 69),
    MAX_ANGLE("MaxAngle", 70),
    SCAN_TIME("ScannerTimeToScan", 71, false),
    HULL_BOOST("DefenceModifierHealthMultiplier", 72),
    SHIELD_BOOST("DefenceModifierShieldMultiplier", 73),
    KINETIC_RESISTANCE("KineticResistance", 74),
    THERMAL_RESISTANCE("ThermicResistance", 75),
    EXPLOSIVE_RESISTANCE("ExplosiveResistance", 76),
    CAUSTIC_RESISTANCE("CausticResistance", 77),
    POWER_CAPACITY("PowerCapacity", 78),
    HEAT_EFFICIENCY("HeatEfficiency", 79, false),
    MAX_FUEL_PER_JUMP("MaxFuelPerJump", 80),
    EMERGENCY_LIFE_SUPPORT("OxygenTimeCapacity", 81),
    FUEL_MULTIPLIER("", 82),
    JUMP_RANGE_INCREASE("FSDJumpRangeBoost", 83),
    FUEL_POWER("", 84),
    FSD_FUEL_USE_INCREASE("FSDFuelUseIncrease", 85, false),
    WEAPONS_CAPACITY("WeaponsCapacity", 86),
    WEAPONS_RECHARGE("WeaponsRecharge", 87),
    ENGINES_CAPACITY("EnginesCapacity", 88),
    ENGINES_RECHARGE("EnginesRecharge", 89),
    SYSTEMS_CAPACITY("SystemsCapacity", 90),
    SYSTEMS_RECHARGE("SystemsRecharge", 91),
    POWER_BOOST("", 92),
    SCAN_ANGLE("SensorTargetScanAngle", 93),
    ENERGY_PER_REGEN("EnergyPerRegen", 94),
    TYPICAL_EMISSION_RANGE("Range", 95),
    DRONE_FUEL_CAPACITY("DroneFuelCapacity", 96),
    FUEL_CAPACITY("FuelCapacity", 97),
    DRONE_REPAIR_CAPACITY("DroneRepairCapacity", 98),
    REPAIR_CAPACITY("AFMRepairCapacity", 99),
    CONSUMPTION("AFMRepairConsumption", 100, false),
    REPAIR_RATING("AFMRepairPerAmmo", 101),
    CABIN_CAPACITY("CabinCapacity", 102),
    CARGO_CAPACITY("CargoCapacity", 103),
    CABIN_CLASS("CabinClass", 104),
    MAX_ACTIVE_LIMPETS("MaxActiveDrones", 105),
    DRONE_ACTIVE_RANGE("", 106),
    DRONE_TARGET_RANGE("DroneTargetRange", 107),
    DRONE_LIFE_TIME("DroneLifeTime", 108),
    DRONE_SPEED("DroneSpeed", 109),
    DRONE_MULTI_TARGET_SPEED("DroneMultiTargetSpeed", 110),
    MINE_BONUS("", 111),
    MODULE_DEFENCE_ABSORPTION("ModuleDefenceAbsorption", 112),
    REGEN_RATE("RegenRate", 113),
    SCOOP_RATE("FuelScoopRate", 114),
    BROKEN_REGEN_RATE("BrokenRegenRate", 115),
    DRONE_HACKING_TIME("DroneHackingTime", 116, false),
    DRONE_MIN_JETTISONED_CARGO("DroneMinJettisonedCargo", 117),
    DRONE_MAX_JETTISONED_CARGO("DroneMaxJettisonedCargo", 118),
    SHIELD_REINFORCEMENT("DefenceModifierShieldAddition", 119),
    SHIELDBANK_REINFORCEMENT("ShieldBankReinforcement", 120),
    HULL_REINFORCEMENT("DefenceModifierHealthAddition", 121),
    SHIELD_SACRIFICE_AMOUNT_GIVEN("ShieldSacrificeAmountGiven", 122),
    SHIELD_SACRIFICE_AMOUNT_REMOVED("ShieldSacrificeAmountRemoved", 123),
    BIN_COUNT("RefineryBins", 124),
    SHIELDGEN_MINIMUM_STRENGTH("ShieldGenMinStrength", 125),
    SHIELDGEN_OPTIMAL_STRENGTH("ShieldGenStrength", 126),
    SHIELDGEN_MAXIMUM_STRENGTH("ShieldGenMaxStrength", 127),
    DSS_PATCH_RADIUS("DSS_PatchRadius", 128),
    WING_MIN_DURATION("WingMinDuration", 129),
    WING_MAX_DURATION("WingMaxDuration", 130),
    WING_DAMAGE_REDUCTION("WingDamageReduction", 131),
    ABSOLUTE_DAMAGE_RATIO("", 201),
    THERMAL_DAMAGE_RATIO("", 202),
    KINETIC_DAMAGE_RATIO("", 203),
    EXPLOSIVE_DAMAGE_RATIO("", 204),
    CAUSTIC_DAMAGE_RATIO("", 205),
    ANTI_XENO_DAMAGE_RATIO("", 206),
    TARGET_ARMOR_HARDNESS_REDUCED("", 700),
    TARGET_FSD_INHIBITED("", 701),
    TARGET_FSD_REBOOTS("", 702),
    TARGET_GIMBAL_TURRET_TRACKING_REDUCED("", 703),
    TARGET_HEAT_INCREASED("", 704),
    TARGET_LOSES_TARGET_LOCK("", 705),
    TARGET_MODULE_DAMAGE("", 706),
    TARGET_MODULES_MALFUNCTIONS("", 707),
    TARGET_PUSHED_OFF_COURSE("", 708),
    TARGET_SENSOR_ACUITY_REDUCED("", 709),
    TARGET_SHIELD_CELL_DISRUPTED("", 710),
    TARGET_SHIELD_GENERATOR_DAMAGED("", 711),
    TARGET_SIGNATURE_INCREASED("", 712),
    TARGET_SPEED_REDUCED("", 713),
    TARGET_THRUSTERS_REBOOT("", 714),
    TARGET_WING_SHIELDS_REGENERATED("", 715),
    SHIELDED_TARGET_HEAT_INCREASED("", 716),
    WING_SHIELD_REGENERATION_INCREASED("", 717),
    RELOAD_FROM_SHIP_FUEL("", 718),
    PART_OF_DAMAGE_THROUGH_SHIELDS("", 719),
    NO_DAMAGE_TO_UNTARGETED_SHIPS("", 720),
    HEAT_REDUCED_WHEN_STRIKING_A_TARGET("", 721),
    EFFECTIVENESS_INCREASE_AGAINST_MUNITIONS("", 722),
    DAMAGE_PARTIALLY_THERMAL("", 723),
    DAMAGE_PARTIALLY_KINETIC("", 724),
    DAMAGE_PARTIALLY_EXPLOSIVE("", 725),
    DAMAGE_INCREASES_WITH_HEAT_LEVEL("", 726),
    AUTO_RELOAD_WHILE_FIRING("", 727),
    AREA_HEAT_INCREASED_SENSORS_DISRUPTED("", 728),
    ANTI_XENO_DAMAGE("", 729),
    AREA_FSDS_REBOOT("", 729),
    VEHICLE_ARMOUR_HEALTH("VehicleArmourHealth", 801),
    VEHICLE_CARGO_CAPACITY("VehicleCargoCapacity", 802),
    VEHICLE_COUNT("", 803),
    VEHICLE_FUEL_CAPACITY("VehicleFuelCapacity", 804),
    VEHICLE_HULL_MASS("VehicleHullMass", 805),
    VEHICLE_SHIELD_HEALTH("VehicleShieldHealth", 806),
    VEHICLE_SLOTS("NumBuggySlots", 807),
    FIGHTER_BOOST_SPEED("FighterBoostSpeed", 808),
    FIGHTER_DPS("FighterDPS", 809),
    FIGHTER_MAX_SPEED("FighterMaxSpeed", 810),
    FIGHTER_PITCH_RATE("FighterPitchRate", 811),
    FIGHTER_ROLL_RATE("FighterRollRate", 812),
    FIGHTER_YAW_RATE("FighterYawRate", 813),
    CREW("", 1001),//ship
    TOP_SPEED("", 1002),//ship
    BOOST_SPEED("", 1003),//ship
    MINIMUM_THRUST("", 1004),//ship
    BOOST_COST("", 1005, false),//ship
    MANOEUVRABILITY("", 1006),//ship
    MIN_PITCH_SPEED("", 1007),//ship
    PITCH_SPEED("", 1008),//ship
    YAW_SPEED("", 1009),//ship
    ROLL_SPEED("", 1010),//ship
    SHIELDS("", 1011),//ship
    ARMOUR("", 1012),//ship
    ARMOUR_HARDNESS("", 1013),//ship
    HEAT_CAPACITY("", 1014),//ship
    HEAT_DISSIPATION_MIN("", 1015),//ship
    HEAT_DISSIPATION_MAX("", 1016),//ship
    MASS_LOCK("", 1017, false),//ship
    FUEL_COST("", 1018, false),//ship
    FUEL_RESERVE("", 1019),//ship
    FUEL_EFFICIENCY("", 2000),//synthesis
    HEAT_DISSIPATION("", 2001),//synthesis
    HULL_STRENGTH("", 2002),//synthesis
    JUMP_RANGE("", 2003),//synthesis
    LIMPETS("", 2004),//synthesis
    REFILL("", 2005),//synthesis
    DAMAGE_BOOST("", 2006),//synthesis
    BOOST_AUGMENTER_POWER_USE("BoostAugmenterPowerUse", 8000, false),//todo remove?
    BOOST_SPEED_MULTIPLIER("BoostSpeedMultiplier", 8000),//todo remove?
    ;
    private final String internalName;
    private final int order;
    @Getter
    private boolean higherBetter = true;

    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance();
    private static final NumberFormat NUMBER_FORMAT_1 = NumberFormat.getNumberInstance();
    private static final NumberFormat NUMBER_FORMAT_2 = NumberFormat.getNumberInstance();

    static {
        NUMBER_FORMAT.setMaximumFractionDigits(0);
        NUMBER_FORMAT_1.setMaximumFractionDigits(1);
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
        if (value instanceof Double d) {
            if (formatString.endsWith("%")) {
                return String.format(formatString, NUMBER_FORMAT_2.format(d * 100));//TODO format 1
            }
            return String.format(formatString, NUMBER_FORMAT_2.format(d));
        }
        if (value instanceof Boolean b) {
            return UTF8Constants.forBool(b);
        }

        return value.toString();
    }

    private String getFormatString() {
        return "%s" + LocaleService.getLocalizedStringForCurrentLocale("modifier.horizons.unit." + this.name().toLowerCase()).replace("%", "%%");
    }

    public double scale(double value) {
        return switch (this) {
            case ABSOLUTE_DAMAGE_RATIO,
                    ANTI_XENO_DAMAGE_RATIO,
                    ARMOUR_HARDNESS,
                    CAUSTIC_DAMAGE_RATIO,
                    CAUSTIC_RESISTANCE,
                    DAMAGE_BOOST,
                    DSS_PATCH_RADIUS,
                    EXPLOSIVE_DAMAGE_RATIO,
                    EXPLOSIVE_RESISTANCE,
                    FUEL_EFFICIENCY,
                    HULL_BOOST,
                    KINETIC_DAMAGE_RATIO,
                    KINETIC_RESISTANCE,
                    MAX_BREACH_CHANCE,
                    MIN_BREACH_CHANCE,
                    MODULE_DEFENCE_ABSORPTION,
                    REFILL,
                    SHIELD_BOOST,
                    SHIELDGEN_MAXIMUM_STRENGTH,
                    SHIELDGEN_MINIMUM_STRENGTH,
                    SHIELDGEN_OPTIMAL_STRENGTH,
                    THERMAL_DAMAGE_RATIO,
                    THERMAL_RESISTANCE -> value / 100;
            default -> value;
        };
    }

    public int getOrder() {
        if(order == 9999)
            return order + ordinal();
        return order;
    }
}
