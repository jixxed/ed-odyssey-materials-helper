package nl.jixxed.eliteodysseymaterials.service.event.trade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.service.event.Event;
import nl.jixxed.eliteodysseymaterials.trade.message.inbound.EnlistMessage;

@AllArgsConstructor
@Getter
public class EnlistWebSocketEvent implements Event {
    private final EnlistMessage enlistMessage;

}
