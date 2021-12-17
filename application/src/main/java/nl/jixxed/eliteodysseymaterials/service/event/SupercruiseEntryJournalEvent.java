package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;

@Getter
public class SupercruiseEntryJournalEvent extends JournalEvent {
    private final String starSystem;

    public SupercruiseEntryJournalEvent(final String timeStamp, final String starSystem) {
        super(timeStamp);
        this.starSystem = starSystem;
    }
}
