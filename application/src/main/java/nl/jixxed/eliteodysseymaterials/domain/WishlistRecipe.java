package nl.jixxed.eliteodysseymaterials.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import nl.jixxed.eliteodysseymaterials.enums.RecipeName;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class WishlistRecipe {
    private final RecipeName recipeName;
    @Setter
    private boolean visible;

}
