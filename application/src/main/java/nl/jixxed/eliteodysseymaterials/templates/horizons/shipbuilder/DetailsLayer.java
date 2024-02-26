package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;

public class DetailsLayer extends AnchorPane implements Template {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    @Getter
    private ModuleDetails moduleDetails;

    public DetailsLayer() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("shipbuilder-details-layer");
        this.moduleDetails = new ModuleDetails();
        moduleDetails.setPickOnBounds(false);
        final VBox pane = new VBox(this.moduleDetails, new GrowingRegion());
        pane.setPickOnBounds(false);
        pane.getStyleClass().add("stats-values-pane");
        this.getChildren().add(pane);
        AnchorPane.setRightAnchor(pane, 0D);
        AnchorPane.setTopAnchor(pane, 0D);
        AnchorPane.setBottomAnchor(pane, 0D);

    }

    @Override
    public void initEventHandling() {

    }
}
