package nl.jixxed.eliteodysseymaterials.enums;

public enum AlternateLocationType implements SpawnLocation {
    MISSION;

    public static AlternateLocationType forName(final String name) {
        return AlternateLocationType.valueOf(name.toUpperCase());
    }

    @Override
    public String getLocalizationKey() {
        return "spawn.location.type.other." + this.name().toLowerCase();
    }
}
