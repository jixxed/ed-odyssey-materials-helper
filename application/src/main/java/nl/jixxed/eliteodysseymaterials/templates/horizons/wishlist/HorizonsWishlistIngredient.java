package nl.jixxed.eliteodysseymaterials.templates.horizons.wishlist;

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
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.*;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.components.EdAwesomeIconViewPane;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIconView;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.SegmentType;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.TypeSegment;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.TypeSegmentView;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.util.*;

@Slf4j
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class HorizonsWishlistIngredient extends DestroyableVBox implements DestroyableComponent, DestroyableEventTemplate {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    @Getter
    @EqualsAndHashCode.Include
    private final HorizonsMaterial horizonsMaterial;

    private EdAwesomeIconViewPane image;
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
    private List<PathItem<HorizonsBlueprintName>> pathItems = new ArrayList<>();
    final BooleanProperty showRemaining;
    final BooleanProperty hideCompleted;
    final BooleanProperty completed = new SimpleBooleanProperty(false);
    private String currentSearchQuery = "";
    ObjectProperty<HorizonsWishlistBlueprint> blueprint = new SimpleObjectProperty<>();
    private int quantityOverride = -1;

    HorizonsWishlistIngredient(final HorizonsMaterial horizonsMaterial) {
        this.horizonsMaterial = horizonsMaterial;
        this.hideCompleted = new SimpleBooleanProperty(PreferencesService.getPreference("blueprint.horizons.hide.completed", false));
        this.showRemaining = new SimpleBooleanProperty(PreferencesService.getPreference(PreferenceConstants.FLIP_WISHLIST_REMAINING_AVAILABLE_HORIZONS, Boolean.FALSE));
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
                .withText(this.horizonsMaterial.getLocalizationKey())
                .build();
        DestroyableLabel categoryLabel = LabelBuilder.builder()
                .withStyleClass("category")
                .withText(this.horizonsMaterial.getMaterialType().getLocalizationKey())
                .build();
        VBox.setVgrow(nameLabel, Priority.ALWAYS);
        HBox.setHgrow(nameLabel, Priority.ALWAYS);
        VBox.setVgrow(categoryLabel, Priority.ALWAYS);
        HBox.setHgrow(categoryLabel, Priority.ALWAYS);
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
                .withNodes(this.image, BoxBuilder.builder().withNodes(nameLabel, categoryLabel).buildVBox())
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
        MaterialService.addMaterialInfoPopOver(this, this.getHorizonsMaterial(), true, () -> this.maximum);
    }

    @SuppressWarnings("java:S2177")
    public void initEventHandling() {
        register(EventService.addListener(true, this, FlipRemainingAvailableEvent.class, flipRemainingAvailableEvent -> {
            if (Expansion.HORIZONS.equals(flipRemainingAvailableEvent.getExpansion())) {
                this.showRemaining.set(flipRemainingAvailableEvent.isShowRemaining());
            }
        }));
        register(EventService.addListener(true, this, StorageEvent.class, evt -> {
            if (evt.getStoragePool().equals(StoragePool.SHIP) || evt.getStoragePool().equals(StoragePool.FLEETCARRIER) || evt.getStoragePool().equals(StoragePool.SQUADRONCARRIER)) {
                this.update();
            }
        }));
        //when we highlight a specific blueprint
        register(EventService.addListener(true, this, HorizonsWishlistHighlightEvent.class, event -> {
            if (event.isActive()) {
                this.quantityOverride = event.getQuantity();
                this.blueprint.set(event.getBlueprint());
            } else {
                this.quantityOverride = -1;
                this.blueprint.set(null);
            }
            this.update();
        }));
        //search
        register(EventService.addListener(true, this, HorizonsWishlistSearchEvent.class, horizonsWishlistSearchEvent -> {
            this.currentSearchQuery = horizonsWishlistSearchEvent.getSearch().getQuery();
            this.updateStyle();
        }));

        register(EventService.addListener(true, this, WishlistHideCompletedEvent.class, event -> {
            if (Expansion.HORIZONS.equals(event.getExpansion())) {
                hideCompleted.set(event.getHideCompleted());
            }
        }));
        register(EventService.addListener(true, this, HorizonsWishlistChangedEvent.class, event -> {
            update();
        }));
        register(EventService.addListener(true, this, EngineerPinEvent.class, _ -> {
            this.update();
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
                && (LocaleService.getLocalizedStringForCurrentLocale(this.horizonsMaterial.getLocalizationKey()).toLowerCase().contains(this.currentSearchQuery.toLowerCase())
                || LocaleService.getLocalizedStringForCurrentLocale(this.horizonsMaterial.getMaterialType().getLocalizationKey()).toLowerCase().contains(this.currentSearchQuery.toLowerCase()));
    }

    @SuppressWarnings("java:S6205")
    private void initImage() {
        if (this.horizonsMaterial instanceof Commodity commodity) {
             this.image = EdAwesomeIconViewPaneBuilder.builder()
                    .withStyleClass("image")
                    .withIcons(Arrays.stream(commodity.getCommodityType().getIcons()).map(EdAwesomeIconView::new).toArray(EdAwesomeIconView[]::new))
                    .build();
        } else {
            this.image = EdAwesomeIconViewPaneBuilder.builder()
                    .withStyleClass("image")
                    .withIcons(new EdAwesomeIconView(this.horizonsMaterial.getRarity().getIcon()))
                    .build();
        }
    }

    @SuppressWarnings("unchecked")
    private void calculate() {
        defaults();
        final Optional<HorizonsWishlist> horizonsWishlist = APPLICATION_STATE.getPreferredCommander()
                .map(commander -> WishlistService.getHorizonsWishlists(commander).getSelectedWishlist());
        pathItems = horizonsWishlist
                .map(wishlist -> PathService.calculateHorizonsShortestPath((List<HorizonsWishlistBlueprint>) (List<?>) wishlist.getItems().stream().filter(WishlistBlueprint::isVisible).toList()))
                .orElse(Collections.emptyList());
        if (blueprint.get() == null) {
            horizonsWishlist.ifPresent(wishlist ->
                    wishlist.getItems().stream()
                            .map(HorizonsWishlistBlueprint.class::cast)
                            .filter(HorizonsWishlistBlueprint::isVisible)
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
        if (this.horizonsMaterial instanceof Commodity commodity) {
            availableShip = StorageService.getCommodityCount(commodity, StoragePool.SHIP);
            availableFleetCarrier = StorageService.getCommodityCount(commodity, StoragePool.FLEETCARRIER) + StorageService.getCommodityCount(commodity, StoragePool.SQUADRONCARRIER);
        } else {
            availableShip = StorageService.getMaterialCount(horizonsMaterial);
            availableFleetCarrier = 0;
        }
        remaining = 0;
    }

    private void count(HorizonsWishlistBlueprint wishlistItem) {
        if (wishlistItem instanceof HorizonsModuleWishlistBlueprint moduleWishlistBlueprint) {
            moduleWishlistBlueprint.getPercentageToComplete().forEach((grade, percentage) -> {
                final HorizonsBlueprint bp = (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipe(moduleWishlistBlueprint.getRecipeName(), moduleWishlistBlueprint.getBlueprintType(), grade);
                add(bp, wishlistItem.getQuantity(), percentage, hasHigherGradeConfigured(grade, moduleWishlistBlueprint.getPercentageToComplete()));
            });
            if (moduleWishlistBlueprint.getExperimentalEffect() != null) {
                final HorizonsBlueprint bpex = (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipe(moduleWishlistBlueprint.getRecipeName(), moduleWishlistBlueprint.getExperimentalEffect(), null);
                add(bpex, wishlistItem.getQuantity());
            }
        } else {
            final HorizonsBlueprint bp = (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipe(wishlistItem.getRecipeName(), WishlistService.getBlueprintType(wishlistItem), WishlistService.getBlueprintGrade(wishlistItem));
            add(bp, wishlistItem.getQuantity());
        }
    }

    private boolean hasHigherGradeConfigured(final HorizonsBlueprintGrade grade, final Map<HorizonsBlueprintGrade, Double> percentageToComplete) {
        return percentageToComplete.entrySet().stream()
                .filter(e -> e.getKey().getGrade() > grade.getGrade())
                .anyMatch(e -> e.getValue() > 0D);
    }

    private void add(HorizonsBlueprint blueprint, int quantity, Double percentage, boolean hasHigherGradeOrIsMaxGrade) {
        Map<HorizonsMaterial, Integer> materials = blueprint.getMaterialCollection(this.horizonsMaterial.getClass());
        if (materials.isEmpty() || (percentage <= 0.2 && hasHigherGradeOrIsMaxGrade) || !materials.containsKey(this.horizonsMaterial))
            return;

        final Integer amount = materials.get(this.horizonsMaterial);
        if (blueprint instanceof HorizonsModuleBlueprint moduleBlueprint) {
            final Integer minRank = blueprint.getEngineers().stream()
                    .filter(engineer -> engineer != Engineer.REMOTE_WORKSHOP)
                    .map(eng -> ApplicationState.getInstance().getEngineerRank(eng))
                    .min(Comparator.comparingInt(Integer::intValue))
                    .orElse(0);
            final Integer maxRank = blueprint.getEngineers().stream()
                    .filter(engineer -> engineer != Engineer.REMOTE_WORKSHOP)
                    .map(eng -> ApplicationState.getInstance().getEngineerRank(eng))
                    .max(Comparator.comparingInt(Integer::intValue))
                    .orElse(0);

            final Engineer engineer = getCurrentEngineerForBlueprint(blueprint, pathItems).orElseGet(() -> getWorstEngineer(blueprint));
            minimum += (int) Math.ceil(amount * percentage * blueprint.getHorizonsBlueprintGrade().getNumberOfRolls(maxRank, moduleBlueprint.getHorizonsBlueprintType())) * (quantityOverride == -1 ? quantity : quantityOverride);
            required.set(required.get() + (int) Math.ceil(amount * percentage * blueprint.getHorizonsBlueprintGrade().getNumberOfRolls(engineer, moduleBlueprint.getHorizonsBlueprintName(), moduleBlueprint.getHorizonsBlueprintType())) * (quantityOverride == -1 ? quantity : quantityOverride));
            maximum += (int) Math.ceil(amount * percentage * blueprint.getHorizonsBlueprintGrade().getNumberOfRolls(minRank, moduleBlueprint.getHorizonsBlueprintType())) * (quantityOverride == -1 ? quantity : quantityOverride);
        } else {
            minimum += amount * (quantityOverride == -1 ? quantity : quantityOverride);
            required.set(required.get() + amount * (quantityOverride == -1 ? quantity : quantityOverride));
            maximum += amount * (quantityOverride == -1 ? quantity : quantityOverride);
        }
//        if (quantityOverride == -1) {
//            minimum *= quantity;
//            required.set(required.get() * quantity);
//            maximum *= quantity;
//        } else {
//            minimum *= quantityOverride;
//            required.set(required.get() * quantityOverride);
//            maximum *= quantityOverride;
//        }
    }

    private void add(HorizonsBlueprint blueprint, int quantity) {
        add(blueprint, quantity, 1D, false);
    }

    private Optional<Engineer> getCurrentEngineerForBlueprint(Blueprint<HorizonsBlueprintName> recipe, List<PathItem<HorizonsBlueprintName>> pathItems) {
        if (recipe == null) {
            return Optional.empty();
        }
        return pathItems.stream()
                .filter(pathItem -> pathItem.getRecipes().keySet().stream()
                        .anyMatch(bp -> bp.getBlueprintName().equals(recipe.getBlueprintName())
                                && ((HorizonsBlueprint) bp).getHorizonsBlueprintType().equals(((HorizonsBlueprint) recipe).getHorizonsBlueprintType())))
                .findFirst()
                .filter(pathItem -> !pathItem.getEngineer().equals(Engineer.UNKNOWN))
                .map(PathItem::getEngineer);
    }

    private Engineer getWorstEngineer(Blueprint<HorizonsBlueprintName> recipe) {
        if (!(recipe instanceof HorizonsModuleBlueprint)) {
            return null;
        }
        return ((HorizonsModuleBlueprint) recipe).getEngineers().stream()
                .min(Comparator.comparingInt(eng -> ApplicationState.getInstance().getEngineerRank(eng)))
                .orElse(null);

    }
}
