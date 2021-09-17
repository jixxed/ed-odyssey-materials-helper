package nl.jixxed.eliteodysseymaterials.templates;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.*;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ModuleRecipe;
import nl.jixxed.eliteodysseymaterials.domain.PathItem;
import nl.jixxed.eliteodysseymaterials.domain.WishlistRecipe;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.export.TextExporter;
import nl.jixxed.eliteodysseymaterials.helper.WishlistHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PathService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.JournalProcessedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.SaveWishlistEvent;
import nl.jixxed.eliteodysseymaterials.service.event.ViewOnlyWishlistRecipeEvent;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Slf4j
public class SharedWishlistTab extends EDOTab {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final String WISHLIST_HEADER_CLASS = "wishlist-header";
    private static final String WISHLIST_CATEGORY_CLASS = "wishlist-category";
    private int wishlistSize;
    private List<WishlistBlueprint> wishlistBlueprints;
    private final AtomicBoolean hideCompleted = new AtomicBoolean();
    private final Map<Material, Integer> wishlistNeededMaterials = new HashMap<>();
    private final List<WishlistRecipe> wishlistRecipes;
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
    private TableView<PathItem> tableView;
    private VBox blueprints;
    private ScrollPane scrollPane;
    private VBox content;
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

    SharedWishlistTab(final String wishlist) {
        this.wishlistRecipes = WishlistHelper.convertWishlist(wishlist);
        initComponents();
        initEventHandling();

    }

    private void copyWishListToClipboard() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(Base64.getEncoder().encodeToString(WishlistHelper.convertWishlist(this.wishlistRecipes).getBytes(StandardCharsets.UTF_8)));
        clipboard.setContent(clipboardContent);
    }

    private void initComponents() {
        this.setClosable(true);
        this.scrollPane = new ScrollPane();
        setupScrollPane(this.scrollPane);
        initLabels();
        initShortestPathTable();

        this.engineerRecipes = FlowPaneBuilder.builder().withStyleClass("wishlist-recipes").build();
        this.suitUpgradeRecipes = FlowPaneBuilder.builder().withStyleClass("wishlist-recipes").build();
        this.suitModuleRecipes = FlowPaneBuilder.builder().withStyleClass("wishlist-recipes").build();
        this.weaponUpgradeRecipes = FlowPaneBuilder.builder().withStyleClass("wishlist-recipes").build();
        this.weaponModuleRecipes = FlowPaneBuilder.builder().withStyleClass("wishlist-recipes").build();
        this.goodFlow = FlowPaneBuilder.builder().withStyleClass("wishlist-ingredients").build();
        this.assetChemicalFlow = FlowPaneBuilder.builder().withStyleClass("wishlist-ingredients").build();
        this.dataFlow = FlowPaneBuilder.builder().withStyleClass("wishlist-ingredients").build();
        this.assetCircuitFlow = FlowPaneBuilder.builder().withStyleClass("wishlist-ingredients").build();
        this.assetTechFlow = FlowPaneBuilder.builder().withStyleClass("wishlist-ingredients").build();

        this.hideCompleted.set(PreferencesService.getPreference("blueprint.hide.completed", Boolean.FALSE));

        final Button exportButton = ButtonBuilder.builder().withStyleClass("wishlist-button").withText(LocaleService.getStringBinding("tab.wishlist.export"))
                .withOnAction(event -> EventService.publish(new SaveWishlistEvent(TextExporter.createTextWishlist(this.wishlistNeededMaterials)))).build();
        final Button toStringButton = ButtonBuilder.builder().withStyleClass("wishlist-button").withText(LocaleService.getStringBinding("tab.wishlist.copy"))
                .withOnAction(event -> copyWishListToClipboard()).build();
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
        this.wishlistSize = this.wishlistRecipes.size();
        this.textProperty().bind(LocaleService.getStringBinding("tabs.imported.wishlist"));
        this.getStyleClass().add("imported-wishlist-tab");

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
        final Region region2 = new Region();
        HBox.setHgrow(region2, Priority.ALWAYS);
        final Region region3 = new Region();
        region3.setMinWidth(5);
        final HBox hBoxBlueprints = BoxBuilder.builder().withNodes(this.selectedBlueprintsLabel, region, toStringButton, region3, exportButton).buildHBox();
        final HBox hBoxMaterials = BoxBuilder.builder().withNodes(this.requiredMaterialsLabel, region2, this.hideCompletedCheckBox).buildHBox();
        this.flows = BoxBuilder.builder().withStyleClass("wishlist-content").withNodes(this.goodFlow, this.assetChemicalFlow, this.assetCircuitFlow, this.assetTechFlow, this.dataFlow).buildVBox();
        this.content = BoxBuilder.builder().withStyleClass("wishlist-content").withNodes(hBoxBlueprints, this.blueprints, hBoxMaterials, this.flows, this.travelPathLabel, this.tableView).buildVBox();
        this.scrollPane.setContent(this.wishlistSize > 0 ? this.content : this.noBlueprint);
        this.setContent(this.scrollPane);
        Observable.create((ObservableEmitter<JournalProcessedEvent> emitter) -> EventService.addListener(JournalProcessedEvent.class, emitter::onNext))
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .subscribe(newValue -> Platform.runLater(this::refreshContent));

        this.wishlistBlueprints = this.wishlistRecipes.stream()
                .map(wishlistRecipe -> new WishlistBlueprint(wishlistRecipe, true))
                .collect(Collectors.toList());
        final Set<ModuleRecipe> collect = (Set<ModuleRecipe>) (Set<?>) this.wishlistBlueprints.stream().filter(wishlistBlueprint -> wishlistBlueprint.getRecipe() instanceof ModuleRecipe).map(wishlistBlueprint -> wishlistBlueprint.getRecipe()).collect(Collectors.toSet());
        final List<PathItem> pathItems = PathService.calculateShortestPath(collect);
        this.tableView.getItems().addAll(pathItems);
        log.info("PATHS");
        pathItems.forEach(item -> log.info("item {}", item.toString()));

        refreshWishlistRecipes();
        refreshBlueprintOverview();
        refreshContent();
    }

    private void initShortestPathTable() {
        this.tableView = new TableView<>();
        final TableColumn<PathItem, String> columnEngineer = new TableColumn<>("Engineer");
        columnEngineer.setCellValueFactory(param -> LocaleService.getStringBinding(param.getValue().getEngineer().getLocalizationKey()));
        final TableColumn<PathItem, String> columnBlueprint = new TableColumn<>("Blueprint");
        columnBlueprint.setCellValueFactory(param -> LocaleService.getStringBinding(() -> param.getValue().getRecipes().stream().map(recipe -> LocaleService.getLocalizedStringForCurrentLocale(recipe.getRecipeName().getLocalizationKey())).collect(Collectors.joining(", "))));
        columnEngineer.setMaxWidth(175);
        columnEngineer.setPrefWidth(175);
        columnEngineer.setMinWidth(175);
        this.tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.tableView.getStyleClass().add("wishlist-travel-path-table");
        this.tableView.prefHeightProperty().bind(this.tableView.fixedCellSizeProperty().multiply(Bindings.size(this.tableView.getItems()).add(1.05)));
        this.tableView.minHeightProperty().bind(this.tableView.prefHeightProperty());
        this.tableView.maxHeightProperty().bind(this.tableView.prefHeightProperty());
        this.tableView.getColumns().add(columnEngineer);
        this.tableView.getColumns().add(columnBlueprint);
        this.tableView.visibleProperty().bind(Bindings.greaterThan(Bindings.size(this.tableView.getItems()), 0));
        this.travelPathLabel.visibleProperty().bind(Bindings.greaterThan(Bindings.size(this.tableView.getItems()), 0));
    }

    private void initLabels() {
        this.noBlueprint = LabelBuilder.builder().withStyleClass(WISHLIST_HEADER_CLASS).withText(LocaleService.getStringBinding("tab.wishlist.no.blueprint")).build();
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
        EventService.addListener(ViewOnlyWishlistRecipeEvent.class, wishlistEvent ->
        {
            refreshContent();
        });
    }

    private void refreshWishlistRecipes() {
        this.engineerRecipes.getChildren().clear();
        this.suitUpgradeRecipes.getChildren().clear();
        this.suitModuleRecipes.getChildren().clear();
        this.weaponUpgradeRecipes.getChildren().clear();
        this.weaponModuleRecipes.getChildren().clear();
        this.engineerRecipes.getChildren().addAll(this.wishlistBlueprints.stream().filter(wishlistBlueprint -> RecipeCategory.ENGINEER_UNLOCKS.equals(wishlistBlueprint.getRecipeCategory())).collect(Collectors.toList()));
        this.suitUpgradeRecipes.getChildren().addAll(this.wishlistBlueprints.stream().filter(wishlistBlueprint -> RecipeCategory.SUIT_GRADES.equals(wishlistBlueprint.getRecipeCategory())).collect(Collectors.toList()));
        this.suitModuleRecipes.getChildren().addAll(this.wishlistBlueprints.stream().filter(wishlistBlueprint -> RecipeCategory.SUIT_MODULES.equals(wishlistBlueprint.getRecipeCategory())).collect(Collectors.toList()));
        this.weaponUpgradeRecipes.getChildren().addAll(this.wishlistBlueprints.stream().filter(wishlistBlueprint -> RecipeCategory.WEAPON_GRADES.equals(wishlistBlueprint.getRecipeCategory())).collect(Collectors.toList()));
        this.weaponModuleRecipes.getChildren().addAll(this.wishlistBlueprints.stream().filter(wishlistBlueprint -> RecipeCategory.WEAPON_MODULES.equals(wishlistBlueprint.getRecipeCategory())).collect(Collectors.toList()));
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
        this.scrollPane.setContent(!this.wishlistBlueprints.isEmpty() ? this.content : this.noBlueprint);
        this.goodFlow.getChildren().clear();
        this.assetChemicalFlow.getChildren().clear();
        this.dataFlow.getChildren().clear();
        this.assetCircuitFlow.getChildren().clear();
        this.assetTechFlow.getChildren().clear();
        this.wishlistNeededMaterials.clear();
        this.wishlistBlueprints.stream()
                .filter(WishlistBlueprint::isVisibleBlueprint)
                .map(WishlistBlueprint::getRecipe)
                .forEach(recipe ->
                        recipe.getMaterialCollection(Material.class).forEach((key, value1) -> this.wishlistNeededMaterials.merge(key, value1, Integer::sum))
                );

        final List<WishlistIngredient> ingredients = this.wishlistNeededMaterials.entrySet().stream()
                .filter(entry -> !this.hideCompleted.get() || !(switch (entry.getKey().getStorageType()) {
                    case GOOD -> APPLICATION_STATE.getGoods().get(entry.getKey()).getTotalValue() >= entry.getValue();
                    case DATA -> APPLICATION_STATE.getData().get(entry.getKey()).getTotalValue() >= entry.getValue();
                    case ASSET -> APPLICATION_STATE.getAssets().get(entry.getKey()).getTotalValue() >= entry.getValue();
                    case OTHER -> false;
                }))
                .map(wishlistItem ->
                        switch (wishlistItem.getKey().getStorageType()) {
                            case GOOD -> new WishlistIngredient(StorageType.forMaterial(wishlistItem.getKey()), wishlistItem.getKey(), wishlistItem.getValue(), APPLICATION_STATE.getGoods().get(wishlistItem.getKey()).getTotalValue());
                            case DATA -> new WishlistIngredient(StorageType.forMaterial(wishlistItem.getKey()), wishlistItem.getKey(), wishlistItem.getValue(), APPLICATION_STATE.getData().get(wishlistItem.getKey()).getTotalValue());
                            case ASSET -> new WishlistIngredient(StorageType.forMaterial(wishlistItem.getKey()), wishlistItem.getKey(), wishlistItem.getValue(), APPLICATION_STATE.getAssets().get(wishlistItem.getKey()).getTotalValue());
                            case OTHER -> null;
                        }
                ).collect(Collectors.toList());

        this.wishlistBlueprints.stream()
                .forEach(wishlistBlueprint -> wishlistBlueprint.addWishlistIngredients(ingredients));
        this.goodFlow.getChildren().addAll(ingredients.stream().filter(ingredient -> ingredient.getType().equals(StorageType.GOOD)).sorted(Comparator.comparing(WishlistIngredient::getName)).collect(Collectors.toList()));
        this.dataFlow.getChildren().addAll(ingredients.stream().filter(ingredient -> ingredient.getType().equals(StorageType.DATA)).sorted(Comparator.comparing(WishlistIngredient::getName)).collect(Collectors.toList()));
        this.assetCircuitFlow.getChildren().addAll(ingredients.stream().filter(ingredient -> ingredient.getType().equals(StorageType.ASSET) && ((Asset) ingredient.getMaterial()).getType().equals(AssetType.CIRCUIT)).sorted(Comparator.comparing(WishlistIngredient::getName)).collect(Collectors.toList()));
        this.assetChemicalFlow.getChildren().addAll(ingredients.stream().filter(ingredient -> ingredient.getType().equals(StorageType.ASSET) && ((Asset) ingredient.getMaterial()).getType().equals(AssetType.CHEMICAL)).sorted(Comparator.comparing(WishlistIngredient::getName)).collect(Collectors.toList()));
        this.assetTechFlow.getChildren().addAll(ingredients.stream().filter(ingredient -> ingredient.getType().equals(StorageType.ASSET) && ((Asset) ingredient.getMaterial()).getType().equals(AssetType.TECH)).sorted(Comparator.comparing(WishlistIngredient::getName)).collect(Collectors.toList()));
        removeAndAddFlows();
        this.tableView.getItems().clear();
        final Set<ModuleRecipe> collect = (Set<ModuleRecipe>) (Set<?>) this.wishlistBlueprints.stream().filter(wishlistBlueprint -> wishlistBlueprint.getRecipe() instanceof ModuleRecipe).map(wishlistBlueprint -> wishlistBlueprint.getRecipe()).collect(Collectors.toSet());
        final List<PathItem> pathItems = PathService.calculateShortestPath(collect);
        this.tableView.getItems().addAll(pathItems);

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