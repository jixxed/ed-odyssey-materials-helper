package nl.jixxed.eliteodysseymaterials.templates.horizons.wishlist;

import javafx.beans.binding.Bindings;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ShortestPathFlowBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsWishlist;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsWishlistBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.PathItem;
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
        this.shortestPathFlow.addBinding(this.shortestPathFlow.visibleProperty(), Bindings.greaterThan(Bindings.size(this.shortestPathFlow.getItems()), 0));
        this.shortestPathFlow.addBinding(this.shortestPathFlow.managedProperty(), Bindings.greaterThan(Bindings.size(this.shortestPathFlow.getItems()), 0));

        DestroyableLabel travelPathLabel = LabelBuilder.builder()
                .withStyleClass("title")
                .withText("tab.wishlist.travel.path")
                .withVisibilityProperty(Bindings.greaterThan(Bindings.size(this.shortestPathFlow.getItems()), 0))
                .withManagedProperty(Bindings.greaterThan(Bindings.size(this.shortestPathFlow.getItems()), 0))
                .build();

        this.getNodes().addAll(travelPathLabel, this.shortestPathFlow);
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, CommanderSelectedEvent.class, _ -> update()));
        register(EventService.addListener(true, this, HorizonsWishlistSelectedEvent.class, _ -> update()));
        register(EventService.addListener(true, this, HorizonsWishlistChangedEvent.class, _ -> update()));
        register(EventService.addListener(true, this, LocationChangedEvent.class, _ -> update()));
        register(EventService.addListener(true, this, EngineerPinEvent.class, _ -> update()));
    }

    private void update() {
        final List<PathItem<HorizonsBlueprintName>> pathItems = getPathItems();
        this.shortestPathFlow.setItems(pathItems);
        EventService.publish(new ShortestPathChangedEvent(pathItems));
    }

    @SuppressWarnings("unchecked")
    private static List<PathItem<HorizonsBlueprintName>> getPathItems() {
        final Optional<HorizonsWishlist> horizonsWishlist = ApplicationState.getInstance().getPreferredCommander()
                .map(commander -> WishlistService.getHorizonsWishlists(commander).getSelectedWishlist());
        return horizonsWishlist
                .map(wishlist -> PathService.calculateHorizonsShortestPath2((List<HorizonsWishlistBlueprint>) (List<?>) wishlist.getItems()))
                .orElse(Collections.emptyList());
    }
}
