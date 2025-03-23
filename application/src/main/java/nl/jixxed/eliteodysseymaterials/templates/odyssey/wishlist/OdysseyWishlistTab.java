package nl.jixxed.eliteodysseymaterials.templates.odyssey.wishlist;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;
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
public class OdysseyWishlistTab extends OdysseyTab implements DestroyableEventTemplate {


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

    private DestroyableComboBox<Wishlist> wishlistSelect;
    private DestroyableLabel noBlueprint;
    private DestroyableHBox engineerBlueprintsLine;
    private DestroyableHBox suitUpgradeBlueprintsLine;
    private DestroyableHBox suitModuleBlueprintsLine;
    private DestroyableHBox weaponUpgradeBlueprintsLine;
    private DestroyableHBox weaponModuleBlueprintsLine;
    private DestroyableFlowPane engineerRecipes;
    private DestroyableFlowPane suitUpgradeRecipes;
    private DestroyableFlowPane suitModuleRecipes;
    private DestroyableFlowPane weaponUpgradeRecipes;
    private DestroyableFlowPane weaponModuleRecipes;
    private DestroyableFlowPane allFlow;
    private DestroyableFlowPane goodFlow;
    private DestroyableFlowPane assetChemicalFlow;
    private DestroyableFlowPane dataFlow;
    private DestroyableFlowPane assetCircuitFlow;
    private DestroyableFlowPane assetTechFlow;
    private ShortestPathFlow<OdysseyBlueprintName> shortestPathFlow;
    private DestroyableVBox blueprints;
    private DestroyableScrollPane scrollPane;
    private DestroyableVBox content;
    private DestroyableVBox contentChild;
    private DestroyableVBox flows;
    private DestroyableCheckBox hideCompletedCheckBox;
    private DestroyableLabel selectedBlueprintsLabel;
    private DestroyableLabel requiredMaterialsLabel;
    private DestroyableLabel travelPathLabel;
    private DestroyableLabel engineerRecipesLabel;
    private DestroyableLabel suitUpgradeRecipesLabel;
    private DestroyableLabel suitModuleRecipesLabel;
    private DestroyableLabel weaponUpgradeRecipesLabel;
    private DestroyableLabel weaponModuleRecipesLabel;
    private String activeWishlistUUID;
    private OdysseyMaterialTotals totals;

    static {
        NUMBER_FORMAT.setMaximumFractionDigits(2);
    }

    private DestroyableMenuButton menuButton;
    private DestroyableHBox selectedBlueprintsHintWhite;
    private DestroyableHBox selectedBlueprintsHintYellow;
    private DestroyableHBox selectedBlueprintsHintGreen;
    private DestroyableResizableImageView blueprintsHelp;
    private DestroyableResizableImageView materialsHelp;
    private DestroyableHBox materialHintRed;
    private DestroyableHBox materialHintYellow;
    private DestroyableHBox materialHintGreen;


    public OdysseyWishlistTab() {
        initComponents();
        initEventHandling();

    }

    private void copyWishListToClipboard() {
        ClipboardHelper.copyToClipboard(ClipboardHelper.createClipboardWishlist());
    }

    private static final String FX_FONT_SIZE_DPX = "-fx-font-size: %dpx";

    private void applyFontSizingHack(final Integer fontSize) {
        //hack for component resizing on other fontsizes
        final String fontStyle = String.format(FX_FONT_SIZE_DPX, fontSize);
        this.wishlistSelect.styleProperty().set(fontStyle);
    }

    public void initComponents() {
        initLabels();
        initShortestPathTable();
        final Set<Wishlist> items = APPLICATION_STATE.getPreferredCommander()
                .map(commander -> WishlistService.getWishlists(commander).getAllWishlists())
                .orElse(Collections.emptySet());
        this.wishlistSelect = ComboBoxBuilder.builder(Wishlist.class)
                .withStyleClass("wishlist-select")
                .withItemsProperty(FXCollections.observableArrayList(items.stream().sorted(Comparator.comparing(Wishlist::getName)).toList()))
                .withValueChangeListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                            this.activeWishlistUUID = newValue.getUuid();
                            WishlistService.selectWishlist(this.activeWishlistUUID, commander);
                            EventService.publish(new WishlistSelectedEvent(this.activeWishlistUUID));
                        });
                    }
                })
                .build();
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> this.wishlistSelect.getSelectionModel().select(WishlistService.getWishlists(commander).getSelectedWishlist()));
        this.totals = new OdysseyMaterialTotals();
        this.engineerRecipes = FlowPaneBuilder.builder()
                .withStyleClass(WISHLIST_RECIPES_STYLE_CLASS)
                .build();
        this.suitUpgradeRecipes = FlowPaneBuilder.builder()
                .withStyleClass(WISHLIST_RECIPES_STYLE_CLASS)
                .build();
        this.suitModuleRecipes = FlowPaneBuilder.builder()
                .withStyleClass(WISHLIST_RECIPES_STYLE_CLASS)
                .build();
        this.weaponUpgradeRecipes = FlowPaneBuilder.builder()
                .withStyleClass(WISHLIST_RECIPES_STYLE_CLASS)
                .build();
        this.weaponModuleRecipes = FlowPaneBuilder.builder()
                .withStyleClass(WISHLIST_RECIPES_STYLE_CLASS)
                .build();
        this.allFlow = FlowPaneBuilder.builder()
                .withStyleClass(WISHLIST_INGREDIENTS_STYLE_CLASS)
                .build();
        this.goodFlow = FlowPaneBuilder.builder()
                .withStyleClass(WISHLIST_INGREDIENTS_STYLE_CLASS)
                .build();
        this.assetChemicalFlow = FlowPaneBuilder.builder()
                .withStyleClass(WISHLIST_INGREDIENTS_STYLE_CLASS)
                .build();
        this.dataFlow = FlowPaneBuilder.builder()
                .withStyleClass(WISHLIST_INGREDIENTS_STYLE_CLASS)
                .build();
        this.assetCircuitFlow = FlowPaneBuilder.builder()
                .withStyleClass(WISHLIST_INGREDIENTS_STYLE_CLASS)
                .build();
        this.assetTechFlow = FlowPaneBuilder.builder()
                .withStyleClass(WISHLIST_INGREDIENTS_STYLE_CLASS)
                .build();

        this.hideCompleted.set(PreferencesService.getPreference("blueprint.hide.completed", Boolean.FALSE));

        this.menuButton = MenuButtonBuilder.builder()
                .withText("tab.wishlist.options")
                .withMenuItems(
                        Map.of("tab.wishlist.create", event -> {
                                    final DestroyableTextField textField = TextFieldBuilder.builder()
                                            .withStyleClasses("root", "wishlist-newname")
                                            .withPromptTextProperty(LocaleService.getStringBinding("tab.wishlist.rename.prompt"))
                                            .build();
                                    final DestroyableButton button = ButtonBuilder.builder()
                                            .withText("tab.wishlist.create")
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
                                            .withArrowLocation(PopOver.ArrowLocation.RIGHT_CENTER)
                                            .build();
                                    popOver.show(this.menuButton);
                                    button.setOnAction(eventB -> APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                                        final Wishlists wishlists = WishlistService.getWishlists(commander);
                                        wishlists.createWishlist(textField.getText());
                                        WishlistService.saveWishlists(commander, wishlists);
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
                                                    .withNodes(new GrowingRegion(), popOverContent, new GrowingRegion()).buildVBox())
                                            .withDetachable(false)
                                            .withHeaderAlwaysVisible(false)
                                            .withArrowLocation(PopOver.ArrowLocation.RIGHT_CENTER)
                                            .build();
                                    popOver.show(this.menuButton);
                                    button.setOnAction(eventB -> APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                                        final Wishlists wishlists = WishlistService.getWishlists(commander);
                                        wishlists.renameWishlist(this.activeWishlistUUID, textField.getText());
                                        WishlistService.saveWishlists(commander, wishlists);
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
                                            WishlistService.deleteWishlist(this.activeWishlistUUID, commander);
                                            Platform.runLater(this::refreshWishlistSelect);
                                        });
                                    }
                                },
                                "tab.wishlist.copy", event -> {
                                    copyWishListToClipboard();
                                    NotificationService.showInformation(NotificationType.COPY, LocaleService.LocaleString.of("notification.clipboard.title"), LocaleService.LocaleString.of("notification.clipboard.wishlist.copied.text"));
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
                )
                .build();
        this.menuButton.setFocusTraversable(false);

        this.hideCompletedCheckBox = CheckBoxBuilder.builder().build();
        this.hideCompletedCheckBox.getStyleClass().add("wishlist-checkbox");
        this.hideCompletedCheckBox.addBinding(this.hideCompletedCheckBox.textProperty(), LocaleService.getStringBinding("tab.wishlist.hide.completed"));
        this.hideCompletedCheckBox.setSelected(this.hideCompleted.get());
        this.hideCompletedCheckBox.selectedProperty().addListener((observable, oldValue, newValue) ->
        {
            this.hideCompleted.set(newValue);
            PreferencesService.setPreference("blueprint.hide.completed", newValue);
            refreshContent();
        });
        this.wishlistSize = APPLICATION_STATE.getPreferredCommander().map(commander -> WishlistService.getWishlists(commander).getSelectedWishlist().getItems().size()).orElse(0);
        this.addBinding(this.textProperty(), LocaleService.getSupplierStringBinding("tabs.wishlist", () -> (this.wishlistSize > 0) ? " (" + this.wishlistSize + ")" : ""));

        this.engineerBlueprintsLine = BoxBuilder.builder()
                .withNodes(this.engineerRecipesLabel, this.engineerRecipes).buildHBox();
        this.suitUpgradeBlueprintsLine = BoxBuilder.builder()
                .withNodes(this.suitUpgradeRecipesLabel, this.suitUpgradeRecipes).buildHBox();
        this.suitModuleBlueprintsLine = BoxBuilder.builder()
                .withNodes(this.suitModuleRecipesLabel, this.suitModuleRecipes).buildHBox();
        this.weaponUpgradeBlueprintsLine = BoxBuilder.builder()
                .withNodes(this.weaponUpgradeRecipesLabel, this.weaponUpgradeRecipes).buildHBox();
        this.weaponModuleBlueprintsLine = BoxBuilder.builder()
                .withNodes(this.weaponModuleRecipesLabel, this.weaponModuleRecipes).buildHBox();
        HBox.setHgrow(this.engineerRecipes, Priority.ALWAYS);
        HBox.setHgrow(this.suitUpgradeRecipes, Priority.ALWAYS);
        HBox.setHgrow(this.suitModuleRecipes, Priority.ALWAYS);
        HBox.setHgrow(this.weaponUpgradeRecipes, Priority.ALWAYS);
        HBox.setHgrow(this.weaponModuleRecipes, Priority.ALWAYS);
        this.blueprints = BoxBuilder.builder()
                .withStyleClass("wishlist-blueprints").buildVBox();

        final DestroyableHBox hBoxBlueprints = BoxBuilder.builder()
                .withNodes(this.wishlistSelect, this.menuButton).buildHBox();
        this.materialHintRed = BoxBuilder.builder()
                .withNodes(LabelBuilder.builder()
                        .withStyleClasses("wishlist-hint-red", "wishlist-hint-box")
                        .withText("tab.wishlist.material.hint.red")
                        .build(), LabelBuilder.builder()
                        .withStyleClass("wishlist-hint-white")
                        .withText("tab.wishlist.material.hint.red.explain")
                        .build()).buildHBox();
        this.materialHintYellow = BoxBuilder.builder()
                .withNodes(LabelBuilder.builder()
                        .withStyleClasses("wishlist-hint-yellow", "wishlist-hint-box")
                        .withText("tab.wishlist.material.hint.yellow")
                        .build(), LabelBuilder.builder()
                        .withStyleClass("wishlist-hint-white")
                        .withText("tab.wishlist.material.hint.yellow.explain")
                        .build()).buildHBox();
        this.materialHintGreen = BoxBuilder.builder()
                .withNodes(LabelBuilder.builder()
                        .withStyleClasses("wishlist-hint-green", "wishlist-hint-box")
                        .withText("tab.wishlist.material.hint.green")
                        .build(), LabelBuilder.builder()
                        .withStyleClass("wishlist-hint-white")
                        .withText("tab.wishlist.material.hint.green.explain")
                        .build()).buildHBox();
        final DestroyableVBox contentNodeMaterials = BoxBuilder.builder().withStyleClass("help-popover")
                .withNodes(this.materialHintRed, this.materialHintYellow, this.materialHintGreen).buildVBox();
        final DestroyablePopOver popOverMaterials = PopOverBuilder.builder()
                .withStyleClass("popover-menubutton-layout")
                .withContent(contentNodeMaterials)
                .withDetachable(false)
                .withHeaderAlwaysVisible(false)
                .withArrowLocation(PopOver.ArrowLocation.RIGHT_CENTER)
                .build();

        this.materialsHelp = ResizableImageViewBuilder.builder()
                .withOnMouseClicked(event -> {
                    popOverMaterials.show(this.materialsHelp, event.getScreenX(), event.getScreenY());
                })
                .withStyleClass("help-image")
                .withImage("/images/other/help.png")
                .build();
        final DestroyableHBox hBoxMaterials = BoxBuilder.builder()
                .withNodes(this.requiredMaterialsLabel, this.materialsHelp, this.hideCompletedCheckBox).buildHBox();
        hBoxBlueprints.addBinding(hBoxBlueprints.spacingProperty(), ScalingHelper.getPixelDoubleBindingFromEm(0.25));
        hBoxMaterials.addBinding(hBoxMaterials.spacingProperty(), ScalingHelper.getPixelDoubleBindingFromEm(0.71));
        this.flows = BoxBuilder.builder()
                .withStyleClass(WISHLIST_CONTENT_STYLE_CLASS)
                .withNodes(this.allFlow, this.goodFlow, this.assetChemicalFlow, this.assetCircuitFlow, this.assetTechFlow, this.dataFlow).buildVBox();
        this.selectedBlueprintsHintWhite = BoxBuilder.builder()
                .withNodes(LabelBuilder.builder()
                        .withStyleClasses("wishlist-hint-white", "wishlist-hint-box")
                        .withText("tab.wishlist.selected.blueprints.hint.white")
                        .build(), LabelBuilder.builder()
                        .withStyleClass("wishlist-hint-white")
                        .withText("tab.wishlist.selected.blueprints.hint.white.explain")
                        .build()).buildHBox();
        this.selectedBlueprintsHintYellow = BoxBuilder.builder()
                .withNodes(LabelBuilder.builder()
                        .withStyleClasses("wishlist-hint-yellow", "wishlist-hint-box")
                        .withText("tab.wishlist.selected.blueprints.hint.yellow")
                        .build(), LabelBuilder.builder()
                        .withStyleClass("wishlist-hint-white")
                        .withText("tab.wishlist.selected.blueprints.hint.yellow.odyssey.explain")
                        .build()).buildHBox();
        this.selectedBlueprintsHintGreen = BoxBuilder.builder()
                .withNodes(LabelBuilder.builder()
                        .withStyleClasses("wishlist-hint-green", "wishlist-hint-box")
                        .withText("tab.wishlist.selected.blueprints.hint.green")
                        .build(), LabelBuilder.builder()
                        .withStyleClass("wishlist-hint-white")
                        .withText("tab.wishlist.selected.blueprints.hint.green.explain")
                        .build()).buildHBox();
        final DestroyableVBox contentNode = BoxBuilder.builder()
                .withStyleClass("help-popover")
                .withNodes(this.selectedBlueprintsHintWhite, this.selectedBlueprintsHintYellow, this.selectedBlueprintsHintGreen)
                .buildVBox();
        final DestroyablePopOver popOver = PopOverBuilder.builder()
                .withStyleClass("popover-menubutton-layout")
                .withContent(contentNode)
                .withDetachable(false)
                .withHeaderAlwaysVisible(false)
                .withArrowLocation(PopOver.ArrowLocation.RIGHT_CENTER)
                .build();
        this.blueprintsHelp = ResizableImageViewBuilder.builder()
                .withOnMouseClicked(event -> {
                    popOver.show(this.blueprintsHelp, event.getScreenX(), event.getScreenY());
                })
                .withStyleClass("help-image")
                .withImage("/images/other/help.png")
                .build();
        final DestroyableHBox titleBar = BoxBuilder.builder()
                .withStyleClass("help-image-bar")
                .withNodes(this.selectedBlueprintsLabel, this.blueprintsHelp).buildHBox();
        this.contentChild = BoxBuilder.builder()
                .withStyleClass(WISHLIST_CONTENT_STYLE_CLASS)
                .withNodes(this.totals, titleBar, /*this.selectedBlueprintsHintWhite, this.selectedBlueprintsHintYellow, this.selectedBlueprintsHintGreen, */this.blueprints, hBoxMaterials, this.flows, this.travelPathLabel, this.shortestPathFlow).buildVBox();
        this.content = BoxBuilder.builder()
                .withStyleClass(WISHLIST_CONTENT_STYLE_CLASS)
                .withNodes(hBoxBlueprints, this.wishlistSize > 0 ? this.contentChild : this.noBlueprint).buildVBox();
        this.scrollPane = ScrollPaneBuilder.builder()
                .withContent(this.content)
                .build();
        this.setContent(this.scrollPane);
        Observable.create((ObservableEmitter<JournalLineProcessedEvent> emitter) -> register(EventService.addListener(true, this, JournalLineProcessedEvent.class, emitter::onNext)))
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .subscribe(newValue -> Platform.runLater(this::refreshContent));

        this.wishlistBlueprints.clear();
        this.wishlistBlueprints.addAll(APPLICATION_STATE.getPreferredCommander()
                .map(commander -> WishlistService.getWishlists(commander).getSelectedWishlist().getItems().stream()
                        .map(wishlistRecipe -> new OdysseyWishlistBlueprintTemplate(WishlistService.getWishlists(commander).getSelectedWishlist().getUuid(), wishlistRecipe))
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

        this.shortestPathFlow.addBinding(this.shortestPathFlow.visibleProperty(), Bindings.greaterThan(Bindings.size(this.shortestPathFlow.getItems()), 0));
        this.travelPathLabel.addBinding(this.travelPathLabel.visibleProperty(), Bindings.greaterThan(Bindings.size(this.shortestPathFlow.getItems()), 0));
    }

    private void initLabels() {
        this.noBlueprint = LabelBuilder.builder()
                .withStyleClasses(WISHLIST_HEADER_CLASS, WISHLIST_CONTENT_STYLE_CLASS)
                .withText("tab.wishlist.no.blueprint")
                .build();
        this.selectedBlueprintsLabel = LabelBuilder.builder()
                .withStyleClass(WISHLIST_HEADER_CLASS)
                .withText("tab.wishlist.selected.blueprints")
                .build();
        this.requiredMaterialsLabel = LabelBuilder.builder()
                .withStyleClass(WISHLIST_HEADER_CLASS)
                .withText("tab.wishlist.required.materials")
                .build();
        this.travelPathLabel = LabelBuilder.builder()
                .withStyleClass(WISHLIST_HEADER_CLASS)
                .withText("tab.wishlist.travel.path")
                .build();
        this.engineerRecipesLabel = LabelBuilder.builder()
                .withStyleClass(WISHLIST_CATEGORY_CLASS)
                .withText("blueprint.category.name.engineer_unlocks")
                .build();
        this.suitUpgradeRecipesLabel = LabelBuilder.builder()
                .withStyleClass(WISHLIST_CATEGORY_CLASS)
                .withText("blueprint.category.name.suit_grades")
                .build();
        this.suitModuleRecipesLabel = LabelBuilder.builder()
                .withStyleClass(WISHLIST_CATEGORY_CLASS)
                .withText("blueprint.category.name.suit_modules")
                .build();
        this.weaponUpgradeRecipesLabel = LabelBuilder.builder()
                .withStyleClass(WISHLIST_CATEGORY_CLASS)
                .withText("blueprint.category.name.weapon_grades")
                .build();
        this.weaponModuleRecipesLabel = LabelBuilder.builder()
                .withStyleClass(WISHLIST_CATEGORY_CLASS)
                .withText("blueprint.category.name.weapon_modules")
                .build();
    }

    public void initEventHandling() {
        register(EventService.addListener(true, this, AfterFontSizeSetEvent.class, fontSizeEvent -> applyFontSizingHack(fontSizeEvent.getFontSize())));
        register(EventService.addListener(true, this, WishlistSelectedEvent.class, wishlistChangedEvent -> {
            refreshWishlistBlueprints();
            refreshWishlistRecipes();
            refreshBlueprintOverview();
            refreshContent();
            EventService.publish(new WishlistChangedEvent(this.activeWishlistUUID));
        }));
        register(EventService.addListener(true, this, WishlistChangedEvent.class, wishlistChangedEvent -> {
            this.activeWishlistUUID = wishlistChangedEvent.getWishlistUUID();
            APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> this.wishlistSize = WishlistService.getWishlists(commander).getWishlist(this.activeWishlistUUID).getItems().size());

            this.addBinding(this.textProperty(), LocaleService.getSupplierStringBinding("tabs.wishlist", () -> (this.wishlistSize > 0) ? " (" + this.wishlistSize + ")" : ""));
        }));
        register(EventService.addListener(true, this, WishlistBlueprintEvent.class, wishlistEvent ->
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
        }));
        register(EventService.addListener(true, this, CommanderSelectedEvent.class, commanderSelectedEvent ->
        {
            final Wishlist selectedWishlist = WishlistService.getWishlists(commanderSelectedEvent.getCommander()).getSelectedWishlist();
            this.activeWishlistUUID = selectedWishlist.getUuid();
            this.wishlistBlueprints.clear();
            this.wishlistBlueprints.addAll(selectedWishlist.getItems().stream()
                    .map(wishlistRecipe -> new OdysseyWishlistBlueprintTemplate(this.activeWishlistUUID, wishlistRecipe))
                    .toList());
            refreshWishlistSelect();
            refreshWishlistRecipes();
            refreshBlueprintOverview();
            refreshContent();
            EventService.publish(new WishlistChangedEvent(this.activeWishlistUUID));
        }));
        register(EventService.addListener(true, this, LanguageChangedEvent.class, languageChangedEvent ->
        {
            refreshWishlistSelect();
        }));
        register(EventService.addListener(true, this, WishlistCreatedEvent.class, event ->
        {
            refreshWishlistSelect();
        }));
        register(EventService.addListener(true, this, CommanderAllListedEvent.class, commanderAllListedEvent -> refreshWishlistBlueprints()));
        register(EventService.addListener(true, this, LocationChangedEvent.class, locationChangedEvent -> refreshContent()));
        register(EventService.addListener(true, this, ImportResultEvent.class, importResultEvent -> {
            if (importResultEvent.getResult().getResultType().equals(ImportResult.ResultType.SUCCESS_ODYSSEY_WISHLIST)) {
                refreshWishlistBlueprints();
            }
        }));
        register(EventService.addListener(true, this, HideWishlistShortestPathItemEvent.class, event -> {
            final List<OdysseyWishlistBlueprintTemplate> pathBlueprints = getPathWishlistBlueprints(event.getPathItem());
            pathBlueprints.forEach(wishlistBlueprint -> wishlistBlueprint.setVisibility(false));
            refreshContent();
        }));
        register(EventService.addListener(true, this, RemoveWishlistShortestPathItemEvent.class, event -> {
            final List<OdysseyWishlistBlueprintTemplate> pathBlueprints = getPathWishlistBlueprints(event.getPathItem());
            pathBlueprints.forEach(OdysseyWishlistBlueprintTemplate::destroy);
        }));
        register(EventService.addListener(true, this, OdysseyWishlistSearchEvent.class, odysseyWishlistSearchEvent -> {
            this.currentSearch = odysseyWishlistSearchEvent.getSearch();
            Platform.runLater(this::refreshContent);
        }));
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
        this.wishlistBlueprints.clear();
        final List<OdysseyWishlistBlueprintTemplate> newWishlistBlueprints = APPLICATION_STATE.getPreferredCommander()
                .map(commander -> {
                    final Wishlist selectedWishlist = WishlistService.getWishlists(commander).getSelectedWishlist();
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
            final Wishlists wishlists = WishlistService.getWishlists(commander);
            final Set<Wishlist> items = wishlists.getAllWishlists();
            this.wishlistSelect.getItems().clear();
            this.wishlistSelect.getItems().addAll(items.stream().sorted(Comparator.comparing(Wishlist::getName)).toList());
            this.wishlistSelect.getSelectionModel().select(wishlists.getSelectedWishlist());
        });
    }

    private void refreshWishlistRecipes() {
        this.engineerRecipes.getNodes().clear();
        this.suitUpgradeRecipes.getNodes().clear();
        this.suitModuleRecipes.getNodes().clear();
        this.weaponUpgradeRecipes.getNodes().clear();
        this.weaponModuleRecipes.getNodes().clear();
        this.engineerRecipes.getNodes().addAll(this.wishlistBlueprints.stream().filter(wishlistBlueprint -> BlueprintCategory.ENGINEER_UNLOCKS.equals(wishlistBlueprint.getRecipeCategory())).toList());
        this.suitUpgradeRecipes.getNodes().addAll(this.wishlistBlueprints.stream().filter(wishlistBlueprint -> BlueprintCategory.SUIT_GRADES.equals(wishlistBlueprint.getRecipeCategory())).toList());
        this.suitModuleRecipes.getNodes().addAll(this.wishlistBlueprints.stream().filter(wishlistBlueprint -> BlueprintCategory.SUIT_MODULES.equals(wishlistBlueprint.getRecipeCategory())).toList());
        this.weaponUpgradeRecipes.getNodes().addAll(this.wishlistBlueprints.stream().filter(wishlistBlueprint -> BlueprintCategory.WEAPON_GRADES.equals(wishlistBlueprint.getRecipeCategory())).toList());
        this.weaponModuleRecipes.getNodes().addAll(this.wishlistBlueprints.stream().filter(wishlistBlueprint -> BlueprintCategory.WEAPON_MODULES.equals(wishlistBlueprint.getRecipeCategory())).toList());
    }


    private void addBluePrint(final OdysseyWishlistBlueprintTemplate wishlistBlueprint) {
        switch (wishlistBlueprint.getRecipeCategory()) {
            case ENGINEER_UNLOCKS -> this.engineerRecipes.getNodes().add(wishlistBlueprint);
            case SUIT_GRADES -> this.suitUpgradeRecipes.getNodes().add(wishlistBlueprint);
            case SUIT_MODULES -> this.suitModuleRecipes.getNodes().add(wishlistBlueprint);
            case WEAPON_GRADES -> this.weaponUpgradeRecipes.getNodes().add(wishlistBlueprint);
            case WEAPON_MODULES -> this.weaponModuleRecipes.getNodes().add(wishlistBlueprint);
            default -> throw new IllegalArgumentException("Unsupported Category");
        }
        refreshBlueprintOverview();
    }

    private void removeBluePrint(final OdysseyWishlistBlueprintTemplate wishlistBlueprint) {
        switch (wishlistBlueprint.getRecipeCategory()) {
            case ENGINEER_UNLOCKS -> this.engineerRecipes.getNodes().remove(wishlistBlueprint);
            case SUIT_GRADES -> this.suitUpgradeRecipes.getNodes().remove(wishlistBlueprint);
            case SUIT_MODULES -> this.suitModuleRecipes.getNodes().remove(wishlistBlueprint);
            case WEAPON_GRADES -> this.weaponUpgradeRecipes.getNodes().remove(wishlistBlueprint);
            case WEAPON_MODULES -> this.weaponModuleRecipes.getNodes().remove(wishlistBlueprint);
            default -> throw new IllegalArgumentException("Unsupported Category");
        }
        refreshBlueprintOverview();
    }

    private void refreshBlueprintOverview() {
        this.blueprints.getNodes().clear();
        for (final DestroyableHBox blueprintList : List.of(this.engineerBlueprintsLine, this.suitUpgradeBlueprintsLine, this.suitModuleBlueprintsLine, this.weaponUpgradeBlueprintsLine, this.weaponModuleBlueprintsLine)) {
            if (!((DestroyableFlowPane) blueprintList.getNodes().get(1)).getNodes().isEmpty()) {
                this.blueprints.getNodes().add(blueprintList);
                final ArrayList<OdysseyWishlistBlueprintTemplate> wishlistItems = (ArrayList<OdysseyWishlistBlueprintTemplate>) (ArrayList<?>) new ArrayList<>(((DestroyableFlowPane) blueprintList.getNodes().get(1)).getChildren());
                wishlistItems
                        .sort(Comparator
                                .comparing(node -> LocaleService.getLocalizedStringForCurrentLocale(((WishlistBlueprintTemplate<OdysseyBlueprintName>) node).getRecipeName().getLocalizationKey()))
                                .thenComparing(node -> ((WishlistBlueprintTemplate<OdysseyBlueprintName>) node).getSequenceID()));
                ((DestroyableFlowPane) blueprintList.getNodes().get(1)).getNodes().clear();
                ((DestroyableFlowPane) blueprintList.getNodes().get(1)).getNodes().addAll(wishlistItems);
            }
        }
    }

    private void refreshContent() {
        if (this.wishlistBlueprints.isEmpty()) {
            this.content.getNodes().remove(this.contentChild);
            if (!this.content.getNodes().contains(this.noBlueprint)) {
                this.content.getNodes().add(this.noBlueprint);
            }
        } else {
            this.content.getNodes().remove(this.noBlueprint);
            if (!this.content.getNodes().contains(this.contentChild)) {
                this.content.getNodes().add(this.contentChild);
            }
        }
        this.allFlow.getNodes().clear();
        this.goodFlow.getNodes().clear();
        this.assetChemicalFlow.getNodes().clear();
        this.dataFlow.getNodes().clear();
        this.assetCircuitFlow.getNodes().clear();
        this.assetTechFlow.getNodes().clear();
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
                    .filter(entry -> !this.hideCompleted.get() || StorageService.getMaterialCount(entry.getKey(), AmountType.AVAILABLE) < entry.getValue())
                    .map(wishlistItem -> new OdysseyWishlistIngredient(OdysseyStorageType.forMaterial(wishlistItem.getKey()), wishlistItem.getKey(), wishlistItem.getValue(), StorageService.getMaterialCount(wishlistItem.getKey(), AmountType.TOTAL)))
                    .toList();
            final List<OdysseyWishlistIngredient> ingredientsGood = this.wishlistNeededGoods.entrySet().stream()
                    .filter(entry -> !this.hideCompleted.get() || StorageService.getMaterialCount(entry.getKey(), AmountType.AVAILABLE) < entry.getValue())
                    .map(wishlistItem -> new OdysseyWishlistIngredient(OdysseyStorageType.forMaterial(wishlistItem.getKey()), wishlistItem.getKey(), wishlistItem.getValue(), StorageService.getMaterialCount(wishlistItem.getKey(), AmountType.TOTAL)))
                    .toList();
            final List<OdysseyWishlistIngredient> ingredientsAsset = this.wishlistNeededAssets.entrySet().stream()
                    .filter(entry -> !this.hideCompleted.get() || StorageService.getMaterialCount(entry.getKey(), AmountType.AVAILABLE) < entry.getValue())
                    .map(wishlistItem -> new OdysseyWishlistIngredient(OdysseyStorageType.forMaterial(wishlistItem.getKey()), wishlistItem.getKey(), wishlistItem.getValue(), StorageService.getMaterialCount(wishlistItem.getKey(), AmountType.TOTAL)))
                    .toList();
            allIngredients.addAll(ingredientsData);
            allIngredients.addAll(ingredientsGood);
            allIngredients.addAll(ingredientsAsset);

            this.goodFlow.getNodes().addAll(ingredientsGood.stream().sorted(OdysseyWishlistMaterialSort.getSort(this.currentSearch)).toList());
            this.dataFlow.getNodes().addAll(ingredientsData.stream().sorted(OdysseyWishlistMaterialSort.getSort(this.currentSearch)).toList());
            this.assetCircuitFlow.getNodes().addAll(ingredientsAsset.stream().filter(ingredient -> ((Asset) ingredient.getOdysseyMaterial()).getType().equals(AssetType.CIRCUIT)).sorted(OdysseyWishlistMaterialSort.getSort(this.currentSearch)).toList());
            this.assetChemicalFlow.getNodes().addAll(ingredientsAsset.stream().filter(ingredient -> ((Asset) ingredient.getOdysseyMaterial()).getType().equals(AssetType.CHEMICAL)).sorted(OdysseyWishlistMaterialSort.getSort(this.currentSearch)).toList());
            this.assetTechFlow.getNodes().addAll(ingredientsAsset.stream().filter(ingredient -> ((Asset) ingredient.getOdysseyMaterial()).getType().equals(AssetType.TECH)).sorted(OdysseyWishlistMaterialSort.getSort(this.currentSearch)).toList());
        } else if (this.currentSearch.getWishlistMaterialGrouping().equals(WishlistMaterialGrouping.NONE)) {
            final List<OdysseyWishlistIngredient> ingredientsAll = this.wishlistNeededAll.entrySet().stream()
                    .filter(entry -> !this.hideCompleted.get() || StorageService.getMaterialStorage(entry.getKey()).getAvailableValue() < entry.getValue())
                    .map(wishlistItem -> new OdysseyWishlistIngredient(OdysseyStorageType.forMaterial(wishlistItem.getKey()), wishlistItem.getKey(), wishlistItem.getValue(), StorageService.getMaterialStorage(wishlistItem.getKey()).getTotalValue()))
                    .toList();
            allIngredients.addAll(ingredientsAll);
            this.allFlow.getNodes().addAll(ingredientsAll.stream().sorted(OdysseyWishlistMaterialSort.getSort(this.currentSearch)).toList());

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
        this.flows.getNodes().removeAll(this.goodFlow, this.assetChemicalFlow, this.assetCircuitFlow, this.assetTechFlow, this.dataFlow);
        for (final DestroyableFlowPane flowPane : new DestroyableFlowPane[]{this.goodFlow, this.assetChemicalFlow, this.assetCircuitFlow, this.assetTechFlow, this.dataFlow}) {
            if (!flowPane.getNodes().isEmpty()) {
                this.flows.getNodes().add(flowPane);
            }
        }
    }

    @Override
    public OdysseyTabs getTabType() {
        return OdysseyTabs.WISHLIST;
    }
}