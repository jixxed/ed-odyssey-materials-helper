package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum HorizonsBlueprintGrade {
    GRADE_1(1),
    GRADE_2(2),
    GRADE_3(3),
    GRADE_4(4),
    GRADE_5(5),
    NONE(0);
    private final int grade;

    public static HorizonsBlueprintGrade forDigit(final String digit) {
        return forDigit(Integer.parseInt(digit));
    }

    public static HorizonsBlueprintGrade forDigit(final int digit) {
        return Arrays.stream(HorizonsBlueprintGrade.values()).filter(grade -> grade.grade == digit).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return String.valueOf(grade);
    }

    public int getNumberOfRolls(Engineer engineer, HorizonsBlueprintType type) {
        if(engineer == null){
            return 1;
        }
        final Integer engineerRank = ApplicationState.getInstance().getEngineerRank(engineer);
        return getNumberOfRolls(engineerRank,type);
    }

    public int getNumberOfRolls(Integer engineerRank, HorizonsBlueprintType type) {
        if(HorizonsBlueprintType.ANTI_GUARDIAN_ZONE_RESISTANCE.equals(type)){
            return 1;
        }
        if(engineerRank == 0){//engineer not unlocked
            return 5;
        }

        int diff = engineerRank - grade;

        return 5 - diff;
    }
}
