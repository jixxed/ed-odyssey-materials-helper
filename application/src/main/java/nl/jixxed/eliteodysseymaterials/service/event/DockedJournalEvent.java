package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.journalevents.Docked.Docked;

@Getter
@RequiredArgsConstructor
public class DockedJournalEvent extends JournalEvent {
    private final Docked docked;

}
