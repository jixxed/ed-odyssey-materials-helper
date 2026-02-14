package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.service.PinnedBlueprintService;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

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

    public static HorizonsBlueprintGrade forDigit(final BigInteger digit) {
        return forDigit(digit.intValue());
    }

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

    public int getNumberOfRolls(Engineer engineer, HorizonsBlueprintName name, HorizonsBlueprintType type) {
        if (engineer == null) {
            return 1;
        }
        Optional<Engineer> bestRemoteEngineer = Optional.empty();
        if (engineer == Engineer.REMOTE_WORKSHOP) {
            bestRemoteEngineer = Arrays.stream(Engineer.values())
                    .filter(eng -> eng.isHorizons() && eng != Engineer.REMOTE_WORKSHOP)
                    .filter(eng -> PinnedBlueprintService.isPinned(eng, (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipe(name, type, GRADE_1)))
                    .max(Comparator.comparing(eng -> ApplicationState.getInstance().getEngineerRank(eng)));
        }
        return bestRemoteEngineer
                .map(remoteEngineer -> getNumberOfRolls(ApplicationState.getInstance().getEngineerRank(remoteEngineer), type))
                .orElseGet(() -> getNumberOfRolls(ApplicationState.getInstance().getEngineerRank(engineer), type));
    }

    public int getNumberOfRolls(Integer engineerRank, HorizonsBlueprintType type) {
        if (HorizonsBlueprintType.ANTI_GUARDIAN_ZONE_RESISTANCE.equals(type)) {
            return 1;
        }
        if (engineerRank == 0) {//engineer not unlocked
            return 5;
        }

        int diff = engineerRank - grade;
        if (diff == 0) {
            return 5;
        }
        if (diff == 1) {
            return 4;
        }
        if (diff == 2) {
            return 3;
        }
        if (diff == 3) {
            return 2;
        }
        if (diff == 4) {
            return 1;
        }
        return 5;
    }
}
