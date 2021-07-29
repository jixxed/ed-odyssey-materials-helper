package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import com.fasterxml.jackson.databind.JsonNode;
import nl.jixxed.eliteodysseymaterials.service.event.BackpackEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;

public class ResupplyMessageProcessor implements MessageProcessor {
    @Override
    public void process(final JsonNode journalMessage) {
        EventService.publish(new BackpackEvent(journalMessage.get("timestamp").asText()));

    }
}
