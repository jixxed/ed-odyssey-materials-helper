package nl.jixxed.eliteodysseymaterials.service.event.trade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.service.event.Event;
import nl.jixxed.eliteodysseymaterials.trade.message.inbound.XBidPullMessage;

@AllArgsConstructor
@Getter
public class XBidPullWebSocketEvent implements Event {
    private final XBidPullMessage xBidPullMessage;

}
