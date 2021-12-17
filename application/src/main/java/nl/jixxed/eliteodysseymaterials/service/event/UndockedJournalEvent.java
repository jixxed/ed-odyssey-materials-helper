package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;

@Getter
public class UndockedJournalEvent extends JournalEvent {
    private final String stationName;

    public UndockedJournalEvent(final String timeStamp, final String stationName) {
        super(timeStamp);
        this.stationName = stationName;
    }
}
