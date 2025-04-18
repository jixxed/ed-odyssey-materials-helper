package nl.jixxed.eliteodysseymaterials.templates.generic;

import nl.jixxed.eliteodysseymaterials.enums.StorageType;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;

public abstract class Ingredient extends DestroyableVBox {

    public abstract StorageType getType();

    public abstract String getName();
}
