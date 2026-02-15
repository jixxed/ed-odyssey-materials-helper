package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.enums.Permit;

@RequiredArgsConstructor
@Getter
public class PermitEvent implements Event {
    private final Permit permit;
}
