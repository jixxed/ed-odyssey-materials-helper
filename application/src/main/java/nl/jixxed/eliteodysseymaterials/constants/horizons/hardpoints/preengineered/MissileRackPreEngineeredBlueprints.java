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
public class MissileRackPreEngineeredBlueprints {
    //https://forums.frontier.co.uk/threads/enhanced-missile-rack-initiative-mining.564538/
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> PRE_ENGINEERED_BLUEPRINTS = Map.of(
            HorizonsBlueprintType.HIGH_CAPACITY_MAGAZINE_THERMAL_CASCADE,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.MISSILE_RACK, HorizonsBlueprintType.HIGH_CAPACITY_MAGAZINE_THERMAL_CASCADE, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-90%", true, percentageNegative(0.5, 0.9)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-60%", false, percentageNegative(0.5, 0.6)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-20%", true, percentageNegative(0.16, 0.2)),
                                    HorizonsModifier.DAMAGE_PER_SECOND, new HorizonsNumberModifierValue("+11.1%", true, percentagePositive(0.0, 0.111)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-40%", true, percentageNegative(0.0, 0.4)),
                                    HorizonsModifier.RATE_OF_FIRE, new HorizonsNumberModifierValue("+11.1%", true, percentagePositive(0.0, 0.111)),
                                    HorizonsModifier.AMMO_CLIP_SIZE, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.0, 1.0)),
                                    HorizonsModifier.AMMO_MAXIMUM, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.0, 1.0))
                            ),
                            List.of(
                            ),
                            true
                    )
            )
    );

}
