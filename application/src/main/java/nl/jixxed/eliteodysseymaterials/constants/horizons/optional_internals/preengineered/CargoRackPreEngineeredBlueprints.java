package nl.jixxed.eliteodysseymaterials.constants.horizons.optional_internals.preengineered;

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

public class CargoRackPreEngineeredBlueprints {
    //https://forums.frontier.co.uk/threads/brewer-corporation-calls-for-trailblazer-resupply.639562/
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> PRE_ENGINEERED_BLUEPRINTS = Map.of(
            HorizonsBlueprintType.INCREASED_CARGO_CAPACITY,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.CARGO_RACK, HorizonsBlueprintType.INCREASED_CARGO_CAPACITY, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                            ),
                            Map.of(
                                    HorizonsModifier.CARGO_CAPACITY, new HorizonsNumberModifierValue("+35%", true, percentagePositive(0.0, 0.34375))
                            ),
                            List.of(
                            ),
                            true
                    )
            )
    );
}
