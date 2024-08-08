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