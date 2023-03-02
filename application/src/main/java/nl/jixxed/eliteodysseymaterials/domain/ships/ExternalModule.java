package nl.jixxed.eliteodysseymaterials.domain.ships;

import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Map;

abstract class ExternalModule extends ShipModule {
    private final Mounting mounting;
    ExternalModule(final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final Mounting mounting, final boolean multiCrew, final int basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        super(name, moduleSize, moduleClass, multiCrew, basePrice, internalName, attributes);
        this.mounting = mounting;
    }

    ExternalModule(final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final Origin origin, final boolean multiCrew, final Mounting mounting, final int basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        super(name, moduleSize, moduleClass, origin, multiCrew, basePrice, internalName, attributes);
        this.mounting = mounting;
    }

    ExternalModule(final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final boolean multiCrew, final Mounting mounting, final int basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        super(name, moduleSize, moduleClass, multiCrew, basePrice, internalName, attributes);
        this.mounting = mounting;
    }

    ExternalModule(final ExternalModule externalModule) {
        super(externalModule);
        this.mounting = externalModule.mounting;
    }
}
