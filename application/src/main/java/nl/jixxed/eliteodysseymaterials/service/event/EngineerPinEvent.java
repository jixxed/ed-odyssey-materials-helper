package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;

@AllArgsConstructor
@Getter
public class EngineerPinEvent implements Event {
    private final Engineer engineer;
    private final HorizonsBlueprint horizonsBlueprint;
    private final boolean pinned;
}
