package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.enums.HardpointGroup;

@RequiredArgsConstructor
@Getter
public class HardpointGroupSelectedEvent implements Event {
    private final HardpointGroup group;
    private final boolean enabled;
}
