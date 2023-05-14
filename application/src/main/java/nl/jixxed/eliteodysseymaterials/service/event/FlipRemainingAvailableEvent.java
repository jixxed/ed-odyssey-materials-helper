package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.enums.Expansion;

@RequiredArgsConstructor
@Getter
public class FlipRemainingAvailableEvent implements Event {
    private final Expansion expansion;
    private final boolean showRemaining;
}
