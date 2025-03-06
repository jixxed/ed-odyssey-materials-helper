package nl.jixxed.eliteodysseymaterials.domain;

import nl.jixxed.eliteodysseymaterials.enums.Commodity;

import java.math.BigInteger;
//{ "id":128049162, "Name":"$cobalt_name;", "Name_Localised":"Cobalt", "Category":"$MARKET_category_metals;", "Category_Localised":"Metals", "BuyPrice":2698, "SellPrice":2564, "MeanPrice":3546, "StockBracket":3, "DemandBracket":0, "Stock":101881, "Demand":1, "Consumer":false, "Producer":true, "Rare":false },

public record MarketItem(Commodity commodity, BigInteger buyPrice, BigInteger sellPrice, BigInteger meanPrice, BigInteger stockBracket, BigInteger demandBracket, BigInteger stock, BigInteger demand, boolean consumer, boolean producer, boolean rare) {
    public MarketItem {
        if (commodity == null) {
            throw new IllegalArgumentException("Commodity cannot be null");
        }
    }

    public boolean isSelling() {
        return stock.intValue() > 0;
    }

    public boolean isBuying() {
        return demand.intValue() > 0;
    }
}
