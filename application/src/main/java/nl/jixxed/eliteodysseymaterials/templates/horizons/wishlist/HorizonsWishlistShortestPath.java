package nl.jixxed.eliteodysseymaterials.templates.horizons.wishlist;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.IntegerBinding;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ShortestPathFlowBuilder;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.service.PathService;
import nl.jixxed.eliteodysseymaterials.service.WishlistService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;
import nl.jixxed.eliteodysseymaterials.templates.generic.ShortestPathFlow;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
public class HorizonsWishlistShortestPath extends DestroyableVBox implements DestroyableEventTemplate {

    private ShortestPathFlow<HorizonsBlueprintName> shortestPathFlow;

    private static final PublishSubject<Object> updatePathItems = PublishSubject.create();
    private Disposable subscribe;
    public HorizonsWishlistShortestPath() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("shortest-path");
        final List<PathItem<HorizonsBlueprintName>> pathItems = getPathItems();

        this.shortestPathFlow = ShortestPathFlowBuilder.<HorizonsBlueprintName>builder()
                .withExpansion(Expansion.HORIZONS)
                .withPathItems(pathItems)
                .build();
        IntegerBinding size = Bindings.size(this.shortestPathFlow.getItems());
        final BooleanBinding listNotEmptyBinding = Bindings.greaterThan(size, 0);

        this.shortestPathFlow.addBinding(this.shortestPathFlow.visibleProperty(), listNotEmptyBinding);
        this.shortestPathFlow.addBinding(this.shortestPathFlow.managedProperty(), listNotEmptyBinding);

        DestroyableLabel travelPathLabel = LabelBuilder.builder()
                .withStyleClass("title")
                .withText("tab.wishlist.travel.path")
                .withVisibilityProperty(listNotEmptyBinding)
                .withManagedProperty(listNotEmptyBinding)
                .build();

        this.getNodes().addAll(travelPathLabel, this.shortestPathFlow);
        Observable<Object> debouncedFleetCarrier = updatePathItems.observeOn(Schedulers.computation());
        subscribe = debouncedFleetCarrier.subscribe(_ -> {
            final List<PathItem<HorizonsBlueprintName>> pathItems2 = getPathItems();
            Platform.runLater(() ->{
                this.shortestPathFlow.setItems(pathItems2);
                EventService.publish(new HorizonsShortestPathChangedEvent(pathItems2));
            });
        }, throwable -> log.error("Error updating path items", throwable));
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, HorizonsWishlistSelectedEvent.class, _ -> update()));
        register(EventService.addListener(true, this, HorizonsWishlistChangedEvent.class, _ -> update()));
        register(EventService.addListener(true, this, HorizonsRemoveWishlistShortestPathItemEvent.class, _ -> update()));
        register(EventService.addListener(true, this, HorizonsHideWishlistShortestPathItemEvent.class, _ -> update()));
        register(EventService.addListener(true, this, LocationChangedEvent.class, _ -> update()));
        register(EventService.addListener(true, this, EngineerPinEvent.class, _ -> update()));
    }

    private void update() {
        updatePathItems.onNext(new Object());
    }

    @SuppressWarnings("unchecked")
    private static List<PathItem<HorizonsBlueprintName>> getPathItems() {
        final Optional<HorizonsWishlist> horizonsWishlist = ApplicationState.getInstance().getPreferredCommander()
                .map(commander -> WishlistService.getHorizonsWishlists(commander).getSelectedWishlist());
        return horizonsWishlist
                .map(wishlist -> PathService.calculateHorizonsShortestPath((List<HorizonsWishlistBlueprint>) (List<?>) wishlist.getItems().stream().filter(WishlistBlueprint::isVisible).toList()))
                .orElse(Collections.emptyList());
    }

    @Override
    public void destroyInternal() {
        super.destroyInternal();
        subscribe.dispose();
    }
}
