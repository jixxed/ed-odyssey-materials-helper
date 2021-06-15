package nl.jixxed.eliteodysseymaterials.enums;

public interface Material {
    String friendlyName();

    static Material subtypeForName(final String name) {
        Material material = Data.forName(name);
        if (Data.UNKNOWN.equals(material)) {
            material = Good.forName(name);
            if (Good.UNKNOWN.equals(material)) {
                material = Asset.forName(name);
                if (Asset.UNKNOWN.equals(material)) {
                    throw new IllegalArgumentException("Unknown material type for name: " + name);
                }
            }
        }
        return material;
    }
}
