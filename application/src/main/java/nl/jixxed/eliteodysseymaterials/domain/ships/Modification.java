package nl.jixxed.eliteodysseymaterials.domain.ships;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Optional;

@Data
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Modification implements Serializable {
    @EqualsAndHashCode.Include
    private HorizonsBlueprintType modification;
    private BigDecimal modificationCompleteness;
    private HorizonsBlueprintGrade grade;

    public Modification(HorizonsBlueprintType modification, Double modificationCompleteness, HorizonsBlueprintGrade grade) {
        this.modification = modification;
        this.modificationCompleteness = modificationCompleteness != null ? BigDecimal.valueOf(modificationCompleteness) : null;
        this.grade = grade;
    }

    public Optional<BigDecimal> getModificationCompleteness() {
        return Optional.ofNullable(modificationCompleteness);
    }
}
