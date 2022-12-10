package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.schemas.journal.SupercruiseEntry.SupercruiseEntry;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.SupercruiseEntryJournalEvent;

public class SupercruiseEntryMessageProcessor implements MessageProcessor<SupercruiseEntry> {
    @Override
    public void process(final SupercruiseEntry event) {

        EventService.publish(new SupercruiseEntryJournalEvent(event));
    }
    @Override
    public Class<SupercruiseEntry> getMessageClass() {
        return SupercruiseEntry.class;
    }
}
