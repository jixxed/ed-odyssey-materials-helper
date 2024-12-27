package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Event indicating an empty shipLocker message was received in the journal
 */
@AllArgsConstructor
@Getter
public class ShipLockerEvent implements TimestampedEvent {
    private final String timestamp;
}
