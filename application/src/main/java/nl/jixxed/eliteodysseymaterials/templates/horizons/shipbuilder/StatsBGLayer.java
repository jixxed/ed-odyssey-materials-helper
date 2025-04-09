package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsShipSelectedEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableAnchorPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;

public class StatsBGLayer extends DestroyableAnchorPane implements DestroyableEventTemplate {

    @Getter
    private DestroyableHBox stats;
    private StatsLayer statsLayer;

    public StatsBGLayer(StatsLayer statsLayer) {
        this.statsLayer = statsLayer;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("shipbuilder-stats-layer-bg");

        final GrowingRegion growingRegion = new GrowingRegion();
        growingRegion.addBinding(growingRegion.minHeightProperty(), statsLayer.getStats().heightProperty());
        growingRegion.addBinding(growingRegion.maxHeightProperty(), statsLayer.getStats().heightProperty());
        growingRegion.addBinding(growingRegion.prefHeightProperty(), statsLayer.getStats().heightProperty());

        stats = BoxBuilder.builder()
                .withStyleClass("shipbuilder-stats-box-bg")
                .withPickOnBounds(false)
                .withNodes(growingRegion)
                .buildHBox();

        this.getNodes().add(stats);

        AnchorPane.setBottomAnchor(stats, 0D);
        AnchorPane.setLeftAnchor(stats, 0D);
        AnchorPane.setRightAnchor(stats, 0D);
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, HorizonsShipSelectedEvent.class, _ -> stats.requestLayout()));
    }
}
