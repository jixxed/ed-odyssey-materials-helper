package nl.jixxed.eliteodysseymaterials.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;

@AllArgsConstructor
public class MaterialTrader {

    @Getter
    private final StarSystem starSystem;
    @Getter
    private final String name;
    @Getter
    private final Double distanceFromStar;
    @Getter
    private final HorizonsStorageType type;


}
