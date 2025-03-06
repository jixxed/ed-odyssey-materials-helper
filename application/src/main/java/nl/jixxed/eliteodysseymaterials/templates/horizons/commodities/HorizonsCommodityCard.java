package nl.jixxed.eliteodysseymaterials.templates.horizons.commodities;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.Commodity;
import nl.jixxed.eliteodysseymaterials.enums.GameVersion;
import nl.jixxed.eliteodysseymaterials.enums.RareCommodity;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.MarketService;
import nl.jixxed.eliteodysseymaterials.service.MaterialService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.MarketUpdatedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class HorizonsCommodityCard extends StackPane implements Template {
    private DestroyableResizableImageView typeImage;
    private Label nameLabel;
    @Getter
    private Integer fleetcarrierAmount;
    @Getter
    private Integer shipAmount;
    private Label leftAmountLabel;
    private Label rightAmountLabel;
    private DestroyableResizableImageView leftImage;
    private DestroyableResizableImageView rightImage;
    private DestroyableResizableImageView rareImage;
    private DestroyableResizableImageView coriolisImage;
    private DestroyableResizableImageView bracket1SellImage;
    private DestroyableResizableImageView bracket3SellImage;
    private DestroyableResizableImageView bracket1BuyImage;
    private DestroyableResizableImageView bracket3BuyImage;
    private HBox leftHBox;
    private HBox rightHBox;
    private HBox firstLine;
    private VBox content;
    private Region region;
    private HBox secondLine;
    private Region region2;
    @Getter
    private final Commodity commodity;
    private final List<EventListener<?>> eventListeners = new ArrayList<>();
    private DestroyableLabel stationBuy;
    private DestroyableLabel stationSell;
    private HBox market;
    private DestroyableLabel stationBuyArrow;
    private DestroyableLabel stationSellArrow;

    HorizonsCommodityCard(final Commodity commodity) {
        this.commodity = commodity;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("horizons-commoditycard-pane");
        this.rareImage = ResizableImageViewBuilder.builder().withStyleClass("horizons-commoditycard-rare-image").withImage("/images/material/stock/rare.png").build();
        if (GameVersion.LIVE.equals(this.commodity.getGameVersion())) {
            this.getStyleClass().add("horizons-commoditycard-pane-live");
        }
        this.typeImage = ResizableImageViewBuilder.builder().withStyleClass("horizons-commoditycard-image").withImage(this.commodity.getCommodityType().getImagePath()).build();
        this.nameLabel = LabelBuilder.builder()
                .withStyleClass("horizons-commoditycard-name")
                .withText(LocaleService.getStringBinding(this.commodity.getLocalizationKey()))
                .build();
        this.leftAmountLabel = LabelBuilder.builder().withStyleClass("fleetcarrier-amount").build();
        this.rightAmountLabel = LabelBuilder.builder().withStyleClass("ship-amount").build();
        this.leftImage = ResizableImageViewBuilder.builder().withStyleClass("horizons-materialcard-image").withImage("/images/material/fleetcarrier.png").build();
        this.rightImage = ResizableImageViewBuilder.builder().withStyleClass("horizons-materialcard-image").withImage("/images/material/ship.png").build();
        this.coriolisImage = ResizableImageViewBuilder.builder().withStyleClass("horizons-materialcard-image").withImage("/images/material/coriolis.png").build();
        this.bracket1SellImage = ResizableImageViewBuilder.builder().withStyleClasses("horizons-materialcard-image").withImage("/images/material/stock/bracket1.png").build();
        this.bracket3SellImage = ResizableImageViewBuilder.builder().withStyleClasses("horizons-materialcard-image").withImage("/images/material/stock/bracket3.png").build();
        this.bracket1BuyImage = ResizableImageViewBuilder.builder().withStyleClasses("horizons-materialcard-image").withImage("/images/material/stock/bracket1.png").build();
        this.bracket3BuyImage = ResizableImageViewBuilder.builder().withStyleClasses("horizons-materialcard-image").withImage("/images/material/stock/bracket3.png").build();
        this.bracket1SellImage.managedProperty().bind(this.bracket1SellImage.visibleProperty());
        this.bracket3SellImage.managedProperty().bind(this.bracket3SellImage.visibleProperty());
        this.bracket1BuyImage.managedProperty().bind(this.bracket1BuyImage.visibleProperty());
        this.bracket3BuyImage.managedProperty().bind(this.bracket3BuyImage.visibleProperty());
        this.stationBuy = LabelBuilder.builder().withStyleClass("station-amount-buy").withNonLocalizedText("0").build();
        this.stationSell = LabelBuilder.builder().withStyleClass("station-amount-sell").withNonLocalizedText("0").build();
        this.stationBuyArrow = LabelBuilder.builder().withStyleClass("station-arrow").withNonLocalizedText("→").build();
        this.stationSellArrow = LabelBuilder.builder().withStyleClass("station-arrow").withNonLocalizedText("→").build();
        this.market = BoxBuilder.builder().withStyleClass("horizons-commoditycard-market-box").withNodes(new GrowingRegion(), BoxBuilder.builder().withStyleClass("station-box").withNodes(new GrowingRegion(), bracket1BuyImage, bracket3BuyImage, stationBuy).buildHBox(),  stationBuyArrow, coriolisImage, stationSellArrow, BoxBuilder.builder().withStyleClass("station-box").withNodes(stationSell, bracket1SellImage, bracket3SellImage,new GrowingRegion()).buildHBox(),new GrowingRegion()).buildHBox();
        this.leftHBox = BoxBuilder.builder().withNodes(this.leftImage, this.leftAmountLabel).withStyleClass("ingredient-quantity-section").buildHBox();
        this.rightHBox = BoxBuilder.builder().withNodes(this.rightAmountLabel, this.rightImage).withStyleClass("ingredient-quantity-section").buildHBox();
        updateQuantity();
        HBox.setHgrow(this.leftAmountLabel, Priority.ALWAYS);
        this.region = new Region();
        HBox.setHgrow(this.region, Priority.ALWAYS);
        this.region2 = new Region();
        VBox.setVgrow(this.region2, Priority.ALWAYS);
        this.leftAmountLabel.visibleProperty().bind(ApplicationState.getInstance().getFcMaterials());
        this.leftImage.visibleProperty().bind(ApplicationState.getInstance().getFcMaterials());
        this.firstLine = BoxBuilder.builder().withStyleClass("horizons-commoditycard-firstline").withNodes(this.typeImage, this.nameLabel).buildHBox();
        this.firstLine.spacingProperty().bind(ScalingHelper.getPixelDoubleBindingFromEm(0.5));
        this.secondLine = new HBox(this.leftHBox, region, this.rightHBox);
        content = BoxBuilder.builder().withStyleClass("horizons-commoditycard").withNodes(this.firstLine, this.region2, this.secondLine).buildVBox();
        if (this.commodity instanceof RareCommodity) {
            this.getChildren().add(this.rareImage);
        }
        this.getChildren().add(content);
        this.getChildren().add(this.market);
        StackPane.setAlignment(rareImage, Pos.TOP_RIGHT);
        StackPane.setAlignment(market, Pos.BOTTOM_CENTER);
        updateStyle();

        MaterialService.addMaterialInfoPopOver(this, this.commodity, false);

    }

    @Override
    public void initEventHandling() {
        this.eventListeners.add(EventService.addListener(true, this, StorageEvent.class, storageEvent -> {
            if (storageEvent.getStoragePool().equals(StoragePool.FLEETCARRIER) || storageEvent.getStoragePool().equals(StoragePool.SHIP)) {
                updateQuantity();
            }
        }));
        this.eventListeners.add(EventService.addListener(true, this, MarketUpdatedEvent.class, marketUpdatedEvent -> {
            updateQuantity();
            updateStyle();
        }));
    }

    private void updateQuantity() {
        this.fleetcarrierAmount = (StorageService.getCommodityCount(this.commodity, StoragePool.FLEETCARRIER));
        this.shipAmount = (StorageService.getCommodityCount(this.commodity, StoragePool.SHIP));
        this.leftAmountLabel.setText(this.fleetcarrierAmount.toString());
        this.rightAmountLabel.setText(this.shipAmount.toString());
        this.stationBuy.setText(MarketService.getMarketItem(this.commodity).map(marketItem -> marketItem.demand().equals(BigInteger.ONE) ? "∞" : marketItem.demand().toString()).orElse("0"));
        this.stationSell.setText(MarketService.getMarketItem(this.commodity).map(marketItem ->  marketItem.stock().toString()).orElse("0"));

    }

    private void updateStyle() {
        this.getStyleClass().removeAll("horizons-commoditycard-sells", "horizons-commoditycard-buys", "horizons-commoditycard-buys-sells");
        final boolean sells = MarketService.sells(commodity);
        final boolean buys = MarketService.buys(commodity);

        if (sells && buys) {
            this.getStyleClass().add("horizons-commoditycard-buys-sells");
        } else if (sells) {
            this.getStyleClass().add("horizons-commoditycard-sells");
        } else if (buys) {
            this.getStyleClass().add("horizons-commoditycard-buys");
        }
        MarketService.getMarketItem(this.commodity).ifPresentOrElse((marketItem)->{

            final boolean buyIsZero = stationBuy.getText().equals("0");
            final boolean sellIsZero = stationSell.getText().equals("0");
            this.market.setVisible(!buyIsZero || !sellIsZero);
            this.stationBuyArrow.setVisible(!buyIsZero);
            this.stationSellArrow.setVisible(!sellIsZero);
            this.bracket1BuyImage.setVisible(!buyIsZero && marketItem.demandBracket().equals(BigInteger.ONE));
            this.bracket3BuyImage.setVisible(!buyIsZero && marketItem.demandBracket().equals(BigInteger.valueOf(3)));
            this.bracket1SellImage.setVisible(!sellIsZero && marketItem.stockBracket().equals(BigInteger.ONE));
            this.bracket3SellImage.setVisible(!sellIsZero && marketItem.stockBracket().equals(BigInteger.valueOf(3)));

            stationBuy.setVisible(!buyIsZero);
            stationSell.setVisible(!sellIsZero);

        }, ()->{
            this.market.setVisible(false);
        });

    }
}
