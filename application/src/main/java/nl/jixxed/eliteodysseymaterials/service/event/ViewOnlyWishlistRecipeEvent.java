package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.WishlistRecipe;
import nl.jixxed.eliteodysseymaterials.enums.Action;

@AllArgsConstructor
@Getter
public class ViewOnlyWishlistRecipeEvent implements Event {
    private final WishlistRecipe wishlistRecipe;
    private final Action action;
}
