package nl.jixxed.eliteodysseymaterials.templates.horizons.wishlist;

import lombok.EqualsAndHashCode;
import nl.jixxed.eliteodysseymaterials.enums.Commodity;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsStorageType;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.templates.horizons.HorizonsMaterialIngredient;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class HorizonsWishlistIngredient extends HorizonsMaterialIngredient {

    private static final String INGREDIENT_FILLED_CLASS = "ingredient-filled";
    private static final String INGREDIENT_UNFILLED_CLASS = "ingredient-unfilled";
    private static final String INGREDIENT_FILLED_NOT_SHIPLOCKER_CLASS = "ingredient-filled-partial";

    HorizonsWishlistIngredient(final HorizonsStorageType storageType, final HorizonsMaterial horizonsMaterial, final Integer amountRequired, final Integer amountAvailable) {
        super(storageType, horizonsMaterial, amountRequired, amountAvailable);
        initComponents();
    }

    @SuppressWarnings("java:S2177")
    private void initComponents() {
        this.getStyleClass().add("wishlist-ingredient");
    }

    @Override
    protected void update() {

        final Integer materialCountShip;
        final Integer materialCountBoth;
        if (getHorizonsMaterial() instanceof Commodity commodity) {
            materialCountShip = StorageService.getCommodityCount(commodity, StoragePool.SHIP);
            materialCountBoth = materialCountShip + StorageService.getCommodityCount(commodity, StoragePool.FLEETCARRIER);
        } else {
            materialCountBoth = StorageService.getMaterialCount(getHorizonsMaterial());
            materialCountShip = materialCountBoth;
        }
        this.getRightAmountLabel().setText(materialCountBoth.toString());
        this.getStyleClass().removeAll(INGREDIENT_FILLED_CLASS, INGREDIENT_UNFILLED_CLASS, INGREDIENT_FILLED_NOT_SHIPLOCKER_CLASS);
        if (materialCountBoth >= Integer.parseInt(this.getLeftAmountLabel().getText()) && materialCountShip < Integer.parseInt(this.getLeftAmountLabel().getText())) {
            this.getStyleClass().addAll(INGREDIENT_FILLED_NOT_SHIPLOCKER_CLASS);
        } else if (this.getRightAmount() >= Integer.parseInt(this.getLeftAmountLabel().getText())) {
            this.getStyleClass().addAll(INGREDIENT_FILLED_CLASS);
        } else {
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
