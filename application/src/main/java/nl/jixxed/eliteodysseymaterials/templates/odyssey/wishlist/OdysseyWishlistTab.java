package nl.jixxed.eliteodysseymaterials.templates.odyssey.wishlist;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ScrollPaneBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyTabs;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.WishlistService;
import nl.jixxed.eliteodysseymaterials.service.event.CommanderSelectedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.OdysseyWishlistChangedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.OdysseyWishlistSelectedEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;
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


        this.wishlistSize = APPLICATION_STATE.getPreferredCommander().map(commander -> WishlistService.getOdysseyWishlists(commander).getSelectedWishlist().getItems().size()).orElse(0);
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

    }

    @SuppressWarnings("unchecked")
    private void update() {
        this.wishlistSize = APPLICATION_STATE.getPreferredCommander().map(commander -> WishlistService.getOdysseyWishlists(commander).getSelectedWishlist().getItems().size()).orElse(0);
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
        register(EventService.addListener(true, this, CommanderSelectedEvent.class, _ -> update()));
    }

    @Override
    public OdysseyTabs getTabType() {
        return OdysseyTabs.WISHLIST;
    }


}