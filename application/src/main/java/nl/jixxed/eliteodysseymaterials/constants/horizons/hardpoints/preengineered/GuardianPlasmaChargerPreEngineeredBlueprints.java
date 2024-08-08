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
                                    HorizonsModifier.AMMO_CLIP_SIZE, new HorizonsNumberModifierValue("+33.3%", true, percentagePositive(0.0, 0.333))
                            ),
                            List.of(
                            ),
                            true
                    )
            )
    );
}
