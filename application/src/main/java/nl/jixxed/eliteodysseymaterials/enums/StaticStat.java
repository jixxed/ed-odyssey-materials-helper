package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.constants.UnitConstants;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.List;

@RequiredArgsConstructor
@Getter
public enum StaticStat implements Stat {
    //suit
    HEALTH(StatGroup.GENERAL, 2),
    MASS(StatGroup.GENERAL, 3),
    PRIMARY_SLOTS(StatGroup.GENERAL, 4),
    SECONDARY_SLOTS(StatGroup.GENERAL, 5),
    //weapon
    CLASS(StatGroup.GENERAL, 2),
    TYPE(StatGroup.GENERAL, 3),
    DAMAGE_TYPE(StatGroup.GENERAL, 4),
    FIRE_MODE(StatGroup.GENERAL, 5),
    SPRINT_SPEED(StatGroup.MOVEMENT, 1),
    SPRINT_SPEED_PISTOL(StatGroup.MOVEMENT, 2),
    WALK_SPEED(StatGroup.MOVEMENT, 6),
    WALK_SPEED_PISTOL(StatGroup.MOVEMENT, 7),
    LATERAL_SPEED(StatGroup.MOVEMENT, 15),
    RATE_OF_FIRE(StatGroup.DAMAGE, 2),
    SHIELD_POWER_USAGE(StatGroup.POWER, 6),
    LIGHTS_POWER_USAGE(StatGroup.POWER, 7),
    NIGHTVISION_POWER_USAGE(StatGroup.POWER, 8),
    PROFILESCANNER_SCAN_DURATION(StatGroup.POWER, 10),
    PROFILESCANNER_CLONE_DURATION(StatGroup.POWER, 11),
    ENERGYLINK_RECHARGE_RATE(StatGroup.POWER, 12),
    ENERGYLINK_DISCHARGE_RATE(StatGroup.POWER, 13),
    ENERGYLINK_DISCHARGE_DURATION(StatGroup.POWER, 14);
    private final StatGroup statGroup;
    private final Integer order;

    static {
        NUMBER_FORMAT_2.setMaximumFractionDigits(2);
        NUMBER_FORMAT_1.setMaximumFractionDigits(1);
        NUMBER_FORMAT_0.setMaximumFractionDigits(0);
    }

    @Override
    public String formatValue(final Object value, final Equipment equipment, final Integer level, final List<Modification> modifications) {
        return switch (this) {
            case RATE_OF_FIRE -> value + UnitConstants.PER_SECOND;
            case HEALTH, PRIMARY_SLOTS, SECONDARY_SLOTS -> value.toString();
            case CLASS, TYPE, DAMAGE_TYPE, FIRE_MODE -> LocaleService.getLocalizedStringForCurrentLocale(value.toString());
            case MASS -> value.toString() + UnitConstants.KILOGRAM;
            case ENERGYLINK_RECHARGE_RATE -> NUMBER_FORMAT_2.format(value) + UnitConstants.MEGAWATT_PER_SECOND;
            case ENERGYLINK_DISCHARGE_RATE, SHIELD_POWER_USAGE, LIGHTS_POWER_USAGE, NIGHTVISION_POWER_USAGE -> NUMBER_FORMAT_2.format(value) + UnitConstants.KILOWATT_PER_SECOND;
            case ENERGYLINK_DISCHARGE_DURATION, PROFILESCANNER_SCAN_DURATION, PROFILESCANNER_CLONE_DURATION -> NUMBER_FORMAT_2.format(value) + UnitConstants.SECOND;
            case SPRINT_SPEED, SPRINT_SPEED_PISTOL, WALK_SPEED, WALK_SPEED_PISTOL -> NUMBER_FORMAT_2.format(value) + UnitConstants.METER_PER_SECOND;
            case LATERAL_SPEED -> value + UnitConstants.PERCENT;

        };
    }

    @Override
    public String getLocalizationKey() {
        return "loadout.stat.name." + this.name().toLowerCase();
    }

}
