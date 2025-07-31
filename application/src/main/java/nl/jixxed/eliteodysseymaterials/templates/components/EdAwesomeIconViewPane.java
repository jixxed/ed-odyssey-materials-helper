package nl.jixxed.eliteodysseymaterials.templates.components;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIconView;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableFontAwesomeIconView;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableStackPane;

public class EdAwesomeIconViewPane extends DestroyableStackPane {
    public EdAwesomeIconViewPane(EdAwesomeIconView edAwesomeIconView) {
        this.getStyleClass().add("icon-container");
        this.getNodes().add(edAwesomeIconView);
        StackPane.setAlignment(edAwesomeIconView, Pos.TOP_CENTER);
    }
}
