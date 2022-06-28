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
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.domain.TradeSpec;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.LocationService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.LocationChangedEvent;
import nl.jixxed.eliteodysseymaterials.trade.MarketPlaceClient;

import java.text.NumberFormat;

@Slf4j
@EqualsAndHashCode(callSuper = false)
class OdysseyTradeRequest extends OdysseyTrade {
    public static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance();
    private final MarketPlaceClient marketPlaceClient = MarketPlaceClient.getInstance();
    private Label distanceLabel;
    private Button requestTradeButton;
    private Button cancelTradeButton;
    private Label statusLabel;
    private HBox buttonBox;

    static {
        NUMBER_FORMAT.setMaximumFractionDigits(2);
    }

    OdysseyTradeRequest(final TradeSpec tradeSpec) {
        super(tradeSpec);
        initComponents();
        initEventHandling();
        handleTradeSpecStatus(getTradeSpec().getTradeStatus());
        log.info("TradeRequest " + tradeSpec.getOfferId());
    }

    @Override
    protected void available() {
        setVisibleButtons(this.requestTradeButton);
        this.statusLabel.textProperty().bind(LocaleService.getStringBinding("trade.request.status.available"));
    }

    @Override
    protected void pushed() {
        if (getTradeSpec().isBidFromMe()) {
            this.statusLabel.textProperty().bind(LocaleService.getStringBinding("trade.request.status.waiting.for.a.response"));
            setVisibleButtons(this.cancelTradeButton);
        } else {
            this.statusLabel.textProperty().bind(LocaleService.getStringBinding("trade.request.status.unavailable"));
            clearVisibleButtons();
        }
    }

    @Override
    protected void accepted() {
        if (getTradeSpec().isBidFromMe()) {
            setVisibleButtons(getContactButton());
            this.statusLabel.textProperty().bind(LocaleService.getStringBinding("trade.request.status.accepted"));
        } else {
            clearVisibleButtons();
            this.statusLabel.textProperty().bind(LocaleService.getStringBinding("trade.request.status.unavailable"));
        }
    }

    @Override
    protected void offline() {
        clearVisibleButtons();
        this.statusLabel.textProperty().bind(LocaleService.getStringBinding("trade.request.status.offline"));
    }

    @Override
    protected void removed() {
        clearVisibleButtons();
        this.statusLabel.textProperty().bind(LocaleService.getStringBinding("trade.request.status.removed"));
    }

    @Override
    protected void rejected() {
        clearVisibleButtons();
        this.statusLabel.textProperty().bind(LocaleService.getStringBinding("trade.request.status.refused"));
    }

    @Override
    protected void pulled() {
        clearVisibleButtons();
        this.statusLabel.textProperty().bind(LocaleService.getStringBinding("trade.request.status.cancelled"));
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

        this.getStyleClass().add("trade-request");
        this.offerIngredient = new OdysseyTradeIngredient(getOfferOdysseyMaterial().getStorageType(), getOfferOdysseyMaterial(), StorageService.getMaterials(getOfferOdysseyMaterial().getStorageType()).get(getOfferOdysseyMaterial()).getTotalValue(), getOfferAmount(), true);
        this.receiveIngredient = new OdysseyTradeIngredient(getReceiveOdysseyMaterial().getStorageType(), getReceiveOdysseyMaterial(), StorageService.getMaterials(getReceiveOdysseyMaterial().getStorageType()).get(getReceiveOdysseyMaterial()).getTotalValue(), getReceiveAmount(), false);


        this.distanceLabel = LabelBuilder.builder()
                .withStyleClass("trade-request-distance-label")
                .withText(LocaleService.getStringBinding("trade.request.distance", getStarSystem().getName(), NUMBER_FORMAT.format(LocationService.calculateDistance(LocationService.getCurrentStarSystem(), getStarSystem()))))
                .build();
        this.requestTradeButton = ButtonBuilder.builder()
                .withText(LocaleService.getStringBinding("trade.request.button.request.trade"))
                .withOnAction(event -> this.marketPlaceClient.bidPush(getOfferId()))
                .build();
        this.requestTradeButton.visibleProperty().bind(this.offerIngredient.canTradeProperty());
        this.cancelTradeButton = ButtonBuilder.builder()
                .withText(LocaleService.getStringBinding("trade.request.button.request.cancel"))
                .withOnAction(event ->
                        this.marketPlaceClient.bidPull(getOfferId()))
                .build();
        this.statusLabel = LabelBuilder.builder()
                .withStyleClass("trade-request-status-label")
                .withNonLocalizedText("")
                .build();
        this.hgapProperty().bind(ScalingHelper.getPixelDoubleBindingFromEm(0.25));
        this.vgapProperty().bind(ScalingHelper.getPixelDoubleBindingFromEm(0.25));
        this.buttonBox = BoxBuilder.builder().withNodes(this.requestTradeButton).buildHBox();
        this.buttonBox.spacingProperty().bind(ScalingHelper.getPixelDoubleBindingFromEm(0.25));
        final VBox statusContactBox = BoxBuilder.builder().withStyleClass("trade-info-box").withNodes(this.distanceLabel, this.statusLabel, this.buttonBox).buildVBox();
        this.prefWidthProperty().bind(this.offerIngredient.widthProperty().multiply(this.widthProperty().divide(this.offerIngredient.widthProperty().add(4)).intValue()).add(4));
        this.getChildren().addAll(this.offerIngredient, this.receiveIngredient, statusContactBox);
    }

    @SuppressWarnings("java:S2177")
    private void initEventHandling() {
        EventService.addListener(this, LocationChangedEvent.class, locationChangedEvent -> {
            final StarSystem currentStarSystem = locationChangedEvent.getCurrentStarSystem();
            final Double distance = LocationService.calculateDistance(currentStarSystem, getStarSystem());
            this.distanceLabel.textProperty().bind(LocaleService.getStringBinding("trade.request.distance", getStarSystem().getName(), NUMBER_FORMAT.format(distance)));
        });

    }

}
