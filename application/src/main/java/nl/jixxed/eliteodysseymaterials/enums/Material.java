package nl.jixxed.eliteodysseymaterials.enums;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.stream.Stream;

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

    static <T extends Material> T[] getAllMaterials() {
        final T[] materials = Stream.concat(Arrays.stream(Data.values()), Stream.concat(Arrays.stream(Asset.values()), Arrays.stream(Good.values())))
                .filter(material -> !material.isUnknown())
                .toArray(size -> (T[]) Array.newInstance(Material.class, size));
        return materials;
    }

    String getLocalizationKey();

    boolean isUnknown();

    boolean isIllegal();
}
