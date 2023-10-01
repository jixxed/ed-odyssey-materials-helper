package nl.jixxed.eliteodysseymaterials.constants.horizons.hardpoints;

import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModuleBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsNumberModifierValue;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.List;
import java.util.Map;

import static nl.jixxed.eliteodysseymaterials.helper.ModifierFunctionHelper.percentagePositive;

public class GuardianPlasmaChargerBlueprints {
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> BLUEPRINTS = Map.of(
            HorizonsBlueprintType.OVERCHARGED_WEAPON,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.GUARDIAN_PLASMA_CHARGER, HorizonsBlueprintType.OVERCHARGED, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+70%", true, percentagePositive(0.60, 0.70)),//todo
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("+35%", false, percentagePositive(0.30, 0.35)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+15%", false, percentagePositive(0.12, 0.15))
                            ),
                            List.of(
                                    Engineer.SELENE_JEAN
                            )
                    )
            ),
            HorizonsBlueprintType.FOCUSED_WEAPON,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.GUARDIAN_PLASMA_CHARGER, HorizonsBlueprintType.FOCUSED_WEAPON, HorizonsBlueprintGrade.GRADE_5,
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
