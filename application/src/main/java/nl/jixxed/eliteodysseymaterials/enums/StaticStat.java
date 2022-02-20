package nl.jixxed.eliteodysseymaterials.enums;

import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.constants.UnitConstants;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.List;

@RequiredArgsConstructor
public enum StaticStat implements Stat {

    HEALTH(StatGroup.GENERAL),
    MASS(StatGroup.GENERAL),
    PRIMARY_SLOTS(StatGroup.GENERAL),
    SECONDARY_SLOTS(StatGroup.GENERAL),
    CLASS(StatGroup.GENERAL),
    TYPE(StatGroup.GENERAL),
    DAMAGE_TYPE(StatGroup.GENERAL),
    RATE_OF_FIRE(StatGroup.DAMAGE),
    ENERGYLINK_RECHARGE_TIME(StatGroup.POWER),
    SHIELD_POWER_USAGE(StatGroup.POWER),
    LIGHTS_POWER_USAGE(StatGroup.POWER),
    SPRINT_SPEED(StatGroup.MOVEMENT),
    SPRINT_SPEED_PISTOL(StatGroup.MOVEMENT),
    WALK_SPEED(StatGroup.MOVEMENT),
    WALK_SPEED_PISTOL(StatGroup.MOVEMENT),
    LATERAL_SPEED(StatGroup.MOVEMENT),
    FIRE_MODE(StatGroup.GENERAL);
    private final StatGroup statGroup;

    static {
        NUMBER_FORMAT_2.setMaximumFractionDigits(2);
        NUMBER_FORMAT_0.setMaximumFractionDigits(0);
    }

    @Override
    public String formatValue(final Object value, final Equipment equipment, final Integer level, final List<Modification> modifications) {
        return switch (this) {
            case RATE_OF_FIRE -> value + UnitConstants.PER_SECOND;
            case HEALTH, PRIMARY_SLOTS, SECONDARY_SLOTS -> value.toString();
            case CLASS, TYPE, DAMAGE_TYPE, FIRE_MODE -> LocaleService.getLocalizedStringForCurrentLocale(value.toString());
            case MASS -> value.toString() + UnitConstants.KILOGRAM;
            case SHIELD_POWER_USAGE, LIGHTS_POWER_USAGE -> NUMBER_FORMAT_2.format(value) + UnitConstants.KILOWATT_PER_SECOND;
            case ENERGYLINK_RECHARGE_TIME -> NUMBER_FORMAT_2.format(value) + UnitConstants.SECOND;
            case SPRINT_SPEED, SPRINT_SPEED_PISTOL, WALK_SPEED, WALK_SPEED_PISTOL -> NUMBER_FORMAT_2.format(value) + UnitConstants.METER_PER_SECOND;
            case LATERAL_SPEED -> value + UnitConstants.PERCENT;

        };
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
