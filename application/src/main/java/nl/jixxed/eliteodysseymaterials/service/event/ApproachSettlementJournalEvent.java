package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.schemas.journal.ApproachSettlement.ApproachSettlement;

@Getter
@RequiredArgsConstructor
public class ApproachSettlementJournalEvent extends JournalEvent {
    private final ApproachSettlement approachSettlement;
}
