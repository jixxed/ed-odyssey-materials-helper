package nl.jixxed.eliteodysseymaterials.templates.horizons.commodities;

import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.EdAwesomeIconViewPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.CommoditiesSearch;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.service.*;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsCommoditiesSearchEvent;
import nl.jixxed.eliteodysseymaterials.service.event.MarketUpdatedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.EdAwesomeIconViewPane;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIconView;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.math.BigInteger;
import java.util.Arrays;

@Slf4j
public class HorizonsCommodityCard extends DestroyableStackPane implements DestroyableEventTemplate {
    private CommoditiesSearch commoditiesSearch;
    @Getter
    private Integer fleetcarrierAmount;
    @Getter
    private Integer shipAmount;
    private DestroyableLabel leftAmountLabel;
    private DestroyableLabel rightAmountLabel;
    private DestroyableResizableImageView bracket1SellImage;
    private DestroyableResizableImageView bracket3SellImage;
    private DestroyableResizableImageView bracket1BuyImage;
    private DestroyableResizableImageView bracket3BuyImage;
    @Getter
    private final Commodity commodity;

    private DestroyableLabel stationBuy;
    private DestroyableLabel stationSell;
    private DestroyableHBox market;
    private DestroyableLabel stationBuyArrow;
    private DestroyableLabel stationSellArrow;

    HorizonsCommodityCard(final Commodity commodity) {
        this.commodity = commodity;
        final HorizonsCommoditiesSort materialSort = HorizonsCommoditiesSort.valueOf(PreferencesService.getPreference("search.commodities.sort", "ALPHABETICAL"));
        final HorizonsCommoditiesShow filter = HorizonsCommoditiesShow.valueOf(PreferencesService.getPreference("search.commodities.filter", "ALL"));
        commoditiesSearch = new CommoditiesSearch("", materialSort, filter);
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("commodity-card");
        if (GameVersion.LIVE.equals(this.commodity.getGameVersion())) {
            this.pseudoClassStateChanged(PseudoClass.getPseudoClass("live"), true);
        }
        EdAwesomeIconViewPane typeImage = EdAwesomeIconViewPaneBuilder.builder()
                .withStyleClass("commodity-image")
                .withIcons(Arrays.stream(this.commodity.getCommodityType().getIcons()).map(icon -> new EdAwesomeIconView(icon, "2.5em")).toArray(EdAwesomeIconView[]::new))
                .build();
//        typeImage.getCssMetaData().forEach(System.out::println);
//        typeImage.getChildren().getFirst().getCssMetaData().forEach(System.out::println);
//        DestroyableResizableImageView typeImage = ResizableImageViewBuilder.builder()
//                .withStyleClass("image")
//                .withImage(this.commodity.getCommodityType().getImagePath())
//                .build();
        DestroyableLabel nameLabel = LabelBuilder.builder()
                .withStyleClass("name")
                .withText(this.commodity.getLocalizationKey())
                .build();
        DestroyableLabel categoryLabel = LabelBuilder.builder()
                .withStyleClass("category")
                .withText(this.commodity.getCommodityType().getLocalizationKey())
                .build();
        this.leftAmountLabel = LabelBuilder.builder()
                .withStyleClass("fleetcarrier-amount")
                .build();
        this.rightAmountLabel = LabelBuilder.builder()
                .withStyleClass("ship-amount")
                .build();
        DestroyableResizableImageView fleetCarrierImage = ResizableImageViewBuilder.builder()
                .withStyleClass("image")
                .withImage("/images/material/fleetcarrier.png")
                .build();
        DestroyableResizableImageView shipImage = ResizableImageViewBuilder.builder()
                .withStyleClass("image")
                .withImage("/images/material/ship.png")
                .build();
        DestroyableResizableImageView coriolisImage = ResizableImageViewBuilder.builder()
                .withStyleClass("image")
                .withImage("/images/material/coriolis.png")
                .build();
        this.bracket1SellImage = ResizableImageViewBuilder.builder()
                .withStyleClasses("image")
                .withImage("/images/material/stock/bracket1.png")
                .build();
        this.bracket3SellImage = ResizableImageViewBuilder.builder()
                .withStyleClasses("image")
                .withImage("/images/material/stock/bracket3.png")
                .build();
        this.bracket1BuyImage = ResizableImageViewBuilder.builder()
                .withStyleClasses("image")
                .withImage("/images/material/stock/bracket1.png")
                .build();
        this.bracket3BuyImage = ResizableImageViewBuilder.builder()
                .withStyleClasses("image")
                .withImage("/images/material/stock/bracket3.png")
                .build();
        this.bracket1SellImage.addBinding(this.bracket1SellImage.managedProperty(), this.bracket1SellImage.visibleProperty());
        this.bracket3SellImage.addBinding(this.bracket3SellImage.managedProperty(), this.bracket3SellImage.visibleProperty());
        this.bracket1BuyImage.addBinding(this.bracket1BuyImage.managedProperty(), this.bracket1BuyImage.visibleProperty());
        this.bracket3BuyImage.addBinding(this.bracket3BuyImage.managedProperty(), this.bracket3BuyImage.visibleProperty());
        this.stationBuy = LabelBuilder.builder()
                .withStyleClass("station-amount-buy")
                .withNonLocalizedText("0")
                .build();
        this.stationSell = LabelBuilder.builder()
                .withStyleClass("station-amount-sell")
                .withNonLocalizedText("0")
                .build();
        this.stationBuyArrow = LabelBuilder.builder()
                .withStyleClass("station-arrow")
                .withNonLocalizedText("→")
                .build();
        this.stationSellArrow = LabelBuilder.builder()
                .withStyleClass("station-arrow")
                .withNonLocalizedText("→")
                .build();
        final DestroyableHBox buy = BoxBuilder.builder()
                .withStyleClass("station-box")
                .withNodes(new GrowingRegion(), bracket1BuyImage, bracket3BuyImage, stationBuy)
                .buildHBox();
        final DestroyableHBox sell = BoxBuilder.builder()
                .withStyleClass("station-box")
                .withNodes(stationSell, bracket1SellImage, bracket3SellImage, new GrowingRegion())
                .buildHBox();
        this.market = BoxBuilder.builder()
                .withStyleClass("market")
                .withNodes(new GrowingRegion(), buy, stationBuyArrow, coriolisImage, stationSellArrow, sell, new GrowingRegion())
                .buildHBox();
        DestroyableHBox leftHBox = BoxBuilder.builder()
                .withNodes(fleetCarrierImage, this.leftAmountLabel)
                .withStyleClass("ingredient-quantity-section")
                .buildHBox();
        DestroyableHBox rightHBox = BoxBuilder.builder()
                .withNodes(this.rightAmountLabel, shipImage)
                .withStyleClass("ingredient-quantity-section")
                .buildHBox();
        updateQuantity();
        HBox.setHgrow(this.leftAmountLabel, Priority.ALWAYS);
        this.leftAmountLabel.addBinding(this.leftAmountLabel.visibleProperty(), ApplicationState.getInstance().getFcMaterials());
        fleetCarrierImage.addBinding(fleetCarrierImage.visibleProperty(), ApplicationState.getInstance().getFcMaterials());
        DestroyableVBox nameAndCategory = BoxBuilder.builder()
                .withStyleClass("name-category")
                .withNodes(nameLabel, categoryLabel).buildVBox();
        DestroyableHBox firstLine = BoxBuilder.builder()
                .withStyleClass("name-line")
                .withNodes(typeImage, nameAndCategory).buildHBox();
        firstLine.addBinding(firstLine.spacingProperty(), ScalingHelper.getPixelDoubleBindingFromEm(0.5));
        DestroyableHBox secondLine = BoxBuilder.builder()
                .withStyleClass("quantity-line")
                .withNodes(leftHBox, new GrowingRegion(), rightHBox)
                .buildHBox();
        DestroyableVBox content = BoxBuilder.builder()
                .withStyleClass("content")
                .withNodes(firstLine, new GrowingRegion(), secondLine).buildVBox();
        if (this.commodity instanceof RareCommodity) {
            DestroyableResizableImageView rareImage = ResizableImageViewBuilder.builder()
                    .withStyleClass("rare-image")
                    .withImage("/images/material/stock/rare_right.png")
                    .build();
            var rareContainer = BoxBuilder.builder()
                    .withStyleClass("rare-container")
                    .withNodes(new GrowingRegion(), rareImage).buildHBox();
            this.getNodes().add(rareContainer);
        }
        this.getNodes().add(content);
        this.getNodes().add(this.market);
        StackPane.setAlignment(market, Pos.BOTTOM_CENTER);
        updateStyle();
        this.setOnMouseEntered(event -> log.info("Mouse entered"));
        MaterialService.addMaterialInfoPopOver(this, this.commodity, false);
        update();
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, HorizonsCommoditiesSearchEvent.class, horizonsCommoditiesSearchEvent -> {
            commoditiesSearch = horizonsCommoditiesSearchEvent.getSearch();
            this.update();
        }));

        register(EventService.addListener(true, this, StorageEvent.class, storageEvent -> {
            if (storageEvent.getStoragePool().equals(StoragePool.FLEETCARRIER) || storageEvent.getStoragePool().equals(StoragePool.SHIP)) {
                updateQuantity();
                this.update();
            }
        }));

        register(EventService.addListener(true, this, MarketUpdatedEvent.class, marketUpdatedEvent -> {
            updateQuantity();
            updateStyle();
        }));
    }

    private void update() {
        boolean visible = HorizonsCommoditiesShow.getFilter(commoditiesSearch).test(this) &&
                (commoditiesSearch.getQuery().isBlank()
                        || LocaleService.getLocalizedStringForCurrentLocale(commodity.getLocalizationKey()).toLowerCase(LocaleService.getCurrentLocale()).contains(commoditiesSearch.getQuery().toLowerCase(LocaleService.getCurrentLocale()))
                        || LocaleService.getLocalizedStringForCurrentLocale(commodity.getCommodityType().getLocalizationKey()).toLowerCase(LocaleService.getCurrentLocale()).contains(commoditiesSearch.getQuery().toLowerCase(LocaleService.getCurrentLocale())));
        this.setVisible(visible);
        this.setManaged(visible);
    }

    private void updateQuantity() {
        this.fleetcarrierAmount = (StorageService.getCommodityCount(this.commodity, StoragePool.FLEETCARRIER));
        this.shipAmount = (StorageService.getCommodityCount(this.commodity, StoragePool.SHIP));
        this.leftAmountLabel.setText(this.fleetcarrierAmount.toString());
        this.rightAmountLabel.setText(this.shipAmount.toString());
        this.stationBuy.setText(MarketService.getMarketItem(this.commodity).map(marketItem -> marketItem.demand().equals(BigInteger.ONE) ? "∞" : marketItem.demand().toString()).orElse("0"));
        this.stationSell.setText(MarketService.getMarketItem(this.commodity).map(marketItem -> marketItem.stock().toString()).orElse("0"));

    }

    private void updateStyle() {
        final boolean sells = MarketService.sells(commodity);
        final boolean buys = MarketService.buys(commodity);

        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("sells"), sells);
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("buys"), buys);

        MarketService.getMarketItem(this.commodity).ifPresentOrElse(marketItem -> {
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

        }, () -> this.market.setVisible(false));

    }
}
