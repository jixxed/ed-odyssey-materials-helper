package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.OdysseyWishlistBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.Action;

@AllArgsConstructor
@Getter
public class ViewOnlyWishlistBlueprintEvent implements Event {
    private final OdysseyWishlistBlueprint wishlistBlueprint;
    private final Action action;
}
