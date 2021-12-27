package nl.jixxed.eliteodysseymaterials.enums;

import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.List;

public enum StaticStat implements Stat {

    HEALTH,
    MASS,
    PRIMARY_SLOTS,
    SECONDARY_SLOTS,
    CLASS,
    TYPE,
    DAMAGE_TYPE,
    RATE_OF_FIRE,
    FIRE_MODE;


    @Override
    public String formatValue(final Object value, final Equipment equipment, final Integer level, final List<Modification> modifications) {
        return switch (this) {
            case RATE_OF_FIRE -> value + "/s";
            case HEALTH, PRIMARY_SLOTS, SECONDARY_SLOTS -> value.toString();
            case CLASS, TYPE, DAMAGE_TYPE, FIRE_MODE -> LocaleService.getLocalizedStringForCurrentLocale(value.toString());
            case MASS -> value.toString() + "kg";
        };
    }


    @Override
    public String getLocalizationKey() {
        return "loadout.stat.name." + this.name().toLowerCase();
    }
}
