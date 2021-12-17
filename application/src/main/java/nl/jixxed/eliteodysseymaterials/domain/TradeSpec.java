package nl.jixxed.eliteodysseymaterials.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.Material;
import nl.jixxed.eliteodysseymaterials.enums.TradeStatus;
import nl.jixxed.eliteodysseymaterials.enums.TradeType;
import nl.jixxed.eliteodysseymaterials.helper.CryptoHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.trade.*;
import nl.jixxed.eliteodysseymaterials.trade.MarketPlaceClient;
import nl.jixxed.eliteodysseymaterials.trade.message.common.Offer;
import nl.jixxed.eliteodysseymaterials.trade.message.common.XBid;
import nl.jixxed.eliteodysseymaterials.trade.message.common.XMessage;

import java.util.*;
import java.util.function.Consumer;

@Getter
@ToString
@Slf4j
public class TradeSpec {
    private final MarketPlaceClient marketPlaceClient = MarketPlaceClient.getInstance();
    public static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    public static final String SHA_KEY = "xt23s778RHY";
    public static final String NOTIFICATION_TRADE_TITLE_LOCALIZATION_KEY = "notification.trade.title";
    private final String offerId;
    private final Material offerMaterial;
    private final int offerAmount;
    private final Material receiveMaterial;
    private final int receiveAmount;
    private final StarSystem starSystem;
    private final TradeType tradeType;
    private TradeStatus tradeStatus;
    private String bid;
    private String acceptedTokenHash;
    private final String ownerHash;
    private final List<EventListener<?>> listeners = new ArrayList<>();
    private String chat = "";
    @Setter
    private Optional<Consumer<TradeSpec>> callback = Optional.empty();

    @SuppressWarnings("java:S107")
    public TradeSpec(final String offerId, final Material offerMaterial, final int offerAmount, final Material receiveMaterial, final int receiveAmount, final StarSystem starSystem, final TradeType tradeType, final TradeStatus tradeStatus, final String bid, final String acceptedTokenHash, final String ownerHash) {
        this.offerId = offerId;
        this.offerMaterial = offerMaterial;
        this.offerAmount = offerAmount;
        this.receiveMaterial = receiveMaterial;
        this.receiveAmount = receiveAmount;
        this.starSystem = starSystem;
        this.tradeType = tradeType;
        this.bid = bid;
        this.acceptedTokenHash = acceptedTokenHash;
        this.ownerHash = ownerHash;
        this.tradeStatus = tradeStatus;

        initEventHandling();
    }

    private TradeStatus calculateTradeStatus() {
        if (isOwnedByMe()) {
            if (isAccepted()) {
                return TradeStatus.ACCEPTED;
            } else if (hasBid()) {
                return TradeStatus.PUSHED;
            } else {
                return TradeStatus.AVAILABLE;
            }
        } else {
            if (isBidAcceptedFromMe()) {
                return TradeStatus.ACCEPTED;
            } else if (isAccepted()) {
                return TradeStatus.ACCEPTED;
            } else if (isBidFromMe()) {
                return TradeStatus.PUSHED;
            } else if (hasBid()) {
                return TradeStatus.PUSHED;
            } else {
                return TradeStatus.AVAILABLE;
            }
        }
    }

    @SuppressWarnings("java:S3776")
    private void initEventHandling() {
        this.listeners.add(EventService.addListener(this, 1, XBidPullWebSocketEvent.class, xBidPullWebSocketEvent -> {
            final Offer bidOffer = xBidPullWebSocketEvent.getXBidPullMessage().getOffer();
            if (getOfferId().equals(bidOffer.getOfferId())) {
                if (isBidFromMe()) {
                    this.tradeStatus = TradeStatus.PULLED;
                    NotificationService.showInformation(LocaleService.getLocalizedStringForCurrentLocale(NOTIFICATION_TRADE_TITLE_LOCALIZATION_KEY), LocaleService.getLocalizedStringForCurrentLocale("notification.trade.your.bid.was.pulled"));
                    final TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            if (TradeStatus.PULLED.equals(TradeSpec.this.tradeStatus)) {
                                TradeSpec.this.bid = "";
                                TradeSpec.this.tradeStatus = TradeStatus.AVAILABLE;
                                TradeSpec.this.callback.ifPresent(c -> c.accept(TradeSpec.this));
                            }
                        }
                    };
                    final Timer timer = new Timer("Timer pulled offer reset to available");

                    final long delay = 60_000L;
                    timer.schedule(task, delay);
                } else {
                    if (isOwnedByMe()) {
                        NotificationService.showInformation(LocaleService.getLocalizedStringForCurrentLocale(NOTIFICATION_TRADE_TITLE_LOCALIZATION_KEY), LocaleService.getLocalizedStringForCurrentLocale("notification.trade.a.bid.was.pulled"));
                    }
                    this.tradeStatus = TradeStatus.AVAILABLE;
                    this.bid = "";
                }
                this.callback.ifPresent(c -> c.accept(this));
            }
        }));

        this.listeners.add(EventService.addListener(this, 1, XBidPushWebSocketEvent.class, xBidPushWebSocketEvent -> {
            final Offer bidOffer = xBidPushWebSocketEvent.getXBidPushMessage().getOffer();
            if (getOfferId().equals(bidOffer.getOfferId()) && !hasBid()) {
                this.bid = bidOffer.getXbids().stream().sorted(Comparator.comparingLong(XBid::getTimestamp)).map(XBid::getTokenhash).findFirst().orElse("");
                this.tradeStatus = TradeStatus.PUSHED;
                if (isBidFromMe()) {
                    NotificationService.showInformation(LocaleService.getLocalizedStringForCurrentLocale(NOTIFICATION_TRADE_TITLE_LOCALIZATION_KEY), LocaleService.getLocalizedStringForCurrentLocale("notification.trade.your.bid.is.placed"));
                } else if (isOwnedByMe()) {
                    NotificationService.showInformation(LocaleService.getLocalizedStringForCurrentLocale(NOTIFICATION_TRADE_TITLE_LOCALIZATION_KEY), LocaleService.getLocalizedStringForCurrentLocale("notification.trade.you.received.a.bid.on.an.offer"));
                    final TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            if (TradeStatus.PUSHED.equals(TradeSpec.this.tradeStatus)) {
                                TradeSpec.this.marketPlaceClient.bidAccept(getOfferId(), getBid(), false);
                            }
                        }
                    };
                    final Timer timer = new Timer("Timer auto reject offer 60s");

                    final long delay = 60_000L;
                    timer.schedule(task, delay);
                }
                this.callback.ifPresent(c -> c.accept(this));
            }
        }));

        this.listeners.add(EventService.addListener(this, 1, XBidAcceptWebSocketEvent.class, xBidAcceptWebSocketEvent -> {
            final Offer bidOffer = xBidAcceptWebSocketEvent.getXBidAcceptMessage().getOffer();
            final Optional<XBid> acceptedBid = bidOffer.getXbids().stream().sorted(Comparator.comparingLong(XBid::getTimestamp)).filter(XBid::getAccepted).findFirst();
            final boolean accepted = acceptedBid.map(XBid::getAccepted).orElse(Boolean.FALSE);
            if (getOfferId().equals(bidOffer.getOfferId())) {
                if (accepted) {
                    this.tradeStatus = TradeStatus.ACCEPTED;
                    this.acceptedTokenHash = acceptedBid.map(XBid::getTokenhash).orElse("");
                    if (isOwnedByMe()) {
                        NotificationService.showInformation(LocaleService.getLocalizedStringForCurrentLocale(NOTIFICATION_TRADE_TITLE_LOCALIZATION_KEY), LocaleService.getLocalizedStringForCurrentLocale("notification.trade.you.accepted.a.bid"));
                    } else if (isBidFromMe()) {
                        NotificationService.showInformation(LocaleService.getLocalizedStringForCurrentLocale(NOTIFICATION_TRADE_TITLE_LOCALIZATION_KEY), LocaleService.getLocalizedStringForCurrentLocale("notification.trade.your.bid.has.been.accepted"));
                    }
                } else if (isBidFromMe()) {
                    this.tradeStatus = TradeStatus.REJECTED;
                    this.bid = "";
                    NotificationService.showInformation(LocaleService.getLocalizedStringForCurrentLocale(NOTIFICATION_TRADE_TITLE_LOCALIZATION_KEY), LocaleService.getLocalizedStringForCurrentLocale("notification.trade.your.bid.has.been.rejected"));
                    final TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            if (TradeStatus.REJECTED.equals(TradeSpec.this.tradeStatus)) {
                                TradeSpec.this.tradeStatus = TradeStatus.AVAILABLE;
                                TradeSpec.this.callback.ifPresent(c -> c.accept(TradeSpec.this));
                            }
                        }
                    };
                    final Timer timer = new Timer("Timer rejected offer reset to available");

                    final long delay = 60_000L;
                    timer.schedule(task, delay);
                } else {
                    this.tradeStatus = TradeStatus.AVAILABLE;
                    this.bid = "";
                }
                this.callback.ifPresent(c -> c.accept(this));
            }
        }));

        this.listeners.add(EventService.addListener(this, 1, DropOffersWebSocketEvent.class, dropOffersWebSocketEvent -> {
            final List<String> offers = dropOffersWebSocketEvent.getDropOffersMessage().getOfferIds();
            if (offers.contains(this.getOfferId())) {
                this.tradeStatus = TradeStatus.REMOVED;
                this.callback.ifPresent(c -> c.accept(this));
            }
        }));

        this.listeners.add(EventService.addListener(this, 1, XMessageWebSocketEvent.class, xMessageWebSocketEvent -> {
            final XMessage message = xMessageWebSocketEvent.getXMessageMessage().getMessage();
            if (message.getOfferId().equals(this.offerId)) {
                this.chat += message.getInfo().getNickname() + "(" + message.getInfo().getLocation() + "): " + message.getText() + "\n";
            }
        }));

        this.listeners.add(EventService.addListener(this, 1, OnlineOffersWebSocketEvent.class, onlineOffersWebSocketEvent -> {
            final List<Offer> offers = onlineOffersWebSocketEvent.getOnlineOffersMessage().getOffers();
            if (offers != null && offers.stream().anyMatch(offer -> offer.getOfferId().equals(getOfferId()))) {
                this.tradeStatus = calculateTradeStatus();
                this.callback.ifPresent(c -> c.accept(this));
            }
        }));

        this.listeners.add(EventService.addListener(this, 1, OfflineOffersWebSocketEvent.class, offlineOffersWebSocketEvent -> {
            final List<String> offers = offlineOffersWebSocketEvent.getOfflineOffersMessage().getOfferIds();
            if (offers.contains(getOfferId())) {
                this.tradeStatus = TradeStatus.OFFLINE;
                this.callback.ifPresent(c -> c.accept(this));
            }
        }));

    }

    public boolean hasBid() {
        return !"".equals(this.bid);
    }

    private boolean isOwnedByMe() {
        return Objects.equals(this.ownerHash, CryptoHelper.sha256(SHA_KEY, APPLICATION_STATE.getMarketPlaceToken()));
    }

    public boolean isBidFromMe() {
        return Objects.equals(this.bid, CryptoHelper.sha256(SHA_KEY, APPLICATION_STATE.getMarketPlaceToken()));
    }

    private boolean isBidAcceptedFromMe() {
        return Objects.equals(this.acceptedTokenHash, CryptoHelper.sha256(SHA_KEY, APPLICATION_STATE.getMarketPlaceToken()));
    }

    private boolean isAccepted() {
        return !"".equals(this.acceptedTokenHash);
    }

    public void onDestroy() {
        this.listeners.forEach(EventService::removeListener);
        setCallback(Optional.empty());
    }

}
