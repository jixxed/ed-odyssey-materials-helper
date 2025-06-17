package nl.jixxed.eliteodysseymaterials.templates.generic;

import javafx.beans.property.SimpleIntegerProperty;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.Destroyable;

public interface QuantitySelectable extends Destroyable {
    SimpleIntegerProperty getQuantity();
}
