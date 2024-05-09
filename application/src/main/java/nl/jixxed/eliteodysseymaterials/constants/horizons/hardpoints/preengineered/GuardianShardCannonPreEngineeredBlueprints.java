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

import static nl.jixxed.eliteodysseymaterials.helper.ModifierFunctionHelper.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GuardianShardCannonPreEngineeredBlueprints {
    //https://forums.frontier.co.uk/threads/azimuth-salvage-operation-requires-resources-trade.606107/page-3
    //https://www.reddit.com/r/EliteDangerous/comments/oc8kyy/in_case_you_missed_it_here_are_the_specs_of_the/
    //https://elite-dangerous.fandom.com/wiki/Guardian_Shard_Cannon
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> PRE_ENGINEERED_BLUEPRINTS = Map.of(
            HorizonsBlueprintType.LONG_RANGE_WEAPON_FOCUSED_WEAPON_PENETRATOR_MUNITIONS,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.GUARDIAN_SHARD_CANNON, HorizonsBlueprintType.LONG_RANGE_WEAPON_FOCUSED_WEAPON_PENETRATOR_MUNITIONS, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+50%", false, percentagePositive(0.0, 0.50)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+130%", false, percentagePositive(0.0, 1.30)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("+427.7%", false, percentagePositive(0.0, 4.277)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+1550%", false, percentagePositive(0.0, 15.50)),
                                    HorizonsModifier.ARMOUR_PIERCING, new HorizonsNumberModifierValue("+102%", true, percentagePositive(0.0, 1.02)),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("+76.5%", true, percentagePositive(0.0, 2999.998291 / 1700.0 - 1.0)),
                                    HorizonsModifier.SHOT_SPEED, new HorizonsNumberModifierValue("+455.8%", true, percentagePositive(0.0, 6299.208984 / 1133.333374 - 1.0)),
                                    HorizonsModifier.JITTER, new HorizonsNumberModifierValue("-3.5°", true, minus(3.5)),
                                    HorizonsModifier.DAMAGE_FALLOFF_START, new HorizonsNumberModifierValue("-11.8%", false, percentageNegative(0.0, 1499.999146 / 1700.0 - 1.0))
                            ),
                            List.of(
                            ),
                            true
                    )
            )
    );
}
