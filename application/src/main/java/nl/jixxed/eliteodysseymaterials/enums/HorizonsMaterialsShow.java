package nl.jixxed.eliteodysseymaterials.enums;

import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsMaterialsSearch;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.function.Predicate;

public enum HorizonsMaterialsShow {
    ALL,
    RAW,
    ENCODED,
    MANUFACTURED,
    GUARDIAN,
    THARGOID,
    HUMAN;

    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    public String getLocalizationKey() {
        return "search.filter." + this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey());
    }

    @SuppressWarnings("java:S1452")
    public static Predicate<HorizonsMaterial> getFilter(final HorizonsMaterialsSearch search) {
        return switch (search.getMaterialsShow()) {
            case ALL -> (HorizonsMaterial o) -> true;
            case RAW -> (HorizonsMaterial o) -> o instanceof Raw;
            case ENCODED -> (HorizonsMaterial o) -> o instanceof Encoded;
            case MANUFACTURED -> (HorizonsMaterial o) -> o instanceof Manufactured;
            case GUARDIAN -> (HorizonsMaterial o) -> o.getMaterialType() == HorizonsMaterialType.GUARDIAN;
            case THARGOID -> (HorizonsMaterial o) -> o.getMaterialType() == HorizonsMaterialType.THARGOID;
            case HUMAN -> (HorizonsMaterial o) -> o.getMaterialType() != HorizonsMaterialType.GUARDIAN && o.getMaterialType() != HorizonsMaterialType.THARGOID;
        };
    }
}
