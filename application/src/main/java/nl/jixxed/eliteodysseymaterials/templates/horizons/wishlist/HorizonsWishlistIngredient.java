package nl.jixxed.eliteodysseymaterials.templates.horizons.wishlist;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.*;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.SegmentType;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.TypeSegment;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.TypeSegmentView;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.util.*;

@Slf4j
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class HorizonsWishlistIngredient extends DestroyableVBox implements DestroyableComponent, DestroyableEventTemplate {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    //    public static final String FILLED = "filled";
//    public static final String PARTIAL = "partial";
    @Getter
    @EqualsAndHashCode.Include
    private final HorizonsMaterial horizonsMaterial;
//    @Getter
//    @Setter
//    private Integer leftAmount;
//    private Integer rightAmount;

    private DestroyableResizableImageView image;
    private DestroyableLabel requiredAmountLabel;
    private DestroyableLabel availableAmountLabel;
    private DestroyableLabel remainingAmountLabel;
    //    private static final String INGREDIENT_FILLED_CLASS = "ingredient-filled";
//    private static final String INGREDIENT_UNFILLED_CLASS = "ingredient-unfilled";
//    private static final String INGREDIENT_FILLED_NOT_SHIPLOCKER_CLASS = "ingredient-filled-partial";

    private TypeSegment presentShip;
    private TypeSegment presentFleetCarrier;
    private TypeSegment missingForMinimum;
    private TypeSegment missingForRequired;
    private TypeSegment missingForMaximum;
    //    private Integer amountMinimum;
//    private Integer amountRequired;
//    private Integer amountMaximum;
    private Integer minimum = 0;
    @Getter
    private Integer required = 0;
    private Integer maximum = 0;
    private Integer availableShip = 0;
    private Integer availableFleetCarrier = 0;
    private Integer remaining = 0;
    private Integer remainingFull = 0;
    private List<PathItem<HorizonsBlueprintName>> pathItems = new ArrayList<>();
    final BooleanProperty showRemaining;
    final BooleanProperty hideCompleted;
    final BooleanProperty completed = new SimpleBooleanProperty(false);
    private String currentSearchQuery = "";
    private HorizonsWishlistBlueprint blueprint;

    HorizonsWishlistIngredient(final HorizonsMaterial horizonsMaterial) {
        this.horizonsMaterial = horizonsMaterial;
        this.hideCompleted = new SimpleBooleanProperty(PreferencesService.getPreference("blueprint.hide.completed", false));
        this.showRemaining = new SimpleBooleanProperty(PreferencesService.getPreference(PreferenceConstants.FLIP_WISHLIST_REMAINING_AVAILABLE_HORIZONS, Boolean.FALSE));
        calculate();

        initComponents();
        initEventHandling();
    }


    @SuppressWarnings("java:S2177")
    public void initComponents() {
        this.getStyleClass().add("wishlist-ingredient");
        this.getStyleClass().add("material");
//        this.hoverProperty().addListener((observable, oldValue, newValue) -> {
//            showAsHovered(newValue);
//        });
        this.visibleProperty().bind(hideCompleted.and(completed).not());//todo also managed
        this.managedProperty().bind(hideCompleted.and(completed).not());//todo also managed
        final int progressShip = Math.min(availableShip, this.maximum);
        final int progressFleetCarrier = Math.min(availableFleetCarrier, this.maximum - progressShip);
        final int progressTotal = Math.min(progressShip + progressFleetCarrier, this.maximum);
        this.presentShip = new TypeSegment(Math.max(0, progressShip), SegmentType.PRESENT_SHIP);
        this.presentFleetCarrier = new TypeSegment(Math.max(0, progressFleetCarrier), SegmentType.PRESENT_FLEET_CARRIER);
        this.missingForMinimum = new TypeSegment(Math.max(0, minimum - progressTotal), SegmentType.MISSING_FOR_MINIMUM);
        this.missingForRequired = new TypeSegment(Math.max(0, required - Math.max(minimum, progressTotal)), SegmentType.MISSING_FOR_REQUIRED);
        this.missingForMaximum = new TypeSegment(Math.max(0, maximum - Math.max(required, progressTotal)), SegmentType.MISSING_FOR_MAXIMUM);
        DestroyableSegmentedBar<TypeSegment> progressbar = SegmentedBarBuilder.builder(TypeSegment.class)
                .withStyleClass("ingredient-progressbar")
                .withOrientation(Orientation.HORIZONTAL)
                .withInfoNodeFactory(_ -> null)
                .withSegmentViewFactory(segment -> new TypeSegmentView(segment, false))
                .withSegments(presentShip, presentFleetCarrier, missingForMinimum, missingForRequired, missingForMaximum)
                .build();
//        this.segmentedBar.getStyleClass().add("ingredient-progressbar");
//        this.segmentedBar.setOrientation(Orientation.HORIZONTAL);
//        this.segmentedBar.setInfoNodeFactory(segment -> null);
//        green/red = current material count
//        dark grey = minimum requirement(all engineers G5) - current material count
//        light grey = current requirement(based on shortest path) - max(minimum requirement, current material count)
//        light green/red = maximum requirement(all engineers G1) - current requirement(based on shortest path)

//        this.segmentedBar.setSegmentViewFactory(segment -> new TypeSegmentView(segment, Map.of(
//                SegmentType.PRESENT, Color.rgb(255, 255, 255),
//                SegmentType.MISSING_FOR_MINIMUM, Color.rgb(128, 128, 128),
//                SegmentType.MISSING_FOR_REQUIRED, Color.rgb(192, 192, 192),
//                SegmentType.MISSING_FOR_MAXIMUM, Color.rgb(255, 255, 255)
//        ), false));

//        this.segmentedBar.getSegments().addAll(this.present, this.missingForMinimum, this.missingForRequired, this.missingForMaximum);
//        this.getRequiredAmountLabel().setText(getLeftAmountString());
        DestroyableLabel nameLabel = LabelBuilder.builder()
                .withStyleClass("name")
                .withText(this.horizonsMaterial.getLocalizationKey())
                .build();
        VBox.setVgrow(nameLabel, Priority.ALWAYS);
        HBox.setHgrow(nameLabel, Priority.ALWAYS);
        initImage();

        this.requiredAmountLabel = LabelBuilder.builder()
                .withStyleClass("quantity")
                .withNonLocalizedText(maximum.equals(minimum) ? required.toString() : required + " (" + minimum + " - " + maximum + ")")
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


//        this.requiredAmountLabel.setText(getLeftAmountString());
//        HBox.setHgrow(this.requiredAmountLabel, Priority.ALWAYS);
//        this.availableAmountLabel.setText(getRightAmountString());

        DestroyableHBox firstLine = BoxBuilder.builder()
                .withStyleClass("title-line")
                .withNodes(this.image, nameLabel)
                .buildHBox();
//        this.firstLine.addBinding(this.firstLine.prefHeightProperty(), this.nameLabel.heightProperty());
        DestroyableHBox secondLine = BoxBuilder.builder()
                .withStyleClass("amount-line")
                .withNodes(requiredLabel, this.requiredAmountLabel, new GrowingRegion(), this.availableAmountLabel, availableLabel, this.remainingAmountLabel, remainingLabel)
                .buildHBox();
        final DestroyableVBox text = BoxBuilder.builder()
                .withStyleClass("text-lines")
                .withNodes(firstLine, new GrowingRegion(), secondLine)
                .buildVBox();
        this.getNodes().addAll(text, progressbar);

        installPopOver();

        updateStyle();
    }

    protected void installPopOver() {
        MaterialService.addMaterialInfoPopOver(this, this.getHorizonsMaterial(), true);
    }

//    private void showAsHovered(final Boolean newValue) {
//        final Boolean showRemaining = !PreferencesService.getPreference(PreferenceConstants.FLIP_WISHLIST_REMAINING_AVAILABLE_HORIZONS, Boolean.FALSE);
//        if (showRemaining.equals(newValue) && getHorizonsMaterial() instanceof Commodity commodity && (required - (StorageService.getCommodityCount(commodity, StoragePool.SHIP))) > 0) {
//            this.getAvailableAmountLabel().setText(String.valueOf((required - StorageService.getCommodityCount(commodity, StoragePool.SHIP))));
//            setAvailableLabel(LocaleService.getStringBinding("blueprint.header.remaining"));
//        } else if (showRemaining.equals(newValue) && !(getHorizonsMaterial() instanceof Commodity) && (required - (StorageService.getMaterialCount(getHorizonsMaterial()))) > 0) {
//            this.getAvailableAmountLabel().setText(String.valueOf((required - StorageService.getMaterialCount(getHorizonsMaterial()))));
//            setAvailableLabel(LocaleService.getStringBinding("blueprint.header.remaining"));
//        } else {
//            setAvailableLabel(LocaleService.getStringBinding("blueprint.header.available"));
//            this.getAvailableAmountLabel().setText(this.getRightAmount().toString());
//        }
//    }

    @SuppressWarnings("java:S2177")
    public void initEventHandling() {
        register(EventService.addListener(true, this, FlipRemainingAvailableEvent.class, flipRemainingAvailableEvent -> {
            if (Expansion.HORIZONS.equals(flipRemainingAvailableEvent.getExpansion())) {
                this.showRemaining.set(flipRemainingAvailableEvent.isShowRemaining());
            }
        }));
        register(EventService.addListener(true, this, StorageEvent.class, evt -> {
            if (evt.getStoragePool().equals(StoragePool.SHIP) || evt.getStoragePool().equals(StoragePool.FLEETCARRIER)) {
                this.update();
            }
        }));
        //when we highlight a specific blueprint
        register(EventService.addListener(true, this, HorizonsWishlistHighlightEvent.class, event -> {
            if (event.isActive()) {
                this.blueprint = event.getBlueprint();
            } else {
                this.blueprint = null;
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
    }

    protected void update() {
        this.calculate();
//        final Integer materialCountShip;
//        final Integer materialCountBoth;
//        if (this.horizonsMaterial instanceof Commodity commodity) {
//            materialCountShip = StorageService.getCommodityCount(commodity, StoragePool.SHIP);
//            materialCountBoth = materialCountShip + StorageService.getCommodityCount(commodity, StoragePool.FLEETCARRIER);
//        } else {
//            materialCountBoth = StorageService.getMaterialCount(this.horizonsMaterial);
//            materialCountShip = materialCountBoth;
//        }
//        setRightAmount(materialCountBoth);
//        this.rightAmountLabel.setText(getRightAmountString());
//        if (materialCountBoth >= this.leftAmount && materialCountShip < this.leftAmount) {
//            this.pseudoClassStateChanged(PseudoClass.getPseudoClass(FILLED), true);
//            this.pseudoClassStateChanged(PseudoClass.getPseudoClass(PARTIAL), true);
//        } else if (materialCountBoth >= this.leftAmount) {
//            this.pseudoClassStateChanged(PseudoClass.getPseudoClass(FILLED), true);
//            this.pseudoClassStateChanged(PseudoClass.getPseudoClass(PARTIAL), false);
//        } else {
//            this.pseudoClassStateChanged(PseudoClass.getPseudoClass(FILLED), false);
//            this.pseudoClassStateChanged(PseudoClass.getPseudoClass(PARTIAL), false);
//        }
//        final Integer materialCountShip;
//        final Integer materialCountBoth;
//        if (getHorizonsMaterial() instanceof Commodity commodity) {
//            materialCountShip = StorageService.getCommodityCount(commodity, StoragePool.SHIP);
//            materialCountBoth = materialCountShip + StorageService.getCommodityCount(commodity, StoragePool.FLEETCARRIER);
//        } else {
//            materialCountBoth = StorageService.getMaterialCount(getHorizonsMaterial());
//            materialCountShip = materialCountBoth;
//        }

        requiredAmountLabel.setText(maximum.equals(minimum) ? required.toString() : required + " (" + minimum + " - " + maximum + ")");
        availableAmountLabel.setText(availableFleetCarrier > 0 ? availableShip + " + " + availableFleetCarrier : availableShip.toString());
        remainingAmountLabel.setText(remaining.toString());
        //progressbar
        final Integer progressShip = Math.min(availableShip, this.maximum);
        final Integer progressFleetCarrier = Math.min(availableFleetCarrier, this.maximum - progressShip);
        final Integer progressTotal = Math.min(progressShip + progressFleetCarrier, this.maximum);
        this.presentShip.setValue(Math.max(0, progressShip));
        this.presentFleetCarrier.setValue(Math.max(0, progressFleetCarrier));
        this.missingForMinimum.setValue(Math.max(0, minimum - progressTotal));
        this.missingForRequired.setValue(Math.max(0, required - Math.max(minimum, progressTotal)));
        this.missingForMaximum.setValue(Math.max(0, maximum - Math.max(required, progressTotal)));

        updateStyle();


//        final Integer progress = Math.min(availableShip, maximum);
//        this.present.setValue(progress);
//        this.missingForMinimum.setValue(Math.max(0, minimum - progress));
//        this.missingForRequired.setValue(Math.max(0, required - Math.max(minimum, progress)));
//        this.missingForMaximum.setValue(Math.max(0, maximum - Math.max(required, progress)));

//        final Boolean showRemaining = !PreferencesService.getPreference(PreferenceConstants.FLIP_WISHLIST_REMAINING_AVAILABLE_HORIZONS, Boolean.FALSE);
//
//        this.setRightAmount(materialCountBoth);
//        this.getStyleClass().removeAll(INGREDIENT_FILLED_CLASS, INGREDIENT_UNFILLED_CLASS, INGREDIENT_FILLED_NOT_SHIPLOCKER_CLASS);
//        if (materialCountBoth >= required && materialCountShip < required) {
//            this.getStyleClass().addAll(INGREDIENT_FILLED_NOT_SHIPLOCKER_CLASS);
//        } else if (this.getRightAmount() >= required) {
//            this.getStyleClass().addAll(INGREDIENT_FILLED_CLASS);
//        } else {
//            this.getStyleClass().addAll(INGREDIENT_UNFILLED_CLASS);
//        }
//        if (this.present != null) {
//            final Integer progress = Math.min(this.getRightAmount(), maximum);
////            final int progress = Math.min(this.getLeftAmount(), this.getRightAmount());
//            this.present.setValue(progress);
//            this.missingForMinimum.setValue(Math.max(0, minimum - progress));
//            this.missingForRequired.setValue(Math.max(0, required - Math.max(minimum, progress)));
//            this.missingForMaximum.setValue(Math.max(0, maximum - Math.max(required, progress)));
////            this.notPresent.setValue(Math.max(this.getLeftAmount() - progress, 0));
//            if (Math.max(required - progress, 0) > 0) {
//                this.segmentedBar.setSegmentViewFactory(segment -> new TypeSegmentView(segment, Map.of(
//                        SegmentType.PRESENT, Color.web("#ff7c7c"),
//                        SegmentType.MISSING_FOR_MINIMUM, Color.rgb(128, 128, 128),
//                        SegmentType.MISSING_FOR_REQUIRED, Color.rgb(192, 192, 192),
//                        SegmentType.MISSING_FOR_MAXIMUM, Color.web("#FFC8C8")
//                ), false));
//            } else {
//                this.segmentedBar.setSegmentViewFactory(segment -> new TypeSegmentView(segment, Map.of(
//                        SegmentType.PRESENT, Color.web("#89d07f"),
//                        SegmentType.MISSING_FOR_MINIMUM, Color.rgb(128, 128, 128),
//                        SegmentType.MISSING_FOR_REQUIRED, Color.rgb(192, 192, 192),
//                        SegmentType.MISSING_FOR_MAXIMUM, Color.web("#BED3BB")
//                ), false));
//            }
//        }
    }

    private void updateStyle() {
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("filled"), availableShip >= required);
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("partial"), availableShip < required && availableShip + availableFleetCarrier >= required);
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("search"), matchesSearch());
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("filter"), this.blueprint != null && required == 0);
    }

    private boolean matchesSearch() {
        return !this.currentSearchQuery.isEmpty()
                && (LocaleService.getLocalizedStringForCurrentLocale(this.horizonsMaterial.getLocalizationKey()).toLowerCase().contains(this.currentSearchQuery.toLowerCase())
                || LocaleService.getLocalizedStringForCurrentLocale(this.horizonsMaterial.getMaterialType().getLocalizationKey()).toLowerCase().contains(this.currentSearchQuery.toLowerCase()));
    }

//    void searchHighlight(final boolean enable) {
//        if (enable) {
//            this.getStyleClass().add("wishlist-search-highlight");
//        } else {
//            this.getStyleClass().removeAll("wishlist-search-highlight");
//        }
//        update();
//    }
//
//    void highlight(final boolean enable, final WishlistMaterial amount) {
//        if (enable) {
//            this.getStyleClass().add("wishlist-highlight");
//            setLeftAmount(amount.getRequired());
//            this.amountMinimum = amount.getMinimum();
//            this.amountMaximum = amount.getMaximum();
//            this.amountRequired = amount.getRequired();
//        } else {
//            this.getStyleClass().removeAll("wishlist-highlight");
//            setLeftAmount(required);
//            this.amountMinimum = minimum;
//            this.amountMaximum = maximum;
//            this.amountRequired = required;
//        }
//        this.getRequiredAmountLabel().setText(getLeftAmountString());
//        update();
//    }
//
//    void lowlight(final boolean enable) {
//        if (enable) {
//            this.getStyleClass().add("wishlist-lowlight");
//        } else {
//            this.getStyleClass().removeAll("wishlist-lowlight");
//        }
//    }

//
//    protected String getLeftAmountString() {
//        if (Objects.equals(this.amountMinimum, this.amountMaximum)) {
//            return this.amountRequired != null ? this.amountRequired.toString() : "0";
//        }
//        return this.amountRequired + " (" + this.amountMinimum + " - " + this.amountMaximum + ")";
//    }
//
//    protected String getRightAmountString() {
//        return this.rightAmount.toString();
//    }

    @SuppressWarnings("java:S6205")
    private void initImage() {
        if (this.horizonsMaterial instanceof Commodity commodity) {
            this.image = ResizableImageViewBuilder.builder()
                    .withStyleClass("image")
                    .withImage(commodity.getCommodityType().getImagePath())
                    .build();
        } else {
            this.image = ResizableImageViewBuilder.builder()
                    .withStyleClass("image")
                    .withImage(this.horizonsMaterial.getRarity().getImagePath())
                    .build();
        }
    }

//    protected void setRightAmount(final Integer rightAmount) {
//        this.rightAmount = rightAmount;
//    }
//
//
//    protected Label getRequiredAmountLabel() {
//        return this.requiredAmountLabel;
//    }
//
//    protected Integer getRightAmount() {
//        return this.rightAmount;
//    }
//
//    protected Label getAvailableAmountLabel() {
//        return this.availableAmountLabel;
//    }
//
//    protected void setAvailableLabel(final StringBinding availableLabel) {
//        this.availableLabel.addBinding(this.availableLabel.textProperty(), availableLabel);
//    }

    private void calculate() {
        defaults();
        final Optional<HorizonsWishlist> horizonsWishlist = APPLICATION_STATE.getPreferredCommander()
                .map(commander -> WishlistService.getHorizonsWishlists(commander).getSelectedWishlist());
        pathItems = horizonsWishlist
                .map(wishlist -> PathService.calculateHorizonsShortestPath2((List<HorizonsWishlistBlueprint>) (List<?>) wishlist.getItems()))
                .orElse(Collections.emptyList());
        if (blueprint == null) {
            horizonsWishlist.ifPresent(wishlist ->
                    wishlist.getItems().stream()
                            .map(HorizonsWishlistBlueprint.class::cast)
                            .filter(HorizonsWishlistBlueprint::isVisible)
                            .forEach(this::count));
        } else {
            count(blueprint);
        }
        remaining = Math.max(0, required - availableShip);
        if (blueprint == null) {
            remainingFull = remaining;
        }
        completed.set(remainingFull.equals(0));
    }

    private void defaults() {
        minimum = 0;
        required = 0;
        maximum = 0;
        if (this.horizonsMaterial instanceof Commodity commodity) {
            availableShip = StorageService.getCommodityCount(commodity, StoragePool.SHIP);
            availableFleetCarrier = StorageService.getCommodityCount(commodity, StoragePool.FLEETCARRIER);
        } else {
            availableShip = StorageService.getMaterialCount(horizonsMaterial);
            availableFleetCarrier = 0;
        }
        remaining = 0;
    }

    private void count(HorizonsWishlistBlueprint wishlistItem) {
        if (wishlistItem instanceof HorizonsModuleWishlistBlueprint moduleWishlistBlueprint) {
            moduleWishlistBlueprint.getPercentageToComplete().forEach((grade, percentage) -> {
                final HorizonsBlueprint blueprint = (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipe(moduleWishlistBlueprint.getRecipeName(), moduleWishlistBlueprint.getBlueprintType(), grade);
                add(blueprint, percentage);
            });
        } else {
            final HorizonsBlueprint blueprint = (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipe(wishlistItem.getRecipeName(), WishlistService.getBlueprintType(wishlistItem), WishlistService.getBlueprintGrade(wishlistItem));
            add(blueprint);
        }
    }

    private void add(HorizonsBlueprint blueprint, Double percentage) {
        Map<HorizonsMaterial, Integer> materials = blueprint.getMaterialCollection(this.horizonsMaterial.getClass());
        if (materials.isEmpty() || percentage < 0.2 || !materials.containsKey(this.horizonsMaterial)) return;

        final Integer amount = materials.get(this.horizonsMaterial);
        if (blueprint instanceof HorizonsModuleBlueprint moduleBlueprint) {
            final Integer minRank = blueprint.getEngineers().stream()
                    .map(eng -> ApplicationState.getInstance().getEngineerRank(eng))
                    .min(Comparator.comparingInt(Integer::intValue))
                    .orElse(0);
            final Integer maxRank = blueprint.getEngineers().stream()
                    .map(eng -> ApplicationState.getInstance().getEngineerRank(eng))
                    .max(Comparator.comparingInt(Integer::intValue))
                    .orElse(0);

            final Engineer engineer = getCurrentEngineerForBlueprint(blueprint, pathItems).orElseGet(() -> getWorstEngineer(blueprint));
            minimum += (int) Math.ceil(amount * percentage * blueprint.getHorizonsBlueprintGrade().getNumberOfRolls(maxRank, moduleBlueprint.getHorizonsBlueprintType()));
            required += (int) Math.ceil(amount * percentage * blueprint.getHorizonsBlueprintGrade().getNumberOfRolls(engineer, moduleBlueprint.getHorizonsBlueprintType()));
            maximum += (int) Math.ceil(amount * percentage * blueprint.getHorizonsBlueprintGrade().getNumberOfRolls(minRank, moduleBlueprint.getHorizonsBlueprintType()));
        } else {
            minimum += amount;
            required += amount;
            maximum += amount;
        }

    }

    private void add(HorizonsBlueprint blueprint) {
        add(blueprint, 1D);
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

//    private Engineer getBestEngineer(Blueprint<HorizonsBlueprintName> recipe) {
//        if (!(recipe instanceof HorizonsModuleBlueprint)) {
//            return null;
//        }
//        return ((HorizonsModuleBlueprint) recipe).getEngineers().stream().max(Comparator.comparingInt(eng -> ApplicationState.getInstance().getEngineerRank(eng))).orElse(null);
//
//    }

    private Engineer getWorstEngineer(Blueprint<HorizonsBlueprintName> recipe) {
        if (!(recipe instanceof HorizonsModuleBlueprint)) {
            return null;
        }
        return ((HorizonsModuleBlueprint) recipe).getEngineers().stream()
                .min(Comparator.comparingInt(eng -> ApplicationState.getInstance().getEngineerRank(eng)))
                .orElse(null);

    }
}
