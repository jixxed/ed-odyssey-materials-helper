package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.constants.UTF8Constants;
import nl.jixxed.eliteodysseymaterials.constants.UnitConstants;
import nl.jixxed.eliteodysseymaterials.domain.LevelValue;

import java.util.List;

@RequiredArgsConstructor
@Getter
public enum DynamicStat implements Stat {
    //both
    MODIFICATION_SLOTS(StatGroup.GENERAL, 1),
    //suit
    SHIELD_STRENGTH(StatGroup.SHIELD, 1),
    SHIELD_REGEN(StatGroup.SHIELD, 2),
    KINETIC_RESIST(StatGroup.RESISTANCE, 1),
    THERMAL_RESIST(StatGroup.RESISTANCE, 2),
    PLASMA_RESIST(StatGroup.RESISTANCE, 3),
    EXPLOSIVE_RESIST(StatGroup.RESISTANCE, 4),
    EMERGENCY_AIR(StatGroup.CAPACITY, 1),
    AMMO_CAPACITY(StatGroup.CAPACITY, 2),
    GOODS_CAPACITY(StatGroup.CAPACITY, 3),
    ASSETS_CAPACITY(StatGroup.CAPACITY, 4),
    DATA_CAPACITY(StatGroup.CAPACITY, 5),
    //weapon
    MAGAZINE_SIZE(StatGroup.CAPACITY, 1),
    RESERVE_AMMO(StatGroup.CAPACITY, 2),
    //suit
    SPRINT_SPEED_CARBINE_SHOTGUN(StatGroup.MOVEMENT, 3),
    SPRINT_SPEED_RIFLE(StatGroup.MOVEMENT, 4),
    SPRINT_SPEED_SNIPER_LAUNCHER(StatGroup.MOVEMENT, 5),
    WALK_SPEED_CARBINE_SHOTGUN(StatGroup.MOVEMENT, 8),
    WALK_SPEED_RIFLE(StatGroup.MOVEMENT, 9),
    WALK_SPEED_SNIPER_LAUNCHER(StatGroup.MOVEMENT, 10),
    ADS_SPEED_PISTOL(StatGroup.MOVEMENT, 11),
    ADS_SPEED_CARBINE_SHOTGUN(StatGroup.MOVEMENT, 12),
    ADS_SPEED_RIFLE(StatGroup.MOVEMENT, 13),
    ADS_SPEED_SNIPER_LAUNCHER(StatGroup.MOVEMENT, 14),
    SPRINT_DURATION(StatGroup.MOVEMENT, 16),
    //weapon
    DRAW_SPEED(StatGroup.MOVEMENT, 1),
    STOW_SPEED(StatGroup.MOVEMENT, 2),
    ADS_SPEED(StatGroup.MOVEMENT, 3),
    //suit
    BATTERY(StatGroup.POWER, 1),
    BATTERY_RECHARGE_DURATION(StatGroup.POWER, 2),
    JUMP_ASSIST_RECHARGE(StatGroup.POWER, 3),
    JUMP_ASSIST_BATTERY_CONSUMPTION(StatGroup.POWER, 4),
    JUMP_ASSIST_DURATION(StatGroup.POWER, 5),
    PROFILESCANNER_POWER_USAGE(StatGroup.POWER, 9),
    ENERGYLINK_OVERLOAD_POWER_USAGE(StatGroup.POWER, 15),
    ARCCUTTER_POWER_USAGE(StatGroup.POWER, 16),
    GENETICSAMPLER_POWER_USAGE(StatGroup.POWER, 16),
    //weapon
    DAMAGE(StatGroup.DAMAGE, 1),
    HEADSHOT_DAMAGE(StatGroup.DAMAGE, 2),
    RELOAD_SPEED(StatGroup.DAMAGE, 3),
    SILENCED_INSIDE(StatGroup.NOISE, 1),
    SILENCED_OUTSIDE(StatGroup.NOISE, 2),
    //suit
    NIGHT_VISION(StatGroup.OTHER, 1),
    FOOTSTEPS_AUDIBLE_RANGE(StatGroup.OTHER, 2),
    LOS_ANALYSIS_RANGE(StatGroup.OTHER, 3),
    LOS_ANALYSIS_TIME(StatGroup.OTHER, 4),
    MELEE_DAMAGE(StatGroup.OTHER, 5),
    //weapon
    EFFECTIVE_RANGE(StatGroup.OTHER, 1),
    HIP_FIRE_ACCURACY(StatGroup.OTHER, 2),
    STABILITY(StatGroup.OTHER, 3),
    SCOPE(StatGroup.OTHER, 4),
    STOWED_RELOADING(StatGroup.OTHER, 5);
    private final StatGroup statGroup;
    private final Integer order;


    @Override
    @SuppressWarnings({"java:S3358", "java:S6205", "java:S3776"})
    public String formatValue(final Object value, final Equipment equipment, final Integer level, final List<Modification> modifications) {
        if (value == null) {
            throw new IllegalArgumentException(this.name() + " value has not been set");
        }
        return switch (this) {
            case DAMAGE, MODIFICATION_SLOTS -> ((LevelValue) value).getValueForLevel(level).toString();
            case GOODS_CAPACITY, ASSETS_CAPACITY, DATA_CAPACITY -> modifications.contains(SuitModification.EXTRA_BACKPACK_CAPACITY) ? String.valueOf((Integer) value * 2) : value.toString();
            case JUMP_ASSIST_DURATION -> NUMBER_FORMAT_0.format((modifications.contains(SuitModification.IMPROVED_JUMP_ASSIST)) ? (Integer) value * 1.33 : value) + UnitConstants.PERCENT;
            case JUMP_ASSIST_BATTERY_CONSUMPTION -> NUMBER_FORMAT_0.format((modifications.contains(SuitModification.IMPROVED_JUMP_ASSIST)) ? (Integer) value * 0.5 : value) + UnitConstants.KILOWATT;
            case JUMP_ASSIST_RECHARGE -> NUMBER_FORMAT_2.format((modifications.contains(SuitModification.IMPROVED_JUMP_ASSIST)) ? (Double) value * 2 / 3 : (Double) value) + UnitConstants.SECOND;
            case HIP_FIRE_ACCURACY -> (modifications.contains(WeaponModification.HIGHER_ACCURACY_KINETIC) || modifications.contains(WeaponModification.HIGHER_ACCURACY_LASER) || modifications.contains(WeaponModification.HIGHER_ACCURACY_PLASMA) ? getHipFireAccuracy(equipment, (Double) value) : value.toString()) + UnitConstants.PERCENT;
            case STABILITY -> (modifications.contains(WeaponModification.STABILITY) ? getStability((Double) value) : value.toString()) + UnitConstants.PERCENT;
            case MAGAZINE_SIZE -> modifications.contains(WeaponModification.MAGAZINE_SIZE) ? String.valueOf(Math.round((Integer) value * 1.5)) : value.toString();
            case AMMO_CAPACITY -> NUMBER_FORMAT_0.format(modifications.contains(SuitModification.EXTRA_AMMO_CAPACITY) ? (Integer) value * 1.5 : value) + UnitConstants.PERCENT;
            case BATTERY -> NUMBER_FORMAT_2.format(modifications.contains(SuitModification.IMPROVED_BATTERY_CAPACITY) ? (Double) value * 1.5 : value) + UnitConstants.MEGAWATT;
            case BATTERY_RECHARGE_DURATION -> NUMBER_FORMAT_1.format((Double) (modifications.contains(SuitModification.IMPROVED_BATTERY_CAPACITY) ? (Double) value * 1.5 : value) / 2.7) + UnitConstants.SECOND;
            case SHIELD_STRENGTH -> ((LevelValue) value).getValueForLevel(level).toString() + UnitConstants.MEGAWATT;
            case SHIELD_REGEN -> NUMBER_FORMAT_2.format(modifications.contains(SuitModification.FASTER_SHIELD_REGEN) ? (Double) ((LevelValue) value).getValueForLevel(level) * 1.25 : ((LevelValue) value).getValueForLevel(level)) + UnitConstants.MEGAWATT_PER_SECOND;
            case RESERVE_AMMO -> (modifications.contains(SuitModification.EXTRA_AMMO_CAPACITY)) ? String.valueOf(Math.round((Integer) value * 1.5)) : value.toString();
            case KINETIC_RESIST, THERMAL_RESIST, PLASMA_RESIST, EXPLOSIVE_RESIST -> {
                if (modifications.contains(SuitModification.DAMAGE_RESISTANCE)) {
                    final Integer resistance = (Integer) ((LevelValue) value).getValueForLevel(level);
                    final long effectiveResistance = Math.round((100 - resistance) * 0.1 + resistance);
                    yield effectiveResistance + UnitConstants.PERCENT;
                } else {
                    yield ((LevelValue) value).getValueForLevel(level).toString() + UnitConstants.PERCENT;
                }
            }
            case EMERGENCY_AIR -> (modifications.contains(SuitModification.INCREASED_AIR_RESERVES) ? String.valueOf((Integer) value * 5) : value.toString()) + UnitConstants.SECOND;
            case SPRINT_DURATION -> (modifications.contains(SuitModification.INCREASED_SPRINT_DURATION) ? String.valueOf((Integer) value * 2) : value.toString()) + UnitConstants.SECOND;
            case HEADSHOT_DAMAGE -> (modifications.contains(WeaponModification.HEADSHOT_DAMAGE_KINETIC) || modifications.contains(WeaponModification.HEADSHOT_DAMAGE_LASER) || modifications.contains(WeaponModification.HEADSHOT_DAMAGE_PLASMA) ? String.valueOf(Math.round((Integer) value * 1.5)) : value) + UnitConstants.PERCENT;
            case EFFECTIVE_RANGE -> (modifications.contains(WeaponModification.GREATER_RANGE_KINETIC) || modifications.contains(WeaponModification.GREATER_RANGE_LASER) || modifications.contains(WeaponModification.GREATER_RANGE_PLASMA) ? String.valueOf(Math.round((Integer) value * 1.5)) : value) + UnitConstants.METER;
            case NIGHT_VISION -> (modifications.contains(SuitModification.NIGHT_VISION)) ? value + UnitConstants.KILOWATT_PER_SECOND : UTF8Constants.CHECK_FALSE;
            case FOOTSTEPS_AUDIBLE_RANGE -> ((modifications.contains(SuitModification.QUIETER_FOOTSTEPS)) ? ((Integer) value) / 2 : ((Integer) value)) + UnitConstants.METER;
            case SPRINT_SPEED_CARBINE_SHOTGUN, SPRINT_SPEED_RIFLE, SPRINT_SPEED_SNIPER_LAUNCHER -> NUMBER_FORMAT_2.format(modifications.contains(SuitModification.COMBAT_MOVEMENT_SPEED) ? 7.0 : value) + UnitConstants.METER_PER_SECOND;
            case WALK_SPEED_CARBINE_SHOTGUN, WALK_SPEED_RIFLE, WALK_SPEED_SNIPER_LAUNCHER -> NUMBER_FORMAT_2.format(modifications.contains(SuitModification.COMBAT_MOVEMENT_SPEED) ? 4.0 : value) + UnitConstants.METER_PER_SECOND;
            case ADS_SPEED_PISTOL, ADS_SPEED_CARBINE_SHOTGUN, ADS_SPEED_RIFLE, ADS_SPEED_SNIPER_LAUNCHER -> NUMBER_FORMAT_2.format(modifications.contains(SuitModification.COMBAT_MOVEMENT_SPEED) ? 4.0 : value) + UnitConstants.METER_PER_SECOND;
            case LOS_ANALYSIS_RANGE -> (modifications.contains(SuitModification.ENHANCED_TRACKING) ? (Integer) value * 2 : value) + UnitConstants.METER;
            case LOS_ANALYSIS_TIME -> (modifications.contains(SuitModification.ENHANCED_TRACKING) ? 0 : value) + UnitConstants.SECOND;
            case ARCCUTTER_POWER_USAGE, GENETICSAMPLER_POWER_USAGE, PROFILESCANNER_POWER_USAGE -> NUMBER_FORMAT_2.format((modifications.contains(SuitModification.REDUCED_TOOL_BATTERY_CONSUMPTION) ? (Double) value / 2 : value)) + UnitConstants.KILOWATT_PER_SECOND;
            case ENERGYLINK_OVERLOAD_POWER_USAGE -> NUMBER_FORMAT_2.format((modifications.contains(SuitModification.REDUCED_TOOL_BATTERY_CONSUMPTION) ? (Double) value / 2 : value)) + UnitConstants.MEGAWATT;
            case MELEE_DAMAGE -> NUMBER_FORMAT_0.format((modifications.contains(SuitModification.ADDED_MELEE_DAMAGE)) ? (Integer) value * 2.5 : value) + UnitConstants.PERCENT;
            case DRAW_SPEED, STOW_SPEED -> NUMBER_FORMAT_2.format(modifications.contains(WeaponModification.FASTER_HANDLING) ? getDrawStowSpeed(equipment, (Double) value) : value) + UnitConstants.SECOND;
            case ADS_SPEED -> NUMBER_FORMAT_2.format(modifications.contains(WeaponModification.FASTER_HANDLING) ? getADSSpeed(equipment, (Double) value) : value) + UnitConstants.SECOND;
            case SILENCED_INSIDE -> (modifications.contains(WeaponModification.NOISE_SUPPRESSOR) || (boolean) value) ? UTF8Constants.CHECK_TRUE : UTF8Constants.CHECK_FALSE;
            case SILENCED_OUTSIDE -> (modifications.contains(WeaponModification.AUDIO_MASKING) || (boolean) value) ? UTF8Constants.CHECK_TRUE : UTF8Constants.CHECK_FALSE;
            case RELOAD_SPEED -> ((modifications.contains(WeaponModification.RELOAD_SPEED)) ? NUMBER_FORMAT_2.format((Double) value / (equipment.equals(Weapon.TAKADA_APHELION) ? 1.20 : 1.25)) : value) + UnitConstants.SECOND;
            case SCOPE -> (modifications.contains(WeaponModification.SCOPE) || (boolean) value) ? UTF8Constants.CHECK_TRUE : UTF8Constants.CHECK_FALSE;
            case STOWED_RELOADING -> (modifications.contains(WeaponModification.STOWED_RELOADING) || (boolean) value) ? UTF8Constants.CHECK_TRUE : UTF8Constants.CHECK_FALSE;
        };
    }


    private String getHipFireAccuracy(final Equipment equipment, final Double value) {
        if (equipment instanceof Weapon) {
            return round((100.0 - value) * (Weapon.getHIP_FIRE_FACTORS().get(equipment) / 100) + value, 1).toString();
        }
        throw new IllegalArgumentException("Equipment not a weapon!");
    }

    private Double getADSSpeed(final Equipment equipment, final Double value) {
        if (equipment instanceof Weapon) {
            return round(value - value * Weapon.getADS_FACTORS().get(equipment) / 100, 2);
        }
        throw new IllegalArgumentException("Equipment not a weapon!");
    }

    private Double getDrawStowSpeed(final Equipment equipment, final Double value) {
        if (equipment instanceof Weapon) {
            return round(value - value * Weapon.getDRAW_STOW_FACTORS().get(equipment) / 100, 2);
        }
        throw new IllegalArgumentException("Equipment not a weapon!");
    }

    private String getStability(final Double value) {
        return round((100.0 - value) * 0.5 + value, 1).toString();
    }

    private static Double round(final Double value, final int precision) {
        final int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    @Override
    public String getLocalizationKey() {
        return "loadout.stat.name." + this.name().toLowerCase();
    }


}
