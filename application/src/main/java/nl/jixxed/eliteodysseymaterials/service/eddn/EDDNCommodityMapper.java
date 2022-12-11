package nl.jixxed.eliteodysseymaterials.service.eddn;

import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.commodity.Item;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.commodity.Message;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Market.Market;

import java.util.HashSet;
import java.util.Set;

public class EDDNCommodityMapper extends EDDNMapper {
    public static Message mapToEDDN(final Market market, final Expansion expansion) {
        return new Message.MessageBuilder()
                .withTimestamp(market.getTimestamp())
                .withEvent(market.getEvent())
                .withSystemName(market.getStarSystem())
                .withStationName(market.getStationName())
                .withCommodities(mapToNullIfEmptyList(market.getItems())
                        .map(items -> items.stream()
                                .map(item -> new Item.ItemBuilder()
                                        .withName(item.getName())
                                        .withBuyPrice(item.getBuyPrice())
                                        .withDemand(item.getDemand())
                                        .withDemandBracket(item.getDemandBracket())
                                        .withSellPrice(item.getSellPrice())
                                        .withStock(item.getStock())
                                        .withStockBracket(item.getStockBracket())
                                        .withMeanPrice(item.getMeanPrice())
                                        .withStatusFlags(getStatusFlags(item))
                                        .build())
                                .toList())
                        .orElse(null))
                .withHorizons(expansion.equals(Expansion.HORIZONS) || expansion.equals(Expansion.ODYSSEY))
                .withOdyssey(expansion.equals(Expansion.ODYSSEY))
                .withMarketId(market.getMarketID())
                .build();
    }

    private static Set<String> getStatusFlags(final nl.jixxed.eliteodysseymaterials.schemas.journal.Market.Item item) {
        final Set<String> flags = new HashSet<>();
        if(Boolean.TRUE.equals(item.getConsumer())){
            flags.add("Consumer");
        }
        if(Boolean.TRUE.equals(item.getProducer())){
            flags.add("Producer");
        }
        if(Boolean.TRUE.equals(item.getRare())){
            flags.add("rare");
        }
        if(!flags.isEmpty()) {
            return flags;
        }

        return null;
    }
}
