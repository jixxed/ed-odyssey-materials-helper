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
public class MiningLaserPreEngineeredBlueprints {
    //https://forums.frontier.co.uk/threads/support-torval-mastopolos-mining-campaign.588342/
    //also for sale now
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> PRE_ENGINEERED_BLUEPRINTS = Map.of(
            HorizonsBlueprintType.LONG_RANGE_WEAPON_INCENDIARY_ROUNDS,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.MINING_LASER, HorizonsBlueprintType.LONG_RANGE_WEAPON, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-50%", true, percentageNegative(0.0, 0.5)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-50%", true, percentageNegative(0.0, 0.5)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-50%", false, percentageNegative(0.0, 0.5)),
                                    HorizonsModifier.DAMAGE_PER_SECOND, new HorizonsNumberModifierValue("-5%", false, percentageNegative(0.0, 0.05)),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("+400%", true, percentagePositive(0.0, 4.0)),
                                    HorizonsModifier.DAMAGE_FALLOFF_START, new HorizonsNumberModifierValue("+733.3%", true, percentagePositive(0.0, 2200.0 / 300.0)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-49%", true, percentageNegative(0.0, 0.49))
                            ),
                            List.of(
                            ),
                            true
                    )
            )
    );
}
