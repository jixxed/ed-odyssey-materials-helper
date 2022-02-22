package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.control.Tab;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsTabs;

public abstract class EDHTab extends Tab {
    public abstract HorizonsTabs getTabType();
}
