package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum HorizonsBlueprintGrade {
    GRADE_1(1, 2),
    GRADE_2(2, 4),
    GRADE_3(3, 6),
    GRADE_4(4, 8),
    GRADE_5(5, 10),
    NONE(0, 0);
    private final int grade;
    private final int defaultNumberOfRolls;

}
