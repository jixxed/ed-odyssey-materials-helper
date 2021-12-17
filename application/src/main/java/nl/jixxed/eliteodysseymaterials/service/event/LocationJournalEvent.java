package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;

@Getter
public class LocationJournalEvent extends JournalEvent {
    private final StarSystem starSystem;
    private final String body;
    private final String stationName;

    public LocationJournalEvent(final String timeStamp, final StarSystem starSystem, final String body, final String stationName) {
        super(timeStamp);
        this.starSystem = starSystem;
        this.body = body;
        this.stationName = stationName;
    }
}
