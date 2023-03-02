package nl.jixxed.eliteodysseymaterials.domain.ships;

import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Map;

public abstract class UtilityModule extends ExternalModule {
    public UtilityModule(final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final boolean multiCrew, final Mounting mounting, final int basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        super(name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public UtilityModule(final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final Origin origin, final boolean multiCrew, final Mounting mounting, final int basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        super(name, moduleSize, moduleClass, origin, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public UtilityModule(final UtilityModule utilityModule) {
        super(utilityModule);
    }
}
