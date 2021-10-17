package nl.jixxed.eliteodysseymaterials.service.event.trade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.service.event.Event;
import nl.jixxed.eliteodysseymaterials.trade.message.inbound.GetOffersMessage;

@AllArgsConstructor
@Getter
public class GetOffersWebSocketEvent implements Event {
    private final GetOffersMessage getOffersMessage;

}
