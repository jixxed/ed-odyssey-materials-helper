package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import com.fasterxml.jackson.databind.JsonNode;
import nl.jixxed.eliteodysseymaterials.service.event.ApproachSettlementJournalEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;

public class ApproachSettlementMessageProcessor implements MessageProcessor {

    @Override
    public void process(final JsonNode journalMessage) {
        final String timestamp = asTextOrBlank(journalMessage, "timestamp");
        final String settlement = asTextOrBlank(journalMessage, "Name");
        final String body = asTextOrBlank(journalMessage, "BodyName");
        EventService.publish(new ApproachSettlementJournalEvent(timestamp, body, settlement));
    }
}
