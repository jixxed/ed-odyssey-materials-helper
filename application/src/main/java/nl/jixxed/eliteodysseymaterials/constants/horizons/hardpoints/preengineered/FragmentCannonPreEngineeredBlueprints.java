package nl.jixxed.eliteodysseymaterials.constants.horizons.hardpoints.preengineered;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.constants.UTF8Constants;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBooleanModifierValue;
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
public class FragmentCannonPreEngineeredBlueprints {
    //https://forums.frontier.co.uk/threads/join-aisling-duvals-call-to-arms-combat.638946/
    //https://forums.frontier.co.uk/threads/join-archers-exploitation-of-imperial-weakness-combat.638947/
    //https://forums.frontier.co.uk/threads/join-li-yong-ruis-territorial-opportunism-combat.638948/
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> PRE_ENGINEERED_BLUEPRINTS = Map.of(
            HorizonsBlueprintType.DOUBLE_SHOT_HIGH_CAPACITY_MAGAZINE_SCREENING_SHELL,
            Map.of(
//                    Burst rate of fire +1400%
//                    Burst size +2
//                    Reload time -50%
//                    Maximum range -10%
//                    Clip size +8
//                    Ammo capacity +68%
//                    Power draw +20%
//                    Mass +60%
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.DOUBLE_SHOT_HIGH_CAPACITY_MAGAZINE_SCREENING_SHELL, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                            ),
                            Map.ofEntries(
                                    Map.entry(HorizonsModifier.MASS, new HorizonsNumberModifierValue("+60%", false, percentagePositive(0.0, 0.6))),
                                    Map.entry(HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.0, 0.20))),
                                    Map.entry(HorizonsModifier.AMMO_MAXIMUM, new HorizonsNumberModifierValue("+68%", true, percentagePositive(0.0, 0.68))),
                                    Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, new HorizonsNumberModifierValue("+8", true, plus(8.0))),
                                    Map.entry(HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.0, 0.1))),
                                    Map.entry(HorizonsModifier.RELOAD_TIME, new HorizonsNumberModifierValue("-50%", true, percentageNegative(0.0, 0.5))),
                                    Map.entry(HorizonsModifier.BURST_SIZE, new HorizonsNumberModifierValue("+33%", true, plus(2.0))),
                                    Map.entry(HorizonsModifier.BURST_RATE_OF_FIRE, new HorizonsNumberModifierValue("+1400%", true, percentagePositive(0.0, 14.0))),
                                    Map.entry(HorizonsModifier.EFFECTIVENESS_INCREASE_AGAINST_MUNITIONS, new HorizonsBooleanModifierValue(UTF8Constants.CHECK_TRUE, true, bool(Boolean.TRUE)))

                            ),
                            List.of(
                            ),
                            true
                    )
            )
    );
}
