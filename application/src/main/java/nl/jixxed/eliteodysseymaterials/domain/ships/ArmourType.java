package nl.jixxed.eliteodysseymaterials.domain.ships;

public enum ArmourType {
    LIGHTWEIGHT_ALLOY,
    REINFORCED_ALLOY,
    MILITARY_GRADE_COMPOSITE,
    MIRRORED_SURFACE_COMPOSITE,
    REACTIVE_SURFACE_COMPOSITE;

    public String getLocalizationKey() {
        return "ships.module.name.armour." + this.name().toLowerCase();
    }
}
