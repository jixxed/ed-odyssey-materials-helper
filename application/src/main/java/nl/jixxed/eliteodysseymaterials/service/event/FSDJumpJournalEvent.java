package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.schemas.journal.FSDJump.FSDJump;

@Getter
@RequiredArgsConstructor
public class FSDJumpJournalEvent extends JournalEvent {
    private final FSDJump event;
    private final StarSystem starSystem;
    private final String body;

}
