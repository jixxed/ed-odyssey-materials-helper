package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.Commander;

@AllArgsConstructor
@Getter
public class CommanderAddedEvent implements Event {
    private final Commander commander;

}
