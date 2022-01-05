package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import com.fasterxml.jackson.databind.JsonNode;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.LiftOffJournalEvent;

public class LiftOffMessageProcessor implements MessageProcessor {
    @Override
    public void process(final JsonNode journalMessage) {
        final String timestamp = journalMessage.get("timestamp").asText();
        final String starSystem = asTextOrBlank(journalMessage, "StarSystem");
        final String body = asTextOrBlank(journalMessage, "Body");
        final String nearestDestination = asTextOrBlank(journalMessage, "NearestDestination");
        final Boolean playerControlled = journalMessage.get("PlayerControlled").asBoolean(false);
        final Boolean taxi = journalMessage.get("Taxi").asBoolean(false);
        final Double latitude = journalMessage.get("Latitude").asDouble(999.9);
        final Double longitude = journalMessage.get("Longitude").asDouble(999.9);
        EventService.publish(new LiftOffJournalEvent(timestamp, starSystem, body, nearestDestination, playerControlled, taxi, latitude, longitude));
    }
}
