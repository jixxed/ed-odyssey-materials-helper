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

    ABSOLUTE_DAMAGE_RATIO(""),
    AMMO_CLIP_SIZE("AmmoClipSize", 33),
    AMMO_COST("", false),
    AMMO_MAXIMUM("AmmoMaximum", 34),
    ANTI_XENO_DAMAGE("",129),//bool val
    ANTI_XENO_DAMAGE_RATIO(""),
    AREA_FSDS_REBOOT("",129),//bool val
    AREA_HEAT_INCREASED_SENSORS_DISRUPTED("",128),//bool val
    ARMOUR(""),
    ARMOUR_HARDNESS(""),
    ARMOUR_PIERCING("ArmourPenetration", 25),
    AUTO_RELOAD_WHILE_FIRING("",127),//bool val
    BIN_COUNT("RefineryBins", 84),
    BOOST_AUGMENTER_POWER_USE("BoostAugmenterPowerUse", false),
    BOOST_COST("", false),
    BOOST_SPEED(""),
    BOOST_SPEED_MULTIPLIER("BoostSpeedMultiplier"),
    BOOT_TIME("BootTime",4, false),
    BREACH_DAMAGE("BreachDamage", 37),
    BROKEN_REGEN_RATE("BrokenRegenRate", 74),
    BURST_INTERVAL("", false),
    BURST_RATE_OF_FIRE("BurstRateOfFire", 31),
    BURST_SIZE("BurstSize", 32),
    CABIN_CAPACITY("CabinCapacity", 65),
    CABIN_CLASS("CabinClass", 66),
    CARGO_CAPACITY("CargoCapacity", 65),
    CAUSTIC_DAMAGE_RATIO(""),
    CAUSTIC_RESISTANCE("CausticResistance", 48),
    CHARGE_TIME("", 6, false),
    CONSUMPTION("AFMRepairConsumption", 63, false),
    CREW(""),
    DAMAGE("Damage", 8),
    DAMAGE_BOOST(""),//synth
    DAMAGE_FALLOFF_START("DamageFalloffRange", 37),
    DAMAGE_INCREASES_WITH_HEAT_LEVEL("",126),//bool val
    DAMAGE_MULTIPLIER("", 7),
    DAMAGE_PARTIALLY_EXPLOSIVE("",125),//bool val
    DAMAGE_PARTIALLY_KINETIC("",124),//bool val
    DAMAGE_PARTIALLY_THERMAL("",123),//bool val
    DAMAGE_PER_SECOND("DamagePerSecond", 9),
    DAMAGE_TYPE("DamageType", 36),
    DISCOVERY_SCANNER_PASSIVE_RANGE("DiscoveryScannerPassiveRange", 39),//new
    DISCOVERY_SCANNER_RANGE("DiscoveryScannerRange", 40),//rename
    DISRUPTION_BARRIER_ACTIVE_POWER("DisruptionBarrierActivePower", 13, false),//xeno
    DISRUPTION_BARRIER_CHARGE_DURATION("DisruptionBarrierChargeDuration", 6, false),//xeno
    DISRUPTION_BARRIER_COOLDOWN("DisruptionBarrierCooldown", 24),//xeno
    DISRUPTION_BARRIER_RANGE("DisruptionBarrierRange", 11),//xeno
    DISTRIBUTOR_DRAW("DistributorDraw", 10, false),
    DRONE_ACTIVE_RANGE("", 67),
    DRONE_FUEL_CAPACITY("DroneFuelCapacity", 61),
    DRONE_HACKING_TIME("DroneHackingTime", 75, false),
    DRONE_LIFE_TIME("DroneLifeTime", 68),
    DRONE_MIN_JETTISONED_CARGO("DroneMinJettisonedCargo", 76),
    DRONE_MAX_JETTISONED_CARGO("DroneMaxJettisonedCargo", 77),
    DRONE_MULTI_TARGET_SPEED("DroneMultiTargetSpeed", 70),
    DRONE_REPAIR_CAPACITY("DroneRepairCapacity", 62),
    DRONE_SPEED("DroneSpeed", 69),
    DRONE_TARGET_RANGE("DroneTargetRange", 67),//new
    DSS_PATCH_RADIUS("DSS_PatchRadius", 87),
    ECM_ACTIVE_POWER_CONSUMPTION("ECMActivePowerConsumption", 13, false),//rename
    ECM_COOLDOWN("ECMCooldown", 24, false),//new
    ECM_HEAT("ECMHeat", 23),//new - thermal laod
    ECM_RANGE("ECMRange", 11),
    ECM_TIME_TO_CHARGE("ECMTimeToCharge", 12, false),
    EFFECTIVENESS_INCREASE_AGAINST_MUNITIONS("",122),//bool val
    EMERGENCY_LIFE_SUPPORT("OxygenTimeCapacity", 52),
    ENERGY_PER_REGEN("EnergyPerRegen", 60),//new
    ENGINE_MINIMUM_MASS("EngineMinimumMass", 14),//rename
    ENGINE_OPTIMAL_MASS("EngineOptimalMass", 16),
    ENGINE_THERMAL_LOAD("EngineHeatRate", 23, false),
    ENGINES_CAPACITY("EnginesCapacity", 55),
    ENGINES_RECHARGE("EnginesRecharge", 56),
    EXPLOSIVE_DAMAGE_RATIO(""),
    EXPLOSIVE_RESISTANCE("ExplosiveResistance", 47),
    FIGHTER_BOOST_SPEED("FighterBoostSpeed"),
    FIGHTER_DPS("FighterDPS"),
    FIGHTER_MAX_SPEED("FighterMaxSpeed"),
    FIGHTER_PITCH_RATE("FighterPitchRate"),
    FIGHTER_ROLL_RATE("FighterRollRate"),
    FIGHTER_YAW_RATE("FighterYawRate"),
    FSD_FUEL_USE_INCREASE("FSDFuelUseIncrease", 53, false),
    FSD_HEAT_RATE("FSDHeatRate", 23, false),//new
    FSD_INTERDICTOR_FACING_LIMIT("FSDInterdictorFacingLimit", 41),
    FSD_INTERDICTOR_RANGE("FSDInterdictorRange", 11),
    FSD_OPTIMISED_MASS("FSDOptimalMass", 15),//rename
    FUEL_CAPACITY("FuelCapacity", 61),
    FUEL_COST("", false),
    FUEL_EFFICIENCY(""),//synth
    FUEL_MULTIPLIER(""),
    FUEL_POWER(""),
    FUEL_RESERVE(""),//ship
    HEAT_CAPACITY(""),
    HEAT_DISSIPATION(""),//synth
    HEAT_DISSIPATION_MAX(""),
    HEAT_DISSIPATION_MIN(""),
    HEAT_EFFICIENCY("HeatEfficiency", 50, false),
    HEAT_REDUCED_WHEN_STRIKING_A_TARGET("",121),//bool val
    HEATSINK_DURATION("HeatSinkDuration", 12, false),
    HULL_BOOST("DefenceModifierHealthMultiplier", 43),
    HULL_REINFORCEMENT("DefenceModifierHealthAddition", 79),
    HULL_STRENGTH(""),//synth
    INTEGRITY("Integrity", 1),
    JAM_DURATION("ChaffJamDuration", 38),
    JITTER("Jitter", 36, false),//degreem
    JUMP_RANGE(""),//synth
    JUMP_RANGE_INCREASE("FSDJumpRangeBoost", 52),
    KINETIC_DAMAGE_RATIO(""),
    KINETIC_RESISTANCE("KineticResistance", 45),
    LIMPETS(""),//synth
    MASS("Mass", 0, false),
    MASS_LOCK("", false),
    MAX_ACTIVE_LIMPETS("MaxActiveDrones", 66),
    MAX_ANGLE("MaxAngle", 41),
    MAX_BREACH_CHANCE("MaxBreachChance", 39),
    MAX_FUEL_PER_JUMP("MaxFuelPerJump", 51),//rename
    MAX_RANGE("MaxRange", 40),
    MAXIMUM_MASS("MaximumMass", 17),
    MAXIMUM_MULIPLIER("EngineMaxPerformance", 22),
    MAXIMUM_RANGE("MaximumRange", 26),
    MIN_BREACH_CHANCE("MinBreachChance", 38),
    MIN_PITCH_SPEED(""),
    MINE_BONUS(""),
    MINIMUM_MULTIPLIER("EngineMinPerformance", 20),
    MODULE_DEFENCE_ABSORPTION("ModuleDefenceAbsorption", 72),//module reinf
    NO_DAMAGE_TO_UNTARGETED_SHIPS("",120),//bool val
    OPTIMAL_MULTIPLIER("EngineOptPerformance", 21),
    PART_OF_DAMAGE_THROUGH_SHIELDS("",119),//bool val
    PITCH_SPEED(""),
    POWER_BOOST(""),//might be needed in some calculation, not official?
    POWER_CAPACITY("PowerCapacity", 49),
    POWER_DRAW("PowerDraw", 3, false),
    RATE_OF_FIRE("RateOfFire", 30),
    REFILL(""),//synth
    REGEN_RATE("RegenRate", 73),
    RELOAD_FROM_SHIP_FUEL("",118),//bool val
    RELOAD_TIME("ReloadTime", 35, false),
    REPAIR_CAPACITY("AFMRepairCapacity", 62),
    REPAIR_RATING("AFMRepairPerAmmo", 64),
    ROLL_SPEED(""),
    ROUNDS_PER_SHOT("RoundsPerShot", 30),//new
    SCAN_ANGLE("SensorTargetScanAngle", 59),
    SCAN_TIME("ScannerTimeToScan", 42, false),
    SCANNER_RANGE("ScannerRange", 40),
    SCOOP_RATE("FuelScoopRate", 73),
    SHIELD_BOOST("DefenceModifierShieldMultiplier", 44),
    SHIELD_REINFORCEMENT("DefenceModifierShieldAddition", 78),
    SHIELD_SACRIFICE_AMOUNT_GIVEN("ShieldSacrificeAmountGiven", 79),
    SHIELD_SACRIFICE_AMOUNT_REMOVED("ShieldSacrificeAmountRemoved", 80),
    SHIELDBANK_DURATION("ShieldBankDuration", 12),
    SHIELDBANK_HEAT("ShieldBankHeat", 23, false),
    SHIELDBANK_REINFORCEMENT("ShieldBankReinforcement", 78),
    SHIELDBANK_SPIN_UP("ShieldBankSpinUp", 5, false),
    SHIELDED_TARGET_HEAT_INCREASED("", 116),//bool val
    SHIELDGEN_MAXIMUM_MASS("ShieldGenMaximumMass", 19),//new?
    SHIELDGEN_MAXIMUM_STRENGTH("ShieldGenMaxStrength", 86),//rename
    SHIELDGEN_MINIMUM_MASS("ShieldGenMinimumMass", 17),//new?
    SHIELDGEN_MINIMUM_STRENGTH("ShieldGenMinStrength", 84),
    SHIELDGEN_OPTIMAL_MASS("ShieldGenOptimalMass", 18),//new?
    SHIELDGEN_OPTIMAL_STRENGTH("ShieldGenStrength", 85),
    SHIELDS(""),
    SHOT_SPEED("ShotSpeed", 29),
    SYSTEMS_CAPACITY("SystemsCapacity", 57),
    SYSTEMS_RECHARGE("SystemsRecharge", 58),
    TARGET_ARMOR_HARDNESS_REDUCED("", 100),//bool val
    TARGET_FSD_INHIBITED("", 101),//bool val
    TARGET_FSD_REBOOTS("", 102),//bool val
    TARGET_GIMBAL_TURRET_TRACKING_REDUCED("", 103),//bool val
    TARGET_HEAT_INCREASED("", 104),//bool val
    TARGET_LOSES_TARGET_LOCK("", 105),//bool val
    TARGET_MODULE_DAMAGE("", 106),//bool val
    TARGET_MODULES_MALFUNCTIONS("", 107),//bool val
    TARGET_PUSHED_OFF_COURSE("", 108),//bool val
    TARGET_SENSOR_ACUITY_REDUCED("", 109),//bool val
    TARGET_SHIELD_CELL_DISRUPTED("", 110),//bool val
    TARGET_SHIELD_GENERATOR_DAMAGED("", 111),//bool val
    TARGET_SIGNATURE_INCREASED("", 112),//bool val
    TARGET_SPEED_REDUCED("", 113),//bool val
    TARGET_THRUSTERS_REBOOT("", 114),//bool val
    TARGET_WING_SHIELDS_REGENERATED("", 115),//bool val
    THERMAL_DAMAGE_RATIO(""),
    THERMAL_DRAIN("ThermalDrain", 39),
    THERMAL_LOAD("ThermalLoad", 23, false),
    THERMAL_RESISTANCE("ThermicResistance", 46),
    TOP_SPEED(""),
    TYPICAL_EMISSION_RANGE("Range", 60),
    VEHICLE_ARMOUR_HEALTH("VehicleArmourHealth"),
    VEHICLE_CARGO_CAPACITY("VehicleCargoCapacity"),
    VEHICLE_COUNT(""),
    VEHICLE_FUEL_CAPACITY("VehicleFuelCapacity"),
    VEHICLE_HULL_MASS("VehicleHullMass"),
    VEHICLE_SHIELD_HEALTH("VehicleShieldHealth"),
    VEHICLE_SLOTS("NumBuggySlots"),
    WEAPONS_CAPACITY("WeaponsCapacity",53),
    WEAPONS_RECHARGE("WeaponsRecharge", 54),
    WING_DAMAGE_REDUCTION("WingDamageReduction", 92),
    WING_MAX_DURATION("WingMaxDuration", 91),
    WING_MIN_DURATION("WingMinDuration", 90),
    WING_SHIELD_REGENERATION_INCREASED("",117),//bool val
    YAW_SPEED(""),
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

    HorizonsModifier(String internalName) {
        this.internalName = internalName;
        this.order = 999;
    }

    HorizonsModifier(String internalName, boolean higherBetter) {
        this.internalName = internalName;
        this.higherBetter = higherBetter;
        this.order = 999;
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
        if(order == 999)
            return order + ordinal();
        return order;
    }
}
