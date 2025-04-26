package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Event to indicate a ship has been chosen in the ship list
 **/
@AllArgsConstructor
@Getter
public class HorizonsShipChoiceEvent implements Event {
    private final String shipUUID;
}

