package nl.jixxed.eliteodysseymaterials.service.event;

import nl.jixxed.eliteodysseymaterials.enums.RecipeName;

public class BlueprintClickEvent extends Event {
    private final RecipeName recipeName;

    public BlueprintClickEvent(final RecipeName recipeName) {
        this.recipeName = recipeName;
    }

    public RecipeName getRecipeName() {
        return this.recipeName;
    }
}
