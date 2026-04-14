/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum Refinable {
    BAUXITE_ALUMINIUM(RegularCommodity.BAUXITE, RegularCommodity.ALUMINIUM),
    BERTRANDITE_BERYLLIUM(RegularCommodity.BERTRANDITE, RegularCommodity.BERYLLIUM),
    COLTAN_TANTALUM(RegularCommodity.COLTAN, RegularCommodity.TANTALUM),
    GALLITE_GALLIUM(RegularCommodity.GALLITE, RegularCommodity.GALLIUM),
    HAEMATITE_STEEL(RegularCommodity.HAEMATITE, RegularCommodity.STEEL),
    INDITE_INDIUM(RegularCommodity.INDITE, RegularCommodity.INDIUM),
    LEPIDOLITE_LITHIUM(RegularCommodity.LEPIDOLITE, RegularCommodity.LITHIUM),
    RUTILE_TITANIUM(RegularCommodity.RUTILE, RegularCommodity.TITANIUM),
    URANINITE_URANIUM(RegularCommodity.URANINITE, RegularCommodity.URANIUM);

    private final Commodity commodityFrom;
    private final Commodity commodityTo;

    public static Optional<Refinable> getRefinable(final Commodity commodity) {
        if (commodity.getCommodityType().equals(CommodityType.METALS) || commodity.getCommodityType().equals(CommodityType.MINERALS)) {
            for (final Refinable refinable : Refinable.values()) {
                if (refinable.commodityFrom.equals(commodity) || refinable.commodityTo.equals(commodity)) {
                    return Optional.of(refinable);
                }
            }
        }
        return Optional.empty();
    }

    public Integer getCommodityIn() {
        return 2;
    }

    public Integer getCommodityOut() {
        return 1;
    }
}
