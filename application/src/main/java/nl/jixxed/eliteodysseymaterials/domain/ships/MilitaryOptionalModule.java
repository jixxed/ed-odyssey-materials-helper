package nl.jixxed.eliteodysseymaterials.domain.ships;

import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Map;

public abstract class MilitaryOptionalModule extends OptionalModule {
    public MilitaryOptionalModule(final String id, final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final long basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, false, basePrice, internalName, attributes);
    }

    public MilitaryOptionalModule(final String id, final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final Origin origin, final long basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin,false, basePrice, internalName, attributes);
    }

    public MilitaryOptionalModule(final MilitaryOptionalModule militaryOptionalModule) {
        super(militaryOptionalModule);
    }
}
