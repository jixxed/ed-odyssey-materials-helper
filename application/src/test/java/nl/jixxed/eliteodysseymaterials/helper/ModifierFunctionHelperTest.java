package nl.jixxed.eliteodysseymaterials.helper;

import nl.jixxed.eliteodysseymaterials.domain.ships.core_internals.Armour;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import org.apache.commons.math3.util.Precision;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier.*;

class ModifierFunctionHelperTest {

    @Test
    void percentageNegative() {
    }

    @Test
    void percentagePositive() throws Throwable {
        final Armour clone = Armour.ASP_ARMOUR_GRADE_1.Clone();
        clone.applyModification(HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_5, 1D);
        Assertions.assertEquals(-0.02,(Double)clone.getAttributeValue(KINETIC_RESISTANCE), Precision.EPSILON);
        Assertions.assertEquals( 0.15,(Double)clone.getAttributeValue(THERMAL_RESISTANCE), Precision.EPSILON);
        Assertions.assertEquals(-0.19,(Double)clone.getAttributeValue(EXPLOSIVE_RESISTANCE), Precision.EPSILON);

    }
}