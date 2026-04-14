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
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.service.ships.ShipService;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableFontAwesomeIconView;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;

public class SlotBoxSizeControl extends DestroyableVBox implements DestroyableEventTemplate {
    private DestroyableLabel sizeLabel;
    private DestroyableFontAwesomeIconView sizeUp;
    private DestroyableFontAwesomeIconView sizeDown;
    private final SlotBox slotBox;

    public SlotBoxSizeControl(SlotBox slotBox) {
        this.slotBox = slotBox;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("size-control");
        sizeLabel = LabelBuilder.builder()
                .withStyleClass("control-text")
                .build();
        if (!isCurrentShip()) {
            sizeUp = FontAwesomeIconViewBuilder.builder()
                    .withStyleClass("control-button")
                    .withIcon(FontAwesomeIcon.ANGLE_UP)
                    .withOnMouseClicked(event -> {
                        slotBox.getSlot().getShipModule().findHigherSize().ifPresent(slotBox::replaceModule);
                        event.consume();
                    })
                    .build();
            sizeDown = FontAwesomeIconViewBuilder.builder()
                    .withStyleClass("control-button")
                    .withIcon(FontAwesomeIcon.ANGLE_DOWN)
                    .withOnMouseClicked(event -> {
                        slotBox.getSlot().getShipModule().findLowerSize().ifPresent(slotBox::replaceModule);
                        event.consume();
                    })
                    .build();
            this.getNodes().addAll(sizeUp, sizeLabel, sizeDown);
        } else {
            this.getNodes().addAll(new GrowingRegion(), sizeLabel, new GrowingRegion());
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

    public void updateSize(ShipModule shipModule) {
        if (shipModule != null) {
            if (!isCurrentShip()) {
                this.sizeUp.setVisible(isSizeButtonEnabled(shipModule) && shipModule.getModuleSize().intValue() < slotBox.getSlot().getSlotSize() && shipModule.findHigherSize().map(biggerModule -> biggerModule.getModuleSize().intValue() <= slotBox.getSlot().getSlotSize()).orElse(false));
                this.sizeDown.setVisible(isSizeButtonEnabled(shipModule) && shipModule.findHighestSize(Math.min(slotBox.getSlot().getSlotSize(), shipModule.getModuleSize().intValue())).flatMap(ShipModule::findLowerSize).isPresent());
            }
            final int min = Math.min(
                    shipModule.findHighestSize(slotBox.getSlot().getSlotSize()).map(highestModule -> highestModule.getModuleSize().intValue()).orElse(0),
                    shipModule.getModuleSize().intValue()
            );
            this.sizeLabel.setText(String.valueOf(min));
//            layer2.setVisible(min != shipModule.getModuleSize().intValue());
        }
    }

    private boolean isSizeButtonEnabled(ShipModule shipModule) {
        return switch (shipModule.getName()) {
            case FRAME_SHIFT_DRIVE_OVERCHARGE, FRAME_SHIFT_DRIVE_OVERCHARGE_PRE, SENSORS, LIFE_SUPPORT -> false;
            default -> true;
        };

    }
}
