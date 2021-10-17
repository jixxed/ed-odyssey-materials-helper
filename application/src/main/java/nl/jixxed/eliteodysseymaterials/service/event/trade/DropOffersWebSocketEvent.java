package nl.jixxed.eliteodysseymaterials.service.event.trade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.service.event.Event;
import nl.jixxed.eliteodysseymaterials.trade.message.inbound.DropOffersMessage;

@AllArgsConstructor
@Getter
public class DropOffersWebSocketEvent implements Event {
    private final DropOffersMessage dropOffersMessage;

}
