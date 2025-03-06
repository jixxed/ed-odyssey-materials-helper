package nl.jixxed.eliteodysseymaterials.templates.horizons.colonisation;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.domain.MarketItem;
import nl.jixxed.eliteodysseymaterials.enums.Commodity;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
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

public class BillOfMaterialsEntry extends VBox implements Template {
    private Commodity commodity;
    private Integer requiredAmount;
    private DestroyableLabel commodityLabel;
    private DestroyableLabel amountLabel;
    private DestroyableLabel fleetCarrierLabel;
    private DestroyableLabel shipLabel;
    private DestroyableLabel marketLabel;
    private final List<EventListener<?>> eventListeners = new ArrayList<>();
    private DestroyableResizableImageView fleetCarrierImage;
    private DestroyableResizableImageView shipImage;
    private DestroyableResizableImageView sumImage;
    private DestroyableResizableImageView coriolisImage;
    private DestroyableResizableImageView commodityImage;
    private DestroyableResizableImageView bracket1Image;
    private DestroyableResizableImageView bracket3Image;


    public BillOfMaterialsEntry(Commodity commodity, Integer requiredAmount) {
        this.commodity = commodity;
        this.requiredAmount = requiredAmount;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("horizons-colonisation-entry");
        commodityLabel = LabelBuilder.builder().withStyleClass("horizons-colonisation-entry-commodity").withText(LocaleService.getStringBinding(commodity.getLocalizationKey())).build();
        amountLabel = LabelBuilder.builder().withStyleClass("horizons-colonisation-entry-amount-left").withNonLocalizedText(requiredAmount.toString()).build();
        fleetCarrierLabel = LabelBuilder.builder().withStyleClass("horizons-colonisation-entry-amount-right").withNonLocalizedText("0").build();
        shipLabel = LabelBuilder.builder().withStyleClass("horizons-colonisation-entry-amount-left").withNonLocalizedText("0").build();
        marketLabel = LabelBuilder.builder().withStyleClass("horizons-colonisation-entry-amount-right" ).withNonLocalizedText("0").build();
        this.commodityImage = ResizableImageViewBuilder.builder().withStyleClass("horizons-materialcard-image").withImage(commodity.getCommodityType().getImagePath()).build();
        this.fleetCarrierImage = ResizableImageViewBuilder.builder().withStyleClass("horizons-materialcard-image").withImage("/images/material/fleetcarrier.png").build();
        this.shipImage = ResizableImageViewBuilder.builder().withStyleClass("horizons-materialcard-image").withImage("/images/material/ship.png").build();
        this.sumImage = ResizableImageViewBuilder.builder().withStyleClass("horizons-materialcard-image").withImage("/images/material/sum.png").build();
        this.coriolisImage = ResizableImageViewBuilder.builder().withStyleClass("horizons-materialcard-image").withImage("/images/material/coriolis.png").build();
        this.bracket1Image = ResizableImageViewBuilder.builder().withStyleClasses("horizons-materialcard-image").withImage("/images/material/stock/bracket1.png").build();
        this.bracket3Image = ResizableImageViewBuilder.builder().withStyleClasses("horizons-materialcard-image").withImage("/images/material/stock/bracket3.png").build();
        this.bracket1Image.managedProperty().bind(this.bracket1Image.visibleProperty());
        this.bracket3Image.managedProperty().bind(this.bracket3Image.visibleProperty());
        final HBox title = BoxBuilder.builder().withStyleClass("horizons-colonisation-entry-values").withNodes(commodityImage, commodityLabel).buildHBox();
        final HBox left = BoxBuilder.builder().withStyleClass("horizons-colonisation-entry-values-sub").withNodes(sumImage, amountLabel, new GrowingRegion(), fleetCarrierLabel, fleetCarrierImage).buildHBox();
        final HBox right = BoxBuilder.builder().withStyleClass("horizons-colonisation-entry-values-sub").withNodes(shipImage, shipLabel, new GrowingRegion(), bracket1Image, BoxBuilder.builder().withStyleClass("horizons-colonisation-entry-values-market").withNodes(bracket3Image, marketLabel).buildHBox(), coriolisImage).buildHBox();
        final HBox values = BoxBuilder.builder().withStyleClass("horizons-colonisation-entry-values").withNodes(left, right).buildHBox();
        this.getChildren().addAll(title, values);
        MaterialService.addMaterialInfoPopOver(this, this.commodity, false);
        update();
    }

    @Override
    public void initEventHandling() {
        eventListeners.add(EventService.addListener(this, StorageEvent.class, storageEvent -> {
            if (StoragePool.FLEETCARRIER.equals(storageEvent.getStoragePool()) || StoragePool.SHIP.equals(storageEvent.getStoragePool())) {
                update();
            }
        }));
        eventListeners.add(EventService.addListener(this, MarketUpdatedEvent.class, event -> {
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

    public void onDestroy() {
        this.eventListeners.forEach(EventService::removeListener);
    }
}
