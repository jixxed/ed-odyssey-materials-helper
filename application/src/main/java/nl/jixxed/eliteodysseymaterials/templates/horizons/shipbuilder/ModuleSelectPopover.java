package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.application.Platform;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ships.Modification;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.SlotType;
import nl.jixxed.eliteodysseymaterials.enums.HardpointGroup;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ShipBuilderEvent;
import nl.jixxed.eliteodysseymaterials.service.event.SlotboxEngineeringEvent;
import nl.jixxed.eliteodysseymaterials.service.event.SlotboxHoverEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
public class ModuleSelectPopover extends DestroyablePopOver implements DestroyableEventTemplate {
    private SlotBox slotBox;
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    IntegerProperty maxGrade = new SimpleIntegerProperty(0);

    private DestroyableSlider progressSlider;
    private List<DestroyableToggleButton> toggleButtonsRank;
    private ToggleGroup toggleGroupRank;
    private DestroyableButton restore;
    private DestroyableButton save;
    private DestroyableButton clear;
    private DestroyableVBox blueprintSection;
    private DestroyableVBox experintalEffectsSection;

    private Disposable subscribe;

    public ModuleSelectPopover(SlotBox slotBox) {
        this.slotBox = slotBox;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("shipbuilder-module-select-popover");
        final DestroyableVBox content = BoxBuilder.builder()
                .withStyleClass("module-select-content")
                .buildVBox();
        final List<SlotBoxEntry> entries = ShipModule.getModules(slotBox.getSlot().getSlotType()).stream()
                .filter(module -> module.isAllowed(ApplicationState.getInstance().getShip().getShipType()))
                .collect(Collectors.groupingBy(ShipModule::getClass))
                .values().stream()
                .map(list -> new SlotBoxEntry(slotBox, list))
                .sorted(Comparator.comparing(slotBoxEntry -> slotBoxEntry.name.getText()))
                .toList();
        if (entries.size() > 1) {
            addSearch(content, entries);
        }
        if (SlotType.HARDPOINT.equals(slotBox.getSlot().getSlotType())) {
            addHardpointGroupButtons(content);
        }
        addChangedButtons(content);
        //add engineering
        if (slotBox.getSlot().isOccupied() && !slotBox.getSlot().getShipModule().isLegacy()) {
            addEngineering(content);
            addExperimentalEffects(content);
        }
        content.getNodes().addAll(entries);
        final DestroyableScrollPane scrollPane = ScrollPaneBuilder.builder()
                .withContent(content)
                .withStyleClass("scroll-pane2")
                .build();
        setContentNode(scrollPane);
        setDetachable(false);
        setHeaderAlwaysVisible(false);
        setArrowIndent(0);
        setArrowSize(0);
        setCornerRadius(0);
        this.addChangeListener(this.showingProperty(), (_, _, newValue) -> {
            if (Boolean.FALSE.equals(newValue)) {
                Platform.runLater(() -> {
                    EventService.publish(new SlotboxHoverEvent(slotBox, slotBox.getSlot().getShipModule(), slotBox.isHover()));
                    close();
                });
                this.destroy();
            }
        });
    }

    @Override
    public void initEventHandling() {

    }


    private void addEngineering(final DestroyableVBox content) {
        final ShipModule shipModule = slotBox.getSlot().getShipModule();
        if (shipModule != null && !shipModule.getAllowedBlueprints().isEmpty()) {
            this.blueprintSection = addBlueprintSection(content, "tabs.ships.blueprints", false);
        }
    }

    private void addExperimentalEffects(final DestroyableVBox content) {
        final ShipModule shipModule = slotBox.getSlot().getShipModule();
        if (shipModule != null && !shipModule.getAllowedExperimentalEffects().isEmpty()) {
            this.experintalEffectsSection = addBlueprintSection(content, "tabs.ships.experimental.effects", true);
        }
    }


    private DestroyableVBox addBlueprintSection(final DestroyableVBox content, final String sectionLabelKey, final boolean experimental) {
        final ShipModule shipModule = slotBox.getSlot().getShipModule();
        final List<HorizonsBlueprintType> allowedBlueprints = experimental ? shipModule.getAllowedExperimentalEffects() : shipModule.getAllowedBlueprints();
        if (shipModule != null && !allowedBlueprints.isEmpty()) {
            final ToggleGroup toggleGroup = new ToggleGroup();
            final List<DestroyableToggleButton> toggleButtons = allowedBlueprints.stream()
                    .sorted(Comparator.comparing(horizonsBlueprintType -> LocaleService.getLocalizedStringForCurrentLocale(horizonsBlueprintType.getLocalizationKey(true))))
                    .map(horizonsBlueprintType -> {
                                final int multiplier = (experimental ? shipModule.getExperimentalEffects().stream().filter(horizonsBlueprintType::equals).toList().size() : shipModule.getModifications().stream().filter(modification -> modification.getModification().equals(horizonsBlueprintType)).toList().size());
                                final StringBinding blueprintStringBinding;
                                if (multiplier > 1) {
                                    blueprintStringBinding = LocaleService.getStringBinding(() -> LocaleService.getLocalizedStringForCurrentLocale(horizonsBlueprintType.getLocalizationKey(true)).concat(" x" + multiplier));
                                } else {
                                    blueprintStringBinding = LocaleService.getStringBinding(horizonsBlueprintType.getLocalizationKey(true));
                                }
                                final DestroyableToggleButton button = ToggleButtonBuilder.builder()
                                        .withStyleClass("blueprint-button")
                                        .withText(blueprintStringBinding)
                                        .withOnAction(event -> {
                                            if (experimental) {
                                                if (((ToggleButton) event.getTarget()).isSelected()) {
                                                    slotBox.getSlot().getShipModule().applyExperimentalEffect(horizonsBlueprintType);
                                                } else {
                                                    slotBox.getSlot().getShipModule().removeExperimentalEffect(horizonsBlueprintType);
                                                }
                                            } else {
                                                if (((ToggleButton) event.getTarget()).isSelected()) {
                                                    final HorizonsBlueprintGrade maxGradeForModification = HorizonsBlueprintConstants.getBlueprintGrades(shipModule.getName().getPrimary(), horizonsBlueprintType).stream().max(Comparator.comparing(HorizonsBlueprintGrade::getGrade)).orElseThrow(IllegalArgumentException::new);
                                                    final HorizonsBlueprintGrade grade = HorizonsBlueprintGrade.forDigit(((ToggleButton) toggleGroupRank.getSelectedToggle()).getText());
                                                    final BigDecimal completeness = BigDecimal.valueOf(progressSlider.getValue()).divide(BigDecimal.valueOf(100));
                                                    slotBox.getSlot().getShipModule().applyModification(horizonsBlueprintType, grade.getGrade() <= maxGradeForModification.getGrade() ? grade : maxGradeForModification, completeness);

                                                    maxGrade.set(maxGradeForModification.getGrade());
                                                } else {
                                                    slotBox.getSlot().getShipModule().removeModification(horizonsBlueprintType);
                                                    maxGrade.set(0);
                                                }
                                            }
                                            notifyChanged();
                                            slotBox.refresh();
                                            setChangedButtonsState();
                                            EventService.publish(new SlotboxEngineeringEvent(this.slotBox, slotBox.getSlot().getShipModule()));
                                        })
                                        .build();
                                button.setFocusTraversable(false);
                                boolean selected = experimental ? shipModule.getExperimentalEffects().contains(horizonsBlueprintType) : shipModule.getModifications().stream().anyMatch(modification -> modification.getModification().equals(horizonsBlueprintType));
                                button.selectedProperty().set(selected);
                                if (!experimental && selected) {
                                    final HorizonsBlueprintGrade maxGradeForModification = HorizonsBlueprintConstants.getBlueprintGrades(shipModule.getName().getPrimary(), horizonsBlueprintType).stream().max(Comparator.comparing(HorizonsBlueprintGrade::getGrade)).orElseThrow(IllegalArgumentException::new);
                                    maxGrade.set(maxGradeForModification.getGrade());
                                }
                                button.selectedProperty().addListener((observable, oldValue, newValue) ->
                                {
                                    if (Boolean.TRUE.equals(oldValue)) {
                                        if (experimental) {
                                            slotBox.getSlot().getShipModule().removeExperimentalEffect(horizonsBlueprintType);
                                        } else {
                                            slotBox.getSlot().getShipModule().removeModification(horizonsBlueprintType);
                                        }
                                        notifyChanged();
                                        slotBox.refresh();
                                    }
                                    if (Boolean.TRUE.equals(newValue)) {
                                        if (experimental) {
                                            slotBox.getSlot().getShipModule().applyExperimentalEffect(horizonsBlueprintType);
                                        } else {
                                            final HorizonsBlueprintGrade maxGradeForModification = HorizonsBlueprintConstants.getBlueprintGrades(shipModule.getName().getPrimary(), horizonsBlueprintType).stream().max(Comparator.comparing(HorizonsBlueprintGrade::getGrade)).orElseThrow(IllegalArgumentException::new);
                                            final HorizonsBlueprintGrade grade = HorizonsBlueprintGrade.forDigit(((ToggleButton) toggleGroupRank.getSelectedToggle()).getText());
                                            final BigDecimal completeness = BigDecimal.valueOf(progressSlider.getValue()).divide(BigDecimal.valueOf(100), 6, RoundingMode.HALF_EVEN);
                                            slotBox.getSlot().getShipModule().applyModification(horizonsBlueprintType, grade.getGrade() <= maxGradeForModification.getGrade() ? grade : maxGradeForModification, completeness);
                                        }
                                        notifyChanged();
                                        slotBox.refresh();
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

            DestroyableVBox blueprintButtons = BoxBuilder.builder()
                    .withStyleClass("blueprint-buttons")
                    .withNodes(toggleButtons)
                    .buildVBox();
            DestroyableLabel title = LabelBuilder.builder()
                    .withStyleClass("title")
                    .withText(sectionLabelKey)
                    .build();
            DestroyableVBox blueprintSection = BoxBuilder.builder()
                    .withStyleClass("section")
                    .withNodes(title, blueprintButtons)
                    .buildVBox();
            addGradeSelection(experimental, shipModule, toggleGroup, blueprintSection);
            content.getNodes().add(blueprintSection);
            return blueprintSection;
        }
        return null;
    }

    private void addGradeSelection(boolean experimental, ShipModule shipModule, ToggleGroup toggleGroup, DestroyableVBox vBox) {
        if (!experimental) {
            final DestroyableHBox progression = BoxBuilder.builder()
                    .withStyleClass("progression")
                    .buildHBox();
            toggleGroupRank = new ToggleGroup();
            toggleButtonsRank = new ArrayList<>();
            Arrays.stream(HorizonsBlueprintGrade.values()).filter(grade -> !HorizonsBlueprintGrade.NONE.equals(grade)).forEach(horizonsBlueprintGrade -> {
                final DestroyableToggleButton toggleButton = ToggleButtonBuilder.builder()
                        .withStyleClass("grade-button")
                        .withNonLocalizedText(String.valueOf(horizonsBlueprintGrade.getGrade()))
                        .withOnAction(event -> {
                            shipModule.getModifications().stream()
                                    .findFirst()
                                    .ifPresent(modification -> modification.setGrade(horizonsBlueprintGrade));
                            shipModule.getModifiers().clear();
                            notifyChanged();
                            slotBox.refresh();
                            EventService.publish(new SlotboxEngineeringEvent(this.slotBox, slotBox.getSlot().getShipModule()));
                        })
                        .build();
                toggleButton.setFocusTraversable(false);
                toggleButton.setToggleGroup(toggleGroupRank);
                toggleButton.addBinding(toggleButton.disableProperty(), toggleGroup.selectedToggleProperty().isNull().or(maxGrade.lessThan(horizonsBlueprintGrade.getGrade())));
                toggleButton.disableProperty().addListener((observable, oldValue, newValue) -> {
                    if (Boolean.TRUE.equals(newValue) && toggleButton.isSelected()) {
                        toggleButton.setSelected(false);
                        toggleButtonsRank.stream().filter(toggle -> !toggle.isDisabled()).max(Comparator.comparing(ToggleButton::getText)).ifPresent(button -> button.setSelected(true));
                        notifyChanged();
                        slotBox.refresh();
                    }
                });
                maxGrade.addListener((observable, oldValue, newValue) -> {
                    if (oldValue.equals(0) && newValue.equals(horizonsBlueprintGrade.getGrade())) {
                        toggleButton.setSelected(true);
                        notifyChanged();
                        slotBox.refresh();
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
            progression.getNodes().addAll(toggleButtonsRank);
            progressSlider = SliderBuilder.builder()
                    .withMin(0)
                    .withMax(100)
                    .withValue(shipModule.getModifications().stream()
                            .findFirst()
                            .flatMap(Modification::getModificationCompleteness)
                            .orElse(BigDecimal.ONE).multiply(BigDecimal.valueOf(100)).doubleValue())
                    .build();
            progressSlider.setFocusTraversable(false);
            subscribe = Observable.create((ObservableEmitter<Number> emitter) -> progressSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                        emitter.onNext(newValue);
                    }))
                    .debounce(500, TimeUnit.MILLISECONDS)
                    .observeOn(Schedulers.io())
                    .subscribe(newValue -> {
                                Platform.runLater(() -> {
                                            shipModule.getModifications().stream()
                                                    .findFirst()
                                                    .ifPresent(modification -> modification.setModificationCompleteness(BigDecimal.valueOf(newValue.doubleValue()).divide(BigDecimal.valueOf(100D))));
                                            shipModule.getModifiers().clear();
                                            notifyChanged();
                                            slotBox.refresh();
                                            EventService.publish(new SlotboxEngineeringEvent(this.slotBox, slotBox.getSlot().getShipModule()));
                                        }
                                );
                            },
                            t -> log.error(t.getMessage(), t));
            progressSlider.setStyle("-fx-fit-to-width: true;-fx-max-width: 40em;-fx-pref-width: 40em;");
            progressSlider.addBinding(progressSlider.disableProperty(), toggleGroup.selectedToggleProperty().isNull());
            final DestroyableTextField textvalue = TextFieldBuilder.builder()
                    .withFocusTraversable(false)
                    .build();
            textvalue.addBinding(textvalue.textProperty(), progressSlider.valueProperty().map(number -> Formatters.NUMBER_FORMAT_2.format(number) + "%"));
            textvalue.setDisable(true);
            textvalue.setStyle("-fx-max-width: 4.5em;-fx-min-width: 4.5em;");
            progression.getNodes().add(progressSlider);
            progression.getNodes().add(textvalue);

            vBox.getNodes().add(progression);
        }
    }


    private void addSearch(DestroyableVBox content, final List<SlotBoxEntry> entries) {
        final DestroyableTextField textField = TextFieldBuilder.builder()
                .withPromptTextProperty(LocaleService.getStringBinding("search.text.placeholder"))
                .build();

        textField.addChangeListener(textField.textProperty(), (_, _, newValue) -> {
            content.getChildren().subList(1, content.getNodes().size()).forEach(node -> {
                node.setManaged(false);
                node.setVisible(false);
            });
            if (Objects.equals(newValue, "")) {
                //add engineering
                if (slotBox.getSlot().isOccupied() && !slotBox.getSlot().getShipModule().isLegacy()) {
                    final ShipModule shipModule = slotBox.getSlot().getShipModule();
                    if (shipModule != null && !shipModule.getAllowedBlueprints().isEmpty()) {
                        blueprintSection.setVisible(true);
                        blueprintSection.setManaged(true);
                    }

                    if (shipModule != null && !shipModule.getAllowedExperimentalEffects().isEmpty()) {
                        experintalEffectsSection.setVisible(true);
                        experintalEffectsSection.setManaged(true);
                    }
                }
            }
            entries.stream()
                    .filter(entry -> entry.matches(newValue))
                    .forEach(e -> {
                        e.setVisible(true);
                        e.setManaged(true);
                    });
        });
        content.getNodes().add(textField);
    }

    private void addHardpointGroupButtons(DestroyableVBox content) {
        DestroyableLabel label = LabelBuilder.builder()
                .withStyleClass("title")
                .withText("ship.module.hardpoint.group.label")
                .build();
        final ToggleGroup toggleGroup = new ToggleGroup();
        DestroyableToggleButton[] buttons = Arrays.stream(HardpointGroup.values())
                .map(group -> ToggleButtonBuilder.builder()
                        .withStyleClass("hardpoint-button")
                        .withNonLocalizedText(group.name())
                        .withFocusTraversable(false)
                        .withToggleGroup(toggleGroup)
                        .withSelected(group.equals(slotBox.getSlot().getHardpointGroup()))
                        .withOnAction(event -> {
                            slotBox.getSlot().setHardpointGroup(group);
                            slotBox.refresh();
                            notifyChanged();
                            close();
                        })
                        .build())
                .toArray(DestroyableToggleButton[]::new);

        DestroyableHBox box = BoxBuilder.builder()
                .withStyleClass("hardpoint-buttons")
                .withNodes(buttons).buildHBox();
        content.getNodes().add(BoxBuilder.builder()
                .withStyleClass("section")
                .withNodes(label, box).buildVBox());
    }

    private void addChangedButtons(DestroyableVBox content) {
        DestroyableLabel label = LabelBuilder.builder()
                .withStyleClass("title")
                .withText("ship.module.state")
                .build();
        restore = ButtonBuilder.builder()
                .withText("ship.module.restore")
                .withOnAction(event -> {
                    final ShipModule shipModule = getShipModuleToRestore();
                    slotBox.getSlot().setShipModule(shipModule);
                    slotBox.refresh();
                    notifyChanged();
                    close();
                })
                .withFocusTraversable(false)
                .build();
        save = ButtonBuilder.builder()
                .withText("ship.module.save")
                .withOnAction(event -> {
                    slotBox.getSlot().setOldShipModule(slotBox.getSlot().getShipModule() == null ? null : slotBox.getSlot().getShipModule().Clone());
                    slotBox.refresh();
                    close();
                })
                .withFocusTraversable(false)
                .build();
        clear = ButtonBuilder.builder()
                .withText("ship.module.clear")
                .withOnAction(event -> {
                    //restore to default module for core modules, since they are not allowed to be removed
                    switch (slotBox.getSlot().getSlotType()) {
                        case CORE_ARMOUR, CORE_FRAME_SHIFT_DRIVE, CORE_FUEL_TANK, CORE_LIFE_SUPPORT,
                             CORE_POWER_DISTRIBUTION,
                             CORE_POWER_PLANT, CORE_SENSORS, CORE_THRUSTERS -> {
                            final ShipModule coreShipModule = getBaseShip().getCoreSlots().stream()
                                    .filter(baseShipSlot -> baseShipSlot.getSlotType().equals(slotBox.getSlot().getSlotType()))
                                    .findFirst()
                                    .map(baseShipSlot -> baseShipSlot.getShipModule().Clone())
                                    .orElseThrow(IllegalArgumentException::new);
                            slotBox.getSlot().setOldShipModule(coreShipModule);
                        }
                        default -> slotBox.getSlot().setOldShipModule(null);
                    }
                    slotBox.refresh();
                    close();
                })
                .withFocusTraversable(false)
                .build();
        setChangedButtonsState();
        DestroyableVBox box = BoxBuilder.builder()
                .withStyleClasses("section", "changes")
                .withNodes(label, new GrowingRegion(), restore, save, clear).buildVBox();
        content.getNodes().add(box);
    }


    private ShipModule getShipModuleToRestore() {
        final ShipModule shipModule = slotBox.getSlot().getOldShipModule() == null ? null : slotBox.getSlot().getOldShipModule().Clone();
        if (shipModule == null) {
            //restore to default module for core modules, since they are not allowed to be removed
            return switch (slotBox.getSlot().getSlotType()) {
                case CORE_ARMOUR, CORE_FRAME_SHIFT_DRIVE, CORE_FUEL_TANK, CORE_LIFE_SUPPORT, CORE_POWER_DISTRIBUTION,
                     CORE_POWER_PLANT, CORE_SENSORS, CORE_THRUSTERS -> {
                    final ShipModule coreShipModule = getBaseShip().getCoreSlots().stream()
                            .filter(baseShipSlot -> baseShipSlot.getSlotType().equals(slotBox.getSlot().getSlotType()))
                            .findFirst()
                            .map(baseShipSlot -> baseShipSlot.getShipModule().Clone())
                            .orElseThrow(IllegalArgumentException::new);
                    slotBox.getSlot().setOldShipModule(coreShipModule);
                    yield coreShipModule;
                }
                default -> null;
            };
        }
        return shipModule;
    }

    private Ship getBaseShip() {
        return Ship.ALL.stream()
                .filter(ship -> ship.getShipType().equals(APPLICATION_STATE.getShip().getShipType()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    private void setChangedButtonsState() {
        restore.setDisable(isSimilar(slotBox.getSlot().getShipModule(), slotBox.getSlot().getOldShipModule()));
        save.setDisable(isSimilar(slotBox.getSlot().getShipModule(), slotBox.getSlot().getOldShipModule()));
        clear.setDisable(slotBox.getSlot().getOldShipModule() == null);
    }

    private boolean isSimilar(ShipModule shipModule, ShipModule oldModule) {
        return (shipModule == null && oldModule == null) || (shipModule != null && shipModule.isSame(oldModule));
    }

    public void close() {
//        if (this.popOver != null) {
////            log.debug("destroying popover");
//            EventService.publish(new SlotboxHoverEvent(this, this.slot.getShipModule(), this.isHover()));
//            this.popOver.hide();
//            this.popOver = null;
//        }
        hide();
        restore = null;
        save = null;
        clear = null;
        this.blueprintSection = null;
        this.experintalEffectsSection = null;
    }


    void notifyChanged() {
        EventService.publish(new ShipBuilderEvent());
        EventService.publish(new SlotboxHoverEvent(this.slotBox, this.slotBox.getSlot().getShipModule(), true));
    }


    @Override
    public void destroyInternal() {
        super.destroyInternal();
        if (subscribe != null) {
            subscribe.dispose();
        }
    }
}
