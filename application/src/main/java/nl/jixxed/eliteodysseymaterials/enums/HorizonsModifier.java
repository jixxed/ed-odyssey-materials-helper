package nl.jixxed.eliteodysseymaterials.enums;

public enum HorizonsModifier {
    DAMAGE,
    THERMAL_LOAD,
    RELOAD_TIME,
    AMMO_CAPACITY,
    MASS,
    OPTIMAL_MASS,
    INTEGRITY,
    BOOT_TIME,
    POWER_DRAW,
    //edengineer
    RANGE,
    SPIN_UP_TIME,
    OPTIMAL_MULTIPLIER,
    RATE_OF_FIRE,
    SHIELD_BOOST,
    BURST_SIZE,
    BURST_RATE_OF_FIRE,
    FACING_LIMIT,
    DURATION,
    SHIELD_REINFORCEMENT,
    OPTIMAL_STRENGTH,
    AMMO_MAXIMUM,
    BROKEN_REGEN_RATE,
    SCAN_ANGLE,
    SCAN_RANGE,
    SCAN_TIME,
    PROBE_RADIUS,
    MAXIMUM_RANGE,
    ALL_RESISTANCES,
    HULL_REINFORCEMENT,
    DISTRIBUTOR_DRAW,
    EXPLOSIVE_RESISTANCE,
    KINETIC_RESISTANCE,
    THERMAL_RESISTANCE,
    POWER_GENERATION,
    HULL_BOOST,
    POWER_CAPACITY,
    POWER_RECHARGE,
    WEAPONS_CAPACITY,
    WEAPONS_RECHARGE,
    SYSTEMS_CAPACITY,
    SYSTEMS_RECHARGE,
    ENGINES_CAPACITY,
    ENGINES_RECHARGE,
    HEAT_EFFICIENCY,
    DAMAGE_FALLOFF_START,
    ARMOUR_PIERCING,
    SHOT_SPEED,
    JITTER,
    CLIP_SIZE;

    public static HorizonsModifier forName(final String name) {
        try {
            return HorizonsModifier.valueOf(name.toUpperCase());
        } catch (final IllegalArgumentException ex) {
            return null;
        }
    }

    public String getLocalizationKey() {
        return "modifier.horizons.name." + this.name().toLowerCase();
    }
}
