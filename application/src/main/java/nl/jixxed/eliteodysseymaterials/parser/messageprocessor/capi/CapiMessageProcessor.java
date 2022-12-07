package nl.jixxed.eliteodysseymaterials.parser.messageprocessor.capi;

import com.fasterxml.jackson.databind.JsonNode;

public interface CapiMessageProcessor {
    void process(final JsonNode jsonNode);

}
