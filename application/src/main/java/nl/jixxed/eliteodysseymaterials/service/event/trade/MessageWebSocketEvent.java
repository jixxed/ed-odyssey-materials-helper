package nl.jixxed.eliteodysseymaterials.service.event.trade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.service.event.Event;
import nl.jixxed.eliteodysseymaterials.trade.message.inbound.MessageMessage;

@AllArgsConstructor
@Getter
public class MessageWebSocketEvent implements Event {
    private final MessageMessage messageMessage;

}
