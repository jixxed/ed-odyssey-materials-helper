/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.templates.horizons.engineers;

import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class HorizonsEngineerCardTest {
    @Tag("manual")
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