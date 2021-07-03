package nl.jixxed.eliteodysseymaterials.enums;

public enum LockerType implements SpawnLocation {
    AGR,
    CMD,
    EXT,
    HAB,
    DORM,
    IND,
    LAB,
    MED,
    PWR,
    OPR,
    STO,
    PROC,
    SEC,
    OUT;


    public static LockerType forName(final String name) {
        return LockerType.valueOf(name.toUpperCase());
    }

    @Override
    public String getLocalizationKey() {
        return "spawn.location.type.locker." + this.name().toLowerCase();
    }
}
