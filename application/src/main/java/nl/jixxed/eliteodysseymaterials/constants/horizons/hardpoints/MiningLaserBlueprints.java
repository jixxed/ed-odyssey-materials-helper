package nl.jixxed.eliteodysseymaterials.constants.horizons.hardpoints;

import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModuleBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsNumberModifierValue;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.List;
import java.util.Map;

import static nl.jixxed.eliteodysseymaterials.helper.ModifierFunctionHelper.percentageNegative;
import static nl.jixxed.eliteodysseymaterials.helper.ModifierFunctionHelper.percentagePositive;

public class MiningLaserBlueprints {
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> BLUEPRINTS = Map.of(
            HorizonsBlueprintType.LONG_RANGE_WEAPON,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.MINING_LASER, HorizonsBlueprintType.LONG_RANGE_WEAPON, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-50%", true, percentageNegative(0.0, 0.5)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-50%", true, percentageNegative(0.0, 0.5)),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("+400%", true, percentagePositive(0.0, 4.0)),
                                    HorizonsModifier.DAMAGE_FALLOFF_START, new HorizonsNumberModifierValue("+733.3%", true, percentagePositive(0.0, 2200.0 / 300.0)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-49%", true, percentageNegative(0.0, 0.49))
                            ),
                            List.of(
                                    Engineer.SELENE_JEAN
                            )
                    )
            )
    );
}
