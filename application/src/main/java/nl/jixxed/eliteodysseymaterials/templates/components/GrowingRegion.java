package nl.jixxed.eliteodysseymaterials.templates.components;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableRegion;

public class GrowingRegion extends DestroyableRegion {
    public GrowingRegion() {
        HBox.setHgrow(this, Priority.ALWAYS);
        VBox.setVgrow(this, Priority.ALWAYS);
    }

    public GrowingRegion(final String styleClass) {
        this();
        this.getStyleClass().add(styleClass);
    }
}
