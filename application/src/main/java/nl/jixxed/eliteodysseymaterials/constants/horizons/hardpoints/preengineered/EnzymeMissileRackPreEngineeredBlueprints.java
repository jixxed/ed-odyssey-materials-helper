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

import static nl.jixxed.eliteodysseymaterials.helper.ModifierFunctionHelper.percentagePositive;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EnzymeMissileRackPreEngineeredBlueprints {
    //https://forums.frontier.co.uk/threads/fight-for-the-neo-marlinist-order-of-mudhrid-against-the-empire-combat.569311/page-11
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> PRE_ENGINEERED_BLUEPRINTS = Map.of(
            HorizonsBlueprintType.HIGH_CAPACITY_MAGAZINE_INCREASED_DAMAGE,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.ENZYME_MISSILE_RACK, HorizonsBlueprintType.HIGH_CAPACITY_MAGAZINE_INCREASED_DAMAGE, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+60%", false, percentagePositive(0.0, 0.6)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.0, 0.2)),
                                    HorizonsModifier.DAMAGE_PER_SECOND, new HorizonsNumberModifierValue("+50%", true, percentagePositive(0.0, 0.5)),
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+50%", true, percentagePositive(0.0, 0.5)),
                                    HorizonsModifier.AMMO_CLIP_SIZE, new HorizonsNumberModifierValue("+75%", true, percentagePositive(0.0, 0.75)),
                                    HorizonsModifier.AMMO_MAXIMUM, new HorizonsNumberModifierValue("+25%", true, percentagePositive(0.0, 0.25))
                            ),
                            List.of(
                            ),
                            true
                    )
            )
    );
}
