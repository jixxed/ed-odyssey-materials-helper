package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.css.PseudoClass;
import javafx.geometry.Bounds;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.input.*;
import javafx.util.Duration;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ShipConfiguration;
import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HardpointGroup;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ShipBuilderEvent;
import nl.jixxed.eliteodysseymaterials.service.event.SlotboxHoverEvent;
import nl.jixxed.eliteodysseymaterials.service.event.SlotboxOpenEvent;
import nl.jixxed.eliteodysseymaterials.service.ships.ShipService;
import nl.jixxed.eliteodysseymaterials.templates.components.FontAwesomeIconViewPane;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class SlotBox extends DestroyableStackPane {


    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    @Getter
    private final Slot slot;
    @Getter
    private ModulesSection modulesSection;
    private final DestroyableLabel emptyLabel;
    private final DestroyableLabel moduleName;
    private final BlueprintsTextFlow blueprints;
    private final DestroyableVBox moduleData;
    private final DestroyableHBox iconBox;
    private final DestroyableHBox layer1;
    private final DestroyableStackPane layer2;
    private final DestroyableLabel hardpointGroupLabel;
    private final FontAwesomeIconViewPane changedIcon;
    private static final DataFormat customFormat = new DataFormat("ship.module");
    private final SlotBoxClassControl classControl;
    private final SlotBoxSizeControl sizeControl;
    private final SlotBoxMountingControl mountingControl;
    private final SlotBoxPowerControl powerControl;
    private ModuleSelectPopover moduleSelectPopover;

    SlotBox(final ModulesSection modulesSection, final Slot slot) {
        this.getStyleClass().add("slot-box");
        this.setFocusTraversable(true);
        layer1 = BoxBuilder.builder()
                .withStyleClass("slot-content")
                .buildHBox();
        layer2 = StackPaneBuilder.builder()
                .withStyleClass("overlay")
                .withVisibility(false)
                .withNodes(LabelBuilder.builder()
                        .withText("tab.ships.slot.resized")
                        .withStyleClass("resize-warning")
                        .build())
                .build();
        this.getNodes().addAll(layer1, layer2);
        layer1.pseudoClassStateChanged(PseudoClass.getPseudoClass("military"), SlotType.MILITARY.equals(slot.getSlotType()));
        layer1.pseudoClassStateChanged(PseudoClass.getPseudoClass("cargo"), SlotType.CARGO.equals(slot.getSlotType()));

        this.iconBox = BoxBuilder.builder()
                .withStyleClass("module-icons")
                .buildHBox();
        this.moduleData = BoxBuilder.builder()
                .withStyleClass("module-data")
                .buildVBox();
        this.slot = slot;
        if (SlotType.HARDPOINT.equals(slot.getSlotType())) {
            this.slot.setHardpointGroup(HardpointGroup.A);
        }
        this.modulesSection = modulesSection;
        sizeControl = new SlotBoxSizeControl(this);
        classControl = new SlotBoxClassControl(this);
        mountingControl = new SlotBoxMountingControl(this);
        powerControl = new SlotBoxPowerControl(this);

        this.emptyLabel = LabelBuilder.builder()
                .withStyleClass("empty")
                .withText("ship.module.slot.empty")
                .build();
        DestroyableLabel size = LabelBuilder.builder()
                .withStyleClass("slot-size")
                .withNonLocalizedText(slot.getSlotSizeName())
                .build();

        this.changedIcon = new FontAwesomeIconViewPane(FontAwesomeIconViewBuilder.builder()
                .withStyleClass("changed-icon")
                .withIcon(FontAwesomeIcon.REFRESH)
                .build());
        //testing
        this.moduleName = LabelBuilder.builder()
                .withStyleClass("name")
                .build();
        this.blueprints = new BlueprintsTextFlow();

        updateBlueprints(slot.getShipModule(), slot.getOldShipModule());

        this.moduleData.getNodes().addAll(this.iconBox, this.moduleName, this.blueprints);

        String tagKey = "blank";
        if (SlotType.HARDPOINT.equals(slot.getSlotType())) {
            tagKey = this.slot.getHardpointGroup().getLocalizationKey();
        } else if (SlotType.MILITARY.equals(slot.getSlotType())) {
            tagKey = "ships.slot.type.military";
        } else if (SlotType.CARGO.equals(slot.getSlotType())) {
            tagKey = "ships.slot.type.cargo";
        }
        hardpointGroupLabel = LabelBuilder.builder()
                .withStyleClass("tag")
                .withText(tagKey)
                .build();
        final DestroyableVBox vBox = BoxBuilder.builder()
                .withStyleClass("slot-info")
                .withNodes(hardpointGroupLabel, size, new GrowingRegion(), changedIcon)
                .buildVBox();
        layer1.getNodes().add(vBox);
        DestroyableSeparator destroyableSeparator = new DestroyableSeparator(Orientation.VERTICAL);
        destroyableSeparator.getStyleClass().add("splitter");
        layer1.getNodes().addAll(destroyableSeparator, this.emptyLabel, sizeControl, mountingControl, classControl, this.moduleData, powerControl);

        IntegerProperty maxGrade = new SimpleIntegerProperty(0);
        maxGrade.set(Optional.ofNullable(slot.getShipModule())
                .map(shipModule -> shipModule.getModifications().stream()
                        .findFirst()
                        .map(modification -> HorizonsBlueprintConstants.getBlueprintGrades(shipModule.getName().getPrimary(), modification.getModification()).stream().max(Comparator.comparing(HorizonsBlueprintGrade::getGrade)).map(HorizonsBlueprintGrade::getGrade).orElse(0))
                        .orElse(0))
                .orElse(0));
        mouseBehaviour(slot);
        this.refresh();
    }

    private void mouseBehaviour(Slot slot) {

        this.addEventBinding(this.onMouseEnteredProperty(), event -> {
            if (!event.isAltDown()) {
                EventService.publish(new SlotboxHoverEvent(this, this.slot.getShipModule(), true));
            }
        });
        this.addEventBinding(this.onMouseExitedProperty(), event -> {
            if (!event.isAltDown()) {
                EventService.publish(new SlotboxHoverEvent(this, this.slot.getShipModule(), false));
            }
        });

        if (!isCurrentShip()) {
            this.addEventBinding(this.onMouseClickedProperty(), event -> {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    if (this.moduleSelectPopover != null && this.moduleSelectPopover.isShowing()) {
                        close();
                    } else {
                        this.moduleSelectPopover = getModuleSelectPopover();

                        final Bounds boundsInLocal = this.getBoundsInLocal();
                        final Bounds bounds = this.localToScreen(boundsInLocal);
                        try{
                            this.moduleSelectPopover.show(this, bounds.getMinX(),
                                    bounds.getMaxY());
                            EventService.publish(new SlotboxOpenEvent(this, this.slot.getShipModule(), true));
                        }catch (Exception ex){
                            log.error("Error showing ModuleSelectPopover", ex);
                        }
                    }
                } else if (event.getButton().equals(MouseButton.SECONDARY)) {
                    close();

                    if (!slot.getSlotType().isCore()) {
                        this.slot.setShipModule(null);
                        notifyChanged();
                        this.refresh();
                    }
                }
            });
            this.addEventBinding(this.onDragDetectedProperty(), event -> {
                close();
//                log.debug("setOnDragDetected " + this.slot.getIndex());
                final Dragboard db = this.startDragAndDrop(TransferMode.ANY);
                /* Put a string on a dragboard */
                if (this.slot.isOccupied() && !slot.getSlotType().isCore()) {
                    final ClipboardContent content = new ClipboardContent();
                    content.put(customFormat, new DragboardContent(this.slot.getShipModule(), this.slot.getOldShipModule()));
                    db.setContent(content);
                    db.setDragView(this.snapshot(null, null), event.getX(), event.getY());
                    event.consume();
                }
            });
            AtomicReference<TransferMode> atomicBoolean = new AtomicReference<>();
            this.addEventBinding(this.onDragOverProperty(), event -> {

                //drag from outside the application
                if (((SlotBox) event.getGestureSource()) != null && event.getDragboard() != null) {
                    /* data is dragged over the target */
                    /* accept it only if it is not dragged from the same node
                     * and if it has a string data */
                    final Slot sourceSlot = ((SlotBox) event.getGestureSource()).slot;
                    if (event.getGestureSource() != this
                            && event.getDragboard().hasContent(customFormat)
                            && isSlotValid(this.slot, ((DragboardContent) event.getDragboard().getContent(customFormat)).shipModule())
                            && moduleCouldFit(this.slot, ((DragboardContent) event.getDragboard().getContent(customFormat)).shipModule())
                            && (event.getTransferMode() == TransferMode.COPY || moduleCouldFit(sourceSlot, this.slot.getShipModule()))

                    ) {
                        /* allow for both copying and moving, whatever user chooses */
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                    }
                    if (event.getTransferMode() == TransferMode.COPY) {
                        ((SlotBox) event.getGestureSource()).moduleName.addBinding(((SlotBox) event.getGestureSource()).moduleName.textProperty(), LocaleService.getStringBinding(sourceSlot.getShipModule().getName().getLocalizationKey()));
                        ((SlotBox) event.getGestureSource()).showContents(sourceSlot.getShipModule(), sourceSlot.getOldShipModule());
                    } else if (event.getTransferMode() == TransferMode.MOVE && this.slot.isOccupied()
                            && isSlotValid(sourceSlot, this.slot.getShipModule())
                            && isSlotValid(this.slot, ((DragboardContent) event.getDragboard().getContent(customFormat)).shipModule())
                            && moduleCouldFit(this.slot, ((DragboardContent) event.getDragboard().getContent(customFormat)).shipModule())
                            && moduleCouldFit(sourceSlot, this.slot.getShipModule())
                    ) {

                        ((SlotBox) event.getGestureSource()).moduleName.addBinding(((SlotBox) event.getGestureSource()).moduleName.textProperty(), LocaleService.getStringBinding(this.slot.getShipModule().getName().getLocalizationKey()));
                        ((SlotBox) event.getGestureSource()).showContents(this.slot.getShipModule(), this.slot.getOldShipModule());
                    } else if (event.getTransferMode() == TransferMode.MOVE && event.getGestureSource() != this) {
                        ((SlotBox) event.getGestureSource()).hideContents();
                    }
                    if (!event.getTransferMode().equals(atomicBoolean.get())) {
//                        log.debug("updateTransferMode " + this.slot.getIndex());
                        atomicBoolean.set(event.getTransferMode());
                        dragOver(event);
                    }

                }
                event.consume();
            });
            this.addEventBinding(this.onDragEnteredProperty(), event -> {
//                log.debug("setOnDragEntered " + this.slot.getIndex());
                /* the drag-and-drop gesture entered the target */
                /* show to the user that it is an actual gesture target */

                if (((SlotBox) event.getGestureSource()) != null) {
                    dragOver(event);
                    this.requestFocus();
                    event.consume();
                }
            });
            this.addEventBinding(this.onDragExitedProperty(), event -> {
                layer1.pseudoClassStateChanged(PseudoClass.getPseudoClass("bad"), false);
                layer1.pseudoClassStateChanged(PseudoClass.getPseudoClass("good"), false);
                /* mouse moved away, remove the graphical cues */
                this.refresh();
                if (!this.slot.isOccupied()) {
                    hideContents();
                }
                event.consume();
            });
            this.addEventBinding(this.onDragDroppedProperty(), event -> {
//                log.debug("setOnDragDropped " + this.slot.getIndex());
                layer1.pseudoClassStateChanged(PseudoClass.getPseudoClass("bad"), false);
                layer1.pseudoClassStateChanged(PseudoClass.getPseudoClass("good"), false);
                /* data dropped */
                /* if there is a string data on dragboard, read it and use it */
                if (((SlotBox) event.getGestureSource()) != null && event.getDragboard() != null) {
                    final Dragboard db = event.getDragboard();
                    boolean success = false;
                    if (db.hasContent(customFormat)
                            && db.getContent(customFormat) != null
                            && moduleCouldFit(this.slot, ((DragboardContent) event.getDragboard().getContent(customFormat)).shipModule())
                            && (event.getTransferMode() == TransferMode.COPY || (!this.slot.isOccupied() || isSlotValid(((SlotBox) event.getGestureSource()).slot, this.slot.getShipModule())))
                    ) {
                        final Object content = event.getDragboard().getContent(customFormat);
                        final ClipboardContent contentOld = new ClipboardContent();
                        contentOld.put(customFormat, this.slot.isOccupied() ? new DragboardContent(this.slot.getShipModule(), this.slot.getOldShipModule()) : "null");
                        event.getDragboard().setContent(contentOld);
                        this.slot.setShipModule(((DragboardContent) content).shipModule());
                        this.slot.setOldShipModule(((DragboardContent) content).oldShipModule());
                        if (((DragboardContent) content).shipModule().getModuleSize().intValue() > this.slot.getSlotSize()) {
                            this.slot.getShipModule().findLowerSize(this.slot.getSlotSize()).ifPresent(this::replaceModule);
                        }
                        notifyChanged();
                        this.refresh();
                        success = true;
                    }
                    /* let the source know whether the string was successfully
                     * transferred and used */
                    event.setDropCompleted(success);

                    event.consume();
                }
            });
            this.addEventBinding(this.onDragDoneProperty(), event -> {
//                log.debug("setOnDragDone " + this.slot.getIndex());
                /* the drag and drop gesture ended */
                /* if the data was successfully moved, clear it */
                if (event.getTransferMode() == TransferMode.MOVE && event.getDragboard() != null) {
                    final Object content = event.getDragboard().getContent(customFormat);
                    if (!(content instanceof String)
                            && isSlotValid(this.slot, ((DragboardContent) event.getDragboard().getContent(customFormat)).shipModule())) {
                        this.slot.setShipModule(((DragboardContent) content).shipModule());
                        this.slot.setOldShipModule(((DragboardContent) content).oldShipModule());
                        if (((DragboardContent) content).shipModule().getModuleSize().intValue() > this.slot.getSlotSize()) {
                            this.slot.getShipModule().findLowerSize(this.slot.getSlotSize()).ifPresent(this::replaceModule);
                        }
                        notifyChanged();
                    } else {
                        this.slot.setShipModule(null);
                        this.slot.setOldShipModule(null);
                    }
                }
                this.refresh();
                event.consume();
                notifyChanged();
            });
        }
    }

    private void dragOver(DragEvent event) {
        if (event.getGestureSource() == this) {
            layer1.pseudoClassStateChanged(PseudoClass.getPseudoClass("good"), true);
        } else {
            final Slot sourceSlot = ((SlotBox) event.getGestureSource()).slot;
            if (event.getDragboard() != null
                    && event.getDragboard().hasContent(customFormat)
                    && isSlotValid(this.slot, ((DragboardContent) event.getDragboard().getContent(customFormat)).shipModule())
                    && (event.getTransferMode() == TransferMode.COPY || (!this.slot.isOccupied() || isSlotValid(sourceSlot, this.slot.getShipModule())))
                    && moduleCouldFit(this.slot, ((DragboardContent) event.getDragboard().getContent(customFormat)).shipModule())
                    && (event.getTransferMode() == TransferMode.COPY || moduleCouldFit(sourceSlot, this.slot.getShipModule()))
            ) {
                this.moduleName.addBinding(this.moduleName.textProperty(), LocaleService.getStringBinding(((DragboardContent) event.getDragboard().getContent(customFormat)).shipModule().getName().getLocalizationKey())/*.concat(" " + ((ShipModule) event.getDragboard().getContent(customFormat)).getModuleSize() + ((ShipModule) event.getDragboard().getContent(customFormat)).getModuleClass())*/);
                layer1.pseudoClassStateChanged(PseudoClass.getPseudoClass("good"), true);
                showContents(sourceSlot.getShipModule(), sourceSlot.getOldShipModule());
            } else {
                layer1.pseudoClassStateChanged(PseudoClass.getPseudoClass("bad"), true);
            }
        }
    }

    private boolean isSlotValid(final Slot slot, final ShipModule shipModule) {
        return slot.getSlotType().getModuleClasses().stream().anyMatch(moduleClass -> moduleClass.isAssignableFrom(shipModule.getClass()));
    }

    private boolean moduleCouldFit(Slot slot, ShipModule module) {
        if (module == null) {
            return true;
        }
        if (slot.getSlotSize() < module.getModuleSize().intValue()) {
            return module.findLowerSize(slot.getSlotSize()).isPresent();
        }
        return true;
    }

    private static boolean isCurrentShip() {
        return APPLICATION_STATE.getPreferredCommander()
                .flatMap(commander -> ShipService.getShipConfigurations(commander).getSelectedShipConfiguration())
                .map(shipConfiguration -> ShipConfiguration.CURRENT == shipConfiguration)
                .orElse(Boolean.FALSE);
    }

    public void replaceModule(ShipModule shipModule) {
        final ShipModule clone = shipModule.Clone();
        if (!clone.isPreEngineered()) {//already pre-applied
            clone.getModifications().addAll(this.getSlot().getShipModule().getModifications());
        }
        clone.getExperimentalEffects().addAll(this.getSlot().getShipModule().getExperimentalEffects());
        if (this.getSlot().getOldShipModule() != null && isSimilar(clone, this.getSlot().getOldShipModule())) {
            clone.setBuyPrice(this.getSlot().getOldShipModule().getBuyPrice());
        }
        this.getSlot().setShipModule(clone);
        notifyChanged();
        this.refresh();
    }

    private boolean isSimilar(ShipModule shipModule, ShipModule oldModule) {
        return (shipModule == null && oldModule == null) || (shipModule != null && shipModule.isSame(oldModule));

    }

    private void updateBlueprints(ShipModule shipModule, ShipModule oldShipModule) {
        this.blueprints.update(shipModule, oldShipModule);
        final boolean isSimilar = similarModules(shipModule, oldShipModule);
        this.changedIcon.setVisible(!isSimilar);
    }

    private static boolean similarModules(ShipModule shipModule, ShipModule oldShipModule) {
        return (shipModule == null && oldShipModule == null) || (shipModule != null && shipModule.isSame(oldShipModule));//shipModule != null && oldShipModule != null && shipModule.getId().equals(oldShipModule.getId()) && matchingExperimentalEffects(shipModule, oldShipModule) && matchingBlueprints(shipModule, oldShipModule);
    }

    private void updateMounting(ShipModule shipModule) {
        mountingControl.updateMounting(shipModule);
    }

    private void updateHardpointGroup() {
        hardpointGroupLabel.addBinding(hardpointGroupLabel.textProperty(), LocaleService.getStringBinding(this.slot.getHardpointGroup().getLocalizationKey()));
    }

    private void updateClass(ShipModule shipModule) {
        classControl.updateClass(shipModule);
    }

    private void updateSize(ShipModule shipModule) {
        if (shipModule != null) {

            sizeControl.updateSize(shipModule);
            final int min = Math.min(
                    shipModule.findHighestSize(this.slot.getSlotSize()).map(highestModule -> highestModule.getModuleSize().intValue()).orElse(0),
                    shipModule.getModuleSize().intValue()
            );

            layer2.setVisible(min != shipModule.getModuleSize().intValue());
        }
    }

    private void updatePower(ShipModule shipModule) {
        powerControl.updatePower(shipModule);
    }

    private void updateSlotIcons(final ShipModule shipModule) {
        this.iconBox.getNodes().clear();
        if (shipModule != null) {
            addIcon((shipModule instanceof HardpointModule hp && Mounting.GIMBALLED.equals(hp.getMounting())) || (shipModule instanceof UtilityModule um && Mounting.GIMBALLED.equals(um.getMounting())), "/images/ships/icons/gimballed.png", "ship.module.icon.tooltip.gimballed", "module-icon");
            addIcon((shipModule instanceof HardpointModule hp && Mounting.TURRETED.equals(hp.getMounting())) || (shipModule instanceof UtilityModule um && Mounting.TURRETED.equals(um.getMounting())), "/images/ships/icons/turreted.png", "ship.module.icon.tooltip.turreted", "module-icon");
            addIcon((shipModule instanceof HardpointModule hp && Mounting.FIXED.equals(hp.getMounting())) || (shipModule instanceof UtilityModule um && Mounting.FIXED.equals(um.getMounting())), "/images/ships/icons/fixed.png", "ship.module.icon.tooltip.fixed", "module-icon");

            addIcon(shipModule.isPreEngineered(), "/images/ships/icons/preengineered.png", "ship.module.icon.tooltip.pre.engineered", "module-icon");
            addIcon(shipModule.isStoreExclusive(), "/images/ships/icons/arx.png", "ship.module.icon.tooltip.arx", "module-icon");
            addIcon(shipModule.isAdvanced(), "/images/ships/icons/advanced2.png", "ship.module.icon.tooltip.advanced", "module-icon-wide");
            addIcon(shipModule.isEnhanced(), "/images/ships/icons/enhanced2.png", "ship.module.icon.tooltip.enhanced", "module-icon-wide");
            addIcon(shipModule.isSeeker(), "/images/ships/icons/seeker2.png", "ship.module.icon.tooltip.seeker", "module-icon-wide");
            addIcon(shipModule.isDumbfire(), "/images/ships/icons/dumb2.png", "ship.module.icon.tooltip.dumbfire", "module-icon-wide");
            addIcon(shipModule.isLegacy(), "/images/ships/icons/legacy.png", "ship.module.icon.tooltip.legacy", "module-icon");
            addIcon(Origin.POWERPLAY.equals(shipModule.getOrigin()), "/images/ships/icons/powerplay.png", "ship.module.icon.tooltip.powerplay", "module-icon");
            addIcon(Origin.GUARDIAN.equals(shipModule.getOrigin()), "/images/ships/icons/guardian.png", "ship.module.icon.tooltip.guardian", "module-icon");
            addIcon(shipModule.isMultiCrew(), "/images/ships/icons/multicrew.png", "ship.module.icon.tooltip.multicrew", "module-icon");
            addIcon(shipModule.isCGExclusive(), "/images/ships/icons/cg.png", "ship.module.icon.tooltip.community.goal.module", "module-icon");
            this.iconBox.getNodes().add(new GrowingRegion());
            if (!shipModule.getModifications().isEmpty()) {
                if (!shipModule.isLegacy()) {
                    this.iconBox.getNodes().add(
                            LabelBuilder.builder()
                                    .withStyleClass("module-percent-modified")
                                    .withNonLocalizedText(shipModule.getModifications().getFirst().getModificationCompleteness().map(completeness -> Formatters.NUMBER_FORMAT_2.format(completeness.multiply(BigDecimal.valueOf(100))) + "%").orElse(""))
                                    .build()
                    );
                }
                this.iconBox.getNodes().addAll(
                        createIconWithTooltip("/images/ships/icons/engineered.png", "ship.module.icon.tooltip.engineered", "module-icon"),
                        LabelBuilder.builder()
                                .withStyleClass("module-grade")
                                .withNonLocalizedText(String.valueOf(shipModule.getModifications().getFirst().getGrade().getGrade()))
                                .build()
                );

            }
        }
    }

    private void addIcon(boolean condition, String imageResource, String tooltipKey, String... styleClasses) {
        if (condition) {
            this.iconBox.getNodes().add(createIconWithTooltip(imageResource, tooltipKey, styleClasses));
        }
    }

    private static DestroyableResizableImageView createIconWithTooltip(String imageResource, String tooltipKey, String... styleClasses) {
        final DestroyableResizableImageView icon = createIconWithoutTooltip(imageResource, styleClasses);
        final DestroyableTooltip tooltip = TooltipBuilder.builder()
                .withShowDelay(Duration.seconds(0.1))
                .withText(tooltipKey)
                .build();
        tooltip.install(icon);
        tooltip.setHideDelay(Duration.seconds(0));
        return icon;
    }


    private static DestroyableResizableImageView createIconWithoutTooltip(String imageResource, String... styleClasses) {
        return ResizableImageViewBuilder.builder()
                .withStyleClasses(styleClasses)
                .withImage(imageResource)
                .build();
    }

    private void hideContents() {

        setVisibility(this.sizeControl, false);
        setVisibility(this.classControl, false);
        setVisibility(this.mountingControl, false);
        setVisibility(this.powerControl, false);
        setVisibility(this.moduleData, false);
        setVisibility(this.emptyLabel, true);
        layer2.setVisible(false);
    }

    private void showContents(ShipModule shipModule, ShipModule oldShipModule) {
        if (shipModule != null) {
            setVisibility(this.emptyLabel, false);
            setVisibility(sizeControl, true);
            if (shipModule instanceof HardpointModule) {
                setVisibility(this.mountingControl, true);
                setVisibility(this.classControl, false);
            } else {
                setVisibility(this.mountingControl, false);
                setVisibility(this.classControl, true);
            }
            setVisibility(this.moduleData, true);
            setVisibility(this.powerControl, true);
        }
        if (shipModule instanceof HardpointModule) {
            updateMounting(shipModule);
            updateHardpointGroup();
        } else {
            updateClass(shipModule);
        }
        updateSize(shipModule);
        updatePower(shipModule);
        updateSlotIcons(shipModule);
        updateBlueprints(shipModule, oldShipModule);
    }

    private void setVisibility(Node node, boolean visible) {
        node.setVisible(visible);
        node.setManaged(visible);
    }

    private ModuleSelectPopover getModuleSelectPopover() {
        return new ModuleSelectPopover(this);
    }


    public void refresh() {
        final Optional<ShipModule> shipModule = Optional.ofNullable(this.slot.getShipModule());
        this.moduleName.addBinding(this.moduleName.textProperty(), shipModule.map(mod -> LocaleService.getStringBinding(mod.getName().getLocalizationKey())).orElse(LocaleService.getStringBinding("blank")));
        shipModule.ifPresent(
                module -> {
                    final boolean sameSize = module.isSameSize(this.slot.getOldShipModule());
                    final boolean sameClass = module.isSameClass(this.slot.getOldShipModule());
                    final boolean sameMounting = !(module instanceof ExternalModule externalModule) || externalModule.isSameMounting(this.slot.getOldShipModule());
                    this.moduleName.pseudoClassStateChanged(PseudoClass.getPseudoClass("changed"), !sameSize || !sameClass || !sameMounting);
                });

        if (shipModule.isEmpty()) {
            hideContents();
        } else {
            showContents(shipModule.get(), this.slot.getOldShipModule());
        }
        final boolean isSame = similarModules(this.slot.getShipModule(), this.slot.getOldShipModule());
        this.changedIcon.setVisible(!isSame);
    }

    public void close() {
        if (this.moduleSelectPopover != null) {
            this.moduleSelectPopover.close();
            this.moduleSelectPopover = null;
        }
    }

    void notifyChanged() {
        EventService.publish(new ShipBuilderEvent());
        EventService.publish(new SlotboxHoverEvent(this, this.slot.getShipModule(), true));
    }

    public boolean isMenuOpen() {
        return this.moduleSelectPopover != null && this.moduleSelectPopover.isShowing();
    }

    public ModuleSelectPopover menu() {
        return this.moduleSelectPopover;
    }
}
