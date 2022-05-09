package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.PathItem;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyBlueprintName;

@RequiredArgsConstructor
@Getter
public class RemoveWishlistShortestPathItemEvent implements Event {
    private final PathItem<OdysseyBlueprintName> pathItem;
}
