package nl.jixxed.eliteodysseymaterials.templates;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import nl.jixxed.eliteodysseymaterials.RecipeConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.Storage;
import nl.jixxed.eliteodysseymaterials.enums.Asset;
import nl.jixxed.eliteodysseymaterials.enums.Data;
import nl.jixxed.eliteodysseymaterials.enums.Good;
import nl.jixxed.eliteodysseymaterials.enums.Material;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

public class ContentArea extends AnchorPane {
    private static final Insets CARD_MARGIN = new Insets(2, 5, 2, 5);
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private final GridPane materialOverview = new GridPane();
    private final SearchBar searchBar;
    private final RecipeBar recipeBar;

    public ContentArea(final Application application) {
        super();

        for (int i = 0; i < 5; i++) {
            final ColumnConstraints column = new ColumnConstraints(250);
            this.materialOverview.getColumnConstraints().add(column);
        }
        this.materialOverview.getStyleClass().add("card-grid");


        this.recipeBar = new RecipeBar(application);
        this.getChildren().add(this.recipeBar);
        setAnchor(this.recipeBar, 0.0, 0.0, 0.0, null);


        this.searchBar = new SearchBar((searchQuery) -> updateContent(), (show) -> updateContent(), (sort) -> updateContent());
        final ScrollPane scrollPane = new ScrollPane();
        scrollPane.pannableProperty().set(true);
        scrollPane.setContent(this.materialOverview);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        final VBox body = new VBox(this.searchBar, scrollPane);
        this.getChildren().add(body);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);//Added this line
        setAnchor(body, 0.0, 0.0, 372.0, 0.0);


//        setAnchor(scrollPane, 40.0, 25.0, 0.0, 0.0);
//        setAnchor(scrollPane, 0.0, 0.0, 372.0, 0.0);

    }

    private void setAnchor(final Node child, final Double topValue, final Double bottomValue, final Double leftValue, final Double rightValue) {
        AnchorPane.setTopAnchor(child, topValue);
        AnchorPane.setBottomAnchor(child, bottomValue);
        AnchorPane.setLeftAnchor(child, leftValue);
        AnchorPane.setRightAnchor(child, rightValue);
    }


    private void updateContent() {
        Platform.runLater(() -> {
            this.materialOverview.getChildren().clear();
            showGoods(this.materialOverview);
            showAssets(this.materialOverview);
            showDatas(this.materialOverview);
            updateTotals();
        });
    }

    private void showGoods(final GridPane layout) {
        final AtomicInteger counter = new AtomicInteger(0);
        APPLICATION_STATE.getGoods().entrySet().stream().filter(getFilter()).filter(searchFilter()).filter(unknownFilter()).sorted(getSort()).forEach((entry) -> {
            final String name = entry.getKey().friendlyName();
            final MaterialCard materialCard = new MaterialCard(entry.getKey(), name, entry.getValue(), RecipeConstants.isEngineeringIngredient(entry.getKey()));
            layout.add(materialCard, counter.get() % 2, 1 + (counter.getAndIncrement() / 2));
            GridPane.setMargin(materialCard, CARD_MARGIN);
        });
        APPLICATION_STATE.getUnknownGoods().entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach((entry) -> {

            final String name = entry.getKey() + " (unknown)";
            final MaterialCard materialCard = new MaterialCard(Good.UNKNOWN, name, entry.getValue(), false);

            layout.add(materialCard, counter.get() % 2, 1 + (counter.getAndIncrement() / 2));
            GridPane.setMargin(materialCard, CARD_MARGIN);
        });
    }

    private void showAssets(final GridPane layout) {
        final AtomicInteger counter = new AtomicInteger(0);
        APPLICATION_STATE.getAssets().entrySet().stream().filter(getFilter()).filter(searchFilter()).filter(unknownFilter()).sorted(
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
        APPLICATION_STATE.getData().entrySet().stream().filter(getFilter()).filter(searchFilter()).filter(unknownFilter()).sorted(getSort()).forEach((entry) -> {
            final String name = entry.getKey().friendlyName();

            final MaterialCard materialCard = new MaterialCard(entry.getKey(), name, entry.getValue(), RecipeConstants.isEngineeringIngredient(entry.getKey()));
            layout.add(materialCard, 4 + counter.get() % 2, 1 + (counter.getAndIncrement() / 2));
            GridPane.setMargin(materialCard, CARD_MARGIN);
        });
        APPLICATION_STATE.getUnknownData().entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach((entry) -> {

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
        return (Map.Entry<? extends Material, Storage> o) -> this.searchBar.getQuery().isBlank() || o.getKey().friendlyName().toLowerCase().contains(this.searchBar.getQuery().toLowerCase());
    }

    private Predicate<? super Map.Entry<? extends Material, Storage>> getFilter() {
        return switch (APPLICATION_STATE.getShow()) {
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
        return switch (APPLICATION_STATE.getSort()) {
            case ALPHABETICAL -> Comparator.comparing((Map.Entry<? extends Material, Storage> o) -> o.getKey().friendlyName());
            case RELEVANT_IRRELEVANT -> Comparator.comparing((Map.Entry<? extends Material, Storage> o) -> RecipeConstants.isEngineeringIngredient(o.getKey()) || RecipeConstants.isBlueprintIngredient(o.getKey())).reversed().thenComparing((Map.Entry<? extends Material, Storage> o) -> o.getKey().friendlyName());
            case ENGINEER_BLUEPRINT_IRRELEVANT -> Comparator.comparing((Map.Entry<? extends Material, Storage> o) -> RecipeConstants.isEngineeringIngredient(o.getKey())).thenComparing((Map.Entry<? extends Material, Storage> o) -> RecipeConstants.isBlueprintIngredient(o.getKey())).reversed().thenComparing((Map.Entry<? extends Material, Storage> o) -> o.getKey().friendlyName());
        };
    }

    private void updateTotals() {
        updateTotalsGoods();
        updateTotalsAssets();
        updateTotalsData();
    }

    private void updateTotalsData() {
        final Integer recipeDatas = APPLICATION_STATE.getData().entrySet().stream()
                .filter(data -> RecipeConstants.isBlueprintIngredient(data.getKey()))
                .map(entry -> entry.getValue().getTotalValue())
                .reduce(0, Integer::sum);
        final Integer nonRecipeDatas = APPLICATION_STATE.getData().entrySet().stream()
                .filter(data -> !RecipeConstants.isBlueprintIngredient(data.getKey()))
                .map(entry -> entry.getValue().getTotalValue())
                .reduce(0, Integer::sum);
        final Integer unknownDatas = APPLICATION_STATE.getUnknownData().size();
        final MaterialCard datasLabel = new MaterialCard("Data (Blueprint: " + recipeDatas + " / Irrelevant: " + (nonRecipeDatas + unknownDatas) + " / Total: " + (recipeDatas + nonRecipeDatas + unknownDatas) + ")", null);
        datasLabel.getStyleClass().add("category-label");
        this.materialOverview.add(datasLabel, 4, 0, 2, 1);
        GridPane.setMargin(datasLabel, CARD_MARGIN);
    }

    private void updateTotalsAssets() {
        final Integer recipeAssets = APPLICATION_STATE.getAssets().entrySet().stream()
                .filter(assetEntry -> RecipeConstants.isBlueprintIngredient(assetEntry.getKey()))
                .map(entry -> entry.getValue().getTotalValue())
                .reduce(0, Integer::sum);
        final Integer nonRecipeAssets = APPLICATION_STATE.getAssets().entrySet().stream()
                .filter(assetEntry -> !RecipeConstants.isBlueprintIngredient(assetEntry.getKey()))
                .map(entry -> entry.getValue().getTotalValue())
                .reduce(0, Integer::sum);
        final MaterialCard assetsLabel = new MaterialCard("Assets (Blueprint: " + recipeAssets + " / Irrelevant: " + nonRecipeAssets + " / Total: " + (recipeAssets + nonRecipeAssets) + ")", null);
        assetsLabel.getStyleClass().add("category-label");
        this.materialOverview.add(assetsLabel, 2, 0, 2, 1);
        GridPane.setMargin(assetsLabel, CARD_MARGIN);
    }

    private void updateTotalsGoods() {
        final Integer recipeGoods = APPLICATION_STATE.getGoods().entrySet().stream()
                .filter(good -> RecipeConstants.isBlueprintIngredient(good.getKey()))
                .map(entry -> entry.getValue().getTotalValue())
                .reduce(0, Integer::sum);
        final Integer nonRecipeGoods = APPLICATION_STATE.getGoods().entrySet().stream()
                .filter(good -> !RecipeConstants.isBlueprintIngredient(good.getKey()))
                .map(entry -> entry.getValue().getTotalValue())
                .reduce(0, Integer::sum);
        final Integer unknownGoods = APPLICATION_STATE.getUnknownGoods().size();
        final MaterialCard goodsLabel = new MaterialCard("Goods (Blueprint: " + recipeGoods + " / Irrelevant: " + (nonRecipeGoods + unknownGoods) + " / Total: " + (recipeGoods + nonRecipeGoods + unknownGoods) + ")", null);
        goodsLabel.getStyleClass().add("category-label");
        this.materialOverview.add(goodsLabel, 0, 0, 2, 1);
        GridPane.setMargin(goodsLabel, CARD_MARGIN);
    }

    public void updateGui() {
        this.recipeBar.updateIngredientsValues();
        updateContent();
        this.recipeBar.updateIngredients();
        this.recipeBar.updateEngineerStyles();
    }
}
