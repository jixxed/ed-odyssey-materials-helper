package nl.jixxed.eliteodysseymaterials.enums;

public enum HorizonsStorageType implements StorageType {
    RAW,
    ENCODED,
    MANUFACTURED,
    COMMODITY,
    OTHER,
    UNKNOWN;

    public static HorizonsStorageType forMaterial(final HorizonsMaterial horizonsMaterial) {
        return HorizonsStorageType.valueOf(horizonsMaterial.getClass().getSimpleName().toUpperCase());
    }

    public static HorizonsStorageType forName(final String name) {
        try {
            return HorizonsStorageType.valueOf(name.toUpperCase());
        } catch (final IllegalArgumentException ex) {
            return HorizonsStorageType.UNKNOWN;
        }

    }

    public String getLocalizationKey() {
        return "storage.type.horizons.name." + this.name().toLowerCase();
    }
}
