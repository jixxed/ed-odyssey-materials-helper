package nl.jixxed.eliteodysseymaterials.templates.odyssey;

import nl.jixxed.eliteodysseymaterials.enums.OdysseyTabs;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTab;

public abstract class OdysseyTab extends DestroyableTab {
    public abstract OdysseyTabs getTabType();
}
