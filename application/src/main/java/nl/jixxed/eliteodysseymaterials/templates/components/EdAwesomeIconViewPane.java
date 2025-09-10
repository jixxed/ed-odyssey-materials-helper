package nl.jixxed.eliteodysseymaterials.templates.components;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIcon;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIconView;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableStackPane;

import java.util.Arrays;

public class EdAwesomeIconViewPane extends DestroyableStackPane {
    public EdAwesomeIconViewPane(EdAwesomeIconView... edAwesomeIconViews) {
        this.getStyleClass().add("icon-container");
        this.getNodes().addAll(edAwesomeIconViews);
        Arrays.stream(edAwesomeIconViews).forEach(edAwesomeIconView -> StackPane.setAlignment(edAwesomeIconView, Pos.CENTER));
    }

    public void setIcons(EdAwesomeIcon... edAwesomeIcons) {
        this.getNodes().clear();
        EdAwesomeIconView[] edAwesomeIconViews = Arrays.stream(edAwesomeIcons).map(EdAwesomeIconView::new).toArray(EdAwesomeIconView[]::new);
        this.getNodes().addAll(edAwesomeIconViews);
        Arrays.stream(edAwesomeIconViews).forEach(edAwesomeIconView -> StackPane.setAlignment(edAwesomeIconView, Pos.CENTER));
    }
}
