package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Liftoff.Liftoff;

@Getter
@RequiredArgsConstructor
public class LiftOffJournalEvent extends JournalEvent {
    private final Liftoff liftoff;

}
