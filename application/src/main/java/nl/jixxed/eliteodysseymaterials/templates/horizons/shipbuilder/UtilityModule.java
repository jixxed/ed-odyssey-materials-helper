package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

class UtilityModule extends ShipModule {
    static final UtilityModule XENO_SCANNER_0_E = new UtilityModule("XENO_SCANNER_0_E");

    private UtilityModule(final String name) {
        super(name);
    }
    UtilityModule(final UtilityModule utilityModule) {
        super(utilityModule.getName());
    }
}
