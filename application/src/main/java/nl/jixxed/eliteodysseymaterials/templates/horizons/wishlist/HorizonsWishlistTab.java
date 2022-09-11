package nl.jixxed.eliteodysseymaterials.templates.horizons.wishlist;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.schedulers.Schedulers;
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
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.export.TextExporter;
import nl.jixxed.eliteodysseymaterials.helper.ClipboardHelper;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.service.*;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
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
    private int wishlistSize;
    private final List<WishlistBlueprintTemplate<HorizonsBlueprintName>> wishlistBlueprints = new ArrayList<>();
    private final AtomicBoolean hideCompleted = new AtomicBoolean();
    private final Map<Raw, Integer> wishlistNeededRaw = new EnumMap<>(Raw.class);
    private final Map<Encoded, Integer> wishlistNeededEncoded = new EnumMap<>(Encoded.class);
    private final Map<Manufactured, Integer> wishlistNeededManufactured = new EnumMap<>(Manufactured.class);
    private final Map<Commodity, Integer> wishlistNeededCommmodity = new EnumMap<>(Commodity.class);

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

    static {
        NUMBER_FORMAT.setMaximumFractionDigits(2);
    }

    private MenuButton menuButton;

    public HorizonsWishlistTab() {
        initComponents();
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

    private void initComponents() {
        initLabels();
        initShortestPathTable();
        final Set<HorizonsWishlist> items = APPLICATION_STATE.getPreferredCommander()
                .map(commander -> APPLICATION_STATE.getHorizonsWishlists(commander.getFid()).getAllWishlists())
                .orElse(Collections.emptySet());
        this.wishlistSelect = ComboBoxBuilder.builder(HorizonsWishlist.class)
                .withStyleClass("wishlist-select")
                .withItemsProperty(FXCollections.observableArrayList(items.stream().sorted(Comparator.comparing(HorizonsWishlist::getName)).toList()))
                .withValueChangeListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                            this.activeWishlistUUID = newValue.getUuid();
                            APPLICATION_STATE.selectHorizonsWishlist(this.activeWishlistUUID, commander.getFid());
                            EventService.publish(new HorizonsWishlistSelectedEvent(this.activeWishlistUUID));
                        });
                    }
                })
                .build();
        this.engineerRecipes = FlowPaneBuilder.builder().withStyleClass(WISHLIST_RECIPES_STYLE_CLASS).build();
        this.hardpointRecipes = FlowPaneBuilder.builder().withStyleClass(WISHLIST_RECIPES_STYLE_CLASS).build();
        this.utilityMountRecipes = FlowPaneBuilder.builder().withStyleClass(WISHLIST_RECIPES_STYLE_CLASS).build();
        this.coreInternalRecipes = FlowPaneBuilder.builder().withStyleClass(WISHLIST_RECIPES_STYLE_CLASS).build();
        this.optionalInternalRecipes = FlowPaneBuilder.builder().withStyleClass(WISHLIST_RECIPES_STYLE_CLASS).build();
        this.experimentalEffectRecipes = FlowPaneBuilder.builder().withStyleClass(WISHLIST_RECIPES_STYLE_CLASS).build();
        this.synthesisRecipes = FlowPaneBuilder.builder().withStyleClass(WISHLIST_RECIPES_STYLE_CLASS).build();
        this.techbrokerRecipes = FlowPaneBuilder.builder().withStyleClass(WISHLIST_RECIPES_STYLE_CLASS).build();
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
                                final HorizonsWishlists wishlists = APPLICATION_STATE.getHorizonsWishlists(commander.getFid());
                                wishlists.createWishlist(textField.getText());
                                APPLICATION_STATE.saveHorizonsWishlists(commander.getFid(), wishlists);
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
                                final HorizonsWishlists wishlists = APPLICATION_STATE.getHorizonsWishlists(commander.getFid());
                                wishlists.renameWishlist(this.activeWishlistUUID, textField.getText());
                                APPLICATION_STATE.saveHorizonsWishlists(commander.getFid(), wishlists);
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
                                    APPLICATION_STATE.deleteHorizonsWishlist(this.activeWishlistUUID, commander.getFid());
                                    Platform.runLater(this::refreshWishlistSelect);
                                });
                            }
                        },
                        "tab.wishlist.copy", event -> {
                            copyWishListToClipboard();
                            NotificationService.showInformation(NotificationType.COPY, "Wishlists", "The wishlist has been copied to your clipboard");
                        },
                        "tab.wishlist.export", event ->
                                EventService.publish(new SaveWishlistEvent(TextExporter.createTextWishlist(this.wishlistNeededRaw, this.wishlistNeededEncoded, this.wishlistNeededManufactured, this.wishlistNeededCommmodity)))
                ),
                Map.of(
                        "tab.wishlist.rename", this.wishlistSelect.getSelectionModel().selectedItemProperty().isEqualTo(HorizonsWishlist.ALL),
                        "tab.wishlist.copy", this.wishlistSelect.getSelectionModel().selectedItemProperty().isEqualTo(HorizonsWishlist.ALL),
                        "tab.wishlist.delete", this.wishlistSelect.getSelectionModel().selectedItemProperty().isEqualTo(HorizonsWishlist.ALL)
                )).build();
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
        this.wishlistSize = APPLICATION_STATE.getPreferredCommander().map(commander -> APPLICATION_STATE.getHorizonsWishlists(commander.getFid()).getSelectedWishlist().getItems().size()).orElse(0);
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

        final HBox hBoxBlueprints = BoxBuilder.builder().withNodes(this.wishlistSelect, this.menuButton).buildHBox();
        final HBox hBoxMaterials = BoxBuilder.builder().withNodes(this.requiredMaterialsLabel, this.hideCompletedCheckBox).buildHBox();
        hBoxBlueprints.spacingProperty().bind(ScalingHelper.getPixelDoubleBindingFromEm(0.25));
        hBoxMaterials.spacingProperty().bind(ScalingHelper.getPixelDoubleBindingFromEm(0.71));
        this.flows = BoxBuilder.builder().withStyleClass(WISHLIST_CONTENT_STYLE_CLASS).withNodes(this.rawFlow, this.encodedFlow, this.manufacturedFlow, this.commodityFlow).buildVBox();
        this.selectedBlueprintsHintWhite = BoxBuilder.builder().withNodes(LabelBuilder.builder().withStyleClasses("wishlist-hint-white", "wishlist-hint-box").withText(LocaleService.getStringBinding("tab.wishlist.selected.blueprints.hint.white")).build(), LabelBuilder.builder().withStyleClass("wishlist-hint-white").withText(LocaleService.getStringBinding("tab.wishlist.selected.blueprints.hint.white.explain")).build()).buildHBox();
        this.selectedBlueprintsHintYellow = BoxBuilder.builder().withNodes(LabelBuilder.builder().withStyleClasses("wishlist-hint-yellow", "wishlist-hint-box").withText(LocaleService.getStringBinding("tab.wishlist.selected.blueprints.hint.yellow")).build(), LabelBuilder.builder().withStyleClass("wishlist-hint-white").withText(LocaleService.getStringBinding("tab.wishlist.selected.blueprints.hint.yellow.explain")).build()).buildHBox();
        this.selectedBlueprintsHintGreen = BoxBuilder.builder().withNodes(LabelBuilder.builder().withStyleClasses("wishlist-hint-green", "wishlist-hint-box").withText(LocaleService.getStringBinding("tab.wishlist.selected.blueprints.hint.green")).build(), LabelBuilder.builder().withStyleClass("wishlist-hint-white").withText(LocaleService.getStringBinding("tab.wishlist.selected.blueprints.hint.green.explain")).build()).buildHBox();

        this.contentChild = BoxBuilder.builder().withStyleClass(WISHLIST_CONTENT_STYLE_CLASS).withNodes(this.selectedBlueprintsLabel, this.selectedBlueprintsHintWhite, this.selectedBlueprintsHintYellow, this.selectedBlueprintsHintGreen, this.blueprints, hBoxMaterials, this.flows, this.travelPathLabel, this.shortestPathFlow).buildVBox();
        this.content = BoxBuilder.builder().withStyleClass(WISHLIST_CONTENT_STYLE_CLASS).withNodes(hBoxBlueprints, this.wishlistSize > 0 ? this.contentChild : this.noBlueprint).buildVBox();
        this.scrollPane = ScrollPaneBuilder.builder()
                .withContent(this.content)
                .build();
        this.setContent(this.scrollPane);
        Observable.create((ObservableEmitter<JournalLineProcessedEvent> emitter) -> EventService.addListener(this, JournalLineProcessedEvent.class, emitter::onNext))
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .subscribe(newValue -> Platform.runLater(this::refreshContent));

        this.wishlistBlueprints.forEach(WishlistBlueprintTemplate::onDestroy);
        this.wishlistBlueprints.clear();
        this.wishlistBlueprints.addAll(APPLICATION_STATE.getPreferredCommander()
                .map(commander -> APPLICATION_STATE.getHorizonsWishlists(commander.getFid()).getSelectedWishlist().getItems().stream()
                        .map(wishlistRecipe -> createWishListBlueprintTemplate(wishlistRecipe, APPLICATION_STATE.getHorizonsWishlists(commander.getFid()).getSelectedWishlist().getUuid()))
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
        EventService.addListener(this, AfterFontSizeSetEvent.class, fontSizeEvent -> applyFontSizingHack(fontSizeEvent.getFontSize()));
        EventService.addListener(this, HorizonsWishlistSelectedEvent.class, wishlistChangedEvent -> {
            refreshWishlistBlueprints();
            refreshWishlistRecipes();
            refreshBlueprintOverview();
            refreshContent();
            EventService.publish(new HorizonsWishlistChangedEvent(this.activeWishlistUUID));
        });
        EventService.addListener(this, HorizonsWishlistBlueprintAlteredEvent.class, wishlistChangedEvent -> {
            refreshContent();
        });
        EventService.addListener(this, HorizonsWishlistChangedEvent.class, wishlistChangedEvent -> {
            this.activeWishlistUUID = wishlistChangedEvent.getWishlistUUID();
            APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> this.wishlistSize = APPLICATION_STATE.getHorizonsWishlists(commander.getFid()).getWishlist(this.activeWishlistUUID).getItems().size());

            this.textProperty().bind(LocaleService.getSupplierStringBinding("tabs.wishlist", () -> (this.wishlistSize > 0) ? " (" + this.wishlistSize + ")" : ""));
        });
        EventService.addListener(this, HorizonsWishlistBlueprintEvent.class, wishlistEvent ->
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
        });
        EventService.addListener(this, CommanderSelectedEvent.class, commanderSelectedEvent ->
        {
            final String fid = commanderSelectedEvent.getCommander().getFid();
            final HorizonsWishlist selectedWishlist = APPLICATION_STATE.getHorizonsWishlists(fid).getSelectedWishlist();
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
        });
        EventService.addListener(this, LanguageChangedEvent.class, languageChangedEvent ->
        {
            refreshWishlistSelect();
        });
        EventService.addListener(this, CommanderAllListedEvent.class, commanderAllListedEvent -> refreshWishlistBlueprints());
        EventService.addListener(this, LocationChangedEvent.class, locationChangedEvent -> refreshContent());
        EventService.addListener(this, ImportResultEvent.class, importResultEvent -> {
            if (importResultEvent.getResult().getResultType().equals(ImportResult.ResultType.SUCCESS_HORIZONS_WISHLIST) || importResultEvent.getResult().getResultType().equals(ImportResult.ResultType.SUCCESS_EDSY_WISHLIST)) {
                refreshWishlistBlueprints();
            }
        });
        EventService.addListener(this, HorizonsHideWishlistShortestPathItemEvent.class, event -> {
            final List<WishlistBlueprintTemplate<HorizonsBlueprintName>> pathBlueprints = getPathWishlistBlueprints(event.getPathItem());
            pathBlueprints.forEach(wishlistBlueprint -> wishlistBlueprint.setVisibility(false));
            refreshContent();
        });
        EventService.addListener(this, HorizonsRemoveWishlistShortestPathItemEvent.class, event -> {
            final List<WishlistBlueprintTemplate<HorizonsBlueprintName>> pathBlueprints = getPathWishlistBlueprints(event.getPathItem());
            pathBlueprints.forEach(WishlistBlueprintTemplate::remove);
        });
        EventService.addListener(this, EngineerPinEvent.class, event -> {

            refreshContent();
        });

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
                    final HorizonsWishlist selectedWishlist = APPLICATION_STATE.getHorizonsWishlists(commander.getFid()).getSelectedWishlist();
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
            final HorizonsWishlists wishlists = APPLICATION_STATE.getHorizonsWishlists(commander.getFid());
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
        this.rawFlow.getChildren().forEach(node -> ((HorizonsWishlistIngredient) node).destroy());
        this.encodedFlow.getChildren().forEach(node -> ((HorizonsWishlistIngredient) node).destroy());
        this.manufacturedFlow.getChildren().forEach(node -> ((HorizonsWishlistIngredient) node).destroy());
        this.commodityFlow.getChildren().forEach(node -> ((HorizonsWishlistIngredient) node).destroy());
        this.rawFlow.getChildren().clear();
        this.encodedFlow.getChildren().clear();
        this.manufacturedFlow.getChildren().clear();
        this.commodityFlow.getChildren().clear();
        this.wishlistNeededRaw.clear();
        this.wishlistNeededEncoded.clear();
        this.wishlistNeededManufactured.clear();
        this.wishlistNeededCommmodity.clear();
        this.wishlistBlueprints.stream()
                .filter(WishlistBlueprintTemplate::isVisibleBlueprint)
                .flatMap(template -> template.getRecipe().stream())
                .forEach(recipe -> {
                            ((HorizonsBlueprint) recipe).getMaterialCollection(Raw.class).forEach((key, value1) -> this.wishlistNeededRaw.merge((Raw) key, value1, Integer::sum));
                            ((HorizonsBlueprint) recipe).getMaterialCollection(Encoded.class).forEach((key, value1) -> this.wishlistNeededEncoded.merge((Encoded) key, value1, Integer::sum));
                            ((HorizonsBlueprint) recipe).getMaterialCollection(Manufactured.class).forEach((key, value1) -> this.wishlistNeededManufactured.merge((Manufactured) key, value1, Integer::sum));
                            ((HorizonsBlueprint) recipe).getMaterialCollection(Commodity.class).forEach((key, value1) -> this.wishlistNeededCommmodity.merge((Commodity) key, value1, Integer::sum));
                        }
                );
        final List<HorizonsWishlistIngredient> ingredientsRaw = this.wishlistNeededRaw.entrySet().stream()
                .filter(entry -> !this.hideCompleted.get() || StorageService.getMaterialCount(entry.getKey()) < entry.getValue())
                .map(wishlistItem -> new HorizonsWishlistIngredient(HorizonsStorageType.forMaterial(wishlistItem.getKey()), wishlistItem.getKey(), wishlistItem.getValue(), StorageService.getRaw().get(wishlistItem.getKey())))
                .toList();
        final List<HorizonsWishlistIngredient> ingredientsEncoded = this.wishlistNeededEncoded.entrySet().stream()
                .filter(entry -> !this.hideCompleted.get() || StorageService.getMaterialCount(entry.getKey()) < entry.getValue())
                .map(wishlistItem -> new HorizonsWishlistIngredient(HorizonsStorageType.forMaterial(wishlistItem.getKey()), wishlistItem.getKey(), wishlistItem.getValue(), StorageService.getEncoded().get(wishlistItem.getKey())))
                .toList();
        final List<HorizonsWishlistIngredient> ingredientsManufactured = this.wishlistNeededManufactured.entrySet().stream()
                .filter(entry -> !this.hideCompleted.get() || StorageService.getMaterialCount(entry.getKey()) < entry.getValue())
                .map(wishlistItem -> new HorizonsWishlistIngredient(HorizonsStorageType.forMaterial(wishlistItem.getKey()), wishlistItem.getKey(), wishlistItem.getValue(), StorageService.getManufactured().get(wishlistItem.getKey())))
                .toList();
        final List<HorizonsWishlistIngredient> ingredientsCommodities = this.wishlistNeededCommmodity.entrySet().stream()
                .filter(entry -> !this.hideCompleted.get() || StorageService.getCommodityCount(entry.getKey(), StoragePool.SHIP) < entry.getValue())
                .map(wishlistItem -> new HorizonsWishlistIngredient(HorizonsStorageType.forMaterial(wishlistItem.getKey()), wishlistItem.getKey(), wishlistItem.getValue(), StorageService.getCommoditiesShip().get(wishlistItem.getKey())))
                .toList();
        final List<Ingredient> allIngredients = new ArrayList<>();
        allIngredients.addAll(ingredientsRaw);
        allIngredients.addAll(ingredientsEncoded);
        allIngredients.addAll(ingredientsManufactured);
        allIngredients.addAll(ingredientsCommodities);

        this.wishlistBlueprints.forEach(wishlistBlueprint -> wishlistBlueprint.addWishlistIngredients(allIngredients));
        this.rawFlow.getChildren().addAll(ingredientsRaw.stream().sorted(Comparator.comparing(HorizonsWishlistIngredient::getName)).toList());
        this.encodedFlow.getChildren().addAll(ingredientsEncoded.stream().sorted(Comparator.comparing(HorizonsWishlistIngredient::getName)).toList());
        this.manufacturedFlow.getChildren().addAll(ingredientsManufactured.stream().sorted(Comparator.comparing(HorizonsWishlistIngredient::getName)).toList());
        this.commodityFlow.getChildren().addAll(ingredientsCommodities.stream().sorted(Comparator.comparing(HorizonsWishlistIngredient::getName)).toList());


        removeAndAddFlows();
        try {
            final List<PathItem<HorizonsBlueprintName>> pathItems = PathService.calculateHorizonsShortestPath(this.wishlistBlueprints);
            this.shortestPathFlow.setItems(pathItems);
        } catch (final IllegalArgumentException ex) {
            log.error("Failed to generate path", ex);
        }

    }

    private void removeAndAddFlows() {
        this.flows.getChildren().removeAll(this.rawFlow, this.encodedFlow, this.manufacturedFlow, this.commodityFlow);
        for (final FlowPane flowPane : new FlowPane[]{this.rawFlow, this.encodedFlow, this.manufacturedFlow, this.commodityFlow}) {
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