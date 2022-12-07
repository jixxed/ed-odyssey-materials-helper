package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.journalevents.Touchdown.Touchdown;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.TouchdownJournalEvent;

public class TouchdownMessageProcessor implements MessageProcessor<Touchdown> {
    @Override
    public void process(final Touchdown event) {
        EventService.publish(new TouchdownJournalEvent(event));

    }

    @Override
    public Class<Touchdown> getMessageClass() {
        return Touchdown.class;
    }
}
