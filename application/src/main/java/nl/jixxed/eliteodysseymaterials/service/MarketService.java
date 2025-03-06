package nl.jixxed.eliteodysseymaterials.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.MarketItem;
import nl.jixxed.eliteodysseymaterials.enums.Commodity;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Market.Market;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.MarketUpdatedEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class MarketService {
    private static final Map<Commodity, MarketItem> MARKET_ITEMS = new HashMap<>();
    @Getter
    private static boolean fleetCarrier;

    public static void updateMarket(Market event) {
        MARKET_ITEMS.clear();
        fleetCarrier = "FleetCarrier".equals(event.getStationType());
        event.getItems().ifPresent(items -> items.forEach(item -> {
            final String name = item.getName().substring(1, item.getName().length() - 6);
            try {
                final Commodity commodity = Commodity.forName(name);
                final MarketItem marketItem = new MarketItem(commodity,
                        item.getBuyPrice(), item.getSellPrice(), item.getMeanPrice(),
                        item.getStockBracket(), item.getDemandBracket(),
                        item.getStock(), item.getDemand(),
                        item.getConsumer(), item.getProducer(), item.getRare());
                MARKET_ITEMS.put(commodity, marketItem);
            } catch (IllegalArgumentException ex) {
                log.error("Unknown commodity: {}", name);
            }
        }));
        EventService.publish(new MarketUpdatedEvent());
    }

    public static Optional<MarketItem> getMarketItem(Commodity commodity) {
        return Optional.ofNullable(MARKET_ITEMS.get(commodity));
    }

    public static boolean sells(Commodity commodity) {
        final Optional<MarketItem> marketItem = getMarketItem(commodity);
        return marketItem.map(MarketItem::isSelling).orElse(false);
    }

    public static boolean buys(Commodity commodity) {
        final Optional<MarketItem> marketItem = getMarketItem(commodity);
        return marketItem.map(MarketItem::isBuying).orElse(false);
    }
}
