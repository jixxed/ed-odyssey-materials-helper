package nl.jixxed.eliteodysseymaterials.templates;

import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.constants.RecipeConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ModuleRecipe;
import nl.jixxed.eliteodysseymaterials.domain.Recipe;
import nl.jixxed.eliteodysseymaterials.domain.WishlistRecipe;
import nl.jixxed.eliteodysseymaterials.enums.Action;
import nl.jixxed.eliteodysseymaterials.enums.RecipeCategory;
import nl.jixxed.eliteodysseymaterials.enums.RecipeName;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.BlueprintClickEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;
import nl.jixxed.eliteodysseymaterials.service.event.WishlistRecipeEvent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class WishlistBlueprint extends HBox {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
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
                .withStyleClasses("wishlist-visible-icon", "visible")
                .withOnAction(event -> {
                    setVisibility(!this.visible);
                })
                .build();
        this.visibilityButton.setGraphic(this.visibilityImage);
        setVisibility(this.wishlistRecipe.isVisible());

        this.wishlistRecipeName = LabelBuilder.builder()
                .withText(LocaleService.getStringBinding(this.wishlistRecipe.getRecipeName().getLocalizationKey()))
                .withOnMouseClicked(event -> EventService.publish(new BlueprintClickEvent(this.wishlistRecipe.getRecipeName())))
                .withHoverProperty((ChangeListener<? super Boolean>) (observable, oldValue, newValue) -> {
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


        if (this.recipe instanceof ModuleRecipe) {
            final Tooltip tooltip = new Tooltip();
            tooltip.textProperty().bind(LocaleService.getToolTipStringBinding((ModuleRecipe) this.recipe));
            tooltip.setShowDelay(Duration.millis(100));
            Tooltip.install(this.wishlistRecipeName, tooltip);
        }
        initFadeTransition();
        final boolean isCraftable = APPLICATION_STATE.amountCraftable(this.getRecipeName()) > 0;
        this.canCraft(isCraftable);
    }

    public void remove() {
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> EventService.publish(new WishlistRecipeEvent(commander.getFid(), this.wishlistUUID, this.wishlistRecipe, Action.REMOVED)));
    }

    private void initFadeTransition() {
        final FadeTransition fadeTransition = new FadeTransition(Duration.millis(2000));
        fadeTransition.setNode(this);
        fadeTransition.setFromValue(0.3);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }

    private void initEventHandling() {
        EventService.addListener(StorageEvent.class, storageEvent -> {
            final int amountCraftable = APPLICATION_STATE.amountCraftable(this.getRecipeName());
            this.canCraft(amountCraftable > 0);
        });
    }

    private void highlight(final boolean enable) {
        if (enable) {
            this.getStyleClass().add("wishlist-highlight");
        } else {
            this.getStyleClass().removeAll("wishlist-highlight");
        }
    }

    private void canCraft(final boolean isCraftable) {
        if (isCraftable) {
            this.wishlistRecipeName.getStyleClass().add("wishlist-craftable");
        } else {
            this.wishlistRecipeName.getStyleClass().removeAll("wishlist-craftable");
        }
    }

    void addWishlistIngredients(final List<WishlistIngredient> wishlistIngredients) {
        this.wishlistIngredients.addAll(wishlistIngredients.stream().filter(wishlistIngredient -> this.recipe.hasIngredient(wishlistIngredient.getMaterial())).collect(Collectors.toSet()));
        this.otherIngredients.addAll(wishlistIngredients.stream().filter(wishlistIngredient -> !this.recipe.hasIngredient(wishlistIngredient.getMaterial())).collect(Collectors.toSet()));
    }

    void setVisibility(final boolean visible) {
        this.visible = visible;
        this.wishlistRecipe.setVisible(this.visible);
        this.visibilityImage.setImage(new Image(getClass().getResourceAsStream(this.visible ? "/images/other/visible_blue.png" : "/images/other/invisible_gray.png")));
        if (this.visible) {
            this.visibilityButton.getStyleClass().add("visible");
        } else {
            this.visibilityButton.getStyleClass().remove("visible");
        }
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> EventService.publish(new WishlistRecipeEvent(commander.getFid(), this.wishlistUUID, this.wishlistRecipe, Action.VISIBILITY_CHANGED)));
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
}
