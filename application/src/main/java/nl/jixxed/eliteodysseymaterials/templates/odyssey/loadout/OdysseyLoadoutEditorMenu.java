package nl.jixxed.eliteodysseymaterials.templates.odyssey.loadout;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.helper.ClipboardHelper;
import nl.jixxed.eliteodysseymaterials.service.LoadoutService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.WishlistService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;
import org.controlsfx.control.PopOver;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class OdysseyLoadoutEditorMenu extends DestroyableHBox implements DestroyableEventTemplate {


    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    private DestroyableComboBox<LoadoutSet> loadoutSetSelect;
    private DestroyableMenuButton menuButton;
    private DestroyableMenuButton addToWishlist;


    public OdysseyLoadoutEditorMenu() {
        initComponents();
        initEventHandling();
    }


    @Override
    public void initComponents() {
        this.getStyleClass().add("loadout-menu");
        initMenu();
        APPLICATION_STATE.getPreferredCommander().ifPresent(this::loadCommanderWishlists);
    }

    @Override
    public void initEventHandling() {

        register(EventService.addListener(true, this, OdysseyWishlistSelectedEvent.class, _ ->
                APPLICATION_STATE.getPreferredCommander().ifPresent(this::loadCommanderWishlists)));
        register(EventService.addListener(true, this, ImportResultEvent.class, importResultEvent -> {
            if (importResultEvent.getResult().getResultType().equals(ImportResult.ResultType.SUCCESS_LOADOUT)) {
                refreshLoadoutSetSelect();
            }
        }));
        register(EventService.addListener(true, this, DeleteSelectedItemEvent.class, event -> {
            if (event.getSelectedTab() == MainTabType.ODYSSEY && event.getSelectedChildTab() == OdysseyTabType.LOADOUT && this.loadoutSetSelect.getSelectionModel().selectedItemProperty().isNotEqualTo(LoadoutSet.CURRENT).get()) {
                getDeleteHandler().handle(null);
            }
        }));
        register(EventService.addListener(true, this, RenameSelectedItemEvent.class, event -> {
            if (event.getSelectedTab() == MainTabType.ODYSSEY && event.getSelectedChildTab() == OdysseyTabType.LOADOUT && this.loadoutSetSelect.getSelectionModel().selectedItemProperty().isNotEqualTo(LoadoutSet.CURRENT).get()) {
                getRenameHandler().handle(null);
            }
        }));
        register(EventService.addListener(true, this, CreateItemEvent.class, event -> {
            if (event.getSelectedTab() == MainTabType.ODYSSEY && event.getSelectedChildTab() == OdysseyTabType.LOADOUT) {
                getCreateHandler().handle(null);
            }
        }));
        register(EventService.addListener(true, this, CopySelectedItemEvent.class, event -> {
            if (event.getSelectedTab() == MainTabType.ODYSSEY && event.getSelectedChildTab() == OdysseyTabType.LOADOUT && this.loadoutSetSelect.getSelectionModel().selectedItemProperty().isNotEqualTo(LoadoutSet.CURRENT).get()) {
                getCopyHandler().handle(null);
            }
        }));
        register(EventService.addListener(true, this, CloneSelectedItemEvent.class, event -> {
            if (event.getSelectedTab() == MainTabType.ODYSSEY && event.getSelectedChildTab() == OdysseyTabType.LOADOUT && this.loadoutSetSelect.getSelectionModel().selectedItemProperty().isNotEqualTo(LoadoutSet.CURRENT).get()) {
                getCloneHandler().handle(null);
            }
        }));

    }

    @SuppressWarnings("unchecked")
    private void initMenu() {
        this.addToWishlist = MenuButtonBuilder.builder()
                .withText("blueprint.add.all.to.wishlist")
                .build();

        final Set<LoadoutSet> loadoutSets = APPLICATION_STATE.getPreferredCommander()
                .map(commander -> LoadoutService.getLoadoutSetList(commander).getAllLoadoutSets())
                .orElse(Collections.emptySet());

        initLoadoutSelect(loadoutSets);
        final DestroyableMenuButton addSuitButton = createAddSuitButton();
        final DestroyableMenuButton addWeaponButton = createAddWeaponsButton();

        this.menuButton = MenuButtonBuilder.builder()
                .withText("tab.wishlist.options")
                .withMenuItems(
                        Map.of("tab.loadout.create", getCreateHandler(),
                                "tab.loadout.rename", getRenameHandler(),
                                "tab.loadout.delete", getDeleteHandler(),
                                "tab.loadout.clone", getCloneHandler(),
                                "tab.loadout.copy", getCopyHandler()),
                        Map.of(
                                "tab.loadout.delete", this.loadoutSetSelect.getSelectionModel().selectedItemProperty().isEqualTo(LoadoutSet.CURRENT),
                                "tab.loadout.copy", this.loadoutSetSelect.getSelectionModel().selectedItemProperty().isEqualTo(LoadoutSet.CURRENT),
                                "tab.loadout.rename", this.loadoutSetSelect.getSelectionModel().selectedItemProperty().isEqualTo(LoadoutSet.CURRENT)
                        ),
                        Map.of(
                                "tab.loadout.create", new KeyCodeCombination(KeyCode.N, KeyCodeCombination.CONTROL_DOWN),
                                "tab.loadout.rename", new KeyCodeCombination(KeyCode.F2),
                                "tab.loadout.delete", new KeyCodeCombination(KeyCode.DELETE),
                                "tab.loadout.copy", new KeyCodeCombination(KeyCode.C, KeyCodeCombination.CONTROL_DOWN),
                                "tab.loadout.clone", new KeyCodeCombination(KeyCode.D, KeyCodeCombination.CONTROL_DOWN)
                        ))
                .withFocusTraversable(false)
                .build();

        this.getNodes().addAll(this.loadoutSetSelect, this.menuButton, addSuitButton, addWeaponButton, this.addToWishlist);
    }

    private EventHandler<ActionEvent> getCloneHandler() {
        return _ -> APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
            final LoadoutSet loadoutSet = this.loadoutSetSelect.getSelectionModel().getSelectedItem().cloneLoadoutSet();
            final LoadoutSetList loadoutSetList = LoadoutService.getLoadoutSetList(commander);
            loadoutSetList.addLoadoutSet(loadoutSet);
            loadoutSetList.setSelectedLoadoutSetUUID(loadoutSet.getUuid());
            LoadoutService.saveLoadoutSetList(commander, loadoutSetList);
            refreshLoadoutSetSelect();
        });
    }

    private EventHandler<ActionEvent> getCopyHandler() {
        return _ -> {
            copyLoadoutSetToClipboard();
            NotificationService.showInformation(NotificationType.COPY, LocaleService.LocaleString.of("notification.clipboard.title"), LocaleService.LocaleString.of("notification.clipboard.loadout.copied.text"));
        };
    }

    private EventHandler<ActionEvent> getDeleteHandler() {
        return _ -> {
            final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(LocaleService.getLocalizedStringForCurrentLocale("tab.loadout.delete.confirm.title"));
            alert.setHeaderText(LocaleService.getLocalizedStringForCurrentLocale("tab.loadout.delete.confirm.header"));
            alert.setContentText(LocaleService.getLocalizedStringForCurrentLocale("tab.loadout.delete.confirm.content"));

            final Optional<ButtonType> result = alert.showAndWait();
            if (result.map(type -> type == ButtonType.OK).orElse(false)) {
                APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                    LoadoutService.deleteLoadoutSet(this.loadoutSetSelect.getSelectionModel().getSelectedItem().getUuid(), commander);
                    Platform.runLater(this::refreshLoadoutSetSelect);
                });
            }
        };
    }

    private EventHandler<ActionEvent> getRenameHandler() {
        return _ -> showInputPopOver("tab.loadout.rename", "tab.loadout.rename.prompt",
                (commander, input) -> {
                    final LoadoutSetList loadoutSetList = LoadoutService.getLoadoutSetList(commander);
                    loadoutSetList.renameLoadoutSet(this.loadoutSetSelect.getSelectionModel().getSelectedItem().getUuid(), input);
                    LoadoutService.saveLoadoutSetList(commander, loadoutSetList);
                });
    }

    private EventHandler<ActionEvent> getCreateHandler() {
        return _ -> showInputPopOver("tab.loadout.create", "tab.loadout.create.prompt",
                (commander, input) -> {
                    final LoadoutSetList loadoutSetList = LoadoutService.getLoadoutSetList(commander);
                    loadoutSetList.createLoadoutSet(input);
                    LoadoutService.saveLoadoutSetList(commander, loadoutSetList);
                });
    }

    private void showInputPopOver(String buttonLocaleKey, String textfieldPromptLocaleKey, BiConsumer<Commander, String> inputHandler) {
        final DestroyableTextField textField = TextFieldBuilder.builder()
                .withStyleClasses("root", "loadout-newname")
                .withNonLocalizedText(this.loadoutSetSelect.getSelectionModel().getSelectedItem().getName())
                .withPromptTextProperty(LocaleService.getStringBinding(textfieldPromptLocaleKey))
                .build();

        final DestroyableButton button = ButtonBuilder.builder()
                .withText(buttonLocaleKey)
                .build();
        final DestroyableHBox popOverContent = BoxBuilder.builder()
                .withNodes(textField, button).buildHBox();
        final DestroyablePopOver popOver = PopOverBuilder.builder()
                .withStyleClass("popover-menubutton-layout")
                .withContent(BoxBuilder.builder()
                        .withStyleClass("popover-menubutton-box")
                        .withNodes(new GrowingRegion(), popOverContent, new GrowingRegion()).buildVBox())
                .withDetachable(false)
                .withHeaderAlwaysVisible(false)
                .withCornerRadius(0)
                .withArrowLocation(PopOver.ArrowLocation.RIGHT_CENTER)
                .build();
        popOver.show(this.menuButton);
        textField.selectAll();
        button.setOnAction(_ -> APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
            inputHandler.accept(commander, textField.getText());
            textField.clear();
            refreshLoadoutSetSelect();
            popOver.hide();
        }));
        textField.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                button.fire();
            }
        });
    }

    private DestroyableMenuButton createAddWeaponsButton() {
        final Map<String, EventHandler<ActionEvent>> weaponMenuItems = Arrays.stream(Weapon.values()).collect(Collectors.toMap(Weapon::getLocalizationKey, weapon -> event ->
                APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                    final LoadoutSetList loadoutSetList = LoadoutService.getLoadoutSetList(commander);
                    final Loadout loadout = new Loadout(weapon, 1, 5);
                    loadoutSetList.getSelectedLoadoutSet().addLoadout(loadout);
                    LoadoutService.saveLoadoutSetList(commander, loadoutSetList);
                    EventService.publish(new LoadoutAddedEvent(loadout));
                })));
        return MenuButtonBuilder.builder()
                .withText("tab.loadout.add.weapon")
                .withMenuItems(weaponMenuItems)
                .withDisableProperty(this.loadoutSetSelect.getSelectionModel().selectedItemProperty().isEqualTo(LoadoutSet.CURRENT))
                .build();

    }

    private DestroyableMenuButton createAddSuitButton() {
        final Map<String, EventHandler<ActionEvent>> suitMenuItems = Arrays.stream(Suit.values()).collect(Collectors.toMap(Suit::getLocalizationKey, suit -> event ->
                APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                    final LoadoutSetList loadoutSetList = LoadoutService.getLoadoutSetList(commander);
                    final Loadout loadout = new Loadout(suit, 1, 5);
                    loadoutSetList.getSelectedLoadoutSet().addLoadout(loadout);
                    LoadoutService.saveLoadoutSetList(commander, loadoutSetList);
                    EventService.publish(new LoadoutAddedEvent(loadout));
                })));
        return MenuButtonBuilder.builder()
                .withText("tab.loadout.add.suit")
                .withMenuItems(suitMenuItems)
                .withDisableProperty(this.loadoutSetSelect.getSelectionModel().selectedItemProperty().isEqualTo(LoadoutSet.CURRENT))
                .build();
    }

    private void initLoadoutSelect(Set<LoadoutSet> loadoutSets) {
        final Optional<LoadoutSet> loadoutSet = APPLICATION_STATE.getPreferredCommander()
                .map(commander -> LoadoutService.getLoadoutSetList(commander).getSelectedLoadoutSet());
        this.loadoutSetSelect = ComboBoxBuilder.builder(LoadoutSet.class)
                .withStyleClass("loadout-select")
                .withSelected(loadoutSet.orElse(null))
                .withItemsProperty(FXCollections.observableArrayList(loadoutSets.stream().sorted(Comparator.comparing(LoadoutSet::getName)).toList()))
                .withValueChangeListener((_, _, newValue) -> {
                    if (newValue != null) {
                        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                            LoadoutService.selectLoadoutSet(this.loadoutSetSelect.getSelectionModel().getSelectedItem().getUuid(), commander);
                            EventService.publish(new LoadoutSetSelectedEvent(this.loadoutSetSelect.getSelectionModel().getSelectedItem().getUuid()));
                        });
                    }
                })
                .build();

    }

    private void copyLoadoutSetToClipboard() {
        ClipboardHelper.copyToClipboard(ClipboardHelper.createClipboardLoadout());
    }

    private void refreshLoadoutSetSelect() {
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
            final LoadoutSetList loadoutSetList = LoadoutService.getLoadoutSetList(commander);
            final Set<LoadoutSet> items = loadoutSetList.getAllLoadoutSets();
            this.loadoutSetSelect.clear();
            this.loadoutSetSelect.addAll(items.stream().sorted(Comparator.comparing(LoadoutSet::getName)).toList());
            this.loadoutSetSelect.getSelectionModel().select(loadoutSetList.getSelectedLoadoutSet());
        });
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
                                    NotificationService.showWarning(NotificationType.ERROR, LocaleService.LocaleString.of("notification.wishlist.cannot.add.title"), LocaleService.LocaleString.of("notification.wishlist.cannot.add.text"));
                                } else {
                                    EventService.publish(new OdysseyWishlistBlueprintEvent(commander, wishlist.getUuid(), wishlistBlueprints, Action.ADDED));
                                }
                            })
                            .build())
                    .toList();
            this.addToWishlist.addAll(menuItems);
            this.addToWishlist.add(newWishlistButton(commander));
        }
    }

    private DestroyableMenuItem newWishlistButton(Commander commander) {
        return MenuItemBuilder.builder()
                .withStyleClass("loadout-wishlist-create-new")
                .withText("loadout.create.new.wishlist")
                .withOnAction(_ -> {
                    final List<OdysseyWishlistBlueprint> wishlistBlueprints = getRequiredWishlistRecipes();
                    if (wishlistBlueprints.isEmpty()) {
                        NotificationService.showWarning(NotificationType.ERROR, LocaleService.LocaleString.of("notification.wishlist.cannot.add.title"), LocaleService.LocaleString.of("notification.wishlist.cannot.add.text"));
                    } else {
                        final Wishlists odysseyWishlists = WishlistService.getOdysseyWishlists(commander);
                        final Wishlist newWishlist = odysseyWishlists.createWishlist(this.loadoutSetSelect.getSelectionModel().getSelectedItem().getName());
                        WishlistService.saveOdysseyWishlists(commander, odysseyWishlists);
                        EventService.publish(new OdysseyWishlistCreatedEvent());//refreshes wishlist dropdown
                        EventService.publish(new OdysseyWishlistBlueprintEvent(commander, newWishlist.getUuid(), wishlistBlueprints, Action.ADDED));
                    }
                })
                .build();
    }

    private List<OdysseyWishlistBlueprint> getRequiredWishlistRecipes() {
        final List<OdysseyWishlistBlueprint> wishlistBlueprints = new ArrayList<>();
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander ->
                LoadoutService.getLoadoutSetList(commander).getSelectedLoadoutSet().getLoadouts().forEach(loadout -> {
                    final LevelValue recipes = loadout.getEquipment().getRecipes();
                    for (int level = loadout.getCurrentLevel() + 1; level <= loadout.getTargetLevel(); level++) {
                        final Object recipe = recipes.getValueForLevel(level);
                        if (!OdysseyBlueprintName.NONE.equals(recipe)) {
                            wishlistBlueprints.add(new OdysseyWishlistBlueprint((OdysseyBlueprintName) recipe));
                        }
                    }
                    wishlistBlueprints.addAll(Arrays.stream(loadout.getModifications()).filter(modification -> modification.getModification() != null && !WeaponModification.NONE.equals(modification.getModification())).filter(SelectedModification::isNotPresent).map(modification -> new OdysseyWishlistBlueprint(modification.getModification().getRecipe())).toList());
                }));
        return wishlistBlueprints;
    }

}
