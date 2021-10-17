package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.enums.Material;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.trade.MarketPlaceClient;

import java.util.Collections;

class TradeOffer extends Trade {
    private final MarketPlaceClient marketPlaceClient = MarketPlaceClient.getInstance();
    private Label offerMaterialLabel;
    private Label receiveMaterialLabel;
    private Label offerAmountLabel;
    private Label receiveAmountLabel;
    private Button removeOfferButton;
    private Button acceptOfferButton;
    private Label statusLabel;
    private String bidId = "";


    TradeOffer(final String offerId, final Material offerMaterial, final Integer offerAmount, final Material receiveMaterial, final Integer receiveAmount) {
        super(offerId, offerMaterial, offerAmount, receiveMaterial, receiveAmount);
        initComponents();
    }

    private void initComponents() {
        this.offerMaterialLabel = LabelBuilder.builder()
                .withStyleClass("trade-offer-label")
                .withText(LocaleService.getStringBinding(getOfferMaterial().getLocalizationKey()))
                .build();
        this.offerAmountLabel = LabelBuilder.builder()
                .withStyleClass("trade-offer-label")
                .withNonLocalizedText(getOfferAmount().toString())
                .build();
        this.receiveMaterialLabel = LabelBuilder.builder()
                .withStyleClass("trade-offer-label")
                .withText(LocaleService.getStringBinding(getReceiveMaterial().getLocalizationKey()))
                .build();
        this.receiveAmountLabel = LabelBuilder.builder()
                .withStyleClass("trade-offer-label")
                .withNonLocalizedText(getReceiveAmount().toString())
                .build();
        this.removeOfferButton = ButtonBuilder.builder()
                .withText(LocaleService.getStringBinding("trade.offer.button.remove"))
                .withOnAction(event -> this.marketPlaceClient.dropOffers(Collections.singletonList(getOfferId())))
                .build();
        this.acceptOfferButton = ButtonBuilder.builder()
                .withNonLocalizedText("Accept")
                .withOnAction(event -> this.marketPlaceClient.bidPull2(this.getOfferId(), this.bidId))
                .build();
        this.statusLabel = LabelBuilder.builder()
                .withStyleClass("trade-request-label")
                .withNonLocalizedText("")
                .build();
        this.getChildren().addAll(this.offerMaterialLabel, this.offerAmountLabel, this.receiveMaterialLabel, this.receiveAmountLabel, this.removeOfferButton, this.acceptOfferButton, this.statusLabel);
    }

    public void push(final String bidId) {
        this.statusLabel.setText("received bid");
        this.bidId = bidId;
    }

    void pull(final String bidId) {
        this.statusLabel.setText("");
        if (this.bidId.equals(bidId)) {
            this.bidId = "";
        }
    }
}
