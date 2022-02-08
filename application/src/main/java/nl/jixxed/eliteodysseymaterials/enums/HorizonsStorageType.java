package nl.jixxed.eliteodysseymaterials.enums;

public enum HorizonsStorageType {
    RAW,
    ENCODED,
    MANUFACURED;

    public static HorizonsStorageType forMaterial(final Material material) {
        return HorizonsStorageType.valueOf(material.getClass().getSimpleName().toUpperCase());
    }

    public String getLocalizationKey() {
        return "storage.type.name." + this.name().toLowerCase();
    }
}
