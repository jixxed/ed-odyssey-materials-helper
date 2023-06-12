package nl.jixxed.eliteodysseymaterials.templates.odyssey.trade;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.TradeSpec;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.trade.MarketPlaceClient;

import java.util.Collections;
import java.util.Optional;

@Slf4j
@EqualsAndHashCode(callSuper = false)
class OdysseyTradeOffer extends OdysseyTrade {
    public static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private final Optional<MarketPlaceClient> marketPlaceClient = MarketPlaceClient.getInstance();
    private Button removeOfferButton;
    private Button acceptOfferButton;
    private Button rejectOfferButton;
    private Label statusLabel;
    private Label otherLabel;
    private HBox buttonBox;

    OdysseyTradeOffer(final TradeSpec tradeSpec) {
        super(tradeSpec);
        initComponents();
        handleTradeSpecStatus(getTradeSpec().getTradeStatus());
        log.info("TradeOffer " + tradeSpec.getOfferId());
    }

    @Override
    protected void available() {
        this.statusLabel.textProperty().bind(LocaleService.getStringBinding("trade.status.waiting.for.bids"));
        setVisibleButtons(this.removeOfferButton);
    }

    @Override
    protected void pushed() {
        this.statusLabel.textProperty().bind(LocaleService.getStringBinding("trade.status.received.a.bid"));
        setVisibleButtons(this.removeOfferButton, this.acceptOfferButton, this.rejectOfferButton);
    }

    @Override
    protected void accepted() {
        this.statusLabel.textProperty().bind(LocaleService.getStringBinding("trade.status.bid.accepted"));
        setVisibleButtons(this.removeOfferButton, getContactButton());

    }

    private void setVisibleButtons(final Button... buttons) {
        clearVisibleButtons();
        this.buttonBox.getChildren().addAll(buttons);
    }

    private void clearVisibleButtons() {
        this.buttonBox.getChildren().clear();
    }

    @SuppressWarnings("java:S2177")
    private void initComponents() {
        this.getStyleClass().add("trade-offer");
        this.offerIngredient = new OdysseyTradeIngredient(getOfferOdysseyMaterial().getStorageType(), getOfferOdysseyMaterial(), StorageService.getMaterials(getOfferOdysseyMaterial().getStorageType()).get(getOfferOdysseyMaterial()).getTotalValue(), getOfferAmount(), true);
        this.receiveIngredient = new OdysseyTradeIngredient(getReceiveOdysseyMaterial().getStorageType(), getReceiveOdysseyMaterial(), StorageService.getMaterials(getReceiveOdysseyMaterial().getStorageType()).get(getReceiveOdysseyMaterial()).getTotalValue(), getReceiveAmount(), false);

        this.removeOfferButton = ButtonBuilder.builder()
                .withText(LocaleService.getStringBinding("trade.offer.button.remove"))

                .withOnAction(event -> this.marketPlaceClient.ifPresent(c -> c.dropOffers(Collections.singletonList(getOfferId()))))
                .build();
        this.acceptOfferButton = ButtonBuilder.builder()
                .withText(LocaleService.getStringBinding("trade.buttons.accept"))
                .withOnAction(event -> this.marketPlaceClient.ifPresent(c -> c.bidAccept(this.getOfferId(), getTradeSpec().getBid(), true))
                )
                .build();
        this.rejectOfferButton = ButtonBuilder.builder()
                .withText(LocaleService.getStringBinding("trade.buttons.reject"))
                .withOnAction(event -> this.marketPlaceClient.ifPresent(c -> c.bidAccept(this.getOfferId(), getTradeSpec().getBid(), false)))
                .build();
        this.statusLabel = LabelBuilder.builder()
                .withStyleClass("trade-offer-status-label")
                .withText(LocaleService.getStringBinding("trade.status.waiting.for.bids"))
                .build();
        this.otherLabel = LabelBuilder.builder()
                .withStyleClass("trade-offer-other-label")
                .withNonLocalizedText("")
                .build();
        if (getTradeSpec().hasBid()) {
            this.statusLabel.textProperty().bind(LocaleService.getStringBinding("trade.status.received.a.bid"));
        }
        this.hgapProperty().bind(ScalingHelper.getPixelDoubleBindingFromEm(0.25));
        this.vgapProperty().bind(ScalingHelper.getPixelDoubleBindingFromEm(0.25));
        this.buttonBox = BoxBuilder.builder().buildHBox();
        this.buttonBox.spacingProperty().bind(ScalingHelper.getPixelDoubleBindingFromEm(0.25));
        final VBox statusContactBox = BoxBuilder.builder().withStyleClass("trade-info-box").withNodes(this.statusLabel, this.otherLabel, this.buttonBox).buildVBox();

        this.getChildren().addAll(this.offerIngredient, this.receiveIngredient, statusContactBox);
    }

}
