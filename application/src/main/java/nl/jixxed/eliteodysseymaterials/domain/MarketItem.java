/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
