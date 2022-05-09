package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsWishlistBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.Action;

import java.util.List;

@AllArgsConstructor
@Getter
public class HorizonsWishlistBlueprintEvent implements Event {
    private final String fid;
    private final String wishlistUUID;
    private final List<HorizonsWishlistBlueprint> wishlistBlueprints;
    private final Action action;
}
