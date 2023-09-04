package nl.jixxed.eliteodysseymaterials.constants.horizons;

import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModuleBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsNumberModifierValue;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.List;
import java.util.Map;

import static nl.jixxed.eliteodysseymaterials.helper.ModifierFunctionHelper.percentagePositive;

public class SpecialBlueprints {
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> DETAILED_SURFACE_SCANNER_1_I_V1_PRE = Map.of(
            HorizonsBlueprintType.EXPANDED_PROBE_SCANNING_RADIUS_X2,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.DETAILED_SURFACE_SCANNER, HorizonsBlueprintType.EXPANDED_PROBE_SCANNING_RADIUS_X2, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                            ),
                            Map.of(
                                    HorizonsModifier.PROBE_RADIUS, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.0, 1.0))
                            ),
                            List.of(
                                    Engineer.LEI_CHEUNG
                            )
                    )
            )
    );

}
