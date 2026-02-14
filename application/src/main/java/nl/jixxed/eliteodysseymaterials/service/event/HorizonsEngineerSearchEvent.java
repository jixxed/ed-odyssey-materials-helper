package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsEngineersSearch;

@AllArgsConstructor
@Getter
public class HorizonsEngineerSearchEvent implements Event {
    private final HorizonsEngineersSearch search;
}
