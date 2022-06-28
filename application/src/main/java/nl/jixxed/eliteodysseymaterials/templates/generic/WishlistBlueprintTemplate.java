package nl.jixxed.eliteodysseymaterials.templates.generic;

import nl.jixxed.eliteodysseymaterials.domain.Blueprint;
import nl.jixxed.eliteodysseymaterials.domain.WishlistBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.BlueprintCategory;
import nl.jixxed.eliteodysseymaterials.enums.BlueprintName;

import java.util.List;

public interface WishlistBlueprintTemplate<E extends BlueprintName<E>> {

    List<Blueprint<E>> getRecipe();

    Blueprint<E> getPrimaryRecipe();

    BlueprintName<E> getRecipeName();

    BlueprintCategory getRecipeCategory();

    Integer getSequenceID();

    boolean isVisibleBlueprint();

    WishlistBlueprint<E> getWishlistRecipe();

    public void remove();

    void onDestroy();

    void setVisibility(boolean b);


    void addWishlistIngredients(final List<Ingredient> wishlistIngredients);
}
