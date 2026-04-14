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

import nl.jixxed.eliteodysseymaterials.domain.CommoditiesSearch;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.horizons.commodities.HorizonsCommodityCard;

import java.util.Comparator;

public enum HorizonsCommoditiesSort {

    ALPHABETICAL,
    QUANTITY_SHIP,
    QUANTITY_FLEETCARRIER,
    QUANTITY_SQUADRONCARRIER,
    QUANTITY_TOTAL;

    public String getLocalizationKey() {
        return "search.sort." + this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey());
    }

    @SuppressWarnings("java:S1452")
    public static Comparator<HorizonsCommodityCard> getSort(final CommoditiesSearch search) {
        return switch (search.getCommoditiesSort()) {
            case ALPHABETICAL ->
                    Comparator.comparing((HorizonsCommodityCard o) -> LocaleService.getLocalizedStringForCurrentLocale(o.getCommodity().getLocalizationKey()));
            case QUANTITY_SHIP -> Comparator.comparing(HorizonsCommodityCard::getShipAmount).reversed();
            case QUANTITY_FLEETCARRIER -> Comparator.comparing(HorizonsCommodityCard::getFleetCarrierAmount).reversed();
            case QUANTITY_SQUADRONCARRIER -> Comparator.comparing(HorizonsCommodityCard::getSquadronCarrierAmount).reversed();
            case QUANTITY_TOTAL ->
                    Comparator.comparing((HorizonsCommodityCard o) -> o.getShipAmount() + o.getFleetCarrierAmount()+ o.getSquadronCarrierAmount()).reversed();
        };
    }
}
