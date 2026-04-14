/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.constants.horizons.core_internals.preengineered;

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
public class PowerDistributorPreEngineeredBlueprints {
    //    https://forums.frontier.co.uk/threads/support-edmund-mahon-in-trade-war-trade.637814/
    //    https://forums.frontier.co.uk/threads/support-nakato-kaine-in-trade-war-trade.637815/
    //    Weapons: -25% capacity, -20% recharge
    //    Engines: +45% capacity, +40% recharge
    //    Systems: +45% capacity, +40% recharge
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> PRE_ENGINEERED_BLUEPRINTS = Map.of(
            HorizonsBlueprintType.SYSTEM_ENGINE_FOCUSED,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.POWER_DISTRIBUTOR, HorizonsBlueprintType.SYSTEM_ENGINE_FOCUSED, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                            ),
                            Map.of(
                                    HorizonsModifier.SYSTEMS_CAPACITY, new HorizonsNumberModifierValue("+45%", true, percentagePositive(0.0, 0.45)),
                                    HorizonsModifier.SYSTEMS_RECHARGE, new HorizonsNumberModifierValue("+40%", true, percentagePositive(0.0, 0.40)),
                                    HorizonsModifier.ENGINES_CAPACITY, new HorizonsNumberModifierValue("+45%", true, percentagePositive(0.0, 0.45)),
                                    HorizonsModifier.ENGINES_RECHARGE, new HorizonsNumberModifierValue("+40%", true, percentagePositive(0.0, 0.40)),
                                    HorizonsModifier.WEAPONS_CAPACITY, new HorizonsNumberModifierValue("-25%", false, percentageNegative(0.0, 0.25)),
                                    HorizonsModifier.WEAPONS_RECHARGE, new HorizonsNumberModifierValue("-20%", false, percentageNegative(0.0, 0.20))
                            ),
                            List.of(),
                            true
                    )
            )
    );
}
