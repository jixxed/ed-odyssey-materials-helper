package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.enums.Expansion;

@AllArgsConstructor
@Getter
public class WishlistHideCompletedEvent implements Event {
    private final Expansion expansion;
    private final Boolean hideCompleted;
}
