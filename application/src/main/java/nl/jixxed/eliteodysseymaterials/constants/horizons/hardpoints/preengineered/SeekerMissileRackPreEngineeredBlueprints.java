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
public class SeekerMissileRackPreEngineeredBlueprints {
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> PRE_ENGINEERED_BLUEPRINTS = Map.of(
            //https://forums.frontier.co.uk/threads/enhanced-missile-rack-initiative-mining.564538/
            //available at tech broker
            HorizonsBlueprintType.HIGH_CAPACITY_MAGAZINE_THERMAL_CASCADE,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.SEEKER_MISSILE_RACK, HorizonsBlueprintType.HIGH_CAPACITY_MAGAZINE_THERMAL_CASCADE, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-90%", true, percentageNegative(0.0, 0.9)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-60%", false, percentageNegative(0.0, 0.6)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-20%", true, percentageNegative(0.0, 0.2)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-40%", true, percentageNegative(0.0, 0.4)),
                                    HorizonsModifier.BURST_INTERVAL, new HorizonsNumberModifierValue("-10%", true, percentageNegative(0.0, 0.1)),
                                    HorizonsModifier.AMMO_CLIP_SIZE, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.0, 1.0)),
                                    HorizonsModifier.AMMO_MAXIMUM, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.0, 1.0))
                            ),
                            List.of(
                            ),
                            true
                    )
            ),
            //https://forums.frontier.co.uk/threads/imperial-campaign-targets-archon-delaine-in-beta-3-tucani-combat.638440
            HorizonsBlueprintType.HIGH_CAPACITY_MAGAZINE_STURDY_FSD_INTERRUPT,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.SEEKER_MISSILE_RACK, HorizonsBlueprintType.HIGH_CAPACITY_MAGAZINE_STURDY_FSD_INTERRUPT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                            ),
                            Map.ofEntries(
                                    Map.entry(HorizonsModifier.MASS, new HorizonsNumberModifierValue("+120%", true, percentagePositive(0.0, 1.2))),
                                    Map.entry(HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.0, 1.0))),
                                    Map.entry(HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.0, 0.3))),
                                    Map.entry(HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-10%", true, percentageNegative(0.0, 0.1))),
                                    Map.entry(HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("-5.5%", false, percentageNegative(0.0, 0.055))),
                                    Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.0, 0.1))),
                                    Map.entry(HorizonsModifier.ARMOUR_PIERCING, new HorizonsNumberModifierValue("+40%", true, percentagePositive(0.0, 0.4))),
                                    Map.entry(HorizonsModifier.BURST_INTERVAL, new HorizonsNumberModifierValue("+35%", false, percentagePositive(0.0, 0.35))),
                                    Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.0, 1.0))),
                                    Map.entry(HorizonsModifier.AMMO_MAXIMUM, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.0, 1.0))),
                                    Map.entry(HorizonsModifier.TARGET_FSD_REBOOTS, new HorizonsBooleanModifierValue(UTF8Constants.CHECK_TRUE, true, bool(Boolean.TRUE)))
                            ),
                            List.of(
                            ),
                            true
                    )
            )
    );
}
