package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SoloModeEvent implements Event {
    private final boolean enabled;
}
