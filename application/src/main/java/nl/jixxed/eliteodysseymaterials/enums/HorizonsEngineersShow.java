package nl.jixxed.eliteodysseymaterials.enums;

import nl.jixxed.eliteodysseymaterials.domain.HorizonsEngineersSearch;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.horizons.engineers.HorizonsEngineerCard;

import java.util.function.Predicate;

public enum HorizonsEngineersShow {
    ALL,
    COLONIA,
    BUBBLE;


    public String getLocalizationKey() {
        return "search.filter." + this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey());
    }

    @SuppressWarnings("java:S1452")
    public static Predicate<HorizonsEngineerCard> getFilter(final HorizonsEngineersSearch search) {
        return switch (search.getEngineersShow()) {
            case ALL -> (HorizonsEngineerCard o) -> true;
            case COLONIA -> (HorizonsEngineerCard o) -> o.getEngineer().isInColonia();
            case BUBBLE -> (HorizonsEngineerCard o) -> !o.getEngineer().isInColonia();
        };
    }
}
