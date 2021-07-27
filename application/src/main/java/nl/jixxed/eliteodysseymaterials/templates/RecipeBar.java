package nl.jixxed.eliteodysseymaterials.templates;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import nl.jixxed.eliteodysseymaterials.RecipeConstants;
import nl.jixxed.eliteodysseymaterials.domain.EngineerRecipe;
import nl.jixxed.eliteodysseymaterials.domain.Recipe;
import nl.jixxed.eliteodysseymaterials.enums.RecipeName;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.BlueprintClickEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EngineerEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class RecipeBar extends Accordion {
    private final Settings settings;

    public RecipeBar(final Application application) {
        this.settings = new Settings(application);
        final List<TitledPane> titledPanes = RecipeConstants.RECIPES.entrySet().stream()
                .map(this::createCategoryTitledPane)
                .sorted(Comparator.comparing(Labeled::getText))
                .collect(Collectors.toList());
        final TitledPane about = new TitledPane();
        about.textProperty().bind(LocaleService.getStringBinding("menu.about"));
        about.setContent(this.settings);
        titledPanes.add(about);
        this.getPanes().addAll(titledPanes.toArray(new TitledPane[0]));
        this.setExpandedPane(about);

        this.setMinWidth(500);
        this.setPrefWidth(500);
        this.setMaxWidth(500);
    }


    private TitledPane createCategoryTitledPane(final Map.Entry<String, Map<RecipeName, ? extends Recipe>> recipesEntry) {
        final TitledPane categoryTitledPane = new TitledPane();
        final ComboBox<RecipeName> recipes = new ComboBox<>();
        final ScrollPane scroll = new ScrollPane();
        final Map<RecipeName, Node> recipeContent = createRecipeContent(recipesEntry, recipes, categoryTitledPane);
        recipes.itemsProperty().bind(LocaleService.getListBinding(recipesEntry.getValue().keySet().stream().sorted(Comparator.comparing(recipeName -> LocaleService.getLocalizedStringForCurrentLocale(recipeName.getLocalizationKey()))).toArray(RecipeName[]::new)));
        recipes.valueProperty().addListener((obs, oldValue, newValue) -> {
            scroll.setContent(recipeContent.get(newValue));
        });
        recipes.setCellFactory(getCellFactory());
        recipes.getSelectionModel().select(recipes.getItems().get(0));
        scroll.setPannable(true);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);
        categoryTitledPane.textProperty().bind(LocaleService.getStringBinding(recipesEntry.getKey()));
        final HBox hBox = new HBox(recipes);
        recipes.setMinWidth(500);
        recipes.setPrefWidth(500);
        recipes.setMaxWidth(500);
        HBox.setHgrow(recipes, Priority.ALWAYS);
        final VBox value = new VBox(hBox, scroll);
        value.setFillWidth(true);
        value.setStyle("-fx-padding: 0;");
        VBox.setVgrow(scroll, Priority.ALWAYS);
        categoryTitledPane.setContent(value);
        categoryTitledPane.getStyleClass().add("category-title-pane");
        return categoryTitledPane;
    }

    private Callback<ListView<RecipeName>, ListCell<RecipeName>> getCellFactory() {
        return new Callback<>() {
            @Override
            public ListCell<RecipeName> call(final ListView<RecipeName> listView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(final RecipeName item, final boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            setText(item.toString());
                        }
                        updateStyle(item);
                        EventService.addListener(EngineerEvent.class, event -> {
                            updateStyle(item);
                        });
                    }

                    private void updateStyle(final RecipeName item) {
                        if (RecipeConstants.getRecipe(item) instanceof EngineerRecipe && ((EngineerRecipe) RecipeConstants.getRecipe(item)).isCompleted()) {
                            this.setStyle("-fx-text-fill: #89d07f;");
                        } else if (RecipeConstants.getRecipe(item) instanceof EngineerRecipe) {
                            this.setStyle("-fx-text-fill: white;");
                        }
                    }
                };
            }
        };
    }

    private Map<RecipeName, Node> createRecipeContent(final Map.Entry<String, Map<RecipeName, ? extends Recipe>> recipesEntry, final ComboBox<RecipeName> comboBox, final TitledPane categoryTitledPane) {
        final Map<RecipeName, Node> contents = new HashMap<>();
        recipesEntry.getValue().entrySet().forEach(recipe -> {
            EventService.addListener(BlueprintClickEvent.class, blueprintClickEvent -> {
                if (blueprintClickEvent.getRecipeName().equals(recipe.getKey())) {
                    comboBox.getSelectionModel().select(recipe.getKey());
                    this.setExpandedPane(categoryTitledPane);
                }
            });
            final RecipeContent recipeContent = new RecipeContent(recipe);
            contents.put(recipe.getKey(), recipeContent);
        });
        return contents;
    }
}
