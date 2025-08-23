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
