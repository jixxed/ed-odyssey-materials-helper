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
public class GuardianGaussCannonPreEngineeredBlueprints {
    //https://forums.frontier.co.uk/threads/defend-the-proteus-wave-project-week-2-ax-combat.605927/page-5
    //https://elite-dangerous.fandom.com/wiki/Guardian_Gauss_Cannon
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> PRE_ENGINEERED_BLUEPRINTS = Map.of(
            HorizonsBlueprintType.HIGH_CAPACITY_MAGAZINE_RAPID_FIRE_MODIFICATION,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.GUARDIAN_GAUSS_CANNON, HorizonsBlueprintType.HIGH_CAPACITY_MAGAZINE, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE_PER_SECOND, new HorizonsNumberModifierValue("+0.0%", true, percentagePositive(0.0, 0.0)),
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("-75.2%", false, percentageNegative(0.0, 0.752)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-75%", true, percentageNegative(0.0, 0.75)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-75%", true, percentageNegative(0.0, 0.75)),
                                    HorizonsModifier.RATE_OF_FIRE, new HorizonsNumberModifierValue("+303.5%", true, percentagePositive(0.0, 3.035)),
                                    HorizonsModifier.BURST_RATE_OF_FIRE, new HorizonsNumberModifierValue("+1100%", true, percentagePositive(0.0, 11.0)),
                                    HorizonsModifier.BURST_SIZE, new HorizonsNumberModifierValue("+3", true, plus(3.0)),
                                    HorizonsModifier.AMMO_CLIP_SIZE, new HorizonsNumberModifierValue("+300%", true, percentagePositive(0.0, 3.0)),
                                    HorizonsModifier.AMMO_MAXIMUM, new HorizonsNumberModifierValue("+400%", true, percentagePositive(0.0, 4.0)),
                                    HorizonsModifier.DAMAGE_FALLOFF_START, new HorizonsNumberModifierValue("-20%", false, percentageNegative(0.0, 0.20))
                            ),
                            List.of(
                            ),
                            true
                    )
            )
    );
}
