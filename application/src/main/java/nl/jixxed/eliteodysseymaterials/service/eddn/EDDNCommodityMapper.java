/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.service.eddn;

import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.commodity.Item;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.commodity.Message;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Market.Market;

import java.util.HashSet;
import java.util.Set;

public class EDDNCommodityMapper extends EDDNMapper {
    @SuppressWarnings("unchecked")
    public static Message mapToEDDN(final Market market, final Expansion expansion) {
        return new Message.MessageBuilder()
                .withTimestamp(market.getTimestamp())
                .withSystemName(market.getStarSystem())
                .withStationName(market.getStationName())
                .withStationType(market.getStationType())
                .withCarrierDockingAccess(market.getCarrierDockingAccess().orElse(null))
                .withEconomies(null)//capi only field
                .withProhibited(null)//capi only field
                .withCommodities(mapToOptionalEmptyIfEmptyList(market.getItems())
                        .map(items -> items.stream()
                                .map(item -> new Item.ItemBuilder()
                                        .withName(cleanName(item.getName()))
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

    static String cleanName(final String name) {
        if (name.startsWith("$") && name.endsWith("_name;")) {
            return name.substring(1, name.length() - 6);
        }
        return name;
    }

    private static Set<String> getStatusFlags(final nl.jixxed.eliteodysseymaterials.schemas.journal.Market.Item item) {
        final Set<String> flags = new HashSet<>();
        if (Boolean.TRUE.equals(item.getConsumer())) {
            flags.add("Consumer");
        }
        if (Boolean.TRUE.equals(item.getProducer())) {
            flags.add("Producer");
        }
        if (Boolean.TRUE.equals(item.getRare())) {
            flags.add("rare");
        }
        if (!flags.isEmpty()) {
            return flags;
        }

        return null;
    }
}
