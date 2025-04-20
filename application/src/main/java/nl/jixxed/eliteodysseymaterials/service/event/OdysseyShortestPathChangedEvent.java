package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.PathItem;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyBlueprintName;

import java.util.List;

@AllArgsConstructor
@Getter
public class OdysseyShortestPathChangedEvent implements Event {
    final List<PathItem<OdysseyBlueprintName>> pathItems;
}
