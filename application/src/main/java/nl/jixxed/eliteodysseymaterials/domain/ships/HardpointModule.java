package nl.jixxed.eliteodysseymaterials.domain.ships;

import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Map;

public abstract class HardpointModule extends ExternalModule {
    public HardpointModule(final String id, final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final boolean multiCrew, final Mounting mounting, final int basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, mounting, multiCrew, basePrice, internalName, attributes);
    }

    public HardpointModule(final String id, final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final Origin origin, final boolean multiCrew, final Mounting mounting, final int basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public HardpointModule(final HardpointModule hardpointModule) {
        super(hardpointModule);
    }
}
