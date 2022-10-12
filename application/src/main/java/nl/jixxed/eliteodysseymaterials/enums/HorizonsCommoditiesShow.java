package nl.jixxed.eliteodysseymaterials.enums;

import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.CommoditiesSearch;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.horizons.commodities.HorizonsCommodityCard;

import java.util.function.Predicate;

public enum HorizonsCommoditiesShow {
    ALL,
    ALL_WITH_STOCK,
    SHIP,
    FLEETCARRIER;

    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    public String getLocalizationKey() {
        return "search.filter." + this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey());
    }

    @SuppressWarnings("java:S1452")
    public static Predicate<HorizonsCommodityCard> getFilter(final CommoditiesSearch search) {
        return switch (search.getCommoditiesShow()) {
            case ALL -> (HorizonsCommodityCard o) -> true;
            case ALL_WITH_STOCK -> (HorizonsCommodityCard o) -> o.getFleetcarrierAmount() > 0 || o.getShipAmount() > 0;
            case SHIP -> (HorizonsCommodityCard o) -> o.getShipAmount() > 0;
            case FLEETCARRIER -> (HorizonsCommodityCard o) -> o.getFleetcarrierAmount() > 0;
        };
    }
}
