package nl.jixxed.eliteodysseymaterials.enums;

import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.constants.UTF8Constants;
import nl.jixxed.eliteodysseymaterials.constants.UnitConstants;
import nl.jixxed.eliteodysseymaterials.domain.LevelValue;

import java.util.List;

@RequiredArgsConstructor
public enum DynamicStat implements Stat {
    //both
    MODIFICATION_SLOTS(StatGroup.GENERAL),
    //suits
    SHIELD_STRENGTH(StatGroup.SHIELD),
    SHIELD_REGEN(StatGroup.SHIELD),
    KINETIC_RESIST(StatGroup.RESISTANCE),
    THERMAL_RESIST(StatGroup.RESISTANCE),
    PLASMA_RESIST(StatGroup.RESISTANCE),
    EXPLOSIVE_RESIST(StatGroup.RESISTANCE),
    BATTERY(StatGroup.POWER),
    EMERGENCY_AIR(StatGroup.CAPACITY),
    AMMO_CAPACITY(StatGroup.CAPACITY),
    GOODS_CAPACITY(StatGroup.CAPACITY),
    ASSETS_CAPACITY(StatGroup.CAPACITY),
    DATA_CAPACITY(StatGroup.CAPACITY),
    SPRINT_DURATION(StatGroup.MOVEMENT),
    NIGHT_VISION(StatGroup.POWER),
    FOOTSTEPS_AUDIBLE_RANGE(StatGroup.OTHER),
    SPRINT_SPEED_CARBINE_SHOTGUN(StatGroup.MOVEMENT),
    SPRINT_SPEED_RIFLE(StatGroup.MOVEMENT),
    SPRINT_SPEED_SNIPER_LAUNCHER(StatGroup.MOVEMENT),
    WALK_SPEED_CARBINE_SHOTGUN(StatGroup.MOVEMENT),
    WALK_SPEED_RIFLE(StatGroup.MOVEMENT),
    WALK_SPEED_SNIPER_LAUNCHER(StatGroup.MOVEMENT),
    ADS_SPEED_PISTOL(StatGroup.MOVEMENT),
    ADS_SPEED_CARBINE_SHOTGUN(StatGroup.MOVEMENT),
    ADS_SPEED_RIFLE(StatGroup.MOVEMENT),
    ADS_SPEED_SNIPER_LAUNCHER(StatGroup.MOVEMENT),
    LOS_ANALYSIS_RANGE(StatGroup.OTHER),
    LOS_ANALYSIS_TIME(StatGroup.OTHER),
    ARCCUTTER_POWER_USAGE(StatGroup.POWER),
    ENERGYLINK_OVERLOAD_POWER_USAGE(StatGroup.POWER),
    MELEE_DAMAGE(StatGroup.OTHER),
    JUMP_ASSIST_BATTERY_CONSUMPTION(StatGroup.POWER),
    JUMP_ASSIST_DRAIN(StatGroup.POWER),
    JUMP_ASSIST_RECHARGE(StatGroup.POWER),
    //weapons
    DAMAGE(StatGroup.DAMAGE),
    MAGAZINE_SIZE(StatGroup.CAPACITY),
    RESERVE_AMMO(StatGroup.CAPACITY),
    HEADSHOT_DAMAGE(StatGroup.DAMAGE),
    EFFECTIVE_RANGE(StatGroup.OTHER),
    HIP_FIRE_ACCURACY(StatGroup.OTHER),
    STABILITY(StatGroup.OTHER),
    HANDLING(StatGroup.OTHER),
    SILENCED_INSIDE(StatGroup.NOISE),
    SILENCED_OUTSIDE(StatGroup.NOISE),
    RELOAD_SPEED(StatGroup.DAMAGE),
    SCOPE(StatGroup.OTHER),
    STOWED_RELOADING(StatGroup.OTHER);
    private final StatGroup statGroup;


    @Override
    @SuppressWarnings({"java:S3358", "java:S6205", "java:S3776"})
    public String formatValue(final Object value, final Equipment equipment, final Integer level, final List<Modification> modifications) {
        if (value == null) {
            throw new IllegalArgumentException(this.name() + " value has not been set");
        }
        return switch (this) {
            case DAMAGE, MODIFICATION_SLOTS -> ((LevelValue) value).getValueForLevel(level).toString();
            case GOODS_CAPACITY, ASSETS_CAPACITY, DATA_CAPACITY -> modifications.contains(SuitModification.EXTRA_BACKPACK_CAPACITY) ? String.valueOf((Integer) value * 2) : value.toString();
            case JUMP_ASSIST_BATTERY_CONSUMPTION, JUMP_ASSIST_DRAIN -> NUMBER_FORMAT_0.format((modifications.contains(SuitModification.IMPROVED_JUMP_ASSIST)) ? (Integer) value * 0.75 : value) + UnitConstants.PERCENT;
            case JUMP_ASSIST_RECHARGE -> NUMBER_FORMAT_0.format((modifications.contains(SuitModification.IMPROVED_JUMP_ASSIST)) ? (Integer) value * 1.25 : value) + UnitConstants.PERCENT;
            case HIP_FIRE_ACCURACY -> (modifications.contains(WeaponModification.HIGHER_ACCURACY_KINETIC) || modifications.contains(WeaponModification.HIGHER_ACCURACY_LASER) || modifications.contains(WeaponModification.HIGHER_ACCURACY_PLASMA) ? getHipFireAccuracy(equipment, (Double) value) : value.toString()) + UnitConstants.PERCENT;
            case STABILITY -> (modifications.contains(WeaponModification.STABILITY) ? getStability((Double) value) : value.toString()) + UnitConstants.PERCENT;
            case HANDLING -> (modifications.contains(WeaponModification.FASTER_HANDLING) ? String.valueOf((Integer) value + 10) : String.valueOf(value)) + UnitConstants.PERCENT;
            case MAGAZINE_SIZE -> modifications.contains(WeaponModification.MAGAZINE_SIZE) ? String.valueOf(Math.round((Integer) value * 1.5)) : value.toString();
            case AMMO_CAPACITY -> NUMBER_FORMAT_0.format(modifications.contains(SuitModification.EXTRA_AMMO_CAPACITY) ? (Integer) value * 1.5 : value) + UnitConstants.PERCENT;
            case BATTERY -> NUMBER_FORMAT_2.format(modifications.contains(SuitModification.IMPROVED_BATTERY_CAPACITY) ? (Double) value * 1.5 : value) + UnitConstants.MEGAWATT;
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
            case ARCCUTTER_POWER_USAGE -> NUMBER_FORMAT_2.format((modifications.contains(SuitModification.REDUCED_TOOL_BATTERY_CONSUMPTION) ? (Double) value / 2 : value)) + UnitConstants.KILOWATT_PER_SECOND;
            case ENERGYLINK_OVERLOAD_POWER_USAGE -> NUMBER_FORMAT_2.format((modifications.contains(SuitModification.REDUCED_TOOL_BATTERY_CONSUMPTION) ? (Double) value / 2 : value)) + UnitConstants.MEGAWATT;
            case MELEE_DAMAGE -> NUMBER_FORMAT_0.format((modifications.contains(SuitModification.ADDED_MELEE_DAMAGE)) ? (Integer) value * 2.5 : value) + UnitConstants.PERCENT;
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

    @Override
    public StatGroup getStatGroup() {
        return this.statGroup;
    }


}
