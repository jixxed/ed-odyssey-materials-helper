package nl.jixxed.eliteodysseymaterials.helper;

import nl.jixxed.eliteodysseymaterials.domain.WishlistRecipe;
import nl.jixxed.eliteodysseymaterials.enums.RecipeName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WishlistHelper {
    public static List<WishlistRecipe> convertWishlist(final String recipes) {
        final List<WishlistRecipe> wishlist = new ArrayList<>();
        Arrays.stream(recipes.split(","))
                .filter(recipe -> !recipes.isBlank())
                .map(recipe -> {
                    final String[] splittedRecipe = recipe.split(":");
                    return new WishlistRecipe(RecipeName.forName(splittedRecipe[0]), Boolean.parseBoolean(splittedRecipe[1]));
                })
                .forEach(wishlist::add);
        return wishlist;
    }

    public static String convertWishlist(final List<WishlistRecipe> recipes) {
        return recipes.stream().map(recipe -> recipe.getRecipeName().name() + ":" + recipe.isVisible()).collect(Collectors.joining(","));
    }
}
