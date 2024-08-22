package nl.jixxed.eliteodysseymaterials.templates.odyssey.trade;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TextAreaBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.trade.XMessageWebSocketEvent;
import nl.jixxed.eliteodysseymaterials.trade.MarketPlaceClient;
import nl.jixxed.eliteodysseymaterials.trade.message.common.XMessage;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

class OdysseyChatDialog extends VBox {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private final Optional<MarketPlaceClient> marketPlaceClient = MarketPlaceClient.getInstance();
    private final String offerId;
    @Getter
    private TextArea chat;
    private TextArea input;
    private Button send;
    private String messageRef;
    private final Stage stage;
    private EventListener<XMessageWebSocketEvent> xMessageWebSocketEventEventListener;

    OdysseyChatDialog(final Stage stage, final String chat, final String offerId, final String tokenHash) {
        this.offerId = offerId;
        this.stage = stage;
        initComponents(stage, chat, tokenHash);
        initEventHandling();
    }

    private void initEventHandling() {
        this.xMessageWebSocketEventEventListener = EventService.addListener(true, this, XMessageWebSocketEvent.class, xMessageWebSocketEvent -> {
            final XMessage message = xMessageWebSocketEvent.getXMessageMessage().getMessage();
            if (message.getOfferId().equals(this.offerId)) {
                this.chat.appendText(message.getInfo().getNickname() + "(" + message.getInfo().getLocation() + "): " + message.getText() + "\n");
                if (Objects.equals(message.getMessageRef(), this.messageRef)) {
                    this.send.setDisable(false);
                    this.input.setDisable(false);
                }
            }
        });
    }

    private void initComponents(final Stage stage, final String chat, final String tokenHash) {
        this.chat = TextAreaBuilder.builder()
                .withStyleClass("trade-chat-log")
                .withFocusTraversable(false)
                .build();
        this.input = TextAreaBuilder.builder()
                .withStyleClass("trade-chat-input")
                .build();
        this.chat.setWrapText(true);
        this.input.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER && !event.isShiftDown()) {
                sendMessage(tokenHash);
            } else if (event.getCode() == KeyCode.ENTER) {
                this.input.appendText("\n");
            }
        });
        this.chat.setEditable(false);
        this.chat.setText(chat);
        stage.widthProperty().addListener((observable, oldValue, newValue) -> this.chat.setPrefWidth(newValue.doubleValue()));
        stage.heightProperty().addListener((observable, oldValue, newValue) -> this.chat.setPrefHeight(newValue.doubleValue() - 80));
        stage.widthProperty().addListener((observable, oldValue, newValue) -> this.input.setPrefWidth(newValue.doubleValue() - 80));
        this.send = ButtonBuilder.builder()
                .withStyleClass("trade-chat-send")
                .withText(LocaleService.getStringBinding("trade.message.chat.send"))
                .withOnAction(event -> sendMessage(tokenHash))
                .build();
        this.getChildren().addAll(this.chat, BoxBuilder.builder().withNodes(this.input, this.send).buildHBox());
    }

    private void sendMessage(final String tokenHash) {
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
            final String text = this.input.getText().trim();
            this.messageRef = UUID.randomUUID().toString();
            this.marketPlaceClient.ifPresent(c->c.message(this.offerId, this.messageRef, tokenHash, text));
            this.input.setText("");
            this.send.setDisable(true);
            this.input.setDisable(true);
        });
    }

    void onDestroy() {
        EventService.removeListener(this.xMessageWebSocketEventEventListener);
    }
}
