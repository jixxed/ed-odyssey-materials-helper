package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

class OptionalModule extends ShipModule  {
    static final OptionalModule CARGO_RACK_2_E = new OptionalModule("CARGO_RACK_2_E");
    OptionalModule(final String name) {
        super(name);
    }
    OptionalModule(final OptionalModule optionalModule) {
        super(optionalModule.getName());
    }
}
