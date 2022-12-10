package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.schemas.journal.Resupply.Resupply;
import nl.jixxed.eliteodysseymaterials.service.event.BackpackEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;

import java.time.format.DateTimeFormatter;

public class ResupplyMessageProcessor implements MessageProcessor<Resupply> {

    @Override
    public void process(final Resupply event) {
        final String timestamp = event.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
        EventService.publish(new BackpackEvent(timestamp));
    }

    @Override
    public Class<Resupply> getMessageClass() {
        return Resupply.class;
    }
}
