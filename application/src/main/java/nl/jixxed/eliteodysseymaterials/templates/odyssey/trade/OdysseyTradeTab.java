package nl.jixxed.eliteodysseymaterials.templates.odyssey.trade;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ScrollPaneBuilder;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyTabs;
import nl.jixxed.eliteodysseymaterials.enums.TradeType;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.CommanderSelectedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.TerminateApplicationEvent;
import nl.jixxed.eliteodysseymaterials.service.event.trade.ConnectionWebSocketEvent;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.OdysseyTab;
import nl.jixxed.eliteodysseymaterials.trade.MarketPlaceClient;

@Slf4j
public class OdysseyTradeTab extends OdysseyTab {
    private final MarketPlaceClient marketPlaceClient = MarketPlaceClient.getInstance();
    private ScrollPane scrollPane;
    private Label status;
    private Button connectButton;
    private Button disconnectButton;
    private HBox tradeStatusRow;
    private OdysseyTradePagination tradeRequestsPagination;
    private OdysseyTradePagination tradeOffersPagination;
    private OdysseyTradeCreateBlock tradeCreateBlock;
    private boolean reconnect = true;
    private Thread thread;

    @Override
    public OdysseyTabs getTabType() {
        return OdysseyTabs.TRADE;
    }

    public OdysseyTradeTab() {
        initComponents();
        initEventHandling();
    }

    private void initComponents() {
        this.textProperty().bind(LocaleService.getStringBinding("tabs.trade"));

        final Label tradeLabel = LabelBuilder.builder()
                .withStyleClass("settings-header")
                .withText(LocaleService.getStringBinding("tabs.trade"))
                .build();
        this.status = LabelBuilder.builder()
                .withStyleClass("trade-status-label")
                .withText(LocaleService.getStringBinding("tab.trade.status.not.connected"))
                .build();

        this.connectButton = ButtonBuilder.builder()
                .withText(LocaleService.getStringBinding("tab.trade.button.connect"))
                .withOnAction(event -> {
                    this.reconnect = true;
                    connect();
                })
                .build();
        this.disconnectButton = ButtonBuilder.builder()
                .withText(LocaleService.getStringBinding("tab.trade.button.disconnect"))
                .withOnAction(event -> {
                    this.reconnect = false;
                    this.marketPlaceClient.close();
                })
                .build();
        this.tradeRequestsPagination = new OdysseyTradePagination(0, 0, TradeType.REQUEST);
        this.tradeOffersPagination = new OdysseyTradePagination(0, 0, TradeType.OFFER);

        final Region spacing = new Region();
        HBox.setHgrow(spacing, Priority.ALWAYS);
        this.tradeStatusRow = BoxBuilder.builder().withStyleClass("trade-status-row").withNodes(this.status, this.connectButton).buildHBox();
        final HBox titleRow = BoxBuilder.builder().withNodes(tradeLabel, spacing, this.tradeStatusRow).buildHBox();
        this.tradeCreateBlock = new OdysseyTradeCreateBlock();
        final VBox trade = BoxBuilder.builder()
                .withStyleClass("trade")
                .withNodes(titleRow, this.tradeCreateBlock)
                .buildVBox();
        this.tradeOffersPagination.getTrades().sizeProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() > 0 && !trade.getChildren().contains(this.tradeOffersPagination)) {
                trade.getChildren().add(Math.min(3, trade.getChildren().size()), this.tradeOffersPagination);
            } else if (newValue.intValue() == 0) {
                trade.getChildren().remove(this.tradeOffersPagination);
            }
        });
        this.tradeRequestsPagination.getTrades().sizeProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() > 0 && !trade.getChildren().contains(this.tradeRequestsPagination)) {
                trade.getChildren().add(Math.min(4, trade.getChildren().size()), this.tradeRequestsPagination);
            } else if (newValue.intValue() == 0) {
                trade.getChildren().remove(this.tradeRequestsPagination);
            }
        });
        VBox.setVgrow(this.tradeRequestsPagination, Priority.ALWAYS);
        VBox.setVgrow(this.tradeOffersPagination, Priority.ALWAYS);
        this.scrollPane = ScrollPaneBuilder.builder()
                .withContent(trade)
                .build();
        this.setContent(this.scrollPane);

    }

    private void initEventHandling() {
        EventService.addListener(this, ConnectionWebSocketEvent.class, connectionWebSocketEvent -> {
            final boolean connected = connectionWebSocketEvent.isConnected();
            if (connected) {
                this.status.textProperty().bind(LocaleService.getStringBinding("tab.trade.status.connected"));
                this.status.getStyleClass().add("connected");
                this.tradeStatusRow.getChildren().remove(this.connectButton);
                this.tradeStatusRow.getChildren().add(this.disconnectButton);
            } else {
                this.status.textProperty().bind(LocaleService.getStringBinding("tab.trade.status.not.connected"));
                this.status.getStyleClass().remove("connected");
                this.tradeStatusRow.getChildren().remove(this.disconnectButton);
                this.tradeStatusRow.getChildren().add(this.connectButton);
                if (this.reconnect) {
                    connect();
                }
            }
        });
        EventService.addListener(this, CommanderSelectedEvent.class, commanderSelectedEvent -> this.marketPlaceClient.close());

        EventService.addStaticListener(TerminateApplicationEvent.class, event -> {
            this.stop();
        });
    }

    private void connect() {
        this.connectButton.setDisable(true);
        this.status.textProperty().bind(LocaleService.getStringBinding("tab.trade.status.connecting"));
        final Runnable r = () -> {
            this.marketPlaceClient.connect();
            this.marketPlaceClient.enlist();
            this.marketPlaceClient.getOffers();
            Platform.runLater(() -> this.connectButton.setDisable(false));
        };
        this.thread = new Thread(r);
        this.thread.start();
    }

    private void stop() {
        if (this.thread != null) {
            this.thread.interrupt();
        }
    }

}
