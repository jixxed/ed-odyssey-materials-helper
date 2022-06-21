package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.CommoditiesSearch;

@AllArgsConstructor
@Getter
public class HorizonsCommoditiesSearchEvent implements Event {
    private final CommoditiesSearch search;
}
