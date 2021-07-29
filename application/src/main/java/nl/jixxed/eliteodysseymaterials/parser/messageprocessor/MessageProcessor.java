package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import com.fasterxml.jackson.databind.JsonNode;

public interface MessageProcessor {
    void process(final JsonNode journalMessage);
}
