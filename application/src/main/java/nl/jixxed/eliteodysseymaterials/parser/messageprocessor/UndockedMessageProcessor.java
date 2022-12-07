package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.journalevents.Undocked.Undocked;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.UndockedJournalEvent;

public class UndockedMessageProcessor implements MessageProcessor<Undocked> {

    @Override
    public void process(final Undocked event) {
        EventService.publish(new UndockedJournalEvent(event));

    }

    @Override
    public Class<Undocked> getMessageClass() {
        return Undocked.class;
    }
}
