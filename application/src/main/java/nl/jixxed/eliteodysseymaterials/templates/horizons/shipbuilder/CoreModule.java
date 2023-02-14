package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

class CoreModule extends ShipModule {
    static final CoreModule POWER_PLANT_4_A = new CoreModule("POWER_PLANT_4_A");

    private CoreModule(final String name) {
        super(name);
    }
    CoreModule(final CoreModule coreModule) {
        super(coreModule.getName());
    }
}
