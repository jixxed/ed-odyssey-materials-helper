package nl.jixxed.eliteodysseymaterials.templates.horizons.commodities;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.domain.CommoditiesSearch;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsCommoditiesSearchEvent;
import nl.jixxed.eliteodysseymaterials.service.event.MarketUpdatedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableFlowPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class HorizonsCommoditiesOverview extends DestroyableVBox implements DestroyableEventTemplate {
    private HorizonsCommodityCard[] commodityCards;
    private CommoditiesSearch currentSearch = new CommoditiesSearch("", HorizonsCommoditiesSort.ALPHABETICAL, HorizonsCommoditiesShow.ALL);
    private List<DestroyableFlowPane> flows = new ArrayList<>();
    private Disposable subscribe;

    HorizonsCommoditiesOverview() {
        final HorizonsCommoditiesSort materialSort = HorizonsCommoditiesSort.valueOf(PreferencesService.getPreference("search.commodities.sort", "ALPHABETICAL"));
        final HorizonsCommoditiesShow filter = HorizonsCommoditiesShow.valueOf(PreferencesService.getPreference("search.commodities.filter", "ALL"));
        currentSearch.setCommoditiesShow(filter);
        currentSearch.setCommoditiesSort(materialSort);

        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("horizons-commodities-overview");
        this.commodityCards = Stream.concat(Arrays.stream(RegularCommodity.values()), Arrays.stream(RareCommodity.values()))
                .filter(Predicate.not(Commodity::isUnknown))
                .map(HorizonsCommodityCard::new)
                .toArray(HorizonsCommodityCard[]::new);
        initCards();
        update();
    }

    private void initCards() {
        final List<DestroyableFlowPane> rows = Arrays.stream(CommodityType.values())
                .filter(Predicate.not(CommodityType::isUnknown)).map(commodityType -> {
                    final HorizonsCommodityCard[] array = Arrays.stream(this.commodityCards)
                            .filter(card -> card.getCommodity().getCommodityType().equals(commodityType))
                            .toArray(HorizonsCommodityCard[]::new);
                    return createCommodityCardRow(commodityType, array);
                }).toList();
        this.getNodes().addAll(rows);
    }

    private void update() {
        flows.forEach(flowPane -> {
            final List<HorizonsCommodityCard> cards = flowPane.getChildren().stream()
                    .map(HorizonsCommodityCard.class::cast)
                    .sorted(HorizonsCommoditiesSort.getSort(this.currentSearch))
                    .toList();
            flowPane.getChildren().clear();
            flowPane.getChildren().addAll(cards);
        });
    }

    private DestroyableFlowPane createCommodityCardRow(final CommodityType type, final HorizonsCommodityCard[] array) {
        final DestroyableFlowPane commodities = FlowPaneBuilder.builder()
                .withStyleClass("material-card-flow-pane")
                .withNodes(array)
                .build();
        flows.add(commodities);
        makeFlowVisibleWhenHasChildren(commodities);
        HBox.setHgrow(commodities, Priority.ALWAYS);
//        final DestroyableHBox row = BoxBuilder.builder()
//                .withStyleClass("category-row")
//                .withNodes(commodities)
//                .buildHBox();
//        row.addBinding(row.visibleProperty(), commodities.visibleProperty());
//        row.addBinding(row.managedProperty(), commodities.managedProperty());
        return commodities;
    }

    private void makeFlowVisibleWhenHasChildren(DestroyableFlowPane flow) {
        flow.getChildren().addListener((InvalidationListener) _ -> {
            final boolean visible = flow.getChildren().stream().anyMatch(Node::isVisible);
            flow.setVisible(visible);
            flow.setManaged(visible);
        });
    }

    @Override
    public void initEventHandling() {

        subscribe = Observable
                .create(emitter -> register(EventService.addListener(true, this, StorageEvent.class, storageEvent -> {
                    if (StoragePool.SHIP.equals(storageEvent.getStoragePool()) || StoragePool.FLEETCARRIER.equals(storageEvent.getStoragePool())) {
                        emitter.onNext(storageEvent);
                    }
                })))
                .debounce(250, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .subscribe(_ -> Platform.runLater(this::update));

        register(EventService.addListener(true, this, HorizonsCommoditiesSearchEvent.class, horizonsCommoditiesSearchEvent -> {
            this.currentSearch = horizonsCommoditiesSearchEvent.getSearch();
            this.update();
        }));
        register(EventService.addListener(true, this, MarketUpdatedEvent.class, _ -> this.update()));
        register(EventService.addListener(true, this, StorageEvent.class, _ -> this.update()));
    }

    @Override
    public void destroyInternal() {
        super.destroyInternal();
        subscribe.dispose();
    }
}
