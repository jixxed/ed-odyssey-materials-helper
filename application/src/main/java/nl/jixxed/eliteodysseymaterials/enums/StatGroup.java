package nl.jixxed.eliteodysseymaterials.enums;

public enum StatGroup {
    GENERAL, SHIELD, RESISTANCE, CAPACITY, MOVEMENT, POWER, DAMAGE, NOISE, OTHER;

    public String getLocalizationKey() {
        return "loadout.stat.group.name." + this.name().toLowerCase();
    }
}
