package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableAnchorPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;

public class DetailsLayer extends DestroyableAnchorPane implements DestroyableTemplate {
    @Getter
    private ModuleDetails moduleDetails;

    public DetailsLayer() {
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("shipbuilder-details-layer");
        this.moduleDetails = new ModuleDetails(this);
        moduleDetails.setPickOnBounds(false);
        final DestroyableVBox vBox = BoxBuilder.builder()
                .withStyleClass("stats-values-pane")
                .withNodes(this.moduleDetails)
                .withPickOnBounds(false)
                .buildVBox();
        this.getNodes().add(vBox);
        AnchorPane.setRightAnchor(vBox, 0D);
        AnchorPane.setTopAnchor(vBox, 0D);
        AnchorPane.setBottomAnchor(vBox, 0D);

    }
}
