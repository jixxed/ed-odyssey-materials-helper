package nl.jixxed.eliteodysseymaterials.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HorizonsSynthesisWishlistBlueprint extends HorizonsWishlistBlueprint {
    private HorizonsBlueprintGrade blueprintGrade;
}
