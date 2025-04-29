package nl.jixxed.eliteodysseymaterials.templates.horizons.colonisation;

import javafx.css.PseudoClass;
import javafx.geometry.Orientation;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.builder.SegmentedBarBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ColonisationItem;
import nl.jixxed.eliteodysseymaterials.domain.ConstructionProgress;
import nl.jixxed.eliteodysseymaterials.domain.MarketItem;
import nl.jixxed.eliteodysseymaterials.enums.Commodity;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.service.MarketService;
import nl.jixxed.eliteodysseymaterials.service.MaterialService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.MarketUpdatedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.SegmentType;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.TypeSegment;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.TypeSegmentView;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.math.BigInteger;

public class BillOfMaterialsEntry extends DestroyableVBox implements DestroyableEventTemplate {
    private Commodity commodity;
    private ConstructionProgress progress;
    private DestroyableLabel commodityLabel;
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
    private TypeSegment presentShip;
    private TypeSegment presentFleetCarrier;
    private TypeSegment delivered;
    private TypeSegment missingForCompletion;
    private Integer availableShip;
    private Integer availableFleetCarrier;


    public BillOfMaterialsEntry(ColonisationItem colonisationItem, Commodity commodity, ConstructionProgress progress) {
        this.colonisationItem = colonisationItem;
        this.commodity = commodity;
        this.progress = progress;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("bom-entry");
        commodityLabel = LabelBuilder.builder()
                .withStyleClass("name")
                .withText(commodity.getLocalizationKey())
                .build();

        fleetCarrierLabel = LabelBuilder.builder()
                .withStyleClass("amount-right")
                .withNonLocalizedText("0")
                .build();
        shipLabel = LabelBuilder.builder()
                .withStyleClass("amount-left")
                .withNonLocalizedText("0")
                .build();
        marketLabel = LabelBuilder.builder()
                .withStyleClass("amount-right")
                .withNonLocalizedText("0")
                .build();
        this.commodityImage = ResizableImageViewBuilder.builder()
                .withStyleClass("material-image")
                .withImage(commodity.getCommodityType().getImagePath())
                .build();
        this.fleetCarrierImage = ResizableImageViewBuilder.builder()
                .withStyleClass("storage-image")
                .withImage("/images/material/fleetcarrier.png")
                .build();
        this.shipImage = ResizableImageViewBuilder.builder()
                .withStyleClass("storage-image")
                .withImage("/images/material/ship.png")
                .build();
        this.sumImage = ResizableImageViewBuilder.builder()
                .withStyleClass("storage-image")
                .withImage("/images/material/sum.png")
                .build();
        this.coriolisImage = ResizableImageViewBuilder.builder()
                .withStyleClass("storage-image")
                .withImage("/images/material/coriolis.png")
                .build();
        this.bracket1Image = ResizableImageViewBuilder.builder()
                .withStyleClasses("storage-image")
                .withImage("/images/material/stock/bracket1.png")
                .build();
        this.bracket3Image = ResizableImageViewBuilder.builder()
                .withStyleClasses("storage-image")
                .withImage("/images/material/stock/bracket3.png")
                .build();
        addBinding(this.bracket1Image.managedProperty(), this.bracket1Image.visibleProperty());
        addBinding(this.bracket3Image.managedProperty(), this.bracket3Image.visibleProperty());
        final DestroyableHBox title = BoxBuilder.builder()
                .withStyleClass("values")
                .withNodes(commodityImage, commodityLabel).buildHBox();
        final DestroyableHBox left = BoxBuilder.builder()
                .withStyleClass("values-sub")
                .withNodes(sumImage, getAmountLabel(), new GrowingRegion(), fleetCarrierLabel, fleetCarrierImage).buildHBox();
        final DestroyableHBox right = BoxBuilder.builder()
                .withStyleClass("values-sub")
                .withNodes(shipImage, shipLabel, new GrowingRegion(), bracket1Image, BoxBuilder.builder()
                        .withStyleClass("values-market")
                        .withNodes(bracket3Image, marketLabel).buildHBox(), coriolisImage).buildHBox();
        final DestroyableHBox values = BoxBuilder.builder()
                .withStyleClass("values")
                .withNodes(left, right).buildHBox();

        availableShip = StorageService.getCommodityCount(commodity, StoragePool.SHIP);
        availableFleetCarrier = StorageService.getCommodityCount(commodity, StoragePool.FLEETCARRIER);
        var remaining = progress.required() - progress.provided();
        this.delivered = new TypeSegment(Math.max(0, progress.provided()), SegmentType.PRESENT);
        this.presentShip = new TypeSegment(Math.min(remaining, availableShip), SegmentType.PRESENT_SHIP);
        this.presentFleetCarrier = new TypeSegment(Math.min(remaining - Math.min(remaining, availableShip), availableFleetCarrier), SegmentType.PRESENT_FLEET_CARRIER);
        this.missingForCompletion = new TypeSegment(Math.max(0, remaining - availableShip - availableFleetCarrier), SegmentType.NOT_PRESENT);
        DestroyableSegmentedBar<TypeSegment> progressbar = SegmentedBarBuilder.builder(TypeSegment.class)
                .withStyleClass("bom-entry-progressbar")
                .withOrientation(Orientation.HORIZONTAL)
                .withInfoNodeFactory(_ -> null)
                .withSegmentViewFactory(segment -> new TypeSegmentView(segment, false))
                .withSegments(delivered, presentShip, presentFleetCarrier, missingForCompletion)
                .build();

        final DestroyableVBox content = BoxBuilder.builder()
                .withStyleClass("bom-entry-content")
                .withNodes(title, values)
                .buildVBox();
        this.getNodes().addAll(content, progressbar);
        MaterialService.addMaterialInfoPopOver(this, this.commodity, false);
        update();
    }
//
//    private IntField getAmountField() {
//        IntField amountField = IntFieldBuilder.builder()
//                .withStyleClass("amount-sum")
//                .withMinValue(0)
//                .withMaxValue(999999)
//                .withInitialValue(requiredAmount)
//                .build();
//        amountField.addChangeListener(amountField.valueProperty(), (_, _, newValue) -> {
//            ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
//                final ColonisationItems colonisationItems = ColonisationService.getColonisationItems(commander);
//                colonisationItems.getColonisationItem(colonisationItem.getUuid()).updateAmount(commodity, newValue.intValue());
//                ColonisationService.saveColonisationItems(commander, colonisationItems);
//            });
//            colonisationItem.updateAmount(commodity, newValue.intValue());
//        });
//        return amountField;
//    }

    private DestroyableLabel getAmountLabel() {
        return LabelBuilder.builder()
                .withStyleClass("amount-left")
                .withNonLocalizedText(progress.provided() + "/" + progress.required())
                .build();
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
        availableShip = StorageService.getCommodityCount(commodity, StoragePool.SHIP);
        availableFleetCarrier = StorageService.getCommodityCount(commodity, StoragePool.FLEETCARRIER);

        fleetCarrierLabel.setText(availableFleetCarrier.toString());
        shipLabel.setText(availableShip.toString());
        marketLabel.setText(MarketService.isFleetCarrier() ? "0" : MarketService.getMarketItem(commodity).map(MarketItem::stock).orElse(BigInteger.ZERO).toString());

        availableShip = StorageService.getCommodityCount(commodity, StoragePool.SHIP);
        availableFleetCarrier = StorageService.getCommodityCount(commodity, StoragePool.FLEETCARRIER);
        var remaining = progress.required() - progress.provided();
        this.delivered.setValue(Math.max(0, progress.provided()));
        this.presentShip.setValue(Math.min(remaining, availableShip));
        this.presentFleetCarrier.setValue(Math.min(remaining - Math.min(remaining, availableShip), availableFleetCarrier));
        this.missingForCompletion.setValue(Math.max(0, remaining - availableShip - availableFleetCarrier));

        updateStyle();
    }

    private void updateStyle() {
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("available"), !"0".equals(marketLabel.getText()));
        MarketService.getMarketItem(commodity).ifPresentOrElse(marketItem -> {
            this.bracket1Image.setVisible(marketItem.stockBracket().equals(BigInteger.ONE));
            this.bracket3Image.setVisible(marketItem.stockBracket().equals(BigInteger.valueOf(3)));
        }, () -> {
            this.bracket1Image.setVisible(false);
            this.bracket3Image.setVisible(false);
        });
    }
}
