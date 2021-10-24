package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.Wishlist;

@RequiredArgsConstructor
@Getter
public class ImportWishlistEvent implements Event {
    private final Wishlist wishlist;
}
