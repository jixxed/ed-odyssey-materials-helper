package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import com.fasterxml.jackson.databind.JsonNode;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.SuperCruiseEntryJournalEvent;

public class SupercruiseEntryMessageProcessor implements MessageProcessor {

    @Override
    public void process(final JsonNode journalMessage) {
        final String timestamp = journalMessage.get("timestamp").asText();
        final String starSystem = journalMessage.get("StarSystem").asText();
        EventService.publish(new SuperCruiseEntryJournalEvent(timestamp, starSystem));
    }
}
