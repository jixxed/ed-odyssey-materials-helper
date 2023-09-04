package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;

public class HullStats extends Stats implements Template {
    public HullStats() {
        super();
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getChildren().add(BoxBuilder.builder().withNodes(new GrowingRegion(), createTitle("ship.stats.hull"), new GrowingRegion()).buildHBox());
        this.getChildren().add(new Separator(Orientation.HORIZONTAL));

    }

    @Override
    public void initEventHandling() {

    }
    @Override
    protected void update() {
    }
}
