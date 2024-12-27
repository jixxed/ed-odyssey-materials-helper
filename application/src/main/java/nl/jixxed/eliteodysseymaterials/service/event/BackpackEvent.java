package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Event indicating an empty backpack message was received in the journal
 */
@AllArgsConstructor
@Getter
public class BackpackEvent implements TimestampedEvent {
    private final String timestamp;
}
