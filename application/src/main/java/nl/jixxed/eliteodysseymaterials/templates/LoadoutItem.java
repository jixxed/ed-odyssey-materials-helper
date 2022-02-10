package nl.jixxed.eliteodysseymaterials.templates;

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
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class LoadoutItem extends VBox implements DestroyableTemplate {
    private final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private LoadoutSet loadoutSet;
    private Loadout loadout;
    private final List<HBox> statsList;
    private VBox stats;
    private DestroyableMenuButton addToWishlist;
    private DestroyableToggleSwitch statsToggle;
    private BooleanProperty isValid;
    private final List<Destroyable> destroyables = new ArrayList<>();

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

    LoadoutItem(final LoadoutSet loadoutSet, final Loadout loadout) {
        this.loadoutSet = loadoutSet;
        this.loadout = loadout;
        this.statsList = new ArrayList<>();
        initComponents();
        initEventHandling();
        System.gc();
    }

    @Override
    public void initEventHandling() {
        if (!Suit.FLIGHTSUIT.equals(this.loadout.getEquipment())) {

            EventService.addListener(this, WishlistSelectedEvent.class, wishlistSelectedEvent ->
                    this.APPLICATION_STATE.getPreferredCommander().ifPresent(this::loadCommanderWishlists))
            ;
            EventService.addListener(this, CommanderSelectedEvent.class, commanderSelectedEvent ->
                    loadCommanderWishlists(commanderSelectedEvent.getCommander())
            );
            EventService.addListener(this, CommanderAllListedEvent.class, commanderAllListedEvent ->
                    this.APPLICATION_STATE.getPreferredCommander().ifPresent(this::loadCommanderWishlists)
            );
            EventService.addListener(this, ModificationChangedEvent.class, modificationChangedEvent -> {
                if (modificationChangedEvent.getLoadoutItem() == this) {
                    handleModificationChange(modificationChangedEvent.getModificationChange());
                }
            });

        }
        if (this.loadout.getEquipment() instanceof Weapon) {
            EventService.addListener(this, AmmoCapacityModEvent.class, ammoCapacityModEvent -> {
                updateStatsList();
            });
        }
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("loadout-item");
        this.setSpacing(5);
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
//            this.onDestroy();
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
                            setValid(Arrays.stream(this.loadout.getModifications()).filter(Objects::nonNull).count() >= newValue);
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

            //modifications
            final DestroyableLabel modificationsLabel = LabelBuilder
                    .builder().withStyleClass("loadout-item-subtitle")
                    .withText(LocaleService.getStringBinding("loadout.equipment.modifications"))
                    .build();
            this.destroyables.add(modificationsLabel);
            final LoadoutModification loadoutModification1 = new LoadoutModification(this.loadout, 0, this);
            final LoadoutModification loadoutModification2 = new LoadoutModification(this.loadout, 1, this);
            final LoadoutModification loadoutModification3 = new LoadoutModification(this.loadout, 2, this);
            final LoadoutModification loadoutModification4 = new LoadoutModification(this.loadout, 3, this);

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
        if (Suit.FLIGHTSUIT != this.loadout.getEquipment()) {
            this.addToWishlist = MenuButtonBuilder.builder().withStyleClass("loadout-wishlist-button").withText(LocaleService.getStringBinding("recipe.add.to.wishlist")).build();
//            this.addToWishlist.getItems().addAll(new ArrayList<>());
            final Label warning = LabelBuilder.builder().withStyleClass("loadout-warning").withText(LocaleService.getStringBinding("loadout.equipment.warning")).withVisibilityProperty(isValidProperty()).build();
            this.APPLICATION_STATE.getPreferredCommander().ifPresent(this::loadCommanderWishlists);
            final Region region1 = new Region();
            VBox.setVgrow(region1, Priority.ALWAYS);
            this.getChildren().addAll(region1, this.addToWishlist, warning);
        }
    }

    private void handleModificationChange(final ModificationChange modificationChange) {
        setValid(Arrays.stream(this.loadout.getModifications()).filter(Objects::nonNull).count() >= this.loadout.getTargetLevel());
        saveLoadoutSet();
        updateStatsList();
        if (Objects.equals(modificationChange.getNewModification(), SuitModification.EXTRA_AMMO_CAPACITY) || Objects.equals(modificationChange.getOldModification(), SuitModification.EXTRA_AMMO_CAPACITY)) {
            EventService.publish(new AmmoCapacityModEvent());
        }
    }

    private void saveLoadoutSet() {
        this.APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
            this.APPLICATION_STATE.saveLoadoutSet(commander.getFid(), this.loadoutSet);
        });
    }

    private Wishlists loadCommanderWishlists(final Commander commander) {
        final Wishlists wishlists = this.APPLICATION_STATE.getWishlists(commander.getFid());
        this.addToWishlist.getItems().stream().map(DestroyableMenuItem.class::cast).forEach(DestroyableMenuItem::destroy);
        this.addToWishlist.getItems().clear();
        final List<DestroyableMenuItem> menuItems = wishlists.getAllWishlists().stream().sorted(Comparator.comparing(Wishlist::getName)).map(wishlist -> {
            final DestroyableMenuItem menuItem = new DestroyableMenuItem();
            menuItem.setOnAction(event -> {
                final List<WishlistRecipe> wishlistRecipes = getRequiredWishlistRecipes();
                EventService.publish(new WishlistRecipeEvent(commander.getFid(), wishlist.getUuid(), wishlistRecipes, Action.ADDED));
            });
            menuItem.setText(wishlist.getName());
            return menuItem;
        }).toList();
        this.addToWishlist.getItems().addAll(menuItems);
        return wishlists;
    }

    private List<WishlistRecipe> getRequiredWishlistRecipes() {
        final LevelValue recipes = this.loadout.getEquipment().getRecipes();
        final List<WishlistRecipe> wishlistRecipes = new ArrayList<>();
        for (int level = this.loadout.getCurrentLevel() + 1; level <= this.loadout.getTargetLevel(); level++) {
            final Object recipe = recipes.getValueForLevel(level);
            if (!RecipeName.NONE.equals(recipe)) {
                wishlistRecipes.add(new WishlistRecipe((RecipeName) recipe, true));
            }
        }
        wishlistRecipes.addAll(Arrays.stream(this.loadout.getModifications()).filter(modification -> modification != null && !WeaponModification.NONE.equals(modification)).map(modification -> new WishlistRecipe(modification.getRecipe(), true)).toList());
        return wishlistRecipes;
    }

    private void updateStatsList() {
        this.statsList.clear();

        final AtomicReference<StatGroup> currentGroup = new AtomicReference<>();
        this.loadout.getEquipment().getStats().entrySet().stream()
                .sorted(Comparator.comparing(statObjectEntry -> statObjectEntry.getKey().getStatGroup()))
                .forEach(statObjectEntry ->
                {
                    if (!Objects.equals(currentGroup.get(), statObjectEntry.getKey().getStatGroup())) {

                        final Separator separator = new Separator(Orientation.HORIZONTAL);
                        HBox.setHgrow(separator, Priority.ALWAYS);
                        this.statsList.add(new HBox(separator));

                        currentGroup.set(statObjectEntry.getKey().getStatGroup());
                        final StatGroup group = statObjectEntry.getKey().getStatGroup();
                        final DestroyableLabel nameColumn = LabelBuilder.builder().withStyleClasses("loadout-item-stats-name").withText(LocaleService.getStringBinding(group.getLocalizationKey())).build();
                        this.destroyables.add(nameColumn);
                        this.statsList.add(BoxBuilder.builder().withNodes(
                                nameColumn
                        ).buildHBox());
                    }
                    if (statObjectEntry.getKey() instanceof DynamicStat stat) {
                        final Object value = statObjectEntry.getValue();
                        final String currentLevelValue = stat.formatValue(value, this.loadout.getEquipment(), this.loadout.getCurrentLevel());
                        final String targetLevelValue = stat.formatValue(value, this.loadout.getEquipment(), this.loadout.getTargetLevel());
                        final List<Modification> modifications = new ArrayList<>(Arrays.asList(this.loadout.getModifications()));
                        if (this.loadoutSet.getLoadouts().stream().filter(loadoutItem -> loadoutItem.getEquipment() instanceof Suit).anyMatch(loadoutItem -> Arrays.asList(loadoutItem.getModifications()).contains(SuitModification.EXTRA_AMMO_CAPACITY))) {
                            modifications.add(SuitModification.EXTRA_AMMO_CAPACITY);
                        }
                        final String moddedLevelValue = stat.formatValue(value, this.loadout.getEquipment(), this.loadout.getTargetLevel(), modifications);
                        if (this.statsToggle.isSelected() && Objects.equals(currentLevelValue, targetLevelValue) && Objects.equals(targetLevelValue, moddedLevelValue)) {
                            return;
                        }
                        final Label nameColumn = LabelBuilder.builder().withStyleClasses("loadout-item-stats-name").withText(LocaleService.getStringBinding(stat.getLocalizationKey())).build();
                        final DestroyableLabel currentValueLabel = LabelBuilder.builder().withStyleClasses("loadout-item-stats-value").withNonLocalizedText(currentLevelValue).build();
                        this.destroyables.add(currentValueLabel);
                        final HBox valueRow = BoxBuilder.builder().withNodes(nameColumn, currentValueLabel).buildHBox();
                        if (Suit.FLIGHTSUIT.equals(this.loadout.getEquipment())) {
                            nameColumn.getStyleClass().add("loadout-item-stats-wide");
                        } else {
                            final DestroyableLabel targetValue = LabelBuilder.builder().withStyleClasses("loadout-item-stats-value").withNonLocalizedText(targetLevelValue).build();
                            final DestroyableLabel moddedValue = LabelBuilder.builder().withStyleClasses("loadout-item-stats-value", "loadout-item-stats-value-modded").withNonLocalizedText(moddedLevelValue).build();
                            this.destroyables.add(targetValue);
                            this.destroyables.add(moddedValue);
                            valueRow.getChildren().addAll(targetValue, moddedValue);
                            if (!currentLevelValue.equals(targetLevelValue)) {
                                targetValue.getStyleClass().add("changed");
                            }
                            if (!currentLevelValue.equals(moddedLevelValue)) {
                                moddedValue.getStyleClass().add("changed");
                            }
                            if (!targetLevelValue.equals(moddedLevelValue)) {
                                moddedValue.getStyleClass().add("modded");
                            }
                        }
                        this.statsList.add(valueRow);
                    } else {
                        if (this.statsToggle.isSelected()) {
                            return;
                        }
                        final StaticStat stat = (StaticStat) statObjectEntry.getKey();
                        final Object value = statObjectEntry.getValue();
                        final String currentLevelValue = stat.formatValue(value, this.loadout.getEquipment(), this.loadout.getCurrentLevel());
                        final DestroyableLabel nameColumn = LabelBuilder.builder().withStyleClasses("loadout-item-stats-name").withText(LocaleService.getStringBinding(stat.getLocalizationKey())).build();
                        final DestroyableLabel currentLevelValueLabel = LabelBuilder.builder().withStyleClasses("loadout-item-stats-value-wide").withNonLocalizedText(currentLevelValue).build();
                        this.destroyables.add(nameColumn);
                        this.destroyables.add(currentLevelValueLabel);
                        final Separator separator = new Separator(Orientation.HORIZONTAL);
                        final Separator separator1 = new Separator(Orientation.HORIZONTAL);
                        HBox.setHgrow(separator, Priority.ALWAYS);
                        HBox.setHgrow(currentLevelValueLabel, Priority.ALWAYS);
                        HBox.setHgrow(separator1, Priority.ALWAYS);
                        this.statsList.add(BoxBuilder.builder().withNodes(
                                nameColumn,
                                BoxBuilder.builder().withStyleClass("loadout-item-stats-value-wide-box").withNodes(separator,
                                        currentLevelValueLabel,
                                        separator1).buildHBox()
                        ).buildHBox());
                    }
                });
        if (!Suit.FLIGHTSUIT.equals(this.loadout.getEquipment())) {//|| Suit.FLIGHTSUIT.equals(this.loadout.getEquipment()) && !this.statsToggle.isSelected()
            final DestroyableLabel valueColumn = LabelBuilder.builder().withStyleClasses("loadout-item-stats-header", "loadout-item-stats-name").withNonLocalizedText("").build();
            final DestroyableLabel headerCurrent = LabelBuilder.builder().withStyleClasses("loadout-item-stats-header", "loadout-item-stats-value").withText(LocaleService.getStringBinding("loadout.equipment.stats.header.current")).build();
            this.destroyables.add(headerCurrent);
            this.destroyables.add(valueColumn);
            final HBox headerRow = BoxBuilder.builder().withNodes(valueColumn, headerCurrent).buildHBox();
            if (Suit.FLIGHTSUIT.equals(this.loadout.getEquipment())) {
                valueColumn.getStyleClass().add("loadout-item-stats-wide");
            } else {
                final DestroyableLabel headerTarget = LabelBuilder.builder().withStyleClasses("loadout-item-stats-header", "loadout-item-stats-value").withText(LocaleService.getStringBinding("loadout.equipment.stats.header.target")).build();
                final DestroyableLabel headerModded = LabelBuilder.builder().withStyleClasses("loadout-item-stats-header", "loadout-item-stats-value", "loadout-item-stats-value-modded").withText(LocaleService.getStringBinding("loadout.equipment.stats.header.modded")).build();
                this.destroyables.add(headerTarget);
                this.destroyables.add(headerModded);
                headerRow.getChildren().addAll(headerTarget, headerModded);
            }
            this.statsList.add(0, headerRow);
        }
        this.stats.getChildren().clear();
        this.stats.getChildren().addAll(this.statsList);
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
        EventService.removeListener(this);
        this.statsList.clear();
        this.loadoutSet = null;
        this.loadout = null;
        this.stats = null;
        this.addToWishlist = null;
        this.statsToggle = null;
        this.isValid = null;
    }

}
