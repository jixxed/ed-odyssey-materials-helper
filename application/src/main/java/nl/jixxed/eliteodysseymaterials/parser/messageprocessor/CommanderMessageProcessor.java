package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import com.fasterxml.jackson.databind.JsonNode;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;

public class CommanderMessageProcessor implements MessageProcessor {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    @Override
    public void process(final JsonNode journalMessage) {
        if (journalMessage.get("Name") != null) {
            APPLICATION_STATE.addCommander(journalMessage.get("Name").asText(), journalMessage.get("FID").asText());
        }

    }
}
