package nl.jixxed.eliteodysseymaterials.service.event.trade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.service.event.Event;
import nl.jixxed.eliteodysseymaterials.trade.message.inbound.BidPullMessage;

@AllArgsConstructor
@Getter
public class BidPullWebSocketEvent implements Event {
    private final BidPullMessage bidPullMessage;

}
