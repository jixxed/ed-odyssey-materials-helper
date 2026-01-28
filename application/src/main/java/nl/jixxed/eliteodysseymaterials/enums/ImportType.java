package nl.jixxed.eliteodysseymaterials.enums;

public enum ImportType {
    HORIZONSWISHLIST,
    CAPI,
    WISHLIST,
    LOADOUT,
    SHIP,
    EDSY,
    CORIOLIS,
    PINCONFIG,
    UNKNOWN;


    public static ImportType forName(final String name) {
        try {
            return ImportType.valueOf(name.toUpperCase());
        } catch (final IllegalArgumentException ex) {
            return UNKNOWN;
        }
    }


    public String getLocalizationKey() {
        return "import.type." + this.name().toLowerCase();
    }
}