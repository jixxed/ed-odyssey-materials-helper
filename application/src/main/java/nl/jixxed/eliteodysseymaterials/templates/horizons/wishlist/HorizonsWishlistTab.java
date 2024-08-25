package nl.jixxed.eliteodysseymaterials.templates.horizons.wishlist;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
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
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;
import nl.jixxed.eliteodysseymaterials.templates.generic.Ingredient;
import nl.jixxed.eliteodysseymaterials.templates.generic.ShortestPathFlow;
import nl.jixxed.eliteodysseymaterials.templates.generic.WishlistBlueprintTemplate;
import nl.jixxed.eliteodysseymaterials.templates.horizons.HorizonsTab;
import org.controlsfx.control.PopOver;

import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class HorizonsWishlistTab extends HorizonsTab {


    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance();
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final String WISHLIST_HEADER_CLASS = "wishlist-header";
    private static final String WISHLIST_CATEGORY_CLASS = "wishlist-category";
    private static final String WISHLIST_RECIPES_STYLE_CLASS = "wishlist-recipes";
    private static final String WISHLIST_INGREDIENTS_STYLE_CLASS = "wishlist-ingredients";
    private static final String WISHLIST_CONTENT_STYLE_CLASS = "wishlist-content";
    private HorizonsWishlistMaterialSearch currentSearch = new HorizonsWishlistMaterialSearch("", HorizonsWishlistMaterialSort.ALPHABETICAL, WishlistMaterialGrouping.CATEGORY);
    private int wishlistSize;
    private final List<WishlistBlueprintTemplate<HorizonsBlueprintName>> wishlistBlueprints = new ArrayList<>();
    private final AtomicBoolean hideCompleted = new AtomicBoolean();
    private final Map<HorizonsMaterial, WishlistMaterial> wishlistNeededAll = new HashMap<>();
    private final Map<Raw, WishlistMaterial> wishlistNeededRaw = new EnumMap<>(Raw.class);
    private final Map<Encoded, WishlistMaterial> wishlistNeededEncoded = new EnumMap<>(Encoded.class);
    private final Map<Manufactured, WishlistMaterial> wishlistNeededManufactured = new EnumMap<>(Manufactured.class);
    private final Map<Commodity, WishlistMaterial> wishlistNeededCommodity = new EnumMap<>(Commodity.class);

    private ComboBox<HorizonsWishlist> wishlistSelect;
    private Label noBlueprint;
    private HBox engineerBlueprintsLine;
    private HBox hardpointBlueprintsLine;
    private HBox utilityMountBlueprintsLine;
    private HBox coreInternalBlueprintsLine;
    private HBox optionalInternalBlueprintsLine;
    private HBox experimentalEffectBlueprintsLine;
    private HBox synthesisBlueprintsLine;
    private HBox techbrokerBlueprintsLine;
    private FlowPane engineerRecipes;
    private FlowPane hardpointRecipes;
    private FlowPane utilityMountRecipes;
    private FlowPane coreInternalRecipes;
    private FlowPane optionalInternalRecipes;
    private FlowPane experimentalEffectRecipes;
    private FlowPane synthesisRecipes;
    private FlowPane techbrokerRecipes;
    private FlowPane allFlow;
    private FlowPane rawFlow;
    private FlowPane encodedFlow;
    private FlowPane manufacturedFlow;
    private FlowPane commodityFlow;
    private ShortestPathFlow<HorizonsBlueprintName> shortestPathFlow;
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
    private Label hardpointRecipesLabel;
    private Label utilityMountRecipesLabel;
    private Label coreInternalRecipesLabel;
    private Label optionalInternalRecipesLabel;
    private Label experimentalEffectRecipesLabel;
    private Label synthesisRecipesLabel;
    private Label techbrokerRecipesLabel;
    private String activeWishlistUUID;
    private HBox selectedBlueprintsHintWhite;
    private HBox selectedBlueprintsHintYellow;
    private HBox selectedBlueprintsHintGreen;
    private DestroyableResizableImageView blueprintsHelp;
    private DestroyableResizableImageView materialsHelp;
    private HBox materialHintRequired;
    private HBox materialHintRed;
    private HBox materialHintYellow;
    private HBox materialHintGreen;
    private final List<EventListener<?>> eventListeners = new ArrayList<>();

    static {
        NUMBER_FORMAT.setMaximumFractionDigits(2);
    }

    private MenuButton menuButton;
    private Button shipBuilderButton;
    private Button edsyButton;
    private Button coriolisButton;

    public HorizonsWishlistTab(final Application application) {
        initComponents(application);
        initEventHandling();

    }

    private void copyWishListToClipboard() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent clipboardContent = new ClipboardContent();

        clipboardContent.putString(ClipboardHelper.createClipboardHorizonsWishlist());
        clipboard.setContent(clipboardContent);
    }

    private static final String FX_FONT_SIZE_DPX = "-fx-font-size: %dpx";

    private void applyFontSizingHack(final Integer fontSize) {
        //hack for component resizing on other fontsizes
        final String fontStyle = String.format(FX_FONT_SIZE_DPX, fontSize);
        this.wishlistSelect.styleProperty().set(fontStyle);
    }

    private void initComponents(final Application application) {
        initLabels();
        initShortestPathTable();
        final Set<HorizonsWishlist> items = APPLICATION_STATE.getPreferredCommander()
                .map(commander -> WishlistService.getHorizonsWishlists(commander).getAllWishlists())
                .orElse(Collections.emptySet());
        this.wishlistSelect = ComboBoxBuilder.builder(HorizonsWishlist.class)
                .withStyleClass("wishlist-select")
                .withItemsProperty(FXCollections.observableArrayList(items.stream().sorted(Comparator.comparing(HorizonsWishlist::getName)).toList()))
                .withValueChangeListener((observable, oldValue, newValue) -> {
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
        this.engineerRecipes = FlowPaneBuilder.builder().withStyleClass(WISHLIST_RECIPES_STYLE_CLASS).build();
        this.hardpointRecipes = FlowPaneBuilder.builder().withStyleClass(WISHLIST_RECIPES_STYLE_CLASS).build();
        this.utilityMountRecipes = FlowPaneBuilder.builder().withStyleClass(WISHLIST_RECIPES_STYLE_CLASS).build();
        this.coreInternalRecipes = FlowPaneBuilder.builder().withStyleClass(WISHLIST_RECIPES_STYLE_CLASS).build();
        this.optionalInternalRecipes = FlowPaneBuilder.builder().withStyleClass(WISHLIST_RECIPES_STYLE_CLASS).build();
        this.experimentalEffectRecipes = FlowPaneBuilder.builder().withStyleClass(WISHLIST_RECIPES_STYLE_CLASS).build();
        this.synthesisRecipes = FlowPaneBuilder.builder().withStyleClass(WISHLIST_RECIPES_STYLE_CLASS).build();
        this.techbrokerRecipes = FlowPaneBuilder.builder().withStyleClass(WISHLIST_RECIPES_STYLE_CLASS).build();
        this.allFlow = FlowPaneBuilder.builder().withStyleClass(WISHLIST_INGREDIENTS_STYLE_CLASS).build();
        this.rawFlow = FlowPaneBuilder.builder().withStyleClass(WISHLIST_INGREDIENTS_STYLE_CLASS).build();
        this.encodedFlow = FlowPaneBuilder.builder().withStyleClass(WISHLIST_INGREDIENTS_STYLE_CLASS).build();
        this.manufacturedFlow = FlowPaneBuilder.builder().withStyleClass(WISHLIST_INGREDIENTS_STYLE_CLASS).build();
        this.commodityFlow = FlowPaneBuilder.builder().withStyleClass(WISHLIST_INGREDIENTS_STYLE_CLASS).build();

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
                                final HorizonsWishlists wishlists = WishlistService.getHorizonsWishlists(commander);
                                wishlists.renameWishlist(this.activeWishlistUUID, textField.getText());
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
                        },
                        "tab.wishlist.delete", event -> {
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
                        },
                        "tab.wishlist.copy", event -> {
                            copyWishListToClipboard();
                            NotificationService.showInformation(NotificationType.COPY, "Wishlists", "The wishlist has been copied to your clipboard");
                        },
                        "tab.wishlist.export", event ->

                                EventService.publish(new SaveWishlistEvent(
                                        () -> TextExporter.createTextWishlist(this.wishlistNeededRaw, this.wishlistNeededEncoded, this.wishlistNeededManufactured, this.wishlistNeededCommodity),
                                        () -> CsvExporter.createCsvWishlist(this.wishlistNeededRaw, this.wishlistNeededEncoded, this.wishlistNeededManufactured, this.wishlistNeededCommodity),
                                        () -> XlsExporter.createXlsWishlist(this.wishlistNeededRaw, this.wishlistNeededEncoded, this.wishlistNeededManufactured, this.wishlistNeededCommodity)
                                ))
                ),
                Map.of(
                        "tab.wishlist.rename", this.wishlistSelect.getSelectionModel().selectedItemProperty().isEqualTo(HorizonsWishlist.ALL),
                        "tab.wishlist.copy", this.wishlistSelect.getSelectionModel().selectedItemProperty().isEqualTo(HorizonsWishlist.ALL),
                        "tab.wishlist.delete", this.wishlistSelect.getSelectionModel().selectedItemProperty().isEqualTo(HorizonsWishlist.ALL)
                )).build();
        this.menuButton.setFocusTraversable(false);

        this.shipBuilderButton = ButtonBuilder.builder().withText(LocaleService.getStringBinding("horizons.wishlist.shipbuilder")).withOnAction(event -> {
            EventService.publish(new HorizonsWishlistOpenShipBuilderEvent());
        }).build();
        this.edsyButton = ButtonBuilder.builder().withText(LocaleService.getStringBinding("horizons.wishlist.edsy")).withOnAction(event -> {
            application.getHostServices().showDocument("https://edsy.org");
        }).build();
        this.coriolisButton = ButtonBuilder.builder().withText(LocaleService.getStringBinding("horizons.wishlist.coriolis")).withOnAction(event -> {
            application.getHostServices().showDocument("https://coriolis.io");
        }).build();
        Tooltip.install(this.edsyButton, TooltipBuilder.builder().withShowDelay(Duration.millis(100D)).withText(LocaleService.getStringBinding("horizons.wishlist.edsy.tooltip")).build());
        Tooltip.install(this.coriolisButton, TooltipBuilder.builder().withShowDelay(Duration.millis(100D)).withText(LocaleService.getStringBinding("horizons.wishlist.coriolis.tooltip")).build());
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
        this.wishlistSize = APPLICATION_STATE.getPreferredCommander().map(commander -> WishlistService.getHorizonsWishlists(commander).getSelectedWishlist().getItems().size()).orElse(0);
        this.textProperty().bind(LocaleService.getSupplierStringBinding("tabs.wishlist", () -> (this.wishlistSize > 0) ? " (" + this.wishlistSize + ")" : ""));

        this.engineerBlueprintsLine = BoxBuilder.builder().withNodes(this.engineerRecipesLabel, this.engineerRecipes).buildHBox();
        this.hardpointBlueprintsLine = BoxBuilder.builder().withNodes(this.hardpointRecipesLabel, this.hardpointRecipes).buildHBox();
        this.utilityMountBlueprintsLine = BoxBuilder.builder().withNodes(this.utilityMountRecipesLabel, this.utilityMountRecipes).buildHBox();
        this.coreInternalBlueprintsLine = BoxBuilder.builder().withNodes(this.coreInternalRecipesLabel, this.coreInternalRecipes).buildHBox();
        this.optionalInternalBlueprintsLine = BoxBuilder.builder().withNodes(this.optionalInternalRecipesLabel, this.optionalInternalRecipes).buildHBox();
        this.experimentalEffectBlueprintsLine = BoxBuilder.builder().withNodes(this.experimentalEffectRecipesLabel, this.experimentalEffectRecipes).buildHBox();
        this.synthesisBlueprintsLine = BoxBuilder.builder().withNodes(this.synthesisRecipesLabel, this.synthesisRecipes).buildHBox();
        this.techbrokerBlueprintsLine = BoxBuilder.builder().withNodes(this.techbrokerRecipesLabel, this.techbrokerRecipes).buildHBox();
        HBox.setHgrow(this.engineerRecipes, Priority.ALWAYS);
        HBox.setHgrow(this.hardpointRecipes, Priority.ALWAYS);
        HBox.setHgrow(this.utilityMountRecipes, Priority.ALWAYS);
        HBox.setHgrow(this.coreInternalRecipes, Priority.ALWAYS);
        HBox.setHgrow(this.optionalInternalRecipes, Priority.ALWAYS);
        HBox.setHgrow(this.experimentalEffectRecipes, Priority.ALWAYS);
        HBox.setHgrow(this.synthesisRecipes, Priority.ALWAYS);
        HBox.setHgrow(this.techbrokerRecipes, Priority.ALWAYS);
        this.blueprints = BoxBuilder.builder().withStyleClass("wishlist-blueprints").buildVBox();

        final HBox hBoxBlueprints = BoxBuilder.builder().withNodes(this.wishlistSelect, this.menuButton, this.shipBuilderButton, this.edsyButton, this.coriolisButton).buildHBox();
        this.materialHintRed = BoxBuilder.builder().withNodes(LabelBuilder.builder().withStyleClasses("wishlist-hint-red", "wishlist-hint-box").withText(LocaleService.getStringBinding("tab.wishlist.material.hint.red")).build(), LabelBuilder.builder().withStyleClass("wishlist-hint-white").withText(LocaleService.getStringBinding("tab.wishlist.material.hint.red.explain")).build()).buildHBox();
        this.materialHintYellow = BoxBuilder.builder().withNodes(LabelBuilder.builder().withStyleClasses("wishlist-hint-yellow", "wishlist-hint-box").withText(LocaleService.getStringBinding("tab.wishlist.material.hint.yellow")).build(), LabelBuilder.builder().withStyleClass("wishlist-hint-white").withText(LocaleService.getStringBinding("tab.wishlist.material.hint.yellow.explain")).build()).buildHBox();
        this.materialHintGreen = BoxBuilder.builder().withNodes(LabelBuilder.builder().withStyleClasses("wishlist-hint-green", "wishlist-hint-box").withText(LocaleService.getStringBinding("tab.wishlist.material.hint.green")).build(), LabelBuilder.builder().withStyleClass("wishlist-hint-white").withText(LocaleService.getStringBinding("tab.wishlist.material.hint.green.explain")).build()).buildHBox();
        this.materialHintRequired = BoxBuilder.builder().withNodes(LabelBuilder.builder().withStyleClass("wishlist-hint-white").withText(LocaleService.getStringBinding("tab.wishlist.material.hint.required.explain")).build()).buildHBox();

        final PopOver popOverMaterials = new PopOver();
        final VBox contentNodeMaterials = BoxBuilder.builder().withNodes(this.materialHintRed, this.materialHintYellow, this.materialHintGreen, this.materialHintRequired).buildVBox();
        contentNodeMaterials.getStyleClass().add("help-popover");
        popOverMaterials.setContentNode(contentNodeMaterials);
        popOverMaterials.setDetachable(false);
        this.materialsHelp = ResizableImageViewBuilder.builder().withOnMouseClicked(event -> {
            popOverMaterials.show(this.materialsHelp, event.getScreenX(), event.getScreenY());
        }).withStyleClass("help-image").withImage("/images/other/help.png").build();
        final HBox hBoxMaterials = BoxBuilder.builder().withNodes(this.requiredMaterialsLabel, this.materialsHelp, this.hideCompletedCheckBox).buildHBox();
        hBoxBlueprints.spacingProperty().bind(ScalingHelper.getPixelDoubleBindingFromEm(0.25));
        hBoxMaterials.spacingProperty().bind(ScalingHelper.getPixelDoubleBindingFromEm(0.71));
        this.flows = BoxBuilder.builder().withStyleClass(WISHLIST_CONTENT_STYLE_CLASS).withNodes(this.allFlow, this.rawFlow, this.encodedFlow, this.manufacturedFlow, this.commodityFlow).buildVBox();
        this.selectedBlueprintsHintWhite = BoxBuilder.builder().withNodes(LabelBuilder.builder().withStyleClasses("wishlist-hint-white", "wishlist-hint-box").withText(LocaleService.getStringBinding("tab.wishlist.selected.blueprints.hint.white")).build(), LabelBuilder.builder().withStyleClass("wishlist-hint-white").withText(LocaleService.getStringBinding("tab.wishlist.selected.blueprints.hint.white.horizons.explain")).build()).buildHBox();
        this.selectedBlueprintsHintYellow = BoxBuilder.builder().withNodes(LabelBuilder.builder().withStyleClasses("wishlist-hint-yellow", "wishlist-hint-box").withText(LocaleService.getStringBinding("tab.wishlist.selected.blueprints.hint.yellow")).build(), LabelBuilder.builder().withStyleClass("wishlist-hint-white").withText(LocaleService.getStringBinding("tab.wishlist.selected.blueprints.hint.yellow.explain")).build()).buildHBox();
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

        this.contentChild = BoxBuilder.builder().withStyleClass(WISHLIST_CONTENT_STYLE_CLASS).withNodes(titleBar, this.blueprints, hBoxMaterials, this.flows, this.travelPathLabel, this.shortestPathFlow).buildVBox();
        this.content = BoxBuilder.builder().withStyleClass(WISHLIST_CONTENT_STYLE_CLASS).withNodes(hBoxBlueprints, this.wishlistSize > 0 ? this.contentChild : this.noBlueprint).buildVBox();
        this.scrollPane = ScrollPaneBuilder.builder()
                .withContent(this.content)
                .build();
        this.setContent(this.scrollPane);
        Observable.create((ObservableEmitter<JournalLineProcessedEvent> emitter) -> this.eventListeners.add(EventService.addListener(true, this, JournalLineProcessedEvent.class, emitter::onNext)))
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .subscribe(newValue -> Platform.runLater(this::refreshContent));

        this.wishlistBlueprints.forEach(WishlistBlueprintTemplate::onDestroy);
        this.wishlistBlueprints.clear();
        this.wishlistBlueprints.addAll(APPLICATION_STATE.getPreferredCommander()
                .map(commander -> WishlistService.getHorizonsWishlists(commander).getSelectedWishlist().getItems().stream()
                        .map(wishlistRecipe -> createWishListBlueprintTemplate(wishlistRecipe, WishlistService.getHorizonsWishlists(commander).getSelectedWishlist().getUuid()))
                        .toList()
                )
                .orElse(new ArrayList<>()));
        try {
            final List<PathItem<HorizonsBlueprintName>> pathItems = PathService.calculateHorizonsShortestPath(this.wishlistBlueprints);
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
        this.shortestPathFlow = new ShortestPathFlow<>(Expansion.HORIZONS);

        this.shortestPathFlow.visibleProperty().bind(Bindings.greaterThan(Bindings.size(this.shortestPathFlow.getItems()), 0));
        this.travelPathLabel.visibleProperty().bind(Bindings.greaterThan(Bindings.size(this.shortestPathFlow.getItems()), 0));
    }

    private void initLabels() {
        this.noBlueprint = LabelBuilder.builder().withStyleClasses(WISHLIST_HEADER_CLASS, WISHLIST_CONTENT_STYLE_CLASS).withText(LocaleService.getStringBinding("tab.wishlist.no.blueprint")).build();
        this.selectedBlueprintsLabel = LabelBuilder.builder().withStyleClass(WISHLIST_HEADER_CLASS).withText(LocaleService.getStringBinding("tab.wishlist.selected.blueprints")).build();
        this.requiredMaterialsLabel = LabelBuilder.builder().withStyleClass(WISHLIST_HEADER_CLASS).withText(LocaleService.getStringBinding("tab.wishlist.required.materials")).build();
        this.travelPathLabel = LabelBuilder.builder().withStyleClass(WISHLIST_HEADER_CLASS).withText(LocaleService.getStringBinding("tab.wishlist.travel.path")).build();
        this.engineerRecipesLabel = LabelBuilder.builder().withStyleClass(WISHLIST_CATEGORY_CLASS).withText(LocaleService.getStringBinding("blueprint.category.name.engineer_unlocks")).build();
        this.coreInternalRecipesLabel = LabelBuilder.builder().withStyleClass(WISHLIST_CATEGORY_CLASS).withText(LocaleService.getStringBinding("blueprint.category.name.core_internal")).build();
        this.experimentalEffectRecipesLabel = LabelBuilder.builder().withStyleClass(WISHLIST_CATEGORY_CLASS).withText(LocaleService.getStringBinding("blueprint.category.name.experimental_effects")).build();
        this.hardpointRecipesLabel = LabelBuilder.builder().withStyleClass(WISHLIST_CATEGORY_CLASS).withText(LocaleService.getStringBinding("blueprint.category.name.hardpoint")).build();
        this.optionalInternalRecipesLabel = LabelBuilder.builder().withStyleClass(WISHLIST_CATEGORY_CLASS).withText(LocaleService.getStringBinding("blueprint.category.name.optional_internal")).build();
        this.synthesisRecipesLabel = LabelBuilder.builder().withStyleClass(WISHLIST_CATEGORY_CLASS).withText(LocaleService.getStringBinding("blueprint.category.name.synthesis")).build();
        this.techbrokerRecipesLabel = LabelBuilder.builder().withStyleClass(WISHLIST_CATEGORY_CLASS).withText(LocaleService.getStringBinding("blueprint.category.name.techbroker")).build();
        this.utilityMountRecipesLabel = LabelBuilder.builder().withStyleClass(WISHLIST_CATEGORY_CLASS).withText(LocaleService.getStringBinding("blueprint.category.name.utility_mount")).build();


    }

    private void initEventHandling() {
        this.eventListeners.add(EventService.addListener(true, this, AfterFontSizeSetEvent.class, fontSizeEvent -> applyFontSizingHack(fontSizeEvent.getFontSize())));
        this.eventListeners.add(EventService.addListener(true, this, HorizonsWishlistSelectedEvent.class, wishlistChangedEvent -> {
            refreshWishlistBlueprints();
            refreshWishlistRecipes();
            refreshBlueprintOverview();
            refreshContent();
            EventService.publish(new HorizonsWishlistChangedEvent(this.activeWishlistUUID));
        }));
        this.eventListeners.add(EventService.addListener(true, this, HorizonsWishlistBlueprintAlteredEvent.class, wishlistChangedEvent -> {
            refreshContent();
        }));
        this.eventListeners.add(EventService.addListener(true, this, HorizonsWishlistChangedEvent.class, wishlistChangedEvent -> {
            this.activeWishlistUUID = wishlistChangedEvent.getWishlistUUID();
            APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> this.wishlistSize = WishlistService.getHorizonsWishlists(commander).getWishlist(this.activeWishlistUUID).getItems().size());

            this.textProperty().bind(LocaleService.getSupplierStringBinding("tabs.wishlist", () -> (this.wishlistSize > 0) ? " (" + this.wishlistSize + ")" : ""));
        }));
        this.eventListeners.add(EventService.addListener(true, this, HorizonsWishlistBlueprintEvent.class, wishlistEvent ->
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
                            final WishlistBlueprintTemplate<HorizonsBlueprintName> wishlistBlueprint = createWishListBlueprintTemplate(wishlistRecipe, wishlistEvent.getWishlistUUID());
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
        this.eventListeners.add(EventService.addListener(true, this, CommanderSelectedEvent.class, commanderSelectedEvent ->
        {
            final HorizonsWishlist selectedWishlist = WishlistService.getHorizonsWishlists(commanderSelectedEvent.getCommander()).getSelectedWishlist();
            this.activeWishlistUUID = selectedWishlist.getUuid();
            this.wishlistBlueprints.forEach(WishlistBlueprintTemplate::onDestroy);
            this.wishlistBlueprints.clear();
            this.wishlistBlueprints.addAll(selectedWishlist.getItems().stream()
                    .map(wishlistRecipe -> createWishListBlueprintTemplate(wishlistRecipe, this.activeWishlistUUID))
                    .toList());
            refreshWishlistSelect();
            refreshWishlistRecipes();
            refreshBlueprintOverview();
            refreshContent();
            EventService.publish(new HorizonsWishlistChangedEvent(this.activeWishlistUUID));
        }));
        this.eventListeners.add(EventService.addListener(true, this, LanguageChangedEvent.class, languageChangedEvent ->
        {
            refreshWishlistSelect();
        }));
        this.eventListeners.add(EventService.addListener(true, this, CommanderAllListedEvent.class, commanderAllListedEvent -> refreshWishlistBlueprints()));
        this.eventListeners.add(EventService.addListener(true, this, LocationChangedEvent.class, locationChangedEvent -> refreshContent()));
        this.eventListeners.add(EventService.addListener(true, this, ImportResultEvent.class, importResultEvent -> {
            if (importResultEvent.getResult().getResultType().equals(ImportResult.ResultType.SUCCESS_HORIZONS_WISHLIST) || importResultEvent.getResult().getResultType().equals(ImportResult.ResultType.SUCCESS_EDSY_WISHLIST) || importResultEvent.getResult().getResultType().equals(ImportResult.ResultType.SUCCESS_CORIOLIS_WISHLIST)) {
                refreshWishlistBlueprints();
            }
        }));
        this.eventListeners.add(EventService.addListener(true, this, HorizonsHideWishlistShortestPathItemEvent.class, event -> {
            final List<WishlistBlueprintTemplate<HorizonsBlueprintName>> pathBlueprints = getPathWishlistBlueprints(event.getPathItem());
            pathBlueprints.forEach(wishlistBlueprint -> wishlistBlueprint.setVisibility(false));
            refreshContent();
        }));
        this.eventListeners.add(EventService.addListener(true, this, HorizonsRemoveWishlistShortestPathItemEvent.class, event -> {
            final List<WishlistBlueprintTemplate<HorizonsBlueprintName>> pathBlueprints = getPathWishlistBlueprints(event.getPathItem());
            pathBlueprints.forEach(WishlistBlueprintTemplate::remove);
        }));
        this.eventListeners.add(EventService.addListener(true, this, EngineerPinEvent.class, event -> {

            refreshContent();
        }));

        this.eventListeners.add(EventService.addListener(true, this, HorizonsWishlistSearchEvent.class, horizonsWishlistSearchEvent -> {
            this.currentSearch = horizonsWishlistSearchEvent.getSearch();
            Platform.runLater(this::refreshContent);
        }));

        this.eventListeners.add(EventService.addListener(true, this, HorizonsWishlistCreatedEvent.class, event ->
        {
            refreshWishlistSelect();
        }));
    }

    private List<WishlistBlueprintTemplate<HorizonsBlueprintName>> getPathWishlistBlueprints(final PathItem<HorizonsBlueprintName> pathItem) {
        return pathItem.getRecipes().entrySet().stream()
                .flatMap(recipeEntry -> HorizonsWishlistTab.this.wishlistBlueprints.stream()
                        .filter(w -> pathItem.getBlueprints().contains(w))
                ).toList();
    }

    private void refreshWishlistBlueprints() {
        this.wishlistBlueprints.forEach(WishlistBlueprintTemplate::onDestroy);
        this.wishlistBlueprints.clear();
        final List<WishlistBlueprintTemplate<HorizonsBlueprintName>> newWishlistBlueprints = APPLICATION_STATE.getPreferredCommander()
                .map(commander -> {
                    final HorizonsWishlist selectedWishlist = WishlistService.getHorizonsWishlists(commander).getSelectedWishlist();
                    this.activeWishlistUUID = selectedWishlist.getUuid();
                    return selectedWishlist.getItems().stream()
                            .map(wishlistRecipe -> createWishListBlueprintTemplate(wishlistRecipe, this.activeWishlistUUID))
                            .toList();
                })
                .orElse(Collections.emptyList());
        this.wishlistBlueprints.addAll(newWishlistBlueprints);
        refreshWishlistSelect();
        refreshWishlistRecipes();
        refreshBlueprintOverview();
        refreshContent();
        EventService.publish(new HorizonsWishlistChangedEvent(this.activeWishlistUUID));
    }

    private WishlistBlueprintTemplate<HorizonsBlueprintName> createWishListBlueprintTemplate(final WishlistBlueprint<HorizonsBlueprintName> wishlistRecipe, final String wishlistUUID) {
        if (wishlistRecipe instanceof HorizonsModuleWishlistBlueprint horizonsModuleWishlistBlueprint) {
            return new HorizonsWishlistModuleBlueprintTemplate(wishlistUUID, horizonsModuleWishlistBlueprint);
        }
        return new HorizonsWishlistBlueprintTemplate(wishlistUUID, wishlistRecipe);
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

    private void refreshWishlistRecipes() {
        this.engineerRecipes.getChildren().clear();
        this.hardpointRecipes.getChildren().clear();
        this.utilityMountRecipes.getChildren().clear();
        this.coreInternalRecipes.getChildren().clear();
        this.optionalInternalRecipes.getChildren().clear();
        this.experimentalEffectRecipes.getChildren().clear();
        this.synthesisRecipes.getChildren().clear();
        this.techbrokerRecipes.getChildren().clear();
        this.engineerRecipes.getChildren().addAll((List<Node>) (List<?>) this.wishlistBlueprints.stream().filter(wishlistBlueprint -> BlueprintCategory.ENGINEER_UNLOCKS.equals(wishlistBlueprint.getRecipeCategory())).toList());
        this.hardpointRecipes.getChildren().addAll((List<Node>) (List<?>) this.wishlistBlueprints.stream().filter(wishlistBlueprint -> BlueprintCategory.HARDPOINT.equals(wishlistBlueprint.getRecipeCategory())).toList());
        this.utilityMountRecipes.getChildren().addAll((List<Node>) (List<?>) this.wishlistBlueprints.stream().filter(wishlistBlueprint -> BlueprintCategory.UTILITY_MOUNT.equals(wishlistBlueprint.getRecipeCategory())).toList());
        this.coreInternalRecipes.getChildren().addAll((List<Node>) (List<?>) this.wishlistBlueprints.stream().filter(wishlistBlueprint -> BlueprintCategory.CORE_INTERNAL.equals(wishlistBlueprint.getRecipeCategory())).toList());
        this.optionalInternalRecipes.getChildren().addAll((List<Node>) (List<?>) this.wishlistBlueprints.stream().filter(wishlistBlueprint -> BlueprintCategory.OPTIONAL_INTERNAL.equals(wishlistBlueprint.getRecipeCategory())).toList());
        this.experimentalEffectRecipes.getChildren().addAll((List<Node>) (List<?>) this.wishlistBlueprints.stream().filter(wishlistBlueprint -> BlueprintCategory.EXPERIMENTAL_EFFECTS.equals(wishlistBlueprint.getRecipeCategory())).toList());
        this.synthesisRecipes.getChildren().addAll((List<Node>) (List<?>) this.wishlistBlueprints.stream().filter(wishlistBlueprint -> BlueprintCategory.SYNTHESIS.equals(wishlistBlueprint.getRecipeCategory())).toList());
        this.techbrokerRecipes.getChildren().addAll((List<Node>) (List<?>) this.wishlistBlueprints.stream().filter(wishlistBlueprint -> BlueprintCategory.TECHBROKER.equals(wishlistBlueprint.getRecipeCategory())).toList());
    }


    private void addBluePrint(final WishlistBlueprintTemplate<HorizonsBlueprintName> wishlistBlueprint) {
        switch (wishlistBlueprint.getRecipeCategory()) {
            case ENGINEER_UNLOCKS -> this.engineerRecipes.getChildren().add((Node) wishlistBlueprint);
            case HARDPOINT -> this.hardpointRecipes.getChildren().add((Node) wishlistBlueprint);
            case UTILITY_MOUNT -> this.utilityMountRecipes.getChildren().add((Node) wishlistBlueprint);
            case CORE_INTERNAL -> this.coreInternalRecipes.getChildren().add((Node) wishlistBlueprint);
            case OPTIONAL_INTERNAL -> this.optionalInternalRecipes.getChildren().add((Node) wishlistBlueprint);
            case EXPERIMENTAL_EFFECTS -> this.experimentalEffectRecipes.getChildren().add((Node) wishlistBlueprint);
            case SYNTHESIS -> this.synthesisRecipes.getChildren().add((Node) wishlistBlueprint);
            case TECHBROKER -> this.techbrokerRecipes.getChildren().add((Node) wishlistBlueprint);
            default -> throw new IllegalArgumentException("Unsupported Category");
        }
        refreshBlueprintOverview();
    }

    private void removeBluePrint(final WishlistBlueprintTemplate<HorizonsBlueprintName> wishlistBlueprint) {
        switch (wishlistBlueprint.getRecipeCategory()) {
            case ENGINEER_UNLOCKS -> this.engineerRecipes.getChildren().remove(wishlistBlueprint);
            case HARDPOINT -> this.hardpointRecipes.getChildren().remove(wishlistBlueprint);
            case UTILITY_MOUNT -> this.utilityMountRecipes.getChildren().remove(wishlistBlueprint);
            case CORE_INTERNAL -> this.coreInternalRecipes.getChildren().remove(wishlistBlueprint);
            case OPTIONAL_INTERNAL -> this.optionalInternalRecipes.getChildren().remove(wishlistBlueprint);
            case EXPERIMENTAL_EFFECTS -> this.experimentalEffectRecipes.getChildren().remove(wishlistBlueprint);
            case SYNTHESIS -> this.synthesisRecipes.getChildren().remove(wishlistBlueprint);
            case TECHBROKER -> this.techbrokerRecipes.getChildren().remove(wishlistBlueprint);
            default -> throw new IllegalArgumentException("Unsupported Category");
        }
        refreshBlueprintOverview();
    }

    private void refreshBlueprintOverview() {
        this.blueprints.getChildren().clear();
        for (final HBox blueprintList : List.of(this.engineerBlueprintsLine, this.hardpointBlueprintsLine, this.utilityMountBlueprintsLine, this.coreInternalBlueprintsLine, this.optionalInternalBlueprintsLine, this.experimentalEffectBlueprintsLine, this.synthesisBlueprintsLine, this.techbrokerBlueprintsLine)) {
            if (!((FlowPane) blueprintList.getChildren().get(1)).getChildren().isEmpty()) {
                this.blueprints.getChildren().add(blueprintList);
                final ArrayList<HorizonsWishlistBlueprintTemplate> wishlistItems = (ArrayList<HorizonsWishlistBlueprintTemplate>) (ArrayList<?>) new ArrayList<>(((FlowPane) blueprintList.getChildren().get(1)).getChildren());
                wishlistItems
                        .sort(Comparator
                                .comparing(node -> LocaleService.getLocalizedStringForCurrentLocale(((WishlistBlueprintTemplate<HorizonsBlueprintName>) node).getRecipeName().getLocalizationKey()))
                                .thenComparing(node -> ((WishlistBlueprintTemplate<HorizonsBlueprintName>) node).getSequenceID()));
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
        this.allFlow.getChildren().forEach(node -> ((HorizonsWishlistIngredient) node).destroy());
        this.rawFlow.getChildren().forEach(node -> ((HorizonsWishlistIngredient) node).destroy());
        this.encodedFlow.getChildren().forEach(node -> ((HorizonsWishlistIngredient) node).destroy());
        this.manufacturedFlow.getChildren().forEach(node -> ((HorizonsWishlistIngredient) node).destroy());
        this.commodityFlow.getChildren().forEach(node -> ((HorizonsWishlistIngredient) node).destroy());
        this.allFlow.getChildren().clear();
        this.rawFlow.getChildren().clear();
        this.encodedFlow.getChildren().clear();
        this.manufacturedFlow.getChildren().clear();
        this.commodityFlow.getChildren().clear();
        this.wishlistNeededAll.clear();
        this.wishlistNeededRaw.clear();
        this.wishlistNeededEncoded.clear();
        this.wishlistNeededManufactured.clear();
        this.wishlistNeededCommodity.clear();
        final List<PathItem<HorizonsBlueprintName>> pathItems = new ArrayList<>();
        try {
            pathItems.addAll(PathService.calculateHorizonsShortestPath(this.wishlistBlueprints));
            this.shortestPathFlow.setItems(pathItems);
        } catch (final IllegalArgumentException ex) {
            log.error("Failed to generate path", ex);
        }
        this.wishlistBlueprints.forEach(wishlistBlueprint -> wishlistBlueprint.setEngineer(getCurrentEngineerForRecipe(wishlistBlueprint.getPrimaryRecipe(), pathItems).orElseGet(() -> getBestEngineer(wishlistBlueprint.getPrimaryRecipe()))));
        this.wishlistBlueprints.stream()
                .filter(WishlistBlueprintTemplate::isVisibleBlueprint)
                .filter(temp -> Objects.nonNull(temp.getPrimaryRecipe()))
                .forEach(template -> template.getRecipe().entrySet()
                        .forEach(entry -> {
                            final Blueprint<HorizonsBlueprintName> recipe = entry.getKey();
                            final Double percentageToComplete = entry.getValue();
                            final Engineer engineer = getCurrentEngineerForRecipe(recipe, pathItems).orElseGet(() -> getWorstEngineer(recipe));
                            final HorizonsBlueprintGrade grade = ((HorizonsBlueprint) recipe).getHorizonsBlueprintGrade();
                            //lowest engineer rank available for recipe
                            final Integer minRank = ((HorizonsBlueprint) recipe).getEngineers().stream().map(eng -> ApplicationState.getInstance().getEngineerRank(eng)).min(Comparator.comparingInt(Integer::intValue)).orElse(0);
                            final Integer maxRank = ((HorizonsBlueprint) recipe).getEngineers().stream().map(eng -> ApplicationState.getInstance().getEngineerRank(eng)).max(Comparator.comparingInt(Integer::intValue)).orElse(0);
                            final int minRoll;
                            final int currentRoll;
                            final int maxRoll;
                            if (percentageToComplete < 0.2 && ((HorizonsBlueprint) template.getPrimaryRecipe()).getHorizonsBlueprintGrade().getGrade() > ((HorizonsBlueprint) recipe).getHorizonsBlueprintGrade().getGrade()) {
                                minRoll = 0;
                                currentRoll = 0;
                                maxRoll = 0;
                            } else if (engineer != null && recipe instanceof HorizonsModuleBlueprint) {
                                minRoll = (int) Math.ceil(percentageToComplete * grade.getNumberOfRolls(maxRank, ((HorizonsModuleBlueprint) recipe).getHorizonsBlueprintType()));
                                currentRoll = (int) Math.ceil(percentageToComplete * grade.getNumberOfRolls(engineer, ((HorizonsModuleBlueprint) recipe).getHorizonsBlueprintType()));
                                maxRoll = (int) Math.ceil(percentageToComplete * grade.getNumberOfRolls(minRank, ((HorizonsModuleBlueprint) recipe).getHorizonsBlueprintType()));
                            } else {
                                minRoll = 1;
                                currentRoll = 1;
                                maxRoll = 1;
                            }
                            if (this.currentSearch.getWishlistMaterialGrouping().equals(WishlistMaterialGrouping.CATEGORY)) {
                                ((HorizonsBlueprint) recipe).getMaterialCollection(Raw.class).forEach((key, value) -> this.wishlistNeededRaw.merge((Raw) key, new WishlistMaterial(minRoll * value, currentRoll * value, maxRoll * value), WishlistMaterial::sum));
                                ((HorizonsBlueprint) recipe).getMaterialCollection(Encoded.class).forEach((key, value) -> this.wishlistNeededEncoded.merge((Encoded) key, new WishlistMaterial(minRoll * value, currentRoll * value, maxRoll * value), WishlistMaterial::sum));
                                ((HorizonsBlueprint) recipe).getMaterialCollection(Manufactured.class).forEach((key, value) -> this.wishlistNeededManufactured.merge((Manufactured) key, new WishlistMaterial(minRoll * value, currentRoll * value, maxRoll * value), WishlistMaterial::sum));
                                ((HorizonsBlueprint) recipe).getMaterialCollection(Commodity.class).forEach((key, value) -> this.wishlistNeededCommodity.merge((Commodity) key, new WishlistMaterial(minRoll * value, currentRoll * value, maxRoll * value), WishlistMaterial::sum));
                            } else if (this.currentSearch.getWishlistMaterialGrouping().equals(WishlistMaterialGrouping.NONE)) {
                                ((HorizonsBlueprint) recipe).getMaterialCollection(HorizonsMaterial.class).forEach((key, value) -> this.wishlistNeededAll.merge(key, new WishlistMaterial(minRoll * value, currentRoll * value, maxRoll * value), WishlistMaterial::sum));
                            }
                        }));
        final List<Ingredient> allIngredients = new ArrayList<>();
        if (this.currentSearch.getWishlistMaterialGrouping().equals(WishlistMaterialGrouping.CATEGORY)) {
            final List<HorizonsWishlistIngredient> ingredientsRaw = this.wishlistNeededRaw.entrySet().stream()
                    .filter(entry -> !this.hideCompleted.get() || StorageService.getMaterialCount(entry.getKey()) < entry.getValue().getRequired())
                    .filter(wishlistItem -> wishlistItem.getValue().getRequired() > 0)
                    .map(wishlistItem -> new HorizonsWishlistIngredient(HorizonsStorageType.forMaterial(wishlistItem.getKey()), wishlistItem.getKey(), wishlistItem.getValue().getMinimum(), wishlistItem.getValue().getRequired(), wishlistItem.getValue().getMaximum(), StorageService.getMaterialCount(wishlistItem.getKey())))
                    .toList();
            final List<HorizonsWishlistIngredient> ingredientsEncoded = this.wishlistNeededEncoded.entrySet().stream()
                    .filter(entry -> !this.hideCompleted.get() || StorageService.getMaterialCount(entry.getKey()) < entry.getValue().getRequired())
                    .filter(wishlistItem -> wishlistItem.getValue().getRequired() > 0)
                    .map(wishlistItem -> new HorizonsWishlistIngredient(HorizonsStorageType.forMaterial(wishlistItem.getKey()), wishlistItem.getKey(), wishlistItem.getValue().getMinimum(), wishlistItem.getValue().getRequired(), wishlistItem.getValue().getMaximum(), StorageService.getMaterialCount(wishlistItem.getKey())))
                    .toList();
            final List<HorizonsWishlistIngredient> ingredientsManufactured = this.wishlistNeededManufactured.entrySet().stream()
                    .filter(entry -> !this.hideCompleted.get() || StorageService.getMaterialCount(entry.getKey()) < entry.getValue().getRequired())
                    .filter(wishlistItem -> wishlistItem.getValue().getRequired() > 0)
                    .map(wishlistItem -> new HorizonsWishlistIngredient(HorizonsStorageType.forMaterial(wishlistItem.getKey()), wishlistItem.getKey(), wishlistItem.getValue().getMinimum(), wishlistItem.getValue().getRequired(), wishlistItem.getValue().getMaximum(), StorageService.getMaterialCount(wishlistItem.getKey())))
                    .toList();
            final List<HorizonsWishlistIngredient> ingredientsCommodities = this.wishlistNeededCommodity.entrySet().stream()
                    //might be missing: StorageService.getCommodityCount(entry.getKey(), StoragePool.FLEETCARRIER) +
                    .filter(entry -> !this.hideCompleted.get() || StorageService.getCommodityCount(entry.getKey(), StoragePool.SHIP) < entry.getValue().getRequired())
                    .filter(wishlistItem -> wishlistItem.getValue().getRequired() > 0)
                    .map(wishlistItem -> new HorizonsWishlistIngredient(HorizonsStorageType.forMaterial(wishlistItem.getKey()), wishlistItem.getKey(), wishlistItem.getValue().getMinimum(), wishlistItem.getValue().getRequired(), wishlistItem.getValue().getMaximum(), StorageService.getCommodityCount(wishlistItem.getKey(),StoragePool.SHIP)))
                    .toList();
            allIngredients.addAll(ingredientsRaw);
            allIngredients.addAll(ingredientsEncoded);
            allIngredients.addAll(ingredientsManufactured);
            allIngredients.addAll(ingredientsCommodities);
            this.rawFlow.getChildren().addAll(ingredientsRaw.stream().sorted(HorizonsWishlistMaterialSort.getSort(this.currentSearch)).toList());
            this.encodedFlow.getChildren().addAll(ingredientsEncoded.stream().sorted(HorizonsWishlistMaterialSort.getSort(this.currentSearch)).toList());
            this.manufacturedFlow.getChildren().addAll(ingredientsManufactured.stream().sorted(HorizonsWishlistMaterialSort.getSort(this.currentSearch)).toList());
            this.commodityFlow.getChildren().addAll(ingredientsCommodities.stream().sorted(HorizonsWishlistMaterialSort.getSort(this.currentSearch)).toList());
        } else if (this.currentSearch.getWishlistMaterialGrouping().equals(WishlistMaterialGrouping.NONE)) {
            final List<HorizonsWishlistIngredient> ingredientsAll = this.wishlistNeededAll.entrySet().stream()
                    //might be missing: StorageService.getCommodityCount(commodity, StoragePool.FLEETCARRIER) +
                    .filter(entry -> !this.hideCompleted.get() || ((entry.getKey() instanceof Commodity commodity) ? StorageService.getCommodityCount(commodity, StoragePool.SHIP) : StorageService.getMaterialCount(entry.getKey())) < entry.getValue().getRequired())
                    .filter(wishlistItem -> wishlistItem.getValue().getRequired() > 0)
                    .map(wishlistItem -> {
                        final Integer materialCount = (wishlistItem.getKey() instanceof Commodity commodity) ? StorageService.getCommodityCount(commodity, StoragePool.FLEETCARRIER) + StorageService.getCommodityCount(commodity, StoragePool.SHIP) : StorageService.getMaterialCount(wishlistItem.getKey());
                        return new HorizonsWishlistIngredient(HorizonsStorageType.forMaterial(wishlistItem.getKey()), wishlistItem.getKey(), wishlistItem.getValue().getMinimum(), wishlistItem.getValue().getRequired(), wishlistItem.getValue().getMaximum(), materialCount);
                    })
                    .toList();
            allIngredients.addAll(ingredientsAll);
            this.allFlow.getChildren().addAll(ingredientsAll.stream().sorted(HorizonsWishlistMaterialSort.getSort(this.currentSearch)).toList());
        }

        this.wishlistBlueprints.forEach(wishlistBlueprint -> wishlistBlueprint.addWishlistIngredients(allIngredients));
        allIngredients.forEach(horizonsWishlistIngredient -> ((HorizonsWishlistIngredient) horizonsWishlistIngredient).searchHighlight(!this.currentSearch.getQuery().isBlank() && LocaleService.getLocalizedStringForCurrentLocale(((HorizonsWishlistIngredient) horizonsWishlistIngredient).getHorizonsMaterial().getLocalizationKey()).toLowerCase().contains(this.currentSearch.getQuery().toLowerCase())));


        removeAndAddFlows();

    }

    private Optional<Engineer> getCurrentEngineerForRecipe(Blueprint<HorizonsBlueprintName> recipe, List<PathItem<HorizonsBlueprintName>> pathItems) {
        if (recipe == null) {
            return null;
        }
        return pathItems.stream()
                .filter(pathItem -> pathItem.getRecipes().keySet().stream().anyMatch(blueprint -> blueprint.getBlueprintName().equals(recipe.getBlueprintName()) && ((HorizonsBlueprint) blueprint).getHorizonsBlueprintType().equals(((HorizonsBlueprint) recipe).getHorizonsBlueprintType())))
                .findFirst()
                .map(pathItem -> /*pathItem.getEngineer().equals(Engineer.UNKNOWN) ? null :*/ pathItem.getEngineer());
    }

    private Engineer getBestEngineer(Blueprint<HorizonsBlueprintName> recipe) {
        if (recipe instanceof HorizonsWishlistModuleBlueprintTemplate moduleBlueprint) {
            recipe = moduleBlueprint.getPrimaryRecipe();
        }
        if (!(recipe instanceof HorizonsModuleBlueprint)) {
            return null;
        }
        return ((HorizonsModuleBlueprint) recipe).getEngineers().stream().max(Comparator.comparingInt(eng -> ApplicationState.getInstance().getEngineerRank(eng))).orElse(null);

    }
    private Engineer getWorstEngineer(Blueprint<HorizonsBlueprintName> recipe) {
        if (recipe instanceof HorizonsWishlistModuleBlueprintTemplate moduleBlueprint) {
            recipe = moduleBlueprint.getPrimaryRecipe();
        }
        if (!(recipe instanceof HorizonsModuleBlueprint)) {
            return null;
        }
        return ((HorizonsModuleBlueprint) recipe).getEngineers().stream().min(Comparator.comparingInt(eng -> ApplicationState.getInstance().getEngineerRank(eng))).orElse(null);

    }

    private void removeAndAddFlows() {
        this.flows.getChildren().removeAll(this.allFlow, this.rawFlow, this.encodedFlow, this.manufacturedFlow, this.commodityFlow);
        for (final FlowPane flowPane : new FlowPane[]{this.allFlow, this.rawFlow, this.encodedFlow, this.manufacturedFlow, this.commodityFlow}) {
            if (!flowPane.getChildren().isEmpty()) {
                this.flows.getChildren().add(flowPane);
            }
        }
    }

    @Override
    public HorizonsTabs getTabType() {
        return HorizonsTabs.WISHLIST;
    }
}