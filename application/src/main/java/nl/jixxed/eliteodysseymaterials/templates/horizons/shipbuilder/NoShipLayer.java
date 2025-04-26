package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableAnchorPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;

public class NoShipLayer extends DestroyableAnchorPane implements DestroyableTemplate {

    public NoShipLayer() {
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("no-ship");
    }

}
