package nl.jixxed.eliteodysseymaterials.constants.horizons.utilitymounts.preengineered;

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
@SuppressWarnings("java:S1192")
public class KillWarrantScannerPreEngineeredBlueprints {
    // KWS from Hudson data CG
    // https://forums.frontier.co.uk/threads/support-president-hudson-to-increase-federal-surveillance-data.573553/page-24
    // https://forums.frontier.co.uk/threads/defeat-pirates-in-planetary-rings-of-dhan-bounty-hunting.636453/
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> PRE_ENGINEERED_BLUEPRINTS = Map.of(
            HorizonsBlueprintType.FAST_SCANNER_LONG_RANGE_SCANNER,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.FAST_SCANNER_LONG_RANGE_SCANNER, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_TIME, new HorizonsNumberModifierValue("-80%", true, percentageNegative(0.0, 0.8)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-50%", false, percentageNegative(0.0, 0.5)),
                                    HorizonsModifier.SCANNER_RANGE, new HorizonsNumberModifierValue("+65%", true, percentagePositive(0.0, 0.65)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+50%", false, percentagePositive(0.0, 0.5)),
                                    HorizonsModifier.MAX_ANGLE, new HorizonsNumberModifierValue("-30%", false, percentageNegative(0.0, 0.3))
                            ),
                            List.of(
                            ),
                            true
                    )
            )
    );
}
