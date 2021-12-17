package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import com.fasterxml.jackson.databind.JsonNode;

public interface MessageProcessor {
    void process(final JsonNode journalMessage);

    default String asTextOrBlank(final JsonNode journalMessage, final String field) {
        return journalMessage.get(field) != null ? journalMessage.get(field).asText() : "";
    }
}
