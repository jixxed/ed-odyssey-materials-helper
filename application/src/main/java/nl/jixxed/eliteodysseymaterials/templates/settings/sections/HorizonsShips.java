package nl.jixxed.eliteodysseymaterials.templates.settings.sections;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.util.Callback;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.*;
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
import nl.jixxed.eliteodysseymaterials.service.event.EngineerEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.LegacyModuleSavedEvent;
import nl.jixxed.eliteodysseymaterials.service.ships.LegacyModuleService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static nl.jixxed.eliteodysseymaterials.templates.settings.SettingsTab.*;

@Slf4j
public class HorizonsShips extends DestroyableVBox implements DestroyableEventTemplate {

    public static final String SETTINGS_LEGACY_MODULE_BUTTON_STYLE_CLASS = "settings-legacy-module-button";
    public static final String SETTINGS_LEGACY_MODULE_LABEL_STYLE_CLASS = "settings-legacy-module-label";
    public static final String SETTINGS_LEGACY_MODULE_CB_STYLE_CLASS = "settings-legacy-module-cb";
    private DestroyableListView<ShipLegacyModule> modulesList;
    private DestroyableGridPane attributes;
    private DestroyableButton saveButton;
    private DestroyableTextField nameValue;
    private DestroyableLabel typeValue;
    private List<DestroyableToggleButton> toggleButtonsRank;
    private ToggleGroup toggleGroupRank;
    private final IntegerProperty maxGrade = new SimpleIntegerProperty(0);
    private DestroyableVBox blueprints;
    private DestroyableVBox effects;
    //    private DestroyableLabel blueprintLabel;
//    private DestroyableLabel effectLabel;
    private BooleanBinding selectedNull;

    private Callback<ListView<ShipLegacyModule>, ListCell<ShipLegacyModule>> createDestroyableCellFactory(Destroyable destroyable) {
        return listView -> new DestroyableListCell<>() {
            {
                destroyable.register(this);
                register(EventService.addListener(true, listView, EngineerEvent.class, _ -> {
                    updateText(getItem(), this.emptyProperty().get());
                }));
            }

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

    public HorizonsShips() {
        this.initComponents();
        this.initEventHandling();
    }

    @Override
    public void initComponents() {
        final DestroyableLabel shipsLabel = LabelBuilder.builder()
                .withStyleClass("settings-header")
                .withText("tab.settings.title.ships.horizons")
                .build();
        final DestroyableHBox horizonsShipsCreateModule = createHorizonsShipsCreateModule();
        final DestroyableHBox horizonsShipsModuleList = createHorizonsShipsModuleList();

        this.getStyleClass().addAll("settingsblock", SETTINGS_SPACING_10_CLASS);
        this.getNodes().addAll(shipsLabel, horizonsShipsCreateModule, horizonsShipsModuleList);
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, 0, LegacyModuleSavedEvent.class, _ -> updateModules()));
    }

    private DestroyableHBox createHorizonsShipsCreateModule() {
        final DestroyableLabel newLegacyModuleLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("tab.settings.ships.new.module")
                .build();
        DestroyableSearchableComboBox<ShipModule> shipModuleSearchableComboBox = SearchableComboBoxBuilder.builder(ShipModule.class)
                .withItemsProperty(FXCollections.observableList(ShipModule.getBasicModules()))
                .build();

        DestroyableButton create = ButtonBuilder.builder()
                .withText("tab.settings.ships.new.module.create")
                .withOnAction(_ -> {
                    final ShipModule selectedItem = shipModuleSearchableComboBox.getSelectionModel().getSelectedItem();
                    LegacyModuleService.saveLegacyModule(selectedItem);
                    updateModules();
                })
                .withDisableProperty(shipModuleSearchableComboBox.getSelectionModel().selectedItemProperty().isNull())
                .build();
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(newLegacyModuleLabel, shipModuleSearchableComboBox, create)
                .buildHBox();
    }

    private DestroyableHBox createHorizonsShipsModuleList() {

        final DestroyableLabel legacyModulesLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText(LocaleService.getStringBinding("tab.settings.ships.legacy.modules"))
                .build();
        this.modulesList = ListViewBuilder.builder(ShipLegacyModule.class)
                .withStyleClass("settings-legacy-modules-list")
                .build();
        this.modulesList.setCellFactory(createDestroyableCellFactory(this.modulesList));
        DestroyableButton removeButton = ButtonBuilder.builder()
                .withStyleClass(SETTINGS_LEGACY_MODULE_BUTTON_STYLE_CLASS)
                .withText("tab.settings.ships.legacy.modules.remove")
                .withOnAction(e -> {
                    if (this.modulesList.getSelectionModel().getSelectedItem() != null) {
                        LegacyModuleService.deleteLegacyModule(this.modulesList.getSelectionModel().getSelectedItem().getUuid());
                        this.modulesList.getItems().remove(this.modulesList.getSelectionModel().getSelectedItem());
                    }
                })
                .build();

        selectedNull = this.modulesList.getSelectionModel().selectedItemProperty().isNull();
        removeButton.addBinding(removeButton.disableProperty(), selectedNull);
        saveButton = ButtonBuilder.builder()
                .withStyleClass(SETTINGS_LEGACY_MODULE_BUTTON_STYLE_CLASS)
                .withText("tab.settings.ships.legacy.modules.save")
                .build();
        saveButton.addBinding(saveButton.disableProperty(), selectedNull);
        this.modulesList.addChangeListener(this.modulesList.getSelectionModel().selectedItemProperty(), (_, _, newValue) -> update(newValue));
        updateModules();
//
        DestroyableLabel nameTitle = LabelBuilder.builder()
                .withText("tab.settings.ships.legacy.modules.name")
                .withVisibilityProperty(selectedNull.not())
                .build();
        nameValue = TextFieldBuilder.builder()
                .withStyleClass(SETTINGS_LEGACY_MODULE_CB_STYLE_CLASS)
                .withVisibilityProperty(selectedNull.not())
                .build();
        DestroyableLabel typeTitle = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LEGACY_MODULE_LABEL_STYLE_CLASS)
                .withText("tab.settings.ships.legacy.modules.type")
                .withVisibilityProperty(selectedNull.not())
                .build();
        typeValue = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LEGACY_MODULE_LABEL_STYLE_CLASS)
                .withVisibilityProperty(selectedNull.not())
                .build();
        blueprints = BoxBuilder.builder()
                .withStyleClass(SETTINGS_LEGACY_MODULE_CB_STYLE_CLASS)
                .buildVBox();
        effects = BoxBuilder.builder()
                .withStyleClass(SETTINGS_LEGACY_MODULE_CB_STYLE_CLASS)
                .buildVBox();
        final DestroyableVBox buttons = BoxBuilder.builder()
                .withStyleClass("settings-legacy-modules-buttons")
                .withNodes(removeButton, saveButton, typeTitle, typeValue, nameTitle, nameValue, blueprints, effects)
                .buildVBox();
        attributes = GridPaneBuilder.builder()
                .withStyleClass("settings-legacy-modules-attributes-grid")
                .build();

        return BoxBuilder.builder()
                .withStyleClass(SETTINGS_SPACING_10_CLASS)
                .withNodes(legacyModulesLabel, modulesList, buttons, attributes)
                .buildHBox();

    }

    private void update(ShipLegacyModule newValue) {
        blueprints.getNodes().clear();
        effects.getNodes().clear();
        attributes.getNodes().clear();
        if (newValue != null) {
            final ShipModule shipModule = ShipModule.getModule(newValue.getId()).Clone();

            maxGrade.set(Optional.of(newValue)
                    .map(module -> module.getModification().stream()
                            .findFirst()
                            .map(modification -> HorizonsBlueprintConstants.getBlueprintGrades(shipModule.getName().getPrimary(), modification.getType()).stream().max(Comparator.comparing(HorizonsBlueprintGrade::getGrade)).map(HorizonsBlueprintGrade::getGrade).orElse(0))
                            .orElse(0))
                    .orElse(0));
            newValue.getModification()
                    .forEach(modification -> shipModule.applyModification(modification.getType(), modification.getGrade(), null));
            newValue.getExperimentalEffect()
                    .forEach(effect -> shipModule.applyExperimentalEffect(effect.getType()));
            shipModule.getModifiers().putAll(newValue.getModifiers());
            shipModule.setLegacy(true);
            blueprints.getNodes().add(getBlueprintSection(newValue, shipModule, false));
            effects.getNodes().add(getBlueprintSection(newValue, shipModule, true));
            showAttributes(shipModule);
            nameValue.setText(newValue.getName());
            typeValue.addBinding(typeValue.textProperty(), LocaleService.getStringBinding("tab.settings.module",
                    LocaleService.LocalizationKey.of(shipModule.getLocalizationKey()),
                    shipModule.getModuleSize(),
                    shipModule.getModuleClass(),
                    (shipModule instanceof HardpointModule hardpointModule ? "-" + hardpointModule.getMounting() : "")));

            saveButton.addActionEventBinding(saveButton.onActionProperty(), _ -> {
                LegacyModuleService.updateLegacyModule(newValue.getUuid(), nameValue.getText(), shipModule);
                updateModules();
                modulesList.getSelectionModel().select(newValue);
            });
        } else {
            typeValue.textProperty().unbind();
            nameValue.setText("");
        }
    }

    private void updateModules() {
        final ObservableList<ShipLegacyModule> items = ApplicationState.getInstance().getPreferredCommander()
                .map(commander -> LegacyModuleService.loadModules(commander).getLegacyModules().stream()
                        .collect(Collectors.toCollection(FXCollections::observableArrayList)))
                .orElseGet(FXCollections::emptyObservableList);
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

    private <E extends Node & DestroyableComponent> DestroyableVBox getBlueprintSection(ShipLegacyModule shipLegacyModule, final ShipModule shipModule, final boolean experimental) {

        if (shipLegacyModule != null) {
            final List<HorizonsBlueprintType> allowedBlueprints = experimental ? shipModule.getAllowedExperimentalEffects() : shipModule.getAllowedBlueprints();
            if (!allowedBlueprints.isEmpty()) {
                final ToggleGroup toggleGroup = new ToggleGroup();
                final List<DestroyableToggleButton> toggleButtons = allowedBlueprints.stream()
                        .sorted(Comparator.comparing(horizonsBlueprintType -> LocaleService.getLocalizedStringForCurrentLocale(horizonsBlueprintType.getLocalizationKey(true))))
                        .map(horizonsBlueprintType -> createBlueprintButton(shipLegacyModule, shipModule, experimental, horizonsBlueprintType, toggleGroup))
                        .toList();
                DestroyableLabel title = LabelBuilder.builder()
                        .withStyleClass(SETTINGS_LEGACY_MODULE_LABEL_STYLE_CLASS)
                        .withText((experimental) ? "tab.settings.ships.legacy.modules.effect" : "tab.settings.ships.legacy.modules.blueprint")
                        .build();

                final DestroyableVBox vBox = BoxBuilder.builder()
                        .withStyleClass(SETTINGS_LEGACY_MODULE_CB_STYLE_CLASS)
                        .withNode(title)
                        .withNodes(toggleButtons)
                        .buildVBox();
                addGradeSelection(experimental, shipLegacyModule, shipModule, toggleGroup, vBox);
                return vBox;
            }
        }
        return BoxBuilder.builder()
                .buildVBox();
    }

    //TODO needs refactoring
    private DestroyableToggleButton createBlueprintButton(ShipLegacyModule shipLegacyModule, ShipModule shipModule, boolean experimental, HorizonsBlueprintType horizonsBlueprintType, ToggleGroup toggleGroup) {
        final int multiplier = experimental
                ? shipModule.getExperimentalEffects().stream().filter(horizonsBlueprintType::equals).toList().size()
                : shipModule.getModifications().stream().filter(modification -> modification.getModification().equals(horizonsBlueprintType)).toList().size();
        final StringBinding blueprintStringBinding;
        if (multiplier > 1) {
            blueprintStringBinding = LocaleService.getStringBinding(() -> LocaleService.getLocalizedStringForCurrentLocale(horizonsBlueprintType.getLocalizationKey(true)).concat(" x" + multiplier));
        } else {
            blueprintStringBinding = LocaleService.getStringBinding(horizonsBlueprintType.getLocalizationKey(true));
        }
        final DestroyableToggleButton button = ToggleButtonBuilder.builder()
                .withStyleClass(SETTINGS_LEGACY_MODULE_BUTTON_STYLE_CLASS)
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
                            final HorizonsBlueprintGrade maxGradeForModification = HorizonsBlueprintConstants.getBlueprintGrades(shipModule.getName().getPrimary(), horizonsBlueprintType).stream()
                                    .max(Comparator.comparing(HorizonsBlueprintGrade::getGrade))
                                    .orElseThrow(IllegalArgumentException::new);
                            final HorizonsBlueprintGrade grade = HorizonsBlueprintGrade.forDigit(((ToggleButton) toggleGroupRank.getSelectedToggle()).getText());
                            shipModule.getModifications().clear();
                            shipModule.getModifications().add(new Modification(horizonsBlueprintType, (Double) null, grade.getGrade() <= maxGradeForModification.getGrade() ? grade : maxGradeForModification));
                            maxGrade.set(maxGradeForModification.getGrade());
                        } else {
                            shipModule.getModifications().clear();
                            maxGrade.set(0);
                        }
                    }
                })
                .withSelected((experimental ? shipLegacyModule.getExperimentalEffect().stream().anyMatch(effect -> effect.getType().equals(horizonsBlueprintType)) : shipLegacyModule.getModification().stream().anyMatch(modification -> modification.getType().equals(horizonsBlueprintType))))
                .withFocusTraversable(false)
                .build();
        button.addChangeListener(button.selectedProperty(), (_, oldValue, newValue) ->
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
        if ((experimental && shipModule.getExperimentalEffects().size() <= 1) || (!experimental && shipModule.getModifications().size() <= 1)) {
            button.setToggleGroup(toggleGroup);
        } else {
            button.setDisable(true);
        }
        return button;
    }

    private void addGradeSelection(boolean experimental, ShipLegacyModule shipLegacyModule, ShipModule shipModule, ToggleGroup toggleGroup, DestroyableVBox vBox) {
        if (!experimental) {
            final DestroyableHBox progression = BoxBuilder.builder()
                    .withStyleClass("settings-legacy-module-grades")
                    .buildHBox();
            toggleGroupRank = new ToggleGroup();
            toggleButtonsRank = new ArrayList<>();
            Arrays.stream(HorizonsBlueprintGrade.values()).filter(grade -> !HorizonsBlueprintGrade.NONE.equals(grade)).forEach(horizonsBlueprintGrade -> {
                final DestroyableToggleButton toggleButton = createToggleButton(shipLegacyModule, shipModule, toggleGroup, horizonsBlueprintGrade);
                toggleButtonsRank.add(toggleButton);

            });
            progression.getNodes().addAll(toggleButtonsRank);

            vBox.getNodes().add(progression);
        }
    }

    private DestroyableToggleButton createToggleButton(ShipLegacyModule shipLegacyModule, ShipModule shipModule, ToggleGroup toggleGroup, HorizonsBlueprintGrade horizonsBlueprintGrade) {
        final DestroyableToggleButton toggleButton = ToggleButtonBuilder.builder()
                .withStyleClass("settings-legacy-module-button-grade")
                .withNonLocalizedText(String.valueOf(horizonsBlueprintGrade.getGrade()))
                .withOnAction(_ -> shipModule.getModifications().getFirst().setGrade(horizonsBlueprintGrade))
                .withDisableProperty(toggleGroup.selectedToggleProperty().isNull().or(maxGrade.lessThan(horizonsBlueprintGrade.getGrade())))
                .withSelected(horizonsBlueprintGrade.equals(shipLegacyModule.getModification().stream().findFirst().map(ShipConfigurationModification::getGrade).orElse(HorizonsBlueprintGrade.GRADE_5)))
                .withToggleGroup(toggleGroup)
                .build();
        toggleButton.setToggleGroup(toggleGroupRank);
        toggleButton.addChangeListener(toggleButton.disableProperty(), (_, _, newValue) -> {
            if (Boolean.TRUE.equals(newValue) && toggleButton.isSelected()) {
                toggleButton.setSelected(false);
                toggleButtonsRank.stream()
                        .filter(toggle -> !toggle.isDisabled())
                        .max(Comparator.comparing(ToggleButton::getText)).ifPresent(button -> button.setSelected(true));
            }
        });
        addChangeListener(maxGrade, (_, oldValue, newValue) -> {
            if (oldValue.equals(0) && newValue.equals(horizonsBlueprintGrade.getGrade())) {
                toggleButton.setSelected(true);
            }
        });
        toggleButton.addChangeListener(toggleButton.selectedProperty(), (_, _, newValue) -> {
            if (Boolean.TRUE.equals(!newValue) && toggleButton.getToggleGroup().getSelectedToggle() == null) {
                toggleButton.setSelected(true);
            }
        });
        return toggleButton;
    }

    @Override
    public void destroyInternal() {
        super.destroyInternal();
        selectedNull.dispose();
        maxGrade.unbind();
    }
}
