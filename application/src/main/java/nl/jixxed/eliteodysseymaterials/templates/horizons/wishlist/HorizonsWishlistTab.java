package nl.jixxed.eliteodysseymaterials.templates.horizons.wishlist;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ScrollPaneBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsTabs;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.WishlistService;
import nl.jixxed.eliteodysseymaterials.service.event.CommanderSelectedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsWishlistChangedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsWishlistSelectedEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;
import nl.jixxed.eliteodysseymaterials.templates.horizons.HorizonsTab;

@Slf4j
public class HorizonsWishlistTab extends HorizonsTab implements DestroyableEventTemplate {

    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private int wishlistSize;
    private DestroyableLabel noBlueprint;
    private HorizonsWishlistBlueprints horizonsWishlistBlueprints;
    private HorizonsWishlistMaterials horizonsWishlistMaterials;
    private HorizonsWishlistShortestPath horizonsWishlistShortestPath;

    public HorizonsWishlistTab() {
        initComponents();
        initEventHandling();
    }

    @SuppressWarnings("unchecked")
    public void initComponents() {
        this.getStyleClass().add("wishlist-tab");


        this.wishlistSize = APPLICATION_STATE.getPreferredCommander().map(commander -> WishlistService.getHorizonsWishlists(commander).getSelectedWishlist().getItems().size()).orElse(0);
        this.addBinding(this.textProperty(), LocaleService.getSupplierStringBinding("tabs.wishlist", () -> (this.wishlistSize > 0) ? " (" + this.wishlistSize + ")" : ""));

        this.noBlueprint = LabelBuilder.builder()
                .withStyleClasses("wishlist-header")
                .withText("tab.wishlist.no.blueprint")
                .build();

        HorizonsWishlistMenu horizonsWishlistMenu = new HorizonsWishlistMenu();
        this.horizonsWishlistBlueprints = new HorizonsWishlistBlueprints();
        this.horizonsWishlistMaterials = new HorizonsWishlistMaterials();
        this.horizonsWishlistShortestPath = new HorizonsWishlistShortestPath();
        DestroyableVBox content = BoxBuilder.builder()
                .withStyleClass("wishlist-content")
                .withNodes(horizonsWishlistMenu, this.horizonsWishlistBlueprints, this.horizonsWishlistMaterials, this.horizonsWishlistShortestPath, this.noBlueprint)
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
        this.wishlistSize = APPLICATION_STATE.getPreferredCommander().map(commander -> WishlistService.getHorizonsWishlists(commander).getSelectedWishlist().getItems().size()).orElse(0);
        //no need to use addBinding, since it is already registered
        this.textProperty().bind(LocaleService.getSupplierStringBinding("tabs.wishlist", () -> (this.wishlistSize > 0) ? " (" + this.wishlistSize + ")" : ""));

        setVisibility(horizonsWishlistBlueprints, this.wishlistSize > 0);
        setVisibility(horizonsWishlistMaterials, this.wishlistSize > 0);
        setVisibility(horizonsWishlistShortestPath, this.wishlistSize > 0);
        setVisibility(noBlueprint, this.wishlistSize == 0);
    }

    void setVisibility(Node node, boolean visible) {
        node.setVisible(visible);
        node.setManaged(visible);
    }

    public void initEventHandling() {
        register(EventService.addListener(true, this, HorizonsWishlistSelectedEvent.class, _ -> update()));
        register(EventService.addListener(true, this, HorizonsWishlistChangedEvent.class, _ -> update()));
        register(EventService.addListener(true, this, CommanderSelectedEvent.class, _ -> update()));
    }

    @Override
    public HorizonsTabs getTabType() {
        return HorizonsTabs.WISHLIST;
    }


}