/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.helper;

import nl.jixxed.eliteodysseymaterials.domain.ships.core_internals.Armour;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import org.apache.commons.math3.util.Precision;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier.*;

class ModifierFunctionHelperTest {

    @Test
    void percentageNegative() {
    }

    @Test
    void percentagePositive() throws Throwable {
        final Armour clone = Armour.ASP_ARMOUR_GRADE_1.Clone();
        clone.applyModification(HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_5, BigDecimal.ONE);
        Assertions.assertEquals(-0.02,(Double)clone.getAttributeValue(KINETIC_RESISTANCE, false), Precision.EPSILON);
        Assertions.assertEquals( 0.15,(Double)clone.getAttributeValue(THERMAL_RESISTANCE, false), Precision.EPSILON);
        Assertions.assertEquals(-0.19,(Double)clone.getAttributeValue(EXPLOSIVE_RESISTANCE, false), Precision.EPSILON);

    }
}