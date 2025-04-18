package nl.jixxed.eliteodysseymaterials.templates.horizons.wishlist;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModuleWishlistBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsWishlist;
import nl.jixxed.eliteodysseymaterials.domain.WishlistBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.Action;
import nl.jixxed.eliteodysseymaterials.enums.BlueprintCategory;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.WishlistService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsWishlistBlueprintEvent;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsWishlistChangedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsWishlistSelectedEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;
import nl.jixxed.eliteodysseymaterials.templates.generic.WishlistBlueprintTemplate;
import org.controlsfx.control.PopOver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HorizonsWishlistBlueprints extends DestroyableVBox implements DestroyableEventTemplate {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final String WISHLIST_HEADER_CLASS = "wishlist-header";
    private static final String WISHLIST_CATEGORY_CLASS = "wishlist-category";
    private static final String WISHLIST_RECIPES_STYLE_CLASS = "wishlist-recipes";
    private static final String WISHLIST_INGREDIENTS_STYLE_CLASS = "wishlist-ingredients";
    private static final String WISHLIST_CONTENT_STYLE_CLASS = "wishlist-content";
    private DestroyableLabel engineerRecipesLabel;
    private DestroyableLabel hardpointRecipesLabel;
    private DestroyableLabel utilityMountRecipesLabel;
    private DestroyableLabel coreInternalRecipesLabel;
    private DestroyableLabel optionalInternalRecipesLabel;
    private DestroyableLabel experimentalEffectRecipesLabel;
    private DestroyableLabel synthesisRecipesLabel;
    private DestroyableLabel techbrokerRecipesLabel;
    private DestroyableFlowPane engineerRecipes;
    private DestroyableFlowPane hardpointRecipes;
    private DestroyableFlowPane utilityMountRecipes;
    private DestroyableFlowPane coreInternalRecipes;
    private DestroyableFlowPane optionalInternalRecipes;
    private DestroyableFlowPane experimentalEffectRecipes;
    private DestroyableFlowPane synthesisRecipes;
    private DestroyableFlowPane techbrokerRecipes;
    private DestroyableHBox engineerBlueprintsLine;
    private DestroyableHBox hardpointBlueprintsLine;
    private DestroyableHBox utilityMountBlueprintsLine;
    private DestroyableHBox coreInternalBlueprintsLine;
    private DestroyableHBox optionalInternalBlueprintsLine;
    private DestroyableHBox experimentalEffectBlueprintsLine;
    private DestroyableHBox synthesisBlueprintsLine;
    private DestroyableHBox techbrokerBlueprintsLine;

    private final List<WishlistBlueprintTemplate<HorizonsBlueprintName>> wishlistBlueprints = new ArrayList<>();
    //    private final List<HorizonsWishlistBlueprintTemplate> wishlistBlueprints = new ArrayList<>();
    private DestroyableLabel selectedBlueprintsLabel;
    private DestroyableVBox blueprints;
    private DestroyableResizableImageView blueprintsHelp;
    private DestroyableHBox selectedBlueprintsHintWhite;
    private DestroyableHBox selectedBlueprintsHintYellow;
    private DestroyableHBox selectedBlueprintsHintGreen;
    private String activeWishlistUUID;


    public HorizonsWishlistBlueprints() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {

        this.blueprints = BoxBuilder.builder()
                .withStyleClass("wishlist-blueprints").buildVBox();
        this.engineerRecipes = FlowPaneBuilder.builder()
                .withStyleClass(WISHLIST_RECIPES_STYLE_CLASS)
                .build();
        this.hardpointRecipes = FlowPaneBuilder.builder()
                .withStyleClass(WISHLIST_RECIPES_STYLE_CLASS)
                .build();
        this.utilityMountRecipes = FlowPaneBuilder.builder()
                .withStyleClass(WISHLIST_RECIPES_STYLE_CLASS)
                .build();
        this.coreInternalRecipes = FlowPaneBuilder.builder()
                .withStyleClass(WISHLIST_RECIPES_STYLE_CLASS)
                .build();
        this.optionalInternalRecipes = FlowPaneBuilder.builder()
                .withStyleClass(WISHLIST_RECIPES_STYLE_CLASS)
                .build();
        this.experimentalEffectRecipes = FlowPaneBuilder.builder()
                .withStyleClass(WISHLIST_RECIPES_STYLE_CLASS)
                .build();
        this.synthesisRecipes = FlowPaneBuilder.builder()
                .withStyleClass(WISHLIST_RECIPES_STYLE_CLASS)
                .build();
        this.techbrokerRecipes = FlowPaneBuilder.builder()
                .withStyleClass(WISHLIST_RECIPES_STYLE_CLASS)
                .build();

        this.engineerRecipesLabel = LabelBuilder.builder()
                .withStyleClass(WISHLIST_CATEGORY_CLASS)
                .withText("blueprint.category.name.engineer_unlocks")
                .build();
        this.coreInternalRecipesLabel = LabelBuilder.builder()
                .withStyleClass(WISHLIST_CATEGORY_CLASS)
                .withText("blueprint.category.name.core_internal")
                .build();
        this.experimentalEffectRecipesLabel = LabelBuilder.builder()
                .withStyleClass(WISHLIST_CATEGORY_CLASS)
                .withText("blueprint.category.name.experimental_effects")
                .build();
        this.hardpointRecipesLabel = LabelBuilder.builder()
                .withStyleClass(WISHLIST_CATEGORY_CLASS)
                .withText("blueprint.category.name.hardpoint")
                .build();
        this.optionalInternalRecipesLabel = LabelBuilder.builder()
                .withStyleClass(WISHLIST_CATEGORY_CLASS)
                .withText("blueprint.category.name.optional_internal")
                .build();
        this.synthesisRecipesLabel = LabelBuilder.builder()
                .withStyleClass(WISHLIST_CATEGORY_CLASS)
                .withText("blueprint.category.name.synthesis")
                .build();
        this.techbrokerRecipesLabel = LabelBuilder.builder()
                .withStyleClass(WISHLIST_CATEGORY_CLASS)
                .withText("blueprint.category.name.techbroker")
                .build();
        this.utilityMountRecipesLabel = LabelBuilder.builder()
                .withStyleClass(WISHLIST_CATEGORY_CLASS)
                .withText("blueprint.category.name.utility_mount")
                .build();

        this.engineerBlueprintsLine = BoxBuilder.builder()
                .withNodes(this.engineerRecipesLabel, this.engineerRecipes).buildHBox();
        this.hardpointBlueprintsLine = BoxBuilder.builder()
                .withNodes(this.hardpointRecipesLabel, this.hardpointRecipes).buildHBox();
        this.utilityMountBlueprintsLine = BoxBuilder.builder()
                .withNodes(this.utilityMountRecipesLabel, this.utilityMountRecipes).buildHBox();
        this.coreInternalBlueprintsLine = BoxBuilder.builder()
                .withNodes(this.coreInternalRecipesLabel, this.coreInternalRecipes).buildHBox();
        this.optionalInternalBlueprintsLine = BoxBuilder.builder()
                .withNodes(this.optionalInternalRecipesLabel, this.optionalInternalRecipes).buildHBox();
        this.experimentalEffectBlueprintsLine = BoxBuilder.builder()
                .withNodes(this.experimentalEffectRecipesLabel, this.experimentalEffectRecipes).buildHBox();
        this.synthesisBlueprintsLine = BoxBuilder.builder()
                .withNodes(this.synthesisRecipesLabel, this.synthesisRecipes).buildHBox();
        this.techbrokerBlueprintsLine = BoxBuilder.builder()
                .withNodes(this.techbrokerRecipesLabel, this.techbrokerRecipes).buildHBox();
        HBox.setHgrow(this.engineerRecipes, Priority.ALWAYS);
        HBox.setHgrow(this.hardpointRecipes, Priority.ALWAYS);
        HBox.setHgrow(this.utilityMountRecipes, Priority.ALWAYS);
        HBox.setHgrow(this.coreInternalRecipes, Priority.ALWAYS);
        HBox.setHgrow(this.optionalInternalRecipes, Priority.ALWAYS);
        HBox.setHgrow(this.experimentalEffectRecipes, Priority.ALWAYS);
        HBox.setHgrow(this.synthesisRecipes, Priority.ALWAYS);
        HBox.setHgrow(this.techbrokerRecipes, Priority.ALWAYS);


        this.selectedBlueprintsLabel = LabelBuilder.builder()
                .withStyleClass(WISHLIST_HEADER_CLASS)
                .withText("tab.wishlist.selected.blueprints")
                .build();
        this.selectedBlueprintsHintWhite = BoxBuilder.builder()
                .withNodes(LabelBuilder.builder()
                        .withStyleClasses("wishlist-hint-white", "wishlist-hint-box")
                        .withText("tab.wishlist.selected.blueprints.hint.white")
                        .build(), LabelBuilder.builder()
                        .withStyleClass("wishlist-hint-white")
                        .withText("tab.wishlist.selected.blueprints.hint.white.horizons.explain")
                        .build()).buildHBox();
        this.selectedBlueprintsHintYellow = BoxBuilder.builder()
                .withNodes(LabelBuilder.builder()
                        .withStyleClasses("wishlist-hint-yellow", "wishlist-hint-box")
                        .withText("tab.wishlist.selected.blueprints.hint.yellow")
                        .build(), LabelBuilder.builder()
                        .withStyleClass("wishlist-hint-white")
                        .withText("tab.wishlist.selected.blueprints.hint.yellow.explain")
                        .build()).buildHBox();
        this.selectedBlueprintsHintGreen = BoxBuilder.builder()
                .withNodes(LabelBuilder.builder()
                        .withStyleClasses("wishlist-hint-green", "wishlist-hint-box")
                        .withText("tab.wishlist.selected.blueprints.hint.green")
                        .build(), LabelBuilder.builder()
                        .withStyleClass("wishlist-hint-white")
                        .withText("tab.wishlist.selected.blueprints.hint.green.explain")
                        .build()).buildHBox();
        final DestroyableVBox contentNode = BoxBuilder.builder().withStyleClass("help-popover")
                .withNodes(this.selectedBlueprintsHintWhite, this.selectedBlueprintsHintYellow, this.selectedBlueprintsHintGreen).buildVBox();
        final DestroyablePopOver popOver = PopOverBuilder.builder()
                .withStyleClass("popover-menubutton-layout")
                .withContent(contentNode)
                .withDetachable(false)
                .withHeaderAlwaysVisible(false)
                .withArrowLocation(PopOver.ArrowLocation.RIGHT_CENTER)
                .build();
        this.blueprintsHelp = ResizableImageViewBuilder.builder()
                .withOnMouseClicked(event -> {
                    popOver.show(this.blueprintsHelp, event.getScreenX(), event.getScreenY());
                })
                .withStyleClass("help-image")
                .withImage("/images/other/help.png")
                .build();
        final DestroyableHBox titleBar = BoxBuilder.builder()
                .withStyleClass("help-image-bar")
                .withNodes(this.selectedBlueprintsLabel, this.blueprintsHelp).buildHBox();
        this.getNodes().addAll(titleBar, this.blueprints);
        this.wishlistBlueprints.forEach(WishlistBlueprintTemplate::destroy);
        this.wishlistBlueprints.clear();
        this.wishlistBlueprints.addAll(APPLICATION_STATE.getPreferredCommander()
                .map(commander -> WishlistService.getHorizonsWishlists(commander).getSelectedWishlist().getItems().stream()
                        .map(wishlistRecipe -> createWishListBlueprintTemplate(wishlistRecipe, WishlistService.getHorizonsWishlists(commander).getSelectedWishlist().getUuid()))
                        .toList()
                )
                .orElse(new ArrayList<>()));
        refreshWishlistRecipes();
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, HorizonsWishlistSelectedEvent.class, wishlistChangedEvent -> {
            refreshWishlistRecipes();
        }));
        register(EventService.addListener(true, this, HorizonsWishlistBlueprintEvent.class, wishlistEvent ->
        {
            if (Action.REMOVED.equals(wishlistEvent.getAction())) {
                this.wishlistBlueprints.stream()
                        .filter(wishlistBlueprint -> wishlistEvent.getWishlistBlueprints().contains(wishlistBlueprint.getWishlistRecipe()))
                        .findFirst()
                        .ifPresent(wishlistBlueprint -> {
                            this.wishlistBlueprints.remove(wishlistBlueprint);
                            removeBluePrint(wishlistBlueprint);
                        });
            }
            if (Action.ADDED.equals(wishlistEvent.getAction())) {
                APPLICATION_STATE.getPreferredCommander().ifPresent(commander ->
                        wishlistEvent.getWishlistBlueprints().forEach(wishlistRecipe -> {
                            final WishlistBlueprintTemplate<HorizonsBlueprintName> wishlistBlueprint = createWishListBlueprintTemplate(wishlistRecipe, wishlistEvent.getWishlistUUID());
                            if (wishlistEvent.getWishlistUUID().equals(this.activeWishlistUUID)) {
                                this.wishlistBlueprints.add(wishlistBlueprint);
                                addBluePrint(wishlistBlueprint);
                            }
                        })
                );
            }
        }));
    }

    private WishlistBlueprintTemplate<HorizonsBlueprintName> createWishListBlueprintTemplate(final WishlistBlueprint<HorizonsBlueprintName> wishlistRecipe, final String wishlistUUID) {
        if (wishlistRecipe instanceof HorizonsModuleWishlistBlueprint horizonsModuleWishlistBlueprint) {
            return new HorizonsWishlistModuleBlueprintTemplate(wishlistUUID, horizonsModuleWishlistBlueprint);
        }
        return new HorizonsWishlistBlueprintTemplate(wishlistUUID, wishlistRecipe);
    }

    private void refreshWishlistRecipes() {
        this.engineerRecipes.getNodes().clear();
        this.hardpointRecipes.getNodes().clear();
        this.utilityMountRecipes.getNodes().clear();
        this.coreInternalRecipes.getNodes().clear();
        this.optionalInternalRecipes.getNodes().clear();
        this.experimentalEffectRecipes.getNodes().clear();
        this.synthesisRecipes.getNodes().clear();
        this.techbrokerRecipes.getNodes().clear();
        this.engineerRecipes.getNodes().addAll((List) this.wishlistBlueprints.stream().filter(wishlistBlueprint -> BlueprintCategory.ENGINEER_UNLOCKS.equals(wishlistBlueprint.getRecipeCategory())).toList());
        this.hardpointRecipes.getNodes().addAll((List) this.wishlistBlueprints.stream().filter(wishlistBlueprint -> BlueprintCategory.HARDPOINT.equals(wishlistBlueprint.getRecipeCategory())).toList());
        this.utilityMountRecipes.getNodes().addAll((List) this.wishlistBlueprints.stream().filter(wishlistBlueprint -> BlueprintCategory.UTILITY_MOUNT.equals(wishlistBlueprint.getRecipeCategory())).toList());
        this.coreInternalRecipes.getNodes().addAll((List) this.wishlistBlueprints.stream().filter(wishlistBlueprint -> BlueprintCategory.CORE_INTERNAL.equals(wishlistBlueprint.getRecipeCategory())).toList());
        this.optionalInternalRecipes.getNodes().addAll((List) this.wishlistBlueprints.stream().filter(wishlistBlueprint -> BlueprintCategory.OPTIONAL_INTERNAL.equals(wishlistBlueprint.getRecipeCategory())).toList());
        this.experimentalEffectRecipes.getNodes().addAll((List) this.wishlistBlueprints.stream().filter(wishlistBlueprint -> BlueprintCategory.EXPERIMENTAL_EFFECTS.equals(wishlistBlueprint.getRecipeCategory())).toList());
        this.synthesisRecipes.getNodes().addAll((List) this.wishlistBlueprints.stream().filter(wishlistBlueprint -> BlueprintCategory.SYNTHESIS.equals(wishlistBlueprint.getRecipeCategory())).toList());
        this.techbrokerRecipes.getNodes().addAll((List) this.wishlistBlueprints.stream().filter(wishlistBlueprint -> BlueprintCategory.TECHBROKER.equals(wishlistBlueprint.getRecipeCategory())).toList());
    }

    private void refreshWishlistBlueprints() {
        this.wishlistBlueprints.clear();
        final List<WishlistBlueprintTemplate<HorizonsBlueprintName>> newWishlistBlueprints = APPLICATION_STATE.getPreferredCommander()
                .map(commander -> {
                    final HorizonsWishlist selectedWishlist = WishlistService.getHorizonsWishlists(commander).getSelectedWishlist();
                    this.activeWishlistUUID = selectedWishlist.getUuid();
                    return selectedWishlist.getItems().stream()
                            .map(wishlistRecipe -> createWishListBlueprintTemplate(wishlistRecipe, this.activeWishlistUUID))
                            .toList();
                })
                .orElse(Collections.emptyList());
        this.wishlistBlueprints.addAll(newWishlistBlueprints);
        refreshWishlistRecipes();
        refreshBlueprintOverview();
        EventService.publish(new HorizonsWishlistChangedEvent(this.activeWishlistUUID));
    }

    private <E extends Node & WishlistBlueprintTemplate<HorizonsBlueprintName>> void addBluePrint(final WishlistBlueprintTemplate<HorizonsBlueprintName> wishlistBlueprint) {
        switch (wishlistBlueprint.getRecipeCategory()) {
            case ENGINEER_UNLOCKS -> this.engineerRecipes.getNodes().add((E) wishlistBlueprint);
            case HARDPOINT -> this.hardpointRecipes.getNodes().add((E) wishlistBlueprint);
            case UTILITY_MOUNT -> this.utilityMountRecipes.getNodes().add((E) wishlistBlueprint);
            case CORE_INTERNAL -> this.coreInternalRecipes.getNodes().add((E) wishlistBlueprint);
            case OPTIONAL_INTERNAL -> this.optionalInternalRecipes.getNodes().add((E) wishlistBlueprint);
            case EXPERIMENTAL_EFFECTS -> this.experimentalEffectRecipes.getNodes().add((E) wishlistBlueprint);
            case SYNTHESIS -> this.synthesisRecipes.getNodes().add((E) wishlistBlueprint);
            case TECHBROKER -> this.techbrokerRecipes.getNodes().add((E) wishlistBlueprint);
            default -> throw new IllegalArgumentException("Unsupported Category");
        }
        refreshBlueprintOverview();
    }

    private <E extends Node & Destroyable> void removeBluePrint(final WishlistBlueprintTemplate<HorizonsBlueprintName> wishlistBlueprint) {
        switch (wishlistBlueprint.getRecipeCategory()) {
            case ENGINEER_UNLOCKS -> this.engineerRecipes.getNodes().remove((E) wishlistBlueprint);
            case HARDPOINT -> this.hardpointRecipes.getNodes().remove((E) wishlistBlueprint);
            case UTILITY_MOUNT -> this.utilityMountRecipes.getNodes().remove((E) wishlistBlueprint);
            case CORE_INTERNAL -> this.coreInternalRecipes.getNodes().remove((E) wishlistBlueprint);
            case OPTIONAL_INTERNAL -> this.optionalInternalRecipes.getNodes().remove((E) wishlistBlueprint);
            case EXPERIMENTAL_EFFECTS -> this.experimentalEffectRecipes.getNodes().remove((E) wishlistBlueprint);
            case SYNTHESIS -> this.synthesisRecipes.getNodes().remove((E) wishlistBlueprint);
            case TECHBROKER -> this.techbrokerRecipes.getNodes().remove((E) wishlistBlueprint);
            default -> throw new IllegalArgumentException("Unsupported Category");
        }
        refreshBlueprintOverview();
    }

    private void refreshBlueprintOverview() {
        this.blueprints.getNodes().clear();
        for (final DestroyableHBox blueprintList : List.of(this.engineerBlueprintsLine, this.hardpointBlueprintsLine, this.utilityMountBlueprintsLine, this.coreInternalBlueprintsLine, this.optionalInternalBlueprintsLine, this.experimentalEffectBlueprintsLine, this.synthesisBlueprintsLine, this.techbrokerBlueprintsLine)) {
            if (!((DestroyableFlowPane) blueprintList.getNodes().get(1)).getNodes().isEmpty()) {
                this.blueprints.getNodes().add(blueprintList);
                final ArrayList<HorizonsWishlistBlueprintTemplate> wishlistItems = (ArrayList<HorizonsWishlistBlueprintTemplate>) (ArrayList<?>) new ArrayList<>(((DestroyableFlowPane) blueprintList.getNodes().get(1)).getChildren());
                wishlistItems
                        .sort(Comparator
                                .comparing(node -> LocaleService.getLocalizedStringForCurrentLocale(((WishlistBlueprintTemplate<HorizonsBlueprintName>) node).getRecipeName().getLocalizationKey()))
                                .thenComparing(node -> ((WishlistBlueprintTemplate<HorizonsBlueprintName>) node).getSequenceID()));
                ((DestroyableFlowPane) blueprintList.getNodes().get(1)).getNodes().clear();
                ((DestroyableFlowPane) blueprintList.getNodes().get(1)).getNodes().addAll(wishlistItems);
            }
        }
    }
}
