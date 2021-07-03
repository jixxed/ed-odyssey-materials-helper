package nl.jixxed.eliteodysseymaterials.enums;

public enum DataPortType implements SpawnLocation {
    AGR,
    CMD,
    EXT,
    HAB,
    IND,
    LAB,
    MED,
    PWR,
    SEC;


    public static DataPortType forName(final String name) {
        return DataPortType.valueOf(name.toUpperCase());
    }


    @Override
    public String getLocalizationKey() {
        return "spawn.location.type.dataport." + this.name().toLowerCase();
    }
}
