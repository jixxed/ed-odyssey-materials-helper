package nl.jixxed.eliteodysseymaterials.domain;

import nl.jixxed.eliteodysseymaterials.enums.OdysseyBlueprintName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class EngineerBlueprintTest {

    @Test
    void isCompleted() {
        final EngineerBlueprint engineerRecipeTrue = new EngineerBlueprint(OdysseyBlueprintName.ENGINEER_B1, List.of(), () -> true);
        Assertions.assertTrue(engineerRecipeTrue.isCompleted());
        final EngineerBlueprint engineerRecipeFalse = new EngineerBlueprint(OdysseyBlueprintName.ENGINEER_B1, List.of(), () -> false);
        Assertions.assertFalse(engineerRecipeFalse.isCompleted());
    }
}