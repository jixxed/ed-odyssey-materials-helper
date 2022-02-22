package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.WishlistBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.Action;

@AllArgsConstructor
@Getter
public class ViewOnlyWishlistBlueprintEvent implements Event {
    private final WishlistBlueprint wishlistBlueprint;
    private final Action action;
}
