package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.ColonisationSearch;

@AllArgsConstructor
@Getter
public class HorizonsColonisationSearchEvent implements Event {
    private final ColonisationSearch search;
}
