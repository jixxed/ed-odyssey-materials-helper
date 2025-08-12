package nl.jixxed.eliteodysseymaterials.constants.horizons;

import nl.jixxed.eliteodysseymaterials.domain.HorizonsModifierValue;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsNumberModifierValue;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Map;

import static nl.jixxed.eliteodysseymaterials.helper.ModifierFunctionHelper.percentageNegative;
import static nl.jixxed.eliteodysseymaterials.helper.ModifierFunctionHelper.percentagePositive;

public class BlueprintModifications {
    public static final Map<HorizonsModifier, HorizonsModifierValue> HIGH_CAPACITY_GRADE_1 = Map.of(
            HorizonsModifier.MASS, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.1, 0.2)),
            HorizonsModifier.BURST_INTERVAL, new HorizonsNumberModifierValue("-2%", true, percentageNegative(0.0, 0.02)),
            HorizonsModifier.AMMO_CLIP_SIZE, new HorizonsNumberModifierValue("+36%", true, percentagePositive(0.2, 0.36)),
            HorizonsModifier.AMMO_MAXIMUM, new HorizonsNumberModifierValue("+36%", true, percentagePositive(0.2, 0.36)),
            HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+4%", false, percentagePositive(0.0, 0.04))
    );
    public static final Map<HorizonsModifier, HorizonsModifierValue> HIGH_CAPACITY_GRADE_2 = Map.of(
            HorizonsModifier.MASS, new HorizonsNumberModifierValue("+30%", false, percentagePositive(0.2, 0.3)),
            HorizonsModifier.BURST_INTERVAL, new HorizonsNumberModifierValue("-4%", true, percentageNegative(0.02, 0.04)),
            HorizonsModifier.AMMO_CLIP_SIZE, new HorizonsNumberModifierValue("+51.67%", true, percentagePositive(0.36, 0.5167)),
            HorizonsModifier.AMMO_MAXIMUM, new HorizonsNumberModifierValue("+51.67%", true, percentagePositive(0.36, 0.5167)),
            HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+8%", false, percentagePositive(0.04, 0.08))
    );
    public static final Map<HorizonsModifier, HorizonsModifierValue> HIGH_CAPACITY_GRADE_3 = Map.of(
            HorizonsModifier.MASS, new HorizonsNumberModifierValue("+40%", false, percentagePositive(0.3, 0.4)),
            HorizonsModifier.BURST_INTERVAL, new HorizonsNumberModifierValue("-6%", true, percentageNegative(0.04, 0.06)),
            HorizonsModifier.AMMO_CLIP_SIZE, new HorizonsNumberModifierValue("+68%", true, percentagePositive(0.5167, 0.68)),
            HorizonsModifier.AMMO_MAXIMUM, new HorizonsNumberModifierValue("+68%", true, percentagePositive(0.5167, 0.68)),
            HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+12%", false, percentagePositive(0.08, 0.12))
    );
    public static final Map<HorizonsModifier, HorizonsModifierValue> HIGH_CAPACITY_GRADE_4 = Map.of(
            HorizonsModifier.MASS, new HorizonsNumberModifierValue("+50%", false, percentagePositive(0.4, 0.5)),
            HorizonsModifier.BURST_INTERVAL, new HorizonsNumberModifierValue("-8%", true, percentageNegative(0.06, 0.08)),
            HorizonsModifier.AMMO_CLIP_SIZE, new HorizonsNumberModifierValue("+83.67%", true, percentagePositive(0.68, 0.8367)),
            HorizonsModifier.AMMO_MAXIMUM, new HorizonsNumberModifierValue("+83.67%", true, percentagePositive(0.68, 0.8367)),
            HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+16%", false, percentagePositive(0.12, 0.16))
    );
    public static final Map<HorizonsModifier, HorizonsModifierValue> HIGH_CAPACITY_GRADE_5 = Map.of(
            HorizonsModifier.MASS, new HorizonsNumberModifierValue("+60%", false, percentagePositive(0.5, 0.6)),
            HorizonsModifier.BURST_INTERVAL, new HorizonsNumberModifierValue("-10%", true, percentageNegative(0.08, 0.1)),
            HorizonsModifier.AMMO_CLIP_SIZE, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.8367, 1.0)),
            HorizonsModifier.AMMO_MAXIMUM, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.8367, 1.0)),
            HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.16, 0.2))
    );
}
