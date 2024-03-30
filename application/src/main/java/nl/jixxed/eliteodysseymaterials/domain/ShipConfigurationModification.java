package nl.jixxed.eliteodysseymaterials.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipConfigurationModification {
    private HorizonsBlueprintType type;
    private HorizonsBlueprintGrade grade;
    private BigDecimal percentComplete;
}
