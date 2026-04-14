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

public sealed interface Commodity extends HorizonsMaterial permits RegularCommodity, RareCommodity {

    @Override
    default String getLocalizationKey() {
        return "material.commodity." + this.name().toLowerCase();
    }

    default String getDescriptionLocalizationKey() {
        return "material.commodity.description." + this.name().toLowerCase();
    }

    @Override
    default HorizonsMaterialType getMaterialType() {
        return HorizonsMaterialType.COMMODITY;
    }

    static Commodity forName(final String name) {
        try {
            return RareCommodity.valueOf(name.toUpperCase());
        } catch (final IllegalArgumentException ex) {
            return RegularCommodity.forName(name);
        }
    }

    @Override
    default Rarity getRarity() {
        return Rarity.UNKNOWN;
    }

    @Override
    default int getMaxAmount() {
        return 999;
    }

    @Override
    default HorizonsStorageType getStorageType() {
        return HorizonsStorageType.COMMODITY;
    }
    CommodityType getCommodityType();

    boolean isPurchasable();
}
