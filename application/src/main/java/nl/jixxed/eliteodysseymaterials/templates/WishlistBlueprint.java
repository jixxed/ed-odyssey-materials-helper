package nl.jixxed.eliteodysseymaterials.templates;

import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import nl.jixxed.eliteodysseymaterials.RecipeConstants;
import nl.jixxed.eliteodysseymaterials.domain.ModuleRecipe;
import nl.jixxed.eliteodysseymaterials.domain.Recipe;
import nl.jixxed.eliteodysseymaterials.enums.Action;
import nl.jixxed.eliteodysseymaterials.enums.RecipeName;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.BlueprintClickEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.WishlistEvent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class WishlistBlueprint extends HBox {
    final Label label = new Label();
    final Button close = new Button("X");
    final RecipeName recipeName;
    final Recipe recipe;
    final Set<WishlistIngredient> wishlistIngredients = new HashSet<>();
    final Set<WishlistIngredient> otherIngredients = new HashSet<>();

    public WishlistBlueprint(final RecipeName recipeName) {
        super();
        this.recipeName = recipeName;
        this.recipe = RecipeConstants.getRecipe(recipeName);
        this.label.textProperty().bind(LocaleService.getStringBinding(recipeName.getLocalizationKey()));
        this.label.setOnMouseClicked(event -> EventService.publish(new BlueprintClickEvent(recipeName)));
        this.close.setOnAction(event -> EventService.publish(new WishlistEvent(recipeName, Action.REMOVED)));
        this.getChildren().addAll(this.label, this.close);
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
    }

    private void highlight(final boolean enable) {
        if (enable) {
            this.getStyleClass().add("wishlist-highlight");
        } else {
            this.getStyleClass().removeAll("wishlist-highlight");
        }
    }

    public void addWishlistIngredients(final List<WishlistIngredient> wishlistIngredients) {
        this.wishlistIngredients.addAll(wishlistIngredients.stream().filter(wishlistIngredient -> this.recipe.hasIngredient(wishlistIngredient.getMaterial())).collect(Collectors.toSet()));
        this.otherIngredients.addAll(wishlistIngredients.stream().filter(wishlistIngredient -> !this.recipe.hasIngredient(wishlistIngredient.getMaterial())).collect(Collectors.toSet()));
    }

    public Recipe getRecipe() {
        return this.recipe;
    }

    public RecipeName getRecipeName() {
        return this.recipeName;
    }
}
