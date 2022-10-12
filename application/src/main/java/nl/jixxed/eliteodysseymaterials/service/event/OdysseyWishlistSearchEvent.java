package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.OdysseyWishlistMaterialSearch;

@AllArgsConstructor
@Getter
public class OdysseyWishlistSearchEvent implements Event {
    private final OdysseyWishlistMaterialSearch search;
}
