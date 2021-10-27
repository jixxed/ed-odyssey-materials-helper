package nl.jixxed.eliteodysseymaterials.templates;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.Location;
import nl.jixxed.eliteodysseymaterials.enums.Material;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.trade.MarketPlaceClient;

import java.util.Collections;
import java.util.List;

class TradeOffer extends Trade {
    private final MarketPlaceClient marketPlaceClient = MarketPlaceClient.getInstance();
    private Label offerMaterialLabel;
    private Label receiveMaterialLabel;
    private Label offerAmountLabel;
    private Label receiveAmountLabel;
    private Button removeOfferButton;
    private Button acceptOfferButton;
    private Button rejectOfferButton;
    private Label statusLabel;
    private String bidId = "";
    private String acceptedTokenHash = "";


    TradeOffer(final Application application, final String offerId, final Material offerMaterial, final Integer offerAmount, final Material receiveMaterial, final Integer receiveAmount, final Location location) {
        super(application, offerId, offerMaterial, offerAmount, receiveMaterial, receiveAmount, location);
        initComponents();
    }

    private void initComponents() {
        this.getStyleClass().add("trade-offer");
        this.offerMaterialLabel = LabelBuilder.builder()
                .withStyleClass("trade-offer-material-label")
                .withText(LocaleService.getStringBinding(getOfferMaterial().getLocalizationKey()))
                .build();
        this.offerAmountLabel = LabelBuilder.builder()
                .withStyleClass("trade-offer-amount-label")
                .withNonLocalizedText(getOfferAmount().toString())
                .build();
        this.receiveMaterialLabel = LabelBuilder.builder()
                .withStyleClass("trade-offer-material-label")
                .withText(LocaleService.getStringBinding(getReceiveMaterial().getLocalizationKey()))
                .build();
        this.receiveAmountLabel = LabelBuilder.builder()
                .withStyleClass("trade-offer-amount-label")
                .withNonLocalizedText(getReceiveAmount().toString())
                .build();
        this.removeOfferButton = ButtonBuilder.builder()
                .withText(LocaleService.getStringBinding("trade.offer.button.remove"))
                .withOnAction(event -> this.marketPlaceClient.dropOffers(Collections.singletonList(getOfferId())))
                .build();
        this.acceptOfferButton = ButtonBuilder.builder()
                .withNonLocalizedText("Accept")
                .withOnAction(event ->
                        this.marketPlaceClient.bidAccept(this.getOfferId(), this.bidId, true)
                )
                .build();
        this.rejectOfferButton = ButtonBuilder.builder()
                .withNonLocalizedText("Reject")
                .withOnAction(event ->
                        this.marketPlaceClient.bidAccept(this.getOfferId(), this.bidId, false)
                )
                .build();
        this.acceptOfferButton.setVisible(false);
        this.rejectOfferButton.setVisible(false);
        this.statusLabel = LabelBuilder.builder()
                .withStyleClass("trade-offer-status-label")
                .withNonLocalizedText("")
                .build();
        final Region spacing2 = new Region();
        HBox.setHgrow(spacing2, Priority.ALWAYS);
        this.getChildren().addAll(this.offerMaterialLabel, this.offerAmountLabel, this.receiveMaterialLabel,
                this.receiveAmountLabel, spacing2, this.statusLabel, this.acceptOfferButton, this.rejectOfferButton, this.removeOfferButton, getContactButton());
    }

    public void push(final String bidId) {
        if (this.bidId.equals("")) {
            this.statusLabel.setText("Received bid");
            this.bidId = bidId;
            this.acceptOfferButton.setVisible(true);
            this.rejectOfferButton.setVisible(true);
        }
    }

    void pull(final List<String> bids) {
        if (!bids.contains(this.bidId)) {
            this.statusLabel.setText("");
            this.bidId = "";
        }
    }

    void accept(final boolean accept, final String acceptedTokenHash) {
        this.statusLabel.setText("");
        this.bidId = "";
        if (accept) {
            this.acceptedTokenHash = acceptedTokenHash;
            this.getContactButton().setVisible(true);
            this.acceptOfferButton.setVisible(false);
            this.rejectOfferButton.setVisible(false);
        } else {
            this.getContactButton().setVisible(false);
            this.acceptOfferButton.setVisible(false);
            this.rejectOfferButton.setVisible(false);
        }
    }

    boolean hasBid() {
        return "".equals(this.bidId);
    }

    @Override
    String getTokenHash() {
        return this.acceptedTokenHash;
    }
}
