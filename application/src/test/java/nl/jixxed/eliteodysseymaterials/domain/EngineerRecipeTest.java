package nl.jixxed.eliteodysseymaterials.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class EngineerRecipeTest {

    @Test
    void isCompleted() {
        final EngineerRecipe engineerRecipeTrue = new EngineerRecipe(List.of(), () -> true);
        Assertions.assertTrue(engineerRecipeTrue.isCompleted());
        final EngineerRecipe engineerRecipeFalse = new EngineerRecipe(List.of(), () -> false);
        Assertions.assertFalse(engineerRecipeFalse.isCompleted());
    }
}