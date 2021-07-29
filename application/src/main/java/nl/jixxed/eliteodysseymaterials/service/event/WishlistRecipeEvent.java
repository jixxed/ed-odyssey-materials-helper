package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.WishlistRecipe;
import nl.jixxed.eliteodysseymaterials.enums.Action;

@AllArgsConstructor
@Getter
public class WishlistRecipeEvent implements Event {
    private final String fid;
    private final WishlistRecipe wishlistRecipe;
    private final Action action;
}
