package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.builder.ScrollPaneBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.templates.Template;

import java.util.ArrayList;
import java.util.List;

public class ShipSelectionLayer extends AnchorPane implements Template {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private final List<EventListener<?>> eventListeners = new ArrayList<>();
    private VBox shipSelectView;
    private HorizonsShipBuilderTab tab;
    private ScrollPane scrollPane;
    public ShipSelectionLayer(HorizonsShipBuilderTab tab) {
        this.tab = tab;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("shipbuilder-shipselection-layer");
        this.shipSelectView = new ShipSelectView(this.tab);

        this.scrollPane = ScrollPaneBuilder.builder()
                .withContent(this.shipSelectView)
                .withStyleClass("shipbuilder-shipselection-scrollpane")
                .build();
        this.getChildren().add(this.scrollPane);
        AnchorPane.setTopAnchor(this.scrollPane,0D);
        AnchorPane.setRightAnchor(this.scrollPane,0D);
        AnchorPane.setBottomAnchor(this.scrollPane,0D);
        AnchorPane.setLeftAnchor(this.scrollPane,0D);

    }

    @Override
    public void initEventHandling() {

    }
}
