package nl.jixxed.eliteodysseymaterials.domain.ships;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Modification implements Serializable {
    @EqualsAndHashCode.Include
    private HorizonsBlueprintType modification;
    private BigDecimal modificationCompleteness;
    private HorizonsBlueprintGrade grade;

    public Modification(HorizonsBlueprintType modification, double modificationCompleteness, HorizonsBlueprintGrade grade) {
        this.modification = modification;
        this.modificationCompleteness = BigDecimal.valueOf(modificationCompleteness);
        this.grade = grade;
    }
}
