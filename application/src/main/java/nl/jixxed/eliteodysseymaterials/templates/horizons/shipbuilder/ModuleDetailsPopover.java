package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.stage.Screen;
import javafx.util.Duration;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.Destroyable;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyablePopOver;

@Slf4j
public class ModuleDetailsPopover extends DestroyablePopOver implements DestroyableEventTemplate {
    @Setter
    private SlotBox slotBox;

    public ModuleDetailsPopover() {
        initComponents();
        initEventHandling();
//        detailsPopOver = PopOverBuilder.builder()
//                .withStyleClass()
////                .withContent(moduleDetails)
//                .withDetachable(false)
//                .withHeaderAlwaysVisible(false)
//                .withCornerRadius(0)
//                .withArrowSize(0)
//                .withArrowIndent(0)
//                .withDestroyOnHide(false)
//                .withAutoFix(false)
//                .build();
//        detailsPopOver.showingProperty().addListener((observable, oldValue, newValue) -> {
//            if (Boolean.TRUE.equals(newValue)) {
//                updateDetailsPopoverPosition();
//            }
//
//        });
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("shipbuilder-details-popover");
        this.setDetachable(false);
        this.setHeaderAlwaysVisible(false);
        this.setCornerRadius(0);
        this.setArrowSize(0);
        this.setArrowIndent(0);
        this.setAutoFix(false);
        this.setAutoHide(false);
        this.showingProperty().addListener((observable, oldValue, newValue) -> {
            if (Boolean.TRUE.equals(newValue)) {
                updateDetailsPopoverPosition();
            }
        });
    }

    @Override
    public void initEventHandling() {

        register(EventService.addListener(true, this, /*9,*/ SlotboxOpenEvent.class, (event) -> {
//            log.debug("SlotboxOpenEvent {}", event);
            updateContent(event.getShipModule());
            slotBox = event.getSlotBox();
            showPopover(event.isHover(), event.getShipModule());
            updateDetailsPopoverPosition();
//            if (event.isShow() && event.getShipModule() != null) {
//                if (!this.isShowing()) {
//                    updateContent(event.getShipModule());
//                    this.show(slotBox);
//                }
//            } else if (this.isShowing() && !slotBox.isMenuOpen()) {
//                this.hide(Duration.ZERO);
//            }

        }));
        register(EventService.addListener(true, this, /*9,*/ SlotboxHoverEvent.class, (event) -> {
//            log.debug("SlotboxHoverEvent {}", event);
            //no updates if a menu is open
            if (slotBox != null && slotBox.isMenuOpen()) {
                return;
            }
            //update content if slotbox is not set, the shotbox has no menu open or the slotbox is different from the one in the event
            if (slotBox == null || !slotBox.isMenuOpen() || (event.getSlotBox().isMenuOpen() && slotBox != event.getSlotBox())) {
                updateContent(event.getShipModule());
            }
            slotBox = event.getSlotBox();
            showPopover(event.isHover(), event.getShipModule());
            updateDetailsPopoverPosition();
//            if (event.isShow() && event.getShipModule() != null) {
//                if (!this.isShowing()) {
//                    updateContent(event.getShipModule());
//                    this.show(slotBox);
//                }
//            } else if (this.isShowing() && !slotBox.isMenuOpen()) {
//                this.hide(Duration.ZERO);
//            }

        }));
        register(EventService.addListener(true, this, /*9,*/ SlotboxEngineeringEvent.class, (event) -> {
            updateContent(event.getShipModule());
            slotBox = event.getSlotBox();
            showPopover(true, event.getShipModule());
            updateDetailsPopoverPosition();
        }));
        register(EventService.addListener(true, this, /*9,*/ ModuleSelectHoverEvent.class, (event) -> {
//            log.debug("ModuleSelectHoverEvent {}", event);
            //update content with the shipmodule from the event or the one from the slotbox, depending on whether the button is hovered
            ShipModule shipModule = event.isHover() ? event.getShipModule() : slotBox.getSlot().getShipModule();
            updateContent(shipModule);
            showPopover(true, event.getShipModule());
            updateDetailsPopoverPosition();
        }));
//        register(EventService.addListener(true, this, /*9,*/ ModuleSelectCloseEvent.class, (event) -> {
////            log.debug("ModuleSelectCloseEvent {}", event);
//            //when the menu is showing
////            if (this.isShowing()) {
////                this.hide(Duration.ZERO);
////            }
//        }));
    }

    private void showPopover(boolean show, ShipModule shipModule) {
        if (slotBox == null) {
            return;
        }
        if (show && hasContent(shipModule) && !this.isShowing()) {
            this.show(slotBox, 0);
        } else if (!show && this.isShowing() && !slotBox.isMenuOpen()) {
//            log.debug("hiding");
            this.hide(Duration.ZERO);
        }
    }

    private void updateContent(ShipModule shipModule) {
        if (!hasContent(shipModule)) {
//            log.debug("ShipModule has no content, hiding");
            this.hide(Duration.ZERO);
            return;
        }
        var moduleDetails = new ModuleDetails(shipModule);
        if (this.getContentNode() instanceof Destroyable destroyable) {
            destroyable.destroy();
        }
        this.setContentNode(this.register(moduleDetails));
        this.getContentNode().setMouseTransparent(true);
    }

    private static boolean hasContent(ShipModule shipModule) {
        return shipModule != null;
    }

    private void updateDetailsPopoverPosition() {

        Platform.runLater(() -> {
            final Bounds boundsInLocal = slotBox.getBoundsInLocal();
            final Bounds bounds = slotBox.localToScreen(boundsInLocal);
            double x = bounds.getMaxX() + 1;
            double y = bounds.getMinY();
            if (getContentNode() instanceof ModuleDetails moduleDetails) {
                final double width = moduleDetails.getWidth();
                final double height = moduleDetails.getHeight();
//                log.debug("width: {}, height: {}, x: {}, y: {}", moduleDetails.getPrefWidth(), moduleDetails.getPrefHeight(), x, y);
                if (width == 0) {
                    moduleDetails.addChangeListener(moduleDetails.widthProperty(), (observable, oldValue, newValue) -> {
                        updateDetailsPopoverPosition();
                    });
                    return;
                }
                // compare to maxX of current screen
                final Screen screen = Screen.getScreensForRectangle(x, y, 1, 1).getFirst();
                if (x + width > screen.getVisualBounds().getMaxX()) {
                    x = bounds.getMinX() - width - 2;
                }
                if (y + height > screen.getVisualBounds().getMaxY()) {
                    y = bounds.getMaxY() - height;
                }
                setX(x);
                setAnchorX(x);
                setY(y);
                setAnchorY(y);
            }
        });
    }
}
