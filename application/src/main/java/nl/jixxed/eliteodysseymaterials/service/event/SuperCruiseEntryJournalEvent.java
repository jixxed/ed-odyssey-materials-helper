package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;

@Getter
public class SuperCruiseEntryJournalEvent extends JournalEvent {
    private final String starSystem;

    public SuperCruiseEntryJournalEvent(final String timeStamp, final String starSystem) {
        super(timeStamp);
        this.starSystem = starSystem;
    }
}
