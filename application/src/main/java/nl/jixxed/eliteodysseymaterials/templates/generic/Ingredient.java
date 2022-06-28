package nl.jixxed.eliteodysseymaterials.templates.generic;

import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.enums.StorageType;

public abstract class Ingredient extends VBox {

    public abstract StorageType getType();

    public abstract String getName();
}
