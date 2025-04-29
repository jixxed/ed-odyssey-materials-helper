package nl.jixxed.eliteodysseymaterials.templates.odyssey.wishlist;

import javafx.beans.binding.Bindings;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ShortestPathFlowBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.OdysseyWishlistBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.PathItem;
import nl.jixxed.eliteodysseymaterials.domain.Wishlist;
import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyBlueprintName;
import nl.jixxed.eliteodysseymaterials.service.PathService;
import nl.jixxed.eliteodysseymaterials.service.WishlistService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;
import nl.jixxed.eliteodysseymaterials.templates.generic.ShortestPathFlow;
import nl.jixxed.eliteodysseymaterials.templates.generic.ShortestPathItem;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
public class OdysseyWishlistShortestPath extends DestroyableVBox implements DestroyableEventTemplate {

    private ShortestPathFlow<OdysseyBlueprintName> shortestPathFlow;

    public OdysseyWishlistShortestPath() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("shortest-path");
        final List<PathItem<OdysseyBlueprintName>> pathItems = getPathItems();

        this.shortestPathFlow = ShortestPathFlowBuilder.<OdysseyBlueprintName>builder()
                .withExpansion(Expansion.ODYSSEY)
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
        register(EventService.addListener(true, this, OdysseyWishlistSelectedEvent.class, _ -> update()));
        register(EventService.addListener(true, this, OdysseyWishlistChangedEvent.class, _ -> update()));
        register(EventService.addListener(true, this, LocationChangedEvent.class, _ -> update()));
        register(EventService.addListener(true, this, EngineerPinEvent.class, _ -> update()));
    }

    private void update() {
//        final List<PathItem<OdysseyBlueprintName>> pathItems = getPathItems();
//        this.shortestPathFlow.setItems(pathItems);
//        EventService.publish(new OdysseyShortestPathChangedEvent(pathItems));
        final List<PathItem<OdysseyBlueprintName>> pathItems = getPathItems();
        if (this.shortestPathFlow.getItems().size() != pathItems.size() || !this.shortestPathFlow.getItems().stream().map(ShortestPathItem::getPathItem).allMatch(pathItems::contains)) {
            this.shortestPathFlow.setItems(pathItems);
            EventService.publish(new OdysseyShortestPathChangedEvent(pathItems));
        }
    }

    @SuppressWarnings("unchecked")
    private static List<PathItem<OdysseyBlueprintName>> getPathItems() {
        final Optional<Wishlist> odysseyWishlist = ApplicationState.getInstance().getPreferredCommander()
                .map(commander -> WishlistService.getOdysseyWishlists(commander).getSelectedWishlist());
        return odysseyWishlist
                .map(wishlist -> PathService.calculateOdysseyShortestPath((List<OdysseyWishlistBlueprint>) (List<?>) wishlist.getItems()))
                .orElse(Collections.emptyList());
    }
}
