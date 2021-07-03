package nl.jixxed.eliteodysseymaterials.enums;

public enum PlanetSignalSourceType implements SpawnLocation {
    CRASHED,
    SKIMMER,
    SRV,
    LARGE_SAT,
    SMALL_SAT;

    public static PlanetSignalSourceType forName(final String name) {
        return PlanetSignalSourceType.valueOf(name.toUpperCase());
    }

    @Override
    public String getLocalizationKey() {
        return "spawn.location.type.planetary." + this.name().toLowerCase();
    }
}
