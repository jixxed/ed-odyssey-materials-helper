package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;

@Getter
public class DockedJournalEvent extends JournalEvent {
    private final String starSystem;
    private final String stationName;

    public DockedJournalEvent(final String timeStamp, final String starSystem, final String stationName) {
        super(timeStamp);
        this.starSystem = starSystem;
        this.stationName = stationName;
    }
}
