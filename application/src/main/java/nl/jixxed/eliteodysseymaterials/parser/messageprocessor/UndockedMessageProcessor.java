package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import com.fasterxml.jackson.databind.JsonNode;
import nl.jixxed.eliteodysseymaterials.enums.JournalEventType;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.SimpleLocationEvent;

import java.util.Optional;

public class UndockedMessageProcessor implements MessageProcessor {
    @Override
    public void process(final JsonNode journalMessage) {
        EventService.publish(new SimpleLocationEvent(Optional.empty(), Optional.empty(), Optional.empty(), JournalEventType.UNDOCKED));

    }
}
