package nl.jixxed.eliteodysseymaterials.templates.horizons.menu;

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
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.BlueprintCategory;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.helper.BlueprintHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.Destroyable;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableComboBox;
import nl.jixxed.eliteodysseymaterials.templates.generic.About;
import org.controlsfx.control.SegmentedButton;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
public
class HorizonsBlueprintBar extends Accordion {
    private static final String BLUEPRINT_TITLED_PANE_CONTENT_STYLE_CLASS = "blueprint-titled-pane-content";
    private static final String BLUEPRINT_LIST_STYLE_CLASS = "blueprint-list";
    private About about;
    private TitledPane[] categoryTitledPanes;
    private TitledPane aboutTitledPane;
    private final Application application;
    private final List<EventListener<?>> eventListeners = new ArrayList<>();

    public HorizonsBlueprintBar(final Application application) {
        this.application = application;
        initComponents();

    }

    private void initComponents() {
        this.getStyleClass().add("blueprint-bar");
        this.categoryTitledPanes = HorizonsBlueprintConstants.RECIPES.entrySet().stream()
                .sorted(Comparator.comparing(recipeCategoryMapEntry -> recipeCategoryMapEntry.getKey().getOrder()))
                .map(this::createBlueprintCategoryTitledPane)
                .toArray(TitledPane[]::new);

        final TitledPane categoryTitledPaneEngineers = createCategoryTitledPaneEngineers(HorizonsBlueprintConstants.getEngineerUnlockRequirements());
        final TitledPane categoryTitledPaneExperimental = createExperimentalEffectsCategoryTitledPane(HorizonsBlueprintConstants.getExperimentalEffects());
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
                .withStyleClass(BLUEPRINT_LIST_STYLE_CLASS)
                .asLocalized()
                .build();
        final DestroyableComboBox<HorizonsBlueprintName> blueprints = createBlueprintsComboboxForTypes(types, recipesEntry.keySet(), recipesEntry.entrySet().stream().map(horizonsBlueprintNameMapEntry -> Map.entry(horizonsBlueprintNameMapEntry.getKey(), horizonsBlueprintNameMapEntry.getValue().keySet())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

        final TitledPane categoryTitledPane = createTitledPane(BlueprintCategory.TECHBROKER.getLocalizationKey());

        types.addChangeListener(types.valueProperty(), (ChangeListener<HorizonsBlueprintType>) (observable, oldValue, newValue) -> {
            if (newValue != null) {
                setContent(scroll, blueprints.getSelectionModel().getSelectedItem(), types.getSelectionModel().getSelectedItem(), true,
                        generateContent(recipesEntry.getOrDefault(blueprints.getSelectionModel().getSelectedItem(), Collections.emptyMap()).get(types.getSelectionModel().getSelectedItem())));
            }
            types.setVisibleRowCount(types.getItems().size());
        });
        final HBox hBoxBlueprints = BoxBuilder.builder().withNode(blueprints).buildHBox();
        final HBox hBoxTypes = BoxBuilder.builder().withNode(types).buildHBox();
        HBox.setHgrow(blueprints, Priority.ALWAYS);
        HBox.setHgrow(types, Priority.ALWAYS);
        this.eventListeners.add(EventService.addListener(this, HorizonsBlueprintClickEvent.class, blueprintClickEvent -> {
            if (blueprintClickEvent.getBlueprint().getBlueprintName() instanceof HorizonsBlueprintName blueprintName && BlueprintCategory.TECHBROKER.equals(blueprintName.getBlueprintCategory())) {
                blueprints.getSelectionModel().select(blueprintName);
                types.getSelectionModel().select(getBlueprintType(blueprintClickEvent.getBlueprint()));
                this.setExpandedPane(categoryTitledPane);
            }
        }));

        //auto select first option
        blueprints.getSelectionModel().select(0);
        final VBox content = BoxBuilder.builder()
                .withStyleClass(BLUEPRINT_TITLED_PANE_CONTENT_STYLE_CLASS)
                .withNodes(hBoxBlueprints, hBoxTypes, scroll)
                .buildVBox();
        categoryTitledPane.setContent(content);
        VBox.setVgrow(scroll, Priority.ALWAYS);
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

    private TitledPane createExperimentalEffectsCategoryTitledPane(final Map<HorizonsBlueprintName, Map<HorizonsBlueprintType, HorizonsBlueprint>> recipesEntry) {
        final ScrollPane scroll = createScrollPane();

        final DestroyableComboBox<HorizonsBlueprintType> types = ComboBoxBuilder.builder(HorizonsBlueprintType.class)
                .withStyleClass(BLUEPRINT_LIST_STYLE_CLASS)
                .asLocalized()
                .build();
        final DestroyableComboBox<HorizonsBlueprintName> blueprints = createBlueprintsComboboxForTypes(types, recipesEntry.keySet(), recipesEntry.entrySet().stream().map(horizonsBlueprintNameMapEntry -> Map.entry(horizonsBlueprintNameMapEntry.getKey(), horizonsBlueprintNameMapEntry.getValue().keySet())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

        final TitledPane categoryTitledPane = createTitledPane(BlueprintCategory.EXPERIMENTAL_EFFECTS.getLocalizationKey());

        types.addChangeListener(types.valueProperty(), (ChangeListener<HorizonsBlueprintType>) (observable, oldValue, newValue) -> {
            if (newValue != null) {
                setContent(scroll, blueprints.getSelectionModel().getSelectedItem(), types.getSelectionModel().getSelectedItem(), true,
                        generateContent(recipesEntry.getOrDefault(blueprints.getSelectionModel().getSelectedItem(), Collections.emptyMap()).get(types.getSelectionModel().getSelectedItem())));
            }
            types.setVisibleRowCount(types.getItems().size());
        });
        final HBox hBoxBlueprints = BoxBuilder.builder().withNode(blueprints).buildHBox();
        final HBox hBoxTypes = BoxBuilder.builder().withNode(types).buildHBox();
        HBox.setHgrow(blueprints, Priority.ALWAYS);
        HBox.setHgrow(types, Priority.ALWAYS);
        this.eventListeners.add(EventService.addListener(this, HorizonsBlueprintClickEvent.class, blueprintClickEvent -> {
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
        final VBox content = BoxBuilder.builder()
                .withStyleClass(BLUEPRINT_TITLED_PANE_CONTENT_STYLE_CLASS)
                .withNodes(hBoxBlueprints, hBoxTypes, scroll)
                .buildVBox();
        categoryTitledPane.setContent(content);
        VBox.setVgrow(scroll, Priority.ALWAYS);
        return categoryTitledPane;
    }

    private TitledPane createSynthesisCategoryTitledPane(final Map<HorizonsBlueprintName, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> recipesEntry) {
        final ScrollPane scroll = createScrollPane();
        final List<ToggleButton> toggleButtons = new ArrayList<>();
        final DestroyableComboBox<HorizonsBlueprintName> blueprints = createBlueprintsComboboxForSynthesis(scroll, toggleButtons, recipesEntry.keySet(), HorizonsBlueprintType.SYNTHESIS, recipesEntry);

        final TitledPane categoryTitledPane = createTitledPane(BlueprintCategory.SYNTHESIS.getLocalizationKey());

        toggleButtons.addAll(getSynthesisToggleButtons(scroll, () -> HorizonsBlueprintType.SYNTHESIS, () -> blueprints.getSelectionModel().getSelectedItem(), recipesEntry));
        final SegmentedButton gradeButtons = createGradeSegmentedButton(toggleButtons);


        final HBox hBoxBlueprints = BoxBuilder.builder().withNode(blueprints).buildHBox();
        HBox.setHgrow(blueprints, Priority.ALWAYS);
        this.eventListeners.add(EventService.addListener(this, HorizonsBlueprintClickEvent.class, blueprintClickEvent -> {
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
        final VBox content = BoxBuilder.builder()
                .withStyleClass(BLUEPRINT_TITLED_PANE_CONTENT_STYLE_CLASS)
                .withNodes(hBoxBlueprints, gradeButtons, scroll)
                .buildVBox();
        categoryTitledPane.setContent(content);
        VBox.setVgrow(scroll, Priority.ALWAYS);
        return categoryTitledPane;
    }

    private TitledPane createBlueprintCategoryTitledPane(final Map.Entry<BlueprintCategory, Map<HorizonsBlueprintName, Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>>>> recipesEntry) {
        final ScrollPane scroll = createScrollPane();

        final DestroyableComboBox<HorizonsBlueprintType> types = ComboBoxBuilder.builder(HorizonsBlueprintType.class)
                .withStyleClass(BLUEPRINT_LIST_STYLE_CLASS)
                .asLocalized()
                .build();
        final DestroyableComboBox<HorizonsBlueprintName> blueprints = createBlueprintsComboboxForTypes(types, recipesEntry.getValue().keySet(), recipesEntry.getValue().entrySet().stream().map(horizonsBlueprintNameMapEntry -> Map.entry(horizonsBlueprintNameMapEntry.getKey(), horizonsBlueprintNameMapEntry.getValue().keySet())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

        final TitledPane categoryTitledPane = createTitledPane(recipesEntry.getKey().getLocalizationKey());
        final List<ToggleButton> toggleButtons = getGradeToggleButtons(scroll, () -> types.getSelectionModel().getSelectedItem(), () -> blueprints.getSelectionModel().getSelectedItem(), recipesEntry.getValue());
        final SegmentedButton gradeButtons = createGradeSegmentedButton(toggleButtons);

        types.addChangeListener(types.valueProperty(), (ChangeListener<HorizonsBlueprintType>) (observable, oldValue, newValue) -> {
            if (newValue != null) {
                final Set<HorizonsBlueprintGrade> blueprintGrades = HorizonsBlueprintConstants.getBlueprintGrades(blueprints.getSelectionModel().getSelectedItem(), newValue);
                configureToggleButtonsState(toggleButtons, blueprints.getSelectionModel().getSelectedItem(), blueprintGrades);
                types.setVisibleRowCount(types.getItems().size());
                if (!blueprintGrades.isEmpty()) {
                    setContent(scroll, blueprints.getSelectionModel().getSelectedItem(), newValue, true, generateContent(recipesEntry.getValue().getOrDefault(blueprints.getSelectionModel().getSelectedItem(), Collections.emptyMap()).getOrDefault(newValue, Collections.emptyMap()).get(HorizonsBlueprintGrade.GRADE_1)));
                }
            }
        });
        this.eventListeners.add(EventService.addListener(this, HorizonsBlueprintClickEvent.class, blueprintClickEvent -> {
            if (blueprintClickEvent.getBlueprint().getBlueprintName() instanceof HorizonsBlueprintName blueprintName && recipesEntry.getKey().equals(blueprintName.getBlueprintCategory()) && !blueprintClickEvent.isExperimental()) {
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
        final HBox hBoxBlueprints = BoxBuilder.builder().withNode(blueprints).buildHBox();
        final HBox hBoxTypes = BoxBuilder.builder().withNode(types).buildHBox();
        HBox.setHgrow(blueprints, Priority.ALWAYS);
        HBox.setHgrow(types, Priority.ALWAYS);


        //auto select first option
        blueprints.getSelectionModel().select(0);
        final VBox content = BoxBuilder.builder()
                .withStyleClass(BLUEPRINT_TITLED_PANE_CONTENT_STYLE_CLASS)
                .withNodes(hBoxBlueprints, hBoxTypes, gradeButtons, scroll)
                .buildVBox();
        categoryTitledPane.setContent(content);
        VBox.setVgrow(scroll, Priority.ALWAYS);
        return categoryTitledPane;
    }

    private SegmentedButton createGradeSegmentedButton(final List<ToggleButton> toggleButtons) {
        final SegmentedButton segmentedButton = new SegmentedButton(toggleButtons.toArray(ToggleButton[]::new));
        segmentedButton.getStyleClass().add(BLUEPRINT_LIST_STYLE_CLASS);
        HBox.setHgrow(segmentedButton, Priority.ALWAYS);
        return segmentedButton;
    }

    private TitledPane createTitledPane(final String localizationKey) {
        return TitledPaneBuilder.builder()
                .withStyleClass("category-title-pane")
                .withText(LocaleService.getStringBinding(localizationKey))
                .build();
    }

    private DestroyableComboBox<HorizonsBlueprintName> createBlueprintsComboboxForSynthesis(final ScrollPane scroll, final List<ToggleButton> toggleButtons, final Set<HorizonsBlueprintName> horizonsBlueprintNames, final HorizonsBlueprintType type, final Map<HorizonsBlueprintName, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> recipeEntry) {
        final DestroyableComboBox<HorizonsBlueprintName> blueprints = ComboBoxBuilder.builder(HorizonsBlueprintName.class)
                .withStyleClass(BLUEPRINT_LIST_STYLE_CLASS)
                .withItemsProperty(LocaleService.getListBinding(horizonsBlueprintNames.stream().sorted(Comparator.comparing(recipeName -> LocaleService.getLocalizedStringForCurrentLocale(recipeName.getLocalizationKey()))).toArray(HorizonsBlueprintName[]::new)))
                .withValueChangeListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        final Set<HorizonsBlueprintGrade> blueprintGrades = HorizonsBlueprintConstants.getSynthesisBlueprintGrades(newValue);
                        configureToggleButtonsState(toggleButtons, newValue, blueprintGrades);
                        setContent(scroll, newValue, type, true, generateContent(recipeEntry.getOrDefault(newValue, Collections.emptyMap()).get(HorizonsBlueprintGrade.GRADE_1)));
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

    private List<ToggleButton> getGradeToggleButtons(final ScrollPane scroll, final Supplier<HorizonsBlueprintType> selectedType, final Supplier<HorizonsBlueprintName> selectedBlueprint, final Map<HorizonsBlueprintName, Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>>> recipeEntry) {
        final ToggleButton toggleButton1 = getToggleButton(scroll, selectedType, selectedBlueprint, HorizonsBlueprintGrade.GRADE_1, recipeEntry);
        final ToggleButton toggleButton2 = getToggleButton(scroll, selectedType, selectedBlueprint, HorizonsBlueprintGrade.GRADE_2, recipeEntry);
        final ToggleButton toggleButton3 = getToggleButton(scroll, selectedType, selectedBlueprint, HorizonsBlueprintGrade.GRADE_3, recipeEntry);
        final ToggleButton toggleButton4 = getToggleButton(scroll, selectedType, selectedBlueprint, HorizonsBlueprintGrade.GRADE_4, recipeEntry);
        final ToggleButton toggleButton5 = getToggleButton(scroll, selectedType, selectedBlueprint, HorizonsBlueprintGrade.GRADE_5, recipeEntry);
        return List.of(toggleButton1, toggleButton2, toggleButton3, toggleButton4, toggleButton5);
    }

    private List<ToggleButton> getSynthesisToggleButtons(final ScrollPane scroll, final Supplier<HorizonsBlueprintType> selectedType, final Supplier<HorizonsBlueprintName> selectedBlueprint, final Map<HorizonsBlueprintName, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> recipesEntry) {
        final ToggleButton toggleButton1 = getSynthesisToggleButton(scroll, selectedType, selectedBlueprint, HorizonsBlueprintGrade.GRADE_1, recipesEntry);
        final ToggleButton toggleButton2 = getSynthesisToggleButton(scroll, selectedType, selectedBlueprint, HorizonsBlueprintGrade.GRADE_2, recipesEntry);
        final ToggleButton toggleButton3 = getSynthesisToggleButton(scroll, selectedType, selectedBlueprint, HorizonsBlueprintGrade.GRADE_3, recipesEntry);
        return List.of(toggleButton1, toggleButton2, toggleButton3);
    }

    private ToggleButton getToggleButton(final ScrollPane scroll, final Supplier<HorizonsBlueprintType> selectedType, final Supplier<HorizonsBlueprintName> selectedBlueprint, final HorizonsBlueprintGrade grade, final Map<HorizonsBlueprintName, Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>>> recipeEntry) {
        final ToggleButton toggleButton = ToggleButtonBuilder.builder().withStyleClass("recipe-grade-togglebutton").build();
        toggleButton.setGraphic(ResizableImageViewBuilder.builder().withImage("/images/ships/engineers/ranks/" + grade.getGrade() + ".png").withStyleClasses("blueprint-grade-image").build());
        toggleButton.selectedProperty().addListener((observable, oldValue, newValue) ->
        {
            if (Boolean.TRUE.equals(newValue)) {
                setContent(scroll, selectedBlueprint.get(), selectedType.get(), newValue, generateContent(recipeEntry.getOrDefault(selectedBlueprint.get(), Collections.emptyMap()).getOrDefault(selectedType.get(), Collections.emptyMap()).get(grade)));
            }
        });
        return toggleButton;
    }

    private ToggleButton getSynthesisToggleButton(final ScrollPane scroll, final Supplier<HorizonsBlueprintType> selectedType, final Supplier<HorizonsBlueprintName> selectedBlueprint, final HorizonsBlueprintGrade grade, final Map<HorizonsBlueprintName, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> recipesEntry) {
        final ToggleButton toggleButton = ToggleButtonBuilder.builder().withText(LocaleService.getStringBinding("blueprint.synthesis.grade" + grade.getGrade())).withStyleClass("recipe-synthesis-togglebutton").build();
        toggleButton.selectedProperty().addListener((observable, oldValue, newValue) ->
        {
            if (Boolean.TRUE.equals(newValue)) {
                setContent(scroll, selectedBlueprint.get(), selectedType.get(), newValue, generateContent(recipesEntry.getOrDefault(selectedBlueprint.get(), Collections.emptyMap()).get(grade)));
            }
        });
        return toggleButton;
    }

    private Node generateContent(final HorizonsBlueprint horizonsBlueprint) {
        if (horizonsBlueprint != null) {
            return new HorizonsBlueprintContent(horizonsBlueprint);
        }
        return null;
    }

    private void setContent(final ScrollPane scroll, final HorizonsBlueprintName name, final HorizonsBlueprintType type, final Boolean isSelected, final Node content) {
        if (Objects.equals(true, isSelected) && type != null && name != null) {
            try {
                if (scroll.getContent() instanceof Destroyable destroyableContent) {
                    log.debug("destroy existing content" + destroyableContent);
                    destroyableContent.destroy();
                }
                log.debug("set content" + content);
                scroll.setContent(content);
            } catch (final NullPointerException ex) {
                log.error("NPE", ex);
            }
        } else {
            if (content instanceof Destroyable destroyableContent) {
                log.debug("set provided content" + destroyableContent);
                destroyableContent.destroy();
            }
        }
    }

    private TitledPane createCategoryTitledPaneEngineers(final Map<HorizonsBlueprintName, ? extends HorizonsEngineerBlueprint> recipesEngineers) {
        final ScrollPane scroll = createScrollPane();

        final ComboBox<HorizonsBlueprintName> blueprints = ComboBoxBuilder.builder(HorizonsBlueprintName.class)
                .withStyleClass(BLUEPRINT_LIST_STYLE_CLASS)
                .withItemsProperty(LocaleService.getListBinding(recipesEngineers.keySet().stream().sorted(Comparator.comparing(HorizonsBlueprintName::getLocalizationKey)).toArray(HorizonsBlueprintName[]::new)))
                .asLocalized()
                .build();
        blueprints.setVisibleRowCount(blueprints.getItems().size());

        final HBox hBox = BoxBuilder.builder().withNode(blueprints).buildHBox();
        HBox.setHgrow(blueprints, Priority.ALWAYS);

        final VBox content = BoxBuilder.builder()
                .withStyleClass(BLUEPRINT_TITLED_PANE_CONTENT_STYLE_CLASS)
                .withNodes(hBox, scroll)
                .buildVBox();
        VBox.setVgrow(scroll, Priority.ALWAYS);

        final TitledPane categoryTitledPane = TitledPaneBuilder.builder()
                .withStyleClass("category-title-pane")
                .withText(LocaleService.getStringBinding(BlueprintCategory.ENGINEER_UNLOCKS.getLocalizationKey()))
                .withContent(content)
                .build();

        blueprints.valueProperty().addListener((obs, oldValue, newValue) -> {
            setContent(scroll, newValue, HorizonsBlueprintType.ENGINEER, true, generateContent(recipesEngineers.get(newValue)));
        });
        this.eventListeners.add(EventService.addListener(this, HorizonsBlueprintClickEvent.class, blueprintClickEvent -> {
            if (blueprintClickEvent.getBlueprint().getBlueprintName() instanceof HorizonsBlueprintName blueprintName && BlueprintCategory.ENGINEER_UNLOCKS.equals(blueprintName.getBlueprintCategory())) {
                blueprints.getSelectionModel().select(blueprintName);
                this.setExpandedPane(categoryTitledPane);
            }
        }));
        this.eventListeners.add(EventService.addListener(this, BlueprintClickEvent.class, blueprintClickEvent -> {
            if (blueprintClickEvent.getBlueprintName() instanceof HorizonsBlueprintName blueprintName && BlueprintCategory.ENGINEER_UNLOCKS.equals(blueprintName.getBlueprintCategory())) {
                blueprints.getSelectionModel().select(blueprintName);
                this.setExpandedPane(categoryTitledPane);
            }
        }));
        blueprints.setCellFactory(getCellFactory());
        blueprints.getSelectionModel().select(blueprints.getItems().get(0));
        blueprints.setButtonCell(new ListCell<>() {
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

//    private Map<HorizonsBlueprintName, Node> createRecipeContentEngineers(final Map<HorizonsBlueprintName, ? extends HorizonsEngineerBlueprint> recipesEntry, final ComboBox<HorizonsBlueprintName> comboBox, final TitledPane categoryTitledPane) {
//        final Map<HorizonsBlueprintName, Node> contents = new EnumMap<>(HorizonsBlueprintName.class);
//        recipesEntry.forEach((key, value) -> {
//            eventListeners.add(EventService.addListener(this, BlueprintClickEvent.class, blueprintClickEvent -> {
//                if (blueprintClickEvent.getBlueprintName().equals(key)) {
//                    comboBox.getSelectionModel().select(key);
//                    this.setExpandedPane(categoryTitledPane);
//                }
//            });
//            final HorizonsBlueprintContent horizonsBlueprintContent = new HorizonsBlueprintContent(value);
//            contents.put(key, horizonsBlueprintContent);
//        });
//        return contents;
//    }
}
