package nl.jixxed.eliteodysseymaterials.templates.horizons.wishlist;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.util.Duration;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TooltipBuilder;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.ImageService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;
import nl.jixxed.eliteodysseymaterials.templates.generic.ControllableQuantitySelect;
import nl.jixxed.eliteodysseymaterials.templates.generic.QuantitySelect;
import nl.jixxed.eliteodysseymaterials.templates.generic.QuantitySelectable;
import nl.jixxed.eliteodysseymaterials.templates.generic.WishlistBlueprintTemplate;

import java.util.List;

@Slf4j
public non-sealed class HorizonsWishlistBlueprintTemplate extends DestroyableVBox implements WishlistBlueprintTemplate<HorizonsBlueprintName>, DestroyableEventTemplate {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static int counter = 0;

    private SimpleBooleanProperty visible = new SimpleBooleanProperty();
    private final Integer sequenceID;
    private final HorizonsWishlistBlueprint wishlistBlueprint;
    private final HorizonsBlueprint blueprint;
    private final String wishlistUUID;

    private DestroyableResizableImageView visibilityImage;

    @Getter
    private boolean deleted = false;
    private DestroyableTooltip tooltip;
    private DestroyableLabel title;
    private DestroyableLabel blueprintName;
    private QuantitySelectable quantityLine;

    HorizonsWishlistBlueprintTemplate(final String wishlistUUID, final WishlistBlueprint<HorizonsBlueprintName> wishlistBlueprint) {
        this.wishlistUUID = wishlistUUID;
        this.wishlistBlueprint = (HorizonsWishlistBlueprint) wishlistBlueprint;
        this.sequenceID = counter++;
        this.blueprint = (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipe(getRecipeName(), getBlueprintType(), getBlueprintGrade());
        initComponents();
        initEventHandling();
    }

    public void initComponents() {
        this.getStyleClass().add("blueprint");
        this.visibilityImage = ResizableImageViewBuilder.builder()
                .withStyleClass("visible-image")
                .withImage("/images/other/visible_blue.png")
                .build();
        DestroyableLabel visibilityButton = LabelBuilder.builder()
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
        final StringBinding blueprintStringBinding = switch (this.wishlistBlueprint) {
            case HorizonsSynthesisWishlistBlueprint wbp ->
                    LocaleService.getStringBinding("wishlist.blueprint.horizons.title.synthesis", LocaleService.LocalizationKey.of("blueprint.synthesis.grade" + wbp.getBlueprintGrade().getGrade()));
            case HorizonsTechBrokerWishlistBlueprint wbp ->
                    LocaleService.getStringBinding("wishlist.blueprint.horizons.title.techbroker", LocaleService.LocalizationKey.of(wbp.getBlueprintType().getLocalizationKey()));
            case HorizonsExperimentalWishlistBlueprint wbp ->
                    LocaleService.getStringBinding("wishlist.blueprint.horizons.title.experimental", LocaleService.LocalizationKey.of(wbp.getBlueprintType().getLocalizationKey()));
            case null, default ->
                    LocaleService.getStringBinding("wishlist.blueprint.horizons.title.engineer", LocaleService.LocalizationKey.of(this.wishlistBlueprint.getRecipeName().getLocalizationKey()));
        };

        blueprintName = LabelBuilder.builder()
                .withStyleClass("name")
                .withText(Bindings.createStringBinding(() -> blueprintStringBinding.get().trim(), blueprintStringBinding))
                .withOnMouseClicked(_ -> EventService.publish(new HorizonsBlueprintClickEvent(this.blueprint)))
                .build();

        DestroyableLabel removeBlueprint = LabelBuilder.builder()
                .withStyleClass("remove")
                .withNonLocalizedText("\u274C")
                .withManaged(!this.wishlistUUID.equals(Wishlist.ALL.getUuid()))
                .withVisibility(!this.wishlistUUID.equals(Wishlist.ALL.getUuid()))
                .withOnMouseClicked(_ -> {
                    //deleted by parent category
                    this.deleted = true;
                    APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> EventService.publish(new HorizonsWishlistBlueprintEvent(commander, this.wishlistUUID, List.of(this.wishlistBlueprint), Action.REMOVED)));
                })
                .build();

        this.title = LabelBuilder.builder()
                .withStyleClass("module")
                .withText(this.wishlistBlueprint instanceof HorizonsEngineerWishlistBlueprint ? "wishlist.blueprint.horizons.title.engineerunlock" : this.wishlistBlueprint.getRecipeName().getLocalizationKey())
                .build();
        if (this.blueprint instanceof HorizonsExperimentalEffectBlueprint moduleRecipe) {
            tooltip = TooltipBuilder.builder()
                    .withText(LocaleService.getToolTipStringBinding(moduleRecipe, "tab.wishlist.blueprint.tooltip"))
                    .withShowDelay(Duration.millis(100))
                    .build();
            tooltip.install(blueprintName);
//            register(this.tooltip);
        }
        this.canCraft();
        final DestroyableLabel quantityLabel = LabelBuilder.builder().withText("wishlist.blueprint.horizons.quantity", this.wishlistBlueprint.getQuantity())
                .withStyleClass("quantity-text")
                .withManaged(this.wishlistUUID.equals(Wishlist.ALL.getUuid()) && wishlistBlueprint.getQuantity() > 10)
                .withVisibility(this.wishlistUUID.equals(Wishlist.ALL.getUuid()) && wishlistBlueprint.getQuantity() > 10)
                .build();
        final DestroyableHBox titleLine = BoxBuilder.builder().withStyleClass("title-line").withNodes(title, new GrowingRegion(), quantityLabel, visibilityButton, removeBlueprint).buildHBox();
        final DestroyableHBox moduleLine = BoxBuilder.builder().withStyleClass("blueprint-line").withNodes(this.blueprintName).buildHBox();
        quantityLine = (HorizonsWishlist.ALL.getUuid().equals(wishlistUUID)) ? new QuantitySelect(wishlistBlueprint.getQuantity(), visible, wishlistBlueprint) : new ControllableQuantitySelect(wishlistBlueprint.getQuantity(), visible, wishlistBlueprint);
        quantityLine.addChangeListener(quantityLine.getQuantity(), (_, _, quantity) -> updateQuantity(quantity.intValue()));
        this.getNodes().addAll(titleLine, moduleLine, (Node & Destroyable) quantityLine);
    }

    public void initEventHandling() {
        register(EventService.addListener(true, this, StorageEvent.class, _ -> {
            this.canCraft();
        }));
        register(EventService.addListener(true, this, HorizonsShortestPathChangedEvent.class, _ -> {
            this.canCraft();
        }));
    }

    private void updateQuantity(Integer quantity) {
        //update quantity
        this.wishlistBlueprint.setQuantity(quantity);
        modify();
        update();
    }

    private void modify() {
        ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> EventService.publish(new HorizonsWishlistBlueprintEvent(commander, this.wishlistUUID, List.of(this.wishlistBlueprint), Action.MODIFY)));
    }

    private void update() {
        EventService.publish(new HorizonsWishlistBlueprintAlteredEvent(this.wishlistUUID));
        canCraft();
    }

    //    private void updateStyle() {
//        var craftability = HorizonsBlueprintConstants.getCraftability(wishlistBlueprint.getQuantity(), getRecipeName(), getBlueprintType(), getBlueprintGrade());
//        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("filled"), Craftability.CRAFTABLE.equals(craftability));
//        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("partial"), Craftability.CRAFTABLE_WITH_TRADE.equals(craftability));
//    }
    private void canCraft() {
//        log.debug(String.valueOf(this.hashCode()));
        for (int quantity = 1; quantity <= 10; quantity++) {
            if (quantity > wishlistBlueprint.getQuantity()) {
                this.pseudoClassStateChanged(PseudoClass.getPseudoClass("filled-" + quantity), false);
                this.pseudoClassStateChanged(PseudoClass.getPseudoClass("partial-" + quantity), false);
                continue;
            }
            var craftability = HorizonsBlueprintConstants.getCraftability(quantity, getRecipeName(), getBlueprintType(), getBlueprintGrade());
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

    @Override
    public void setVisibility(final boolean visible) {
        setVisibilityValue(visible);
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander ->
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

    private HorizonsBlueprintGrade getBlueprintGrade() {
        //this is not used for modules and only synthesis has grades
        if (this.wishlistBlueprint instanceof HorizonsSynthesisWishlistBlueprint horizonsSynthesisWishlistBlueprint) {
            return horizonsSynthesisWishlistBlueprint.getBlueprintGrade();
        }
        return HorizonsBlueprintGrade.NONE;
    }

    private HorizonsBlueprintType getBlueprintType() {
        if (this.wishlistBlueprint instanceof HorizonsModuleWishlistBlueprint horizonsModuleWishlistBlueprint) {
            return horizonsModuleWishlistBlueprint.getBlueprintType();
        } else if (this.wishlistBlueprint instanceof HorizonsExperimentalWishlistBlueprint horizonsExperimentalWishlistBlueprint) {
            return horizonsExperimentalWishlistBlueprint.getBlueprintType();
        } else if (this.wishlistBlueprint instanceof HorizonsTechBrokerWishlistBlueprint horizonsTechBrokerWishlistBlueprint) {
            return horizonsTechBrokerWishlistBlueprint.getBlueprintType();
        } else if (this.wishlistBlueprint instanceof HorizonsEngineerWishlistBlueprint) {
            return HorizonsBlueprintType.ENGINEER;
        }
        return null;
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
    public WishlistBlueprint getWishlistRecipe() {
        return this.wishlistBlueprint;
    }

}
