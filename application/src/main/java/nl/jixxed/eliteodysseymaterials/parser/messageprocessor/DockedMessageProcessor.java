package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import com.fasterxml.jackson.databind.JsonNode;
import nl.jixxed.eliteodysseymaterials.service.event.DockedJournalEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;

public class DockedMessageProcessor implements MessageProcessor {

    @Override
    public void process(final JsonNode journalMessage) {
        final String timestamp = asTextOrBlank(journalMessage, "timestamp");
        final String station = asTextOrBlank(journalMessage, "StationName");
        final String starSystem = asTextOrBlank(journalMessage, "StarSystem");
        EventService.publish(new DockedJournalEvent(timestamp, starSystem, station));
    }
}
