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

import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIcon;
@RequiredArgsConstructor
public enum CommodityType {
    CHEMICALS(new EdAwesomeIcon[]{EdAwesomeIcon.COMMODITIES_CHEMICALS}),
    CONSUMER_ITEMS(new EdAwesomeIcon[]{EdAwesomeIcon.COMMODITIES_CONSUMER_ITEMS}),
    FOODS(new EdAwesomeIcon[]{EdAwesomeIcon.COMMODITIES_FOODS}),
    INDUSTRIAL_MATERIALS(new EdAwesomeIcon[]{EdAwesomeIcon.COMMODITIES_INDUSTRIAL_MATERIALS}),
    LEGAL_DRUGS(new EdAwesomeIcon[]{EdAwesomeIcon.COMMODITIES_LEGAL_DRUGS}),
    MACHINERY(new EdAwesomeIcon[]{EdAwesomeIcon.COMMODITIES_MACHINERY}),
    MEDICINES(new EdAwesomeIcon[]{EdAwesomeIcon.COMMODITIES_MEDICINES}),
    METALS(new EdAwesomeIcon[]{EdAwesomeIcon.COMMODITIES_METALS}),
    MINERALS(new EdAwesomeIcon[]{EdAwesomeIcon.COMMODITIES_MINERALS}),
    NONMARKETABLE(new EdAwesomeIcon[]{EdAwesomeIcon.COMMODITIES_LIMPETS_1, EdAwesomeIcon.COMMODITIES_LIMPETS_2}),
    SALVAGE(new EdAwesomeIcon[]{EdAwesomeIcon.COMMODITIES_SALVAGE}),
    SLAVERY(new EdAwesomeIcon[]{EdAwesomeIcon.COMMODITIES_SLAVERY}),
    TECHNOLOGY(new EdAwesomeIcon[]{EdAwesomeIcon.COMMODITIES_TECHNOLOGY}),
    TEXTILES(new EdAwesomeIcon[]{EdAwesomeIcon.COMMODITIES_TEXTILES}),
    WASTE(new EdAwesomeIcon[]{EdAwesomeIcon.COMMODITIES_WASTE}),
    WEAPONS(new EdAwesomeIcon[]{EdAwesomeIcon.COMMODITIES_WEAPONS}),
    POWERPLAY(new EdAwesomeIcon[]{EdAwesomeIcon.COMMODITIES_TECHNOLOGY}),
    UNKNOWN(new EdAwesomeIcon[]{EdAwesomeIcon.COMMODITIES_TECHNOLOGY});
    private final EdAwesomeIcon[] icons;
    public String getLocalizationKey() {
        return "material.commoditytype." + this.name().toLowerCase();
    }

    public boolean isUnknown() {
        return this == CommodityType.UNKNOWN;
    }

    public EdAwesomeIcon[] getIcons() {
        return icons;
    }

}
