package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Event indicating an empty cargo message was received in the journal
 */
@AllArgsConstructor
@Getter
public class ShipyardEvent implements Event {
    private final String timestamp;
}
