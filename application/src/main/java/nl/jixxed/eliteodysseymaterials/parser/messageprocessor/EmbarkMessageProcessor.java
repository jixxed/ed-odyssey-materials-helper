package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import com.fasterxml.jackson.databind.JsonNode;
import nl.jixxed.eliteodysseymaterials.service.StorageService;

public class EmbarkMessageProcessor implements MessageProcessor {

    @Override
    public void process(final JsonNode journalMessage) {
        StorageService.resetBackPackCounts();
    }
}
