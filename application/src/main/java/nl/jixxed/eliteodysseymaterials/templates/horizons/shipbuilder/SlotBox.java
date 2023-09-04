package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.beans.binding.StringBinding;
import javafx.geometry.Bounds;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ToggleButtonBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.Slot;
import nl.jixxed.eliteodysseymaterials.domain.ships.SlotType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ShipBuilderEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import org.controlsfx.control.PopOver;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
class SlotBox extends HBox {
    @Getter
    private final Slot slot;
    @Getter
    private final HorizonsShipBuilderTab tab;
    private final DestroyableLabel label;
    private final DestroyableLabel module;
    private final DestroyableLabel size;
    private final VBox texts;
    private static final DataFormat customFormat =
            new DataFormat("ship.module");
    private PopOver popOver;
    private boolean isControlDown;
    private boolean dragging;

    SlotBox(final HorizonsShipBuilderTab tab, final Slot slot) {
        this.setFocusTraversable(true);
        this.getStyleClass().add("shipbuilder-slots-slotbox");
        this.texts = BoxBuilder.builder().withStyleClass("shipbuilder-slots-slotbox-texts").buildVBox();
        this.slot = slot;
        this.tab = tab;
        this.label = LabelBuilder.builder().withNonLocalizedText(slot.getSlotType().name() + slot.getIndex()).build();
        this.size = LabelBuilder.builder().withStyleClass("shipbuilder-slots-slotbox-size-label").withNonLocalizedText(slot.getSlotSizeName()).build();
        //testing
        this.module = LabelBuilder.builder().withText(LocaleService.getStringBinding(Optional.ofNullable(slot.getShipModule()).map(mod -> mod.getName().getLocalizationKey()).orElse("blank"))).build();
        this.texts.getChildren().addAll(this.label/*, this.module*/);
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
//        ShipModule.getModules(slot.getSlotType());

        this.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {

                this.popOver = getPopOver();

                final Bounds boundsInLocal = this.getBoundsInLocal();
                final Bounds bounds = this.localToScreen(boundsInLocal);
                this.popOver.show(this, bounds.getMinX(),
                        bounds.getMaxY());
            } else if (event.getButton().equals(MouseButton.SECONDARY)) {
                if (!slot.getSlotType().isCore()) {
                    this.slot.setShipModule(null);
                    notifyChanged();
                    this.refresh();
                }
            }
//            refreshStyle(false);
        });
        this.setOnDragDetected(event -> {
            if (this.popOver != null && this.popOver.isShowing()) {
                this.popOver.hide();
                this.popOver = null;
            }
            log.debug("setOnDragDetected " + this.slot.getIndex());
            final Dragboard db = this.startDragAndDrop(TransferMode.ANY);
            this.dragging = true;
            /* Put a string on a dragboard */
            if (this.slot.getShipModule() != null && !slot.getSlotType().isCore()) {
                final ClipboardContent content = new ClipboardContent();
                content.put(customFormat, this.slot.getShipModule());
                db.setContent(content);
                db.setDragView(this.snapshot(null, null), event.getX(), event.getY());
//                refreshStyle(true);
                event.consume();
            }
        });
        this.setOnDragOver(event -> {
            log.debug("setOnDragOver " + this.slot.getIndex() + " " + event.getTransferMode());

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
            //refreshStyle(false);
            if (((SlotBox) event.getGestureSource()) != null && ((SlotBox) event.getGestureSource()).getSlot().getShipModule() != null) {

                log.debug("source:" + ((SlotBox) event.getGestureSource()).getSlot().getShipModule().getInternalName());
            }
            if (((SlotBox) event.getGestureTarget()) != null && ((SlotBox) event.getGestureTarget()).getSlot().getShipModule() != null) {

                log.debug("target:" + ((SlotBox) event.getGestureTarget()).getSlot().getShipModule().getInternalName());
            }
            if (this.slot.getShipModule() != null) {

                log.debug("this.slot:" + this.slot.getShipModule().getInternalName());
            }
            if (event.getTransferMode() == TransferMode.COPY) {
//                ((SlotBox)event.getGestureSource()).module.setText(((SlotBox)event.getGestureSource()).slot.getShipModule().getName());
                ((SlotBox) event.getGestureSource()).module.textProperty().bind(LocaleService.getStringBinding(((SlotBox) event.getGestureSource()).slot.getShipModule().getName().getLocalizationKey()).concat(" " + ((SlotBox) event.getGestureSource()).slot.getShipModule().getModuleSize() + ((SlotBox) event.getGestureSource()).slot.getShipModule().getModuleClass()));
                ((SlotBox) event.getGestureSource()).showContents();
            } else if (event.getTransferMode() == TransferMode.MOVE && this.slot.getShipModule() != null) {// && (((SlotBox) event.getGestureSource()) == null || this.slot.getShipModule().getModuleClass().getClass().isAssignableFrom(((SlotBox) event.getGestureSource()).getSlot().getSlotType().getModuleClass()))) {

                ((SlotBox) event.getGestureSource()).module.textProperty().bind(LocaleService.getStringBinding(this.slot.getShipModule().getName().getLocalizationKey()).concat(" " + this.slot.getShipModule().getModuleSize() + this.slot.getShipModule().getModuleClass()));
                ((SlotBox) event.getGestureSource()).showContents();
            } else if (event.getTransferMode() == TransferMode.MOVE && event.getGestureSource() != this) {// && (this.slot.getShipModule() == null || ( this.slot.getShipModule().getModuleClass().getClass().isAssignableFrom(((SlotBox) event.getGestureSource()).getSlot().getSlotType().getModuleClass())))) {
                ((SlotBox) event.getGestureSource()).hideContents();
            }
            event.consume();
        });
        this.setOnDragEntered(event -> {
            log.debug("setOnDragEntered " + this.slot.getIndex());
            /* the drag-and-drop gesture entered the target */
            /* show to the user that it is an actual gesture target */
            if (event.getGestureSource() != this &&
                    event.getDragboard().hasContent(customFormat) && this.slot.getSlotType().getModuleClass().isAssignableFrom(((ShipModule) event.getDragboard().getContent(customFormat)).getClass())) {
                this.module.textProperty().bind(LocaleService.getStringBinding(((ShipModule) event.getDragboard().getContent(customFormat)).getName().getLocalizationKey()).concat(" " + ((ShipModule) event.getDragboard().getContent(customFormat)).getModuleSize() + ((ShipModule) event.getDragboard().getContent(customFormat)).getModuleClass()));
                this.getStyleClass().add("shipbuilder-slots-slotbox-hover-good");
            } else {
                this.getStyleClass().add("shipbuilder-slots-slotbox-hover-bad");
            }
            showContents();
//            refreshStyle(false);
            event.consume();
        });
        this.setOnDragExited(event -> {
            log.debug("setOnDragExited " + this.slot.getIndex());
            this.getStyleClass().removeAll("shipbuilder-slots-slotbox-hover-bad", "shipbuilder-slots-slotbox-hover-good");
            if (slot.getSlotType().equals(SlotType.HARDPOINT)) {
                tab.toggleHardpointImage(false);
            }
            if (slot.getSlotType().equals(SlotType.UTILITY)) {
                tab.toggleUtilityImage(false);
            }
            /* mouse moved away, remove the graphical cues */
            this.refresh();
            if (this.slot.getShipModule() == null) {
                hideContents();
            }
//            refreshStyle(false);
            event.consume();
        });
        this.setOnDragDropped(event -> {
            log.debug("setOnDragDropped " + this.slot.getIndex());
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
                notifyChanged();
                this.refresh();
                success = true;
            }
//            refreshStyle(false);
            /* let the source know whether the string was successfully
             * transferred and used */
            event.setDropCompleted(success);

            event.consume();
        });
        this.setOnDragDone(event -> {
            log.debug("setOnDragDone " + this.slot.getIndex());
            /* the drag and drop gesture ended */
            /* if the data was successfully moved, clear it */
            if (event.getTransferMode() == TransferMode.MOVE) {
                final Object content = event.getDragboard().getContent(customFormat);
                if (!(content instanceof String) && this.slot.getSlotType().getModuleClass().isAssignableFrom(((ShipModule) event.getDragboard().getContent(customFormat)).getClass())) {
                    this.slot.setShipModule(((ShipModule) content));
                    notifyChanged();
                } else {
                    this.slot.setShipModule(null);
                }
            }
            this.refresh();
            this.dragging = false;
//            this.refreshStyle(false);
            event.consume();
            ApplicationState.getInstance().getShip().getOptionalSlots().forEach(slot1 -> System.out.println(slot1.getShipModule() != null ? slot1.getShipModule().getInternalName() : "EMPTY"));
            notifyChanged();
        });
        this.refresh();
    }

    private void hideContents() {
        this.label.getStyleClass().remove(0);
        this.label.getStyleClass().add("shipbuilder-slots-slotbox-label-empty");
        this.label.setText("EMPTY");
        this.texts.getChildren().remove(this.module);

    }

    private void showContents() {
        this.label.getStyleClass().remove(0);
        this.label.getStyleClass().add("shipbuilder-slots-slotbox-label-normal");
        if (!this.texts.getChildren().contains(this.module)) {
            this.texts.getChildren().add(this.module);
        }
        this.label.setText(this.slot.getSlotType().name() + this.slot.getIndex());
    }

    private PopOver getPopOver() {
        final VBox content = BoxBuilder.builder()/*.withNode(LabelBuilder.builder().withNonLocalizedText("test").build())*/.withStyleClass("shipbuilder-slots-slotbox-popover-vbox").buildVBox();
        //add engineering
        addEngineering(content);
        addExperimentalEffects(content);
        content.getChildren().addAll(
                ShipModule.getModules(this.slot.getSlotType()).stream()
                        .filter(module -> module.isAllowed(ApplicationState.getInstance().getShip().getShipType()))
                        .collect(Collectors.groupingBy(ShipModule::getClass))
                        .values().stream().map(list -> new SlotBoxEntry(tab, this, list)).toList()
        );
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
            final List<ToggleButton> toggleButtons = allowedBlueprints.stream().sorted(Comparator.comparing(horizonsBlueprintType -> LocaleService.getLocalizedStringForCurrentLocale(horizonsBlueprintType.getLocalizationKey()))).map(horizonsBlueprintType -> {
                        final int multiplier = (experimental ? shipModule.getExperimentalEffects().stream().filter(horizonsBlueprintType::equals).toList().size() : shipModule.getModifications().stream().filter(modification -> modification.getModification().equals(horizonsBlueprintType)).toList().size());
                        final StringBinding blueprintStringBinding;
                        if (multiplier > 1) {
                            blueprintStringBinding = LocaleService.getStringBinding(() -> LocaleService.getLocalizedStringForCurrentLocale(horizonsBlueprintType.getLocalizationKey()).concat(" x" + multiplier));
                        } else {
                            blueprintStringBinding = LocaleService.getStringBinding(horizonsBlueprintType.getLocalizationKey());
                        }
                        final ToggleButton button = ToggleButtonBuilder.builder().withStyleClass("toggle-button-blueprints").withText(blueprintStringBinding).withOnAction(event -> {
                            if (experimental) {
//                                getSlot().setExperimentalEffect(((ToggleButton) event.getTarget()).isSelected() ? horizonsBlueprintType : null);
                                if (((ToggleButton) event.getTarget()).isSelected()) {
                                    getSlot().getShipModule().applyExperimentalEffect(horizonsBlueprintType);
                                } else {
                                    getSlot().getShipModule().removeExperimentalEffect(horizonsBlueprintType);
                                }
                            } else {
//                                getSlot().setEngineering(((ToggleButton) event.getTarget()).isSelected() ? horizonsBlueprintType : null);
                                if (((ToggleButton) event.getTarget()).isSelected()) {
                                    getSlot().getShipModule().applyModification(horizonsBlueprintType, HorizonsBlueprintGrade.GRADE_5, 1D);
                                } else {
                                    getSlot().getShipModule().removeExperimentalEffect(horizonsBlueprintType);
                                }
                                //TODO configurable completeness and grades
                            }
                            notifyChanged();
                            refresh();
                        }).build();
                        button.selectedProperty().set((experimental ? shipModule.getExperimentalEffects().contains(horizonsBlueprintType) : shipModule.getModifications().stream().anyMatch(modification -> modification.getModification().equals(horizonsBlueprintType))));
                        button.selectedProperty().addListener((observable, oldValue, newValue) ->
                        {
                            if (Boolean.TRUE.equals(oldValue)) {
                                if (experimental) {
                                    getSlot().getShipModule().removeExperimentalEffect(horizonsBlueprintType);
//                                    getSlot().setExperimentalEffect(null);
                                } else {
                                    getSlot().getShipModule().removeModification(horizonsBlueprintType);
//                                    getSlot().setEngineering(null);
                                }
                                notifyChanged();
                                refresh();
                            }
                            if (Boolean.TRUE.equals(newValue)) {
                                if (experimental) {
//                                    getSlot().setExperimentalEffect(horizonsBlueprintType);
                                    getSlot().getShipModule().applyExperimentalEffect(horizonsBlueprintType);
                                } else {
//                                    getSlot().setEngineering(horizonsBlueprintType);
                                    getSlot().getShipModule().applyModification(horizonsBlueprintType, HorizonsBlueprintGrade.GRADE_5, 1D);
                                    //TODO configurable completeness and grades
                                }
                                notifyChanged();
                                refresh();
                            }
//                            else{
//                                if (experimental) {
//                                    slotBox.getSlot().getShipModule().removeExperimentalEffect(horizonsBlueprintType);
//                                } else {
//                                    slotBox.getSlot().getShipModule().removeModification(horizonsBlueprintType);
//                                }
//                                notifyChanged();
//                            }
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
            content.getChildren().add(vBox);
        }

    }

    public void refresh() {
        final Optional<ShipModule> shipModule = Optional.ofNullable(this.slot.getShipModule());
        this.module.textProperty().bind(shipModule.map(mod -> LocaleService.getStringBinding(mod.getName().getLocalizationKey()).concat(" " + this.slot.getShipModule().getModuleSize() + this.slot.getShipModule().getModuleClass())).orElse(LocaleService.getStringBinding("blank")));
//        this.module.setText(shipModule.map(ShipModule::getName).orElse(""));
        if (shipModule.isEmpty()) {
            this.label.getStyleClass().remove(0);
            this.label.getStyleClass().add("shipbuilder-slots-slotbox-label-empty");
            this.label.setText("EMPTY");
            this.texts.getChildren().remove(this.module);
        } else {
            this.label.getStyleClass().remove(0);
            this.label.getStyleClass().add("shipbuilder-slots-slotbox-label-normal");
            if (!this.texts.getChildren().contains(this.module)) {
                this.texts.getChildren().add(this.module);
            }
            this.label.setText(this.slot.getSlotType().name() + this.slot.getIndex());
        }
//        refreshStyle(false);
    }

    public void close() {
        if (this.popOver != null && this.popOver.isShowing()) {
            this.popOver.hide();
            this.popOver = null;
        }
    }

    //    private void refreshStyle(final boolean hover) {
//        final Optional<ShipModule> shipModule = Optional.ofNullable(this.slot.getShipModule());
//        if (this.dragging && !this.isControlDown) {
//            this.label.getStyleClass().remove(0);
//            this.label.getStyleClass().add("shipbuilder-slots-slotbox-label-empty");
//            this.label.setText("EMPTY");
//            this.texts.getChildren().remove(this.module);
//        }else if (this.dragging && this.isControlDown) {
//            this.label.getStyleClass().remove(0);
//            this.label.getStyleClass().add("shipbuilder-slots-slotbox-label-normal");
//            if (!this.texts.getChildren().contains(this.module)) {
//                this.texts.getChildren().add(this.module);
//            }
//            this.label.setText(this.slot.getSlotType().name() + this.slot.getIndex());
//        } else if (hover) {
//            this.label.getStyleClass().remove(0);
//            this.label.getStyleClass().add("shipbuilder-slots-slotbox-label-normal");
//            if (!this.texts.getChildren().contains(this.module)) {
//                this.texts.getChildren().add(this.module);
//            }
//            this.label.setText(this.slot.getSlotType().name() + this.slot.getIndex());
//        } else if (shipModule.isEmpty()) {
//            this.label.getStyleClass().remove(0);
//            this.label.getStyleClass().add("shipbuilder-slots-slotbox-label-empty");
//            this.label.setText("EMPTY");
//            this.texts.getChildren().remove(this.module);
//        } else {
//            this.label.getStyleClass().remove(0);
//            this.label.getStyleClass().add("shipbuilder-slots-slotbox-label-normal");
//            if (!this.texts.getChildren().contains(this.module)) {
//                this.texts.getChildren().add(this.module);
//            }
//            this.label.setText(this.slot.getSlotType().name() + this.slot.getIndex());
//        }
//    }
    private void notifyChanged() {
        EventService.publish(new ShipBuilderEvent());
    }
}
