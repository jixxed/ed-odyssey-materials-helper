package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.journalevents.Touchdown.Touchdown;

@Getter
@RequiredArgsConstructor
public class TouchdownJournalEvent extends JournalEvent {
    private final Touchdown touchdown;
}
