package nl.jixxed.eliteodysseymaterials.templates;

import javafx.application.Application;
import javafx.beans.binding.ListBinding;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.Location;
import nl.jixxed.eliteodysseymaterials.enums.Material;
import nl.jixxed.eliteodysseymaterials.enums.Tabs;
import nl.jixxed.eliteodysseymaterials.helper.CryptoHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.trade.*;
import nl.jixxed.eliteodysseymaterials.trade.MarketPlaceClient;
import nl.jixxed.eliteodysseymaterials.trade.message.common.Info;
import nl.jixxed.eliteodysseymaterials.trade.message.common.Item;
import nl.jixxed.eliteodysseymaterials.trade.message.common.Offer;
import nl.jixxed.eliteodysseymaterials.trade.message.common.XBid;

import java.util.*;

@Slf4j
public class TradeTab extends EDOTab {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private final MarketPlaceClient marketPlaceClient = MarketPlaceClient.getInstance();
    private final Application application;
    private ScrollPane scrollPane;
    private VBox tradeOffers;
    private VBox tradeRequests;
    private Label status;
    private final Set<Trade> allTrades = new HashSet<>();
    private Button connectButton;
    private Button disconnectButton;
    private HBox tradeStatusRow;
    private Label otherTradesLabel;
    private Label myTradesLabel;
    private Button createTradeButton;
    private HBox newTradeReceive;
    private HBox newTradeOffer;
    private Label newTradeLabel;

    @Override
    public Tabs getTabType() {
        return Tabs.TRADE;
    }

    TradeTab(final Application application) {
        this.application = application;
        initComponents();
        initEventHandling();
    }

    private void initComponents() {
        this.textProperty().bind(LocaleService.getStringBinding("tabs.trade"));
        this.newTradeLabel = LabelBuilder.builder()
                .withStyleClass("settings-header")
                .withText(LocaleService.getStringBinding("tab.trade.new.trade"))
                .build();
        final Label tradeLabel = LabelBuilder.builder()
                .withStyleClass("settings-header")
                .withNonLocalizedText("Trade")
                .build();

        this.status = LabelBuilder.builder().withStyleClass("trade-status-label").withNonLocalizedText("not connected").build();
        this.connectButton = ButtonBuilder.builder()
                .withNonLocalizedText("connect")
                .withOnAction(event -> {
                    this.marketPlaceClient.connect();
                    this.marketPlaceClient.enlist();
                    this.marketPlaceClient.getOffers();
                })
                .build();
        this.disconnectButton = ButtonBuilder.builder()
                .withNonLocalizedText("disconnect")
                .withOnAction(event -> this.marketPlaceClient.close())
                .build();
        final Label offer = LabelBuilder.builder().withStyleClass("trade-new-offer-offer").withNonLocalizedText("Offer").build();
        final ListBinding<Material> materialListBinding = LocaleService.getListBinding(Material.getAllRelevantMaterials());
        final ComboBox<Material> offerItems = ComboBoxBuilder.builder(Material.class).withItemsProperty(materialListBinding).build();
        final Label offerAmount = LabelBuilder.builder().withStyleClass("trade-new-offer-offer-amount").withNonLocalizedText("Amount").build();
        final IntField offerAmountInput = new IntField(1, 1000, 1);
        final Label receive = LabelBuilder.builder().withStyleClass("trade-new-offer-receive").withNonLocalizedText("In exchange for").build();
        final ComboBox<Material> receiveItems = ComboBoxBuilder.builder(Material.class).withItemsProperty(materialListBinding).build();
        final Label receiveAmount = LabelBuilder.builder().withStyleClass("trade-new-offer-receive-amount").withNonLocalizedText("Amount").build();
        final IntField receiveAmountInput = new IntField(1, 1000, 1);
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
        this.createTradeButton = ButtonBuilder.builder().withOnAction(event -> {
            this.marketPlaceClient.publishOffer(offerItems.getSelectionModel().getSelectedItem(), offerAmountInput.getValue(), receiveItems.getSelectionModel().getSelectedItem(), receiveAmountInput.getValue());
            clearCreateTrade();
        }).withNonLocalizedText("create trade offer").build();
        this.newTradeOffer = BoxBuilder.builder()
                .withStyleClass("trade-new-offer-row")
                .withNodes(offer, offerItems, offerAmount, offerAmountInput)
                .buildHBox();
        this.newTradeReceive = BoxBuilder.builder()
                .withStyleClass("trade-new-offer-row")
                .withNodes(receive, receiveItems, receiveAmount, receiveAmountInput)
                .buildHBox();

        this.myTradesLabel = LabelBuilder.builder()
                .withStyleClass("settings-header")
                .withText(LocaleService.getStringBinding("tab.trade.my.trades"))
                .build();

        this.otherTradesLabel = LabelBuilder.builder()
                .withStyleClass("settings-header")
                .withText(LocaleService.getStringBinding("tab.trade.other.trades"))
                .build();
        this.tradeOffers = BoxBuilder.builder()
                .withStyleClass("trade-offer-list")
                .buildVBox();
        this.tradeRequests = BoxBuilder.builder()
                .withStyleClass("trade-request-list")
                .buildVBox();

        final Region spacing = new Region();
        HBox.setHgrow(spacing, Priority.ALWAYS);
        this.tradeStatusRow = BoxBuilder.builder().withStyleClass("trade-status-row").withNodes(this.status, this.connectButton).buildHBox();
        final HBox titleRow = BoxBuilder.builder().withNodes(tradeLabel, spacing, this.tradeStatusRow).buildHBox();
        final VBox trade = BoxBuilder.builder()
                .withStyleClass("trade")
                .withNodes(titleRow, this.newTradeLabel, this.newTradeOffer, this.newTradeReceive, this.createTradeButton, this.myTradesLabel, this.tradeOffers, this.otherTradesLabel, this.tradeRequests)
                .buildVBox();
        setVisible(false, this.newTradeLabel, this.newTradeOffer, this.newTradeReceive, this.createTradeButton, this.myTradesLabel, this.tradeOffers, this.otherTradesLabel, this.tradeRequests);
        this.scrollPane = ScrollPaneBuilder.builder()
                .withContent(trade)
                .build();
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
                this.status.setText("Connected");
                this.status.getStyleClass().add("connected");
                this.tradeStatusRow.getChildren().remove(this.connectButton);
                this.tradeStatusRow.getChildren().add(this.disconnectButton);
                setVisible(true, this.newTradeLabel, this.newTradeOffer, this.newTradeReceive, this.createTradeButton, this.myTradesLabel, this.tradeOffers, this.otherTradesLabel, this.tradeRequests);
            } else {
                this.status.setText("Not connected");
                this.status.getStyleClass().remove("connected");
                this.tradeOffers.getChildren().clear();
                this.tradeRequests.getChildren().clear();
                this.allTrades.clear();
                this.tradeStatusRow.getChildren().remove(this.disconnectButton);
                this.tradeStatusRow.getChildren().add(this.connectButton);
                setVisible(false, this.newTradeLabel, this.newTradeOffer, this.newTradeReceive, this.createTradeButton, this.myTradesLabel, this.tradeOffers, this.otherTradesLabel, this.tradeRequests);
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
            this.tradeRequests.getChildren().clear();
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

        EventService.addListener(XBidPushWebSocketEvent.class, xBidPushWebSocketEvent -> {
            final Offer bidOffer = xBidPushWebSocketEvent.getXBidPushMessage().getOffer();
            final String offerId = bidOffer.getOfferId();
            final String bidId = bidOffer.getXbids().stream().sorted(Comparator.comparingLong(XBid::getTimestamp)).map(XBid::getTokenhash).findFirst().orElse("");
            this.allTrades.stream()
                    .filter(trade -> trade.getOfferId().equals(offerId))
                    .findFirst()
                    .ifPresent(trade -> {
                        if (trade instanceof TradeOffer tradeOffer && tradeOffer.hasBid() && bidOffer.getXbids().size() > 1) {
                            bidOffer.getXbids().stream()
                                    .filter(xBid -> !xBid.getTokenhash().equals(bidId))
                                    .forEach(xBid -> this.marketPlaceClient.bidAccept(offerId, xBid.getTokenhash(), false));
                        }
                        push(trade, bidId);
                    });

        });

        EventService.addListener(XBidPullWebSocketEvent.class, xBidPullWebSocketEvent -> {
            final Offer bidOffer = xBidPullWebSocketEvent.getXBidPullMessage().getOffer();
            final String offerId = bidOffer.getOfferId();
            final List<String> bids = bidOffer.getXbids().stream().map(XBid::getTokenhash).toList();
            this.allTrades.stream()
                    .filter(trade -> trade.getOfferId().equals(offerId))
                    .findFirst()
                    .ifPresent(trade -> pull(trade, bids));
        });

        EventService.addListener(XBidAcceptWebSocketEvent.class, xBidAcceptWebSocketEvent -> {
            final Offer bidOffer = xBidAcceptWebSocketEvent.getXBidAcceptMessage().getOffer();
            final String offerId = bidOffer.getOfferId();
            final Optional<XBid> acceptedBid = bidOffer.getXbids().stream().sorted(Comparator.comparingLong(XBid::getTimestamp)).filter(XBid::getAccepted).findFirst();
            final String acceptedTokenHash = acceptedBid.map(XBid::getTokenhash).orElse("");
            final String ownerHash = bidOffer.getToken();
            final boolean accepted = acceptedBid.map(XBid::getAccepted).orElse(Boolean.FALSE);
            this.allTrades.stream()
                    .filter(trade -> trade.getOfferId().equals(offerId))
                    .findFirst()
                    .ifPresent(trade -> accept(trade, accepted, acceptedTokenHash, ownerHash));
        });

        EventService.addListener(XMessageWebSocketEvent.class, xMessageWebSocketEvent -> {
        });
    }

    private void setVisible(final boolean visible, final Node... nodes) {
        Arrays.stream(nodes).forEach(node -> node.setVisible(visible));
    }

    private void push(final Trade trade, final String bidId) {
        if (trade instanceof TradeOffer tradeOffer) {
            tradeOffer.push(bidId);
        } else if (trade instanceof TradeRequest tradeRequest) {
            tradeRequest.push();
        }
    }

    private void pull(final Trade trade, final List<String> bids) {
        if (trade instanceof TradeOffer tradeOffer) {
            tradeOffer.pull(bids);
        } else if (trade instanceof TradeRequest tradeRequest) {
            tradeRequest.pull(bids);
        }
    }

    private void accept(final Trade trade, final boolean accept, final String acceptedTokenHash, final String ownerHash) {
        if (trade instanceof TradeOffer tradeOffer) {
            tradeOffer.accept(accept, acceptedTokenHash);
        } else if (trade instanceof TradeRequest tradeRequest) {
            tradeRequest.accept(accept, acceptedTokenHash, ownerHash);
        }
    }

    private Trade mapOffer(final Offer offer) {
        try {
            final Item item = offer.getItems().stream().findFirst().orElseThrow(IllegalArgumentException::new);
            final Info info = offer.getInfo();
            final Material offerMaterial = Material.subtypeForName(item.getSid().substring(item.getSid().lastIndexOf(".") + 1));
            final Material receiveMaterial = Material.subtypeForName(item.getDid().substring(item.getDid().lastIndexOf(".") + 1));
            final Location location = new Location(info.getLocation(), info.getX(), info.getY(), info.getZ());
            if (Objects.equals(offer.getToken(), CryptoHelper.sha256("xt23s778RHY", APPLICATION_STATE.getMarketPlaceToken()))) {
                return createTradeOffer(offer.getOfferId(), offerMaterial, item.getSupply(), receiveMaterial, item.getDemand(), location);
            } else {
                return createTradeRequest(offer.getOfferId(), offerMaterial, item.getSupply(), receiveMaterial, item.getDemand(), location);
            }
        } catch (final IllegalArgumentException ex) {
            log.error("failed to fetch items for offer");
        }
        return null;
    }

    private TradeOffer createTradeOffer(final String offerId, final Material offerMaterial, final int offerAmount, final Material receiveMaterial, final int receiveAmount, final Location location) {
        return new TradeOffer(this.application, offerId, offerMaterial, offerAmount, receiveMaterial, receiveAmount, location);
    }

    private TradeRequest createTradeRequest(final String offerId, final Material offerMaterial, final int offerAmount, final Material receiveMaterial, final int receiveAmount, final Location location) {
        return new TradeRequest(this.application, offerId, offerMaterial, offerAmount, receiveMaterial, receiveAmount, location);
    }
}
