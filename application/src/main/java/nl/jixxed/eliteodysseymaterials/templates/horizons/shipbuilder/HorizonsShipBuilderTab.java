package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.skin.ScrollPaneSkin;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ShipConfiguration;
import nl.jixxed.eliteodysseymaterials.domain.ShipConfigurations;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.enums.FontSize;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsTabs;
import nl.jixxed.eliteodysseymaterials.enums.ImportResult;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.helper.ClipboardHelper;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.service.ImageService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.service.ships.ShipMapper;
import nl.jixxed.eliteodysseymaterials.service.ships.ShipService;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;
import nl.jixxed.eliteodysseymaterials.templates.horizons.HorizonsTab;
import nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats.*;
import org.controlsfx.control.PopOver;

import java.text.NumberFormat;
import java.util.*;

public class HorizonsShipBuilderTab extends HorizonsTab {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final String SHIP_CONTENT_STYLE_CLASS = "ships-content";
    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance();
    private ScrollPane scrollPane;
    private VBox hardpointsVbox;
    private VBox coreVbox;
    private VBox optionalVBox;
    private VBox utilityVbox;
    private HBox slotColumns;
    private StackPane stackPane;
    private Circle circleStart;
    private Circle circleEnd;
    private Line line;
    private Line line2;
    private Group overlayLine;
    private Pane pane;
    private DestroyableResizableImageView shipImage;
    private ComboBox<ShipConfiguration> shipSelect;
    private MenuButton menuButton;
    private String activeShipUUID;
    private final List<EventListener<?>> eventListeners = new ArrayList<>();
    private Label noShip;
    private VBox content;
    private VBox contentChild;
    private VBox shipView;
    private VBox shipSelectView;
    private ModuleDetails moduleDetails;
    private Region filler;
    private VBox right;


    @Override
    public HorizonsTabs getTabType() {
        return HorizonsTabs.SHIPBUILDER;
    }

    public HorizonsShipBuilderTab() {
        initComponents();
        initEventHandling();

    }

    private static final String FX_FONT_SIZE_DPX = "-fx-font-size: %dpx";

    private void applyFontSizingHack(final Integer fontSize) {
        //hack for component resizing on other fontsizes
        final String fontStyle = String.format(FX_FONT_SIZE_DPX, fontSize);
        this.shipSelect.styleProperty().set(fontStyle);
    }

    private void setFillerHeight(final double scrollbarPos) {
        final long height = Math.round(scrollbarPos * (this.scrollPane.getContent().getBoundsInParent().getHeight() - this.scrollPane.getViewportBounds().getHeight()));

        right.setPrefHeight(0);
        right.setMaxHeight(0);
        right.setMaxHeight(this.scrollPane.getHeight() + height);
        this.filler.setMaxHeight(height);
        this.filler.setMinHeight(height);
        right.setPrefHeight(this.scrollPane.getHeight() + height);

//        this.filler.minHeightProperty().bind(this.scrollPane.boundsInParentProperty().map(Bounds::getHeight).map(val-> Math.round(scrollbarPos * (val - this.scrollPane.getViewportBounds().getHeight()))));
//        this.filler.maxHeightProperty().bind(this.scrollPane.boundsInParentProperty().map(Bounds::getHeight).map(val-> Math.round(scrollbarPos * (val - this.scrollPane.getViewportBounds().getHeight()))));
    }

    private void initComponents() {
        this.textProperty().bind(LocaleService.getStringBinding("tabs.ships"));
        this.moduleDetails = new ModuleDetails();
        filler = new Region();
        filler.getStyleClass().add("stats-filler");
        right = BoxBuilder.builder().withStyleClass("stats-column").withNodes(this.filler, this.moduleDetails, new GrowingRegion()).buildVBox();

        this.setContent(this.scrollPane);

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
//                        "tab.ships.rename", this.shipSelect.getSelectionModel().selectedItemProperty().isEqualTo(HorizonsWishlist.ALL),
//                        "tab.ships.copy", this.shipSelect.getSelectionModel().selectedItemProperty().isEqualTo(HorizonsWishlist.ALL),
//                        "tab.ships.delete", this.shipSelect.getSelectionModel().selectedItemProperty().isEqualTo(HorizonsWishlist.ALL)
                )).build();
        this.menuButton.setFocusTraversable(false);
        final Integer fontSize = FontSize.valueOf(PreferencesService.getPreference(PreferenceConstants.TEXTSIZE, "NORMAL")).getSize();
        applyFontSizingHack(fontSize);
        final HBox hBoxShips = BoxBuilder.builder().withNodes(this.shipSelect, this.menuButton).buildHBox();
        hBoxShips.spacingProperty().bind(ScalingHelper.getPixelDoubleBindingFromEm(0.25));

        this.scrollPane = ScrollPaneBuilder.builder()
                .withContent(content)
                .build();
        this.setContent(this.scrollPane);
        initShipSelectView();
        initShipLayout();
        this.noShip = LabelBuilder.builder().withNonLocalizedText("NOSHIP").build();
        this.contentChild = BoxBuilder.builder().withStyleClass(SHIP_CONTENT_STYLE_CLASS).withNodes(this.shipSelect.getSelectionModel().getSelectedItem() == null || (this.shipSelect.getSelectionModel().getSelectedItem().getShipType() == null && this.shipSelect.getSelectionModel().getSelectedItem() != ShipConfiguration.CURRENT) ? this.shipSelectView : this.shipView).buildVBox();
        this.content = BoxBuilder.builder().withStyleClass(SHIP_CONTENT_STYLE_CLASS).withNodes(hBoxShips, this.shipSelect.getItems().isEmpty() || this.shipSelect.getSelectionModel().getSelectedItem() == ShipConfiguration.CURRENT ? this.noShip : this.contentChild).buildVBox();
//        this.scrollPane = ScrollPaneBuilder.builder()
//                .withContent(this.content)
//                .build();

        final HBox layout = BoxBuilder.builder().withNodes(this.content, new GrowingRegion(), this.right).buildHBox();

        this.scrollPane = ScrollPaneBuilder.builder()
                .withContent(layout)
                .build();
        this.content.minHeightProperty().bind(this.right.prefHeightProperty());
//        this.scrollPane.getContent().getBoundsInParent().getHeight() - this.scrollPane.getViewportBounds().getHeight()
//        layout.prefHeightProperty().bind(this.scrollPane.prefViewportHeightProperty());
//        layout.minHeightProperty().bind(this.scrollPane.getContent().boundsInParentProperty().map(bounds -> bounds.getHeight()));
        this.scrollPane.skinProperty().addListener((observable, oldValue, newValue) ->
                ((ScrollPaneSkin) this.scrollPane.getSkin()).getVerticalScrollBar().valueProperty().addListener((observable2, oldValue2, newValue2) -> {
                    setFillerHeight((double) newValue2);
                }));
        this.scrollPane.heightProperty().addListener((observable, oldValue, newValue) -> {
            setFillerHeight(((ScrollPaneSkin) this.scrollPane.getSkin()).getVerticalScrollBar().getValue());
        });
        this.scrollPane.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
            setFillerHeight(((ScrollPaneSkin) this.scrollPane.getSkin()).getVerticalScrollBar().getValue());
        });
        this.setContent(this.scrollPane);
    }

    private void copyShipToClipboard() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent clipboardContent = new ClipboardContent();

        clipboardContent.putString(ClipboardHelper.createClipboardShipConfiguration());
        clipboard.setContent(clipboardContent);
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

    private void refreshContent() {
        APPLICATION_STATE.getPreferredCommander().ifPresentOrElse(commander -> {
            final Optional<ShipConfiguration> shipConfiguration = ShipService.getShipConfigurations(commander).getSelectedShipConfiguration();
            shipConfiguration.ifPresentOrElse(configuration -> {
                if (configuration.getShipType() != null) {
                    if (!this.contentChild.getChildren().contains(this.shipView)) {
                        this.contentChild.getChildren().remove(this.shipSelectView);
                        this.contentChild.getChildren().add(this.shipView);
                    }
                    initShipSlots();
                } else if (configuration != ShipConfiguration.CURRENT) {
                    this.contentChild.getChildren().remove(this.shipSelectView);
                    this.contentChild.getChildren().remove(this.shipView);
                } else {
                    if (!this.contentChild.getChildren().contains(this.shipSelectView)) {
                        this.contentChild.getChildren().remove(this.shipView);
                        this.contentChild.getChildren().add(this.shipSelectView);
                    }
                }
                if (!this.content.getChildren().contains(this.contentChild) && (configuration != ShipConfiguration.CURRENT || configuration.getShipType() != null)) {
                    this.content.getChildren().remove(this.noShip);
                    this.content.getChildren().add(1, this.contentChild);
                }

            }, () -> {
                if (!this.content.getChildren().contains(this.noShip)) {
                    this.content.getChildren().remove(this.contentChild);
                    this.content.getChildren().add(1, this.noShip);
                }
            });
        }, () -> {
            if (!this.content.getChildren().contains(this.noShip)) {
                this.content.getChildren().remove(this.contentChild);
                this.content.getChildren().add(1, this.noShip);
            }
        });
    }

    private void initShipSelectView() {
        final List<HBox> shipRows = Ship.ALL.stream().map(ship1 -> {
            final DestroyableLabel shipName = LabelBuilder.builder().withStyleClass("shipbuilder-ship-row-name").withText(LocaleService.getStringBinding(ship1.getShipType().getLocalizationKey())).build();
            final DestroyableLabel price = LabelBuilder.builder().withStyleClass("shipbuilder-ship-row-price").withNonLocalizedText(NUMBER_FORMAT.format(ship1.getRetailPrice())).build();
            return BoxBuilder.builder().withStyleClass("shipbuilder-ship-row").withNodes(shipName, price).withOnMouseClicked(event -> {
                APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                            final ShipConfigurations shipConfigurations = ShipService.getShipConfigurations(commander);
                            shipConfigurations.getSelectedShipConfiguration().ifPresent(configuration -> {
                                APPLICATION_STATE.setShip(new Ship(ship1));
                                ShipMapper.toShipConfiguration(APPLICATION_STATE.getShip(), configuration, shipSelect.getSelectionModel().getSelectedItem().getName());
                                ShipService.saveShipConfigurations(commander, shipConfigurations);
                                refreshContent();

                            });
                        }
                );
            }).buildHBox();
        }).toList();
        HBox header = BoxBuilder.builder().withStyleClass("shipbuilder-ship-header").withNodes(
                LabelBuilder.builder().withStyleClass("shipbuilder-ship-row-name").withText(LocaleService.getStringBinding("ship.view.header.ship.name")).build(),
                LabelBuilder.builder().withStyleClass("shipbuilder-ship-row-price").withText(LocaleService.getStringBinding("ship.view.header.price")).build()
        ).buildHBox();
        this.shipSelectView = BoxBuilder.builder().withNodes(shipRows.toArray(HBox[]::new)).buildVBox();
        this.shipSelectView.getChildren().add(0, header);

    }

    private void initShipLayout() {
        this.hardpointsVbox = BoxBuilder.builder().withStyleClass("shipbuilder-slots-vbox").buildVBox();
        this.coreVbox = BoxBuilder.builder().withStyleClass("shipbuilder-slots-vbox").buildVBox();
        this.optionalVBox = BoxBuilder.builder().withStyleClass("shipbuilder-slots-vbox").buildVBox();
        this.utilityVbox = BoxBuilder.builder().withStyleClass("shipbuilder-slots-vbox").buildVBox();
//        this.hardpointsVbox = BoxBuilder.builder().withStyleClass("shipbuilder-slots-vbox").withNodes(this.ship.getHardpointSlots().stream().map(slot -> new SlotBox(this, slot)).toArray(SlotBox[]::new)).buildVBox();
//        this.coreVbox = BoxBuilder.builder().withStyleClass("shipbuilder-slots-vbox").withNodes(this.ship.getCoreSlots().stream().map(slot -> new SlotBox(this, slot)).toArray(SlotBox[]::new)).buildVBox();
//        this.optionalVBox = BoxBuilder.builder().withStyleClass("shipbuilder-slots-vbox").withNodes(this.ship.getOptionalSlots().stream().map(slot -> new SlotBox(this, slot)).toArray(SlotBox[]::new)).buildVBox();
//        this.utilityVbox = BoxBuilder.builder().withStyleClass("shipbuilder-slots-vbox").withNodes(this.ship.getUtilitySlots().stream().map(slot -> new SlotBox(this, slot)).toArray(SlotBox[]::new)).buildVBox();
        initShipSlots();
        this.slotColumns = BoxBuilder.builder().withNodes(this.hardpointsVbox, this.coreVbox, this.optionalVBox, this.utilityVbox).withStyleClass("shipbuilder-slots-hbox").buildHBox();
        final DestroyableResizableImageView image = ResizableImageViewBuilder.builder().withStyleClass("shipbuilder-ship-image").withImage("/images/ships/ship/grid.png").build();
        shipImage = ResizableImageViewBuilder.builder().withStyleClass("shipbuilder-ship-image").withImage("/images/ships/ship/grid.png").build();
        this.stackPane = new StackPane(image, shipImage);
        this.stackPane.getStyleClass().add("shipbuilder-stackpane");
        final HBox stats = BoxBuilder.builder().buildHBox();
        stats.getChildren().addAll(
                new JumpStats(),
                new EngineStats(),
                new HandlingStats(),
                new ArmourStats(),
                new ShieldStats(),
                new WeaponStats()
        );
        this.shipView = BoxBuilder.builder().withNodes(stats, this.slotColumns).buildVBox();
        //Drawing a Circle
        this.circleStart = new Circle();
        this.circleEnd = new Circle();
        this.line = new Line();
        this.line2 = new Line();
        this.pane = new Pane(this.circleStart, this.line, this.circleEnd, this.line2);
        this.pane.getStyleClass().add("shipbuilder-overlay");
        this.overlayLine = new Group(this.pane);
//        this.overlayLine.getStyleClass().add("shipbuilder-overlay");
        this.stackPane.getChildren().add(this.overlayLine);
        StackPane.setAlignment(this.overlayLine, Pos.TOP_LEFT);
        StackPane.setAlignment(image, Pos.TOP_LEFT);
    }

    private void initShipSlots() {
        this.hardpointsVbox.getChildren().clear();
        this.coreVbox.getChildren().clear();
        this.optionalVBox.getChildren().clear();
        this.utilityVbox.getChildren().clear();
        if (APPLICATION_STATE.getShip() != null) {
            this.hardpointsVbox.getChildren().addAll(APPLICATION_STATE.getShip().getHardpointSlots().stream().map(slot -> new SlotBox(this, slot)).toArray(SlotBox[]::new));
            this.coreVbox.getChildren().addAll(APPLICATION_STATE.getShip().getCoreSlots().stream().map(slot -> new SlotBox(this, slot)).toArray(SlotBox[]::new));
            this.optionalVBox.getChildren().addAll(APPLICATION_STATE.getShip().getOptionalSlots().stream().map(slot -> new SlotBox(this, slot)).toArray(SlotBox[]::new));
            this.utilityVbox.getChildren().addAll(APPLICATION_STATE.getShip().getUtilitySlots().stream().map(slot -> new SlotBox(this, slot)).toArray(SlotBox[]::new));
        }
    }

    private void initEventHandling() {
        this.eventListeners.add(EventService.addListener(this, AfterFontSizeSetEvent.class, fontSizeEvent -> applyFontSizingHack(fontSizeEvent.getFontSize())));
        this.eventListeners.add(EventService.addListener(this, 0, HorizonsShipSelectedEvent.class, horizonsShipSelectedEvent -> {
            APPLICATION_STATE.getPreferredCommander()
                    .flatMap(commander -> ShipService.getShipConfigurations(commander).getSelectedShipConfiguration())
                    .ifPresent(configuration -> APPLICATION_STATE.setShip(ShipMapper.toShip(configuration)));
            initShipSlots();
            refreshContent();
            EventService.publish(new HorizonsShipChangedEvent(this.activeShipUUID));
        }));
//        this.eventListeners.add(EventService.addListener(this, HorizonsWishlistBlueprintAlteredEvent.class, wishlistChangedEvent -> {
//            refreshContent();
//        }));
        this.eventListeners.add(EventService.addListener(this, HorizonsShipChangedEvent.class, horizonsShipChangedEvent -> {
            this.activeShipUUID = horizonsShipChangedEvent.getShipUUID();

//            APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> this.wishlistSize = WishlistService.getHorizonsWishlists(commander).getWishlist(this.activeWishlistUUID).getItems().size());

//            this.textProperty().bind(LocaleService.getSupplierStringBinding("tabs.wishlist", () -> (this.wishlistSize > 0) ? " (" + this.wishlistSize + ")" : ""));
        }));
        this.eventListeners.add(EventService.addListener(this, CommanderSelectedEvent.class, commanderSelectedEvent -> {
            final Optional<ShipConfiguration> shipConfiguration = ShipService.getShipConfigurations(commanderSelectedEvent.getCommander()).getSelectedShipConfiguration();
            this.activeShipUUID = shipConfiguration.map(ShipConfiguration::getUuid).orElse(null);
            refreshShipSelect();
            refreshContent();
            EventService.publish(new HorizonsShipChangedEvent(this.activeShipUUID));
        }));
//        this.eventListeners.add(EventService.addListener(this, LanguageChangedEvent.class, languageChangedEvent -> {
//            refreshShipSelect();
//        }));
        this.eventListeners.add(EventService.addListener(this, CommanderAllListedEvent.class, commanderAllListedEvent -> {
            refreshShipSelect();
            refreshContent();
        }));
        this.eventListeners.add(EventService.addListener(this, ImportResultEvent.class, importResultEvent -> {
            if (importResultEvent.getResult().getResultType().equals(ImportResult.ResultType.SUCCESS_HORIZONS_SHIP)) {
                refreshShipSelect();
                refreshContent();
            }
        }));
        this.eventListeners.add(EventService.addListener(this, ShipBuilderEvent.class, importResultEvent -> {
            APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                final ShipConfigurations shipConfigurations = ShipService.getShipConfigurations(commander);
                final Optional<ShipConfiguration> selectedShipConfiguration = shipConfigurations.getSelectedShipConfiguration();
                selectedShipConfiguration.ifPresent(shipConfiguration -> {
                    ShipMapper.toShipConfiguration(APPLICATION_STATE.getShip(), shipConfiguration, shipConfiguration.getName());
                    ShipService.saveShipConfigurations(commander, shipConfigurations);
                });
            });
        }));

        this.eventListeners.add(EventService.addListener(this, 9, ShipLoadoutEvent.class, event -> {
            refreshShipSelect();
            refreshCurrentLoadout();
            EventService.publish(new HorizonsShipSelectedEvent(this.shipSelect.getSelectionModel().getSelectedItem().getUuid()));
        }));
    }

    private void refreshCurrentLoadout() {
        if (this.shipSelect.getSelectionModel().getSelectedItem().equals(ShipConfiguration.CURRENT)) {
            refreshContent();
        }
    }

    void toggleHardpointImage(final boolean imageVisible) {
        if (imageVisible) {
            if (this.slotColumns.getChildren().contains(this.coreVbox)) {
                this.slotColumns.getChildren().removeAll(this.coreVbox, this.optionalVBox, this.utilityVbox);
                this.slotColumns.getChildren().add(this.stackPane);
            }
        } else {
            if (this.slotColumns.getChildren().contains(this.stackPane)) {
                this.slotColumns.getChildren().remove(this.stackPane);
                this.slotColumns.getChildren().addAll(this.coreVbox, this.optionalVBox, this.utilityVbox);
            }
        }
    }

    void toggleUtilityImage(final boolean imageVisible) {
        if (imageVisible) {
            if (this.slotColumns.getChildren().contains(this.coreVbox)) {
                this.slotColumns.getChildren().removeAll(this.coreVbox, this.optionalVBox, this.hardpointsVbox);
                this.slotColumns.getChildren().add(0, this.stackPane);
            }
        } else {
            if (this.slotColumns.getChildren().contains(this.stackPane)) {
                this.slotColumns.getChildren().remove(this.stackPane);
                this.slotColumns.getChildren().add(0, this.hardpointsVbox);
                this.slotColumns.getChildren().add(1, this.coreVbox);
                this.slotColumns.getChildren().add(2, this.optionalVBox);
            }
        }
    }

    void drawHardpointLine(final int index) {
        shipImage.setImage(ImageService.getImage("/images/ships/ship/" + APPLICATION_STATE.getShip().getShipType().name().toLowerCase() + "." + APPLICATION_STATE.getShip().getHardpointSlots().get(index).getImageIndex() + ".png"));
        Platform.runLater(() -> {
            final double x = this.shipImage.getWidth() / 1920D * APPLICATION_STATE.getShip().getHardpointSlots().get(index).getX();
            final double y = this.shipImage.getHeight() / 1080D * APPLICATION_STATE.getShip().getHardpointSlots().get(index).getY();
            final double slotHeight = 5.0D;
            final double spacing = 0.0D;
            //Setting the properties of the circle
            this.circleStart.setCenterX(0);
            this.circleStart.setCenterY(ScalingHelper.getPixelDoubleFromEm(slotHeight / 2) + ScalingHelper.getPixelDoubleFromEm(slotHeight + spacing) * index);
            this.circleStart.setRadius(ScalingHelper.getPixelDoubleFromEm(0.2D));
            this.circleStart.setStroke(Color.valueOf("#89D07F"));
            this.circleStart.setFill(Color.valueOf("#89D07F"));

            this.line.setStartX(this.circleStart.getCenterX());
            this.line.setStartY(this.circleStart.getCenterY());
            this.line.setEndX(this.circleStart.getCenterX() + ScalingHelper.getPixelDoubleFromEm(2D));
            this.line.setEndY(this.circleStart.getCenterY());
            this.line.setStrokeWidth(ScalingHelper.getPixelDoubleFromEm(0.1D));
            this.line.setStroke(Color.valueOf("#89D07F"));

            //        this.circleEnd.setCenterX(ScalingHelper.getPixelDoubleFromEm(600D/14D));
            //        this.circleEnd.setCenterY(ScalingHelper.getPixelDoubleFromEm(200D/14D));
            this.circleEnd.setCenterX(x);
            this.circleEnd.setCenterY(y);
            this.circleEnd.setRadius(ScalingHelper.getPixelDoubleFromEm(0.4D));
            this.circleEnd.setStroke(Color.valueOf("#89D07F"));
            this.circleEnd.setFill(Color.valueOf("#89D07F"));

            this.line2.setStartX(this.line.getEndX() + ScalingHelper.getPixelDoubleFromEm(0.1D));
            this.line2.setStartY(this.line.getEndY());
            this.line2.setEndX(this.circleEnd.getCenterX());
            this.line2.setEndY(this.circleEnd.getCenterY());
            this.line2.setStrokeWidth(ScalingHelper.getPixelDoubleFromEm(0.1D));
            this.line2.setStroke(Color.valueOf("#89D07F"));
        });
    }

    void drawUtilityLine(final int index) {
        shipImage.setImage(ImageService.getImage("/images/ships/ship/" + APPLICATION_STATE.getShip().getShipType().name().toLowerCase() + "." + APPLICATION_STATE.getShip().getUtilitySlots().get(index).getImageIndex() + ".png"));
        Platform.runLater(() -> {
            final double x = this.shipImage.getWidth() / 1920D * APPLICATION_STATE.getShip().getUtilitySlots().get(index).getX();
            final double y = this.shipImage.getHeight() / 1080D * APPLICATION_STATE.getShip().getUtilitySlots().get(index).getY();
            final double slotHeight = 5.0D;
            final double spacing = 0.0D;
            final double imageWidth = 81D;
            //Setting the properties of the circle
            this.circleStart.setCenterX(this.shipImage.getWidth() - ScalingHelper.getPixelDoubleFromEm(0.2D) * 1.5);
            this.circleStart.setCenterY(ScalingHelper.getPixelDoubleFromEm(slotHeight / 2) + ScalingHelper.getPixelDoubleFromEm(slotHeight + spacing) * index);
            this.circleStart.setRadius(ScalingHelper.getPixelDoubleFromEm(0.2D));
            this.circleStart.setStroke(Color.valueOf("#89D07F"));
            this.circleStart.setFill(Color.valueOf("#89D07F"));

            this.line.setStartX(this.circleStart.getCenterX());
            this.line.setStartY(this.circleStart.getCenterY());
            this.line.setEndX(this.circleStart.getCenterX() - ScalingHelper.getPixelDoubleFromEm(2D));
            this.line.setEndY(this.circleStart.getCenterY());
            this.line.setStrokeWidth(ScalingHelper.getPixelDoubleFromEm(0.1D));
            this.line.setStroke(Color.valueOf("#89D07F"));

            this.circleEnd.setCenterX(x);
            this.circleEnd.setCenterY(y);
            this.circleEnd.setRadius(ScalingHelper.getPixelDoubleFromEm(0.4D));
            this.circleEnd.setStroke(Color.valueOf("#89D07F"));
            this.circleEnd.setFill(Color.valueOf("#89D07F"));

            this.line2.setStartX(this.line.getEndX() - ScalingHelper.getPixelDoubleFromEm(0.1D));
            this.line2.setStartY(this.line.getEndY());
            this.line2.setEndX(this.circleEnd.getCenterX());
            this.line2.setEndY(this.circleEnd.getCenterY());
            this.line2.setStrokeWidth(ScalingHelper.getPixelDoubleFromEm(0.1D));
            this.line2.setStroke(Color.valueOf("#89D07F"));
        });
    }
}
