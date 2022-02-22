package nl.jixxed.eliteodysseymaterials.enums;

import nl.jixxed.eliteodysseymaterials.domain.TradeSearch;
import nl.jixxed.eliteodysseymaterials.domain.TradeSpec;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;

import java.util.function.Predicate;

public enum TradeShow {
    ALL,
    CAN_TRADE,
    ACTIVE;


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
            case CAN_TRADE -> (TradeSpec o) -> !o.getTradeType().equals(TradeType.REQUEST) || o.getOfferAmount() <= StorageService.getMaterials(o.getOfferOdysseyMaterial().getStorageType()).get(o.getOfferOdysseyMaterial()).getTotalValue();
            case ACTIVE -> (TradeSpec o) -> !o.getTradeType().equals(TradeType.REQUEST) || o.isBidFromMe();
        };
    }
}
