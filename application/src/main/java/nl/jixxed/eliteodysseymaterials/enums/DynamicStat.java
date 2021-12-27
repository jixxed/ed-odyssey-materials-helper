package nl.jixxed.eliteodysseymaterials.enums;

import nl.jixxed.eliteodysseymaterials.domain.LevelValue;

import java.text.NumberFormat;
import java.util.List;

public enum DynamicStat implements Stat {
    //both
    MODIFICATION_SLOTS,
    //suits
    SHIELD_STRENGTH,
    SHIELD_REGEN,
    KINETIC_RESIST,
    THERMAL_RESIST,
    PLASMA_RESIST,
    EXPLOSIVE_RESIST,
    BATTERY,
    EMERGENCY_AIR,
    AMMO_CAPACITY,
    GOODS_CAPACITY,
    ASSETS_CAPACITY,
    DATA_CAPACITY,
    SPRINT_DURATION,
    NIGHT_VISION,
    FOOTSTEPS_AUDIBLE_RANGE,
    REMOVE_MOVEMENT_SPEED_PENALTY,
    LOS_ANALYSIS_RANGE,
    LOS_ANALYSIS_TIME,
    TOOL_ENERGY_DRAIN_MULTIPLIER,
    MELEE_DAMAGE,
    JUMP_ASSIST_BATTERY_CONSUMPTION,
    JUMP_ASSIST_DRAIN,
    JUMP_ASSIST_RECHARGE,
    //weapons
    DAMAGE,
    MAGAZINE_SIZE,
    HEADSHOT_DAMAGE,
    EFFECTIVE_RANGE,
    HIP_FIRE_ACCURACY,
    STABILITY,
    HANDLING,
    SILENCED_INSIDE,
    SILENCED_OUTSIDE,
    RELOAD_SPEED,
    SCOPE,
    STOWED_RELOADING;
    private static final NumberFormat NUMBER_FORMAT_2 = NumberFormat.getNumberInstance();
    private static final NumberFormat NUMBER_FORMAT_0 = NumberFormat.getNumberInstance();

    static {
        NUMBER_FORMAT_2.setMaximumFractionDigits(2);
        NUMBER_FORMAT_0.setMaximumFractionDigits(0);
    }

    @Override
    public String formatValue(final Object value, final Equipment equipment, final Integer level, final List<Modification> modifications) {
        if (value == null) {
            throw new IllegalArgumentException(this.name() + " value has not been set");
        }
        return switch (this) {
            case DAMAGE, MODIFICATION_SLOTS -> ((LevelValue) value).getValueForLevel(level).toString();
            case GOODS_CAPACITY, ASSETS_CAPACITY, DATA_CAPACITY -> modifications.contains(SuitModification.EXTRA_BACKPACK_CAPACITY) ? String.valueOf((Integer) value * 2) : value.toString();
            case JUMP_ASSIST_BATTERY_CONSUMPTION, JUMP_ASSIST_DRAIN -> NUMBER_FORMAT_0.format((modifications.contains(SuitModification.IMPROVED_JUMP_ASSIST)) ? (Integer) value * 0.75 : value) + "%";
            case JUMP_ASSIST_RECHARGE -> NUMBER_FORMAT_0.format((modifications.contains(SuitModification.IMPROVED_JUMP_ASSIST)) ? (Integer) value * 1.25 : value) + "%";
            case HIP_FIRE_ACCURACY -> (modifications.contains(WeaponModification.HIGHER_ACCURACY_KINETIC) || modifications.contains(WeaponModification.HIGHER_ACCURACY_LASER) || modifications.contains(WeaponModification.HIGHER_ACCURACY_PLASMA) ? getHipFireAccuracy(equipment, (Double) value) : value.toString()) + "%";
            case STABILITY -> (modifications.contains(WeaponModification.STABILITY) ? getStability((Double) value) : value.toString()) + "%";
            case HANDLING -> (modifications.contains(WeaponModification.FASTER_HANDLING) ? String.valueOf((Integer) value + 10) : String.valueOf(value)) + "%";
            case MAGAZINE_SIZE -> modifications.contains(WeaponModification.MAGAZINE_SIZE) ? String.valueOf(Math.round((Integer) value * 1.5)) : value.toString();
            case AMMO_CAPACITY -> NUMBER_FORMAT_0.format(modifications.contains(SuitModification.EXTRA_AMMO_CAPACITY) ? (Integer) value * 1.5 : value) + "%";
            case BATTERY -> NUMBER_FORMAT_2.format(modifications.contains(SuitModification.IMPROVED_BATTERY_CAPACITY) ? (Double) value * 1.5 : value) + "MW";
            case SHIELD_STRENGTH -> ((LevelValue) value).getValueForLevel(level).toString() + "MW";
            case SHIELD_REGEN -> NUMBER_FORMAT_2.format(modifications.contains(SuitModification.FASTER_SHIELD_REGEN) ? (Double) ((LevelValue) value).getValueForLevel(level) * 1.25 : ((LevelValue) value).getValueForLevel(level)) + "MW/s";
            case KINETIC_RESIST, THERMAL_RESIST, PLASMA_RESIST, EXPLOSIVE_RESIST -> {
                if (modifications.contains(SuitModification.DAMAGE_RESISTANCE)) {
                    final Integer resistance = (Integer) ((LevelValue) value).getValueForLevel(level);
                    final long effectiveResistance = Math.round((100 - resistance) * 0.1 + resistance);
                    yield effectiveResistance + "%";
                } else {
                    yield ((LevelValue) value).getValueForLevel(level).toString() + "%";
                }
            }
            case EMERGENCY_AIR -> (modifications.contains(SuitModification.INCREASED_AIR_RESERVES) ? String.valueOf((Integer) value * 5) : value.toString()) + "s";
            case SPRINT_DURATION -> (modifications.contains(SuitModification.INCREASED_SPRINT_DURATION) ? String.valueOf((Integer) value * 2) : value.toString()) + "s";
            case HEADSHOT_DAMAGE -> (modifications.contains(WeaponModification.HEADSHOT_DAMAGE_KINETIC) || modifications.contains(WeaponModification.HEADSHOT_DAMAGE_LASER) || modifications.contains(WeaponModification.HEADSHOT_DAMAGE_PLASMA) ? String.valueOf(Math.round((Integer) value * 1.5)) : value) + "%";
            case EFFECTIVE_RANGE -> (modifications.contains(WeaponModification.GREATER_RANGE_KINETIC) || modifications.contains(WeaponModification.GREATER_RANGE_LASER) || modifications.contains(WeaponModification.GREATER_RANGE_PLASMA) ? String.valueOf(Math.round((Integer) value * 1.5)) : value) + "m";
            case NIGHT_VISION -> (modifications.contains(SuitModification.NIGHT_VISION) || (boolean) value) ? "\u2714" : "\u2718";
            case FOOTSTEPS_AUDIBLE_RANGE -> ((modifications.contains(SuitModification.QUIETER_FOOTSTEPS)) ? ((Integer) value) / 2 : ((Integer) value)) + "m";
            case LOS_ANALYSIS_RANGE -> (modifications.contains(SuitModification.ENHANCED_TRACKING) ? (Integer) value * 2 : value) + "m";
            case LOS_ANALYSIS_TIME -> (modifications.contains(SuitModification.ENHANCED_TRACKING) ? 0 : value) + "s";
            case TOOL_ENERGY_DRAIN_MULTIPLIER -> NUMBER_FORMAT_0.format((modifications.contains(SuitModification.REDUCED_TOOL_BATTERY_CONSUMPTION)) ? (Integer) value * 0.5 : value) + "%";
            case MELEE_DAMAGE -> NUMBER_FORMAT_0.format((modifications.contains(SuitModification.ADDED_MELEE_DAMAGE)) ? (Integer) value * 2.5 : value) + "%";
            case REMOVE_MOVEMENT_SPEED_PENALTY -> (modifications.contains(SuitModification.COMBAT_MOVEMENT_SPEED) && (boolean) value) ? "\u2714" : "\u2718";
            case SILENCED_INSIDE -> (modifications.contains(WeaponModification.NOISE_SUPPRESSOR) || (boolean) value) ? "\u2714" : "\u2718";
            case SILENCED_OUTSIDE -> (modifications.contains(WeaponModification.AUDIO_MASKING) || (boolean) value) ? "\u2714" : "\u2718";
            case RELOAD_SPEED -> (modifications.contains(WeaponModification.RELOAD_SPEED) || (boolean) value) ? "\u2714" : "\u2718";
            case SCOPE -> (modifications.contains(WeaponModification.SCOPE) || (boolean) value) ? "\u2714" : "\u2718";
            case STOWED_RELOADING -> (modifications.contains(WeaponModification.STOWED_RELOADING) || (boolean) value) ? "\u2714" : "\u2718";
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
}
