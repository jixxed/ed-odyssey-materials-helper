package nl.jixxed.eliteodysseymaterials.service.event.trade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.service.event.Event;
import nl.jixxed.eliteodysseymaterials.trade.message.inbound.AcceptedOffersMessage;

@AllArgsConstructor
@Getter
public class AcceptedOffersWebSocketEvent implements Event {
    private final AcceptedOffersMessage acceptedOffersMessage;

}
