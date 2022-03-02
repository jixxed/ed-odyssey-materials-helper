package nl.jixxed.eliteodysseymaterials.templates;

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
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.Loadout;
import nl.jixxed.eliteodysseymaterials.domain.LoadoutSet;
import nl.jixxed.eliteodysseymaterials.domain.LoadoutSetList;
import nl.jixxed.eliteodysseymaterials.enums.ImportResult;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyTabs;
import nl.jixxed.eliteodysseymaterials.enums.Suit;
import nl.jixxed.eliteodysseymaterials.enums.Weapon;
import nl.jixxed.eliteodysseymaterials.helper.ClipboardHelper;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import org.controlsfx.control.PopOver;

import java.util.*;
import java.util.stream.Collectors;

public class LoadoutEditorTab extends EDOTab implements Template {


    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private ScrollPane scrollPane;

    private String activeLoadoutSetUUID;
    private ComboBox<LoadoutSet> loadoutSetSelect;
    private FlowPane loadoutItemsFlow;
    private MenuButton menuButton;

    LoadoutEditorTab() {
        initComponents();
        initEventHandling();
    }

    @Override
    public OdysseyTabs getTabType() {
        return OdysseyTabs.LOADOUT;
    }

    @Override
    public void initEventHandling() {
        EventService.addListener(this, AfterFontSizeSetEvent.class, event -> refreshContent());
        EventService.addListener(this, LanguageChangedEvent.class, event -> refreshContent());
        EventService.addListener(this, LoadoutSetSelectedEvent.class, loadoutSetSelectedEvent -> refreshContent());
        EventService.addListener(this, LoadoutRemovedEvent.class, loadoutRemovedEvent -> refreshContent());
        EventService.addListener(this, LoadoutMovedEvent.class, loadoutMovedEvent -> refreshContent());
        EventService.addListener(this, CommanderSelectedEvent.class, commanderSelectedEvent ->
        {
            refreshLoadoutSetSelect();
            refreshContent();
        });
        EventService.addListener(this, CommanderAllListedEvent.class, commanderAllListedEvent -> refreshLoadoutSetSelect());
        EventService.addListener(this, ImportResultEvent.class, importResultEvent -> {
            if (importResultEvent.getResult().getResultType().equals(ImportResult.ResultType.SUCCESS_LOADOUT)) {
                refreshLoadoutSetSelect();
            }
        });
    }

    private void refreshContent() {
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
            final String fid = commander.getFid();
            final LoadoutSet selectedLoadoutSet = APPLICATION_STATE.getLoadoutSetList(fid).getSelectedLoadoutSet();
            this.activeLoadoutSetUUID = selectedLoadoutSet.getUuid();
            this.loadoutItemsFlow.getChildren().stream().map(LoadoutItem.class::cast).forEach(LoadoutItem::destroy);
            this.loadoutItemsFlow.getChildren().clear();
            this.loadoutItemsFlow.getChildren().addAll(selectedLoadoutSet.getLoadouts().stream()
                    .map(loadout -> new LoadoutItem(selectedLoadoutSet, loadout))
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
        final Set<LoadoutSet> items = APPLICATION_STATE.getPreferredCommander()
                .map(commander -> APPLICATION_STATE.getLoadoutSetList(commander.getFid()).getAllLoadoutSets())
                .orElse(Collections.emptySet());
        this.loadoutSetSelect = ComboBoxBuilder.builder(LoadoutSet.class)
                .withStyleClass("loadout-select")
                .withItemsProperty(FXCollections.observableArrayList(items.stream().sorted(Comparator.comparing(LoadoutSet::getName)).toList()))
                .withValueChangeListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                            this.activeLoadoutSetUUID = newValue.getUuid();
                            APPLICATION_STATE.selectLoadoutSet(this.activeLoadoutSetUUID, commander.getFid());
                            EventService.publish(new LoadoutSetSelectedEvent(this.activeLoadoutSetUUID));
                        });
                    }
                })
                .build();
        final Map<String, EventHandler<ActionEvent>> suitMenuItems = Arrays.stream(Suit.values()).collect(Collectors.toMap(Suit::getLocalizationKey, suit -> event ->
                APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                    final LoadoutSetList loadoutSetList = APPLICATION_STATE.getLoadoutSetList(commander.getFid());
                    final Loadout loadout = new Loadout(suit, 1, 5);
                    loadoutSetList.getSelectedLoadoutSet().addLoadout(loadout);
                    APPLICATION_STATE.saveLoadoutSetList(commander.getFid(), loadoutSetList);
                    refreshContent();
                })));
        final MenuButton addSuitButton = MenuButtonBuilder.builder().withText(LocaleService.getStringBinding("tab.loadout.add.suit")).withMenuItems(suitMenuItems).build();
        final Map<String, EventHandler<ActionEvent>> weaponMenuItems = Arrays.stream(Weapon.values()).collect(Collectors.toMap(Weapon::getLocalizationKey, weapon -> event ->
                APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                    final LoadoutSetList loadoutSetList = APPLICATION_STATE.getLoadoutSetList(commander.getFid());
                    final Loadout loadout = new Loadout(weapon, 1, 5);
                    loadoutSetList.getSelectedLoadoutSet().addLoadout(loadout);
                    APPLICATION_STATE.saveLoadoutSetList(commander.getFid(), loadoutSetList);
                    refreshContent();
                })));
        final MenuButton addWeaponButton = MenuButtonBuilder.builder().withText(LocaleService.getStringBinding("tab.loadout.add.weapon")).withMenuItems(weaponMenuItems).build();

        this.menuButton = MenuButtonBuilder.builder().withText(LocaleService.getStringBinding("tab.wishlist.options")).withMenuItems(
                Map.of("tab.loadout.create", event -> {
                            final TextField textField = TextFieldBuilder.builder().withStyleClasses("root", "loadout-newname").withPromptTextProperty(LocaleService.getStringBinding("tab.loadout.rename.prompt")).build();
                            final Button button = ButtonBuilder.builder().withText(LocaleService.getStringBinding("tab.wishlist.create")).build();
                            final HBox popOverContent = BoxBuilder.builder().withNodes(textField, button).buildHBox();
                            final PopOver popOver = new PopOver(BoxBuilder.builder().withStyleClass("popover-menubutton-box").withNodes(new GrowingRegion(), popOverContent, new GrowingRegion()).buildVBox());
                            popOver.setDetachable(false);
                            popOver.setHeaderAlwaysVisible(false);
                            popOver.getStyleClass().add("popover-menubutton-layout");
                            popOver.setArrowLocation(PopOver.ArrowLocation.RIGHT_CENTER);
                            popOver.show(this.menuButton);
                            button.setOnAction(eventB -> APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                                final LoadoutSetList loadoutSetList = APPLICATION_STATE.getLoadoutSetList(commander.getFid());
                                loadoutSetList.createLoadoutSet(textField.getText());
                                APPLICATION_STATE.saveLoadoutSetList(commander.getFid(), loadoutSetList);
                                textField.clear();
                                refreshLoadoutSetSelect();
                                popOver.hide();
                            }));
                            textField.setOnKeyPressed(ke -> {
                                if (ke.getCode().equals(KeyCode.ENTER)) {
                                    button.fire();
                                }
                            });
                        },
                        "tab.loadout.rename", event -> {
                            final TextField textField = TextFieldBuilder.builder().withStyleClasses("root", "loadout-newname").withPromptTextProperty(LocaleService.getStringBinding("tab.loadout.rename.prompt")).build();

                            final Button button = ButtonBuilder.builder().withText(LocaleService.getStringBinding("tab.wishlist.rename")).build();
                            final HBox popOverContent = BoxBuilder.builder().withNodes(textField, button).buildHBox();
                            final PopOver popOver = new PopOver(BoxBuilder.builder().withStyleClass("popover-menubutton-box").withNodes(new GrowingRegion(), popOverContent, new GrowingRegion()).buildVBox());
                            popOver.setDetachable(false);
                            popOver.setHeaderAlwaysVisible(false);
                            popOver.getStyleClass().add("popover-menubutton-layout");
                            popOver.setArrowLocation(PopOver.ArrowLocation.RIGHT_CENTER);
                            popOver.show(this.menuButton);
                            button.setOnAction(eventB -> APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                                final LoadoutSetList loadoutSetList = APPLICATION_STATE.getLoadoutSetList(commander.getFid());
                                loadoutSetList.renameLoadoutSet(this.activeLoadoutSetUUID, textField.getText());
                                APPLICATION_STATE.saveLoadoutSetList(commander.getFid(), loadoutSetList);
                                textField.clear();
                                refreshLoadoutSetSelect();
                                popOver.hide();
                            }));
                            textField.setOnKeyPressed(ke -> {
                                if (ke.getCode().equals(KeyCode.ENTER)) {
                                    button.fire();
                                }
                            });
                        },
                        "tab.loadout.delete", event -> {
                            final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle(LocaleService.getLocalizedStringForCurrentLocale("tab.loadout.delete.confirm.title"));
                            alert.setHeaderText(LocaleService.getLocalizedStringForCurrentLocale("tab.loadout.delete.confirm.header"));
                            alert.setContentText(LocaleService.getLocalizedStringForCurrentLocale("tab.loadout.delete.confirm.content"));

                            final Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                                    APPLICATION_STATE.deleteLoadoutSet(this.activeLoadoutSetUUID, commander.getFid());
                                    Platform.runLater(this::refreshLoadoutSetSelect);

                                });
                            }
                        },
                        "tab.loadout.copy", event -> {
                            copyLoadoutSetToClipboard();
                            NotificationService.showInformation("Loadout Editor", "The loadout has been copied to your clipboard");
                        })).build();
        this.menuButton.setFocusTraversable(false);

        final HBox hBoxBlueprints = BoxBuilder.builder().withNodes(this.loadoutSetSelect, this.menuButton, addSuitButton, addWeaponButton).buildHBox();
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
            final LoadoutSetList loadoutSetList = APPLICATION_STATE.getLoadoutSetList(commander.getFid());
            final Set<LoadoutSet> items = loadoutSetList.getAllLoadoutSets();
            this.loadoutSetSelect.getItems().clear();
            this.loadoutSetSelect.getItems().addAll(items.stream().sorted(Comparator.comparing(LoadoutSet::getName)).toList());
            this.loadoutSetSelect.getSelectionModel().select(loadoutSetList.getSelectedLoadoutSet());
        });
    }

}
