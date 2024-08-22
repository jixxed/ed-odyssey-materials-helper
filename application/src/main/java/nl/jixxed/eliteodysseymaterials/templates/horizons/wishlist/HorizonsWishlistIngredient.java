package nl.jixxed.eliteodysseymaterials.templates.horizons.wishlist;

import javafx.geometry.Orientation;
import javafx.scene.paint.Color;
import lombok.EqualsAndHashCode;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.WishlistMaterial;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.MaterialService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.FlipRemainingAvailableEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.SegmentType;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.TypeSegment;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.TypeSegmentView;
import nl.jixxed.eliteodysseymaterials.templates.horizons.HorizonsMaterialIngredient;
import org.controlsfx.control.SegmentedBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class HorizonsWishlistIngredient extends HorizonsMaterialIngredient {

    private static final String INGREDIENT_FILLED_CLASS = "ingredient-filled";
    private static final String INGREDIENT_UNFILLED_CLASS = "ingredient-unfilled";
    private static final String INGREDIENT_FILLED_NOT_SHIPLOCKER_CLASS = "ingredient-filled-partial";
    private SegmentedBar<TypeSegment> segmentedBar;
    private TypeSegment present;
    private TypeSegment missingForMinimum;
    private TypeSegment missingForRequired;
    private TypeSegment missingForMaximum;
    private Integer amountMinimum;
    private Integer amountRequired;
    private Integer amountMaximum;
    private Integer minimum;
    private Integer required;
    private Integer maximum;
    protected List<EventListener<?>> eventListeners = new ArrayList<>();

    HorizonsWishlistIngredient(final HorizonsStorageType storageType, final HorizonsMaterial horizonsMaterial, final Integer amountMinimum, final Integer amountRequired, final Integer amountMaximum, final Integer amountAvailable) {
        super(storageType, horizonsMaterial, amountRequired, amountAvailable);
        this.amountMinimum = amountMinimum;
        this.amountMaximum = amountMaximum;
        this.amountRequired = amountRequired;
        this.minimum = amountMinimum;
        this.maximum = amountMaximum;
        this.required = amountRequired;
        initComponents();
        initEventHandling();
    }

    @SuppressWarnings("java:S2177")
    private void initComponents() {
        this.getStyleClass().add("wishlist-ingredient");
        this.hoverProperty().addListener((observable, oldValue, newValue) -> {
            showAsHovered(newValue);
        });

        this.segmentedBar = new SegmentedBar<>();
        this.segmentedBar.getStyleClass().add("ingredient-progressbar");
        this.segmentedBar.setOrientation(Orientation.HORIZONTAL);
        this.segmentedBar.setInfoNodeFactory(segment -> null);
//        green/red = current material count
//        dark grey = minimum requirement(all engineers G5) - current material count
//        light grey = current requirement(based on shortest path) - max(minimum requirement, current material count)
//        light green/red = maximum requirement(all engineers G1) - current requirement(based on shortest path)

        this.segmentedBar.setSegmentViewFactory(segment -> new TypeSegmentView(segment, Map.of(
                SegmentType.PRESENT, Color.rgb(255, 255, 255),
                SegmentType.MISSING_FOR_MINIMUM, Color.rgb(128, 128, 128),
                SegmentType.MISSING_FOR_REQUIRED, Color.rgb(192, 192, 192),
                SegmentType.MISSING_FOR_MAXIMUM, Color.rgb(255, 255, 255)
        ), false));
        final Integer progress = Math.min(this.getRightAmount(), this.amountMaximum);
        this.present = new TypeSegment(progress, SegmentType.PRESENT);
//        this.notPresent = new TypeSegment(Math.max(this.getLeftAmount() - progress, 0), SegmentType.NOT_PRESENT);
        this.missingForMinimum = new TypeSegment(Math.max(0, amountMinimum - progress), SegmentType.MISSING_FOR_MINIMUM);
        this.missingForRequired = new TypeSegment(Math.max(0, amountRequired - Math.max(amountMinimum, progress)), SegmentType.MISSING_FOR_REQUIRED);
        this.missingForMaximum = new TypeSegment(Math.max(0, amountMaximum - Math.max(amountRequired, progress)), SegmentType.MISSING_FOR_MAXIMUM);
        this.segmentedBar.getSegments().addAll(this.present, this.missingForMinimum,        this.missingForRequired,        this.missingForMaximum);
        this.getChildren().add(this.segmentedBar);
        this.getLeftAmountLabel().setText(getLeftAmountString());
    }
    @Override
    protected void installPopOver() {
        MaterialService.addMaterialInfoPopOver(this, this.getHorizonsMaterial(), true);
    }
    private void showAsHovered(final Boolean newValue) {
        final Boolean showRemaining = !PreferencesService.getPreference(PreferenceConstants.FLIP_WISHLIST_REMAINING_AVAILABLE_HORIZONS, Boolean.FALSE);
        if (showRemaining.equals(newValue) && getHorizonsMaterial() instanceof Commodity commodity && (this.getLeftAmount() - (StorageService.getCommodityCount(commodity, StoragePool.SHIP))) > 0) {
            this.getRightAmountLabel().setText(String.valueOf((this.getLeftAmount() - StorageService.getCommodityCount(commodity, StoragePool.SHIP))));
            setRightDescriptionLabel(LocaleService.getStringBinding("blueprint.header.remaining"));
        } else if (showRemaining.equals(newValue) && !(getHorizonsMaterial() instanceof Commodity) && (this.getLeftAmount() - (StorageService.getMaterialCount(getHorizonsMaterial()))) > 0) {
            this.getRightAmountLabel().setText(String.valueOf((this.getLeftAmount() - StorageService.getMaterialCount(getHorizonsMaterial()))));
            setRightDescriptionLabel(LocaleService.getStringBinding("blueprint.header.remaining"));
        } else {
            setRightDescriptionLabel(LocaleService.getStringBinding("blueprint.header.available"));
            this.getRightAmountLabel().setText(this.getRightAmount().toString());
        }
    }

    @SuppressWarnings("java:S2177")
    private void initEventHandling() {
        this.eventListeners.add(EventService.addListener(true, this, FlipRemainingAvailableEvent.class, flipRemainingAvailableEvent -> {
            if(Expansion.HORIZONS.equals(flipRemainingAvailableEvent.getExpansion())){
                showAsHovered(this.hoverProperty().getValue());
            }
        }));
    }

    @Override
    protected void update() {

        final Integer materialCountShip;
        final Integer materialCountBoth;
        if (getHorizonsMaterial() instanceof Commodity commodity) {
            materialCountShip = StorageService.getCommodityCount(commodity, StoragePool.SHIP);
            materialCountBoth = materialCountShip + StorageService.getCommodityCount(commodity, StoragePool.FLEETCARRIER);
        } else {
            materialCountBoth = StorageService.getMaterialCount(getHorizonsMaterial());
            materialCountShip = materialCountBoth;
        }
        final Boolean showRemaining = !PreferencesService.getPreference(PreferenceConstants.FLIP_WISHLIST_REMAINING_AVAILABLE_HORIZONS, Boolean.FALSE);

        this.setRightAmount(materialCountBoth);
        showAsHovered(this.hoverProperty().getValue());
        this.getStyleClass().removeAll(INGREDIENT_FILLED_CLASS, INGREDIENT_UNFILLED_CLASS, INGREDIENT_FILLED_NOT_SHIPLOCKER_CLASS);
        if (materialCountBoth >= getLeftAmount() && materialCountShip < getLeftAmount()) {
            this.getStyleClass().addAll(INGREDIENT_FILLED_NOT_SHIPLOCKER_CLASS);
        } else if (this.getRightAmount() >= getLeftAmount()) {
            this.getStyleClass().addAll(INGREDIENT_FILLED_CLASS);
        } else {
            this.getStyleClass().addAll(INGREDIENT_UNFILLED_CLASS);
        }
        if(this.present != null){
            final Integer progress = Math.min(this.getRightAmount(), this.amountMaximum);
//            final int progress = Math.min(this.getLeftAmount(), this.getRightAmount());
            this.present.setValue(progress);
            this.missingForMinimum.setValue(Math.max(0, amountMinimum - progress));
            this.missingForRequired.setValue(Math.max(0, amountRequired - Math.max(amountMinimum, progress)));
            this.missingForMaximum.setValue(Math.max(0, amountMaximum - Math.max(amountRequired, progress)));
//            this.notPresent.setValue(Math.max(this.getLeftAmount() - progress, 0));
            if(Math.max(this.getLeftAmount() - progress, 0) > 0){
                this.segmentedBar.setSegmentViewFactory(segment -> new TypeSegmentView(segment, Map.of(
                        SegmentType.PRESENT, Color.web("#ff7c7c"),
                        SegmentType.MISSING_FOR_MINIMUM, Color.rgb(128, 128, 128),
                        SegmentType.MISSING_FOR_REQUIRED, Color.rgb(192, 192, 192),
                        SegmentType.MISSING_FOR_MAXIMUM, Color.web("#FFC8C8")
                ), false));
            }else{
                this.segmentedBar.setSegmentViewFactory(segment -> new TypeSegmentView(segment, Map.of(
                        SegmentType.PRESENT, Color.web("#89d07f"),
                        SegmentType.MISSING_FOR_MINIMUM, Color.rgb(128, 128, 128),
                        SegmentType.MISSING_FOR_REQUIRED, Color.rgb(192, 192, 192),
                        SegmentType.MISSING_FOR_MAXIMUM, Color.web("#BED3BB")
                ), false));
            }
        }
    }

    void searchHighlight(final boolean enable) {
        if (enable) {
            this.getStyleClass().add("wishlist-search-highlight");
        } else {
            this.getStyleClass().removeAll("wishlist-search-highlight");
        }
        update();
    }

    void highlight(final boolean enable, final WishlistMaterial amount) {
        if (enable) {
            this.getStyleClass().add("wishlist-highlight");
            setLeftAmount(amount.getRequired());
            this.amountMinimum = amount.getMinimum();
            this.amountMaximum = amount.getMaximum();
            this.amountRequired = amount.getRequired();
        } else {
            this.getStyleClass().removeAll("wishlist-highlight");
            setLeftAmount(required);
            this.amountMinimum = minimum;
            this.amountMaximum = maximum;
            this.amountRequired = required;
        }
        this.getLeftAmountLabel().setText(getLeftAmountString());
        update();
    }

    void lowlight(final boolean enable) {
        if (enable) {
            this.getStyleClass().add("wishlist-lowlight");
        } else {
            this.getStyleClass().removeAll("wishlist-lowlight");
        }
    }

    @Override
    protected String getLeftAmountString() {
        if(Objects.equals(this.amountMinimum, this.amountMaximum)) {
            return this.amountRequired!= null ? this.amountRequired.toString() : "0";
        }
        return this.amountRequired +  " (" + this.amountMinimum  + " - " + this.amountMaximum + ")";
    }

}
