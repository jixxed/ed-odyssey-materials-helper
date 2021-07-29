package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import com.fasterxml.jackson.databind.JsonNode;
import nl.jixxed.eliteodysseymaterials.enums.JournalEventType;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.SimpleLocationEvent;

import java.util.Optional;

public class LeaveBodyMessageProcessor implements MessageProcessor {
    @Override
    public void process(final JsonNode journalMessage) {
        final String starSystem = journalMessage.get("StarSystem").asText();
        EventService.publish(new SimpleLocationEvent(Optional.of(starSystem), Optional.empty(), Optional.empty(), JournalEventType.LEAVEBODY));

    }
}
