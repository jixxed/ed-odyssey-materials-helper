package nl.jixxed.eliteodysseymaterials.templates.odyssey.wishlist;

import lombok.EqualsAndHashCode;
import nl.jixxed.eliteodysseymaterials.domain.Storage;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyStorageType;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.OdysseyMaterialIngredient;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
class OdysseyWishlistIngredient extends OdysseyMaterialIngredient {

    private static final String INGREDIENT_FILLED_CLASS = "ingredient-filled";
    private static final String INGREDIENT_UNFILLED_CLASS = "ingredient-unfilled";
    private static final String INGREDIENT_FILLED_NOT_SHIPLOCKER_CLASS = "ingredient-filled-partial";

    OdysseyWishlistIngredient(final OdysseyStorageType storageType, final OdysseyMaterial odysseyMaterial, final Integer amountRequired, final Integer amountAvailable) {
        super(storageType, odysseyMaterial, amountRequired, amountAvailable);
        initComponents();
    }

    @SuppressWarnings("java:S2177")
    private void initComponents() {
        this.getStyleClass().add("wishlist-ingredient");
    }

    @Override
    protected void update() {

        final Storage storage = StorageService.getMaterials(this.getOdysseyMaterial().getStorageType()).get(this.getOdysseyMaterial());
        final int leftAmount = Integer.parseInt(this.getLeftAmountLabel().getText());
        this.getStyleClass().removeAll(INGREDIENT_FILLED_NOT_SHIPLOCKER_CLASS, INGREDIENT_FILLED_CLASS, INGREDIENT_UNFILLED_CLASS);
        if (storage.getTotalValue() >= leftAmount && storage.getShipLockerValue() + storage.getBackPackValue() < leftAmount) {
            this.getRightAmountLabel().setText(this.getRightAmount().toString());
            this.getStyleClass().addAll(INGREDIENT_FILLED_NOT_SHIPLOCKER_CLASS);
        } else if (storage.getTotalValue() >= leftAmount) {
            this.getRightAmountLabel().setText(this.getRightAmount().toString());
            this.getStyleClass().addAll(INGREDIENT_FILLED_CLASS);
        } else {
            this.getRightAmountLabel().setText(this.getRightAmount().toString());
            this.getStyleClass().addAll(INGREDIENT_UNFILLED_CLASS);
        }
    }

    void highlight(final boolean enable, final Integer amountRequiredForRecipe) {
        if (enable) {
            this.getStyleClass().add("wishlist-highlight");
            this.getLeftAmountLabel().setText(amountRequiredForRecipe.toString());
        } else {
            this.getStyleClass().removeAll("wishlist-highlight");
            this.getLeftAmountLabel().setText(this.getLeftAmount().toString());
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
