package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import lombok.Getter;

public enum SlotType {
    HARDPOINT(HardpointModule.class),
    UTILITY(UtilityModule.class),
    CORE(CoreModule.class),
    OPTIONAL(OptionalModule.class),
    MILITARY(MilitaryOptionalModule.class);
    @Getter
    private final Class<? extends ShipModule> moduleClass;
    SlotType(final Class<? extends ShipModule> moduleClass) {
        this.moduleClass = moduleClass;
    }
}
