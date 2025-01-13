package nl.jixxed.eliteodysseymaterials.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;

@AllArgsConstructor
public class TechnologyBroker {

    @Getter
    private final StarSystem starSystem;
    @Getter
    private final String name;
    @Getter
    private final Double distanceFromStar;
    @Getter
    private final Double distanceFromStarVariance;
    @Getter
    private final HorizonsBrokerType type;


}
