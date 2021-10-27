package nl.jixxed.eliteodysseymaterials.service.event.trade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.service.event.Event;
import nl.jixxed.eliteodysseymaterials.trade.message.inbound.XMessageMessage;

@AllArgsConstructor
@Getter
public class XMessageWebSocketEvent implements Event {
    private final XMessageMessage xMessageMessage;

}
