package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;

class SlotBox extends VBox {
    private final Slot slot;
    private final HorizonsShipBuilderTab tab;

    SlotBox(final HorizonsShipBuilderTab tab,final Slot slot) {
        this.getStyleClass().add("shipbuilder-slots-slotbox");
        this.slot = slot;
        this.tab = tab;
        this.getChildren().add(LabelBuilder.builder().withNonLocalizedText(slot.getSlotType().name()).build());
        if(slot.getSlotType().equals(SlotType.HARDPOINT)) {
            this.onMouseEnteredProperty().set(event -> {
                tab.toggleHardpointImage(true);
                tab.drawHardpointLine(slot.getIndex());
            });
            this.onMouseExitedProperty().set(event -> tab.toggleHardpointImage(false));
        }
        if(slot.getSlotType().equals(SlotType.UTILITY)) {
            this.onMouseEnteredProperty().set(event -> {
                tab.toggleUtilityImage(true);
                tab.drawUtilityLine(slot.getIndex());
            });
            this.onMouseExitedProperty().set(event -> tab.toggleUtilityImage(false));
        }
        ShipModule.getModules(slot.getSlotType());
    }
}
