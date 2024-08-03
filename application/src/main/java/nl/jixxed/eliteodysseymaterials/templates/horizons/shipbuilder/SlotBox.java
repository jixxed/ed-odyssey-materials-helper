package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.application.Platform;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Bounds;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ModuleHighlightEvent;
import nl.jixxed.eliteodysseymaterials.service.event.ShipBuilderEvent;
import nl.jixxed.eliteodysseymaterials.service.ships.ShipService;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;
import org.controlsfx.control.PopOver;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
class SlotBox extends StackPane {

    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance();

    static {
        NUMBER_FORMAT.setMaximumFractionDigits(2);
    }

    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    @Getter
    private final Slot slot;
    @Getter
    private final ModulesLayer modulesLayer;
    private final DestroyableLabel emptyLabel;
    private final DestroyableLabel module;
    private final DestroyableLabel blueprints;
    private final DestroyableLabel size;
    private final VBox texts;
    private final VBox sizeBox;
    private final VBox classBox;
    private final VBox mountingBox;
    private final HBox iconBox;
    private final HBox layer1;
    private final StackPane layer2;
    private final DestroyableLabel hardpointGroupLabel;
    private DestroyableResizableImageView power;
    private final VBox powerBox;
    IntegerProperty maxGrade = new SimpleIntegerProperty(0);
    private static final DataFormat customFormat =
            new DataFormat("ship.module");
    private Button mountingUp;
    private Button mountingDown;
    private Button classUp;
    private Button classDown;
    private Button sizeUp;
    private Button sizeDown;
    private DestroyableLabel classLabel;
    private DestroyableLabel mountingLabel;
    private DestroyableLabel sizeLabel;
    private PopOver popOver;
    private boolean isControlDown;
    private boolean dragging;
    private Button powerButton;
    private Button powerUp;
    private Button powerDown;
    private Slider progressSlider;
    private List<ToggleButton> toggleButtonsRank;
    private ToggleGroup toggleGroupRank;

    SlotBox(final ModulesLayer modulesLayer, final Slot slot) {
        this.setFocusTraversable(true);
        layer1 = BoxBuilder.builder().buildHBox();
        layer2 = new StackPane(LabelBuilder.builder().withNonLocalizedText("MODULE RESIZED").withStyleClass("shipbuilder-slots-slotbox-module-resize-warning").build());
        layer2.getStyleClass().add("shipbuilder-slots-slotbox-layer2");
        layer2.setVisible(false);
        this.getChildren().addAll(layer1, layer2);
        layer1.getStyleClass().add("shipbuilder-slots-slotbox");
        if (SlotType.MILITARY.equals(slot.getSlotType())) {
            layer1.getStyleClass().add("shipbuilder-slots-slotbox-military");
        }
        this.sizeBox = BoxBuilder.builder().withStyleClass("shipbuilder-slots-slotbox-size").buildVBox();
        this.classBox = BoxBuilder.builder().withStyleClass("shipbuilder-slots-slotbox-class").buildVBox();
        this.mountingBox = BoxBuilder.builder().withStyleClass("shipbuilder-slots-slotbox-mounting").buildVBox();
        this.powerBox = BoxBuilder.builder().withStyleClass("shipbuilder-slots-slotbox-power").buildVBox();
        this.iconBox = BoxBuilder.builder().withStyleClass("shipbuilder-slots-slotbox-icons").buildHBox();
        this.texts = BoxBuilder.builder().withStyleClass("shipbuilder-slots-slotbox-texts").buildVBox();
        this.slot = slot;
        if(SlotType.HARDPOINT.equals(slot.getSlotType())){
            this.slot.setHardpointGroup(HardpointGroup.A);
        }
        this.modulesLayer = modulesLayer;
        sizeBox();
        classBox();
        mountingBox();
        powerBox();
        this.emptyLabel = LabelBuilder.builder().withStyleClass("shipbuilder-slots-slotbox-label-empty").withText(LocaleService.getStringBinding("ship.module.slot.empty")).build();
        this.size = LabelBuilder.builder().withStyleClass((SlotType.HARDPOINT.equals(slot.getSlotType()))  ? "shipbuilder-slots-slotbox-size-hardpoint-label" : "shipbuilder-slots-slotbox-size-label").withNonLocalizedText(slot.getSlotSizeName()).build();


        //testing
        this.module = LabelBuilder.builder().withStyleClass("shipbuilder-slots-slotbox-module-label")/*.withText(LocaleService.getStringBinding(Optional.ofNullable(slot.getShipModule()).map(mod -> mod.getName().getLocalizationKey()).orElse("blank")))*/.build();
        this.blueprints = LabelBuilder.builder().withStyleClass("shipbuilder-slots-slotbox-blueprints-label").withText(
                LocaleService.getStringBinding(() -> Optional.ofNullable(slot.getShipModule()).map(mod -> {
                    final String mods = mod.getModifications().stream()
                            .map(modification -> LocaleService.getLocalizedStringForCurrentLocale(modification.getModification().getLocalizationKey()))
                            .collect(Collectors.joining(", "));
                    final String effects = mod.getExperimentalEffects().stream()
                            .map(effect -> LocaleService.getLocalizedStringForCurrentLocale(effect.getLocalizationKey()))
                            .collect(Collectors.joining(", "));
                    return mods + ((mods.isEmpty() || effects.isEmpty()) ? "" : ", " + effects);
                }).orElse(""))
        ).build();
        updateBlueprints(slot.getShipModule(), slot.getOldShipModule());

        this.texts.getChildren().addAll(this.iconBox, this.module, this.blueprints);

        if (SlotType.HARDPOINT.equals(slot.getSlotType())) {
            hardpointGroupLabel = LabelBuilder.builder().withStyleClass("shipbuilder-slots-slotbox-hardpoint-group-label").withText(LocaleService.getStringBinding(this.slot.getHardpointGroup().getLocalizationKey())).build();
            final VBox vBox = BoxBuilder.builder().withNodes(hardpointGroupLabel, this.size).buildVBox();
            layer1.getChildren().add(vBox);
        }else{
            layer1.getChildren().add(this.size);
            hardpointGroupLabel = LabelBuilder.builder().build();
        }
        layer1.getChildren().addAll(new Separator(Orientation.VERTICAL), sizeBox, (this.getSlot().getSlotType().equals(SlotType.HARDPOINT)) ? mountingBox : classBox, this.texts, powerBox);

        maxGrade.set(Optional.ofNullable(slot.getShipModule())
                .map(shipModule -> shipModule.getModifications().stream()
                        .findFirst()
                        .map(modification -> HorizonsBlueprintConstants.getBlueprintGrades(shipModule.getName().getPrimary(), modification.getModification()).stream().max(Comparator.comparing(HorizonsBlueprintGrade::getGrade)).map(HorizonsBlueprintGrade::getGrade).orElse(0))
                        .orElse(0))
                .orElse(0));
        mouseBehaviour(modulesLayer, slot);
        this.refresh();
    }

    private void mouseBehaviour(ModulesLayer modulesLayer, Slot slot) {
        if (slot.getSlotType().equals(SlotType.HARDPOINT)) {
            this.onMouseEnteredProperty().set(event -> {
                if (!event.isAltDown()) {
                    EventService.publish(new ModuleHighlightEvent(this.slot.getShipModule()));
                    modulesLayer.toggleHardpointImage(true);
                    modulesLayer.drawHardpointLine(slot.getIndex());
                }
            });
            this.onMouseExitedProperty().set(event -> {
                if (!event.isAltDown()) {
                    modulesLayer.toggleHardpointImage(false);
                }
            });
        } else if (slot.getSlotType().equals(SlotType.UTILITY)) {
            this.onMouseEnteredProperty().set(event -> {
                if (!event.isAltDown()) {
                    EventService.publish(new ModuleHighlightEvent(this.slot.getShipModule()));
                    modulesLayer.toggleUtilityImage(true);
                    modulesLayer.drawUtilityLine(slot.getIndex());
                }
            });
            this.onMouseExitedProperty().set(event -> {
                if (!event.isAltDown()) {
                    modulesLayer.toggleUtilityImage(false);
                }
            });
        } else {
            this.onMouseEnteredProperty().set(event -> {
                if (!event.isAltDown()) {
                    EventService.publish(new ModuleHighlightEvent(this.slot.getShipModule()));
                }
            });
        }
        if (!isCurrentShip()) {
            this.setOnMouseClicked(event -> {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    if(this.popOver != null && this.popOver.isShowing()){
                        close();
                    }else {
                        this.popOver = getPopOver();

                        final Bounds boundsInLocal = this.getBoundsInLocal();
                        final Bounds bounds = this.localToScreen(boundsInLocal);
                        this.popOver.show(this, bounds.getMinX(),
                                bounds.getMaxY());
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
            this.setOnDragDetected(event -> {
                close();
//                log.debug("setOnDragDetected " + this.slot.getIndex());
                final Dragboard db = this.startDragAndDrop(TransferMode.ANY);
                this.dragging = true;
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
            this.setOnDragOver(event -> {
//                log.debug("setOnDragOver " + this.slot.getIndex() + " " + event.getTransferMode());

                if (slot.getSlotType().equals(SlotType.HARDPOINT)) {
                    modulesLayer.toggleHardpointImage(true);
                    modulesLayer.drawHardpointLine(slot.getIndex());
                }
                if (slot.getSlotType().equals(SlotType.UTILITY)) {
                    modulesLayer.toggleUtilityImage(true);
                    modulesLayer.drawUtilityLine(slot.getIndex());
                }
                //drag from outside the application
                if (((SlotBox) event.getGestureSource()) != null) {
                    /* data is dragged over the target */
                    /* accept it only if it is not dragged from the same node
                     * and if it has a string data */
                    final Slot sourceSlot = ((SlotBox) event.getGestureSource()).slot;
                    if (event.getGestureSource() != this
                            && event.getDragboard().hasContent(customFormat)
                            && this.slot.getSlotType().getModuleClass().isAssignableFrom(((DragboardContent) event.getDragboard().getContent(customFormat)).shipModule().getClass())
                            && moduleCouldFit(this.slot, ((DragboardContent) event.getDragboard().getContent(customFormat)).shipModule())
                            && (event.getTransferMode() == TransferMode.COPY || moduleCouldFit(sourceSlot, this.slot.getShipModule()))

                    ) {
                        /* allow for both copying and moving, whatever user chooses */
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                    }
                    if (((SlotBox) event.getGestureSource()) != null && ((SlotBox) event.getGestureSource()).getSlot().getShipModule() != null) {

//                        log.debug("source:" + ((SlotBox) event.getGestureSource()).getSlot().getShipModule().getInternalName());
                    }
                    if (((SlotBox) event.getGestureTarget()) != null && ((SlotBox) event.getGestureTarget()).getSlot().getShipModule() != null) {

//                        log.debug("target:" + ((SlotBox) event.getGestureTarget()).getSlot().getShipModule().getInternalName());
                    }
                    if (this.slot.isOccupied()) {

//                        log.debug("this.slot:" + this.slot.getShipModule().getInternalName());
                    }
                    if (event.getTransferMode() == TransferMode.COPY) {
                        ((SlotBox) event.getGestureSource()).module.textProperty().bind(LocaleService.getStringBinding(sourceSlot.getShipModule().getName().getLocalizationKey()));
                        ((SlotBox) event.getGestureSource()).showContents(sourceSlot.getShipModule(), sourceSlot.getOldShipModule());
                    } else if (event.getTransferMode() == TransferMode.MOVE && this.slot.isOccupied()
                            && sourceSlot.getSlotType().getModuleClass().isAssignableFrom(this.slot.getShipModule().getClass())
                            && this.slot.getSlotType().getModuleClass().isAssignableFrom(((DragboardContent) event.getDragboard().getContent(customFormat)).shipModule().getClass())
                            && moduleCouldFit(this.slot, ((DragboardContent) event.getDragboard().getContent(customFormat)).shipModule())
                            && moduleCouldFit(sourceSlot, this.slot.getShipModule())
                    ) {

                        ((SlotBox) event.getGestureSource()).module.textProperty().bind(LocaleService.getStringBinding(this.slot.getShipModule().getName().getLocalizationKey()));
                        ((SlotBox) event.getGestureSource()).showContents(this.slot.getShipModule(), this.slot.getOldShipModule());
                    } else if (event.getTransferMode() == TransferMode.MOVE && event.getGestureSource() != this) {
                        ((SlotBox) event.getGestureSource()).hideContents();
                    }
                    if (!event.getTransferMode().equals(atomicBoolean.get())) {
//                        log.debug("updateTransferMode " + this.slot.getIndex());
                        atomicBoolean.set(event.getTransferMode());
                        extracted(event);
                    }

                }
                event.consume();
            });
            this.setOnDragEntered(event -> {
//                log.debug("setOnDragEntered " + this.slot.getIndex());
                /* the drag-and-drop gesture entered the target */
                /* show to the user that it is an actual gesture target */

                if (((SlotBox) event.getGestureSource()) != null) {
                    extracted(event);
                    this.requestFocus();
                    event.consume();
                }
            });
            this.setOnDragExited(event -> {
//                log.debug("setOnDragExited " + this.slot.getIndex());
                layer1.getStyleClass().removeAll("shipbuilder-slots-slotbox-hover-bad", "shipbuilder-slots-slotbox-hover-good");
                if (slot.getSlotType().equals(SlotType.HARDPOINT)) {
                    modulesLayer.toggleHardpointImage(false);
                }
                if (slot.getSlotType().equals(SlotType.UTILITY)) {
                    modulesLayer.toggleUtilityImage(false);
                }
                /* mouse moved away, remove the graphical cues */
                this.refresh();
                if (!this.slot.isOccupied()) {
                    hideContents();
                }
                event.consume();
            });
            this.setOnDragDropped(event -> {
//                log.debug("setOnDragDropped " + this.slot.getIndex());
                layer1.getStyleClass().removeAll("shipbuilder-slots-slotbox-hover-bad", "shipbuilder-slots-slotbox-hover-good");
                /* data dropped */
                /* if there is a string data on dragboard, read it and use it */
                if (((SlotBox) event.getGestureSource()) != null) {
                    final Dragboard db = event.getDragboard();
                    boolean success = false;
                    if (db.hasContent(customFormat)
                            && db.getContent(customFormat) != null
                            && moduleCouldFit(this.slot, ((DragboardContent) event.getDragboard().getContent(customFormat)).shipModule())
                            && (event.getTransferMode() == TransferMode.COPY || (!this.slot.isOccupied() || ((SlotBox) event.getGestureSource()).slot.getSlotType().getModuleClass().isAssignableFrom(this.slot.getShipModule().getClass())))
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
            this.setOnDragDone(event -> {
//                log.debug("setOnDragDone " + this.slot.getIndex());
                /* the drag and drop gesture ended */
                /* if the data was successfully moved, clear it */
                if (event.getTransferMode() == TransferMode.MOVE) {
                    final Object content = event.getDragboard().getContent(customFormat);
                    if (!(content instanceof String)
                            && this.slot.getSlotType().getModuleClass().isAssignableFrom(((DragboardContent) event.getDragboard().getContent(customFormat)).shipModule().getClass())) {
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
                this.dragging = false;
                event.consume();
                notifyChanged();
            });
        }
    }

    private void extracted(DragEvent event) {
        layer1.getStyleClass().removeAll("shipbuilder-slots-slotbox-hover-good", "shipbuilder-slots-slotbox-hover-bad");
        if (event.getGestureSource() == this) {
            layer1.getStyleClass().add("shipbuilder-slots-slotbox-hover-good");
        } else {
            final Slot sourceSlot = ((SlotBox) event.getGestureSource()).slot;
            if (/*event.getGestureSource() != this
                    &&*/ event.getDragboard().hasContent(customFormat)
                    && this.slot.getSlotType().getModuleClass().isAssignableFrom(((DragboardContent) event.getDragboard().getContent(customFormat)).shipModule().getClass())
                    && (event.getTransferMode() == TransferMode.COPY || (!this.slot.isOccupied() || sourceSlot.getSlotType().getModuleClass().isAssignableFrom(this.slot.getShipModule().getClass())))
                    && moduleCouldFit(this.slot, ((DragboardContent) event.getDragboard().getContent(customFormat)).shipModule())
                    && (event.getTransferMode() == TransferMode.COPY || moduleCouldFit(sourceSlot, this.slot.getShipModule()))
            ) {
                this.module.textProperty().bind(LocaleService.getStringBinding(((DragboardContent) event.getDragboard().getContent(customFormat)).shipModule().getName().getLocalizationKey())/*.concat(" " + ((ShipModule) event.getDragboard().getContent(customFormat)).getModuleSize() + ((ShipModule) event.getDragboard().getContent(customFormat)).getModuleClass())*/);
                layer1.getStyleClass().add("shipbuilder-slots-slotbox-hover-good");
                showContents(sourceSlot.getShipModule(), sourceSlot.getOldShipModule());
            } else {
                layer1.getStyleClass().add("shipbuilder-slots-slotbox-hover-bad");
            }
        }
    }

    private boolean moduleCouldFit(Slot slot, ShipModule module) {
        if (module == null) {
            return true;
        }
//        log.debug("moduleCouldFit: " + slot.getSlotSize() + "<" + module.getModuleSize().intValue());
        if (slot.getSlotSize() < module.getModuleSize().intValue()) {
            return module.findLowerSize(slot.getSlotSize()).isPresent();
        }
        return true;
    }

    private static Boolean isCurrentShip() {
        return APPLICATION_STATE.getPreferredCommander()
                .flatMap(commander -> ShipService.getShipConfigurations(commander).getSelectedShipConfiguration())
                .map(shipConfiguration -> ShipConfiguration.CURRENT == shipConfiguration)
                .orElse(Boolean.FALSE);
    }

    private void mountingBox() {
        mountingLabel = LabelBuilder.builder().withStyleClass("shipbuilder-slots-slotbox-size-text").build();
        if (!isCurrentShip()) {
            mountingUp = ButtonBuilder.builder().withStyleClass("shipbuilder-slots-slotbox-button").withOnAction(event -> {
                ((HardpointModule) this.getSlot().getShipModule()).findHigherMounting().ifPresent(shipModule -> {
                    replaceModule(shipModule);
                });
            }).withGraphic(createIconWithoutTooltip("/images/ships/icons/arrow_up.png", "shipbuilder-slots-slotbox-button-icon")).build();
            mountingDown = ButtonBuilder.builder().withStyleClass("shipbuilder-slots-slotbox-button").withOnAction(event -> {
                ((HardpointModule) this.getSlot().getShipModule()).findLowerMounting().ifPresent(shipModule -> {
                    replaceModule(shipModule);
                });
            }).withGraphic(createIconWithoutTooltip("/images/ships/icons/arrow_down.png", "shipbuilder-slots-slotbox-button-icon")).build();
            mountingUp.setFocusTraversable(false);
            mountingDown.setFocusTraversable(false);
            this.mountingBox.getChildren().addAll(mountingUp, mountingLabel, mountingDown);
        } else {
            final GrowingRegion growingRegion = new GrowingRegion();
            this.mountingBox.getChildren().addAll(growingRegion, mountingLabel, new GrowingRegion());
        }
    }

    private void powerBox() {
        this.power = createIconWithTooltip("/images/ships/icons/powered1.png", 1, "shipbuilder-slots-slotbox-button-icon");
        this.powerButton = ButtonBuilder.builder().withStyleClass("shipbuilder-slots-slotbox-button").withOnAction(event -> {
            this.getSlot().getShipModule().togglePower();
            notifyChanged();
            refresh();
        }).withGraphic(this.power).build();
        if (!isCurrentShip()) {
            powerUp = ButtonBuilder.builder().withStyleClass("shipbuilder-slots-slotbox-button").withOnAction(event -> {
                this.getSlot().getShipModule().increasePowerGroup();
                notifyChanged();
                refresh();
            }).withGraphic(createIconWithoutTooltip("/images/ships/icons/arrow_up.png", "shipbuilder-slots-slotbox-button-icon")).build();
            powerDown = ButtonBuilder.builder().withStyleClass("shipbuilder-slots-slotbox-button").withOnAction(event -> {
                this.getSlot().getShipModule().decreasePowerGroup();
                notifyChanged();
                refresh();
            }).withGraphic(createIconWithoutTooltip("/images/ships/icons/arrow_down.png", "shipbuilder-slots-slotbox-button-icon")).build();
            this.powerUp.setFocusTraversable(false);
            this.powerDown.setFocusTraversable(false);
            this.powerButton.setFocusTraversable(false);
            this.powerBox.getChildren().addAll(powerUp, powerButton, powerDown);
        } else {
            final GrowingRegion growingRegion = new GrowingRegion();
            this.powerBox.getChildren().addAll(growingRegion, this.power, new GrowingRegion());
        }
    }

    private void classBox() {
        classLabel = LabelBuilder.builder().withStyleClass("shipbuilder-slots-slotbox-size-text").build();
        if (!isCurrentShip()) {
            classUp = ButtonBuilder.builder().withStyleClass("shipbuilder-slots-slotbox-button").withOnAction(event -> {
                this.getSlot().getShipModule().findHigherClass().ifPresent(shipModule -> {
                    replaceModule(shipModule);
                });
            }).withGraphic(createIconWithoutTooltip("/images/ships/icons/arrow_up.png", "shipbuilder-slots-slotbox-button-icon")).build();
            classDown = ButtonBuilder.builder().withStyleClass("shipbuilder-slots-slotbox-button").withOnAction(event -> {
                this.getSlot().getShipModule().findLowerClass().ifPresent(shipModule -> {
                    replaceModule(shipModule);
                });
            }).withGraphic(createIconWithoutTooltip("/images/ships/icons/arrow_down.png", "shipbuilder-slots-slotbox-button-icon")).build();
            classUp.setFocusTraversable(false);
            classDown.setFocusTraversable(false);
            this.classBox.getChildren().addAll(classUp, classLabel, classDown);
        } else {
            final GrowingRegion growingRegion = new GrowingRegion();
            this.classBox.getChildren().addAll(growingRegion, classLabel, new GrowingRegion());
        }
    }

    private void sizeBox() {
        sizeLabel = LabelBuilder.builder().withStyleClass("shipbuilder-slots-slotbox-size-text").build();
        if (!isCurrentShip()) {
            sizeUp = ButtonBuilder.builder().withStyleClass("shipbuilder-slots-slotbox-button").withOnAction(event -> {
                this.getSlot().getShipModule().findHigherSize().ifPresent(shipModule -> {
                    replaceModule(shipModule);
                });
            }).withGraphic(createIconWithoutTooltip("/images/ships/icons/arrow_up.png", "shipbuilder-slots-slotbox-button-icon")).build();
            sizeDown = ButtonBuilder.builder().withStyleClass("shipbuilder-slots-slotbox-button").withOnAction(event -> {
                this.getSlot().getShipModule().findLowerSize().ifPresent(shipModule -> {
                    replaceModule(shipModule);
                });
            }).withGraphic(createIconWithoutTooltip("/images/ships/icons/arrow_down.png", "shipbuilder-slots-slotbox-button-icon")).build();
            sizeUp.setFocusTraversable(false);
            sizeDown.setFocusTraversable(false);
            this.sizeBox.getChildren().addAll(sizeUp, sizeLabel, sizeDown);
        } else {
            final GrowingRegion growingRegion = new GrowingRegion();
//            growingRegion.getStyleClass().add("shipbuilder-slots-slotbox-button");
            this.sizeBox.getChildren().addAll(growingRegion, sizeLabel, new GrowingRegion());

        }
    }

    private void replaceModule(ShipModule shipModule) {
//        log.debug("size " + shipModule.getModuleSize().toString());
        final ShipModule clone = shipModule.Clone();
        if (!clone.isPreEngineered()) {//already pre-applied
            clone.getModifications().addAll(this.getSlot().getShipModule().getModifications());
        }
        clone.getExperimentalEffects().addAll(this.getSlot().getShipModule().getExperimentalEffects());
        if(this.getSlot().getOldShipModule() != null && isSimilar(clone, this.getSlot().getOldShipModule())){
            clone.setBuyPrice(this.getSlot().getOldShipModule().getBuyPrice());
        }
        this.getSlot().setShipModule(clone);
        notifyChanged();
        this.refresh();
    }

    private boolean isSimilar(ShipModule shipModule, ShipModule oldModule) {
        return shipModule.getName().getBlueprintGroup().equals(oldModule.getName().getBlueprintGroup()) &&
                Objects.equals(shipModule.getId(), oldModule.getId()) &&
                shipModule.getAllowedBlueprints().size() == oldModule.getAllowedBlueprints().size() &&
                shipModule.getAllowedExperimentalEffects().size() == oldModule.getAllowedExperimentalEffects().size() &&
                shipModule.getAllowedBlueprints().containsAll(oldModule.getAllowedBlueprints()) &&
                shipModule.getAllowedExperimentalEffects().containsAll(oldModule.getAllowedExperimentalEffects());
    }
    private void updateBlueprints(ShipModule shipModule, ShipModule oldShipModule) {

        this.blueprints.textProperty().bind(
                LocaleService.getStringBinding(() -> Optional.ofNullable(shipModule).map(mod -> {
                    final String mods = mod.getModifications().stream()
                            .map(modification -> LocaleService.getLocalizedStringForCurrentLocale(modification.getModification().getLocalizationKey(true)))
                            .collect(Collectors.joining(", "));
                    final String effects = mod.getExperimentalEffects().stream()
                            .map(effect -> LocaleService.getLocalizedStringForCurrentLocale(effect.getLocalizationKey(true)))
                            .collect(Collectors.joining(", "));
                    return mods + ((mods.isEmpty() || effects.isEmpty()) ? "" : ", " + effects);
                }).orElse(""))
        );
        if (similarModules(shipModule, oldShipModule)) {
            if (!this.blueprints.getStyleClass().contains("shipbuilder-slots-slotbox-blueprints-label-gold")) {
                this.blueprints.getStyleClass().add("shipbuilder-slots-slotbox-blueprints-label-gold");
            }
        } else {
            this.blueprints.getStyleClass().removeAll("shipbuilder-slots-slotbox-blueprints-label-gold");
        }
    }

    private static boolean similarModules(ShipModule shipModule, ShipModule oldShipModule) {
        return shipModule != null && oldShipModule != null && shipModule.getId().equals(oldShipModule.getId()) && matchingExperimentalEffects(shipModule, oldShipModule) && matchingBlueprints(shipModule, oldShipModule);
    }

    private static boolean matchingExperimentalEffects(ShipModule shipModule, ShipModule oldShipModule) {
        return new HashSet<>(shipModule.getExperimentalEffects()).containsAll(oldShipModule.getExperimentalEffects());
    }

    private static boolean matchingBlueprints(ShipModule shipModule, ShipModule oldShipModule) {
        boolean matching = shipModule.getModifications().size() == oldShipModule.getModifications().size();
        if (matching) {
            for (Modification modification : shipModule.getModifications()) {
                final Optional<Modification> oldModification = oldShipModule.getModifications().stream()
                        .filter(oldMod -> oldMod.equals(modification))
                        .findFirst();
                matching = oldModification
                        .map(oldModification1 -> modification.getModificationCompleteness().equals(oldModification1.getModificationCompleteness()) && modification.getGrade().equals(oldModification1.getGrade()))
                        .orElse(false);
                if (!matching) break;
            }
        }
        return matching;
    }

    private void updateMounting(ShipModule shipModule) {
        if (shipModule instanceof HardpointModule hardpointModule) {
            if (!isCurrentShip()) {
                this.mountingUp.setVisible(hardpointModule.findHigherMounting().isPresent());
                this.mountingDown.setVisible(hardpointModule.findLowerMounting().isPresent());
            }
            this.mountingLabel.setGraphic(createIconWithoutTooltip("/images/ships/icons/" + hardpointModule.getMounting().name().toLowerCase() + ".png", "shipbuilder-slots-slotbox-icon"));
        }
    }

    private void updateHardpointGroup() {
        hardpointGroupLabel.textProperty().bind(LocaleService.getStringBinding(this.slot.getHardpointGroup().getLocalizationKey()));
    }

    private void updateClass(ShipModule shipModule) {
        if (shipModule != null && !(shipModule instanceof HardpointModule)) {
            if (!isCurrentShip()) {
                this.classUp.setVisible(shipModule.findHigherClass().isPresent());
                this.classDown.setVisible(shipModule.findLowerClass().isPresent());
            }
            this.classLabel.setText(shipModule.getModuleClass().name());
        }
    }

    private void updateSize(ShipModule shipModule) {
        if (shipModule != null) {
            if (!isCurrentShip()) {
                this.sizeUp.setVisible(shipModule.getModuleSize().intValue() < slot.getSlotSize() && shipModule.findHigherSize().map(biggerModule -> biggerModule.getModuleSize().intValue() <= slot.getSlotSize()).orElse(false));
                this.sizeDown.setVisible(shipModule.findHighestSize(Math.min(this.slot.getSlotSize(), shipModule.getModuleSize().intValue())).flatMap(ShipModule::findLowerSize).isPresent());
            }
            final int min = Math.min(
                    shipModule.findHighestSize(this.slot.getSlotSize()).map(highestModule -> highestModule.getModuleSize().intValue()).orElse(0),
                    shipModule.getModuleSize().intValue()
            );
            this.sizeLabel.setText(
                    String.valueOf(
                            min
                    )
            );
            layer2.setVisible(min != shipModule.getModuleSize().intValue());
        }
    }

    private void updatePower(ShipModule shipModule) {
        if (shipModule != null) {
            this.power = createIconWithTooltip("/images/ships/icons/" + (shipModule.isPowered() ? "powered" : "unpowered") + shipModule.getPowerGroup() + ".png", shipModule.getPowerGroup(), "shipbuilder-slots-slotbox-button-icon");
            this.powerButton.setGraphic(this.power);
            final boolean hasPowerToggle = shipModule.hasPowerToggle();
            this.power.setVisible(hasPowerToggle);
            if (!isCurrentShip()) {
                final int powerGroup = shipModule.getPowerGroup();
                this.powerUp.setVisible(hasPowerToggle && powerGroup < 5);
                this.powerDown.setVisible(hasPowerToggle && powerGroup > 1);
                this.powerButton.setVisible(hasPowerToggle);
            } else {
                this.powerBox.getChildren().clear();
                this.powerBox.getChildren().addAll(new GrowingRegion(), this.power, new GrowingRegion());
            }
        }
    }

    private void updateSlotIcons(final ShipModule shipModule) {
        this.iconBox.getChildren().clear();
        if (shipModule != null) {
            if ((shipModule instanceof HardpointModule hp && Mounting.GIMBALLED.equals(hp.getMounting())) || (shipModule instanceof UtilityModule um && Mounting.GIMBALLED.equals(um.getMounting()))) {
                this.iconBox.getChildren().add(
                        createIconWithTooltip("/images/ships/icons/gimballed.png", "ship.module.icon.tooltip.gimballed", "shipbuilder-slots-slotbox-icon")
                );
            }
            if ((shipModule instanceof HardpointModule hp && Mounting.TURRETED.equals(hp.getMounting())) || (shipModule instanceof UtilityModule um && Mounting.TURRETED.equals(um.getMounting()))) {
                this.iconBox.getChildren().add(
                        createIconWithTooltip("/images/ships/icons/turreted.png", "ship.module.icon.tooltip.turreted", "shipbuilder-slots-slotbox-icon")
                );
            }
            if ((shipModule instanceof HardpointModule hp && Mounting.FIXED.equals(hp.getMounting())) || (shipModule instanceof UtilityModule um && Mounting.FIXED.equals(um.getMounting()))) {
                this.iconBox.getChildren().add(
                        createIconWithTooltip("/images/ships/icons/fixed.png", "ship.module.icon.tooltip.fixed", "shipbuilder-slots-slotbox-icon")
                );
            }
            if (shipModule.isPreEngineered()) {
                this.iconBox.getChildren().add(
                        createIconWithTooltip("/images/ships/icons/preengineered.png", "ship.module.icon.tooltip.pre.engineered", "shipbuilder-slots-slotbox-icon")
                );
            }
            if (shipModule.isStoreExclusive()) {
                this.iconBox.getChildren().add(
                        createIconWithTooltip("/images/ships/icons/arx.png", "ship.module.icon.tooltip.arx", "shipbuilder-slots-slotbox-icon")
                );
            }
            if (shipModule.isAdvanced()) {
                this.iconBox.getChildren().add(
                        createIconWithTooltip("/images/ships/icons/advanced2.png", "ship.module.icon.tooltip.advanced", "shipbuilder-slots-slotbox-icon", "shipbuilder-slots-slotbox-icon-wide")
                );
            }
            if (shipModule.isEnhanced()) {
                this.iconBox.getChildren().add(
                        createIconWithTooltip("/images/ships/icons/enhanced2.png", "ship.module.icon.tooltip.enhanced", "shipbuilder-slots-slotbox-icon", "shipbuilder-slots-slotbox-icon-wide")
                );
            }
            if (shipModule.isSeeker()) {
                this.iconBox.getChildren().add(
                        createIconWithTooltip("/images/ships/icons/seeker2.png", "ship.module.icon.tooltip.seeker", "shipbuilder-slots-slotbox-icon", "shipbuilder-slots-slotbox-icon-wide")
                );
            }
            if (shipModule.isDumbfire()) {
                this.iconBox.getChildren().add(
                        createIconWithTooltip("/images/ships/icons/dumb2.png", "ship.module.icon.tooltip.dumbfire", "shipbuilder-slots-slotbox-icon", "shipbuilder-slots-slotbox-icon-wide")
                );
            }
            if (shipModule.isLegacy()) {
                this.iconBox.getChildren().add(
                        createIconWithTooltip("/images/ships/icons/legacy.png", "ship.module.icon.tooltip.legacy", "shipbuilder-slots-slotbox-icon")
                );
            }
            if (Origin.POWERPLAY.equals(shipModule.getOrigin())) {

                this.iconBox.getChildren().add(
                        createIconWithTooltip("/images/ships/icons/powerplay.png", "ship.module.icon.tooltip.powerplay", "shipbuilder-slots-slotbox-icon")
                );
            }
            if (Origin.GUARDIAN.equals(shipModule.getOrigin())) {

                this.iconBox.getChildren().add(
                        createIconWithTooltip("/images/ships/icons/guardian.png", "ship.module.icon.tooltip.guardian", "shipbuilder-slots-slotbox-icon")
                );
            }
            if (shipModule.isMultiCrew()) {

                this.iconBox.getChildren().add(
                        createIconWithTooltip("/images/ships/icons/multicrew.png", "ship.module.icon.tooltip.multicrew", "shipbuilder-slots-slotbox-icon")
                );
            }
            if (shipModule.isCGExclusive()) {

                this.iconBox.getChildren().add(
                        createIconWithTooltip("/images/ships/icons/cg.png", "ship.module.icon.tooltip.community.goal.module", "shipbuilder-slots-slotbox-icon")
                );
            }
            this.iconBox.getChildren().add(new GrowingRegion());
            if (!shipModule.getModifications().isEmpty()) {
                if (!shipModule.isLegacy()) {
                    this.iconBox.getChildren().add(
                            LabelBuilder.builder().withStyleClass("module-percent-modified").withNonLocalizedText(shipModule.getModifications().getFirst().getModificationCompleteness().map(completeness -> NUMBER_FORMAT.format(completeness.multiply(BigDecimal.valueOf(100))) + "%").orElse("")).build()
                    );
                }
                this.iconBox.getChildren().addAll(
                        createIconWithTooltip("/images/ships/icons/engineered.png", "ship.module.icon.tooltip.engineered", "shipbuilder-slots-slotbox-icon"),
                        LabelBuilder.builder().withStyleClass("module-grade").withNonLocalizedText(String.valueOf(shipModule.getModifications().getFirst().getGrade().getGrade())).build()
                );

            }
        }
    }

    private static DestroyableResizableImageView createIconWithTooltip(String imageResource, String tooltipKey, String... styleClasses) {
        final DestroyableResizableImageView icon = createIconWithoutTooltip(imageResource, styleClasses);
        Tooltip.install(icon, TooltipBuilder.builder().withShowDelay(Duration.seconds(0.1)).withText(LocaleService.getStringBinding(tooltipKey)).build());
        return icon;
    }
    private static DestroyableResizableImageView createIconWithTooltip(String imageResource, Integer powerGroup, String... styleClasses) {
        final DestroyableResizableImageView icon = createIconWithoutTooltip(imageResource, styleClasses);
        Tooltip.install(icon, TooltipBuilder.builder().withShowDelay(Duration.seconds(0.1)).withText(LocaleService.getStringBinding("ship.stats.config.power.group", powerGroup)).build());
        return icon;
    }

    private static DestroyableResizableImageView createIconWithoutTooltip(String imageResource, String... styleClasses) {
        return ResizableImageViewBuilder.builder().withStyleClasses(styleClasses).withImage(imageResource).build();
    }

    private void hideContents() {
        layer1.getChildren().remove(this.sizeBox);
        layer1.getChildren().remove(this.classBox);
        layer1.getChildren().remove(this.mountingBox);
        layer1.getChildren().remove(this.texts);
        layer1.getChildren().remove(this.powerBox);
        if (!layer1.getChildren().contains(this.emptyLabel)) {
            layer1.getChildren().add(2, this.emptyLabel);
        }
        layer2.setVisible(false);
    }

    private void showContents(ShipModule shipModule, ShipModule oldShipModule) {
        if (shipModule != null) {
            layer1.getChildren().remove(this.emptyLabel);
            if (!layer1.getChildren().contains(this.sizeBox)) {
                layer1.getChildren().add(2, this.sizeBox);
                if (shipModule instanceof HardpointModule) {
                    layer1.getChildren().add(3, this.mountingBox);
                } else {
                    layer1.getChildren().add(3, this.classBox);
                }
                layer1.getChildren().add(4, this.texts);
                layer1.getChildren().add(5, this.powerBox);
            }
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


    private PopOver getPopOver() {
        final VBox content = BoxBuilder.builder()/*.withNode(LabelBuilder.builder().withNonLocalizedText("test").build())*/.withStyleClass("shipbuilder-slots-slotbox-popover-vbox").buildVBox();
        final List<SlotBoxEntry> entries = ShipModule.getModules(this.slot.getSlotType()).stream()
                .filter(module -> module.isAllowed(ApplicationState.getInstance().getShip().getShipType()))
                .collect(Collectors.groupingBy(ShipModule::getClass))
                .values().stream().map(list -> new SlotBoxEntry(modulesLayer, this, list))
                .sorted(Comparator.comparing(slotBoxEntry -> slotBoxEntry.name.getText()))
                .toList();
        if (entries.size() > 1) {
            addSearch(content, entries);
        }
        if (SlotType.HARDPOINT.equals(slot.getSlotType())) {
            addHardpointGroupButtons(content);
        }
        addButtons(content);
        //add engineering
        if (this.slot.isOccupied() && !this.slot.getShipModule().isLegacy()) {
            addEngineering(content);
            addExperimentalEffects(content);
        }
        content.getChildren().addAll(entries);
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

    private void addHardpointGroupButtons(VBox content) {
        Label label = LabelBuilder.builder().withStyleClass("ships-modules-buttons-label").withText(LocaleService.getStringBinding("ship.module.hardpoint.group.label")).build();
        final ToggleGroup toggleGroup = new ToggleGroup();
        ToggleButton[] buttons = Arrays.stream(HardpointGroup.values()).map(group -> {
             final ToggleButton  groupButton = ToggleButtonBuilder.builder().withStyleClass("toggle-button-blueprints").withNonLocalizedText(group.name()).withOnAction(event -> {
                this.slot.setHardpointGroup(group);
                refresh();
                notifyChanged();
                close();
            }).build();
            groupButton.setToggleGroup(toggleGroup);
            if(group.equals(this.slot.getHardpointGroup())){
                groupButton.setSelected(true);
            }
            groupButton.setFocusTraversable(false);
            return groupButton;
        }).toArray(ToggleButton[]::new);

        HBox box = BoxBuilder.builder().withNodes(buttons).buildHBox();
        content.getChildren().add(BoxBuilder.builder().withStyleClass("ships-modules-item").withNodes(label,box).buildVBox());
    }

    private void addButtons(VBox content) {
        Label label = LabelBuilder.builder().withStyleClass("ships-modules-buttons-label").withText(LocaleService.getStringBinding("ship.module.buttons.label")).build();
        final Button restore = ButtonBuilder.builder().withText(LocaleService.getStringBinding("ship.module.restore")).withOnAction(event -> {
            this.slot.setShipModule(this.slot.getOldShipModule().Clone());
            refresh();
            notifyChanged();
            close();
        }).build();
        final Button save = ButtonBuilder.builder().withText(LocaleService.getStringBinding("ship.module.save")).withOnAction(event -> {
            this.slot.setOldShipModule(this.slot.getShipModule().Clone());
            refresh();
            close();
        }).build();
        final Button clear = ButtonBuilder.builder().withText(LocaleService.getStringBinding("ship.module.clear")).withOnAction(event -> {
            this.slot.setOldShipModule(null);
            refresh();
            close();
        }).build();
        restore.setDisable(this.slot.getOldShipModule() == null || this.slot.getOldShipModule().getModuleSize().intValue() > this.slot.getSlotSize());
        save.setDisable(this.slot.getShipModule() == null);
        clear.setDisable(this.slot.getOldShipModule() == null);
        restore.setFocusTraversable(false);
        save.setFocusTraversable(false);
        clear.setFocusTraversable(false);
        HBox box = BoxBuilder.builder().withStyleClass("ships-modules-item").withNodes(label, new GrowingRegion(), restore, save, clear).buildHBox();
        content.getChildren().add(box);
    }

    private void addSearch(VBox content, final List<SlotBoxEntry> entries) {
        final TextField textField = TextFieldBuilder.builder().withPromptTextProperty(LocaleService.getStringBinding("search.text.placeholder")).build();

        textField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            content.getChildren().remove(1, content.getChildren().size());
            if (Objects.equals(newValue, "")) {
                //add engineering
                if (this.slot.isOccupied() && !this.slot.getShipModule().isLegacy()) {
                    addEngineering(content);
                    addExperimentalEffects(content);
                }
            }
            boolean isCG = "community goal".contains(newValue) || "cg".contains(newValue);
            boolean isPreEngineered = "pre engineered".contains(newValue) || "pre-engineered".contains(newValue);
            boolean isLegacy = "legacy".contains(newValue);
            boolean isPowerplay = "powerplay".contains(newValue);
            content.getChildren().addAll(entries.stream().filter(entry ->
                    entry.name.getText().toLowerCase().contains(newValue.toLowerCase())
                            || entry.options.stream().anyMatch(box -> box.getChildren().stream().map(button -> ((ShipModuleButton) button).getShipModule()).anyMatch(shipModule ->
                                    LocaleService.getLocalizedStringForCurrentLocale(shipModule.getName().getLocalizationKey()).toLowerCase().contains(newValue.toLowerCase())
                                            || (isCG && shipModule.isCGExclusive())
                                            || (isPreEngineered && shipModule.isPreEngineered())
                                            || (isLegacy && shipModule.isLegacy())
                                            || (isPowerplay && shipModule.getOrigin().equals(Origin.POWERPLAY))
                            )
                    )
            ).toList());
        });
        content.getChildren().add(textField);
    }

    private void addEngineering(final VBox content) {
        final ShipModule shipModule = getSlot().getShipModule();
        if (shipModule != null && !shipModule.getAllowedBlueprints().isEmpty()) {
            addBlueprintSection(content, "tabs.ships.blueprints", false);
        }

    }

    private void addExperimentalEffects(final VBox content) {
        final ShipModule shipModule = getSlot().getShipModule();

        if (shipModule != null && !shipModule.getAllowedExperimentalEffects().isEmpty()) {
            addBlueprintSection(content, "tabs.ships.experimental.effects", true);
        }

    }

    private void addBlueprintSection(final VBox content, final String sectionLabelKey, final boolean experimental) {
        final ShipModule shipModule = getSlot().getShipModule();
        final List<HorizonsBlueprintType> allowedBlueprints = experimental ? shipModule.getAllowedExperimentalEffects() : shipModule.getAllowedBlueprints();
        if (shipModule != null && !allowedBlueprints.isEmpty()) {
            final ToggleGroup toggleGroup = new ToggleGroup();
            final List<ToggleButton> toggleButtons = allowedBlueprints.stream()
                    .sorted(Comparator.comparing(horizonsBlueprintType -> LocaleService.getLocalizedStringForCurrentLocale(horizonsBlueprintType.getLocalizationKey(true))))
                    .map(horizonsBlueprintType -> {
                                final int multiplier = (experimental ? shipModule.getExperimentalEffects().stream().filter(horizonsBlueprintType::equals).toList().size() : shipModule.getModifications().stream().filter(modification -> modification.getModification().equals(horizonsBlueprintType)).toList().size());
                                final StringBinding blueprintStringBinding;
                                if (multiplier > 1) {
                                    blueprintStringBinding = LocaleService.getStringBinding(() -> LocaleService.getLocalizedStringForCurrentLocale(horizonsBlueprintType.getLocalizationKey(true)).concat(" x" + multiplier));
                                } else {
                                    blueprintStringBinding = LocaleService.getStringBinding(horizonsBlueprintType.getLocalizationKey(true));
                                }
                                final ToggleButton button = ToggleButtonBuilder.builder()
                                        .withStyleClass("toggle-button-blueprints")
                                        .withText(blueprintStringBinding)
                                        .withOnAction(event -> {
                                            if (experimental) {
                                                if (((ToggleButton) event.getTarget()).isSelected()) {
                                                    getSlot().getShipModule().applyExperimentalEffect(horizonsBlueprintType);
                                                } else {
                                                    getSlot().getShipModule().removeExperimentalEffect(horizonsBlueprintType);
                                                }
                                            } else {
                                                if (((ToggleButton) event.getTarget()).isSelected()) {
                                                    final HorizonsBlueprintGrade maxGradeForModification = HorizonsBlueprintConstants.getBlueprintGrades(shipModule.getName().getPrimary(), horizonsBlueprintType).stream().max(Comparator.comparing(HorizonsBlueprintGrade::getGrade)).orElseThrow(IllegalArgumentException::new);
                                                    final HorizonsBlueprintGrade grade = HorizonsBlueprintGrade.forDigit(((ToggleButton) toggleGroupRank.getSelectedToggle()).getText());
                                                    final BigDecimal completeness = BigDecimal.valueOf(progressSlider.getValue()).divide(BigDecimal.valueOf(100));
                                                    getSlot().getShipModule().applyModification(horizonsBlueprintType, grade.getGrade() <= maxGradeForModification.getGrade() ? grade : maxGradeForModification, completeness);

                                                    maxGrade.set(maxGradeForModification.getGrade());
                                                } else {
                                                    getSlot().getShipModule().removeModification(horizonsBlueprintType);
                                                    maxGrade.set(0);
                                                }
                                            }
                                            notifyChanged();
                                            refresh();
                                        }).build();
                                button.setFocusTraversable(false);
                                button.selectedProperty().set((experimental ? shipModule.getExperimentalEffects().contains(horizonsBlueprintType) : shipModule.getModifications().stream().anyMatch(modification -> modification.getModification().equals(horizonsBlueprintType))));
                                button.selectedProperty().addListener((observable, oldValue, newValue) ->
                                {
                                    if (Boolean.TRUE.equals(oldValue)) {
                                        if (experimental) {
                                            getSlot().getShipModule().removeExperimentalEffect(horizonsBlueprintType);
                                        } else {
                                            getSlot().getShipModule().removeModification(horizonsBlueprintType);
                                        }
                                        notifyChanged();
                                        refresh();
                                    }
                                    if (Boolean.TRUE.equals(newValue)) {
                                        if (experimental) {
                                            getSlot().getShipModule().applyExperimentalEffect(horizonsBlueprintType);
                                        } else {
                                            final HorizonsBlueprintGrade maxGradeForModification = HorizonsBlueprintConstants.getBlueprintGrades(shipModule.getName().getPrimary(), horizonsBlueprintType).stream().max(Comparator.comparing(HorizonsBlueprintGrade::getGrade)).orElseThrow(IllegalArgumentException::new);
                                            final HorizonsBlueprintGrade grade = HorizonsBlueprintGrade.forDigit(((ToggleButton) toggleGroupRank.getSelectedToggle()).getText());
                                            final BigDecimal completeness = BigDecimal.valueOf(progressSlider.getValue()).divide(BigDecimal.valueOf(100), 6, RoundingMode.HALF_EVEN);
                                            getSlot().getShipModule().applyModification(horizonsBlueprintType, grade.getGrade() <= maxGradeForModification.getGrade() ? grade : maxGradeForModification, completeness);
                                        }
                                        notifyChanged();
                                        refresh();
                                    }
                                });
                                button.setFocusTraversable(false);
                                if ((experimental && shipModule.getExperimentalEffects().size() <= 1) || (!experimental && shipModule.getModifications().size() <= 1)) {
                                    button.setToggleGroup(toggleGroup);
                                } else {
                                    button.setDisable(true);
                                }
                                return button;
                            }

                    ).toList();

            final VBox vBox = BoxBuilder.builder().withStyleClass("ships-modules-item").withNodes(BoxBuilder.builder().withNodes(new GrowingRegion(), LabelBuilder.builder().withStyleClass("ships-modules-title").withText(LocaleService.getStringBinding(sectionLabelKey)).build(), new GrowingRegion()).buildHBox()).buildVBox();
            vBox.getChildren().addAll(toggleButtons);
            addGradeSelection(experimental, shipModule, toggleGroup, vBox);
            content.getChildren().add(vBox);
        }

    }

    private void addGradeSelection(boolean experimental, ShipModule shipModule, ToggleGroup toggleGroup, VBox vBox) {
        if (!experimental) {
            final HBox progression = BoxBuilder.builder().buildHBox();
            toggleGroupRank = new ToggleGroup();
            toggleButtonsRank = new ArrayList<>();
            Arrays.stream(HorizonsBlueprintGrade.values()).filter(grade -> !HorizonsBlueprintGrade.NONE.equals(grade)).forEach(horizonsBlueprintGrade -> {
                final ToggleButton toggleButton = ToggleButtonBuilder.builder().withStyleClass("toggle-button-").withNonLocalizedText(String.valueOf(horizonsBlueprintGrade.getGrade())).withOnAction(event -> {
                    shipModule.getModifications().getFirst().setGrade(horizonsBlueprintGrade);
                    shipModule.getModifiers().clear();
                    notifyChanged();
                    refresh();
                }).build();
                toggleButton.setFocusTraversable(false);
                toggleButton.setToggleGroup(toggleGroupRank);
                toggleButton.disableProperty().bind(toggleGroup.selectedToggleProperty().isNull().or(maxGrade.lessThan(horizonsBlueprintGrade.getGrade())));
                toggleButton.disableProperty().addListener((observable, oldValue, newValue) -> {
                    if (Boolean.TRUE.equals(newValue) && toggleButton.isSelected()) {
                        toggleButton.setSelected(false);
                        toggleButtonsRank.stream().filter(toggle -> !toggle.isDisabled()).max(Comparator.comparing(ToggleButton::getText)).ifPresent(button -> button.setSelected(true));
                        notifyChanged();
                        refresh();
                    }
                });
                maxGrade.addListener((observable, oldValue, newValue) -> {
                    if (oldValue.equals(0) && newValue.equals(horizonsBlueprintGrade.getGrade())) {
                        toggleButton.setSelected(true);
                        notifyChanged();
                        refresh();
                    }
                });
                toggleButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
                    if (Boolean.TRUE.equals(!newValue) && toggleButton.getToggleGroup().getSelectedToggle() == null) {
                        toggleButton.setSelected(true);
                    }
                });
                toggleButton.setSelected(horizonsBlueprintGrade.equals(shipModule.getModifications().stream().findFirst().map(Modification::getGrade).orElse(HorizonsBlueprintGrade.GRADE_5)));
                toggleButtonsRank.add(toggleButton);

            });
            progression.getChildren().addAll(toggleButtonsRank);
            progressSlider = SliderBuilder.builder()
                    .withMin(0)
                    .withMax(100)
                    .withValue(shipModule.getModifications().stream()
                            .findFirst()
                            .flatMap(Modification::getModificationCompleteness)
                            .orElse(BigDecimal.ONE).multiply(BigDecimal.valueOf(100)).doubleValue())
                    .build();
            progressSlider.setFocusTraversable(false);
            Observable.create((ObservableEmitter<Number> emitter) -> progressSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                        shipModule.getModifications().getFirst().setModificationCompleteness(BigDecimal.valueOf(newValue.doubleValue()).divide(BigDecimal.valueOf(100D)));
                        shipModule.getModifiers().clear();
                        emitter.onNext(newValue);
                    }))
                    .debounce(500, TimeUnit.MILLISECONDS)
                    .observeOn(Schedulers.io())
                    .subscribe(newValue -> {
                        Platform.runLater(() -> {
                                    notifyChanged();
                                    refresh();
                                }
                        );
                    });
            progressSlider.setStyle("-fx-fit-to-width: true;-fx-max-width: 40em;-fx-pref-width: 40em;");
            progressSlider.disableProperty().bind(toggleGroup.selectedToggleProperty().isNull());
            final TextField textvalue = TextFieldBuilder.builder().withFocusTraversable(false).build();
            textvalue.textProperty().bind(progressSlider.valueProperty().map(number -> NUMBER_FORMAT.format(number) + "%"));
            textvalue.setDisable(true);
            textvalue.setStyle("-fx-max-width: 4.5em;-fx-min-width: 4.5em;");
            progression.getChildren().add(progressSlider);
            progression.getChildren().add(textvalue);

            vBox.getChildren().add(progression);
        }
    }

    public void refresh() {
        final Optional<ShipModule> shipModule = Optional.ofNullable(this.slot.getShipModule());
        this.module.textProperty().bind(shipModule.map(mod -> LocaleService.getStringBinding(mod.getName().getLocalizationKey())).orElse(LocaleService.getStringBinding("blank")));
        if (shipModule.isEmpty()) {
            hideContents();
        } else {
            showContents(shipModule.get(), this.slot.getOldShipModule());
        }
    }

    public void close() {
        if (this.popOver != null && this.popOver.isShowing()) {
            this.popOver.hide();
            this.popOver = null;
        }
    }

    private void notifyChanged() {
        EventService.publish(new ShipBuilderEvent());
        EventService.publish(new ModuleHighlightEvent(this.slot.getShipModule()));
    }
}
