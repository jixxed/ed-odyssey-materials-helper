package nl.jixxed.eliteodysseymaterials.templates.odyssey.trade;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerPropertyBase;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.domain.TradeSearch;
import nl.jixxed.eliteodysseymaterials.domain.TradeSpec;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.helper.CryptoHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.TradeSearchEvent;
import nl.jixxed.eliteodysseymaterials.service.event.trade.*;
import nl.jixxed.eliteodysseymaterials.trade.message.common.Info;
import nl.jixxed.eliteodysseymaterials.trade.message.common.Item;
import nl.jixxed.eliteodysseymaterials.trade.message.common.Offer;
import nl.jixxed.eliteodysseymaterials.trade.message.common.XBid;

import java.util.*;

@Slf4j
class OdysseyTradePagination extends VBox {
    private static final String SHA_KEY = "xt23s778RHY";
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final int PAGE_SIZE = 5;
    @Getter
    private final SimpleListProperty<TradeSpec> trades = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>()));
    private final SimpleListProperty<OdysseyTrade> visibleTrades = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>()));
    private TradeSearch tradeSearch = new TradeSearch("", TradeSort.NAME_OFFER, TradeShow.ALL);
    private final TradeType tradeType;
    private VBox visibleTradeList;
    private Label tradePaginationTitleLabel;
    private HBox tradePaginationTitle;
    private final Pagination pagination;
    private Button refreshButton;

    private final List<EventListener<?>> eventListeners = new ArrayList<>();
    OdysseyTradePagination(final int pageCount, final int pageIndex, final TradeType tradeType) {
        this.pagination = new Pagination(pageCount, pageIndex);
        this.tradeType = tradeType;
        initComponents();
        initEventHandling();
    }

    private void initComponents() {
        this.getStyleClass().add("trade-pagination");
        this.refreshButton = ButtonBuilder.builder()
                .withStyleClass("trade-pagination-updates")
                .withOnAction(event -> this.reload())
                .withGraphic(ResizableImageViewBuilder.builder().withStyleClass("refresh-icon").withPreserveRatio(true).withImage("/images/other/sync.png").build())
                .build();
        this.tradePaginationTitleLabel = LabelBuilder.builder()
                .withStyleClass("trade-pagination-header")
                .withText(LocaleService.getStringBinding("tab.trade.pagination." + this.tradeType))
                .build();
        this.updateProperty().addListener((observable, oldValue, newValue) -> this.refreshButton.textProperty().bind(LocaleService.getStringBinding("tab.trade.pagination.updates", newValue.intValue())));

        this.tradePaginationTitle = BoxBuilder.builder().withNodes(this.tradePaginationTitleLabel, this.refreshButton).buildHBox();
        this.tradePaginationTitleLabel.visibleProperty().bind(this.visibleProperty());
        this.tradePaginationTitle.visibleProperty().bind(this.visibleProperty());
        this.pagination.visibleProperty().bind(this.visibleTrades.sizeProperty().greaterThan(0));
        this.visibleProperty().bind(this.trades.sizeProperty().greaterThan(0).and(this.isConnectedProperty()));
        this.refreshButton.visibleProperty().bind(this.updateProperty().greaterThan(0));
        this.trades.sizeProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue.equals(0) && newValue.intValue() > 0) {
                reload();
            }
        });
        this.visibleTradeList = BoxBuilder.builder()
                .withStyleClass("trade-list")
                .buildVBox();
        this.pagination.setPageFactory(this::createTradePage);
        this.getChildren().addAll(this.tradePaginationTitle, this.pagination);
        VBox.setVgrow(this.pagination, Priority.ALWAYS);
        VBox.setVgrow(this.tradePaginationTitle, Priority.ALWAYS);
    }

    @SuppressWarnings({"java:S1192", "java:S3776"})
    private void initEventHandling() {
        this.eventListeners.add(EventService.addListener(this, TradeSearchEvent.class, tradeSearchEvent -> {
            this.tradeSearch = tradeSearchEvent.getTradeSearch();
            reload();
        }));
        this.eventListeners.add(EventService.addListener(this, ConnectionWebSocketEvent.class, connectionWebSocketEvent -> {
            final boolean connected = connectionWebSocketEvent.isConnected();
            this.setConnected(connected);

            if (!connected) {
                this.trades.forEach(TradeSpec::onDestroy);
                this.trades.clear();
                this.visibleTrades.forEach(trade -> trade.getTradeSpec().onDestroy());
                this.visibleTrades.forEach(OdysseyTrade::onDestroy);
                this.visibleTrades.clear();
            }
        }));
        this.eventListeners.add(EventService.addListener(this, DropOffersWebSocketEvent.class, dropOffersWebSocketEvent -> {
            this.updateProperty().increase();
            final List<String> offers = dropOffersWebSocketEvent.getDropOffersMessage().getOfferIds();
            this.trades.stream().filter(trade -> offers.contains(trade.getOfferId())).forEach(TradeSpec::onDestroy);
            this.trades.removeIf(trade -> offers.contains(trade.getOfferId()));
            if (TradeType.OFFER.equals(this.tradeType)) {
                this.visibleTrades.stream().filter(trade -> offers.contains(trade.getOfferId())).forEach(OdysseyTrade::onDestroy);
                final boolean visibleTradesRemoved = this.visibleTrades.removeIf(trade -> offers.contains(trade.getOfferId()));
                if (visibleTradesRemoved) {
                    reload();
                }
            }
        }));
        if (TradeType.REQUEST.equals(this.tradeType)) {
            this.eventListeners.add(EventService.addListener(this, GetOffersWebSocketEvent.class, getOffersWebSocketEvent -> {
                this.updateProperty().increase();
                final List<Offer> offers = getOffersWebSocketEvent.getGetOffersMessage().getOffers();
                final List<TradeSpec> allTradeOffers = offers.stream()
                        .map(this::mapOffer)
                        .filter(Objects::nonNull)
                        .toList();
                this.trades.clear();
                this.trades.addAll(allTradeOffers.stream().filter(tradeSpec -> tradeSpec.getTradeType().equals(TradeType.REQUEST)).toList());
                reload();
            }));
        }
        this.eventListeners.add(EventService.addListener(this, PublishOfferWebSocketEvent.class, publishOfferWebSocketEvent -> {
            try {
                final Offer offer = publishOfferWebSocketEvent.getPublishOfferMessage().getOffer();
                final TradeType type = (Objects.equals(offer.getToken(), CryptoHelper.sha256("xt23s778RHY", APPLICATION_STATE.getMarketPlaceToken()))) ? TradeType.OFFER : TradeType.REQUEST;
                if (this.tradeType.equals(type)) {
                    this.updateProperty().increase();
                    final TradeSpec tradeSpec = mapOffer(offer);
                    this.trades.add(tradeSpec);
                    if (TradeType.OFFER.equals(type)) {
                        this.reload();
                    }
                }
            } catch (final IllegalArgumentException ex) {
                log.error("failed to fetch items for offer", ex);
            }
        }));
        if (TradeType.OFFER.equals(this.tradeType)) {
            this.eventListeners.add(EventService.addListener(this, EnlistWebSocketEvent.class, enlistWebSocketEvent -> {
                final List<Offer> offers = enlistWebSocketEvent.getEnlistMessage().getOffers();
                final List<TradeSpec> myTradeOffers = offers.stream()
                        .map(this::mapOffer)
                        .filter(Objects::nonNull)
                        .toList();
                this.trades.addAll(myTradeOffers);
                this.reload();
            }));
        }
        if (TradeType.REQUEST.equals(this.tradeType)) {
            this.eventListeners.add(EventService.addListener(this, OnlineOffersWebSocketEvent.class, onlineOffersWebSocketEvent -> {
                final List<Offer> offers = onlineOffersWebSocketEvent.getOnlineOffersMessage().getOffers();
                if (offers != null) {
                    for (final Offer offer : offers) {
                        if (!Objects.equals(offer.getToken(), CryptoHelper.sha256(SHA_KEY, APPLICATION_STATE.getMarketPlaceToken()))) {
                            this.updateProperty().increase();
                            try {
                                final TradeSpec tradeSpec = this.visibleTrades.stream()
                                        .filter(trade -> Objects.equals(trade.getTradeSpec().getOfferId(), offer.getOfferId()))
                                        .findFirst()
                                        .map(OdysseyTrade::getTradeSpec)
                                        .orElseGet(() -> mapOffer(offer));
                                if (tradeSpec != null && tradeSpec.getTradeType().equals(TradeType.REQUEST)) {
                                    this.trades.removeIf(trade -> trade.getOfferId().equals(offer.getOfferId()));
                                    this.trades.add(tradeSpec);
                                }
                            } catch (final IllegalArgumentException ex) {
                                log.error("failed to fetch items for offer", ex);
                            }
                        }
                    }
                }
            }));
        }
        if (TradeType.REQUEST.equals(this.tradeType)) {
            this.eventListeners.add(EventService.addListener(this, OfflineOffersWebSocketEvent.class, offlineOffersWebSocketEvent -> {
                this.updateProperty().increase();
                final List<String> offers = offlineOffersWebSocketEvent.getOfflineOffersMessage().getOfferIds();
                this.trades.stream()
                        .filter(trade -> offers.contains(trade.getOfferId()))
                        .filter(tradeSpec -> this.visibleTrades.stream().noneMatch(trade -> tradeSpec.getOfferId().equals(trade.getTradeSpec().getOfferId())))
                        .forEach(TradeSpec::onDestroy);
                this.trades.removeIf(trade -> offers.contains(trade.getOfferId()));
            }));
        }

    }

    private void refresh() {

        this.visibleTradeList.getChildren().clear();
        this.visibleTradeList.getChildren().addAll(this.visibleTrades.getValue());
    }

    private void reload() {
        this.updateProperty().set(0);
        this.pagination.setPageCount(Pagination.INDETERMINATE);
        createTradePageData(0);
        this.pagination.setCurrentPageIndex(-1);
        if (!this.visibleTrades.isEmpty()) {
            this.pagination.prefHeightProperty().bind(this.visibleTrades.get(0).heightProperty().add(4).multiply(this.visibleTrades.size()).add(50));
        } else {
            this.pagination.prefHeightProperty().unbind();
            this.pagination.prefHeightProperty().set(0);
        }
    }

    private Node createTradePage(final Integer pageIndex) {
        createTradePageData(pageIndex);

        this.visibleTradeList = BoxBuilder.builder()
                .withStyleClass("trade-list")
                .withNodes(this.visibleTrades.toArray(OdysseyTrade[]::new))
                .buildVBox();
        return this.visibleTradeList;
    }

    private void createTradePageData(final Integer pageIndex) {
        this.visibleTrades.forEach(OdysseyTrade::onDestroy);
        this.visibleTrades.stream().filter(trade -> TradeStatus.OFFLINE.equals(trade.getTradeSpec().getTradeStatus())).forEach(trade -> {
            if (TradeStatus.OFFLINE.equals(trade.getTradeSpec().getTradeStatus())) {
                trade.getTradeSpec().onDestroy();
            }
        });
        this.visibleTrades.clear();
        final List<TradeSpec> filteredTradeSpecs = this.trades.stream()
                .filter(TradeShow.getFilter(this.tradeSearch))
                .filter(tradeSpec -> LocaleService.getLocalizedStringForCurrentLocale(tradeSpec.getOfferOdysseyMaterial().getLocalizationKey()).toLowerCase(LocaleService.getCurrentLocale()).contains(this.tradeSearch.getQuery().toLowerCase(LocaleService.getCurrentLocale()))
                        || LocaleService.getLocalizedStringForCurrentLocale(tradeSpec.getReceiveOdysseyMaterial().getLocalizationKey()).toLowerCase(LocaleService.getCurrentLocale()).contains(this.tradeSearch.getQuery().toLowerCase(LocaleService.getCurrentLocale())))
                .sorted(TradeSort.getSort(this.tradeSearch))
                .toList();
        final int toIndex = Math.min(filteredTradeSpecs.size(), (pageIndex * PAGE_SIZE + PAGE_SIZE));
        if (toIndex > 0) {
            final int fromIndex = pageIndex * PAGE_SIZE <= toIndex ? Math.max(0, pageIndex * PAGE_SIZE) : Math.max(0, (pageIndex - 1) * PAGE_SIZE);
            if (!filteredTradeSpecs.isEmpty()) {
                final List<OdysseyTrade> tradesToShow = filteredTradeSpecs.subList(fromIndex, toIndex).stream().map(tradeSpec -> {
                    if (tradeSpec.getTradeType().equals(TradeType.OFFER)) {
                        final OdysseyTradeOffer tradeOffer = new OdysseyTradeOffer(tradeSpec);
                        tradeOffer.setCallback(Optional.of(spec -> refresh()));
                        return tradeOffer;
                    } else {
                        final OdysseyTradeRequest tradeRequest = new OdysseyTradeRequest(tradeSpec);
                        tradeRequest.setCallback(Optional.of(spec -> refresh()));
                        return tradeRequest;
                    }
                }).toList();
                this.visibleTrades.addAll(tradesToShow);
            }
        }
        this.pagination.setPageCount(Math.max((int) Math.ceil(filteredTradeSpecs.size() / (double) PAGE_SIZE), 1));

    }

    private TradeSpec mapOffer(final Offer offer) {
        try {
            final Item item = offer.getItems().stream().findFirst().orElseThrow(IllegalArgumentException::new);
            final Info info = offer.getInfo();
            final OdysseyMaterial offerOdysseyMaterial = OdysseyMaterial.subtypeForName(item.getSid().substring(item.getSid().lastIndexOf(".") + 1));
            final OdysseyMaterial receiveOdysseyMaterial = OdysseyMaterial.subtypeForName(item.getDid().substring(item.getDid().lastIndexOf(".") + 1));
            final StarSystem starSystem = new StarSystem(info.getLocation(), info.getX(), info.getY(), info.getZ());
            final Optional<XBid> firstBid = offer.getXbids().stream().sorted(Comparator.comparingLong(XBid::getTimestamp)).findFirst();
            final Optional<XBid> acceptedBid = offer.getXbids().stream().sorted(Comparator.comparingLong(XBid::getTimestamp)).filter(XBid::getAccepted).findFirst();

            if (Objects.equals(offer.getToken(), CryptoHelper.sha256("xt23s778RHY", APPLICATION_STATE.getMarketPlaceToken()))) {
                return createTradeOffer(offer.getOfferId(), offerOdysseyMaterial, item.getSupply(), receiveOdysseyMaterial, item.getDemand(), starSystem, acceptedBid.map(XBid::getTokenhash).orElse(firstBid.map(XBid::getTokenhash).orElse("")), acceptedBid.map(XBid::getTokenhash).orElse(""), offer.getToken(), TradeStatus.valueOf(offer.getState()));
            } else {
                return createTradeRequest(offer.getOfferId(), offerOdysseyMaterial, item.getSupply(), receiveOdysseyMaterial, item.getDemand(), starSystem, acceptedBid.map(XBid::getTokenhash).orElse(firstBid.map(XBid::getTokenhash).orElse("")), acceptedBid.map(XBid::getTokenhash).orElse(""), offer.getToken(), TradeStatus.valueOf(offer.getState()));
            }
        } catch (final IllegalArgumentException ex) {
            log.error("failed to fetch items for offer");
        }
        return null;
    }

    @SuppressWarnings("java:S107")
    private TradeSpec createTradeOffer(final String offerId, final OdysseyMaterial offerOdysseyMaterial, final int offerAmount, final OdysseyMaterial receiveOdysseyMaterial, final int receiveAmount, final StarSystem starSystem, final String bid, final String acceptedTokenHash, final String ownerHash, final TradeStatus tradeStatus) {
        return new TradeSpec(offerId, offerOdysseyMaterial, offerAmount, receiveOdysseyMaterial, receiveAmount, starSystem, TradeType.OFFER, tradeStatus, bid, acceptedTokenHash, ownerHash);
    }

    @SuppressWarnings("java:S107")
    private TradeSpec createTradeRequest(final String offerId, final OdysseyMaterial offerOdysseyMaterial, final int offerAmount, final OdysseyMaterial receiveOdysseyMaterial, final int receiveAmount, final StarSystem starSystem, final String bid, final String acceptedTokenHash, final String ownerHash, final TradeStatus tradeStatus) {
        return new TradeSpec(offerId, receiveOdysseyMaterial, receiveAmount, offerOdysseyMaterial, offerAmount, starSystem, TradeType.REQUEST, tradeStatus, bid, acceptedTokenHash, ownerHash);
    }

    private UpdatesProperty updateProperty() {
        if (this.updates == null) {
            this.updates = new UpdatesProperty();
        }
        return this.updates;
    }

    private UpdatesProperty updates;

    private class UpdatesProperty extends IntegerPropertyBase {

        void increase() {
            super.set(get() + 1);
        }

        @Override
        public Object getBean() {
            return OdysseyTradePagination.this;
        }

        @Override
        public String getName() {
            return "updates";
        }

    }


    private BooleanProperty isConnected;

    private final void setConnected(final boolean value) {
        isConnectedProperty().set(value);
    }

    private final BooleanProperty isConnectedProperty() {
        if (this.isConnected == null) {
            this.isConnected = new SimpleBooleanProperty(false) {

                @Override
                public Object getBean() {
                    return OdysseyTradePagination.this;
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
