package nl.jixxed.eliteodysseymaterials.enums;

public enum ColonisationLocation {
    ORBITAL,
    SURFACE;

    public String getLocalizationKey() {
        return "colonisation.location." + this.name().toLowerCase();
    }
}
