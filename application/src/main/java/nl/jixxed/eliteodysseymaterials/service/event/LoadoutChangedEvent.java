package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.Loadout;

@AllArgsConstructor
@Getter
public class LoadoutChangedEvent implements Event {
    private final Loadout loadout;
}
