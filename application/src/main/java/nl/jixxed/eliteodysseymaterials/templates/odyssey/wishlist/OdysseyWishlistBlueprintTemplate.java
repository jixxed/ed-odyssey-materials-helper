package nl.jixxed.eliteodysseymaterials.templates.odyssey.wishlist;

import javafx.animation.FadeTransition;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TooltipBuilder;
import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.ImageService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.BlueprintClickEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;
import nl.jixxed.eliteodysseymaterials.service.event.WishlistBlueprintEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;
import nl.jixxed.eliteodysseymaterials.templates.generic.Ingredient;
import nl.jixxed.eliteodysseymaterials.templates.generic.WishlistBlueprintTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class OdysseyWishlistBlueprintTemplate extends DestroyableHBox implements WishlistBlueprintTemplate<OdysseyBlueprintName>, DestroyableEventTemplate {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final String VISIBLE_STYLE_CLASS = "visible";
    private static final String WISHLIST_VISIBLE_ICON_STYLE_CLASS = "wishlist-visible-icon";
    private static int counter = 0;

    private boolean visible;
    private final Integer sequenceID;
    private final OdysseyWishlistBlueprint odysseyWishlistBlueprint;
    private final BlueprintCategory blueprintCategory;
    private final OdysseyBlueprint blueprint;
    @Getter
    private final String wishlistUUID;

    private DestroyableButton visibilityButton;
    private DestroyableResizableImageView visibilityImage;
    private DestroyableLabel wishlistRecipeName;
    private DestroyableButton removeBlueprint;
    private final Set<OdysseyWishlistIngredient> wishlistIngredients = new HashSet<>();
    private final Set<OdysseyWishlistIngredient> otherIngredients = new HashSet<>();
    private DestroyableTooltip tooltip;

    public OdysseyWishlistBlueprintTemplate(final String wishlistUUID, final OdysseyWishlistBlueprint odysseyWishlistBlueprint) {
        this.wishlistUUID = wishlistUUID;
        this.odysseyWishlistBlueprint = odysseyWishlistBlueprint;
        this.sequenceID = counter++;
        this.blueprintCategory = OdysseyBlueprintConstants.getRecipeCategory(odysseyWishlistBlueprint.getRecipeName());
        this.blueprint = OdysseyBlueprintConstants.getRecipe(odysseyWishlistBlueprint.getRecipeName());
        initComponents();
        initEventHandling();
    }

    public void initComponents() {
        if (!this.wishlistUUID.equals(Wishlist.ALL.getUuid())) {
            this.visibilityImage = ResizableImageViewBuilder.builder()
                    .withStyleClass("wishlist-visible-image")
                    .withImage("/images/other/visible_blue.png")
                    .build();
            this.visibilityButton = ButtonBuilder.builder()
                    .withStyleClasses(WISHLIST_VISIBLE_ICON_STYLE_CLASS, VISIBLE_STYLE_CLASS)
                    .withOnAction(event -> setVisibility(!this.visible))
                    .withGraphic(this.visibilityImage)
                    .build();
            this.getNodes().addAll(this.visibilityButton);
            setVisibility(this.odysseyWishlistBlueprint.isVisible());
        } else {
            setVisibility(true);
        }
        this.wishlistRecipeName = LabelBuilder.builder()
                .withStyleClass("wishlist-label")
                .withText(this.odysseyWishlistBlueprint.getRecipeName().getLocalizationKey())
                .withOnMouseClicked(event -> EventService.publish(new BlueprintClickEvent(this.odysseyWishlistBlueprint.getRecipeName())))
                .withHoverProperty((observable, oldValue, newValue) -> {
                    this.wishlistIngredients.forEach(wishlistIngredient -> wishlistIngredient.highlight(newValue, this.blueprint.getRequiredAmount(wishlistIngredient.getOdysseyMaterial())));
                    this.otherIngredients.forEach(wishlistIngredient -> wishlistIngredient.lowlight(newValue));
                    this.highlight(newValue);
                })
                .build();
        this.getNodes().addAll(this.wishlistRecipeName);
        if (!this.wishlistUUID.equals(HorizonsWishlist.ALL.getUuid())) {
            this.removeBlueprint = ButtonBuilder.builder()
                    .withStyleClass("wishlist-item-button")
                    .withNonLocalizedText("X")
                    .withOnAction(event -> {
                        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> EventService.publish(new WishlistBlueprintEvent(commander, this.wishlistUUID, List.of(this.odysseyWishlistBlueprint), Action.REMOVED)));
                        destroyTemplate();
                    })
                    .build();
            this.getNodes().add(this.removeBlueprint);
        }
        this.getStyleClass().add("wishlist-item");


        if (this.blueprint instanceof ModuleBlueprint moduleRecipe) {
            this.tooltip = TooltipBuilder.builder()
                    .withStyleClass("wishlist-tooltip")
                    .withText(LocaleService.getToolTipStringBinding(moduleRecipe, "tab.wishlist.blueprint.tooltip"))
                    .withShowDelay(Duration.millis(100))
                    .withShowDuration(Duration.seconds(30))
                    .build();
            Tooltip.install(this.wishlistRecipeName, this.tooltip);
        }
        initFadeTransition();
        final Craftability craftability = OdysseyBlueprintConstants.getCraftability((OdysseyBlueprintName) this.getRecipeName());
        this.canCraft(craftability);
    }


    private void initFadeTransition() {
        final FadeTransition fadeTransition = new FadeTransition(Duration.millis(2000));
        fadeTransition.setNode(this);
        fadeTransition.setFromValue(0.3);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }

    public void initEventHandling() {
        register(EventService.addListener(true, this, StorageEvent.class, storageEvent -> {
            final Craftability craftability = OdysseyBlueprintConstants.getCraftability((OdysseyBlueprintName) this.getRecipeName());
            this.canCraft(craftability);
        }));
    }

    private void highlight(final boolean enable) {
        if (enable) {
            this.getStyleClass().add("wishlist-highlight");
        } else {
            this.getStyleClass().removeAll("wishlist-highlight");
        }
    }

    private void canCraft(final Craftability craftability) {
        this.wishlistRecipeName.getStyleClass().removeAll("wishlist-craftable", "wishlist-craftable-with-trade");
        if (Craftability.CRAFTABLE.equals(craftability)) {
            this.wishlistRecipeName.getStyleClass().add("wishlist-craftable");
            if (this.blueprint instanceof ModuleBlueprint moduleRecipe) {
                this.tooltip.addBinding(this.tooltip.textProperty(), LocaleService.getToolTipStringBinding(moduleRecipe, "tab.wishlist.blueprint.tooltip"));
            }
        } else if (Craftability.CRAFTABLE_WITH_TRADE.equals(craftability)) {
            this.wishlistRecipeName.getStyleClass().add("wishlist-craftable-with-trade");
            if (this.blueprint instanceof ModuleBlueprint moduleRecipe) {
                this.tooltip.addBinding(this.tooltip.textProperty(), LocaleService.getToolTipStringBinding(moduleRecipe, "tab.wishlist.blueprint.tooltip.craftable"));
            }
        }
    }

    @Override
    public void addWishlistIngredients(final List<Ingredient> wishlistIngredients) {
        this.wishlistIngredients.clear();
        this.otherIngredients.clear();
        this.wishlistIngredients.addAll(wishlistIngredients.stream().map(OdysseyWishlistIngredient.class::cast).filter(wishlistIngredient -> this.blueprint.hasIngredient(wishlistIngredient.getOdysseyMaterial())).collect(Collectors.toSet()));
        this.otherIngredients.addAll(wishlistIngredients.stream().map(OdysseyWishlistIngredient.class::cast).filter(wishlistIngredient -> !this.blueprint.hasIngredient(wishlistIngredient.getOdysseyMaterial())).collect(Collectors.toSet()));
    }

    @Override
    public void setVisibility(final boolean visible) {
        this.visible = visible;
        this.odysseyWishlistBlueprint.setVisible(this.visible);
        if (this.visibilityButton != null) {
            this.visibilityImage.setImage(ImageService.getImage(this.visible ? "/images/other/visible_blue.png" : "/images/other/invisible_gray.png"));
            if (this.visible) {
                this.visibilityButton.getStyleClass().add(VISIBLE_STYLE_CLASS);
            } else {
                this.visibilityButton.getStyleClass().remove(VISIBLE_STYLE_CLASS);
            }
        }
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> EventService.publish(new WishlistBlueprintEvent(commander, this.wishlistUUID, List.of(this.odysseyWishlistBlueprint), Action.VISIBILITY_CHANGED)));
    }

    @Override
    public Map<Blueprint<OdysseyBlueprintName>, Double> getRecipe() {
        return Map.of(this.blueprint, 1D);
    }

    @Override
    public OdysseyBlueprint getPrimaryRecipe() {
        return this.blueprint;
    }

    @Override
    public BlueprintName<OdysseyBlueprintName> getRecipeName() {
        return this.odysseyWishlistBlueprint.getRecipeName();
    }

    @Override
    public BlueprintCategory getRecipeCategory() {
        return this.blueprintCategory;
    }

    @Override
    public Integer getSequenceID() {
        return this.sequenceID;
    }

    @Override
    public boolean isVisibleBlueprint() {
        return this.visible;
    }

    @Override
    public WishlistBlueprint<OdysseyBlueprintName> getWishlistRecipe() {
        return this.odysseyWishlistBlueprint;
    }

//    @Override
//    public void destroyInternal() {
//        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> EventService.publish(new WishlistBlueprintEvent(commander, this.wishlistUUID, List.of(this.odysseyWishlistBlueprint), Action.REMOVED)));
//    }

    @Override
    public void setEngineer(Engineer engineer) {
        //not needed
    }
}
