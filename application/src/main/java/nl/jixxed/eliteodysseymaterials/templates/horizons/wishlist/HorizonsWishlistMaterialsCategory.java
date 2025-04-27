package nl.jixxed.eliteodysseymaterials.templates.horizons.wishlist;

import javafx.scene.Node;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.WishlistService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableFlowPane;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class HorizonsWishlistMaterialsCategory extends DestroyableFlowPane implements DestroyableEventTemplate {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private final WishlistMaterialsCategory wishlistMaterialsCategory;

    private HorizonsWishlistMaterialSearch currentSearch;

    public HorizonsWishlistMaterialsCategory(WishlistMaterialsCategory wishlistMaterialsCategory) {
        this.wishlistMaterialsCategory = wishlistMaterialsCategory;
        final HorizonsWishlistMaterialSort sort = HorizonsWishlistMaterialSort.valueOf(PreferencesService.getPreference("search.horizons.wishlist.sort", "ALPHABETICAL"));
        final WishlistMaterialGrouping grouping = WishlistMaterialGrouping.valueOf(PreferencesService.getPreference("search.horizons.wishlist.grouping", "CATEGORY"));
        currentSearch = new HorizonsWishlistMaterialSearch("", sort, grouping);
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

        register(EventService.addListener(true, this, HorizonsWishlistSearchEvent.class, event -> {
            if (currentSearch.getWishlistMaterialGrouping() != event.getSearch().getWishlistMaterialGrouping()) {
                group(event.getSearch().getWishlistMaterialGrouping());
            } else if (currentSearch.getWishlistMaterialSort() != event.getSearch().getWishlistMaterialSort()) {
                sort(event.getSearch().getWishlistMaterialSort());
            }
            this.currentSearch = event.getSearch();
        }));
        register(EventService.addListener(true, this, HorizonsWishlistSelectedEvent.class, _ -> refreshContent()));
        register(EventService.addListener(true, this, HorizonsWishlistBlueprintAlteredEvent.class, _ -> refreshContent()));
        register(EventService.addListener(true, this, HorizonsWishlistChangedEvent.class, _ -> refreshContent()));

        register(EventService.addListener(true, this, 9, WishlistHideCompletedEvent.class, event -> {
            if (Expansion.HORIZONS.equals(event.getExpansion())) {
                group(this.currentSearch.getWishlistMaterialGrouping());
            }
        }));

        register(EventService.addListener(true, this, HorizonsWishlistSearchEvent.class, horizonsWishlistSearchEvent -> {
            this.currentSearch = horizonsWishlistSearchEvent.getSearch();
            group(this.currentSearch.getWishlistMaterialGrouping());
        }));
    }

    private void refreshContent() {
        createCards();
    }

    private void createCards() {
        final Set<HorizonsMaterial> allWishlistMaterials = getAllWishlistMaterials();
        destroyCards();
        this.getNodes().addAll(allWishlistMaterials.stream()
                .filter(getFilter())
                .map(HorizonsWishlistIngredient::new)
                .sorted(HorizonsWishlistMaterialSort.getSort(this.currentSearch))
                .toList());
    }

    private Predicate<HorizonsMaterial> getFilter() {
        return switch (wishlistMaterialsCategory) {
            case RAW -> Raw.class::isInstance;
            case ENCODED -> Encoded.class::isInstance;
            case MANUFACTURED -> Manufactured.class::isInstance;
            case COMMODITY -> Commodity.class::isInstance;
            case ALL -> _ -> true;
        };

    }

    private void destroyCards() {
        this.getNodes().clear();
    }

    private void sort(HorizonsWishlistMaterialSort sort) {
        final var cards = this.getChildren().stream()
                .map(HorizonsWishlistIngredient.class::cast)
                .sorted(sort.getSort())
                .toList();
        this.getChildren().clear();
        this.getChildren().addAll(cards);
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

    private Set<HorizonsMaterial> getAllWishlistMaterials() {
        return APPLICATION_STATE.getPreferredCommander()
                .map(commander -> WishlistService.getHorizonsWishlists(commander).getSelectedWishlist())
                .map(wishlist -> wishlist.getItems().stream()
                        .map(HorizonsWishlistBlueprint.class::cast)
                        .flatMap(bp -> this.getMaterials(bp).stream())
                        .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());
    }

    private Set<HorizonsMaterial> getMaterials(HorizonsWishlistBlueprint wishlistBlueprint) {
        Set<HorizonsMaterial> materials = new HashSet<>();
        if (wishlistBlueprint instanceof HorizonsModuleWishlistBlueprint moduleWishlistBlueprint) {
            moduleWishlistBlueprint.getPercentageToComplete().forEach((grade, _) -> {
                final HorizonsBlueprint blueprint = (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipe(moduleWishlistBlueprint.getRecipeName(), moduleWishlistBlueprint.getBlueprintType(), grade);
                materials.addAll(blueprint.getMaterialCollection(HorizonsMaterial.class).keySet());
            });
        } else {
            final HorizonsBlueprint blueprint = (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipe(wishlistBlueprint.getRecipeName(), WishlistService.getBlueprintType(wishlistBlueprint), WishlistService.getBlueprintGrade(wishlistBlueprint));
            materials.addAll(blueprint.getMaterialCollection(HorizonsMaterial.class).keySet());
        }
        return materials;
    }
}