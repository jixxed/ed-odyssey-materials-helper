package nl.jixxed.eliteodysseymaterials.templates.horizons.wishlist;

import javafx.animation.FadeTransition;
import javafx.beans.binding.StringBinding;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
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
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;
import nl.jixxed.eliteodysseymaterials.templates.generic.Ingredient;
import nl.jixxed.eliteodysseymaterials.templates.generic.WishlistBlueprintTemplate;

import java.util.*;
import java.util.stream.Collectors;

public class HorizonsWishlistBlueprintTemplate extends HBox implements WishlistBlueprintTemplate<HorizonsBlueprintName> {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final String VISIBLE_STYLE_CLASS = "visible";
    private static final String WISHLIST_VISIBLE_ICON_STYLE_CLASS = "wishlist-visible-icon";
    private static int counter = 0;

    private boolean visible;
    private final Integer sequenceID;
    private final HorizonsWishlistBlueprint wishlistBlueprint;
    private final BlueprintCategory blueprintCategory;
    private final HorizonsBlueprint blueprint;
    @Getter
    private final String wishlistUUID;

    private Button visibilityButton;
    private DestroyableResizableImageView visibilityImage;
    private Label wishlistRecipeName;
    private Button removeBlueprint;
    private final Set<HorizonsWishlistIngredient> wishlistIngredients = new HashSet<>();
    private final Set<HorizonsWishlistIngredient> otherIngredients = new HashSet<>();
    private EventListener<StorageEvent> storageEventEventListener;
    private Tooltip tooltip;

    HorizonsWishlistBlueprintTemplate(final String wishlistUUID, final WishlistBlueprint<HorizonsBlueprintName> wishlistBlueprint) {
        this.wishlistUUID = wishlistUUID;
        this.wishlistBlueprint = (HorizonsWishlistBlueprint) wishlistBlueprint;
        this.sequenceID = counter++;
        this.blueprintCategory = HorizonsBlueprintConstants.getRecipeCategory(wishlistBlueprint.getRecipeName(), wishlistBlueprint instanceof HorizonsExperimentalWishlistBlueprint);
        this.blueprint = (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipe(getRecipeName(), getBlueprintType(), getBlueprintGrade());
        initComponents();
        initEventHandling();
    }

    private void initComponents() {
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
            this.getChildren().addAll(this.visibilityButton);
        } else {
            setVisibility(true);
        }
        final StringBinding titleStringBinding;
        if (this.wishlistBlueprint instanceof HorizonsSynthesisWishlistBlueprint wbp) {
            titleStringBinding = LocaleService.getStringBinding("wishlist.blueprint.horizons.title.synthesis", LocaleService.LocalizationKey.of(wbp.getRecipeName().getLocalizationKey()), LocaleService.LocalizationKey.of("blueprint.synthesis.grade" + wbp.getBlueprintGrade().getGrade()));

        } else if (this.wishlistBlueprint instanceof HorizonsTechBrokerWishlistBlueprint wbp) {
            titleStringBinding = LocaleService.getStringBinding("wishlist.blueprint.horizons.title.techbroker", LocaleService.LocalizationKey.of(wbp.getRecipeName().getLocalizationKey()), LocaleService.LocalizationKey.of(wbp.getBlueprintType().getLocalizationKey()));

        } else if (this.wishlistBlueprint instanceof HorizonsExperimentalWishlistBlueprint wbp) {
            titleStringBinding = LocaleService.getStringBinding("wishlist.blueprint.horizons.title.experimental", LocaleService.LocalizationKey.of(wbp.getRecipeName().getLocalizationKey()), LocaleService.LocalizationKey.of(wbp.getBlueprintType().getLocalizationKey()));

        } else {
            titleStringBinding = LocaleService.getStringBinding("wishlist.blueprint.horizons.title.engineer", LocaleService.LocalizationKey.of(this.wishlistBlueprint.getRecipeName().getLocalizationKey()));
        }
        this.wishlistRecipeName = LabelBuilder.builder()
                .withStyleClass("wishlist-label")
                .withText(titleStringBinding)
                .withOnMouseClicked(event -> EventService.publish(new HorizonsBlueprintClickEvent(this.blueprint)))
                .withHoverProperty((observable, oldValue, newValue) -> {
                    this.wishlistIngredients.forEach(wishlistIngredient -> {
                        final Integer requiredAmount = this.blueprint.getRequiredAmount(wishlistIngredient.getHorizonsMaterial(), null);
                        final Integer minimumAmount = this.blueprint.getMinimumAmount(wishlistIngredient.getHorizonsMaterial());
                        final Integer maximumAmount = this.blueprint.getMaximumAmount(wishlistIngredient.getHorizonsMaterial());

                        wishlistIngredient.highlight(newValue, new WishlistMaterial(minimumAmount,requiredAmount,maximumAmount));
                    });
                    this.otherIngredients.forEach(wishlistIngredient -> wishlistIngredient.lowlight(newValue));
                    this.highlight(newValue);
                })
                .build();
        this.getChildren().addAll(this.wishlistRecipeName);
        if (!this.wishlistUUID.equals(Wishlist.ALL.getUuid())) {
            this.removeBlueprint = ButtonBuilder.builder()
                    .withStyleClass("wishlist-item-button").withNonLocalizedText("X")
                    .withOnAction(event -> remove())
                    .build();
            this.getChildren().add(this.removeBlueprint);
        }
        this.getStyleClass().add("wishlist-item");


        if (this.blueprint instanceof HorizonsExperimentalEffectBlueprint moduleRecipe) {
            this.tooltip = TooltipBuilder.builder()
                    .withText(LocaleService.getToolTipStringBinding(moduleRecipe, "tab.wishlist.blueprint.tooltip"))
                    .withShowDelay(Duration.millis(100))
                    .build();
            Tooltip.install(this.wishlistRecipeName, this.tooltip);
        }
        initFadeTransition();
        final Craftability craftability = HorizonsBlueprintConstants.getCraftability(getRecipeName(), getBlueprintType(), getBlueprintGrade());
        this.canCraft(craftability);
    }

    @Override
    public void remove() {
        EventService.removeListener(this.storageEventEventListener);
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> EventService.publish(new HorizonsWishlistBlueprintEvent(commander, this.wishlistUUID, List.of(this.wishlistBlueprint), Action.REMOVED)));
    }

    private void initFadeTransition() {
        final FadeTransition fadeTransition = new FadeTransition(Duration.millis(2000));
        fadeTransition.setNode(this);
        fadeTransition.setFromValue(0.3);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }

    private void initEventHandling() {
        this.storageEventEventListener = EventService.addListener(true, this, StorageEvent.class, storageEvent -> {
            final Craftability craftability = HorizonsBlueprintConstants.getCraftability(getRecipeName(), getBlueprintType(), getBlueprintGrade());
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
            if (this.blueprint instanceof HorizonsModuleBlueprint moduleRecipe) {
                this.tooltip.textProperty().bind(LocaleService.getToolTipStringBinding(moduleRecipe, "tab.wishlist.blueprint.tooltip"));
            }
        } else if (Craftability.CRAFTABLE_WITH_TRADE.equals(craftability)) {
            this.wishlistRecipeName.getStyleClass().add("wishlist-craftable-with-trade");
            if (this.blueprint instanceof HorizonsModuleBlueprint moduleRecipe) {
                this.tooltip.textProperty().bind(LocaleService.getToolTipStringBinding(moduleRecipe, "tab.wishlist.blueprint.tooltip.craftable"));
            }
        }
    }

    @Override
    public void addWishlistIngredients(final List<Ingredient> wishlistIngredients) {
        this.wishlistIngredients.clear();
        this.otherIngredients.clear();
        this.wishlistIngredients.addAll(wishlistIngredients.stream().map(HorizonsWishlistIngredient.class::cast).filter(wishlistIngredient -> this.blueprint.hasIngredient(wishlistIngredient.getHorizonsMaterial())).collect(Collectors.toSet()));
        this.otherIngredients.addAll(wishlistIngredients.stream().map(HorizonsWishlistIngredient.class::cast).filter(wishlistIngredient -> !this.blueprint.hasIngredient(wishlistIngredient.getHorizonsMaterial())).collect(Collectors.toSet()));
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
        return (this.blueprint == null) ? Collections.emptyMap() : Map.of(this.blueprint, 1D);
    }

    @Override
    public HorizonsBlueprint getPrimaryRecipe() {
        return this.blueprint;
    }

    @Override
    public HorizonsBlueprintName getRecipeName() {
        return this.wishlistBlueprint.getRecipeName();
    }

    private HorizonsBlueprintGrade getBlueprintGrade() {
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
    public WishlistBlueprint getWishlistRecipe() {
        return this.wishlistBlueprint;
    }

    @Override
    public void onDestroy() {
        EventService.removeListener(this.storageEventEventListener);
    }

    @Override
    public void setEngineer(Engineer engineer) {
        //not needed
    }
}
