package nl.jixxed.eliteodysseymaterials.domain.ships;

import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Map;

public abstract class MilitaryOptionalModule extends OptionalModule {
    public MilitaryOptionalModule(final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final int basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        super(name, moduleSize, moduleClass, false, basePrice, internalName, attributes);
    }

    public MilitaryOptionalModule(final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final Origin origin, final int basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        super(name, moduleSize, moduleClass, origin,false, basePrice, internalName, attributes);
    }

    public MilitaryOptionalModule(final MilitaryOptionalModule militaryOptionalModule) {
        super(militaryOptionalModule);
    }
}
