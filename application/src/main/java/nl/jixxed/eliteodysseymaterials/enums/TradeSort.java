package nl.jixxed.eliteodysseymaterials.enums;

import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.TradeSearch;
import nl.jixxed.eliteodysseymaterials.domain.TradeSpec;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.Comparator;

public enum TradeSort {
    NAME_OFFER,
    NAME_RECEIVE,
    QUANTITY_OFFER,
    QUANTITY_RECEIVE;

    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    public String getLocalizationKey() {
        return "trade.search.sort." + this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey());
    }

    public static Comparator<TradeSpec> getSort(final TradeSearch search) {
        return switch (search.getTradeSort()) {
            case NAME_OFFER -> Comparator.comparing((TradeSpec o) -> LocaleService.getLocalizedStringForCurrentLocale(o.getOfferOdysseyMaterial().getLocalizationKey()));
            case NAME_RECEIVE -> Comparator.comparing((TradeSpec o) -> LocaleService.getLocalizedStringForCurrentLocale(o.getReceiveOdysseyMaterial().getLocalizationKey()));
            case QUANTITY_OFFER -> Comparator.comparing(TradeSpec::getOfferAmount).reversed();
            case QUANTITY_RECEIVE -> Comparator.comparing(TradeSpec::getReceiveAmount).reversed();
        };
    }
}
