package nl.jixxed.eliteodysseymaterials.service.event.trade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.service.event.Event;
import nl.jixxed.eliteodysseymaterials.trade.message.inbound.BidPushMessage;

@AllArgsConstructor
@Getter
public class BidPushWebSocketEvent implements Event {
    private final BidPushMessage bidPushMessage;

}
