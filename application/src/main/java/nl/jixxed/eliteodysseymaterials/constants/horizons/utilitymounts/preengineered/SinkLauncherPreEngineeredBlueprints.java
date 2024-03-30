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

import static nl.jixxed.eliteodysseymaterials.helper.ModifierFunctionHelper.percentagePositive;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("java:S1192")
public class SinkLauncherPreEngineeredBlueprints {
    //https://forums.frontier.co.uk/threads/operation-tyndareus-reacts-to-thargoid-fleet-ax-combat.609176/
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> PRE_ENGINEERED_BLUEPRINTS = Map.of(
            HorizonsBlueprintType.AMMO_CAPACITY_X2,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.HEAT_SINK_LAUNCHER, HorizonsBlueprintType.AMMO_CAPACITY_X2, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                            ),
                            Map.of(
                                    HorizonsModifier.AMMO_MAXIMUM, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.0, 1.0)),
                                    HorizonsModifier.RELOAD_TIME, new HorizonsNumberModifierValue("+75%", false, percentagePositive(0.0, 0.75))
                            ),
                            List.of(
                            ),
                            true
                    )
            )
    );

}
