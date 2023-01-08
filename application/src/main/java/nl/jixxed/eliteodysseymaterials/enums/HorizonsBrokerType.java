package nl.jixxed.eliteodysseymaterials.enums;

public enum HorizonsBrokerType {
    HUMAN,
    GUARDIAN,
    TORVAL,
    SALVATION,
    SIRIUS,
    UNKNOWN;

    public static HorizonsBrokerType forName(final String name) {
        try {
            return HorizonsBrokerType.valueOf(name.toUpperCase());
        } catch (final IllegalArgumentException ex) {
            return HorizonsBrokerType.UNKNOWN;
        }

    }

    public String getLocalizationKey() {
        return "horizons.technology.broker.type." + this.name().toLowerCase();
    }
}
