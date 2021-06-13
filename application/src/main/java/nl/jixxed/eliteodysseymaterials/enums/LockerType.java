package nl.jixxed.eliteodysseymaterials.enums;

public enum LockerType implements SpawnLocation {
    AGR("Agricultural"),
    CMD("Command"),
    EXT("Extraction"),
    HAB("Habitat"),
    DORM("Dormitory"),
    IND("Industrial"),
    LAB("Laboratory"),
    MED("Medical"),
    PWR("Power"),
    OPR("Operations"),
    STO("Storage"),
    PROC("Processing"),
    SEC("Security"),
    OUT("Containers outside");

    String name;

    LockerType(final String name) {
        this.name = name;
    }

    public static LockerType forName(final String name) {
        return LockerType.valueOf(name.toUpperCase());
    }

    @Override
    public String friendlyName() {
        return this.name;
    }
}
