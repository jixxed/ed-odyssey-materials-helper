package nl.jixxed.eliteodysseymaterials.enums;

public enum CommodityType {
    CHEMICALS,
    CONSUMER_ITEMS,
    FOODS,
    INDUSTRIAL_MATERIALS,
    LEGAL_DRUGS,
    MACHINERY,
    MEDICINES,
    METALS,
    MINERALS,
    NONMARKETABLE,
    SALVAGE,
    SLAVERY,
    TECHNOLOGY,
    TEXTILES,
    WASTE,
    WEAPONS;

    public String getLocalizationKey() {
        return "material.commoditytype." + this.name().toLowerCase();
    }
}
