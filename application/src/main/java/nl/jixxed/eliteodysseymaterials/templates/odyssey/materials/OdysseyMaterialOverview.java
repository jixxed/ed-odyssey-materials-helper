package nl.jixxed.eliteodysseymaterials.templates.odyssey.materials;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import lombok.Getter;
import lombok.Setter;
import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.Search;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableFlowPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.OdysseyMaterialTotals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

public class OdysseyMaterialOverview extends DestroyableVBox implements DestroyableEventTemplate {

    private static final String FLOW_PANE_STYLE_CLASS = "material-card-flow-pane";

    private OdysseyMaterialTotals totals;
    private DestroyableFlowPane assetChemicalFlow;
    private DestroyableFlowPane assetCircuitFlow;
    private DestroyableFlowPane assetTechFlow;
    private DestroyableFlowPane goodFlow;
    private DestroyableFlowPane dataFlow;
    @Setter
    private double width;
    private final Map<OdysseyMaterial, OdysseyMaterialCard> materialCards = new HashMap<>();
    private Search currentSearch = new Search("", OdysseyMaterialSort.ALPHABETICAL, OdysseyMaterialShow.ALL);
    @Getter
    private final ChangeListener<Number> resizeListener = (_, _, newValue) ->
    {
        width = newValue.doubleValue();
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
    private Disposable subscribe;


    OdysseyMaterialOverview() {
        initComponents();
        initEventHandling();
    }

    public void initComponents() {
        this.getStyleClass().addAll("material-overview");
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

        makeFlowVisibleWhenHasChildren(this.assetChemicalFlow);
        makeFlowVisibleWhenHasChildren(this.assetCircuitFlow);
        makeFlowVisibleWhenHasChildren(this.assetTechFlow);
        makeFlowVisibleWhenHasChildren(this.goodFlow);
        makeFlowVisibleWhenHasChildren(this.dataFlow);

        updateContent(currentSearch);

        this.getNodes().addAll(this.totals, this.goodFlow, this.assetChemicalFlow, this.assetCircuitFlow, this.assetTechFlow, this.dataFlow);
    }

    private void makeFlowVisibleWhenHasChildren(DestroyableFlowPane flow) {
        flow.getChildren().addListener((InvalidationListener) _ -> {
            final boolean visible = !flow.getChildren().isEmpty();
            flow.setVisible(visible);
            flow.setManaged(visible);
        });
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
        flowPane.setPrefWidth(newValue.doubleValue() - ScalingHelper.getPixelDoubleFromEm(38D / 12D));
        flowPane.setMinWidth(newValue.doubleValue() - ScalingHelper.getPixelDoubleFromEm(38D / 12D));
        flowPane.setMaxWidth(newValue.doubleValue() - ScalingHelper.getPixelDoubleFromEm(38D / 12D));
        if (Orientation.VERTICAL.equals(flowPane.getOrientation())) {
            if (!flowPane.getNodes().isEmpty()) {
                final OdysseyMaterialCard card = (OdysseyMaterialCard) flowPane.getNodes().get(0);
                final long size = flowPane.getChildren().stream().filter(Node::isVisible).count();
                final double materialCardWidth = (card.getWidth() > 0 ? card.getWidth() : card.getPrefWidth()) + ScalingHelper.getPixelDoubleFromEm(0.25);
                final double materialCardHeight = (card.getHeight() > 0 ? card.getHeight() : card.getPrefHeight()) + ScalingHelper.getPixelDoubleFromEm(0.33);
                final int cardsPerRow = Math.max(1, (int) Math.floor((newValue.doubleValue() /*- ScalingHelper.getPixelDoubleFromEm(2.0)*/) / materialCardWidth));
                final double rows = Math.ceil((double) size / cardsPerRow);
                Platform.runLater(() -> {
                    flowPane.setMaxHeight(rows * (materialCardHeight));// + ScalingHelper.getPixelDoubleFromEm(0.33)));
                    flowPane.setPrefHeight(rows * (materialCardHeight));// + ScalingHelper.getPixelDoubleFromEm(0.33)));
                    flowPane.setMinHeight(rows * (materialCardHeight));// + ScalingHelper.getPixelDoubleFromEm(0.33)));
                });
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
        this.materialCards.forEach((odysseyMaterial, odysseyMaterialCard) -> {
            final boolean visible = OdysseyMaterialShow.getFilter(search).test(odysseyMaterial) &&
                    onSearchQuery(search).test(odysseyMaterial) &&
                    onKnownMaterial().test(odysseyMaterial);
            odysseyMaterialCard.setVisible(visible);
            odysseyMaterialCard.setManaged(visible);
        });
        assetChemicalFlow.getChildren().clear();
        assetChemicalFlow.getChildren().addAll(this.materialCards.entrySet().stream()
                .filter(entry -> entry.getKey() instanceof Asset asset && asset.isType(AssetType.CHEMICAL))
                .sorted(Map.Entry.comparingByKey(OdysseyMaterialSort.getSort(currentSearch)))
                .map(Map.Entry::getValue)
                .toArray(OdysseyMaterialCard[]::new));
        assetCircuitFlow.getChildren().clear();
        assetCircuitFlow.getChildren().addAll(this.materialCards.entrySet().stream()
                .filter(entry -> entry.getKey() instanceof Asset asset && asset.isType(AssetType.CIRCUIT))
                .sorted(Map.Entry.comparingByKey(OdysseyMaterialSort.getSort(currentSearch)))
                .map(Map.Entry::getValue)
                .toArray(OdysseyMaterialCard[]::new));
        assetTechFlow.getChildren().clear();
        assetTechFlow.getChildren().addAll(this.materialCards.entrySet().stream()
                .filter(entry -> entry.getKey() instanceof Asset asset && asset.isType(AssetType.TECH))
                .sorted(Map.Entry.comparingByKey(OdysseyMaterialSort.getSort(currentSearch)))
                .map(Map.Entry::getValue)
                .toArray(OdysseyMaterialCard[]::new));
        goodFlow.getChildren().clear();
        goodFlow.getChildren().addAll(this.materialCards.entrySet().stream()
                .filter(entry -> entry.getKey() instanceof Good)
                .sorted(Map.Entry.comparingByKey(OdysseyMaterialSort.getSort(currentSearch)))
                .map(Map.Entry::getValue)
                .toArray(OdysseyMaterialCard[]::new));
        dataFlow.getChildren().clear();
        dataFlow.getChildren().addAll(this.materialCards.entrySet().stream()
                .filter(entry -> entry.getKey() instanceof Data)
                .sorted(Map.Entry.comparingByKey(OdysseyMaterialSort.getSort(currentSearch)))
                .map(Map.Entry::getValue)
                .toArray(OdysseyMaterialCard[]::new));
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        setFlowPaneHeight(this.goodFlow, width);
        setFlowPaneHeight(this.assetChemicalFlow, width);
        setFlowPaneHeight(this.assetCircuitFlow, width);
        setFlowPaneHeight(this.assetTechFlow, width);
        setFlowPaneHeight(this.dataFlow, width);

    }

//    private void removeAndAddFlows() {
//        this.getNodes().removeAll(this.goodFlow, this.assetChemicalFlow, this.assetCircuitFlow, this.assetTechFlow, this.dataFlow);
//        for (final DestroyableFlowPane flowPane : new DestroyableFlowPane[]{this.goodFlow, this.assetChemicalFlow, this.assetCircuitFlow, this.assetTechFlow, this.dataFlow}) {
//            if (!flowPane.getNodes().isEmpty()) {
//                this.getNodes().add(flowPane);
//            }
//        }
//    }

//    private void addGoods(final Search search) {
//        Arrays.stream(Good.values())
//                .filter(OdysseyMaterialShow.getFilter(search))
//                .filter(onSearchQuery(search))
//                .filter(onKnownMaterial())
//                .sorted(OdysseyMaterialSort.getSort(search))
//                .forEach(good -> {
//                    this.goodFlow.getNodes().add(this.materialCards.get(good));
//                });
//    }
//
//    private void addAssets(final Search search) {
//        Arrays.stream(Asset.values())
//                .filter(OdysseyMaterialShow.getFilter(search))
//                .filter(onSearchQuery(search))
//                .filter(onKnownMaterial())
//                .sorted(Comparator.comparing(Asset::getType)
//                        .thenComparing(OdysseyMaterialSort.getSort(search)))
//                .forEach(asset -> {
//                    final OdysseyMaterialCard materialCard = this.materialCards.get(asset);
//                    switch (asset.getType()) {
//                        case TECH -> this.assetTechFlow.getNodes().add(materialCard);
//                        case CHEMICAL -> this.assetChemicalFlow.getNodes().add(materialCard);
//                        case CIRCUIT -> this.assetCircuitFlow.getNodes().add(materialCard);
//                    }
//                });
//    }
//
//    private void addDatas(final Search search) {
//        Arrays.stream(Data.values())
//                .filter(OdysseyMaterialShow.getFilter(search))
//                .filter(onSearchQuery(search))
//                .filter(onKnownMaterial())
//                .sorted(OdysseyMaterialSort.getSort(search))
//                .forEach(data -> this.dataFlow.getNodes().add(this.materialCards.get(data)));
//    }

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
