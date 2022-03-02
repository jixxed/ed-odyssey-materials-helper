package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.PathItem;

@RequiredArgsConstructor
@Getter
public class HideWishlistShortestPathItemEvent implements Event {
    private final PathItem pathItem;
}
