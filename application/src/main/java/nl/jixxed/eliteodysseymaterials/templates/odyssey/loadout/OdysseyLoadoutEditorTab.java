package nl.jixxed.eliteodysseymaterials.templates.odyssey.loadout;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.helper.ClipboardHelper;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.service.LoadoutService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.WishlistService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.OdysseyTab;
import org.controlsfx.control.PopOver;

import java.util.*;
import java.util.stream.Collectors;

public class OdysseyLoadoutEditorTab extends OdysseyTab implements DestroyableEventTemplate {


    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private ScrollPane scrollPane;

    private String activeLoadoutSetUUID;
    private DestroyableComboBox<LoadoutSet> loadoutSetSelect;
    private DestroyableFlowPane loadoutItemsFlow;
    private DestroyableMenuButton menuButton;
    private DestroyableMenuButton addToWishlist;


    public OdysseyLoadoutEditorTab() {
        initComponents();
        initEventHandling();
    }

    @Override
    public OdysseyTabs getTabType() {
        return OdysseyTabs.LOADOUT;
    }

    @Override
    public void initEventHandling() {

        register(EventService.addListener(true, this, WishlistSelectedEvent.class, wishlistSelectedEvent -> {
            APPLICATION_STATE.getPreferredCommander().ifPresent(this::loadCommanderWishlists);
        }));
        register(EventService.addListener(true, this, AfterFontSizeSetEvent.class, event -> refreshContent()));
        register(EventService.addListener(true, this, LanguageChangedEvent.class, event -> refreshContent()));
        register(EventService.addListener(true, this, LoadoutSetSelectedEvent.class, loadoutSetSelectedEvent -> refreshContent()));
        register(EventService.addListener(true, this, LoadoutRemovedEvent.class, loadoutRemovedEvent -> refreshContent()));
        register(EventService.addListener(true, this, LoadoutMovedEvent.class, loadoutMovedEvent -> refreshContent()));
        register(EventService.addListener(true, this, CommanderSelectedEvent.class, commanderSelectedEvent ->
        {
            refreshLoadoutSetSelect();
            refreshContent();
            loadCommanderWishlists(commanderSelectedEvent.getCommander());
        }));
        register(EventService.addListener(true, this, CommanderAllListedEvent.class, commanderAllListedEvent -> {
            refreshLoadoutSetSelect();
            APPLICATION_STATE.getPreferredCommander().ifPresent(this::loadCommanderWishlists);
        }));
        register(EventService.addListener(true, this, ImportResultEvent.class, importResultEvent -> {
            if (importResultEvent.getResult().getResultType().equals(ImportResult.ResultType.SUCCESS_LOADOUT)) {
                refreshLoadoutSetSelect();
            }
        }));
        register(EventService.addListener(true, this, 9, LoadoutEvent.class, event -> {
            refreshCurrentLoadout();
        }));

    }

    private void refreshContent() {
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
            final LoadoutSet selectedLoadoutSet = LoadoutService.getLoadoutSetList(commander).getSelectedLoadoutSet();
            this.activeLoadoutSetUUID = selectedLoadoutSet.getUuid();
            this.loadoutItemsFlow.getNodes().clear();
            this.loadoutItemsFlow.getNodes().addAll(selectedLoadoutSet.getLoadouts().stream()
                    .map(loadout -> new OdysseyLoadoutItem(selectedLoadoutSet, loadout))
                    .toList());
        });
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("loadout-tab");
        this.addBinding(this.textProperty(), LocaleService.getStringBinding("tabs.loadout"));
        final DestroyableHBox menu = initMenu();
        this.loadoutItemsFlow = FlowPaneBuilder.builder()
                .build();
        this.loadoutItemsFlow.addBinding(this.loadoutItemsFlow.hgapProperty(), ScalingHelper.getPixelDoubleBindingFromEm(0.25));
        this.loadoutItemsFlow.addBinding(this.loadoutItemsFlow.vgapProperty(), ScalingHelper.getPixelDoubleBindingFromEm(0.25));
        final DestroyableVBox vBox = BoxBuilder.builder()
                .withStyleClass("loadout-box")
                .withNodes(menu, this.loadoutItemsFlow).buildVBox();
        vBox.addBinding(vBox.spacingProperty(), ScalingHelper.getPixelDoubleBindingFromEm(0.25));
        this.scrollPane = ScrollPaneBuilder.builder()
                .withStyleClass("loadout-tab-content")
                .withContent(vBox)
                .build();
        this.setContent(this.scrollPane);
        refreshContent();

        APPLICATION_STATE.getPreferredCommander().ifPresent(this::loadCommanderWishlists);
    }

    private DestroyableHBox initMenu() {
        this.addToWishlist = MenuButtonBuilder.builder()
                .withText("blueprint.add.all.to.wishlist")
                .build();
        final Set<LoadoutSet> items = APPLICATION_STATE.getPreferredCommander()
                .map(commander -> LoadoutService.getLoadoutSetList(commander).getAllLoadoutSets())
                .orElse(Collections.emptySet());
        this.loadoutSetSelect = ComboBoxBuilder.builder(LoadoutSet.class)
                .withStyleClass("loadout-select")
                .withItemsProperty(FXCollections.observableArrayList(items.stream().sorted(Comparator.comparing(LoadoutSet::getName)).toList()))
                .withValueChangeListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                            this.activeLoadoutSetUUID = newValue.getUuid();
                            LoadoutService.selectLoadoutSet(this.activeLoadoutSetUUID, commander);
                            EventService.publish(new LoadoutSetSelectedEvent(this.activeLoadoutSetUUID));
                        });
                    }
                })
                .build();

        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> this.loadoutSetSelect.getSelectionModel().select(LoadoutService.getLoadoutSetList(commander).getSelectedLoadoutSet()));
        final Map<String, EventHandler<ActionEvent>> suitMenuItems = Arrays.stream(Suit.values()).collect(Collectors.toMap(Suit::getLocalizationKey, suit -> event ->
                APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                    final LoadoutSetList loadoutSetList = LoadoutService.getLoadoutSetList(commander);
                    final Loadout loadout = new Loadout(suit, 1, 5);
                    loadoutSetList.getSelectedLoadoutSet().addLoadout(loadout);
                    LoadoutService.saveLoadoutSetList(commander, loadoutSetList);
                    refreshContent();
                })));
        final DestroyableMenuButton addSuitButton = MenuButtonBuilder.builder()
                .withText("tab.loadout.add.suit")
                .withMenuItems(suitMenuItems)
                .build();
        final Map<String, EventHandler<ActionEvent>> weaponMenuItems = Arrays.stream(Weapon.values()).collect(Collectors.toMap(Weapon::getLocalizationKey, weapon -> event ->
                APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                    final LoadoutSetList loadoutSetList = LoadoutService.getLoadoutSetList(commander);
                    final Loadout loadout = new Loadout(weapon, 1, 5);
                    loadoutSetList.getSelectedLoadoutSet().addLoadout(loadout);
                    LoadoutService.saveLoadoutSetList(commander, loadoutSetList);
                    refreshContent();
                })));
        final DestroyableMenuButton addWeaponButton = MenuButtonBuilder.builder()
                .withText("tab.loadout.add.weapon")
                .withMenuItems(weaponMenuItems)
                .build();
        addSuitButton.addBinding(addSuitButton.disableProperty(), this.loadoutSetSelect.getSelectionModel().selectedItemProperty().isEqualTo(LoadoutSet.CURRENT));
        addWeaponButton.addBinding(addWeaponButton.disableProperty(), this.loadoutSetSelect.getSelectionModel().selectedItemProperty().isEqualTo(LoadoutSet.CURRENT));
        final EventHandler<ActionEvent> createHandler = event -> {
            final DestroyableTextField textField = TextFieldBuilder.builder()
                    .withStyleClasses("root", "loadout-newname")
                    .withPromptTextProperty(LocaleService.getStringBinding("tab.loadout.rename.prompt"))
                    .build();
            final DestroyableButton button = ButtonBuilder.builder()
                    .withText("tab.loadout.create")
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
                    .withArrowLocation(PopOver.ArrowLocation.RIGHT_CENTER)
                    .build();
            popOver.show(this.menuButton);
            button.setOnAction(eventB -> APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                final LoadoutSetList loadoutSetList = LoadoutService.getLoadoutSetList(commander);
                loadoutSetList.createLoadoutSet(textField.getText());
                LoadoutService.saveLoadoutSetList(commander, loadoutSetList);
                textField.clear();
                refreshLoadoutSetSelect();
                popOver.hide();
            }));
            textField.setOnKeyPressed(ke -> {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    button.fire();
                }
            });
        };
        final EventHandler<ActionEvent> renameHandler = event -> {
            final DestroyableTextField textField = TextFieldBuilder.builder()
                    .withStyleClasses("root", "loadout-newname")
                    .withPromptTextProperty(LocaleService.getStringBinding("tab.loadout.rename.prompt"))
                    .build();

            final DestroyableButton button = ButtonBuilder.builder()
                    .withText("tab.loadout.rename")
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
                    .withArrowLocation(PopOver.ArrowLocation.RIGHT_CENTER)
                    .build();
            popOver.show(this.menuButton);
            button.setOnAction(eventB -> APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                final LoadoutSetList loadoutSetList = LoadoutService.getLoadoutSetList(commander);
                loadoutSetList.renameLoadoutSet(this.activeLoadoutSetUUID, textField.getText());
                LoadoutService.saveLoadoutSetList(commander, loadoutSetList);
                textField.clear();
                refreshLoadoutSetSelect();
                popOver.hide();
            }));
            textField.setOnKeyPressed(ke -> {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    button.fire();
                }
            });
        };
        final EventHandler<ActionEvent> deleteHandler = event -> {
            final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(LocaleService.getLocalizedStringForCurrentLocale("tab.loadout.delete.confirm.title"));
            alert.setHeaderText(LocaleService.getLocalizedStringForCurrentLocale("tab.loadout.delete.confirm.header"));
            alert.setContentText(LocaleService.getLocalizedStringForCurrentLocale("tab.loadout.delete.confirm.content"));

            final Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                    LoadoutService.deleteLoadoutSet(this.activeLoadoutSetUUID, commander);
                    Platform.runLater(this::refreshLoadoutSetSelect);

                });
            }
        };
        final EventHandler<ActionEvent> copyHandler = event -> {
            copyLoadoutSetToClipboard();
            NotificationService.showInformation(NotificationType.COPY, LocaleService.LocaleString.of("notification.clipboard.title"), LocaleService.LocaleString.of("notification.clipboard.loadout.copied.text"));
        };
        final EventHandler<ActionEvent> cloneHandler = event -> {
            APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                final LoadoutSet loadoutSet = this.loadoutSetSelect.getSelectionModel().getSelectedItem().cloneLoadoutSet();
                final LoadoutSetList loadoutSetList = LoadoutService.getLoadoutSetList(commander);
                loadoutSetList.addLoadoutSet(loadoutSet);
                loadoutSetList.setSelectedLoadoutSetUUID(loadoutSet.getUuid());
                LoadoutService.saveLoadoutSetList(commander, loadoutSetList);
                refreshLoadoutSetSelect();
            });
        };
        this.menuButton = MenuButtonBuilder.builder()
                .withText("tab.wishlist.options")
                .withMenuItems(
                        Map.of("tab.loadout.create", createHandler,
                                "tab.loadout.rename", renameHandler,
                                "tab.loadout.delete", deleteHandler,
                                "tab.loadout.clone", cloneHandler,
                                "tab.loadout.copy", copyHandler),
                        Map.of(
                                "tab.loadout.delete", this.loadoutSetSelect.getSelectionModel().selectedItemProperty().isEqualTo(LoadoutSet.CURRENT),
                                "tab.loadout.copy", this.loadoutSetSelect.getSelectionModel().selectedItemProperty().isEqualTo(LoadoutSet.CURRENT),
                                "tab.loadout.rename", this.loadoutSetSelect.getSelectionModel().selectedItemProperty().isEqualTo(LoadoutSet.CURRENT)
                        ))
                .withFocusTraversable(false)
                .build();

        final DestroyableHBox hBoxBlueprints = BoxBuilder.builder()
                .withNodes(this.loadoutSetSelect, this.menuButton, addSuitButton, addWeaponButton, this.addToWishlist).buildHBox();
        hBoxBlueprints.addBinding(hBoxBlueprints.spacingProperty(), ScalingHelper.getPixelDoubleBindingFromEm(0.25));
        return hBoxBlueprints;
    }

    private void copyLoadoutSetToClipboard() {
        ClipboardHelper.copyToClipboard(ClipboardHelper.createClipboardLoadout());
    }

    private void refreshLoadoutSetSelect() {
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
            final LoadoutSetList loadoutSetList = LoadoutService.getLoadoutSetList(commander);
            final Set<LoadoutSet> items = loadoutSetList.getAllLoadoutSets();
            this.loadoutSetSelect.getItems().clear();
            this.loadoutSetSelect.getItems().addAll(items.stream().sorted(Comparator.comparing(LoadoutSet::getName)).toList());
            this.loadoutSetSelect.getSelectionModel().select(loadoutSetList.getSelectedLoadoutSet());
        });
    }

    private void refreshCurrentLoadout() {
        if (this.loadoutSetSelect.getSelectionModel().getSelectedItem().equals(LoadoutSet.CURRENT)) {
            refreshContent();
        }
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
                        NotificationService.showWarning(NotificationType.ERROR, "Can't add to wishlist", "No items to add");
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
                    final Wishlist newWishlist = odysseyWishlists.createWishlist(this.loadoutSetSelect.getSelectionModel().getSelectedItem().getName());
                    WishlistService.saveWishlists(commander, odysseyWishlists);
                    EventService.publish(new WishlistCreatedEvent());//refreshes wishlist dropdown
                    EventService.publish(new WishlistBlueprintEvent(commander, newWishlist.getUuid(), wishlistBlueprints, Action.ADDED));
                }
            });
            createNew.addBinding(createNew.textProperty(), LocaleService.getStringBinding("loadout.create.new.wishlist"));
            createNew.getStyleClass().add("loadout-wishlist-create-new");
            this.addToWishlist.getItems().add(createNew);
        }
        return wishlists;
    }

    private List<OdysseyWishlistBlueprint> getRequiredWishlistRecipes() {
        final List<OdysseyWishlistBlueprint> wishlistBlueprints = new ArrayList<>();
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
            LoadoutService.getLoadoutSetList(commander).getSelectedLoadoutSet().getLoadouts().forEach(loadout -> {
                final LevelValue recipes = loadout.getEquipment().getRecipes();
                for (int level = loadout.getCurrentLevel() + 1; level <= loadout.getTargetLevel(); level++) {
                    final Object recipe = recipes.getValueForLevel(level);
                    if (!OdysseyBlueprintName.NONE.equals(recipe)) {
                        wishlistBlueprints.add(new OdysseyWishlistBlueprint((OdysseyBlueprintName) recipe, true));
                    }
                }
                wishlistBlueprints.addAll(Arrays.stream(loadout.getModifications()).filter(modification -> modification.getModification() != null && !WeaponModification.NONE.equals(modification.getModification())).filter(SelectedModification::isNotPresent).map(modification -> new OdysseyWishlistBlueprint(modification.getModification().getRecipe(), true)).toList());

            });
        });
        return wishlistBlueprints;
    }
}
