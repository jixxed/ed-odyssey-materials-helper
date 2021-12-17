package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import com.fasterxml.jackson.databind.JsonNode;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.LeaveBodyJournalEvent;

public class LeaveBodyMessageProcessor implements MessageProcessor {
    @Override
    public void process(final JsonNode journalMessage) {
        final String timestamp = asTextOrBlank(journalMessage, "timestamp");
        final String starSystem = asTextOrBlank(journalMessage, "StarSystem");
        final String body = asTextOrBlank(journalMessage, "Body");
        EventService.publish(new LeaveBodyJournalEvent(timestamp, starSystem, body));

    }
}
