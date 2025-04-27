package nl.jixxed.eliteodysseymaterials.templates.odyssey.wishlist;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.export.CsvExporter;
import nl.jixxed.eliteodysseymaterials.export.TextExporter;
import nl.jixxed.eliteodysseymaterials.export.XlsExporter;
import nl.jixxed.eliteodysseymaterials.helper.ClipboardHelper;
import nl.jixxed.eliteodysseymaterials.helper.TriFunction;
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
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class OdysseyWishlistMenu extends DestroyableHBox implements DestroyableEventTemplate {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private DestroyableComboBox<Wishlist> wishlistSelect;
    private DestroyableMenuButton menuButton;
    private OdysseyWishlistMaterialSearch currentSearch = new OdysseyWishlistMaterialSearch("", OdysseyWishlistMaterialSort.ALPHABETICAL, WishlistMaterialGrouping.CATEGORY);

    public OdysseyWishlistMenu() {
        final OdysseyWishlistMaterialSort materialSort = OdysseyWishlistMaterialSort.valueOf(PreferencesService.getPreference("search.odyssey.wishlist.sort", "ALPHABETICAL"));
        final WishlistMaterialGrouping filter = WishlistMaterialGrouping.valueOf(PreferencesService.getPreference("search.odyssey.wishlist.grouping", "CATEGORY"));
        currentSearch.setWishlistMaterialGrouping(filter);
        currentSearch.setWishlistMaterialSort(materialSort);

        initComponents();
        initEventHandling();
    }


    @Override
    public void initComponents() {
        this.getStyleClass().add("wishlist-menu");
        final Optional<Wishlists> wishlists = APPLICATION_STATE.getPreferredCommander().map(WishlistService::getOdysseyWishlists);
        final Set<Wishlist> items = wishlists.map(Wishlists::getAllWishlists)
                .orElse(Collections.emptySet());
        this.wishlistSelect = ComboBoxBuilder.builder(Wishlist.class)
                .withStyleClass("wishlist-select")
                .withSelected(wishlists.map(Wishlists::getSelectedWishlist).orElse(null))
                .withItemsProperty(FXCollections.observableArrayList(items.stream().sorted(Comparator.comparing(Wishlist::getName)).toList()))
                .withValueChangeListener((_, _, newValue) -> {
                    if (newValue != null) {
                        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                            WishlistService.selectOdysseyWishlist(newValue.getUuid(), commander);
                            EventService.publish(new OdysseyWishlistSelectedEvent(newValue.getUuid()));
                        });
                    }
                })
                .build();

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
                                "tab.wishlist.rename", this.wishlistSelect.getSelectionModel().selectedItemProperty().isEqualTo(Wishlist.ALL),
                                "tab.wishlist.copy", this.wishlistSelect.getSelectionModel().selectedItemProperty().isEqualTo(Wishlist.ALL),
                                "tab.wishlist.delete", this.wishlistSelect.getSelectionModel().selectedItemProperty().isEqualTo(Wishlist.ALL)
                        ))
                .build();
        this.menuButton.setFocusTraversable(false);


//        DestroyableButton loadoutEditorButton = ButtonBuilder.builder()
//                .withText("odyssey.wishlist.loadouteditor")
//                .withOnAction(_ -> EventService.publish(new OdysseyWishlistOpenLoadoutEditorEvent()))
//                .build();

        this.getNodes().addAll(this.wishlistSelect, this.menuButton/*, loadoutEditorButton*/);
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
                    WishlistService.deleteOdysseyWishlist(this.wishlistSelect.getSelectionModel().getSelectedItem().getUuid(), commander);
                    Platform.runLater(this::refreshWishlistSelect);
                });
            }
        };
    }

    private EventHandler<ActionEvent> getRenameHandler() {
        return _ -> showInputPopOver("tab.wishlist.rename", "tab.wishlist.rename.prompt",
                (commander, input) -> {
                    final Wishlists wishlists = WishlistService.getOdysseyWishlists(commander);
                    wishlists.renameWishlist(this.wishlistSelect.getSelectionModel().getSelectedItem().getUuid(), input);
                    WishlistService.saveOdysseyWishlists(commander, wishlists);
                });
    }

    private EventHandler<ActionEvent> getCreateHandler() {
        return _ -> showInputPopOver("tab.wishlist.create", "tab.wishlist.create.prompt",
                (commander, input) -> {
                    final Wishlists wishlists = WishlistService.getOdysseyWishlists(commander);
                    wishlists.createWishlist(input);
                    WishlistService.saveOdysseyWishlists(commander, wishlists);
                });
    }

    private void showInputPopOver(String buttonLocaleKey, String textfieldPromptLocaleKey, BiConsumer<Commander, String> inputHandler) {
        final DestroyableTextField textField = TextFieldBuilder.builder()
                .withStyleClasses("root", "wishlist-newname")
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
            refreshWishlistSelect();
            popOver.hide();
        }));
        textField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                button.fire();
            }
        });
    }

    private Supplier<XSSFWorkbook> getXlsExporter() {
        return getExporter((datas, goods, assets) ->
                () -> XlsExporter.createXlsWishlist(datas, goods, assets));
    }

    private Supplier<String> getCsvExporter() {
        return getExporter((datas, goods, assets) ->
                () -> CsvExporter.createCsvWishlist(datas, goods, assets));
    }

    private Supplier<String> getTextExporter() {
        return getExporter((datas, goods, assets) ->
                () -> TextExporter.createTextWishlist(datas, goods, assets));
    }

    private <T> Supplier<T> getExporter(TriFunction<Map<Data, Integer>, Map<Good, Integer>, Map<Asset, Integer>, Supplier<T>> exporter) {
        final Map<Data, Integer> wishlistNeededDatas = new EnumMap<>(Data.class);
        final Map<Good, Integer> wishlistNeededGoods = new EnumMap<>(Good.class);
        final Map<Asset, Integer> wishlistNeededAssets = new EnumMap<>(Asset.class);
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander ->
                WishlistService.getOdysseyWishlists(commander).getSelectedWishlist().getItems().stream()
                        .filter(OdysseyWishlistBlueprint::isVisible)
                        .map((OdysseyWishlistBlueprint odysseyWishlistBlueprint) -> OdysseyBlueprintConstants.getRecipe(odysseyWishlistBlueprint.getRecipeName()))
                        .forEach(recipe -> {
                                    recipe.getMaterialCollection(Data.class).forEach((key, value1) -> wishlistNeededDatas.merge((Data) key, value1, Integer::sum));
                                    recipe.getMaterialCollection(Good.class).forEach((key, value1) -> wishlistNeededGoods.merge((Good) key, value1, Integer::sum));
                                    recipe.getMaterialCollection(Asset.class).forEach((key, value1) -> wishlistNeededAssets.merge((Asset) key, value1, Integer::sum));
                                }
                        ));
        return exporter.apply(wishlistNeededDatas, wishlistNeededGoods, wishlistNeededAssets);
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, AfterFontSizeSetEvent.class, fontSizeEvent -> applyFontSizingHack(fontSizeEvent.getFontSize())));
        register(EventService.addListener(true, this, LanguageChangedEvent.class, _ -> refreshWishlistSelect()));
        register(EventService.addListener(true, this, OdysseyWishlistCreatedEvent.class, _ -> refreshWishlistSelect()));
        register(EventService.addListener(true, this, OdysseyWishlistChangedEvent.class, event ->
                this.wishlistSelect.getItems().stream()
                        .filter(wl -> wl.getUuid().equals(event.getWishlistUUID()))
                        .findFirst()
                        .ifPresent(wl -> this.wishlistSelect.getSelectionModel().select(wl))));
        register(EventService.addListener(true, this, OdysseyWishlistSearchEvent.class, odysseyWishlistSearchEvent ->
                this.currentSearch = odysseyWishlistSearchEvent.getSearch()));//intended for export sorting
    }

    private void refreshWishlistSelect() {
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
            final Wishlists wishlists = WishlistService.getOdysseyWishlists(commander);
            final Set<Wishlist> items = wishlists.getAllWishlists();
            this.wishlistSelect.getItems().clear();
            this.wishlistSelect.getItems().addAll(items.stream().sorted(Comparator.comparing(Wishlist::getName)).toList());
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
        ClipboardHelper.copyToClipboard(ClipboardHelper.createClipboardOdysseyWishlist());
    }
}
