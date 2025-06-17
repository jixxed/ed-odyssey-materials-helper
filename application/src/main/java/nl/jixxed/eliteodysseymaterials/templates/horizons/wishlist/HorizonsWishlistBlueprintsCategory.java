package nl.jixxed.eliteodysseymaterials.templates.horizons.wishlist;

import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.Action;
import nl.jixxed.eliteodysseymaterials.enums.BlueprintCategory;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.WishlistService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;
import nl.jixxed.eliteodysseymaterials.templates.generic.WishlistBlueprintTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Slf4j
public class HorizonsWishlistBlueprintsCategory extends DestroyableVBox implements DestroyableEventTemplate {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private DestroyableFlowPane blueprints;

    private final BlueprintCategory blueprintCategory;

    public HorizonsWishlistBlueprintsCategory(BlueprintCategory blueprintCategory) {
        this.blueprintCategory = blueprintCategory;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("category");
        final List<WishlistBlueprintTemplate<HorizonsBlueprintName>> blueprintTemplates = createBlueprintTemplates();

        this.blueprints = FlowPaneBuilder.builder()
                .withStyleClass("list")
                .withNodes((List) blueprintTemplates)
                .build();
        sort();

        DestroyableLabel categoryTitle = LabelBuilder.builder()
                .withStyleClass("category-title")
                .withText(blueprintCategory.getLocalizationKey())
                .build();

        this.getNodes().addAll(categoryTitle, this.blueprints);
        HBox.setHgrow(this.blueprints, Priority.ALWAYS);
        addBinding(this.visibleProperty(), Bindings.greaterThan(Bindings.size(this.blueprints.getChildren()), 0));
        addBinding(this.managedProperty(), Bindings.greaterThan(Bindings.size(this.blueprints.getChildren()), 0));
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, HorizonsWishlistSelectedEvent.class, _ -> refreshBlueprints()));
        register(EventService.addListener(true, this, HorizonsRemoveWishlistShortestPathItemEvent.class, _ -> refreshBlueprints()));
        register(EventService.addListener(true, this, HorizonsHideWishlistShortestPathItemEvent.class, _ -> refreshBlueprints()));
        register(EventService.addListener(true, this, HorizonsWishlistBlueprintEvent.class, wishlistEvent ->
                APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                    final String selectedWishlistUUID = WishlistService.getHorizonsWishlists(commander).getSelectedWishlist().getUuid();
                    if (Objects.equals(wishlistEvent.getWishlistUUID(), selectedWishlistUUID)) {
                        if (Action.REMOVED.equals(wishlistEvent.getAction())) {
//                            log.debug("Action.REMOVED");
                            remove();
                        } else if (Action.ADDED.equals(wishlistEvent.getAction())) {
//                            log.debug("Action.ADDED");
                            add(wishlistEvent.getWishlistBlueprints(), wishlistEvent.getWishlistUUID());
                        }
                    }
                })));
    }

    private List<WishlistBlueprintTemplate<HorizonsBlueprintName>> createBlueprintTemplates() {
        return APPLICATION_STATE.getPreferredCommander()
                .map(commander -> WishlistService.getHorizonsWishlists(commander).getSelectedWishlist().getItems().stream()
                        .filter(bp -> this.blueprintCategory.equals(getBlueprintCategory((HorizonsWishlistBlueprint) bp)))
                        .map(wishlistRecipe -> createWishListBlueprintTemplate(wishlistRecipe, WishlistService.getHorizonsWishlists(commander).getSelectedWishlist().getUuid()))
                        .toList())
                .orElse(new ArrayList<>());
    }

    private void add(List<HorizonsWishlistBlueprint> wishlistBlueprints, String wishlistUUID) {
        wishlistBlueprints.stream()
                .filter(blueprint -> this.blueprintCategory.equals(getBlueprintCategory(blueprint)))
                .forEach(wishlistRecipe -> {
//                    log.debug("add");
                    final WishlistBlueprintTemplate<HorizonsBlueprintName> wishlistBlueprint = createWishListBlueprintTemplate(wishlistRecipe, wishlistUUID);
                    addBluePrint(wishlistBlueprint);
                });
        sort();
    }

    private void remove() {
        this.blueprints.getNodes().removeAll(this.blueprints.getChildren().stream()
                .map(WishlistBlueprintTemplate.class::cast)
                .filter(WishlistBlueprintTemplate::isDeleted)
                .toList());
    }

    private static BlueprintCategory getBlueprintCategory(HorizonsWishlistBlueprint blueprint) {
        return HorizonsBlueprintConstants.getRecipeCategory(blueprint.getRecipeName(), blueprint instanceof HorizonsExperimentalWishlistBlueprint);
    }

    private WishlistBlueprintTemplate<HorizonsBlueprintName> createWishListBlueprintTemplate(final WishlistBlueprint<HorizonsBlueprintName> wishlistRecipe, final String wishlistUUID) {
        if (wishlistRecipe instanceof HorizonsModuleWishlistBlueprint horizonsModuleWishlistBlueprint) {
            return new HorizonsWishlistModuleBlueprintTemplate(wishlistUUID, horizonsModuleWishlistBlueprint);
        }
        return new HorizonsWishlistBlueprintTemplate(wishlistUUID, wishlistRecipe);
    }

    private <E extends Node & Destroyable> void refreshBlueprints() {
        this.blueprints.getNodes().clear();
        final List<WishlistBlueprintTemplate<HorizonsBlueprintName>> blueprintTemplates = createBlueprintTemplates();
        this.blueprints.getNodes().addAll((List<E>) (List<?>) blueprintTemplates);
        sort();
    }

    @SuppressWarnings("unchecked")
    private void sort() {
        final List<Node> list = this.blueprints.getChildren().stream()
                .sorted(Comparator
                        .comparing(x -> LocaleService.getLocalizedStringForCurrentLocale(((WishlistBlueprintTemplate<HorizonsBlueprintName>) x).getWishlistRecipe().getRecipeName().getLocalizationKey()))
                        .thenComparing(x -> ((WishlistBlueprintTemplate<HorizonsBlueprintName>) x).getSequenceID()))
                .toList();
        this.blueprints.getChildren().clear();
        this.blueprints.getChildren().addAll(list);
    }

    @SuppressWarnings("unchecked")
    private <E extends Node & WishlistBlueprintTemplate<HorizonsBlueprintName>> void addBluePrint(final WishlistBlueprintTemplate<HorizonsBlueprintName> wishlistBlueprint) {
        this.blueprints.getNodes().add((E) wishlistBlueprint);

    }
}
