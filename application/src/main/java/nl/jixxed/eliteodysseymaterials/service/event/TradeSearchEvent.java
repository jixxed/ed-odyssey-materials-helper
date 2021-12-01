package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.TradeSearch;

@AllArgsConstructor
@Getter
public class TradeSearchEvent implements Event {
    private final TradeSearch tradeSearch;
}
