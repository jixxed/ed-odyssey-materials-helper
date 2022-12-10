package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.schemas.journal.ApproachBody.ApproachBody;
import nl.jixxed.eliteodysseymaterials.service.event.ApproachBodyJournalEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;

public class ApproachBodyMessageProcessor implements MessageProcessor<ApproachBody> {

    @Override
    public void process(final ApproachBody approachBody) {
        EventService.publish(new ApproachBodyJournalEvent(approachBody));

    }

    @Override
    public Class<ApproachBody> getMessageClass() {
        return ApproachBody.class;
    }
}
