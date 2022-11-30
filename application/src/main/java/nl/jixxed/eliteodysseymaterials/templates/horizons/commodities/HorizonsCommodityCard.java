package nl.jixxed.eliteodysseymaterials.templates.horizons.commodities;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.enums.Commodity;
import nl.jixxed.eliteodysseymaterials.enums.GameVersion;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.MaterialService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;

public class HorizonsCommodityCard extends VBox implements Template {
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
    private HBox leftHBox;
    private HBox rightHBox;
    private HBox firstLine;
    private Region region;
    private HBox secondLine;
    private Region region2;
    @Getter
    private final Commodity commodity;

    HorizonsCommodityCard(final Commodity commodity) {
        this.commodity = commodity;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("horizons-commoditycard");
        if (GameVersion.LIVE.equals(this.commodity.getGameVersion())) {
            this.getStyleClass().add("horizons-commoditycard-live");
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
        this.leftHBox = BoxBuilder.builder().withNodes(this.leftImage, this.leftAmountLabel).withStyleClass("ingredient-quantity-section").buildHBox();
        this.rightHBox = BoxBuilder.builder().withNodes(this.rightAmountLabel, this.rightImage).withStyleClass("ingredient-quantity-section").buildHBox();
        updateQuantity();
        HBox.setHgrow(this.leftAmountLabel, Priority.ALWAYS);
        this.region = new Region();
        HBox.setHgrow(this.region, Priority.ALWAYS);
        this.region2 = new Region();
        VBox.setVgrow(this.region2, Priority.ALWAYS);

        this.firstLine = BoxBuilder.builder().withStyleClass("horizons-commoditycard-firstline").withNodes(this.typeImage, this.nameLabel).buildHBox();
        this.firstLine.spacingProperty().bind(ScalingHelper.getPixelDoubleBindingFromEm(0.5));
        this.secondLine = new HBox(this.leftHBox, this.region, this.rightHBox);
        this.getChildren().addAll(this.firstLine, this.region2, this.secondLine);


        MaterialService.addMaterialInfoPopOver(this, this.commodity);

    }

    @Override
    public void initEventHandling() {
        EventService.addListener(this, StorageEvent.class, storageEvent -> {
            if (storageEvent.getStoragePool().equals(StoragePool.FLEETCARRIER) || storageEvent.getStoragePool().equals(StoragePool.SHIP)) {
                updateQuantity();
            }
        });
    }

    private void updateQuantity() {
        this.fleetcarrierAmount = (StorageService.getCommodityCount(this.commodity, StoragePool.FLEETCARRIER));
        this.shipAmount = (StorageService.getCommodityCount(this.commodity, StoragePool.SHIP));
        this.leftAmountLabel.setText(this.fleetcarrierAmount.toString());
        this.rightAmountLabel.setText(this.shipAmount.toString());

    }
}
