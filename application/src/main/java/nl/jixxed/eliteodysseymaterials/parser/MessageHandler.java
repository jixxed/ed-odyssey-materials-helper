package nl.jixxed.eliteodysseymaterials.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.JournalEventType;
import nl.jixxed.eliteodysseymaterials.parser.messageprocessor.*;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.JournalProcessedEvent;

import java.io.File;
import java.util.Map;

@Slf4j
class MessageHandler {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Map<JournalEventType, MessageProcessor> messageProcessors = Map.ofEntries(
            Map.entry(JournalEventType.COMMANDER, new CommanderMessageProcessor()),
            Map.entry(JournalEventType.ENGINEERPROGRESS, new EngineerProgressMessageProcessor()),
            Map.entry(JournalEventType.EMBARK, new EmbarkMessageProcessor()),
            Map.entry(JournalEventType.SHIPLOCKER, new ShipLockerMessageProcessor()),
            Map.entry(JournalEventType.BACKPACK, new BackpackMessageProcessor()),
            Map.entry(JournalEventType.BACKPACKCHANGE, new BackpackChangeMessageProcessor()),
            Map.entry(JournalEventType.RESUPPLY, new ResupplyMessageProcessor()),
            Map.entry(JournalEventType.FSDJUMP, new FSDJumpMessageProcessor()),
            Map.entry(JournalEventType.LOCATION, new LocationMessageProcessor()),
            Map.entry(JournalEventType.TOUCHDOWN, new TouchdownMessageProcessor()),
            Map.entry(JournalEventType.UNDOCKED, new UndockedMessageProcessor()),
            Map.entry(JournalEventType.LIFTOFF, new LiftOffMessageProcessor()),
            Map.entry(JournalEventType.APPROACHBODY, new ApproachBodyMessageProcessor()),
            Map.entry(JournalEventType.LEAVEBODY, new LeaveBodyMessageProcessor())
    );

    static void handleMessage(final String message, final File file) {
        try {
            final JsonNode jsonNode = OBJECT_MAPPER.readTree(message);
            if (jsonNode.get("event") != null) {
                log.info("event: " + jsonNode.get("event").asText());
                final JournalEventType journalEventType = JournalEventType.forName(jsonNode.get("event").asText());
                final MessageProcessor messageProcessor = messageProcessors.get(journalEventType);
                if (messageProcessor != null) {
                    messageProcessor.process(jsonNode);
                    EventService.publish(new JournalProcessedEvent(jsonNode.get("timestamp").asText(), journalEventType, file));
                }
            } else {
                log.warn("EVENT NULL: " + jsonNode.toPrettyString());
            }
        } catch (final JsonProcessingException e) {
            log.error("Error processing json message", e);
        }
    }
}
