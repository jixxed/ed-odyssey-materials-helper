package nl.jixxed.eliteodysseymaterials.service.event.trade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.service.event.Event;
import nl.jixxed.eliteodysseymaterials.trade.message.inbound.XBidAcceptMessage;


@AllArgsConstructor
@Getter
public class XBidAcceptWebSocketEvent implements Event {
    private final XBidAcceptMessage xBidAcceptMessage;

}
