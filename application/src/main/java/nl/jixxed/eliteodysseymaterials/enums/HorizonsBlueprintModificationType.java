package nl.jixxed.eliteodysseymaterials.enums;

import nl.jixxed.eliteodysseymaterials.service.LocaleService;

public enum HorizonsBlueprintModificationType {
    //inara
    AMMO_CAPACITY,
    ARMOURED,
    BLAST_RESISTANT,
    CHARGE_ENHANCED,
    DOUBLE_SHOT,
    ENGINE_FOCUSED,
    EXPANDED_PROBE_SCANNING_RADIUS,
    HEAVY_DUTY,
    HIGH_CHARGE_CAPACITY,
    KINETIC_RESISTANT,
    LIGHTWEIGHT,
    LOW_EMISSIONS,
    OVERCHARGED,
    RAPID_CHARGE,
    REINFORCED,
    RESISTANCE_AUGMENTED,
    SHIELDED,
    SPECIALISED,
    SYSTEM_FOCUSED,
    THERMAL_RESISTANT,
    WEAPON_FOCUSED,
    INCREASED_FSD_RANGE,
    SHIELDED_FSD,
    KINETIC_RESISTANT_SHIELDS,
    ENHANCED_LOW_POWER_SHIELDS,
    LIGHTWEIGHT_HULL_REINFORCEMENT,
    KINETIC_RESISTANT_HULL_REINFORCEMENT,
    BLAST_RESISTANT_HULL_REINFORCEMENT,
    THERMAL_RESISTANT_HULL_REINFORCEMENT,
    CLEAN_DRIVE_TUNING,
    LONG_RANGE_SCANNER,
    LIGHT_WEIGHT_SCANNER,
    WIDE_ANGLE_SCANNER,
    FAST_SCANNER,
    EXPANDED_FSD_INTERDICTOR_CAPTURE_ARC,
    SHORT_RANGE_BLASTER,
    HEAVY_DUTY_HULL_REINFORCEMENT,
    DRIVE_STRENGTHENING,
    EFFICIENT_WEAPON,
    THERMAL_RESISTANT_SHIELDS,
    LIGHTWEIGHT_MOUNT,
    STURDY_MOUNT,
    LONG_RANGE_FSD_INTERDICTOR,
    REINFORCED_SHIELDS,
    FASTER_FSD_BOOT_SEQUENCE,
    LONG_RANGE_WEAPON,
    FOCUSED_WEAPON,
    RAPID_FIRE_MODIFICATION,
    OVERCHARGED_WEAPON,
    HIGH_CAPACITY_MAGAZINE,
    DIRTY_DRIVE_TUNING;

    public static HorizonsBlueprintModificationType forName(final String name) {
        try {
            return HorizonsBlueprintModificationType.valueOf(name.toUpperCase());
        } catch (final IllegalArgumentException ex) {
            return null;
        }
    }

    public String getLocalizationKey() {
        return "blueprint.horizons.type." + this.name().toLowerCase();
    }

    public String getDescriptionLocalizationKey() {
        return "blueprint.horizons.type.description." + this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey());
    }

}
