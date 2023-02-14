package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

class MilitaryOptionalModule extends OptionalModule{
    static final MilitaryOptionalModule MILITARY_2_E = new MilitaryOptionalModule("MILITARY_2_E");
    private MilitaryOptionalModule(final String name) {
        super(name);
    }
    MilitaryOptionalModule(final MilitaryOptionalModule militaryOptionalModule) {
        super(militaryOptionalModule.getName());
    }
}
