package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.helper.ClipboardHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.WishlistService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.service.ships.ShipMapper;
import nl.jixxed.eliteodysseymaterials.service.ships.ShipService;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;
import org.controlsfx.control.PopOver;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ControlsLayer extends DestroyableAnchorPane implements DestroyableEventTemplate {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final String FX_FONT_SIZE_DPX = "-fx-font-size: %dpx";

    @Getter
    private DestroyableComboBox<ShipConfiguration> shipSelect;
    private DestroyableMenuButton menuButton;
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
        this.getStyleClass().add("controls");
        final Set<ShipConfiguration> items = APPLICATION_STATE.getPreferredCommander()
                .map(commander -> ShipService.getShipConfigurations(commander).getAllShipConfigurations())
                .orElse(Collections.emptySet());
        this.shipSelect = ComboBoxBuilder.builder(ShipConfiguration.class)
                .withStyleClass("ship-select")
                .withFocusTraversable(false)
                .withItemsProperty(FXCollections.observableArrayList(items.stream().sorted(Comparator.comparing(ShipConfiguration::getName)).toList()))
                .withValueChangeListener((_, _, newValue) -> {
                    if (newValue != null) {
                        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                            this.activeShipUUID = newValue.getUuid();
                            ShipService.selectShipConfiguration(this.activeShipUUID, commander);
                            EventService.publish(new HorizonsShipSelectedEvent(this.activeShipUUID));
                        });
                    }
                })
                .build();

        APPLICATION_STATE.getPreferredCommander()
                .flatMap(commander -> ShipService.getShipConfigurations(commander).getSelectedShipConfiguration())
                .ifPresent(shipConfiguration -> this.shipSelect.getSelectionModel().select(shipConfiguration));
        this.menuButton = MenuButtonBuilder.builder()
                .withText("tab.ships.options")
                .withMenuItems(
                        Map.of("tab.ships.clone", getCloneHandler(),
                                "tab.ships.create", getCreateHandler(),
                                "tab.ships.rename", getRenameHandler(),
                                "tab.ships.delete", getDeleteHandler(),
                                "tab.ships.reset", getResetHandler(),
                                "tab.ships.copy", getCopyHandler()
                        ),
                        Map.of(
                                "tab.ships.clone", Bindings.createBooleanBinding(() -> this.shipSelect.getSelectionModel().getSelectedItem() == null || this.shipSelect.getSelectionModel().getSelectedItem().getShipType() == null, this.shipSelect.getSelectionModel().selectedItemProperty()),
                                "tab.ships.rename", this.shipSelect.getSelectionModel().selectedItemProperty().isEqualTo(ShipConfiguration.CURRENT),
                                "tab.ships.copy", this.shipSelect.getSelectionModel().selectedItemProperty().isEqualTo(ShipConfiguration.CURRENT).or(Bindings.createBooleanBinding(() -> this.shipSelect.getSelectionModel().getSelectedItem() == null || this.shipSelect.getSelectionModel().getSelectedItem().getShipType() == null, this.shipSelect.getSelectionModel().selectedItemProperty())),
                                "tab.ships.delete", this.shipSelect.getSelectionModel().selectedItemProperty().isEqualTo(ShipConfiguration.CURRENT),
                                "tab.ships.reset", this.shipSelect.getSelectionModel().selectedItemProperty().isEqualTo(ShipConfiguration.CURRENT).or(Bindings.createBooleanBinding(() -> this.shipSelect.getSelectionModel().getSelectedItem() == null || this.shipSelect.getSelectionModel().getSelectedItem().getShipType() == null, this.shipSelect.getSelectionModel().selectedItemProperty()))
                        ))
                .build();
        this.menuButton.setFocusTraversable(false);

        final Integer fontSize = FontSize.valueOf(PreferencesService.getPreference(PreferenceConstants.TEXTSIZE, "NORMAL")).getSize();
        applyFontSizingHack(fontSize);

        this.addAllToWishlist = MenuButtonBuilder.builder()
                .withText("ship.blueprint.add.all.to.wishlist")
                .withVisibilityProperty(this.shipSelect.getSelectionModel().selectedItemProperty().map(s -> s.getShipType() != null))
                .withFocusTraversable(false)
                .build();

        this.addChangedToWishlist = MenuButtonBuilder.builder()
                .withText("ship.blueprint.add.changed.to.wishlist")
                .withDisableProperty(this.shipSelect.getSelectionModel().selectedItemProperty().isEqualTo(ShipConfiguration.CURRENT))
                .withVisibilityProperty(this.shipSelect.getSelectionModel().selectedItemProperty().map(s -> s.getShipType() != null))
                .withFocusTraversable(false)
                .build();

        this.shipsHelp = ResizableImageViewBuilder.builder()
                .withStyleClasses("help-image")
                .withImage("/images/other/help.png")
                .withOnMouseClicked(this::showHelp)
                .withVisibilityProperty(this.shipSelect.getSelectionModel().selectedItemProperty().map(s -> s.getShipType() != null))
                .build();

        final DestroyableRegion spacer = RegionBuilder.builder()
                .withStyleClass("spacer")
                .build();

        final DestroyableHBox shipsMenu = BoxBuilder.builder()
                .withStyleClass("ships-menu")
                .withNodes(this.shipSelect, this.menuButton, this.addAllToWishlist, this.addChangedToWishlist, new GrowingRegion(), this.shipsHelp, spacer)
                .buildHBox();
        this.getNodes().add(shipsMenu);
        shipsMenu.setPickOnBounds(false);

        AnchorPane.setLeftAnchor(shipsMenu, 0D);
        AnchorPane.setRightAnchor(shipsMenu, 0D);
        APPLICATION_STATE.getPreferredCommander().ifPresent(this::loadCommanderWishlists);
    }

    private void showHelp(MouseEvent event) {
        DestroyableHBox dragDropHint = getHint("dragdrop");
        DestroyableHBox lockModeHint = getHint("lockmode");
        DestroyableHBox legacyHint = getHint("legacy");
        DestroyableHBox liveModeHint = getHint("livemode");
        DestroyableHBox yellowBlueHint = getHint("yellowblue");
        final DestroyableVBox contentNode = BoxBuilder.builder()
                .withStyleClass("help-popover")
                .withNodes(dragDropHint, lockModeHint, legacyHint, liveModeHint, yellowBlueHint)
                .buildVBox();
        final DestroyablePopOver popOver = PopOverBuilder.builder()
                .withStyleClass("horizons-shipbuilder-help-popover")
                .withContent(contentNode)
                .withDetachable(false)
                .withHeaderAlwaysVisible(false)
                .withCornerRadius(0)
                .withArrowLocation(PopOver.ArrowLocation.TOP_RIGHT)
                .build();
        popOver.show(this.shipsHelp, event.getScreenX(), event.getScreenY());
    }

    private static DestroyableHBox getHint(String hint) {
        return BoxBuilder.builder()
                .withStyleClass("hint-line")
                .withNodes(
                        LabelBuilder.builder()
                                .withStyleClass("hint-color")
                                .withText("tab.ships." + hint + ".hint")
                                .build(),
                        LabelBuilder.builder()
                                .withStyleClass("ship-text")
                                .withText("tab.ships." + hint + ".hint.explain")
                                .build()
                ).buildHBox();
    }

    private EventHandler<ActionEvent> getCopyHandler() {
        return _ -> {
            copyShipToClipboard();
            NotificationService.showInformation(NotificationType.COPY, LocaleService.LocaleString.of("notification.clipboard.title"), LocaleService.LocaleString.of("notification.clipboard.ship.copied.text"));
        };
    }

    private EventHandler<ActionEvent> getResetHandler() {
        return _ -> {
            final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(LocaleService.getLocalizedStringForCurrentLocale("tab.ships.reset.confirm.title"));
            alert.setHeaderText(LocaleService.getLocalizedStringForCurrentLocale("tab.ships.reset.confirm.header"));
            alert.setContentText(LocaleService.getLocalizedStringForCurrentLocale("tab.ships.reset.confirm.content"));

            final Optional<ButtonType> result = alert.showAndWait();
            result.ifPresent(buttonType -> {
                if (buttonType == ButtonType.OK) {
                    APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                        final ShipConfigurations shipConfigurations = ShipService.getShipConfigurations(commander);
                        shipConfigurations.resetShipConfiguration(this.activeShipUUID);
                        ShipService.saveShipConfigurations(commander, shipConfigurations);
                        EventService.publish(new HorizonsShipSelectedEvent(this.activeShipUUID));
                        refreshShipSelect();
                    });
                }
            });
        };
    }

    private EventHandler<ActionEvent> getDeleteHandler() {
        return _ -> {
            final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(LocaleService.getLocalizedStringForCurrentLocale("tab.ships.delete.confirm.title"));
            alert.setHeaderText(LocaleService.getLocalizedStringForCurrentLocale("tab.ships.delete.confirm.header"));
            alert.setContentText(LocaleService.getLocalizedStringForCurrentLocale("tab.ships.delete.confirm.content"));

            final Optional<ButtonType> result = alert.showAndWait();
            result.ifPresent(buttonType -> {
                if (buttonType == ButtonType.OK) {
                    APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                        ShipService.deleteShipConfiguration(this.activeShipUUID, commander);
                        Platform.runLater(this::refreshShipSelect);
                    });
                }
            });
        };
    }

    private EventHandler<ActionEvent> getRenameHandler() {
        return _ -> showInputPopOver("tab.ships.rename", "tab.ships.rename.prompt",
                (commander, input) -> {
                    final ShipConfigurations shipConfigurations = ShipService.getShipConfigurations(commander);
                    shipConfigurations.renameShipConfiguration(this.activeShipUUID, input);
                    ShipService.saveShipConfigurations(commander, shipConfigurations);
                });
    }

    private EventHandler<ActionEvent> getCreateHandler() {
        return _ -> showInputPopOver("tab.ships.create", "tab.ships.create.prompt",
                (commander, input) -> {
                    final ShipConfigurations shipConfigurations = ShipService.getShipConfigurations(commander);
                    shipConfigurations.createShipConfiguration(input);
                    ShipService.saveShipConfigurations(commander, shipConfigurations);
                });
    }

    private void showInputPopOver(String buttonLocaleKey, String textfieldPromptLocaleKey, BiConsumer<Commander, String> inputHandler) {
        final DestroyableTextField textField = TextFieldBuilder.builder()
                .withStyleClasses("root", "ships-newname")
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
        button.setOnAction(_ -> APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
            inputHandler.accept(commander, textField.getText());
            textField.clear();
            refreshShipSelect();
            popOver.hide();
        }));
        textField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                button.fire();
            }
        });
    }

    private EventHandler<ActionEvent> getCloneHandler() {
        return _ ->
                APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                    final ShipConfigurations shipConfigurations = ShipService.getShipConfigurations(commander);
                    final ShipConfiguration shipConfiguration = this.shipSelect.getSelectionModel().getSelectedItem().cloneShipConfiguration();
                    shipConfigurations.addShipConfiguration(shipConfiguration);
                    shipConfigurations.setSelectedShipConfigurationUUID(shipConfiguration.getUuid());
                    ShipService.saveShipConfigurations(commander, shipConfigurations);
                    refreshShipSelect();
                });
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, HorizonsWishlistSelectedEvent.class, _ ->
                APPLICATION_STATE.getPreferredCommander().ifPresent(this::loadCommanderWishlists)));
        register(EventService.addListener(true, this, AfterFontSizeSetEvent.class, fontSizeEvent -> applyFontSizingHack(fontSizeEvent.getFontSize())));
        register(EventService.addListener(true, this, 9, ShipLoadoutEvent.class, _ -> EventService.publish(new HorizonsShipSelectedEvent(this.shipSelect.getSelectionModel().getSelectedItem().getUuid()))));
        register(EventService.addListener(true, this, HorizonsShipChangedEvent.class, horizonsShipChangedEvent -> this.activeShipUUID = horizonsShipChangedEvent.getShipUUID()));
        register(EventService.addListener(true, this, 0, HorizonsShipSelectedEvent.class, _ -> {
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
        register(EventService.addListener(true, this, CommanderAllListedEvent.class, _ -> {
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
        ClipboardHelper.copyToClipboard(ClipboardHelper.createClipboardShipConfiguration());
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
        return MenuItemBuilder.builder()
                .withStyleClass("ships-wishlist-create-new")
                .withText("ship.create.new.wishlist")
                .withOnAction(_ -> {
                    final List<HorizonsWishlistBlueprint> wishlistBlueprints = getRequiredWishlistRecipes(all);
                    if (wishlistBlueprints.isEmpty()) {
                        NotificationService.showWarning(NotificationType.ERROR, LocaleService.LocaleString.of("notification.shipbuilder.error.wishlist.title"), LocaleService.LocaleString.of("notification.shipbuilder.error.wishlist.text"));
                    } else {
                        final HorizonsWishlists horizonsWishlists = WishlistService.getHorizonsWishlists(commander);
                        final HorizonsWishlist newWishlist = horizonsWishlists.createWishlist(this.shipSelect.getSelectionModel().getSelectedItem().toString().replace(" (read only)", ""));
                        WishlistService.saveHorizonsWishlists(commander, horizonsWishlists);
                        EventService.publish(new HorizonsWishlistCreatedEvent());
                        EventService.publish(new HorizonsWishlistBlueprintEvent(commander, newWishlist.getUuid(), wishlistBlueprints, Action.ADDED));
                    }
                })
                .build();
    }

    private List<DestroyableMenuItem> getMenuItems(Commander commander, HorizonsWishlists wishlists, boolean all) {
        return wishlists.getAllWishlists().stream()
                .filter(wishlist -> wishlist != HorizonsWishlist.ALL)
                .sorted(Comparator.comparing(HorizonsWishlist::getName))
                .map(wishlist -> MenuItemBuilder.builder()
                        .withNonLocalizedText(wishlist.getName())
                        .withOnAction(_ -> {
                            final List<HorizonsWishlistBlueprint> wishlistBlueprints = getRequiredWishlistRecipes(all);
                            if (wishlistBlueprints.isEmpty()) {
                                NotificationService.showWarning(NotificationType.ERROR, LocaleService.LocaleString.of("notification.shipbuilder.error.wishlist.title"), LocaleService.LocaleString.of("notification.shipbuilder.error.wishlist.text"));
                            } else {
                                EventService.publish(new HorizonsWishlistBlueprintEvent(commander, wishlist.getUuid(), wishlistBlueprints, Action.ADDED));
                            }
                        })
                        .build())
                .toList();
    }

    private List<HorizonsWishlistBlueprint> getRequiredWishlistRecipes(boolean all) {
        final List<HorizonsWishlistBlueprint> wishlistBlueprints = new ArrayList<>();
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander ->
                ShipService.getShipConfigurations(commander).getSelectedShipConfiguration().ifPresent(shipConfiguration -> Stream
                        .concat(
                                Stream.concat(shipConfiguration.getUtilitySlots().stream(), shipConfiguration.getCoreSlots().stream()),
                                Stream.concat(shipConfiguration.getOptionalSlots().stream(), shipConfiguration.getHardpointSlots().stream()))
                        .filter(slot -> !slot.isLegacy())
                        .forEach(slot -> {
                            final HorizonsBlueprintName name = ShipModule.getModule(slot.getId()).getName().getPrimary();
                            wishlistBlueprints.addAll(getModuleBlueprints(all, slot, name));
                            wishlistBlueprints.addAll(getExperimentalEffectBlueprints(all, slot, name));
                        })));
        return wishlistBlueprints;
    }

    private static List<HorizonsWishlistBlueprint> getExperimentalEffectBlueprints(boolean all, ShipConfigurationSlot slot, HorizonsBlueprintName name) {
        return slot.getExperimentalEffect().stream()
                .map(effect -> {
                    if (all || didNotHaveModule(slot) || hasDifferentExperimentalEffect(slot, effect) || hasDifferentModification(slot)) {
                        final HorizonsWishlistBlueprint bp = new HorizonsExperimentalWishlistBlueprint(effect.getType());
                        bp.setRecipeName(name);
                        bp.setVisible(true);
                        return bp;
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .toList();
    }

    private static boolean hasDifferentModification(ShipConfigurationSlot slot) {
        return slot.getOldModule().getModification().stream()
                .noneMatch(oldMod -> slot.getModification().stream()
                        .anyMatch(modification -> oldMod.getType().equals(modification.getType())));
    }

    private static boolean hasDifferentModification(ShipConfigurationSlot slot, ShipConfigurationModification modification) {
        return slot.getOldModule().getModification().stream()
                .noneMatch(oldMod -> modification.getType().equals(oldMod.getType()));
    }

    private static boolean hasDifferentExperimentalEffect(ShipConfigurationSlot slot, ShipConfigurationExperimentalEffect effect) {
        return slot.getOldModule().getExperimentalEffect().stream()
                .noneMatch(oldEffect -> oldEffect.getType().equals(effect.getType()));
    }

    private static List<HorizonsWishlistBlueprint> getModuleBlueprints(boolean all, ShipConfigurationSlot slot, HorizonsBlueprintName name) {
        final List<HorizonsWishlistBlueprint> wishlistBlueprints = new ArrayList<>();
        slot.getModification().stream()
                .filter(modification -> !modification.getType().isPreEngineered())
                .forEach(modification -> {
                    //get grade
                    final HorizonsBlueprintGrade grade = modification.getGrade();
                    //get grades before and including grade
                    final Map<HorizonsBlueprintGrade, Double> gradePercentageToComplete = new EnumMap<>(HorizonsBlueprintGrade.class);
                    if (all || didNotHaveModule(slot) || hasDifferentModification(slot, modification)) {
                        gradePercentageToComplete.putAll(getPercentagesUpTo(modification, name, grade));
                    } else {
                        final ShipConfigurationModification oldModification = slot.getOldModule().getModification().stream().filter(oldMod -> oldMod.getType().equals(modification.getType())).findFirst().orElseThrow(IllegalArgumentException::new);
                        final HorizonsBlueprintGrade oldGrade = oldModification.getGrade();
                        if (hasHigherGrade(oldGrade, grade) || (hasSameGrade(oldGrade, grade) && hasHigherCompletion(modification, oldModification))) {
                            gradePercentageToComplete.putAll(getPercentagesToComplete(name, modification, oldGrade, grade, oldModification));
                        } else {
                            gradePercentageToComplete.putAll(getPercentagesUpTo(modification, name, grade));
                        }

                        //cases
                        //oldgrade is lower -> upgrade from oldgrade to new grade
                        //no oldmodule or oldgrade is higher -> upgrade from 0 to new grade
                    }
                    if (!gradePercentageToComplete.values().stream().allMatch(rolls -> rolls.equals(0D))) {
                        final HorizonsModuleWishlistBlueprint bp = new HorizonsModuleWishlistBlueprint(modification.getType(), gradePercentageToComplete);
                        bp.setRecipeName(name);
                        bp.setVisible(true);
                        wishlistBlueprints.add(bp);
                    }

                });
        return wishlistBlueprints;
    }

    private static Map<HorizonsBlueprintGrade, Double> getPercentagesToComplete(HorizonsBlueprintName name, ShipConfigurationModification modification, HorizonsBlueprintGrade oldGrade, HorizonsBlueprintGrade grade, ShipConfigurationModification oldModification) {
        return HorizonsBlueprintConstants.getBlueprintGrades(name, modification.getType())
                .stream()
                .filter(gradeToAdd -> gradeToAdd.getGrade() >= oldGrade.getGrade() && gradeToAdd.getGrade() <= grade.getGrade())
                .map(horizonsBlueprintGrade -> {
                    double percentageToComplete = getPercentageToComplete(modification, horizonsBlueprintGrade, oldGrade, grade, oldModification);
                    if (percentageToComplete > 0D) {
                        return Map.entry(horizonsBlueprintGrade, percentageToComplete);
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static double getPercentageToComplete(ShipConfigurationModification modification, HorizonsBlueprintGrade horizonsBlueprintGrade, HorizonsBlueprintGrade oldGrade, HorizonsBlueprintGrade grade, ShipConfigurationModification oldModification) {
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
        return percentageToComplete;
    }

    private static boolean hasHigherCompletion(ShipConfigurationModification modification, ShipConfigurationModification oldModification) {
        return oldModification.getPercentComplete().doubleValue() <= modification.getPercentComplete().doubleValue();
    }

    private static boolean hasSameGrade(HorizonsBlueprintGrade oldGrade, HorizonsBlueprintGrade grade) {
        return oldGrade.getGrade() == grade.getGrade();
    }

    private static boolean hasHigherGrade(HorizonsBlueprintGrade oldGrade, HorizonsBlueprintGrade grade) {
        return oldGrade.getGrade() < grade.getGrade();
    }

    private static boolean didNotHaveModule(ShipConfigurationSlot slot) {
        return slot.getOldModule() == null;
    }

    @SuppressWarnings("java:S1640")
    private static Map<HorizonsBlueprintGrade, Double> getPercentagesUpTo(ShipConfigurationModification modification, HorizonsBlueprintName name, HorizonsBlueprintGrade grade) {
        final Map<HorizonsBlueprintGrade, Double> gradePercentageToComplete = new HashMap<>();

        final Set<HorizonsBlueprintGrade> blueprintGrades = HorizonsBlueprintConstants.getBlueprintGrades(name, modification.getType())
                .stream()
                .filter(gradeToAdd -> gradeToAdd.getGrade() <= grade.getGrade())
                .collect(Collectors.toSet());

        //reduce number of rolls based on percentage for final grade
        blueprintGrades.forEach(horizonsBlueprintGrade -> {
            final double percentageToComplete = (horizonsBlueprintGrade.equals(grade)) ? modification.getPercentComplete().doubleValue() : 1D;
            gradePercentageToComplete.put(horizonsBlueprintGrade, percentageToComplete);
        });
        return gradePercentageToComplete;
    }
}
