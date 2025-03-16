package nl.jixxed.eliteodysseymaterials.templates.horizons.colonisation;

import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.IntFieldBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ColonisationItem;
import nl.jixxed.eliteodysseymaterials.domain.ColonisationItems;
import nl.jixxed.eliteodysseymaterials.domain.MarketItem;
import nl.jixxed.eliteodysseymaterials.enums.Commodity;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.service.ColonisationService;
import nl.jixxed.eliteodysseymaterials.service.MarketService;
import nl.jixxed.eliteodysseymaterials.service.MaterialService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.MarketUpdatedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.components.IntField;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.math.BigInteger;

public class BillOfMaterialsEntry extends DestroyableVBox implements DestroyableEventTemplate {
    private Commodity commodity;
    private Integer requiredAmount;
    private DestroyableLabel commodityLabel;
    private DestroyableLabel amountLabel;
    private IntField amountField;
    private DestroyableLabel fleetCarrierLabel;
    private DestroyableLabel shipLabel;
    private DestroyableLabel marketLabel;

    private DestroyableResizableImageView fleetCarrierImage;
    private DestroyableResizableImageView shipImage;
    private DestroyableResizableImageView sumImage;
    private DestroyableResizableImageView coriolisImage;
    private DestroyableResizableImageView commodityImage;
    private DestroyableResizableImageView bracket1Image;
    private DestroyableResizableImageView bracket3Image;
    ColonisationItem colonisationItem;


    public BillOfMaterialsEntry(ColonisationItem colonisationItem, Commodity commodity, Integer requiredAmount) {
        this.colonisationItem = colonisationItem;
        this.commodity = commodity;
        this.requiredAmount = requiredAmount;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("horizons-colonisation-entry");
        commodityLabel = LabelBuilder.builder()
                .withStyleClass("horizons-colonisation-entry-commodity")
                .withText(commodity.getLocalizationKey())
                .build();
        amountLabel = LabelBuilder.builder()
                .withStyleClass("horizons-colonisation-entry-amount-left")
                .withNonLocalizedText(requiredAmount.toString())
                .build();
        amountField = IntFieldBuilder.builder()
                .withStyleClass("horizons-colonisation-entry-amount-sum")
                .withMinValue(0)
                .withMaxValue(999999)
                .withInitialValue(requiredAmount)
                .build();
        amountField.valueProperty().addListener((observable, oldValue, newValue) -> {
            ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
                final ColonisationItems colonisationItems = ColonisationService.getColonisationItems(commander);
                colonisationItems.getColonisationItem(colonisationItem.getUuid()).updateAmount(commodity, newValue.intValue());
                ColonisationService.saveColonisationItems(commander, colonisationItems);
            });
            colonisationItem.updateAmount(commodity, newValue.intValue());
        });
        fleetCarrierLabel = LabelBuilder.builder()
                .withStyleClass("horizons-colonisation-entry-amount-right")
                .withNonLocalizedText("0")
                .build();
        shipLabel = LabelBuilder.builder()
                .withStyleClass("horizons-colonisation-entry-amount-left")
                .withNonLocalizedText("0")
                .build();
        marketLabel = LabelBuilder.builder()
                .withStyleClass("horizons-colonisation-entry-amount-right")
                .withNonLocalizedText("0")
                .build();
        this.commodityImage = ResizableImageViewBuilder.builder()
                .withStyleClass("horizons-materialcard-image")
                .withImage(commodity.getCommodityType().getImagePath())
                .build();
        this.fleetCarrierImage = ResizableImageViewBuilder.builder()
                .withStyleClass("horizons-materialcard-image")
                .withImage("/images/material/fleetcarrier.png")
                .build();
        this.shipImage = ResizableImageViewBuilder.builder()
                .withStyleClass("horizons-materialcard-image")
                .withImage("/images/material/ship.png")
                .build();
        this.sumImage = ResizableImageViewBuilder.builder()
                .withStyleClass("horizons-materialcard-image")
                .withImage("/images/material/sum.png")
                .build();
        this.coriolisImage = ResizableImageViewBuilder.builder()
                .withStyleClass("horizons-materialcard-image")
                .withImage("/images/material/coriolis.png")
                .build();
        this.bracket1Image = ResizableImageViewBuilder.builder()
                .withStyleClasses("horizons-materialcard-image")
                .withImage("/images/material/stock/bracket1.png")
                .build();
        this.bracket3Image = ResizableImageViewBuilder.builder()
                .withStyleClasses("horizons-materialcard-image")
                .withImage("/images/material/stock/bracket3.png")
                .build();
        addBinding(this.bracket1Image.managedProperty(), this.bracket1Image.visibleProperty());
        addBinding(this.bracket3Image.managedProperty(), this.bracket3Image.visibleProperty());
        final DestroyableHBox title = BoxBuilder.builder()
                .withStyleClass("horizons-colonisation-entry-values")
                .withNodes(commodityImage, commodityLabel).buildHBox();
        final DestroyableHBox left = BoxBuilder.builder()
                .withStyleClass("horizons-colonisation-entry-values-sub")
                .withNodes(sumImage, colonisationItem == ColonisationItem.ALL ? amountLabel : amountField, new GrowingRegion(), fleetCarrierLabel, fleetCarrierImage).buildHBox();
        final DestroyableHBox right = BoxBuilder.builder()
                .withStyleClass("horizons-colonisation-entry-values-sub")
                .withNodes(shipImage, shipLabel, new GrowingRegion(), bracket1Image, BoxBuilder.builder()
                        .withStyleClass("horizons-colonisation-entry-values-market")
                        .withNodes(bracket3Image, marketLabel).buildHBox(), coriolisImage).buildHBox();
        final DestroyableHBox values = BoxBuilder.builder()
                .withStyleClass("horizons-colonisation-entry-values")
                .withNodes(left, right).buildHBox();
        this.getNodes().addAll(title, values);
        MaterialService.addMaterialInfoPopOver(this, this.commodity, false);
        update();
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(this, StorageEvent.class, storageEvent -> {
            if (StoragePool.FLEETCARRIER.equals(storageEvent.getStoragePool()) || StoragePool.SHIP.equals(storageEvent.getStoragePool())) {
                update();
            }
        }));
        register(EventService.addListener(this, MarketUpdatedEvent.class, event -> {
            update();
        }));
    }

    private void update() {
        fleetCarrierLabel.setText(StorageService.getCommodityCount(commodity, StoragePool.FLEETCARRIER).toString());
        shipLabel.setText(StorageService.getCommodityCount(commodity, StoragePool.SHIP).toString());
        marketLabel.setText(MarketService.isFleetCarrier() ? "0" : MarketService.getMarketItem(commodity).map(MarketItem::stock).orElse(BigInteger.ZERO).toString());
        updateStyle();
    }

    private void updateStyle() {
        this.getStyleClass().remove("horizons-colonisation-entry-available");
        if (!"0".equals(marketLabel.getText())) {
            this.getStyleClass().add("horizons-colonisation-entry-available");
        }
        MarketService.getMarketItem(commodity).ifPresentOrElse(marketItem -> {
            this.bracket1Image.setVisible(marketItem.stockBracket().equals(BigInteger.ONE));
            this.bracket3Image.setVisible(marketItem.stockBracket().equals(BigInteger.valueOf(3)));
        }, () -> {
            this.bracket1Image.setVisible(false);
            this.bracket3Image.setVisible(false);
        });
    }
}
