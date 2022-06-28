package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.ModificationChange;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.loadout.OdysseyLoadoutItem;

@RequiredArgsConstructor
@Getter
public class ModificationChangedEvent implements Event {
    private final OdysseyLoadoutItem loadoutItem;
    private final ModificationChange modificationChange;
}
