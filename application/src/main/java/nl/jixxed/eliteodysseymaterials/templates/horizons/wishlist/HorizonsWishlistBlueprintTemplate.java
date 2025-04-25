package nl.jixxed.eliteodysseymaterials.templates.horizons.wishlist;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.css.PseudoClass;
import javafx.util.Duration;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TooltipBuilder;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.ImageService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;
import nl.jixxed.eliteodysseymaterials.templates.generic.WishlistBlueprintTemplate;

import java.util.List;

public non-sealed class HorizonsWishlistBlueprintTemplate extends DestroyableHBox implements WishlistBlueprintTemplate<HorizonsBlueprintName>, DestroyableEventTemplate {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static int counter = 0;

    private boolean visible;
    private final Integer sequenceID;
    private final HorizonsWishlistBlueprint wishlistBlueprint;
    private final HorizonsBlueprint blueprint;
    private final String wishlistUUID;

    private DestroyableResizableImageView visibilityImage;

    @Getter
    private boolean deleted = false;
    private DestroyableTooltip tooltip;
    private DestroyableLabel wishlistRecipeName;

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
        final StringBinding titleStringBinding = switch (this.wishlistBlueprint) {
            case HorizonsSynthesisWishlistBlueprint wbp ->
                    LocaleService.getStringBinding("wishlist.blueprint.horizons.title.synthesis", LocaleService.LocalizationKey.of(wbp.getRecipeName().getLocalizationKey()), LocaleService.LocalizationKey.of("blueprint.synthesis.grade" + wbp.getBlueprintGrade().getGrade()));
            case HorizonsTechBrokerWishlistBlueprint wbp ->
                    LocaleService.getStringBinding("wishlist.blueprint.horizons.title.techbroker", LocaleService.LocalizationKey.of(wbp.getRecipeName().getLocalizationKey()), LocaleService.LocalizationKey.of(wbp.getBlueprintType().getLocalizationKey()));
            case HorizonsExperimentalWishlistBlueprint wbp ->
                    LocaleService.getStringBinding("wishlist.blueprint.horizons.title.experimental", LocaleService.LocalizationKey.of(wbp.getRecipeName().getLocalizationKey()), LocaleService.LocalizationKey.of(wbp.getBlueprintType().getLocalizationKey()));
            case null, default ->
                    LocaleService.getStringBinding("wishlist.blueprint.horizons.title.engineer", LocaleService.LocalizationKey.of(this.wishlistBlueprint.getRecipeName().getLocalizationKey()));
        };
        wishlistRecipeName = LabelBuilder.builder()
                .withStyleClass("name")
                .withText(Bindings.createStringBinding(() -> titleStringBinding.get().trim(), titleStringBinding))
                .withOnMouseClicked(_ -> EventService.publish(new HorizonsBlueprintClickEvent(this.blueprint)))
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
                    //deleted by parent category
                    this.deleted = true;
                    APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> EventService.publish(new HorizonsWishlistBlueprintEvent(commander, this.wishlistUUID, List.of(this.wishlistBlueprint), Action.REMOVED)));
                })
                .build();


        if (this.blueprint instanceof HorizonsExperimentalEffectBlueprint moduleRecipe) {
            tooltip = TooltipBuilder.builder()
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
        register(EventService.addListener(true, this, StorageEvent.class, _ -> {
            this.update();
        }));
    }

    private void update() {
        updateStyle();
    }

    private void updateStyle() {
        var craftability = HorizonsBlueprintConstants.getCraftability(getRecipeName(), getBlueprintType(), getBlueprintGrade());
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("filled"), Craftability.CRAFTABLE.equals(craftability));
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("partial"), Craftability.CRAFTABLE_WITH_TRADE.equals(craftability));
    }

    @Override
    public void setVisibility(final boolean visible) {
        this.visible = visible;
        this.wishlistBlueprint.setVisible(this.visible);
        this.visibilityImage.setImage(ImageService.getImage(this.visible ? "/images/other/visible_blue.png" : "/images/other/invisible_gray.png"));
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander ->
                EventService.publish(new HorizonsWishlistBlueprintEvent(commander, this.wishlistUUID, List.of(this.wishlistBlueprint), Action.VISIBILITY_CHANGED)));
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
        return this.visible;
    }

    @Override
    public WishlistBlueprint getWishlistRecipe() {
        return this.wishlistBlueprint;
    }


    @Override
    public void destroyInternal() {
        super.destroyInternal();
//        Tooltip.uninstall(this.wishlistRecipeName, this.tooltip);
    }
}
