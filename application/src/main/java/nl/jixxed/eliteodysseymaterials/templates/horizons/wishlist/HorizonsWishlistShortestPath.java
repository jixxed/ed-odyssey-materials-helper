/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.templates.horizons.wishlist;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.scene.input.MouseEvent;
import lombok.extern.slf4j.Slf4j;
import nl.edomh.core.domain.*;
import nl.edomh.core.service.event.*;
import nl.edomh.core.enums.Expansion;
import nl.edomh.core.enums.HorizonsBlueprintName;
import nl.edomh.core.service.PathService;
import nl.edomh.core.service.WishlistService;
import nl.edomh.ui.shared.builder.*;
import nl.edomh.ui.shared.service.event.EngineerPinEvent;
import nl.edomh.ui.shared.service.event.HorizonsHideWishlistShortestPathItemEvent;
import nl.edomh.ui.shared.service.event.HorizonsRemoveWishlistShortestPathItemEvent;
import nl.edomh.ui.shared.templates.destroyables.*;
import nl.edomh.ui.shared.templates.generic.ShortestPathFlow;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import org.controlsfx.control.PopOver;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
public class HorizonsWishlistShortestPath extends DestroyableVBox implements DestroyableEventTemplate {


    private DestroyableResizableImageView pathHelp;
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
        register(EventService.addListener(true, this, EngineerEvent.class, _ -> update()));
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
                .withStyleClass("horizons-wishlist-path-help-popover")
                .withContent(contentNodepath)
                .withDetachable(false)
                .withHeaderAlwaysVisible(false)
                .withCornerRadius(0)
                .withArrowLocation(PopOver.ArrowLocation.TOP_LEFT)
                .build();
        popOverPath.show(this.pathHelp, event.getScreenX(), event.getScreenY());
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
