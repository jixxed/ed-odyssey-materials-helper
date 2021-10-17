package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.enums.Material;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.trade.MarketPlaceClient;

class TradeRequest extends Trade {
    private final MarketPlaceClient marketPlaceClient = MarketPlaceClient.getInstance();
    private Label offerMaterialLabel;
    private Label receiveMaterialLabel;
    private Label offerAmountLabel;
    private Label receiveAmountLabel;
    private Button contactButton;
    private boolean bid = false;
    private Label statusLabel;

    TradeRequest(final String offerId, final Material offerMaterial, final Integer offerAmount, final Material receiveMaterial, final Integer receiveAmount) {
        super(offerId, offerMaterial, offerAmount, receiveMaterial, receiveAmount);
        initComponents();
    }

    private void initComponents() {
        this.offerMaterialLabel = LabelBuilder.builder()
                .withStyleClass("trade-request-label")
                .withText(LocaleService.getStringBinding(getOfferMaterial().getLocalizationKey()))
                .build();
        this.offerAmountLabel = LabelBuilder.builder()
                .withStyleClass("trade-request-label")
                .withNonLocalizedText(getOfferAmount().toString())
                .build();
        this.receiveMaterialLabel = LabelBuilder.builder()
                .withStyleClass("trade-request-label")
                .withText(LocaleService.getStringBinding(getReceiveMaterial().getLocalizationKey()))
                .build();
        this.receiveAmountLabel = LabelBuilder.builder()
                .withStyleClass("trade-request-label")
                .withNonLocalizedText(getReceiveAmount().toString())
                .build();
        this.contactButton = ButtonBuilder.builder()
                .withText(LocaleService.getStringBinding("trade.request.button.contact"))
                .withOnAction(event -> {
                    if (this.bid) {
                        this.marketPlaceClient.bidPush(getOfferId());
                    } else {
                        this.marketPlaceClient.bidPull(getOfferId());
                    }
                })
                .build();
        this.statusLabel = LabelBuilder.builder()
                .withStyleClass("trade-request-label")
                .withNonLocalizedText("")
                .build();
        this.getChildren().addAll(this.offerMaterialLabel, this.offerAmountLabel, this.receiveMaterialLabel, this.receiveAmountLabel, this.contactButton, this.statusLabel);
    }

    public void push(final String bidId) {
        this.statusLabel.setText("waiting for a response");
        this.bid = true;
    }

    void pull(final String bidId) {
        this.statusLabel.setText("");
        this.bid = false;
    }

}
