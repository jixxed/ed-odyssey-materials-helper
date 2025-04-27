package nl.jixxed.eliteodysseymaterials.templates.odyssey.wishlist;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.css.PseudoClass;
import javafx.geometry.Orientation;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.builder.SegmentedBarBuilder;
import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.OdysseyBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.OdysseyWishlistBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.Wishlist;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.*;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.SegmentType;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.TypeSegment;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.TypeSegmentView;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.util.Map;
import java.util.Optional;

@Slf4j
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class OdysseyWishlistIngredient extends DestroyableVBox implements DestroyableComponent, DestroyableEventTemplate {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    @Getter
    @EqualsAndHashCode.Include
    private final OdysseyMaterial odysseyMaterial;

    private DestroyableResizableImageView image;
    private DestroyableLabel requiredAmountLabel;
    private DestroyableLabel availableAmountLabel;
    private DestroyableLabel remainingAmountLabel;

    private TypeSegment presentShip;
    private TypeSegment presentFleetCarrier;
    private TypeSegment missingForMinimum;
    private TypeSegment missingForRequired;
    private TypeSegment missingForMaximum;
    private Integer minimum = 0;
    @Getter
    private IntegerProperty required = new SimpleIntegerProperty(0);
    private IntegerProperty requiredFull = new SimpleIntegerProperty(0);
    private Integer maximum = 0;
    private Integer availableShip = 0;
    private Integer availableFleetCarrier = 0;
    private Integer remaining = 0;
    //field to store the original full amount, so we have something to compare when hovering
    private Integer remainingFull = 0;
    final BooleanProperty showRemaining;
    final BooleanProperty hideCompleted;
    final BooleanProperty completed = new SimpleBooleanProperty(false);
    private String currentSearchQuery = "";
    ObjectProperty<OdysseyWishlistBlueprint> blueprint = new SimpleObjectProperty<>();

    OdysseyWishlistIngredient(final OdysseyMaterial odysseyMaterial) {
        this.odysseyMaterial = odysseyMaterial;
        this.hideCompleted = new SimpleBooleanProperty(PreferencesService.getPreference("blueprint.odyssey.hide.completed", false));
        this.showRemaining = new SimpleBooleanProperty(PreferencesService.getPreference(PreferenceConstants.FLIP_WISHLIST_REMAINING_AVAILABLE_ODYSSEY, Boolean.FALSE));
        calculate();

        initComponents();
        initEventHandling();
    }


    @SuppressWarnings("java:S2177")
    public void initComponents() {
        this.getStyleClass().add("wishlist-ingredient");
        this.getStyleClass().add("material");
        BooleanBinding hideWhenHideCompleted = hideCompleted.and(completed);
        BooleanBinding hideWhenHighlighting = blueprint.isNotNull().and(requiredFull.isEqualTo(0));
        BooleanBinding hideWhenHidden = blueprint.isNull().and(required.isEqualTo(0));
        this.visibleProperty().bind(hideWhenHighlighting.or(hideWhenHideCompleted).or(hideWhenHidden).not());
        this.managedProperty().bind(hideWhenHighlighting.or(hideWhenHideCompleted).or(hideWhenHidden).not());
        final int progressShip = Math.min(availableShip, this.maximum);
        final int progressFleetCarrier = Math.min(availableFleetCarrier, this.maximum - progressShip);
        final int progressTotal = Math.min(progressShip + progressFleetCarrier, this.maximum);
        this.presentShip = new TypeSegment(Math.max(0, progressShip), SegmentType.PRESENT_SHIP);
        this.presentFleetCarrier = new TypeSegment(Math.max(0, progressFleetCarrier), SegmentType.PRESENT_FLEET_CARRIER);
        this.missingForMinimum = new TypeSegment(Math.max(0, minimum - progressTotal), SegmentType.MISSING_FOR_MINIMUM);
        this.missingForRequired = new TypeSegment(Math.max(0, required.get() - Math.max(minimum, progressTotal)), SegmentType.MISSING_FOR_REQUIRED);
        this.missingForMaximum = new TypeSegment(Math.max(0, maximum - Math.max(required.get(), progressTotal)), SegmentType.MISSING_FOR_MAXIMUM);
        DestroyableSegmentedBar<TypeSegment> progressbar = SegmentedBarBuilder.builder(TypeSegment.class)
                .withStyleClass("ingredient-progressbar")
                .withOrientation(Orientation.HORIZONTAL)
                .withInfoNodeFactory(_ -> null)
                .withSegmentViewFactory(segment -> new TypeSegmentView(segment, false))
                .withSegments(presentShip, presentFleetCarrier, missingForMinimum, missingForRequired, missingForMaximum)
                .build();

        DestroyableLabel nameLabel = LabelBuilder.builder()
                .withStyleClass("name")
                .withText(this.odysseyMaterial.getLocalizationKey())
                .build();
        VBox.setVgrow(nameLabel, Priority.ALWAYS);
        HBox.setHgrow(nameLabel, Priority.ALWAYS);
        initImage();

        this.requiredAmountLabel = LabelBuilder.builder()
                .withStyleClass("quantity")
                .withNonLocalizedText(maximum.equals(minimum) ? String.valueOf(required.get()) : required.get() + " (" + minimum + " - " + maximum + ")")
                .build();
        this.remainingAmountLabel = LabelBuilder.builder()
                .withStyleClass("quantity")
                .withNonLocalizedText(remaining.toString())
                .withVisibilityProperty(showRemaining.or(this.hoverProperty()).and(this.hoverProperty().and(showRemaining).not()))//xor?
                .withManagedProperty(showRemaining.or(this.hoverProperty()).and(this.hoverProperty().and(showRemaining).not()))//xor?
                .build();
        this.availableAmountLabel = LabelBuilder.builder()
                .withStyleClass("quantity")
                .withNonLocalizedText(availableFleetCarrier > 0 ? availableShip + " + " + availableFleetCarrier : availableShip.toString())
                .withVisibilityProperty(this.remainingAmountLabel.visibleProperty().not())
                .withManagedProperty(this.remainingAmountLabel.visibleProperty().not())
                .build();
        DestroyableLabel requiredLabel = LabelBuilder.builder()
                .withStyleClass("required")
                .withText("blueprint.header.required")
                .build();
        //xor?
        DestroyableLabel remainingLabel = LabelBuilder.builder()
                .withStyleClass("remaining")
                .withText("blueprint.header.remaining")
                .withVisibilityProperty(showRemaining.or(this.hoverProperty()).and(this.hoverProperty().and(showRemaining).not()))//xor?
                .withManagedProperty(showRemaining.or(this.hoverProperty()).and(this.hoverProperty().and(showRemaining).not()))//xor?
                .build();
        DestroyableLabel availableLabel = LabelBuilder.builder()
                .withStyleClass("available")
                .withText("blueprint.header.available")
                .withVisibilityProperty(remainingLabel.visibleProperty().not())
                .withManagedProperty(remainingLabel.visibleProperty().not())
                .build();
        HBox.setHgrow(requiredLabel, Priority.ALWAYS);
        HBox.setHgrow(remainingLabel, Priority.ALWAYS);
        HBox.setHgrow(availableLabel, Priority.ALWAYS);
        HBox.setHgrow(requiredAmountLabel, Priority.ALWAYS);
        HBox.setHgrow(remainingAmountLabel, Priority.ALWAYS);
        HBox.setHgrow(availableAmountLabel, Priority.ALWAYS);


        DestroyableHBox firstLine = BoxBuilder.builder()
                .withStyleClass("title-line")
                .withNodes(this.image, nameLabel)
                .buildHBox();
        DestroyableHBox secondLine = BoxBuilder.builder()
                .withStyleClass("amount-line")
                .withNodes(requiredLabel, this.requiredAmountLabel, new GrowingRegion(), this.availableAmountLabel, availableLabel, this.remainingAmountLabel, remainingLabel)
                .buildHBox();
        final DestroyableVBox text = BoxBuilder.builder()
                .withStyleClass("text-lines")
                .withNodes(firstLine, new GrowingRegion(), secondLine)
                .buildVBox();
        VBox.setVgrow(text, Priority.ALWAYS);
        this.getNodes().addAll(text, progressbar);

        installPopOver();

        updateStyle();
    }

    protected void installPopOver() {
        MaterialService.addMaterialInfoPopOver(this, this.getOdysseyMaterial(), true);
    }

    @SuppressWarnings("java:S2177")
    public void initEventHandling() {
        register(EventService.addListener(true, this, FlipRemainingAvailableEvent.class, flipRemainingAvailableEvent -> {
            if (Expansion.ODYSSEY.equals(flipRemainingAvailableEvent.getExpansion())) {
                this.showRemaining.set(flipRemainingAvailableEvent.isShowRemaining());
            }
        }));
        register(EventService.addListener(true, this, StorageEvent.class, evt -> {
            if (evt.getStoragePool().equals(StoragePool.SHIP) || evt.getStoragePool().equals(StoragePool.FLEETCARRIER)) {
                this.update();
            }
        }));
        //when we highlight a specific blueprint
        register(EventService.addListener(true, this, OdysseyWishlistHighlightEvent.class, event -> {
            if (event.isActive()) {
                this.blueprint.set(event.getBlueprint());
            } else {
                this.blueprint.set(null);
            }
            this.update();
        }));
        //search
        register(EventService.addListener(true, this, OdysseyWishlistSearchEvent.class, odysseyWishlistSearchEvent -> {
            this.currentSearchQuery = odysseyWishlistSearchEvent.getSearch().getQuery();
            this.updateStyle();
        }));

        register(EventService.addListener(true, this, WishlistHideCompletedEvent.class, event -> {
            if (Expansion.ODYSSEY.equals(event.getExpansion())) {
                hideCompleted.set(event.getHideCompleted());
            }
        }));
        register(EventService.addListener(true, this, OdysseyWishlistChangedEvent.class, event -> {
            update();
        }));
    }

    protected void update() {
        this.calculate();

        requiredAmountLabel.setText(maximum.equals(minimum) ? String.valueOf(required.get()) : required.get() + " (" + minimum + " - " + maximum + ")");
        availableAmountLabel.setText(availableFleetCarrier > 0 ? availableShip + " + " + availableFleetCarrier : availableShip.toString());
        remainingAmountLabel.setText(remaining.toString());
        //progressbar
        final int progressShip = Math.min(availableShip, this.maximum);
        final int progressFleetCarrier = Math.min(availableFleetCarrier, this.maximum - progressShip);
        final int progressTotal = Math.min(progressShip + progressFleetCarrier, this.maximum);
        this.presentShip.setValue(Math.max(0, progressShip));
        this.presentFleetCarrier.setValue(Math.max(0, progressFleetCarrier));
        this.missingForMinimum.setValue(Math.max(0, minimum - progressTotal));
        this.missingForRequired.setValue(Math.max(required.get() == 0 ? 1 : 0, required.get() - Math.max(minimum, progressTotal)));// required == 0 ? 1 : 0 to prevent div/0 resulting in high CPU load
        this.missingForMaximum.setValue(Math.max(0, maximum - Math.max(required.get(), progressTotal)));

        updateStyle();

    }

    private void updateStyle() {
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("filled"), availableShip >= required.get());
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("partial"), availableShip < required.get() && availableShip + availableFleetCarrier >= required.get());
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("search"), matchesSearch());
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("filter"), this.blueprint.get() != null && required.get() == 0);
    }

    private boolean matchesSearch() {
        return !this.currentSearchQuery.isEmpty()
                && (LocaleService.getLocalizedStringForCurrentLocale(this.odysseyMaterial.getLocalizationKey()).toLowerCase().contains(this.currentSearchQuery.toLowerCase())
                || LocaleService.getLocalizedStringForCurrentLocale(this.odysseyMaterial.getStorageType().getLocalizationKey()).toLowerCase().contains(this.currentSearchQuery.toLowerCase()));
    }

    @SuppressWarnings("java:S6205")
    private void initImage() {
        final ResizableImageViewBuilder imageViewBuilder = ResizableImageViewBuilder.builder()
                .withStyleClass("image");
        switch (OdysseyStorageType.forMaterial(this.odysseyMaterial)) {
            case DATA -> imageViewBuilder.withImage("/images/material/data.png");
            case GOOD -> imageViewBuilder.withImage("/images/material/good.png");
            case ASSET -> {
                switch (((Asset) this.odysseyMaterial).getType()) {
                    case TECH -> imageViewBuilder.withImage("/images/material/tech.png");
                    case CIRCUIT -> imageViewBuilder.withImage("/images/material/circuit.png");
                    case CHEMICAL -> imageViewBuilder.withImage("/images/material/chemical.png");
                }
            }
            case CONSUMABLE -> imageViewBuilder.withImage("/images/material/unknown.png");
            case OTHER -> throw new IllegalArgumentException("StorageType Other must use MissionIngredient class");
        }
        this.image = imageViewBuilder.build();
    }

    @SuppressWarnings("unchecked")
    private void calculate() {
        defaults();
        final Optional<Wishlist> odysseyWishlist = APPLICATION_STATE.getPreferredCommander()
                .map(commander -> WishlistService.getOdysseyWishlists(commander).getSelectedWishlist());

        if (blueprint.get() == null) {
            odysseyWishlist.ifPresent(wishlist ->
                    wishlist.getItems().stream()
                            .filter(OdysseyWishlistBlueprint::isVisible)
                            .forEach(this::count));
        } else {
            count(blueprint.get());
        }
        remaining = Math.max(0, required.get() - availableShip);
        if (blueprint.get() == null) {
            requiredFull.set(required.get());
            remainingFull = remaining;
        }
        completed.set(remainingFull.equals(0));
    }

    private void defaults() {
        minimum = 0;
        required.set(0);
        maximum = 0;

        availableShip = StorageService.getMaterialCount(odysseyMaterial, AmountType.SHIPLOCKER);
        availableFleetCarrier = StorageService.getMaterialCount(odysseyMaterial, AmountType.FLEETCARRIER);
        remaining = 0;
    }

    private void count(OdysseyWishlistBlueprint wishlistItem) {
        final OdysseyBlueprint bp = OdysseyBlueprintConstants.getRecipe(wishlistItem.getRecipeName());
        add(bp);
    }

    private void add(OdysseyBlueprint blueprint) {
        Map<OdysseyMaterial, Integer> materials = blueprint.getMaterialCollection(this.odysseyMaterial.getClass());
        if (materials.isEmpty() || !materials.containsKey(this.odysseyMaterial)) return;

        final Integer amount = materials.get(this.odysseyMaterial);
        minimum += amount;
        required.set(required.get() + amount);
        maximum += amount;

    }
}
