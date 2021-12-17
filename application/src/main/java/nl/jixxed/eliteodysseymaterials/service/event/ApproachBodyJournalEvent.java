package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;

@Getter
public class ApproachBodyJournalEvent extends JournalEvent {
    private final String starSystem;
    private final String body;

    public ApproachBodyJournalEvent(final String timeStamp, final String starSystem, final String body) {
        super(timeStamp);
        this.starSystem = starSystem;
        this.body = body;
    }
}
