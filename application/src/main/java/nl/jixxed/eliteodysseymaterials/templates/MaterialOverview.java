package nl.jixxed.eliteodysseymaterials.templates;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.RecipeConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.Search;
import nl.jixxed.eliteodysseymaterials.domain.Storage;
import nl.jixxed.eliteodysseymaterials.enums.Asset;
import nl.jixxed.eliteodysseymaterials.enums.Data;
import nl.jixxed.eliteodysseymaterials.enums.Good;
import nl.jixxed.eliteodysseymaterials.enums.Material;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Predicate;

public class MaterialOverview extends VBox {

    private static final Insets CARD_MARGIN = new Insets(2, 5, 2, 5);
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    private final FlowPane totals;
    private final FlowPane assetChemicalFlow;
    private final FlowPane assetCircuitFlow;
    private final FlowPane assetTechFlow;
    private final FlowPane goodFlow;
    private final FlowPane dataFlow;

    public MaterialOverview() {
        final Orientation flowPaneOrientation = Orientation.HORIZONTAL;
        this.totals = new FlowPane(flowPaneOrientation);
        this.assetChemicalFlow = new FlowPane(flowPaneOrientation);
        this.assetCircuitFlow = new FlowPane(flowPaneOrientation);
        this.assetTechFlow = new FlowPane(flowPaneOrientation);
        this.goodFlow = new FlowPane(flowPaneOrientation);
        this.dataFlow = new FlowPane(flowPaneOrientation);
        setGaps(this.assetCircuitFlow, this.assetTechFlow, this.assetChemicalFlow, this.goodFlow, this.dataFlow);
        this.getChildren().addAll(this.totals, this.goodFlow, this.assetChemicalFlow, this.assetCircuitFlow, this.assetTechFlow, this.dataFlow);

    }

    private void setGaps(final FlowPane... flowPanes) {
        Arrays.stream(flowPanes).forEach(flowPane -> {
            flowPane.setHgap(4);
            flowPane.setVgap(4);
        });
    }

    void updateContent(final Search search) {
        this.totals.getChildren().clear();
        this.assetChemicalFlow.getChildren().clear();
        this.assetTechFlow.getChildren().clear();
        this.assetCircuitFlow.getChildren().clear();
        this.goodFlow.getChildren().clear();
        this.dataFlow.getChildren().clear();
        showGoods(search);
        showAssets(search);
        showDatas(search);
        updateTotals();
    }


    private void showGoods(final Search search) {
        APPLICATION_STATE.getGoods().entrySet().stream()
                .filter(getFilter(search))
                .filter(searchFilter(search))
                .filter(unknownFilter())
                .sorted(getSort(search))
                .forEach((entry) -> {
                    final String name = entry.getKey().friendlyName();
                    final MaterialCard materialCard = new MaterialCard(entry.getKey(), name, entry.getValue(), RecipeConstants.isEngineeringIngredient(entry.getKey()));
                    this.goodFlow.getChildren().add(materialCard);
                    GridPane.setMargin(materialCard, CARD_MARGIN);
                });
        APPLICATION_STATE.getUnknownGoods().entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach((entry) -> {
            final String name = entry.getKey() + " (unknown)";
            final MaterialCard materialCard = new MaterialCard(Good.UNKNOWN, name, entry.getValue(), false);
            this.goodFlow.getChildren().add(materialCard);
        });
    }

    private void showAssets(final Search search) {
        APPLICATION_STATE.getAssets().entrySet().stream()
                .filter(getFilter(search))
                .filter(searchFilter(search))
                .filter(unknownFilter())
                .sorted(Comparator.comparing((Map.Entry<Asset, Storage> o) -> o.getKey().getType())
                        .thenComparing(o -> o.getKey().friendlyName()))
                .forEach((entry) -> {
                    final String name = entry.getKey().friendlyName();
                    final MaterialCard materialCard = new MaterialCard(entry.getKey(), name, entry.getValue());
                    switch (entry.getKey().getType()) {
                        case TECH -> this.assetTechFlow.getChildren().add(materialCard);
                        case CHEMICAL -> this.assetChemicalFlow.getChildren().add(materialCard);
                        case CIRCUIT -> this.assetCircuitFlow.getChildren().add(materialCard);
                    }
                });
    }

    private void showDatas(final Search search) {
        APPLICATION_STATE.getData().entrySet().stream()
                .filter(getFilter(search))
                .filter(searchFilter(search))
                .filter(unknownFilter())
                .sorted(getSort(search))
                .forEach((entry) -> {
                    final String name = entry.getKey().friendlyName();
                    final MaterialCard materialCard = new MaterialCard(entry.getKey(), name, entry.getValue(), RecipeConstants.isEngineeringIngredient(entry.getKey()));
                    this.dataFlow.getChildren().add(materialCard);
                    GridPane.setMargin(materialCard, CARD_MARGIN);
                });
        APPLICATION_STATE.getUnknownData().entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach((entry) -> {
            final String name = entry.getKey() + " (unknown)";
            final MaterialCard materialCard = new MaterialCard(Data.UNKNOWN, name, entry.getValue(), false);
            this.dataFlow.getChildren().add(materialCard);
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

    private Predicate<? super Map.Entry<? extends Material, Storage>> searchFilter(final Search search) {
        return (Map.Entry<? extends Material, Storage> o) -> search.getQuery().isBlank() || o.getKey().friendlyName().toLowerCase().contains(search.getQuery().toLowerCase());
    }

    private Predicate<? super Map.Entry<? extends Material, Storage>> getFilter(final Search search) {
        return switch (search.getShow()) {
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

    private Comparator<Map.Entry<? extends Material, Storage>> getSort(final Search search) {
        return switch (search.getSort()) {
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
        this.totals.getChildren().add(datasLabel);
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
        this.totals.getChildren().add(assetsLabel);
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
        this.totals.getChildren().add(goodsLabel);
    }

}
