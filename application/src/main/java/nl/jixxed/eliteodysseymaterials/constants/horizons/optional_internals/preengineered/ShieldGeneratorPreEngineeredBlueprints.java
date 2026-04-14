/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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

import static nl.jixxed.eliteodysseymaterials.helper.ModifierFunctionHelper.percentagePositive;
import static nl.jixxed.eliteodysseymaterials.helper.ModifierFunctionHelper.resistancePositive;

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
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("+30%", true, resistancePositive(0.0, 0.30)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("+35%", true, resistancePositive(0.0, 0.35))
                            ),
                            List.of(
                            ),
                            true
                    )
            )
    );
}
