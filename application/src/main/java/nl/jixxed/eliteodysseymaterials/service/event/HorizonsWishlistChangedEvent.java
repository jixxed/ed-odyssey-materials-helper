package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class HorizonsWishlistChangedEvent implements Event {
    private final String wishlistUUID;
}
