package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.Search;

@AllArgsConstructor
@Getter
public class SearchEvent implements Event {
    private final Search search;
}
