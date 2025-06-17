package nl.jixxed.eliteodysseymaterials.templates.odyssey.wishlist;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TooltipBuilder;
import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.Action;
import nl.jixxed.eliteodysseymaterials.enums.Craftability;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyBlueprintName;
import nl.jixxed.eliteodysseymaterials.service.ImageService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.BlueprintClickEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.OdysseyWishlistBlueprintEvent;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;
import nl.jixxed.eliteodysseymaterials.templates.generic.ControllableQuantitySelect;
import nl.jixxed.eliteodysseymaterials.templates.generic.QuantitySelect;
import nl.jixxed.eliteodysseymaterials.templates.generic.QuantitySelectable;
import nl.jixxed.eliteodysseymaterials.templates.generic.WishlistBlueprintTemplate;

import java.util.List;

@Slf4j
public non-sealed class OdysseyWishlistBlueprintTemplate extends DestroyableVBox implements WishlistBlueprintTemplate<OdysseyBlueprintName>, DestroyableEventTemplate {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static int counter = 0;

    private SimpleBooleanProperty visible = new SimpleBooleanProperty();
    private final Integer sequenceID;
    private final OdysseyWishlistBlueprint wishlistBlueprint;
    private final OdysseyBlueprint blueprint;
    private final String wishlistUUID;

    private DestroyableResizableImageView visibilityImage;
    @Getter
    private boolean deleted = false;
    private DestroyableLabel title;
    private DestroyableLabel blueprintName;
    private DestroyableTooltip tooltip;
    private QuantitySelectable quantityLine;

    OdysseyWishlistBlueprintTemplate(final String wishlistUUID, final WishlistBlueprint<OdysseyBlueprintName> wishlistBlueprint) {
        this.wishlistUUID = wishlistUUID;
        this.wishlistBlueprint = (OdysseyWishlistBlueprint) wishlistBlueprint;
        this.sequenceID = counter++;
        this.blueprint = OdysseyBlueprintConstants.getRecipe(wishlistBlueprint.getRecipeName());
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
        blueprintName = LabelBuilder.builder()
                .withStyleClass("name")
                .withText(this.wishlistBlueprint.getRecipeName().getShortLocalizationKey())
                .withOnMouseClicked(_ -> EventService.publish(new BlueprintClickEvent(this.blueprint.getBlueprintName())))

                .build();

        DestroyableLabel removeBlueprint = LabelBuilder.builder()
                .withStyleClass("remove")
                .withNonLocalizedText("\u274C")
                .withManaged(!this.wishlistUUID.equals(Wishlist.ALL.getUuid()))
                .withVisibility(!this.wishlistUUID.equals(Wishlist.ALL.getUuid()))
                .withOnMouseClicked(_ -> {
                    //deleted by parent category
                    this.deleted = true;
                    APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> EventService.publish(new OdysseyWishlistBlueprintEvent(commander, this.wishlistUUID, List.of(this.wishlistBlueprint), Action.REMOVED)));
                })
                .build();

        if (this.blueprint instanceof ModuleBlueprint moduleRecipe) {
            tooltip = TooltipBuilder.builder()
                    .withStyleClass("wishlist-tooltip")
                    .withText(LocaleService.getToolTipStringBinding(moduleRecipe, "tab.wishlist.blueprint.tooltip"))
                    .withShowDelay(Duration.millis(100))
                    .build();
            tooltip.install(blueprintName);
//            register(this.tooltip);
        }
        this.title = LabelBuilder.builder()
                .withStyleClass("module")
                .withText(this.wishlistBlueprint.getRecipeName().getTitleLocalizationKey())
                .build();
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

//        this.getNodes().addAll(visibilityButton, wishlistRecipeName, removeBlueprint);
    }

    private void updateQuantity(Integer quantity) {
        //update quantity
        this.wishlistBlueprint.setQuantity(quantity);
        modify();
        update();
    }

    public void initEventHandling() {
        register(EventService.addListener(true, this, StorageEvent.class, _ -> this.update()));
    }

    private void modify() {
        ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> EventService.publish(new OdysseyWishlistBlueprintEvent(commander, this.wishlistUUID, List.of(this.wishlistBlueprint), Action.MODIFY)));
    }

    private void update() {
        canCraft();
    }

    //    private void updateStyle() {
//        var craftability = OdysseyBlueprintConstants.getCraftability(getRecipeName());
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
            var craftability = OdysseyBlueprintConstants.getCraftability(quantity, getRecipeName());
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
                EventService.publish(new OdysseyWishlistBlueprintEvent(commander, this.wishlistUUID, List.of(this.wishlistBlueprint), Action.VISIBILITY_CHANGED)));
    }

    public void setVisibilityValue(final boolean visible) {
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("hidden"), !visible);
        this.visible.set(visible);
        this.wishlistBlueprint.setVisible(visible);
        this.visibilityImage.setImage(ImageService.getImage(visible ? "/images/other/visible_white.png" : "/images/other/invisible_gray.png"));
    }

    public OdysseyBlueprintName getRecipeName() {
        return this.wishlistBlueprint.getRecipeName();
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

    @Override
    public void destroyInternal() {
        super.destroyInternal();
        Tooltip.uninstall(this.blueprintName, this.tooltip);
    }
}
