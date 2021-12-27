package nl.jixxed.eliteodysseymaterials.templates;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.*;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.Loadout;
import nl.jixxed.eliteodysseymaterials.domain.LoadoutSet;
import nl.jixxed.eliteodysseymaterials.domain.LoadoutSetList;
import nl.jixxed.eliteodysseymaterials.enums.ImportResult;
import nl.jixxed.eliteodysseymaterials.enums.Suit;
import nl.jixxed.eliteodysseymaterials.enums.Tabs;
import nl.jixxed.eliteodysseymaterials.enums.Weapon;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.event.*;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class LoadoutEditorTab extends EDOTab implements Template {


    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final String LOADOUT_BUTTON_STYLE_CLASS = "loadout-button";
    private ScrollPane scrollPane;

    private Button createLoadoutSetButton;
    private Button renameLoadoutSetButton;
    private Button removeLoadoutSetButton;
    private Button clipboardButton;
    private String activeLoadoutSetUUID;
    private TextField loadoutSetName;
    private ComboBox<LoadoutSet> loadoutSetSelect;
    private FlowPane loadoutItemsFlow;

    LoadoutEditorTab() {
        initComponents();
        initEventHandling();
    }

    @Override
    public Tabs getTabType() {
        return Tabs.LOADOUT;
    }

    @Override
    public void initEventHandling() {
        EventService.addListener(this, AfterFontSizeSetEvent.class, event -> {
            refreshContent();
        });
        EventService.addListener(this, LanguageChangedEvent.class, event -> {
            refreshContent();
        });
        EventService.addListener(this, LoadoutSetSelectedEvent.class, loadoutSetSelectedEvent -> {
            refreshContent();
        });
        EventService.addListener(this, LoadoutRemovedEvent.class, loadoutRemovedEvent -> {
            refreshContent();
        });
        EventService.addListener(this, LoadoutMovedEvent.class, loadoutMovedEvent -> {
            refreshContent();
        });

        EventService.addListener(this, CommanderSelectedEvent.class, commanderSelectedEvent ->
        {
            refreshLoadoutSetSelect();
            refreshContent();
        });
        EventService.addListener(this, CommanderAllListedEvent.class, commanderAllListedEvent -> refreshLoadoutSetSelect());
        EventService.addListener(this, ImportResultEvent.class, importResultEvent -> {
            if (importResultEvent.getResult().equals(ImportResult.SUCCESS_LOADOUT)) {
                refreshLoadoutSetSelect();
            }
        });
    }

    private void refreshContent() {
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
            final String fid = commander.getFid();
            final LoadoutSet selectedLoadoutSet = APPLICATION_STATE.getLoadoutSetList(fid).getSelectedLoadoutSet();
            this.activeLoadoutSetUUID = selectedLoadoutSet.getUuid();
            this.loadoutItemsFlow.getChildren().stream().map(LoadoutItem.class::cast).forEach(LoadoutItem::onDestroy);
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
        this.loadoutItemsFlow.setHgap(5);
        this.loadoutItemsFlow.setVgap(5);
        final VBox vBox = BoxBuilder.builder().withStyleClass("loadout-box").withNodes(menu, this.loadoutItemsFlow).buildVBox();
        vBox.setSpacing(5);
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
        final Map<String, EventHandler<ActionEvent>> suitMenuItems = Arrays.stream(Suit.values()).collect(Collectors.toMap(Suit::getLocalizationKey, suit -> event -> {
            APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                final LoadoutSetList loadoutSetList = APPLICATION_STATE.getLoadoutSetList(commander.getFid());
                final Loadout loadout = new Loadout(suit, 1, 5);
                loadoutSetList.getSelectedLoadoutSet().addLoadout(loadout);
                APPLICATION_STATE.saveLoadoutSetList(commander.getFid(), loadoutSetList);
                refreshContent();
            });
        }));
        final MenuButton addSuitButton = MenuButtonBuilder.builder().withText(LocaleService.getStringBinding("tab.loadout.add.suit")).withMenuItems(suitMenuItems).build();
        final Map<String, EventHandler<ActionEvent>> weaponMenuItems = Arrays.stream(Weapon.values()).collect(Collectors.toMap(Weapon::getLocalizationKey, weapon -> event -> {
            APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                final LoadoutSetList loadoutSetList = APPLICATION_STATE.getLoadoutSetList(commander.getFid());
                final Loadout loadout = new Loadout(weapon, 1, 5);
                loadoutSetList.getSelectedLoadoutSet().addLoadout(loadout);
                APPLICATION_STATE.saveLoadoutSetList(commander.getFid(), loadoutSetList);
                refreshContent();
            });
        }));
        final MenuButton addWeaponButton = MenuButtonBuilder.builder().withText(LocaleService.getStringBinding("tab.loadout.add.weapon")).withMenuItems(weaponMenuItems).build();
        this.removeLoadoutSetButton = ButtonBuilder.builder()
                .withStyleClass(LOADOUT_BUTTON_STYLE_CLASS)
                .withText(LocaleService.getStringBinding("tab.loadout.delete"))
                .withOnAction(event -> {
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
                })
                .build();
        this.renameLoadoutSetButton = ButtonBuilder.builder()
                .withStyleClass(LOADOUT_BUTTON_STYLE_CLASS)
                .withText(LocaleService.getStringBinding("tab.loadout.rename"))
                .withOnAction(event -> APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                    final LoadoutSetList loadoutSetList = APPLICATION_STATE.getLoadoutSetList(commander.getFid());
                    loadoutSetList.renameLoadoutSet(this.activeLoadoutSetUUID, this.loadoutSetName.getText());
                    APPLICATION_STATE.saveLoadoutSetList(commander.getFid(), loadoutSetList);
                    this.loadoutSetName.clear();
                    refreshLoadoutSetSelect();
                }))
                .build();
        this.createLoadoutSetButton = ButtonBuilder.builder()
                .withStyleClass(LOADOUT_BUTTON_STYLE_CLASS)
                .withText(LocaleService.getStringBinding("tab.loadout.create"))
                .withOnAction(event -> APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                    final LoadoutSetList loadoutSetList = APPLICATION_STATE.getLoadoutSetList(commander.getFid());
                    loadoutSetList.createLoadoutSet(this.loadoutSetName.getText());
                    APPLICATION_STATE.saveLoadoutSetList(commander.getFid(), loadoutSetList);
                    this.loadoutSetName.clear();
                    refreshLoadoutSetSelect();
                }))
                .build();
        this.loadoutSetName = TextFieldBuilder.builder().withStyleClasses("root", "loadout-newname").withPromptTextProperty(LocaleService.getStringBinding("tab.loadout.rename.prompt")).build();
        this.clipboardButton = ButtonBuilder.builder().withStyleClass(LOADOUT_BUTTON_STYLE_CLASS).withText(LocaleService.getStringBinding("tab.loadout.copy"))
                .withOnAction(event -> {
                    copyLoadoutSetToClipboard();
                    NotificationService.showInformation("Loadout Editor", "The loadout has been copied to your clipboard");
                }).build();

        final double minButtonWidth = 110.0;
        this.removeLoadoutSetButton.setMinWidth(minButtonWidth);
        this.renameLoadoutSetButton.setMinWidth(minButtonWidth);
        this.createLoadoutSetButton.setMinWidth(minButtonWidth);
        this.clipboardButton.setMinWidth(minButtonWidth);


        final Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        region.setMinWidth(5);

        final FlowPane flowPane = FlowPaneBuilder.builder().withNodes(this.renameLoadoutSetButton, this.createLoadoutSetButton, this.removeLoadoutSetButton, this.clipboardButton).build();
        flowPane.setHgap(4);
        flowPane.setVgap(4);
        flowPane.prefWrapLengthProperty().bind(this.renameLoadoutSetButton.widthProperty().add(this.createLoadoutSetButton.widthProperty()).add(this.removeLoadoutSetButton.widthProperty()).add(this.clipboardButton.widthProperty()).add(20));
        flowPane.setColumnHalignment(HPos.RIGHT);
        final HBox hBoxBlueprints = BoxBuilder.builder().withNodes(this.loadoutSetSelect, addSuitButton, addWeaponButton, region, this.loadoutSetName, flowPane).buildHBox();
        hBoxBlueprints.setSpacing(5);
        return hBoxBlueprints;
    }

    private void copyLoadoutSetToClipboard() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent clipboardContent = new ClipboardContent();
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
            try {
                clipboardContent.putString(Base64.getEncoder().encodeToString(OBJECT_MAPPER.writeValueAsString(APPLICATION_STATE.getLoadoutSetList(commander.getFid()).getSelectedLoadoutSet()).getBytes(StandardCharsets.UTF_8)));
            } catch (final JsonProcessingException e) {
                e.printStackTrace();
            }
        });
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
