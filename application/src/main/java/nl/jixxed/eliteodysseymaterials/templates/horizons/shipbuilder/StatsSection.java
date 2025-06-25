package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.css.PseudoClass;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.StackPaneBuilder;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsShipSelectedEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;
import nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats.*;

public class StatsSection extends DestroyableHBox implements DestroyableEventTemplate {

    @Getter
    private DestroyableStackPane stats;

    public StatsSection() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("stats");
        initStats();
    }


    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, HorizonsShipSelectedEvent.class, horizonsShipSelectedEvent -> {
            stats.requestLayout();
        }));
    }

    public void initStats() {
        this.getNodes().clear();
        final ThermalStats thermalStats = new ThermalStats();
        final PowerStats powerStats = new PowerStats();
        final JumpStats jumpStats = new JumpStats();
        final EngineStats engineStats = new EngineStats();
        final PriceStats priceStats = new PriceStats();
        final HandlingStats handlingStats = new HandlingStats();
        final ArmourStats armourStats = new ArmourStats();
        final ShieldStats shieldStats = new ShieldStats();
        final WeaponStats weaponStats = new WeaponStats();
        stats = StackPaneBuilder.builder()
                .withStyleClass("shipbuilder-stats-box")
                .withNodes(
                        thermalStats,
                        powerStats,
                        jumpStats,
                        engineStats,
                        priceStats,
                        handlingStats,
                        armourStats,
                        shieldStats,
                        weaponStats)
//                .withPickOnBounds(false)
                .build();
        final DestroyableLabel thermal = createLabel("tab.ships.stats.thermal", thermalStats);
        thermal.pseudoClassStateChanged(PseudoClass.getPseudoClass("first"), true);
        DestroyableVBox selectors = BoxBuilder.builder()
                .withStyleClass("selectors")
                .withNodes(
                        thermal,
                        createLabel("tab.ships.stats.power", powerStats),
                        createLabel("tab.ships.stats.jump", jumpStats),
                        createLabel("tab.ships.stats.engine", engineStats),
                        createLabel("tab.ships.stats.price", priceStats),
                        createLabel("tab.ships.stats.handling", handlingStats),
                        createLabel("tab.ships.stats.armour", armourStats),
                        createLabel("tab.ships.stats.shield", shieldStats),
                        createLabel("tab.ships.stats.weapon", weaponStats)
                )
                .buildVBox();
        stats.getChildren().forEach(stat -> {
            stat.setVisible(false);
            stat.setManaged(false);
        });
        this.getNodes().add(new Config());
        this.getNodes().add(selectors);
        this.getNodes().add(stats);
    }

    private DestroyableLabel createLabel(String localeKey, Stats relatedStats) {
        return LabelBuilder.builder()
                .withStyleClass("stat-select")
                .withText(localeKey)
                .withHoverProperty((_, _, newValue) -> {
                    if (Boolean.TRUE.equals(newValue)) {
                        stats.getChildren().forEach(stat -> {
                            stat.setVisible(false);
                            stat.setManaged(false);
                        });
                        relatedStats.setVisible(true);
                        relatedStats.setManaged(true);
                    }
                })
                .build();
    }
}
