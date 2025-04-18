package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.PathItem;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;

import java.util.List;

@AllArgsConstructor
@Getter
public class ShortestPathChangedEvent implements Event {
    final List<PathItem<HorizonsBlueprintName>> pathItems;
}
