package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.schemas.journal.ApproachBody.ApproachBody;

@Getter
public class ApproachBodyJournalEvent extends JournalEvent {
    private final ApproachBody approachBody;

    public ApproachBodyJournalEvent(final ApproachBody approachBody) {
        this.approachBody = approachBody;
    }
}
