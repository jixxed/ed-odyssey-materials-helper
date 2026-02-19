package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.PermitsSearch;

@Getter
@RequiredArgsConstructor
public class PermitSearchEvent implements Event {
    private final PermitsSearch permitsSearch;
}

