package nl.jixxed.eliteodysseymaterials.constants.horizons.hardpoints;

import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModuleBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsNumberModifierValue;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.List;
import java.util.Map;

import static nl.jixxed.eliteodysseymaterials.helper.ModifierFunctionHelper.percentagePositive;

public class GuardianShardCannonBlueprints {
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> BLUEPRINTS = Map.of(
            HorizonsBlueprintType.LONG_RANGE_WEAPON,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.GUARDIAN_SHARD_CANNON, HorizonsBlueprintType.LONG_RANGE_WEAPON, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+15%", false, percentagePositive(0.12, 0.15)),//todo
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.80, 1.0)),
                                    HorizonsModifier.DAMAGE_FALLOFF_START, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.80, 1.0)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+30%", false, percentagePositive(0.25, 0.30))
                            ),
                            List.of(
                                    Engineer.SELENE_JEAN
                            )
                    )
            ),
            HorizonsBlueprintType.FOCUSED_WEAPON,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.GUARDIAN_SHARD_CANNON, HorizonsBlueprintType.FOCUSED_WEAPON, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                            ),
                            Map.of(
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.84, 1.00)),//todo
                                    HorizonsModifier.ARMOUR_PIERCING, new HorizonsNumberModifierValue("+120%", true, percentagePositive(1.00, 1.20)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+5%", false, percentagePositive(0.04, 0.05))
                            ),
                            List.of(
                                    Engineer.SELENE_JEAN
                            )
                    )
            )
    );
}
