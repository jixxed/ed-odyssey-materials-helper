package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyStorageType;

public abstract class Ingredient extends VBox {

    public abstract OdysseyStorageType getType();

    public abstract String getName();
}
