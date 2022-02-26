package nl.jixxed.eliteodysseymaterials.templates;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsEngineerBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.BlueprintCategory;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.helper.BlueprintHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableComboBox;
import org.controlsfx.control.SegmentedButton;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
class HorizonsBlueprintBar extends Accordion {
    private About about;
    private TitledPane[] categoryTitledPanes;
    private TitledPane aboutTitledPane;
    private final Application application;

    HorizonsBlueprintBar(final Application application) {
        this.application = application;
        initComponents();

    }

    private void initComponents() {
        this.getStyleClass().add("blueprint-bar");
        this.categoryTitledPanes = HorizonsBlueprintConstants.RECIPES.entrySet().stream()
                .sorted(Comparator.comparing(recipeCategoryMapEntry -> recipeCategoryMapEntry.getKey().getOrder()))
                .map(this::createCategoryTitledPane)
                .toArray(TitledPane[]::new);

        final TitledPane categoryTitledPaneEngineers = createCategoryTitledPaneEngineers(HorizonsBlueprintConstants.getEngineerUnlockRequirements());
        final TitledPane categoryTitledPaneExperimental = createExperimentalCategoryTitledPane(HorizonsBlueprintConstants.getExperimentalEffects());
        final TitledPane categoryTitledPaneSynthesis = createSynthesisCategoryTitledPane(HorizonsBlueprintConstants.getSynthesis());
        final TitledPane categoryTitledPaneTechBroker = createTechbrokerCategoryTitledPane(HorizonsBlueprintConstants.getTechbrokerUnlocks());
        initAboutTitledPane();
        this.getPanes().addAll(categoryTitledPaneEngineers);
        this.getPanes().addAll(this.categoryTitledPanes);
        this.getPanes().addAll(categoryTitledPaneExperimental);
        this.getPanes().addAll(categoryTitledPaneSynthesis);
        this.getPanes().addAll(categoryTitledPaneTechBroker);
        this.getPanes().add(this.aboutTitledPane);
        this.setExpandedPane(this.aboutTitledPane);
    }

    private void initAboutTitledPane() {
        this.about = new About(this.application);
        this.aboutTitledPane = TitledPaneBuilder.builder().withContent(this.about).withText(LocaleService.getStringBinding("menu.about")).build();
    }

    private TitledPane createTechbrokerCategoryTitledPane(final Map<HorizonsBlueprintName, Map<HorizonsBlueprintType, HorizonsBlueprint>> recipesEntry) {
        final ScrollPane scroll = createScrollPane();

        final DestroyableComboBox<HorizonsBlueprintType> types = ComboBoxBuilder.builder(HorizonsBlueprintType.class)
                .withStyleClass("blueprint-list")
                .asLocalized()
                .build();
        final DestroyableComboBox<HorizonsBlueprintName> blueprints = createBlueprintsComboboxForTypes(types, recipesEntry.keySet(), recipesEntry.entrySet().stream().map(horizonsBlueprintNameMapEntry -> Map.entry(horizonsBlueprintNameMapEntry.getKey(), horizonsBlueprintNameMapEntry.getValue().keySet())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

        final TitledPane categoryTitledPane = createTitledPane(BlueprintCategory.TECHBROKER.getLocalizationKey());
        final Map<HorizonsBlueprintName, Map<HorizonsBlueprintType, Node>> recipeContent = createRecipeContentNoGrades(blueprints, categoryTitledPane, recipesEntry);

        types.addChangeListener(types.valueProperty(), (ChangeListener<HorizonsBlueprintType>) (observable, oldValue, newValue) -> {
            if (newValue != null) {
                setContent(scroll, blueprints.getSelectionModel().getSelectedItem(), types.getSelectionModel().getSelectedItem(), true,
                        recipeContent.getOrDefault(blueprints.getSelectionModel().getSelectedItem(), Collections.emptyMap()).get(types.getSelectionModel().getSelectedItem()));
            }
            types.setVisibleRowCount(types.getItems().size());
        });
        final HBox hBoxBlueprints = BoxBuilder.builder().withNode(blueprints).buildHBox();
        final HBox hBoxTypes = BoxBuilder.builder().withNode(types).buildHBox();
        HBox.setHgrow(blueprints, Priority.ALWAYS);
        HBox.setHgrow(types, Priority.ALWAYS);


        //auto select first option
        blueprints.getSelectionModel().select(0);
        final VBox content = BoxBuilder.builder()
                .withStyleClass("blueprint-titled-pane-content")
                .withNodes(hBoxBlueprints, hBoxTypes, scroll)
                .buildVBox();
        categoryTitledPane.setContent(content);
        VBox.setVgrow(scroll, Priority.ALWAYS);
        return categoryTitledPane;
    }

    private TitledPane createExperimentalCategoryTitledPane(final Map<HorizonsBlueprintName, Map<HorizonsBlueprintType, HorizonsBlueprint>> recipesEntry) {
        final ScrollPane scroll = createScrollPane();

        final DestroyableComboBox<HorizonsBlueprintType> types = ComboBoxBuilder.builder(HorizonsBlueprintType.class)
                .withStyleClass("blueprint-list")
                .asLocalized()
                .build();
        final DestroyableComboBox<HorizonsBlueprintName> blueprints = createBlueprintsComboboxForTypes(types, recipesEntry.keySet(), recipesEntry.entrySet().stream().map(horizonsBlueprintNameMapEntry -> Map.entry(horizonsBlueprintNameMapEntry.getKey(), horizonsBlueprintNameMapEntry.getValue().keySet())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

        final TitledPane categoryTitledPane = createTitledPane(BlueprintCategory.EXPERIMENTAL_EFFECTS.getLocalizationKey());
        final Map<HorizonsBlueprintName, Map<HorizonsBlueprintType, Node>> recipeContent = createRecipeContentNoGrades(blueprints, categoryTitledPane, recipesEntry);

        types.addChangeListener(types.valueProperty(), (ChangeListener<HorizonsBlueprintType>) (observable, oldValue, newValue) -> {
            if (newValue != null) {
                setContent(scroll, blueprints.getSelectionModel().getSelectedItem(), types.getSelectionModel().getSelectedItem(), true,
                        recipeContent.getOrDefault(blueprints.getSelectionModel().getSelectedItem(), Collections.emptyMap()).get(types.getSelectionModel().getSelectedItem()));
            }
            types.setVisibleRowCount(types.getItems().size());
        });
        final HBox hBoxBlueprints = BoxBuilder.builder().withNode(blueprints).buildHBox();
        final HBox hBoxTypes = BoxBuilder.builder().withNode(types).buildHBox();
        HBox.setHgrow(blueprints, Priority.ALWAYS);
        HBox.setHgrow(types, Priority.ALWAYS);


        //auto select first option
        blueprints.getSelectionModel().select(0);
        final VBox content = BoxBuilder.builder()
                .withStyleClass("blueprint-titled-pane-content")
                .withNodes(hBoxBlueprints, hBoxTypes, scroll)
                .buildVBox();
        categoryTitledPane.setContent(content);
        VBox.setVgrow(scroll, Priority.ALWAYS);
        return categoryTitledPane;
    }

    private TitledPane createSynthesisCategoryTitledPane(final Map<HorizonsBlueprintName, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> recipesEntry) {
        final ScrollPane scroll = createScrollPane();
        final List<ToggleButton> toggleButtons = new ArrayList<>();
        final Map<HorizonsBlueprintName, Map<HorizonsBlueprintGrade, Node>> recipeContent = new EnumMap<>(HorizonsBlueprintName.class);
        final DestroyableComboBox<HorizonsBlueprintName> blueprints = createBlueprintsComboboxForSynthesis(scroll, toggleButtons, recipesEntry.keySet(), HorizonsBlueprintType.SYNTHESIS, () -> recipeContent);

        final TitledPane categoryTitledPane = createTitledPane(BlueprintCategory.SYNTHESIS.getLocalizationKey());
        recipeContent.putAll(createRecipeContentNoTypes(blueprints, categoryTitledPane, recipesEntry));

        toggleButtons.addAll(getSynthesisToggleButtons(scroll, () -> HorizonsBlueprintType.SYNTHESIS, () -> blueprints.getSelectionModel().getSelectedItem(), () -> recipeContent.getOrDefault(blueprints.getSelectionModel().getSelectedItem(), recipeContent.getOrDefault(blueprints.getSelectionModel().getSelectedItem(), Collections.emptyMap()))));
        final SegmentedButton gradeButtons = createGradeSegmentedButton(toggleButtons);


        final HBox hBoxBlueprints = BoxBuilder.builder().withNode(blueprints).buildHBox();
        HBox.setHgrow(blueprints, Priority.ALWAYS);


        //auto select first option
        blueprints.getSelectionModel().select(0);
        final VBox content = BoxBuilder.builder()
                .withStyleClass("blueprint-titled-pane-content")
                .withNodes(hBoxBlueprints, gradeButtons, scroll)
                .buildVBox();
        categoryTitledPane.setContent(content);
        VBox.setVgrow(scroll, Priority.ALWAYS);
        return categoryTitledPane;
    }

    private TitledPane createCategoryTitledPane(final Map.Entry<BlueprintCategory, Map<HorizonsBlueprintName, Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>>>> recipesEntry) {
        final ScrollPane scroll = createScrollPane();

        final DestroyableComboBox<HorizonsBlueprintType> types = ComboBoxBuilder.builder(HorizonsBlueprintType.class)
                .withStyleClass("blueprint-list")
                .asLocalized()
                .build();
        final DestroyableComboBox<HorizonsBlueprintName> blueprints = createBlueprintsComboboxForTypes(types, recipesEntry.getValue().keySet(), recipesEntry.getValue().entrySet().stream().map(horizonsBlueprintNameMapEntry -> Map.entry(horizonsBlueprintNameMapEntry.getKey(), horizonsBlueprintNameMapEntry.getValue().keySet())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

        final TitledPane categoryTitledPane = createTitledPane(recipesEntry.getKey().getLocalizationKey());
        final Map<HorizonsBlueprintName, Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, Node>>> recipeContent = createRecipeContent(blueprints, categoryTitledPane, recipesEntry.getValue());

        final Supplier<Map<HorizonsBlueprintGrade, Node>> gradeContentMap = () -> recipeContent.getOrDefault(blueprints.getSelectionModel().getSelectedItem(),
                Collections.emptyMap()).getOrDefault(types.getSelectionModel().getSelectedItem(), Collections.emptyMap());
        final List<ToggleButton> toggleButtons = getGradeToggleButtons(scroll, () -> types.getSelectionModel().getSelectedItem(), () -> blueprints.getSelectionModel().getSelectedItem(), gradeContentMap);
        final SegmentedButton gradeButtons = createGradeSegmentedButton(toggleButtons);

        types.addChangeListener(types.valueProperty(), (ChangeListener<HorizonsBlueprintType>) (observable, oldValue, newValue) -> {
            if (newValue != null) {
                final Set<HorizonsBlueprintGrade> blueprintGrades = HorizonsBlueprintConstants.getBlueprintGrades(blueprints.getSelectionModel().getSelectedItem(), newValue);
                configureToggleButtonsState(toggleButtons, blueprints.getSelectionModel().getSelectedItem(), blueprintGrades);
                types.setVisibleRowCount(types.getItems().size());
                if (!blueprintGrades.isEmpty()) {
                    setContent(scroll, blueprints.getSelectionModel().getSelectedItem(), newValue, true, recipeContent.getOrDefault(blueprints.getSelectionModel().getSelectedItem(), Collections.emptyMap()).getOrDefault(newValue, Collections.emptyMap()).get(HorizonsBlueprintGrade.GRADE_1));
                }
            }
        });
        final HBox hBoxBlueprints = BoxBuilder.builder().withNode(blueprints).buildHBox();
        final HBox hBoxTypes = BoxBuilder.builder().withNode(types).buildHBox();
        HBox.setHgrow(blueprints, Priority.ALWAYS);
        HBox.setHgrow(types, Priority.ALWAYS);


        //auto select first option
        blueprints.getSelectionModel().select(0);
        final VBox content = BoxBuilder.builder()
                .withStyleClass("blueprint-titled-pane-content")
                .withNodes(hBoxBlueprints, hBoxTypes, gradeButtons, scroll)
                .buildVBox();
        categoryTitledPane.setContent(content);
        VBox.setVgrow(scroll, Priority.ALWAYS);
        return categoryTitledPane;
    }

    private SegmentedButton createGradeSegmentedButton(final List<ToggleButton> toggleButtons) {
        final SegmentedButton segmentedButton = new SegmentedButton(toggleButtons.toArray(ToggleButton[]::new));
        segmentedButton.getStyleClass().add("blueprint-list");
        HBox.setHgrow(segmentedButton, Priority.ALWAYS);
        return segmentedButton;
    }

    private TitledPane createTitledPane(final String localizationKey) {
        final TitledPane categoryTitledPane = TitledPaneBuilder.builder()
                .withStyleClass("category-title-pane")
                .withText(LocaleService.getStringBinding(localizationKey))
                .build();
        return categoryTitledPane;
    }

    private DestroyableComboBox<HorizonsBlueprintName> createBlueprintsComboboxForSynthesis(final ScrollPane scroll, final List<ToggleButton> toggleButtons, final Set<HorizonsBlueprintName> horizonsBlueprintNames, final HorizonsBlueprintType type, final Supplier<Map<HorizonsBlueprintName, Map<HorizonsBlueprintGrade, Node>>> recipeContent) {
        final DestroyableComboBox<HorizonsBlueprintName> blueprints = ComboBoxBuilder.builder(HorizonsBlueprintName.class)
                .withStyleClass("blueprint-list")
                .withItemsProperty(LocaleService.getListBinding(horizonsBlueprintNames.stream().sorted(Comparator.comparing(recipeName -> LocaleService.getLocalizedStringForCurrentLocale(recipeName.getLocalizationKey()))).toArray(HorizonsBlueprintName[]::new)))
                .withValueChangeListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        final Set<HorizonsBlueprintGrade> blueprintGrades = HorizonsBlueprintConstants.getSynthesisBlueprintGrades(newValue);
                        configureToggleButtonsState(toggleButtons, newValue, blueprintGrades);
                        setContent(scroll, newValue, type, true, recipeContent.get().getOrDefault(newValue, Collections.emptyMap()).get(HorizonsBlueprintGrade.GRADE_1));
                    }
                })
                .asLocalized()
                .build();
        blueprints.setVisibleRowCount(blueprints.getItems().size());
        return blueprints;
    }

    private void configureToggleButtonsState(final List<ToggleButton> toggleButtons, final HorizonsBlueprintName newValue, final Set<HorizonsBlueprintGrade> blueprintGrades) {
        toggleButtons.forEach(toggleButton -> toggleButton.setDisable(true));
        blueprintGrades.forEach(blueprintGrade -> {
            switch (blueprintGrade) {
                case GRADE_1 -> toggleButtons.get(0).setDisable(false);
                case GRADE_2 -> toggleButtons.get(1).setDisable(false);
                case GRADE_3 -> toggleButtons.get(2).setDisable(false);
                case GRADE_4 -> toggleButtons.get(3).setDisable(false);
                case GRADE_5 -> toggleButtons.get(4).setDisable(false);
            }
        });
        if (newValue != null) {
            toggleButtons.get(0).setSelected(true);
        }
    }

    private DestroyableComboBox<HorizonsBlueprintName> createBlueprintsComboboxForTypes(final DestroyableComboBox<HorizonsBlueprintType> types, final Set<HorizonsBlueprintName> horizonsBlueprintNames, final Map<HorizonsBlueprintName, Set<HorizonsBlueprintType>> typesMap) {
        final DestroyableComboBox<HorizonsBlueprintName> blueprints = ComboBoxBuilder.builder(HorizonsBlueprintName.class)
                .withStyleClass("blueprint-list")
                .withItemsProperty(LocaleService.getListBinding(horizonsBlueprintNames.stream().sorted(Comparator.comparing(recipeName -> LocaleService.getLocalizedStringForCurrentLocale(recipeName.getLocalizationKey()))).toArray(HorizonsBlueprintName[]::new)))
                .withValueChangeListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        types.itemsProperty().bind(LocaleService.getListBinding(typesMap.get(newValue).stream().sorted(Comparator.comparing(type -> LocaleService.getLocalizedStringForCurrentLocale(type.getLocalizationKey()))).toArray(HorizonsBlueprintType[]::new)));
                        types.getSelectionModel().select(0);
                    }
                })
                .asLocalized()
                .build();
        blueprints.setVisibleRowCount(blueprints.getItems().size());
        return blueprints;
    }

    private ScrollPane createScrollPane() {
        final ScrollPane scroll = new ScrollPane();
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setPannable(true);
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);
        return scroll;
    }

    private List<ToggleButton> getGradeToggleButtons(final ScrollPane scroll, final Supplier<HorizonsBlueprintType> selectedType, final Supplier<HorizonsBlueprintName> selectedBlueprint, final Supplier<Map<HorizonsBlueprintGrade, Node>> gradeContentMap) {
        final ToggleButton toggleButton1 = getToggleButton(scroll, selectedType, selectedBlueprint, HorizonsBlueprintGrade.GRADE_1, gradeContentMap);
        final ToggleButton toggleButton2 = getToggleButton(scroll, selectedType, selectedBlueprint, HorizonsBlueprintGrade.GRADE_2, gradeContentMap);
        final ToggleButton toggleButton3 = getToggleButton(scroll, selectedType, selectedBlueprint, HorizonsBlueprintGrade.GRADE_3, gradeContentMap);
        final ToggleButton toggleButton4 = getToggleButton(scroll, selectedType, selectedBlueprint, HorizonsBlueprintGrade.GRADE_4, gradeContentMap);
        final ToggleButton toggleButton5 = getToggleButton(scroll, selectedType, selectedBlueprint, HorizonsBlueprintGrade.GRADE_5, gradeContentMap);
        final List<ToggleButton> toggleButtons = List.of(toggleButton1, toggleButton2, toggleButton3, toggleButton4, toggleButton5);
        return toggleButtons;
    }

    private List<ToggleButton> getSynthesisToggleButtons(final ScrollPane scroll, final Supplier<HorizonsBlueprintType> selectedType, final Supplier<HorizonsBlueprintName> selectedBlueprint, final Supplier<Map<HorizonsBlueprintGrade, Node>> gradeContentMap) {
        final ToggleButton toggleButton1 = getSynthesisToggleButton(scroll, selectedType, selectedBlueprint, HorizonsBlueprintGrade.GRADE_1, gradeContentMap);
        final ToggleButton toggleButton2 = getSynthesisToggleButton(scroll, selectedType, selectedBlueprint, HorizonsBlueprintGrade.GRADE_2, gradeContentMap);
        final ToggleButton toggleButton3 = getSynthesisToggleButton(scroll, selectedType, selectedBlueprint, HorizonsBlueprintGrade.GRADE_3, gradeContentMap);
        final List<ToggleButton> toggleButtons = List.of(toggleButton1, toggleButton2, toggleButton3);
        return toggleButtons;
    }

    private ToggleButton getToggleButton(final ScrollPane scroll, final Supplier<HorizonsBlueprintType> selectedType, final Supplier<HorizonsBlueprintName> selectedBlueprint, final HorizonsBlueprintGrade grade, final Supplier<Map<HorizonsBlueprintGrade, Node>> gradeContentMap) {
        final ToggleButton toggleButton = ToggleButtonBuilder.builder().withStyleClass("recipe-grade-togglebutton").build();
        toggleButton.setGraphic(ResizableImageViewBuilder.builder().withImage("/images/ships/engineers/ranks/" + grade.getGrade() + ".png").withStyleClasses("blueprint-grade-image").build());
        toggleButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            setContent(scroll, selectedBlueprint.get(), selectedType.get(), newValue, gradeContentMap.get().get(grade));
        });
        return toggleButton;
    }

    private ToggleButton getSynthesisToggleButton(final ScrollPane scroll, final Supplier<HorizonsBlueprintType> selectedType, final Supplier<HorizonsBlueprintName> selectedBlueprint, final HorizonsBlueprintGrade grade, final Supplier<Map<HorizonsBlueprintGrade, Node>> gradeContentMap) {
        final ToggleButton toggleButton = ToggleButtonBuilder.builder().withText(LocaleService.getStringBinding("blueprint.synthesis.grade" + grade.getGrade())).withStyleClass("recipe-synthesis-togglebutton").build();
//        toggleButton.setGraphic(ResizableImageViewBuilder.builder().withImage("/images/ships/engineers/ranks/rank_" + grade.getGrade() + ".png").withStyleClasses("blueprint-grade-image").build());
        toggleButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            setContent(scroll, selectedBlueprint.get(), selectedType.get(), newValue, gradeContentMap.get().get(grade));
        });
        return toggleButton;
    }

    private void setContent(final ScrollPane scroll, final HorizonsBlueprintName name, final HorizonsBlueprintType type, final Boolean isSelected, final Node content) {
        if (Objects.equals(true, isSelected) && type != null && name != null) {
            try {
                scroll.setContent(content);
            } catch (final NullPointerException ex) {
                log.error("NPE", ex);
            }
        }
    }

    private Map<HorizonsBlueprintName, Map<HorizonsBlueprintType, Node>> createRecipeContentNoGrades(final ComboBox<HorizonsBlueprintName> comboBox, final TitledPane categoryTitledPane, final Map<HorizonsBlueprintName, Map<HorizonsBlueprintType, HorizonsBlueprint>> value) {
        final Map<HorizonsBlueprintName, Map<HorizonsBlueprintType, Node>> contents = new EnumMap<>(HorizonsBlueprintName.class);
        value.forEach((horizonsRecipeObjectName, horizonsRecipeModificationTypeMap) -> {
            EventService.addListener(this, BlueprintClickEvent.class, blueprintClickEvent -> {
                if (blueprintClickEvent.getBlueprintName().equals(horizonsRecipeObjectName) && blueprintClickEvent.isExperimental()) {
                    comboBox.getSelectionModel().select(horizonsRecipeObjectName);
                    this.setExpandedPane(categoryTitledPane);
                }
            });
            horizonsRecipeModificationTypeMap.forEach((horizonsRecipeModificationType, horizonsBlueprint) -> {
                final HorizonsBlueprintContent recipeContent = new HorizonsBlueprintContent(horizonsBlueprint);
                final Map<HorizonsBlueprintType, Node> typeMap = contents.getOrDefault(horizonsRecipeObjectName, new EnumMap<>(HorizonsBlueprintType.class));
                typeMap.put(horizonsRecipeModificationType, recipeContent);
                contents.put(horizonsRecipeObjectName, typeMap);
            });
        });
        return contents;
    }

    private Map<HorizonsBlueprintName, Map<HorizonsBlueprintGrade, Node>> createRecipeContentNoTypes(final ComboBox<HorizonsBlueprintName> comboBox, final TitledPane categoryTitledPane, final Map<HorizonsBlueprintName, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> value) {
        final Map<HorizonsBlueprintName, Map<HorizonsBlueprintGrade, Node>> contents = new EnumMap<>(HorizonsBlueprintName.class);
        value.forEach((horizonsRecipeObjectName, horizonsRecipeModificationTypeMap) -> {
            EventService.addListener(this, BlueprintClickEvent.class, blueprintClickEvent -> {
                if (blueprintClickEvent.getBlueprintName().equals(horizonsRecipeObjectName) && !blueprintClickEvent.isExperimental()) {
                    comboBox.getSelectionModel().select(horizonsRecipeObjectName);
                    this.setExpandedPane(categoryTitledPane);
                }
            });
            horizonsRecipeModificationTypeMap.forEach((horizonsBlueprintGrade, horizonsBlueprint) -> {
                final HorizonsBlueprintContent recipeContent = new HorizonsBlueprintContent(horizonsBlueprint);
                final Map<HorizonsBlueprintGrade, Node> gradeMap = contents.getOrDefault(horizonsRecipeObjectName, new EnumMap<>(HorizonsBlueprintGrade.class));
                gradeMap.put(horizonsBlueprintGrade, recipeContent);
                contents.put(horizonsRecipeObjectName, gradeMap);
            });
        });
        return contents;
    }

    private Map<HorizonsBlueprintName, Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, Node>>> createRecipeContent(final ComboBox<HorizonsBlueprintName> comboBox, final TitledPane categoryTitledPane, final Map<HorizonsBlueprintName, Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>>> value) {
        final Map<HorizonsBlueprintName, Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, Node>>> contents = new EnumMap<>(HorizonsBlueprintName.class);
        value.forEach((horizonsRecipeObjectName, horizonsRecipeModificationTypeMap) -> {
            EventService.addListener(this, BlueprintClickEvent.class, blueprintClickEvent -> {
                if (blueprintClickEvent.getBlueprintName().equals(horizonsRecipeObjectName) && !blueprintClickEvent.isExperimental()) {
                    comboBox.getSelectionModel().select(horizonsRecipeObjectName);
                    this.setExpandedPane(categoryTitledPane);
                }
            });
            horizonsRecipeModificationTypeMap.forEach((horizonsRecipeModificationType, horizonsRecipeGradeHorizonsRecipeMap) -> {
                horizonsRecipeGradeHorizonsRecipeMap.forEach((horizonsRecipeGrade, horizonsRecipe) -> {
                    final HorizonsBlueprintContent recipeContent = new HorizonsBlueprintContent(horizonsRecipe);
                    final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, Node>> typeMap = contents.getOrDefault(horizonsRecipeObjectName, new EnumMap<>(HorizonsBlueprintType.class));
                    final Map<HorizonsBlueprintGrade, Node> gradeMap = typeMap.getOrDefault(horizonsRecipeModificationType, new EnumMap<>(HorizonsBlueprintGrade.class));
                    gradeMap.put(horizonsRecipeGrade, recipeContent);
                    typeMap.put(horizonsRecipeModificationType, gradeMap);
                    contents.put(horizonsRecipeObjectName, typeMap);
                });
            });
        });
        return contents;
    }

    private TitledPane createCategoryTitledPaneEngineers(final Map<HorizonsBlueprintName, ? extends HorizonsEngineerBlueprint> recipesEngineers) {
        final ScrollPane scroll = createScrollPane();

        final ComboBox<HorizonsBlueprintName> recipes = ComboBoxBuilder.builder(HorizonsBlueprintName.class)
                .withStyleClass("blueprint-list")
                .withItemsProperty(LocaleService.getListBinding(recipesEngineers.keySet().stream().sorted(Comparator.comparing(recipeName -> LocaleService.getLocalizedStringForCurrentLocale(recipeName.getLocalizationKey()))).toArray(HorizonsBlueprintName[]::new)))
                .asLocalized()
                .build();
        recipes.setVisibleRowCount(recipes.getItems().size());

        final HBox hBox = BoxBuilder.builder().withNode(recipes).buildHBox();
        HBox.setHgrow(recipes, Priority.ALWAYS);

        final VBox content = BoxBuilder.builder()
                .withStyleClass("blueprint-titled-pane-content")
                .withNodes(hBox, scroll)
                .buildVBox();
        VBox.setVgrow(scroll, Priority.ALWAYS);

        final TitledPane categoryTitledPane = TitledPaneBuilder.builder()
                .withStyleClass("category-title-pane")
                .withText(LocaleService.getStringBinding(BlueprintCategory.ENGINEER_UNLOCKS.getLocalizationKey()))
                .withContent(content)
                .build();

        final Map<HorizonsBlueprintName, Node> recipeContent = createRecipeContentEngineers(recipesEngineers, recipes, categoryTitledPane);
        recipes.valueProperty().addListener((obs, oldValue, newValue) -> scroll.setContent(recipeContent.get(newValue)));
        recipes.setCellFactory(getCellFactory());
        recipes.getSelectionModel().select(recipes.getItems().get(0));
        recipes.setButtonCell(new ListCell<>() {
            @SuppressWarnings("java:S1068")
            private final nl.jixxed.eliteodysseymaterials.service.event.EventListener<StorageEvent> storageEventEventListener = EventService.addListener(HorizonsBlueprintBar.this, StorageEvent.class, event -> {
                updateStyle(getItem());
                updateText(getItem(), this.emptyProperty().get());
            });
            @SuppressWarnings("java:S1068")
            private final nl.jixxed.eliteodysseymaterials.service.event.EventListener<EngineerEvent> engineerEventEventListener = EventService.addListener(HorizonsBlueprintBar.this, EngineerEvent.class, event -> {
                updateStyle(getItem());
                updateText(getItem(), this.emptyProperty().get());
            });

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
                    setText(item.toString() + (BlueprintHelper.isCompletedEngineerRecipe(item) ? " \u2714" : ""));
                }
            }


            private void updateStyle(final HorizonsBlueprintName item) {
                if (item != null && BlueprintHelper.isCompletedEngineerRecipe(item)) {
                    this.setStyle("-fx-text-fill: #89d07f;");
                } else {
                    this.setStyle("-fx-text-fill: white;");
                }
            }

        });
        return categoryTitledPane;
    }

    private Callback<ListView<HorizonsBlueprintName>, ListCell<HorizonsBlueprintName>> getCellFactory() {
        return listView -> new ListCell<>() {

            @SuppressWarnings("java:S1068")
            private final nl.jixxed.eliteodysseymaterials.service.event.EventListener<StorageEvent> storageEventEventListener = EventService.addListener(HorizonsBlueprintBar.this, StorageEvent.class, event -> {
                updateStyle(getItem());
                updateText(getItem(), this.emptyProperty().get());
            });
            @SuppressWarnings("java:S1068")
            private final EventListener<EngineerEvent> engineerEventEventListener = EventService.addListener(HorizonsBlueprintBar.this, EngineerEvent.class, event -> {
                updateStyle(getItem());
                updateText(getItem(), this.emptyProperty().get());
            });


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
                    setText(item + (BlueprintHelper.isCompletedEngineerRecipe(item) ? " \u2714" : ""));
                }
            }

            private void updateStyle(final HorizonsBlueprintName item) {
                if (item != null && BlueprintHelper.isCompletedEngineerRecipe(item)) {
                    this.setStyle("-fx-text-fill: #89d07f;");
                } else {
                    this.setStyle("-fx-text-fill: white;");
                }
            }

        };
    }

    private Map<HorizonsBlueprintName, Node> createRecipeContentEngineers(final Map<HorizonsBlueprintName, ? extends HorizonsEngineerBlueprint> recipesEntry, final ComboBox<HorizonsBlueprintName> comboBox, final TitledPane categoryTitledPane) {
        final Map<HorizonsBlueprintName, Node> contents = new EnumMap<>(HorizonsBlueprintName.class);
        recipesEntry.forEach((key, value) -> {
            EventService.addListener(this, BlueprintClickEvent.class, blueprintClickEvent -> {
                if (blueprintClickEvent.getBlueprintName().equals(key)) {
                    comboBox.getSelectionModel().select(key);
                    this.setExpandedPane(categoryTitledPane);
                }
            });
            final HorizonsBlueprintContent horizonsBlueprintContent = new HorizonsBlueprintContent(value);
            contents.put(key, horizonsBlueprintContent);
        });
        return contents;
    }
}
