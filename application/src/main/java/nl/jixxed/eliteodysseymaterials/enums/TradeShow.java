package nl.jixxed.eliteodysseymaterials.enums;

import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.TradeSearch;
import nl.jixxed.eliteodysseymaterials.domain.TradeSpec;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.function.Predicate;

public enum TradeShow {
    ALL,
    CAN_TRADE,
    ACTIVE;

    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    public String getLocalizationKey() {
        return "trade.search.filter." + this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey());
    }

    public static Predicate<TradeSpec> getFilter(final TradeSearch search) {
        return switch (search.getTradeShow()) {
            case ALL -> (TradeSpec o) -> true;
            case CAN_TRADE -> (TradeSpec o) -> !o.getTradeType().equals(TradeType.REQUEST) || o.getOfferAmount() <= APPLICATION_STATE.getMaterials(o.getOfferMaterial().getStorageType()).get(o.getOfferMaterial()).getTotalValue();
            case ACTIVE -> (TradeSpec o) -> !o.getTradeType().equals(TradeType.REQUEST) || o.isBidFromMe();
        };
    }
}
