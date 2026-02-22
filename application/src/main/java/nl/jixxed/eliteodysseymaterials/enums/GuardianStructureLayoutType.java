package nl.jixxed.eliteodysseymaterials.enums;

public enum GuardianStructureLayoutType {
    VESSEL,
    WEAPON,
    MODULE,
    BEACON;

    public String getLocalizationKey() {
        return "guardian.structure.type." + this.name().toLowerCase();
    }
}
