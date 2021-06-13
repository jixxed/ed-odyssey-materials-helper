package nl.jixxed.eliteodysseymaterials.enums;

public enum DataPortType implements SpawnLocation {
    AGR("Agricultural"),
    CMD("Command"),
    EXT("Extraction"),
    HAB("Habitat"),
    IND("Industrial"),
    LAB("Laboratory"),
    MED("Medical"),
    PWR("Power"),
    SEC("Security");

    String name;

    DataPortType(final String name) {
        this.name = name;
    }

    public static DataPortType forName(final String name) {
        return DataPortType.valueOf(name.toUpperCase());
    }

    @Override
    public String friendlyName() {
        return this.name;
    }
}
