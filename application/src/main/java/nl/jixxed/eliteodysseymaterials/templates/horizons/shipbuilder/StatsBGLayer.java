package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsShipSelectedEvent;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;

import java.util.ArrayList;
import java.util.List;

public class StatsBGLayer extends AnchorPane implements Template {
    private final List<EventListener<?>> eventListeners = new ArrayList<>();
    @Getter
    private HBox stats;
    private StatsLayer statsLayer;

    public StatsBGLayer(StatsLayer statsLayer) {
        this.statsLayer = statsLayer;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("shipbuilder-stats-layer-bg");
        stats = BoxBuilder.builder().withStyleClass("shipbuilder-stats-box-bg").buildHBox();
        final GrowingRegion growingRegion = new GrowingRegion();
        growingRegion.minHeightProperty().bind(statsLayer.getStats().heightProperty());
        growingRegion.maxHeightProperty().bind(statsLayer.getStats().heightProperty());
        growingRegion.prefHeightProperty().bind(statsLayer.getStats().heightProperty());
        stats.getChildren().addAll(growingRegion);
        this.getChildren().add(stats);
        stats.setPickOnBounds(false);
        AnchorPane.setBottomAnchor(stats,0D);
        AnchorPane.setLeftAnchor(stats,0D);
        AnchorPane.setRightAnchor(stats,0D);
    }

    @Override
    public void initEventHandling() {

        this.eventListeners.add(EventService.addListener(true, this, HorizonsShipSelectedEvent.class, horizonsShipSelectedEvent -> {
            stats.requestLayout();
        }));
    }
}
