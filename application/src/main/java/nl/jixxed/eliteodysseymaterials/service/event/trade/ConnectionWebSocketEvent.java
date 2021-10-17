package nl.jixxed.eliteodysseymaterials.service.event.trade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.service.event.Event;

@AllArgsConstructor
@Getter
public class ConnectionWebSocketEvent implements Event {
    private final boolean connected;

}
