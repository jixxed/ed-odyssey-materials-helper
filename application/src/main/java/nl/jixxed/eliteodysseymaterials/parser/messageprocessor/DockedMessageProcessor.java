package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.schemas.journal.Docked.Docked;
import nl.jixxed.eliteodysseymaterials.service.EDDNService;
import nl.jixxed.eliteodysseymaterials.service.MiningService;
import nl.jixxed.eliteodysseymaterials.service.event.DockedJournalEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;

public class DockedMessageProcessor implements MessageProcessor<Docked> {


    @Override
    public void process(final Docked event) {
        EventService.publish(new DockedJournalEvent(event));
        EDDNService.docked(event);
        MiningService.sendMiningEventAndReset();
    }

    @Override
    public Class<Docked> getMessageClass() {
        return Docked.class;
    }
}
