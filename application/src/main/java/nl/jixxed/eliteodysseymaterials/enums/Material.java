package nl.jixxed.eliteodysseymaterials.enums;

public interface Material {
    default StorageType getStorageType() {
        return StorageType.OTHER;
    }

    static Material subtypeForName(final String name) {
        final String fixedName = hotfixName(name);
        Material material = Data.forName(fixedName);
        if (material.isUnknown()) {
            material = Good.forName(fixedName);
            if (material.isUnknown()) {
                material = Asset.forName(fixedName);
                if (material.isUnknown()) {
                    throw new IllegalArgumentException("Unknown material type for name: " + fixedName);
                }
            }
        }
        return material;
    }

    static String hotfixName(final String name) {
        if ("ENHANCEDINTERROGATION".equals(name)) {
            return "ENHANCEDINTERROGATIONRECORDINGS";
        } else if ("GEOGRAPHICALDATA".equals(name)) {
            return "GEOLOGICALDATA";
        }
        return name;
    }

    String getLocalizationKey();

    boolean isUnknown();

    boolean isIllegal();
}
