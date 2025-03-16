package nl.jixxed.eliteodysseymaterials.templates.odyssey.materials;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Orientation;
import javafx.scene.layout.FlowPane;
import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.Search;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableFlowPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableScrollPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.OdysseyMaterialTotals;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

public class OdysseyMaterialOverview extends DestroyableVBox implements DestroyableEventTemplate {

    private static final String FLOW_PANE_STYLE_CLASS = "material-overview-flow-pane";
    private final DestroyableScrollPane scrollPane;
    private OdysseyMaterialTotals totals;
    private DestroyableFlowPane assetChemicalFlow;
    private DestroyableFlowPane assetCircuitFlow;
    private DestroyableFlowPane assetTechFlow;
    private DestroyableFlowPane goodFlow;
    private DestroyableFlowPane dataFlow;
    private final Map<OdysseyMaterial, OdysseyMaterialCard> materialCards = new HashMap<>();
    private Search currentSearch = new Search("", OdysseyMaterialSort.ALPHABETICAL, OdysseyMaterialShow.ALL);
    private ChangeListener<Number> resizeListener;
    private Disposable subscribe;


    OdysseyMaterialOverview(final DestroyableScrollPane scrollPane) {
        this.scrollPane = scrollPane;
        initComponents();
        initEventHandling();
    }

    public void initComponents() {
        this.getStyleClass().add("material-overview");
        final Orientation flowPaneOrientation = MaterialOrientation.valueOf(PreferencesService.getPreference(PreferenceConstants.ORIENTATION, "VERTICAL")).getOrientation();
        this.totals = new OdysseyMaterialTotals();

        Arrays.stream(Good.values()).forEach(good -> this.materialCards.put(good, new OdysseyMaterialCard((good))));
        Arrays.stream(Asset.values()).forEach(asset -> this.materialCards.put(asset, new OdysseyMaterialCard((asset))));
        Arrays.stream(Data.values()).forEach(data -> this.materialCards.put(data, new OdysseyMaterialCard((data))));

        this.assetChemicalFlow = FlowPaneBuilder.builder()
                .withStyleClass(FLOW_PANE_STYLE_CLASS)
                .withOrientation(flowPaneOrientation)
                .build();
        this.assetCircuitFlow = FlowPaneBuilder.builder()
                .withStyleClass(FLOW_PANE_STYLE_CLASS)
                .withOrientation(flowPaneOrientation)
                .build();
        this.assetTechFlow = FlowPaneBuilder.builder()
                .withStyleClass(FLOW_PANE_STYLE_CLASS)
                .withOrientation(flowPaneOrientation)
                .build();
        this.goodFlow = FlowPaneBuilder.builder()
                .withStyleClass(FLOW_PANE_STYLE_CLASS)
                .withOrientation(flowPaneOrientation)
                .build();
        this.dataFlow = FlowPaneBuilder.builder()
                .withStyleClass(FLOW_PANE_STYLE_CLASS)
                .withOrientation(flowPaneOrientation)
                .build();

        this.setMaxWidth(this.scrollPane.getWidth() - 28);

        this.resizeListener = (_, _, newValue) ->
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
        this.getNodes().addAll(this.totals, this.goodFlow, this.assetChemicalFlow, this.assetCircuitFlow, this.assetTechFlow, this.dataFlow);
    }

    public void initEventHandling() {

        register(EventService.addListener(true, this, IrrelevantMaterialOverrideEvent.class, event -> {
            Platform.runLater(() -> {
                this.updateContent(this.currentSearch);
                layoutChildren();
            });
        }));
        register(EventService.addListener(true, this, OrientationChangeEvent.class, orientationChangeEvent -> {
            final Orientation orientation = orientationChangeEvent.getMaterialOrientation().getOrientation();
            this.assetChemicalFlow.setOrientation(orientation);
            this.assetCircuitFlow.setOrientation(orientation);
            this.assetTechFlow.setOrientation(orientation);
            this.goodFlow.setOrientation(orientation);
            this.dataFlow.setOrientation(orientation);
            Platform.runLater(() -> this.updateContent(this.currentSearch));
        }));
        register(EventService.addListener(true, this, 1, SearchEvent.class, searchEvent -> {
            this.currentSearch = searchEvent.getSearch();
            Platform.runLater(() -> {
                this.updateContent(this.currentSearch);
                layoutChildren();
            });

        }));
        register(EventService.addListener(true, this, CommanderResetEvent.class, event -> Platform.runLater(() -> this.updateContent(this.currentSearch))));
        subscribe = Observable
                .create((ObservableEmitter<JournalLineProcessedEvent> emitter) -> register(EventService.addListener(true, this, JournalLineProcessedEvent.class, journalProcessedEvent -> {
                    if (JournalEventType.BACKPACK.equals(journalProcessedEvent.getJournalEventType())
                            || JournalEventType.EMBARK.equals(journalProcessedEvent.getJournalEventType())
                            || JournalEventType.SHIPLOCKER.equals(journalProcessedEvent.getJournalEventType())) {
                        emitter.onNext(journalProcessedEvent);
                    }
                })))
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .subscribe(journalProcessedEvent -> Platform.runLater(() -> this.updateContent(this.currentSearch)));
    }

    private void setFlowPaneHeight(final DestroyableFlowPane flowPane, final Number newValue) {
        flowPane.setPrefWidth(newValue.doubleValue() - 38D);
        flowPane.setMinWidth(newValue.doubleValue() - 38D);
        flowPane.setMaxWidth(newValue.doubleValue() - 38D);
        if (Orientation.VERTICAL.equals(flowPane.getOrientation())) {
            if (!flowPane.getNodes().isEmpty()) {
                final OdysseyMaterialCard card = (OdysseyMaterialCard) flowPane.getNodes().get(0);
                final int size = flowPane.getNodes().size();
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
        this.assetChemicalFlow.getNodes().clear();
        this.assetTechFlow.getNodes().clear();
        this.assetCircuitFlow.getNodes().clear();
        this.goodFlow.getNodes().clear();
        this.dataFlow.getNodes().clear();
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
        this.getNodes().removeAll(this.goodFlow, this.assetChemicalFlow, this.assetCircuitFlow, this.assetTechFlow, this.dataFlow);
        for (final DestroyableFlowPane flowPane : new DestroyableFlowPane[]{this.goodFlow, this.assetChemicalFlow, this.assetCircuitFlow, this.assetTechFlow, this.dataFlow}) {
            if (!flowPane.getNodes().isEmpty()) {
                this.getNodes().add(flowPane);
            }
        }
    }

    private void addGoods(final Search search) {
        Arrays.stream(Good.values())
                .filter(OdysseyMaterialShow.getFilter(search))
                .filter(onSearchQuery(search))
                .filter(onKnownMaterial())
                .sorted(OdysseyMaterialSort.getSort(search))
                .forEach(good -> {
                    this.goodFlow.getNodes().add(this.materialCards.get(good));
                });
    }

    private void addAssets(final Search search) {
        Arrays.stream(Asset.values())
                .filter(OdysseyMaterialShow.getFilter(search))
                .filter(onSearchQuery(search))
                .filter(onKnownMaterial())
                .sorted(Comparator.comparing(Asset::getType)
                        .thenComparing(OdysseyMaterialSort.getSort(search)))
                .forEach(asset -> {
                    final OdysseyMaterialCard materialCard = this.materialCards.get(asset);
                    switch (asset.getType()) {
                        case TECH -> this.assetTechFlow.getNodes().add(materialCard);
                        case CHEMICAL -> this.assetChemicalFlow.getNodes().add(materialCard);
                        case CIRCUIT -> this.assetCircuitFlow.getNodes().add(materialCard);
                    }
                });
    }

    private void addDatas(final Search search) {
        Arrays.stream(Data.values())
                .filter(OdysseyMaterialShow.getFilter(search))
                .filter(onSearchQuery(search))
                .filter(onKnownMaterial())
                .sorted(OdysseyMaterialSort.getSort(search))
                .forEach(data -> this.dataFlow.getNodes().add(this.materialCards.get(data)));
    }

    private Predicate<? super OdysseyMaterial> onKnownMaterial() {
        return material -> !material.isUnknown();
    }

    private Predicate<? super OdysseyMaterial> onSearchQuery(final Search search) {
        return material -> search.getQuery().isBlank() || LocaleService.getLocalizedStringForCurrentLocale(material.getLocalizationKey()).toLowerCase().contains(search.getQuery().toLowerCase());
    }

    @Override
    public void destroyInternal() {
        if (subscribe != null) {
            subscribe.dispose();
        }
    }
}
