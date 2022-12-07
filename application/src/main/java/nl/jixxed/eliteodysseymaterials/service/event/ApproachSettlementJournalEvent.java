package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.journalevents.ApproachSettlement.ApproachSettlement;

@Getter
@RequiredArgsConstructor
public class ApproachSettlementJournalEvent extends JournalEvent {
    private final ApproachSettlement approachSettlement;
}
