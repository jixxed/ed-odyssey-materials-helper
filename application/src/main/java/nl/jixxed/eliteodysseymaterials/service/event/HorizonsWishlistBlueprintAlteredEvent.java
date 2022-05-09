package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class HorizonsWishlistBlueprintAlteredEvent implements Event {
    private final String wishlistUUID;
}
