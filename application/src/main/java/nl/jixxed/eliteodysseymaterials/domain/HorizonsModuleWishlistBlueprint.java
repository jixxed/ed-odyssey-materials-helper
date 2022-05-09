package nl.jixxed.eliteodysseymaterials.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HorizonsModuleWishlistBlueprint extends HorizonsWishlistBlueprint {
    private HorizonsBlueprintType blueprintType;
    private Map<HorizonsBlueprintGrade, Integer> blueprintGradeRolls;

    public void setBlueprintGradeRollsFor(final HorizonsBlueprintGrade grade, final Integer rolls) {
        if (rolls == 0) {
            this.blueprintGradeRolls.remove(grade);
            return;
        } else if (rolls > 0) {
            this.blueprintGradeRolls.put(grade, rolls);
            return;
        }
        throw new IllegalArgumentException("Cannot set rolls for grade: " + grade.name() + "/" + rolls);
    }
}
