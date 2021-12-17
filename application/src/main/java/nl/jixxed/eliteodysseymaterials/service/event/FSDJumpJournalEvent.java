package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;

@Getter
public class FSDJumpJournalEvent extends JournalEvent {
    private final StarSystem starSystem;
    private final String body;

    public FSDJumpJournalEvent(final String timeStamp, final StarSystem starSystem, final String body) {
        super(timeStamp);
        this.starSystem = starSystem;
        this.body = body;
    }
}
