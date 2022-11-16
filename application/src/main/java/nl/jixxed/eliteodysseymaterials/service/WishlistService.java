package nl.jixxed.eliteodysseymaterials.service;

import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.*;

public class WishlistService {

    public static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    public static Integer getCurrentWishlistCount(final OdysseyMaterial odysseyMaterial) {
        return APPLICATION_STATE.getPreferredCommander().map(commander ->
                        APPLICATION_STATE.getWishlists(commander.getFid()).getWishlists().stream().mapToInt(wishlist -> wishlist.getItems().stream()
                                        .map(odysseyWishlistBlueprint -> OdysseyBlueprintConstants.getRecipe(odysseyWishlistBlueprint.getRecipeName()).getRequiredAmount(odysseyMaterial))
                                        .mapToInt(Integer::intValue)
                                        .sum())
                                .sum())
                .orElse(0);

    }

    public static Integer getAllWishlistsCount(final OdysseyMaterial odysseyMaterial) {
        return APPLICATION_STATE.getPreferredCommander().map(commander ->
                APPLICATION_STATE.getWishlists(commander.getFid()).getSelectedWishlist().getItems().stream()
                        .map(odysseyWishlistBlueprint -> OdysseyBlueprintConstants.getRecipe(odysseyWishlistBlueprint.getRecipeName()).getRequiredAmount(odysseyMaterial))
                        .mapToInt(Integer::intValue)
                        .sum()
        ).orElse(0);

    }

    public static Integer getCurrentWishlistCount(final HorizonsMaterial horizonsMaterial) {
        return APPLICATION_STATE.getPreferredCommander().map(commander ->
                APPLICATION_STATE.getHorizonsWishlists(commander.getFid()).getSelectedWishlist().getItems().stream()
                        .mapToInt(horizonsWishlistBlueprint -> {
                            if (horizonsWishlistBlueprint instanceof HorizonsModuleWishlistBlueprint horizonsModuleWishlistBlueprint) {//modules
                                return horizonsModuleWishlistBlueprint.getBlueprintGradeRolls().entrySet().stream().mapToInt(entry -> {
                                    final HorizonsBlueprint blueprint = (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipe(horizonsModuleWishlistBlueprint.getRecipeName(), getBlueprintType(horizonsModuleWishlistBlueprint), entry.getKey());
                                    return blueprint.getRequiredAmount(horizonsMaterial) * entry.getValue();
                                }).sum();
                            } else {//other
                                final HorizonsBlueprint blueprint = (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipe((HorizonsBlueprintName) horizonsWishlistBlueprint.getRecipeName(), getBlueprintType(horizonsWishlistBlueprint), getBlueprintGrade(horizonsWishlistBlueprint));
                                return blueprint.getRequiredAmount(horizonsMaterial);
                            }
                        })
                        .sum()
        ).orElse(0);
    }

    public static Integer getAllWishlistsCount(final HorizonsMaterial horizonsMaterial) {
        return APPLICATION_STATE.getPreferredCommander().map(commander ->
                APPLICATION_STATE.getHorizonsWishlists(commander.getFid()).getWishlists().stream().mapToInt(wishlist -> wishlist.getItems().stream()
                        .mapToInt(horizonsWishlistBlueprint -> {
                            if (horizonsWishlistBlueprint instanceof HorizonsModuleWishlistBlueprint horizonsModuleWishlistBlueprint) {//modules
                                return horizonsModuleWishlistBlueprint.getBlueprintGradeRolls().entrySet().stream().mapToInt(entry -> {
                                    final HorizonsBlueprint blueprint = (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipe(horizonsModuleWishlistBlueprint.getRecipeName(), getBlueprintType(horizonsModuleWishlistBlueprint), entry.getKey());
                                    return blueprint.getRequiredAmount(horizonsMaterial) * entry.getValue();
                                }).sum();
                            } else {//other
                                final HorizonsBlueprint blueprint = (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipe((HorizonsBlueprintName) horizonsWishlistBlueprint.getRecipeName(), getBlueprintType(horizonsWishlistBlueprint), getBlueprintGrade(horizonsWishlistBlueprint));
                                return blueprint.getRequiredAmount(horizonsMaterial);
                            }
                        })
                        .sum()
                ).sum()).orElse(0);
    }

    private static HorizonsBlueprintType getBlueprintType(final WishlistBlueprint<HorizonsBlueprintName> blueprint) {
        if (blueprint instanceof HorizonsModuleWishlistBlueprint horizonsModuleWishlistBlueprint) {
            return horizonsModuleWishlistBlueprint.getBlueprintType();
        } else if (blueprint instanceof HorizonsExperimentalWishlistBlueprint horizonsExperimentalWishlistBlueprint) {
            return horizonsExperimentalWishlistBlueprint.getBlueprintType();
        } else if (blueprint instanceof HorizonsTechBrokerWishlistBlueprint horizonsTechBrokerWishlistBlueprint) {
            return horizonsTechBrokerWishlistBlueprint.getBlueprintType();
        } else if (blueprint instanceof HorizonsEngineerWishlistBlueprint) {
            return HorizonsBlueprintType.ENGINEER;
        }
        return null;
    }

    private static HorizonsBlueprintGrade getBlueprintGrade(final WishlistBlueprint<HorizonsBlueprintName> blueprint) {
        if (blueprint instanceof HorizonsSynthesisWishlistBlueprint horizonsSynthesisWishlistBlueprint) {
            return horizonsSynthesisWishlistBlueprint.getBlueprintGrade();
        }
        return HorizonsBlueprintGrade.NONE;
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
                                .anyMatch(wishlistBlueprint -> {
                                    if (wishlistBlueprint instanceof HorizonsModuleWishlistBlueprint horizonsModuleWishlistBlueprint) {//modules
                                        return horizonsModuleWishlistBlueprint.getBlueprintGradeRolls().entrySet().stream().anyMatch(entry -> {
                                            final HorizonsBlueprint blueprint = (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipe(horizonsModuleWishlistBlueprint.getRecipeName(), getBlueprintType(horizonsModuleWishlistBlueprint), entry.getKey());
                                            return blueprint.hasIngredient(horizonsMaterial);
                                        });
                                    } else {//other
                                        final HorizonsBlueprint blueprint = (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipe((HorizonsBlueprintName) wishlistBlueprint.getRecipeName(), getBlueprintType(wishlistBlueprint), getBlueprintGrade(wishlistBlueprint));
                                        return blueprint.hasIngredient(horizonsMaterial);
                                    }

                                })))
                .orElse(false);

    }
}
