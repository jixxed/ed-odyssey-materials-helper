package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.scene.layout.AnchorPane;
import nl.jixxed.eliteodysseymaterials.builder.ScrollPaneBuilder;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableAnchorPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableScrollPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;

public class ShipSelectionLayer extends DestroyableAnchorPane implements DestroyableTemplate {

    private HorizonsShipBuilderTab tab;

    public ShipSelectionLayer(HorizonsShipBuilderTab tab) {
        this.tab = tab;
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("ship-selection");
        ShipSelectView shipSelectView = new ShipSelectView(this.tab);

        DestroyableScrollPane scrollPane = ScrollPaneBuilder.builder()
                .withContent(shipSelectView)
                .withStyleClass("shipbuilder-shipselection-scrollpane")
                .build();
        this.getNodes().add(scrollPane);
        AnchorPane.setTopAnchor(scrollPane, 0D);
        AnchorPane.setRightAnchor(scrollPane, 0D);
        AnchorPane.setBottomAnchor(scrollPane, 0D);
        AnchorPane.setLeftAnchor(scrollPane, 0D);

    }
}
