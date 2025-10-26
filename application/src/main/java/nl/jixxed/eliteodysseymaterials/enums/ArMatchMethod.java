package nl.jixxed.eliteodysseymaterials.enums;

import nl.jixxed.eliteodysseymaterials.service.LocaleService;

public enum ArMatchMethod {
    EXACT, FUZZY;

    public static ArMatchMethod forName(final String name) {
        return ArMatchMethod.valueOf(name.toUpperCase());
    }

    public String getLocalizationKey() {
        return "ar.match.method." + this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey());
    }
}
