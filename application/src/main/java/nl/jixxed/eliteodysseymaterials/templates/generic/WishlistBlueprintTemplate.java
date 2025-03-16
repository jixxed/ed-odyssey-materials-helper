package nl.jixxed.eliteodysseymaterials.templates.generic;

import nl.jixxed.eliteodysseymaterials.domain.Blueprint;
import nl.jixxed.eliteodysseymaterials.domain.WishlistBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.BlueprintCategory;
import nl.jixxed.eliteodysseymaterials.enums.BlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableComponent;

import java.util.List;
import java.util.Map;

public interface WishlistBlueprintTemplate<E extends BlueprintName<E>> extends DestroyableComponent {

    Map<Blueprint<E>, Double> getRecipe();

    Blueprint<E> getPrimaryRecipe();

    BlueprintName<E> getRecipeName();

    BlueprintCategory getRecipeCategory();

    Integer getSequenceID();

    boolean isVisibleBlueprint();

    WishlistBlueprint<E> getWishlistRecipe();

    void setVisibility(boolean b);


    void addWishlistIngredients(final List<Ingredient> wishlistIngredients);

    String getWishlistUUID();

    void setEngineer(Engineer currentEngineerForRecipe);
}
