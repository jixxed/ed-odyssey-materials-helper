package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.constants.UTF8Constants;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.math.BigDecimal;
import java.util.Arrays;

public enum HorizonsModifier {

    MASS(0, false, "Mass"),
    INTEGRITY(1, "Integrity"),
    POWER_DRAW(2, false, "PowerDraw"),
    BOOT_TIME(3, false, "BootTime"),
    SHIELDBANK_SPIN_UP(4, false, "ShieldBankSpinUp"),
    CHARGE_TIME(5, false, ""),
    DISRUPTION_BARRIER_CHARGE_DURATION(6, false, "DisruptionBarrierChargeDuration"),
    DAMAGE_MULTIPLIER(7, ""),
    DAMAGE(8, "Damage"),
    DAMAGE_PER_SECOND(9, "DamagePerSecond"),
    DISTRIBUTOR_DRAW(10, false, "DistributorDraw"),
    DISRUPTION_BARRIER_RANGE(11, "DisruptionBarrierRange"),
    ECM_RANGE(12, "ECMRange"),
    FSD_INTERDICTOR_RANGE(13, "FSDInterdictorRange"),
    ECM_TIME_TO_CHARGE(14, false, "ECMTimeToCharge"),
    HEATSINK_DURATION(15, false, "HeatSinkDuration"),
    SHIELDBANK_DURATION(16, "ShieldBankDuration"),
    DISRUPTION_BARRIER_ACTIVE_POWER(17, false, "DisruptionBarrierActivePower"),
    ECM_ACTIVE_POWER_CONSUMPTION(18, false, "ECMActivePowerConsumption"),
    FSD_OPTIMISED_MASS(19, "FSDOptimalMass"),
    ENGINE_MINIMUM_MASS(20, "EngineMinimumMass"),
    ENGINE_OPTIMAL_MASS(21, "EngineOptimalMass"),
    MAXIMUM_MASS(22, "MaximumMass"),
    MINIMUM_MULTIPLIER(23, "EngineMinPerformance"),
    OPTIMAL_MULTIPLIER(24, "EngineOptPerformance"),
    MAXIMUM_MULIPLIER(25, "EngineMaxPerformance"),
    MINIMUM_MULTIPLIER_SPEED(26, ""),
    OPTIMAL_MULTIPLIER_SPEED(27, ""),
    MAXIMUM_MULTIPLIER_SPEED(28, ""),
    MINIMUM_MULTIPLIER_ACCELERATION(29, ""),
    OPTIMAL_MULTIPLIER_ACCELERATION(30, ""),
    MAXIMUM_MULTIPLIER_ACCELERATION(31, ""),
    MINIMUM_MULTIPLIER_ROTATION(32, ""),
    OPTIMAL_MULTIPLIER_ROTATION(33, ""),
    MAXIMUM_MULTIPLIER_ROTATION(34, ""),
    SHIELDGEN_MINIMUM_MASS(35, "ShieldGenMinimumMass"),
    SHIELDGEN_OPTIMAL_MASS(36, "ShieldGenOptimalMass"),
    SHIELDGEN_MAXIMUM_MASS(37, "ShieldGenMaximumMass"),
    ECM_HEAT(38, "ECMHeat"),
    ENGINE_THERMAL_LOAD(39, false, "EngineHeatRate"),
    FSD_HEAT_RATE(40, false, "FSDHeatRate"),
    SHIELDBANK_HEAT(41, false, "ShieldBankHeat"),
    THERMAL_LOAD(42, false, "ThermalLoad"),
    DISRUPTION_BARRIER_COOLDOWN(43, "DisruptionBarrierCooldown"),
    ECM_COOLDOWN(44, false, "ECMCooldown"),
    ARMOUR_PIERCING(45, "ArmourPenetration"),
    MAXIMUM_RANGE(46, "MaximumRange"),
    SHOT_SPEED(47, "ShotSpeed"),
    RATE_OF_FIRE(48, "RateOfFire"),
    ROUNDS_PER_SHOT(49, "RoundsPerShot"),
    BURST_RATE_OF_FIRE(50, "BurstRateOfFire"),
    BURST_SIZE(51, "BurstSize"),
    BURST_INTERVAL(52, false, ""),
    AMMO_CLIP_SIZE(53, "AmmoClipSize"),
    AMMO_MAXIMUM(54, "AmmoMaximum"),
    AMMO_COST(55, false, ""),
    RELOAD_TIME(56, false, "ReloadTime"),
    DAMAGE_TYPE(57, "DamageType"),
    JITTER(58, false, "Jitter"),
    DAMAGE_FALLOFF_START(59, "DamageFalloffRange", "FalloffRange"),
    JAM_DURATION(60, "ChaffJamDuration"),
    BREACH_DAMAGE(61, "BreachDamage"),
    MIN_BREACH_CHANCE(62, "MinBreachChance"),
    MAX_BREACH_CHANCE(63, "MaxBreachChance"),
    DISCOVERY_SCANNER_PASSIVE_RANGE(64, "DiscoveryScannerPassiveRange"),
    THERMAL_DRAIN(65, "ThermalDrain"),
    DISCOVERY_SCANNER_RANGE(66, "DiscoveryScannerRange"),
    MAX_RANGE(67, "MaxRange"),
    SCANNER_RANGE(68, "ScannerRange"),
    FSD_INTERDICTOR_FACING_LIMIT(69, "FSDInterdictorFacingLimit"),
    MAX_ANGLE(70, "MaxAngle"),
    SCAN_TIME(71, false, "ScannerTimeToScan"),
    HULL_BOOST(72, "DefenceModifierHealthMultiplier"),
    SHIELD_BOOST(73, "DefenceModifierShieldMultiplier"),
    KINETIC_RESISTANCE(74, BigDecimal.valueOf(100), "KineticResistance"),
    THERMAL_RESISTANCE(75, BigDecimal.valueOf(100), "ThermicResistance"),
    EXPLOSIVE_RESISTANCE(76, BigDecimal.valueOf(100), "ExplosiveResistance"),
    CAUSTIC_RESISTANCE(77, BigDecimal.valueOf(100), "CausticResistance"),
    POWER_CAPACITY(78, "PowerCapacity"),
    HEAT_EFFICIENCY(79, false, "HeatEfficiency"),
    MAX_FUEL_PER_JUMP(80, "MaxFuelPerJump"),
    EMERGENCY_LIFE_SUPPORT(81, "OxygenTimeCapacity"),
    FUEL_MULTIPLIER(82, ""),
    JUMP_RANGE_INCREASE(83, "FSDJumpRangeBoost"),
    FUEL_POWER(84, ""),
    FSD_FUEL_USE_INCREASE(85, false, "FSDFuelUseIncrease"),
    WEAPONS_CAPACITY(86, "WeaponsCapacity"),
    WEAPONS_RECHARGE(87, "WeaponsRecharge"),
    ENGINES_CAPACITY(88, "EnginesCapacity"),
    ENGINES_RECHARGE(89, "EnginesRecharge"),
    SYSTEMS_CAPACITY(90, "SystemsCapacity"),
    SYSTEMS_RECHARGE(91, "SystemsRecharge"),
    POWER_BOOST(92, ""),
    SCAN_ANGLE(93, "SensorTargetScanAngle"),
    ENERGY_PER_REGEN(94, "EnergyPerRegen"),
    TYPICAL_EMISSION_RANGE(95, "Range"),
    DRONE_FUEL_CAPACITY(96, "DroneFuelCapacity"),
    FUEL_CAPACITY(97, "FuelCapacity"),
    DRONE_REPAIR_CAPACITY(98, "DroneRepairCapacity"),
    REPAIR_CAPACITY(99, "AFMRepairCapacity"),
    CONSUMPTION(100, true, "AFMRepairConsumption"),
    REPAIR_RATING(101, "AFMRepairPerAmmo"),
    CABIN_CAPACITY(102, "CabinCapacity"),
    CARGO_CAPACITY(103, "CargoCapacity"),
    CABIN_CLASS(104, "CabinClass"),
    MAX_ACTIVE_LIMPETS(105, "MaxActiveDrones"),
    DRONE_ACTIVE_RANGE(106, ""),
    DRONE_TARGET_RANGE(107, "DroneTargetRange"),
    DRONE_LIFE_TIME(108, "DroneLifeTime"),
    DRONE_SPEED(109, "DroneSpeed"),
    DRONE_MULTI_TARGET_SPEED(110, "DroneMultiTargetSpeed"),
    MINE_BONUS(111, ""),
    MODULE_DEFENCE_ABSORPTION(112, "ModuleDefenceAbsorption"),
    REGEN_RATE(113, "RegenRate"),
    SCOOP_RATE(114, "FuelScoopRate"),
    BROKEN_REGEN_RATE(115, "BrokenRegenRate"),
    DRONE_HACKING_TIME(116, false, "DroneHackingTime"),
    DRONE_MIN_JETTISONED_CARGO(117, "DroneMinJettisonedCargo"),
    DRONE_MAX_JETTISONED_CARGO(118, "DroneMaxJettisonedCargo"),
    SHIELD_REINFORCEMENT(119, "DefenceModifierShieldAddition"),
    SHIELDBANK_REINFORCEMENT(120, "ShieldBankReinforcement"),
    HULL_REINFORCEMENT(121, "DefenceModifierHealthAddition"),
    SHIELD_SACRIFICE_AMOUNT_GIVEN(122, "ShieldSacrificeAmountGiven"),
    SHIELD_SACRIFICE_AMOUNT_REMOVED(123, "ShieldSacrificeAmountRemoved"),
    BIN_COUNT(124, "RefineryBins"),
    SHIELDGEN_MINIMUM_STRENGTH(125, "ShieldGenMinStrength"),
    SHIELDGEN_OPTIMAL_STRENGTH(126, "ShieldGenStrength"),
    SHIELDGEN_MAXIMUM_STRENGTH(127, "ShieldGenMaxStrength"),
    DSS_PATCH_RADIUS(128, BigDecimal.valueOf(100), "DSS_PatchRadius"),
    WING_MIN_DURATION(129, "WingMinDuration"),
    WING_MAX_DURATION(130, "WingMaxDuration"),
    WING_DAMAGE_REDUCTION(131, "WingDamageReduction"),
    ABSOLUTE_DAMAGE_RATIO(201, ""),
    THERMAL_DAMAGE_RATIO(202, ""),
    KINETIC_DAMAGE_RATIO(203, ""),
    EXPLOSIVE_DAMAGE_RATIO(204, ""),
    CAUSTIC_DAMAGE_RATIO(205, ""),
    ANTI_XENO_DAMAGE_RATIO(206, ""),
    OVERCHARGE_MAX_SPEED_INCREASE(207, ""),
    OVERCHARGE_MAX_ACCELERATION_RATE(208, ""),
    OVERCHARGE_HEAT_GENERATION_RATE(209, ""),
    OVERCHARGE_CONTROL_INTERFERENCE(210, ""),
    OVERCHARGE_FUEL_CONSUMPTION(211, ""),
    TARGET_ARMOR_HARDNESS_REDUCED(700, ""),
    TARGET_FSD_INHIBITED(701, ""),
    TARGET_FSD_REBOOTS(702, ""),
    TARGET_GIMBAL_TURRET_TRACKING_REDUCED(703, ""),
    TARGET_HEAT_INCREASED(704, ""),
    TARGET_LOSES_TARGET_LOCK(705, ""),
    TARGET_MODULE_DAMAGE(706, ""),
    TARGET_MODULES_MALFUNCTIONS(707, ""),
    TARGET_PUSHED_OFF_COURSE(708, ""),
    TARGET_SENSOR_ACUITY_REDUCED(709, ""),
    TARGET_SHIELD_CELL_DISRUPTED(710, ""),
    TARGET_SHIELD_GENERATOR_DAMAGED(711, ""),
    TARGET_SIGNATURE_INCREASED(712, ""),
    TARGET_SPEED_REDUCED(713, ""),
    TARGET_THRUSTERS_REBOOT(714, ""),
    TARGET_WING_SHIELDS_REGENERATED(715, ""),
    SHIELDED_TARGET_HEAT_INCREASED(716, ""),
    WING_SHIELD_REGENERATION_INCREASED(717, ""),
    RELOAD_FROM_SHIP_FUEL(718, ""),
    PART_OF_DAMAGE_THROUGH_SHIELDS(719, ""),
    NO_DAMAGE_TO_UNTARGETED_SHIPS(720, ""),
    HEAT_REDUCED_WHEN_STRIKING_A_TARGET(721, ""),
    EFFECTIVENESS_INCREASE_AGAINST_MUNITIONS(722, ""),
    DAMAGE_PARTIALLY_THERMAL(723, ""),
    DAMAGE_PARTIALLY_KINETIC(724, ""),
    DAMAGE_PARTIALLY_EXPLOSIVE(725, ""),
    DAMAGE_INCREASES_WITH_HEAT_LEVEL(726, ""),
    AUTO_RELOAD_WHILE_FIRING(727, ""),
    AREA_HEAT_INCREASED_SENSORS_DISRUPTED(728, ""),
    ANTI_XENO_DAMAGE(729, ""),
    AREA_FSDS_REBOOT(729, ""),
    ANTI_GUARDIAN_ZONE_RESISTANCE(730, "GuardianModuleResistance"),
    DWARF_BOOST(731, ""),
    NEUTRON_BOOST(732, ""),
    SUPERCRUISE_OVERCHARGE(733, ""),
    VEHICLE_ARMOUR_HEALTH(801, "VehicleArmourHealth"),
    VEHICLE_CARGO_CAPACITY(802, "VehicleCargoCapacity"),
    VEHICLE_COUNT(803, ""),
    VEHICLE_FUEL_CAPACITY(804, "VehicleFuelCapacity"),
    VEHICLE_HULL_MASS(805, "VehicleHullMass"),
    VEHICLE_SHIELD_HEALTH(806, "VehicleShieldHealth"),
    VEHICLE_SLOTS(807, "NumBuggySlots"),
    FIGHTER_BOOST_SPEED(808, "FighterBoostSpeed"),
    FIGHTER_DPS(809, "FighterDPS"),
    FIGHTER_MAX_SPEED(810, "FighterMaxSpeed"),
    FIGHTER_PITCH_RATE(811, "FighterPitchRate"),
    FIGHTER_ROLL_RATE(812, "FighterRollRate"),
    FIGHTER_YAW_RATE(813, "FighterYawRate"),
    MODULE_LIMIT_INCREASE(814, ""),
    MAX_GLIDE_ANGLE(815, ""),
    MAX_GLIDE_SPEED(816, ""),
    CREW(1010, ""),//ship
    TOP_SPEED(1020, ""),//ship
    BOOST_SPEED(1030, ""),//ship
    MINIMUM_THRUST(1040, ""),//ship
    FORWARD_ACCELERATION(1045, ""),//ship
    REVERSE_ACCELERATION(1045, ""),//ship
    LATERAL_ACCELERATION(1045, ""),//ship
    BOOST_COST(1050, false, ""),//ship
    BOOST_INTERVAL(1055, ""),
    MANOEUVRABILITY(1060, ""),//ship
    SUPERCRUISE_PROFILE(1065, ""),//ship
    CRUISE_PITCH_PROFILE(1066, ""),//ship
    CRUISE_ROLL_PROFILE(1067, ""),//ship
    CRUISE_YAW_PROFILE(1068, ""),//ship
    SC_ROLL_SPEED(1070, ""),//ship
    SC_YAW_SPEED(1070, ""),//ship
    MIN_PITCH_SPEED(1070, ""),//ship
    MAX_PITCH_SPEED(1075, ""),//ship
    MIN_YAW_SPEED(1080, ""),//ship
    MAX_YAW_SPEED(1085, ""),//ship
    MIN_ROLL_SPEED(1090, ""),//ship
    MAX_ROLL_SPEED(1195, ""),//ship
    SHIELDS(1110, ""),//ship
    ARMOUR(1120, ""),//ship
    ARMOUR_HARDNESS(1130, ""),//ship
    HEAT_CAPACITY(1140, ""),//ship
    HEAT_DISSIPATION_MIN(1150, ""),//ship
    HEAT_DISSIPATION_MAX(1160, ""),//ship
    MASS_LOCK(1170, false, ""),//ship
    SENSOR_LOCK_MIN(1175, ""),//ship
    FUEL_COST(1180, false, ""),//ship
    FUEL_RESERVE(1190, ""),//ship
    FUEL_EFFICIENCY(2000, ""),//synthesis
    HEAT_DISSIPATION(2010, ""),//synthesis
    HULL_STRENGTH(2020, ""),//synthesis
    JUMP_RANGE(2030, ""),//synthesis
    LIMPETS(2040, ""),//synthesis
    REFILL(2050, ""),//synthesis
    DAMAGE_BOOST(2060, ""),//synthesis
    BOOST_AUGMENTER_POWER_USE(8000, false, "BoostAugmenterPowerUse"),//todo remove?
    BOOST_SPEED_MULTIPLIER(8000, "BoostSpeedMultiplier"),//todo remove?
    LISTED_POWER_DRAW(9999, false, ""),

    ;
    private final String[] internalNames;
    private final int order;
    @Getter
    private BigDecimal multiplier = BigDecimal.ONE;
    @Getter
    private boolean higherBetter = true;

    HorizonsModifier(int order, String... internalNames) {
        this.internalNames = internalNames;
        this.order = order;
    }

    HorizonsModifier(int order, boolean higherBetter, String... internalNames) {
        this(order, internalNames);
        this.higherBetter = higherBetter;
    }

    HorizonsModifier(int order, BigDecimal multiplier, String... internalNames) {
        this(order, internalNames);
        this.multiplier = multiplier;
    }

    HorizonsModifier(int order, boolean higherBetter, BigDecimal multiplier, String... internalNames) {
        this(order, higherBetter, internalNames);
        this.multiplier = multiplier;
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
                .filter(horizonsModifier -> Arrays.stream(horizonsModifier.internalNames).anyMatch(name -> name.equalsIgnoreCase(internalName)))
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
                return String.format(formatString, Formatters.NUMBER_FORMAT_1.format(d * 100));
            }
            return String.format(formatString, Formatters.NUMBER_FORMAT_2.format(d));
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
        if (order == 9999)
            return order + ordinal();
        return order;
    }

}
