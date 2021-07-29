package nl.jixxed.eliteodysseymaterials.templates;

import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import nl.jixxed.eliteodysseymaterials.RecipeConstants;
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
    private final Integer sequenceID;
    private final Button visibility = new Button();
    private final ImageView imageView = new ImageView();
    private boolean visible;
    private final Label label = new Label();
    private final Button close = new Button("X");
    private final WishlistRecipe wishlistRecipe;
    private final RecipeCategory recipeCategory;
    private final Recipe recipe;
    private final Set<WishlistIngredient> wishlistIngredients = new HashSet<>();
    private final Set<WishlistIngredient> otherIngredients = new HashSet<>();

    WishlistBlueprint(final WishlistRecipe wishlistRecipe) {
        super();
        this.wishlistRecipe = wishlistRecipe;
        this.sequenceID = counter++;
        this.recipeCategory = RecipeConstants.getRecipeCategory(wishlistRecipe.getRecipeName());
        this.imageView.setFitWidth(24);
        this.imageView.setFitHeight(24);
        this.visibility.setGraphic(this.imageView);
        setVisibility(wishlistRecipe.isVisible());
        this.visibility.setOnMouseClicked(event -> {
            setVisibility(!this.visible);
            APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> EventService.publish(new WishlistRecipeEvent(commander.getFid(), wishlistRecipe, Action.VISIBILITY_CHANGED)));

        });
        this.visibility.getStyleClass().addAll("wishlist-visible-icon", "visible");

        this.recipe = RecipeConstants.getRecipe(wishlistRecipe.getRecipeName());
        this.label.textProperty().bind(LocaleService.getStringBinding(wishlistRecipe.getRecipeName().getLocalizationKey()));
        this.label.setOnMouseClicked(event -> EventService.publish(new BlueprintClickEvent(wishlistRecipe.getRecipeName())));

        this.close.setOnAction(event -> APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> EventService.publish(new WishlistRecipeEvent(commander.getFid(), wishlistRecipe, Action.REMOVED))));
        this.close.getStyleClass().add("wishlist-item-close");
        this.getChildren().addAll(this.visibility, this.label, this.close);
        this.getStyleClass().add("wishlist-item");
        this.label.hoverProperty().addListener((ChangeListener<? super Boolean>) (observable, oldValue, newValue) -> {
            this.wishlistIngredients.forEach(wishlistIngredient -> wishlistIngredient.highlight(newValue, this.recipe.getRequiredAmount(wishlistIngredient.getMaterial())));
            this.otherIngredients.forEach(wishlistIngredient -> wishlistIngredient.lowlight(newValue));
            this.highlight(newValue);
        });

        if (this.recipe instanceof ModuleRecipe) {
            final Tooltip tooltip = new Tooltip();
            tooltip.textProperty().bind(LocaleService.getToolTipStringBinding((ModuleRecipe) this.recipe));
            tooltip.setShowDelay(Duration.millis(100));
            Tooltip.install(this.label, tooltip);
        }
        final FadeTransition fadeTransition = new FadeTransition(Duration.millis(2000));
        fadeTransition.setNode(this);
        fadeTransition.setFromValue(0.3);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
        EventService.addListener(StorageEvent.class, storageEvent -> {
            final int amountCraftable = APPLICATION_STATE.amountCraftable(this.getRecipeName());
            this.canCraft(amountCraftable > 0);
        });
        final int amountCraftable = APPLICATION_STATE.amountCraftable(this.getRecipeName());
        this.canCraft(amountCraftable > 0);
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
            this.label.getStyleClass().add("wishlist-craftable");
        } else {
            this.label.getStyleClass().removeAll("wishlist-craftable");
        }
    }

    void addWishlistIngredients(final List<WishlistIngredient> wishlistIngredients) {
        this.wishlistIngredients.addAll(wishlistIngredients.stream().filter(wishlistIngredient -> this.recipe.hasIngredient(wishlistIngredient.getMaterial())).collect(Collectors.toSet()));
        this.otherIngredients.addAll(wishlistIngredients.stream().filter(wishlistIngredient -> !this.recipe.hasIngredient(wishlistIngredient.getMaterial())).collect(Collectors.toSet()));
    }

    private void setVisibility(final boolean visible) {
        this.visible = visible;
        this.wishlistRecipe.setVisible(this.visible);
        this.imageView.setImage(new Image(getClass().getResourceAsStream(this.visible ? "/images/other/visible_blue.png" : "/images/other/invisible_gray.png")));
        if (this.visible) {
            this.visibility.getStyleClass().add("visible");
        } else {
            this.visibility.getStyleClass().remove("visible");
        }
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

    boolean isVisibleBlueprint() {
        return this.visible;
    }

    WishlistRecipe getWishlistRecipe() {
        return this.wishlistRecipe;
    }
}
