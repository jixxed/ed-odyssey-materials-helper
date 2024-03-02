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

import static nl.jixxed.eliteodysseymaterials.helper.ModifierFunctionHelper.percentageNegative;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RemoteReleaseFlakLauncherBlueprints {
    //halloween/x-mas? time based purchase
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> PRE_ENGINEERED_BLUEPRINTS = Map.of(
            HorizonsBlueprintType.DECORATIVE_GREEN,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.REMOTE_RELEASE_FLAK_LAUNCHER, HorizonsBlueprintType.DECORATIVE_GREEN, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("-99%", false, percentageNegative(0.0, 0.99)),
                                    HorizonsModifier.DAMAGE_PER_SECOND, new HorizonsNumberModifierValue("-99%", false, percentageNegative(0.0, 0.99))
                            ),
                            List.of(
                            ),
                            true
                    )
            ),
            HorizonsBlueprintType.DECORATIVE_RED,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.REMOTE_RELEASE_FLAK_LAUNCHER, HorizonsBlueprintType.DECORATIVE_RED, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("-99%", false, percentageNegative(0.0, 0.99)),
                                    HorizonsModifier.DAMAGE_PER_SECOND, new HorizonsNumberModifierValue("-99%", false, percentageNegative(0.0, 0.99))
                            ),
                            List.of(
                            ),
                            true
                    )
            ),
            HorizonsBlueprintType.DECORATIVE_YELLOW,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.REMOTE_RELEASE_FLAK_LAUNCHER, HorizonsBlueprintType.DECORATIVE_YELLOW, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("-99%", false, percentageNegative(0.0, 0.99)),
                                    HorizonsModifier.DAMAGE_PER_SECOND, new HorizonsNumberModifierValue("-99%", false, percentageNegative(0.0, 0.99))
                            ),
                            List.of(
                            ),
                            true
                    )
            )
    );
}
