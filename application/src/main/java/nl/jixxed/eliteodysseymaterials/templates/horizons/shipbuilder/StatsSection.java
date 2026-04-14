/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.css.PseudoClass;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableFlowPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;
import nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats.*;

import java.util.List;

public class StatsSection extends DestroyableFlowPane implements DestroyableEventTemplate {

    @Getter
    private List<Stats> stats;

    public StatsSection() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("stats-section");
        initStats();
    }


    @Override
    public void initEventHandling() {
//        register(EventService.addListener(true, this, HorizonsShipSelectedEvent.class, horizonsShipSelectedEvent -> {
//            stats.requestLayout();
//        }));
    }

    public void initStats() {
        this.getNodes().clear();
        final ThermalStats thermalStats = new ThermalStats();
//        final ThermalStats thermalStats = new ThermalStats();
//        final PowerStats powerStats = new PowerStats();
        final JumpStats jumpStats = new JumpStats();
        final EngineStats engineStats = new EngineStats();
        final ShipStats shipStats = new ShipStats();
        final HandlingStats handlingStats = new HandlingStats();
        final ArmourStats armourStats = new ArmourStats();
        final ShieldStats shieldStats = new ShieldStats();
        final WeaponStats weaponStats = new WeaponStats();
        final Validation validation = new Validation();
        stats = List.of(
                shipStats,
                jumpStats,
                engineStats,
                handlingStats,
                thermalStats,
//                thermalStats,
//                powerStats,
                armourStats,
                shieldStats,
                weaponStats,
                validation);
//        stats = BoxBuilder.builder()
//                .withStyleClass("shipbuilder-stats-box")
//                .withNodes(
//                        )
////                .withPickOnBounds(false)
//                .buildHBox();
//        final DestroyableLabel thermal = createLabel("tab.ships.stats.thermals", thermalStats);
        final DestroyableLabel ship = createLabel("tab.ships.stats.ship", shipStats);
        ship.pseudoClassStateChanged(PseudoClass.getPseudoClass("first"), true);
        DestroyableVBox selectors = BoxBuilder.builder()
                .withStyleClass("selectors")
                .withNodes(
                        ship,
//                        createLabel("tab.ships.stats.power", powerStats),
                        createLabel("tab.ships.stats.jump", jumpStats),
                        createLabel("tab.ships.stats.engine", engineStats),
                        createLabel("tab.ships.stats.handling", handlingStats),
                        createLabel("tab.ships.stats.thermals", thermalStats),
                        createLabel("tab.ships.stats.armour", armourStats),
                        createLabel("tab.ships.stats.shield", shieldStats),
                        createLabel("tab.ships.stats.weapon", weaponStats)
                )
                .withHoverProperty(_ -> (_, _, newValue) -> {
                    if (Boolean.FALSE.equals(newValue)) {
                        stats.forEach(node -> {
                            if (node instanceof Validation valid) {
                                valid.setCanShow(true);
                            } else {
                                Boolean visible = PreferencesService.getPreference("stat.visible." + node.getClass().getName().toLowerCase(), getDefaultValue(node.getClass().getName().toLowerCase()));
                                node.setVisible(visible);
                                node.setManaged(visible);
                            }
                        });
                    }
                })
                .buildVBox();

        stats.forEach(stat -> {
            if (stat instanceof Validation valid) {
                valid.setCanShow(true);
            } else {
                Boolean visible = PreferencesService.getPreference("stat.visible." + stat.getClass().getName().toLowerCase(), getDefaultValue(stat.getClass().getName().toLowerCase()));
                stat.setVisible(visible);
                stat.setManaged(visible);
            }
        });
        this.getNodes().add(new Config());
        this.getNodes().add(selectors);
        this.getNodes().addAll(stats);
    }

    private DestroyableLabel createLabel(String localeKey, Stats relatedStats) {
        Boolean visible = PreferencesService.getPreference("stat.visible." + relatedStats.getClass().getName().toLowerCase(), getDefaultValue(relatedStats.getClass().getName().toLowerCase()));
        DestroyableLabel selector = LabelBuilder.builder()
                .withStyleClass("stat-select")
                .withText(localeKey)
                .withHoverProperty(_ -> (_, _, newValue) -> {
                    if (Boolean.TRUE.equals(newValue)) {
                        stats.forEach(stat -> {
                            if (stat instanceof Validation valid) {
                                valid.setCanShow(false);

                            } else {
                                stat.setVisible(false);
                                stat.setManaged(false);
                            }
                        });
                        relatedStats.setVisible(true);
                        relatedStats.setManaged(true);
                    }
                })
                .build();
        selector.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), visible);
        selector.addEventBinding(selector.onMouseClickedProperty(), event -> {
            Boolean current = PreferencesService.getPreference("stat.visible." + relatedStats.getClass().getName().toLowerCase(), getDefaultValue(relatedStats.getClass().getName().toLowerCase()));
            boolean newState = !current;
            PreferencesService.setPreference("stat.visible." + relatedStats.getClass().getName().toLowerCase(), newState);
            selector.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), newState);
            relatedStats.setVisible(newState);
            relatedStats.setManaged(newState);
        });
        VBox.setVgrow(selector, Priority.ALWAYS);
        return selector;
    }

    private static Boolean getDefaultValue(String className) {
        return "nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats.shipstats".equals(className)
                || "nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats.jumpstats".equals(className);
    }
}
