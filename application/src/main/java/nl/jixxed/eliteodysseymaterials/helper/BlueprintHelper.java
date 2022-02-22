package nl.jixxed.eliteodysseymaterials.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.constants.BlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.EngineerBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.ModuleBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.UpgradeBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.BlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.Craftability;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BlueprintHelper {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    public static Craftability getCraftabilityForModuleOrUpgradeRecipe(final BlueprintName blueprintName) {
        if (!(BlueprintConstants.getRecipe(blueprintName) instanceof ModuleBlueprint) && !(BlueprintConstants.getRecipe(blueprintName) instanceof UpgradeBlueprint)) {
            return Craftability.NOT_CRAFTABLE;
        }
        return APPLICATION_STATE.getCraftability(blueprintName);
    }

    public static boolean isCompletedEngineerRecipe(final BlueprintName blueprintName) {
        return BlueprintConstants.getRecipe(blueprintName) instanceof EngineerBlueprint engineerRecipe && engineerRecipe.isCompleted();
    }
}
