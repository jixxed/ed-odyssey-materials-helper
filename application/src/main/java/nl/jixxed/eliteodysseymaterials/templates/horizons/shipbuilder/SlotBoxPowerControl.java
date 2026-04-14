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

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.css.PseudoClass;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.FontAwesomeIconViewBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ShipConfiguration;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.service.ships.ShipService;
import nl.jixxed.eliteodysseymaterials.templates.components.FontAwesomeIconViewPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

public class SlotBoxPowerControl extends DestroyableVBox implements DestroyableEventTemplate {

    private DestroyableFontAwesomeIconView power;
    private DestroyableLabel powerLabel;
    private DestroyableHBox powerIconAndLabel;
    private DestroyableFontAwesomeIconView powerUp;
    private DestroyableFontAwesomeIconView powerDown;
    private final SlotBox slotBox;

    public SlotBoxPowerControl(SlotBox slotBox) {
        this.slotBox = slotBox;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("power-control");
        power = FontAwesomeIconViewBuilder.builder()
                .withStyleClass("icon")
                .withIcon(FontAwesomeIcon.POWER_OFF)
                .build();
        powerLabel = LabelBuilder.builder()
                .withStyleClass("control-text")
                .withNonLocalizedText(String.valueOf(0))
                .build();
        FontAwesomeIconViewPane fontAwesomeIconViewPane = new FontAwesomeIconViewPane(this.power);
        powerIconAndLabel = BoxBuilder.builder()
                .withStyleClass("power-icon-and-label")
                .withNodes(fontAwesomeIconViewPane, this.powerLabel)
                .withOnMouseClicked(event -> {
                    if (!isCurrentShip()) {
                        slotBox.getSlot().getShipModule().togglePower();
                        slotBox.notifyChanged();
                        slotBox.refresh();
                        event.consume();
                    }
                })
                .buildHBox();
        powerIconAndLabel.setOnMousePressed(_ -> powerIconAndLabel.pseudoClassStateChanged(PseudoClass.getPseudoClass("pressed"), true));
        powerIconAndLabel.setOnMouseReleased(_ -> powerIconAndLabel.pseudoClassStateChanged(PseudoClass.getPseudoClass("pressed"), false));
        powerUp = FontAwesomeIconViewBuilder.builder()
                .withStyleClass("control-button")
                .withIcon(FontAwesomeIcon.ANGLE_UP)
                .withOnMouseClicked(event -> {
                    if (!isCurrentShip()) {
                        slotBox.getSlot().getShipModule().increasePowerGroup();
                        slotBox.notifyChanged();
                        slotBox.refresh();
                        event.consume();
                    }
                })
                .build();
        powerDown = FontAwesomeIconViewBuilder.builder()
                .withStyleClass("control-button")
                .withIcon(FontAwesomeIcon.ANGLE_DOWN)
                .withOnMouseClicked(event -> {
                    if (!isCurrentShip()) {
                        slotBox.getSlot().getShipModule().decreasePowerGroup();
                        slotBox.notifyChanged();
                        slotBox.refresh();
                        event.consume();
                    }
                })
                .build();
        this.getNodes().addAll(new FontAwesomeIconViewPane(powerUp), powerIconAndLabel, new FontAwesomeIconViewPane(powerDown));

    }

    @Override
    public void initEventHandling() {

    }

    private static boolean isCurrentShip() {
        return ApplicationState.getInstance().getPreferredCommander()
                .flatMap(commander -> ShipService.getShipConfigurations(commander).getSelectedShipConfiguration())
                .map(shipConfiguration -> ShipConfiguration.CURRENT == shipConfiguration)
                .orElse(Boolean.FALSE);
    }

    public void updatePower(ShipModule shipModule) {
        if (shipModule != null) {
//            this.power.setImage(ImageService.getImage("/images/ships/icons/" + (shipModule.isPowered() ? "powered" : "unpowered") + shipModule.getPowerGroup() + ".png"));
            this.powerLabel.setText(String.valueOf(shipModule.getPowerGroup()));
            powerIconAndLabel.pseudoClassStateChanged(PseudoClass.getPseudoClass("powered"), shipModule.isPowered());
            this.pseudoClassStateChanged(PseudoClass.getPseudoClass("configurable"), !isCurrentShip());
//            if (!isCurrentShip()) {
//                this.powerLabel.setGraphic(this.power);
//                this.powerLabel.register(this.power);
//            }
            final boolean hasPowerToggle = shipModule.hasPowerToggle();
            if (!isCurrentShip()) {
                final int powerGroup = shipModule.getPowerGroup();
                this.powerUp.setVisible(hasPowerToggle && powerGroup < 5);
                this.powerDown.setVisible(hasPowerToggle && powerGroup > 1);
                this.powerIconAndLabel.setVisible(hasPowerToggle);
            } else {
                this.powerUp.setVisible(false);
                this.powerDown.setVisible(false);
                this.powerIconAndLabel.setVisible(hasPowerToggle);
//                this.powerBox.getNodes().clear();
//                this.powerBox.getNodes().addAll(new GrowingRegion(), this.power, new GrowingRegion());
            }
        }
    }

//    private static DestroyableResizableImageView createIconWithTooltip(String imageResource, Integer powerGroup, String... styleClasses) {
//        final DestroyableResizableImageView icon = createIconWithoutTooltip(imageResource, styleClasses);
//        final DestroyableTooltip tooltip = TooltipBuilder.builder()
//                .withShowDelay(Duration.seconds(0.1))
//                .withText("ship.stats.config.power.group", powerGroup)
//                .build();
//        tooltip.install(icon);
//        return icon;
//    }
//
//    private static DestroyableResizableImageView createIconWithoutTooltip(String imageResource, String... styleClasses) {
//        return ResizableImageViewBuilder.builder()
//                .withStyleClasses(styleClasses)
//                .withImage(imageResource)
//                .build();
//    }
}
