package nl.jixxed.eliteodysseymaterials.constants.horizons.hardpoints;

import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModuleBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsNumberModifierValue;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.List;
import java.util.Map;

import static nl.jixxed.eliteodysseymaterials.helper.ModifierFunctionHelper.*;

public class GuardianGaussCannonBlueprints {
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> BLUEPRINTS = Map.of(
            HorizonsBlueprintType.HIGH_CAPACITY_MAGAZINE,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.GUARDIAN_GAUSS_CANNON, HorizonsBlueprintType.HIGH_CAPACITY_MAGAZINE, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                            ),
                            Map.of(
                                    HorizonsModifier.RATE_OF_FIRE, new HorizonsNumberModifierValue("+10%", true, percentagePositive(0.08, 0.1)),//todo
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+60%", false, percentagePositive(0.5, 0.6)),
                                    HorizonsModifier.AMMO_CLIP_SIZE, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.84, 1.0)),
                                    HorizonsModifier.AMMO_MAXIMUM, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.84, 1.0)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.16, 0.2))
                            ),
                            List.of(
                                    Engineer.SELENE_JEAN
                            )
                    )
            ),
            HorizonsBlueprintType.RAPID_FIRE_MODIFICATION,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.GUARDIAN_GAUSS_CANNON, HorizonsBlueprintType.RAPID_FIRE_MODIFICATION, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("-5%", false, percentageNegative(0.04, 0.05)),//todo
                                    HorizonsModifier.RELOAD_TIME, new HorizonsNumberModifierValue("-65%", true, percentageNegative(0.55, 0.65)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-35%", true, percentageNegative(0.25, 0.35)),
                                    HorizonsModifier.JITTER, new HorizonsNumberModifierValue("0.5", false, plus(0.5)),
                                    HorizonsModifier.RATE_OF_FIRE, new HorizonsNumberModifierValue("+44%", true, percentagePositive(0.35, 0.44))
                            ),
                            List.of(
                                    Engineer.SELENE_JEAN
                            )
                    )
            )
    );
}
