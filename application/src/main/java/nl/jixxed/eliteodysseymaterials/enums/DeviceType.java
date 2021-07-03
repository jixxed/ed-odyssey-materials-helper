package nl.jixxed.eliteodysseymaterials.enums;

public enum DeviceType implements SpawnLocation {
    PWR,
    SRV,
    BIO,
    SAM,
    ENV,
    SYN;


    public static DeviceType forName(final String name) {
        return DeviceType.valueOf(name.toUpperCase());
    }

    @Override
    public String getLocalizationKey() {
        return "spawn.location.type.device." + this.name().toLowerCase();
    }
}
