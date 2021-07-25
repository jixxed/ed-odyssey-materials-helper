package nl.jixxed.eliteodysseymaterials.templates;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.JournalProcessedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.WishlistChangedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.WishlistEvent;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class WishlistTab extends EDOTab {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private final FlowPane recipes = new FlowPane();
    private final Map<Material, Integer> wishlistNeededMaterials = new HashMap<>();
    private final FlowPane goodFlow = new FlowPane();
    private final FlowPane assetChemicalFlow = new FlowPane();
    private final FlowPane dataFlow = new FlowPane();
    private final FlowPane assetCircuitFlow = new FlowPane();
    private final FlowPane assetTechFlow = new FlowPane();
    private int wishlistSize;
    private final List<WishlistBlueprint> wishlistBlueprints;
    private final VBox content;

    public WishlistTab() {
        super();
        this.wishlistSize = APPLICATION_STATE.getWishlist().size();
        this.textProperty().bind(LocaleService.getSupplierStringBinding("tabs.wishlist", () -> (this.wishlistSize > 0) ? " (" + this.wishlistSize + ")" : ""));
        EventService.addListener(WishlistChangedEvent.class, wishlistChangedEvent -> {
            this.wishlistSize = wishlistChangedEvent.getWishlistSize();
            this.textProperty().bind(LocaleService.getSupplierStringBinding("tabs.wishlist", () -> (this.wishlistSize > 0) ? " (" + this.wishlistSize + ")" : ""));
        });
        final ScrollPane scrollPane = new ScrollPane();
        scrollPane.pannableProperty().set(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        this.recipes.setHgap(10);
        this.recipes.setVgap(10);

        this.goodFlow.setHgap(4);
        this.goodFlow.setVgap(4);
        this.dataFlow.setHgap(4);
        this.dataFlow.setVgap(4);
        this.assetChemicalFlow.setHgap(4);
        this.assetChemicalFlow.setVgap(4);
        this.assetCircuitFlow.setHgap(4);
        this.assetCircuitFlow.setVgap(4);
        this.assetTechFlow.setHgap(4);
        this.assetTechFlow.setVgap(4);
        final Label selected_blueprints = new Label();
        selected_blueprints.textProperty().bind(LocaleService.getStringBinding("tab.wishlist.selected.blueprints"));
        final Label required_materials = new Label();
        required_materials.textProperty().bind(LocaleService.getStringBinding("tab.wishlist.required.materials"));
        selected_blueprints.getStyleClass().add("wishlist-header");
        required_materials.getStyleClass().add("wishlist-header");
        this.content = new VBox(selected_blueprints, this.recipes, required_materials, this.goodFlow, this.assetChemicalFlow, this.assetCircuitFlow, this.assetTechFlow, this.dataFlow);
        this.content.setSpacing(10);
        scrollPane.setContent(this.content);
        VBox.setVgrow(this.dataFlow, Priority.ALWAYS);
        this.content.setPadding(new Insets(5));
        this.setContent(scrollPane);
        EventService.addListener(WishlistEvent.class, (wishlistEvent) ->
                Platform.runLater(this::refreshContent));
        Observable.create((ObservableEmitter<JournalProcessedEvent> emitter) -> EventService.addListener(JournalProcessedEvent.class, emitter::onNext))
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .subscribe((newValue) -> {
                    Platform.runLater(this::refreshContent);
                });
        this.wishlistBlueprints = APPLICATION_STATE.getWishlist().stream()
                .map(WishlistBlueprint::new)
                .collect(Collectors.toList());
        this.recipes.getChildren().clear();
        this.recipes.getChildren().addAll(this.wishlistBlueprints);
        EventService.addListener(WishlistEvent.class, (wishlistEvent) ->
        {
            if (Action.REMOVED.equals(wishlistEvent.getAction())) {
                this.wishlistBlueprints.stream()
                        .filter(wishlistBlueprint -> wishlistBlueprint.getRecipeName().equals(wishlistEvent.getRecipeName()))
                        .findFirst()
                        .ifPresent(wishlistBlueprint -> {
                            this.recipes.getChildren().remove(wishlistBlueprint);
                            this.wishlistBlueprints.remove(wishlistBlueprint);
                        });
            }
            if (Action.ADDED.equals(wishlistEvent.getAction())) {
                final WishlistBlueprint wishlistBlueprint = new WishlistBlueprint(wishlistEvent.getRecipeName());
                this.wishlistBlueprints.add(wishlistBlueprint);
                this.recipes.getChildren().add(wishlistBlueprint);
            }
        });
        refreshContent();
    }

    public void refreshContent() {
        this.goodFlow.getChildren().clear();
        this.assetChemicalFlow.getChildren().clear();
        this.dataFlow.getChildren().clear();
        this.assetCircuitFlow.getChildren().clear();
        this.assetTechFlow.getChildren().clear();
        this.wishlistNeededMaterials.clear();
        this.wishlistBlueprints.stream()
                .map(WishlistBlueprint::getRecipe)
                .forEach(recipe ->
                        recipe.getMaterialCollection(Material.class).forEach((key, value1) -> this.wishlistNeededMaterials.merge(key, value1, Integer::sum))
                );

        final List<WishlistIngredient> ingredients = this.wishlistNeededMaterials.entrySet().stream()
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