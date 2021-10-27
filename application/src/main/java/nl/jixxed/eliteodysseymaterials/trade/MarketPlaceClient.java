package nl.jixxed.eliteodysseymaterials.trade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.Location;
import nl.jixxed.eliteodysseymaterials.enums.Material;
import nl.jixxed.eliteodysseymaterials.service.LocationService;
import nl.jixxed.eliteodysseymaterials.trade.message.common.Info;
import nl.jixxed.eliteodysseymaterials.trade.message.common.Item;
import nl.jixxed.eliteodysseymaterials.trade.message.common.XMessage;
import nl.jixxed.eliteodysseymaterials.trade.message.outbound.Data;
import nl.jixxed.eliteodysseymaterials.trade.message.outbound.OutboundMessage;
import nl.jixxed.eliteodysseymaterials.trade.message.outbound.payload.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Slf4j
public class MarketPlaceClient {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private WebSocket webSocket;
    private final MarketPlaceListener listener;
    private final URI serverURI;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final Timer timer;
    private final TimerTask task;
    private static MarketPlaceClient marketPlaceClient;

    public static MarketPlaceClient getInstance() {
        try {
            if (marketPlaceClient == null) {
                marketPlaceClient = new MarketPlaceClient(new URI("wss://fgj7vgiy85.execute-api.eu-central-1.amazonaws.com/Prod"));
            }
        } catch (final URISyntaxException e) {
            log.error("failed to connect Websocket", e);
        }
        return marketPlaceClient;
    }

    private MarketPlaceClient(final URI serverURI) {
        this.listener = new MarketPlaceListener();
        this.serverURI = serverURI;
        this.task = new TimerTask() {
            @Override
            public void run() {
                if (MarketPlaceClient.this.webSocket != null && !MarketPlaceClient.this.webSocket.isInputClosed()) {
                    MarketPlaceClient.this.webSocket.sendPing(ByteBuffer.wrap("ping".getBytes(StandardCharsets.UTF_8)));
                }
            }
        };
        this.timer = new Timer("Websocket-keep-alive", true);
        this.timer.scheduleAtFixedRate(this.task, 0, 59 * 1000);
    }

    public void connect() {
        final HttpClient httpClient = HttpClient.newHttpClient();
        this.webSocket = httpClient.newWebSocketBuilder().buildAsync(this.serverURI, this.listener).join();
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
            log.error("getOffers error", e);
        }
    }

    public void publishOffer(final Material offerMaterial, final Integer offerAmount, final Material receiveMaterial, final Integer receiveAmount) {
        final Location location = LocationService.getCurrentLocation();
        final OutboundMessage message = OutboundMessage.builder()
                .action("offer")
                .data(Data.builder()
                        .method("publishoffer")
                        .payload(PublishOfferPayload.builder()
                                .info(Info.builder()
                                        .nickname(APPLICATION_STATE.getPreferredCommander().orElseThrow(IllegalArgumentException::new).getName())
                                        .location(location.getStarSystem())
                                        .x(location.getX())
                                        .y(location.getY())
                                        .z(location.getZ())
                                        .build())
                                .items(List.of(Item.builder()
                                        .did(receiveMaterial.getLocalizationKey())
                                        .sid(offerMaterial.getLocalizationKey())
                                        .demand(receiveAmount)
                                        .supply(offerAmount)
                                        .build()))
                                .created(ZonedDateTime.now().toEpochSecond() * 1000)
                                .expired(ZonedDateTime.now().plusDays(1).toEpochSecond() * 1000)
                                .build())
                        .build())
                .build();
        try {
            final String data = OBJECT_MAPPER.writeValueAsString(message);
            log.debug(data);
            this.webSocket.sendText(data, true);
        } catch (final JsonProcessingException e) {
            log.error("publishOffer error", e);
        }
    }

    public void dropOffers(final List<String> offerIds) {
        final OutboundMessage message = OutboundMessage.builder()
                .action("offer")
                .data(Data.builder()
                        .method("dropoffers")
                        .payload(DropOffersPayload.builder()
                                .offerIds(offerIds)
                                .build())
                        .build())
                .build();
        try {
            final String data = OBJECT_MAPPER.writeValueAsString(message);
            log.debug(data);
            this.webSocket.sendText(data, true);
        } catch (final JsonProcessingException e) {
            log.error("dropOffers error", e);
        }
    }

    public void bidPush(final String offerId) {
        final OutboundMessage message = OutboundMessage.builder()
                .action("comms")
                .data(Data.builder()
                        .method("xbidpush")
                        .payload(XBidPushPayload.builder()
                                .offerId(offerId)
                                .build())
                        .build())
                .build();
        try {
            final String data = OBJECT_MAPPER.writeValueAsString(message);
            log.debug(data);
            this.webSocket.sendText(data, true);
        } catch (final JsonProcessingException e) {
            log.error("bidPush error", e);
        }
    }

    public void bidPull(final String offerId) {
        final OutboundMessage message = OutboundMessage.builder()
                .action("comms")
                .data(Data.builder()
                        .method("xbidpull")
                        .payload(XBidPullPayload.builder()
                                .offerId(offerId)
                                .build())
                        .build())
                .build();
        try {
            final String data = OBJECT_MAPPER.writeValueAsString(message);
            log.debug(data);
            this.webSocket.sendText(data, true);
        } catch (final JsonProcessingException e) {
            log.error("bidPull error", e);
        }
    }

    public void bidAccept(final String offerId, final String tokenHash, final boolean accept) {
        final OutboundMessage message = OutboundMessage.builder()
                .action("comms")
                .data(Data.builder()
                        .method("xbidaccept")
                        .payload(XBidAcceptPayload.builder()
                                .offerId(offerId)
                                .tokenhash(tokenHash)
                                .accept(accept)
                                .build())
                        .build())
                .build();
        try {
            final String data = OBJECT_MAPPER.writeValueAsString(message);
            log.debug(data);
            this.webSocket.sendText(data, true);
        } catch (final JsonProcessingException e) {
            log.error("bidPull error", e);
        }
    }

    public void message(final String offerId, final String messageRef, final String tokenHash, final String text) {
        final Location location = LocationService.getCurrentLocation();
        final OutboundMessage message = OutboundMessage.builder()
                .action("comms")
                .data(Data.builder()
                        .method("xmessage")
                        .payload(MessagePayload.builder()
                                .token(APPLICATION_STATE.getMarketPlaceToken())
                                .message(XMessage.builder()
                                        .messageRef(messageRef)
                                        .offerId(offerId)
                                        .info(Info.builder()
                                                .nickname(APPLICATION_STATE.getPreferredCommander().orElseThrow(IllegalArgumentException::new).getName())
                                                .location(location.getStarSystem())
                                                .x(location.getX())
                                                .y(location.getY())
                                                .z(location.getZ())
                                                .build())
                                        .tokenhash(tokenHash)
                                        .text(text)
                                        .date(ZonedDateTime.now().toEpochSecond())
                                        .build())
                                .build())
                        .build())
                .build();
        try {
            final String data = OBJECT_MAPPER.writeValueAsString(message);
            log.debug(data);
            this.webSocket.sendText(data, true);
        } catch (final JsonProcessingException e) {
            log.error("message error", e);
        }
    }

    public void close() {
        if (MarketPlaceClient.this.webSocket != null && !MarketPlaceClient.this.webSocket.isInputClosed()) {
            this.webSocket.sendClose(WebSocket.NORMAL_CLOSURE, "finished");
        }
    }

}