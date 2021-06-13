package nl.jixxed.eliteodysseymaterials.enums;

public enum PlanetSignalSourceType implements SpawnLocation {
    CRASHED("Crashed ship(Crash site)"),
    SKIMMER("Skimmer(Irregular marker)"),
    SRV("SRV(Irregular marker)"),
    LARGE_SAT("Large satellite(Impact site)"),
    SMALL_SAT("Small satellite(Impact site)");

    String name;

    PlanetSignalSourceType(final String name) {
        this.name = name;
    }

    public static PlanetSignalSourceType forName(final String name) {
        return PlanetSignalSourceType.valueOf(name.toUpperCase());
    }

    @Override
    public String friendlyName() {
        return this.name;
    }
}
