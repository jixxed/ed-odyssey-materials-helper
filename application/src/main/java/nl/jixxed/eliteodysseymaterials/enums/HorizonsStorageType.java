package nl.jixxed.eliteodysseymaterials.enums;

public enum HorizonsStorageType implements StorageType {
    RAW,
    ENCODED,
    MANUFACTURED,
    COMMODITY,
    OTHER,
    UNKNOWN;

    public static HorizonsStorageType forMaterial(final HorizonsMaterial horizonsMaterial) {
        return HorizonsStorageType.valueOf(remapCommodity(horizonsMaterial.getClass().getSimpleName().toUpperCase()));
    }

    private static String remapCommodity(String type) {
        //if REGULARCOMMODITY or RARECOMMODITY, return COMMODITY
        if (type.contains("COMMODITY")) {
            return "COMMODITY";
        }
        return type;
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
