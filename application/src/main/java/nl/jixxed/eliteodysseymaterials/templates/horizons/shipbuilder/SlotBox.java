package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.geometry.Bounds;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import org.controlsfx.control.PopOver;

import java.util.Optional;

class SlotBox extends HBox {
    private final Slot slot;
    private final HorizonsShipBuilderTab tab;
    private final DestroyableLabel label;
    private final DestroyableLabel module;
    private final DestroyableLabel size;
    private final VBox texts;
    private static final DataFormat customFormat =
            new DataFormat("ship.module");

    SlotBox(final HorizonsShipBuilderTab tab, final Slot slot) {
        this.getStyleClass().add("shipbuilder-slots-slotbox");
        this.texts = BoxBuilder.builder().withStyleClass("shipbuilder-slots-slotbox-texts").buildVBox();
        this.slot = slot;
        this.tab = tab;
        this.label = LabelBuilder.builder().withNonLocalizedText(slot.getSlotType().name() + slot.getIndex()).build();
        this.size = LabelBuilder.builder().withStyleClass("shipbuilder-slots-slotbox-size-label").withNonLocalizedText(slot.getSlotSizeName()).build();
        //testing
        this.module = LabelBuilder.builder().withNonLocalizedText(Optional.ofNullable(slot.getShipModule()).map(ShipModule::getName).orElse("")).build();
        this.texts.getChildren().addAll(this.label, this.module);
        this.getChildren().addAll(this.size, new Separator(Orientation.VERTICAL), this.texts);
        if (slot.getSlotType().equals(SlotType.HARDPOINT)) {
            this.onMouseEnteredProperty().set(event -> {
                tab.toggleHardpointImage(true);
                tab.drawHardpointLine(slot.getIndex());
            });
            this.onMouseExitedProperty().set(event -> tab.toggleHardpointImage(false));
        }
        if (slot.getSlotType().equals(SlotType.UTILITY)) {
            this.onMouseEnteredProperty().set(event -> {
                tab.toggleUtilityImage(true);
                tab.drawUtilityLine(slot.getIndex());
            });
            this.onMouseExitedProperty().set(event -> tab.toggleUtilityImage(false));
        }
        ShipModule.getModules(slot.getSlotType());

        this.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                final PopOver popOver = getPopOver();

                final Bounds boundsInLocal = this.getBoundsInLocal();
                final Bounds bounds = this.localToScreen(boundsInLocal);
                popOver.show(this, bounds.getMinX(),
                        bounds.getMaxY());
            } else if (event.getButton().equals(MouseButton.SECONDARY)) {
                if (slot.getSlotType() != SlotType.CORE) {
                    this.slot.setShipModule(null);
                    this.refresh();
                }
            }
        });

        this.setOnDragDetected(event -> {
            final Dragboard db = this.startDragAndDrop(TransferMode.ANY);

            /* Put a string on a dragboard */
            if (this.slot.getShipModule() != null && this.slot.getSlotType() != SlotType.CORE) {
                final ClipboardContent content = new ClipboardContent();
                content.put(customFormat, this.slot.getShipModule());
                db.setContent(content);
                db.setDragView(this.snapshot(null, null), event.getX(), event.getY());
                event.consume();
            }
        });
        this.setOnDragOver(event -> {
            if (slot.getSlotType().equals(SlotType.HARDPOINT)) {
                tab.toggleHardpointImage(true);
                tab.drawHardpointLine(slot.getIndex());
            }
            if (slot.getSlotType().equals(SlotType.UTILITY)) {
                tab.toggleUtilityImage(true);
                tab.drawUtilityLine(slot.getIndex());
            }
            /* data is dragged over the target */
            /* accept it only if it is not dragged from the same node
             * and if it has a string data */
            if (event.getGestureSource() != this &&
                    event.getDragboard().hasContent(customFormat) && this.slot.getSlotType().getModuleClass().isAssignableFrom(((ShipModule) event.getDragboard().getContent(customFormat)).getClass())) {
                /* allow for both copying and moving, whatever user chooses */
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }

            event.consume();
        });
        this.setOnDragEntered(event -> {
//            if(event.getAcceptedTransferMode() != null) {
//                this.getStyleClass().add("shipbuilder-slots-slotbox-hover-good");
//            } else {
//                this.getStyleClass().add("shipbuilder-slots-slotbox-hover-bad");
//            }
//            if (slot.getSlotType().equals(SlotType.HARDPOINT)) {
//                    tab.toggleHardpointImage(true);
//                    tab.drawHardpointLine(slot.getIndex());
//            }
//            if (slot.getSlotType().equals(SlotType.UTILITY)) {
//                    tab.toggleUtilityImage(true);
//                    tab.drawUtilityLine(slot.getIndex());
//            }
            /* the drag-and-drop gesture entered the target */
            /* show to the user that it is an actual gesture target */
            if (event.getGestureSource() != this &&
                    event.getDragboard().hasContent(customFormat) && this.slot.getSlotType().getModuleClass().isAssignableFrom(((ShipModule) event.getDragboard().getContent(customFormat)).getClass())) {
                this.module.setText(((ShipModule) event.getDragboard().getContent(customFormat)).getName());
                this.getStyleClass().add("shipbuilder-slots-slotbox-hover-good");
            } else {
                this.getStyleClass().add("shipbuilder-slots-slotbox-hover-bad");

            }

            event.consume();
        });
        this.setOnDragExited(event -> {
            this.getStyleClass().removeAll("shipbuilder-slots-slotbox-hover-bad", "shipbuilder-slots-slotbox-hover-good");
            if (slot.getSlotType().equals(SlotType.HARDPOINT)) {
                tab.toggleHardpointImage(false);
            }
            if (slot.getSlotType().equals(SlotType.UTILITY)) {
                tab.toggleUtilityImage(false);
            }
            /* mouse moved away, remove the graphical cues */
            this.refresh();
            event.consume();
        });
        this.setOnDragDropped(event -> {
            this.getStyleClass().removeAll("shipbuilder-slots-slotbox-hover-bad", "shipbuilder-slots-slotbox-hover-good");
            /* data dropped */
            /* if there is a string data on dragboard, read it and use it */
            final Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasContent(customFormat) && db.getContent(customFormat) != null) {
                final Object content = event.getDragboard().getContent(customFormat);
                final ClipboardContent contentOld = new ClipboardContent();
                contentOld.put(customFormat, this.slot.getShipModule() != null ? this.slot.getShipModule() : "null");
                event.getDragboard().setContent(contentOld);
                this.slot.setShipModule(((ShipModule) content));
                this.refresh();
                success = true;
            }
            /* let the source know whether the string was successfully
             * transferred and used */
            event.setDropCompleted(success);

            event.consume();
        });
        this.setOnDragDone(event -> {
            /* the drag and drop gesture ended */
            /* if the data was successfully moved, clear it */
            if (event.getTransferMode() == TransferMode.MOVE) {
                final Object content = event.getDragboard().getContent(customFormat);
                if (!(content instanceof String) && this.slot.getSlotType().getModuleClass().isAssignableFrom(((ShipModule) event.getDragboard().getContent(customFormat)).getClass())) {
                    this.slot.setShipModule(((ShipModule) content));
                } else {
                    this.slot.setShipModule(null);
                }
                this.refresh();
            }
            event.consume();
        });
    }

    private static PopOver getPopOver() {
        final VBox content = BoxBuilder.builder().withNode(LabelBuilder.builder().withNonLocalizedText("test").build()).withStyleClass("shipbuilder-slots-slotbox-popover-vbox").buildVBox();
        final ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.getStyleClass().add("shipbuilder-slots-slotbox-popover-content");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        final PopOver popOver = new PopOver(scrollPane);
        popOver.getStyleClass().add("shipbuilder-slots-slotbox-popover");

        popOver.setDetachable(false);
        popOver.setHeaderAlwaysVisible(false);
        popOver.arrowSizeProperty().set(0);
        popOver.arrowIndentProperty().set(0);
        popOver.cornerRadiusProperty().set(0);
        return popOver;
    }

    private void refresh() {
        this.module.setText(Optional.ofNullable(this.slot.getShipModule()).map(ShipModule::getName).orElse(""));
    }
}
