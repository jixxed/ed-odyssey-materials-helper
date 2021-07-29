package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.enums.RecipeName;

@AllArgsConstructor
@Getter
public class BlueprintClickEvent implements Event {
    private final RecipeName recipeName;
}
