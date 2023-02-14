package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

class HardpointModule extends ShipModule {
    static final HardpointModule PULSE_LASER_1_A = new HardpointModule("PULSE_LASER_1_A");

    private HardpointModule(final String name) {
        super(name);
    }
    HardpointModule(final HardpointModule hardpointModule) {
        super(hardpointModule.getName());
    }
}
