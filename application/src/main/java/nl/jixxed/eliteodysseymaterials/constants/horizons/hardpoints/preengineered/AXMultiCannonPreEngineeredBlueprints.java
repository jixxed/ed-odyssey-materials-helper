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

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AXMultiCannonPreEngineeredBlueprints {
//    https://forums.frontier.co.uk/threads/maintain-security-in-xi-wangda-bounty-hunting.618372/page-6
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> PRE_ENGINEERED_BLUEPRINTS = Map.of(
            HorizonsBlueprintType.OVERCHARGED_WEAPON_AUTO_LOADER,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.AX_MULTI_CANNON, HorizonsBlueprintType.OVERCHARGED_WEAPON_AUTO_LOADER, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+75%", false, percentagePositive(0.0, 0.75)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+300%", false, percentagePositive(0.0, 3.0)),
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+10%", true, percentagePositive(0.0, 0.1))
                            ),
                            List.of(
                            ),
                            true
                    )
            )
    );
}
