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
public class RailGunPreEngineeredBlueprints {
    //https://forums.frontier.co.uk/threads/fight-for-the-epsilon-fornacis-empire-group-against-the-neo-marlinists-combat.569308/page-12
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> PRE_ENGINEERED_BLUEPRINTS = Map.of(
            HorizonsBlueprintType.LONG_RANGE_WEAPON_HIGH_CAPACITY_MAGAZINE_FEEDBACK_CASCADE,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.RAIL_GUN, HorizonsBlueprintType.LONG_RANGE_WEAPON_HIGH_CAPACITY_MAGAZINE_FEEDBACK_CASCADE, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                            ),
                            Map.ofEntries(
                                   Map.entry(HorizonsModifier.MASS, new HorizonsNumberModifierValue("+90%", false, percentagePositive(0.0, 0.9))),
                                   Map.entry(HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+65%", false, percentagePositive(0.0, 0.65))),
                                   Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, new HorizonsNumberModifierValue("+6.7%", true, percentagePositive(0.0, 53.371883 / 50.036144 - 1))),
                                   Map.entry(HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("-20%", false, percentageNegative(0.0, 0.2))),
                                   Map.entry(HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-40%", true, percentageNegative(0.0, 0.4))),
                                   Map.entry(HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.0, 1.0))),
                                   Map.entry(HorizonsModifier.RATE_OF_FIRE, new HorizonsNumberModifierValue("+33%", true, percentagePositive(0.0, 1.606426 / 1.204819 -1))),
                                   Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.0, 1.0))),
                                   Map.entry(HorizonsModifier.AMMO_MAXIMUM, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.0, 1.0))),
                                   Map.entry(HorizonsModifier.RELOAD_TIME, new HorizonsNumberModifierValue("+25%", false, percentagePositive(0.0, 0.25))),
                                   Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START, new HorizonsNumberModifierValue("+500%", true, percentagePositive(0.0, 5.0)))
                            ),
                            List.of(
                            ),
                            true
                    )
            )
    );
}
