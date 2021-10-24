package nl.jixxed.eliteodysseymaterials.enums;

public enum Specialisation {
    STRATEGIC, DYNAMIC, FORCE, HYBRID, UNKNOWN;


    public String getLocalizationKey() {
        return "specialisation.name." + this.name().toLowerCase();
    }
}
