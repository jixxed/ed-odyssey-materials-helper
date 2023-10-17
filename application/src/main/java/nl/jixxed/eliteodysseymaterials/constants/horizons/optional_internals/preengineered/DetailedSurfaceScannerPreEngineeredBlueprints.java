package nl.jixxed.eliteodysseymaterials.constants.horizons.optional_internals.preengineered;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModuleBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsNumberModifierValue;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.List;
import java.util.Map;

import static nl.jixxed.eliteodysseymaterials.helper.ModifierFunctionHelper.percentagePositive;
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DetailedSurfaceScannerPreEngineeredBlueprints {
    //https://forums.frontier.co.uk/threads/engineered-detailed-surface-scanner-initiative-trade.582512/
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> PRE_ENGINEERED_BLUEPRINTS = Map.of(
            HorizonsBlueprintType.EXPANDED_PROBE_SCANNING_RADIUS_X2,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.DETAILED_SURFACE_SCANNER, HorizonsBlueprintType.EXPANDED_PROBE_SCANNING_RADIUS_X2, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                            ),
                            Map.of(
                                    HorizonsModifier.DSS_PATCH_RADIUS, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.0, 1.0))
                            ),
                            List.of(
                            ),
                            true
                    )
            )
    );

}
