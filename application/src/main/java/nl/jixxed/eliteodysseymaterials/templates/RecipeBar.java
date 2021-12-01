package nl.jixxed.eliteodysseymaterials.templates;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ComboBoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TitledPaneBuilder;
import nl.jixxed.eliteodysseymaterials.constants.RecipeConstants;
import nl.jixxed.eliteodysseymaterials.domain.Recipe;
import nl.jixxed.eliteodysseymaterials.enums.RecipeCategory;
import nl.jixxed.eliteodysseymaterials.enums.RecipeName;
import nl.jixxed.eliteodysseymaterials.helper.RecipeHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.*;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.Map;

@Slf4j
class RecipeBar extends Accordion {
    private About about;
    private TitledPane[] categoryTitledPanes;
    private TitledPane aboutTitledPane;
    private final Application application;

    RecipeBar(final Application application) {
        this.application = application;
        initComponents();

    }

    private void initComponents() {
        this.getStyleClass().add("recipe");
        this.categoryTitledPanes = RecipeConstants.RECIPES.entrySet().stream()
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

    private TitledPane createCategoryTitledPane(final Map.Entry<RecipeCategory, Map<RecipeName, ? extends Recipe>> recipesEntry) {
        final ScrollPane scroll = new ScrollPane();
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setPannable(true);
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);

        final ComboBox<RecipeName> recipes = ComboBoxBuilder.builder(RecipeName.class)
                .withStyleClass("recipes-list")
                .withItemsProperty(LocaleService.getListBinding(recipesEntry.getValue().keySet().stream().sorted(Comparator.comparing(recipeName -> LocaleService.getLocalizedStringForCurrentLocale(recipeName.getLocalizationKey()))).toArray(RecipeName[]::new)))
                .asLocalized()
                .build();
        recipes.setVisibleRowCount(recipes.getItems().size());

        final HBox hBox = BoxBuilder.builder().withNode(recipes).buildHBox();
        HBox.setHgrow(recipes, Priority.ALWAYS);

        final VBox content = BoxBuilder.builder()
                .withStyleClass("recipe-titled-pane-content")
                .withNodes(hBox, scroll)
                .buildVBox();
        VBox.setVgrow(scroll, Priority.ALWAYS);

        final TitledPane categoryTitledPane = TitledPaneBuilder.builder()
                .withStyleClass("category-title-pane")
                .withText(LocaleService.getStringBinding(recipesEntry.getKey().getLocalizationKey()))
                .withContent(content)
                .build();

        final Map<RecipeName, Node> recipeContent = createRecipeContent(recipesEntry, recipes, categoryTitledPane);
        recipes.valueProperty().addListener((obs, oldValue, newValue) -> scroll.setContent(recipeContent.get(newValue)));
        recipes.setCellFactory(getCellFactory());
        recipes.getSelectionModel().select(recipes.getItems().get(0));
        recipes.setButtonCell(new ListCell<>() {
            @SuppressWarnings("java:S1068")
            private final EventListener<StorageEvent> storageEventEventListener = EventService.addListener(RecipeBar.this, StorageEvent.class, event -> {
                updateStyle(getItem());
                updateText(getItem(), this.emptyProperty().get());
            });
            @SuppressWarnings("java:S1068")
            private final EventListener<EngineerEvent> engineerEventEventListener = EventService.addListener(RecipeBar.this, EngineerEvent.class, event -> {
                updateStyle(getItem());
                updateText(getItem(), this.emptyProperty().get());
            });

            @Override
            protected void updateItem(final RecipeName item, final boolean empty) {
                super.updateItem(item, empty);
                updateText(item, empty);
                updateStyle(item);
            }


            private void updateText(final RecipeName item, final boolean empty) {
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item.toString() + (RecipeHelper.isCompletedEngineerRecipe(item) ? " \u2714" : ""));
                }
            }


            private void updateStyle(final RecipeName item) {
                if (RecipeHelper.isCompletedEngineerRecipe(item) || RecipeHelper.isCraftableModuleOrUpgradeRecipe(item)) {
                    this.setStyle("-fx-text-fill: #89d07f;");
                } else {
                    this.setStyle("-fx-text-fill: white;");
                }
            }

        });
        return categoryTitledPane;
    }

    private Callback<ListView<RecipeName>, ListCell<RecipeName>> getCellFactory() {
        return listView -> new ListCell<>() {

            @SuppressWarnings("java:S1068")
            private final EventListener<StorageEvent> storageEventEventListener = EventService.addListener(RecipeBar.this, StorageEvent.class, event -> {
                updateStyle(getItem());
                updateText(getItem(), this.emptyProperty().get());
            });
            @SuppressWarnings("java:S1068")
            private final EventListener<EngineerEvent> engineerEventEventListener = EventService.addListener(RecipeBar.this, EngineerEvent.class, event -> {
                updateStyle(getItem());
                updateText(getItem(), this.emptyProperty().get());
            });


            @Override
            protected void updateItem(final RecipeName item, final boolean empty) {
                super.updateItem(item, empty);
                updateText(item, empty);
                updateStyle(item);
            }

            private void updateText(final RecipeName item, final boolean empty) {
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item + (RecipeHelper.isCompletedEngineerRecipe(item) ? " \u2714" : ""));
                }
            }

            private void updateStyle(final RecipeName item) {
                if (RecipeHelper.isCompletedEngineerRecipe(item) || RecipeHelper.isCraftableModuleOrUpgradeRecipe(item)) {
                    this.setStyle("-fx-text-fill: #89d07f;");
                } else {
                    this.setStyle("-fx-text-fill: white;");
                }
            }

        };
    }

    private Map<RecipeName, Node> createRecipeContent(final Map.Entry<RecipeCategory, Map<RecipeName, ? extends Recipe>> recipesEntry, final ComboBox<RecipeName> comboBox, final TitledPane categoryTitledPane) {
        final Map<RecipeName, Node> contents = new EnumMap<>(RecipeName.class);
        recipesEntry.getValue().forEach((key, value) -> {
            EventService.addListener(this, BlueprintClickEvent.class, blueprintClickEvent -> {
                if (blueprintClickEvent.getRecipeName().equals(key)) {
                    comboBox.getSelectionModel().select(key);
                    this.setExpandedPane(categoryTitledPane);
                }
            });
            final RecipeContent recipeContent = new RecipeContent(value);
            contents.put(key, recipeContent);
        });
        return contents;
    }
}
