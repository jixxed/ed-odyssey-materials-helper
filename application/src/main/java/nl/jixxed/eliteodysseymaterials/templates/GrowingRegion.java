package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

class GrowingRegion extends Region {
    GrowingRegion() {
        HBox.setHgrow(this, Priority.ALWAYS);
        VBox.setVgrow(this, Priority.ALWAYS);
    }

    GrowingRegion(final String styleClass) {
        this();
        this.getStyleClass().add(styleClass);
    }
}
