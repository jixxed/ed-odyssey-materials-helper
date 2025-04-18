package nl.jixxed.eliteodysseymaterials.domain;

import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;

@NoArgsConstructor
public final class HorizonsEngineerWishlistBlueprint extends HorizonsWishlistBlueprint {
    public HorizonsEngineerWishlistBlueprint(final HorizonsBlueprintName recipeName, final boolean visible) {
        super(recipeName, visible);
    }
}
