package nl.jixxed.eliteodysseymaterials.service.event.trade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.service.event.Event;
import nl.jixxed.eliteodysseymaterials.trade.message.inbound.XBidPushMessage;

@AllArgsConstructor
@Getter
public class XBidPushWebSocketEvent implements Event {
    private final XBidPushMessage xBidPushMessage;
}
