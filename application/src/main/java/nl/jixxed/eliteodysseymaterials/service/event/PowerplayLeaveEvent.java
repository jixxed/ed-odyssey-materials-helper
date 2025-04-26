package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import nl.jixxed.eliteodysseymaterials.enums.Power;

@AllArgsConstructor
@Data
public class PowerplayLeaveEvent implements Event {
    private final Power power;
}
