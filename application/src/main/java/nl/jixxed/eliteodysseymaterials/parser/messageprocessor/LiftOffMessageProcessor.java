package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.schemas.journal.Liftoff.Liftoff;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.LiftOffJournalEvent;

public class LiftOffMessageProcessor implements MessageProcessor<Liftoff> {

    @Override
    public void process(final Liftoff event) {
        EventService.publish(new LiftOffJournalEvent(event));
    }

    @Override
    public Class<Liftoff> getMessageClass() {
        return Liftoff.class;
    }
}
