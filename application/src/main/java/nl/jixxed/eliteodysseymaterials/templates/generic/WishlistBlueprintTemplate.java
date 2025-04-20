package nl.jixxed.eliteodysseymaterials.templates.generic;

import nl.jixxed.eliteodysseymaterials.domain.Blueprint;
import nl.jixxed.eliteodysseymaterials.domain.WishlistBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.BlueprintCategory;
import nl.jixxed.eliteodysseymaterials.enums.BlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableComponent;
import nl.jixxed.eliteodysseymaterials.templates.horizons.wishlist.HorizonsWishlistBlueprintTemplate;
import nl.jixxed.eliteodysseymaterials.templates.horizons.wishlist.HorizonsWishlistModuleBlueprintTemplate;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.wishlist.OdysseyWishlistBlueprintTemplate;

import java.util.Map;

public sealed interface WishlistBlueprintTemplate<E extends BlueprintName<E>> extends DestroyableComponent permits HorizonsWishlistBlueprintTemplate, OdysseyWishlistBlueprintTemplate, HorizonsWishlistModuleBlueprintTemplate {

    Map<Blueprint<E>, Double> getRecipe();

    Blueprint<E> getPrimaryRecipe();

    BlueprintName<E> getRecipeName();

    BlueprintCategory getRecipeCategory();

    Integer getSequenceID();

    boolean isVisibleBlueprint();

    WishlistBlueprint<E> getWishlistRecipe();

    void setVisibility(boolean b);


//    void addWishlistIngredients(final List<Ingredient> wishlistIngredients);

    String getWishlistUUID();

    void setEngineer(Engineer currentEngineerForRecipe);
}
