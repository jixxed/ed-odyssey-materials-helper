package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsWishlistMaterialSearch;

@AllArgsConstructor
@Getter
public class HorizonsWishlistSearchEvent implements Event {
    private final HorizonsWishlistMaterialSearch search;
}
