package nl.jixxed.eliteodysseymaterials.templates.horizons.menu;

import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.constants.UTF8Constants;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.helper.BlueprintHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIcon;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;
import nl.jixxed.eliteodysseymaterials.templates.generic.menu.About;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class HorizonsBlueprintBar extends DestroyableAccordion implements DestroyableEventTemplate {
    private static final String BLUEPRINT_TITLED_PANE_CONTENT_STYLE_CLASS = "blueprint-titled-pane-content";
    private static final String BLUEPRINT_LIST_STYLE_CLASS = "blueprint-list";
    private DestroyableTitledPane categoryTitledPaneSynthesis;

    private Callback<ListView<HorizonsBlueprintName>, ListCell<HorizonsBlueprintName>> createDestroyableCellFactory(Destroyable destroyable) {
        return _ -> new DestroyableListCell<>() {
            {
                destroyable.register(this);
                register(EventService.addListener(true, destroyable, StorageEvent.class, _ -> {
                    updateText(getItem(), this.emptyProperty().get());
                }));
                register(EventService.addListener(true, destroyable, EngineerEvent.class, _ -> {
                    updateText(getItem(), this.emptyProperty().get());
                }));
            }

            @Override
            protected void updateItem(final HorizonsBlueprintName item, final boolean empty) {
                super.updateItem(item, empty);
                updateText(item, empty);
                updateStyle(item);
            }

            private void updateText(final HorizonsBlueprintName item, final boolean empty) {
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item + (BlueprintHelper.isCompletedEngineerRecipe(item) ? " " + UTF8Constants.CHECK_TRUE : ""));
                    setGraphic(item.isInColonia() ? EdAwesomeIconViewPaneBuilder.builder().withStyleClass("colonia-icon").withIcons(EdAwesomeIcon.OTHER_COLONIA).build() : null);
                }
            }

            private void updateStyle(final HorizonsBlueprintName item) {
                if (item != null && BlueprintHelper.isCompletedEngineerRecipe(item)) {
                    this.pseudoClassStateChanged(PseudoClass.getPseudoClass("complete"), true);
                } else {
                    this.pseudoClassStateChanged(PseudoClass.getPseudoClass("complete"), false);
                }
            }
        };
    }

    private Callback<ListView<HorizonsBlueprintName>, ListCell<HorizonsBlueprintName>> createDestroyableButtonCellFactory(Destroyable destroyable) {
        return _ -> new DestroyableListCell<>() {
            {
                destroyable.register(this);
                register(EventService.addListener(true, destroyable, StorageEvent.class, _ -> {
                    updateText(getItem(), this.emptyProperty().get());
                }));
                register(EventService.addListener(true, destroyable, EngineerEvent.class, _ -> {
                    updateText(getItem(), this.emptyProperty().get());
                }));
            }

            @Override
            protected void updateItem(final HorizonsBlueprintName item, final boolean empty) {
                super.updateItem(item, empty);
                updateText(item, empty);
                updateStyle(item);
            }

            private void updateText(final HorizonsBlueprintName item, final boolean empty) {
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    String displayText = item + (BlueprintHelper.isCompletedEngineerRecipe(item) ? " " + UTF8Constants.CHECK_TRUE : "");
                    displayText = displayText.replaceAll("^[ ├└\\s]+", "");
                    setText(displayText);
                    setGraphic(item.isInColonia() ? EdAwesomeIconViewPaneBuilder.builder().withStyleClass("colonia-icon").withIcons(EdAwesomeIcon.OTHER_COLONIA).build() : null);
                }
            }

            private void updateStyle(final HorizonsBlueprintName item) {
                if (item != null && BlueprintHelper.isCompletedEngineerRecipe(item)) {
                    this.pseudoClassStateChanged(PseudoClass.getPseudoClass("complete"), true);
                } else {
                    this.pseudoClassStateChanged(PseudoClass.getPseudoClass("complete"), false);
                }
            }
        };
    }

    public HorizonsBlueprintBar() {
        initComponents();
        initEventHandling();
    }

    public void initComponents() {
        this.getStyleClass().add("blueprint-menu");
//        this.getStyleClass().add("blueprint-bar");
        DestroyableTitledPane[] categoryTitledPanes = HorizonsBlueprintConstants.RECIPES.entrySet().stream()
                .sorted(Comparator.comparing(recipeCategoryMapEntry -> recipeCategoryMapEntry.getKey().getOrder()))
                .map(this::createBlueprintCategoryTitledPane)
                .toArray(DestroyableTitledPane[]::new);

        final DestroyableTitledPane categoryTitledPaneEngineers = register(createCategoryTitledPaneEngineers(HorizonsBlueprintConstants.getEngineerUnlockRequirements()));
        final DestroyableTitledPane categoryTitledPaneExperimental = register(createExperimentalEffectsCategoryTitledPane(HorizonsBlueprintConstants.getExperimentalEffects()));
        categoryTitledPaneSynthesis = register(createSynthesisCategoryTitledPane(HorizonsBlueprintConstants.getSynthesis()));
        final DestroyableTitledPane categoryTitledPaneTechBroker = register(createTechbrokerCategoryTitledPane(HorizonsBlueprintConstants.getTechbrokerUnlocks()));
        final DestroyableTitledPane aboutTitledPane = register(createAboutTitledPane());
        this.getPanes().addAll(categoryTitledPaneEngineers);
        this.getPanes().addAll(categoryTitledPanes);
        this.getPanes().addAll(categoryTitledPaneExperimental);
        this.getPanes().addAll(categoryTitledPaneSynthesis);
        this.getPanes().addAll(categoryTitledPaneTechBroker);
        this.getPanes().add(aboutTitledPane);
        this.setExpandedPane(aboutTitledPane);

    }

    @Override
    public void initEventHandling() {


        register(EventService.addListener(true, this, PerkChangedEvent.class, _ -> {
//            final DestroyableVBox content = BoxBuilder.builder()
//                    .withStyleClass(BLUEPRINT_TITLED_PANE_CONTENT_STYLE_CLASS)
//                    .withNodes(hBoxBlueprints, gradeButtons, scroll)
//                    .buildVBox();
//
//            categoryTitledPane.setContentNode(content);
//            VBox.setVgrow(scroll, Priority.ALWAYS);
            DestroyableVBox vBox = (DestroyableVBox)categoryTitledPaneSynthesis.getContent();
            DestroyableSegmentedButton gradeButtons = (DestroyableSegmentedButton)vBox.getChildren().get(1);
            gradeButtons.getButtons().stream().filter(ToggleButton::isSelected).findFirst().ifPresent(button -> {button.setSelected(false);button.setSelected(true);});
//            DestroyableHBox hBoxBlueprints = (DestroyableHBox)vBox.getChildren().get(0);
//            DestroyableScrollPane scroll = (DestroyableScrollPane)vBox.getChildren().get(2);
//            DestroyableComboBox<HorizonsBlueprintName> blueprints = (DestroyableComboBox<HorizonsBlueprintName>)hBoxBlueprints.getChildren().get(0);
//            setContent(scroll, blueprints.getSelectionModel().getSelectedItem(), HorizonsBlueprintType.SYNTHESIS, true, generateContent(HorizonsBlueprintConstants.getSynthesis().getOrDefault(blueprints.getSelectionModel().getSelectedItem(), Collections.emptyMap()).get(gradeButtons.getButtons().)));
        }));
    }

    private DestroyableTitledPane createAboutTitledPane() {
        About about = new About();
        return TitledPaneBuilder.builder()
                .withContent(about)
                .withText("menu.about")
                .build();
    }

    private DestroyableTitledPane createTechbrokerCategoryTitledPane(final Map<HorizonsBlueprintName, Map<HorizonsBlueprintType, HorizonsBlueprint>> recipesEntry) {
        final DestroyableScrollPane scroll = createScrollPane();

        final DestroyableComboBox<HorizonsBlueprintType> types = ComboBoxBuilder.builder(HorizonsBlueprintType.class)
                .withStyleClass(BLUEPRINT_LIST_STYLE_CLASS)
                .asLocalized()
                .build();
        types.setVisibleRowCount(Math.min(types.getItems().size(), 10));

        DestroyableComboBox<HorizonsBlueprintName> blueprints = createBlueprintsComboboxForTypes(types, recipesEntry.keySet(), recipesEntry.entrySet().stream().map(horizonsBlueprintNameMapEntry -> Map.entry(horizonsBlueprintNameMapEntry.getKey(), horizonsBlueprintNameMapEntry.getValue().keySet())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
        DestroyableTitledPane categoryTitledPane = createTitledPane(BlueprintCategory.TECHBROKER.getLocalizationKey());

        types.addChangeListener(types.valueProperty(), (_, _, newValue) -> {
            if (newValue != null) {
                setContent(scroll, blueprints.getSelectionModel().getSelectedItem(), types.getSelectionModel().getSelectedItem(), true,
                        generateContent(recipesEntry.getOrDefault(blueprints.getSelectionModel().getSelectedItem(), Collections.emptyMap()).get(types.getSelectionModel().getSelectedItem())));
            }
            types.setVisibleRowCount(types.getItems().size());
        });
        final DestroyableHBox hBoxBlueprints = BoxBuilder.builder()
                .withNode(blueprints).buildHBox();
        final DestroyableHBox hBoxTypes = BoxBuilder.builder()
                .withNode(types).buildHBox();
        HBox.setHgrow(blueprints, Priority.ALWAYS);
        HBox.setHgrow(types, Priority.ALWAYS);

        //auto select first option
        blueprints.getSelectionModel().select(0);
        final DestroyableVBox content = BoxBuilder.builder()
                .withStyleClass(BLUEPRINT_TITLED_PANE_CONTENT_STYLE_CLASS)
                .withNodes(hBoxBlueprints, hBoxTypes, scroll)
                .buildVBox();
        categoryTitledPane.setContentNode(content);
        VBox.setVgrow(scroll, Priority.ALWAYS);
        register(EventService.addListener(true, this, HorizonsBlueprintClickEvent.class, blueprintClickEvent -> {
            if (blueprintClickEvent.getBlueprint().getBlueprintName() instanceof HorizonsBlueprintName blueprintName
                    && BlueprintCategory.TECHBROKER.equals(blueprintName.getBlueprintCategory())
                    && HorizonsBlueprintConstants.getTechbrokerUnlocks().get(blueprintName).containsKey(((HorizonsTechBrokerBlueprint) blueprintClickEvent.getBlueprint()).getHorizonsBlueprintType())) {
                blueprints.getSelectionModel().select(blueprintName);
                types.getSelectionModel().select(getBlueprintType(blueprintClickEvent.getBlueprint()));
                this.setExpandedPane(categoryTitledPane);
            }
        }));
        return categoryTitledPane;
    }

    private HorizonsBlueprintGrade getBlueprintGrade(final Blueprint blueprint) {
        if (blueprint instanceof HorizonsBlueprint horizonsBlueprint) {
            return horizonsBlueprint.getHorizonsBlueprintGrade();
        }
        return null;
    }

    private HorizonsBlueprintType getBlueprintType(final Blueprint blueprint) {
        if (blueprint instanceof HorizonsBlueprint horizonsBlueprint) {
            return horizonsBlueprint.getHorizonsBlueprintType();
        }
        return null;
    }

    private DestroyableTitledPane createExperimentalEffectsCategoryTitledPane(final Map<HorizonsBlueprintName, Map<HorizonsBlueprintType, HorizonsBlueprint>> recipesEntry) {
        final DestroyableScrollPane scroll = createScrollPane();

        final DestroyableComboBox<HorizonsBlueprintType> types = ComboBoxBuilder.builder(HorizonsBlueprintType.class)
                .withStyleClass(BLUEPRINT_LIST_STYLE_CLASS)
                .asLocalized()
                .build();
        types.setVisibleRowCount(Math.min(types.getItems().size(), 10));
        final DestroyableComboBox<HorizonsBlueprintName> blueprints = createBlueprintsComboboxForTypes(types, recipesEntry.keySet(), recipesEntry.entrySet().stream().map(horizonsBlueprintNameMapEntry -> Map.entry(horizonsBlueprintNameMapEntry.getKey(), horizonsBlueprintNameMapEntry.getValue().keySet())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

        final DestroyableTitledPane categoryTitledPane = createTitledPane(BlueprintCategory.EXPERIMENTAL_EFFECTS.getLocalizationKey());

        types.addChangeListener(types.valueProperty(), (_, _, newValue) -> {
            if (newValue != null) {
                setContent(scroll, blueprints.getSelectionModel().getSelectedItem(), types.getSelectionModel().getSelectedItem(), true,
                        generateContent(recipesEntry.getOrDefault(blueprints.getSelectionModel().getSelectedItem(), Collections.emptyMap()).get(types.getSelectionModel().getSelectedItem())));
            }
            types.setVisibleRowCount(types.getItems().size());
        });
        final DestroyableHBox hBoxBlueprints = BoxBuilder.builder()
                .withNode(blueprints).buildHBox();
        final DestroyableHBox hBoxTypes = BoxBuilder.builder()
                .withNode(types).buildHBox();
        HBox.setHgrow(blueprints, Priority.ALWAYS);
        HBox.setHgrow(types, Priority.ALWAYS);
        register(EventService.addListener(true, this, HorizonsBlueprintClickEvent.class, blueprintClickEvent -> {
            if (blueprintClickEvent.getBlueprint().getBlueprintName() instanceof HorizonsBlueprintName blueprintName && blueprintClickEvent.isExperimental()) {
                blueprints.getSelectionModel().select(blueprintName);
                if (blueprintClickEvent.getBlueprint() instanceof HorizonsExperimentalEffectBlueprint) {
                    types.getSelectionModel().select(getBlueprintType(blueprintClickEvent.getBlueprint()));
                }
                this.setExpandedPane(categoryTitledPane);
            }
        }));

        //auto select first option
        blueprints.getSelectionModel().select(0);
        final DestroyableVBox content = BoxBuilder.builder()
                .withStyleClass(BLUEPRINT_TITLED_PANE_CONTENT_STYLE_CLASS)
                .withNodes(hBoxBlueprints, hBoxTypes, scroll)
                .buildVBox();
        categoryTitledPane.setContentNode(content);
        VBox.setVgrow(scroll, Priority.ALWAYS);
        return categoryTitledPane;
    }

    private DestroyableTitledPane createSynthesisCategoryTitledPane(final Map<HorizonsBlueprintName, Map<HorizonsBlueprintGrade, ? extends HorizonsBlueprint>> recipesEntry) {
        final DestroyableScrollPane scroll = createScrollPane();
        final List<DestroyableToggleButton> toggleButtons = new ArrayList<>();
        final DestroyableComboBox<HorizonsBlueprintName> blueprints = createBlueprintsComboboxForSynthesis(scroll, toggleButtons, recipesEntry.keySet(), HorizonsBlueprintType.SYNTHESIS, recipesEntry);

        final DestroyableTitledPane categoryTitledPane = createTitledPane(BlueprintCategory.SYNTHESIS.getLocalizationKey());

        toggleButtons.addAll(getSynthesisToggleButtons(scroll, () -> HorizonsBlueprintType.SYNTHESIS, () -> blueprints.getSelectionModel().getSelectedItem(), recipesEntry));
        final DestroyableSegmentedButton gradeButtons = createGradeSegmentedButton(toggleButtons);


        final DestroyableHBox hBoxBlueprints = BoxBuilder.builder()
                .withNode(blueprints).buildHBox();
        HBox.setHgrow(blueprints, Priority.ALWAYS);
        register(EventService.addListener(true, this, HorizonsBlueprintClickEvent.class, blueprintClickEvent -> {
            if (blueprintClickEvent.getBlueprint().getBlueprintName() instanceof HorizonsBlueprintName blueprintName && BlueprintCategory.SYNTHESIS.equals(blueprintName.getBlueprintCategory())) {
                blueprints.getSelectionModel().select(blueprintName);
                final HorizonsBlueprintGrade horizonsBlueprintGrade = ((HorizonsBlueprint) blueprintClickEvent.getBlueprint()).getHorizonsBlueprintGrade();
                switch (horizonsBlueprintGrade) {
                    case GRADE_1 -> gradeButtons.getButtons().get(0).selectedProperty().set(true);
                    case GRADE_2 -> gradeButtons.getButtons().get(1).selectedProperty().set(true);
                    case GRADE_3 -> gradeButtons.getButtons().get(2).selectedProperty().set(true);
                    default -> throw new UnsupportedOperationException();
                }

                this.setExpandedPane(categoryTitledPane);
            }
        }));

        //auto select first option
        blueprints.getSelectionModel().select(0);
        final DestroyableVBox content = BoxBuilder.builder()
                .withStyleClass(BLUEPRINT_TITLED_PANE_CONTENT_STYLE_CLASS)
                .withNodes(hBoxBlueprints, gradeButtons, scroll)
                .buildVBox();

        categoryTitledPane.setContentNode(content);
        VBox.setVgrow(scroll, Priority.ALWAYS);
        return categoryTitledPane;
    }

    private DestroyableTitledPane createBlueprintCategoryTitledPane(final Map.Entry<BlueprintCategory, Map<HorizonsBlueprintName, Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>>>> recipesEntry) {
        final DestroyableScrollPane scroll = createScrollPane();

        final DestroyableComboBox<HorizonsBlueprintType> types = ComboBoxBuilder.builder(HorizonsBlueprintType.class)
                .withStyleClass(BLUEPRINT_LIST_STYLE_CLASS)
                .asLocalized()
                .build();
        final DestroyableComboBox<HorizonsBlueprintName> blueprints = createBlueprintsComboboxForTypes(
                types,
                recipesEntry.getValue().entrySet().stream().filter(entry -> entry.getValue().values().stream().anyMatch(map -> map.values().stream().anyMatch(bp -> !bp.isPreEngineered()))).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).keySet(),
                recipesEntry.getValue().entrySet().stream()
                        .map(horizonsBlueprintNameMapEntry -> Map.entry(horizonsBlueprintNameMapEntry.getKey(), horizonsBlueprintNameMapEntry.getValue().entrySet().stream().filter(entry -> entry.getValue().entrySet().stream().anyMatch(horizonsBlueprintGradeHorizonsBlueprintEntry -> !horizonsBlueprintGradeHorizonsBlueprintEntry.getValue().isPreEngineered())).map(horizonsBlueprintTypeMapEntry -> horizonsBlueprintTypeMapEntry.getKey()).collect(Collectors.toSet())))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
        );

        final DestroyableTitledPane categoryTitledPane = createTitledPane(recipesEntry.getKey().getLocalizationKey());
        final List<DestroyableToggleButton> toggleButtons = getGradeToggleButtons(scroll, () -> types.getSelectionModel().getSelectedItem(), () -> blueprints.getSelectionModel().getSelectedItem(), recipesEntry.getValue());
        final DestroyableSegmentedButton gradeButtons = createGradeSegmentedButton(toggleButtons);

        types.addChangeListener(types.valueProperty(), (_, _, newValue) -> {
            if (newValue != null) {
                final Set<HorizonsBlueprintGrade> blueprintGrades = HorizonsBlueprintConstants.getBlueprintGrades(blueprints.getSelectionModel().getSelectedItem(), newValue);
                configureToggleButtonsState(toggleButtons, blueprints.getSelectionModel().getSelectedItem(), blueprintGrades);
                types.setVisibleRowCount(types.getItems().size());
                if (!blueprintGrades.isEmpty()) {
                    setContent(scroll, blueprints.getSelectionModel().getSelectedItem(), newValue, true, generateContent(recipesEntry.getValue().getOrDefault(blueprints.getSelectionModel().getSelectedItem(), Collections.emptyMap()).getOrDefault(newValue, Collections.emptyMap()).get(HorizonsBlueprintGrade.GRADE_1)));
                }
            }
        });
        register(EventService.addListener(true, this, HorizonsBlueprintClickEvent.class, blueprintClickEvent -> {
            if (blueprintClickEvent.getBlueprint().getBlueprintName() instanceof HorizonsBlueprintName blueprintName && sameCategory(recipesEntry.getKey(), blueprintName.getBlueprintCategory()) && !blueprintClickEvent.isExperimental()) {
                blueprints.getSelectionModel().select(blueprintName);
                if (blueprintClickEvent.getBlueprint() instanceof HorizonsModuleBlueprint) {
                    types.getSelectionModel().select(getBlueprintType(blueprintClickEvent.getBlueprint()));
                    final HorizonsBlueprintGrade horizonsBlueprintGrade = ((HorizonsBlueprint) blueprintClickEvent.getBlueprint()).getHorizonsBlueprintGrade();
                    switch (horizonsBlueprintGrade) {
                        case GRADE_1 -> gradeButtons.getButtons().get(0).selectedProperty().set(true);
                        case GRADE_2 -> gradeButtons.getButtons().get(1).selectedProperty().set(true);
                        case GRADE_3 -> gradeButtons.getButtons().get(2).selectedProperty().set(true);
                        case GRADE_4 -> gradeButtons.getButtons().get(3).selectedProperty().set(true);
                        case GRADE_5 -> gradeButtons.getButtons().get(4).selectedProperty().set(true);
                        default -> throw new UnsupportedOperationException();
                    }
                }
                this.setExpandedPane(categoryTitledPane);
            }
        }));
        final DestroyableHBox hBoxBlueprints = BoxBuilder.builder()
                .withNode(blueprints).buildHBox();
        final DestroyableHBox hBoxTypes = BoxBuilder.builder()
                .withNode(types).buildHBox();
        HBox.setHgrow(blueprints, Priority.ALWAYS);
        HBox.setHgrow(types, Priority.ALWAYS);


        //auto select first option
        blueprints.getSelectionModel().select(0);
        final DestroyableVBox content = BoxBuilder.builder()
                .withStyleClass(BLUEPRINT_TITLED_PANE_CONTENT_STYLE_CLASS)
                .withNodes(hBoxBlueprints, hBoxTypes, gradeButtons, scroll)
                .buildVBox();
        categoryTitledPane.setContentNode(content);
        VBox.setVgrow(scroll, Priority.ALWAYS);
        return categoryTitledPane;
    }

    private boolean sameCategory(BlueprintCategory menuCategory, BlueprintCategory blueprintCategory) {
        if (BlueprintCategory.OPTIONAL_INTERNAL.equals(menuCategory) && (BlueprintCategory.OPTIONAL_INTERNAL.equals(blueprintCategory) || BlueprintCategory.OPTIONAL_MILITARY.equals(blueprintCategory))) {
            return true;
        }
        return menuCategory.equals(blueprintCategory);
    }

    private DestroyableSegmentedButton createGradeSegmentedButton(final List<DestroyableToggleButton> toggleButtons) {
        final DestroyableSegmentedButton segmentedButton = SegmentedButtonBuilder.builder()
                .withStyleClass(BLUEPRINT_LIST_STYLE_CLASS)
                .withButtons(toggleButtons)
                .build();
        HBox.setHgrow(segmentedButton, Priority.ALWAYS);
        return segmentedButton;
    }

    private DestroyableTitledPane createTitledPane(final String localizationKey) {
        return register(TitledPaneBuilder.builder()
                .withStyleClass("category-title-pane")
                .withText(localizationKey)
                .build());
    }

    private DestroyableComboBox<HorizonsBlueprintName> createBlueprintsComboboxForSynthesis(final DestroyableScrollPane scroll, final List<DestroyableToggleButton> toggleButtons, final Set<HorizonsBlueprintName> horizonsBlueprintNames, final HorizonsBlueprintType type, final Map<HorizonsBlueprintName, Map<HorizonsBlueprintGrade, ? extends HorizonsBlueprint>> recipeEntry) {
        final DestroyableComboBox<HorizonsBlueprintName> blueprints = ComboBoxBuilder.builder(HorizonsBlueprintName.class)
                .withStyleClass(BLUEPRINT_LIST_STYLE_CLASS)
                .withItemsProperty(LocaleService.getListBinding(horizonsBlueprintNames.stream().sorted(Comparator.comparing(recipeName -> LocaleService.getLocalizedStringForCurrentLocale(recipeName.getLocalizationKey()))).toArray(HorizonsBlueprintName[]::new)))
                .withValueChangeListener((_, _, newValue) -> {
                    if (newValue != null) {
                        final Set<HorizonsBlueprintGrade> blueprintGrades = HorizonsBlueprintConstants.getSynthesisBlueprintGrades(newValue);
                        configureToggleButtonsState(toggleButtons, newValue, blueprintGrades);
                        setContent(scroll, newValue, type, true, generateContent(recipeEntry.getOrDefault(newValue, Collections.emptyMap()).get(HorizonsBlueprintGrade.GRADE_1)));
                    }
                })
                .asLocalized()
                .build();
        blueprints.setVisibleRowCount(Math.min(blueprints.getItems().size(), 10));
        return blueprints;
    }

    private void configureToggleButtonsState(final List<DestroyableToggleButton> toggleButtons, final HorizonsBlueprintName newValue, final Set<HorizonsBlueprintGrade> blueprintGrades) {
        toggleButtons.forEach(toggleButton -> toggleButton.setDisable(true));
        blueprintGrades.forEach(blueprintGrade -> {
            switch (blueprintGrade) {
                case GRADE_1 -> toggleButtons.get(0).setDisable(false);
                case GRADE_2 -> toggleButtons.get(1).setDisable(false);
                case GRADE_3 -> toggleButtons.get(2).setDisable(false);
                case GRADE_4 -> toggleButtons.get(3).setDisable(false);
                case GRADE_5 -> toggleButtons.get(4).setDisable(false);
                default -> throw new IllegalArgumentException("Unsupported Grade");
            }
        });
        if (newValue != null) {
            toggleButtons.get(0).setSelected(true);
        }
    }

    private DestroyableComboBox<HorizonsBlueprintName> createBlueprintsComboboxForTypes(final DestroyableComboBox<HorizonsBlueprintType> types, final Set<HorizonsBlueprintName> horizonsBlueprintNames, final Map<HorizonsBlueprintName, Set<HorizonsBlueprintType>> typesMap) {
        final DestroyableComboBox<HorizonsBlueprintName> blueprints = ComboBoxBuilder.builder(HorizonsBlueprintName.class)
                .withStyleClass(BLUEPRINT_LIST_STYLE_CLASS)
                .withItemsProperty(LocaleService.getListBinding(horizonsBlueprintNames.stream().sorted(Comparator.comparing(recipeName -> LocaleService.getLocalizedStringForCurrentLocale(recipeName.getLocalizationKey()))).toArray(HorizonsBlueprintName[]::new)))
                .withValueChangeListener((_, _, newValue) -> {
                    if (newValue != null) {
                        types.addBinding(types.itemsProperty(), LocaleService.getListBinding(typesMap.get(newValue).stream().filter(Predicate.not(HorizonsBlueprintType::isPreEngineered)).sorted(Comparator.comparing(type -> LocaleService.getLocalizedStringForCurrentLocale(type.getLocalizationKey()))).toArray(HorizonsBlueprintType[]::new)));
                        types.getSelectionModel().select(0);
                    }
                })
                .asLocalized()
                .build();
        blueprints.setVisibleRowCount(Math.min(blueprints.getItems().size(), 10));
        return blueprints;
    }

    private DestroyableScrollPane createScrollPane() {
        final DestroyableScrollPane scroll = ScrollPaneBuilder.builder().build();
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setPannable(true);
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);
        return scroll;
    }

    private List<DestroyableToggleButton> getGradeToggleButtons(final DestroyableScrollPane scroll, final Supplier<HorizonsBlueprintType> selectedType, final Supplier<HorizonsBlueprintName> selectedBlueprint, final Map<HorizonsBlueprintName, Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>>> recipeEntry) {
        return Stream.of(HorizonsBlueprintGrade.GRADE_1, HorizonsBlueprintGrade.GRADE_2, HorizonsBlueprintGrade.GRADE_3, HorizonsBlueprintGrade.GRADE_4, HorizonsBlueprintGrade.GRADE_5)
                .map(grade -> getToggleButton(scroll, selectedType, selectedBlueprint, grade, recipeEntry))
                .toList();
    }

    private List<DestroyableToggleButton> getSynthesisToggleButtons(final DestroyableScrollPane scroll, final Supplier<HorizonsBlueprintType> selectedType, final Supplier<HorizonsBlueprintName> selectedBlueprint, final Map<HorizonsBlueprintName, Map<HorizonsBlueprintGrade, ? extends HorizonsBlueprint>> recipesEntry) {
        return Stream.of(HorizonsBlueprintGrade.GRADE_1, HorizonsBlueprintGrade.GRADE_2, HorizonsBlueprintGrade.GRADE_3)
                .map(grade -> getSynthesisToggleButton(scroll, selectedType, selectedBlueprint, grade, recipesEntry))
                .toList();
    }

    private DestroyableToggleButton getToggleButton(final DestroyableScrollPane scroll, final Supplier<HorizonsBlueprintType> selectedType, final Supplier<HorizonsBlueprintName> selectedBlueprint, final HorizonsBlueprintGrade grade, final Map<HorizonsBlueprintName, Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>>> recipeEntry) {
        final DestroyableToggleButton toggleButton = ToggleButtonBuilder.builder()
                .withStyleClass("blueprint-grade-togglebutton")
                .withGraphic(ResizableImageViewBuilder.builder()
                        .withImage("/images/ships/engineers/ranks/" + grade.getGrade() + ".png")
                        .withStyleClasses("blueprint-grade-image")
                        .build())
                .build();
        toggleButton.addChangeListener(toggleButton.selectedProperty(), (_, _, newValue) ->
        {
            if (Boolean.TRUE.equals(newValue)) {
                setContent(scroll, selectedBlueprint.get(), selectedType.get(), newValue, generateContent(recipeEntry.getOrDefault(selectedBlueprint.get(), Collections.emptyMap()).getOrDefault(selectedType.get(), Collections.emptyMap()).get(grade)));
            }
        });
        return toggleButton;
    }

    private DestroyableToggleButton getSynthesisToggleButton(final DestroyableScrollPane scroll, final Supplier<HorizonsBlueprintType> selectedType, final Supplier<HorizonsBlueprintName> selectedBlueprint, final HorizonsBlueprintGrade grade, final Map<HorizonsBlueprintName, Map<HorizonsBlueprintGrade, ? extends HorizonsBlueprint>> recipesEntry) {
        final DestroyableToggleButton toggleButton = ToggleButtonBuilder.builder()
                .withText("blueprint.synthesis.grade" + grade.getGrade())
                .withStyleClass("blueprint-synthesis-togglebutton")
                .build();
        toggleButton.addChangeListener(toggleButton.selectedProperty(), (_, _, newValue) ->
        {
            if (Boolean.TRUE.equals(newValue)) {
                setContent(scroll, selectedBlueprint.get(), selectedType.get(), newValue, generateContent(recipesEntry.getOrDefault(selectedBlueprint.get(), Collections.emptyMap()).get(grade)));
            }
        });
        return toggleButton;
    }

    private <E extends Node & Destroyable> E generateContent(final HorizonsBlueprint horizonsBlueprint) {
        if (horizonsBlueprint != null) {
            return (E) new HorizonsBlueprintContent(horizonsBlueprint);
        }
        return null;
    }

    private <E extends Node & Destroyable> void setContent(final DestroyableScrollPane scroll, final HorizonsBlueprintName name, final HorizonsBlueprintType type, final Boolean isSelected, final E content) {
        if (Objects.equals(true, isSelected) && type != null && name != null) {
            try {
                if (scroll.getContent() instanceof DestroyableTemplate destroyableContent) {
                    scroll.deregister(destroyableContent);
                    destroyableContent.destroyTemplate();
                }
                scroll.setContentNode(content);
            } catch (final NullPointerException ex) {
                log.error("NPE", ex);
            }
        } else {
            if (content instanceof DestroyableTemplate destroyableContent) {
                destroyableContent.destroyTemplate();
            }
        }
    }

    private DestroyableTitledPane createCategoryTitledPaneEngineers(final Map<HorizonsBlueprintName, ? extends HorizonsEngineerBlueprint> recipesEngineers) {
        final DestroyableScrollPane scroll = createScrollPane();

        final DestroyableComboBox<HorizonsBlueprintName> blueprints = ComboBoxBuilder.builder(HorizonsBlueprintName.class)
                .withStyleClass(BLUEPRINT_LIST_STYLE_CLASS)
                .withItemsProperty(LocaleService.getListBinding(recipesEngineers.keySet().stream().sorted(Comparator.comparing(HorizonsBlueprintName::getLocalizationKey)).toArray(HorizonsBlueprintName[]::new)))
                .asLocalized()
                .build();
        blueprints.setVisibleRowCount(Math.min(blueprints.getItems().size(), 10));

        final DestroyableHBox hBox = BoxBuilder.builder()
                .withNode(blueprints).buildHBox();
        HBox.setHgrow(blueprints, Priority.ALWAYS);

        final DestroyableVBox content = BoxBuilder.builder()
                .withStyleClass(BLUEPRINT_TITLED_PANE_CONTENT_STYLE_CLASS)
                .withNodes(hBox, scroll)
                .buildVBox();
        VBox.setVgrow(scroll, Priority.ALWAYS);

        final DestroyableTitledPane categoryTitledPane = TitledPaneBuilder.builder()
                .withStyleClass("category-title-pane")
                .withText(BlueprintCategory.ENGINEER_UNLOCKS.getLocalizationKey())
                .withContent(content)
                .build();

        blueprints.addChangeListener(blueprints.valueProperty(), (_, _, newValue) ->
                setContent(scroll, newValue, HorizonsBlueprintType.ENGINEER, true, generateContent(recipesEngineers.get(newValue))));
        register(EventService.addListener(true, this, HorizonsBlueprintClickEvent.class, blueprintClickEvent -> {
            if (blueprintClickEvent.getBlueprint().getBlueprintName() instanceof HorizonsBlueprintName blueprintName && BlueprintCategory.ENGINEER_UNLOCKS.equals(blueprintName.getBlueprintCategory())) {
                blueprints.getSelectionModel().select(blueprintName);
                this.setExpandedPane(categoryTitledPane);
            }
        }));
        register(EventService.addListener(true, this, BlueprintClickEvent.class, blueprintClickEvent -> {
            if (blueprintClickEvent.getBlueprintName() instanceof HorizonsBlueprintName blueprintName && BlueprintCategory.ENGINEER_UNLOCKS.equals(blueprintName.getBlueprintCategory())) {
                blueprints.getSelectionModel().select(blueprintName);
                this.setExpandedPane(categoryTitledPane);
            }
        }));
        var destroyableCellFactory = createDestroyableCellFactory(blueprints);
        blueprints.setCellFactory(destroyableCellFactory);
        blueprints.getSelectionModel().select(blueprints.getItems().getFirst());
        blueprints.setButtonCell(createDestroyableButtonCellFactory(blueprints).call(null));
        return categoryTitledPane;
    }

    @Override
    public void destroyInternal() {
        super.destroyInternal();
    }
}
