package nl.jixxed.eliteodysseymaterials.enums;

public enum HorizonsBrokerType {
    GUARDIAN("Guardian"),
    HUMAN("Human"),
    SIRIUS("Sirius"),
    TORVALMINING("Torval Mining"),
    SALVATION("Salvation"),
    RESCUE("Rescue"),
    UNKNOWN("Unknown"),
    NONE("None");
    private final String displayName;

    HorizonsBrokerType(String displayName) {
        this.displayName = displayName;
    }

    public static HorizonsBrokerType forName(final String name) {
        try {
            return HorizonsBrokerType.valueOf(name.toUpperCase());
        } catch (final IllegalArgumentException ex) {
            return HorizonsBrokerType.UNKNOWN;
        }

    }


    public String getDisplayName() {
        return displayName;
    }

    public static HorizonsBrokerType forDisplayName(final String displayName) {
        for (final HorizonsBrokerType type : values()) {
            if (type.displayName.equalsIgnoreCase(displayName)) {
                return type;
            }
        }
        return UNKNOWN;
    }
    public String getLocalizationKey() {
        return "horizons.technology.broker.type." + this.name().toLowerCase();
    }
}
