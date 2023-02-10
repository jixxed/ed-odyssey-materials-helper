package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import java.util.ArrayList;
import java.util.List;

class ShipModule {
    private static final List<ShipModule> SHIP_MODULES = new ArrayList<>();

    ShipModule() {
        SHIP_MODULES.add(this);
    }

    static List<ShipModule> getModules(final SlotType slotType){
        return SHIP_MODULES.stream().filter(module -> module.getClass().isAssignableFrom(slotType.getModuleClass())).toList();
    }
}
