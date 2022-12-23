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
    WEAPONS,
    POWERPLAY,
    UNKNOWN;

    public String getLocalizationKey() {
        return "material.commoditytype." + this.name().toLowerCase();
    }

    public boolean isUnknown() {
        return this == CommodityType.UNKNOWN;
    }


    public String getImagePath() {
        return "/images/ships/materials/commodities/" + name().toLowerCase() + ".png";
    }
}
