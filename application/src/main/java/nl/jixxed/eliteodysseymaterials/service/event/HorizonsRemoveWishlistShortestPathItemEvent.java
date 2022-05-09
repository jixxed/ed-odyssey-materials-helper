package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.PathItem;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;

@RequiredArgsConstructor
@Getter
public class HorizonsRemoveWishlistShortestPathItemEvent implements Event {
    private final PathItem<HorizonsBlueprintName> pathItem;
}
