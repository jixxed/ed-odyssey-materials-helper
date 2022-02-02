package nl.jixxed.eliteodysseymaterials.templates;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ComboBoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import org.controlsfx.control.ToggleSwitch;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class LoadoutItem extends VBox implements Template {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private final LoadoutSet loadoutSet;
    private final Loadout loadout;
    private final List<HBox> statsList;
    private final List<HBox> staticStatsList;
    private VBox stats;
    private MenuButton addToWishlist;
    private final List<EventListener<?>> listeners = new ArrayList<>();
    private ToggleSwitch statsToggle;
    private BooleanProperty isValid;
    private LoadoutModification loadoutModification1;
    private LoadoutModification loadoutModification2;
    private LoadoutModification loadoutModification3;
    private LoadoutModification loadoutModification4;

    private final void setValid(final boolean value) {
        isValidProperty().set(value);
    }

    public final boolean isValid() {
        return this.isValid == null || this.isValid.get();
    }

    private final BooleanProperty isValidProperty() {
        if (this.isValid == null) {
            this.isValid = new SimpleBooleanProperty(false) {

                @Override
                public Object getBean() {
                    return LoadoutItem.this;
                }

                @Override
                public String getName() {
                    return "isValid";
                }
            };
        }
        return this.isValid;
    }

    LoadoutItem(final LoadoutSet loadoutSet, final Loadout loadout) {
        this.loadoutSet = loadoutSet;
        this.loadout = loadout;
        this.statsList = new ArrayList<>();
        this.staticStatsList = new ArrayList<>();
        initComponents();
        initEventHandling();
    }

    @Override
    public void initEventHandling() {
        if (!Suit.FLIGHTSUIT.equals(this.loadout.getEquipment())) {

            this.listeners.add(EventService.addListener(this, WishlistSelectedEvent.class, wishlistSelectedEvent ->
                    APPLICATION_STATE.getPreferredCommander().ifPresent(this::loadCommanderWishlists))
            );
            this.listeners.add(EventService.addListener(this, CommanderSelectedEvent.class, commanderSelectedEvent ->
                    loadCommanderWishlists(commanderSelectedEvent.getCommander())
            ));
            this.listeners.add(EventService.addListener(this, CommanderAllListedEvent.class, commanderAllListedEvent ->
                    APPLICATION_STATE.getPreferredCommander().ifPresent(this::loadCommanderWishlists)
            ));
        }
        if (this.loadout.getEquipment() instanceof Weapon) {
            this.listeners.add(EventService.addListener(this, AmmoCapacityModEvent.class, ammoCapacityModEvent -> {
                updateStatsList();
            }));
        }
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("loadout-item");
        this.setSpacing(5);
        this.stats = BoxBuilder.builder().withNodes().buildVBox();
        this.statsToggle = new ToggleSwitch();
        this.statsToggle.setSelected(this.loadout.isShowChanged());
        this.statsToggle.textProperty().bind(LocaleService.getStringBinding(this.loadout.isShowChanged() ? "loadout.equipment.stats.toggle.changed" : "loadout.equipment.stats.toggle.all"));
        this.statsToggle.selectedProperty().addListener((observable, oldValue, newValue) -> {
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
        });
        //navbar
        final Label left = LabelBuilder.builder().withNonLocalizedText("<").withOnMouseClicked(event -> {
            this.loadoutSet.moveDown(this.loadout);
            saveLoadoutSet();
            EventService.publish(new LoadoutMovedEvent());
        }).build();
        if (this.loadoutSet.getLoadouts().indexOf(this.loadout) <= 0) {
            left.setVisible(false);
        }
        final Region regionL = new Region();
        HBox.setHgrow(regionL, Priority.ALWAYS);
        final Label delete = LabelBuilder.builder().withNonLocalizedText("delete").withOnMouseClicked(event -> {
            this.onDestroy();
            this.loadoutSet.removeLoadout(this.loadout);
            saveLoadoutSet();
            EventService.publish(new LoadoutRemovedEvent(this));
        }).build();
        final Region regionR = new Region();
        HBox.setHgrow(regionR, Priority.ALWAYS);
        final Label right = LabelBuilder.builder().withNonLocalizedText(">").withOnMouseClicked(event -> {
            this.loadoutSet.moveUp(this.loadout);
            saveLoadoutSet();
            EventService.publish(new LoadoutMovedEvent());
        }).build();
        if (this.loadoutSet.getLoadouts().indexOf(this.loadout) == this.loadoutSet.getLoadouts().size() - 1) {
            right.setVisible(false);
        }
        final HBox navBar = BoxBuilder.builder().withStyleClass("loadout-navbar").withNodes(left, regionL, delete, regionR, right).buildHBox();
        //image
        final ResizableImageView image = ResizableImageViewBuilder.builder()
                .withStyleClass("loadout-item-image")
                .withImage(this.loadout.getEquipment().getImage())
                .build();
        //title
        final Label title = LabelBuilder.builder()
                .withStyleClass("loadout-item-title")
                .withText(LocaleService.getStringBinding(this.loadout.getEquipment().getLocalizationKey()))
                .build();
        final HBox imageBox = centerImage(image);
        this.getChildren().addAll(navBar, imageBox, title);
        if (Suit.FLIGHTSUIT != this.loadout.getEquipment()) {
            //current level
            final Label currentLevelLabel = LabelBuilder.builder()
                    .withStyleClass("loadout-item-level-label")
                    .withText(LocaleService.getStringBinding("loadout.equipment.level.current"))
                    .build();
            final ComboBox<Integer> currentLevel = ComboBoxBuilder.builder(Integer.class)
                    .withItemsProperty(FXCollections.observableArrayList(1, 2, 3, 4, 5))
                    .withValueChangeListener((observable, oldValue, newValue) -> {
                        if (newValue != null) {
                            this.loadout.setCurrentLevel(newValue);
                            updateStatsList();
                            saveLoadoutSet();
                        }
                    })
                    .build();
            currentLevel.getSelectionModel().select(this.loadout.getCurrentLevel());
            final Region regionCurrent = new Region();
            HBox.setHgrow(regionCurrent, Priority.ALWAYS);
            currentLevelLabel.setAlignment(Pos.CENTER_LEFT);
            final HBox currentLevelBox = BoxBuilder.builder()
                    .withNodes(currentLevelLabel, regionCurrent, currentLevel)
                    .buildHBox();
            //target level
            final Label targetLevelLabel = LabelBuilder.builder()
                    .withStyleClass("loadout-item-level-label")
                    .withText(LocaleService.getStringBinding("loadout.equipment.level.target"))
                    .build();
            final ComboBox<Integer> targetLevel = ComboBoxBuilder.builder(Integer.class)
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
            targetLevel.getSelectionModel().select(this.loadout.getTargetLevel());
            final Region regionTarget = new Region();
            HBox.setHgrow(regionTarget, Priority.ALWAYS);
            targetLevelLabel.setAlignment(Pos.CENTER_LEFT);
            final HBox targetLevelBox = BoxBuilder.builder()
                    .withNodes(targetLevelLabel, regionTarget, targetLevel)
                    .buildHBox();
            //auto set correct levels on change
            currentLevel.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null && newValue > this.loadout.getTargetLevel()) {
                    targetLevel.getSelectionModel().select(newValue);
                }
            });
            targetLevel.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null && newValue < this.loadout.getCurrentLevel()) {
                    currentLevel.getSelectionModel().select(newValue);
                }
            });

            //modifications
            final Label modificationsLabel = LabelBuilder
                    .builder().withStyleClass("loadout-item-subtitle")
                    .withText(LocaleService.getStringBinding("loadout.equipment.modifications"))
                    .build();
            this.loadoutModification1 = new LoadoutModification(this.loadout, 0, getModificationChangeCallback());
            this.loadoutModification2 = new LoadoutModification(this.loadout, 1, getModificationChangeCallback());
            this.loadoutModification3 = new LoadoutModification(this.loadout, 2, getModificationChangeCallback());
            this.loadoutModification4 = new LoadoutModification(this.loadout, 3, getModificationChangeCallback());

            final HBox modifications = BoxBuilder.builder()
                    .withNodes(this.loadoutModification1, createRegion(), this.loadoutModification2, createRegion(), this.loadoutModification3, createRegion(), this.loadoutModification4)
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
            this.addToWishlist = new MenuButton();
            this.addToWishlist.getStyleClass().add("loadout-wishlist-button");
            this.addToWishlist.textProperty().bind(LocaleService.getStringBinding("recipe.add.to.wishlist"));
            this.addToWishlist.getItems().addAll(new ArrayList<>());
            final Label warning = LabelBuilder.builder().withStyleClass("loadout-warning").withText(LocaleService.getStringBinding("loadout.equipment.warning")).build();
            warning.visibleProperty().bind(isValidProperty());
            APPLICATION_STATE.getPreferredCommander().ifPresent(this::loadCommanderWishlists);
            final Region region1 = new Region();
            VBox.setVgrow(region1, Priority.ALWAYS);
            this.getChildren().addAll(region1, this.addToWishlist, warning);
        }
    }

    private Consumer<ModificationChange> getModificationChangeCallback() {
        return modification -> {
            setValid(Arrays.stream(this.loadout.getModifications()).filter(Objects::nonNull).count() >= this.loadout.getTargetLevel());
            saveLoadoutSet();
            updateStatsList();
            this.loadoutModification1.resetPopOver();
            this.loadoutModification2.resetPopOver();
            this.loadoutModification3.resetPopOver();
            this.loadoutModification4.resetPopOver();
            if (Objects.equals(modification.getNewModification(), SuitModification.EXTRA_AMMO_CAPACITY) || Objects.equals(modification.getOldModification(), SuitModification.EXTRA_AMMO_CAPACITY)) {
                EventService.publish(new AmmoCapacityModEvent());
            }
        };
    }

    private void saveLoadoutSet() {
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
            APPLICATION_STATE.saveLoadoutSet(commander.getFid(), this.loadoutSet);
        });
    }

    private Wishlists loadCommanderWishlists(final Commander commander) {
        final Wishlists wishlists = APPLICATION_STATE.getWishlists(commander.getFid());
        this.addToWishlist.getItems().clear();
        final List<MenuItem> menuItems = wishlists.getAllWishlists().stream().sorted(Comparator.comparing(Wishlist::getName)).map(wishlist -> {
            final MenuItem menuItem = new MenuItem();
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
        this.staticStatsList.clear();

//        this.staticStatsList.addAll(this.loadout.getEquipment().getStats().entrySet().stream()
//                .filter(statObjectEntry -> statObjectEntry.getKey() instanceof StaticStat)
//                .map(statObjectEntry ->
//                {
//                    final Stat stat = statObjectEntry.getKey();
//                    final Object value = statObjectEntry.getValue();
//                    final String currentLevelValue = stat.formatValue(value, this.loadout.getEquipment(), this.loadout.getCurrentLevel());
//                    final Label nameColumn = LabelBuilder.builder().withStyleClasses("loadout-item-stats-name").withText(LocaleService.getStringBinding(stat.getLocalizationKey())).build();
//                    final Label build = LabelBuilder.builder().withStyleClasses("loadout-item-stats-value-wide").withNonLocalizedText(currentLevelValue).build();
//
//                    final Separator separator = new Separator(Orientation.HORIZONTAL);
//                    final Separator separator1 = new Separator(Orientation.HORIZONTAL);
//                    HBox.setHgrow(separator, Priority.ALWAYS);
//                    HBox.setHgrow(build, Priority.ALWAYS);
//                    HBox.setHgrow(separator1, Priority.ALWAYS);
//                    return BoxBuilder.builder().withNodes(
//                            nameColumn,
//                            BoxBuilder.builder().withStyleClass("loadout-item-stats-value-wide-box").withNodes(separator,
//                                    build,
//                                    separator1).buildHBox()
//                    ).buildHBox();
//                })
//                .filter(Objects::nonNull)
//                .toList());
        final AtomicReference<StatGroup> currentGroup = new AtomicReference<>();
        this.loadout.getEquipment().getStats().entrySet().stream()
//                .filter(statObjectEntry -> statObjectEntry.getKey() instanceof DynamicStat)
                .sorted(Comparator.comparing(statObjectEntry -> statObjectEntry.getKey().getStatGroup()))
                .forEach(statObjectEntry ->
                {
                    if (!Objects.equals(currentGroup.get(), statObjectEntry.getKey().getStatGroup())) {

                        final Separator separator = new Separator(Orientation.HORIZONTAL);
                        HBox.setHgrow(separator, Priority.ALWAYS);
                        this.statsList.add(new HBox(separator));

                        currentGroup.set(statObjectEntry.getKey().getStatGroup());
                        final StatGroup group = statObjectEntry.getKey().getStatGroup();
                        final Label nameColumn = LabelBuilder.builder().withStyleClasses("loadout-item-stats-name").withText(LocaleService.getStringBinding(group.getLocalizationKey())).build();
                        this.statsList.add(BoxBuilder.builder().withNodes(
                                nameColumn
                        ).buildHBox());
                    }
                    if (statObjectEntry.getKey() instanceof DynamicStat stat) {
//                        final DynamicStat stat = (DynamicStat) statObjectEntry.getKey();
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
                        final HBox valueRow = BoxBuilder.builder().withNodes(
                                nameColumn,
                                LabelBuilder.builder().withStyleClasses("loadout-item-stats-value").withNonLocalizedText(currentLevelValue).build()).buildHBox();
                        if (Suit.FLIGHTSUIT.equals(this.loadout.getEquipment())) {
                            nameColumn.getStyleClass().add("loadout-item-stats-wide");
                        } else {
                            final Label targetValue = LabelBuilder.builder().withStyleClasses("loadout-item-stats-value").withNonLocalizedText(targetLevelValue).build();
                            final Label moddedValue = LabelBuilder.builder().withStyleClasses("loadout-item-stats-value", "loadout-item-stats-value-modded").withNonLocalizedText(moddedLevelValue).build();
                            valueRow.getChildren().addAll(
                                    targetValue,
                                    moddedValue
                            );
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
                        final Label nameColumn = LabelBuilder.builder().withStyleClasses("loadout-item-stats-name").withText(LocaleService.getStringBinding(stat.getLocalizationKey())).build();
                        final Label build = LabelBuilder.builder().withStyleClasses("loadout-item-stats-value-wide").withNonLocalizedText(currentLevelValue).build();

                        final Separator separator = new Separator(Orientation.HORIZONTAL);
                        final Separator separator1 = new Separator(Orientation.HORIZONTAL);
                        HBox.setHgrow(separator, Priority.ALWAYS);
                        HBox.setHgrow(build, Priority.ALWAYS);
                        HBox.setHgrow(separator1, Priority.ALWAYS);
                        this.statsList.add(BoxBuilder.builder().withNodes(
                                nameColumn,
                                BoxBuilder.builder().withStyleClass("loadout-item-stats-value-wide-box").withNodes(separator,
                                        build,
                                        separator1).buildHBox()
                        ).buildHBox());
                    }
                });
        if (!Suit.FLIGHTSUIT.equals(this.loadout.getEquipment())) {//|| Suit.FLIGHTSUIT.equals(this.loadout.getEquipment()) && !this.statsToggle.isSelected()
            final Label valueColumn = LabelBuilder.builder().withStyleClasses("loadout-item-stats-header", "loadout-item-stats-name").withNonLocalizedText("").build();
            final HBox headerRow = BoxBuilder.builder().withNodes(
                    valueColumn,
                    LabelBuilder.builder().withStyleClasses("loadout-item-stats-header", "loadout-item-stats-value").withText(LocaleService.getStringBinding("loadout.equipment.stats.header.current")).build()).buildHBox();
            if (Suit.FLIGHTSUIT.equals(this.loadout.getEquipment())) {
                valueColumn.getStyleClass().add("loadout-item-stats-wide");
            } else {
                headerRow.getChildren().addAll(
                        LabelBuilder.builder().withStyleClasses("loadout-item-stats-header", "loadout-item-stats-value").withText(LocaleService.getStringBinding("loadout.equipment.stats.header.target")).build(),
                        LabelBuilder.builder().withStyleClasses("loadout-item-stats-header", "loadout-item-stats-value", "loadout-item-stats-value-modded").withText(LocaleService.getStringBinding("loadout.equipment.stats.header.modded")).build()
                );
            }
            this.statsList.add(0, headerRow);
        }
        this.stats.getChildren().clear();
//        this.stats.getChildren().addAll(this.staticStatsList);
//        this.stats.getChildren().add(new Separator(Orientation.HORIZONTAL));
        this.stats.getChildren().addAll(this.statsList);
    }

    private Region createRegion() {
        final Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        return region;
    }

    private HBox centerImage(final ResizableImageView resizableImageView) {
        final HBox hBox = BoxBuilder.builder().buildHBox();
        final VBox vBox = BoxBuilder.builder().buildVBox();
        hBox.setStyle("-fx-alignment: center;-fx-min-height: 22em");
        vBox.setStyle("-fx-alignment: center;-fx-min-height: 22em");
        vBox.getChildren().add(resizableImageView);
        hBox.getChildren().addAll(vBox);
        return hBox;
    }


    void onDestroy() {
        this.listeners.forEach(EventService::removeListener);
    }
}
