package nl.jixxed.eliteodysseymaterials.enums;

public enum StorageType {
    GOOD, DATA, ASSET, OTHER;

    public static StorageType forMaterial(final Material material) {
        return StorageType.valueOf(material.getClass().getSimpleName().toUpperCase());
    }
}
