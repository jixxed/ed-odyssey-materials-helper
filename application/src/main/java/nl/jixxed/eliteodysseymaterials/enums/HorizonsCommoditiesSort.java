package nl.jixxed.eliteodysseymaterials.enums;

import nl.jixxed.eliteodysseymaterials.domain.CommoditiesSearch;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.horizons.commodities.HorizonsCommodityCard;

import java.util.Comparator;

public enum HorizonsCommoditiesSort {

    ALPHABETICAL,
    QUANTITY_SHIP,
    QUANTITY_FLEETCARRIER,
    QUANTITY_TOTAL;

    public String getLocalizationKey() {
        return "search.sort." + this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey());
    }

    @SuppressWarnings("java:S1452")
    public static Comparator<HorizonsCommodityCard> getSort(final CommoditiesSearch search) {
        return switch (search.getCommoditiesSort()) {
            case ALPHABETICAL ->
                    Comparator.comparing((HorizonsCommodityCard o) -> LocaleService.getLocalizedStringForCurrentLocale(o.getCommodity().getLocalizationKey()));
            case QUANTITY_SHIP -> Comparator.comparing(HorizonsCommodityCard::getShipAmount).reversed();
            case QUANTITY_FLEETCARRIER -> Comparator.comparing(HorizonsCommodityCard::getFleetcarrierAmount).reversed();
            case QUANTITY_TOTAL ->
                    Comparator.comparing((HorizonsCommodityCard o) -> o.getShipAmount() + o.getFleetcarrierAmount()).reversed();
        };
    }
}
