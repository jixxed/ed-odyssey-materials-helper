package nl.jixxed.eliteodysseymaterials.enums;

public enum SpawnLocationType implements SpawnLocation {
    DATAPORT,
    OPENLOCKER,
    CLOSEDLOCKER,
    DEVICE,
    OTHER,
    PLANETARY;

    public static SpawnLocationType forName(final String name) {
        return SpawnLocationType.valueOf(name.toUpperCase());
    }

    @Override
    public String getLocalizationKey() {
        return "spawn.location.type." + this.name().toLowerCase();
    }
}
