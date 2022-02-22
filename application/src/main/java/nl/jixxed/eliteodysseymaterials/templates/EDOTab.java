package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.control.Tab;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyTabs;

public abstract class EDOTab extends Tab {
    public abstract OdysseyTabs getTabType();
}
