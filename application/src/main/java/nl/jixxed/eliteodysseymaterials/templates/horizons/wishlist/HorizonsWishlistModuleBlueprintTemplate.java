package nl.jixxed.eliteodysseymaterials.templates.horizons.wishlist;

import javafx.animation.FadeTransition;
import javafx.css.PseudoClass;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.ImageService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.components.CompletionSlider;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;
import nl.jixxed.eliteodysseymaterials.templates.generic.WishlistBlueprintTemplate;
import org.controlsfx.control.PopOver;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.DoubleConsumer;
import java.util.stream.Collectors;

public final class HorizonsWishlistModuleBlueprintTemplate extends DestroyableHBox implements WishlistBlueprintTemplate<HorizonsBlueprintName>, DestroyableEventTemplate {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static int counter = 0;

    private boolean visible;
    private final Integer sequenceID;
    private final HorizonsModuleWishlistBlueprint wishlistBlueprint;
    private final BlueprintCategory blueprintCategory;
    //    private Map<Blueprint<HorizonsBlueprintName>, Double> blueprints;
    @Getter
    private final String wishlistUUID;

    private DestroyableResizableImageView visibilityImage;
    private DestroyableLabel wishlistRecipeName;
    private DestroyableButton toggleControls;
    private DestroyableTooltip tooltip;
    final AtomicReference<PopOver> popOverRef = new AtomicReference<>();

    private Engineer engineer;
    private List<CompletionSlider> sliders = new ArrayList<>();

    HorizonsWishlistModuleBlueprintTemplate(final String wishlistUUID, final HorizonsModuleWishlistBlueprint wishlistBlueprint) {
        this.wishlistUUID = wishlistUUID;
        this.wishlistBlueprint = wishlistBlueprint;
        this.sequenceID = counter++;
        this.blueprintCategory = HorizonsBlueprintConstants.getRecipeCategory(wishlistBlueprint.getRecipeName(), false);
//        this.blueprints = wishlistBlueprint.getPercentageToComplete().entrySet().stream().map(gradePercentage -> Map.entry(HorizonsBlueprintConstants.getRecipe(getRecipeName(), getBlueprintType(), gradePercentage.getKey()), gradePercentage.getValue())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        initComponents();
        initEventHandling();
    }

    public void initComponents() {
        this.getStyleClass().add("blueprint");
        this.visibilityImage = ResizableImageViewBuilder.builder()
                .withStyleClass("visible-image")
                .withImage("/images/other/visible_blue.png")
                .build();
        DestroyableButton visibilityButton = ButtonBuilder.builder()
                .withStyleClass("visible-button")
                .withOnAction(_ -> setVisibility(!this.visible))
                .withGraphic(this.visibilityImage)
                .build();
        if (Wishlist.ALL.getUuid().equals(this.wishlistUUID)) {
            setVisibility(true);
            visibilityButton.setVisible(false);
            visibilityButton.setManaged(false);
        } else {
            setVisibility(this.wishlistBlueprint.isVisible());
        }
        final String gradeList = this.wishlistBlueprint.getPercentageToComplete().keySet().stream().sorted(Comparator.comparing(HorizonsBlueprintGrade::getGrade)).map(HorizonsBlueprintGrade::getGrade).map(String::valueOf).collect(Collectors.joining(","));
        this.wishlistRecipeName = LabelBuilder.builder()
                .withStyleClass("name")
                .withText("wishlist.blueprint.horizons.title.module",
                        LocaleService.LocalizationKey.of(this.wishlistBlueprint.getRecipeName().getLocalizationKey()),
                        LocaleService.LocalizationKey.of(this.wishlistBlueprint.getBlueprintType().getLocalizationKey()),
                        gradeList.isEmpty() ? "?" : gradeList)
                .withOnMouseClicked(event -> EventService.publish(new HorizonsBlueprintClickEvent(HorizonsBlueprintConstants.getRecipe(getRecipeName(), getBlueprintType(), this.wishlistBlueprint.getPercentageToComplete().keySet().stream().findFirst().orElse(HorizonsBlueprintGrade.GRADE_1)))))
                .withHoverProperty((_, _, newValue) -> {
                    EventService.publish(new HorizonsWishlistHighlightEvent(this.wishlistBlueprint, newValue));
                })
                .build();
        DestroyableButton removeBlueprint = ButtonBuilder.builder()
                .withStyleClass("remove")
                .withNonLocalizedText("X")
                .withManaged(!this.wishlistUUID.equals(Wishlist.ALL.getUuid()))
                .withVisibility(!this.wishlistUUID.equals(Wishlist.ALL.getUuid()))
                .withOnAction(_ -> {
                    APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> EventService.publish(new HorizonsWishlistBlueprintEvent(commander, this.wishlistUUID, List.of(this.wishlistBlueprint), Action.REMOVED)));
                    destroyTemplate();
                })
                .build();
        this.toggleControls = ButtonBuilder.builder()
                .withStyleClass("config")
                .withNonLocalizedText("\u25BC")
                .withManaged(!this.wishlistUUID.equals(Wishlist.ALL.getUuid()))
                .withVisibility(!this.wishlistUUID.equals(Wishlist.ALL.getUuid()))
                .withOnAction(_ -> showGradeSettings())
                .build();

        this.getNodes().addAll(visibilityButton, this.wishlistRecipeName, this.toggleControls, removeBlueprint);


        this.tooltip = TooltipBuilder.builder()
                .withStyleClass("tooltip")
                .withText(LocaleService.getToolTipStringBinding((HorizonsEngineeringBlueprint) HorizonsBlueprintConstants.getRecipe(getRecipeName(), getBlueprintType(), HorizonsBlueprintGrade.GRADE_1), "tab.wishlist.blueprint.tooltip"))
                .withShowDelay(Duration.millis(100))
                .build();
        Tooltip.install(this.wishlistRecipeName, this.tooltip);

        initFadeTransition();
        this.canCraft();
    }

    private void showGradeSettings() {
        if (popOverRef.get() == null || !popOverRef.get().isShowing()) {
            final DestroyableHBox[] gradeControls = HorizonsBlueprintConstants.getBlueprintGrades(this.wishlistBlueprint.getRecipeName(), this.wishlistBlueprint.getBlueprintType()).stream().sorted(Comparator.comparing(HorizonsBlueprintGrade::getGrade)).map(grade ->
                    {
                        DoubleConsumer function = percentage -> {
                            this.wishlistBlueprint.setBlueprintGradePercentageToComplete(grade, percentage / 100D);
//                            this.blueprints = this.wishlistBlueprint.getPercentageToComplete().entrySet().stream().map(gradePercentage -> Map.entry(HorizonsBlueprintConstants.getRecipe(getRecipeName(), getBlueprintType(), gradePercentage.getKey()), gradePercentage.getValue())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                            modify();
                            EventService.publish(new HorizonsWishlistBlueprintAlteredEvent(this.wishlistUUID));
                            final String gradeList2 = this.wishlistBlueprint.getPercentageToComplete().keySet().stream().sorted(Comparator.comparing(HorizonsBlueprintGrade::getGrade)).map(HorizonsBlueprintGrade::getGrade).map(String::valueOf).collect(Collectors.joining(","));
                            this.wishlistRecipeName.addBinding(this.wishlistRecipeName.textProperty(), LocaleService.getStringBinding("wishlist.blueprint.horizons.title.module",
                                    LocaleService.LocalizationKey.of(this.wishlistBlueprint.getRecipeName().getLocalizationKey()),
                                    LocaleService.LocalizationKey.of(this.wishlistBlueprint.getBlueprintType().getLocalizationKey()),
                                    gradeList2.isEmpty() ? "?" : gradeList2));
                            this.canCraft();
                        };
                        CompletionSlider completionSlider = new CompletionSlider(0D, 100D, this.wishlistBlueprint.getPercentageToComplete().getOrDefault(grade, 0D) * 100D, function);
                        sliders.add(completionSlider);

                        final DestroyableLabel label = LabelBuilder.builder()
                                .withStyleClass("grade")
                                .withNonLocalizedText(String.valueOf(grade.getGrade()))
                                .build();
                        return BoxBuilder.builder()
                                .withStyleClasses("grade-select")
                                .withNodes(label, completionSlider)
                                .buildHBox();
                    })
                    .toArray(DestroyableHBox[]::new);

            final DestroyableVBox grades = BoxBuilder.builder()
                    .withStyleClasses("grade-selects")
                    .withNodes(gradeControls).buildVBox();
            final DestroyableLabel title = LabelBuilder.builder()
                    .withStyleClass("title")
                    .withText("wishlist.percentage.per.grade")
                    .build();
            final DestroyableLabel explain = LabelBuilder.builder()
                    .withStyleClass("explain")
                    .withText("wishlist.percentage.per.grade.explain")
                    .build();
            final DestroyableVBox content = BoxBuilder.builder()
                    .withStyleClass("content")
                    .withNodes(title, explain, grades)
                    .buildVBox();
            final DestroyablePopOver popOver = PopOverBuilder.builder()
                    .withStyleClass("horizons-wishlist-blueprints-module-popover")
                    .withContent(content)
                    .withDetachable(false)
                    .withHeaderAlwaysVisible(false)
                    .withArrowLocation(PopOver.ArrowLocation.TOP_CENTER)
                    .withCornerRadius(0)
                    .build();
            popOverRef.set(popOver);
            popOver.show(this.toggleControls);
        } else {
            popOverRef.get().hide();
            popOverRef.set(null);
        }
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

    public void initEventHandling() {
        register(EventService.addListener(true, this, StorageEvent.class, _ -> {
            this.canCraft();
        }));
    }

    private void canCraft() {
        final Craftability craftability = HorizonsBlueprintConstants.getCraftability(getRecipeName(), getBlueprintType(), this.wishlistBlueprint.getPercentageToComplete(), engineer);
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("filled"), Craftability.CRAFTABLE.equals(craftability));
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("partial"), Craftability.CRAFTABLE_WITH_TRADE.equals(craftability));
        if (Craftability.CRAFTABLE.equals(craftability)) {
            this.tooltip.addBinding(this.tooltip.textProperty(), LocaleService.getToolTipStringBinding((HorizonsEngineeringBlueprint) HorizonsBlueprintConstants.getRecipe(getRecipeName(), getBlueprintType(), HorizonsBlueprintGrade.GRADE_1), "tab.wishlist.blueprint.tooltip"));
        } else if (Craftability.CRAFTABLE_WITH_TRADE.equals(craftability)) {
            this.tooltip.addBinding(this.tooltip.textProperty(), LocaleService.getToolTipStringBinding((HorizonsEngineeringBlueprint) HorizonsBlueprintConstants.getRecipe(getRecipeName(), getBlueprintType(), HorizonsBlueprintGrade.GRADE_1), "tab.wishlist.blueprint.tooltip.craftable"));
        }
    }

    @Override
    public void setVisibility(final boolean visible) {
        this.visible = visible;
        this.wishlistBlueprint.setVisible(this.visible);
        this.visibilityImage.setImage(ImageService.getImage(this.visible ? "/images/other/visible_blue.png" : "/images/other/invisible_gray.png"));
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander ->
                EventService.publish(new HorizonsWishlistBlueprintEvent(commander, this.wishlistUUID, List.of(this.wishlistBlueprint), Action.VISIBILITY_CHANGED)));
    }

    @Override
    public Map<Blueprint<HorizonsBlueprintName>, Double> getRecipe() {
        return null;
    }

    @Override
    public @Nullable HorizonsBlueprint getPrimaryRecipe() {
        return null;
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
    public void setEngineer(Engineer engineer) {
        this.engineer = engineer;
        this.canCraft();
    }
}
