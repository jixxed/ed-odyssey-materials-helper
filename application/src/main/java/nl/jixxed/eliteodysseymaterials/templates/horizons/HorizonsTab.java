package nl.jixxed.eliteodysseymaterials.templates.horizons;

import javafx.scene.control.Tab;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsTabs;

public abstract class HorizonsTab extends Tab {
    public abstract HorizonsTabs getTabType();
}
