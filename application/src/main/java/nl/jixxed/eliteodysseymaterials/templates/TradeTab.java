package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ComboBoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.Material;
import nl.jixxed.eliteodysseymaterials.enums.Tabs;
import nl.jixxed.eliteodysseymaterials.helper.CryptoHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.trade.*;
import nl.jixxed.eliteodysseymaterials.trade.MarketPlaceClient;
import nl.jixxed.eliteodysseymaterials.trade.message.common.Item;
import nl.jixxed.eliteodysseymaterials.trade.message.common.Offer;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
public class TradeTab extends EDOTab {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private final MarketPlaceClient marketPlaceClient = MarketPlaceClient.getInstance();
    private ScrollPane scrollPane;
    private VBox tradeOffers;
    private VBox tradeRequests;
    private Label status;
    private final Set<Trade> allTrades = new HashSet<>();

    @Override
    public Tabs getTabType() {
        return Tabs.TRADE;
    }

    TradeTab() {

        initComponents();
        initEventHandling();
    }

    private void initComponents() {
        this.scrollPane = new ScrollPane();
        setupScrollPane(this.scrollPane);
        this.textProperty().bind(LocaleService.getStringBinding("tabs.trade"));
        final Label newTradeLabel = LabelBuilder.builder()
                .withStyleClass("settings-header")
                .withText(LocaleService.getStringBinding("tab.trade.new.trade"))
                .build();
        this.status = LabelBuilder.builder().withNonLocalizedText("not connected").build();
        final Button connectButton = ButtonBuilder.builder()
                .withNonLocalizedText("connect")
                .withOnAction(event -> {
                    this.marketPlaceClient.connect();
                    this.marketPlaceClient.enlist();
                    this.marketPlaceClient.getOffers();
                })
                .build();
        final Button disconnectButton = ButtonBuilder.builder()
                .withNonLocalizedText("disconnect")
                .withOnAction(event -> this.marketPlaceClient.close())
                .build();
        final Label offer = LabelBuilder.builder().withNonLocalizedText("offer").build();
        final ComboBox<Material> offerItems = ComboBoxBuilder.builder(Material.class).withItemsProperty(LocaleService.getListBinding(Material.getAllMaterials())).build();
        final Label offerAmount = LabelBuilder.builder().withNonLocalizedText("Amount").build();
        final IntField offerAmountInput = new IntField(0, 1000, 0);
        final Label receive = LabelBuilder.builder().withNonLocalizedText("receive").build();
        final ComboBox<Material> receiveItems = ComboBoxBuilder.builder(Material.class).withItemsProperty(LocaleService.getListBinding(Material.getAllMaterials())).build();
        final Label receiveAmount = LabelBuilder.builder().withNonLocalizedText("Amount").build();
        final IntField receiveAmountInput = new IntField(0, 1000, 0);
        final Callback<ListView<Material>, ListCell<Material>> factory = lv -> new ListCell<>() {
            @Override
            protected void updateItem(final Material item, final boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : LocaleService.getLocalizedStringForCurrentLocale(item.getLocalizationKey()));
            }
        };
        receiveItems.setCellFactory(factory);
        receiveItems.setButtonCell(factory.call(null));
        offerItems.setCellFactory(factory);
        offerItems.setButtonCell(factory.call(null));
        final Button createTradeButton = ButtonBuilder.builder().withOnAction(event -> {
            this.marketPlaceClient.publishOffer(offerItems.getSelectionModel().getSelectedItem(), offerAmountInput.getValue(), receiveItems.getSelectionModel().getSelectedItem(), receiveAmountInput.getValue());
            clearCreateTrade();
        }).withNonLocalizedText("create trade offer").build();
        final HBox newTradeOffer = BoxBuilder.builder()
                .withNodes(offer, offerItems, offerAmount, offerAmountInput)
                .buildHBox();
        final HBox newTradeReceive = BoxBuilder.builder()
                .withNodes(receive, receiveItems, receiveAmount, receiveAmountInput)
                .buildHBox();

        final Label myTradesLabel = LabelBuilder.builder()
                .withStyleClass("settings-header")
                .withText(LocaleService.getStringBinding("tab.trade.my.trades"))
                .build();

        final Label otherTradesLabel = LabelBuilder.builder()
                .withStyleClass("settings-header")
                .withText(LocaleService.getStringBinding("tab.trade.other.trades"))
                .build();
        this.tradeOffers = BoxBuilder.builder()
                //.withStyleClass("")
                .buildVBox();
        this.tradeRequests = BoxBuilder.builder()
                //.withStyleClass("")
                .buildVBox();

        final VBox trade = BoxBuilder.builder()
                //.withStyleClass("")
                .withNodes(newTradeLabel, BoxBuilder.builder().withNodes(connectButton, disconnectButton, this.status).buildHBox(), newTradeOffer, newTradeReceive, createTradeButton, myTradesLabel, this.tradeOffers, otherTradesLabel, this.tradeRequests)
                .buildVBox();
        this.scrollPane.setContent(trade);
        this.setContent(this.scrollPane);

    }

    private void clearCreateTrade() {

    }


    private void initEventHandling() {
        EventService.addListener(EnlistWebSocketEvent.class, enlistWebSocketEvent -> {
            final List<Offer> offers = enlistWebSocketEvent.getEnlistMessage().getOffers();
            final List<Trade> myTradeOffers = offers.stream()
                    .map(this::mapOffer)
                    .filter(Objects::nonNull)
                    .toList();
            this.tradeOffers.getChildren().clear();
            this.tradeOffers.getChildren().addAll(myTradeOffers.stream().filter(TradeOffer.class::isInstance).toList());
//            this.tradeRequests.getChildren().clear();
//            this.tradeRequests.getChildren().addAll(myTradeOffers.stream().filter(TradeRequest.class::isInstance).toList());

            this.allTrades.addAll(myTradeOffers);
        });
        EventService.addListener(ConnectionWebSocketEvent.class, connectionWebSocketEvent -> {
            final boolean connected = connectionWebSocketEvent.isConnected();
            if (connected) {
                this.status.setText("connected");
            } else {
                this.status.setText("not connected");
                this.tradeOffers.getChildren().clear();
                this.tradeRequests.getChildren().clear();
                this.allTrades.clear();
            }
        });
        EventService.addListener(PublishOfferWebSocketEvent.class, publishOfferWebSocketEvent -> {
            try {
                final Offer offer = publishOfferWebSocketEvent.getPublishOfferMessage().getOffer();
                final Trade trade = mapOffer(offer);
                if (trade instanceof TradeOffer) {
                    this.tradeOffers.getChildren().removeIf(node -> ((Trade) node).getOfferId().equals(offer.getOfferId()));
                    this.tradeOffers.getChildren().add(trade);
                } else if (trade instanceof TradeRequest) {
                    this.tradeRequests.getChildren().removeIf(node -> ((Trade) node).getOfferId().equals(offer.getOfferId()));
                    this.tradeRequests.getChildren().add(trade);
                }
                this.allTrades.remove(trade);
                this.allTrades.add(trade);
            } catch (final IllegalArgumentException ex) {
                log.error("failed to fetch items for offer");
            }
        });
        EventService.addListener(GetOffersWebSocketEvent.class, getOffersWebSocketEvent -> {
            final List<Offer> offers = getOffersWebSocketEvent.getGetOffersMessage().getOffers();
            final List<Trade> allTradeOffers = offers.stream()
                    .map(this::mapOffer)
                    .filter(Objects::nonNull)
                    .toList();
//            this.tradeOffers.getChildren().clear();
            this.tradeRequests.getChildren().clear();
//            this.tradeOffers.getChildren().addAll(allTradeOffers.stream().filter(TradeOffer.class::isInstance).toList());
            this.tradeRequests.getChildren().addAll(allTradeOffers.stream().filter(TradeRequest.class::isInstance).toList());
            this.allTrades.removeAll(allTradeOffers);
            this.allTrades.addAll(allTradeOffers);
        });
        EventService.addListener(DropOffersWebSocketEvent.class, dropOffersWebSocketEvent -> {
            final List<String> offers = dropOffersWebSocketEvent.getDropOffersMessage().getOfferIds();
            this.tradeOffers.getChildren().removeIf(trade -> offers.contains(((Trade) trade).getOfferId()));
            this.tradeRequests.getChildren().removeIf(trade -> offers.contains(((Trade) trade).getOfferId()));
            this.allTrades.removeIf(trade -> offers.contains(trade.getOfferId()));
        });
        EventService.addListener(OnlineOffersWebSocketEvent.class, onlineOffersWebSocketEvent -> {
            final List<String> offers = onlineOffersWebSocketEvent.getOnlineOffersMessage().getOfferIds();
            this.allTrades.stream().filter(trade -> offers.contains(trade.getOfferId())).forEach(trade -> trade.setOnline(true));
            offers.forEach(offerId ->
                    {
                        try {
                            final Trade trade = this.allTrades.stream().filter(trade1 -> trade1.getOfferId().equals(offerId)).findFirst().orElseThrow(() -> {
                                throw new IllegalArgumentException("unknown offer marked for online");
                            });
                            if (trade instanceof TradeOffer && !this.tradeOffers.getChildren().contains(trade)) {
                                this.tradeOffers.getChildren().add(trade);
                            } else if (trade instanceof TradeRequest && !this.tradeRequests.getChildren().contains(trade)) {
                                this.tradeRequests.getChildren().add(trade);
                            }
                        } catch (final IllegalArgumentException ex) {
                            log.error("unknown offer marked for online", ex);
                        }
                    }
            );
        });
        EventService.addListener(OfflineOffersWebSocketEvent.class, offlineOffersWebSocketEvent -> {
            final List<String> offers = offlineOffersWebSocketEvent.getOfflineOffersMessage().getOfferIds();
            this.allTrades.stream().filter(trade -> offers.contains(trade.getOfferId())).forEach(trade -> trade.setOnline(false));
            offers.forEach(offerId ->
                    {
                        try {
                            final Trade trade = this.allTrades.stream().filter(trade1 -> trade1.getOfferId().equals(offerId)).findFirst().orElseThrow(() -> {
                                throw new IllegalArgumentException("unknown offer marked for offline");
                            });
                            if (trade instanceof TradeOffer && this.tradeOffers.getChildren().contains(trade)) {
                                this.tradeOffers.getChildren().remove(trade);
                            } else if (trade instanceof TradeRequest && this.tradeRequests.getChildren().contains(trade)) {
                                this.tradeRequests.getChildren().remove(trade);
                            }
                        } catch (final IllegalArgumentException ex) {
                            log.error("unknown offer marked for offline", ex);
                        }

                    }
            );
        });

        EventService.addListener(BidPushWebSocketEvent.class, bidPushWebSocketEvent -> {
            final Offer bidOffer = bidPushWebSocketEvent.getBidPushMessage().getOffer();
            final String offerId = bidOffer.getOfferId();
            final String bidId = bidOffer.getBids().stream().findFirst().orElse("");
            this.allTrades.stream()
                    .filter(trade -> trade.getOfferId().equals(offerId) || (bidOffer.getBids().contains(bidId) && trade.getOfferId().equals(bidOffer.getOfferId())))
                    .findFirst()
                    .ifPresent(trade -> push(trade, bidId));

        });
        EventService.addListener(BidPullWebSocketEvent.class, bidPullWebSocketEvent -> {
            final Offer bidOffer = bidPullWebSocketEvent.getBidPullMessage().getOffer();
            final String offerId = bidOffer.getOfferId();
            final String bidId = CryptoHelper.sha256(offerId, APPLICATION_STATE.getMarketPlaceToken());
            this.allTrades.stream()
                    .filter(trade -> trade.getOfferId().equals(offerId) || (bidOffer.getBids().contains(bidId) && trade.getOfferId().equals(bidOffer.getOfferId())))
                    .findFirst()
                    .ifPresent(trade -> pull(trade, bidId));
        });
        EventService.addListener(MessageWebSocketEvent.class, messageWebSocketEvent -> {
        });
    }

    private void push(final Trade trade, final String bidId) {
        if (trade instanceof TradeOffer tradeOffer) {
            tradeOffer.push(bidId);
        } else if (trade instanceof TradeRequest tradeRequest) {
            tradeRequest.push(bidId);
        }
    }

    private void pull(final Trade trade, final String bidId) {
        if (trade instanceof TradeOffer tradeOffer) {
            tradeOffer.pull(bidId);
        } else if (trade instanceof TradeRequest tradeRequest) {
            tradeRequest.pull(bidId);
        }
    }

    private Trade mapOffer(final Offer offer) {
        try {
            final Item item = offer.getItems().stream().findFirst().orElseThrow(IllegalArgumentException::new);
            final Material offerMaterial = Material.subtypeForName(item.getSid().substring(item.getSid().lastIndexOf(".") + 1));
            final Material receiveMaterial = Material.subtypeForName(item.getDid().substring(item.getDid().lastIndexOf(".") + 1));
            if (Objects.equals(offer.getToken(), CryptoHelper.sha256("xt23s778RHY", APPLICATION_STATE.getMarketPlaceToken()))) {
                return createTradeOffer(offer.getOfferId(), offerMaterial, item.getSupply(), receiveMaterial, item.getDemand());
            } else {
                return createTradeRequest(offer.getOfferId(), offerMaterial, item.getSupply(), receiveMaterial, item.getDemand());
            }
        } catch (final IllegalArgumentException ex) {
            log.error("failed to fetch items for offer");
        }
        return null;
    }

    private TradeOffer createTradeOffer(final String offerId, final Material offerMaterial, final int offerAmount, final Material receiveMaterial, final int receiveAmount) {
        return new TradeOffer(offerId, offerMaterial, offerAmount, receiveMaterial, receiveAmount);
    }

    private TradeRequest createTradeRequest(final String offerId, final Material offerMaterial, final int offerAmount, final Material receiveMaterial, final int receiveAmount) {
        return new TradeRequest(offerId, offerMaterial, offerAmount, receiveMaterial, receiveAmount);
    }
}
