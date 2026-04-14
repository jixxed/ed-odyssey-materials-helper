/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.constants.horizons.hardpoints.preengineered;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.constants.UTF8Constants;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBooleanModifierValue;
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
public class MultiCannonPreEngineeredBlueprints {
    //https://forums.frontier.co.uk/threads/disrupt-azimuths-resource-campaign-combat.610080/page-6
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> PRE_ENGINEERED_BLUEPRINTS = Map.of(
            HorizonsBlueprintType.RAPID_FIRE_MODIFICATION_PHASING_SEQUENCE,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.RAIL_GUN, HorizonsBlueprintType.RAPID_FIRE_MODIFICATION_PHASING_SEQUENCE, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.0, 0.1)),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("-50%", false, percentageNegative(0.0, 0.5)),
                                    HorizonsModifier.BURST_INTERVAL, new HorizonsNumberModifierValue("-28.57%", true, percentageNegative(0.0, 0.2857)),
                                    HorizonsModifier.AMMO_CLIP_SIZE, new HorizonsNumberModifierValue("+300%", true, percentagePositive(0.0, 3.0)),
                                    HorizonsModifier.AMMO_MAXIMUM, new HorizonsNumberModifierValue("+4300", true, plus(4300.0)),
                                    HorizonsModifier.ROUNDS_PER_SHOT, new HorizonsNumberModifierValue("+200%", true, percentagePositive(0.0, 2.0)),
                                    HorizonsModifier.JITTER, new HorizonsNumberModifierValue("3", false, plus(3.0)),
                                    HorizonsModifier.DAMAGE_FALLOFF_START, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.0, 0.1)),
                                    HorizonsModifier.PART_OF_DAMAGE_THROUGH_SHIELDS, new HorizonsBooleanModifierValue(UTF8Constants.CHECK_TRUE, true, bool(Boolean.TRUE))
                            ),
                            List.of(
                            ),
                            true
                    )
            )
    );
}
