package nl.jixxed.eliteodysseymaterials.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.EngineerBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.ModuleBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.UpgradeBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.Craftability;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyBlueprintName;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BlueprintHelper {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    public static Craftability getCraftabilityForModuleOrUpgradeRecipe(final OdysseyBlueprintName odysseyBlueprintName) {
        if (!(OdysseyBlueprintConstants.getRecipe(odysseyBlueprintName) instanceof ModuleBlueprint) && !(OdysseyBlueprintConstants.getRecipe(odysseyBlueprintName) instanceof UpgradeBlueprint)) {
            return Craftability.NOT_CRAFTABLE;
        }
        return OdysseyBlueprintConstants.getCraftability(odysseyBlueprintName);
    }

    public static boolean isCompletedEngineerRecipe(final OdysseyBlueprintName odysseyBlueprintName) {
        return OdysseyBlueprintConstants.getRecipe(odysseyBlueprintName) instanceof EngineerBlueprint engineerRecipe && engineerRecipe.isCompleted();
    }

    public static boolean isCompletedEngineerRecipe(final HorizonsBlueprintName horizonsBlueprintName) {
        return HorizonsBlueprintConstants.getEngineerRecipe(horizonsBlueprintName).isCompleted();
    }
}
