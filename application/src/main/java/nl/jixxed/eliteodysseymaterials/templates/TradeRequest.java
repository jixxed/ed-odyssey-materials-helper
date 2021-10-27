package nl.jixxed.eliteodysseymaterials.templates;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.Location;
import nl.jixxed.eliteodysseymaterials.enums.Material;
import nl.jixxed.eliteodysseymaterials.helper.CryptoHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.LocationService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.LocationEvent;
import nl.jixxed.eliteodysseymaterials.trade.MarketPlaceClient;

import java.text.NumberFormat;
import java.util.List;
import java.util.Objects;

class TradeRequest extends Trade {
    public static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance();
    private final MarketPlaceClient marketPlaceClient = MarketPlaceClient.getInstance();
    private Label offerMaterialLabel;
    private Label receiveMaterialLabel;
    private Label offerAmountLabel;
    private Label receiveAmountLabel;
    private Label distanceLabel;
    private Button contactButton;
    private Button cancelContactButton;
    private boolean bid = false;
    private Label statusLabel;
    private String ownerTokenHash = "";

    TradeRequest(final Application application, final String offerId, final Material offerMaterial, final Integer offerAmount, final Material receiveMaterial, final Integer receiveAmount, final Location location) {
        super(application, offerId, offerMaterial, offerAmount, receiveMaterial, receiveAmount, location);
        initComponents();
        initEventHandling();
    }

    private void initComponents() {

        this.getStyleClass().add("trade-request");
        this.offerMaterialLabel = LabelBuilder.builder()
                .withStyleClass("trade-request-material-label")
                .withText(LocaleService.getStringBinding(getOfferMaterial().getLocalizationKey()))
                .build();
        this.offerAmountLabel = LabelBuilder.builder()
                .withStyleClass("trade-request-amount-label")
                .withNonLocalizedText(getOfferAmount().toString())
                .build();
        this.receiveMaterialLabel = LabelBuilder.builder()
                .withStyleClass("trade-request-material-label")
                .withText(LocaleService.getStringBinding(getReceiveMaterial().getLocalizationKey()))
                .build();
        this.receiveAmountLabel = LabelBuilder.builder()
                .withStyleClass("trade-request-amount-label")
                .withNonLocalizedText(getReceiveAmount().toString())
                .build();
        this.distanceLabel = LabelBuilder.builder()
                .withStyleClass("trade-request-distance-label")
                .withNonLocalizedText("(" + NUMBER_FORMAT.format(LocationService.calculateDistance(LocationService.getCurrentLocation(), getLocation())) + "Ly)")
                .build();
        this.contactButton = ButtonBuilder.builder()
                .withText(LocaleService.getStringBinding("trade.request.button.request.trade"))
                .withOnAction(event -> {
                    this.marketPlaceClient.bidPush(getOfferId());
                })
                .build();
        this.cancelContactButton = ButtonBuilder.builder()
                .withText(LocaleService.getStringBinding("trade.request.button.request.cancel"))
                .withOnAction(event -> {
                    this.marketPlaceClient.bidPull(getOfferId());

                })
                .build();
        this.statusLabel = LabelBuilder.builder()
                .withStyleClass("trade-request-status-label")
                .withNonLocalizedText("")
                .build();
        final Region spacing2 = new Region();
        HBox.setHgrow(spacing2, Priority.ALWAYS);
        this.getChildren().addAll(this.offerMaterialLabel, this.offerAmountLabel, this.receiveMaterialLabel, this.receiveAmountLabel, spacing2, this.distanceLabel, this.statusLabel, this.contactButton, getContactButton());
    }

    private void initEventHandling() {
        EventService.addListener(LocationEvent.class, locationEvent -> {
            final Location currentLocation = locationEvent.getLocation();
            final Double distance = LocationService.calculateDistance(currentLocation, getLocation());
            this.distanceLabel.setText("(" + NUMBER_FORMAT.format(distance) + "Ly)");
        });
    }

    public void push() {
        this.statusLabel.setText("waiting for a response...");
        this.contactButton.setVisible(false);
        this.cancelContactButton.setVisible(true);
        this.bid = true;
    }

    void pull(final List<String> bids) {
        if (!bids.contains(CryptoHelper.sha256("xt23s778RHY", APPLICATION_STATE.getMarketPlaceToken()))) {
            this.statusLabel.setText("");
            this.contactButton.setVisible(true);
            this.cancelContactButton.setVisible(false);
            this.bid = false;
        }
    }

    void accept(final boolean accept, final String acceptedTokenHash, final String ownerTokenHash) {
        final String tokenHash = CryptoHelper.sha256("xt23s778RHY", APPLICATION_STATE.getMarketPlaceToken());
        if (Objects.equals(tokenHash, acceptedTokenHash)) {
            if (accept) {
                this.statusLabel.setText("Accepted.");
                this.getContactButton().setVisible(true);
                this.contactButton.setVisible(false);
                this.cancelContactButton.setVisible(false);
            } else {
                this.statusLabel.setText("Refused.");
                this.bid = false;
                this.getContactButton().setVisible(false);
                this.contactButton.setVisible(true);
                this.cancelContactButton.setVisible(false);
            }
            this.ownerTokenHash = ownerTokenHash;
        }
    }

    @Override
    String getTokenHash() {
        return this.ownerTokenHash;
    }
}
