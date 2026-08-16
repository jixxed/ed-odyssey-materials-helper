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

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import lombok.extern.slf4j.Slf4j;
import nl.edomh.core.service.event.*;
import nl.edomh.ui.shared.builder.BoxBuilder;
import nl.edomh.ui.shared.builder.LabelBuilder;
import nl.edomh.ui.shared.builder.ScrollPaneBuilder;
import nl.edomh.core.domain.ApplicationState;
import nl.edomh.core.domain.OdysseyWishlistBlueprint;
import nl.edomh.core.enums.OdysseyTabType;
import nl.edomh.core.service.LocaleService;
import nl.edomh.core.service.WishlistService;
import nl.edomh.ui.shared.service.event.HideWishlistShortestPathItemEvent;
import nl.jixxed.eliteodysseymaterials.service.event.OdysseyWishlistSelectedEvent;
import nl.edomh.ui.shared.service.event.RemoveWishlistShortestPathItemEvent;
import nl.edomh.ui.shared.templates.destroyables.DestroyableEventTemplate;
import nl.edomh.ui.shared.templates.destroyables.DestroyableLabel;
import nl.edomh.ui.shared.templates.destroyables.DestroyableVBox;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.OdysseyMaterialTotals;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.OdysseyTab;

@Slf4j
public class OdysseyWishlistTab extends OdysseyTab implements DestroyableEventTemplate {

    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private int wishlistSize;
    private DestroyableLabel noBlueprint;
    private OdysseyWishlistBlueprints odysseyWishlistBlueprints;
    private OdysseyWishlistMaterials odysseyWishlistMaterials;
    private OdysseyWishlistShortestPath odysseyWishlistShortestPath;
    private OdysseyMaterialTotals totals;

    public OdysseyWishlistTab() {
        initComponents();
        initEventHandling();
    }

    @SuppressWarnings("unchecked")
    public void initComponents() {
        this.getStyleClass().add("wishlist-tab");


        this.wishlistSize = APPLICATION_STATE.getPreferredCommander().map(commander -> WishlistService.getOdysseyWishlists(commander).getSelectedWishlist().getItems().stream().mapToInt(OdysseyWishlistBlueprint::getQuantity).sum()).orElse(0);
        this.addBinding(this.textProperty(), LocaleService.getSupplierStringBinding("tabs.wishlist", () -> (this.wishlistSize > 0) ? " (" + this.wishlistSize + ")" : ""));

        this.noBlueprint = LabelBuilder.builder()
                .withStyleClasses("wishlist-header")
                .withText("tab.wishlist.no.blueprint")
                .build();

        OdysseyWishlistMenu odysseyWishlistMenu = new OdysseyWishlistMenu();

        this.totals = new OdysseyMaterialTotals();
        this.odysseyWishlistBlueprints = new OdysseyWishlistBlueprints();
        this.odysseyWishlistMaterials = new OdysseyWishlistMaterials();
        this.odysseyWishlistShortestPath = new OdysseyWishlistShortestPath();
        DestroyableVBox content = BoxBuilder.builder()
                .withStyleClass("wishlist-content")
                .withNodes(odysseyWishlistMenu, this.totals, this.odysseyWishlistBlueprints, this.odysseyWishlistMaterials, this.odysseyWishlistShortestPath, this.noBlueprint)
                .buildVBox();
        ScrollPane scrollPane = register(ScrollPaneBuilder.builder()
                .withStyleClass("wishlist-tab-content")
                .withContent(content)
                .build());
        this.setContent(scrollPane);
        update();
    }

    @SuppressWarnings("unchecked")
    private void update() {
        this.wishlistSize = APPLICATION_STATE.getPreferredCommander().map(commander -> WishlistService.getOdysseyWishlists(commander).getSelectedWishlist().getItems().stream().mapToInt(OdysseyWishlistBlueprint::getQuantity).sum()).orElse(0);
        //no need to use addBinding, since it is already registered
        this.textProperty().bind(LocaleService.getSupplierStringBinding("tabs.wishlist", () -> (this.wishlistSize > 0) ? " (" + this.wishlistSize + ")" : ""));

        setVisibility(totals, this.wishlistSize > 0);
        setVisibility(odysseyWishlistBlueprints, this.wishlistSize > 0);
        setVisibility(odysseyWishlistMaterials, this.wishlistSize > 0);
        setVisibility(odysseyWishlistShortestPath, this.wishlistSize > 0);
        setVisibility(noBlueprint, this.wishlistSize == 0);
    }

    void setVisibility(Node node, boolean visible) {
        node.setVisible(visible);
        node.setManaged(visible);
    }

    public void initEventHandling() {
        register(EventService.addListener(true, this, OdysseyWishlistSelectedEvent.class, _ -> update()));
        register(EventService.addListener(true, this, OdysseyWishlistChangedEvent.class, _ -> update()));
        register(EventService.addListener(true, this, RemoveWishlistShortestPathItemEvent.class, _ -> update()));
        register(EventService.addListener(true, this, HideWishlistShortestPathItemEvent.class, _ -> update()));
    }

    @Override
    public OdysseyTabType getTabType() {
        return OdysseyTabType.WISHLIST;
    }


}