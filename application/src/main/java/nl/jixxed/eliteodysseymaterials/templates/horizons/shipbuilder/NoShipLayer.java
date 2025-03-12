package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.scene.layout.AnchorPane;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;

public class NoShipLayer extends AnchorPane implements DestroyableTemplate {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    public NoShipLayer() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("shipbuilder-noship-layer");
//        this.getChildren().add(LabelBuilder.builder().withNonLocalizedText("NO SHIP").build());

    }

    @Override
    public void initEventHandling() {

    }
}
