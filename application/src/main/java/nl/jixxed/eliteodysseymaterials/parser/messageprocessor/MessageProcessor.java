package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import com.fasterxml.jackson.databind.JsonNode;

public interface MessageProcessor {
    void process(final JsonNode journalMessage);

    default String asTextOrBlank(final JsonNode jsonNode, final String field) {
        return jsonNode.get(field) != null ? jsonNode.get(field).asText() : "";
    }
}
