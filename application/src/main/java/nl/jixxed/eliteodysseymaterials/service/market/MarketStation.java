package nl.jixxed.eliteodysseymaterials.service.market;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;

import java.time.ZonedDateTime;

@RequiredArgsConstructor
public class MarketStation {
    @Getter
    private final StarSystem starSystem;
    @Getter
    private final String name;
    @Getter
    private final Double distanceFromStar;
    @Getter
    private final Long sellPrice;
    @Getter
    private final Long supply;
    @Getter
    private final ZonedDateTime lastMarketUpdate;
}
