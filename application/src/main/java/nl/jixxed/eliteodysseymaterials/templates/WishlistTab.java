package nl.jixxed.eliteodysseymaterials.templates;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.export.TextExporter;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.*;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class WishlistTab extends EDOTab {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final String WISHLIST_HEADER_CLASS = "wishlist-header";
    private static final String WISHLIST_CATEGORY_CLASS = "wishlist-category";
    private int wishlistSize;
    private List<WishlistBlueprint> wishlistBlueprints;
    private final AtomicBoolean hideCompleted = new AtomicBoolean();
    private final Map<Material, Integer> wishlistNeededMaterials = new HashMap<>();

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
    private VBox blueprints;
    private ScrollPane scrollPane;
    private VBox content;
    private CheckBox hideCompletedCheckBox;
    private Label selectedBlueprintsLabel;
    private Label requiredMaterialsLabel;
    private Label engineerRecipesLabel;
    private Label suitUpgradeRecipesLabel;
    private Label suitModuleRecipesLabel;
    private Label weaponUpgradeRecipesLabel;
    private Label weaponModuleRecipesLabel;

    WishlistTab() {
        initComponents();
        initEventHandling();

    }

    private void initComponents() {
        this.scrollPane = new ScrollPane();
        setupScrollPane(this.scrollPane);
        initLabels();

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
        this.wishlistSize = APPLICATION_STATE.getPreferredCommander().map(commander -> APPLICATION_STATE.getWishlist(commander.getFid()).size()).orElse(0);
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
        final Region region2 = new Region();
        HBox.setHgrow(region2, Priority.ALWAYS);

        final HBox hBox = BoxBuilder.builder().withNodes(this.selectedBlueprintsLabel, region, exportButton).buildHBox();
        final HBox hBox1 = BoxBuilder.builder().withNodes(this.requiredMaterialsLabel, region2, this.hideCompletedCheckBox).buildHBox();
        this.content = BoxBuilder.builder().withStyleClass("wishlist-content").withNodes(hBox, this.blueprints, hBox1, this.goodFlow, this.assetChemicalFlow, this.assetCircuitFlow, this.assetTechFlow, this.dataFlow).buildVBox();
        this.scrollPane.setContent(this.wishlistSize > 0 ? this.content : this.noBlueprint);
        VBox.setVgrow(this.dataFlow, Priority.ALWAYS);
        this.setContent(this.scrollPane);
        Observable.create((ObservableEmitter<JournalProcessedEvent> emitter) -> EventService.addListener(JournalProcessedEvent.class, emitter::onNext))
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .subscribe(newValue -> Platform.runLater(this::refreshContent));

        this.wishlistBlueprints = APPLICATION_STATE.getPreferredCommander()
                .map(commander -> APPLICATION_STATE.getWishlist(commander.getFid()).stream()
                        .map(WishlistBlueprint::new)
                        .collect(Collectors.toList())
                )
                .orElse(new ArrayList<>());

        refreshWishlistRecipes();
        refreshBlueprintOverview();
        refreshContent();
    }

    private void initLabels() {
        this.noBlueprint = LabelBuilder.builder().withStyleClass(WISHLIST_HEADER_CLASS).withText(LocaleService.getStringBinding("tab.wishlist.no.blueprint")).build();
        this.selectedBlueprintsLabel = LabelBuilder.builder().withStyleClass(WISHLIST_HEADER_CLASS).withText(LocaleService.getStringBinding("tab.wishlist.selected.blueprints")).build();
        this.requiredMaterialsLabel = LabelBuilder.builder().withStyleClass(WISHLIST_HEADER_CLASS).withText(LocaleService.getStringBinding("tab.wishlist.required.materials")).build();
        this.engineerRecipesLabel = LabelBuilder.builder().withStyleClass(WISHLIST_CATEGORY_CLASS).withText(LocaleService.getStringBinding("recipe.category.name.engineer_unlocks")).build();
        this.suitUpgradeRecipesLabel = LabelBuilder.builder().withStyleClass(WISHLIST_CATEGORY_CLASS).withText(LocaleService.getStringBinding("recipe.category.name.suit_grades")).build();
        this.suitModuleRecipesLabel = LabelBuilder.builder().withStyleClass(WISHLIST_CATEGORY_CLASS).withText(LocaleService.getStringBinding("recipe.category.name.suit_modules")).build();
        this.weaponUpgradeRecipesLabel = LabelBuilder.builder().withStyleClass(WISHLIST_CATEGORY_CLASS).withText(LocaleService.getStringBinding("recipe.category.name.weapon_grades")).build();
        this.weaponModuleRecipesLabel = LabelBuilder.builder().withStyleClass(WISHLIST_CATEGORY_CLASS).withText(LocaleService.getStringBinding("recipe.category.name.weapon_modules")).build();
    }

    private void initEventHandling() {
        EventService.addListener(WishlistChangedEvent.class, wishlistChangedEvent -> {
            this.wishlistSize = wishlistChangedEvent.getWishlistSize();
            this.textProperty().bind(LocaleService.getSupplierStringBinding("tabs.wishlist", () -> (this.wishlistSize > 0) ? " (" + this.wishlistSize + ")" : ""));
        });
        EventService.addListener(WishlistRecipeEvent.class, wishlistEvent ->
        {
            if (Action.REMOVED.equals(wishlistEvent.getAction())) {
                this.wishlistBlueprints.stream()
                        .filter(wishlistBlueprint -> wishlistBlueprint.getWishlistRecipe().equals(wishlistEvent.getWishlistRecipe()))
                        .findFirst()
                        .ifPresent(wishlistBlueprint -> {
                            this.wishlistBlueprints.remove(wishlistBlueprint);
                            removeBluePrint(wishlistBlueprint);
                        });
            }
            if (Action.ADDED.equals(wishlistEvent.getAction())) {
                final WishlistBlueprint wishlistBlueprint = new WishlistBlueprint(wishlistEvent.getWishlistRecipe());
                this.wishlistBlueprints.add(wishlistBlueprint);
                addBluePrint(wishlistBlueprint);
            }
            refreshContent();
        });
        EventService.addListener(CommanderSelectedEvent.class, commanderSelectedEvent ->
        {
            final String fid = commanderSelectedEvent.getCommander().getFid();
            this.wishlistBlueprints = APPLICATION_STATE.getWishlist(fid).stream()
                    .map(WishlistBlueprint::new)
                    .collect(Collectors.toList());
            refreshWishlistRecipes();
            refreshBlueprintOverview();
            refreshContent();
            EventService.publish(new WishlistChangedEvent(this.wishlistBlueprints.size()));
        });
        EventService.addListener(CommanderAllListedEvent.class, commanderAllListedEvent ->
        {
            this.wishlistBlueprints = APPLICATION_STATE.getPreferredCommander().map(commander -> APPLICATION_STATE.getWishlist(commander.getFid()).stream()
                    .map(WishlistBlueprint::new)
                    .collect(Collectors.toList())).orElse(new ArrayList<>());
            refreshWishlistRecipes();
            refreshBlueprintOverview();
            refreshContent();
            EventService.publish(new WishlistChangedEvent(this.wishlistBlueprints.size()));
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
    }

    private void removeAndAddFlows() {
        this.content.getChildren().removeAll(this.goodFlow, this.assetChemicalFlow, this.assetCircuitFlow, this.assetTechFlow, this.dataFlow);
        for (final FlowPane flowPane : new FlowPane[]{this.goodFlow, this.assetChemicalFlow, this.assetCircuitFlow, this.assetTechFlow, this.dataFlow}) {
            if (!flowPane.getChildren().isEmpty()) {
                this.content.getChildren().add(flowPane);
            }
        }
    }

    @Override
    public Tabs getTabType() {
        return Tabs.WISHLIST;
    }
}