package nl.jixxed.eliteodysseymaterials.enums;

public enum HardpointGroup {
    A,B,C,D;

    public String getLocalizationKey() {
        return "hardpoint.group." + this.name().toLowerCase();
    }
}
