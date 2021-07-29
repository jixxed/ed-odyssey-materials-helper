package nl.jixxed.eliteodysseymaterials.templates;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.*;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class WishlistTab extends EDOTab {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private final Label noBlueprint = new Label();
    private final FlowPane engineerRecipes = new FlowPane();
    private final FlowPane suitUpgradeRecipes = new FlowPane();
    private final FlowPane suitModuleRecipes = new FlowPane();
    private final FlowPane weaponUpgradeRecipes = new FlowPane();
    private final FlowPane weaponModuleRecipes = new FlowPane();
    private final Map<Material, Integer> wishlistNeededMaterials = new HashMap<>();
    private final FlowPane goodFlow = new FlowPane();
    private final FlowPane assetChemicalFlow = new FlowPane();
    private final FlowPane dataFlow = new FlowPane();
    private final FlowPane assetCircuitFlow = new FlowPane();
    private final FlowPane assetTechFlow = new FlowPane();
    private final HBox engineerBlueprints;
    private final HBox suitUpgradeBlueprints;
    private final HBox suitModuleBlueprints;
    private final HBox weaponUpgradeBlueprints;
    private final HBox weaponModuleBlueprints;
    private final VBox blueprints;
    private final ScrollPane scrollPane = new ScrollPane();
    private int wishlistSize;
    private List<WishlistBlueprint> wishlistBlueprints;
    private final VBox content;
    private final AtomicBoolean hideCompleted = new AtomicBoolean();

    WishlistTab() {
        super();
        this.hideCompleted.set(PreferencesService.getPreference("blueprint.hide.completed", Boolean.FALSE));
        this.noBlueprint.textProperty().bind(LocaleService.getStringBinding("tab.wishlist.no.blueprint"));
        this.noBlueprint.getStyleClass().add("wishlist-header");
        final Button exportButton = new Button();
        exportButton.textProperty().bind(LocaleService.getStringBinding("tab.wishlist.export"));
        exportButton.getStyleClass().add("wishlist-button");
        exportButton.setOnAction(event -> EventService.publish(new SaveWishlistEvent(createTextWishlist())));
        final CheckBox hideCompleted = new CheckBox();
        hideCompleted.getStyleClass().add("wishlist-checkbox");
        hideCompleted.textProperty().bind(LocaleService.getStringBinding("tab.wishlist.hide.completed"));
        hideCompleted.setSelected(this.hideCompleted.get());
        hideCompleted.selectedProperty().addListener((observable, oldValue, newValue) ->
        {
            this.hideCompleted.set(newValue);
            PreferencesService.setPreference("blueprint.hide.completed", newValue);
            refreshContent();
        });
        this.wishlistSize = APPLICATION_STATE.getPreferredCommander().map(commander -> APPLICATION_STATE.getWishlist(commander.getFid()).size()).orElse(0);
        this.textProperty().bind(LocaleService.getSupplierStringBinding("tabs.wishlist", () -> (this.wishlistSize > 0) ? " (" + this.wishlistSize + ")" : ""));
        EventService.addListener(WishlistChangedEvent.class, wishlistChangedEvent -> {
            this.wishlistSize = wishlistChangedEvent.getWishlistSize();
            this.textProperty().bind(LocaleService.getSupplierStringBinding("tabs.wishlist", () -> (this.wishlistSize > 0) ? " (" + this.wishlistSize + ")" : ""));
        });
        this.scrollPane.pannableProperty().set(true);
        this.scrollPane.setFitToHeight(true);
        this.scrollPane.setFitToWidth(true);
        this.scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        setGaps(10, this.engineerRecipes, this.suitUpgradeRecipes, this.suitModuleRecipes, this.weaponUpgradeRecipes, this.weaponModuleRecipes);
        setGaps(4, this.goodFlow, this.dataFlow, this.assetChemicalFlow, this.assetCircuitFlow, this.assetTechFlow);
        final Label selectedBlueprints = new Label();
        selectedBlueprints.textProperty().bind(LocaleService.getStringBinding("tab.wishlist.selected.blueprints"));
        final Label requiredMaterials = new Label();
        requiredMaterials.textProperty().bind(LocaleService.getStringBinding("tab.wishlist.required.materials"));
        selectedBlueprints.getStyleClass().add("wishlist-header");
        requiredMaterials.getStyleClass().add("wishlist-header");
        final Label engineerRecipesLabel = new Label();
        final Label suitUpgradeRecipesLabel = new Label();
        final Label suitModuleRecipesLabel = new Label();
        final Label weaponUpgradeRecipesLabel = new Label();
        final Label weaponModuleRecipesLabel = new Label();
        engineerRecipesLabel.textProperty().bind(LocaleService.getStringBinding("recipe.category.name.engineer_unlocks"));
        suitUpgradeRecipesLabel.textProperty().bind(LocaleService.getStringBinding("recipe.category.name.suit_grades"));
        suitModuleRecipesLabel.textProperty().bind(LocaleService.getStringBinding("recipe.category.name.suit_modules"));
        weaponUpgradeRecipesLabel.textProperty().bind(LocaleService.getStringBinding("recipe.category.name.weapon_grades"));
        weaponModuleRecipesLabel.textProperty().bind(LocaleService.getStringBinding("recipe.category.name.weapon_modules"));
        engineerRecipesLabel.getStyleClass().add("wishlist-category");
        suitUpgradeRecipesLabel.getStyleClass().add("wishlist-category");
        suitModuleRecipesLabel.getStyleClass().add("wishlist-category");
        weaponUpgradeRecipesLabel.getStyleClass().add("wishlist-category");
        weaponModuleRecipesLabel.getStyleClass().add("wishlist-category");
        this.engineerBlueprints = new HBox(engineerRecipesLabel, this.engineerRecipes);
        this.suitUpgradeBlueprints = new HBox(suitUpgradeRecipesLabel, this.suitUpgradeRecipes);
        this.suitModuleBlueprints = new HBox(suitModuleRecipesLabel, this.suitModuleRecipes);
        this.weaponUpgradeBlueprints = new HBox(weaponUpgradeRecipesLabel, this.weaponUpgradeRecipes);
        this.weaponModuleBlueprints = new HBox(weaponModuleRecipesLabel, this.weaponModuleRecipes);
        HBox.setHgrow(this.engineerRecipes, Priority.ALWAYS);
        HBox.setHgrow(this.suitUpgradeRecipes, Priority.ALWAYS);
        HBox.setHgrow(this.suitModuleRecipes, Priority.ALWAYS);
        HBox.setHgrow(this.weaponUpgradeRecipes, Priority.ALWAYS);
        HBox.setHgrow(this.weaponModuleRecipes, Priority.ALWAYS);
        this.blueprints = new VBox();
        this.blueprints.setSpacing(4);
        final Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        final Region region2 = new Region();
        HBox.setHgrow(region2, Priority.ALWAYS);
        this.content = new VBox(new HBox(selectedBlueprints, region, exportButton), this.blueprints, new HBox(requiredMaterials, region2, hideCompleted), this.goodFlow, this.assetChemicalFlow, this.assetCircuitFlow, this.assetTechFlow, this.dataFlow);
        this.content.setSpacing(10);
        this.scrollPane.setContent(this.wishlistSize > 0 ? this.content : this.noBlueprint);
        VBox.setVgrow(this.dataFlow, Priority.ALWAYS);
        this.content.setPadding(new Insets(5));
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
        this.engineerRecipes.getChildren().addAll(this.wishlistBlueprints.stream().filter(wishlistBlueprint -> RecipeCategory.ENGINEER_UNLOCKS.equals(wishlistBlueprint.getRecipeCategory())).collect(Collectors.toList()));
        this.suitUpgradeRecipes.getChildren().addAll(this.wishlistBlueprints.stream().filter(wishlistBlueprint -> RecipeCategory.SUIT_GRADES.equals(wishlistBlueprint.getRecipeCategory())).collect(Collectors.toList()));
        this.suitModuleRecipes.getChildren().addAll(this.wishlistBlueprints.stream().filter(wishlistBlueprint -> RecipeCategory.SUIT_MODULES.equals(wishlistBlueprint.getRecipeCategory())).collect(Collectors.toList()));
        this.weaponUpgradeRecipes.getChildren().addAll(this.wishlistBlueprints.stream().filter(wishlistBlueprint -> RecipeCategory.WEAPON_GRADES.equals(wishlistBlueprint.getRecipeCategory())).collect(Collectors.toList()));
        this.weaponModuleRecipes.getChildren().addAll(this.wishlistBlueprints.stream().filter(wishlistBlueprint -> RecipeCategory.WEAPON_MODULES.equals(wishlistBlueprint.getRecipeCategory())).collect(Collectors.toList()));
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
            refreshBlueprintOverview();
            refreshContent();
            EventService.publish(new WishlistChangedEvent(this.wishlistBlueprints.size()));
        });
        EventService.addListener(CommanderAllListedEvent.class, commanderAllListedEvent ->
        {
            this.wishlistBlueprints = APPLICATION_STATE.getPreferredCommander().map(commander -> APPLICATION_STATE.getWishlist(commander.getFid()).stream()
                    .map(WishlistBlueprint::new)
                    .collect(Collectors.toList())).orElse(new ArrayList<>());
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
            refreshBlueprintOverview();
            refreshContent();
            EventService.publish(new WishlistChangedEvent(this.wishlistBlueprints.size()));
        });
        refreshBlueprintOverview();
        refreshContent();
    }

    private String createTextWishlist() {
        final StringBuilder textBuilder = new StringBuilder();
        final Integer maxNameLength = this.wishlistNeededMaterials.keySet().stream()
                .map(item -> LocaleService.getLocalizedStringForCurrentLocale(item.getLocalizationKey()).length())
                .max(Comparator.comparing(Integer::intValue)).orElse(0);

        final String materialColumnWidth = "%-" + (maxNameLength + 5) + "s";
        textBuilder.append(String.format(materialColumnWidth, "Material"));
        textBuilder.append(String.format("%12s", "Available"));
        textBuilder.append(String.format("%12s", "Required"));
        textBuilder.append(String.format("%12s", "Need"));
        textBuilder.append("\n\n");
        this.wishlistNeededMaterials.entrySet().stream()
                .sorted(Comparator.comparing(item -> item.getKey().getStorageType()))
                .forEach(item -> {
                    textBuilder.append(String.format(materialColumnWidth, LocaleService.getLocalizedStringForCurrentLocale(item.getKey().getLocalizationKey())));
                    final Integer total = switch (item.getKey().getStorageType()) {
                        case GOOD -> APPLICATION_STATE.getGoods().get(item.getKey()).getTotalValue();
                        case DATA -> APPLICATION_STATE.getData().get(item.getKey()).getTotalValue();
                        case ASSET -> APPLICATION_STATE.getAssets().get(item.getKey()).getTotalValue();
                        case OTHER -> 0;
                    };
                    textBuilder.append(String.format("%12s", total));
                    textBuilder.append(String.format("%12s", item.getValue()));
                    textBuilder.append(String.format("%12s", Math.max(0, item.getValue() - total)));
                    textBuilder.append("\r\n");
                });
        return textBuilder.toString();
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
        for (final HBox blueprintList : List.of(this.engineerBlueprints, this.suitUpgradeBlueprints, this.suitModuleBlueprints, this.weaponUpgradeBlueprints, this.weaponModuleBlueprints)) {
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

    private void setGaps(final int size, final FlowPane... flowPanes) {
        Arrays.stream(flowPanes).forEach(flowPane -> {
            flowPane.setHgap(size);
            flowPane.setVgap(size);
        });
    }

    @Override
    public Tabs getTabType() {
        return Tabs.WISHLIST;
    }
}