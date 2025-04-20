package nl.jixxed.eliteodysseymaterials.templates.odyssey.wishlist;

import javafx.scene.Node;
import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.OdysseyBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.OdysseyWishlistBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.OdysseyWishlistMaterialSearch;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.WishlistService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableFlowPane;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class OdysseyWishlistMaterialsCategory extends DestroyableFlowPane implements DestroyableEventTemplate {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private final WishlistMaterialsCategory wishlistMaterialsCategory;

    private OdysseyWishlistMaterialSearch currentSearch;

    public OdysseyWishlistMaterialsCategory(WishlistMaterialsCategory wishlistMaterialsCategory) {
        this.wishlistMaterialsCategory = wishlistMaterialsCategory;
        final OdysseyWishlistMaterialSort sort = OdysseyWishlistMaterialSort.valueOf(PreferencesService.getPreference("search.odyssey.wishlist.sort", "ALPHABETICAL"));
        final WishlistMaterialGrouping grouping = WishlistMaterialGrouping.valueOf(PreferencesService.getPreference("search.odyssey.wishlist.grouping", "CATEGORY"));
        currentSearch = new OdysseyWishlistMaterialSearch("", sort, grouping);
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("materials-flow");
        createCards();
        group(this.currentSearch.getWishlistMaterialGrouping());
    }

    @Override
    public void initEventHandling() {

        register(EventService.addListener(true, this, OdysseyWishlistSearchEvent.class, event -> {
            if (currentSearch.getWishlistMaterialGrouping() != event.getSearch().getWishlistMaterialGrouping()) {
                group(event.getSearch().getWishlistMaterialGrouping());
            } else if (currentSearch.getWishlistMaterialSort() != event.getSearch().getWishlistMaterialSort()) {
                sort(event.getSearch().getWishlistMaterialSort());
            }
            this.currentSearch = event.getSearch();
        }));
        register(EventService.addListener(true, this, OdysseyWishlistSelectedEvent.class, _ -> refreshContent()));
//        register(EventService.addListener(true, this, OdysseyWishlistBlueprintAlteredEvent.class, _ -> refreshContent()));
        register(EventService.addListener(true, this, OdysseyWishlistChangedEvent.class, _ -> refreshContent()));

        register(EventService.addListener(true, this, 9, WishlistHideCompletedEvent.class, event -> {
            if (Expansion.ODYSSEY.equals(event.getExpansion())) {
                group(this.currentSearch.getWishlistMaterialGrouping());
            }
        }));

        register(EventService.addListener(true, this, OdysseyWishlistSearchEvent.class, odysseyWishlistSearchEvent -> {
            this.currentSearch = odysseyWishlistSearchEvent.getSearch();
            group(this.currentSearch.getWishlistMaterialGrouping());
        }));
    }

    private void refreshContent() {
        createCards();
    }

    private void createCards() {
        final Set<OdysseyMaterial> allWishlistMaterials = getAllWishlistMaterials();
        destroyCards();
        this.getNodes().addAll(allWishlistMaterials.stream()
                .filter(getFilter())
                .map(OdysseyWishlistIngredient::new)
                .sorted(OdysseyWishlistMaterialSort.getSort(this.currentSearch))
                .toList());
    }

    private Predicate<OdysseyMaterial> getFilter() {
        return switch (wishlistMaterialsCategory) {
            case GOODS -> Good.class::isInstance;
            case ASSETS -> Asset.class::isInstance;
            case DATA -> Data.class::isInstance;
            case ALL -> _ -> true;
        };

    }

    private void destroyCards() {
        this.getNodes().clear();
    }

    @SuppressWarnings("unchecked")
    private void sort(OdysseyWishlistMaterialSort sort) {
        this.getChildren().sort((Comparator<Node>) (Comparator<?>) sort.getSort(this.currentSearch));
    }

    private void group(WishlistMaterialGrouping grouping) {
        final boolean visible = (grouping.equals(WishlistMaterialGrouping.NONE) && WishlistMaterialsCategory.ALL.equals(this.wishlistMaterialsCategory))
                || (grouping.equals(WishlistMaterialGrouping.CATEGORY) && !WishlistMaterialsCategory.ALL.equals(this.wishlistMaterialsCategory));
        setVisible(visible && hasVisibleChildren());
        setManaged(visible && hasVisibleChildren());
    }

    private boolean hasVisibleChildren() {
        return !getChildren().filtered(Node::isVisible).isEmpty();
    }

    private Set<OdysseyMaterial> getAllWishlistMaterials() {
        return APPLICATION_STATE.getPreferredCommander()
                .map(commander -> WishlistService.getOdysseyWishlists(commander).getSelectedWishlist())
                .map(wishlist -> wishlist.getItems().stream()
                        .map(OdysseyWishlistBlueprint.class::cast)
                        .flatMap(bp -> this.getMaterials(bp).stream())
                        .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());
    }

    private Set<OdysseyMaterial> getMaterials(OdysseyWishlistBlueprint wishlistBlueprint) {
        final OdysseyBlueprint blueprint = OdysseyBlueprintConstants.getRecipe(wishlistBlueprint.getRecipeName());
        return new HashSet<>(blueprint.getMaterialCollection(OdysseyMaterial.class).keySet());
    }
}