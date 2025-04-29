package nl.jixxed.eliteodysseymaterials.templates.odyssey.wishlist;

import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.OdysseyWishlistBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.WishlistBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.Action;
import nl.jixxed.eliteodysseymaterials.enums.BlueprintCategory;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyBlueprintName;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.WishlistService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.OdysseyWishlistBlueprintEvent;
import nl.jixxed.eliteodysseymaterials.service.event.OdysseyWishlistSelectedEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableFlowPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.generic.WishlistBlueprintTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OdysseyWishlistBlueprintsCategory extends DestroyableHBox implements DestroyableEventTemplate {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private DestroyableFlowPane blueprints;

    private final BlueprintCategory blueprintCategory;

    public OdysseyWishlistBlueprintsCategory(BlueprintCategory blueprintCategory) {
        this.blueprintCategory = blueprintCategory;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("category");
        final List<WishlistBlueprintTemplate<OdysseyBlueprintName>> blueprintTemplates = createBlueprintTemplates();

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
        register(EventService.addListener(true, this, OdysseyWishlistSelectedEvent.class, _ -> refreshBlueprints()));
        register(EventService.addListener(true, this, OdysseyWishlistBlueprintEvent.class, wishlistEvent ->
                APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                    final String selectedWishlistUUID = WishlistService.getOdysseyWishlists(commander).getSelectedWishlist().getUuid();
                    if (selectedWishlistUUID.equals(wishlistEvent.getWishlistUUID())) {
                        if (Action.REMOVED.equals(wishlistEvent.getAction())) {
                            remove();
                        } else if (Action.ADDED.equals(wishlistEvent.getAction())) {
                            add(wishlistEvent.getWishlistBlueprints(), wishlistEvent.getWishlistUUID());
                        }
                    }
                })));
    }

    private List<WishlistBlueprintTemplate<OdysseyBlueprintName>> createBlueprintTemplates() {
        return APPLICATION_STATE.getPreferredCommander()
                .map(commander -> WishlistService.getOdysseyWishlists(commander).getSelectedWishlist().getItems().stream()
                        .filter(bp -> this.blueprintCategory.equals(getBlueprintCategory(bp)))
                        .map(wishlistRecipe -> createWishListBlueprintTemplate(wishlistRecipe, WishlistService.getOdysseyWishlists(commander).getSelectedWishlist().getUuid()))
                        .toList())
                .orElse(new ArrayList<>());
    }

    private void add(List<OdysseyWishlistBlueprint> wishlistBlueprints, String wishlistUUID) {
        wishlistBlueprints.stream()
                .filter(blueprint -> this.blueprintCategory.equals(getBlueprintCategory(blueprint)))
                .forEach(wishlistRecipe -> {
                    final WishlistBlueprintTemplate<OdysseyBlueprintName> wishlistBlueprint = createWishListBlueprintTemplate(wishlistRecipe, wishlistUUID);
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

    private static BlueprintCategory getBlueprintCategory(OdysseyWishlistBlueprint blueprint) {
        return OdysseyBlueprintConstants.getRecipeCategory(blueprint.getRecipeName());
    }

    private WishlistBlueprintTemplate<OdysseyBlueprintName> createWishListBlueprintTemplate(final WishlistBlueprint<OdysseyBlueprintName> wishlistRecipe, final String wishlistUUID) {
        return new OdysseyWishlistBlueprintTemplate(wishlistUUID, wishlistRecipe);
    }

    private void refreshBlueprints() {
        this.blueprints.getNodes().clear();
        final List<WishlistBlueprintTemplate<OdysseyBlueprintName>> blueprintTemplates = createBlueprintTemplates();
        this.blueprints.getNodes().addAll((List) blueprintTemplates);
        sort();
    }

    @SuppressWarnings("unchecked")
    private void sort() {
        final List<Node> list = this.blueprints.getChildren().stream().sorted(Comparator.comparing(x -> LocaleService.getLocalizedStringForCurrentLocale(((WishlistBlueprintTemplate<OdysseyBlueprintName>) x).getWishlistRecipe().getRecipeName().getLocalizationKey()))
                .thenComparing(x -> ((WishlistBlueprintTemplate<OdysseyBlueprintName>) x).getSequenceID())).toList();
        this.blueprints.getChildren().setAll(list);
    }

    @SuppressWarnings("unchecked")
    private <E extends Node & WishlistBlueprintTemplate<OdysseyBlueprintName>> void addBluePrint(final WishlistBlueprintTemplate<OdysseyBlueprintName> wishlistBlueprint) {
        this.blueprints.getNodes().add((E) wishlistBlueprint);

    }

}
