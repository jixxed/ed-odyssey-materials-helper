package nl.jixxed.eliteodysseymaterials.templates;

import javafx.beans.binding.StringBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import lombok.EqualsAndHashCode;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.MaterialService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.JournalLineProcessedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableComponent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class HorizonsMaterialIngredient extends Ingredient implements DestroyableComponent {
    private static final String INGREDIENT_WITH_AMOUNT_CLASS = "ingredient-with-amount";
    private static final String INGREDIENT_FILLED_CLASS = "ingredient-filled";
    private static final String INGREDIENT_UNFILLED_CLASS = "ingredient-unfilled";
    private static final String INGREDIENT_FILLED_NOT_SHIPLOCKER_CLASS = "ingredient-filled-partial";
    @EqualsAndHashCode.Include
    private final HorizonsStorageType storageType;
    @EqualsAndHashCode.Include
    private final HorizonsMaterial horizonsMaterial;
    private final Integer leftAmount;
    private Integer rightAmount;

    private Label nameLabel;
    private DestroyableResizableImageView image;
    private Label leftAmountLabel;
    private Label rightAmountLabel;
    private Label leftDescriptionLabel;
    private Label rightDescriptionLabel;
    private HBox leftHBox;
    private HBox rightHBox;
    private HBox firstLine;
    private Region region;
    private HBox secondLine;
    private Region region2;

    private final List<EventListener<?>> eventListeners = new ArrayList<>();

    public List<EventListener<?>> getEventListeners() {
        return this.eventListeners;
    }

    HorizonsMaterialIngredient(final HorizonsStorageType storageType, final HorizonsMaterial horizonsMaterial, final Integer leftAmount, final Integer rightAmount) {
        if (storageType.equals(HorizonsStorageType.OTHER)) {
            throw new IllegalArgumentException("StorageType Other must use MissionIngredient class");
        }
        this.storageType = storageType;
        this.leftAmount = leftAmount;
        this.horizonsMaterial = horizonsMaterial;
        this.rightAmount = rightAmount;
        initComponents();
        initEventHandling();
    }

    private void initEventHandling() {
        this.eventListeners.add(EventService.addListener(this, JournalLineProcessedEvent.class, journalProcessedEvent -> this.update()));
        this.eventListeners.add(EventService.addListener(this, StorageEvent.class, evt -> {
            if (evt.getStoragePool().equals(StoragePool.SHIP)) {
                this.update();
            }
        }));
    }

    private void initComponents() {
        this.nameLabel = LabelBuilder.builder().withStyleClass("ingredient-name").withText(LocaleService.getStringBinding(this.horizonsMaterial.getLocalizationKey())).build();
        initImage();

        this.leftAmountLabel = LabelBuilder.builder().withStyleClass("ingredient-required").build();
        this.rightAmountLabel = LabelBuilder.builder().withStyleClass("ingredient-available").build();
        this.leftDescriptionLabel = LabelBuilder.builder().withStyleClass("ingredient-quantity-label").withText(LocaleService.getStringBinding("blueprint.header.required")).build();
        this.rightDescriptionLabel = LabelBuilder.builder().withStyleClass("ingredient-quantity-label").withText(LocaleService.getStringBinding("blueprint.header.available")).build();

        this.leftHBox = BoxBuilder.builder().withNodes(this.leftDescriptionLabel, this.leftAmountLabel).withStyleClass("ingredient-quantity-section").buildHBox();
        this.rightHBox = BoxBuilder.builder().withNodes(this.rightAmountLabel, this.rightDescriptionLabel).withStyleClass("ingredient-quantity-section").buildHBox();
        this.leftAmountLabel.setText(this.leftAmount.toString());
        HBox.setHgrow(this.leftAmountLabel, Priority.ALWAYS);
        this.rightAmountLabel.setText(this.rightAmount.toString());
        this.region = new Region();
        HBox.setHgrow(this.region, Priority.ALWAYS);
        this.region2 = new Region();
        VBox.setVgrow(this.region2, Priority.ALWAYS);

        this.firstLine = BoxBuilder.builder().withNodes(this.image, this.nameLabel).buildHBox();
        this.firstLine.prefHeightProperty().bind(this.nameLabel.heightProperty());
        this.secondLine = new HBox(this.leftHBox, this.region, this.rightHBox);
        this.getChildren().addAll(this.firstLine, this.region2, this.secondLine);

        MaterialService.addMaterialInfoPopOver(this, this.horizonsMaterial);
        this.getStyleClass().add("ingredient");

        update();
    }

    @SuppressWarnings("java:S6205")
    private void initImage() {
        if (this.horizonsMaterial instanceof Commodity commodity) {
            this.image = ResizableImageViewBuilder.builder().withStyleClass("horizons-materialcard-image").withImage(commodity.getCommodityType().getImagePath()).build();
        } else {
            this.image = ResizableImageViewBuilder.builder().withStyleClass("horizons-materialcard-image").withImage(this.horizonsMaterial.getRarity().getImagePath()).build();
        }
    }

    private void setRightAmount(final Integer rightAmount) {
        this.rightAmount = rightAmount;
    }


    protected void update() {
        final Integer materialCountShip;
        final Integer materialCountBoth;
        if (this.horizonsMaterial instanceof Commodity commodity) {
            materialCountShip = StorageService.getCommodityCount(commodity, StoragePool.SHIP);
            materialCountBoth = materialCountShip + StorageService.getCommodityCount(commodity, StoragePool.FLEETCARRIER);
        } else {
            materialCountBoth = StorageService.getMaterialCount(this.horizonsMaterial);
            materialCountShip = materialCountBoth;
        }
        setRightAmount(materialCountBoth);
        this.rightAmountLabel.setText(this.rightAmount.toString());
        this.getStyleClass().removeAll(INGREDIENT_WITH_AMOUNT_CLASS, INGREDIENT_FILLED_CLASS, INGREDIENT_UNFILLED_CLASS, INGREDIENT_FILLED_NOT_SHIPLOCKER_CLASS);
        if (materialCountBoth >= this.leftAmount && materialCountShip < this.leftAmount) {
            this.getStyleClass().addAll(INGREDIENT_WITH_AMOUNT_CLASS, INGREDIENT_FILLED_NOT_SHIPLOCKER_CLASS);
        } else if (materialCountBoth >= this.leftAmount) {
            this.getStyleClass().addAll(INGREDIENT_WITH_AMOUNT_CLASS, INGREDIENT_FILLED_CLASS);
        } else {
            this.getStyleClass().addAll(INGREDIENT_WITH_AMOUNT_CLASS, INGREDIENT_UNFILLED_CLASS);
        }
    }

    @Override
    public StorageType getType() {
        return this.storageType;
    }

    @Override
    public String getName() {
        return this.nameLabel.getText();
    }

    public HorizonsMaterial getHorizonsMaterial() {
        return this.horizonsMaterial;
    }

    Label getLeftAmountLabel() {
        return this.leftAmountLabel;
    }

    Integer getLeftAmount() {
        return this.leftAmount;
    }

    Integer getRightAmount() {
        return this.rightAmount;
    }

    Label getRightAmountLabel() {
        return this.rightAmountLabel;
    }


    void setLeftDescriptionLabel(final StringBinding leftDescriptionLabel) {
        this.leftDescriptionLabel.textProperty().bind(leftDescriptionLabel);
    }

    void setRightDescriptionLabel(final StringBinding rightDescriptionLabel) {
        this.rightDescriptionLabel.textProperty().bind(rightDescriptionLabel);
    }

    Label getLeftDescriptionLabel() {
        return this.leftDescriptionLabel;
    }

    Label getRightDescriptionLabel() {
        return this.rightDescriptionLabel;
    }

    @Override
    public void destroyInternal() {
        this.eventListeners.forEach(EventService::removeListener);
    }

    @Override
    public Map<ObservableValue, List<ChangeListener>> getListenersMap() {
        return Collections.emptyMap();
    }


}
