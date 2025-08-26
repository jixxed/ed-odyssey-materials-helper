package nl.jixxed.eliteodysseymaterials.parser.messageprocessor.capi;

import com.fasterxml.jackson.databind.JsonNode;

public interface CapiMessageProcessor<T> {
    void process(final T data);
    Class<T> getMessageClass();
    void clear();

}
