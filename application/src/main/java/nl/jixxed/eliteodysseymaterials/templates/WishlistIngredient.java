package nl.jixxed.eliteodysseymaterials.templates;

import nl.jixxed.eliteodysseymaterials.enums.Material;
import nl.jixxed.eliteodysseymaterials.enums.StorageType;

class WishlistIngredient extends MaterialIngredient {

    private static final String INGREDIENT_FILLED_CLASS = "ingredient-filled";
    private static final String INGREDIENT_UNFILLED_CLASS = "ingredient-unfilled";

    WishlistIngredient(final StorageType storageType, final Material material, final Integer amount, final Integer amountAvailable) {
        super(storageType, material, amount, amountAvailable);
    }

    @Override
    protected void update() {
        if (this.getAmountAvailable() >= Integer.parseInt(this.getAmountRequiredLabel().getText())) {
            this.getAmountAvailableLabel().setText(this.getAmountAvailable().toString());
            this.getStyleClass().removeAll(INGREDIENT_FILLED_CLASS, INGREDIENT_UNFILLED_CLASS);
            this.getStyleClass().addAll(INGREDIENT_FILLED_CLASS);
        } else {
            this.getAmountAvailableLabel().setText(this.getAmountAvailable().toString());
            this.getStyleClass().removeAll(INGREDIENT_FILLED_CLASS, INGREDIENT_UNFILLED_CLASS);
            this.getStyleClass().addAll(INGREDIENT_UNFILLED_CLASS);
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
