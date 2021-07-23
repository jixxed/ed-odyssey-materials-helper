package nl.jixxed.eliteodysseymaterials.domain;

import nl.jixxed.eliteodysseymaterials.enums.RecipeName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class EngineerRecipeTest {

    @Test
    void isCompleted() {
        final EngineerRecipe engineerRecipeTrue = new EngineerRecipe(RecipeName.ENGINEER_B1, List.of(), () -> true);
        Assertions.assertTrue(engineerRecipeTrue.isCompleted());
        final EngineerRecipe engineerRecipeFalse = new EngineerRecipe(RecipeName.ENGINEER_B1, List.of(), () -> false);
        Assertions.assertFalse(engineerRecipeFalse.isCompleted());
    }
}