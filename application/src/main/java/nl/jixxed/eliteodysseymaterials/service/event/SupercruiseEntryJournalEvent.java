package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.journalevents.SupercruiseEntry.SupercruiseEntry;

@Getter
@RequiredArgsConstructor
public class SupercruiseEntryJournalEvent extends JournalEvent {
    private final SupercruiseEntry event;

}
