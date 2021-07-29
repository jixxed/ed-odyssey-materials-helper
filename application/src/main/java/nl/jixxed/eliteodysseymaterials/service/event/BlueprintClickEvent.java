package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.enums.RecipeName;

@AllArgsConstructor
@Getter
public class BlueprintClickEvent extends Event {
    private final RecipeName recipeName;
}
