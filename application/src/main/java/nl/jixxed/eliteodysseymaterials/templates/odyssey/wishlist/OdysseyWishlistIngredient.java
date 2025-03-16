package nl.jixxed.eliteodysseymaterials.templates.odyssey.wishlist;

import javafx.geometry.Orientation;
import javafx.scene.paint.Color;
import lombok.EqualsAndHashCode;
import nl.jixxed.eliteodysseymaterials.builder.SegmentedBarBuilder;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.Storage;
import nl.jixxed.eliteodysseymaterials.enums.AmountType;
import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyStorageType;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.FlipRemainingAvailableEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.SegmentType;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.TypeSegment;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.TypeSegmentView;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableSegmentedBar;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.OdysseyMaterialIngredient;

import java.util.Map;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public
class OdysseyWishlistIngredient extends OdysseyMaterialIngredient {

    private static final String INGREDIENT_FILLED_CLASS = "ingredient-filled";
    private static final String INGREDIENT_UNFILLED_CLASS = "ingredient-unfilled";
    private static final String INGREDIENT_FILLED_NOT_SHIPLOCKER_CLASS = "ingredient-filled-partial";
    private DestroyableSegmentedBar<TypeSegment> segmentedBar;
    private TypeSegment present;
    private TypeSegment notPresent;

    OdysseyWishlistIngredient(final OdysseyStorageType storageType, final OdysseyMaterial odysseyMaterial, final Integer amountRequired, final Integer amountAvailable) {
        super(storageType, odysseyMaterial, amountRequired, amountAvailable);
        initComponents();
        initEventHandling();
    }

    @SuppressWarnings("java:S2177")
    public void initComponents() {
        this.getStyleClass().add("wishlist-ingredient");
        this.hoverProperty().addListener((observable, oldValue, newValue) -> {
            showAsHovered(newValue);
        });
        this.segmentedBar = SegmentedBarBuilder.builder(TypeSegment.class).build();
        this.segmentedBar.getStyleClass().add("ingredient-progressbar");
        this.segmentedBar.setOrientation(Orientation.HORIZONTAL);
        this.segmentedBar.setInfoNodeFactory(segment -> null);
        this.segmentedBar.setSegmentViewFactory(segment -> new TypeSegmentView(segment, Map.of(
                SegmentType.PRESENT, Color.rgb(255, 255, 255),
                SegmentType.NOT_PRESENT, Color.rgb(128, 128, 128)
        ), false));
        final Integer progress = Math.min(this.getLeftAmount(), this.getRightAmount());
        this.present = new TypeSegment(Math.max(0, progress), SegmentType.PRESENT);
        this.notPresent = new TypeSegment(Math.max(0, this.getLeftAmount() - progress), SegmentType.NOT_PRESENT);
        this.segmentedBar.getSegments().addAll(this.present, this.notPresent);
        this.getNodes().add(this.segmentedBar);
    }

    private void showAsHovered(final Boolean newValue) {
        final Boolean showRemaining = !PreferencesService.getPreference(PreferenceConstants.FLIP_WISHLIST_REMAINING_AVAILABLE_ODYSSEY, Boolean.FALSE);
        if (showRemaining.equals(newValue) && (this.getLeftAmount() - StorageService.getMaterialCount(getOdysseyMaterial(), AmountType.AVAILABLE)) > 0) {
            this.getRightAmountLabel().setText(String.valueOf(this.getLeftAmount() - StorageService.getMaterialCount(getOdysseyMaterial(), AmountType.AVAILABLE)));
            setRightDescriptionLabel(LocaleService.getStringBinding("blueprint.header.remaining"));
        } else {
            setRightDescriptionLabel(LocaleService.getStringBinding("blueprint.header.available"));
            this.getRightAmountLabel().setText(this.getRightAmount().toString());
        }
    }

    @SuppressWarnings("java:S2177")
    public void initEventHandling() {
        register(EventService.addListener(true, this, FlipRemainingAvailableEvent.class, flipRemainingAvailableEvent -> {
            if (Expansion.ODYSSEY.equals(flipRemainingAvailableEvent.getExpansion())) {
                showAsHovered(this.hoverProperty().getValue());
            }
        }));
    }

    @Override
    protected void update() {

        final Storage storage = StorageService.getMaterialStorage(this.getOdysseyMaterial());
        final int leftAmount = Integer.parseInt(this.getLeftAmountLabel().getText());
        this.getStyleClass().removeAll(INGREDIENT_FILLED_NOT_SHIPLOCKER_CLASS, INGREDIENT_FILLED_CLASS, INGREDIENT_UNFILLED_CLASS);
        if (storage.getTotalValue() >= leftAmount && storage.getShipLockerValue() + storage.getBackPackValue() < leftAmount) {

            this.getStyleClass().addAll(INGREDIENT_FILLED_NOT_SHIPLOCKER_CLASS);
        } else if (storage.getTotalValue() >= leftAmount) {
            this.getStyleClass().addAll(INGREDIENT_FILLED_CLASS);
        } else {
            this.getStyleClass().addAll(INGREDIENT_UNFILLED_CLASS);
        }
        showAsHovered(this.hoverProperty().getValue());
        if (this.present != null) {
            final int progress = Math.min(this.getLeftAmount(), this.getRightAmount());
            this.present.setValue(progress);
            this.notPresent.setValue(Math.max(this.getLeftAmount() - progress, 0));
            if (Math.max(this.getLeftAmount() - progress, 0) > 0) {
                this.segmentedBar.setSegmentViewFactory(segment -> new TypeSegmentView(segment, Map.of(
                        SegmentType.PRESENT, Color.web("#ff7c7c"),
                        SegmentType.NOT_PRESENT, Color.rgb(128, 128, 128)
                ), false));
            } else {
                this.segmentedBar.setSegmentViewFactory(segment -> new TypeSegmentView(segment, Map.of(
                        SegmentType.PRESENT, Color.web("#89d07f"),
                        SegmentType.NOT_PRESENT, Color.rgb(128, 128, 128)
                ), false));
            }
        }
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

    void searchHighlight(final boolean enable) {
        if (enable) {
            this.getStyleClass().add("wishlist-search-highlight");
        } else {
            this.getStyleClass().removeAll("wishlist-search-highlight");
        }
        update();
    }
}
