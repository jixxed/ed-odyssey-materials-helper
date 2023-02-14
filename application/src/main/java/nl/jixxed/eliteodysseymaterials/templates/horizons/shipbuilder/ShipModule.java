package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Slf4j
class ShipModule implements Serializable {
    private static final List<ShipModule> SHIP_MODULES = new ArrayList<>();
    @Getter
    private final String name;

    private static final  CoreModule coreModule = CoreModule.POWER_PLANT_4_A;
    private static final  HardpointModule hardpointModule = HardpointModule.PULSE_LASER_1_A;
    private static final  OptionalModule optionalModule = OptionalModule.CARGO_RACK_2_E;
    private static final  UtilityModule utilityModule = UtilityModule.XENO_SCANNER_0_E;
    private static final  MilitaryOptionalModule militaryOptionalModule = MilitaryOptionalModule.MILITARY_2_E;

    ShipModule(final String name) {
        this.name = name;
        SHIP_MODULES.add(this);
    }

    static List<ShipModule> getModules(final SlotType slotType) {
        return SHIP_MODULES.stream().filter(module -> {
            final Class<? extends ShipModule> aClass = module.getClass();
            final Class<? extends ShipModule> moduleClass = slotType.getModuleClass();
            return moduleClass.isAssignableFrom(aClass);
        }).toList();
    }
}
