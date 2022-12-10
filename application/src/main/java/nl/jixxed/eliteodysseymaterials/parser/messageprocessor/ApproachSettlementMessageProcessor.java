package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.schemas.journal.ApproachSettlement.ApproachSettlement;
import nl.jixxed.eliteodysseymaterials.service.EDDNService;
import nl.jixxed.eliteodysseymaterials.service.event.ApproachSettlementJournalEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;

public class ApproachSettlementMessageProcessor implements MessageProcessor<ApproachSettlement> {

    @Override
    public void process(final ApproachSettlement approachSettlement) {
        EventService.publish(new ApproachSettlementJournalEvent(approachSettlement));
        EDDNService.approachSettlement(approachSettlement);
    }

    @Override
    public Class<ApproachSettlement> getMessageClass() {
        return ApproachSettlement.class;
    }
}
