package nl.jixxed.eliteodysseymaterials.templates;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ComboBoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TitledPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ToggleButtonBuilder;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.BlueprintCategory;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintModificationType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintObjectName;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import org.controlsfx.control.SegmentedButton;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

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
        this.getStyleClass().add("recipe");
        this.categoryTitledPanes = HorizonsBlueprintConstants.RECIPES.entrySet().stream()
                .sorted(Comparator.comparing(recipeCategoryMapEntry -> recipeCategoryMapEntry.getKey().toString()))
                .map(this::createCategoryTitledPane)
                .toArray(TitledPane[]::new);
        initAboutTitledPane();
        this.getPanes().addAll(this.categoryTitledPanes);
        this.getPanes().add(this.aboutTitledPane);
        this.setExpandedPane(this.aboutTitledPane);
    }

    private void initAboutTitledPane() {
        this.about = new About(this.application);
        this.aboutTitledPane = TitledPaneBuilder.builder().withContent(this.about).withText(LocaleService.getStringBinding("menu.about")).build();
    }

    private TitledPane createCategoryTitledPane(final Map.Entry<BlueprintCategory, Map<HorizonsBlueprintObjectName, Map<HorizonsBlueprintModificationType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>>>> recipesEntry) {
        final ScrollPane scroll = new ScrollPane();
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setPannable(true);
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);
        final ComboBox<HorizonsBlueprintModificationType> types = ComboBoxBuilder.builder(HorizonsBlueprintModificationType.class)
                .withStyleClass("recipes-list")
//                .withItemsProperty()
                .asLocalized()
                .build();
        final ComboBox<HorizonsBlueprintObjectName> recipes = ComboBoxBuilder.builder(HorizonsBlueprintObjectName.class)
                .withStyleClass("recipes-list")
                .withItemsProperty(LocaleService.getListBinding(recipesEntry.getValue().keySet().stream().sorted(Comparator.comparing(recipeName -> LocaleService.getLocalizedStringForCurrentLocale(recipeName.getLocalizationKey()))).toArray(HorizonsBlueprintObjectName[]::new)))
                .withValueChangeListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        types.itemsProperty().bind(LocaleService.getListBinding(HorizonsBlueprintConstants.getRecipeModificationTypes(newValue).stream().sorted(Comparator.comparing(type -> LocaleService.getLocalizedStringForCurrentLocale(type.getLocalizationKey()))).toArray(HorizonsBlueprintModificationType[]::new)));
                    }
                })
                .asLocalized()
                .build();
        recipes.setVisibleRowCount(recipes.getItems().size());
        recipes.getSelectionModel().select(0);
        types.getSelectionModel().select(0);
        types.setVisibleRowCount(types.getItems().size());
        final HBox hBox = BoxBuilder.builder().withNode(recipes).buildHBox();
        final HBox hBox2 = BoxBuilder.builder().withNode(types).buildHBox();
        HBox.setHgrow(recipes, Priority.ALWAYS);
        HBox.setHgrow(types, Priority.ALWAYS);


        final TitledPane categoryTitledPane = TitledPaneBuilder.builder()
                .withStyleClass("category-title-pane")
                .withText(LocaleService.getStringBinding(recipesEntry.getKey().getLocalizationKey()))
                .build();
        final Map<HorizonsBlueprintObjectName, Map<HorizonsBlueprintModificationType, Map<HorizonsBlueprintGrade, Node>>> recipeContent = createRecipeContent(recipesEntry, recipes, categoryTitledPane);
        final ToggleButton toggleButton1 = ToggleButtonBuilder.builder().withStyleClass("recipe-grade-togglebutton").withNonLocalizedText("1").build();
        final ToggleButton toggleButton2 = ToggleButtonBuilder.builder().withStyleClass("recipe-grade-togglebutton").withNonLocalizedText("2").build();
        final ToggleButton toggleButton3 = ToggleButtonBuilder.builder().withStyleClass("recipe-grade-togglebutton").withNonLocalizedText("3").build();
        final ToggleButton toggleButton4 = ToggleButtonBuilder.builder().withStyleClass("recipe-grade-togglebutton").withNonLocalizedText("4").build();
        final ToggleButton toggleButton5 = ToggleButtonBuilder.builder().withStyleClass("recipe-grade-togglebutton").withNonLocalizedText("5").build();
        final SegmentedButton segmentedButton = new SegmentedButton(toggleButton1, toggleButton2, toggleButton3, toggleButton4, toggleButton5);
        HBox.setHgrow(segmentedButton, Priority.ALWAYS);
        toggleButton1.setOnAction(event -> {
            if (!toggleButton1.isSelected()) {
                toggleButton1.setSelected(true);
            }
            setContent(scroll, recipes.getSelectionModel().getSelectedItem(), types.getSelectionModel().getSelectedItem(), HorizonsBlueprintGrade.GRADE_1, recipeContent, true);
        });
        toggleButton2.setOnAction(event -> {
            if (!toggleButton2.isSelected()) {
                toggleButton2.setSelected(true);
            }
            setContent(scroll, recipes.getSelectionModel().getSelectedItem(), types.getSelectionModel().getSelectedItem(), HorizonsBlueprintGrade.GRADE_2, recipeContent, true);
        });
        toggleButton3.setOnAction(event -> {
            if (!toggleButton3.isSelected()) {
                toggleButton3.setSelected(true);
            }
            setContent(scroll, recipes.getSelectionModel().getSelectedItem(), types.getSelectionModel().getSelectedItem(), HorizonsBlueprintGrade.GRADE_3, recipeContent, true);
        });
        toggleButton4.setOnAction(event -> {
            if (!toggleButton4.isSelected()) {
                toggleButton4.setSelected(true);
            }
            setContent(scroll, recipes.getSelectionModel().getSelectedItem(), types.getSelectionModel().getSelectedItem(), HorizonsBlueprintGrade.GRADE_4, recipeContent, true);
        });
        toggleButton5.setOnAction(event -> {
            if (!toggleButton5.isSelected()) {
                toggleButton5.setSelected(true);
            }
            setContent(scroll, recipes.getSelectionModel().getSelectedItem(), types.getSelectionModel().getSelectedItem(), HorizonsBlueprintGrade.GRADE_5, recipeContent, true);
        });
        toggleButton1.selectedProperty().setValue(true);
        final VBox content = BoxBuilder.builder()
                .withStyleClass("recipe-titled-pane-content")
                .withNodes(hBox, hBox2, segmentedButton, scroll)
                .buildVBox();
        categoryTitledPane.setContent(content);
        VBox.setVgrow(scroll, Priority.ALWAYS);


//        recipes.valueProperty().addListener((obs, oldValue, newValue) -> scroll.setContent(recipeContent.get(newValue)));
//        recipes.setCellFactory(getCellFactory());
//        recipes.getSelectionModel().select(recipes.getItems().get(0));
//        recipes.setButtonCell(new ListCell<>() {
//            @SuppressWarnings("java:S1068")
//            private final EventListener<StorageEvent> storageEventEventListener = EventService.addListener(HorizonsRecipeBar.this, StorageEvent.class, event -> {
//                updateStyle(getItem());
//                updateText(getItem(), this.emptyProperty().get());
//            });
//            @SuppressWarnings("java:S1068")
//            private final EventListener<EngineerEvent> engineerEventEventListener = EventService.addListener(HorizonsRecipeBar.this, EngineerEvent.class, event -> {
//                updateStyle(getItem());
//                updateText(getItem(), this.emptyProperty().get());
//            });
//
//            @Override
//            protected void updateItem(final HorizonsRecipeObjectName item, final boolean empty) {
//                super.updateItem(item, empty);
//                updateText(item, empty);
//                updateStyle(item);
//            }
//
//
//            private void updateText(final HorizonsRecipeObjectName item, final boolean empty) {
//                if (empty || item == null) {
//                    setText(null);
//                    setGraphic(null);
//                } else {
//                    setText(item.toString() + (RecipeHelper.isCompletedEngineerRecipe(item) ? " \u2714" : ""));
//                }
//            }
//
//
//            private void updateStyle(final RecipeName item) {
//                if (RecipeHelper.isCompletedEngineerRecipe(item) || RecipeHelper.getCraftabilityForModuleOrUpgradeRecipe(item).equals(Craftability.CRAFTABLE)) {
//                    this.setStyle("-fx-text-fill: #89d07f;");
//                } else if (RecipeHelper.getCraftabilityForModuleOrUpgradeRecipe(item).equals(Craftability.CRAFTABLE_WITH_TRADE)) {
//                    this.setStyle("-fx-text-fill: #dcc030;");
//                } else {
//                    this.setStyle("-fx-text-fill: white;");
//                }
//            }
//
//        });
        return categoryTitledPane;
    }

    private void setContent(final ScrollPane scroll, final HorizonsBlueprintObjectName name, final HorizonsBlueprintModificationType type, final HorizonsBlueprintGrade grade, final Map<HorizonsBlueprintObjectName, Map<HorizonsBlueprintModificationType, Map<HorizonsBlueprintGrade, Node>>> recipeContent, final Boolean isSelected) {
        if (Objects.equals(true, isSelected) && type != null && name != null) {
            try {
                scroll.setContent(recipeContent.get(name).get(type).get(grade));
            } catch (final NullPointerException ex) {
                log.error("NPE", ex);
            }
        }
    }

//    private Callback<ListView<RecipeName>, ListCell<RecipeName>> getCellFactory() {
//        return listView -> new ListCell<>() {
//
//            @SuppressWarnings("java:S1068")
//            private final EventListener<StorageEvent> storageEventEventListener = EventService.addListener(HorizonsRecipeBar.this, StorageEvent.class, event -> {
//                updateStyle(getItem());
//                updateText(getItem(), this.emptyProperty().get());
//            });
//            @SuppressWarnings("java:S1068")
//            private final EventListener<EngineerEvent> engineerEventEventListener = EventService.addListener(HorizonsRecipeBar.this, EngineerEvent.class, event -> {
//                updateStyle(getItem());
//                updateText(getItem(), this.emptyProperty().get());
//            });
//
//
//            @Override
//            protected void updateItem(final RecipeName item, final boolean empty) {
//                super.updateItem(item, empty);
//                updateText(item, empty);
//                updateStyle(item);
//            }
//
//            private void updateText(final RecipeName item, final boolean empty) {
//                if (empty || item == null) {
//                    setText(null);
//                    setGraphic(null);
//                } else {
//                    setText(item + (RecipeHelper.isCompletedEngineerRecipe(item) ? " \u2714" : ""));
//                }
//            }
//
//            private void updateStyle(final RecipeName item) {
//                if (RecipeHelper.isCompletedEngineerRecipe(item) || RecipeHelper.getCraftabilityForModuleOrUpgradeRecipe(item).equals(Craftability.CRAFTABLE)) {
//                    this.setStyle("-fx-text-fill: #89d07f;");
//                } else if (RecipeHelper.getCraftabilityForModuleOrUpgradeRecipe(item).equals(Craftability.CRAFTABLE_WITH_TRADE)) {
//                    this.setStyle("-fx-text-fill: #dcc030;");
//                } else {
//                    this.setStyle("-fx-text-fill: white;");
//                }
//            }
//
//        };
//    }

    private Map<HorizonsBlueprintObjectName, Map<HorizonsBlueprintModificationType, Map<HorizonsBlueprintGrade, Node>>> createRecipeContent(final Map.Entry<BlueprintCategory, Map<HorizonsBlueprintObjectName, Map<HorizonsBlueprintModificationType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>>>> recipesEntry, final ComboBox<HorizonsBlueprintObjectName> comboBox, final TitledPane categoryTitledPane) {
        final Map<HorizonsBlueprintObjectName, Map<HorizonsBlueprintModificationType, Map<HorizonsBlueprintGrade, Node>>> contents = new EnumMap<>(HorizonsBlueprintObjectName.class);
        recipesEntry.getValue().forEach((horizonsRecipeObjectName, horizonsRecipeModificationTypeMap) -> {
            horizonsRecipeModificationTypeMap.forEach((horizonsRecipeModificationType, horizonsRecipeGradeHorizonsRecipeMap) -> {
                horizonsRecipeGradeHorizonsRecipeMap.forEach((horizonsRecipeGrade, horizonsRecipe) -> {
                    final HorizonsBlueprintContent recipeContent = new HorizonsBlueprintContent(horizonsRecipe);
                    final Map<HorizonsBlueprintModificationType, Map<HorizonsBlueprintGrade, Node>> typeMap = contents.getOrDefault(horizonsRecipeObjectName, new EnumMap<>(HorizonsBlueprintModificationType.class));
                    final Map<HorizonsBlueprintGrade, Node> gradeMap = typeMap.getOrDefault(horizonsRecipeModificationType, new EnumMap<>(HorizonsBlueprintGrade.class));
                    gradeMap.put(horizonsRecipeGrade, recipeContent);
                    typeMap.put(horizonsRecipeModificationType, gradeMap);
                    contents.put(horizonsRecipeObjectName, typeMap);
                });
            });
        });
        return contents;
    }
}
