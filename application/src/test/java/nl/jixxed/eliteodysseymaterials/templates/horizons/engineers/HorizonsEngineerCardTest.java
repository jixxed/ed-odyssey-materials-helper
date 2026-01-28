package nl.jixxed.eliteodysseymaterials.templates.horizons.engineers;

import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class HorizonsEngineerCardTest {
    @Disabled
    @Test
    void listBlueprints() {
        for (Engineer engineer : Arrays.stream(Engineer.values()).filter(Engineer::isHorizons).toList()) {

            Map<HorizonsBlueprint, Integer> hardpointBlueprints = getBlueprints(engineer, HorizonsBlueprintConstants.getHardpointBlueprints());
            Map<HorizonsBlueprint, Integer> utilityMountBlueprints = getBlueprints(engineer, HorizonsBlueprintConstants.getUtilityMountBlueprints());
            Map<HorizonsBlueprint, Integer> coreInternalBlueprints = getBlueprints(engineer, HorizonsBlueprintConstants.getCoreInternalBlueprints());
            Map<HorizonsBlueprint, Integer> optionalInternalBlueprints = getBlueprints(engineer, HorizonsBlueprintConstants.getOptionalInternalBlueprints());
            hardpointBlueprints.entrySet().forEach(entry-> {
                System.out.println(engineer + "," + entry.getKey().getBlueprintName().name() + "," +  entry.getKey().getHorizonsBlueprintType().name() + "," + entry.getValue());
            });
            utilityMountBlueprints.entrySet().forEach(entry-> {
                System.out.println(engineer + "," + entry.getKey().getBlueprintName().name() + "," +  entry.getKey().getHorizonsBlueprintType().name() + "," + entry.getValue());
            });
            coreInternalBlueprints.entrySet().forEach(entry-> {
                System.out.println(engineer + "," + entry.getKey().getBlueprintName().name() + "," +  entry.getKey().getHorizonsBlueprintType().name() + "," + entry.getValue());
            });
            optionalInternalBlueprints.entrySet().forEach(entry-> {
                System.out.println(engineer + "," + entry.getKey().getBlueprintName().name() + "," +  entry.getKey().getHorizonsBlueprintType().name() + "," + entry.getValue());
            });
        }
    }


    @SuppressWarnings("java:S1640")
    private Map<HorizonsBlueprint, Integer> getBlueprints(Engineer engineer, final Map<HorizonsBlueprintName, Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>>> blueprints) {
        final Map<HorizonsBlueprint, Integer> maxGrades = new HashMap<>();
        blueprints.values().stream()
                .flatMap(horizonsBlueprintTypeMapMap -> horizonsBlueprintTypeMapMap.values().stream())
                .flatMap(horizonsBlueprintGradeHorizonsBlueprintMap -> horizonsBlueprintGradeHorizonsBlueprintMap.values().stream())
                .filter(horizonsBlueprint -> horizonsBlueprint.getEngineers().contains(engineer))
                .sorted((Comparator.comparing(HorizonsBlueprint::getHorizonsBlueprintType)))
                .sorted((Comparator.comparing((HorizonsBlueprint horizonsBlueprint) -> horizonsBlueprint.getHorizonsBlueprintGrade().getGrade()).reversed()))
                .forEach(horizonsBlueprint -> {
                    if (maxGrades.keySet().stream().noneMatch(horizonsBlueprintInMap -> horizonsBlueprintInMap.getHorizonsBlueprintName().equals(horizonsBlueprint.getBlueprintName()) && horizonsBlueprintInMap.getHorizonsBlueprintType().equals(horizonsBlueprint.getHorizonsBlueprintType()))) {
                        maxGrades.put(horizonsBlueprint, horizonsBlueprint.getHorizonsBlueprintGrade().getGrade());
                    }
                });
        return maxGrades;
    }
}