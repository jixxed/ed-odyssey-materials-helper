package nl.jixxed.eliteodysseymaterials.enums;

public enum OdysseyStorageType {
    GOOD,
    DATA,
    ASSET,
    TRADE,
    CONSUMABLE,
    OTHER;

    public static OdysseyStorageType forMaterial(final Material material) {
        return OdysseyStorageType.valueOf(material.getClass().getSimpleName().toUpperCase());
    }

    public String getLocalizationKey() {
        return "storage.type.name." + this.name().toLowerCase();
    }
}
