package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class HorizonsShipChangedEvent implements Event {
    private final String shipUUID;
}

