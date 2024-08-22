package nl.jixxed.eliteodysseymaterials.templates.odyssey.loadout;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.service.LoadoutService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.WishlistService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class OdysseyLoadoutItem extends VBox implements DestroyableTemplate {
    private static final String STYLECLASS_LOADOUT_ITEM_STATS_NAME = "loadout-item-stats-name";
    private static final String STYLECLASS_LOADOUT_ITEM_STATS_VALUE = "loadout-item-stats-value";
    private static final String STYLECLASS_LOADOUT_ITEM_STATS_VALUE_MODDED = "loadout-item-stats-value-modded";
    private static final String STYLECLASS_LOADOUT_ITEM_STATS_WIDE = "loadout-item-stats-wide";
    private static final String STYLECLASS_CHANGED = "changed";
    private static final String STYLECLASS_MODDED = "modded";
    private static final String STYLECLASS_LOADOUT_ITEM_STATS_VALUE_WIDE = "loadout-item-stats-value-wide";
    private static final String STYLECLASS_LOADOUT_ITEM_STATS_VALUE_WIDE_BOX = "loadout-item-stats-value-wide-box";
    private static final String STYLECLASS_LOADOUT_ITEM_STATS_HEADER = "loadout-item-stats-header";
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    @Getter
    private LoadoutSet loadoutSet;
    private Loadout loadout;
    private final List<HBox> statsList;
    private VBox stats;
    private DestroyableMenuButton addToWishlist;
    private DestroyableToggleSwitch statsToggle;
    private BooleanProperty isValid;
    private final List<Destroyable> destroyables = new ArrayList<>();

    private final List<EventListener<?>> eventListeners = new ArrayList<>();

    private final void setValid(final boolean value) {
        isValidProperty().set(value);
    }

    public final boolean isValid() {
        return this.isValid == null || this.isValid.get();
    }

    private final BooleanProperty isValidProperty() {
        if (this.isValid == null) {
            this.isValid = new SimpleBooleanProperty(false);
        }
        return this.isValid;
    }

    OdysseyLoadoutItem(final LoadoutSet loadoutSet, final Loadout loadout) {
        this.loadoutSet = loadoutSet;
        this.loadout = loadout;
        this.statsList = new ArrayList<>();
        initComponents();
        initEventHandling();
    }

    @Override
    public void initEventHandling() {
        if (!Suit.FLIGHTSUIT.equals(this.loadout.getEquipment())) {

            this.eventListeners.add(EventService.addListener(true, this, WishlistSelectedEvent.class, wishlistSelectedEvent -> {
                if (Suit.FLIGHTSUIT != this.loadout.getEquipment()) {
                    APPLICATION_STATE.getPreferredCommander().ifPresent(this::loadCommanderWishlists);
                }
            }));
            this.eventListeners.add(EventService.addListener(true, this, CommanderSelectedEvent.class, commanderSelectedEvent ->
                    loadCommanderWishlists(commanderSelectedEvent.getCommander())
            ));
            this.eventListeners.add(EventService.addListener(true, this, CommanderAllListedEvent.class, commanderAllListedEvent -> {
                        if (this.loadout != null && Suit.FLIGHTSUIT != this.loadout.getEquipment()) {
                            APPLICATION_STATE.getPreferredCommander().ifPresent(this::loadCommanderWishlists);
                        }
                    }
            ));
            this.eventListeners.add(EventService.addListener(true, this, ModificationChangedEvent.class, modificationChangedEvent -> {
                if (modificationChangedEvent.getLoadoutItem() == this) {
                    handleModificationChange(modificationChangedEvent.getModificationChange());
                }
            }));

        }
        if (this.loadout.getEquipment() instanceof Weapon) {
            this.eventListeners.add(EventService.addListener(true, this, AmmoCapacityModEvent.class, ammoCapacityModEvent ->
                    updateStatsList()
            ));
        }
    }

    @Override
    @SuppressWarnings("java:S3776")
    public void initComponents() {
        this.getStyleClass().add("loadout-item");
        this.spacingProperty().bind(ScalingHelper.getPixelDoubleBindingFromEm(0.25));
        this.stats = BoxBuilder.builder().withNodes().buildVBox();
        this.statsToggle = ToggleSwitchBuilder.builder()
                .withSelected(this.loadout.isShowChanged())
                .withText(LocaleService.getStringBinding(this.loadout.isShowChanged() ? "loadout.equipment.stats.toggle.changed" : "loadout.equipment.stats.toggle.all"))
                .withSelectedChangeListener((observable, oldValue, newValue) -> {
                    if (newValue != null && newValue) {
                        this.statsToggle.textProperty().bind(LocaleService.getStringBinding("loadout.equipment.stats.toggle.changed"));
                        this.loadout.setShowChanged(true);
                        saveLoadoutSet();
                    } else {
                        this.statsToggle.textProperty().bind(LocaleService.getStringBinding("loadout.equipment.stats.toggle.all"));
                        this.loadout.setShowChanged(false);
                        saveLoadoutSet();
                    }
                    updateStatsList();
                })
                .build();
        addDestroyableNode(this.statsToggle);
        //navbar
        final DestroyableLabel left = LabelBuilder.builder().withNonLocalizedText("<").withOnMouseClicked(event -> {
            this.loadoutSet.moveDown(this.loadout);
            saveLoadoutSet();
            EventService.publish(new LoadoutMovedEvent());
        }).build();
        addDestroyableNode(left);
        if (this.loadoutSet.getLoadouts().indexOf(this.loadout) <= 0) {
            left.setVisible(false);
        }
        final Region regionL = new Region();
        HBox.setHgrow(regionL, Priority.ALWAYS);
        final DestroyableLabel delete = LabelBuilder.builder().withNonLocalizedText("delete").withOnMouseClicked(event -> {
            this.loadoutSet.removeLoadout(this.loadout);
            saveLoadoutSet();
            EventService.publish(new LoadoutRemovedEvent(this));
        }).build();
        addDestroyableNode(delete);
        final Region regionR = new Region();
        HBox.setHgrow(regionR, Priority.ALWAYS);
        final DestroyableLabel right = LabelBuilder.builder().withNonLocalizedText(">").withOnMouseClicked(event -> {
            this.loadoutSet.moveUp(this.loadout);
            saveLoadoutSet();
            EventService.publish(new LoadoutMovedEvent());
        }).build();
        addDestroyableNode(right);
        if (this.loadoutSet.getLoadouts().indexOf(this.loadout) == this.loadoutSet.getLoadouts().size() - 1) {
            right.setVisible(false);
        }
        final HBox navBar = BoxBuilder.builder().withStyleClass("loadout-navbar").withNodes(left, regionL, delete, regionR, right).buildHBox();
        navBar.setVisible(!this.loadoutSet.equals(LoadoutSet.CURRENT));
        //image
        final DestroyableResizableImageView image = ResizableImageViewBuilder.builder()
                .withStyleClass("loadout-item-image")
                .withImage(this.loadout.getEquipment().getImage())
                .build();
        this.destroyables.add(image);
        //title
        final DestroyableLabel title = LabelBuilder.builder()
                .withStyleClass("loadout-item-title")
                .withText(LocaleService.getStringBinding(this.loadout.getEquipment().getLocalizationKey()))
                .build();
        this.destroyables.add(title);
        final HBox imageBox = centerImage(image);
        this.getChildren().addAll(navBar, imageBox, title);
        if (Suit.FLIGHTSUIT != this.loadout.getEquipment()) {
            //current level
            final DestroyableLabel currentLevelLabel = LabelBuilder.builder()
                    .withStyleClass("loadout-item-level-label")
                    .withText(LocaleService.getStringBinding("loadout.equipment.level.current"))
                    .build();
            this.destroyables.add(currentLevelLabel);
            final DestroyableComboBox<Integer> currentLevel = ComboBoxBuilder.builder(Integer.class)
                    .withItemsProperty(FXCollections.observableArrayList(1, 2, 3, 4, 5))
                    .withValueChangeListener((observable, oldValue, newValue) -> {
                        if (newValue != null) {
                            this.loadout.setCurrentLevel(newValue);
                            updateStatsList();
                            saveLoadoutSet();
                        }
                    })
                    .build();
            this.destroyables.add(currentLevel);
            currentLevel.getSelectionModel().select(this.loadout.getCurrentLevel());
            final Region regionCurrent = new Region();
            HBox.setHgrow(regionCurrent, Priority.ALWAYS);
            currentLevelLabel.setAlignment(Pos.CENTER_LEFT);
            final HBox currentLevelBox = BoxBuilder.builder()
                    .withNodes(currentLevelLabel, regionCurrent, currentLevel)
                    .buildHBox();
            //target level
            final DestroyableLabel targetLevelLabel = LabelBuilder.builder()
                    .withStyleClass("loadout-item-level-label")
                    .withText(LocaleService.getStringBinding("loadout.equipment.level.target"))
                    .build();
            this.destroyables.add(targetLevelLabel);
            final DestroyableComboBox<Integer> targetLevel = ComboBoxBuilder.builder(Integer.class)
                    .withItemsProperty(FXCollections.observableArrayList(1, 2, 3, 4, 5))
                    .withValueChangeListener((observable, oldValue, newValue) -> {
                        if (newValue != null) {
                            this.loadout.setTargetLevel(newValue);
                            setValid(Arrays.stream(this.loadout.getModifications()).map(SelectedModification::getModification).filter(Objects::nonNull).count() >= newValue);
                            updateStatsList();
                            saveLoadoutSet();
                        }
                    })
                    .build();
            this.destroyables.add(targetLevel);
            targetLevel.getSelectionModel().select(this.loadout.getTargetLevel());
            final Region regionTarget = new Region();
            HBox.setHgrow(regionTarget, Priority.ALWAYS);
            targetLevelLabel.setAlignment(Pos.CENTER_LEFT);
            final HBox targetLevelBox = BoxBuilder.builder()
                    .withNodes(targetLevelLabel, regionTarget, targetLevel)
                    .buildHBox();
            //auto set correct levels on change
            currentLevel.addChangeListener(currentLevel.valueProperty(), (observable, oldValue, newValue) -> {
                if (newValue != null && (Integer) newValue > this.loadout.getTargetLevel()) {
                    targetLevel.getSelectionModel().select((Integer) newValue);
                }
            });
            targetLevel.addChangeListener(targetLevel.valueProperty(), (observable, oldValue, newValue) -> {
                if (newValue != null && (Integer) newValue < this.loadout.getCurrentLevel()) {
                    currentLevel.getSelectionModel().select((Integer) newValue);
                }
            });

            currentLevel.setDisable(this.loadoutSet.equals(LoadoutSet.CURRENT));
            targetLevel.setDisable(this.loadoutSet.equals(LoadoutSet.CURRENT));
            //modifications
            final DestroyableLabel modificationsLabel = LabelBuilder
                    .builder().withStyleClass("loadout-item-subtitle")
                    .withText(LocaleService.getStringBinding("loadout.equipment.modifications"))
                    .build();
            this.destroyables.add(modificationsLabel);
            final OdysseyLoadoutModification loadoutModification1 = new OdysseyLoadoutModification(this.loadout, 0, this);
            final OdysseyLoadoutModification loadoutModification2 = new OdysseyLoadoutModification(this.loadout, 1, this);
            final OdysseyLoadoutModification loadoutModification3 = new OdysseyLoadoutModification(this.loadout, 2, this);
            final OdysseyLoadoutModification loadoutModification4 = new OdysseyLoadoutModification(this.loadout, 3, this);

            this.destroyables.add(loadoutModification1);
            this.destroyables.add(loadoutModification2);
            this.destroyables.add(loadoutModification3);
            this.destroyables.add(loadoutModification4);

            final HBox modifications = BoxBuilder.builder()
                    .withNodes(loadoutModification1, createRegion(), loadoutModification2, createRegion(), loadoutModification3, createRegion(), loadoutModification4)
                    .buildHBox();
            this.getChildren().addAll(currentLevelBox, targetLevelBox, modificationsLabel, modifications);

        }
        //stats
        final Label statsLabel = LabelBuilder.builder()
                .withStyleClass("loadout-item-subtitle")
                .withText(LocaleService.getStringBinding("loadout.equipment.stats"))
                .build();
        final Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        final HBox statsLine = BoxBuilder.builder().withNodes(statsLabel, region, this.statsToggle).buildHBox();
        updateStatsList();

        this.getChildren().addAll(statsLine, this.stats);
        if (Suit.FLIGHTSUIT != this.loadout.getEquipment() && !this.loadoutSet.equals(LoadoutSet.CURRENT)) {
            this.addToWishlist = MenuButtonBuilder.builder().withStyleClass("loadout-wishlist-button").withText(LocaleService.getStringBinding("blueprint.add.to.wishlist")).build();
            final Label warning = LabelBuilder.builder().withStyleClass("loadout-warning").withText(LocaleService.getStringBinding("loadout.equipment.warning")).withVisibilityProperty(isValidProperty()).build();
            APPLICATION_STATE.getPreferredCommander().ifPresent(this::loadCommanderWishlists);
            final Region region1 = new Region();
            VBox.setVgrow(region1, Priority.ALWAYS);
            this.getChildren().addAll(region1, this.addToWishlist, warning);
        }
    }

    private void handleModificationChange(final ModificationChange modificationChange) {
        setValid(Arrays.stream(this.loadout.getModifications()).map(SelectedModification::getModification).filter(Objects::nonNull).count() >= this.loadout.getTargetLevel());
        saveLoadoutSet();
        updateStatsList();
        if (Objects.equals(modificationChange.getNewModification().getModification(), SuitModification.EXTRA_AMMO_CAPACITY) || Objects.equals(modificationChange.getOldModification().getModification(), SuitModification.EXTRA_AMMO_CAPACITY)) {
            EventService.publish(new AmmoCapacityModEvent());
        }
    }

    private void saveLoadoutSet() {
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander ->
                LoadoutService.saveLoadoutSet(commander, this.loadoutSet)
        );
    }

    private Wishlists loadCommanderWishlists(final Commander commander) {
        final Wishlists wishlists = WishlistService.getWishlists(commander);
        if (this.addToWishlist != null) {
            this.addToWishlist.getItems().stream().map(DestroyableMenuItem.class::cast).forEach(DestroyableMenuItem::destroy);
            this.addToWishlist.getItems().clear();
            final List<DestroyableMenuItem> menuItems = wishlists.getAllWishlists().stream().filter(wishlist -> wishlist != Wishlist.ALL).sorted(Comparator.comparing(Wishlist::getName)).map(wishlist -> {
                final DestroyableMenuItem menuItem = new DestroyableMenuItem();
                menuItem.setOnAction(event -> {
                    final List<OdysseyWishlistBlueprint> wishlistBlueprints = getRequiredWishlistRecipes();
                    if (wishlistBlueprints.isEmpty()) {
                        NotificationService.showWarning(NotificationType.ERROR, "Can't create wishlist", "No items to add");
                    } else {
                        EventService.publish(new WishlistBlueprintEvent(commander, wishlist.getUuid(), wishlistBlueprints, Action.ADDED));
                    }
                });
                menuItem.setText(wishlist.getName());
                return menuItem;
            }).toList();
            this.addToWishlist.getItems().addAll(menuItems);
            final DestroyableMenuItem createNew = new DestroyableMenuItem();
            createNew.setOnAction(event -> {
                final List<OdysseyWishlistBlueprint> wishlistBlueprints = getRequiredWishlistRecipes();
                if (wishlistBlueprints.isEmpty()) {
                    NotificationService.showWarning(NotificationType.ERROR, "Can't create wishlist", "No items to add");
                } else {
                    final Wishlists odysseyWishlists = WishlistService.getWishlists(commander);
                    final Wishlist newWishlist = odysseyWishlists.createWishlist(this.loadoutSet.getName());
                    WishlistService.saveWishlists(commander, odysseyWishlists);
                    EventService.publish(new WishlistCreatedEvent());//refreshes wishlist dropdown
                    EventService.publish(new WishlistBlueprintEvent(commander, newWishlist.getUuid(), wishlistBlueprints, Action.ADDED));
                }
            });
            createNew.textProperty().bind(LocaleService.getStringBinding("loadout.create.new.wishlist"));
            createNew.getStyleClass().add("loadout-wishlist-create-new");
            this.addToWishlist.getItems().add(createNew);
        }
        return wishlists;
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

    private void updateStatsList() {
        this.statsList.clear();

        final AtomicReference<StatGroup> currentGroup = new AtomicReference<>();
        this.loadout.getEquipment().getStats().entrySet().stream()
                .sorted(Comparator.comparing((Map.Entry<Stat, Object> statObjectEntry) -> statObjectEntry.getKey().getStatGroup()).thenComparing(statObjectEntry -> statObjectEntry.getKey().getOrder()))
                .forEach(statObjectEntry ->
                {
                    if (!Objects.equals(currentGroup.get(), statObjectEntry.getKey().getStatGroup())) {

                        final Separator separator = new Separator(Orientation.HORIZONTAL);
                        HBox.setHgrow(separator, Priority.ALWAYS);
                        this.statsList.add(new HBox(separator));

                        currentGroup.set(statObjectEntry.getKey().getStatGroup());
                        final StatGroup group = statObjectEntry.getKey().getStatGroup();
                        final DestroyableLabel nameColumn = LabelBuilder.builder().withStyleClasses(STYLECLASS_LOADOUT_ITEM_STATS_NAME).withText(LocaleService.getStringBinding(group.getLocalizationKey())).build();
                        this.destroyables.add(nameColumn);
                        this.statsList.add(BoxBuilder.builder().withNodes(
                                nameColumn
                        ).buildHBox());
                    }
                    if (statObjectEntry.getKey() instanceof DynamicStat stat) {
                        handleDynamicStat(statObjectEntry, stat);
                    } else {
                        handleStaticStat(statObjectEntry);
                    }
                });
        addHeader();
        this.stats.getChildren().clear();
        this.stats.getChildren().addAll(this.statsList);
    }

    private void addHeader() {
        final DestroyableLabel valueColumn = LabelBuilder.builder().withStyleClasses(STYLECLASS_LOADOUT_ITEM_STATS_HEADER, STYLECLASS_LOADOUT_ITEM_STATS_NAME).withNonLocalizedText("").build();
        final DestroyableLabel headerCurrent = LabelBuilder.builder().withStyleClasses(STYLECLASS_LOADOUT_ITEM_STATS_HEADER, STYLECLASS_LOADOUT_ITEM_STATS_VALUE).withText(LocaleService.getStringBinding("loadout.equipment.stats.header.current")).build();
        this.destroyables.add(headerCurrent);
        this.destroyables.add(valueColumn);
        final HBox headerRow = BoxBuilder.builder().withNodes(valueColumn, headerCurrent).buildHBox();
        if (Suit.FLIGHTSUIT.equals(this.loadout.getEquipment())) {
            valueColumn.getStyleClass().add(STYLECLASS_LOADOUT_ITEM_STATS_WIDE);
        } else {
            final DestroyableLabel headerTarget = LabelBuilder.builder().withStyleClasses(STYLECLASS_LOADOUT_ITEM_STATS_HEADER, STYLECLASS_LOADOUT_ITEM_STATS_VALUE).withText(LocaleService.getStringBinding("loadout.equipment.stats.header.target")).build();
            final DestroyableLabel headerModded = LabelBuilder.builder().withStyleClasses(STYLECLASS_LOADOUT_ITEM_STATS_HEADER, STYLECLASS_LOADOUT_ITEM_STATS_VALUE, STYLECLASS_LOADOUT_ITEM_STATS_VALUE_MODDED).withText(LocaleService.getStringBinding("loadout.equipment.stats.header.modded")).build();
            this.destroyables.add(headerTarget);
            this.destroyables.add(headerModded);
            headerRow.getChildren().addAll(headerTarget, headerModded);
        }
        this.statsList.add(0, headerRow);
    }

    private void handleStaticStat(final Map.Entry<Stat, Object> statObjectEntry) {
        if (this.statsToggle.isSelected()) {
            return;
        }
        final StaticStat stat = (StaticStat) statObjectEntry.getKey();
        final Object value = statObjectEntry.getValue();
        final String currentLevelValue = stat.formatValue(value, this.loadout.getEquipment(), this.loadout.getCurrentLevel());
        final DestroyableLabel nameColumn = LabelBuilder.builder().withStyleClasses(STYLECLASS_LOADOUT_ITEM_STATS_NAME).withText(LocaleService.getStringBinding(stat.getLocalizationKey())).build();
        final DestroyableLabel currentLevelValueLabel = LabelBuilder.builder().withStyleClasses(STYLECLASS_LOADOUT_ITEM_STATS_VALUE_WIDE).withNonLocalizedText(currentLevelValue).build();
        this.destroyables.add(nameColumn);
        this.destroyables.add(currentLevelValueLabel);
        final Separator separator = new Separator(Orientation.HORIZONTAL);
        final Separator separator1 = new Separator(Orientation.HORIZONTAL);
        HBox.setHgrow(separator, Priority.ALWAYS);
        HBox.setHgrow(currentLevelValueLabel, Priority.ALWAYS);
        HBox.setHgrow(separator1, Priority.ALWAYS);
        this.statsList.add(BoxBuilder.builder().withNodes(
                nameColumn,
                BoxBuilder.builder().withStyleClass(STYLECLASS_LOADOUT_ITEM_STATS_VALUE_WIDE_BOX).withNodes(separator,
                        currentLevelValueLabel,
                        separator1).buildHBox()
        ).buildHBox());
    }

    private void handleDynamicStat(final Map.Entry<Stat, Object> statObjectEntry, final DynamicStat stat) {
        final Object value = statObjectEntry.getValue();
        final String currentLevelValue = stat.formatValue(value, this.loadout.getEquipment(), this.loadout.getCurrentLevel());
        final String targetLevelValue = stat.formatValue(value, this.loadout.getEquipment(), this.loadout.getTargetLevel());
        final List<SelectedModification> modifications = new ArrayList<>(Arrays.asList(this.loadout.getModifications()));
        if (this.loadout.getEquipment() instanceof Weapon && this.loadoutSet.getLoadouts().stream().filter(loadoutItem -> loadoutItem.getEquipment() instanceof Suit)
                .anyMatch(loadoutItem -> Arrays.stream(loadoutItem.getModifications())
                        .map(SelectedModification::getModification)
                        .anyMatch(SuitModification.EXTRA_AMMO_CAPACITY::equals))) {
            modifications.add(new SelectedModification(SuitModification.EXTRA_AMMO_CAPACITY, false));
        }
        final List<Modification> modifications1 = modifications.stream().map(SelectedModification::getModification).filter(Objects::nonNull).toList();
        final String moddedLevelValue = stat.formatValue(value, this.loadout.getEquipment(), this.loadout.getTargetLevel(), modifications1);
        if (this.statsToggle.isSelected() && Objects.equals(currentLevelValue, targetLevelValue) && Objects.equals(targetLevelValue, moddedLevelValue)) {
            return;
        }
        final Label nameColumn = LabelBuilder.builder().withStyleClasses(STYLECLASS_LOADOUT_ITEM_STATS_NAME).withText(LocaleService.getStringBinding(stat.getLocalizationKey())).build();
        final DestroyableLabel currentValueLabel = LabelBuilder.builder().withStyleClasses(STYLECLASS_LOADOUT_ITEM_STATS_VALUE).withNonLocalizedText(currentLevelValue).build();
        this.destroyables.add(currentValueLabel);
        final HBox valueRow = BoxBuilder.builder().withNodes(nameColumn, currentValueLabel).buildHBox();
        if (Suit.FLIGHTSUIT.equals(this.loadout.getEquipment())) {
            nameColumn.getStyleClass().add(STYLECLASS_LOADOUT_ITEM_STATS_WIDE);
        } else {
            final DestroyableLabel targetValue = LabelBuilder.builder().withStyleClasses(STYLECLASS_LOADOUT_ITEM_STATS_VALUE).withNonLocalizedText(targetLevelValue).build();
            final DestroyableLabel moddedValue = LabelBuilder.builder().withStyleClasses(STYLECLASS_LOADOUT_ITEM_STATS_VALUE, STYLECLASS_LOADOUT_ITEM_STATS_VALUE_MODDED).withNonLocalizedText(moddedLevelValue).build();
            this.destroyables.add(targetValue);
            this.destroyables.add(moddedValue);
            valueRow.getChildren().addAll(targetValue, moddedValue);
            if (!currentLevelValue.equals(targetLevelValue)) {
                targetValue.getStyleClass().add(STYLECLASS_CHANGED);
            }
            if (!currentLevelValue.equals(moddedLevelValue)) {
                moddedValue.getStyleClass().add(STYLECLASS_CHANGED);
            }
            if (!targetLevelValue.equals(moddedLevelValue)) {
                moddedValue.getStyleClass().add(STYLECLASS_MODDED);
            }
        }
        this.statsList.add(valueRow);
    }

    private Region createRegion() {
        final Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        return region;
    }

    private HBox centerImage(final DestroyableResizableImageView resizableImageView) {
        final HBox hBox = BoxBuilder.builder().buildHBox();
        final VBox vBox = BoxBuilder.builder().buildVBox();
        hBox.setStyle("-fx-alignment: center;-fx-min-height: 22em");
        vBox.setStyle("-fx-alignment: center;-fx-min-height: 22em");
        vBox.getChildren().add(resizableImageView);
        hBox.getChildren().addAll(vBox);
        return hBox;
    }


    @Override
    public List<Destroyable> getDestroyablesList() {
        return this.destroyables;
    }

    @Override
    public void destroyInternal() {
        this.eventListeners.forEach(EventService::removeListener);
        this.statsList.clear();
        this.loadoutSet = null;
        this.loadout = null;
        this.stats = null;
        this.addToWishlist = null;
        this.statsToggle = null;
        this.isValid = null;
    }

}
