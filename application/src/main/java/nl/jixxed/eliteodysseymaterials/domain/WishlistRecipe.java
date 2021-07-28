package nl.jixxed.eliteodysseymaterials.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.enums.RecipeName;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class WishlistRecipe {
    private final RecipeName recipeName;
    private boolean visible;

    public void setVisible(final boolean visible) {
        this.visible = visible;
    }
}
