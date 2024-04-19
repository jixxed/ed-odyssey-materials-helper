package nl.jixxed.eliteodysseymaterials.constants.horizons.hardpoints;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.constants.UTF8Constants;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBooleanModifierValue;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModuleBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsNumberModifierValue;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.List;
import java.util.Map;

import static nl.jixxed.eliteodysseymaterials.helper.ModifierFunctionHelper.bool;
import static nl.jixxed.eliteodysseymaterials.helper.ModifierFunctionHelper.percentageNegative;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GuardianShardCannonBlueprints {
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> BLUEPRINTS = Map.of(
            HorizonsBlueprintType.ANTI_GUARDIAN_ZONE_RESISTANCE,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.GUARDIAN_SHARD_CANNON, HorizonsBlueprintType.ANTI_GUARDIAN_ZONE_RESISTANCE, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Manufactured.TG_ABRASION03,2,
                                    Manufactured.TG_CAUSTICCRYSTAL,1,
                                    Manufactured.UNKNOWNCORECHIP,1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("-20%", false, percentageNegative(0.20, 0.20)),
//                                    HorizonsModifier.DAMAGE_PER_SECOND, new HorizonsNumberModifierValue("-20%", false, percentageNegative(0.20, 0.20)),
                                    HorizonsModifier.ANTI_GUARDIAN_ZONE_RESISTANCE, new HorizonsBooleanModifierValue(UTF8Constants.CHECK_TRUE, true, bool(Boolean.TRUE))
                            ),
                            List.of(
                                    Engineer.RAM_TAH
                            ),
                            GameVersion.LIVE
                    )
            )
    );
}
