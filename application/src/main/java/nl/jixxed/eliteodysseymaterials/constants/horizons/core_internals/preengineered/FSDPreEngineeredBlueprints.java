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
public class FSDPreEngineeredBlueprints {
    //https://forums.frontier.co.uk/threads/protect-the-alliances-expansion-into-the-coalsack-nebula-from-thargoid-attack-ax-combat.558263/
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> PRE_ENGINEERED_BLUEPRINTS = Map.of(
            HorizonsBlueprintType.INCREASED_FSD_RANGE_FASTER_FSD_BOOT_SEQUENCE,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAME_SHIFT_DRIVE, HorizonsBlueprintType.INCREASED_FSD_RANGE_FASTER_FSD_BOOT_SEQUENCE, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+30%", false, percentagePositive(0.12, 0.30)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-30%", false, percentageNegative(0.12, 0.30)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+15%", false, percentagePositive(0.12, 0.15)),
                                    HorizonsModifier.BOOT_TIME, new HorizonsNumberModifierValue("-80%", true, percentageNegative(0.65, 0.80)),
                                    HorizonsModifier.FSD_OPTIMISED_MASS, new HorizonsNumberModifierValue("+70%", true, percentagePositive(0.12, 0.70)),
                                    HorizonsModifier.FSD_HEAT_RATE, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.16, 0.20))
                            ),
                            List.of(
                            ),
                            true
                    )
            )
    );
}
