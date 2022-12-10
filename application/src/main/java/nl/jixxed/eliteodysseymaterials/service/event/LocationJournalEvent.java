package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Location.Location;

@Getter
@RequiredArgsConstructor
public class LocationJournalEvent extends JournalEvent {
    private final Location location;
    private final StarSystem starSystem;
    private final String body;
    private final String stationName;
    private final Boolean first;

}
