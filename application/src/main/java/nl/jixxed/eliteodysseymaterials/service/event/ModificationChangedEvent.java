package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.Loadout;
import nl.jixxed.eliteodysseymaterials.domain.ModificationChange;

@RequiredArgsConstructor
@Getter
public class ModificationChangedEvent implements Event {
    private final Loadout loadout;
    private final ModificationChange modificationChange;
}
