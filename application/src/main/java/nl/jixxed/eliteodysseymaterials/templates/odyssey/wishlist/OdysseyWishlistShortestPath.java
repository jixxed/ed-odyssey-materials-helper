/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.templates.odyssey.wishlist;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.scene.input.MouseEvent;
import lombok.extern.slf4j.Slf4j;
import nl.edomh.core.service.event.*;
import nl.edomh.core.domain.ApplicationState;
import nl.edomh.core.domain.OdysseyWishlistBlueprint;
import nl.edomh.core.domain.PathItem;
import nl.edomh.core.domain.Wishlist;
import nl.edomh.core.enums.Expansion;
import nl.edomh.core.enums.OdysseyBlueprintName;
import nl.edomh.core.service.PathService;
import nl.edomh.core.service.WishlistService;
import nl.edomh.ui.shared.builder.*;
import nl.edomh.ui.shared.service.event.EngineerPinEvent;
import nl.edomh.ui.shared.service.event.HideWishlistShortestPathItemEvent;
import nl.edomh.ui.shared.service.event.RemoveWishlistShortestPathItemEvent;
import nl.edomh.ui.shared.templates.destroyables.*;
import nl.edomh.ui.shared.templates.generic.ShortestPathFlow;
import nl.edomh.ui.shared.templates.generic.ShortestPathItem;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import org.controlsfx.control.PopOver;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
public class OdysseyWishlistShortestPath extends DestroyableVBox implements DestroyableEventTemplate {

    private DestroyableResizableImageView pathHelp;
    private ShortestPathFlow<OdysseyBlueprintName> shortestPathFlow;
    private static final PublishSubject<Boolean> updatePathItems = PublishSubject.create();
    private Disposable subscribe;

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

        this.pathHelp = ResizableImageViewBuilder.builder()
                .withOnMouseClicked(this::showHelp)
                .withStyleClass("help-image")
                .withImage("nl/edomh/ui/shared/images/other/help.png")
                .build();

        DestroyableHBox titleBar = BoxBuilder.builder()
                .withStyleClass("title-bar")
                .withNodes(travelPathLabel, this.pathHelp)
                .buildHBox();

        this.getNodes().addAll(titleBar, this.shortestPathFlow);

        Observable<Boolean> debouncedFleetCarrier = updatePathItems.observeOn(Schedulers.computation());
        subscribe = debouncedFleetCarrier.subscribe(force -> {
            final List<PathItem<OdysseyBlueprintName>> pathItems2 = getPathItems();
            Platform.runLater(() -> {
                if (force || this.shortestPathFlow.getItems().size() != pathItems2.size() || !this.shortestPathFlow.getItems().stream().map(ShortestPathItem::getPathItem).allMatch(pathItems2::contains)) {
                    this.shortestPathFlow.setItems(pathItems2);
                    EventService.publish(new OdysseyShortestPathChangedEvent(pathItems2));
                }
            });
        }, throwable -> log.error("Error updating path items", throwable));
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, OdysseyWishlistSelectedEvent.class, _ -> update(true)));
        register(EventService.addListener(true, this, OdysseyWishlistChangedEvent.class, _ -> update(false)));
        register(EventService.addListener(true, this, RemoveWishlistShortestPathItemEvent.class, _ -> update(false)));
        register(EventService.addListener(true, this, HideWishlistShortestPathItemEvent.class, _ -> update(false)));
        register(EventService.addListener(true, this, LocationChangedEvent.class, _ -> update(false)));
        register(EventService.addListener(true, this, EngineerPinEvent.class, _ -> update(false)));
        register(EventService.addListener(true, this, EngineerEvent.class, _ -> update(true)));
    }

    private void showHelp(MouseEvent event) {
        DestroyableLabel hintDistance = LabelBuilder.builder()
                .withStyleClass("wishlist-path-explain")
                .withText("tab.wishlist.path.hint.distance")
                .build();
        DestroyableLabel hintPriority = LabelBuilder.builder()
                .withStyleClass("wishlist-path-explain")
                .withText("tab.wishlist.path.hint.priority")
                .build();
        final DestroyableVBox contentNodepath = BoxBuilder.builder()
                .withStyleClass("help-popover")
                .withNodes(hintDistance, hintPriority)
                .buildVBox();
        final DestroyablePopOver popOverPath = PopOverBuilder.builder()
                .withStyleClass("odyssey-wishlist-path-help-popover")
                .withContent(contentNodepath)
                .withDetachable(false)
                .withHeaderAlwaysVisible(false)
                .withCornerRadius(0)
                .withArrowLocation(PopOver.ArrowLocation.TOP_LEFT)
                .build();
        popOverPath.show(this.pathHelp, event.getScreenX(), event.getScreenY());
    }

    private void update(boolean force) {
        updatePathItems.onNext(force);
    }

    @SuppressWarnings("unchecked")
    private static List<PathItem<OdysseyBlueprintName>> getPathItems() {
        final Optional<Wishlist> odysseyWishlist = ApplicationState.getInstance().getPreferredCommander()
                .map(commander -> WishlistService.getOdysseyWishlists(commander).getSelectedWishlist());
        return odysseyWishlist
                .map(wishlist -> PathService.calculateOdysseyShortestPath((List<OdysseyWishlistBlueprint>) (List<?>) wishlist.getItems()))
                .orElse(Collections.emptyList());
    }

    @Override
    public void destroyInternal() {
        super.destroyInternal();
        subscribe.dispose();
    }
}
