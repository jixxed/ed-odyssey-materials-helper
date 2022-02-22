package nl.jixxed.eliteodysseymaterials.enums;

public enum OdysseyStorageType implements StorageType {
    GOOD,
    DATA,
    ASSET,
    TRADE,
    CONSUMABLE,
    OTHER;

    public static OdysseyStorageType forMaterial(final OdysseyMaterial odysseyMaterial) {
        return OdysseyStorageType.valueOf(odysseyMaterial.getClass().getSimpleName().toUpperCase());
    }

    public String getLocalizationKey() {
        return "storage.type.name." + this.name().toLowerCase();
    }
}
