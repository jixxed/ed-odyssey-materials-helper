package nl.jixxed.eliteodysseymaterials.templates.odyssey.loadout;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.LoadoutService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.WishlistService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.util.*;

public class OdysseyLoadoutItem extends DestroyableVBox implements DestroyableEventTemplate {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    @Getter
    private LoadoutSet loadoutSet;
    private Loadout loadout;
    private OdysseyLoadoutStats stats;
    private DestroyableMenuButton addToWishlist;
    private BooleanProperty isValid;

    private void setValid(final boolean value) {
        isValidProperty().set(value);
    }

    public final boolean isValid() {
        return this.isValid == null || this.isValid.get();
    }

    private BooleanProperty isValidProperty() {
        if (this.isValid == null) {
            this.isValid = new SimpleBooleanProperty(false);
        }
        return this.isValid;
    }

    OdysseyLoadoutItem(final LoadoutSet loadoutSet, final Loadout loadout) {
        this.loadoutSet = loadoutSet;
        this.loadout = loadout;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initEventHandling() {
        if (!Suit.FLIGHTSUIT.equals(this.loadout.getEquipment())) {

            register(EventService.addListener(true, this, OdysseyWishlistSelectedEvent.class, wishlistSelectedEvent -> {
                if (Suit.FLIGHTSUIT != this.loadout.getEquipment()) {
                    APPLICATION_STATE.getPreferredCommander().ifPresent(this::loadCommanderWishlists);
                }
            }));
            register(EventService.addListener(true, this, ModificationChangedEvent.class, modificationChangedEvent -> {
                if (modificationChangedEvent.getLoadout() == this.loadout) {
                    handleModificationChange(modificationChangedEvent.getModificationChange());
                }
            }));
            register(EventService.addListener(true, this, LoadoutChangedEvent.class, event -> {
                setValid(Arrays.stream(this.loadout.getModifications()).map(SelectedModification::getModification).filter(Objects::nonNull).count() >= this.loadout.getTargetLevel());
                stats.update();
            }));
        }
    }

    @Override
    @SuppressWarnings("java:S3776")
    public void initComponents() {
        this.getStyleClass().add("loadout-item");
        this.stats = new OdysseyLoadoutStats(loadoutSet, loadout);
        //navbar
        final DestroyableLabel left = register(LabelBuilder.builder()
                .withStyleClass("arrow")
                .withNonLocalizedText("<")
                .withOnMouseClicked(event -> {
                    this.loadoutSet.moveDown(this.loadout);
                    saveLoadoutSet();
                    EventService.publish(new LoadoutMovedEvent());
                })
                .build());
        if (this.loadoutSet.getLoadouts().indexOf(this.loadout) <= 0) {
            left.setVisible(false);
        }
        final DestroyableRegion regionL = new DestroyableRegion();
        HBox.setHgrow(regionL, Priority.ALWAYS);
        final DestroyableLabel delete = register(LabelBuilder.builder()
                .withStyleClass("delete")
                .withText("tab.loadout.delete.item")
                .withOnMouseClicked(_ -> {
                    this.loadoutSet.removeLoadout(this.loadout);
                    saveLoadoutSet();
                    EventService.publish(new LoadoutRemovedEvent(this));
                })
                .build());
        final DestroyableRegion regionR = new DestroyableRegion();
        HBox.setHgrow(regionR, Priority.ALWAYS);
        final DestroyableLabel right = register(LabelBuilder.builder()
                .withStyleClass("arrow")
                .withNonLocalizedText(">")
                .withOnMouseClicked(event -> {
                    this.loadoutSet.moveUp(this.loadout);
                    saveLoadoutSet();
                    EventService.publish(new LoadoutMovedEvent());
                })
                .build());
        if (this.loadoutSet.getLoadouts().indexOf(this.loadout) == this.loadoutSet.getLoadouts().size() - 1) {
            right.setVisible(false);
        }
        final DestroyableHBox navBar = BoxBuilder.builder()
                .withStyleClass("navbar")
                .withNodes(left, regionL, delete, regionR, right)
                .buildHBox();
        navBar.setVisible(!this.loadoutSet.equals(LoadoutSet.CURRENT));
        navBar.setManaged(!this.loadoutSet.equals(LoadoutSet.CURRENT));
        //image
        final DestroyableResizableImageView image = ResizableImageViewBuilder.builder()
                .withStyleClass("loadout-image")
                .withImage(this.loadout.getEquipment().getImage())
                .build();
        DestroyableStackPane pane = StackPaneBuilder.builder()
                .withNode(image)
                .build();
        //title
        final DestroyableLabel title = LabelBuilder.builder()
                .withStyleClass("title")
                .withText(this.loadout.getEquipment().getLocalizationKey())
                .build();
//        final DestroyableHBox imageBox = centerImage(image);
        this.getNodes().addAll(navBar, pane, title);
        if (Suit.FLIGHTSUIT != this.loadout.getEquipment()) {
            this.getNodes().add(new LoadoutConfig(loadoutSet, loadout));
        }
        //stats

        this.getNodes().add(this.stats);
        if (Suit.FLIGHTSUIT != this.loadout.getEquipment() && !this.loadoutSet.equals(LoadoutSet.CURRENT)) {
            this.addToWishlist = MenuButtonBuilder.builder()
                    .withStyleClass("wishlist-button")
                    .withText("blueprint.add.to.wishlist")
                    .build();
            final DestroyableLabel warning = LabelBuilder.builder()
                    .withStyleClass("warning")
                    .withText("loadout.equipment.warning")
                    .withVisibilityProperty(isValidProperty())
                    .withManagedProperty(isValidProperty())
                    .build();
            APPLICATION_STATE.getPreferredCommander().ifPresent(this::loadCommanderWishlists);
            final DestroyableRegion region1 = new DestroyableRegion();
            VBox.setVgrow(region1, Priority.ALWAYS);
            this.getNodes().addAll(region1, this.addToWishlist, warning);
        }
    }

    private void handleModificationChange(final ModificationChange modificationChange) {
        setValid(Arrays.stream(this.loadout.getModifications()).map(SelectedModification::getModification).filter(Objects::nonNull).count() >= this.loadout.getTargetLevel());
        saveLoadoutSet();
        stats.update();
        if (Objects.equals(modificationChange.getNewModification().getModification(), SuitModification.EXTRA_AMMO_CAPACITY) || Objects.equals(modificationChange.getOldModification().getModification(), SuitModification.EXTRA_AMMO_CAPACITY)) {
            EventService.publish(new AmmoCapacityModEvent());
        }
    }

    private void saveLoadoutSet() {
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander ->
                LoadoutService.saveLoadoutSet(commander, this.loadoutSet)
        );
    }

    private void loadCommanderWishlists(final Commander commander) {
        final Wishlists wishlists = WishlistService.getOdysseyWishlists(commander);
        if (this.addToWishlist != null) {
            this.addToWishlist.clear();
            final List<DestroyableMenuItem> menuItems = wishlists.getAllWishlists().stream()
                    .filter(wishlist -> wishlist != Wishlist.ALL)
                    .sorted(Comparator.comparing(Wishlist::getName))
                    .map(wishlist -> MenuItemBuilder.builder()
                            .withNonLocalizedText(wishlist.getName())
                            .withOnAction(_ -> {
                                final List<OdysseyWishlistBlueprint> wishlistBlueprints = getRequiredWishlistRecipes();
                                if (wishlistBlueprints.isEmpty()) {
                                    NotificationService.showWarning(NotificationType.ERROR, LocaleService.LocaleString.of("notification.wishlist.cannot.create.title"), LocaleService.LocaleString.of("notification.wishlist.cannot.create.text"));
                                } else {
                                    EventService.publish(new OdysseyWishlistBlueprintEvent(commander, wishlist.getUuid(), wishlistBlueprints, Action.ADDED));
                                }
                            })
                            .build())
                    .toList();
            this.addToWishlist.addAll(menuItems);
            final DestroyableMenuItem createNew = MenuItemBuilder.builder()
                    .withStyleClass("wishlist-create-new")
                    .withText("loadout.create.new.wishlist")
                    .withOnAction(_ -> {
                        final List<OdysseyWishlistBlueprint> wishlistBlueprints = getRequiredWishlistRecipes();
                        if (wishlistBlueprints.isEmpty()) {
                            NotificationService.showWarning(NotificationType.ERROR, LocaleService.LocaleString.of("notification.wishlist.cannot.create.title"), LocaleService.LocaleString.of("notification.wishlist.cannot.create.text"));
                        } else {
                            final Wishlists odysseyWishlists = WishlistService.getOdysseyWishlists(commander);
                            final Wishlist newWishlist = odysseyWishlists.createWishlist(this.loadoutSet.getName());
                            WishlistService.saveOdysseyWishlists(commander, odysseyWishlists);
                            EventService.publish(new OdysseyWishlistCreatedEvent());//refreshes wishlist dropdown
                            EventService.publish(new OdysseyWishlistBlueprintEvent(commander, newWishlist.getUuid(), wishlistBlueprints, Action.ADDED));
                        }
                    })
                    .build();
            this.addToWishlist.add(createNew);
        }
    }

    private List<OdysseyWishlistBlueprint> getRequiredWishlistRecipes() {
        final LevelValue recipes = this.loadout.getEquipment().getRecipes();
        final List<OdysseyWishlistBlueprint> wishlistBlueprints = new ArrayList<>();
        for (int level = this.loadout.getCurrentLevel() + 1; level <= this.loadout.getTargetLevel(); level++) {
            final Object recipe = recipes.getValueForLevel(level);
            if (!OdysseyBlueprintName.NONE.equals(recipe)) {
                wishlistBlueprints.add(new OdysseyWishlistBlueprint((OdysseyBlueprintName) recipe, true));
            }
        }
        wishlistBlueprints.addAll(Arrays.stream(this.loadout.getModifications()).filter(modification -> modification.getModification() != null && !WeaponModification.NONE.equals(modification.getModification())).filter(SelectedModification::isNotPresent).map(modification -> new OdysseyWishlistBlueprint(modification.getModification().getRecipe(), true)).toList());
        return wishlistBlueprints;
    }
//
//    private DestroyableHBox centerImage(final DestroyableResizableImageView resizableImageView) {
//        final DestroyableHBox hBox = BoxBuilder.builder()
//                .buildHBox();
//        final DestroyableVBox vBox = BoxBuilder.builder()
//                .buildVBox();
//        hBox.setStyle("-fx-alignment: center;-fx-min-height: 22em");
//        vBox.setStyle("-fx-alignment: center;-fx-min-height: 22em");
//        vBox.getNodes().add(resizableImageView);
//        hBox.getNodes().addAll(vBox);
//        return hBox;
//    }

}
