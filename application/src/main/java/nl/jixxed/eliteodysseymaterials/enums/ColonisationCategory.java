package nl.jixxed.eliteodysseymaterials.enums;

import nl.jixxed.eliteodysseymaterials.service.LocaleService;

public enum ColonisationCategory {
    STAR_PORT,
    OUTPOST,
    INSTALLATION,
    PLANETARY_PORT,
    SETTLEMENT,
    HUB;

    public String getLocalizationKey() {
        return "colonisation.category." + this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey());
    }
}
