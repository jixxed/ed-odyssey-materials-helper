package nl.jixxed.eliteodysseymaterials.templates;

import javafx.animation.FadeTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TooltipBuilder;
import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ModuleBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.OdysseyBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.WishlistBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.ImageService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class WishlistBlueprintTemplate extends HBox {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final String VISIBLE_STYLE_CLASS = "visible";
    private static final String WISHLIST_VISIBLE_ICON_STYLE_CLASS = "wishlist-visible-icon";
    private static int counter = 0;

    private boolean visible;
    private final Integer sequenceID;
    private final WishlistBlueprint wishlistBlueprint;
    private final BlueprintCategory blueprintCategory;
    private final OdysseyBlueprint blueprint;
    private final String wishlistUUID;

    private Button visibilityButton;
    private DestroyableResizableImageView visibilityImage;
    private Label wishlistRecipeName;
    private Button removeBlueprint;
    private final Set<WishlistIngredient> wishlistIngredients = new HashSet<>();
    private final Set<WishlistIngredient> otherIngredients = new HashSet<>();
    private EventListener<StorageEvent> storageEventEventListener;
    private Tooltip tooltip;

    public WishlistBlueprintTemplate(final String wishlistUUID, final WishlistBlueprint wishlistBlueprint) {
        this.wishlistUUID = wishlistUUID;
        this.wishlistBlueprint = wishlistBlueprint;
        this.sequenceID = counter++;
        this.blueprintCategory = OdysseyBlueprintConstants.getRecipeCategory(wishlistBlueprint.getRecipeName());
        this.blueprint = OdysseyBlueprintConstants.getRecipe(wishlistBlueprint.getRecipeName());
        initComponents();
        initEventHandling();
    }

    private void initComponents() {
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

        this.wishlistRecipeName = LabelBuilder.builder()
                .withStyleClass("wishlist-label")
                .withText(LocaleService.getStringBinding(this.wishlistBlueprint.getRecipeName().getLocalizationKey()))
                .withOnMouseClicked(event -> EventService.publish(new BlueprintClickEvent(this.wishlistBlueprint.getRecipeName())))
                .withHoverProperty((observable, oldValue, newValue) -> {
                    this.wishlistIngredients.forEach(wishlistIngredient -> wishlistIngredient.highlight(newValue, this.blueprint.getRequiredAmount(wishlistIngredient.getOdysseyMaterial())));
                    this.otherIngredients.forEach(wishlistIngredient -> wishlistIngredient.lowlight(newValue));
                    this.highlight(newValue);
                })
                .build();
        this.getChildren().addAll(this.visibilityButton, this.wishlistRecipeName);

        this.removeBlueprint = ButtonBuilder.builder()
                .withStyleClass("wishlist-item-close").withNonLocalizedText("X")
                .withOnAction(event -> remove())
                .build();
        this.getChildren().add(this.removeBlueprint);
        this.getStyleClass().add("wishlist-item");


        if (this.blueprint instanceof ModuleBlueprint moduleRecipe) {
            this.tooltip = TooltipBuilder.builder()
                    .withText(LocaleService.getToolTipStringBinding(moduleRecipe, "tab.wishlist.blueprint.tooltip"))
                    .withShowDelay(Duration.millis(100))
                    .build();
            Tooltip.install(this.wishlistRecipeName, this.tooltip);
        }
        initFadeTransition();
        final Craftability craftability = APPLICATION_STATE.getCraftability((OdysseyBlueprintName) this.getRecipeName());
        this.canCraft(craftability);
    }

    public void remove() {
        EventService.removeListener(this.storageEventEventListener);
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> EventService.publish(new WishlistBlueprintEvent(commander.getFid(), this.wishlistUUID, List.of(this.wishlistBlueprint), Action.REMOVED)));
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
            final Craftability craftability = APPLICATION_STATE.getCraftability((OdysseyBlueprintName) this.getRecipeName());
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
            if (this.blueprint instanceof ModuleBlueprint moduleRecipe) {
                this.tooltip.textProperty().bind(LocaleService.getToolTipStringBinding(moduleRecipe, "tab.wishlist.blueprint.tooltip"));
            }
        } else if (Craftability.CRAFTABLE_WITH_TRADE.equals(craftability)) {
            this.wishlistRecipeName.getStyleClass().add("wishlist-craftable-with-trade");
            if (this.blueprint instanceof ModuleBlueprint moduleRecipe) {
                this.tooltip.textProperty().bind(LocaleService.getToolTipStringBinding(moduleRecipe, "tab.wishlist.blueprint.tooltip.craftable"));
            }
        }
    }

    void addWishlistIngredients(final List<WishlistIngredient> wishlistIngredients) {
        this.wishlistIngredients.clear();
        this.otherIngredients.clear();
        this.wishlistIngredients.addAll(wishlistIngredients.stream().filter(wishlistIngredient -> this.blueprint.hasIngredient(wishlistIngredient.getOdysseyMaterial())).collect(Collectors.toSet()));
        this.otherIngredients.addAll(wishlistIngredients.stream().filter(wishlistIngredient -> !this.blueprint.hasIngredient(wishlistIngredient.getOdysseyMaterial())).collect(Collectors.toSet()));
    }

    void setVisibility(final boolean visible) {
        this.visible = visible;
        this.wishlistBlueprint.setVisible(this.visible);
        this.visibilityImage.setImage(ImageService.getImage(this.visible ? "/images/other/visible_blue.png" : "/images/other/invisible_gray.png"));
        if (this.visible) {
            this.visibilityButton.getStyleClass().add(VISIBLE_STYLE_CLASS);
        } else {
            this.visibilityButton.getStyleClass().remove(VISIBLE_STYLE_CLASS);
        }
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> EventService.publish(new WishlistBlueprintEvent(commander.getFid(), this.wishlistUUID, List.of(this.wishlistBlueprint), Action.VISIBILITY_CHANGED)));
    }

    public OdysseyBlueprint getRecipe() {
        return this.blueprint;
    }

    public BlueprintName getRecipeName() {
        return this.wishlistBlueprint.getRecipeName();
    }

    BlueprintCategory getRecipeCategory() {
        return this.blueprintCategory;
    }

    Integer getSequenceID() {
        return this.sequenceID;
    }

    public boolean isVisibleBlueprint() {
        return this.visible;
    }

    WishlistBlueprint getWishlistRecipe() {
        return this.wishlistBlueprint;
    }

    void onDestroy() {
        EventService.removeListener(this.storageEventEventListener);
    }
}