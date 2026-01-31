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

import java.util.NoSuchElementException;

@Slf4j
public class ModuleDetailsPopover extends DestroyablePopOver implements DestroyableEventTemplate {
    @Setter
    private SlotBox slotBox;

    public ModuleDetailsPopover() {
        initComponents();
        initEventHandling();
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

        register(EventService.addListener(true, this, SlotboxOpenEvent.class, (event) -> {
//            log.debug("SlotboxOpenEvent {}", event);
            updateContent(event.getShipModule());
            slotBox = event.getSlotBox();
            showPopover(event.isHover(), event.getShipModule());
            updateDetailsPopoverPosition();

        }));
        register(EventService.addListener(true, this, SlotboxHoverEvent.class, (event) -> {
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

        }));
        register(EventService.addListener(true, this, SlotboxEngineeringEvent.class, (event) -> {
            updateContent(event.getShipModule());
            slotBox = event.getSlotBox();
            showPopover(true, event.getShipModule());
            updateDetailsPopoverPosition();
        }));
        register(EventService.addListener(true, this, ModuleSelectHoverEvent.class, (event) -> {
            //update content with the shipmodule from the event or the one from the slotbox, depending on whether the button is hovered
            ShipModule shipModule = event.isHover() ? event.getShipModule() : slotBox.getSlot().getShipModule();
            updateContent(shipModule);
            showPopover(true, event.getShipModule());
            updateDetailsPopoverPosition();
        }));
    }

    private void showPopover(boolean show, ShipModule shipModule) {
        if (slotBox == null || slotBox.getBoundsInLocal() == null) {
            return;
        }
        if (show && hasContent(shipModule) && !this.isShowing()) {
            try{
                this.show(slotBox, 0);
            }catch (Exception ex){
                log.error("Error showing ModuleDetailsPopover", ex);
            }
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
    }

    private static boolean hasContent(ShipModule shipModule) {
        return shipModule != null;
    }

    private void updateDetailsPopoverPosition() {

        Platform.runLater(() -> {
            final Bounds boundsInLocal = slotBox.getBoundsInLocal();
            final Bounds bounds = slotBox.localToScreen(boundsInLocal);
            if (bounds != null) {
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
                    try {
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
                    } catch (NoSuchElementException e) {
                        log.error("cannot find screen for x:{} y:{}", x, y);
                    }

                }
            }
        });
    }
}
