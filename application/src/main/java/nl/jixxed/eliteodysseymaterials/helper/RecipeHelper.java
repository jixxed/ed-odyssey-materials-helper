package nl.jixxed.eliteodysseymaterials.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.constants.RecipeConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.EngineerRecipe;
import nl.jixxed.eliteodysseymaterials.domain.ModuleRecipe;
import nl.jixxed.eliteodysseymaterials.domain.UpgradeRecipe;
import nl.jixxed.eliteodysseymaterials.enums.RecipeName;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RecipeHelper {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    public static boolean isCraftableModuleOrUpgradeRecipe(final RecipeName recipeName) {
        return (RecipeConstants.getRecipe(recipeName) instanceof ModuleRecipe || RecipeConstants.getRecipe(recipeName) instanceof UpgradeRecipe) && APPLICATION_STATE.amountCraftable(recipeName) > 0;
    }

    public static boolean isCompletedEngineerRecipe(final RecipeName recipeName) {
        return RecipeConstants.getRecipe(recipeName) instanceof EngineerRecipe && ((EngineerRecipe) RecipeConstants.getRecipe(recipeName)).isCompleted();
    }
}
