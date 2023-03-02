package nl.jixxed.eliteodysseymaterials.domain.ships;

import lombok.AllArgsConstructor;
import lombok.Data;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
@Data
@AllArgsConstructor
public class Modification {
    private HorizonsBlueprintType modification;
    private Double modificationCompleteness;
    private HorizonsBlueprintGrade grade;
}
