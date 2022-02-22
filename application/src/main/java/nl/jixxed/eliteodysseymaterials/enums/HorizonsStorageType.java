package nl.jixxed.eliteodysseymaterials.enums;

public enum HorizonsStorageType implements StorageType {
    RAW,
    ENCODED,
    MANUFACTURED,
    OTHER;

    public static HorizonsStorageType forMaterial(final HorizonsMaterial horizonsMaterial) {
        return HorizonsStorageType.valueOf(horizonsMaterial.getClass().getSimpleName().toUpperCase());
    }

    public String getLocalizationKey() {
        return "storage.type.horizons.name." + this.name().toLowerCase();
    }
}
