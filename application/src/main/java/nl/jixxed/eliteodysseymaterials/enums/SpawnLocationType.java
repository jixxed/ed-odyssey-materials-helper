package nl.jixxed.eliteodysseymaterials.enums;

public enum SpawnLocationType implements SpawnLocation {
    DATAPORT("Settlement data ports"),
    OPENLOCKER("Open lockers"),
    CLOSEDLOCKER("Sealed lockers"),
    DEVICE("Devices"),
    OTHER("Other Locations"),
    PLANETARY("Planetary signal sources");

    String name;

    SpawnLocationType(final String name) {
        this.name = name;
    }

    public static SpawnLocationType forName(final String name) {
        return SpawnLocationType.valueOf(name.toUpperCase());
    }

    @Override
    public String friendlyName() {
        return this.name;
    }
}
