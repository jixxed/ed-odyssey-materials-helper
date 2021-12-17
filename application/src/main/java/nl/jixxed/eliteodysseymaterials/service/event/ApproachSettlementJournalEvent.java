package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;

@Getter
public class ApproachSettlementJournalEvent extends JournalEvent {
    private final String body;
    private final String name;

    public ApproachSettlementJournalEvent(final String timeStamp, final String body, final String name) {
        super(timeStamp);
        this.body = body;
        this.name = name;
    }
}
