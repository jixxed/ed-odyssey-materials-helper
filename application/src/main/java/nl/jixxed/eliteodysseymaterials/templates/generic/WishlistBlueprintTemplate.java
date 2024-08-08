package nl.jixxed.eliteodysseymaterials.templates.generic;

import nl.jixxed.eliteodysseymaterials.domain.Blueprint;
import nl.jixxed.eliteodysseymaterials.domain.WishlistBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.BlueprintCategory;
import nl.jixxed.eliteodysseymaterials.enums.BlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;

import java.util.List;
import java.util.Map;

public interface WishlistBlueprintTemplate<E extends BlueprintName<E>> {

    Map<Blueprint<E>, Double> getRecipe();

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

    String getWishlistUUID();

    void setEngineer(Engineer currentEngineerForRecipe);
}
