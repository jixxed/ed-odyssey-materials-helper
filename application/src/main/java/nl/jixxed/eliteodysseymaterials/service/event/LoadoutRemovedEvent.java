package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.templates.LoadoutItem;

@AllArgsConstructor
@Getter
public class LoadoutRemovedEvent implements Event {
    private final LoadoutItem loadoutItem;
}
