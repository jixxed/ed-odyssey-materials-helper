package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.OdysseyWishlistBlueprint;

@AllArgsConstructor
@Getter
public class OdysseyWishlistHighlightEvent implements Event {
    private final OdysseyWishlistBlueprint blueprint;
    private final int quantity;
    private final boolean active;
}
