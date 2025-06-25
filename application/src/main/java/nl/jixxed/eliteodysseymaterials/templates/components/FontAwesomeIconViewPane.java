package nl.jixxed.eliteodysseymaterials.templates.components;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableFontAwesomeIconView;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableStackPane;

public class FontAwesomeIconViewPane extends DestroyableStackPane {
    public FontAwesomeIconViewPane(DestroyableFontAwesomeIconView fontAwesomeIconView) {
        this.getStyleClass().add("icon-container");
        this.getNodes().add(fontAwesomeIconView);
        StackPane.setAlignment(fontAwesomeIconView, Pos.CENTER);
    }
}
