package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OdysseyWishlistChangedEvent implements Event {
    private final String wishlistUUID;
}
