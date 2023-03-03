package nl.jixxed.eliteodysseymaterials.templates.horizons.wishlist;

import javafx.geometry.Orientation;
import javafx.scene.paint.Color;
import lombok.EqualsAndHashCode;
import nl.jixxed.eliteodysseymaterials.enums.Commodity;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsStorageType;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.SegmentType;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.TypeSegment;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.TypeSegmentView;
import nl.jixxed.eliteodysseymaterials.templates.horizons.HorizonsMaterialIngredient;
import org.controlsfx.control.SegmentedBar;

import java.util.Map;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class HorizonsWishlistIngredient extends HorizonsMaterialIngredient {

    private static final String INGREDIENT_FILLED_CLASS = "ingredient-filled";
    private static final String INGREDIENT_UNFILLED_CLASS = "ingredient-unfilled";
    private static final String INGREDIENT_FILLED_NOT_SHIPLOCKER_CLASS = "ingredient-filled-partial";
    private SegmentedBar<TypeSegment> segmentedBar;
    private TypeSegment present;
    private TypeSegment notPresent;

    HorizonsWishlistIngredient(final HorizonsStorageType storageType, final HorizonsMaterial horizonsMaterial, final Integer amountRequired, final Integer amountAvailable) {
        super(storageType, horizonsMaterial, amountRequired, amountAvailable);
        initComponents();
    }

    @SuppressWarnings("java:S2177")
    private void initComponents() {
        this.getStyleClass().add("wishlist-ingredient");
        this.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (Boolean.TRUE.equals(newValue) && getHorizonsMaterial() instanceof Commodity commodity && (this.getLeftAmount() - (StorageService.getCommodityCount(commodity, StoragePool.SHIP))) > 0) {
                this.getRightAmountLabel().setText(String.valueOf((this.getLeftAmount() - StorageService.getCommodityCount(commodity, StoragePool.SHIP))));
                setRightDescriptionLabel(LocaleService.getStringBinding("blueprint.header.remaining"));
            } else if (Boolean.TRUE.equals(newValue) && !(getHorizonsMaterial() instanceof Commodity) && (this.getLeftAmount() - (StorageService.getMaterialCount(getHorizonsMaterial()))) > 0) {
                this.getRightAmountLabel().setText(String.valueOf((this.getLeftAmount() - StorageService.getMaterialCount(getHorizonsMaterial()))));
                setRightDescriptionLabel(LocaleService.getStringBinding("blueprint.header.remaining"));
            } else {
                setRightDescriptionLabel(LocaleService.getStringBinding("blueprint.header.available"));
                this.getRightAmountLabel().setText(this.getRightAmount().toString());
            }
        });

        this.segmentedBar = new SegmentedBar<>();
        this.segmentedBar.getStyleClass().add("ingredient-progressbar");
        this.segmentedBar.setOrientation(Orientation.HORIZONTAL);
        this.segmentedBar.setInfoNodeFactory(segment -> null);
        this.segmentedBar.setSegmentViewFactory(segment -> new TypeSegmentView(segment, Map.of(
                SegmentType.PRESENT, Color.rgb(255, 255, 255),
                SegmentType.NOT_PRESENT, Color.rgb(128, 128, 128)
        ), false));
        final Integer progress = Math.min(this.getLeftAmount(), this.getRightAmount());
        this.present = new TypeSegment(progress, SegmentType.PRESENT);
        this.notPresent = new TypeSegment(Math.max(this.getLeftAmount() - progress, 0), SegmentType.NOT_PRESENT);
        this.segmentedBar.getSegments().addAll(this.present, this.notPresent);
        this.getChildren().add(this.segmentedBar);
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
        this.setRightAmount(materialCountBoth);
        this.getRightAmountLabel().setText(materialCountBoth.toString());
        this.getStyleClass().removeAll(INGREDIENT_FILLED_CLASS, INGREDIENT_UNFILLED_CLASS, INGREDIENT_FILLED_NOT_SHIPLOCKER_CLASS);
        if (materialCountBoth >= Integer.parseInt(this.getLeftAmountLabel().getText()) && materialCountShip < Integer.parseInt(this.getLeftAmountLabel().getText())) {
            this.getStyleClass().addAll(INGREDIENT_FILLED_NOT_SHIPLOCKER_CLASS);
        } else if (this.getRightAmount() >= Integer.parseInt(this.getLeftAmountLabel().getText())) {
            this.getStyleClass().addAll(INGREDIENT_FILLED_CLASS);
        } else {
            this.getStyleClass().addAll(INGREDIENT_UNFILLED_CLASS);
        }
        if(this.present != null){
            final int progress = Math.min(this.getLeftAmount(), this.getRightAmount());
            this.present.setValue(progress);
            this.notPresent.setValue(Math.max(this.getLeftAmount() - progress, 0));
            if(Math.max(this.getLeftAmount() - progress, 0) > 0){
                this.segmentedBar.setSegmentViewFactory(segment -> new TypeSegmentView(segment, Map.of(
                        SegmentType.PRESENT, Color.web("#ff7c7c"),
                        SegmentType.NOT_PRESENT, Color.rgb(128, 128, 128)
                ), false));
            }else{
                this.segmentedBar.setSegmentViewFactory(segment -> new TypeSegmentView(segment, Map.of(
                        SegmentType.PRESENT, Color.web("#89d07f"),
                        SegmentType.NOT_PRESENT, Color.rgb(128, 128, 128)
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

    void highlight(final boolean enable, final Integer amountRequiredForRecipe) {
        if (enable) {
            this.getStyleClass().add("wishlist-highlight");
            this.getLeftAmountLabel().setText(amountRequiredForRecipe.toString());
        } else {
            this.getStyleClass().removeAll("wishlist-highlight");
            this.getLeftAmountLabel().setText(this.getLeftAmount().toString());
        }
        update();
    }

    void lowlight(final boolean enable) {
        if (enable) {
            this.getStyleClass().add("wishlist-lowlight");
        } else {
            this.getStyleClass().removeAll("wishlist-lowlight");
        }
    }
}
