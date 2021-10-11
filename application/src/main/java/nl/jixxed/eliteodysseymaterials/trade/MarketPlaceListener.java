package nl.jixxed.eliteodysseymaterials.trade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.trade.EnlistEvent;
import nl.jixxed.eliteodysseymaterials.trade.message.inbound.EnlistMessage;

import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;

@Slf4j
public class MarketPlaceListener implements WebSocket.Listener {

    private StringBuilder text = new StringBuilder();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    MarketPlaceListener() {

    }

    @Override
    public void onOpen(final WebSocket webSocket) {
        log.info("connected");
        webSocket.request(1);
    }

    @Override
    public CompletionStage<?> onText(final WebSocket webSocket,
                                     final CharSequence message,
                                     final boolean last) {
        this.text.append(message);
        if (last) {
            final String data = this.text.toString();
            log.info(data);
            try {
                final JsonNode jsonNode = OBJECT_MAPPER.readTree(data);
                final String code = jsonNode.get("code").asText();
                switch (code) {
                    case "enlist" -> {
                        final EnlistMessage enlistMessage = OBJECT_MAPPER.readValue(data, EnlistMessage.class);
                        EventService.publish(new EnlistEvent(enlistMessage.getTrace().getToken(), enlistMessage.getTrace().getConnectionId()));
                    }
                    default -> log.error("Unexpected message with code: " + code);
                }
            } catch (final JsonProcessingException e) {
                log.error("mapping error", e);
            }
            this.text = new StringBuilder();
        }
        webSocket.request(1);
        return null;
    }

}
