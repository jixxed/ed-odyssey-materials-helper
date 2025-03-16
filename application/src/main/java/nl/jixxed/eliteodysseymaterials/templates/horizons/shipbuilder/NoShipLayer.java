package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableAnchorPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;

public class NoShipLayer extends DestroyableAnchorPane implements DestroyableTemplate {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    public NoShipLayer() {
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("shipbuilder-noship-layer");
//        this.getNodes().add(LabelBuilder.builder().withNonLocalizedText("NO SHIP").build());

    }
}
