package nl.jixxed.eliteodysseymaterials.constants.horizons.core_internals.preengineered;

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

import static nl.jixxed.eliteodysseymaterials.helper.ModifierFunctionHelper.percentageNegative;
import static nl.jixxed.eliteodysseymaterials.helper.ModifierFunctionHelper.percentagePositive;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PowerPlantPreEngineeredBlueprints {
    //3a, 4a, 5a OVERCHARGED_OVERCHARGED
    //https://forums.frontier.co.uk/threads/colonia-bridge-project-phase-four-week-4-alcor-trade.599890/page-20
    //3a ARMOURED_OVERCHARGED
    //https://forums.frontier.co.uk/threads/medical-supplies-for-federal-starport-bombing-survivors-trade.567325/
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> PRE_ENGINEERED_BLUEPRINTS = Map.of(
            HorizonsBlueprintType.ARMOURED_OVERCHARGED,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.POWER_PLANT, HorizonsBlueprintType.ARMOURED_OVERCHARGED, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.0, 0.20)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+95%", true, percentagePositive(0.0, 0.95)),
                                    HorizonsModifier.POWER_CAPACITY, new HorizonsNumberModifierValue("+52%", true, percentagePositive(0.0, 0.52)),
                                    HorizonsModifier.HEAT_EFFICIENCY, new HorizonsNumberModifierValue("+13%", false, percentagePositive(0.0, 0.13))
                            ),
                            List.of(
                            ),
                            true
                    )
            ),
            HorizonsBlueprintType.OVERCHARGED_OVERCHARGED,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.POWER_PLANT, HorizonsBlueprintType.OVERCHARGED_OVERCHARGED, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-30%", false, percentageNegative(0.0, 0.30)),
                                    HorizonsModifier.POWER_CAPACITY, new HorizonsNumberModifierValue("+60%", true, percentagePositive(0.0, 0.60)),
                                    HorizonsModifier.HEAT_EFFICIENCY, new HorizonsNumberModifierValue("+35%", false, percentagePositive(0.0, 0.35))
                            ),
                            List.of(

                            ),
                            true
                    )
            )
    );
}
