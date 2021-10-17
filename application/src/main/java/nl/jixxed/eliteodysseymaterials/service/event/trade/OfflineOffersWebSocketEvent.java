package nl.jixxed.eliteodysseymaterials.service.event.trade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.service.event.Event;
import nl.jixxed.eliteodysseymaterials.trade.message.inbound.OfflineOffersMessage;

@AllArgsConstructor
@Getter
public class OfflineOffersWebSocketEvent implements Event {
    private final OfflineOffersMessage offlineOffersMessage;

}
