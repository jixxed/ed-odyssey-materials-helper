package nl.jixxed.eliteodysseymaterials.domain.ships;

import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Map;

public abstract class CoreModule extends ShipModule {

    public CoreModule(final String id, final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final int basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, basePrice, internalName, attributes);
    }

    public CoreModule(final String id, final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final Origin origin, final int basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, basePrice, internalName, attributes);
    }

    public CoreModule(final CoreModule coreModule) {
        super(coreModule);
    }
}
