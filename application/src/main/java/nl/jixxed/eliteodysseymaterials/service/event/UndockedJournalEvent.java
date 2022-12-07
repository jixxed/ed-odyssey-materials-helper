package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.journalevents.Undocked.Undocked;

@Getter
@RequiredArgsConstructor
public class UndockedJournalEvent extends JournalEvent {
    private final Undocked undocked;

}
