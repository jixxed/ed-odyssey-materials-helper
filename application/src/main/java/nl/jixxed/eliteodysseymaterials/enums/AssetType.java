package nl.jixxed.eliteodysseymaterials.enums;

public enum AssetType {
    CHEMICAL, CIRCUIT, TECH;


    public String getLocalizationKey() {
        return "material.asset.type." + this.name().toLowerCase();
    }

}
