package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.LoadoutSet;

@RequiredArgsConstructor
@Getter
public class LoadoutEvent implements Event {
    private final LoadoutSet loadoutSet;
}
