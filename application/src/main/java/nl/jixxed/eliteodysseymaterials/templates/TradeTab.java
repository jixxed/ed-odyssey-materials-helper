package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.enums.Tabs;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.trade.MarketPlaceClient;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
public class TradeTab extends EDOTab {
    private MarketPlaceClient marketPlaceClient;
    private ScrollPane scrollPane;

    @Override
    public Tabs getTabType() {
        return Tabs.TRADE;
    }

    TradeTab() {
        try {
            this.marketPlaceClient = new MarketPlaceClient(new URI("wss://igkcqgd3dj.execute-api.eu-central-1.amazonaws.com/Prod"));
        } catch (final URISyntaxException e) {
            log.error("failed to connect Websocket", e);
        }
        initComponents();
        initEventHandling();

    }

    private void initComponents() {
        this.scrollPane = new ScrollPane();

        final Label tradeLabel = LabelBuilder.builder()
                .withStyleClass("settings-header")
                .withText(LocaleService.getStringBinding("tabs.settings"))
                .build();
        final Button button = ButtonBuilder.builder()
                .withNonLocalizedText("connect")
                .withOnAction((event) -> this.marketPlaceClient.enlist())
                .build();
        final VBox trade = BoxBuilder.builder()
                //.withStyleClass("")
                .withNodes(tradeLabel, button)
                .buildVBox();
        this.scrollPane.setContent(trade);
        this.setContent(this.scrollPane);

    }

    private void initEventHandling() {

    }
}
