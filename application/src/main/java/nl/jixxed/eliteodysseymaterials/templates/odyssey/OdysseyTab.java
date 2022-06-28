package nl.jixxed.eliteodysseymaterials.templates.odyssey;

import javafx.scene.control.Tab;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyTabs;

public abstract class OdysseyTab extends Tab {
    public abstract OdysseyTabs getTabType();
}
