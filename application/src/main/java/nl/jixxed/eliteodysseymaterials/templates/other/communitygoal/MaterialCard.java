/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.templates.other.communitygoal;

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
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.service.ImageService;
import nl.jixxed.eliteodysseymaterials.service.MarketService;
import nl.jixxed.eliteodysseymaterials.service.MaterialService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.AfterFontSizeSetEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.MarketUpdatedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.EdAwesomeIconViewPane;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIcon;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIconView;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
public class MaterialCard extends DestroyableStackPane implements DestroyableEventTemplate {
    @Getter
    private Integer fleetCarrierAmount;
    @Getter
    private Integer squadronCarrierAmount;
    @Getter
    private Integer shipAmount;
    @Getter
    private Integer backpackAmount;

    private DestroyableLabel fleetCarrierAmountLabel;
    private DestroyableLabel shipAmountLabel;
    private DestroyableLabel backpackLabel;
    private DestroyableLabel squadronCarrierAmountLabel;
    private EdAwesomeIconViewPane bracket1SellImage;
    private EdAwesomeIconViewPane bracket3SellImage;
    private EdAwesomeIconViewPane bracket1BuyImage;
    private EdAwesomeIconViewPane bracket3BuyImage;
    @Getter
    private final Material material;

    private DestroyableLabel stationBuy;
    private DestroyableLabel stationSell;
    private DestroyableHBox market;
    private DestroyableLabel stationBuyArrow;
    private DestroyableLabel stationSellArrow;
    private DestroyableResizableImageView rareImage;

    public MaterialCard(final Material material) {
        this.material = material;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("material-card");

        EdAwesomeIconViewPane typeImage = switch (material) {
            case Commodity commodity -> EdAwesomeIconViewPaneBuilder.builder()
                    .withStyleClass("commodity-image")
                    .withIcons(Arrays.stream(commodity.getCommodityType().getIcons()).map(icon -> new EdAwesomeIconView(icon, "2.5em")).toArray(EdAwesomeIconView[]::new))
                    .build();
            case HorizonsMaterial horizonsMaterial -> EdAwesomeIconViewPaneBuilder.builder()
                    .withStyleClass("commodity-image")
                    .withIcons(new EdAwesomeIconView(horizonsMaterial.getRarity().getIcon(), "2.5em"))
                    .build();
            case OdysseyMaterial odysseyMaterial -> EdAwesomeIconViewPaneBuilder.builder()
                    .withStyleClass("commodity-image")
                    .withIcons(Arrays.stream(odysseyMaterial.getMaterialIcons()).map(icon -> new EdAwesomeIconView(icon, "2.5em")).toArray(EdAwesomeIconView[]::new))
                    .build();
        };

        DestroyableLabel nameLabel = LabelBuilder.builder()
                .withStyleClass("name")
                .withText(this.material.getLocalizationKey())
                .build();
        DestroyableLabel categoryLabel = LabelBuilder.builder()
                .withStyleClass("category")
                .withText(this.material.getCategoryLocalizationKey())
                .build();
        this.fleetCarrierAmountLabel = LabelBuilder.builder()
                .withStyleClass("fleetcarrier-amount")
                .withVisibility(!(material instanceof EngineeringMaterial))
                .withManaged(!(material instanceof EngineeringMaterial))
                .build();
        this.squadronCarrierAmountLabel = LabelBuilder.builder()
                .withStyleClass("squadroncarrier-amount")
                .withVisibility(!(material instanceof EngineeringMaterial))
                .withManaged(!(material instanceof EngineeringMaterial))
                .build();
        this.shipAmountLabel = LabelBuilder.builder()
                .withStyleClass("ship-amount")
                .build();
        this.backpackLabel = LabelBuilder.builder()
                .withStyleClass("backpack-amount")
                .withVisibility(material instanceof OdysseyMaterial)
                .withManaged(material instanceof OdysseyMaterial)
                .build();
        var fleetCarrierImage = EdAwesomeIconViewPaneBuilder.builder()
                .withStyleClass("icon")
                .withIcons(EdAwesomeIcon.OTHER_CARRIER_SIMPLE)
                .withVisibility(!(material instanceof EngineeringMaterial))
                .withManaged(!(material instanceof EngineeringMaterial))
                .build();
        var shipImage = EdAwesomeIconViewPaneBuilder.builder()
                .withStyleClass("icon")
                .withIcons(EdAwesomeIcon.SHIPS_SHIP_1, EdAwesomeIcon.SHIPS_SHIP_2)
                .build();
        var backpackImage = EdAwesomeIconViewPaneBuilder.builder()
                .withStyleClass("icon")
                .withIcons(EdAwesomeIcon.ONFOOT_BACKPACK_1, EdAwesomeIcon.ONFOOT_BACKPACK_2)
                .withVisibility(material instanceof OdysseyMaterial)
                .withManaged(material instanceof OdysseyMaterial)
                .build();
        var squadronImage = EdAwesomeIconViewPaneBuilder.builder()
                .withStyleClass("icon")
                .withIcons(EdAwesomeIcon.SQUADRON_CARRIER)
                .withVisibility(!(material instanceof EngineeringMaterial))
                .withManaged(!(material instanceof EngineeringMaterial))
                .build();

        DestroyableHBox leftHBox = BoxBuilder.builder()
                .withNodes(fleetCarrierImage, this.fleetCarrierAmountLabel)
                .withStyleClass("ingredient-quantity-section")
                .buildHBox();
        DestroyableHBox bottomRightHBox = BoxBuilder.builder()
                .withNodes(this.squadronCarrierAmountLabel, squadronImage)
                .withStyleClass("ingredient-quantity-section")
                .buildHBox();
        DestroyableHBox middleLeftHBox = BoxBuilder.builder()
                .withNodes(backpackImage, this.backpackLabel)
                .withStyleClass("ingredient-quantity-section")
                .buildHBox();
        DestroyableHBox topRightHBox = BoxBuilder.builder()
                .withNodes(this.shipAmountLabel, shipImage)
                .withStyleClass("ingredient-quantity-section")
                .buildHBox();
        if (!(material instanceof EngineeringMaterial)) {
            HBox.setHgrow(this.fleetCarrierAmountLabel, Priority.ALWAYS);
            this.fleetCarrierAmountLabel.addBinding(this.fleetCarrierAmountLabel.visibleProperty(), ApplicationState.getInstance().getFcMaterials());
            fleetCarrierImage.addBinding(fleetCarrierImage.visibleProperty(), ApplicationState.getInstance().getFcMaterials());
        }
        DestroyableVBox nameAndCategory = BoxBuilder.builder()
                .withStyleClass("name-category")
                .withNodes(nameLabel, categoryLabel).buildVBox();
        DestroyableHBox firstLine = BoxBuilder.builder()
                .withStyleClass("name-line")
                .withNodes(typeImage, nameAndCategory).buildHBox();
        firstLine.addBinding(firstLine.spacingProperty(), ScalingHelper.getPixelDoubleBindingFromEm(0.5));
        DestroyableHBox secondLine = BoxBuilder.builder()
                .withStyleClass("quantity-line")
                .withNodes(middleLeftHBox)
                .buildHBox();
        if (material instanceof HorizonsMaterial) {
            firstLine.getNodes().addAll(new GrowingRegion(), topRightHBox);
            secondLine.setManaged(false);
        } else {
            secondLine.getNodes().addAll(new GrowingRegion(), topRightHBox);
        }
        DestroyableHBox thirdLine = BoxBuilder.builder()
                .withStyleClass("quantity-line")
                .withNodes(leftHBox, new GrowingRegion(), bottomRightHBox)
                .buildHBox();
        DestroyableVBox content = BoxBuilder.builder()
                .withStyleClass("card-content")
                .withNodes(firstLine, new GrowingRegion(), secondLine, thirdLine).buildVBox();
        if (this.material instanceof RareCommodity) {
            rareImage = ResizableImageViewBuilder.builder()
                    .withStyleClass("rare-image")
                    .withImage("/images/material/stock/rare_right.png")
                    .build();
            var rareContainer = BoxBuilder.builder()
                    .withStyleClass("rare-container")
                    .withNodes(new GrowingRegion(), rareImage).buildHBox();
            this.getNodes().add(rareContainer);
        }
        if (material instanceof Commodity) {

            var coriolisImage = EdAwesomeIconViewPaneBuilder.builder()
                    .withStyleClass("icon")
                    .withIcons(EdAwesomeIcon.STATION_CORIOLIS)
                    .build();

            this.bracket1SellImage = EdAwesomeIconViewPaneBuilder.builder()
                    .withStyleClasses("icon")
                    .withIcons(EdAwesomeIcon.OTHER_STOCK_BRACKET_LOW)
                    .build();
            this.bracket3SellImage = EdAwesomeIconViewPaneBuilder.builder()
                    .withStyleClasses("icon")
                    .withIcons(EdAwesomeIcon.OTHER_STOCK_BRACKET_HIGH)
                    .build();
            this.bracket1BuyImage = EdAwesomeIconViewPaneBuilder.builder()
                    .withStyleClasses("icon")
                    .withIcons(EdAwesomeIcon.OTHER_STOCK_BRACKET_LOW)
                    .build();
            this.bracket3BuyImage = EdAwesomeIconViewPaneBuilder.builder()
                    .withStyleClasses("icon")
                    .withIcons(EdAwesomeIcon.OTHER_STOCK_BRACKET_HIGH)
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
            this.getNodes().add(this.market);
            StackPane.setAlignment(market, Pos.BOTTOM_CENTER);
            updateStyle();
        }
        this.getNodes().add(content);
        updateQuantity();
        MaterialService.addMaterialInfoPopOver(this, this.material, false, () -> Math.max(1, Optional.ofNullable(ApplicationState.getInstance().getShip()).map(ship -> (int) ship.getMaxCargo() * 2).orElse(1)));
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, StorageEvent.class, storageEvent -> {
            updateQuantity();
        }));

        register(EventService.addListener(true, this, MarketUpdatedEvent.class, marketUpdatedEvent -> {
            if (this.material instanceof Commodity) {
                updateQuantity();
                updateStyle();
            }
        }));

        register(EventService.addListener(true, this, AfterFontSizeSetEvent.class, fontSizeEvent -> {
            if (this.material instanceof RareCommodity) {
                rareImage.setImage(ImageService.getImage("/images/material/stock/rare_right.png"));
                rareImage.requestLayout();
            }
        }));
    }

    private void updateQuantity() {
        switch (material) {
            case Commodity commodity: {
                this.squadronCarrierAmount = (StorageService.getCommodityCount(commodity, StoragePool.SQUADRONCARRIER));
                this.fleetCarrierAmount = (StorageService.getCommodityCount(commodity, StoragePool.FLEETCARRIER));
                this.shipAmount = (StorageService.getCommodityCount(commodity, StoragePool.SHIP));
                this.fleetCarrierAmountLabel.setText(this.fleetCarrierAmount.toString());
                this.shipAmountLabel.setText(this.shipAmount.toString());
                this.squadronCarrierAmountLabel.setText(this.squadronCarrierAmount.toString());
                this.stationBuy.setText(MarketService.getMarketItem(commodity).map(marketItem -> marketItem.demand().equals(BigInteger.ONE) ? "∞" : marketItem.demand().toString()).orElse("0"));
                this.stationSell.setText(MarketService.getMarketItem(commodity).map(marketItem -> marketItem.stock().toString()).orElse("0"));
                break;
            }
            case HorizonsMaterial horizonsMaterial: {
                this.shipAmount = (StorageService.getMaterialCount(horizonsMaterial));
                this.shipAmountLabel.setText(this.shipAmount.toString());
                break;
            }
            case OdysseyMaterial odysseyMaterial: {
                this.backpackAmount = (StorageService.getMaterialCount(odysseyMaterial, AmountType.BACKPACK));
                this.squadronCarrierAmount = (StorageService.getMaterialCount(odysseyMaterial, AmountType.SQUADRONCARRIER));
                this.fleetCarrierAmount = (StorageService.getMaterialCount(odysseyMaterial, AmountType.FLEETCARRIER));
                this.shipAmount = (StorageService.getMaterialCount(odysseyMaterial, AmountType.SHIPLOCKER));
                this.fleetCarrierAmountLabel.setText(this.fleetCarrierAmount.toString());
                this.shipAmountLabel.setText(this.shipAmount.toString());
                this.squadronCarrierAmountLabel.setText(this.squadronCarrierAmount.toString());
                this.backpackLabel.setText(this.backpackAmount.toString());
                break;
            }
        }
    }

    private void updateStyle() {
        if (material instanceof Commodity commodity) {
            final boolean sells = MarketService.sells(commodity);
            final boolean buys = MarketService.buys(commodity);

            this.pseudoClassStateChanged(PseudoClass.getPseudoClass("sells"), sells);
            this.pseudoClassStateChanged(PseudoClass.getPseudoClass("buys"), buys);

            MarketService.getMarketItem(commodity).ifPresentOrElse(marketItem -> {
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
}
