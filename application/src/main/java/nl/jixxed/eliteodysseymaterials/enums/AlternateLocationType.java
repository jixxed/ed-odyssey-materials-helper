package nl.jixxed.eliteodysseymaterials.enums;

public enum AlternateLocationType implements SpawnLocation {
    MISSION("Missions");

    String name;

    AlternateLocationType(final String name) {
        this.name = name;
    }

    public static AlternateLocationType forName(final String name) {
        return AlternateLocationType.valueOf(name.toUpperCase());
    }

    @Override
    public String friendlyName() {
        return this.name;
    }
}
