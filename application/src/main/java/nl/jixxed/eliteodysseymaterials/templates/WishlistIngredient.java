package nl.jixxed.eliteodysseymaterials.templates;

import nl.jixxed.eliteodysseymaterials.enums.Material;
import nl.jixxed.eliteodysseymaterials.enums.StorageType;

public class WishlistIngredient extends Ingredient {

    WishlistIngredient(final StorageType storageType, final Material material, final Integer amount, final Integer amountAvailable) {
        super(storageType, material, amount, amountAvailable);
    }

    @Override
    public void update() {
        if (this.getAmountAvailable() >= Integer.parseInt(this.getAmountRequiredLabel().getText())) {
            this.getAmountAvailableLabel().setText(this.getAmountAvailable().toString());
            this.getStyleClass().removeAll("ingredient-filled", "ingredient-unfilled");
            this.getStyleClass().addAll("ingredient-filled");
        } else {
            this.getAmountAvailableLabel().setText(this.getAmountAvailable().toString());
            this.getStyleClass().removeAll("ingredient-filled", "ingredient-unfilled");
            this.getStyleClass().addAll("ingredient-unfilled");
        }
    }

    void highlight(final boolean enable, final Integer amountRequiredForRecipe) {
        if (enable) {
            this.getStyleClass().add("wishlist-highlight");
            this.getAmountRequiredLabel().setText(amountRequiredForRecipe.toString());
        } else {
            this.getStyleClass().removeAll("wishlist-highlight");
            this.getAmountRequiredLabel().setText(this.getAmountRequired().toString());
        }
        update();
    }

    void lowlight(final boolean enable) {
        if (enable) {
            this.getStyleClass().add("wishlist-lowlight");
        } else {
            this.getStyleClass().removeAll("wishlist-lowlight");
        }
    }

}
