package nl.jixxed.eliteodysseymaterials.domain.ships;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;

import java.io.Serializable;

@Data
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Modification implements Serializable {
    @EqualsAndHashCode.Include
    private HorizonsBlueprintType modification;
    private Double modificationCompleteness;
    private HorizonsBlueprintGrade grade;
}
