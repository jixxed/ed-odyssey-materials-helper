package nl.jixxed.eliteodysseymaterials.templates.horizons.wishlist;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.css.PseudoClass;
import javafx.util.Duration;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.ImageService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PathService;
import nl.jixxed.eliteodysseymaterials.service.WishlistService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.components.CompletionSlider;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;
import nl.jixxed.eliteodysseymaterials.templates.generic.WishlistBlueprintTemplate;
import org.controlsfx.control.PopOver;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.DoubleConsumer;
import java.util.stream.Collectors;

@Slf4j
public final class HorizonsWishlistModuleBlueprintTemplate extends DestroyableHBox implements WishlistBlueprintTemplate<HorizonsBlueprintName>, DestroyableEventTemplate {
    private static int counter = 0;

    private boolean visible;
    private final Integer sequenceID;
    private final HorizonsModuleWishlistBlueprint wishlistBlueprint;
    private final String wishlistUUID;

    private DestroyableResizableImageView visibilityImage;
    private DestroyableLabel wishlistRecipeName;
    private DestroyableButton toggleControls;
    private DestroyableTooltip tooltip;
    private AtomicReference<PopOver> popOverRef = new AtomicReference<>();


    @Getter
    private boolean deleted = false;
    private DestroyableButton visibilityButton;
    private DestroyableButton removeBlueprint;
    private Disposable subscribe;

    HorizonsWishlistModuleBlueprintTemplate(final String wishlistUUID, final HorizonsModuleWishlistBlueprint wishlistBlueprint) {
        this.wishlistUUID = wishlistUUID;
        this.wishlistBlueprint = wishlistBlueprint;
        this.sequenceID = counter++;
        initComponents();
        initEventHandling();
    }

    public void initComponents() {
        this.getStyleClass().add("blueprint");
        this.visibilityImage = ResizableImageViewBuilder.builder()
                .withStyleClass("visible-image")
                .withImage("/images/other/visible_blue.png")
                .build();
        visibilityButton = ButtonBuilder.builder()
                .withStyleClass("visible-button")
                .withOnAction(_ -> setVisibility(!this.visible))
                .withGraphic(this.visibilityImage)
                .build();
        if (Wishlist.ALL.getUuid().equals(this.wishlistUUID)) {
            this.visible = true;
            this.wishlistBlueprint.setVisible(true);
            this.visibilityImage.setImage(ImageService.getImage("/images/other/visible_blue.png"));
            visibilityButton.setVisible(false);
            visibilityButton.setManaged(false);
        } else {
            this.visible = this.wishlistBlueprint.isVisible();
            this.wishlistBlueprint.setVisible(this.visible);
            this.visibilityImage.setImage(ImageService.getImage(this.visible ? "/images/other/visible_blue.png" : "/images/other/invisible_gray.png"));
        }
        final String gradeList = this.wishlistBlueprint.getPercentageToComplete().keySet().stream().sorted(Comparator.comparing(HorizonsBlueprintGrade::getGrade)).map(HorizonsBlueprintGrade::getGrade).map(String::valueOf).collect(Collectors.joining(","));
        subscribe = Observable.create((ObservableEmitter<HorizonsWishlistHighlightEvent> emitter) -> {
                    this.wishlistRecipeName = LabelBuilder.builder()
                            .withStyleClass("name")
                            .withText("wishlist.blueprint.horizons.title.module",
                                    LocaleService.LocalizationKey.of(this.wishlistBlueprint.getRecipeName().getLocalizationKey()),
                                    LocaleService.LocalizationKey.of(this.wishlistBlueprint.getBlueprintType().getLocalizationKey()),
                                    gradeList.isEmpty() ? "?" : gradeList)
                            .withOnMouseClicked(event -> EventService.publish(new HorizonsBlueprintClickEvent(HorizonsBlueprintConstants.getRecipe(getRecipeName(), getBlueprintType(), this.wishlistBlueprint.getPercentageToComplete().keySet().stream().findFirst().orElse(HorizonsBlueprintGrade.GRADE_1)))))
                            .withHoverProperty((_, _, newValue) -> {
                                if (this.visible) {
                                    if (newValue) {
                                        emitter.onNext(new HorizonsWishlistHighlightEvent(this.wishlistBlueprint, newValue));
                                    } else {
                                        EventService.publish(new HorizonsWishlistHighlightEvent(this.wishlistBlueprint, newValue));
                                    }
                                }
                            })
                            .build();
                })
                .delay(250, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .subscribe(event -> {
                    if (this.wishlistRecipeName.isHover()) {
                        EventService.publish(event);
                    }
                }, t -> log.error(t.getMessage(), t));
        removeBlueprint = ButtonBuilder.builder()
                .withStyleClass("remove")
                .withNonLocalizedText("X")
                .withManaged(!this.wishlistUUID.equals(Wishlist.ALL.getUuid()))
                .withVisibility(!this.wishlistUUID.equals(Wishlist.ALL.getUuid()))
                .withOnAction(_ -> {
                    //deleted by parent category
                    this.deleted = true;
                    ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> EventService.publish(new HorizonsWishlistBlueprintEvent(commander, this.wishlistUUID, List.of(this.wishlistBlueprint), Action.REMOVED)));
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
        tooltip.install(wishlistRecipeName);
//        register(this.tooltip);

        this.canCraft();
    }

    private void showGradeSettings() {
        if (popOverRef.get() == null || !popOverRef.get().isShowing()) {
            final DestroyableHBox[] gradeControls = HorizonsBlueprintConstants.getBlueprintGrades(this.wishlistBlueprint.getRecipeName(), this.wishlistBlueprint.getBlueprintType()).stream().sorted(Comparator.comparing(HorizonsBlueprintGrade::getGrade)).map(grade ->
                    {
                        DoubleConsumer function = percentage -> {
                            this.wishlistBlueprint.setBlueprintGradePercentageToComplete(grade, Math.round(percentage * 10.0) / 1000.0);
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
        ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> EventService.publish(new HorizonsWishlistBlueprintEvent(commander, this.wishlistUUID, List.of(this.wishlistBlueprint), Action.MODIFY)));
    }

    public void initEventHandling() {
        register(EventService.addListener(true, this, StorageEvent.class, _ -> {
            this.canCraft();
        }));
    }

    private void canCraft() {
//        log.debug(String.valueOf(this.hashCode()));
        final Craftability craftability = HorizonsBlueprintConstants.getCraftability(getRecipeName(), getBlueprintType(), this.wishlistBlueprint.getPercentageToComplete(), getCurrentEngineerForBlueprint(this.wishlistBlueprint.getBlueprint()).orElse(null));
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("filled"), Craftability.CRAFTABLE.equals(craftability));
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("partial"), Craftability.CRAFTABLE_WITH_TRADE.equals(craftability));
        if (Craftability.CRAFTABLE.equals(craftability)) {
            this.tooltip.addBinding(this.tooltip.textProperty(), LocaleService.getToolTipStringBinding((HorizonsEngineeringBlueprint) HorizonsBlueprintConstants.getRecipe(getRecipeName(), getBlueprintType(), HorizonsBlueprintGrade.GRADE_1), "tab.wishlist.blueprint.tooltip"));
        } else if (Craftability.CRAFTABLE_WITH_TRADE.equals(craftability)) {
            this.tooltip.addBinding(this.tooltip.textProperty(), LocaleService.getToolTipStringBinding((HorizonsEngineeringBlueprint) HorizonsBlueprintConstants.getRecipe(getRecipeName(), getBlueprintType(), HorizonsBlueprintGrade.GRADE_1), "tab.wishlist.blueprint.tooltip.craftable"));
        }
    }

    private Optional<Engineer> getCurrentEngineerForBlueprint(Blueprint<HorizonsBlueprintName> blueprint) {
        final Optional<HorizonsWishlist> horizonsWishlist = ApplicationState.getInstance().getPreferredCommander()
                .map(commander -> WishlistService.getHorizonsWishlists(commander).getSelectedWishlist());
        var pathItems = horizonsWishlist
                .map(wishlist -> PathService.calculateHorizonsShortestPath((List<HorizonsWishlistBlueprint>) (List<?>) wishlist.getItems()))
                .orElse(Collections.emptyList());

        if (blueprint == null) {
            return Optional.empty();
        }
        return pathItems.stream()
                .filter(pathItem -> pathItem.getRecipes().keySet().stream()
                        .anyMatch(bp -> bp.getBlueprintName().equals(blueprint.getBlueprintName())
                                && ((HorizonsBlueprint) bp).getHorizonsBlueprintType().equals(((HorizonsBlueprint) blueprint).getHorizonsBlueprintType())))
                .findFirst()
                .filter(pathItem -> !pathItem.getEngineer().equals(Engineer.UNKNOWN))
                .map(PathItem::getEngineer);
    }

    @Override
    public void setVisibility(final boolean visible) {
        this.visible = visible;
        this.wishlistBlueprint.setVisible(this.visible);
        this.visibilityImage.setImage(ImageService.getImage(this.visible ? "/images/other/visible_blue.png" : "/images/other/invisible_gray.png"));
        ApplicationState.getInstance().getPreferredCommander().ifPresent(commander ->
                EventService.publish(new HorizonsWishlistBlueprintEvent(commander, this.wishlistUUID, List.of(this.wishlistBlueprint), Action.VISIBILITY_CHANGED)));
    }

    public HorizonsBlueprintName getRecipeName() {
        return this.wishlistBlueprint.getRecipeName();
    }

    private HorizonsBlueprintType getBlueprintType() {
        return this.wishlistBlueprint.getBlueprintType();
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
    public void destroyInternal() {
        super.destroyInternal();
        subscribe.dispose();
    }

}
