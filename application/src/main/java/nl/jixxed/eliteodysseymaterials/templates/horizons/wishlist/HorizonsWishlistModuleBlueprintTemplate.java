package nl.jixxed.eliteodysseymaterials.templates.horizons.wishlist;

import javafx.animation.FadeTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.helper.AnchorPaneHelper;
import nl.jixxed.eliteodysseymaterials.service.ImageService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.components.CompletionSlider;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;
import nl.jixxed.eliteodysseymaterials.templates.generic.Ingredient;
import nl.jixxed.eliteodysseymaterials.templates.generic.WishlistBlueprintTemplate;
import org.controlsfx.control.PopOver;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class HorizonsWishlistModuleBlueprintTemplate extends VBox implements WishlistBlueprintTemplate<HorizonsBlueprintName> {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final String VISIBLE_STYLE_CLASS = "visible";
    private static final String WISHLIST_VISIBLE_ICON_STYLE_CLASS = "wishlist-visible-icon";
    private static int counter = 0;

    private boolean visible;
    private final Integer sequenceID;
    private final HorizonsModuleWishlistBlueprint wishlistBlueprint;
    private final BlueprintCategory blueprintCategory;
    private Map<Blueprint<HorizonsBlueprintName>, Double> blueprints;
    @Getter
    private final String wishlistUUID;

    private Button visibilityButton;
    private DestroyableResizableImageView visibilityImage;
    private Label wishlistRecipeName;
    private Button removeBlueprint;
    private Button toggleControls;
    private final Set<HorizonsWishlistIngredient> wishlistIngredients = new HashSet<>();
    private final Set<HorizonsWishlistIngredient> otherIngredients = new HashSet<>();
    private EventListener<StorageEvent> storageEventEventListener;
    private Tooltip tooltip;

    private Engineer engineer;
    private List<CompletionSlider> sliders = new ArrayList<>();

    HorizonsWishlistModuleBlueprintTemplate(final String wishlistUUID, final HorizonsModuleWishlistBlueprint wishlistBlueprint) {
        this.wishlistUUID = wishlistUUID;
        this.wishlistBlueprint = wishlistBlueprint;
        this.sequenceID = counter++;
        this.blueprintCategory = HorizonsBlueprintConstants.getRecipeCategory(wishlistBlueprint.getRecipeName(), false);
        this.blueprints = wishlistBlueprint.getPercentageToComplete().entrySet().stream().map(gradePercentage -> Map.entry(HorizonsBlueprintConstants.getRecipe(getRecipeName(), getBlueprintType(), gradePercentage.getKey()), gradePercentage.getValue())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        initComponents();
        initEventHandling();
    }

    private void initComponents() {
        final HBox header = BoxBuilder.builder().buildHBox();
        if (!this.wishlistUUID.equals(Wishlist.ALL.getUuid())) {
            this.visibilityImage = ResizableImageViewBuilder.builder()
                    .withStyleClass("wishlist-visible-image")
                    .withImage("/images/other/visible_blue.png")
                    .build();
            this.visibilityButton = ButtonBuilder.builder()
                    .withStyleClasses(WISHLIST_VISIBLE_ICON_STYLE_CLASS, VISIBLE_STYLE_CLASS)
                    .withOnAction(event -> setVisibility(!this.visible))
                    .withGraphic(this.visibilityImage)
                    .build();
            setVisibility(this.wishlistBlueprint.isVisible());
            header.getChildren().addAll(this.visibilityButton);
        } else {
            setVisibility(true);
        }
        this.wishlistRecipeName = LabelBuilder.builder()
                .withStyleClass("wishlist-label")
                .withText(LocaleService.getStringBinding("wishlist.blueprint.horizons.title.module",
                        LocaleService.LocalizationKey.of(this.wishlistBlueprint.getRecipeName().getLocalizationKey()),
                        LocaleService.LocalizationKey.of(this.wishlistBlueprint.getBlueprintType().getLocalizationKey()),
                        this.wishlistBlueprint.getPercentageToComplete().keySet().stream().sorted(Comparator.comparing(HorizonsBlueprintGrade::getGrade)).map(HorizonsBlueprintGrade::getGrade).map(String::valueOf).collect(Collectors.joining(","))))
                .withOnMouseClicked(event -> EventService.publish(new HorizonsBlueprintClickEvent(HorizonsBlueprintConstants.getRecipe(getRecipeName(), getBlueprintType(), this.wishlistBlueprint.getPercentageToComplete().keySet().stream().findFirst().orElse(HorizonsBlueprintGrade.NONE)))))
                .withHoverProperty((observable, oldValue, newValue) -> {


                    this.wishlistIngredients.forEach(wishlistIngredient -> {
                        Integer requiredAmount =  (this.blueprints.keySet().stream().map(bp -> ((HorizonsBlueprint) bp).getRequiredAmount(wishlistIngredient.getHorizonsMaterial(), engineer)).reduce(0, Integer::sum));
                        Integer minimumAmount =  (this.blueprints.keySet().stream().map(bp -> ((HorizonsBlueprint) bp).getMinimumAmount(wishlistIngredient.getHorizonsMaterial())).reduce(0, Integer::sum));
                        Integer maximumAmount =  (this.blueprints.keySet().stream().map(bp -> ((HorizonsBlueprint) bp).getMaximumAmount(wishlistIngredient.getHorizonsMaterial())).reduce(0, Integer::sum));
                        wishlistIngredient.highlight(newValue, new WishlistMaterial(minimumAmount, requiredAmount, maximumAmount));
                    });
                    this.otherIngredients.forEach(wishlistIngredient -> wishlistIngredient.lowlight(newValue));
                    this.highlight(newValue);
                })
                .build();
        final AnchorPane anchorPane = new AnchorPane(this.wishlistRecipeName);
        AnchorPaneHelper.setAnchor(this.wishlistRecipeName, 0D, 0D, 0D, 0D);
        HBox.setHgrow(anchorPane, Priority.ALWAYS);
        header.getChildren().addAll(anchorPane);

        if (!this.wishlistUUID.equals(Wishlist.ALL.getUuid())) {
            this.removeBlueprint = ButtonBuilder.builder()
                    .withStyleClass("wishlist-item-button").withNonLocalizedText("X")
                    .withOnAction(event -> remove())
                    .build();
            final AtomicReference<PopOver> popOverRef = new AtomicReference<>();
            this.toggleControls = ButtonBuilder.builder()
                    .withStyleClass("wishlist-item-button").withNonLocalizedText("\u25BC")
                    .withOnAction(event -> {
                        if (popOverRef.get() == null || !popOverRef.get().isShowing()) {
                            final HBox[] gradeControls = HorizonsBlueprintConstants.getBlueprintGrades(this.wishlistBlueprint.getRecipeName(), this.wishlistBlueprint.getBlueprintType()).stream().sorted(Comparator.comparing(HorizonsBlueprintGrade::getGrade)).map(grade ->
                                    {
                                        CompletionSlider completionSlider = new CompletionSlider(0D, 100D, this.wishlistBlueprint.getPercentageToComplete().getOrDefault(grade, 0D) * 100D);
                                        sliders.add(completionSlider);
                                        completionSlider.setChangeListener(percentage -> {
                                            this.wishlistBlueprint.setBlueprintGradePercentageToComplete(grade, percentage / 100D);
                                            this.blueprints = this.wishlistBlueprint.getPercentageToComplete().entrySet().stream().map(gradePercentage -> Map.entry(HorizonsBlueprintConstants.getRecipe(getRecipeName(), getBlueprintType(), gradePercentage.getKey()), gradePercentage.getValue())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                                            modify();
                                            EventService.publish(new HorizonsWishlistBlueprintAlteredEvent(this.wishlistUUID));
                                            this.wishlistRecipeName.textProperty().bind(LocaleService.getStringBinding("wishlist.blueprint.horizons.title.module",
                                                    LocaleService.LocalizationKey.of(this.wishlistBlueprint.getRecipeName().getLocalizationKey()),
                                                    LocaleService.LocalizationKey.of(this.wishlistBlueprint.getBlueprintType().getLocalizationKey()),
                                                    this.wishlistBlueprint.getPercentageToComplete().keySet().stream().sorted(Comparator.comparing(HorizonsBlueprintGrade::getGrade)).map(HorizonsBlueprintGrade::getGrade).map(String::valueOf).collect(Collectors.joining(","))));
                                            final Craftability craftability = HorizonsBlueprintConstants.getCraftability(getRecipeName(), getBlueprintType(), this.wishlistBlueprint.getPercentageToComplete(), engineer);
                                            this.canCraft(craftability);
                                        });
//                                        final ButtonIntField buttonIntField = new ButtonIntField(0, 15, this.wishlistBlueprint.getBlueprintGradeRolls().getOrDefault(grade, 0));
//                                        buttonIntField.addHandlerOnValidChange(rolls -> APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
//                                            this.wishlistBlueprint.setBlueprintGradeRollsFor(grade, rolls);
//                                            this.blueprints = this.wishlistBlueprint.getBlueprintGradeRolls().entrySet().stream().flatMap(gradeRolls -> IntStream.range(0, gradeRolls.getValue()).mapToObj(value -> HorizonsBlueprintConstants.getRecipe(getRecipeName(), getBlueprintType(), gradeRolls.getKey()))).toList();
//                                            modify();
//                                            EventService.publish(new HorizonsWishlistBlueprintAlteredEvent(this.wishlistUUID));
//                                            this.wishlistRecipeName.textProperty().bind(LocaleService.getStringBinding("wishlist.blueprint.horizons.title.module",
//                                                    LocaleService.LocalizationKey.of(this.wishlistBlueprint.getRecipeName().getLocalizationKey()),
//                                                    LocaleService.LocalizationKey.of(this.wishlistBlueprint.getBlueprintType().getLocalizationKey()),
//                                                    this.wishlistBlueprint.getBlueprintGradeRolls().keySet().stream().sorted(Comparator.comparing(HorizonsBlueprintGrade::getGrade)).map(HorizonsBlueprintGrade::getGrade).map(String::valueOf).collect(Collectors.joining(","))));
//                                            final Craftability craftability = HorizonsBlueprintConstants.getCraftability(getRecipeName(), getBlueprintType(), this.wishlistBlueprint.getBlueprintGradeRolls());
//                                            this.canCraft(craftability);
//                                        }));
//                                        buttonIntField.getStyleClass().add("wishlist-rolls-select");
                                        final DestroyableLabel label = LabelBuilder.builder().withStyleClass("wishlist-rolls-label").withNonLocalizedText(String.valueOf(grade.getGrade())).build();
//                                        final AnchorPane anchorPane2 = new AnchorPane(label);
//                                        AnchorPaneHelper.setAnchor(label, 0D, 0D, 0D, 0D);
                                        return BoxBuilder.builder().withNodes(
                                                label,
                                                completionSlider
                                        ).buildHBox();
                                    })
                                    .toArray(HBox[]::new);

                            final VBox grades = BoxBuilder.builder().withStyleClasses("grade-selects").withNodes(gradeControls).buildVBox();
                            final VBox gradePopOverContent = BoxBuilder.builder().withStyleClass("popover-menubutton-box").withNodes(
                                    LabelBuilder.builder().withStyleClass("grade-selects-title").withText(LocaleService.getStringBinding("wishlist.percentage.per.grade")).build(),
                                    LabelBuilder.builder().withStyleClass("grade-selects-explain").withText(LocaleService.getStringBinding("wishlist.percentage.per.grade.explain")).build(),
                                    grades).buildVBox();
                            final PopOver popOver = new PopOver(gradePopOverContent);
                            popOverRef.set(popOver);
                            popOver.setDetachable(false);
                            popOver.setHeaderAlwaysVisible(false);
                            popOver.getStyleClass().add("popover-menubutton-layout");
                            popOver.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
                            popOver.show(this.toggleControls);
                        } else {
                            popOverRef.get().hide();
                            popOverRef.set(null);
                        }
                    })
                    .build();

            header.getChildren().addAll(this.toggleControls, this.removeBlueprint);
        }
        this.getChildren().addAll(header);
        this.getStyleClass().add("wishlist-item");


        this.tooltip = TooltipBuilder.builder()
                .withText(LocaleService.getToolTipStringBinding((HorizonsEngineeringBlueprint) HorizonsBlueprintConstants.getRecipe(getRecipeName(), getBlueprintType(), HorizonsBlueprintGrade.GRADE_1), "tab.wishlist.blueprint.tooltip"))
                .withShowDelay(Duration.millis(100))
                .build();
        Tooltip.install(this.wishlistRecipeName, this.tooltip);

        initFadeTransition();
        final Craftability craftability = HorizonsBlueprintConstants.getCraftability(getRecipeName(), getBlueprintType(), this.wishlistBlueprint.getPercentageToComplete(), engineer);
        this.canCraft(craftability);
    }

    @Override
    public void remove() {
        EventService.removeListener(this.storageEventEventListener);
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> EventService.publish(new HorizonsWishlistBlueprintEvent(commander, this.wishlistUUID, List.of(this.wishlistBlueprint), Action.REMOVED)));
    }

    private void modify() {
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> EventService.publish(new HorizonsWishlistBlueprintEvent(commander, this.wishlistUUID, List.of(this.wishlistBlueprint), Action.MODIFY)));
    }

    private void initFadeTransition() {
        final FadeTransition fadeTransition = new FadeTransition(Duration.millis(2000));
        fadeTransition.setNode(this);
        fadeTransition.setFromValue(0.3);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }

    private void initEventHandling() {
        this.storageEventEventListener = EventService.addListener(this, StorageEvent.class, storageEvent -> {
            final Craftability craftability = HorizonsBlueprintConstants.getCraftability(getRecipeName(), getBlueprintType(), this.wishlistBlueprint.getPercentageToComplete(), engineer);
            this.canCraft(craftability);
        });
    }

    private void highlight(final boolean enable) {
        if (enable) {
            this.getStyleClass().add("wishlist-highlight");
        } else {
            this.getStyleClass().removeAll("wishlist-highlight");
        }
    }

    private void canCraft(final Craftability craftability) {
        this.wishlistRecipeName.getStyleClass().removeAll("wishlist-craftable", "wishlist-craftable-with-trade");
        if (Craftability.CRAFTABLE.equals(craftability)) {
            this.wishlistRecipeName.getStyleClass().add("wishlist-craftable");
            this.tooltip.textProperty().bind(LocaleService.getToolTipStringBinding((HorizonsEngineeringBlueprint) HorizonsBlueprintConstants.getRecipe(getRecipeName(), getBlueprintType(), HorizonsBlueprintGrade.GRADE_1), "tab.wishlist.blueprint.tooltip"));

        } else if (Craftability.CRAFTABLE_WITH_TRADE.equals(craftability)) {
            this.wishlistRecipeName.getStyleClass().add("wishlist-craftable-with-trade");
            this.tooltip.textProperty().bind(LocaleService.getToolTipStringBinding((HorizonsEngineeringBlueprint) HorizonsBlueprintConstants.getRecipe(getRecipeName(), getBlueprintType(), HorizonsBlueprintGrade.GRADE_1), "tab.wishlist.blueprint.tooltip.craftable"));
        }
    }

    @Override
    public void addWishlistIngredients(final List<Ingredient> wishlistIngredients) {
        this.wishlistIngredients.clear();
        this.otherIngredients.clear();
        this.wishlistIngredients.addAll(wishlistIngredients.stream().map(HorizonsWishlistIngredient.class::cast).filter(wishlistIngredient -> this.blueprints.keySet().stream().anyMatch(bp -> ((HorizonsBlueprint) bp).hasIngredient(wishlistIngredient.getHorizonsMaterial()))).collect(Collectors.toSet()));
        this.otherIngredients.addAll(wishlistIngredients.stream().map(HorizonsWishlistIngredient.class::cast).filter(wishlistIngredient -> this.blueprints.keySet().stream().noneMatch(bp -> ((HorizonsBlueprint) bp).hasIngredient(wishlistIngredient.getHorizonsMaterial()))).collect(Collectors.toSet()));
    }

    @Override
    public void setVisibility(final boolean visible) {
        this.visible = visible;
        this.wishlistBlueprint.setVisible(this.visible);
        if (this.visibilityButton != null) {
            this.visibilityImage.setImage(ImageService.getImage(this.visible ? "/images/other/visible_blue.png" : "/images/other/invisible_gray.png"));
            if (this.visible) {
                this.visibilityButton.getStyleClass().add(VISIBLE_STYLE_CLASS);
            } else {
                this.visibilityButton.getStyleClass().remove(VISIBLE_STYLE_CLASS);
            }
        }
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> EventService.publish(new HorizonsWishlistBlueprintEvent(commander, this.wishlistUUID, List.of(this.wishlistBlueprint), Action.VISIBILITY_CHANGED)));
    }

    @Override
    public Map<Blueprint<HorizonsBlueprintName>, Double> getRecipe() {
        return this.blueprints;
    }

    @Override
    public HorizonsBlueprint getPrimaryRecipe() {
        return (HorizonsBlueprint) this.blueprints.keySet().stream().max(Comparator.comparing(bp -> ((HorizonsBlueprint) bp).getHorizonsBlueprintGrade().getGrade())).orElse(null);
    }

    @Override
    public HorizonsBlueprintName getRecipeName() {
        return this.wishlistBlueprint.getRecipeName();
    }

    private HorizonsBlueprintType getBlueprintType() {
        return this.wishlistBlueprint.getBlueprintType();
    }

    @Override
    public BlueprintCategory getRecipeCategory() {
        return this.blueprintCategory;
    }

    @Override
    public Integer getSequenceID() {
        return this.sequenceID;
    }

    @Override
    public boolean isVisibleBlueprint() {
        return this.visible;
    }

    @Override
    public WishlistBlueprint<HorizonsBlueprintName> getWishlistRecipe() {
        return this.wishlistBlueprint;
    }

    @Override
    public void onDestroy() {
        EventService.removeListener(this.storageEventEventListener);
        sliders.forEach(CompletionSlider::destroy);
    }

    @Override
    public void setEngineer(Engineer engineer) {
        this.engineer = engineer;
    }
}
