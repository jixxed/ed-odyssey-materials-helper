package nl.jixxed.eliteodysseymaterials.templates.settings.sections;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ToggleButtonBuilder;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ShipConfigurationModification;
import nl.jixxed.eliteodysseymaterials.domain.ShipLegacyModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.HardpointModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.Modification;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.ships.LegacyModuleService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;
import org.controlsfx.control.SearchableComboBox;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static nl.jixxed.eliteodysseymaterials.templates.settings.SettingsTab.*;

@Slf4j
public class HorizonsShips extends DestroyableVBox implements DestroyableTemplate {

    private ListView<ShipLegacyModule> modulesList;
    private GridPane attributes;
    private Button saveButton;
    private TextField name;
    private Label typeLabel;
    private Label typeValueLabel;
    private ShipModule selectedShipModule;
    private List<ToggleButton> toggleButtonsRank;
    private ToggleGroup toggleGroupRank;
    IntegerProperty maxGrade = new SimpleIntegerProperty(0);
    private VBox blueprints;
    private VBox effects;
    private Label blueprintLabel;
    private Label effectLabel;

    public HorizonsShips() {
        this.initComponents();
        this.initEventHandling();
    }

    @Override
    public void initComponents() {
        final Label shipsLabel = LabelBuilder.builder()
                .withStyleClass("settings-header")
                .withText(LocaleService.getStringBinding("tab.settings.title.ships.horizons"))
                .build();
        final HBox horizonsShipsCreateModule = createHorizonsShipsCreateModule();
        final HBox horizonsShipsModuleList = createHorizonsShipsModuleList();

        this.getStyleClass().addAll("settingsblock", SETTINGS_SPACING_10_CLASS);
        this.getChildren().addAll(shipsLabel, horizonsShipsCreateModule, horizonsShipsModuleList);
    }

    @Override
    public void initEventHandling() {
        register(EventService.addStaticListener(true, 0, LegacyModuleSavedEvent.class, legacyModuleSavedEvent -> {
            updateModules();
        }));
        register(EventService.addStaticListener(true, 0, CommanderSelectedEvent.class, commanderSelectedEvent -> {
            updateModules();
        }));
        register(EventService.addStaticListener(true, 0, CommanderAllListedEvent.class, commanderAllListedEvent -> {
            ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> updateModules()
            );
        }));
    }

    private HBox createHorizonsShipsCreateModule() {
        final Label newLegacyModuleLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.ships.new.module")).build();
        SearchableComboBox<ShipModule> shipModuleSearchableComboBox = new SearchableComboBox<>(FXCollections.observableList(ShipModule.getBasicModules()));

        Button create = ButtonBuilder.builder().withText(LocaleService.getStringBinding("tab.settings.ships.new.module.create")).withOnAction(event -> {
            final ShipModule selectedItem = shipModuleSearchableComboBox.getSelectionModel().getSelectedItem();
            LegacyModuleService.saveLegacyModule(selectedItem);
            updateModules();
        }).build();
        create.disableProperty().bind(shipModuleSearchableComboBox.getSelectionModel().selectedItemProperty().isNull());
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(newLegacyModuleLabel, shipModuleSearchableComboBox, create)
                .buildHBox();
    }

    private HBox createHorizonsShipsModuleList() {

        final Label legacyModulesLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.ships.legacy.modules")).build();
        this.modulesList = new ListView<>();
        Button removeButton = ButtonBuilder.builder()
                .withStyleClass("settings-legacy-module-button")
                .withText(LocaleService.getStringBinding("tab.settings.ships.legacy.modules.remove"))
                .withOnAction(e -> {
                    if (this.modulesList.getSelectionModel().getSelectedItem() != null) {
                        LegacyModuleService.deleteLegacyModule(this.modulesList.getSelectionModel().getSelectedItem().getUuid());
                        this.modulesList.getItems().remove(this.modulesList.getSelectionModel().getSelectedItem());
                    }
//                    EventService.publish(new LegacyModuleRemovedEvent());
                })
                .build();

        removeButton.disableProperty().bind(this.modulesList.getSelectionModel().selectedItemProperty().isNull());
        saveButton = ButtonBuilder.builder()
                .withStyleClass("settings-legacy-module-button")
                .withText(LocaleService.getStringBinding("tab.settings.ships.legacy.modules.save"))
                .withOnAction(e -> {
//                    LegacyModuleService.deleteLegacyModule(this.modulesList.getSelectionModel().getSelectedItem().getUuid());
//                    this.modulesList.getItems().remove(this.modulesList.getSelectionModel().getSelectedItem());
//                    EventService.publish(new LegacyModuleUpdatedEvent());
                })
                .build();
        saveButton.disableProperty().bind(this.modulesList.getSelectionModel().selectedItemProperty().isNull());
        this.modulesList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    blueprints.getChildren().clear();
                    effects.getChildren().clear();
                    attributes.getChildren().clear();
                    if (newValue != null) {
                        final ShipModule shipModule = ShipModule.getModule(newValue.getId()).Clone();

                        maxGrade.set(Optional.ofNullable(newValue)
                                .map(module -> module.getModification().stream()
                                        .findFirst()
                                        .map(modification -> HorizonsBlueprintConstants.getBlueprintGrades(shipModule.getName().getPrimary(), modification.getType()).stream().max(Comparator.comparing(HorizonsBlueprintGrade::getGrade)).map(HorizonsBlueprintGrade::getGrade).orElse(0))
                                        .orElse(0))
                                .orElse(0));
                        newValue.getModification().forEach(shipConfigurationModification -> {
                            shipModule.applyModification(shipConfigurationModification.getType(), shipConfigurationModification.getGrade(), null);
                                }
                        );
                        newValue.getExperimentalEffect().forEach(shipConfigurationExperimentalEffect -> {
                            shipModule.applyExperimentalEffect(shipConfigurationExperimentalEffect.getType());
                                }
                        );
                        shipModule.getModifiers().putAll(newValue.getModifiers());
                        shipModule.setLegacy(true);
                        blueprints.getChildren().add(getBlueprintSection(newValue, shipModule, false));
                        effects.getChildren().add(getBlueprintSection(newValue, shipModule, true));
                        showAttributes(shipModule);
                        name.setText(newValue.getName());
                        typeValueLabel.setText(LocaleService.getLocalizedStringForCurrentLocale(shipModule.getLocalizationKey()) + " " + shipModule.getModuleSize() + shipModule.getModuleClass() + (shipModule instanceof HardpointModule hardpointModule ? "-" + hardpointModule.getMounting() : ""));
                        saveButton.setOnAction(actionEvent -> {
                            LegacyModuleService.updateLegacyModule(newValue.getUuid(), name.getText(), shipModule);
                            updateModules();
                            modulesList.getSelectionModel().select(newValue);
                        });
                    } else {
                        typeValueLabel.setText("");
                        name.setText("");
                    }

                }
        );
        updateModules();
        this.modulesList.getStyleClass().add("settings-legacy-modules-list");
        this.modulesList.setCellFactory(getLegacyModulesCellFactory());
//
        Label nameLabel = LabelBuilder.builder().withText(LocaleService.getStringBinding("tab.settings.ships.legacy.modules.name")).build();
        name = new TextField();
        name.getStyleClass().add("settings-legacy-module-cb");
        typeLabel = LabelBuilder.builder().withStyleClass("settings-legacy-module-label").withText(LocaleService.getStringBinding("tab.settings.ships.legacy.modules.type")).build();
        typeValueLabel = LabelBuilder.builder().withStyleClass("settings-legacy-module-label").build();
        blueprintLabel = LabelBuilder.builder().withStyleClass("settings-legacy-module-label").withText(LocaleService.getStringBinding("tab.settings.ships.legacy.modules.blueprint")).build();
        blueprints = BoxBuilder.builder().withStyleClass("settings-legacy-module-cb").buildVBox();
        effectLabel = LabelBuilder.builder().withStyleClass("settings-legacy-module-label").withText(LocaleService.getStringBinding("tab.settings.ships.legacy.modules.effect")).build();
        effects = BoxBuilder.builder().withStyleClass("settings-legacy-module-cb").buildVBox();
        final VBox buttons = BoxBuilder.builder().withStyleClass("settings-legacy-modules-buttons").withNodes(removeButton, saveButton, typeLabel, typeValueLabel, nameLabel, name,  blueprints,  effects).buildVBox();
        attributes = new GridPane();
        attributes.getStyleClass().add("settings-legacy-modules-attributes-grid");
        nameLabel.visibleProperty().bind(modulesList.getSelectionModel().selectedItemProperty().isNotNull());
        typeLabel.visibleProperty().bind(modulesList.getSelectionModel().selectedItemProperty().isNotNull());
        name.visibleProperty().bind(modulesList.getSelectionModel().selectedItemProperty().isNotNull());
        typeValueLabel.visibleProperty().bind(modulesList.getSelectionModel().selectedItemProperty().isNotNull());


        return BoxBuilder.builder()
                .withStyleClass(SETTINGS_SPACING_10_CLASS)
                .withNodes(legacyModulesLabel, modulesList, buttons, attributes)
                .buildHBox();

    }

    private void updateModules() {
        final ObservableList<ShipLegacyModule> items = ApplicationState.getInstance().getPreferredCommander().map(commander -> LegacyModuleService.loadModules(commander).getLegacyModules().stream().collect(Collectors.toCollection(FXCollections::observableArrayList))).orElseGet(FXCollections::emptyObservableList);
        this.modulesList.setItems(items);
    }


    private void showAttributes(ShipModule shipModule) {

        AtomicInteger index = new AtomicInteger(0);
        shipModule.getAttibutes().stream().sorted(Comparator.comparing(HorizonsModifier::getOrder)).forEach(horizonsModifier -> {
            if (shipModule.getOriginalAttributeValue(horizonsModifier) instanceof Double) {
                final ShipAttribute shipAttribute = new ShipAttribute(shipModule, horizonsModifier);
                attributes.addRow(index.getAndIncrement(), shipAttribute.getTitle(), shipAttribute.getValuesLine(), shipAttribute.getTextField());
            }
            if (shipModule.getOriginalAttributeValue(horizonsModifier) instanceof Boolean) {
                final ShipAttribute shipAttribute = new ShipAttribute(shipModule, horizonsModifier);
                attributes.addRow(index.getAndIncrement(), shipAttribute.getTitle(), shipAttribute.getValuesLine(), shipAttribute.getCheckBox());
            }
        });
    }

    private VBox getBlueprintSection(ShipLegacyModule shipLegacyModule, final ShipModule shipModule, final boolean experimental) {

        if (shipLegacyModule != null) {
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
                                            .withStyleClass("settings-legacy-module-button")
                                            .withText(blueprintStringBinding)
                                            .withOnAction(event -> {
                                                if (experimental) {
                                                    if (((ToggleButton) event.getTarget()).isSelected()) {
                                                        shipModule.getExperimentalEffects().clear();
                                                        shipModule.getExperimentalEffects().add(horizonsBlueprintType);
                                                    } else {
                                                        shipModule.getExperimentalEffects().clear();
                                                    }
                                                } else {
                                                    if (((ToggleButton) event.getTarget()).isSelected()) {
                                                        final HorizonsBlueprintGrade maxGradeForModification = HorizonsBlueprintConstants.getBlueprintGrades(shipModule.getName().getPrimary(), horizonsBlueprintType).stream().max(Comparator.comparing(HorizonsBlueprintGrade::getGrade)).orElseThrow(IllegalArgumentException::new);
                                                        final HorizonsBlueprintGrade grade = HorizonsBlueprintGrade.forDigit(((ToggleButton) toggleGroupRank.getSelectedToggle()).getText());
                                                        shipModule.getModifications().clear();
                                                        shipModule.getModifications().add(new Modification(horizonsBlueprintType, (Double) null, grade.getGrade() <= maxGradeForModification.getGrade() ? grade : maxGradeForModification));
                                                        maxGrade.set(maxGradeForModification.getGrade());
                                                    } else {
                                                        shipModule.getModifications().clear();
                                                        maxGrade.set(0);
                                                    }
                                                }
                                            }).build();
                                    button.selectedProperty().set((experimental ? shipLegacyModule.getExperimentalEffect().stream().anyMatch(effect -> effect.getType().equals(horizonsBlueprintType)) : shipLegacyModule.getModification().stream().anyMatch(modification -> modification.getType().equals(horizonsBlueprintType))));
                                    button.selectedProperty().addListener((observable, oldValue, newValue) ->
                                    {
                                        if (Boolean.TRUE.equals(oldValue)) {
                                            if (experimental) {
                                                shipModule.getExperimentalEffects().clear();
                                            } else {
                                                shipModule.getModifications().clear();
                                            }
                                        }
                                        if (Boolean.TRUE.equals(newValue)) {
                                            if (experimental) {
                                                shipModule.getExperimentalEffects().clear();
                                                shipModule.getExperimentalEffects().add(horizonsBlueprintType);
                                            } else {
                                                final HorizonsBlueprintGrade maxGradeForModification = HorizonsBlueprintConstants.getBlueprintGrades(shipModule.getName().getPrimary(), horizonsBlueprintType).stream().max(Comparator.comparing(HorizonsBlueprintGrade::getGrade)).orElseThrow(IllegalArgumentException::new);
                                                final HorizonsBlueprintGrade grade = HorizonsBlueprintGrade.forDigit(((ToggleButton) toggleGroupRank.getSelectedToggle()).getText());
                                                shipModule.getModifications().clear();
                                                shipModule.getModifications().add(new Modification(horizonsBlueprintType, (Double) null, grade.getGrade() <= maxGradeForModification.getGrade() ? grade : maxGradeForModification));
                                            }
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

                final VBox vBox = BoxBuilder.builder().withStyleClass("settings-legacy-module-cb").buildVBox();
                vBox.getChildren().add(experimental ? effectLabel : blueprintLabel);
                vBox.getChildren().addAll(toggleButtons);
                addGradeSelection(experimental, shipLegacyModule, shipModule, toggleGroup, vBox);
                return vBox;
            }
        }
        return new VBox();
    }

    private void addGradeSelection(boolean experimental, ShipLegacyModule shipLegacyModule, ShipModule shipModule, ToggleGroup toggleGroup, VBox vBox) {
        if (!experimental) {
            final HBox progression = BoxBuilder.builder().withStyleClass("settings-legacy-module-grades").buildHBox();
            toggleGroupRank = new ToggleGroup();
            toggleButtonsRank = new ArrayList<>();
            Arrays.stream(HorizonsBlueprintGrade.values()).filter(grade -> !HorizonsBlueprintGrade.NONE.equals(grade)).forEach(horizonsBlueprintGrade -> {
                final ToggleButton toggleButton = ToggleButtonBuilder.builder()
                        .withStyleClass("settings-legacy-module-button-grade").withNonLocalizedText(String.valueOf(horizonsBlueprintGrade.getGrade())).withOnAction(event -> {
                    shipModule.getModifications().getFirst().setGrade(horizonsBlueprintGrade);
                }).build();
                toggleButton.setToggleGroup(toggleGroupRank);
                toggleButton.disableProperty().bind(toggleGroup.selectedToggleProperty().isNull().or(maxGrade.lessThan(horizonsBlueprintGrade.getGrade())));
                toggleButton.disableProperty().addListener((observable, oldValue, newValue) -> {
                    if (Boolean.TRUE.equals(newValue) && toggleButton.isSelected()) {
                        toggleButton.setSelected(false);
                        toggleButtonsRank.stream().filter(toggle -> !toggle.isDisabled()).max(Comparator.comparing(ToggleButton::getText)).ifPresent(button -> button.setSelected(true));
                    }
                });
                maxGrade.addListener((observable, oldValue, newValue) -> {
                    if (oldValue.equals(0) && newValue.equals(horizonsBlueprintGrade.getGrade())) {
                        toggleButton.setSelected(true);
                    }
                });
                toggleButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
                    if (Boolean.TRUE.equals(!newValue) && toggleButton.getToggleGroup().getSelectedToggle() == null) {
                        toggleButton.setSelected(true);
                    }
                });
                toggleButton.setSelected(horizonsBlueprintGrade.equals(shipLegacyModule.getModification().stream().findFirst().map(ShipConfigurationModification::getGrade).orElse(HorizonsBlueprintGrade.GRADE_5)));
                toggleButtonsRank.add(toggleButton);

            });
            progression.getChildren().addAll(toggleButtonsRank);

            vBox.getChildren().add(progression);
        }
    }

    private Callback<ListView<ShipLegacyModule>, ListCell<ShipLegacyModule>> getLegacyModulesCellFactory() {
        return listView -> new ListCell<>() {

            @SuppressWarnings("java:S1068")
            private final EventListener<LanguageChangedEvent> engineerEventEventListener = EventService.addListener(true, this, LanguageChangedEvent.class, event ->
                    updateText(getItem(), this.emptyProperty().get())
            );


            @Override
            protected void updateItem(final ShipLegacyModule item, final boolean empty) {
                super.updateItem(item, empty);
                updateText(item, empty);
            }

            private void updateText(final ShipLegacyModule item, final boolean empty) {
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item.getName());
                }
            }

        };
    }

}
