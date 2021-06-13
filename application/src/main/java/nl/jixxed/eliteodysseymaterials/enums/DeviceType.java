package nl.jixxed.eliteodysseymaterials.enums;

public enum DeviceType implements SpawnLocation {
    PWR("Power reactor"),
    SRV("SRV"),
    BIO("Bio containment unit");

    String name;

    DeviceType(final String name) {
        this.name = name;
    }

    public static DeviceType forName(final String name) {
        return DeviceType.valueOf(name.toUpperCase());
    }

    @Override
    public String friendlyName() {
        return this.name;
    }
}
