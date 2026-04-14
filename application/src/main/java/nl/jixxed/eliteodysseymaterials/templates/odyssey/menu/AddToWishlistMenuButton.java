/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.templates.odyssey.menu;

import javafx.collections.ListChangeListener;
import javafx.css.PseudoClass;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import nl.jixxed.eliteodysseymaterials.builder.MenuItemBuilder;
import nl.jixxed.eliteodysseymaterials.domain.Commander;
import nl.jixxed.eliteodysseymaterials.domain.OdysseyWishlistBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.Wishlist;
import nl.jixxed.eliteodysseymaterials.domain.Wishlists;
import nl.jixxed.eliteodysseymaterials.enums.Action;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyBlueprintName;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.WishlistService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.OdysseyWishlistBlueprintEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableMenuButton;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableMenuItem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AddToWishlistMenuButton extends DestroyableMenuButton implements DestroyableEventTemplate {

    private ListChangeListener<MenuItem> menuItemListChangeListener;

    public AddToWishlistMenuButton() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("wishlist-button");
        this.addBinding(this.textProperty(), LocaleService.getStringBinding("blueprint.add.to.wishlist"));
        this.getItems().addAll(new ArrayList<>());
        menuItemListChangeListener = c ->
                this.pseudoClassStateChanged(PseudoClass.getPseudoClass("hidden-menu"), c.getList().size() <= 1);
        this.getItems().addListener(menuItemListChangeListener);
        this.addEventBinding(this.onMouseClickedProperty(), event -> {
            if (this.getItems().size() == 1) {
                this.getItems().getFirst().fire();
                event.consume();
            }
        });
        this.registerEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            if (this.getItems().size() == 1) {
                event.consume();
            }
        });
        this.registerEventFilter(MouseEvent.MOUSE_RELEASED, event -> {
            if (this.getItems().size() == 1) {
                event.consume();
            }
        });
    }

    @Override
    public void initEventHandling() {

    }

    public void loadCommanderWishlists(final Commander commander, OdysseyBlueprintName odysseyBlueprintName) {
        final Wishlists wishlists = WishlistService.getOdysseyWishlists(commander);
        this.clear();
        final List<DestroyableMenuItem> menuItems = wishlists.getAllWishlists().stream()
                .filter(wishlist -> wishlist != Wishlist.ALL)
                .sorted(Comparator.comparing(Wishlist::getName))
                .map(wishlist -> MenuItemBuilder.builder()
                        .withNonLocalizedText(wishlist.getName())
                        .withOnAction(event -> EventService.publish(new OdysseyWishlistBlueprintEvent(commander, wishlist.getUuid(), List.of(new OdysseyWishlistBlueprint(odysseyBlueprintName)), Action.ADDED)))
                        .build())
                .toList();
        this.addAll(menuItems);
    }

    @Override
    public void destroyInternal() {
        super.destroyInternal();
        this.getItems().removeListener(menuItemListChangeListener);
    }
}
