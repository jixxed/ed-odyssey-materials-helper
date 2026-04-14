/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.enums;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HorizonsBlueprintGradeTest {

    //MethodSource provideDataForGetNumberOfRolls
    private static Stream<Arguments> provideDataForGetNumberOfRolls(){
        return Stream.of(
                Arguments.of(HorizonsBlueprintGrade.GRADE_1, 0, HorizonsBlueprintType.AMMO_CAPACITY, 5),
                Arguments.of(HorizonsBlueprintGrade.GRADE_1, 1, HorizonsBlueprintType.AMMO_CAPACITY, 5),
                Arguments.of(HorizonsBlueprintGrade.GRADE_1, 2, HorizonsBlueprintType.AMMO_CAPACITY, 4),
                Arguments.of(HorizonsBlueprintGrade.GRADE_1, 3, HorizonsBlueprintType.AMMO_CAPACITY, 3),
                Arguments.of(HorizonsBlueprintGrade.GRADE_1, 4, HorizonsBlueprintType.AMMO_CAPACITY, 2),
                Arguments.of(HorizonsBlueprintGrade.GRADE_1, 5, HorizonsBlueprintType.AMMO_CAPACITY, 1),
                Arguments.of(HorizonsBlueprintGrade.GRADE_2, 0, HorizonsBlueprintType.AMMO_CAPACITY, 5),
                Arguments.of(HorizonsBlueprintGrade.GRADE_2, 1, HorizonsBlueprintType.AMMO_CAPACITY, 5),
                Arguments.of(HorizonsBlueprintGrade.GRADE_2, 2, HorizonsBlueprintType.AMMO_CAPACITY, 5),
                Arguments.of(HorizonsBlueprintGrade.GRADE_2, 3, HorizonsBlueprintType.AMMO_CAPACITY, 4),
                Arguments.of(HorizonsBlueprintGrade.GRADE_2, 4, HorizonsBlueprintType.AMMO_CAPACITY, 3),
                Arguments.of(HorizonsBlueprintGrade.GRADE_2, 5, HorizonsBlueprintType.AMMO_CAPACITY, 2),
                Arguments.of(HorizonsBlueprintGrade.GRADE_3, 0, HorizonsBlueprintType.AMMO_CAPACITY, 5),
                Arguments.of(HorizonsBlueprintGrade.GRADE_3, 1, HorizonsBlueprintType.AMMO_CAPACITY, 5),
                Arguments.of(HorizonsBlueprintGrade.GRADE_3, 2, HorizonsBlueprintType.AMMO_CAPACITY, 5),
                Arguments.of(HorizonsBlueprintGrade.GRADE_3, 3, HorizonsBlueprintType.AMMO_CAPACITY, 5),
                Arguments.of(HorizonsBlueprintGrade.GRADE_3, 4, HorizonsBlueprintType.AMMO_CAPACITY, 4),
                Arguments.of(HorizonsBlueprintGrade.GRADE_3, 5, HorizonsBlueprintType.AMMO_CAPACITY, 3),
                Arguments.of(HorizonsBlueprintGrade.GRADE_4, 0, HorizonsBlueprintType.AMMO_CAPACITY, 5),
                Arguments.of(HorizonsBlueprintGrade.GRADE_4, 1, HorizonsBlueprintType.AMMO_CAPACITY, 5),
                Arguments.of(HorizonsBlueprintGrade.GRADE_4, 2, HorizonsBlueprintType.AMMO_CAPACITY, 5),
                Arguments.of(HorizonsBlueprintGrade.GRADE_4, 3, HorizonsBlueprintType.AMMO_CAPACITY, 5),
                Arguments.of(HorizonsBlueprintGrade.GRADE_4, 4, HorizonsBlueprintType.AMMO_CAPACITY, 5),
                Arguments.of(HorizonsBlueprintGrade.GRADE_4, 5, HorizonsBlueprintType.AMMO_CAPACITY, 4),
                Arguments.of(HorizonsBlueprintGrade.GRADE_5, 0, HorizonsBlueprintType.AMMO_CAPACITY, 5),
                Arguments.of(HorizonsBlueprintGrade.GRADE_5, 1, HorizonsBlueprintType.AMMO_CAPACITY, 5),
                Arguments.of(HorizonsBlueprintGrade.GRADE_5, 2, HorizonsBlueprintType.AMMO_CAPACITY, 5),
                Arguments.of(HorizonsBlueprintGrade.GRADE_5, 3, HorizonsBlueprintType.AMMO_CAPACITY, 5),
                Arguments.of(HorizonsBlueprintGrade.GRADE_5, 4, HorizonsBlueprintType.AMMO_CAPACITY, 5),
                Arguments.of(HorizonsBlueprintGrade.GRADE_5, 5, HorizonsBlueprintType.AMMO_CAPACITY, 5),
                Arguments.of(HorizonsBlueprintGrade.GRADE_5, 0, HorizonsBlueprintType.ANTI_GUARDIAN_ZONE_RESISTANCE, 1),
                Arguments.of(HorizonsBlueprintGrade.GRADE_5, 1, HorizonsBlueprintType.ANTI_GUARDIAN_ZONE_RESISTANCE, 1),
                Arguments.of(HorizonsBlueprintGrade.GRADE_5, 2, HorizonsBlueprintType.ANTI_GUARDIAN_ZONE_RESISTANCE, 1),
                Arguments.of(HorizonsBlueprintGrade.GRADE_5, 5, HorizonsBlueprintType.ANTI_GUARDIAN_ZONE_RESISTANCE, 1)
        );
    }

    @ParameterizedTest
    @MethodSource("provideDataForGetNumberOfRolls")
    void getNumberOfRolls(HorizonsBlueprintGrade grade,int engineerRank, HorizonsBlueprintType type, int expected) {
        final int numberOfRolls = grade.getNumberOfRolls(engineerRank, type);
        assertEquals(expected, numberOfRolls);
    }
}