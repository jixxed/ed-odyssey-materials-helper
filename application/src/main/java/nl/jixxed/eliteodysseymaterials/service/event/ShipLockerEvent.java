package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ShipLockerEvent implements Event {
    private final String timestamp;
}
