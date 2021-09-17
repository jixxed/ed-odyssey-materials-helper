package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import com.fasterxml.jackson.databind.JsonNode;
import nl.jixxed.eliteodysseymaterials.domain.Location;
import nl.jixxed.eliteodysseymaterials.enums.JournalEventType;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.LocationEvent;
import nl.jixxed.eliteodysseymaterials.service.event.SimpleLocationEvent;

import java.util.Iterator;
import java.util.Optional;

public class LocationMessageProcessor implements MessageProcessor {
    @Override
    public void process(final JsonNode journalMessage) {
        if (journalMessage.get("StarSystem") != null && journalMessage.get("StarPos") != null) {
            final String system = journalMessage.get("StarSystem").asText();
            final Iterator<JsonNode> starPos = journalMessage.get("StarPos").elements();
            final double x = starPos.next().asDouble();
            final double y = starPos.next().asDouble();
            final double z = starPos.next().asDouble();
            EventService.publish(new LocationEvent(new Location(system, x, y, z)));
            final String body = journalMessage.get("Body") != null ? journalMessage.get("Body").asText() : null;
            final String station = journalMessage.get("StationName") != null ? journalMessage.get("StationName").asText() : null;
            EventService.publish(new SimpleLocationEvent(Optional.of(system), Optional.ofNullable(body), Optional.ofNullable(station), JournalEventType.LOCATION));
        }
    }
}
