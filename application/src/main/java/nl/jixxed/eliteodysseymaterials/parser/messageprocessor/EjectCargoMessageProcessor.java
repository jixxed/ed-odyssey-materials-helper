package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.schemas.journal.ApproachSettlement.ApproachSettlement;
import nl.jixxed.eliteodysseymaterials.schemas.journal.CargoTransfer.CargoTransfer;
import nl.jixxed.eliteodysseymaterials.schemas.journal.EjectCargo.EjectCargo;
import nl.jixxed.eliteodysseymaterials.service.EDDNService;
import nl.jixxed.eliteodysseymaterials.service.event.ApproachSettlementJournalEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;

import java.time.LocalDateTime;

public class EjectCargoMessageProcessor implements MessageProcessor<EjectCargo> {

    public static LocalDateTime lastEjectCargo = null;
    @Override
    public void process(final EjectCargo event) {
        lastEjectCargo = event.getTimestamp();
    }

    @Override
    public Class<EjectCargo> getMessageClass() {
        return EjectCargo.class;
    }
}
