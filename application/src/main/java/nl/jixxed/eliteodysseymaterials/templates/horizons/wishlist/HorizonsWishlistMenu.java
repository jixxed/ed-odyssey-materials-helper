package nl.jixxed.eliteodysseymaterials.templates.horizons.wishlist;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import nl.jixxed.eliteodysseymaterials.FXApplication;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.export.CsvExporter;
import nl.jixxed.eliteodysseymaterials.export.TextExporter;
import nl.jixxed.eliteodysseymaterials.export.XlsExporter;
import nl.jixxed.eliteodysseymaterials.helper.ClipboardHelper;
import nl.jixxed.eliteodysseymaterials.helper.QuadFunction;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.WishlistService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.controlsfx.control.PopOver;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class HorizonsWishlistMenu extends DestroyableHBox implements DestroyableEventTemplate {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private DestroyableComboBox<HorizonsWishlist> wishlistSelect;
    private DestroyableMenuButton menuButton;
    private String activeWishlistUUID;
    private List<PathItem<HorizonsBlueprintName>> shortestPath;
    private HorizonsWishlistMaterialSearch currentSearch = new HorizonsWishlistMaterialSearch("", HorizonsWishlistMaterialSort.ALPHABETICAL, WishlistMaterialGrouping.CATEGORY);

    public HorizonsWishlistMenu() {
        final HorizonsWishlistMaterialSort materialSort = HorizonsWishlistMaterialSort.valueOf(PreferencesService.getPreference("search.horizons.wishlist.sort", "ALPHABETICAL"));
        final WishlistMaterialGrouping filter = WishlistMaterialGrouping.valueOf(PreferencesService.getPreference("search.horizons.wishlist.grouping", "CATEGORY"));
        currentSearch.setWishlistMaterialGrouping(filter);
        currentSearch.setWishlistMaterialSort(materialSort);

        initComponents();
        initEventHandling();
    }


    @Override
    public void initComponents() {
        final Set<HorizonsWishlist> items = APPLICATION_STATE.getPreferredCommander()
                .map(commander -> WishlistService.getHorizonsWishlists(commander).getAllWishlists())
                .orElse(Collections.emptySet());
        this.wishlistSelect = ComboBoxBuilder.builder(HorizonsWishlist.class)
                .withStyleClass("wishlist-select")
                .withItemsProperty(FXCollections.observableArrayList(items.stream().sorted(Comparator.comparing(HorizonsWishlist::getName)).toList()))
                .withValueChangeListener((_, _, newValue) -> {
                    if (newValue != null) {
                        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                            this.activeWishlistUUID = newValue.getUuid();
                            WishlistService.selectHorizonsWishlist(this.activeWishlistUUID, commander);
                            EventService.publish(new HorizonsWishlistSelectedEvent(this.activeWishlistUUID));
                        });
                    }
                })
                .build();
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> this.wishlistSelect.getSelectionModel().select(WishlistService.getHorizonsWishlists(commander).getSelectedWishlist()));
        this.menuButton = MenuButtonBuilder.builder()
                .withText("tab.wishlist.options")
                .withMenuItems(Map.of(
                                "tab.wishlist.create", getCreateHandler(),
                                "tab.wishlist.rename", getRenameHandler(),
                                "tab.wishlist.delete", getDeleteHandler(),
                                "tab.wishlist.copy", getCopyHandler(),
                                "tab.wishlist.export", getExportHandler()
                        ),
                        Map.of(
                                "tab.wishlist.rename", this.wishlistSelect.getSelectionModel().selectedItemProperty().isEqualTo(HorizonsWishlist.ALL),
                                "tab.wishlist.copy", this.wishlistSelect.getSelectionModel().selectedItemProperty().isEqualTo(HorizonsWishlist.ALL),
                                "tab.wishlist.delete", this.wishlistSelect.getSelectionModel().selectedItemProperty().isEqualTo(HorizonsWishlist.ALL)
                        ))
                .build();
        this.menuButton.setFocusTraversable(false);


        DestroyableButton shipBuilderButton = ButtonBuilder.builder()
                .withText("horizons.wishlist.shipbuilder")
                .withOnAction(_ -> EventService.publish(new HorizonsWishlistOpenShipBuilderEvent()))
                .build();
        DestroyableButton edsyButton = ButtonBuilder.builder()
                .withText("horizons.wishlist.edsy")
                .withOnAction(_ -> FXApplication.getInstance().getHostServices().showDocument("https://edsy.org"))
                .build();
        DestroyableButton coriolisButton = ButtonBuilder.builder()
                .withText("horizons.wishlist.coriolis")
                .withOnAction(_ -> FXApplication.getInstance().getHostServices().showDocument("https://coriolis.io"))
                .build();
        Tooltip.install(edsyButton, TooltipBuilder.builder()
                .withShowDelay(Duration.millis(100D))
                .withText("horizons.wishlist.edsy.tooltip")
                .build());
        Tooltip.install(coriolisButton, TooltipBuilder.builder()
                .withShowDelay(Duration.millis(100D))
                .withText("horizons.wishlist.coriolis.tooltip")
                .build());

        final Integer fontSize = FontSize.valueOf(PreferencesService.getPreference(PreferenceConstants.TEXTSIZE, "NORMAL")).getSize();
        applyFontSizingHack(fontSize);
        this.getNodes().addAll(this.wishlistSelect, this.menuButton, shipBuilderButton, edsyButton, coriolisButton);
    }

    private EventHandler<ActionEvent> getExportHandler() {
        return _ ->
                EventService.publish(new SaveWishlistEvent(
                        getTextExporter(),
                        getCsvExporter(),
                        getXlsExporter()
                ));
    }

    private EventHandler<ActionEvent> getCopyHandler() {
        return _ -> {
            copyWishListToClipboard();
            NotificationService.showInformation(NotificationType.COPY, LocaleService.LocaleString.of("notification.clipboard.title"), LocaleService.LocaleString.of("notification.clipboard.wishlist.copied.text"));
        };
    }

    private EventHandler<ActionEvent> getDeleteHandler() {
        return _ -> {
            final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(LocaleService.getLocalizedStringForCurrentLocale("tab.wishlist.delete.confirm.title"));
            alert.setHeaderText(LocaleService.getLocalizedStringForCurrentLocale("tab.wishlist.delete.confirm.header"));
            alert.setContentText(LocaleService.getLocalizedStringForCurrentLocale("tab.wishlist.delete.confirm.content"));

            final Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                    WishlistService.deleteHorizonsWishlist(this.activeWishlistUUID, commander);
                    Platform.runLater(this::refreshWishlistSelect);
                });
            }
        };
    }

    private EventHandler<ActionEvent> getRenameHandler() {
        return _ -> {
            final DestroyableTextField textField = TextFieldBuilder.builder()
                    .withStyleClasses("root", "wishlist-newname")
                    .withPromptTextProperty(LocaleService.getStringBinding("tab.wishlist.rename.prompt"))
                    .build();
            final DestroyableButton button = ButtonBuilder.builder()
                    .withText("tab.wishlist.rename")
                    .build();
            final DestroyableHBox popOverContent = BoxBuilder.builder()
                    .withNodes(textField, button).buildHBox();
            final DestroyablePopOver popOver = PopOverBuilder.builder()
                    .withStyleClass("popover-menubutton-layout")
                    .withContent(BoxBuilder.builder()
                            .withStyleClass("popover-menubutton-box")
                            .withNodes(new GrowingRegion(), popOverContent, new GrowingRegion())
                            .buildVBox())
                    .withDetachable(false)
                    .withHeaderAlwaysVisible(false)
                    .withArrowLocation(PopOver.ArrowLocation.RIGHT_CENTER)
                    .build();
            popOver.show(this.menuButton);
            button.setOnAction(_ -> APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                final HorizonsWishlists wishlists = WishlistService.getHorizonsWishlists(commander);
                wishlists.renameWishlist(this.activeWishlistUUID, textField.getText());
                WishlistService.saveHorizonsWishlists(commander, wishlists);
                textField.clear();
                refreshWishlistSelect();
                popOver.hide();
            }));
            textField.setOnKeyPressed(event -> {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    button.fire();
                }
            });
        };
    }

    private EventHandler<ActionEvent> getCreateHandler() {
        return _ -> {
            final DestroyableTextField textField = TextFieldBuilder.builder()
                    .withStyleClasses("root", "wishlist-newname")
                    .withPromptTextProperty(LocaleService.getStringBinding("tab.wishlist.rename.prompt"))
                    .build();
            final DestroyableButton button = ButtonBuilder.builder()
                    .withText("tab.wishlist.create")
                    .build();
            final DestroyableHBox popOverContent = BoxBuilder.builder()
                    .withNodes(textField, button)
                    .buildHBox();
            final DestroyablePopOver popOver = PopOverBuilder.builder()
                    .withStyleClass("popover-menubutton-layout")
                    .withContent(BoxBuilder.builder()
                            .withStyleClass("popover-menubutton-box")
                            .withNodes(new GrowingRegion(), popOverContent, new GrowingRegion())
                            .buildVBox())
                    .withDetachable(false)
                    .withHeaderAlwaysVisible(false)
                    .withArrowLocation(PopOver.ArrowLocation.RIGHT_CENTER)
                    .build();
            popOver.show(this.menuButton);
            button.setOnAction(eventB -> APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                final HorizonsWishlists wishlists = WishlistService.getHorizonsWishlists(commander);
                wishlists.createWishlist(textField.getText());
                WishlistService.saveHorizonsWishlists(commander, wishlists);
                textField.clear();
                refreshWishlistSelect();
                popOver.hide();
            }));
            textField.setOnKeyPressed(ke -> {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    button.fire();
                }
            });
        };
    }

    private Supplier<XSSFWorkbook> getXlsExporter() {
        return getExporter((raw, encoded, manufactured, commodity) ->
                () -> XlsExporter.createXlsWishlist(raw, encoded, manufactured, commodity));
    }

    private Supplier<String> getCsvExporter() {
        return getExporter((raw, encoded, manufactured, commodity) ->
                () -> CsvExporter.createCsvWishlist(raw, encoded, manufactured, commodity));
    }

    private Supplier<String> getTextExporter() {
        return getExporter((raw, encoded, manufactured, commodity) ->
                () -> TextExporter.createTextWishlist(raw, encoded, manufactured, commodity));
    }

    private <T> Supplier<T> getExporter(QuadFunction<Map<Raw, WishlistMaterial>, Map<Encoded, WishlistMaterial>, Map<Manufactured, WishlistMaterial>, Map<Commodity, WishlistMaterial>, Supplier<T>> exporter) {
        final Map<Raw, WishlistMaterial> wishlistNeededRaw = new HashMap<>();
        final Map<Encoded, WishlistMaterial> wishlistNeededEncoded = new HashMap<>();
        final Map<Manufactured, WishlistMaterial> wishlistNeededManufactured = new HashMap<>();
        final Map<Commodity, WishlistMaterial> wishlistNeededCommodity = new HashMap<>();
        APPLICATION_STATE.getPreferredCommander().ifPresent(_ -> {
            Arrays.stream(Raw.values()).forEach(raw ->
                    wishlistNeededRaw.compute(raw, getMaterialCompute(raw))
            );
            Arrays.stream(Encoded.values()).forEach(encoded ->
                    wishlistNeededEncoded.compute(encoded, getMaterialCompute(encoded))
            );
            Arrays.stream(Manufactured.values()).forEach(manufactured ->
                    wishlistNeededManufactured.compute(manufactured, getMaterialCompute(manufactured))
            );
            Stream.concat(Arrays.stream(RareCommodity.values()), Arrays.stream(RegularCommodity.values())).forEach(commodity ->
                    wishlistNeededCommodity.compute(commodity, getMaterialCompute(commodity))
            );
        });
        return exporter.apply(wishlistNeededRaw, wishlistNeededEncoded, wishlistNeededManufactured, wishlistNeededCommodity);
    }

    private BiFunction<HorizonsMaterial, WishlistMaterial, WishlistMaterial> getMaterialCompute(HorizonsMaterial material) {
        return (key, value) -> {
            final IntegerRange range = WishlistService.getCurrentWishlistCount(key);
            final Integer current = getCurrentWishlistCount(material);
            return value != null ? WishlistMaterial.sum(value, new WishlistMaterial(range.min(), current, range.max())) : new WishlistMaterial(range.min(), current, range.max());
        };
    }

    private Integer getCurrentWishlistCount(HorizonsMaterial horizonsMaterial) {
        return APPLICATION_STATE.getPreferredCommander().map(commander ->
                        WishlistService.getHorizonsWishlists(commander).getSelectedWishlist().getItems().stream()
                                .map(bp -> {
                                    if (bp instanceof HorizonsModuleWishlistBlueprint horizonsModuleWishlistBlueprint) {//modules
                                        return horizonsModuleWishlistBlueprint.getPercentageToComplete().keySet().stream()
                                                .map(grade -> {
                                                    final HorizonsBlueprint blueprint = (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipe(horizonsModuleWishlistBlueprint.getRecipeName(), HorizonsWishlistBlueprint.getBlueprintType(horizonsModuleWishlistBlueprint), grade);
                                                    return blueprint.getRequiredAmount(horizonsMaterial, getCurrentEngineerForRecipe(blueprint).orElseGet(() -> getWorstEngineerForRecipe(blueprint)));
                                                })
                                                .reduce(Integer::sum)
                                                .orElse(0);
                                    } else if (bp instanceof HorizonsWishlistBlueprint horizonsWishlistBlueprint) {//other
                                        final HorizonsBlueprint blueprint = (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipe(horizonsWishlistBlueprint.getRecipeName(), HorizonsWishlistBlueprint.getBlueprintType(bp), HorizonsWishlistBlueprint.getBlueprintGrade(bp));
                                        return blueprint.getRequiredAmount(horizonsMaterial, getCurrentEngineerForRecipe(blueprint).orElseGet(() -> getWorstEngineerForRecipe(blueprint)));
                                    } else {
                                        return 0;
                                    }
                                })
                                .reduce(Integer::sum)
                                .orElse(0))
                .orElse(0);
    }

    private static <T extends BlueprintName<T>> List<? extends Blueprint<T>> createList(Blueprint<T> key, Integer value) {
        final List<Blueprint<T>> list = new ArrayList<>();
        for (int i = 0; i < value; i++) {
            list.add(key);
        }
        return list;
    }

    private Optional<Engineer> getCurrentEngineerForRecipe(Blueprint<HorizonsBlueprintName> blueprint) {
        if (blueprint == null) {
            return Optional.empty();
        }
        return shortestPath.stream()
                .filter(pathItem -> pathItem.getRecipes().keySet().stream()
                        .anyMatch(bp -> bp.getBlueprintName().equals(blueprint.getBlueprintName())
                                && ((HorizonsBlueprint) bp).getHorizonsBlueprintType().equals(((HorizonsBlueprint) blueprint).getHorizonsBlueprintType())))
                .findFirst()
                .filter(pathItem -> !pathItem.getEngineer().equals(Engineer.UNKNOWN))
                .map(PathItem::getEngineer);
    }

    private Engineer getWorstEngineerForRecipe(Blueprint<HorizonsBlueprintName> recipe) {
        if (!(recipe instanceof HorizonsModuleBlueprint)) {
            return null;
        }
        return ((HorizonsModuleBlueprint) recipe).getEngineers().stream().min(Comparator.comparingInt(eng -> ApplicationState.getInstance().getEngineerRank(eng))).orElse(null);

    }
//    private Engineer getWorstEngineerForRecipe(HorizonsWishlistBlueprint bp) {
//        if (bp instanceof HorizonsModuleWishlistBlueprint horizonsModuleWishlistBlueprint) {//modules
//            final HorizonsBlueprintGrade grade = horizonsModuleWishlistBlueprint.getPercentageToComplete().entrySet().stream()
//                    .filter(entry -> entry.getValue() > 0D)
//                    .map(Map.Entry::getKey)
//                    .max(Comparator.comparing(HorizonsBlueprintGrade::getGrade))
//                    .orElse(HorizonsBlueprintGrade.GRADE_1);
//            final HorizonsBlueprint blueprint = (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipe(horizonsModuleWishlistBlueprint.getRecipeName(), HorizonsWishlistBlueprint.getBlueprintType(horizonsModuleWishlistBlueprint), HorizonsBlueprintGrade.GRADE_1);
//            return blueprint.getEngineers().stream().min(Comparator.comparing(engineer -> ApplicationState.getInstance().getEngineerRank(engineer))).orElse(null);
//        } else if (bp instanceof HorizonsWishlistBlueprint horizonsWishlistBlueprint) {//other
//            final HorizonsBlueprint blueprint = (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipe(horizonsWishlistBlueprint.getRecipeName(), HorizonsWishlistBlueprint.getBlueprintType(bp), HorizonsWishlistBlueprint.getBlueprintGrade(bp));
//            return blueprint.getEngineers().stream().min(Comparator.comparing(engineer -> ApplicationState.getInstance().getEngineerRank(engineer))).orElse(null);
//        }
//        return null;
//    }


    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, AfterFontSizeSetEvent.class, fontSizeEvent -> applyFontSizingHack(fontSizeEvent.getFontSize())));
        register(EventService.addListener(true, this, CommanderSelectedEvent.class, commanderSelectedEvent ->
        {
            refreshWishlistSelect();
        }));
        register(EventService.addListener(true, this, LanguageChangedEvent.class, languageChangedEvent ->
        {
            refreshWishlistSelect();
        }));
        register(EventService.addListener(true, this, HorizonsWishlistCreatedEvent.class, event ->
        {
            refreshWishlistSelect();
        }));

        register(EventService.addListener(true, this, HorizonsWishlistSearchEvent.class, horizonsWishlistSearchEvent -> {
            this.currentSearch = horizonsWishlistSearchEvent.getSearch();//intended for export sorting
        }));
        register(EventService.addListener(true, this, ShortestPathChangedEvent.class, shortestPathChangedEvent -> {
            this.shortestPath = shortestPathChangedEvent.getPathItems();
        }));
    }

    private void refreshWishlistSelect() {
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
            final HorizonsWishlists wishlists = WishlistService.getHorizonsWishlists(commander);
            final Set<HorizonsWishlist> items = wishlists.getAllWishlists();
            this.wishlistSelect.getItems().clear();
            this.wishlistSelect.getItems().addAll(items.stream().sorted(Comparator.comparing(HorizonsWishlist::getName)).toList());
            this.wishlistSelect.getSelectionModel().select(wishlists.getSelectedWishlist());
        });
    }

    private static final String FX_FONT_SIZE_DPX = "-fx-font-size: %dpx";

    private void applyFontSizingHack(final Integer fontSize) {
        //hack for component resizing on other fontsizes
        final String fontStyle = String.format(FX_FONT_SIZE_DPX, fontSize);
        this.wishlistSelect.styleProperty().set(fontStyle);
    }

    private void copyWishListToClipboard() {
        ClipboardHelper.copyToClipboard(ClipboardHelper.createClipboardHorizonsWishlist());
    }
}
