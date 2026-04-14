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
import nl.jixxed.eliteodysseymaterials.builder.FontAwesomeIconViewBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ShipConfiguration;
import nl.jixxed.eliteodysseymaterials.domain.ships.HardpointModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.service.ships.ShipService;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableFontAwesomeIconView;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;

public class SlotBoxClassControl extends DestroyableVBox implements DestroyableEventTemplate {
    private DestroyableLabel classLabel;
    private DestroyableFontAwesomeIconView classUp;
    private DestroyableFontAwesomeIconView classDown;
    private final SlotBox slotBox;

    public SlotBoxClassControl(SlotBox slotBox) {
        this.slotBox = slotBox;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("class-control");
        classLabel = LabelBuilder.builder()
                .withStyleClass("control-text")
                .build();
        if (!isCurrentShip()) {
            classUp = FontAwesomeIconViewBuilder.builder()
                    .withStyleClass("control-button")
                    .withIcon(FontAwesomeIcon.ANGLE_UP)
                    .withOnMouseClicked(event -> {
                        slotBox.getSlot().getShipModule().findHigherClass().ifPresent(slotBox::replaceModule);
                        event.consume();
                    })
                    .build();
            classDown = FontAwesomeIconViewBuilder.builder()
                    .withStyleClass("control-button")
                    .withIcon(FontAwesomeIcon.ANGLE_DOWN)
                    .withOnMouseClicked(event -> {
                        slotBox.getSlot().getShipModule().findLowerClass().ifPresent(slotBox::replaceModule);
                        event.consume();
                    })
                    .build();
            this.getNodes().addAll(classUp, classLabel, classDown);
        } else {
            this.getNodes().addAll(new GrowingRegion(), classLabel, new GrowingRegion());
        }
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

    public void updateClass(ShipModule shipModule) {
        if (shipModule != null && !(shipModule instanceof HardpointModule)) {
            if (!isCurrentShip()) {
                this.classUp.setVisible(shipModule.findHigherClass().isPresent());
                this.classDown.setVisible(shipModule.findLowerClass().isPresent());
            }
            this.classLabel.setText(shipModule.getModuleClass().name());
        }
    }
}
