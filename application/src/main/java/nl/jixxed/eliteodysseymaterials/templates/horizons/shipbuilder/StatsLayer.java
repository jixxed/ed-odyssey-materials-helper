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
import nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats.*;

public class StatsLayer extends DestroyableAnchorPane implements DestroyableEventTemplate {

    @Getter
    private DestroyableHBox stats;

    public StatsLayer() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("shipbuilder-stats-layer");
        stats = BoxBuilder.builder()
                .withStyleClass("shipbuilder-stats-box")
                .withNodes(new Config(),
                        BoxBuilder.builder()
                                .withNodes(new ThermalStats(), new GrowingRegion(), new PowerStats())
                                .buildVBox(),
                        BoxBuilder.builder()
                                .withNodes(new JumpStats(), new GrowingRegion(), new EngineStats())
                                .buildVBox(),
                        BoxBuilder.builder()
                                .withNodes(new PriceStats(), new GrowingRegion(), new HandlingStats())
                                .buildVBox(),
                        new ArmourStats(),
                        new ShieldStats(),
                        new WeaponStats(),
                        new GrowingRegion())
                .withPickOnBounds(false)
                .buildHBox();
        this.getNodes().add(stats);
        AnchorPane.setBottomAnchor(stats, 0D);
        AnchorPane.setLeftAnchor(stats, 0D);
        AnchorPane.setRightAnchor(stats, 0D);
    }

    @Override
    public void initEventHandling() {

        register(EventService.addListener(true, this, HorizonsShipSelectedEvent.class, horizonsShipSelectedEvent -> {
            stats.requestLayout();
        }));
    }
}
