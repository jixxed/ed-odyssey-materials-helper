package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.helper.ClipboardHelper;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.WishlistService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.service.ships.ShipMapper;
import nl.jixxed.eliteodysseymaterials.service.ships.ShipService;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableMenuButton;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableMenuItem;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import org.controlsfx.control.PopOver;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ControlsLayer extends AnchorPane implements DestroyableTemplate {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final String FX_FONT_SIZE_DPX = "-fx-font-size: %dpx";

    @Getter
    private ComboBox<ShipConfiguration> shipSelect;
    private MenuButton menuButton;
    private String activeShipUUID;
    private DestroyableMenuButton addAllToWishlist;
    private DestroyableMenuButton addChangedToWishlist;
    private DestroyableResizableImageView shipsHelp;

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

        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> ShipService.getShipConfigurations(commander).getSelectedShipConfiguration().ifPresent(shipConfiguration -> this.shipSelect.getSelectionModel().select(shipConfiguration)));
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
                        "tab.ships.clone", Bindings.createBooleanBinding(() ->  this.shipSelect.getSelectionModel().getSelectedItem() == null || this.shipSelect.getSelectionModel().getSelectedItem().getShipType() == null,this.shipSelect.getSelectionModel().selectedItemProperty()),
                        "tab.ships.rename", this.shipSelect.getSelectionModel().selectedItemProperty().isEqualTo(ShipConfiguration.CURRENT),
                        "tab.ships.copy", this.shipSelect.getSelectionModel().selectedItemProperty().isEqualTo(ShipConfiguration.CURRENT).or(Bindings.createBooleanBinding(() ->  this.shipSelect.getSelectionModel().getSelectedItem() == null || this.shipSelect.getSelectionModel().getSelectedItem().getShipType() == null,this.shipSelect.getSelectionModel().selectedItemProperty())),
                        "tab.ships.delete", this.shipSelect.getSelectionModel().selectedItemProperty().isEqualTo(ShipConfiguration.CURRENT),
                        "tab.ships.reset", this.shipSelect.getSelectionModel().selectedItemProperty().isEqualTo(ShipConfiguration.CURRENT).or(Bindings.createBooleanBinding(() ->  this.shipSelect.getSelectionModel().getSelectedItem() == null || this.shipSelect.getSelectionModel().getSelectedItem().getShipType() == null,this.shipSelect.getSelectionModel().selectedItemProperty()))
                )).build();
        this.menuButton.setFocusTraversable(false);
        final Integer fontSize = FontSize.valueOf(PreferencesService.getPreference(PreferenceConstants.TEXTSIZE, "NORMAL")).getSize();
        applyFontSizingHack(fontSize);
        this.addAllToWishlist = MenuButtonBuilder.builder().withText(LocaleService.getStringBinding("ship.blueprint.add.all.to.wishlist")).build();
        this.addChangedToWishlist = MenuButtonBuilder.builder().withText(LocaleService.getStringBinding("ship.blueprint.add.changed.to.wishlist")).build();
        this.addChangedToWishlist.disableProperty().bind(this.shipSelect.getSelectionModel().selectedItemProperty().isEqualTo(ShipConfiguration.CURRENT));

        this.addAllToWishlist.visibleProperty().bind(this.shipSelect.getSelectionModel().selectedItemProperty().map(s->s.getShipType() != null));
        this.addChangedToWishlist.visibleProperty().bind(this.shipSelect.getSelectionModel().selectedItemProperty().map(s->s.getShipType() != null));
        this.addAllToWishlist.setFocusTraversable(false);
        this.addChangedToWishlist.setFocusTraversable(false);
        this.shipSelect.setFocusTraversable(false);
        HBox dragDropHint = BoxBuilder.builder().withNodes(
                LabelBuilder.builder().withStyleClasses("ship-hint-yellow", "ship-hint-box").withText(LocaleService.getStringBinding("tab.ships.dragdrop.hint")).build(),
                LabelBuilder.builder().withStyleClasses("ship-hint-white", "ship-hint-explain").withText(LocaleService.getStringBinding("tab.ships.dragdrop.hint.explain")).build()
        ).buildHBox();
        HBox lockModeHint = BoxBuilder.builder().withNodes(
                LabelBuilder.builder().withStyleClasses("ship-hint-yellow", "ship-hint-box").withText(LocaleService.getStringBinding("tab.ships.lockmode.hint")).build(),
                LabelBuilder.builder().withStyleClasses("ship-hint-white", "ship-hint-explain").withText(LocaleService.getStringBinding("tab.ships.lockmode.hint.explain")).build()
        ).buildHBox();
        HBox legacyHint = BoxBuilder.builder().withNodes(
                LabelBuilder.builder().withStyleClasses("ship-hint-yellow", "ship-hint-box").withText(LocaleService.getStringBinding("tab.ships.legacy.hint")).build(),
                LabelBuilder.builder().withStyleClasses("ship-hint-white", "ship-hint-explain").withText(LocaleService.getStringBinding("tab.ships.legacy.hint.explain")).build()
        ).buildHBox();
        HBox liveModeHint = BoxBuilder.builder().withNodes(
                LabelBuilder.builder().withStyleClasses("ship-hint-yellow", "ship-hint-box").withText(LocaleService.getStringBinding("tab.ships.livemode.hint")).build(),
                LabelBuilder.builder().withStyleClasses("ship-hint-white", "ship-hint-explain").withText(LocaleService.getStringBinding("tab.ships.livemode.hint.explain")).build()
        ).buildHBox();
        HBox yellowBlueHint = BoxBuilder.builder().withNodes(
                LabelBuilder.builder().withStyleClasses("ship-hint-yellow", "ship-hint-box").withText(LocaleService.getStringBinding("tab.ships.yellowblue.hint")).build(),
                LabelBuilder.builder().withStyleClasses("ship-hint-white", "ship-hint-explain").withText(LocaleService.getStringBinding("tab.ships.yellowblue.hint.explain")).build()
        ).buildHBox();
        final PopOver popOver = new PopOver();
        final VBox contentNode = BoxBuilder.builder().withNodes(dragDropHint, lockModeHint, legacyHint, liveModeHint, yellowBlueHint).buildVBox();
        contentNode.getStyleClass().add("help-popover");
        popOver.setArrowLocation(PopOver.ArrowLocation.TOP_RIGHT);
        popOver.setContentNode(contentNode);
        popOver.setDetachable(false);
        this.shipsHelp = ResizableImageViewBuilder.builder().withOnMouseClicked(event -> {
            popOver.show(this.shipsHelp, event.getScreenX(), event.getScreenY());
        }).withStyleClasses("help-image", "ships-help-image").withImage("/images/other/help.png").build();
        this.shipsHelp.visibleProperty().bind(this.shipSelect.getSelectionModel().selectedItemProperty().map(s->s.getShipType() != null));

        final HBox hBoxShips = BoxBuilder.builder().withStyleClass("shipbuilder-controls-box").withNodes(this.shipSelect, this.menuButton, this.addAllToWishlist, this.addChangedToWishlist, new GrowingRegion(), this.shipsHelp).buildHBox();
        hBoxShips.spacingProperty().bind(ScalingHelper.getPixelDoubleBindingFromEm(0.25));
        this.getChildren().add(hBoxShips);
        hBoxShips.setPickOnBounds(false);
//        AnchorPane.setTopAnchor(hBoxShips,0D);
        AnchorPane.setLeftAnchor(hBoxShips, 0D);
        AnchorPane.setRightAnchor(hBoxShips, 0D);
        APPLICATION_STATE.getPreferredCommander().ifPresent(this::loadCommanderWishlists);
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, HorizonsWishlistSelectedEvent.class, horizonsWishlistSelectedEvent -> {
            APPLICATION_STATE.getPreferredCommander().ifPresent(this::loadCommanderWishlists);
        }));

        register(EventService.addListener(true, this, AfterFontSizeSetEvent.class, fontSizeEvent -> applyFontSizingHack(fontSizeEvent.getFontSize())));

        register(EventService.addListener(true, this, 9, ShipLoadoutEvent.class, event -> {
            EventService.publish(new HorizonsShipSelectedEvent(this.shipSelect.getSelectionModel().getSelectedItem().getUuid()));
        }));

        register(EventService.addListener(true, this, HorizonsShipChangedEvent.class, horizonsShipChangedEvent -> {
            this.activeShipUUID = horizonsShipChangedEvent.getShipUUID();
        }));

        register(EventService.addListener(true, this, 0, HorizonsShipSelectedEvent.class, horizonsShipSelectedEvent -> {
            APPLICATION_STATE.getPreferredCommander()
                    .flatMap(commander -> ShipService.getShipConfigurations(commander).getSelectedShipConfiguration())
                    .ifPresent(configuration -> APPLICATION_STATE.setShip(ShipMapper.toShip(configuration)));
            refreshShipSelect();
            EventService.publish(new HorizonsShipChangedEvent(this.activeShipUUID));
        }));

        register(EventService.addListener(true, this, CommanderSelectedEvent.class, commanderSelectedEvent -> {
            final Optional<ShipConfiguration> shipConfiguration = ShipService.getShipConfigurations(commanderSelectedEvent.getCommander()).getSelectedShipConfiguration();
            this.activeShipUUID = shipConfiguration.map(ShipConfiguration::getUuid).orElse(null);
            refreshShipSelect();
            loadCommanderWishlists(commanderSelectedEvent.getCommander());
            EventService.publish(new HorizonsShipChangedEvent(this.activeShipUUID));
        }));

        register(EventService.addListener(true, this, CommanderAllListedEvent.class, commanderAllListedEvent -> {
            refreshShipSelect();
            APPLICATION_STATE.getPreferredCommander().ifPresent(this::loadCommanderWishlists);
        }));

        register(EventService.addListener(true, this, ImportResultEvent.class, importResultEvent -> {
            if (importResultEvent.getResult().getResultType().equals(ImportResult.ResultType.SUCCESS_HORIZONS_SHIP) || importResultEvent.getResult().getResultType().equals(ImportResult.ResultType.SUCCESS_SLEF)) {
                refreshShipSelect();
                EventService.publish(new HorizonsShipChangedEvent(this.activeShipUUID));
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

    private HorizonsWishlists loadCommanderWishlists(final Commander commander) {
        final HorizonsWishlists wishlists = WishlistService.getHorizonsWishlists(commander);
        if (this.addAllToWishlist != null && this.addChangedToWishlist != null) {
            this.addAllToWishlist.getItems().stream().map(DestroyableMenuItem.class::cast).forEach(DestroyableMenuItem::destroy);
            this.addChangedToWishlist.getItems().stream().map(DestroyableMenuItem.class::cast).forEach(DestroyableMenuItem::destroy);
            this.addChangedToWishlist.getItems().clear();
            this.addAllToWishlist.getItems().clear();
            final List<DestroyableMenuItem> allMenuItems = getMenuItems(commander, wishlists, true);
            final List<DestroyableMenuItem> changedlMenuItems = getMenuItems(commander, wishlists, false);
            this.addAllToWishlist.getItems().addAll(allMenuItems);
            this.addChangedToWishlist.getItems().addAll(changedlMenuItems);
            final DestroyableMenuItem allCreateNew = createNewMenuItem(commander, true);
            final DestroyableMenuItem changedCreateNew = createNewMenuItem(commander, false);
            this.addAllToWishlist.getItems().add(allCreateNew);
            this.addChangedToWishlist.getItems().add(changedCreateNew);
        }
        return wishlists;
    }

    private DestroyableMenuItem createNewMenuItem(Commander commander, boolean all) {
        final DestroyableMenuItem createNew = new DestroyableMenuItem();
        createNew.setOnAction(event -> {
            final List<HorizonsWishlistBlueprint> wishlistBlueprints = getRequiredWishlistRecipes(all);
            if (wishlistBlueprints.isEmpty()) {
                NotificationService.showWarning(NotificationType.ERROR, "Can't create wishlist", "No items to add");
            } else {
                final HorizonsWishlists horizonsWishlists = WishlistService.getHorizonsWishlists(commander);
                final HorizonsWishlist newWishlist = horizonsWishlists.createWishlist(this.shipSelect.getSelectionModel().getSelectedItem().toString().replace(" (read only)", ""));
                WishlistService.saveHorizonsWishlists(commander, horizonsWishlists);
                EventService.publish(new HorizonsWishlistCreatedEvent());
                EventService.publish(new HorizonsWishlistBlueprintEvent(commander, newWishlist.getUuid(), wishlistBlueprints, Action.ADDED));
            }
        });
        createNew.textProperty().bind(LocaleService.getStringBinding("ship.create.new.wishlist"));
        createNew.getStyleClass().add("ships-wishlist-create-new");
        return createNew;
    }

    private List<DestroyableMenuItem> getMenuItems(Commander commander, HorizonsWishlists wishlists, boolean all) {
        return wishlists.getAllWishlists().stream().filter(wishlist -> wishlist != HorizonsWishlist.ALL).sorted(Comparator.comparing(HorizonsWishlist::getName)).map(wishlist -> {
            final DestroyableMenuItem menuItem = new DestroyableMenuItem();
            menuItem.setOnAction(event -> {
                final List<HorizonsWishlistBlueprint> wishlistBlueprints = getRequiredWishlistRecipes(all);
                if (wishlistBlueprints.isEmpty()) {
                    NotificationService.showWarning(NotificationType.ERROR, "Can't add to wishlist", "No items to add");
                } else {
                    EventService.publish(new HorizonsWishlistBlueprintEvent(commander, wishlist.getUuid(), wishlistBlueprints, Action.ADDED));
                }
            });
            menuItem.setText(wishlist.getName());
            return menuItem;
        }).toList();
    }

    private List<HorizonsWishlistBlueprint> getRequiredWishlistRecipes(boolean all) {
        final List<HorizonsWishlistBlueprint> wishlistBlueprints = new ArrayList<>();
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
            ShipService.getShipConfigurations(commander).getSelectedShipConfiguration().ifPresent(shipConfiguration -> {
                Stream.concat(Stream.concat(Stream.concat(shipConfiguration.getUtilitySlots().stream(), shipConfiguration.getCoreSlots().stream()), shipConfiguration.getOptionalSlots().stream()), shipConfiguration.getHardpointSlots().stream()).filter(slot -> !slot.isLegacy()).forEach(slot -> {
                    final HorizonsBlueprintName name = ShipModule.getModule(slot.getId()).getName().getPrimary();
                    slot.getModification().stream().filter(modification -> !modification.getType().isPreEngineered()).forEach(modification -> {

                        //get grade
                        final HorizonsBlueprintGrade grade = modification.getGrade();
                        //get grades before and including grade
                        final Map<HorizonsBlueprintGrade, Double> gradePercentageToComplete = new EnumMap<>(HorizonsBlueprintGrade.class);
                        if (all || slot.getOldModule() == null || (slot.getOldModule().getModification().stream().noneMatch(oldMod -> modification.getType().equals(oldMod.getType())))) {
                            addAllUpTo(modification, name, modification.getType(), grade, gradePercentageToComplete);
                        } else {
                            final ShipConfigurationModification oldModification = slot.getOldModule().getModification().stream().filter(oldMod -> oldMod.getType().equals(modification.getType())).findFirst().orElseThrow(IllegalArgumentException::new);
                            final HorizonsBlueprintGrade oldGrade = oldModification.getGrade();
                            if (oldGrade.getGrade() < grade.getGrade() || (oldGrade.getGrade() == grade.getGrade() && oldModification.getPercentComplete().doubleValue() <= modification.getPercentComplete().doubleValue())) {
                                final Set<HorizonsBlueprintGrade> blueprintGrades = HorizonsBlueprintConstants.getBlueprintGrades(name, modification.getType())
                                        .stream()
                                        .filter(gradeToAdd -> gradeToAdd.getGrade() >= oldGrade.getGrade() && gradeToAdd.getGrade() <= grade.getGrade())
                                        .collect(Collectors.toSet());

                                //reduce number of rolls based on percentage completed
                                blueprintGrades.forEach(horizonsBlueprintGrade -> {
                                    double percentageToComplete;
                                    if (horizonsBlueprintGrade.equals(oldGrade) && horizonsBlueprintGrade.equals(grade)) {
                                        percentageToComplete = modification.getPercentComplete().doubleValue() - oldModification.getPercentComplete().doubleValue();
                                    } else if (horizonsBlueprintGrade.equals(grade)) {
                                        percentageToComplete = modification.getPercentComplete().doubleValue();
                                    } else if (horizonsBlueprintGrade.equals(oldGrade)) {
                                        percentageToComplete = 1D - oldModification.getPercentComplete().doubleValue();
                                    } else {
                                        percentageToComplete = 1D;
                                    }
                                    if(percentageToComplete > 0D) {
                                        gradePercentageToComplete.put(horizonsBlueprintGrade, percentageToComplete);
                                    }
                                });

                            } else {
                                addAllUpTo(modification, name, modification.getType(), grade, gradePercentageToComplete);
                            }

                            //cases
                            //oldgrade is lower -> upgrade from oldgrade to new grade
                            //no oldmodule or oldgrade is higher -> upgrade from 0 to new grade
                        }
                        if (!gradePercentageToComplete.values().stream().allMatch(rolls -> rolls.equals(0))) {
                            final HorizonsModuleWishlistBlueprint bp = new HorizonsModuleWishlistBlueprint(modification.getType(), gradePercentageToComplete);
                            bp.setRecipeName(name);
                            bp.setVisible(true);
                            wishlistBlueprints.add(bp);
                        }

                    });
                    slot.getExperimentalEffect().forEach(effect -> {
                        if (all || slot.getOldModule() == null || ((slot.getOldModule().getExperimentalEffect().stream().noneMatch(oldEffect -> oldEffect.getType().equals(effect.getType())) || slot.getOldModule().getModification().stream().noneMatch(oldMod -> slot.getModification().stream().anyMatch(modification -> oldMod.getType().equals(modification.getType())))))) {
                            final HorizonsExperimentalWishlistBlueprint bp = new HorizonsExperimentalWishlistBlueprint(effect.getType());
                            bp.setRecipeName(name);
                            bp.setVisible(true);
                            wishlistBlueprints.add(bp);
                        }
                    });
                });
            });
        });
        return wishlistBlueprints;
    }

    private static void addAllUpTo(ShipConfigurationModification modification, HorizonsBlueprintName name, HorizonsBlueprintType type, HorizonsBlueprintGrade grade, Map<HorizonsBlueprintGrade, Double> gradePercentageToComplete) {
        final Set<HorizonsBlueprintGrade> blueprintGrades = HorizonsBlueprintConstants.getBlueprintGrades(name, modification.getType())
                .stream()
                .filter(gradeToAdd -> gradeToAdd.getGrade() <= grade.getGrade())
                .collect(Collectors.toSet());

        //reduce number of rolls based on percentage for final grade
        blueprintGrades.forEach(horizonsBlueprintGrade -> {
            final double percentageToComplete = (horizonsBlueprintGrade.equals(grade)) ? modification.getPercentComplete().doubleValue() : 1D;
            gradePercentageToComplete.put(horizonsBlueprintGrade, percentageToComplete);
        });
    }
//
//    private static int getGradeRolls(HorizonsBlueprintType type, final HorizonsBlueprintGrade horizonsBlueprintGrade) {
//        return type.getGradeRolls(horizonsBlueprintGrade);
//    }
}
