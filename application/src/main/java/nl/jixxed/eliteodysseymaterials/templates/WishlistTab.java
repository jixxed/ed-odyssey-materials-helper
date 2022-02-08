package nl.jixxed.eliteodysseymaterials.templates;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.*;
import javafx.util.Callback;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.export.TextExporter;
import nl.jixxed.eliteodysseymaterials.helper.WishlistHelper;
import nl.jixxed.eliteodysseymaterials.service.*;
import nl.jixxed.eliteodysseymaterials.service.event.*;

import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class WishlistTab extends EDOTab {


    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance();
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final String WISHLIST_HEADER_CLASS = "wishlist-header";
    private static final String WISHLIST_CATEGORY_CLASS = "wishlist-category";
    private static final String WISHLIST_RECIPES_STYLE_CLASS = "wishlist-recipes";
    private static final String WISHLIST_INGREDIENTS_STYLE_CLASS = "wishlist-ingredients";
    private static final String WISHLIST_BUTTON_STYLE_CLASS = "wishlist-button";
    private static final String WISHLIST_CONTENT_STYLE_CLASS = "wishlist-content";
    private int wishlistSize;
    private final List<WishlistBlueprint> wishlistBlueprints = new ArrayList<>();
    private final AtomicBoolean hideCompleted = new AtomicBoolean();
    private final Map<Data, Integer> wishlistNeededDatas = new EnumMap<>(Data.class);
    private final Map<Good, Integer> wishlistNeededGoods = new EnumMap<>(Good.class);
    private final Map<Asset, Integer> wishlistNeededAssets = new EnumMap<>(Asset.class);

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
    private FlowPane goodFlow;
    private FlowPane assetChemicalFlow;
    private FlowPane dataFlow;
    private FlowPane assetCircuitFlow;
    private FlowPane assetTechFlow;
    private TableView<PathItem> shortestRoute;
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
    private TextField wishlistName;

    static {
        NUMBER_FORMAT.setMaximumFractionDigits(2);
    }

    private Button createWishlistButton;
    private Button renameWishlistButton;
    private Button removeWishlistButton;
    private Button exportButton;
    private Button clipboardButton;

    WishlistTab() {
        initComponents();
        initEventHandling();

    }

    private void copyWishListToClipboard() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent clipboardContent = new ClipboardContent();
        final List<WishlistRecipe> wishlistRecipes = APPLICATION_STATE.getPreferredCommander()
                .map(commander -> APPLICATION_STATE.getWishlists(commander.getFid()).getSelectedWishlist().getItems())
                .orElse(new ArrayList<>());
        clipboardContent.putString(Base64.getEncoder().encodeToString(WishlistHelper.convertWishlist(wishlistRecipes).getBytes(StandardCharsets.UTF_8)));
        clipboard.setContent(clipboardContent);
    }

    private static final String FX_FONT_SIZE_DPX = "-fx-font-size: %dpx";

    private void applyFontSizingHack(final Integer fontSize) {
        //hack for component resizing on other fontsizes
        final String fontStyle = String.format(FX_FONT_SIZE_DPX, fontSize);
        this.wishlistName.styleProperty().set(fontStyle);
        this.createWishlistButton.styleProperty().set(fontStyle);
        this.renameWishlistButton.styleProperty().set(fontStyle);
        this.removeWishlistButton.styleProperty().set(fontStyle);
        this.exportButton.styleProperty().set(fontStyle);
        this.clipboardButton.styleProperty().set(fontStyle);
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
        this.engineerRecipes = FlowPaneBuilder.builder().withStyleClass(WISHLIST_RECIPES_STYLE_CLASS).build();
        this.suitUpgradeRecipes = FlowPaneBuilder.builder().withStyleClass(WISHLIST_RECIPES_STYLE_CLASS).build();
        this.suitModuleRecipes = FlowPaneBuilder.builder().withStyleClass(WISHLIST_RECIPES_STYLE_CLASS).build();
        this.weaponUpgradeRecipes = FlowPaneBuilder.builder().withStyleClass(WISHLIST_RECIPES_STYLE_CLASS).build();
        this.weaponModuleRecipes = FlowPaneBuilder.builder().withStyleClass(WISHLIST_RECIPES_STYLE_CLASS).build();
        this.goodFlow = FlowPaneBuilder.builder().withStyleClass(WISHLIST_INGREDIENTS_STYLE_CLASS).build();
        this.assetChemicalFlow = FlowPaneBuilder.builder().withStyleClass(WISHLIST_INGREDIENTS_STYLE_CLASS).build();
        this.dataFlow = FlowPaneBuilder.builder().withStyleClass(WISHLIST_INGREDIENTS_STYLE_CLASS).build();
        this.assetCircuitFlow = FlowPaneBuilder.builder().withStyleClass(WISHLIST_INGREDIENTS_STYLE_CLASS).build();
        this.assetTechFlow = FlowPaneBuilder.builder().withStyleClass(WISHLIST_INGREDIENTS_STYLE_CLASS).build();

        this.hideCompleted.set(PreferencesService.getPreference("blueprint.hide.completed", Boolean.FALSE));

        this.removeWishlistButton = ButtonBuilder.builder()
                .withStyleClass(WISHLIST_BUTTON_STYLE_CLASS)
                .withText(LocaleService.getStringBinding("tab.wishlist.delete"))
                .withOnAction(event -> {
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
                })
                .build();
        this.renameWishlistButton = ButtonBuilder.builder()
                .withStyleClass(WISHLIST_BUTTON_STYLE_CLASS)
                .withText(LocaleService.getStringBinding("tab.wishlist.rename"))
                .withOnAction(event -> APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                    final Wishlists wishlists = APPLICATION_STATE.getWishlists(commander.getFid());
                    wishlists.renameWishlist(this.activeWishlistUUID, this.wishlistName.getText());
                    APPLICATION_STATE.saveWishlists(commander.getFid(), wishlists);
                    this.wishlistName.clear();
                    refreshWishlistSelect();
                }))
                .build();
        this.createWishlistButton = ButtonBuilder.builder()
                .withStyleClass(WISHLIST_BUTTON_STYLE_CLASS)
                .withText(LocaleService.getStringBinding("tab.wishlist.create"))
                .withOnAction(event -> APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                    final Wishlists wishlists = APPLICATION_STATE.getWishlists(commander.getFid());
                    wishlists.createWishlist(this.wishlistName.getText());
                    APPLICATION_STATE.saveWishlists(commander.getFid(), wishlists);
                    this.wishlistName.clear();
                    refreshWishlistSelect();
                }))
                .build();
        this.wishlistName = TextFieldBuilder.builder().withStyleClasses("root", "wishlist-newname").withPromptTextProperty(LocaleService.getStringBinding("tab.wishlist.rename.prompt")).build();
        this.exportButton = ButtonBuilder.builder().withStyleClass(WISHLIST_BUTTON_STYLE_CLASS).withText(LocaleService.getStringBinding("tab.wishlist.export"))
                .withOnAction(event -> EventService.publish(new SaveWishlistEvent(TextExporter.createTextWishlist(this.wishlistNeededDatas, this.wishlistNeededGoods, this.wishlistNeededAssets)))).build();
        this.clipboardButton = ButtonBuilder.builder().withStyleClass(WISHLIST_BUTTON_STYLE_CLASS).withText(LocaleService.getStringBinding("tab.wishlist.copy"))
                .withOnAction(event -> {
                    copyWishListToClipboard();
                    NotificationService.showInformation("Wishlists", "The wishlist has been copied to your clipboard");
                }).build();

        final double minButtonWidth = 110.0;
        this.removeWishlistButton.setMinWidth(minButtonWidth);
        this.renameWishlistButton.setMinWidth(minButtonWidth);
        this.createWishlistButton.setMinWidth(minButtonWidth);
        this.exportButton.setMinWidth(minButtonWidth);
        this.clipboardButton.setMinWidth(minButtonWidth);
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

        final Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        region.setMinWidth(5);

        final Region region4 = new Region();
        region4.setMinWidth(5);
        final Region region5 = new Region();
        region5.setMinWidth(5);
        final Region region6 = new Region();
        region6.setMinWidth(5);
        final Region region7 = new Region();
        region7.setMinWidth(5);
        final FlowPane flowPane = FlowPaneBuilder.builder().withNodes(this.renameWishlistButton, this.createWishlistButton, this.removeWishlistButton, this.clipboardButton, this.exportButton).build();
        flowPane.setHgap(4);
        flowPane.setVgap(4);
        flowPane.prefWrapLengthProperty().bind(this.renameWishlistButton.widthProperty().add(this.createWishlistButton.widthProperty()).add(this.removeWishlistButton.widthProperty()).add(this.clipboardButton.widthProperty()).add(this.exportButton.widthProperty()).add(20));
        flowPane.setColumnHalignment(HPos.RIGHT);
        final HBox hBoxBlueprints = BoxBuilder.builder().withNodes(this.wishlistSelect, region, this.wishlistName, flowPane).buildHBox();
        final HBox hBoxMaterials = BoxBuilder.builder().withNodes(this.requiredMaterialsLabel, this.hideCompletedCheckBox).buildHBox();
        hBoxBlueprints.setSpacing(5);
        hBoxMaterials.setSpacing(10);
        this.flows = BoxBuilder.builder().withStyleClass(WISHLIST_CONTENT_STYLE_CLASS).withNodes(this.goodFlow, this.assetChemicalFlow, this.assetCircuitFlow, this.assetTechFlow, this.dataFlow).buildVBox();
        this.contentChild = BoxBuilder.builder().withStyleClass(WISHLIST_CONTENT_STYLE_CLASS).withNodes(this.selectedBlueprintsLabel, this.blueprints, hBoxMaterials, this.flows, this.travelPathLabel, this.shortestRoute).buildVBox();
        this.content = BoxBuilder.builder().withStyleClass(WISHLIST_CONTENT_STYLE_CLASS).withNodes(hBoxBlueprints, this.wishlistSize > 0 ? this.contentChild : this.noBlueprint).buildVBox();
        this.scrollPane = ScrollPaneBuilder.builder()
                .withContent(this.content)
                .build();
        this.setContent(this.scrollPane);
        Observable.create((ObservableEmitter<JournalLineProcessedEvent> emitter) -> EventService.addListener(this, JournalLineProcessedEvent.class, emitter::onNext))
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .subscribe(newValue -> Platform.runLater(this::refreshContent));

        this.wishlistBlueprints.forEach(WishlistBlueprint::onDestroy);
        this.wishlistBlueprints.clear();
        this.wishlistBlueprints.addAll(APPLICATION_STATE.getPreferredCommander()
                .map(commander -> APPLICATION_STATE.getWishlists(commander.getFid()).getSelectedWishlist().getItems().stream()
                        .map(wishlistRecipe -> new WishlistBlueprint(APPLICATION_STATE.getWishlists(commander.getFid()).getSelectedWishlist().getUuid(), wishlistRecipe))
                        .toList()
                )
                .orElse(new ArrayList<>()));
        try {
            final List<PathItem> pathItems = PathService.calculateShortestPath(this.wishlistBlueprints);
            this.shortestRoute.getItems().addAll(pathItems);
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
        this.shortestRoute = new TableView<>();
        final TableColumn<PathItem, String> columnIndex = new TableColumn<>("#");
        columnIndex.setCellFactory(col -> {
            final TableCell<PathItem, String> cell = new TableCell<>();
            cell.textProperty().bind(Bindings.createStringBinding(() -> {
                if (cell.isEmpty()) {
                    return null;
                } else {
                    return Integer.toString(cell.getIndex() + 1);
                }
            }, cell.emptyProperty(), cell.indexProperty()));
            return cell;
        });
        columnIndex.setSortable(false);
        columnIndex.getStyleClass().add("wishlist-travel-path-index");
        columnIndex.setMaxWidth(50);
        columnIndex.setPrefWidth(50);
        columnIndex.setMinWidth(50);

        final TableColumn<PathItem, String> columnEngineer = new TableColumn<>();
        columnEngineer.textProperty().bind(LocaleService.getStringBinding("tab.wishlist.travel.path.column.engineer"));
        columnEngineer.setCellValueFactory(param -> LocaleService.getStringBinding(param.getValue().getEngineer().getLocalizationKey()));
        columnEngineer.setMaxWidth(500);
        columnEngineer.setPrefWidth(175);
        columnEngineer.setMinWidth(175);
        columnEngineer.setSortable(false);
        columnEngineer.getStyleClass().add("wishlist-travel-path-engineer");

        final TableColumn<PathItem, Void> columnButtons = createButtonsColumn();
        columnButtons.setMaxWidth(300);
        columnButtons.setPrefWidth(300);
        columnButtons.setMinWidth(300);
        columnButtons.setSortable(false);

        final TableColumn<PathItem, String> columnDistance = new TableColumn<>();
        columnDistance.textProperty().bind(LocaleService.getStringBinding("tab.wishlist.travel.path.column.distance"));
        columnDistance.setCellValueFactory(param -> {
            final PathItem item = param.getValue();
            final int index = param.getTableView().getItems().indexOf(item);
            return new SimpleStringProperty(((index > 0) ? "+" : "") + NUMBER_FORMAT.format(item.getDistance()) + "Ly");
        });
        columnDistance.setSortable(false);
        columnDistance.getStyleClass().add("wishlist-travel-path-distance");
        columnDistance.setMaxWidth(120);
        columnDistance.setPrefWidth(120);
        columnDistance.setMinWidth(120);

        final TableColumn<PathItem, String> columnBlueprint = new TableColumn<>();
        columnBlueprint.textProperty().bind(LocaleService.getStringBinding("tab.wishlist.travel.path.column.blueprints"));
        columnBlueprint.setCellValueFactory(param -> LocaleService.getStringBinding(() -> param.getValue().getRecipesString()));
        columnBlueprint.setSortable(false);
        final DoubleBinding usedWidth = columnEngineer.widthProperty().add(columnDistance.widthProperty()).add(columnIndex.widthProperty()).add(columnButtons.widthProperty()).add(10);
        columnBlueprint.prefWidthProperty().bind(this.shortestRoute.widthProperty().subtract(usedWidth));
        columnIndex.getStyleClass().add("wishlist-travel-path-blueprint");

        this.shortestRoute.getStyleClass().add("wishlist-travel-path-table");
        this.shortestRoute.prefHeightProperty().bind(this.shortestRoute.fixedCellSizeProperty().multiply(Bindings.size(this.shortestRoute.getItems()).add(1.05)));
        this.shortestRoute.minHeightProperty().bind(this.shortestRoute.prefHeightProperty());
        this.shortestRoute.maxHeightProperty().bind(this.shortestRoute.prefHeightProperty());
        this.shortestRoute.getColumns().add(columnIndex);
        this.shortestRoute.getColumns().add(columnEngineer);
        this.shortestRoute.getColumns().add(columnBlueprint);
        this.shortestRoute.getColumns().add(columnDistance);
        this.shortestRoute.getColumns().add(columnButtons);
        this.shortestRoute.visibleProperty().bind(Bindings.greaterThan(Bindings.size(this.shortestRoute.getItems()), 0));
        this.travelPathLabel.visibleProperty().bind(Bindings.greaterThan(Bindings.size(this.shortestRoute.getItems()), 0));
    }

    private TableColumn<PathItem, Void> createButtonsColumn() {
        final TableColumn<PathItem, Void> colBtn = new TableColumn<>();
        colBtn.textProperty().bind(LocaleService.getStringBinding("tab.wishlist.travel.path.column.actions"));
        @SuppressWarnings("java:S1171") final Callback<TableColumn<PathItem, Void>, TableCell<PathItem, Void>> cellFactory = param -> new TableCell<>() {

            private final Button removeButton = ButtonBuilder.builder().withText(LocaleService.getStringBinding("tab.wishlist.travel.path.column.actions.remove")).build();
            private final Button hideButton = ButtonBuilder.builder().withText(LocaleService.getStringBinding("tab.wishlist.travel.path.column.actions.hide")).build();

            {
                this.removeButton.setOnAction((ActionEvent event) -> {
                    final List<WishlistBlueprint> pathBlueprints = getPathWishlistBlueprints();
                    pathBlueprints.forEach(WishlistBlueprint::remove);
                });
                this.hideButton.setOnAction((ActionEvent event) -> {
                    final List<WishlistBlueprint> pathBlueprints = getPathWishlistBlueprints();
                    pathBlueprints.forEach(wishlistBlueprint -> wishlistBlueprint.setVisibility(false));
                    refreshContent();
                });
            }

            private List<WishlistBlueprint> getPathWishlistBlueprints() {
                final PathItem pathItem = getTableView().getItems().get(getIndex());
                return pathItem.getRecipes().entrySet().stream()
                        .flatMap(recipeEntry -> WishlistTab.this.wishlistBlueprints.stream()
                                .filter(WishlistBlueprint::isVisibleBlueprint)
                                .filter(wishlistBlueprint -> wishlistBlueprint.getRecipe().equals(recipeEntry.getKey()))
                                .limit(recipeEntry.getValue())
                        ).toList();
            }

            @Override
            public void updateItem(final Void item, final boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new HBox(this.removeButton, this.hideButton));
                }
            }
        };
        colBtn.setCellFactory(cellFactory);
        return colBtn;
    }

    private void initLabels() {
        this.noBlueprint = LabelBuilder.builder().withStyleClasses(WISHLIST_HEADER_CLASS, WISHLIST_CONTENT_STYLE_CLASS).withText(LocaleService.getStringBinding("tab.wishlist.no.blueprint")).build();
        this.selectedBlueprintsLabel = LabelBuilder.builder().withStyleClass(WISHLIST_HEADER_CLASS).withText(LocaleService.getStringBinding("tab.wishlist.selected.blueprints")).build();
        this.requiredMaterialsLabel = LabelBuilder.builder().withStyleClass(WISHLIST_HEADER_CLASS).withText(LocaleService.getStringBinding("tab.wishlist.required.materials")).build();
        this.travelPathLabel = LabelBuilder.builder().withStyleClass(WISHLIST_HEADER_CLASS).withText(LocaleService.getStringBinding("tab.wishlist.travel.path")).build();
        this.engineerRecipesLabel = LabelBuilder.builder().withStyleClass(WISHLIST_CATEGORY_CLASS).withText(LocaleService.getStringBinding("recipe.category.name.engineer_unlocks")).build();
        this.suitUpgradeRecipesLabel = LabelBuilder.builder().withStyleClass(WISHLIST_CATEGORY_CLASS).withText(LocaleService.getStringBinding("recipe.category.name.suit_grades")).build();
        this.suitModuleRecipesLabel = LabelBuilder.builder().withStyleClass(WISHLIST_CATEGORY_CLASS).withText(LocaleService.getStringBinding("recipe.category.name.suit_modules")).build();
        this.weaponUpgradeRecipesLabel = LabelBuilder.builder().withStyleClass(WISHLIST_CATEGORY_CLASS).withText(LocaleService.getStringBinding("recipe.category.name.weapon_grades")).build();
        this.weaponModuleRecipesLabel = LabelBuilder.builder().withStyleClass(WISHLIST_CATEGORY_CLASS).withText(LocaleService.getStringBinding("recipe.category.name.weapon_modules")).build();
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
        EventService.addListener(this, WishlistRecipeEvent.class, wishlistEvent ->
        {
            if (Action.REMOVED.equals(wishlistEvent.getAction())) {
                this.wishlistBlueprints.stream()
                        .filter(wishlistBlueprint -> wishlistEvent.getWishlistRecipes().contains(wishlistBlueprint.getWishlistRecipe()))
                        .findFirst()
                        .ifPresent(wishlistBlueprint -> {
                            this.wishlistBlueprints.remove(wishlistBlueprint);
                            removeBluePrint(wishlistBlueprint);
                        });
            }
            if (Action.ADDED.equals(wishlistEvent.getAction())) {
                APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                    wishlistEvent.getWishlistRecipes().forEach(wishlistRecipe -> {
                        final WishlistBlueprint wishlistBlueprint = new WishlistBlueprint(wishlistEvent.getWishlistUUID(), wishlistRecipe);
                        if (!wishlistEvent.getWishlistUUID().equals(this.activeWishlistUUID)) {
                            Platform.runLater(() -> this.wishlistSelect.getSelectionModel().select(this.wishlistSelect.getItems().stream().filter(wishlist -> wishlist.getUuid().equals(wishlistEvent.getWishlistUUID())).findFirst().orElse(null)));
                        } else {
                            this.wishlistBlueprints.add(wishlistBlueprint);
                            addBluePrint(wishlistBlueprint);
                        }

                    });
                });
            }
            refreshContent();
        });
        EventService.addListener(this, CommanderSelectedEvent.class, commanderSelectedEvent ->
        {
            final String fid = commanderSelectedEvent.getCommander().getFid();
            final Wishlist selectedWishlist = APPLICATION_STATE.getWishlists(fid).getSelectedWishlist();
            this.activeWishlistUUID = selectedWishlist.getUuid();
            this.wishlistBlueprints.forEach(WishlistBlueprint::onDestroy);
            this.wishlistBlueprints.clear();
            this.wishlistBlueprints.addAll(selectedWishlist.getItems().stream()
                    .map(wishlistRecipe -> new WishlistBlueprint(this.activeWishlistUUID, wishlistRecipe))
                    .toList());
            refreshWishlistSelect();
            refreshWishlistRecipes();
            refreshBlueprintOverview();
            refreshContent();
            EventService.publish(new WishlistChangedEvent(this.activeWishlistUUID));
        });
        EventService.addListener(this, CommanderAllListedEvent.class, commanderAllListedEvent -> refreshWishlistBlueprints());
        EventService.addListener(this, LocationChangedEvent.class, locationChangedEvent -> refreshContent());
        EventService.addListener(this, ImportResultEvent.class, importResultEvent -> {
            if (importResultEvent.getResult().equals(ImportResult.SUCCESS_WISHLIST)) {
                refreshWishlistBlueprints();
            }
        });
    }

    private void refreshWishlistBlueprints() {
        this.wishlistBlueprints.forEach(WishlistBlueprint::onDestroy);
        this.wishlistBlueprints.clear();
        final List<WishlistBlueprint> newWishlistBlueprints = APPLICATION_STATE.getPreferredCommander()
                .map(commander -> {
                    final Wishlist selectedWishlist = APPLICATION_STATE.getWishlists(commander.getFid()).getSelectedWishlist();
                    this.activeWishlistUUID = selectedWishlist.getUuid();
                    return selectedWishlist.getItems().stream()
                            .map(wishlistRecipe -> new WishlistBlueprint(this.activeWishlistUUID, wishlistRecipe))
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
        this.engineerRecipes.getChildren().addAll(this.wishlistBlueprints.stream().filter(wishlistBlueprint -> RecipeCategory.ENGINEER_UNLOCKS.equals(wishlistBlueprint.getRecipeCategory())).toList());
        this.suitUpgradeRecipes.getChildren().addAll(this.wishlistBlueprints.stream().filter(wishlistBlueprint -> RecipeCategory.SUIT_GRADES.equals(wishlistBlueprint.getRecipeCategory())).toList());
        this.suitModuleRecipes.getChildren().addAll(this.wishlistBlueprints.stream().filter(wishlistBlueprint -> RecipeCategory.SUIT_MODULES.equals(wishlistBlueprint.getRecipeCategory())).toList());
        this.weaponUpgradeRecipes.getChildren().addAll(this.wishlistBlueprints.stream().filter(wishlistBlueprint -> RecipeCategory.WEAPON_GRADES.equals(wishlistBlueprint.getRecipeCategory())).toList());
        this.weaponModuleRecipes.getChildren().addAll(this.wishlistBlueprints.stream().filter(wishlistBlueprint -> RecipeCategory.WEAPON_MODULES.equals(wishlistBlueprint.getRecipeCategory())).toList());
    }


    private void addBluePrint(final WishlistBlueprint wishlistBlueprint) {
        switch (wishlistBlueprint.getRecipeCategory()) {
            case ENGINEER_UNLOCKS -> this.engineerRecipes.getChildren().add(wishlistBlueprint);
            case SUIT_GRADES -> this.suitUpgradeRecipes.getChildren().add(wishlistBlueprint);
            case SUIT_MODULES -> this.suitModuleRecipes.getChildren().add(wishlistBlueprint);
            case WEAPON_GRADES -> this.weaponUpgradeRecipes.getChildren().add(wishlistBlueprint);
            case WEAPON_MODULES -> this.weaponModuleRecipes.getChildren().add(wishlistBlueprint);
        }
        refreshBlueprintOverview();
    }

    private void removeBluePrint(final WishlistBlueprint wishlistBlueprint) {
        switch (wishlistBlueprint.getRecipeCategory()) {
            case ENGINEER_UNLOCKS -> this.engineerRecipes.getChildren().remove(wishlistBlueprint);
            case SUIT_GRADES -> this.suitUpgradeRecipes.getChildren().remove(wishlistBlueprint);
            case SUIT_MODULES -> this.suitModuleRecipes.getChildren().remove(wishlistBlueprint);
            case WEAPON_GRADES -> this.weaponUpgradeRecipes.getChildren().remove(wishlistBlueprint);
            case WEAPON_MODULES -> this.weaponModuleRecipes.getChildren().remove(wishlistBlueprint);
        }
        refreshBlueprintOverview();
    }

    private void refreshBlueprintOverview() {
        this.blueprints.getChildren().clear();
        for (final HBox blueprintList : List.of(this.engineerBlueprintsLine, this.suitUpgradeBlueprintsLine, this.suitModuleBlueprintsLine, this.weaponUpgradeBlueprintsLine, this.weaponModuleBlueprintsLine)) {
            if (!((FlowPane) blueprintList.getChildren().get(1)).getChildren().isEmpty()) {
                this.blueprints.getChildren().add(blueprintList);
                final ArrayList<WishlistBlueprint> wishlistItems = (ArrayList<WishlistBlueprint>) (ArrayList<?>) new ArrayList<>(((FlowPane) blueprintList.getChildren().get(1)).getChildren());
                wishlistItems
                        .sort(Comparator
                                .comparing(node -> LocaleService.getLocalizedStringForCurrentLocale(((WishlistBlueprint) node).getRecipeName().getLocalizationKey()))
                                .thenComparing(node -> ((WishlistBlueprint) node).getSequenceID()));
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
        this.goodFlow.getChildren().forEach(node -> ((WishlistIngredient) node).onDestroy());
        this.assetChemicalFlow.getChildren().forEach(node -> ((WishlistIngredient) node).onDestroy());
        this.dataFlow.getChildren().forEach(node -> ((WishlistIngredient) node).onDestroy());
        this.assetCircuitFlow.getChildren().forEach(node -> ((WishlistIngredient) node).onDestroy());
        this.assetTechFlow.getChildren().forEach(node -> ((WishlistIngredient) node).onDestroy());
        this.goodFlow.getChildren().clear();
        this.assetChemicalFlow.getChildren().clear();
        this.dataFlow.getChildren().clear();
        this.assetCircuitFlow.getChildren().clear();
        this.assetTechFlow.getChildren().clear();
        this.wishlistNeededGoods.clear();
        this.wishlistNeededAssets.clear();
        this.wishlistNeededDatas.clear();
        this.shortestRoute.getItems().clear();
        this.wishlistBlueprints.stream()
                .filter(WishlistBlueprint::isVisibleBlueprint)
                .map(WishlistBlueprint::getRecipe)
                .forEach(recipe -> {
                            recipe.getMaterialCollection(Data.class).forEach((key, value1) -> this.wishlistNeededDatas.merge((Data) key, value1, Integer::sum));
                            recipe.getMaterialCollection(Good.class).forEach((key, value1) -> this.wishlistNeededGoods.merge((Good) key, value1, Integer::sum));
                            recipe.getMaterialCollection(Asset.class).forEach((key, value1) -> this.wishlistNeededAssets.merge((Asset) key, value1, Integer::sum));
                        }
                );
        final List<WishlistIngredient> ingredientsData = this.wishlistNeededDatas.entrySet().stream()
                .filter(entry -> !this.hideCompleted.get() || !(StorageService.getData().get(entry.getKey()).getTotalValue() >= entry.getValue()))
                .map(wishlistItem -> new WishlistIngredient(OdysseyStorageType.forMaterial(wishlistItem.getKey()), wishlistItem.getKey(), wishlistItem.getValue(), StorageService.getData().get(wishlistItem.getKey()).getTotalValue()))
                .toList();
        final List<WishlistIngredient> ingredientsGood = this.wishlistNeededGoods.entrySet().stream()
                .filter(entry -> !this.hideCompleted.get() || !(StorageService.getGoods().get(entry.getKey()).getTotalValue() >= entry.getValue()))
                .map(wishlistItem -> new WishlistIngredient(OdysseyStorageType.forMaterial(wishlistItem.getKey()), wishlistItem.getKey(), wishlistItem.getValue(), StorageService.getGoods().get(wishlistItem.getKey()).getTotalValue()))
                .toList();
        final List<WishlistIngredient> ingredientsAsset = this.wishlistNeededAssets.entrySet().stream()
                .filter(entry -> !this.hideCompleted.get() || !(StorageService.getAssets().get(entry.getKey()).getTotalValue() >= entry.getValue()))
                .map(wishlistItem -> new WishlistIngredient(OdysseyStorageType.forMaterial(wishlistItem.getKey()), wishlistItem.getKey(), wishlistItem.getValue(), StorageService.getAssets().get(wishlistItem.getKey()).getTotalValue()))
                .toList();
        final List<WishlistIngredient> allIngredients = new ArrayList<>();
        allIngredients.addAll(ingredientsData);
        allIngredients.addAll(ingredientsGood);
        allIngredients.addAll(ingredientsAsset);

        this.wishlistBlueprints.forEach(wishlistBlueprint -> wishlistBlueprint.addWishlistIngredients(allIngredients));
        this.goodFlow.getChildren().addAll(ingredientsGood.stream().sorted(Comparator.comparing(WishlistIngredient::getName)).toList());
        this.dataFlow.getChildren().addAll(ingredientsData.stream().sorted(Comparator.comparing(WishlistIngredient::getName)).toList());
        this.assetCircuitFlow.getChildren().addAll(ingredientsAsset.stream().filter(ingredient -> ((Asset) ingredient.getMaterial()).getType().equals(AssetType.CIRCUIT)).sorted(Comparator.comparing(WishlistIngredient::getName)).toList());
        this.assetChemicalFlow.getChildren().addAll(ingredientsAsset.stream().filter(ingredient -> ((Asset) ingredient.getMaterial()).getType().equals(AssetType.CHEMICAL)).sorted(Comparator.comparing(WishlistIngredient::getName)).toList());
        this.assetTechFlow.getChildren().addAll(ingredientsAsset.stream().filter(ingredient -> ((Asset) ingredient.getMaterial()).getType().equals(AssetType.TECH)).sorted(Comparator.comparing(WishlistIngredient::getName)).toList());


        removeAndAddFlows();
        try {
            final List<PathItem> pathItems = PathService.calculateShortestPath(this.wishlistBlueprints);
            this.shortestRoute.getItems().addAll(pathItems);
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
    public Tabs getTabType() {
        return Tabs.WISHLIST;
    }
}