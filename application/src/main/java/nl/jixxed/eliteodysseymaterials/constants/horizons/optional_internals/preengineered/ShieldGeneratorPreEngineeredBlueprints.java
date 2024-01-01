package nl.jixxed.eliteodysseymaterials.constants.horizons.optional_internals.preengineered;

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
public class ShieldGeneratorPreEngineeredBlueprints {
    //https://forums.frontier.co.uk/threads/medical-supplies-for-imperial-starport-bombing-survivors-trade.567324/page-6#post-9022938
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> PRE_ENGINEERED_BLUEPRINTS = Map.of(
            HorizonsBlueprintType.THERMAL_RESISTANT_SHIELDS_KINETIC_RESISTANT_SHIELDS,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.THERMAL_RESISTANT_SHIELDS_KINETIC_RESISTANT_SHIELDS, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+20%", true, percentagePositive(0.0, 0.2)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+40%", false, percentagePositive(0.0, 0.4)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("+18%", true, resistancePositive(0.0, 0.18)),//todo flat increase - doublecheck
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("+42%", true, resistanceNegative(0.0, 0.42))//todo flat increase - doublecheck
                            ),
                            List.of(
                            ),
                            true
                    )
            )
    );
}
