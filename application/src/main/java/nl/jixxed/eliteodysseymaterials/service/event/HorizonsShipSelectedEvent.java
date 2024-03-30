package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public class HorizonsShipSelectedEvent implements Event {
    private final String shipUUID;
}

