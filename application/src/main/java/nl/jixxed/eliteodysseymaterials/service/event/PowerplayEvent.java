package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.enums.Power;

@RequiredArgsConstructor
@Data
public class PowerplayEvent implements Event {
    private final Power power;
    private final Long merits;
    private final Long rank;
}
