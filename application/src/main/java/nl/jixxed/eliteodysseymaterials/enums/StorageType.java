package nl.jixxed.eliteodysseymaterials.enums;

public enum StorageType {
    GOOD,
    DATA,
    ASSET,
    TRADE,
    OTHER;

    public static StorageType forMaterial(final Material material) {
        return StorageType.valueOf(material.getClass().getSimpleName().toUpperCase());
    }

    public String getLocalizationKey() {
        return "storage.type.name." + this.name().toLowerCase();
    }
}
