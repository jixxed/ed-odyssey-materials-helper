package nl.jixxed.eliteodysseymaterials.templates.odyssey.wishlist;

import javafx.css.PseudoClass;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
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
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;
import nl.jixxed.eliteodysseymaterials.templates.generic.WishlistBlueprintTemplate;

import java.util.List;

public non-sealed class OdysseyWishlistBlueprintTemplate extends DestroyableHBox implements WishlistBlueprintTemplate<OdysseyBlueprintName>, DestroyableEventTemplate {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static int counter = 0;

    private boolean visible;
    private final Integer sequenceID;
    private final OdysseyWishlistBlueprint wishlistBlueprint;
    private final OdysseyBlueprint blueprint;
    private final String wishlistUUID;

    private DestroyableResizableImageView visibilityImage;
    @Getter
    private boolean deleted = false;
    private DestroyableLabel wishlistRecipeName;
    private DestroyableTooltip tooltip;

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
        DestroyableButton visibilityButton = ButtonBuilder.builder()
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
        wishlistRecipeName = LabelBuilder.builder()
                .withStyleClass("name")
                .withText(this.wishlistBlueprint.getRecipeName().getLocalizationKey())
                .withOnMouseClicked(_ -> EventService.publish(new BlueprintClickEvent(this.blueprint.getBlueprintName())))
                .withHoverProperty((_, _, newValue) -> {
                    EventService.publish(new OdysseyWishlistHighlightEvent(this.wishlistBlueprint, newValue));
                })
                .build();
        DestroyableButton removeBlueprint = ButtonBuilder.builder()
                .withStyleClass("remove")
                .withNonLocalizedText("X")
                .withManaged(!this.wishlistUUID.equals(Wishlist.ALL.getUuid()))
                .withVisibility(!this.wishlistUUID.equals(Wishlist.ALL.getUuid()))
                .withOnAction(_ -> {
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
            tooltip.install(wishlistRecipeName);
//            register(this.tooltip);
        }
        this.updateStyle();
        this.getNodes().addAll(visibilityButton, wishlistRecipeName, removeBlueprint);
    }

    public void initEventHandling() {
        register(EventService.addListener(true, this, StorageEvent.class, _ -> this.update()));
    }

    private void update() {
        updateStyle();
    }

    private void updateStyle() {
        var craftability = OdysseyBlueprintConstants.getCraftability(getRecipeName());
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("filled"), Craftability.CRAFTABLE.equals(craftability));
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("partial"), Craftability.CRAFTABLE_WITH_TRADE.equals(craftability));
    }

    @Override
    public void setVisibility(final boolean visible) {
        this.visible = visible;
        this.wishlistBlueprint.setVisible(this.visible);
        this.visibilityImage.setImage(ImageService.getImage(this.visible ? "/images/other/visible_blue.png" : "/images/other/invisible_gray.png"));
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander ->
                EventService.publish(new OdysseyWishlistBlueprintEvent(commander, this.wishlistUUID, List.of(this.wishlistBlueprint), Action.VISIBILITY_CHANGED)));
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
        return this.visible;
    }

    @Override
    public WishlistBlueprint getWishlistRecipe() {
        return this.wishlistBlueprint;
    }

    @Override
    public void destroyInternal() {
        super.destroyInternal();
        Tooltip.uninstall(this.wishlistRecipeName, this.tooltip);
    }
}
