package nl.jixxed.eliteodysseymaterials.templates.odyssey.wishlist;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.export.CsvExporter;
import nl.jixxed.eliteodysseymaterials.export.TextExporter;
import nl.jixxed.eliteodysseymaterials.export.XlsExporter;
import nl.jixxed.eliteodysseymaterials.helper.ClipboardHelper;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.service.*;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;
import nl.jixxed.eliteodysseymaterials.templates.generic.Ingredient;
import nl.jixxed.eliteodysseymaterials.templates.generic.ShortestPathFlow;
import nl.jixxed.eliteodysseymaterials.templates.generic.WishlistBlueprintTemplate;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.OdysseyMaterialTotals;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.OdysseyTab;
import org.controlsfx.control.PopOver;

import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class OdysseyWishlistTab extends OdysseyTab {


    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance();
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private OdysseyWishlistMaterialSearch currentSearch = new OdysseyWishlistMaterialSearch("", OdysseyWishlistMaterialSort.ALPHABETICAL, WishlistMaterialGrouping.CATEGORY);
    private static final String WISHLIST_HEADER_CLASS = "wishlist-header";
    private static final String WISHLIST_CATEGORY_CLASS = "wishlist-category";
    private static final String WISHLIST_RECIPES_STYLE_CLASS = "wishlist-recipes";
    private static final String WISHLIST_INGREDIENTS_STYLE_CLASS = "wishlist-ingredients";
    private static final String WISHLIST_CONTENT_STYLE_CLASS = "wishlist-content";
    private int wishlistSize;
    private final List<OdysseyWishlistBlueprintTemplate> wishlistBlueprints = new ArrayList<>();
    private final AtomicBoolean hideCompleted = new AtomicBoolean();
    private final Map<Data, Integer> wishlistNeededDatas = new EnumMap<>(Data.class);
    private final Map<Good, Integer> wishlistNeededGoods = new EnumMap<>(Good.class);
    private final Map<Asset, Integer> wishlistNeededAssets = new EnumMap<>(Asset.class);
    private final Map<OdysseyMaterial, Integer> wishlistNeededAll = new HashMap<>();

    private ComboBox<Wishlist> wishlistSelect;
    private Label noBlueprint;
    private HBox engineerBlueprintsLine;
    private HBox suitUpgradeBlueprintsLine;
    private HBox suitModuleBlueprintsLine;
    private HBox weaponUpgradeBlueprintsLine;
    private HBox weaponModuleBlueprintsLine;
    private FlowPane engineerRecipes;
    private FlowPane suitUpgradeRecipes;
    private FlowPane suitModuleRecipes;
    private FlowPane weaponUpgradeRecipes;
    private FlowPane weaponModuleRecipes;
    private FlowPane allFlow;
    private FlowPane goodFlow;
    private FlowPane assetChemicalFlow;
    private FlowPane dataFlow;
    private FlowPane assetCircuitFlow;
    private FlowPane assetTechFlow;
    private ShortestPathFlow<OdysseyBlueprintName> shortestPathFlow;
    private VBox blueprints;
    private ScrollPane scrollPane;
    private VBox content;
    private VBox contentChild;
    private VBox flows;
    private CheckBox hideCompletedCheckBox;
    private Label selectedBlueprintsLabel;
    private Label requiredMaterialsLabel;
    private Label travelPathLabel;
    private Label engineerRecipesLabel;
    private Label suitUpgradeRecipesLabel;
    private Label suitModuleRecipesLabel;
    private Label weaponUpgradeRecipesLabel;
    private Label weaponModuleRecipesLabel;
    private String activeWishlistUUID;
    private OdysseyMaterialTotals totals;

    static {
        NUMBER_FORMAT.setMaximumFractionDigits(2);
    }

    private MenuButton menuButton;
    private HBox selectedBlueprintsHintWhite;
    private HBox selectedBlueprintsHintYellow;
    private HBox selectedBlueprintsHintGreen;
    private DestroyableResizableImageView blueprintsHelp;
    private DestroyableResizableImageView materialsHelp;
    private HBox materialHintRed;
    private HBox materialHintYellow;
    private HBox materialHintGreen;

    public OdysseyWishlistTab() {
        initComponents();
        initEventHandling();

    }

    private void copyWishListToClipboard() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent clipboardContent = new ClipboardContent();

        clipboardContent.putString(ClipboardHelper.createClipboardWishlist());
        clipboard.setContent(clipboardContent);
    }

    private static final String FX_FONT_SIZE_DPX = "-fx-font-size: %dpx";

    private void applyFontSizingHack(final Integer fontSize) {
        //hack for component resizing on other fontsizes
        final String fontStyle = String.format(FX_FONT_SIZE_DPX, fontSize);
        this.wishlistSelect.styleProperty().set(fontStyle);
    }

    private void initComponents() {
        initLabels();
        initShortestPathTable();
        final Set<Wishlist> items = APPLICATION_STATE.getPreferredCommander()
                .map(commander -> APPLICATION_STATE.getWishlists(commander.getFid()).getAllWishlists())
                .orElse(Collections.emptySet());
        this.wishlistSelect = ComboBoxBuilder.builder(Wishlist.class)
                .withStyleClass("wishlist-select")
                .withItemsProperty(FXCollections.observableArrayList(items.stream().sorted(Comparator.comparing(Wishlist::getName)).toList()))
                .withValueChangeListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                            this.activeWishlistUUID = newValue.getUuid();
                            APPLICATION_STATE.selectWishlist(this.activeWishlistUUID, commander.getFid());
                            EventService.publish(new WishlistSelectedEvent(this.activeWishlistUUID));
                        });
                    }
                })
                .build();
        this.totals = new OdysseyMaterialTotals();
        this.engineerRecipes = FlowPaneBuilder.builder().withStyleClass(WISHLIST_RECIPES_STYLE_CLASS).build();
        this.suitUpgradeRecipes = FlowPaneBuilder.builder().withStyleClass(WISHLIST_RECIPES_STYLE_CLASS).build();
        this.suitModuleRecipes = FlowPaneBuilder.builder().withStyleClass(WISHLIST_RECIPES_STYLE_CLASS).build();
        this.weaponUpgradeRecipes = FlowPaneBuilder.builder().withStyleClass(WISHLIST_RECIPES_STYLE_CLASS).build();
        this.weaponModuleRecipes = FlowPaneBuilder.builder().withStyleClass(WISHLIST_RECIPES_STYLE_CLASS).build();
        this.allFlow = FlowPaneBuilder.builder().withStyleClass(WISHLIST_INGREDIENTS_STYLE_CLASS).build();
        this.goodFlow = FlowPaneBuilder.builder().withStyleClass(WISHLIST_INGREDIENTS_STYLE_CLASS).build();
        this.assetChemicalFlow = FlowPaneBuilder.builder().withStyleClass(WISHLIST_INGREDIENTS_STYLE_CLASS).build();
        this.dataFlow = FlowPaneBuilder.builder().withStyleClass(WISHLIST_INGREDIENTS_STYLE_CLASS).build();
        this.assetCircuitFlow = FlowPaneBuilder.builder().withStyleClass(WISHLIST_INGREDIENTS_STYLE_CLASS).build();
        this.assetTechFlow = FlowPaneBuilder.builder().withStyleClass(WISHLIST_INGREDIENTS_STYLE_CLASS).build();

        this.hideCompleted.set(PreferencesService.getPreference("blueprint.hide.completed", Boolean.FALSE));

        this.menuButton = MenuButtonBuilder.builder().withText(LocaleService.getStringBinding("tab.wishlist.options")).withMenuItems(
                Map.of("tab.wishlist.create", event -> {
                            final TextField textField = TextFieldBuilder.builder().withStyleClasses("root", "wishlist-newname").withPromptTextProperty(LocaleService.getStringBinding("tab.wishlist.rename.prompt")).build();
                            final Button button = ButtonBuilder.builder().withText(LocaleService.getStringBinding("tab.wishlist.create")).build();
                            final HBox popOverContent = BoxBuilder.builder().withNodes(textField, button).buildHBox();
                            final PopOver popOver = new PopOver(BoxBuilder.builder().withStyleClass("popover-menubutton-box").withNodes(new GrowingRegion(), popOverContent, new GrowingRegion()).buildVBox());
                            popOver.setDetachable(false);
                            popOver.setHeaderAlwaysVisible(false);
                            popOver.getStyleClass().add("popover-menubutton-layout");
                            popOver.setArrowLocation(PopOver.ArrowLocation.RIGHT_CENTER);
                            popOver.show(this.menuButton);
                            button.setOnAction(eventB -> APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                                final Wishlists wishlists = APPLICATION_STATE.getWishlists(commander.getFid());
                                wishlists.createWishlist(textField.getText());
                                APPLICATION_STATE.saveWishlists(commander.getFid(), wishlists);
                                textField.clear();
                                refreshWishlistSelect();
                                popOver.hide();
                            }));
                            textField.setOnKeyPressed(ke -> {
                                if (ke.getCode().equals(KeyCode.ENTER)) {
                                    button.fire();
                                }
                            });
                        },
                        "tab.wishlist.rename", event -> {
                            final TextField textField = TextFieldBuilder.builder().withStyleClasses("root", "wishlist-newname").withPromptTextProperty(LocaleService.getStringBinding("tab.wishlist.rename.prompt")).build();
                            final Button button = ButtonBuilder.builder().withText(LocaleService.getStringBinding("tab.wishlist.rename")).build();
                            final HBox popOverContent = BoxBuilder.builder().withNodes(textField, button).buildHBox();
                            final PopOver popOver = new PopOver(BoxBuilder.builder().withStyleClass("popover-menubutton-box").withNodes(new GrowingRegion(), popOverContent, new GrowingRegion()).buildVBox());
                            popOver.setDetachable(false);
                            popOver.setHeaderAlwaysVisible(false);
                            popOver.getStyleClass().add("popover-menubutton-layout");
                            popOver.setArrowLocation(PopOver.ArrowLocation.RIGHT_CENTER);
                            popOver.show(this.menuButton);
                            button.setOnAction(eventB -> APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                                final Wishlists wishlists = APPLICATION_STATE.getWishlists(commander.getFid());
                                wishlists.renameWishlist(this.activeWishlistUUID, textField.getText());
                                APPLICATION_STATE.saveWishlists(commander.getFid(), wishlists);
                                textField.clear();
                                refreshWishlistSelect();
                                popOver.hide();
                            }));
                            textField.setOnKeyPressed(ke -> {
                                if (ke.getCode().equals(KeyCode.ENTER)) {
                                    button.fire();
                                }
                            });
                        },
                        "tab.wishlist.delete", event -> {
                            final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle(LocaleService.getLocalizedStringForCurrentLocale("tab.wishlist.delete.confirm.title"));
                            alert.setHeaderText(LocaleService.getLocalizedStringForCurrentLocale("tab.wishlist.delete.confirm.header"));
                            alert.setContentText(LocaleService.getLocalizedStringForCurrentLocale("tab.wishlist.delete.confirm.content"));

                            final Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                                    APPLICATION_STATE.deleteWishlist(this.activeWishlistUUID, commander.getFid());
                                    Platform.runLater(this::refreshWishlistSelect);
                                });
                            }
                        },
                        "tab.wishlist.copy", event -> {
                            copyWishListToClipboard();
                            NotificationService.showInformation(NotificationType.COPY, "Wishlists", "The wishlist has been copied to your clipboard");
                        },
                        "tab.wishlist.export", event ->
                                EventService.publish(new SaveWishlistEvent(
                                        () -> TextExporter.createTextWishlist(this.wishlistNeededDatas, this.wishlistNeededGoods, this.wishlistNeededAssets),
                                        () -> CsvExporter.createCsvWishlist(this.wishlistNeededDatas, this.wishlistNeededGoods, this.wishlistNeededAssets),
                                        () -> XlsExporter.createXlsWishlist(this.wishlistNeededDatas, this.wishlistNeededGoods, this.wishlistNeededAssets)
                                ))
                ),
                Map.of(
                        "tab.wishlist.rename", this.wishlistSelect.getSelectionModel().selectedItemProperty().isEqualTo(Wishlist.ALL),
                        "tab.wishlist.copy", this.wishlistSelect.getSelectionModel().selectedItemProperty().isEqualTo(Wishlist.ALL),
                        "tab.wishlist.delete", this.wishlistSelect.getSelectionModel().selectedItemProperty().isEqualTo(Wishlist.ALL)
                )
        ).build();
        this.menuButton.setFocusTraversable(false);

        this.hideCompletedCheckBox = new CheckBox();
        this.hideCompletedCheckBox.getStyleClass().add("wishlist-checkbox");
        this.hideCompletedCheckBox.textProperty().bind(LocaleService.getStringBinding("tab.wishlist.hide.completed"));
        this.hideCompletedCheckBox.setSelected(this.hideCompleted.get());
        this.hideCompletedCheckBox.selectedProperty().addListener((observable, oldValue, newValue) ->
        {
            this.hideCompleted.set(newValue);
            PreferencesService.setPreference("blueprint.hide.completed", newValue);
            refreshContent();
        });
        this.wishlistSize = APPLICATION_STATE.getPreferredCommander().map(commander -> APPLICATION_STATE.getWishlists(commander.getFid()).getSelectedWishlist().getItems().size()).orElse(0);
        this.textProperty().bind(LocaleService.getSupplierStringBinding("tabs.wishlist", () -> (this.wishlistSize > 0) ? " (" + this.wishlistSize + ")" : ""));

        this.engineerBlueprintsLine = BoxBuilder.builder().withNodes(this.engineerRecipesLabel, this.engineerRecipes).buildHBox();
        this.suitUpgradeBlueprintsLine = BoxBuilder.builder().withNodes(this.suitUpgradeRecipesLabel, this.suitUpgradeRecipes).buildHBox();
        this.suitModuleBlueprintsLine = BoxBuilder.builder().withNodes(this.suitModuleRecipesLabel, this.suitModuleRecipes).buildHBox();
        this.weaponUpgradeBlueprintsLine = BoxBuilder.builder().withNodes(this.weaponUpgradeRecipesLabel, this.weaponUpgradeRecipes).buildHBox();
        this.weaponModuleBlueprintsLine = BoxBuilder.builder().withNodes(this.weaponModuleRecipesLabel, this.weaponModuleRecipes).buildHBox();
        HBox.setHgrow(this.engineerRecipes, Priority.ALWAYS);
        HBox.setHgrow(this.suitUpgradeRecipes, Priority.ALWAYS);
        HBox.setHgrow(this.suitModuleRecipes, Priority.ALWAYS);
        HBox.setHgrow(this.weaponUpgradeRecipes, Priority.ALWAYS);
        HBox.setHgrow(this.weaponModuleRecipes, Priority.ALWAYS);
        this.blueprints = BoxBuilder.builder().withStyleClass("wishlist-blueprints").buildVBox();

        final HBox hBoxBlueprints = BoxBuilder.builder().withNodes(this.wishlistSelect, this.menuButton).buildHBox();
        this.materialHintRed = BoxBuilder.builder().withNodes(LabelBuilder.builder().withStyleClasses("wishlist-hint-red", "wishlist-hint-box").withText(LocaleService.getStringBinding("tab.wishlist.material.hint.red")).build(), LabelBuilder.builder().withStyleClass("wishlist-hint-white").withText(LocaleService.getStringBinding("tab.wishlist.material.hint.red.explain")).build()).buildHBox();
        this.materialHintYellow = BoxBuilder.builder().withNodes(LabelBuilder.builder().withStyleClasses("wishlist-hint-yellow", "wishlist-hint-box").withText(LocaleService.getStringBinding("tab.wishlist.material.hint.yellow")).build(), LabelBuilder.builder().withStyleClass("wishlist-hint-white").withText(LocaleService.getStringBinding("tab.wishlist.material.hint.yellow.explain")).build()).buildHBox();
        this.materialHintGreen = BoxBuilder.builder().withNodes(LabelBuilder.builder().withStyleClasses("wishlist-hint-green", "wishlist-hint-box").withText(LocaleService.getStringBinding("tab.wishlist.material.hint.green")).build(), LabelBuilder.builder().withStyleClass("wishlist-hint-white").withText(LocaleService.getStringBinding("tab.wishlist.material.hint.green.explain")).build()).buildHBox();

        final PopOver popOverMaterials = new PopOver();
        final VBox contentNodeMaterials = BoxBuilder.builder().withNodes(this.materialHintRed, this.materialHintYellow, this.materialHintGreen).buildVBox();
        contentNodeMaterials.getStyleClass().add("help-popover");
        popOverMaterials.setContentNode(contentNodeMaterials);
        popOverMaterials.setDetachable(false);
        this.materialsHelp = ResizableImageViewBuilder.builder().withOnMouseClicked(event -> {
            popOverMaterials.show(this.materialsHelp, event.getScreenX(), event.getScreenY());
        }).withStyleClass("help-image").withImage("/images/other/help.png").build();
        final HBox hBoxMaterials = BoxBuilder.builder().withNodes(this.requiredMaterialsLabel, this.materialsHelp, this.hideCompletedCheckBox).buildHBox();
        hBoxBlueprints.spacingProperty().bind(ScalingHelper.getPixelDoubleBindingFromEm(0.25));
        hBoxMaterials.spacingProperty().bind(ScalingHelper.getPixelDoubleBindingFromEm(0.71));
        this.flows = BoxBuilder.builder().withStyleClass(WISHLIST_CONTENT_STYLE_CLASS).withNodes(this.allFlow, this.goodFlow, this.assetChemicalFlow, this.assetCircuitFlow, this.assetTechFlow, this.dataFlow).buildVBox();
        this.selectedBlueprintsHintWhite = BoxBuilder.builder().withNodes(LabelBuilder.builder().withStyleClasses("wishlist-hint-white", "wishlist-hint-box").withText(LocaleService.getStringBinding("tab.wishlist.selected.blueprints.hint.white")).build(), LabelBuilder.builder().withStyleClass("wishlist-hint-white").withText(LocaleService.getStringBinding("tab.wishlist.selected.blueprints.hint.white.explain")).build()).buildHBox();
        this.selectedBlueprintsHintYellow = BoxBuilder.builder().withNodes(LabelBuilder.builder().withStyleClasses("wishlist-hint-yellow", "wishlist-hint-box").withText(LocaleService.getStringBinding("tab.wishlist.selected.blueprints.hint.yellow")).build(), LabelBuilder.builder().withStyleClass("wishlist-hint-white").withText(LocaleService.getStringBinding("tab.wishlist.selected.blueprints.hint.yellow.odyssey.explain")).build()).buildHBox();
        this.selectedBlueprintsHintGreen = BoxBuilder.builder().withNodes(LabelBuilder.builder().withStyleClasses("wishlist-hint-green", "wishlist-hint-box").withText(LocaleService.getStringBinding("tab.wishlist.selected.blueprints.hint.green")).build(), LabelBuilder.builder().withStyleClass("wishlist-hint-white").withText(LocaleService.getStringBinding("tab.wishlist.selected.blueprints.hint.green.explain")).build()).buildHBox();
        final PopOver popOver = new PopOver();
        final VBox contentNode = BoxBuilder.builder().withNodes(this.selectedBlueprintsHintWhite, this.selectedBlueprintsHintYellow, this.selectedBlueprintsHintGreen).buildVBox();
        contentNode.getStyleClass().add("help-popover");
        popOver.setContentNode(contentNode);
        popOver.setDetachable(false);
        this.blueprintsHelp = ResizableImageViewBuilder.builder().withOnMouseClicked(event -> {
            popOver.show(this.blueprintsHelp, event.getScreenX(), event.getScreenY());
        }).withStyleClass("help-image").withImage("/images/other/help.png").build();
        final HBox titleBar = BoxBuilder.builder().withStyleClass("help-image-bar").withNodes(this.selectedBlueprintsLabel, this.blueprintsHelp).buildHBox();
        this.contentChild = BoxBuilder.builder().withStyleClass(WISHLIST_CONTENT_STYLE_CLASS).withNodes(this.totals, titleBar, /*this.selectedBlueprintsHintWhite, this.selectedBlueprintsHintYellow, this.selectedBlueprintsHintGreen, */this.blueprints, hBoxMaterials, this.flows, this.travelPathLabel, this.shortestPathFlow).buildVBox();
        this.content = BoxBuilder.builder().withStyleClass(WISHLIST_CONTENT_STYLE_CLASS).withNodes(hBoxBlueprints, this.wishlistSize > 0 ? this.contentChild : this.noBlueprint).buildVBox();
        this.scrollPane = ScrollPaneBuilder.builder()
                .withContent(this.content)
                .build();
        this.setContent(this.scrollPane);
        Observable.create((ObservableEmitter<JournalLineProcessedEvent> emitter) -> EventService.addListener(this, JournalLineProcessedEvent.class, emitter::onNext))
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .subscribe(newValue -> Platform.runLater(this::refreshContent));

        this.wishlistBlueprints.forEach(OdysseyWishlistBlueprintTemplate::onDestroy);
        this.wishlistBlueprints.clear();
        this.wishlistBlueprints.addAll(APPLICATION_STATE.getPreferredCommander()
                .map(commander -> APPLICATION_STATE.getWishlists(commander.getFid()).getSelectedWishlist().getItems().stream()
                        .map(wishlistRecipe -> new OdysseyWishlistBlueprintTemplate(APPLICATION_STATE.getWishlists(commander.getFid()).getSelectedWishlist().getUuid(), wishlistRecipe))
                        .toList()
                )
                .orElse(new ArrayList<>()));
        try {
            final List<PathItem<OdysseyBlueprintName>> pathItems = PathService.calculateOdysseyShortestPath(this.wishlistBlueprints);
            this.shortestPathFlow.setItems(pathItems);
        } catch (final IllegalArgumentException ex) {
            log.error("Failed to generate path", ex);
        }
        final Integer fontSize = FontSize.valueOf(PreferencesService.getPreference(PreferenceConstants.TEXTSIZE, "NORMAL")).getSize();
        applyFontSizingHack(fontSize);

        refreshWishlistRecipes();
        refreshBlueprintOverview();
        refreshContent();
    }

    private void initShortestPathTable() {
        this.shortestPathFlow = new ShortestPathFlow<>(Expansion.ODYSSEY);

        this.shortestPathFlow.visibleProperty().bind(Bindings.greaterThan(Bindings.size(this.shortestPathFlow.getItems()), 0));
        this.travelPathLabel.visibleProperty().bind(Bindings.greaterThan(Bindings.size(this.shortestPathFlow.getItems()), 0));
    }

    private void initLabels() {
        this.noBlueprint = LabelBuilder.builder().withStyleClasses(WISHLIST_HEADER_CLASS, WISHLIST_CONTENT_STYLE_CLASS).withText(LocaleService.getStringBinding("tab.wishlist.no.blueprint")).build();
        this.selectedBlueprintsLabel = LabelBuilder.builder().withStyleClass(WISHLIST_HEADER_CLASS).withText(LocaleService.getStringBinding("tab.wishlist.selected.blueprints")).build();
        this.requiredMaterialsLabel = LabelBuilder.builder().withStyleClass(WISHLIST_HEADER_CLASS).withText(LocaleService.getStringBinding("tab.wishlist.required.materials")).build();
        this.travelPathLabel = LabelBuilder.builder().withStyleClass(WISHLIST_HEADER_CLASS).withText(LocaleService.getStringBinding("tab.wishlist.travel.path")).build();
        this.engineerRecipesLabel = LabelBuilder.builder().withStyleClass(WISHLIST_CATEGORY_CLASS).withText(LocaleService.getStringBinding("blueprint.category.name.engineer_unlocks")).build();
        this.suitUpgradeRecipesLabel = LabelBuilder.builder().withStyleClass(WISHLIST_CATEGORY_CLASS).withText(LocaleService.getStringBinding("blueprint.category.name.suit_grades")).build();
        this.suitModuleRecipesLabel = LabelBuilder.builder().withStyleClass(WISHLIST_CATEGORY_CLASS).withText(LocaleService.getStringBinding("blueprint.category.name.suit_modules")).build();
        this.weaponUpgradeRecipesLabel = LabelBuilder.builder().withStyleClass(WISHLIST_CATEGORY_CLASS).withText(LocaleService.getStringBinding("blueprint.category.name.weapon_grades")).build();
        this.weaponModuleRecipesLabel = LabelBuilder.builder().withStyleClass(WISHLIST_CATEGORY_CLASS).withText(LocaleService.getStringBinding("blueprint.category.name.weapon_modules")).build();
    }

    private void initEventHandling() {
        EventService.addListener(this, AfterFontSizeSetEvent.class, fontSizeEvent -> applyFontSizingHack(fontSizeEvent.getFontSize()));
        EventService.addListener(this, WishlistSelectedEvent.class, wishlistChangedEvent -> {
            refreshWishlistBlueprints();
            refreshWishlistRecipes();
            refreshBlueprintOverview();
            refreshContent();
            EventService.publish(new WishlistChangedEvent(this.activeWishlistUUID));
        });
        EventService.addListener(this, WishlistChangedEvent.class, wishlistChangedEvent -> {
            this.activeWishlistUUID = wishlistChangedEvent.getWishlistUUID();
            APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> this.wishlistSize = APPLICATION_STATE.getWishlists(commander.getFid()).getWishlist(this.activeWishlistUUID).getItems().size());

            this.textProperty().bind(LocaleService.getSupplierStringBinding("tabs.wishlist", () -> (this.wishlistSize > 0) ? " (" + this.wishlistSize + ")" : ""));
        });
        EventService.addListener(this, WishlistBlueprintEvent.class, wishlistEvent ->
        {
            if (Action.REMOVED.equals(wishlistEvent.getAction())) {
                this.wishlistBlueprints.stream()
                        .filter(wishlistBlueprint -> wishlistEvent.getWishlistBlueprints().contains(wishlistBlueprint.getWishlistRecipe()))
                        .findFirst()
                        .ifPresent(wishlistBlueprint -> {
                            this.wishlistBlueprints.remove(wishlistBlueprint);
                            removeBluePrint(wishlistBlueprint);
                        });
            }
            if (Action.ADDED.equals(wishlistEvent.getAction())) {
                APPLICATION_STATE.getPreferredCommander().ifPresent(commander ->
                        wishlistEvent.getWishlistBlueprints().forEach(wishlistRecipe -> {
                            final OdysseyWishlistBlueprintTemplate wishlistBlueprint = new OdysseyWishlistBlueprintTemplate(wishlistEvent.getWishlistUUID(), wishlistRecipe);
                            if (!wishlistEvent.getWishlistUUID().equals(this.activeWishlistUUID)) {
                                Platform.runLater(() -> this.wishlistSelect.getSelectionModel().select(this.wishlistSelect.getItems().stream().filter(wishlist -> wishlist.getUuid().equals(wishlistEvent.getWishlistUUID())).findFirst().orElse(null)));
                            } else {
                                this.wishlistBlueprints.add(wishlistBlueprint);
                                addBluePrint(wishlistBlueprint);
                            }
                        })
                );
            }
            refreshContent();
        });
        EventService.addListener(this, CommanderSelectedEvent.class, commanderSelectedEvent ->
        {
            final String fid = commanderSelectedEvent.getCommander().getFid();
            final Wishlist selectedWishlist = APPLICATION_STATE.getWishlists(fid).getSelectedWishlist();
            this.activeWishlistUUID = selectedWishlist.getUuid();
            this.wishlistBlueprints.forEach(OdysseyWishlistBlueprintTemplate::onDestroy);
            this.wishlistBlueprints.clear();
            this.wishlistBlueprints.addAll(selectedWishlist.getItems().stream()
                    .map(wishlistRecipe -> new OdysseyWishlistBlueprintTemplate(this.activeWishlistUUID, wishlistRecipe))
                    .toList());
            refreshWishlistSelect();
            refreshWishlistRecipes();
            refreshBlueprintOverview();
            refreshContent();
            EventService.publish(new WishlistChangedEvent(this.activeWishlistUUID));
        });
        EventService.addListener(this, LanguageChangedEvent.class, languageChangedEvent ->
        {
            refreshWishlistSelect();
        });
        EventService.addListener(this, WishlistCreatedEvent.class, event ->
        {
            refreshWishlistSelect();
        });
        EventService.addListener(this, CommanderAllListedEvent.class, commanderAllListedEvent -> refreshWishlistBlueprints());
        EventService.addListener(this, LocationChangedEvent.class, locationChangedEvent -> refreshContent());
        EventService.addListener(this, ImportResultEvent.class, importResultEvent -> {
            if (importResultEvent.getResult().getResultType().equals(ImportResult.ResultType.SUCCESS_ODYSSEY_WISHLIST)) {
                refreshWishlistBlueprints();
            }
        });
        EventService.addListener(this, HideWishlistShortestPathItemEvent.class, event -> {
            final List<OdysseyWishlistBlueprintTemplate> pathBlueprints = getPathWishlistBlueprints(event.getPathItem());
            pathBlueprints.forEach(wishlistBlueprint -> wishlistBlueprint.setVisibility(false));
            refreshContent();
        });
        EventService.addListener(this, RemoveWishlistShortestPathItemEvent.class, event -> {
            final List<OdysseyWishlistBlueprintTemplate> pathBlueprints = getPathWishlistBlueprints(event.getPathItem());
            pathBlueprints.forEach(OdysseyWishlistBlueprintTemplate::remove);
        });
        EventService.addListener(this, OdysseyWishlistSearchEvent.class, odysseyWishlistSearchEvent -> {
            this.currentSearch = odysseyWishlistSearchEvent.getSearch();
            Platform.runLater(this::refreshContent);
        });
    }

    private List<OdysseyWishlistBlueprintTemplate> getPathWishlistBlueprints(final PathItem<OdysseyBlueprintName> pathItem) {
        return pathItem.getRecipes().entrySet().stream()
                .flatMap(recipeEntry -> OdysseyWishlistTab.this.wishlistBlueprints.stream()
                        .filter(OdysseyWishlistBlueprintTemplate::isVisibleBlueprint)
                        .filter(wishlistBlueprint -> wishlistBlueprint.getPrimaryRecipe().equals(recipeEntry.getKey()))
                        .limit(recipeEntry.getValue())
                ).toList();
    }

    private void refreshWishlistBlueprints() {
        this.wishlistBlueprints.forEach(OdysseyWishlistBlueprintTemplate::onDestroy);
        this.wishlistBlueprints.clear();
        final List<OdysseyWishlistBlueprintTemplate> newWishlistBlueprints = APPLICATION_STATE.getPreferredCommander()
                .map(commander -> {
                    final Wishlist selectedWishlist = APPLICATION_STATE.getWishlists(commander.getFid()).getSelectedWishlist();
                    this.activeWishlistUUID = selectedWishlist.getUuid();
                    return selectedWishlist.getItems().stream()
                            .map(wishlistRecipe -> new OdysseyWishlistBlueprintTemplate(this.activeWishlistUUID, wishlistRecipe))
                            .toList();
                })
                .orElse(Collections.emptyList());
        this.wishlistBlueprints.addAll(newWishlistBlueprints);
        refreshWishlistSelect();
        refreshWishlistRecipes();
        refreshBlueprintOverview();
        refreshContent();
        EventService.publish(new WishlistChangedEvent(this.activeWishlistUUID));
    }

    private void refreshWishlistSelect() {
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
            final Wishlists wishlists = APPLICATION_STATE.getWishlists(commander.getFid());
            final Set<Wishlist> items = wishlists.getAllWishlists();
            this.wishlistSelect.getItems().clear();
            this.wishlistSelect.getItems().addAll(items.stream().sorted(Comparator.comparing(Wishlist::getName)).toList());
            this.wishlistSelect.getSelectionModel().select(wishlists.getSelectedWishlist());
        });
    }

    private void refreshWishlistRecipes() {
        this.engineerRecipes.getChildren().clear();
        this.suitUpgradeRecipes.getChildren().clear();
        this.suitModuleRecipes.getChildren().clear();
        this.weaponUpgradeRecipes.getChildren().clear();
        this.weaponModuleRecipes.getChildren().clear();
        this.engineerRecipes.getChildren().addAll(this.wishlistBlueprints.stream().filter(wishlistBlueprint -> BlueprintCategory.ENGINEER_UNLOCKS.equals(wishlistBlueprint.getRecipeCategory())).toList());
        this.suitUpgradeRecipes.getChildren().addAll(this.wishlistBlueprints.stream().filter(wishlistBlueprint -> BlueprintCategory.SUIT_GRADES.equals(wishlistBlueprint.getRecipeCategory())).toList());
        this.suitModuleRecipes.getChildren().addAll(this.wishlistBlueprints.stream().filter(wishlistBlueprint -> BlueprintCategory.SUIT_MODULES.equals(wishlistBlueprint.getRecipeCategory())).toList());
        this.weaponUpgradeRecipes.getChildren().addAll(this.wishlistBlueprints.stream().filter(wishlistBlueprint -> BlueprintCategory.WEAPON_GRADES.equals(wishlistBlueprint.getRecipeCategory())).toList());
        this.weaponModuleRecipes.getChildren().addAll(this.wishlistBlueprints.stream().filter(wishlistBlueprint -> BlueprintCategory.WEAPON_MODULES.equals(wishlistBlueprint.getRecipeCategory())).toList());
    }


    private void addBluePrint(final OdysseyWishlistBlueprintTemplate wishlistBlueprint) {
        switch (wishlistBlueprint.getRecipeCategory()) {
            case ENGINEER_UNLOCKS -> this.engineerRecipes.getChildren().add(wishlistBlueprint);
            case SUIT_GRADES -> this.suitUpgradeRecipes.getChildren().add(wishlistBlueprint);
            case SUIT_MODULES -> this.suitModuleRecipes.getChildren().add(wishlistBlueprint);
            case WEAPON_GRADES -> this.weaponUpgradeRecipes.getChildren().add(wishlistBlueprint);
            case WEAPON_MODULES -> this.weaponModuleRecipes.getChildren().add(wishlistBlueprint);
            default -> throw new IllegalArgumentException("Unsupported Category");
        }
        refreshBlueprintOverview();
    }

    private void removeBluePrint(final OdysseyWishlistBlueprintTemplate wishlistBlueprint) {
        switch (wishlistBlueprint.getRecipeCategory()) {
            case ENGINEER_UNLOCKS -> this.engineerRecipes.getChildren().remove(wishlistBlueprint);
            case SUIT_GRADES -> this.suitUpgradeRecipes.getChildren().remove(wishlistBlueprint);
            case SUIT_MODULES -> this.suitModuleRecipes.getChildren().remove(wishlistBlueprint);
            case WEAPON_GRADES -> this.weaponUpgradeRecipes.getChildren().remove(wishlistBlueprint);
            case WEAPON_MODULES -> this.weaponModuleRecipes.getChildren().remove(wishlistBlueprint);
            default -> throw new IllegalArgumentException("Unsupported Category");
        }
        refreshBlueprintOverview();
    }

    private void refreshBlueprintOverview() {
        this.blueprints.getChildren().clear();
        for (final HBox blueprintList : List.of(this.engineerBlueprintsLine, this.suitUpgradeBlueprintsLine, this.suitModuleBlueprintsLine, this.weaponUpgradeBlueprintsLine, this.weaponModuleBlueprintsLine)) {
            if (!((FlowPane) blueprintList.getChildren().get(1)).getChildren().isEmpty()) {
                this.blueprints.getChildren().add(blueprintList);
                final ArrayList<OdysseyWishlistBlueprintTemplate> wishlistItems = (ArrayList<OdysseyWishlistBlueprintTemplate>) (ArrayList<?>) new ArrayList<>(((FlowPane) blueprintList.getChildren().get(1)).getChildren());
                wishlistItems
                        .sort(Comparator
                                .comparing(node -> LocaleService.getLocalizedStringForCurrentLocale(((WishlistBlueprintTemplate<OdysseyBlueprintName>) node).getRecipeName().getLocalizationKey()))
                                .thenComparing(node -> ((WishlistBlueprintTemplate<OdysseyBlueprintName>) node).getSequenceID()));
                ((FlowPane) blueprintList.getChildren().get(1)).getChildren().clear();
                ((FlowPane) blueprintList.getChildren().get(1)).getChildren().addAll(wishlistItems);
            }
        }
    }

    private void refreshContent() {
        if (this.wishlistBlueprints.isEmpty()) {
            this.content.getChildren().remove(this.contentChild);
            if (!this.content.getChildren().contains(this.noBlueprint)) {
                this.content.getChildren().add(this.noBlueprint);
            }
        } else {
            this.content.getChildren().remove(this.noBlueprint);
            if (!this.content.getChildren().contains(this.contentChild)) {
                this.content.getChildren().add(this.contentChild);
            }
        }
        this.allFlow.getChildren().forEach(node -> ((OdysseyWishlistIngredient) node).onDestroy());
        this.goodFlow.getChildren().forEach(node -> ((OdysseyWishlistIngredient) node).onDestroy());
        this.assetChemicalFlow.getChildren().forEach(node -> ((OdysseyWishlistIngredient) node).onDestroy());
        this.dataFlow.getChildren().forEach(node -> ((OdysseyWishlistIngredient) node).onDestroy());
        this.assetCircuitFlow.getChildren().forEach(node -> ((OdysseyWishlistIngredient) node).onDestroy());
        this.assetTechFlow.getChildren().forEach(node -> ((OdysseyWishlistIngredient) node).onDestroy());
        this.allFlow.getChildren().clear();
        this.goodFlow.getChildren().clear();
        this.assetChemicalFlow.getChildren().clear();
        this.dataFlow.getChildren().clear();
        this.assetCircuitFlow.getChildren().clear();
        this.assetTechFlow.getChildren().clear();
        this.wishlistNeededAll.clear();
        this.wishlistNeededGoods.clear();
        this.wishlistNeededAssets.clear();
        this.wishlistNeededDatas.clear();
        this.wishlistBlueprints.stream()
                .filter(OdysseyWishlistBlueprintTemplate::isVisibleBlueprint)
                .map(OdysseyWishlistBlueprintTemplate::getPrimaryRecipe)
                .forEach(recipe -> {

                            if (this.currentSearch.getWishlistMaterialGrouping().equals(WishlistMaterialGrouping.CATEGORY)) {
                                recipe.getMaterialCollection(Data.class).forEach((key, value1) -> this.wishlistNeededDatas.merge((Data) key, value1, Integer::sum));
                                recipe.getMaterialCollection(Good.class).forEach((key, value1) -> this.wishlistNeededGoods.merge((Good) key, value1, Integer::sum));
                                recipe.getMaterialCollection(Asset.class).forEach((key, value1) -> this.wishlistNeededAssets.merge((Asset) key, value1, Integer::sum));
                            } else if (this.currentSearch.getWishlistMaterialGrouping().equals(WishlistMaterialGrouping.NONE)) {
                                recipe.getMaterialCollection(OdysseyMaterial.class).forEach((key, value1) -> this.wishlistNeededAll.merge(key, value1, Integer::sum));
                            }
                        }
                );

        final List<Ingredient> allIngredients = new ArrayList<>();
        if (this.currentSearch.getWishlistMaterialGrouping().equals(WishlistMaterialGrouping.CATEGORY)) {
            final List<OdysseyWishlistIngredient> ingredientsData = this.wishlistNeededDatas.entrySet().stream()
                    .filter(entry -> !this.hideCompleted.get() || StorageService.getData().get(entry.getKey()).getAvailableValue() < entry.getValue())
                    .map(wishlistItem -> new OdysseyWishlistIngredient(OdysseyStorageType.forMaterial(wishlistItem.getKey()), wishlistItem.getKey(), wishlistItem.getValue(), StorageService.getData().get(wishlistItem.getKey()).getTotalValue()))
                    .toList();
            final List<OdysseyWishlistIngredient> ingredientsGood = this.wishlistNeededGoods.entrySet().stream()
                    .filter(entry -> !this.hideCompleted.get() || StorageService.getGoods().get(entry.getKey()).getAvailableValue() < entry.getValue())
                    .map(wishlistItem -> new OdysseyWishlistIngredient(OdysseyStorageType.forMaterial(wishlistItem.getKey()), wishlistItem.getKey(), wishlistItem.getValue(), StorageService.getGoods().get(wishlistItem.getKey()).getTotalValue()))
                    .toList();
            final List<OdysseyWishlistIngredient> ingredientsAsset = this.wishlistNeededAssets.entrySet().stream()
                    .filter(entry -> !this.hideCompleted.get() || StorageService.getAssets().get(entry.getKey()).getAvailableValue() < entry.getValue())
                    .map(wishlistItem -> new OdysseyWishlistIngredient(OdysseyStorageType.forMaterial(wishlistItem.getKey()), wishlistItem.getKey(), wishlistItem.getValue(), StorageService.getAssets().get(wishlistItem.getKey()).getTotalValue()))
                    .toList();
            allIngredients.addAll(ingredientsData);
            allIngredients.addAll(ingredientsGood);
            allIngredients.addAll(ingredientsAsset);

            this.goodFlow.getChildren().addAll(ingredientsGood.stream().sorted(OdysseyWishlistMaterialSort.getSort(this.currentSearch)).toList());
            this.dataFlow.getChildren().addAll(ingredientsData.stream().sorted(OdysseyWishlistMaterialSort.getSort(this.currentSearch)).toList());
            this.assetCircuitFlow.getChildren().addAll(ingredientsAsset.stream().filter(ingredient -> ((Asset) ingredient.getOdysseyMaterial()).getType().equals(AssetType.CIRCUIT)).sorted(OdysseyWishlistMaterialSort.getSort(this.currentSearch)).toList());
            this.assetChemicalFlow.getChildren().addAll(ingredientsAsset.stream().filter(ingredient -> ((Asset) ingredient.getOdysseyMaterial()).getType().equals(AssetType.CHEMICAL)).sorted(OdysseyWishlistMaterialSort.getSort(this.currentSearch)).toList());
            this.assetTechFlow.getChildren().addAll(ingredientsAsset.stream().filter(ingredient -> ((Asset) ingredient.getOdysseyMaterial()).getType().equals(AssetType.TECH)).sorted(OdysseyWishlistMaterialSort.getSort(this.currentSearch)).toList());
        } else if (this.currentSearch.getWishlistMaterialGrouping().equals(WishlistMaterialGrouping.NONE)) {
            final List<OdysseyWishlistIngredient> ingredientsAll = this.wishlistNeededAll.entrySet().stream()
                    .filter(entry -> !this.hideCompleted.get() || StorageService.getMaterialStorage(entry.getKey()).getAvailableValue() < entry.getValue())
                    .map(wishlistItem -> new OdysseyWishlistIngredient(OdysseyStorageType.forMaterial(wishlistItem.getKey()), wishlistItem.getKey(), wishlistItem.getValue(), StorageService.getMaterialStorage(wishlistItem.getKey()).getTotalValue()))
                    .toList();
            allIngredients.addAll(ingredientsAll);
            this.allFlow.getChildren().addAll(ingredientsAll.stream().sorted(OdysseyWishlistMaterialSort.getSort(this.currentSearch)).toList());

        }

        this.wishlistBlueprints.forEach(wishlistBlueprint -> wishlistBlueprint.addWishlistIngredients(allIngredients));
        allIngredients.forEach(odysseyWishlistIngredient -> ((OdysseyWishlistIngredient) odysseyWishlistIngredient).searchHighlight(!this.currentSearch.getQuery().isBlank() && LocaleService.getLocalizedStringForCurrentLocale(((OdysseyWishlistIngredient) odysseyWishlistIngredient).getOdysseyMaterial().getLocalizationKey()).toLowerCase().contains(this.currentSearch.getQuery().toLowerCase())));

        removeAndAddFlows();
        try {
            final List<PathItem<OdysseyBlueprintName>> pathItems = PathService.calculateOdysseyShortestPath(this.wishlistBlueprints);
            this.shortestPathFlow.setItems(pathItems);
        } catch (final IllegalArgumentException ex) {
            log.error("Failed to generate path", ex);
        }

    }

    private void removeAndAddFlows() {
        this.flows.getChildren().removeAll(this.goodFlow, this.assetChemicalFlow, this.assetCircuitFlow, this.assetTechFlow, this.dataFlow);
        for (final FlowPane flowPane : new FlowPane[]{this.goodFlow, this.assetChemicalFlow, this.assetCircuitFlow, this.assetTechFlow, this.dataFlow}) {
            if (!flowPane.getChildren().isEmpty()) {
                this.flows.getChildren().add(flowPane);
            }
        }
    }

    @Override
    public OdysseyTabs getTabType() {
        return OdysseyTabs.WISHLIST;
    }
}