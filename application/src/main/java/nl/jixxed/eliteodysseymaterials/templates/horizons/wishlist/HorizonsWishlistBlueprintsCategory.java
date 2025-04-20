package nl.jixxed.eliteodysseymaterials.templates.horizons.wishlist;

import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.Action;
import nl.jixxed.eliteodysseymaterials.enums.BlueprintCategory;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.WishlistService;
import nl.jixxed.eliteodysseymaterials.service.event.CommanderSelectedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsWishlistBlueprintEvent;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsWishlistSelectedEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;
import nl.jixxed.eliteodysseymaterials.templates.generic.WishlistBlueprintTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HorizonsWishlistBlueprintsCategory extends DestroyableHBox implements DestroyableEventTemplate {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final String WISHLIST_HEADER_CLASS = "wishlist-header";
    private static final String WISHLIST_CATEGORY_CLASS = "wishlist-category";
    private static final String WISHLIST_RECIPES_STYLE_CLASS = "wishlist-recipes";
    private static final String WISHLIST_INGREDIENTS_STYLE_CLASS = "wishlist-ingredients";
    private static final String WISHLIST_CONTENT_STYLE_CLASS = "wishlist-content";
    private DestroyableLabel categoryTitle;
    private DestroyableFlowPane blueprints;

    //    private final List<WishlistBlueprintTemplate<HorizonsBlueprintName>> wishlistBlueprints = new ArrayList<>();
    private final BlueprintCategory blueprintCategory;

    public HorizonsWishlistBlueprintsCategory(BlueprintCategory blueprintCategory) {
        this.blueprintCategory = blueprintCategory;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {

        this.getStyleClass().add("category");
        final List<WishlistBlueprintTemplate<HorizonsBlueprintName>> blueprints = APPLICATION_STATE.getPreferredCommander()
                .map(commander -> WishlistService.getHorizonsWishlists(commander).getSelectedWishlist().getItems().stream()
                        .filter(bp -> this.blueprintCategory.equals(getBlueprintCategory((HorizonsWishlistBlueprint) bp)))
                        .map(wishlistRecipe -> createWishListBlueprintTemplate(wishlistRecipe, WishlistService.getHorizonsWishlists(commander).getSelectedWishlist().getUuid()))
                        .toList())
                .orElse(new ArrayList<>());

        this.blueprints = FlowPaneBuilder.builder()
                .withStyleClass("list")
                .withNodes((List) blueprints)
                .build();
        sort();
        this.categoryTitle = LabelBuilder.builder()
                .withStyleClass("category-title")
                .withText(blueprintCategory.getLocalizationKey())
                .build();

        this.getNodes().addAll(this.categoryTitle, this.blueprints);
        HBox.setHgrow(this.blueprints, Priority.ALWAYS);
        this.visibleProperty().bind(Bindings.greaterThan(Bindings.size(this.blueprints.getChildren()), 0));
        this.managedProperty().bind(Bindings.greaterThan(Bindings.size(this.blueprints.getChildren()), 0));
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, CommanderSelectedEvent.class, _ -> {
            refreshBlueprints();
        }));
        register(EventService.addListener(true, this, HorizonsWishlistSelectedEvent.class, _ -> {
            refreshBlueprints();
        }));
        register(EventService.addListener(true, this, HorizonsWishlistBlueprintEvent.class, wishlistEvent ->
        {
            APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                final String selectedWishlistUUID = WishlistService.getHorizonsWishlists(commander).getSelectedWishlistUUID();
                if (selectedWishlistUUID.equals(wishlistEvent.getWishlistUUID())) {
                    if (Action.REMOVED.equals(wishlistEvent.getAction())) {
                        remove(wishlistEvent.getWishlistBlueprints());
                    } else if (Action.ADDED.equals(wishlistEvent.getAction())) {
                        add(wishlistEvent.getWishlistBlueprints(), wishlistEvent.getWishlistUUID());
                    }
                }
            });
        }));
    }

    private void add(List<HorizonsWishlistBlueprint> wishlistBlueprints, String wishlistUUID) {
        wishlistBlueprints.stream()
                .filter(blueprint -> this.blueprintCategory.equals(getBlueprintCategory(blueprint)))
                .forEach(wishlistRecipe -> {
                    final WishlistBlueprintTemplate<HorizonsBlueprintName> wishlistBlueprint = createWishListBlueprintTemplate(wishlistRecipe, wishlistUUID);
                    addBluePrint(wishlistBlueprint);
                });
        sort();
    }

    private void remove(List<HorizonsWishlistBlueprint> wishlistBlueprints) {
        wishlistBlueprints.stream()
                .filter(blueprint -> this.blueprintCategory.equals(getBlueprintCategory(blueprint)))
                .forEach(wishlistRecipe ->
                        this.blueprints.getChildren().stream()
                                .map(WishlistBlueprintTemplate.class::cast)
                                .filter(template -> template.getWishlistRecipe().equals(wishlistRecipe))
                                .findFirst()
                                .ifPresent(this::removeBluePrint));
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

    private void refreshBlueprints() {
        this.blueprints.getNodes().clear();
        final List<WishlistBlueprintTemplate<HorizonsBlueprintName>> blueprints = APPLICATION_STATE.getPreferredCommander()
                .map(commander -> WishlistService.getHorizonsWishlists(commander).getSelectedWishlist().getItems().stream()
                        .filter(bp -> this.blueprintCategory.equals(getBlueprintCategory((HorizonsWishlistBlueprint) bp)))
                        .map(wishlistRecipe -> createWishListBlueprintTemplate(wishlistRecipe, WishlistService.getHorizonsWishlists(commander).getSelectedWishlist().getUuid()))
                        .toList()
                )
                .orElse(new ArrayList<>());
        this.blueprints.getNodes().addAll((List) blueprints);
        sort();
    }

    private void sort() {
        final List<Node> list = this.blueprints.getChildren().stream().sorted(Comparator.comparing(x -> LocaleService.getLocalizedStringForCurrentLocale(((WishlistBlueprintTemplate<HorizonsBlueprintName>) x).getWishlistRecipe().getRecipeName().getLocalizationKey()))
                .thenComparing(x -> ((WishlistBlueprintTemplate<HorizonsBlueprintName>) x).getSequenceID())).toList();
        this.blueprints.getChildren().setAll(list);
    }
//    private void refreshWishlistBlueprints() {
//        this.wishlistBlueprints.clear();
//        final List<WishlistBlueprintTemplate<HorizonsBlueprintName>> newWishlistBlueprints = APPLICATION_STATE.getPreferredCommander()
//                .map(commander -> {
//                    final HorizonsWishlist selectedWishlist = WishlistService.getHorizonsWishlists(commander).getSelectedWishlist();
//                    this.activeWishlistUUID = selectedWishlist.getUuid();
//                    return selectedWishlist.getItems().stream()
//                            .map(wishlistRecipe -> createWishListBlueprintTemplate(wishlistRecipe, this.activeWishlistUUID))
//                            .toList();
//                })
//                .orElse(Collections.emptyList());
//        this.wishlistBlueprints.addAll(newWishlistBlueprints);
//        refreshWishlistRecipes();
//        refreshBlueprintOverview();
//        EventService.publish(new HorizonsWishlistChangedEvent(this.activeWishlistUUID));
//    }

    private <E extends Node & WishlistBlueprintTemplate<HorizonsBlueprintName>> void addBluePrint(final WishlistBlueprintTemplate<HorizonsBlueprintName> wishlistBlueprint) {
        this.blueprints.getNodes().add((E) wishlistBlueprint);

    }

    private <E extends Node & Destroyable> void removeBluePrint(final WishlistBlueprintTemplate<HorizonsBlueprintName> wishlistBlueprint) {
        this.blueprints.getNodes().remove((E) wishlistBlueprint);
    }

//    private void refreshBlueprintOverview() {
//        this.blueprints.getNodes().clear();
//        for (final DestroyableHBox blueprintList : List.of(this.engineerBlueprintsLine, this.hardpointBlueprintsLine, this.utilityMountBlueprintsLine, this.coreInternalBlueprintsLine, this.optionalInternalBlueprintsLine, this.experimentalEffectBlueprintsLine, this.synthesisBlueprintsLine, this.techbrokerBlueprintsLine)) {
//            if (!((DestroyableFlowPane) blueprintList.getNodes().get(1)).getNodes().isEmpty()) {
//                this.blueprints.getNodes().add(blueprintList);
//                final ArrayList<HorizonsWishlistBlueprintTemplate> wishlistItems = (ArrayList<HorizonsWishlistBlueprintTemplate>) (ArrayList<?>) new ArrayList<>(((DestroyableFlowPane) blueprintList.getNodes().get(1)).getChildren());
//                wishlistItems
//                        .sort(Comparator
//                                .comparing(node -> LocaleService.getLocalizedStringForCurrentLocale(((WishlistBlueprintTemplate<HorizonsBlueprintName>) node).getRecipeName().getLocalizationKey()))
//                                .thenComparing(node -> ((WishlistBlueprintTemplate<HorizonsBlueprintName>) node).getSequenceID()));
//                ((DestroyableFlowPane) blueprintList.getNodes().get(1)).getNodes().clear();
//                ((DestroyableFlowPane) blueprintList.getNodes().get(1)).getNodes().addAll(wishlistItems);
//            }
//        }
//    }
}
