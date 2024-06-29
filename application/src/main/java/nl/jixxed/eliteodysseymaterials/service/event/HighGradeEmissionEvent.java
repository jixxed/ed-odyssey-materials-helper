package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.service.hge.ExpiringMessage;

@RequiredArgsConstructor
@Getter
public class HighGradeEmissionEvent implements Event {
    private final ExpiringMessage expiringMessage;
}
