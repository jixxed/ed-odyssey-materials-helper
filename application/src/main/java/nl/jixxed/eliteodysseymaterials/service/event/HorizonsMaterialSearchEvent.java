package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsMaterialsSearch;

@AllArgsConstructor
@Getter
public class HorizonsMaterialSearchEvent implements Event {
    private final HorizonsMaterialsSearch search;
}
