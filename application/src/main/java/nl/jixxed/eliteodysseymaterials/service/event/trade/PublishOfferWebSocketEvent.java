package nl.jixxed.eliteodysseymaterials.service.event.trade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.service.event.Event;
import nl.jixxed.eliteodysseymaterials.trade.message.inbound.PublishOfferMessage;

@AllArgsConstructor
@Getter
public class PublishOfferWebSocketEvent implements Event {
    private final PublishOfferMessage publishOfferMessage;

}
