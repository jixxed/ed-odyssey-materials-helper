package nl.jixxed.eliteodysseymaterials.templates;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.constants.RecipeConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.Search;
import nl.jixxed.eliteodysseymaterials.domain.Storage;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

class MaterialOverview extends VBox {

    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private final ScrollPane scrollPane;
    private final FlowPane totals;
    private final FlowPane assetChemicalFlow;
    private final FlowPane assetCircuitFlow;
    private final FlowPane assetTechFlow;
    private final FlowPane goodFlow;
    private final FlowPane dataFlow;
    private final MaterialTotal goodsTotal = new MaterialTotal(StorageType.GOOD, MaterialTotalType.BLUEPRINT, MaterialTotalType.IRRELEVANT);
    private final MaterialTotal assetsTotal = new MaterialTotal(StorageType.ASSET, MaterialTotalType.CHEMICAL, MaterialTotalType.CIRCUIT, MaterialTotalType.TECH);
    private final MaterialTotal dataTotal = new MaterialTotal(StorageType.DATA, MaterialTotalType.BLUEPRINT, MaterialTotalType.IRRELEVANT);
    private Search currentSearch = new Search("", Sort.ALPHABETICAL, Show.ALL);

    MaterialOverview(final ScrollPane scrollPane) {
        this.scrollPane = scrollPane;
        final Orientation flowPaneOrientation = MaterialOrientation.valueOf(PreferencesService.getPreference(PreferenceConstants.ORIENTATION, "HORIZONTAL")).getOrientation();
        this.totals = new FlowPane(Orientation.HORIZONTAL, this.goodsTotal, this.assetsTotal, this.dataTotal);
        this.assetChemicalFlow = new FlowPane(flowPaneOrientation);
        this.assetCircuitFlow = new FlowPane(flowPaneOrientation);
        this.assetTechFlow = new FlowPane(flowPaneOrientation);
        this.goodFlow = new FlowPane(flowPaneOrientation);
        this.dataFlow = new FlowPane(flowPaneOrientation);
        setGaps(this.totals);
        final ChangeListener<Number> resizeListener = (observable, oldValue, newValue) ->
        {
            this.totals.setMaxWidth(newValue.doubleValue() - 28);

            Platform.runLater(() -> {
                setFlowPaneHeight(this.goodFlow, newValue);
                setFlowPaneHeight(this.assetChemicalFlow, newValue);
                setFlowPaneHeight(this.assetCircuitFlow, newValue);
                setFlowPaneHeight(this.assetTechFlow, newValue);
                setFlowPaneHeight(this.dataFlow, newValue);
            });
        };
        EventService.addListener(OrientationChangeEvent.class, orientationChangeEvent -> {
            final Orientation orientation = orientationChangeEvent.getMaterialOrientation().getOrientation();
            this.assetChemicalFlow.setOrientation(orientation);
            this.assetCircuitFlow.setOrientation(orientation);
            this.assetTechFlow.setOrientation(orientation);
            this.goodFlow.setOrientation(orientation);
            this.dataFlow.setOrientation(orientation);
            if (MaterialOrientation.VERTICAL.equals(orientationChangeEvent.getMaterialOrientation())) {
                scrollPane.widthProperty().addListener(resizeListener);
                this.totals.setMaxWidth(this.getWidth());
            } else {
                scrollPane.widthProperty().removeListener(resizeListener);
                this.totals.setMaxWidth(-1);
            }
            Platform.runLater(() -> this.updateContent(this.currentSearch));
        });

        setGaps(this.assetCircuitFlow, this.assetTechFlow, this.assetChemicalFlow, this.goodFlow, this.dataFlow);
        this.getChildren().addAll(this.totals, this.goodFlow, this.assetChemicalFlow, this.assetCircuitFlow, this.assetTechFlow, this.dataFlow);
        Observable.create((ObservableEmitter<JournalProcessedEvent> emitter) -> EventService.addListener(JournalProcessedEvent.class, journalProcessedEvent -> {
            if (JournalEventType.BACKPACK.equals(journalProcessedEvent.getJournalEventType())
                    || JournalEventType.EMBARK.equals(journalProcessedEvent.getJournalEventType())
                    || JournalEventType.SHIPLOCKER.equals(journalProcessedEvent.getJournalEventType())) {
                emitter.onNext(journalProcessedEvent);
            }
        }))
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .subscribe(journalProcessedEvent -> Platform.runLater(() -> this.updateContent(this.currentSearch)));
        EventService.addListener(SearchEvent.class, searchEvent -> {
            this.currentSearch = searchEvent.getSearch();
            Platform.runLater(() -> this.updateContent(this.currentSearch));
        });
        EventService.addListener(CommanderResetEvent.class, event -> Platform.runLater(() -> this.updateContent(this.currentSearch)));

    }

    private void setFlowPaneHeight(final FlowPane flowPane, final Number newValue) {
        if (Orientation.VERTICAL.equals(flowPane.getOrientation())) {
            if (!flowPane.getChildren().isEmpty()) {
                final MaterialCard card = (MaterialCard) flowPane.getChildren().get(0);
                final int size = flowPane.getChildren().size();
                final double materialCardWidth = (card.getWidth() > 0 ? card.getWidth() : card.getPrefWidth()) + 4;
                final double materialCardHeight = card.getHeight() > 0 ? card.getHeight() : card.getPrefHeight();
                final int cardsPerRow = Math.max(1, (int) Math.floor((newValue.doubleValue() - 24) / materialCardWidth));
                final double rows = cardsPerRow != 0 ? Math.ceil((double) size / cardsPerRow) : 0;
                flowPane.setMaxHeight(rows * (materialCardHeight + 4) - 4);
                flowPane.setPrefHeight(rows * (materialCardHeight + 4) - 4);
                flowPane.setMinHeight(rows * (materialCardHeight + 4) - 4);
            }
        } else {
            resetFlowPaneHeight(flowPane);
        }
    }

    private void resetFlowPaneHeight(final FlowPane flowPane) {
        flowPane.setMaxHeight(USE_COMPUTED_SIZE);
        flowPane.setPrefHeight(USE_COMPUTED_SIZE);
        flowPane.setMinHeight(USE_COMPUTED_SIZE);
    }

    private void setGaps(final FlowPane... flowPanes) {
        Arrays.stream(flowPanes).forEach(flowPane -> {
            flowPane.setHgap(4);
            flowPane.setVgap(4);
        });
    }

    private void updateContent(final Search search) {
        this.assetChemicalFlow.getChildren().clear();
        this.assetTechFlow.getChildren().clear();
        this.assetCircuitFlow.getChildren().clear();
        this.goodFlow.getChildren().clear();
        this.dataFlow.getChildren().clear();
        showGoods(search);
        showAssets(search);
        showDatas(search);
        removeAndAddFlows();
        setFlowPaneHeight(this.goodFlow, this.scrollPane.getWidth());
        setFlowPaneHeight(this.assetChemicalFlow, this.scrollPane.getWidth());
        setFlowPaneHeight(this.assetCircuitFlow, this.scrollPane.getWidth());
        setFlowPaneHeight(this.assetTechFlow, this.scrollPane.getWidth());
        setFlowPaneHeight(this.dataFlow, this.scrollPane.getWidth());

        updateTotals();
    }

    private void removeAndAddFlows() {
        this.getChildren().removeAll(this.goodFlow, this.assetChemicalFlow, this.assetCircuitFlow, this.assetTechFlow, this.dataFlow);
        for (final FlowPane flowPane : new FlowPane[]{this.goodFlow, this.assetChemicalFlow, this.assetCircuitFlow, this.assetTechFlow, this.dataFlow}) {
            if (!flowPane.getChildren().isEmpty()) {
                this.getChildren().add(flowPane);
            }
        }
    }

    private void showGoods(final Search search) {
        APPLICATION_STATE.getGoods().entrySet().stream()
                .filter(getFilter(search))
                .filter(searchQueryFilter(search))
                .filter(knownFilter())
                .sorted(getSort(search))
                .forEach(entry -> {
                    final MaterialCard materialCard = new MaterialCard(entry.getKey(), entry.getValue(), RecipeConstants.isEngineeringIngredient(entry.getKey()));
                    this.goodFlow.getChildren().add(materialCard);
                });
        APPLICATION_STATE.getUnknownGoods().entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(entry -> {
            final String name = entry.getKey() + " (unknown)";
            final MaterialCard materialCard = new MaterialCard(Good.UNKNOWN, name, entry.getValue(), false);
            this.goodFlow.getChildren().add(materialCard);
        });
    }

    private void showAssets(final Search search) {
        APPLICATION_STATE.getAssets().entrySet().stream()
                .filter(getFilter(search))
                .filter(searchQueryFilter(search))
                .filter(knownFilter())
                .sorted(Comparator.comparing((Map.Entry<Asset, Storage> o) -> o.getKey().getType())
                        .thenComparing(o -> LocaleService.getLocalizedStringForCurrentLocale(o.getKey().getLocalizationKey())))
                .forEach(entry -> {
                    final MaterialCard materialCard = new MaterialCard(entry.getKey(), entry.getValue());
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
                .filter(searchQueryFilter(search))
                .filter(knownFilter())
                .sorted(getSort(search))
                .forEach(entry -> {
                    final MaterialCard materialCard = new MaterialCard(entry.getKey(), entry.getValue(), RecipeConstants.isEngineeringIngredient(entry.getKey()));
                    this.dataFlow.getChildren().add(materialCard);
                });
        APPLICATION_STATE.getUnknownData().entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(entry -> {
            final String name = entry.getKey() + " (unknown)";
            final MaterialCard materialCard = new MaterialCard(Data.UNKNOWN, name, entry.getValue(), false);
            this.dataFlow.getChildren().add(materialCard);
        });
    }

    private Predicate<? super Map.Entry<? extends Material, Storage>> knownFilter() {
        return (Map.Entry<? extends Material, Storage> o) -> !o.getKey().isUnknown();
    }

    private Predicate<? super Map.Entry<? extends Material, Storage>> searchQueryFilter(final Search search) {
        return (Map.Entry<? extends Material, Storage> o) -> search.getQuery().isBlank() || LocaleService.getLocalizedStringForCurrentLocale(o.getKey().getLocalizationKey()).toLowerCase().contains(search.getQuery().toLowerCase());
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
            case FAVOURITES -> (Map.Entry<? extends Material, Storage> o) -> APPLICATION_STATE.isFavourite(o.getKey());
        };
    }

    private Comparator<Map.Entry<? extends Material, Storage>> getSort(final Search search) {
        return switch (search.getSort()) {
            case ALPHABETICAL -> Comparator.comparing((Map.Entry<? extends Material, Storage> o) -> LocaleService.getLocalizedStringForCurrentLocale(o.getKey().getLocalizationKey()));
            case QUANTITY -> Comparator.comparing((Map.Entry<? extends Material, Storage> o) -> o.getValue().getTotalValue()).reversed();
            case RELEVANT_IRRELEVANT -> Comparator.comparing((Map.Entry<? extends Material, Storage> o) -> RecipeConstants.isEngineeringIngredient(o.getKey()) || RecipeConstants.isBlueprintIngredient(o.getKey())).reversed().thenComparing((Map.Entry<? extends Material, Storage> o) -> LocaleService.getLocalizedStringForCurrentLocale(o.getKey().getLocalizationKey()));
            case ENGINEER_BLUEPRINT_IRRELEVANT -> Comparator.comparing((Map.Entry<? extends Material, Storage> o) -> RecipeConstants.isEngineeringIngredient(o.getKey())).thenComparing((Map.Entry<? extends Material, Storage> o) -> RecipeConstants.isBlueprintIngredient(o.getKey())).reversed().thenComparing((Map.Entry<? extends Material, Storage> o) -> LocaleService.getLocalizedStringForCurrentLocale(o.getKey().getLocalizationKey()));
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

        this.dataTotal.update(MaterialTotalType.BLUEPRINT, recipeDatas);
        this.dataTotal.update(MaterialTotalType.IRRELEVANT, nonRecipeDatas);
    }

    private void updateTotalsAssets() {
        final Integer chemicalAssets = APPLICATION_STATE.getAssets().entrySet().stream()
                .filter(assetEntry -> AssetType.CHEMICAL.equals(assetEntry.getKey().getType()))
                .map(entry -> entry.getValue().getTotalValue())
                .reduce(0, Integer::sum);
        final Integer circuitAssets = APPLICATION_STATE.getAssets().entrySet().stream()
                .filter(assetEntry -> AssetType.CIRCUIT.equals(assetEntry.getKey().getType()))
                .map(entry -> entry.getValue().getTotalValue())
                .reduce(0, Integer::sum);
        final Integer techAssets = APPLICATION_STATE.getAssets().entrySet().stream()
                .filter(assetEntry -> AssetType.TECH.equals(assetEntry.getKey().getType()))
                .map(entry -> entry.getValue().getTotalValue())
                .reduce(0, Integer::sum);
        this.assetsTotal.update(MaterialTotalType.CHEMICAL, chemicalAssets);
        this.assetsTotal.update(MaterialTotalType.CIRCUIT, circuitAssets);
        this.assetsTotal.update(MaterialTotalType.TECH, techAssets);
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
        this.goodsTotal.update(MaterialTotalType.BLUEPRINT, recipeGoods);
        this.goodsTotal.update(MaterialTotalType.IRRELEVANT, nonRecipeGoods);
    }

}
