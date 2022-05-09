package nl.jixxed.eliteodysseymaterials.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HorizonsTechBrokerWishlistBlueprint extends HorizonsWishlistBlueprint {
    private HorizonsBlueprintType blueprintType;
}
