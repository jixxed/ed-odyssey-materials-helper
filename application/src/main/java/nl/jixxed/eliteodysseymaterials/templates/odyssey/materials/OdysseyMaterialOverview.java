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
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
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
        this.setMaxWidth(newValue.doubleValue() - ScalingHelper.getPixelDoubleFromEm(2D));

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
        final OdysseyMaterialSort materialSort = OdysseyMaterialSort.valueOf(PreferencesService.getPreference("search.sort", "ALPHABETICAL"));
        final OdysseyMaterialShow filter = OdysseyMaterialShow.valueOf(PreferencesService.getPreference("search.filter", "ALL"));
        currentSearch.setMaterialShow(filter);
        currentSearch.setMaterialSort(materialSort);

        initComponents();
        initEventHandling();
    }

    public void initComponents() {
        this.getStyleClass().addAll("material-overview");
        final Orientation flowPaneOrientation = MaterialOrientation.valueOf(PreferencesService.getPreference(PreferenceConstants.ORIENTATION, "VERTICAL")).getOrientation();
        this.totals = new OdysseyMaterialTotals();
        this.totals.minWidthProperty().bind(maxWidthProperty());
        this.totals.maxWidthProperty().bind(maxWidthProperty());
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
        this.assetChemicalFlow.minWidthProperty().bind(maxWidthProperty());
        this.assetCircuitFlow.minWidthProperty().bind(maxWidthProperty());
        this.assetTechFlow.minWidthProperty().bind(maxWidthProperty());
        this.goodFlow.minWidthProperty().bind(maxWidthProperty());
        this.dataFlow.minWidthProperty().bind(maxWidthProperty());

        this.assetChemicalFlow.maxWidthProperty().bind(maxWidthProperty());
        this.assetCircuitFlow.maxWidthProperty().bind(maxWidthProperty());
        this.assetTechFlow.maxWidthProperty().bind(maxWidthProperty());
        this.goodFlow.maxWidthProperty().bind(maxWidthProperty());
        this.dataFlow.maxWidthProperty().bind(maxWidthProperty());

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

        register(EventService.addListener(true, this, IrrelevantMaterialOverrideEvent.class, _ ->
                Platform.runLater(() -> {
                    this.updateContent(this.currentSearch);
                    setFlowPaneHeight(this.goodFlow, width);
                    setFlowPaneHeight(this.assetChemicalFlow, width);
                    setFlowPaneHeight(this.assetCircuitFlow, width);
                    setFlowPaneHeight(this.assetTechFlow, width);
                    setFlowPaneHeight(this.dataFlow, width);
                })));
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
                setFlowPaneHeight(this.goodFlow, width);
                setFlowPaneHeight(this.assetChemicalFlow, width);
                setFlowPaneHeight(this.assetCircuitFlow, width);
                setFlowPaneHeight(this.assetTechFlow, width);
                setFlowPaneHeight(this.dataFlow, width);
            });

        }));
        register(EventService.addListener(true, this, CommanderResetEvent.class, _ -> Platform.runLater(() -> this.updateContent(this.currentSearch))));
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
                .subscribe(_ -> Platform.runLater(() -> this.updateContent(this.currentSearch)));
    }

    private void setFlowPaneHeight(final DestroyableFlowPane flowPane, final Number newValue) {

        double availableWidth = 1 + newValue.doubleValue() - ScalingHelper.getPixelDoubleFromEm(2D);

        if (Orientation.VERTICAL.equals(flowPane.getOrientation())) {
            if (!flowPane.getNodes().isEmpty()) {
                final OdysseyMaterialCard card = flowPane.getNodes().get(0);
                final long numberOfCards = flowPane.getChildren().stream().filter(Node::isVisible).count();
                final double materialCardWidth = 0.5 + (card.getWidth() > 0 ? card.getWidth() : card.getPrefWidth());
                final double materialCardHeight = 0.5 + (card.getHeight() > 0 ? card.getHeight() : card.getPrefHeight());
                int count = 0;
                while (availableWidth > materialCardWidth) {
                    availableWidth -= materialCardWidth;
                    availableWidth -= flowPane.getHgap();
                    count++;
                }
                count = Math.max(1, count);//minimum number of cards
                final double rows = Math.ceil((double) numberOfCards / count);
                double height = 1 + (rows * materialCardHeight) + ((rows - 1) * flowPane.getVgap());
                flowPane.setMaxHeight(height);
                flowPane.setPrefHeight(height);
                flowPane.setMinHeight(height);
                flowPane.setPrefWrapLength(height);
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

    private Predicate<? super OdysseyMaterial> onKnownMaterial() {
        return material -> !material.isUnknown();
    }

    private Predicate<? super OdysseyMaterial> onSearchQuery(final Search search) {
        return material -> search.getQuery().isBlank() || LocaleService.getLocalizedStringForCurrentLocale(material.getLocalizationKey()).toLowerCase().contains(search.getQuery().toLowerCase());
    }

    @Override
    public void destroyInternal() {
        super.destroyInternal();
        if (subscribe != null) {
            subscribe.dispose();
        }
    }
}
