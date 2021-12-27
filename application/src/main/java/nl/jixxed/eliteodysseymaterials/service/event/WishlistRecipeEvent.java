package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.WishlistRecipe;
import nl.jixxed.eliteodysseymaterials.enums.Action;

import java.util.List;

@AllArgsConstructor
@Getter
public class WishlistRecipeEvent implements Event {
    private final String fid;
    private final String wishlistUUID;
    private final List<WishlistRecipe> wishlistRecipes;
    private final Action action;
}
