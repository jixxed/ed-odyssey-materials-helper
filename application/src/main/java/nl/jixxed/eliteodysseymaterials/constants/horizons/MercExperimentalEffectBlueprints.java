/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.constants.horizons;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.constants.UTF8Constants;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.List;
import java.util.Map;

import static nl.jixxed.eliteodysseymaterials.helper.ModifierFunctionHelper.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("java:S1192")
public class MercExperimentalEffectBlueprints {

    public static final Map<HorizonsBlueprintType, HorizonsBlueprint> MINING_LASER = Map.of(
            HorizonsBlueprintType.INCENDIARY_ROUNDS, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.MULTI_CANNON, HorizonsBlueprintType.INCENDIARY_ROUNDS,
                    Map.of(
//                            Raw.SULPHUR, 5,
//                            Raw.PHOSPHORUS, 5,
//                            Manufactured.HEATCONDUCTIONWIRING, 5,
//                            Manufactured.PHASEALLOYS, 3
                    ),
                    Map.of(
                            HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+200%", false, percentagePositive(0.0, 2.0)),
                            HorizonsModifier.BURST_INTERVAL, new HorizonsNumberModifierValue("5.26%", false, percentagePositive(0.0, 0.05263157894736842)),
                            HorizonsModifier.DAMAGE_PARTIALLY_THERMAL, new HorizonsBooleanModifierValue(UTF8Constants.CHECK_TRUE, true, bool(Boolean.TRUE)),
                            HorizonsModifier.DAMAGE_RATIO, new HorizonsDamageRatioModifierValue(HorizonsModifier.KINETIC_DAMAGE_RATIO, HorizonsModifier.THERMAL_DAMAGE_RATIO, "90%", damageRatio(HorizonsModifier.KINETIC_DAMAGE_RATIO, HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.9)),
                            HorizonsModifier.KINETIC_DAMAGE_RATIO, new HorizonsNumberModifierValue("-90%", false, minus(0.9)),
                            HorizonsModifier.THERMAL_DAMAGE_RATIO, new HorizonsNumberModifierValue("+90%", true, plus(0.9))
                    ),
                    List.of(
                            Engineer.MARSHA_HICKS,
                            Engineer.TOD_THE_BLASTER_MCQUINN,
                            Engineer.ZACARIAH_NEMO
                    ),
                    true
            )
    );

    public static final Map<HorizonsBlueprintType, HorizonsBlueprint> MULTI_CANNON = Map.of(
            HorizonsBlueprintType.PHASING_SEQUENCE, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.MULTI_CANNON, HorizonsBlueprintType.PHASING_SEQUENCE,
                    Map.of(
//                            Raw.NIOBIUM, 3,
//                            Encoded.SHIELDPATTERNANALYSIS, 3,
//                            Manufactured.CONFIGURABLECOMPONENTS, 3,
//                            Manufactured.FOCUSCRYSTALS, 5
                    ),
                    Map.of(
                            HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.0, 0.1)),
                            HorizonsModifier.PART_OF_DAMAGE_THROUGH_SHIELDS, new HorizonsBooleanModifierValue(UTF8Constants.CHECK_TRUE, true, bool(Boolean.TRUE))
                    ),
                    List.of(
                            Engineer.MEL_BRANDON,
                            Engineer.THE_DWELLER,
                            Engineer.BROO_TARQUIN
                    ),
                    true
            )
    );

    public static final Map<HorizonsBlueprintType, HorizonsBlueprint> SEEKER_MISSILE_RACK = Map.of(
            HorizonsBlueprintType.FSD_INTERRUPT, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.DUMBFIRE_MISSILE_RACK, HorizonsBlueprintType.FSD_INTERRUPT,
                    Map.of(
//                            Encoded.FSDTELEMETRY, 5,
//                            Encoded.WAKESOLUTIONS, 3,
//                            Manufactured.MECHANICALEQUIPMENT, 5,
//                            Manufactured.CONFIGURABLECOMPONENTS, 3
                    ),
                    Map.of(
                            HorizonsModifier.TARGET_FSD_REBOOTS, new HorizonsBooleanModifierValue(UTF8Constants.CHECK_TRUE, true, bool(Boolean.TRUE)),
                            HorizonsModifier.BURST_INTERVAL, new HorizonsNumberModifierValue("50%", false, percentagePositive(0.0, 0.5)),
                            HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("-30%", false, percentageNegative(0.0, 0.3))
                    ),
                    List.of(
                            Engineer.LIZ_RYDER,
                            Engineer.PETRA_OLMANOVA,
                            Engineer.JURI_ISHMAAK
                    ),
                    true
            )
    );


}
