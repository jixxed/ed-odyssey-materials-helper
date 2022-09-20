package nl.jixxed.eliteodysseymaterials.templates.odyssey.materials;

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
import nl.jixxed.eliteodysseymaterials.templates.odyssey.OdysseyMaterialTotals;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

public class OdysseyMaterialOverview extends VBox {

    private static final String FLOW_PANE_STYLE_CLASS = "material-overview-flow-pane";
    private final ScrollPane scrollPane;
    private OdysseyMaterialTotals totals;
    private FlowPane assetChemicalFlow;
    private FlowPane assetCircuitFlow;
    private FlowPane assetTechFlow;
    private FlowPane goodFlow;
    private FlowPane dataFlow;
    private final Map<OdysseyMaterial, OdysseyMaterialCard> materialCards = new HashMap<>();
    private Search currentSearch = new Search("", MaterialSort.ALPHABETICAL, MaterialShow.ALL);
    private ChangeListener<Number> resizeListener;

    OdysseyMaterialOverview(final ScrollPane scrollPane) {
        this.scrollPane = scrollPane;
        initComponents();
        initEventHandling();
    }

    private void initComponents() {
        this.getStyleClass().add("material-overview");
        final Orientation flowPaneOrientation = MaterialOrientation.valueOf(PreferencesService.getPreference(PreferenceConstants.ORIENTATION, "VERTICAL")).getOrientation();
        this.totals = new OdysseyMaterialTotals();

        Arrays.stream(Good.values()).forEach(good -> this.materialCards.put(good, new OdysseyMaterialCard((good))));
        Arrays.stream(Asset.values()).forEach(asset -> this.materialCards.put(asset, new OdysseyMaterialCard((asset))));
        Arrays.stream(Data.values()).forEach(data -> this.materialCards.put(data, new OdysseyMaterialCard((data))));

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

        EventService.addListener(this, IrrelevantMaterialOverrideEvent.class, event -> {
            Platform.runLater(() -> {
                this.updateContent(this.currentSearch);
                layoutChildren();
            });
        });
        EventService.addListener(this, OrientationChangeEvent.class, orientationChangeEvent -> {
            final Orientation orientation = orientationChangeEvent.getMaterialOrientation().getOrientation();
            this.assetChemicalFlow.setOrientation(orientation);
            this.assetCircuitFlow.setOrientation(orientation);
            this.assetTechFlow.setOrientation(orientation);
            this.goodFlow.setOrientation(orientation);
            this.dataFlow.setOrientation(orientation);
            Platform.runLater(() -> this.updateContent(this.currentSearch));
        });
        EventService.addListener(this, 1, SearchEvent.class, searchEvent -> {
            this.currentSearch = searchEvent.getSearch();
            Platform.runLater(() -> {
                this.updateContent(this.currentSearch);
                layoutChildren();
            });

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
                final OdysseyMaterialCard card = (OdysseyMaterialCard) flowPane.getChildren().get(0);
                final int size = flowPane.getChildren().size();
                final double materialCardWidth = (card.getWidth() > 0 ? card.getWidth() : card.getPrefWidth()) + 4;
                final double materialCardHeight = card.getHeight() > 0 ? card.getHeight() : card.getPrefHeight();
                final int cardsPerRow = Math.max(1, (int) Math.floor((newValue.doubleValue() - 24) / materialCardWidth));
                final double rows = Math.ceil((double) size / cardsPerRow);
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
                    this.goodFlow.getChildren().add(this.materialCards.get(entry.getKey()));
                });
    }

    private void addAssets(final Search search) {
        StorageService.getAssets().entrySet().stream()
                .filter(MaterialShow.getFilter(search))
                .filter(onSearchQuery(search))
                .filter(onKnownMaterial())
                .sorted(Comparator.comparing((Map.Entry<Asset, Storage> o) -> o.getKey().getType())
                        .thenComparing(MaterialSort.getSort(search)))
                .forEach(entry -> {
                    final OdysseyMaterialCard materialCard = this.materialCards.get(entry.getKey());
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
                    this.dataFlow.getChildren().add(this.materialCards.get(entry.getKey()));
                });
    }

    private Predicate<? super Map.Entry<? extends OdysseyMaterial, Storage>> onKnownMaterial() {
        return (Map.Entry<? extends OdysseyMaterial, Storage> o) -> !o.getKey().isUnknown();
    }

    private Predicate<? super Map.Entry<? extends OdysseyMaterial, Storage>> onSearchQuery(final Search search) {
        return (Map.Entry<? extends OdysseyMaterial, Storage> o) -> search.getQuery().isBlank() || LocaleService.getLocalizedStringForCurrentLocale(o.getKey().getLocalizationKey()).toLowerCase().contains(search.getQuery().toLowerCase());
    }


}
