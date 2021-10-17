package nl.jixxed.eliteodysseymaterials.service.event.trade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.service.event.Event;
import nl.jixxed.eliteodysseymaterials.trade.message.inbound.OnlineOffersMessage;

@AllArgsConstructor
@Getter
public class OnlineOffersWebSocketEvent implements Event {
    private final OnlineOffersMessage onlineOffersMessage;

}
