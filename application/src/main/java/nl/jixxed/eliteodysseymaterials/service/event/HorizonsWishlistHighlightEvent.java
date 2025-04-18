package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsWishlistBlueprint;

@AllArgsConstructor
@Getter
public class HorizonsWishlistHighlightEvent implements Event {
    private final HorizonsWishlistBlueprint blueprint;
    private final boolean active;
}
