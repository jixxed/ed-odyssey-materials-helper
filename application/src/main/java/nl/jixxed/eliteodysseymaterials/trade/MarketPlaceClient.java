package nl.jixxed.eliteodysseymaterials.trade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.trade.message.outbound.Data;
import nl.jixxed.eliteodysseymaterials.trade.message.outbound.OutboundMessage;
import nl.jixxed.eliteodysseymaterials.trade.message.outbound.payload.EnlistPayload;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;

@Slf4j
public class MarketPlaceClient {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private final WebSocket webSocket;
    private final MarketPlaceListener listener;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public MarketPlaceClient(final URI serverURI) {
        final HttpClient httpClient = HttpClient.newHttpClient();
        this.listener = new MarketPlaceListener();
        this.webSocket = httpClient.newWebSocketBuilder().buildAsync(serverURI, this.listener).join();
    }

    public void connect() {

    }

    public void enlist() {
        final OutboundMessage message = OutboundMessage.builder()
                .action("offer")
                .data(Data.builder()
                        .method("enlist")
                        .payload(EnlistPayload.builder()
                                .token(APPLICATION_STATE.getMarketPlaceToken())
                                .build())
                        .build())
                .build();
        try {
            final String data = OBJECT_MAPPER.writeValueAsString(message);
            log.debug(data);
            this.webSocket.sendText(data, true);
        } catch (final JsonProcessingException e) {
            log.error("enlist error", e);
        }
    }

    public void getOffers() {
        final OutboundMessage message = OutboundMessage.builder()
                .action("offer")
                .data(Data.builder()
                        .method("getoffers")
                        .build())
                .build();
        try {
            final String data = OBJECT_MAPPER.writeValueAsString(message);
            log.debug(data);
            this.webSocket.sendText(data, true);
        } catch (final JsonProcessingException e) {
            log.error("enlist error", e);
        }
    }
}