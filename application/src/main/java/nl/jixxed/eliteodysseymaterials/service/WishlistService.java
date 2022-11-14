package nl.jixxed.eliteodysseymaterials.service;

import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;

public class WishlistService {

    public static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    public static Integer getWishlistCount(final OdysseyMaterial odysseyMaterial) {
        return APPLICATION_STATE.getPreferredCommander().map(commander ->
                APPLICATION_STATE.getWishlists(commander.getFid()).getSelectedWishlist().getItems().stream()
                        .map(odysseyWishlistBlueprint -> OdysseyBlueprintConstants.getRecipe(odysseyWishlistBlueprint.getRecipeName()).getRequiredAmount(odysseyMaterial))
                        .mapToInt(Integer::intValue)
                        .sum()
        ).orElse(0);

    }

    public static Integer getWishlistCount(final HorizonsMaterial horizonsMaterial) {
        return APPLICATION_STATE.getPreferredCommander().map(commander ->
                APPLICATION_STATE.getHorizonsWishlists(commander.getFid()).getSelectedWishlist().getItems().stream()
                        .map(horizonsWishlistBlueprint -> ((HorizonsBlueprint) horizonsWishlistBlueprint).getRequiredAmount(horizonsMaterial))
                        .mapToInt(Integer::intValue)
                        .sum()
        ).orElse(0);
    }

    public static boolean isMaterialOnWishlist(final OdysseyMaterial odysseyMaterial) {
        return APPLICATION_STATE.getPreferredCommander()
                .map(commander -> APPLICATION_STATE.getWishlists(commander.getFid()).getWishlists().stream()
                        .anyMatch(wishlist -> wishlist.getItems().stream()
                                .anyMatch(wishlistBlueprint -> OdysseyBlueprintConstants.getRecipe(wishlistBlueprint.getRecipeName()).hasIngredient(odysseyMaterial))))
                .orElse(false);

    }

    public static boolean isMaterialOnWishlist(final HorizonsMaterial horizonsMaterial) {
        return APPLICATION_STATE.getPreferredCommander()
                .map(commander -> APPLICATION_STATE.getHorizonsWishlists(commander.getFid()).getWishlists().stream()
                        .anyMatch(wishlist -> wishlist.getItems().stream()
                                .anyMatch(wishlistBlueprint -> ((HorizonsBlueprint) wishlistBlueprint).hasIngredient(horizonsMaterial))))
                .orElse(false);

    }
}
