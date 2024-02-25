package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ShipConfiguration;
import nl.jixxed.eliteodysseymaterials.domain.ShipConfigurations;
import nl.jixxed.eliteodysseymaterials.enums.FontSize;
import nl.jixxed.eliteodysseymaterials.enums.ImportResult;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.helper.ClipboardHelper;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.service.ships.ShipMapper;
import nl.jixxed.eliteodysseymaterials.service.ships.ShipService;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import org.controlsfx.control.PopOver;

import java.util.*;

public class ControlsLayer extends AnchorPane implements Template {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final String FX_FONT_SIZE_DPX = "-fx-font-size: %dpx";
    private final List<EventListener<?>> eventListeners = new ArrayList<>();
    @Getter
    private ComboBox<ShipConfiguration> shipSelect;
    private MenuButton menuButton;
    private String activeShipUUID;

    public ControlsLayer() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("shipbuilder-controls-layer");
        final Set<ShipConfiguration> items = APPLICATION_STATE.getPreferredCommander()
                .map(commander -> ShipService.getShipConfigurations(commander).getAllShipConfigurations())
                .orElse(Collections.emptySet());
        this.shipSelect = ComboBoxBuilder.builder(ShipConfiguration.class)
                .withStyleClass("ship-select")
                .withItemsProperty(FXCollections.observableArrayList(items.stream().sorted(Comparator.comparing(ShipConfiguration::getName)).toList()))
                .withValueChangeListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                            this.activeShipUUID = newValue.getUuid();
                            ShipService.selectShipConfiguration(this.activeShipUUID, commander);
                            EventService.publish(new HorizonsShipSelectedEvent(this.activeShipUUID));
                        });
                    }
                })
                .build();
        this.menuButton = MenuButtonBuilder.builder().withText(LocaleService.getStringBinding("tab.ships.options")).withMenuItems(
                Map.of("tab.ships.clone", event -> {
                            APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                                final ShipConfigurations shipConfigurations = ShipService.getShipConfigurations(commander);
                                final ShipConfiguration shipConfiguration = this.shipSelect.getSelectionModel().getSelectedItem().cloneShipConfiguration();
                                shipConfigurations.addShipConfiguration(shipConfiguration);
                                shipConfigurations.setSelectedShipConfigurationUUID(shipConfiguration.getUuid());
                                ShipService.saveShipConfigurations(commander, shipConfigurations);
                                refreshShipSelect();
                            });
                        },
                        "tab.ships.create", event -> {
                            final TextField textField = TextFieldBuilder.builder().withStyleClasses("root", "ships-newname").withPromptTextProperty(LocaleService.getStringBinding("tab.ships.rename.prompt")).build();
                            final Button button = ButtonBuilder.builder().withText(LocaleService.getStringBinding("tab.ships.create")).build();
                            final HBox popOverContent = BoxBuilder.builder().withNodes(textField, button).buildHBox();
                            final PopOver popOver = new PopOver(BoxBuilder.builder().withStyleClass("popover-menubutton-box").withNodes(new GrowingRegion(), popOverContent, new GrowingRegion()).buildVBox());
                            popOver.setDetachable(false);
                            popOver.setHeaderAlwaysVisible(false);
                            popOver.getStyleClass().add("popover-menubutton-layout");
                            popOver.setArrowLocation(PopOver.ArrowLocation.RIGHT_CENTER);
                            popOver.show(this.menuButton);
                            button.setOnAction(eventB -> APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                                final ShipConfigurations shipConfigurations = ShipService.getShipConfigurations(commander);
                                shipConfigurations.createShipConfiguration(textField.getText());
                                ShipService.saveShipConfigurations(commander, shipConfigurations);
                                textField.clear();
                                refreshShipSelect();
                                popOver.hide();
                            }));
                            textField.setOnKeyPressed(ke -> {
                                if (ke.getCode().equals(KeyCode.ENTER)) {
                                    button.fire();
                                }
                            });
                        },
                        "tab.ships.rename", event -> {
                            final TextField textField = TextFieldBuilder.builder().withStyleClasses("root", "ships-newname").withPromptTextProperty(LocaleService.getStringBinding("tab.ships.rename.prompt")).build();
                            final Button button = ButtonBuilder.builder().withText(LocaleService.getStringBinding("tab.ships.rename")).build();
                            final HBox popOverContent = BoxBuilder.builder().withNodes(textField, button).buildHBox();
                            final PopOver popOver = new PopOver(BoxBuilder.builder().withStyleClass("popover-menubutton-box").withNodes(new GrowingRegion(), popOverContent, new GrowingRegion()).buildVBox());
                            popOver.setDetachable(false);
                            popOver.setHeaderAlwaysVisible(false);
                            popOver.getStyleClass().add("popover-menubutton-layout");
                            popOver.setArrowLocation(PopOver.ArrowLocation.RIGHT_CENTER);
                            popOver.show(this.menuButton);
                            button.setOnAction(eventB -> APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                                final ShipConfigurations shipConfigurations = ShipService.getShipConfigurations(commander);
                                shipConfigurations.renameShipConfiguration(this.activeShipUUID, textField.getText());
                                ShipService.saveShipConfigurations(commander, shipConfigurations);
                                textField.clear();
                                refreshShipSelect();
                                popOver.hide();
                            }));
                            textField.setOnKeyPressed(ke -> {
                                if (ke.getCode().equals(KeyCode.ENTER)) {
                                    button.fire();
                                }
                            });
                        },
                        "tab.ships.delete", event -> {
                            final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle(LocaleService.getLocalizedStringForCurrentLocale("tab.ships.delete.confirm.title"));
                            alert.setHeaderText(LocaleService.getLocalizedStringForCurrentLocale("tab.ships.delete.confirm.header"));
                            alert.setContentText(LocaleService.getLocalizedStringForCurrentLocale("tab.ships.delete.confirm.content"));

                            final Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                                    ShipService.deleteShipConfiguration(this.activeShipUUID, commander);
                                    Platform.runLater(this::refreshShipSelect);
                                });
                            }
                        },
                        "tab.ships.reset", event -> {
                            final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle(LocaleService.getLocalizedStringForCurrentLocale("tab.ships.reset.confirm.title"));
                            alert.setHeaderText(LocaleService.getLocalizedStringForCurrentLocale("tab.ships.reset.confirm.header"));
                            alert.setContentText(LocaleService.getLocalizedStringForCurrentLocale("tab.ships.reset.confirm.content"));

                            final Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                                    final ShipConfigurations shipConfigurations = ShipService.getShipConfigurations(commander);
                                    shipConfigurations.resetShipConfiguration(this.activeShipUUID);
                                    ShipService.saveShipConfigurations(commander, shipConfigurations);
                                    EventService.publish(new HorizonsShipSelectedEvent(this.activeShipUUID));
                                    refreshShipSelect();
                                });
                            }
                        },
                        "tab.ships.copy", event -> {
                            copyShipToClipboard();
                            NotificationService.showInformation(NotificationType.COPY, "Ships", "The ship has been copied to your clipboard");
                        }
//                        ,
//                        "tab.ships.export", event ->
//
//                                EventService.publish(new SaveWishlistEvent(
//                                        () -> TextExporter.createTextWishlist(this.wishlistNeededRaw, this.wishlistNeededEncoded, this.wishlistNeededManufactured, this.wishlistNeededCommodity),
//                                        () -> CsvExporter.createCsvWishlist(this.wishlistNeededRaw, this.wishlistNeededEncoded, this.wishlistNeededManufactured, this.wishlistNeededCommodity),
//                                        () -> XlsExporter.createXlsWishlist(this.wishlistNeededRaw, this.wishlistNeededEncoded, this.wishlistNeededManufactured, this.wishlistNeededCommodity)
//                                ))
                ),
                Map.of(
                        "tab.ships.rename", this.shipSelect.getSelectionModel().selectedItemProperty().isEqualTo(ShipConfiguration.CURRENT),
                        "tab.ships.copy", this.shipSelect.getSelectionModel().selectedItemProperty().isEqualTo(ShipConfiguration.CURRENT),
                        "tab.ships.delete", this.shipSelect.getSelectionModel().selectedItemProperty().isEqualTo(ShipConfiguration.CURRENT)
                )).build();
        this.menuButton.setFocusTraversable(false);
        final Integer fontSize = FontSize.valueOf(PreferencesService.getPreference(PreferenceConstants.TEXTSIZE, "NORMAL")).getSize();
        applyFontSizingHack(fontSize);
        final HBox hBoxShips = BoxBuilder.builder().withStyleClass("shipbuilder-controls-box").withNodes(this.shipSelect, this.menuButton).buildHBox();
        hBoxShips.spacingProperty().bind(ScalingHelper.getPixelDoubleBindingFromEm(0.25));
        this.getChildren().add(hBoxShips);
        hBoxShips.setPickOnBounds(false);
//        AnchorPane.setTopAnchor(hBoxShips,0D);
        AnchorPane.setLeftAnchor(hBoxShips, 0D);
        AnchorPane.setRightAnchor(hBoxShips, 0D);
    }

    @Override
    public void initEventHandling() {

        this.eventListeners.add(EventService.addListener(this, AfterFontSizeSetEvent.class, fontSizeEvent -> applyFontSizingHack(fontSizeEvent.getFontSize())));

        this.eventListeners.add(EventService.addListener(this, 9, ShipLoadoutEvent.class, event -> {
            EventService.publish(new HorizonsShipSelectedEvent(this.shipSelect.getSelectionModel().getSelectedItem().getUuid()));
        }));

        this.eventListeners.add(EventService.addListener(this, HorizonsShipChangedEvent.class, horizonsShipChangedEvent -> {
            this.activeShipUUID = horizonsShipChangedEvent.getShipUUID();
        }));

        this.eventListeners.add(EventService.addListener(this, 0, HorizonsShipSelectedEvent.class, horizonsShipSelectedEvent -> {
            APPLICATION_STATE.getPreferredCommander()
                    .flatMap(commander -> ShipService.getShipConfigurations(commander).getSelectedShipConfiguration())
                    .ifPresent(configuration -> APPLICATION_STATE.setShip(ShipMapper.toShip(configuration)));
            refreshShipSelect();
            EventService.publish(new HorizonsShipChangedEvent(this.activeShipUUID));
        }));

        this.eventListeners.add(EventService.addListener(this, CommanderSelectedEvent.class, commanderSelectedEvent -> {
            final Optional<ShipConfiguration> shipConfiguration = ShipService.getShipConfigurations(commanderSelectedEvent.getCommander()).getSelectedShipConfiguration();
            this.activeShipUUID = shipConfiguration.map(ShipConfiguration::getUuid).orElse(null);
            refreshShipSelect();
            EventService.publish(new HorizonsShipChangedEvent(this.activeShipUUID));
        }));

        this.eventListeners.add(EventService.addListener(this, CommanderAllListedEvent.class, commanderAllListedEvent -> {
            refreshShipSelect();
        }));

        this.eventListeners.add(EventService.addListener(this, ImportResultEvent.class, importResultEvent -> {
            if (importResultEvent.getResult().getResultType().equals(ImportResult.ResultType.SUCCESS_HORIZONS_SHIP)) {
                refreshShipSelect();
            }
        }));

    }

    private void applyFontSizingHack(final Integer fontSize) {
        //hack for component resizing on other fontsizes
        final String fontStyle = String.format(FX_FONT_SIZE_DPX, fontSize);
        this.shipSelect.styleProperty().set(fontStyle);
    }

    private void refreshShipSelect() {
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
            final ShipConfigurations shipConfigurations = ShipService.getShipConfigurations(commander);
            final Set<ShipConfiguration> allShipConfigurations = shipConfigurations.getAllShipConfigurations();
            this.shipSelect.getItems().clear();
            this.shipSelect.getItems().addAll(allShipConfigurations.stream().sorted(Comparator.comparing(ShipConfiguration::getName)).toList());
            shipConfigurations.getSelectedShipConfiguration().ifPresent(configuration -> this.shipSelect.getSelectionModel().select(configuration));
        });
    }


    private void copyShipToClipboard() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent clipboardContent = new ClipboardContent();

        clipboardContent.putString(ClipboardHelper.createClipboardShipConfiguration());
        clipboard.setContent(clipboardContent);
    }
}
