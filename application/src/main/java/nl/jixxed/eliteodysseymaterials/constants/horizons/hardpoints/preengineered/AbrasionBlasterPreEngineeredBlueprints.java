package nl.jixxed.eliteodysseymaterials.constants.horizons.hardpoints.preengineered;

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
public class AbrasionBlasterPreEngineeredBlueprints {
    //Long Range 1D Abrasion Blaster - only top 10% received module
    //https://forums.frontier.co.uk/threads/ongoing-community-goal-unique-mining-module-stats.588435/
    //https://forums.frontier.co.uk/threads/support-wreaken-construction-mining-campaign.588343/
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> PRE_ENGINEERED_BLUEPRINTS = Map.of(
            HorizonsBlueprintType.LONG_RANGE_WEAPON_INCENDIARY_ROUNDS,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.ABRASION_BLASTER, HorizonsBlueprintType.LONG_RANGE_WEAPON_INCENDIARY_ROUNDS, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-50%", true, percentageNegative(0.0, 0.5)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-50%", true, percentageNegative(0.0, 0.5)),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("+400%", true, percentagePositive(0.0, 4.0)),
                                    HorizonsModifier.DAMAGE_FALLOFF_START, new HorizonsNumberModifierValue("+400%", true, percentagePositive(0.0, 4.0)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-50%", false, percentageNegative(0.0, 0.5)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-50%", true, percentageNegative(0.0, 0.5)),
                                    HorizonsModifier.SHOT_SPEED, new HorizonsNumberModifierValue("+400%", false, percentagePositive(0.0, 4.0))
                            ),
                            List.of(
                            ),
                            true
                    )
            )
    );
}
