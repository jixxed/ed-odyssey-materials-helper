package nl.jixxed.eliteodysseymaterials.templates.odyssey.loadout;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.helper.ClipboardHelper;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.service.LoadoutService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.WishlistService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableMenuButton;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableMenuItem;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.OdysseyTab;
import org.controlsfx.control.PopOver;

import java.util.*;
import java.util.stream.Collectors;

public class OdysseyLoadoutEditorTab extends OdysseyTab implements Template {


    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private ScrollPane scrollPane;

    private String activeLoadoutSetUUID;
    private ComboBox<LoadoutSet> loadoutSetSelect;
    private FlowPane loadoutItemsFlow;
    private MenuButton menuButton;
    private DestroyableMenuButton addToWishlist;
    private final List<EventListener<?>> eventListeners = new ArrayList<>();

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

        this.eventListeners.add(EventService.addListener(this, WishlistSelectedEvent.class, wishlistSelectedEvent -> {
            APPLICATION_STATE.getPreferredCommander().ifPresent(this::loadCommanderWishlists);
        }));
        this.eventListeners.add(EventService.addListener(this, AfterFontSizeSetEvent.class, event -> refreshContent()));
        this.eventListeners.add(EventService.addListener(this, LanguageChangedEvent.class, event -> refreshContent()));
        this.eventListeners.add(EventService.addListener(this, LoadoutSetSelectedEvent.class, loadoutSetSelectedEvent -> refreshContent()));
        this.eventListeners.add(EventService.addListener(this, LoadoutRemovedEvent.class, loadoutRemovedEvent -> refreshContent()));
        this.eventListeners.add(EventService.addListener(this, LoadoutMovedEvent.class, loadoutMovedEvent -> refreshContent()));
        this.eventListeners.add(EventService.addListener(this, CommanderSelectedEvent.class, commanderSelectedEvent ->
        {
            refreshLoadoutSetSelect();
            refreshContent();
            loadCommanderWishlists(commanderSelectedEvent.getCommander());
        }));
        this.eventListeners.add(EventService.addListener(this, CommanderAllListedEvent.class, commanderAllListedEvent -> {
            refreshLoadoutSetSelect();
            APPLICATION_STATE.getPreferredCommander().ifPresent(this::loadCommanderWishlists);
        }));
        this.eventListeners.add(EventService.addListener(this, ImportResultEvent.class, importResultEvent -> {
            if (importResultEvent.getResult().getResultType().equals(ImportResult.ResultType.SUCCESS_LOADOUT)) {
                refreshLoadoutSetSelect();
            }
        }));
        this.eventListeners.add(EventService.addListener(this, 9, LoadoutEvent.class, event -> {
            refreshCurrentLoadout();
        }));

    }

    private void refreshContent() {
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
            final LoadoutSet selectedLoadoutSet = LoadoutService.getLoadoutSetList(commander).getSelectedLoadoutSet();
            this.activeLoadoutSetUUID = selectedLoadoutSet.getUuid();
            this.loadoutItemsFlow.getChildren().stream().map(OdysseyLoadoutItem.class::cast).forEach(OdysseyLoadoutItem::destroy);
            this.loadoutItemsFlow.getChildren().clear();
            this.loadoutItemsFlow.getChildren().addAll(selectedLoadoutSet.getLoadouts().stream()
                    .map(loadout -> new OdysseyLoadoutItem(selectedLoadoutSet, loadout))
                    .toList());
        });
    }

    @Override
    public void initComponents() {
        this.textProperty().bind(LocaleService.getStringBinding("tabs.loadout"));
        final Node menu = initMenu();
        this.loadoutItemsFlow = FlowPaneBuilder.builder().build();
        this.loadoutItemsFlow.hgapProperty().bind(ScalingHelper.getPixelDoubleBindingFromEm(0.25));
        this.loadoutItemsFlow.vgapProperty().bind(ScalingHelper.getPixelDoubleBindingFromEm(0.25));
        final VBox vBox = BoxBuilder.builder().withStyleClass("loadout-box").withNodes(menu, this.loadoutItemsFlow).buildVBox();
        vBox.spacingProperty().bind(ScalingHelper.getPixelDoubleBindingFromEm(0.25));
        this.scrollPane = ScrollPaneBuilder.builder()
                .withContent(vBox)
                .build();
        this.setContent(this.scrollPane);
    }

    private Node initMenu() {
        this.addToWishlist = MenuButtonBuilder.builder().withText(LocaleService.getStringBinding("blueprint.add.all.to.wishlist")).build();
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
        final Map<String, EventHandler<ActionEvent>> suitMenuItems = Arrays.stream(Suit.values()).collect(Collectors.toMap(Suit::getLocalizationKey, suit -> event ->
                APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                    final LoadoutSetList loadoutSetList = LoadoutService.getLoadoutSetList(commander);
                    final Loadout loadout = new Loadout(suit, 1, 5);
                    loadoutSetList.getSelectedLoadoutSet().addLoadout(loadout);
                    LoadoutService.saveLoadoutSetList(commander, loadoutSetList);
                    refreshContent();
                })));
        final MenuButton addSuitButton = MenuButtonBuilder.builder().withText(LocaleService.getStringBinding("tab.loadout.add.suit")).withMenuItems(suitMenuItems).build();
        final Map<String, EventHandler<ActionEvent>> weaponMenuItems = Arrays.stream(Weapon.values()).collect(Collectors.toMap(Weapon::getLocalizationKey, weapon -> event ->
                APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                    final LoadoutSetList loadoutSetList = LoadoutService.getLoadoutSetList(commander);
                    final Loadout loadout = new Loadout(weapon, 1, 5);
                    loadoutSetList.getSelectedLoadoutSet().addLoadout(loadout);
                    LoadoutService.saveLoadoutSetList(commander, loadoutSetList);
                    refreshContent();
                })));
        final MenuButton addWeaponButton = MenuButtonBuilder.builder().withText(LocaleService.getStringBinding("tab.loadout.add.weapon")).withMenuItems(weaponMenuItems).build();
        addSuitButton.disableProperty().bind(this.loadoutSetSelect.getSelectionModel().selectedItemProperty().isEqualTo(LoadoutSet.CURRENT));
        addWeaponButton.disableProperty().bind(this.loadoutSetSelect.getSelectionModel().selectedItemProperty().isEqualTo(LoadoutSet.CURRENT));
        final EventHandler<ActionEvent> createHandler = event -> {
            final TextField textField = TextFieldBuilder.builder().withStyleClasses("root", "loadout-newname").withPromptTextProperty(LocaleService.getStringBinding("tab.loadout.rename.prompt")).build();
            final Button button = ButtonBuilder.builder().withText(LocaleService.getStringBinding("tab.loadout.create")).build();
            final HBox popOverContent = BoxBuilder.builder().withNodes(textField, button).buildHBox();
            final PopOver popOver = new PopOver(BoxBuilder.builder().withStyleClass("popover-menubutton-box").withNodes(new GrowingRegion(), popOverContent, new GrowingRegion()).buildVBox());
            popOver.setDetachable(false);
            popOver.setHeaderAlwaysVisible(false);
            popOver.getStyleClass().add("popover-menubutton-layout");
            popOver.setArrowLocation(PopOver.ArrowLocation.RIGHT_CENTER);
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
            final TextField textField = TextFieldBuilder.builder().withStyleClasses("root", "loadout-newname").withPromptTextProperty(LocaleService.getStringBinding("tab.loadout.rename.prompt")).build();

            final Button button = ButtonBuilder.builder().withText(LocaleService.getStringBinding("tab.loadout.rename")).build();
            final HBox popOverContent = BoxBuilder.builder().withNodes(textField, button).buildHBox();
            final PopOver popOver = new PopOver(BoxBuilder.builder().withStyleClass("popover-menubutton-box").withNodes(new GrowingRegion(), popOverContent, new GrowingRegion()).buildVBox());
            popOver.setDetachable(false);
            popOver.setHeaderAlwaysVisible(false);
            popOver.getStyleClass().add("popover-menubutton-layout");
            popOver.setArrowLocation(PopOver.ArrowLocation.RIGHT_CENTER);
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
            NotificationService.showInformation(NotificationType.COPY, "Loadout Editor", "The loadout has been copied to your clipboard");
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
        this.menuButton = MenuButtonBuilder.builder().withText(LocaleService.getStringBinding("tab.wishlist.options")).withMenuItems(
                Map.of("tab.loadout.create", createHandler,
                        "tab.loadout.rename", renameHandler,
                        "tab.loadout.delete", deleteHandler,
                        "tab.loadout.clone", cloneHandler,
                        "tab.loadout.copy", copyHandler)).build();
        this.menuButton.setFocusTraversable(false);
        this.menuButton.getItems().forEach(menuItem -> {
            if (!menuItem.getOnAction().equals(createHandler) && !menuItem.getOnAction().equals(cloneHandler)) {
                menuItem.disableProperty().bind(this.loadoutSetSelect.getSelectionModel().selectedItemProperty().isEqualTo(LoadoutSet.CURRENT));
            }
        });
        final HBox hBoxBlueprints = BoxBuilder.builder().withNodes(this.loadoutSetSelect, this.menuButton, addSuitButton, addWeaponButton, this.addToWishlist).buildHBox();
        hBoxBlueprints.spacingProperty().bind(ScalingHelper.getPixelDoubleBindingFromEm(0.25));
        return hBoxBlueprints;
    }

    private void copyLoadoutSetToClipboard() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent clipboardContent = new ClipboardContent();

        clipboardContent.putString(ClipboardHelper.createClipboardLoadout());
        clipboard.setContent(clipboardContent);
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
            createNew.textProperty().bind(LocaleService.getStringBinding("loadout.create.new.wishlist"));
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
