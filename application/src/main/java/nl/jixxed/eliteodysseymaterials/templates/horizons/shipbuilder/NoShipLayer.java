package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.scene.layout.AnchorPane;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.templates.Template;

import java.util.ArrayList;
import java.util.List;

public class NoShipLayer extends AnchorPane implements Template {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private final List<EventListener<?>> eventListeners = new ArrayList<>();
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
