package nl.jixxed.eliteodysseymaterials.service.event.trade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.service.event.Event;
import nl.jixxed.eliteodysseymaterials.trade.message.inbound.DeclinedOffersMessage;

@AllArgsConstructor
@Getter
public class DeclinedOffersWebSocketEvent implements Event {
    private final DeclinedOffersMessage declinedOffersMessage;

}
