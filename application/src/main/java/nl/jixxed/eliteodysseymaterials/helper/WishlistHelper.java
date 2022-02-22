package nl.jixxed.eliteodysseymaterials.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.WishlistBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.BlueprintName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WishlistHelper {
    public static List<WishlistBlueprint> convertWishlist(final String recipes) {
        final List<WishlistBlueprint> wishlist = new ArrayList<>();
        Arrays.stream(recipes.split(","))
                .filter(recipe -> !recipes.isBlank())
                .map(recipe -> {
                    final String[] splittedRecipe = recipe.split(":");
                    return new WishlistBlueprint(BlueprintName.forName(splittedRecipe[0]), Boolean.parseBoolean(splittedRecipe[1]));
                })
                .forEach(wishlist::add);
        return wishlist;
    }

    public static String convertWishlist(final List<WishlistBlueprint> recipes) {
        return recipes.stream().map(recipe -> recipe.getRecipeName().name() + ":" + recipe.isVisible()).collect(Collectors.joining(","));
    }
}
