package nl.jixxed.eliteodysseymaterials.templates;

import com.fasterxml.jackson.databind.JsonNode;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import nl.jixxed.eliteodysseymaterials.RecipeConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.EngineerRecipe;
import nl.jixxed.eliteodysseymaterials.domain.Recipe;
import nl.jixxed.eliteodysseymaterials.domain.Storage;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ApplicationLayout extends AnchorPane {
    private static final Insets CARD_MARGIN = new Insets(2, 5, 2, 5);
    private final List<EngineerTitledPane> engineerTitledPanes = new ArrayList<>();
    private final AnchorPane content = new AnchorPane();
    private String search = "";
    private final GridPane materialOverview = new GridPane();
    private final Settings settings;
    private final List<Ingredient> ingredients = new ArrayList<>();
    private final Legend legend = new Legend();
    final Label watchedFileLabel = new Label();
    final Label lastTimeStampLabel = new Label();
    private final ApplicationState applicationState = ApplicationState.getInstance();

    public ApplicationLayout(final Application application) {
        this.settings = new Settings(application);
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ApplicationLayout.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        }
        final ScrollPane scrollPane = new ScrollPane();
        scrollPane.pannableProperty().set(true);
        final TextField textField = new TextField();
        textField.setAccessibleText("text");
        textField.getStyleClass().add("search");
        textField.setPromptText("Search");
        textField.setFocusTraversable(false);
        setAnchor(textField, 0.0, 0.0, 0.0, 0.0);
        final ObservableList<Show> showOptions =
                FXCollections.observableArrayList(
                        Show.ALL,
                        Show.ALL_WITH_STOCK,
                        Show.ALL_ENGINEER_BLUEPRINT,
                        Show.REQUIRED_ENGINEER_BLUEPRINT,
                        Show.ALL_ENGINEER,
                        Show.REQUIRED_ENGINEER,
                        Show.BLUEPRINT,
                        Show.IRRELEVANT,
                        Show.IRRELEVANT_WITH_STOCK
                );
        final ObservableList<Sort> sortOptions =
                FXCollections.observableArrayList(
                        Sort.ENGINEER_BLUEPRINT_IRRELEVANT, Sort.RELEVANT_IRRELEVANT, Sort.ALPHABETICAL
                );
        final ComboBox<Show> showMaterialsComboBox = new ComboBox<>(showOptions);
        showMaterialsComboBox.getStyleClass().add("filter-and-sort");
        showMaterialsComboBox.setPromptText("Show materials:");
        showMaterialsComboBox.setTooltip(new Tooltip("Show materials"));
        showMaterialsComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            this.applicationState.setShow(newValue);
            updateGui();
        });
        final ComboBox<Sort> sortMaterialsComboBox = new ComboBox<>(sortOptions);
        sortMaterialsComboBox.getStyleClass().add("filter-and-sort");
        sortMaterialsComboBox.setPromptText("Sort materials:");
        sortMaterialsComboBox.setTooltip(new Tooltip("Sort materials"));
        sortMaterialsComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            this.applicationState.setSort(newValue);
            updateGui();
        });
        final Observable<String> searchChanged = Observable.create((ObservableEmitter<String> emitter) ->
                textField.textProperty().addListener((observable, oldValue, newValue) -> emitter.onNext(newValue))
        ).debounce(500, TimeUnit.MILLISECONDS).observeOn(Schedulers.io());
        searchChanged.subscribe((newValue) -> {

                    this.search = newValue;//
                    Platform.runLater(() -> {
                        this.materialOverview.getChildren().clear();
                        showGoods(this.materialOverview);
                        showAssets(this.materialOverview);
                        showDatas(this.materialOverview);
                        updateTotals();
                    });
                }
        );
        for (int i = 0; i < 5; i++) {
            final ColumnConstraints column = new ColumnConstraints(250);
            this.materialOverview.getColumnConstraints().add(column);
        }
        this.materialOverview.getStyleClass().add("card-grid");

        final HBox hBox = new HBox(textField, showMaterialsComboBox, sortMaterialsComboBox);
        HBox.setHgrow(textField, Priority.ALWAYS);//Added this line
        HBox.setHgrow(showMaterialsComboBox, Priority.ALWAYS);//Added this line
        HBox.setHgrow(sortMaterialsComboBox, Priority.ALWAYS);//Added this line
        final VBox value = new VBox(hBox, scrollPane);
        scrollPane.setContent(this.materialOverview);
        setAnchor(this.content, 0.0, 25.0, 0.0, 0.0);
        setAnchor(scrollPane, 40.0, 25.0, 0.0, 0.0);
        setAnchor(value, 0.0, 25.0, 0.0, 0.0);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);//Added this line

        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        this.content.getChildren().add(value);
        setAnchor(value, 0.0, 0.0, 372.0, 0.0);
        setAnchor(scrollPane, 0.0, 0.0, 372.0, 0.0);
        final HBox bottomBar = new HBox(this.watchedFileLabel, new Separator(Orientation.VERTICAL), this.lastTimeStampLabel);
        bottomBar.getStyleClass().add("bottom-bar");
        setAnchor(bottomBar, null, 0.0, 0.0, 0.0);

        showRecipes();
        this.getChildren().add(this.content);

        this.getChildren().add(bottomBar);

        this.getStylesheets().add(getClass().getResource("/nl/jixxed/eliteodysseymaterials/style/style.css").toExternalForm());
    }

    private void setAnchor(final Node child, final Double topValue, final Double bottomValue, final Double leftValue, final Double rightValue) {
        AnchorPane.setTopAnchor(child, topValue);
        AnchorPane.setBottomAnchor(child, bottomValue);
        AnchorPane.setLeftAnchor(child, leftValue);
        AnchorPane.setRightAnchor(child, rightValue);
    }

    public void setWatchedFile(final String watchedFile) {
        Platform.runLater(() -> {
            this.watchedFileLabel.setText(watchedFile);
        });
    }


    public void updateIngredients() {
        this.ingredients.forEach(ingredient -> {
            switch (ingredient.getType()) {
                case ASSET -> ingredient.setAmountAvailable(this.applicationState.getAssets().get(Asset.forName(ingredient.getCode())).getTotalValue());
                case GOOD -> ingredient.setAmountAvailable(this.applicationState.getGoods().get(Good.forName(ingredient.getCode())).getTotalValue());
                case DATA -> ingredient.setAmountAvailable(this.applicationState.getData().get(Data.forName(ingredient.getCode())).getTotalValue());
                case OTHER -> {
                }
            }
        });
    }

    private void showGoods(final GridPane layout) {
        final AtomicInteger counter = new AtomicInteger(0);
        this.applicationState.getGoods().entrySet().stream().filter(getFilter()).filter(searchFilter()).filter(unknownFilter()).sorted(getSort()).forEach((entry) -> {
            final String name = entry.getKey().friendlyName();
            final MaterialCard materialCard = new MaterialCard(entry.getKey(), name, entry.getValue(), RecipeConstants.isEngineeringIngredient(entry.getKey()));
            layout.add(materialCard, counter.get() % 2, 1 + (counter.getAndIncrement() / 2));
            GridPane.setMargin(materialCard, CARD_MARGIN);
        });
        this.applicationState.getUnknownGoods().entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach((entry) -> {

            final String name = entry.getKey() + " (unknown)";
            final MaterialCard materialCard = new MaterialCard(Good.UNKNOWN, name, entry.getValue(), false);

            layout.add(materialCard, counter.get() % 2, 1 + (counter.getAndIncrement() / 2));
            GridPane.setMargin(materialCard, CARD_MARGIN);
        });
    }

    private void showAssets(final GridPane layout) {
        final AtomicInteger counter = new AtomicInteger(0);
        this.applicationState.getAssets().entrySet().stream().filter(getFilter()).filter(searchFilter()).filter(unknownFilter()).sorted(
                Comparator.comparing((Map.Entry<Asset, Storage> o) -> o.getKey().getType())
                        .thenComparing(o -> o.getKey().friendlyName()))
                .forEach((entry) -> {
                    final String name = entry.getKey().friendlyName();
                    final MaterialCard materialCard = new MaterialCard(entry.getKey(), name, entry.getValue());
                    layout.add(materialCard, 2 + counter.get() % 2, 1 + (counter.getAndIncrement() / 2));
                    GridPane.setMargin(materialCard, CARD_MARGIN);
                });
    }

    private void showDatas(final GridPane layout) {
        final AtomicInteger counter = new AtomicInteger(0);
        this.applicationState.getData().entrySet().stream().filter(getFilter()).filter(searchFilter()).filter(unknownFilter()).sorted(getSort()).forEach((entry) -> {
            final String name = entry.getKey().friendlyName();

            final MaterialCard materialCard = new MaterialCard(entry.getKey(), name, entry.getValue(), RecipeConstants.isEngineeringIngredient(entry.getKey()));
            layout.add(materialCard, 4 + counter.get() % 2, 1 + (counter.getAndIncrement() / 2));
            GridPane.setMargin(materialCard, CARD_MARGIN);
        });
        this.applicationState.getUnknownData().entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach((entry) -> {

            final String name = entry.getKey() + " (unknown)";
            final MaterialCard materialCard = new MaterialCard(Data.UNKNOWN, name, entry.getValue(), false);

            layout.add(materialCard, 4 + counter.get() % 2, 1 + (counter.getAndIncrement() / 2));
            GridPane.setMargin(materialCard, CARD_MARGIN);
        });
    }

    private Predicate<? super Map.Entry<? extends Material, Storage>> unknownFilter() {
        return (Map.Entry<? extends Material, Storage> o) -> switch (o.getKey().getClass().getSimpleName()) {
            case "Data" -> !Data.UNKNOWN.equals(o.getKey());
            case "Good" -> !Good.UNKNOWN.equals(o.getKey());
            case "Asset" -> !Asset.UNKNOWN.equals(o.getKey());
            default -> throw new IllegalStateException("Unexpected value: " + o.getKey().getClass().getName());
        };
    }

    private Predicate<? super Map.Entry<? extends Material, Storage>> searchFilter() {
        return (Map.Entry<? extends Material, Storage> o) -> this.search.isBlank() || o.getKey().friendlyName().toLowerCase().contains(this.search.toLowerCase());
    }

    private Predicate<? super Map.Entry<? extends Material, Storage>> getFilter() {
        return switch (this.applicationState.getShow()) {
            case ALL -> (Map.Entry<? extends Material, Storage> o) -> true;
            case ALL_WITH_STOCK -> (Map.Entry<? extends Material, Storage> o) -> o.getValue().getTotalValue() > 0;
            case BLUEPRINT -> (Map.Entry<? extends Material, Storage> o) -> RecipeConstants.isBlueprintIngredient(o.getKey());
            case IRRELEVANT -> (Map.Entry<? extends Material, Storage> o) -> RecipeConstants.isNotRelevantAndNotEngineeringIngredient(o.getKey());
            case IRRELEVANT_WITH_STOCK -> (Map.Entry<? extends Material, Storage> o) -> RecipeConstants.isNotRelevantAndNotEngineeringIngredient(o.getKey()) && o.getValue().getTotalValue() > 0;
            case ALL_ENGINEER -> (Map.Entry<? extends Material, Storage> o) -> RecipeConstants.isEngineeringIngredient(o.getKey());
            case REQUIRED_ENGINEER -> (Map.Entry<? extends Material, Storage> o) -> RecipeConstants.isEngineeringIngredientAndNotCompleted(o.getKey());
            case ALL_ENGINEER_BLUEPRINT -> (Map.Entry<? extends Material, Storage> o) -> RecipeConstants.isBlueprintIngredient(o.getKey()) || RecipeConstants.isEngineeringIngredient(o.getKey());
            case REQUIRED_ENGINEER_BLUEPRINT -> (Map.Entry<? extends Material, Storage> o) -> RecipeConstants.isEngineeringIngredientAndNotCompleted(o.getKey()) || RecipeConstants.isBlueprintIngredient(o.getKey());
        };
    }

    private Comparator<Map.Entry<? extends Material, Storage>> getSort() {
        return switch (this.applicationState.getSort()) {
            case ALPHABETICAL -> Comparator.comparing((Map.Entry<? extends Material, Storage> o) -> o.getKey().friendlyName());
            case RELEVANT_IRRELEVANT -> Comparator.comparing((Map.Entry<? extends Material, Storage> o) -> RecipeConstants.isEngineeringIngredient(o.getKey()) || RecipeConstants.isBlueprintIngredient(o.getKey())).reversed().thenComparing((Map.Entry<? extends Material, Storage> o) -> o.getKey().friendlyName());
            case ENGINEER_BLUEPRINT_IRRELEVANT -> Comparator.comparing((Map.Entry<? extends Material, Storage> o) -> RecipeConstants.isEngineeringIngredient(o.getKey())).thenComparing((Map.Entry<? extends Material, Storage> o) -> RecipeConstants.isBlueprintIngredient(o.getKey())).reversed().thenComparing((Map.Entry<? extends Material, Storage> o) -> o.getKey().friendlyName());
        };
    }

    public void showRecipes() {
        final List<TitledPane> titledPanes = RecipeConstants.RECIPES.entrySet().stream()
                .map(this::createCategoryTitledPane)
                .sorted(Comparator.comparing(Labeled::getText))
                .collect(Collectors.toList());
        final TitledPane about = new TitledPane("About", this.settings);
        titledPanes.add(about);
        final TitledPane legend = new TitledPane("Legend", this.legend);
        titledPanes.add(legend);
        final Accordion categoryAccordion = new Accordion(titledPanes.toArray(new TitledPane[0]));
        categoryAccordion.setExpandedPane(about);
        this.content.getChildren().add(categoryAccordion);
        setAnchor(categoryAccordion, 0.0, 0.0, 0.0, null);
        AnchorPane.setTopAnchor(categoryAccordion, 0.0);
        AnchorPane.setBottomAnchor(categoryAccordion, 0.0);
        AnchorPane.setLeftAnchor(categoryAccordion, 0.0);
    }

    private TitledPane createCategoryTitledPane(final Map.Entry<String, Map<String, ? extends Recipe>> recipesEntry) {
        final Accordion recipesAccordion = new Accordion(recipesEntry.getValue().entrySet().stream()
                .map(this::createRecipeTitledPane)
                .sorted(Comparator.comparing(Labeled::getText))
                .toArray(TitledPane[]::new));
        recipesAccordion.setPrefHeight(500);
        final TitledPane categoryTitledPane = new TitledPane(recipesEntry.getKey(), recipesAccordion);
        categoryTitledPane.getStyleClass().add("category-title-pane");
        return categoryTitledPane;
    }

    private TitledPane createRecipeTitledPane(final Map.Entry<String, ? extends Recipe> recipe) {
        final VBox content = new VBox();
        final List<Ingredient> ingredients = new ArrayList<>();
        ingredients.addAll(getRecipeIngredients(recipe, Good.class, StorageType.GOOD, this.applicationState.getGoods()));
        ingredients.addAll(getRecipeIngredients(recipe, Asset.class, StorageType.ASSET, this.applicationState.getAssets()));
        ingredients.addAll(getRecipeIngredients(recipe, Data.class, StorageType.DATA, this.applicationState.getData()));

        if (recipe.getValue() instanceof EngineerRecipe) {
            ingredients.addAll(((EngineerRecipe) recipe.getValue()).getOther().stream()
                    .map(other -> {
                                final Ingredient newIngredient = new Ingredient(other);
                                this.ingredients.add(newIngredient);
                                return newIngredient;
                            }
                    ).sorted(Comparator.comparing(Ingredient::getName))
                    .collect(Collectors.toList()));
        }
        try {
            if (!(recipe.getValue() instanceof EngineerRecipe) || ingredients.stream().noneMatch(ingredient -> StorageType.OTHER.equals(ingredient.getType()))) {
                content.getChildren().add(new FXMLLoader(getClass().getResource("IngredientHeader.fxml")).load());
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
        content.getChildren().addAll(ingredients);
        final TitledPane recipeTitledPane = createTitledPane(recipe, content);
        recipeTitledPane.getStyleClass().add("blueprint-title-pane");
        if (recipe.getValue() instanceof EngineerRecipe && ((EngineerRecipe) recipe.getValue()).isCompleted()) {
            recipeTitledPane.getStyleClass().add("completed");
        } else {
            recipeTitledPane.getStyleClass().add("regular");
        }
        recipeTitledPane.setPrefHeight(150);
        return recipeTitledPane;
    }

    private List<Ingredient> getRecipeIngredients(final Map.Entry<String, ? extends Recipe> recipe, final Class<? extends Material> materialClass, final StorageType storageType, final Map<? extends Material, Storage> materialMap) {
        return recipe.getValue().getMaterialCollection(materialClass).entrySet().stream()
                .map(material ->
                        {
                            final Ingredient newIngredient = new Ingredient(storageType, material.getKey(), material.getValue().toString(), materialMap.get(material.getKey()).getTotalValue().toString());
                            this.ingredients.add(newIngredient);
                            return newIngredient;
                        }
                ).sorted(Comparator.comparing(Ingredient::getName))
                .collect(Collectors.toList());
    }

    private TitledPane createTitledPane(final Map.Entry<String, ? extends Recipe> recipe, final VBox content) {
        final TitledPane recipeTitledPane;
        if (recipe.getValue() instanceof EngineerRecipe) {
            recipeTitledPane = new EngineerTitledPane(recipe.getKey(), content, (EngineerRecipe) recipe.getValue());
            this.engineerTitledPanes.add((EngineerTitledPane) recipeTitledPane);
        } else {
            recipeTitledPane = new TitledPane(recipe.getKey(), content);
        }
        return recipeTitledPane;
    }

    public void updateLastTimeStamp(final JsonNode journalMessage) {
        Platform.runLater(() -> {
            this.lastTimeStampLabel.setText("Latest observed relevant message: " + journalMessage.get("timestamp").asText() + " (" + journalMessage.get("event").asText() + ")");
        });
    }

    public void updateEngineerStyles() {
        this.engineerTitledPanes.forEach(titledPane -> {
            if (titledPane.getEngineerRecipe().isCompleted() && !titledPane.getStyleClass().contains("completed")) {
                titledPane.getStyleClass().add("completed");
            }
        });
    }

    private void updateTotals() {
        updateTotalsGoods();
        updateTotalsAssets();
        updateTotalsData();
    }

    private void updateTotalsData() {
        final Integer recipeDatas = this.applicationState.getData().entrySet().stream()
                .filter(data -> RecipeConstants.isBlueprintIngredient(data.getKey()))
                .map(entry -> entry.getValue().getTotalValue())
                .reduce(0, Integer::sum);
        final Integer nonRecipeDatas = this.applicationState.getData().entrySet().stream()
                .filter(data -> !RecipeConstants.isBlueprintIngredient(data.getKey()))
                .map(entry -> entry.getValue().getTotalValue())
                .reduce(0, Integer::sum);
        final Integer unknownDatas = this.applicationState.getUnknownData().size();
        final MaterialCard datasLabel = new MaterialCard("Data (Blueprint: " + recipeDatas + " / Irrelevant: " + (nonRecipeDatas + unknownDatas) + " / Total: " + (recipeDatas + nonRecipeDatas + unknownDatas) + ")", null);
        datasLabel.getStyleClass().add("category-label");
        this.materialOverview.add(datasLabel, 4, 0, 2, 1);
        GridPane.setMargin(datasLabel, CARD_MARGIN);
    }

    private void updateTotalsAssets() {
        final Integer recipeAssets = this.applicationState.getAssets().entrySet().stream()
                .filter(assetEntry -> RecipeConstants.isBlueprintIngredient(assetEntry.getKey()))
                .map(entry -> entry.getValue().getTotalValue())
                .reduce(0, Integer::sum);
        final Integer nonRecipeAssets = this.applicationState.getAssets().entrySet().stream()
                .filter(assetEntry -> !RecipeConstants.isBlueprintIngredient(assetEntry.getKey()))
                .map(entry -> entry.getValue().getTotalValue())
                .reduce(0, Integer::sum);
        final MaterialCard assetsLabel = new MaterialCard("Assets (Blueprint: " + recipeAssets + " / Irrelevant: " + nonRecipeAssets + " / Total: " + (recipeAssets + nonRecipeAssets) + ")", null);
        assetsLabel.getStyleClass().add("category-label");
        this.materialOverview.add(assetsLabel, 2, 0, 2, 1);
        GridPane.setMargin(assetsLabel, CARD_MARGIN);
    }

    private void updateTotalsGoods() {
        final Integer recipeGoods = this.applicationState.getGoods().entrySet().stream()
                .filter(good -> RecipeConstants.isBlueprintIngredient(good.getKey()))
                .map(entry -> entry.getValue().getTotalValue())
                .reduce(0, Integer::sum);
        final Integer nonRecipeGoods = this.applicationState.getGoods().entrySet().stream()
                .filter(good -> !RecipeConstants.isBlueprintIngredient(good.getKey()))
                .map(entry -> entry.getValue().getTotalValue())
                .reduce(0, Integer::sum);
        final Integer unknownGoods = this.applicationState.getUnknownGoods().size();
        final MaterialCard goodsLabel = new MaterialCard("Goods (Blueprint: " + recipeGoods + " / Irrelevant: " + (nonRecipeGoods + unknownGoods) + " / Total: " + (recipeGoods + nonRecipeGoods + unknownGoods) + ")", null);
        goodsLabel.getStyleClass().add("category-label");
        this.materialOverview.add(goodsLabel, 0, 0, 2, 1);
        GridPane.setMargin(goodsLabel, CARD_MARGIN);
    }

    public void updateGui() {
        updateIngredients();

        Platform.runLater(() -> {
            this.materialOverview.getChildren().clear();
            showGoods(this.materialOverview);
            showAssets(this.materialOverview);
            showDatas(this.materialOverview);

            this.ingredients.forEach(Ingredient::update);
            updateEngineerStyles();
            updateTotals();
        });
    }
}
