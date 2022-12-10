package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.schemas.journal.CarrierJump.CarrierJump;

@Getter
@RequiredArgsConstructor
public class CarrierJumpJournalEvent extends JournalEvent {
    private final CarrierJump event;
    private final StarSystem starSystem;
    private final String body;

}
