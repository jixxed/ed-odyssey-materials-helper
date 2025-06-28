package nl.jixxed.eliteodysseymaterials.templates.horizons.wishlist;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.scene.Node;
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
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;
import nl.jixxed.eliteodysseymaterials.templates.generic.ControllableQuantitySelect;
import nl.jixxed.eliteodysseymaterials.templates.generic.QuantitySelect;
import nl.jixxed.eliteodysseymaterials.templates.generic.QuantitySelectable;
import nl.jixxed.eliteodysseymaterials.templates.generic.WishlistBlueprintTemplate;
import org.controlsfx.control.PopOver;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.DoubleConsumer;
import java.util.stream.Collectors;

@Slf4j
public final class HorizonsWishlistModuleBlueprintTemplate extends DestroyableVBox implements WishlistBlueprintTemplate<HorizonsBlueprintName>, DestroyableEventTemplate {
    private static int counter = 0;

    private SimpleBooleanProperty visible = new SimpleBooleanProperty();
    private final Integer sequenceID;
    private final HorizonsModuleWishlistBlueprint wishlistBlueprint;
    private final String wishlistUUID;

    private DestroyableResizableImageView visibilityImage;
    private DestroyableLabel title;
    private DestroyableLabel blueprintName;
    private DestroyableLabel experimentalEffectName;
    private DestroyableHBox settingsButton;
    private DestroyableFontAwesomeIconView settingsIcon;
    private DestroyableTooltip tooltip;
    private DestroyableTooltip tooltipEffect;
    private AtomicReference<PopOver> popOverRef = new AtomicReference<>();


    @Getter
    private boolean deleted = false;
    private DestroyableLabel visibilityButton;
    private DestroyableLabel removeBlueprint;
    private QuantitySelectable quantityLine;

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
        visibilityButton = LabelBuilder.builder()
                .withStyleClass("visible-button")
                .withOnMouseClicked(_ -> setVisibility(!this.visible.get()))
                .withHoverProperty((_, _, newValue) -> {
                    if (Boolean.TRUE.equals(newValue)) {
                        this.visibilityImage.setImage(ImageService.getImage("/images/other/visible_blue.png"));
                    } else {
                        this.visibilityImage.setImage(ImageService.getImage(this.visible.get() ? "/images/other/visible_white.png" : "/images/other/invisible_gray.png"));
                    }
                })
                .withGraphic(this.visibilityImage)
                .build();
        if (Wishlist.ALL.getUuid().equals(this.wishlistUUID)) {
            setVisibilityValue(true);
            visibilityButton.setVisible(false);
            visibilityButton.setManaged(false);
        } else {
            setVisibilityValue(this.wishlistBlueprint.isVisible());
        }
        final String gradeList = this.wishlistBlueprint.getPercentageToComplete().keySet().stream().sorted(Comparator.comparing(HorizonsBlueprintGrade::getGrade)).map(HorizonsBlueprintGrade::getGrade).map(String::valueOf).collect(Collectors.joining(","));
        this.title = LabelBuilder.builder()
                .withStyleClass("module")
                .withText(this.wishlistBlueprint.getRecipeName().getLocalizationKey())
                .build();
        this.blueprintName = LabelBuilder.builder()
                .withStyleClass("name")
                .withText("wishlist.blueprint.horizons.title.module",
                        LocaleService.LocalizationKey.of(this.wishlistBlueprint.getBlueprintType().getLocalizationKey()),
                        gradeList.isEmpty() ? "?" : gradeList)
                .withOnMouseClicked(event -> EventService.publish(new HorizonsBlueprintClickEvent(HorizonsBlueprintConstants.getRecipe(getRecipeName(), getBlueprintType(), this.wishlistBlueprint.getPercentageToComplete().keySet().stream().findFirst().orElse(HorizonsBlueprintGrade.GRADE_1)))))
                .build();

        experimentalEffectName = LabelBuilder.builder()
                .withStyleClass("name")
                .withText(this.wishlistBlueprint.getExperimentalEffect() != null ? this.wishlistBlueprint.getExperimentalEffect().getLocalizationKey() : "blank")
                .withOnMouseClicked(event -> EventService.publish(new HorizonsBlueprintClickEvent(HorizonsBlueprintConstants.getRecipe(getRecipeName(), this.wishlistBlueprint.getExperimentalEffect(), null))))
                .build();
        this.tooltip = TooltipBuilder.builder()
                .withStyleClass("tooltip")
                .withText(LocaleService.getToolTipStringBinding((HorizonsEngineeringBlueprint) HorizonsBlueprintConstants.getRecipe(getRecipeName(), getBlueprintType(), HorizonsBlueprintGrade.GRADE_1), "tab.wishlist.blueprint.tooltip"))
                .withShowDelay(Duration.millis(100))
                .build();
        tooltip.install(blueprintName);
        this.tooltipEffect = TooltipBuilder.builder()
                .withStyleClass("tooltip")
                .withText(wishlistBlueprint.getExperimentalEffect() != null ? LocaleService.getToolTipStringBinding((HorizonsEngineeringBlueprint) HorizonsBlueprintConstants.getRecipe(getRecipeName(), wishlistBlueprint.getExperimentalEffect(), null), "tab.wishlist.blueprint.tooltip") : LocaleService.getStringBinding("blank"))
                .withShowDelay(Duration.millis(100))
                .build();
        tooltipEffect.install(experimentalEffectName);
        experimentalEffectName.addBinding(experimentalEffectName.visibleProperty(), this.experimentalEffectName.textProperty().isNotEmpty());
        experimentalEffectName.addBinding(experimentalEffectName.managedProperty(), this.experimentalEffectName.textProperty().isNotEmpty());
        removeBlueprint = LabelBuilder.builder()
                .withStyleClass("remove")
                .withNonLocalizedText("\u274C")
                .withManaged(!this.wishlistUUID.equals(Wishlist.ALL.getUuid()))
                .withVisibility(!this.wishlistUUID.equals(Wishlist.ALL.getUuid()))
                .withOnMouseClicked(_ -> {
                    //deleted by parent category
                    this.deleted = true;
                    ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> EventService.publish(new HorizonsWishlistBlueprintEvent(commander, this.wishlistUUID, List.of(this.wishlistBlueprint), Action.REMOVED)));
                })
                .build();
        final DestroyableLabel quantityLabel = LabelBuilder.builder().withText("wishlist.blueprint.horizons.quantity", this.wishlistBlueprint.getQuantity())
                .withStyleClass("quantity-text")
                .withManaged(this.wishlistUUID.equals(Wishlist.ALL.getUuid()) && wishlistBlueprint.getQuantity() > 10)
                .withVisibility(this.wishlistUUID.equals(Wishlist.ALL.getUuid()) && wishlistBlueprint.getQuantity() > 10)
                .build();
        this.settingsIcon = FontAwesomeIconViewBuilder.builder()
                .withStyleClass("config-icon")
                .withIcon(FontAwesomeIcon.ANGLE_DOWN)
                .withManaged(!this.wishlistUUID.equals(Wishlist.ALL.getUuid()))
                .withVisibility(!this.wishlistUUID.equals(Wishlist.ALL.getUuid()))
//                .withOnMouseClicked(_ -> showGradeSettings())
                .build();
        this.settingsButton = BoxBuilder.builder()
                .withStyleClass("settings-button")
                .withNode(this.settingsIcon)
                .withManaged(!this.wishlistUUID.equals(Wishlist.ALL.getUuid()))
                .withVisibility(!this.wishlistUUID.equals(Wishlist.ALL.getUuid()))
                .withOnMouseClicked(_ -> showGradeSettings())
                .buildHBox();
        final DestroyableHBox titleLine = BoxBuilder.builder().withStyleClass("title-line").withNodes(title, new GrowingRegion(), quantityLabel, visibilityButton, removeBlueprint).buildHBox();
        final DestroyableHBox moduleLine = BoxBuilder.builder().withStyleClass("blueprint-line").withNodes(this.blueprintName, new GrowingRegion(), this.settingsButton).buildHBox();
        quantityLine = (HorizonsWishlist.ALL.getUuid().equals(wishlistUUID)) ? new QuantitySelect(wishlistBlueprint.getQuantity(), visible, wishlistBlueprint) : new ControllableQuantitySelect(wishlistBlueprint.getQuantity(), visible, wishlistBlueprint);
        quantityLine.addChangeListener(quantityLine.getQuantity(), (_, _, quantity) -> updateQuantity(quantity.intValue()));
        this.getNodes().addAll(titleLine, moduleLine, experimentalEffectName, new GrowingRegion(), (Node & Destroyable) quantityLine);


//        register(this.tooltip);

        this.canCraft();
    }

    private void updateQuantity(Integer quantity) {
        //update quantity
        this.wishlistBlueprint.setQuantity(quantity);
        modify();
        update();
    }

    private void update() {
        //update quantity
        final String gradeList = this.wishlistBlueprint.getPercentageToComplete().keySet().stream().sorted(Comparator.comparing(HorizonsBlueprintGrade::getGrade)).map(HorizonsBlueprintGrade::getGrade).map(String::valueOf).collect(Collectors.joining(","));
        this.blueprintName.addBinding(this.blueprintName.textProperty(), LocaleService.getStringBinding("wishlist.blueprint.horizons.title.module",
                LocaleService.LocalizationKey.of(this.wishlistBlueprint.getBlueprintType().getLocalizationKey()),
                gradeList.isEmpty() ? "?" : gradeList));
        this.tooltip.uninstall(blueprintName);
        this.tooltip.destroy();
        this.tooltip = TooltipBuilder.builder()
                .withStyleClass("tooltip")
                .withText(LocaleService.getToolTipStringBinding((HorizonsEngineeringBlueprint) HorizonsBlueprintConstants.getRecipe(getRecipeName(), getBlueprintType(), HorizonsBlueprintGrade.GRADE_1), "tab.wishlist.blueprint.tooltip"))
                .withShowDelay(Duration.millis(100))
                .build();
        this.tooltip.install(blueprintName);
        experimentalEffectName.addBinding(this.experimentalEffectName.textProperty(), LocaleService.getStringBinding(this.wishlistBlueprint.getExperimentalEffect() != null ? this.wishlistBlueprint.getExperimentalEffect().getLocalizationKey() : "blank"));
        this.tooltipEffect.uninstall(experimentalEffectName);
        this.tooltipEffect.destroy();
        this.tooltipEffect = TooltipBuilder.builder()
                .withStyleClass("tooltip")
                .withText(wishlistBlueprint.getExperimentalEffect() != null ? LocaleService.getToolTipStringBinding((HorizonsEngineeringBlueprint) HorizonsBlueprintConstants.getRecipe(getRecipeName(), wishlistBlueprint.getExperimentalEffect(), null), "tab.wishlist.blueprint.tooltip") : LocaleService.getStringBinding("blank"))
                .withShowDelay(Duration.millis(100))
                .build();
        this.tooltipEffect.install(experimentalEffectName);
        EventService.publish(new HorizonsWishlistBlueprintAlteredEvent(this.wishlistUUID));
        //update styles
        canCraft();
    }

    private void showGradeSettings() {
        if (popOverRef.get() == null || !popOverRef.get().isShowing()) {
            final DestroyableHBox[] gradeControls = HorizonsBlueprintConstants.getBlueprintGrades(this.wishlistBlueprint.getRecipeName(), this.wishlistBlueprint.getBlueprintType()).stream().sorted(Comparator.comparing(HorizonsBlueprintGrade::getGrade)).map(grade ->
                    {
                        DoubleConsumer function = percentage -> {
                            this.wishlistBlueprint.setBlueprintGradePercentageToComplete(grade, Math.round(percentage * 10.0) / 1000.0);
                            modify();
                            update();
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
            final DestroyableLabel blueprintSelectTitle = LabelBuilder.builder()
                    .withStyleClass("title")
                    .withText("wishlist.blueprint.select")
                    .build();
            final ObservableList<HorizonsBlueprintType> blueprintItems = FXCollections.observableList(HorizonsBlueprintConstants.getBlueprintTypes(this.wishlistBlueprint.getRecipeName()).stream().filter(type -> !type.isPreEngineered()).toList());
            final DestroyableComboBox<HorizonsBlueprintType> blueprintSelect = ComboBoxBuilder.builder(HorizonsBlueprintType.class)
                    .withStyleClass("blueprint-select")
                    .withItemsProperty(blueprintItems)
                    .withSelected(this.wishlistBlueprint.getBlueprintType())
                    .withValueChangeListener((_, _, newValue) -> {
                        this.wishlistBlueprint.setBlueprintType(newValue);
                        final HorizonsBlueprintGrade maxBlueprintGrade = HorizonsBlueprintConstants.getBlueprintGrades(this.wishlistBlueprint.getRecipeName(), newValue).stream().max(Comparator.comparing(HorizonsBlueprintGrade::getGrade)).orElse(HorizonsBlueprintGrade.GRADE_1);
                        this.wishlistBlueprint.getPercentageToComplete().entrySet().removeIf(entry -> entry.getKey().getGrade() > maxBlueprintGrade.getGrade());
                        modify();
                        update();
                        EventService.publish(new HorizonsWishlistBlueprintAlteredEvent(this.wishlistUUID));
                    })
                    .build();
            final DestroyableLabel experimentalEffectSelectTitle = LabelBuilder.builder()
                    .withStyleClass("title")
                    .withText("wishlist.effect.select")
                    .build();
            final Map<HorizonsBlueprintType, HorizonsBlueprint> effects = HorizonsBlueprintConstants.getExperimentalEffects().get(this.wishlistBlueprint.getRecipeName());
            final ObservableList<HorizonsBlueprintType> items = effects != null ? FXCollections.observableList(effects.keySet().stream().filter(type -> !type.isPreEngineered()).collect(Collectors.toList())) : FXCollections.observableArrayList();
            items.addFirst(null);
            final DestroyableComboBox<HorizonsBlueprintType> experimentalEffectSelect = ComboBoxBuilder.builder(HorizonsBlueprintType.class)
                    .withStyleClass("blueprint-select")
                    .withItemsProperty(items)
                    .withSelected(this.wishlistBlueprint.getExperimentalEffect())
                    .withValueChangeListener((_, _, newValue) -> {
                        this.wishlistBlueprint.setExperimentalEffect(newValue);
                        modify();
                        update();
                        EventService.publish(new HorizonsWishlistBlueprintAlteredEvent(this.wishlistUUID));
                    })
                    .build();
            final boolean hasEffects = !items.isEmpty();
            experimentalEffectSelectTitle.setVisible(hasEffects);
            experimentalEffectSelect.setVisible(hasEffects);
            experimentalEffectSelectTitle.setManaged(hasEffects);
            experimentalEffectSelect.setManaged(hasEffects);
            final DestroyableVBox content = BoxBuilder.builder()
                    .withStyleClass("content")
                    .withNodes(blueprintSelectTitle, blueprintSelect, experimentalEffectSelectTitle, experimentalEffectSelect, title, explain, grades)
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
            popOver.show(this.settingsButton);
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
        register(EventService.addListener(true, this, HorizonsShortestPathChangedEvent.class, _ -> {
            this.canCraft();
        }));
    }

    private void canCraft() {
//        log.debug(String.valueOf(this.hashCode()));
        for (int quantity = 1; quantity <= 10; quantity++) {
            if (quantity > wishlistBlueprint.getQuantity()) {
                this.pseudoClassStateChanged(PseudoClass.getPseudoClass("filled-" + quantity), false);
                this.pseudoClassStateChanged(PseudoClass.getPseudoClass("partial-" + quantity), false);
                continue;
            }
            final Craftability craftability = HorizonsBlueprintConstants.getCraftability(quantity, getRecipeName(), getBlueprintType(), this.wishlistBlueprint.getPercentageToComplete(), getCurrentEngineerForBlueprint(this.wishlistBlueprint.getBlueprint()).orElse(null));
            if (Craftability.CRAFTABLE.equals(craftability)) {
                this.pseudoClassStateChanged(PseudoClass.getPseudoClass("filled-" + quantity), true);
                this.pseudoClassStateChanged(PseudoClass.getPseudoClass("partial-" + quantity), true);
            } else if (Craftability.CRAFTABLE_WITH_TRADE.equals(craftability)) {
                this.pseudoClassStateChanged(PseudoClass.getPseudoClass("filled-" + quantity), false);
                this.pseudoClassStateChanged(PseudoClass.getPseudoClass("partial-" + quantity), true);
            } else {
                this.pseudoClassStateChanged(PseudoClass.getPseudoClass("filled-" + quantity), false);
                this.pseudoClassStateChanged(PseudoClass.getPseudoClass("partial-" + quantity), false);
            }
        }

//        if (Craftability.CRAFTABLE.equals(craftability)) {
//            this.tooltip.addBinding(this.tooltip.textProperty(), LocaleService.getToolTipStringBinding((HorizonsEngineeringBlueprint) HorizonsBlueprintConstants.getRecipe(getRecipeName(), getBlueprintType(), HorizonsBlueprintGrade.GRADE_1), "tab.wishlist.blueprint.tooltip"));
//        } else if (Craftability.CRAFTABLE_WITH_TRADE.equals(craftability)) {
//            this.tooltip.addBinding(this.tooltip.textProperty(), LocaleService.getToolTipStringBinding((HorizonsEngineeringBlueprint) HorizonsBlueprintConstants.getRecipe(getRecipeName(), getBlueprintType(), HorizonsBlueprintGrade.GRADE_1), "tab.wishlist.blueprint.tooltip.craftable"));
//        }
    }

    private Optional<Engineer> getCurrentEngineerForBlueprint(Blueprint<HorizonsBlueprintName> blueprint) {
        final Optional<HorizonsWishlist> horizonsWishlist = ApplicationState.getInstance().getPreferredCommander()
                .map(commander -> WishlistService.getHorizonsWishlists(commander).getSelectedWishlist());
        var pathItems = horizonsWishlist
                .map(wishlist -> PathService.calculateHorizonsShortestPath((List<HorizonsWishlistBlueprint>) (List<?>) wishlist.getItems().stream().filter(WishlistBlueprint::isVisible).toList()))
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
        setVisibilityValue(visible);
        ApplicationState.getInstance().getPreferredCommander().ifPresent(commander ->
                EventService.publish(new HorizonsWishlistBlueprintEvent(commander, this.wishlistUUID, List.of(this.wishlistBlueprint), Action.VISIBILITY_CHANGED)));
    }

    public void setVisibilityValue(final boolean visible) {
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("hidden"), !visible);
        this.visible.set(visible);
        this.wishlistBlueprint.setVisible(visible);
        this.visibilityImage.setImage(ImageService.getImage(visible ? "/images/other/visible_white.png" : "/images/other/invisible_gray.png"));
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
        return this.visible.get();
    }

    @Override
    public WishlistBlueprint<HorizonsBlueprintName> getWishlistRecipe() {
        return this.wishlistBlueprint;
    }


}
