package nl.jixxed.eliteodysseymaterials.templates.horizons.wishlist;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleBooleanProperty;
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
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIcon;
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

//    private DestroyableResizableImageView visibilityImage;

    @Getter
    private boolean deleted = false;
    private DestroyableTooltip tooltip;
    private DestroyableLabel title;
    private DestroyableLabel blueprintName;
    private QuantitySelectable quantityLine;
    private DestroyableFontAwesomeIconView eyeIcon;

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

        eyeIcon = FontAwesomeIconViewBuilder.builder()
                .withStyleClasses("visible-button")
                .withIcon(FontAwesomeIcon.EYE)
                .withOnMouseClicked(_ -> setVisibility(!this.visible.get()))
                .build();
        DestroyableTooltip eyeIconTooltip = TooltipBuilder.builder()
                .withStyleClass("action-tooltip")
                .withShowDelay(Duration.millis(100))
                .withText("wishlist.tooltip.showhide")
                .build();
        eyeIconTooltip.install(eyeIcon);
        var visibility = BoxBuilder.builder()
                .withStyleClasses("visible-container")
                .withNode(eyeIcon)
                .buildHBox();
        if (Wishlist.ALL.getUuid().equals(this.wishlistUUID)) {
            setVisibilityValue(true);
            visibility.setVisible(false);
            visibility.setManaged(false);
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
                .withText(Bindings.createStringBinding(() -> blueprintStringBinding.get().replaceAll("^[ ├└\\s]+", ""), blueprintStringBinding))//replace  ├└\s at the start of the string
                .withOnMouseClicked(_ -> EventService.publish(new HorizonsBlueprintClickEvent(this.blueprint)))
                .withGraphic(this.wishlistBlueprint.getRecipeName().isInColonia() ? EdAwesomeIconViewPaneBuilder.builder().withStyleClass("colonia-icon").withIcons(EdAwesomeIcon.OTHER_COLONIA).build() : null)
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
        DestroyableTooltip removeBlueprintTooltip = TooltipBuilder.builder()
                .withStyleClass("action-tooltip")
                .withShowDelay(Duration.millis(100))
                .withText("wishlist.tooltip.remove")
                .build();
        removeBlueprintTooltip.install(removeBlueprint);

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
        }
        this.canCraft();
        final DestroyableLabel quantityLabel = LabelBuilder.builder().withText("wishlist.blueprint.horizons.quantity", this.wishlistBlueprint.getQuantity())
                .withStyleClass("quantity-text")
                .withManaged(this.wishlistUUID.equals(Wishlist.ALL.getUuid()) && wishlistBlueprint.getQuantity() > 10)
                .withVisibility(this.wishlistUUID.equals(Wishlist.ALL.getUuid()) && wishlistBlueprint.getQuantity() > 10)
                .build();
        final DestroyableHBox titleLine = BoxBuilder.builder().withStyleClass("title-line").withNodes(title, new GrowingRegion(), quantityLabel, visibility, removeBlueprint).buildHBox();
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

    private void canCraft() {
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
        eyeIcon.setIcon(visible ? FontAwesomeIcon.EYE : FontAwesomeIcon.EYE_SLASH);
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
