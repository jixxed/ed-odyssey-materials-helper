package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ScrollPaneBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.templates.Template;

import java.util.ArrayList;
import java.util.List;

public class ShipLayer extends AnchorPane implements Template {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private final List<EventListener<?>> eventListeners = new ArrayList<>();
    public ShipLayer() {
        initComponents();
        initEventHandling();
    }
    @Getter
    private StatsLayer statsLayer;
//    @Getter
//    private StatsBGLayer statsBGLayer;
    @Getter
    private ModulesLayer modulesLayer;
//    @Getter
//    private DetailsLayer detailsLayer;

    private ScrollPane scrollPane;
    @Override
    public void initComponents() {
        this.getStyleClass().add("shipbuilder-noship-layer");
        statsLayer = new StatsLayer();
        modulesLayer = new ModulesLayer();

        final VBox vBox = BoxBuilder.builder().withNodes(statsLayer, modulesLayer).buildVBox();
        this.scrollPane = ScrollPaneBuilder.builder()
                .withContent(vBox)
                .withStyleClass("shipbuilder-modules-scrollpane")
                .build();
        this.getChildren().add(vBox);
        //, modulesLayer, statsBGLayer, detailsLayer, statsLayer

        AnchorPane.setTopAnchor(this.scrollPane,0D);
        AnchorPane.setRightAnchor(this.scrollPane,0D);
        AnchorPane.setBottomAnchor(this.scrollPane,0D);
        AnchorPane.setLeftAnchor(this.scrollPane,0D);
    }

    @Override
    public void initEventHandling() {

    }

    public void initShipSlots() {
        modulesLayer.initShipSlots();
    }
}
