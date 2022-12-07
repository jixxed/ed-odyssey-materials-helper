package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.journalevents.Liftoff.Liftoff;

@Getter
@RequiredArgsConstructor
public class LiftOffJournalEvent extends JournalEvent {
    private final Liftoff liftoff;

}
