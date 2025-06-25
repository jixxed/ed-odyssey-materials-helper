package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.util.Duration;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.ships.ImageSlot;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.SlotboxHoverEvent;
import nl.jixxed.eliteodysseymaterials.service.event.SlotboxOpenEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.Destroyable;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyablePopOver;

@Slf4j
public class ShipViewPopover extends DestroyablePopOver implements DestroyableEventTemplate {
    @Setter
    private SlotBox slotBox;

    public ShipViewPopover() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
//        shipViewPopOver = PopOverBuilder.builder()
//                .withStyleClass("shipbuilder-ship-view-popover")
////                .withContent(shipView)
//                .withDetachable(false)
//                .withHeaderAlwaysVisible(false)
//                .withCornerRadius(0)
//                .withArrowSize(0)
//                .withArrowIndent(0)
//                .withDestroyOnHide(false)
////                .withArrowLocation(PopOver.ArrowLocation.BOTTOM_RIGHT)
//                .withAutoFix(false)
//                .build();
//        shipViewPopOver.showingProperty().addListener((observable, oldValue, newValue) -> {
//            Platform.runLater(() -> {
//                if (Boolean.TRUE.equals(newValue)) {
//                    ((ShipView) shipViewPopOver.getContentNode()).animate();
//                }
//            });
//        });


        this.getStyleClass().add("shipbuilder-ship-view-popover");
        this.setDetachable(false);
        this.setHeaderAlwaysVisible(false);
        this.setCornerRadius(0);
        this.setArrowSize(0);
        this.setArrowIndent(0);
        this.setAutoFix(false);
        this.setAutoHide(false);
        this.showingProperty().addListener((observable, oldValue, newValue) -> {
            if (Boolean.TRUE.equals(newValue) && this.getContentNode() instanceof ShipView shipView) {
//                log.debug("animate");
                shipView.animate();
            }
        });
    }

    @Override
    public void initEventHandling() {

        register(EventService.addListener(true, this, /*9,*/ SlotboxOpenEvent.class, (event) -> {
//            log.debug("SlotboxOpenEvent {}", event);
            if (slotBox != event.getSlotBox()) {
                updateContent(event.getSlotBox());
                this.hide(Duration.ZERO);
            }
            slotBox = event.getSlotBox();
            showPopover(event.isHover(), slotBox);
            updateDetailsPopoverPosition();

        }));
        register(EventService.addListener(true, this, /*9,*/ SlotboxHoverEvent.class, (event) -> {
//            log.debug("SlotboxHoverEvent {}", event);
            //no updates if a menu is open
            if (slotBox != null && slotBox.isMenuOpen()) {
                return;
            }
            //update content if slotbox is not set, the shotbox has no menu open or the slotbox is different from the one in the event
            if (slotBox == null || !slotBox.isMenuOpen() || (event.getSlotBox().isMenuOpen() && slotBox != event.getSlotBox())) {
                if (slotBox != event.getSlotBox()) {
                    updateContent(event.getSlotBox());
                    this.hide(Duration.ZERO);
                }
            }
            slotBox = event.getSlotBox();
            showPopover(event.isHover(), slotBox);
            updateDetailsPopoverPosition();
        }));
    }

    private void showPopover(boolean show, SlotBox slotBox) {
        if (this.slotBox == null) {
            return;
        }
        if (show && hasContent(slotBox) && !this.isShowing()) {
            this.show(this.slotBox, -1);
        } else if (!show && this.isShowing() && !this.slotBox.isMenuOpen()) {
//            log.debug("hiding");
            this.hide(Duration.ZERO);
        }
    }

    private void updateContent(SlotBox slotBox) {
        if (!hasContent(slotBox)) {
//            log.debug("SlotBox has no content, hiding");
            this.hide(Duration.ZERO);
            return;
        }
        var shipView = new ShipView(slotBox);
        if (this.getContentNode() instanceof Destroyable destroyable) {
            destroyable.destroy();
        }
        this.setContentNode(this.register(shipView));
    }

    private static boolean hasContent(SlotBox slotBox) {
        return slotBox.getSlot() instanceof ImageSlot;
    }

    private void updateDetailsPopoverPosition() {

        Platform.runLater(() -> {
            final Bounds boundsInLocal = slotBox.getBoundsInLocal();
            final Bounds bounds = slotBox.localToScreen(boundsInLocal);

            if (getContentNode() instanceof ShipView shipView) {
                final double width = shipView.getWidth();
                final double height = shipView.getHeight();
                double x = bounds.getMinX() - 1;
                double y = bounds.getMinY() - height - 2;
//                log.debug("width: {}, height: {}, x: {}, y: {}", shipView.getPrefWidth(), shipView.getPrefHeight(), x, y);
                if (width == 0) {
                    shipView.addChangeListener(shipView.widthProperty(), (observable, oldValue, newValue) -> {
                        updateDetailsPopoverPosition();
                    });
                    return;
                }

//                // compare to maxX of current screen
//                final Screen screen = Screen.getScreensForRectangle(x, y, 1, 1).getFirst();
//                if (x + width > screen.getVisualBounds().getMaxX()) {
//                    x = bounds.getMinX() - width - 10;
//                }
//                if (y + height > screen.getVisualBounds().getMaxY()) {
//                    y = bounds.getMaxY() - height - 10;
//                }
                setX(x);
                setAnchorX(x);
                setY(y);
                setAnchorY(y);
            }
        });
    }
}
