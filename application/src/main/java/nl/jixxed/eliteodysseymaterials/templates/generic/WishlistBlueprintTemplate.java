package nl.jixxed.eliteodysseymaterials.templates.generic;

import nl.jixxed.eliteodysseymaterials.domain.WishlistBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.BlueprintName;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableComponent;
import nl.jixxed.eliteodysseymaterials.templates.horizons.wishlist.HorizonsWishlistBlueprintTemplate;
import nl.jixxed.eliteodysseymaterials.templates.horizons.wishlist.HorizonsWishlistModuleBlueprintTemplate;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.wishlist.OdysseyWishlistBlueprintTemplate;

public sealed interface WishlistBlueprintTemplate<E extends BlueprintName<E>> extends DestroyableComponent permits HorizonsWishlistBlueprintTemplate, OdysseyWishlistBlueprintTemplate, HorizonsWishlistModuleBlueprintTemplate {
    Integer getSequenceID();

    boolean isVisibleBlueprint();

    WishlistBlueprint<E> getWishlistRecipe();

    void setVisibility(boolean b);

    boolean isDeleted();
}
