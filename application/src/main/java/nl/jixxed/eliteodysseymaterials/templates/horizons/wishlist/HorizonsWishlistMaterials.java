package nl.jixxed.eliteodysseymaterials.templates.horizons.wishlist;

import javafx.application.Platform;
import javafx.scene.Node;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.WishlistService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;
import org.controlsfx.control.PopOver;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HorizonsWishlistMaterials extends DestroyableVBox implements DestroyableEventTemplate {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final String WISHLIST_HEADER_CLASS = "wishlist-header";
    private static final String WISHLIST_INGREDIENTS_STYLE_CLASS = "wishlist-ingredients";
    private static final String WISHLIST_CONTENT_STYLE_CLASS = "wishlist-content";
    private DestroyableLabel requiredMaterialsLabel;
    private DestroyableFlowPane allFlow;
    private DestroyableFlowPane rawFlow;
    private DestroyableFlowPane encodedFlow;
    private DestroyableFlowPane manufacturedFlow;
    private DestroyableFlowPane commodityFlow;
    private DestroyableResizableImageView materialsHelp;

    private HorizonsWishlistMaterialSearch currentSearch = new HorizonsWishlistMaterialSearch("", HorizonsWishlistMaterialSort.ALPHABETICAL, WishlistMaterialGrouping.CATEGORY);

    public HorizonsWishlistMaterials() {

        final HorizonsWishlistMaterialSort materialSort = HorizonsWishlistMaterialSort.valueOf(PreferencesService.getPreference("search.horizons.wishlist.sort", "ALPHABETICAL"));
        final WishlistMaterialGrouping filter = WishlistMaterialGrouping.valueOf(PreferencesService.getPreference("search.horizons.wishlist.grouping", "CATEGORY"));
        currentSearch.setWishlistMaterialGrouping(filter);
        currentSearch.setWishlistMaterialSort(materialSort);
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("materials");
        this.allFlow = FlowPaneBuilder.builder()
                .withStyleClass(WISHLIST_INGREDIENTS_STYLE_CLASS)
                .build();
        this.rawFlow = FlowPaneBuilder.builder()
                .withStyleClass(WISHLIST_INGREDIENTS_STYLE_CLASS)
                .build();
        this.encodedFlow = FlowPaneBuilder.builder()
                .withStyleClass(WISHLIST_INGREDIENTS_STYLE_CLASS)
                .build();
        this.manufacturedFlow = FlowPaneBuilder.builder()
                .withStyleClass(WISHLIST_INGREDIENTS_STYLE_CLASS)
                .build();
        this.commodityFlow = FlowPaneBuilder.builder()
                .withStyleClass(WISHLIST_INGREDIENTS_STYLE_CLASS)
                .build();

        DestroyableCheckBox hideCompletedCheckBox = CheckBoxBuilder.builder()
                .withStyleClass("wishlist-checkbox")
                .withText("tab.wishlist.hide.completed")
                .withSelected(PreferencesService.getPreference("blueprint.hide.completed", false))
                .withSelectedProperty((observable, oldValue, newValue) ->
                {
                    PreferencesService.setPreference("blueprint.hide.completed", newValue);
                    EventService.publish(new WishlistHideCompletedEvent(Expansion.HORIZONS, newValue));
                })
                .build();
        DestroyableHBox materialHintRed = BoxBuilder.builder()
                .withNodes(LabelBuilder.builder()
                        .withStyleClasses("wishlist-hint-red", "wishlist-hint-box")
                        .withText("tab.wishlist.material.hint.red")
                        .build(), LabelBuilder.builder()
                        .withStyleClass("wishlist-hint-white")
                        .withText("tab.wishlist.material.hint.red.explain")
                        .build()).buildHBox();
        DestroyableHBox materialHintYellow = BoxBuilder.builder()
                .withNodes(LabelBuilder.builder()
                        .withStyleClasses("wishlist-hint-yellow", "wishlist-hint-box")
                        .withText("tab.wishlist.material.hint.yellow")
                        .build(), LabelBuilder.builder()
                        .withStyleClass("wishlist-hint-white")
                        .withText("tab.wishlist.material.hint.yellow.explain")
                        .build()).buildHBox();
        DestroyableHBox materialHintGreen = BoxBuilder.builder()
                .withNodes(LabelBuilder.builder()
                        .withStyleClasses("wishlist-hint-green", "wishlist-hint-box")
                        .withText("tab.wishlist.material.hint.green")
                        .build(), LabelBuilder.builder()
                        .withStyleClass("wishlist-hint-white")
                        .withText("tab.wishlist.material.hint.green.explain")
                        .build()).buildHBox();
        DestroyableHBox materialHintRequired = BoxBuilder.builder()
                .withNodes(LabelBuilder.builder()
                        .withStyleClass("wishlist-hint-white")
                        .withText("tab.wishlist.material.hint.required.explain")
                        .build()).buildHBox();
        final DestroyableVBox contentNodeMaterials = BoxBuilder.builder().withStyleClass("help-popover")
                .withNodes(materialHintRed, materialHintYellow, materialHintGreen, materialHintRequired).buildVBox();
        final DestroyablePopOver popOverMaterials = PopOverBuilder.builder()
//                .withStyleClass("popover-menubutton-layout")
                .withContent(contentNodeMaterials)
                .withDetachable(false)
                .withHeaderAlwaysVisible(false)
                .withArrowLocation(PopOver.ArrowLocation.RIGHT_CENTER)
                .build();
        this.materialsHelp = ResizableImageViewBuilder.builder()
                .withOnMouseClicked(event -> {
                    popOverMaterials.show(this.materialsHelp, event.getScreenX(), event.getScreenY());
                })
                .withStyleClass("help-image")
                .withImage("/images/other/help.png")
                .build();
        this.requiredMaterialsLabel = LabelBuilder.builder()
                .withStyleClass(WISHLIST_HEADER_CLASS)
                .withText("tab.wishlist.required.materials")
                .build();
        final DestroyableHBox hBoxMaterials = BoxBuilder.builder()
                .withNodes(this.requiredMaterialsLabel, this.materialsHelp, hideCompletedCheckBox).buildHBox();
        hBoxMaterials.addBinding(hBoxMaterials.spacingProperty(), ScalingHelper.getPixelDoubleBindingFromEm(0.71));
        DestroyableVBox flows = BoxBuilder.builder()
                .withStyleClass(WISHLIST_CONTENT_STYLE_CLASS)
                .withNodes(this.allFlow, this.rawFlow, this.encodedFlow, this.manufacturedFlow, this.commodityFlow)
                .buildVBox();
        this.getNodes().addAll(hBoxMaterials, flows);
        createCards();
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
        register(EventService.addListener(true, this, HorizonsWishlistSelectedEvent.class, wishlistChangedEvent -> {
            refreshContent();
        }));
        register(EventService.addListener(true, this, HorizonsWishlistBlueprintAlteredEvent.class, wishlistChangedEvent -> {
            refreshContent();
        }));
        register(EventService.addListener(true, this, HorizonsWishlistChangedEvent.class, wishlistChangedEvent -> {
            refreshContent();
//            this.activeWishlistUUID = wishlistChangedEvent.getWishlistUUID();
//            APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> this.wishlistSize = Optional.ofNullable(WishlistService.getHorizonsWishlists(commander).getWishlist(this.activeWishlistUUID)).map(wl -> wl.getItems().size()).orElse(0));
//
//            this.addBinding(this.textProperty(), LocaleService.getSupplierStringBinding("tabs.wishlist", () -> (this.wishlistSize > 0) ? " (" + this.wishlistSize + ")" : ""));
        }));
//        register(EventService.addListener(true, this, HorizonsHideWishlistShortestPathItemEvent.class, event -> {
//            final List<WishlistBlueprintTemplate<HorizonsBlueprintName>> pathBlueprints = getPathWishlistBlueprints(event.getPathItem());
//            pathBlueprints.forEach(wishlistBlueprint -> wishlistBlueprint.setVisibility(false));
//            refreshContent();
//        }));
//        register(EventService.addListener(true, this, HorizonsRemoveWishlistShortestPathItemEvent.class, event -> {
//            final List<WishlistBlueprintTemplate<HorizonsBlueprintName>> pathBlueprints = getPathWishlistBlueprints(event.getPathItem());
//            pathBlueprints.forEach(WishlistBlueprintTemplate::destroy);
//        }));
//        register(EventService.addListener(true, this, EngineerPinEvent.class, event -> {
//
//            refreshContent();
//        }));

        register(EventService.addListener(true, this, HorizonsWishlistSearchEvent.class, horizonsWishlistSearchEvent -> {
            this.currentSearch = horizonsWishlistSearchEvent.getSearch();
            Platform.runLater(this::refreshContent);
        }));
    }

    private void refreshContent() {
        createCards();
    }

    private void createCards() {
        final Set<HorizonsMaterial> allWishlistMaterials = getAllWishlistMaterials();
        destroyCards();
        if (this.currentSearch.getWishlistMaterialGrouping().equals(WishlistMaterialGrouping.CATEGORY)) {
            this.rawFlow.getNodes().addAll(allWishlistMaterials.stream()
                    .filter(Raw.class::isInstance)
                    .map(HorizonsWishlistIngredient::new)
                    .sorted(HorizonsWishlistMaterialSort.getSort(this.currentSearch))
                    .toList());
            this.encodedFlow.getNodes().addAll(allWishlistMaterials.stream()
                    .filter(Encoded.class::isInstance)
                    .map(HorizonsWishlistIngredient::new)
                    .sorted(HorizonsWishlistMaterialSort.getSort(this.currentSearch))
                    .toList());
            this.manufacturedFlow.getNodes().addAll(allWishlistMaterials.stream()
                    .filter(Manufactured.class::isInstance)
                    .map(HorizonsWishlistIngredient::new)
                    .sorted(HorizonsWishlistMaterialSort.getSort(this.currentSearch))
                    .toList());
            this.commodityFlow.getNodes().addAll(allWishlistMaterials.stream()
                    .filter(Commodity.class::isInstance)
                    .map(HorizonsWishlistIngredient::new)
                    .sorted(HorizonsWishlistMaterialSort.getSort(this.currentSearch))
                    .toList());
        } else {
            this.allFlow.getNodes().addAll(allWishlistMaterials.stream()
                    .map(HorizonsWishlistIngredient::new)
                    .sorted(HorizonsWishlistMaterialSort.getSort(this.currentSearch))
                    .toList());
        }
    }

    private void destroyCards() {
        this.rawFlow.getNodes().clear();
        this.encodedFlow.getNodes().clear();
        this.manufacturedFlow.getNodes().clear();
        this.commodityFlow.getNodes().clear();
        this.allFlow.getNodes().clear();
    }

    @SuppressWarnings("unchecked")
    private void sort(HorizonsWishlistMaterialSort sort) {
        this.rawFlow.getChildren().sort((Comparator<Node>) (Comparator<?>) sort.getSort());
        this.encodedFlow.getChildren().sort((Comparator<Node>) (Comparator<?>) sort.getSort());
        this.manufacturedFlow.getChildren().sort((Comparator<Node>) (Comparator<?>) sort.getSort());
        this.commodityFlow.getChildren().sort((Comparator<Node>) (Comparator<?>) sort.getSort());
        this.allFlow.getChildren().sort((Comparator<Node>) (Comparator<?>) sort.getSort());
    }

    @SuppressWarnings("unchecked")
    private void group(WishlistMaterialGrouping grouping) {
        if (grouping.equals(WishlistMaterialGrouping.CATEGORY)) {
            final List<HorizonsWishlistIngredient> materialCards = (List<HorizonsWishlistIngredient>) (List<?>) this.allFlow.getChildren();
            this.rawFlow.getChildren().addAll(materialCards.stream()
                    .filter(card -> card.getHorizonsMaterial() instanceof Raw)
                    .sorted(HorizonsWishlistMaterialSort.getSort(this.currentSearch))
                    .toList());
            this.encodedFlow.getChildren().addAll(materialCards.stream()
                    .filter(card -> card.getHorizonsMaterial() instanceof Encoded)
                    .sorted(HorizonsWishlistMaterialSort.getSort(this.currentSearch))
                    .toList());
            this.manufacturedFlow.getChildren().addAll(materialCards.stream()
                    .filter(card -> card.getHorizonsMaterial() instanceof Manufactured)
                    .sorted(HorizonsWishlistMaterialSort.getSort(this.currentSearch))
                    .toList());
            this.commodityFlow.getChildren().addAll(materialCards.stream()
                    .filter(card -> card.getHorizonsMaterial() instanceof Commodity)
                    .sorted(HorizonsWishlistMaterialSort.getSort(this.currentSearch))
                    .toList());
            this.allFlow.getChildren().clear();
        } else if (grouping.equals(WishlistMaterialGrouping.NONE)) {
            final List<HorizonsWishlistIngredient> materialCards = (List<HorizonsWishlistIngredient>) (List<?>)
                    Stream.concat(
                                    Stream.concat(this.rawFlow.getChildren().stream(), this.encodedFlow.getChildren().stream()),
                                    Stream.concat(this.manufacturedFlow.getChildren().stream(), this.commodityFlow.getChildren().stream())
                            )
                            .toList();
            this.allFlow.getChildren().addAll(materialCards.stream()
                    .sorted(HorizonsWishlistMaterialSort.getSort(this.currentSearch))
                    .toList());
            this.rawFlow.getChildren().clear();
            this.encodedFlow.getChildren().clear();
            this.manufacturedFlow.getChildren().clear();
            this.commodityFlow.getChildren().clear();
        }
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
            moduleWishlistBlueprint.getPercentageToComplete().forEach((grade, percentage) -> {
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