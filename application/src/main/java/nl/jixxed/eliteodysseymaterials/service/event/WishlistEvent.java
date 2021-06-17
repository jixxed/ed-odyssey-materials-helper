package nl.jixxed.eliteodysseymaterials.service.event;

import nl.jixxed.eliteodysseymaterials.enums.Action;
import nl.jixxed.eliteodysseymaterials.enums.RecipeName;

public class WishlistEvent extends Event {
    private final RecipeName recipeName;
    private final Action action;

    public WishlistEvent(final RecipeName recipeName, final Action action) {
        this.recipeName = recipeName;
        this.action = action;
    }

    public RecipeName getRecipeName() {
        return this.recipeName;
    }

    public Action getAction() {
        return this.action;
    }
}
