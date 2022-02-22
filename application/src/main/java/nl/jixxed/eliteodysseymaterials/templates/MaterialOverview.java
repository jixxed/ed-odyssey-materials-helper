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
import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.Search;
import nl.jixxed.eliteodysseymaterials.domain.Storage;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.*;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

class MaterialOverview extends VBox {

    private static final String FLOW_PANE_STYLE_CLASS = "flow-pane";
    private final ScrollPane scrollPane;
    private FlowPane totals;
    private FlowPane assetChemicalFlow;
    private FlowPane assetCircuitFlow;
    private FlowPane assetTechFlow;
    private FlowPane goodFlow;
    private FlowPane dataFlow;
    private MaterialTotal goodsTotal;
    private MaterialTotal assetsTotal;
    private MaterialTotal dataTotal;
    private Search currentSearch = new Search("", MaterialSort.ALPHABETICAL, MaterialShow.ALL);
    private ChangeListener<Number> resizeListener;

    MaterialOverview(final ScrollPane scrollPane) {
        this.scrollPane = scrollPane;
        initComponents();
        initEventHandling();
    }

    private void initComponents() {
        this.getStyleClass().add("material-overview");
        this.goodsTotal = new MaterialTotal(OdysseyStorageType.GOOD, MaterialTotalType.BLUEPRINT, MaterialTotalType.IRRELEVANT);
        this.assetsTotal = new MaterialTotal(OdysseyStorageType.ASSET, MaterialTotalType.CHEMICAL, MaterialTotalType.CIRCUIT, MaterialTotalType.TECH);
        this.dataTotal = new MaterialTotal(OdysseyStorageType.DATA, MaterialTotalType.BLUEPRINT, MaterialTotalType.IRRELEVANT);
        final Orientation flowPaneOrientation = MaterialOrientation.valueOf(PreferencesService.getPreference(PreferenceConstants.ORIENTATION, "HORIZONTAL")).getOrientation();
        this.totals = FlowPaneBuilder.builder().withStyleClass(FLOW_PANE_STYLE_CLASS).withOrientation(Orientation.HORIZONTAL).withNodes(this.goodsTotal, this.assetsTotal, this.dataTotal).build();
        this.assetChemicalFlow = FlowPaneBuilder.builder().withStyleClass(FLOW_PANE_STYLE_CLASS).withOrientation(flowPaneOrientation).build();
        this.assetCircuitFlow = FlowPaneBuilder.builder().withStyleClass(FLOW_PANE_STYLE_CLASS).withOrientation(flowPaneOrientation).build();
        this.assetTechFlow = FlowPaneBuilder.builder().withStyleClass(FLOW_PANE_STYLE_CLASS).withOrientation(flowPaneOrientation).build();
        this.goodFlow = FlowPaneBuilder.builder().withStyleClass(FLOW_PANE_STYLE_CLASS).withOrientation(flowPaneOrientation).build();
        this.dataFlow = FlowPaneBuilder.builder().withStyleClass(FLOW_PANE_STYLE_CLASS).withOrientation(flowPaneOrientation).build();

        this.setMaxWidth(this.scrollPane.getWidth() - 28);

        this.resizeListener = (observable, oldValue, newValue) ->
        {
            this.setMaxWidth(newValue.doubleValue() - 28);
            this.totals.setMaxWidth(newValue.doubleValue() - 28);
            this.totals.setPrefWidth(newValue.doubleValue() - 28);
            this.totals.setMinWidth(newValue.doubleValue() - 28);

            Platform.runLater(() -> {
                setFlowPaneHeight(this.goodFlow, newValue);
                setFlowPaneHeight(this.assetChemicalFlow, newValue);
                setFlowPaneHeight(this.assetCircuitFlow, newValue);
                setFlowPaneHeight(this.assetTechFlow, newValue);
                setFlowPaneHeight(this.dataFlow, newValue);
            });
        };
        this.scrollPane.widthProperty().addListener(this.resizeListener);
        this.getChildren().addAll(this.totals, this.goodFlow, this.assetChemicalFlow, this.assetCircuitFlow, this.assetTechFlow, this.dataFlow);


    }

    private void initEventHandling() {

        EventService.addListener(this, IrrelevantMaterialOverrideEvent.class, event ->
                Platform.runLater(() -> this.updateContent(this.currentSearch))
        );
        EventService.addListener(this, OrientationChangeEvent.class, orientationChangeEvent -> {
            final Orientation orientation = orientationChangeEvent.getMaterialOrientation().getOrientation();
            this.assetChemicalFlow.setOrientation(orientation);
            this.assetCircuitFlow.setOrientation(orientation);
            this.assetTechFlow.setOrientation(orientation);
            this.goodFlow.setOrientation(orientation);
            this.dataFlow.setOrientation(orientation);

            Platform.runLater(() -> this.updateContent(this.currentSearch));
        });
        EventService.addListener(this, SearchEvent.class, searchEvent -> {
            this.currentSearch = searchEvent.getSearch();
            Platform.runLater(() -> this.updateContent(this.currentSearch));
        });
        EventService.addListener(this, CommanderResetEvent.class, event -> Platform.runLater(() -> this.updateContent(this.currentSearch)));
        Observable
                .create((ObservableEmitter<JournalLineProcessedEvent> emitter) -> EventService.addListener(this, JournalLineProcessedEvent.class, journalProcessedEvent -> {
                    if (JournalEventType.BACKPACK.equals(journalProcessedEvent.getJournalEventType())
                            || JournalEventType.EMBARK.equals(journalProcessedEvent.getJournalEventType())
                            || JournalEventType.SHIPLOCKER.equals(journalProcessedEvent.getJournalEventType())) {
                        emitter.onNext(journalProcessedEvent);
                    }
                }))
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .subscribe(journalProcessedEvent -> Platform.runLater(() -> this.updateContent(this.currentSearch)));
    }

    private void setFlowPaneHeight(final FlowPane flowPane, final Number newValue) {
        flowPane.setPrefWidth(newValue.doubleValue() - 38D);
        flowPane.setMinWidth(newValue.doubleValue() - 38D);
        flowPane.setMaxWidth(newValue.doubleValue() - 38D);
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


    private void updateContent(final Search search) {
        this.assetChemicalFlow.getChildren().clear();
        this.assetTechFlow.getChildren().clear();
        this.assetCircuitFlow.getChildren().clear();
        this.goodFlow.getChildren().clear();
        this.dataFlow.getChildren().clear();
        addGoods(search);
        addAssets(search);
        addDatas(search);
        removeAndAddFlows();
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        setFlowPaneHeight(this.goodFlow, this.scrollPane.getWidth());
        setFlowPaneHeight(this.assetChemicalFlow, this.scrollPane.getWidth());
        setFlowPaneHeight(this.assetCircuitFlow, this.scrollPane.getWidth());
        setFlowPaneHeight(this.assetTechFlow, this.scrollPane.getWidth());
        setFlowPaneHeight(this.dataFlow, this.scrollPane.getWidth());

    }

    private void removeAndAddFlows() {
        this.getChildren().removeAll(this.goodFlow, this.assetChemicalFlow, this.assetCircuitFlow, this.assetTechFlow, this.dataFlow);
        for (final FlowPane flowPane : new FlowPane[]{this.goodFlow, this.assetChemicalFlow, this.assetCircuitFlow, this.assetTechFlow, this.dataFlow}) {
            if (!flowPane.getChildren().isEmpty()) {
                this.getChildren().add(flowPane);
            }
        }
    }

    private void addGoods(final Search search) {
        StorageService.getGoods().entrySet().stream()
                .filter(MaterialShow.getFilter(search))
                .filter(onSearchQuery(search))
                .filter(onKnownMaterial())
                .sorted(MaterialSort.getSort(search))
                .forEach(entry -> {
                    final MaterialCard materialCard = new MaterialCard(entry.getKey(), entry.getValue());
                    this.goodFlow.getChildren().add(materialCard);
                });
        StorageService.getUnknownGoods().entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(entry -> {
            final String name = entry.getKey() + " (unknown)";
            final MaterialCard materialCard = new MaterialCard(Good.UNKNOWN, name, entry.getValue());
            this.goodFlow.getChildren().add(materialCard);
        });
    }

    private void addAssets(final Search search) {
        StorageService.getAssets().entrySet().stream()
                .filter(MaterialShow.getFilter(search))
                .filter(onSearchQuery(search))
                .filter(onKnownMaterial())
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

    private void addDatas(final Search search) {
        StorageService.getData().entrySet().stream()
                .filter(MaterialShow.getFilter(search))
                .filter(onSearchQuery(search))
                .filter(onKnownMaterial())
                .sorted(MaterialSort.getSort(search))
                .forEach(entry -> {
                    final MaterialCard materialCard = new MaterialCard(entry.getKey(), entry.getValue());
                    this.dataFlow.getChildren().add(materialCard);
                });
        StorageService.getUnknownData().entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(entry -> {
            final String name = entry.getKey() + " (unknown)";
            final MaterialCard materialCard = new MaterialCard(Data.UNKNOWN, name, entry.getValue());
            this.dataFlow.getChildren().add(materialCard);
        });
    }

    private Predicate<? super Map.Entry<? extends OdysseyMaterial, Storage>> onKnownMaterial() {
        return (Map.Entry<? extends OdysseyMaterial, Storage> o) -> !o.getKey().isUnknown();
    }

    private Predicate<? super Map.Entry<? extends OdysseyMaterial, Storage>> onSearchQuery(final Search search) {
        return (Map.Entry<? extends OdysseyMaterial, Storage> o) -> search.getQuery().isBlank() || LocaleService.getLocalizedStringForCurrentLocale(o.getKey().getLocalizationKey()).toLowerCase().contains(search.getQuery().toLowerCase());
    }


}
