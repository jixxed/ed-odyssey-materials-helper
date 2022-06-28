package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.loadout.OdysseyLoadoutItem;

@AllArgsConstructor
@Getter
public class LoadoutRemovedEvent implements Event {
    private final OdysseyLoadoutItem loadoutItem;
}
