package nl.jixxed.eliteodysseymaterials.templates.horizons;

import nl.jixxed.eliteodysseymaterials.enums.HorizonsTabs;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTab;

public abstract class HorizonsTab extends DestroyableTab {
    public abstract HorizonsTabs getTabType();
}
