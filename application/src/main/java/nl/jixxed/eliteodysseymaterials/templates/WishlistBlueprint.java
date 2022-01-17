package nl.jixxed.eliteodysseymaterials.templates;

import javafx.animation.FadeTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TooltipBuilder;
import nl.jixxed.eliteodysseymaterials.constants.RecipeConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ModuleRecipe;
import nl.jixxed.eliteodysseymaterials.domain.Recipe;
import nl.jixxed.eliteodysseymaterials.domain.WishlistRecipe;
import nl.jixxed.eliteodysseymaterials.enums.Action;
import nl.jixxed.eliteodysseymaterials.enums.Craftability;
import nl.jixxed.eliteodysseymaterials.enums.RecipeCategory;
import nl.jixxed.eliteodysseymaterials.enums.RecipeName;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class WishlistBlueprint extends HBox {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final String VISIBLE_STYLE_CLASS = "visible";
    private static final String WISHLIST_VISIBLE_ICON_STYLE_CLASS = "wishlist-visible-icon";
    private static int counter = 0;

    private boolean visible;
    private final Integer sequenceID;
    private final WishlistRecipe wishlistRecipe;
    private final RecipeCategory recipeCategory;
    private final Recipe recipe;
    private final String wishlistUUID;

    private Button visibilityButton;
    private ResizableImageView visibilityImage;
    private Label wishlistRecipeName;
    private Button removeBlueprint;
    private final Set<WishlistIngredient> wishlistIngredients = new HashSet<>();
    private final Set<WishlistIngredient> otherIngredients = new HashSet<>();
    private EventListener<StorageEvent> storageEventEventListener;
    private Tooltip tooltip;

    public WishlistBlueprint(final String wishlistUUID, final WishlistRecipe wishlistRecipe) {
        this.wishlistUUID = wishlistUUID;
        this.wishlistRecipe = wishlistRecipe;
        this.sequenceID = counter++;
        this.recipeCategory = RecipeConstants.getRecipeCategory(wishlistRecipe.getRecipeName());
        this.recipe = RecipeConstants.getRecipe(wishlistRecipe.getRecipeName());
        initComponents();
        initEventHandling();
    }

    private void initComponents() {
        this.visibilityImage = ResizableImageViewBuilder.builder()
                .withStyleClass("wishlist-visible-image")
                .withImage("/images/other/visible_blue.png")
                .build();
        this.visibilityButton = ButtonBuilder.builder()
                .withStyleClasses(WISHLIST_VISIBLE_ICON_STYLE_CLASS, VISIBLE_STYLE_CLASS)
                .withOnAction(event -> setVisibility(!this.visible))
                .withGraphic(this.visibilityImage)
                .build();
        setVisibility(this.wishlistRecipe.isVisible());

        this.wishlistRecipeName = LabelBuilder.builder()
                .withStyleClass("wishlist-label")
                .withText(LocaleService.getStringBinding(this.wishlistRecipe.getRecipeName().getLocalizationKey()))
                .withOnMouseClicked(event -> EventService.publish(new BlueprintClickEvent(this.wishlistRecipe.getRecipeName())))
                .withHoverProperty((observable, oldValue, newValue) -> {
                    this.wishlistIngredients.forEach(wishlistIngredient -> wishlistIngredient.highlight(newValue, this.recipe.getRequiredAmount(wishlistIngredient.getMaterial())));
                    this.otherIngredients.forEach(wishlistIngredient -> wishlistIngredient.lowlight(newValue));
                    this.highlight(newValue);
                })
                .build();
        this.getChildren().addAll(this.visibilityButton, this.wishlistRecipeName);

        this.removeBlueprint = ButtonBuilder.builder()
                .withStyleClass("wishlist-item-close").withNonLocalizedText("X")
                .withOnAction(event -> remove())
                .build();
        this.getChildren().add(this.removeBlueprint);
        this.getStyleClass().add("wishlist-item");


        if (this.recipe instanceof ModuleRecipe moduleRecipe) {
            this.tooltip = TooltipBuilder.builder()
                    .withText(LocaleService.getToolTipStringBinding(moduleRecipe, "tab.wishlist.blueprint.tooltip"))
                    .withShowDelay(Duration.millis(100))
                    .build();
            Tooltip.install(this.wishlistRecipeName, this.tooltip);
        }
        initFadeTransition();
        final Craftability craftability = APPLICATION_STATE.getCraftability(this.getRecipeName());
        this.canCraft(craftability);
    }

    public void remove() {
        EventService.removeListener(this.storageEventEventListener);
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> EventService.publish(new WishlistRecipeEvent(commander.getFid(), this.wishlistUUID, List.of(this.wishlistRecipe), Action.REMOVED)));
    }

    private void initFadeTransition() {
        final FadeTransition fadeTransition = new FadeTransition(Duration.millis(2000));
        fadeTransition.setNode(this);
        fadeTransition.setFromValue(0.3);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }

    private void initEventHandling() {
        this.storageEventEventListener = EventService.addListener(this, StorageEvent.class, storageEvent -> {
            final Craftability craftability = APPLICATION_STATE.getCraftability(this.getRecipeName());
            this.canCraft(craftability);
        });
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
            if (this.recipe instanceof ModuleRecipe moduleRecipe) {
                this.tooltip.textProperty().bind(LocaleService.getToolTipStringBinding(moduleRecipe, "tab.wishlist.blueprint.tooltip"));
            }
        } else if (Craftability.CRAFTABLE_WITH_TRADE.equals(craftability)) {
            this.wishlistRecipeName.getStyleClass().add("wishlist-craftable-with-trade");
            if (this.recipe instanceof ModuleRecipe moduleRecipe) {
                this.tooltip.textProperty().bind(LocaleService.getToolTipStringBinding(moduleRecipe, "tab.wishlist.blueprint.tooltip.craftable"));
            }
        }
    }

    void addWishlistIngredients(final List<WishlistIngredient> wishlistIngredients) {
        this.wishlistIngredients.clear();
        this.otherIngredients.clear();
        this.wishlistIngredients.addAll(wishlistIngredients.stream().filter(wishlistIngredient -> this.recipe.hasIngredient(wishlistIngredient.getMaterial())).collect(Collectors.toSet()));
        this.otherIngredients.addAll(wishlistIngredients.stream().filter(wishlistIngredient -> !this.recipe.hasIngredient(wishlistIngredient.getMaterial())).collect(Collectors.toSet()));
    }

    void setVisibility(final boolean visible) {
        this.visible = visible;
        this.wishlistRecipe.setVisible(this.visible);
        this.visibilityImage.setImage(new Image(getClass().getResourceAsStream(this.visible ? "/images/other/visible_blue.png" : "/images/other/invisible_gray.png")));
        if (this.visible) {
            this.visibilityButton.getStyleClass().add(VISIBLE_STYLE_CLASS);
        } else {
            this.visibilityButton.getStyleClass().remove(VISIBLE_STYLE_CLASS);
        }
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> EventService.publish(new WishlistRecipeEvent(commander.getFid(), this.wishlistUUID, List.of(this.wishlistRecipe), Action.VISIBILITY_CHANGED)));
    }

    public Recipe getRecipe() {
        return this.recipe;
    }

    public RecipeName getRecipeName() {
        return this.wishlistRecipe.getRecipeName();
    }

    RecipeCategory getRecipeCategory() {
        return this.recipeCategory;
    }

    Integer getSequenceID() {
        return this.sequenceID;
    }

    public boolean isVisibleBlueprint() {
        return this.visible;
    }

    WishlistRecipe getWishlistRecipe() {
        return this.wishlistRecipe;
    }

    void onDestroy() {
        EventService.removeListener(this.storageEventEventListener);
    }
}
