package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;

@Getter
public class LocationJournalEvent extends JournalEvent {
    private final StarSystem starSystem;
    private final String body;
    private final String stationName;
    private final Boolean first;

    public LocationJournalEvent(final String timeStamp, final StarSystem starSystem, final String body, final String stationName, final Boolean first) {
        super(timeStamp);
        this.starSystem = starSystem;
        this.body = body;
        this.stationName = stationName;
        this.first = first;
    }
}
