package nl.jixxed.eliteodysseymaterials.trade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.service.event.Event;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.trade.*;
import nl.jixxed.eliteodysseymaterials.trade.message.inbound.*;

import java.io.IOException;
import java.net.http.WebSocket;
import java.nio.ByteBuffer;
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
        Platform.runLater(() -> EventService.publish(new ConnectionWebSocketEvent(true)));
    }

    @Override
    public CompletionStage<?> onClose(final WebSocket webSocket, final int statusCode, final String reason) {
        Platform.runLater(() -> EventService.publish(new ConnectionWebSocketEvent(false)));
        log.info("websocket closed. statuscode: " + statusCode + " reason: " + reason);
        return null;
    }

    @Override
    public void onError(final WebSocket webSocket, final Throwable error) {
        log.error("websocket error", error);
        if (error instanceof IOException) {
            Platform.runLater(() -> EventService.publish(new ConnectionWebSocketEvent(false)));
        }
        webSocket.request(1);
    }

    @Override
    public CompletionStage<?> onPing(final WebSocket webSocket, final ByteBuffer message) {
        log.info("ping " + message.toString());
        return null;
    }

    @Override
    public CompletionStage<?> onPong(final WebSocket webSocket, final ByteBuffer message) {
        log.info("pong " + message.toString());
        webSocket.request(1);
        return null;
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
                log.debug(data);
                handleMessage(MessageType.valueOf(code.toUpperCase()), data);
            } catch (final JsonProcessingException e) {
                log.error("mapping error", e);
            } catch (final IllegalArgumentException e) {
                log.error("unknown messagetype/code", e);
            }
            this.text = new StringBuilder();
        }
        webSocket.request(1);
        return null;
    }

    private void handleMessage(final MessageType messageType, final String data) throws JsonProcessingException {
        final Event event = switch (messageType) {
            case ENLIST -> {
                final EnlistMessage enlistMessage = OBJECT_MAPPER.readValue(data, EnlistMessage.class);
                yield new EnlistWebSocketEvent(enlistMessage);
            }
            case GETOFFERS -> {
                final GetOffersMessage getOffersMessage = OBJECT_MAPPER.readValue(data, GetOffersMessage.class);
                yield new GetOffersWebSocketEvent(getOffersMessage);
            }
            case PUBLISHOFFER -> {
                final PublishOfferMessage publishOfferMessage = OBJECT_MAPPER.readValue(data, PublishOfferMessage.class);
                yield new PublishOfferWebSocketEvent(publishOfferMessage);
            }
            case DROPOFFERS -> {
                final DropOffersMessage dropOffersMessage = OBJECT_MAPPER.readValue(data, DropOffersMessage.class);
                yield new DropOffersWebSocketEvent(dropOffersMessage);
            }
            case XBIDPUSH -> {
                final XBidPushMessage xBidPushMessage = OBJECT_MAPPER.readValue(data, XBidPushMessage.class);
                yield new XBidPushWebSocketEvent(xBidPushMessage);
            }
            case XBIDPULL -> {
                final XBidPullMessage xBidPullMessage = OBJECT_MAPPER.readValue(data, XBidPullMessage.class);
                yield new XBidPullWebSocketEvent(xBidPullMessage);
            }
            case XBIDACCEPT -> {
                final XBidAcceptMessage xBidAcceptMessage = OBJECT_MAPPER.readValue(data, XBidAcceptMessage.class);
                yield new XBidAcceptWebSocketEvent(xBidAcceptMessage);
            }
            case XMESSAGE -> {
                final XMessageMessage xMessageMessage = OBJECT_MAPPER.readValue(data, XMessageMessage.class);
                yield new XMessageWebSocketEvent(xMessageMessage);
            }
            case ONLINEOFFERS -> {
                final OnlineOffersMessage onlineOffersMessage = OBJECT_MAPPER.readValue(data, OnlineOffersMessage.class);
                yield new OnlineOffersWebSocketEvent(onlineOffersMessage);
            }
            case OFFLINEOFFERS -> {
                final OfflineOffersMessage offlineOffersMessage = OBJECT_MAPPER.readValue(data, OfflineOffersMessage.class);
                yield new OfflineOffersWebSocketEvent(offlineOffersMessage);
            }
            case ACCEPTEDOFFERS -> {
                final AcceptedOffersMessage acceptedOffersMessage = OBJECT_MAPPER.readValue(data, AcceptedOffersMessage.class);
                yield new AcceptedOffersWebSocketEvent(acceptedOffersMessage);
            }
            case DECLINEDOFFERS -> {
                final DeclinedOffersMessage declinedOffersMessage = OBJECT_MAPPER.readValue(data, DeclinedOffersMessage.class);
                yield new DeclinedOffersWebSocketEvent(declinedOffersMessage);
            }
        };
        Platform.runLater(() -> EventService.publish(event));
    }

}
