package nl.jixxed.eliteodysseymaterials.templates.odyssey.trade;

import javafx.beans.binding.ListBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyStorageType;
import nl.jixxed.eliteodysseymaterials.enums.TradeOdysseyMaterial;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.trade.ConnectionWebSocketEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.IntField;
import nl.jixxed.eliteodysseymaterials.trade.MarketPlaceClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class OdysseyTradeCreateBlock extends VBox {
    public static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private final MarketPlaceClient marketPlaceClient = MarketPlaceClient.getInstance();
    private Button createTradeButton;
    private HBox newTradeReceive;
    private HBox newTradeOffer;
    private Label newTradeLabel;
    private Label offer;
    private ComboBox<String> offerItems;
    private Label offerAmount;
    private Label maxOfferAmount;
    private IntField offerAmountInput;
    private Label receive;
    private ComboBox<String> receiveItems;
    private Label receiveAmount;
    private IntField receiveAmountInput;

    OdysseyTradeCreateBlock() {
        initComponents();
        initEventHandling();
    }

    private void initEventHandling() {
        EventService.addListener(this, ConnectionWebSocketEvent.class, connectionWebSocketEvent -> {
            final boolean connected = connectionWebSocketEvent.isConnected();
            this.setConnected(connected);
        });
    }

    private void initComponents() {
        final List<OdysseyMaterial> tradeOdysseyMaterials = new ArrayList<>();
        tradeOdysseyMaterials.add(TradeOdysseyMaterial.NOTHING);
        tradeOdysseyMaterials.add(TradeOdysseyMaterial.ANY_RELEVANT);
        tradeOdysseyMaterials.addAll(OdysseyMaterial.getAllRelevantMaterialsWithoutOverride());
        final ListBinding<String> materialListBinding = LocaleService.getListBinding(() -> tradeOdysseyMaterials.stream().map(material -> LocaleService.getLocalizedStringForCurrentLocale(material.getLocalizationKey())).toArray(String[]::new));
        this.offer = LabelBuilder.builder()
                .withStyleClass("trade-new-offer-offer")
                .withText(LocaleService.getStringBinding("trade.create.offer"))
                .build();
        this.offerItems = ComboBoxBuilder.builder(String.class)
                .withItemsProperty(materialListBinding)
                .build();
        this.offerAmount = LabelBuilder.builder()
                .withStyleClass("trade-new-offer-offer-amount")
                .withText(LocaleService.getStringBinding("trade.create.amount"))
                .withVisibilityProperty(this.offerItems.selectionModelProperty().getValue().selectedItemProperty().isNotEqualTo(LocaleService.getLocalizedStringForCurrentLocale(TradeOdysseyMaterial.NOTHING.getLocalizationKey())))
                .build();
        this.maxOfferAmount = LabelBuilder.builder()
                .withStyleClass("trade-new-offer-max-offer-amount")
                .withText(LocaleService.getStringBinding("trade.create.max", 0))
                .withVisibilityProperty(this.offerItems.selectionModelProperty().getValue().selectedItemProperty().isNotEqualTo(LocaleService.getLocalizedStringForCurrentLocale(TradeOdysseyMaterial.NOTHING.getLocalizationKey())))
                .build();
        this.offerAmountInput = IntFieldBuilder.builder()
                .withMinValue(1)
                .withMaxValue(1000)
                .withInitialValue(1)
                .withVisibilityProperty(this.offerItems.selectionModelProperty().getValue().selectedItemProperty().isNotEqualTo(LocaleService.getLocalizedStringForCurrentLocale(TradeOdysseyMaterial.NOTHING.getLocalizationKey())))
                .build();
        this.offerItems.getSelectionModel().selectedItemProperty().addListener(getOfferItemsChangeListener());
        this.receive = LabelBuilder.builder()
                .withStyleClass("trade-new-offer-receive")
                .withText(LocaleService.getStringBinding("trade.create.in.exchange.for"))
                .build();
        this.receiveItems = ComboBoxBuilder.builder(String.class)
                .withItemsProperty(materialListBinding)
                .build();
        this.receiveAmount = LabelBuilder.builder()
                .withStyleClass("trade-new-offer-receive-amount")
                .withText(LocaleService.getStringBinding("trade.create.amount"))
                .withVisibilityProperty(this.receiveItems.selectionModelProperty().getValue().selectedItemProperty().isNotEqualTo(LocaleService.getLocalizedStringForCurrentLocale(TradeOdysseyMaterial.NOTHING.getLocalizationKey())))
                .build();
        this.receiveAmountInput = IntFieldBuilder.builder()
                .withMinValue(1)
                .withMaxValue(1000)
                .withInitialValue(1)
                .withVisibilityProperty(this.receiveItems.selectionModelProperty().getValue().selectedItemProperty().isNotEqualTo(LocaleService.getLocalizedStringForCurrentLocale(TradeOdysseyMaterial.NOTHING.getLocalizationKey())))
                .build();

        this.newTradeLabel = LabelBuilder.builder()
                .withStyleClass("settings-header")
                .withText(LocaleService.getStringBinding("tab.trade.new.trade"))
                .build();
        this.createTradeButton = ButtonBuilder.builder().withOnAction(event -> {
            if (validate()) {
                final OdysseyMaterial offerOdysseyMaterial = OdysseyMaterial.forLocalizedName(this.offerItems.getValue());
                final int offerAmountValue = TradeOdysseyMaterial.NOTHING.equals(offerOdysseyMaterial) ? 0 : this.offerAmountInput.getValue();
                final OdysseyMaterial receiveOdysseyMaterial = OdysseyMaterial.forLocalizedName(this.receiveItems.getValue());
                final int receiveAmountValue = TradeOdysseyMaterial.NOTHING.equals(receiveOdysseyMaterial) ? 0 : this.receiveAmountInput.getValue();
                this.marketPlaceClient.publishOffer(offerOdysseyMaterial, offerAmountValue, receiveOdysseyMaterial, receiveAmountValue);
                clearCreateTrade();
            }
        }).withText(LocaleService.getStringBinding("create.trade.offer.button")).build();
        this.newTradeOffer = BoxBuilder.builder()
                .withStyleClass("trade-new-offer-row")
                .withNodes(this.offer, this.offerItems, this.offerAmount, this.offerAmountInput, this.maxOfferAmount)
                .buildHBox();
        this.newTradeReceive = BoxBuilder.builder()
                .withStyleClass("trade-new-offer-row")
                .withNodes(this.receive, this.receiveItems, this.receiveAmount, this.receiveAmountInput)
                .buildHBox();
        this.visibleProperty().bind(isConnectedProperty());
        this.getChildren().addAll(this.newTradeLabel, this.newTradeOffer, this.newTradeReceive, this.createTradeButton);
    }

    private ChangeListener<String> getOfferItemsChangeListener() {
        return (observable, oldValue, newValue) -> {
            try {
                final OdysseyMaterial offerOdysseyMaterial = OdysseyMaterial.forLocalizedName(this.offerItems.getValue());
                final int maxOfferAmountValue;
                if (TradeOdysseyMaterial.NOTHING.equals(offerOdysseyMaterial)) {
                    maxOfferAmountValue = 0;
                } else if (TradeOdysseyMaterial.ANY_RELEVANT.equals(offerOdysseyMaterial)) {
                    maxOfferAmountValue = getRelevantMaterialsTotal();
                } else {
                    maxOfferAmountValue = StorageService.getMaterials(offerOdysseyMaterial.getStorageType()).get(offerOdysseyMaterial).getTotalValue();
                }
                if (!TradeOdysseyMaterial.NOTHING.equals(offerOdysseyMaterial)) {
                    this.offerAmountInput.setMaxValue(maxOfferAmountValue);
                    if (this.offerAmountInput.getValue() > maxOfferAmountValue) {
                        this.offerAmountInput.setValue(maxOfferAmountValue);
                    }
                    this.maxOfferAmount.textProperty().bind(LocaleService.getStringBinding("trade.create.max", maxOfferAmountValue));
                }
            } catch (final IllegalArgumentException ex) {
                this.maxOfferAmount.textProperty().unbind();
                this.maxOfferAmount.setText("");
            }
        };
    }

    private int getRelevantMaterialsTotal() {
        Integer total = 0;
        for (final OdysseyStorageType storageType : List.of(OdysseyStorageType.DATA, OdysseyStorageType.GOOD, OdysseyStorageType.ASSET)) {
            total += StorageService.getMaterials(storageType).entrySet().stream()
                    .filter(material -> OdysseyBlueprintConstants.isBlueprintIngredientWithOverride(material.getKey()))
                    .map(entry -> entry.getValue().getTotalValue())
                    .reduce(0, Integer::sum);
        }
        return total;
    }

    private boolean validate() {
        try {
            final OdysseyMaterial offerItem = OdysseyMaterial.forLocalizedName(this.offerItems.getValue());
            final OdysseyMaterial receiveItem = OdysseyMaterial.forLocalizedName(this.receiveItems.getValue());
            if (Objects.equals(offerItem, receiveItem)) {
                NotificationService.showError(NotificationType.ERROR, LocaleService.getLocalizedStringForCurrentLocale("trade.create.invalid.offer"), LocaleService.getLocalizedStringForCurrentLocale("trade.create.invalid.duplicate"));
                return false;
            }
        } catch (final IllegalArgumentException ex) {
            NotificationService.showError(NotificationType.ERROR, LocaleService.getLocalizedStringForCurrentLocale("trade.create.invalid.offer"), LocaleService.getLocalizedStringForCurrentLocale("trade.create.invalid.materials"));
            return false;
        }

        return true;
    }

    private void clearCreateTrade() {
        this.offerItems.getSelectionModel().select(LocaleService.getLocalizedStringForCurrentLocale(TradeOdysseyMaterial.NOTHING.getLocalizationKey()));
        this.receiveItems.getSelectionModel().select(LocaleService.getLocalizedStringForCurrentLocale(TradeOdysseyMaterial.NOTHING.getLocalizationKey()));
        this.offerAmountInput.setValue(1);
        this.receiveAmountInput.setValue(1);
    }

    private BooleanProperty isConnected;

    private final void setConnected(final boolean value) {
        isConnectedProperty().set(value);
    }

    public final boolean isConnected() {
        return this.isConnected == null || this.isConnected.get();
    }

    private final BooleanProperty isConnectedProperty() {
        if (this.isConnected == null) {
            this.isConnected = new SimpleBooleanProperty(false) {

                @Override
                public Object getBean() {
                    return OdysseyTradeCreateBlock.this;
                }

                @Override
                public String getName() {
                    return "isConnected";
                }
            };
        }
        return this.isConnected;
    }
}
