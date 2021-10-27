package nl.jixxed.eliteodysseymaterials.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.enums.RecipeName;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WishlistRecipe {
    private RecipeName recipeName;
    private boolean visible;
}
