package nl.jixxed.eliteodysseymaterials.templates.components;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class GrowingRegion extends Region {
    public GrowingRegion() {
        HBox.setHgrow(this, Priority.ALWAYS);
        VBox.setVgrow(this, Priority.ALWAYS);
    }

    public GrowingRegion(final String styleClass) {
        this();
        this.getStyleClass().add(styleClass);
    }
}
