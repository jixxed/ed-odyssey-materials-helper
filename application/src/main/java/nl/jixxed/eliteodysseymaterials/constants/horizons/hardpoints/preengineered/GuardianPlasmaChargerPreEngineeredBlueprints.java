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
import static nl.jixxed.eliteodysseymaterials.helper.ModifierFunctionHelper.plus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GuardianPlasmaChargerPreEngineeredBlueprints {
    //https://forums.frontier.co.uk/threads/supply-salvations-anti-xeno-superweapon-in-maia-rares.595102/
    //https://forums.frontier.co.uk/threads/protect-the-proteus-wave-superweapon.606371/page-3
    //https://forums.frontier.co.uk/threads/praise-salvation-guardian-plasma-charger-is-now-engineered.592247/
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> PRE_ENGINEERED_BLUEPRINTS = Map.of(
            HorizonsBlueprintType.OVERCHARGED_WEAPON_FOCUSED_WEAPON,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.GUARDIAN_PLASMA_CHARGER, HorizonsBlueprintType.OVERCHARGED_WEAPON_FOCUSED_WEAPON, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+32.4%", false, percentagePositive(0.0, 0.324)),
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+66%", true, percentagePositive(0.0, 0.66)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+50%", false, percentagePositive(0.0, 0.5)),
                                    HorizonsModifier.ARMOUR_PIERCING, new HorizonsNumberModifierValue("+37.5%", true, percentagePositive(0.0, 0.375)),
                                    HorizonsModifier.SHOT_SPEED, new HorizonsNumberModifierValue("+400%", true, percentagePositive(0.0, 4.00)),
                                    HorizonsModifier.AMMO_CLIP_SIZE, new HorizonsNumberModifierValue("+33.3%", true, plus(5.0))
                            ),
                            List.of(
                            ),
                            true
                    )
            )
    );
}
