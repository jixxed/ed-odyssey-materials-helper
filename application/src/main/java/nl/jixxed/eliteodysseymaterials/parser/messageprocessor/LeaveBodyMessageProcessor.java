package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.schemas.journal.LeaveBody.LeaveBody;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.LeaveBodyJournalEvent;

public class LeaveBodyMessageProcessor implements MessageProcessor<LeaveBody> {

    @Override
    public void process(final LeaveBody event) {
        EventService.publish(new LeaveBodyJournalEvent(event));
    }

    @Override
    public Class<LeaveBody> getMessageClass() {
        return LeaveBody.class;
    }
}
