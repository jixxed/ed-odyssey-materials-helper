package nl.jixxed.eliteodysseymaterials.service.event.trade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.service.event.Event;

@AllArgsConstructor
@Getter
public class EnlistEvent implements Event {
    private final String token;
    private final String connectionId;

}
