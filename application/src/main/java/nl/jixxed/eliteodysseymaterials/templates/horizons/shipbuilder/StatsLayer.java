package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsShipSelectedEvent;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats.*;

import java.util.ArrayList;
import java.util.List;

public class StatsLayer extends Pane implements Template {
    private final List<EventListener<?>> eventListeners = new ArrayList<>();
    @Getter
    private HBox stats;

    public StatsLayer() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("shipbuilder-stats-layer");
        stats = BoxBuilder.builder().withStyleClass("shipbuilder-stats-box").buildHBox();
        stats.getChildren().addAll(
                new Config(),
                BoxBuilder.builder().withNodes(new ThermalStats(), new GrowingRegion(), new PowerStats()).buildVBox(),
                BoxBuilder.builder().withNodes(new JumpStats(), new GrowingRegion(), new EngineStats()).buildVBox(),
                BoxBuilder.builder().withNodes(new PriceStats(), new GrowingRegion(), new HandlingStats()).buildVBox(),

                new ArmourStats(),
                new ShieldStats(),
                new WeaponStats(),
                new GrowingRegion()
        );
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
