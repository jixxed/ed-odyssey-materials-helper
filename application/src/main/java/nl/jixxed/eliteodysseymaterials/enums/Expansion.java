package nl.jixxed.eliteodysseymaterials.enums;

public enum Expansion {
    ODYSSEY, HORIZONS;

    public String getLocalizationKey() {
        return "game.expansion." + this.name().toLowerCase();
    }
}
