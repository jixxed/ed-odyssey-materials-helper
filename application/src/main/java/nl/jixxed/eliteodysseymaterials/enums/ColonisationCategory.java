package nl.jixxed.eliteodysseymaterials.enums;

public enum ColonisationCategory {
    STAR_PORT,
    OUTPOST,
    INSTALLATION,
    PLANETARY_PORT,
    SETTLEMENT,
    HUB;

    public String getLocalizationKey() {
        return "colonisation.category." + this.name().toLowerCase();
    }
}
