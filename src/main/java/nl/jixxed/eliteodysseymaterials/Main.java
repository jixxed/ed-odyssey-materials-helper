package nl.jixxed.eliteodysseymaterials;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Labeled;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.models.EngineerRecipe;
import nl.jixxed.eliteodysseymaterials.models.Recipe;
import nl.jixxed.eliteodysseymaterials.templates.ApplicationLayout;
import nl.jixxed.eliteodysseymaterials.templates.Ingredient;
import nl.jixxed.eliteodysseymaterials.templates.MaterialCard;
import nl.jixxed.eliteodysseymaterials.templates.Settings;
import nl.jixxed.eliteodysseymaterials.watchdog.JournalWatcher;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Main extends Application {
    private static final Insets CARD_MARGIN = new Insets(2, 5, 2, 5);
    private static final String ENGINEER_UNLOCK = "E";
    private int lineNumber = 0;
    private final ApplicationLayout applicationLayout = new ApplicationLayout();
    private final AnchorPane content = new AnchorPane();
    private final GridPane materialOverview = new GridPane();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<Good, Integer> goods = new HashMap<>();
    private final Map<Asset, Integer> assets = new HashMap<>();
    private final Map<Data, Integer> data = new HashMap<>();
    private final Map<String, Integer> unknownData = new HashMap<>();
    private final List<Ingredient> ingredients = new ArrayList<>();
    private final JournalWatcher journalWatcher = new JournalWatcher();
    private final Settings settings = new Settings();

    @Override
    public void start(final Stage primaryStage) {
        initCounts();
        primaryStage.setTitle("ED Odyssey Materials Helper");
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/rocket.png")));
        final ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(this.materialOverview);
        setAnchor(this.content, 0.0, 0.0, 0.0, 0.0);
        this.content.getChildren().add(scrollPane);
        setAnchor(scrollPane, 0.0, 0.0, 372.0, 0.0);
        this.applicationLayout.getChildren().add(this.content);
        this.applicationLayout.getStylesheets().add(getClass().getResource("/nl/jixxed/eliteodysseymaterials/style/style.css").toExternalForm());

        for (int i = 0; i < 5; i++) {
            final ColumnConstraints column = new ColumnConstraints(250);
            this.materialOverview.getColumnConstraints().add(column);
        }

        final String userprofile = System.getenv("USERPROFILE");
        this.journalWatcher.watch(new File(userprofile + "\\Saved Games\\Frontier Developments\\Elite Dangerous"), this::process, this::resetAndProcess);

        showRecipes();

        primaryStage.setScene(new Scene(this.applicationLayout));
        addSettingsListeners();
        primaryStage.show();
    }

    private void setAnchor(final Node child, final Double topValue, final Double bottomValue, final Double leftValue, final Double rightValue) {
        AnchorPane.setTopAnchor(child, topValue);
        AnchorPane.setBottomAnchor(child, bottomValue);
        AnchorPane.setLeftAnchor(child, leftValue);
        AnchorPane.setRightAnchor(child, rightValue);
    }

    private void addSettingsListeners() {
        this.settings.getCheckBoxIrrelevant().selectedProperty().addListener((observable, oldValue, newValue) -> {
            this.materialOverview.getChildren().clear();
            showGoods(this.materialOverview);
            showComponents(this.materialOverview);
            showDatas(this.materialOverview);
            updateTotals();
        });
        this.settings.getCheckBoxUnlock().selectedProperty().addListener((observable, oldValue, newValue) -> {
            this.materialOverview.getChildren().clear();
            showGoods(this.materialOverview);
            showComponents(this.materialOverview);
            showDatas(this.materialOverview);
            updateTotals();
        });
    }

    protected void resetAndProcess(final File file) {
        this.lineNumber = 0;
        process(file);
    }

    protected void process(final File file) {
        try (final Scanner scanner = new Scanner(file)) {
            int cursor = 0;
            while (scanner.hasNextLine()) {
                final String line = scanner.nextLine();
                cursor++;
                if (cursor > this.lineNumber) {
                    this.lineNumber++;
                    System.out.println(line);
                    processMessage(line);
                }
            }
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        }
        updateIngredients();

        Platform.runLater(() -> {
            this.materialOverview.getChildren().clear();
            showGoods(this.materialOverview);
            showComponents(this.materialOverview);
            showDatas(this.materialOverview);

            this.ingredients.forEach(Ingredient::update);
            updateTotals();
        });
    }

    private void updateIngredients() {
        this.ingredients.forEach(ingredient -> {
            switch (ingredient.getType()) {
                case ASSET -> ingredient.setAmountAvailable(this.assets.get(Asset.forName(ingredient.getCode())));
                case GOOD -> ingredient.setAmountAvailable(this.goods.get(Good.forName(ingredient.getCode())));
                case DATA -> ingredient.setAmountAvailable(this.data.get(Data.forName(ingredient.getCode())));
                case OTHER -> {
                }
            }
        });
    }

    private void updateTotals() {
        updateTotalsGoods();
        updateTotalsAssets();
        updateTotalsData();
    }

    private void updateTotalsData() {
        final Integer recipeDatas = this.data.entrySet().stream()
                .filter(data -> RecipeConstants.isRecipeIngredient(data.getKey()))
                .map(Map.Entry::getValue)
                .reduce(0, Integer::sum);
        final Integer nonRecipeDatas = this.data.entrySet().stream()
                .filter(data -> !RecipeConstants.isRecipeIngredient(data.getKey()))
                .map(Map.Entry::getValue)
                .reduce(0, Integer::sum);
        final MaterialCard datasLabel = new MaterialCard("Data (Blueprint: " + recipeDatas + " / Irrelevant: " + nonRecipeDatas + " / Total: " + (recipeDatas + nonRecipeDatas) + ")", "");
        datasLabel.setStyle("-fx-font-weight: bold");
        datasLabel.getName().setStyle("-fx-pref-width: 400; -fx-label-padding: 2;");
        this.materialOverview.add(datasLabel, 4, 0, 2, 1);
        GridPane.setMargin(datasLabel, CARD_MARGIN);
    }

    private void updateTotalsAssets() {
        final Integer recipeAssets = this.assets.entrySet().stream()
                .filter(assetEntry -> RecipeConstants.isRecipeIngredient(assetEntry.getKey()))
                .map(Map.Entry::getValue)
                .reduce(0, Integer::sum);
        final Integer nonRecipeAssets = this.assets.entrySet().stream()
                .filter(assetEntry -> !RecipeConstants.isRecipeIngredient(assetEntry.getKey()))
                .map(Map.Entry::getValue)
                .reduce(0, Integer::sum);
        final MaterialCard assetsLabel = new MaterialCard("Assets (Blueprint: " + recipeAssets + " / Irrelevant: " + nonRecipeAssets + " / Total: " + (recipeAssets + nonRecipeAssets) + ")", "");
        assetsLabel.setStyle("-fx-font-weight: bold");
        assetsLabel.getName().setStyle("-fx-pref-width: 400; -fx-label-padding: 2;");
        this.materialOverview.add(assetsLabel, 2, 0, 2, 1);
        GridPane.setMargin(assetsLabel, CARD_MARGIN);
    }

    private void updateTotalsGoods() {
        final Integer recipeGoods = this.goods.entrySet().stream()
                .filter(good -> RecipeConstants.isRecipeIngredient(good.getKey()))
                .map(Map.Entry::getValue)
                .reduce(0, Integer::sum);
        final Integer nonRecipeGoods = this.goods.entrySet().stream()
                .filter(good -> !RecipeConstants.isRecipeIngredient(good.getKey()))
                .map(Map.Entry::getValue)
                .reduce(0, Integer::sum);
        final MaterialCard goodsLabel = new MaterialCard("Goods (Blueprint: " + recipeGoods + " / Irrelevant: " + nonRecipeGoods + " / Total: " + (recipeGoods + nonRecipeGoods) + ")", "");
        goodsLabel.setStyle("-fx-font-weight: bold");
        goodsLabel.getName().setStyle("-fx-pref-width: 400; -fx-label-padding: 2;");
        this.materialOverview.add(goodsLabel, 0, 0, 2, 1);
        GridPane.setMargin(goodsLabel, CARD_MARGIN);
    }

    private void processMessage(final String message) {
        try {
            final JsonNode journalMessage = this.objectMapper.readTree(message);
            switch (journalMessage.get("event").asText()) {
                case "ShipLockerMaterials" -> processShipLockerMaterialsMessage(journalMessage);
                case "TransferMicroResources" -> processTransferMicroResourcesMessage(journalMessage);
                case "EngineerProgress" -> processEngineerProgressMessage(journalMessage);
            }
        } catch (final JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void processEngineerProgressMessage(final JsonNode journalMessage) {
        journalMessage.get("Engineers").elements().forEachRemaining(item ->
        {
            final String engineer = item.get("Engineer").asText();
            switch (engineer) {
                case "Domino Green" -> Settings.setEngineerState(Engineer.DOMINO_GREEN, EngineerState.valueOf(item.get("Progress").asText().toUpperCase()));
                case "Hero Ferrari" -> Settings.setEngineerState(Engineer.HERO_FERRARI, EngineerState.valueOf(item.get("Progress").asText().toUpperCase()));
                case "Jude Navarro" -> Settings.setEngineerState(Engineer.JUDE_NAVARRO, EngineerState.valueOf(item.get("Progress").asText().toUpperCase()));
                case "Kit Fowler" -> Settings.setEngineerState(Engineer.KIT_FOWLER, EngineerState.valueOf(item.get("Progress").asText().toUpperCase()));
                case "Oden Geiger" -> Settings.setEngineerState(Engineer.ODEN_GEIGER, EngineerState.valueOf(item.get("Progress").asText().toUpperCase()));
                case "Terra Velasquez" -> Settings.setEngineerState(Engineer.TERRA_VELASQUEZ, EngineerState.valueOf(item.get("Progress").asText().toUpperCase()));
                case "Uma Laszlo" -> Settings.setEngineerState(Engineer.UMA_LASZLO, EngineerState.valueOf(item.get("Progress").asText().toUpperCase()));
                case "Wellington Beck" -> Settings.setEngineerState(Engineer.WELLINGTON_BECK, EngineerState.valueOf(item.get("Progress").asText().toUpperCase()));
                case "Yarden Bond" -> Settings.setEngineerState(Engineer.YARDEN_BOND, EngineerState.valueOf(item.get("Progress").asText().toUpperCase()));
                default -> {
                }
            }
        });
    }

    private void processTransferMicroResourcesMessage(final JsonNode journalMessage) {
        journalMessage.get("Transfers").elements().forEachRemaining(item ->
        {
            final String cat = item.get("Category").asText();
            switch (cat) {
                case "Component" -> updateComponents(item);
                case "Item" -> updateGoods(item);
                case "Data" -> updateDatas(item);
                default -> System.out.println("unsupported category: " + cat);
            }
        });
    }

    private void processShipLockerMaterialsMessage(final JsonNode journalMessage) {
        resetCounts();
        parseGoods(journalMessage.get("Items").elements());
        parseComponents(journalMessage.get("Components").elements());
        parseDatas(journalMessage.get("Data").elements());
    }

    private void showRecipes() {
        final List<TitledPane> titledPanes = RecipeConstants.RECIPES.entrySet().stream()
                .map(this::createCategoryTitledPane)
                .sorted(Comparator.comparing(Labeled::getText))
                .collect(Collectors.toList());
        titledPanes.add(new TitledPane("Settings", this.settings));
        final Accordion categoryAccordion = new Accordion(titledPanes.toArray(new TitledPane[0]));

        this.content.getChildren().add(categoryAccordion);
        AnchorPane.setTopAnchor(categoryAccordion, 0.0);
        AnchorPane.setBottomAnchor(categoryAccordion, 0.0);
        AnchorPane.setLeftAnchor(categoryAccordion, 0.0);
    }

    private TitledPane createCategoryTitledPane(final Map.Entry<String, Map<String, ? extends Recipe>> recipesEntry) {
        final Accordion recipesAccordion = new Accordion(recipesEntry.getValue().entrySet().stream()
                .map(this::createRecipeTitledPane)
                .sorted(Comparator.comparing(Labeled::getText))
                .toArray(TitledPane[]::new));
        recipesAccordion.setPrefHeight(500);
        final TitledPane categoryTitledPane = new TitledPane(recipesEntry.getKey(), recipesAccordion);
        categoryTitledPane.setStyle("-fx-font-weight: bold");
        return categoryTitledPane;
    }

    private TitledPane createRecipeTitledPane(final Map.Entry<String, ? extends Recipe> recipe) {
        final VBox content = new VBox();
        final List<Ingredient> ingredients = new ArrayList<>();
        ingredients.addAll(recipe.getValue().getAssets().entrySet().stream()
                .map(asset ->
                        {
                            final Ingredient newIngredient = new Ingredient(StorageType.ASSET, asset.getKey(), asset.getValue().toString(), this.assets.get(asset.getKey()).toString());
                            this.ingredients.add(newIngredient);
                            return newIngredient;
                        }
                ).sorted(Comparator.comparing(Ingredient::getName))
                .collect(Collectors.toList()));
        ingredients.addAll(recipe.getValue().getData().entrySet().stream()
                .map(data -> {
                            final Ingredient newIngredient = new Ingredient(StorageType.DATA, data.getKey().toString(), data.getKey().friendlyName(), data.getValue().toString(), this.data.get(data.getKey()).toString());
                            this.ingredients.add(newIngredient);
                            return newIngredient;
                        }
                ).sorted(Comparator.comparing(Ingredient::getName))
                .collect(Collectors.toList()));
        ingredients.addAll(recipe.getValue().getGoods().entrySet().stream()
                .map(good -> {
                            final Ingredient newIngredient = new Ingredient(StorageType.GOOD, good.getKey().toString(), good.getKey().friendlyName(), good.getValue().toString(), this.goods.get(good.getKey()).toString());
                            this.ingredients.add(newIngredient);
                            return newIngredient;
                        }
                ).sorted(Comparator.comparing(Ingredient::getName))
                .collect(Collectors.toList()));
        if (recipe.getValue() instanceof EngineerRecipe) {
            ingredients.addAll(((EngineerRecipe) recipe.getValue()).getOther().stream()
                    .map(other -> {
                                final Ingredient newIngredient = new Ingredient(StorageType.OTHER, other, other, "0", "0");
                                this.ingredients.add(newIngredient);
                                return newIngredient;
                            }
                    ).sorted(Comparator.comparing(Ingredient::getName))
                    .collect(Collectors.toList()));
        }
        try {
            if (!(recipe.getValue() instanceof EngineerRecipe) || ingredients.stream().noneMatch(ingredient -> StorageType.OTHER.equals(ingredient.getType()))) {
                content.getChildren().add(new FXMLLoader(getClass().getResource("templates/IngredientHeader.fxml")).load());
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
        content.getChildren().addAll(ingredients);
        final TitledPane recipeTitledPane = new TitledPane(recipe.getKey(), content);
        if (recipe.getValue() instanceof EngineerRecipe && ((EngineerRecipe) recipe.getValue()).isCompleted()) {
            recipeTitledPane.getStyleClass().add("completed");
        } else {
            recipeTitledPane.getStyleClass().add("regular");
        }
        recipeTitledPane.setPrefHeight(150);
        return recipeTitledPane;
    }

    private void showGoods(final GridPane layout) {
        final AtomicInteger counter = new AtomicInteger(0);
        this.goods.entrySet().stream().sorted(Comparator.comparing(o -> o.getKey().friendlyName())).forEach((entry) -> {
            if (Good.UNKNOWN.equals(entry.getKey()) && entry.getValue() == 0) {
                return;
            }
            final String name = entry.getKey().friendlyName() + ((RecipeConstants.isEngineeringOnlyIngredient(entry.getKey(), false)) ? " (" + ENGINEER_UNLOCK + ")" : "");
            final MaterialCard materialCard = new MaterialCard(entry.getKey(), name, entry.getValue().toString());
            if (RecipeConstants.isEngineeringOnlyIngredient(entry.getKey(), this.settings.getCheckBoxUnlock().selectedProperty().get())) {
                materialCard.setStyle("-fx-border-color: black; -fx-background-color: #ffffff;-fx-font-weight: bold");

            } else if (this.settings.getCheckBoxUnlock().selectedProperty().get() && RecipeConstants.isEngineeringOnlyIngredient(entry.getKey(), false)) {
                return;
            } else if (RecipeConstants.isRecipeIngredient(entry.getKey())) {
                materialCard.setStyle("-fx-border-color: black; -fx-background-color: #cab951;-fx-font-weight: bold");

            } else {
                if (this.settings.getCheckBoxIrrelevant().selectedProperty().get()) {
                    return;
                }
                materialCard.setStyle("-fx-border-color: black; -fx-background-color: #9d8e05;-fx-font-weight: normal");
            }
            layout.add(materialCard, counter.get() % 2, 1 + (counter.getAndIncrement() / 2));
            GridPane.setMargin(materialCard, CARD_MARGIN);
        });
    }

    private void showComponents(final GridPane layout) {
        final AtomicInteger counter = new AtomicInteger(0);
        this.assets.entrySet().stream().sorted(
                Comparator.comparing((Map.Entry<Asset, Integer> o) -> o.getKey().getType())
                        .thenComparing(o -> o.getKey().friendlyName()))
                .forEach((entry) -> {
                    if (Asset.UNKNOWN.equals(entry.getKey()) && entry.getValue() == 0) {
                        return;
                    }
                    final String name = entry.getKey().friendlyName() + " (" + entry.getKey().getType().name().toLowerCase() + ")" + ((RecipeConstants.isEngineeringOnlyIngredient(entry.getKey(), false)) ? " (" + ENGINEER_UNLOCK + ")" : "");
                    final MaterialCard materialCard = new MaterialCard(entry.getKey(), name, entry.getValue().toString());
                    if (RecipeConstants.isEngineeringOnlyIngredient(entry.getKey(), false)) {
                        materialCard.setStyle("-fx-border-color: black; -fx-background-color: #ffffff;-fx-font-weight: bold");

                    } else if (this.settings.getCheckBoxUnlock().selectedProperty().get() && RecipeConstants.isEngineeringOnlyIngredient(entry.getKey(), false)) {
                        return;
                    } else if (RecipeConstants.isRecipeIngredient(entry.getKey())) {
                        if (entry.getKey().getType().equals(AssetType.TECH)) {
                            materialCard.setStyle("-fx-border-color: black; -fx-background-color: #78efd7;-fx-font-weight: bold");
                        } else if (entry.getKey().getType().equals(AssetType.CHEMICAL)) {
                            materialCard.setStyle("-fx-border-color: black; -fx-background-color: #98ef78;-fx-font-weight: bold");
                        } else if (entry.getKey().getType().equals(AssetType.CIRCUIT)) {
                            materialCard.setStyle("-fx-border-color: black; -fx-background-color: #efa278;-fx-font-weight: bold");
                        } else {
                            materialCard.setStyle("-fx-border-color: black; -fx-background-color: #bd78ef;-fx-font-weight: bold");
                        }

                    } else {
                        if (this.settings.getCheckBoxIrrelevant().selectedProperty().get()) {
                            return;
                        }
                        if (entry.getKey().getType().equals(AssetType.TECH)) {
                            materialCard.setStyle("-fx-border-color: black; -fx-background-color: #2c7062;-fx-font-weight: normal");
                        } else if (entry.getKey().getType().equals(AssetType.CHEMICAL)) {
                            materialCard.setStyle("-fx-border-color: black; -fx-background-color: #488830;-fx-font-weight: normal");
                        } else if (entry.getKey().getType().equals(AssetType.CIRCUIT)) {
                            materialCard.setStyle("-fx-border-color: black; -fx-background-color: #a0603c;-fx-font-weight: normal");
                        } else {
                            materialCard.setStyle("-fx-border-color: black; -fx-background-color: #b13ee9;-fx-font-weight: normal");
                        }
                    }
                    layout.add(materialCard, 2 + counter.get() % 2, 1 + (counter.getAndIncrement() / 2));
                    GridPane.setMargin(materialCard, CARD_MARGIN);
                });
    }

    private void showDatas(final GridPane layout) {
        final AtomicInteger counter = new AtomicInteger(0);
        this.data.entrySet().stream().sorted(Comparator.comparing(o -> o.getKey().friendlyName())).forEach((entry) -> {
            if (Data.UNKNOWN.equals(entry.getKey()) && entry.getValue() == 0) {
                return;
            }
            final String name = entry.getKey().friendlyName() + ((RecipeConstants.isEngineeringOnlyIngredient(entry.getKey(), false)) ? " (" + ENGINEER_UNLOCK + ")" : "");
            final MaterialCard materialCard = new MaterialCard(entry.getKey(), name, entry.getValue().toString());
            if (RecipeConstants.isEngineeringOnlyIngredient(entry.getKey(), this.settings.getCheckBoxUnlock().selectedProperty().get())) {
                materialCard.setStyle("-fx-border-color: black; -fx-background-color: #ffffff;-fx-font-weight: bold");

            } else if (this.settings.getCheckBoxUnlock().selectedProperty().get() && RecipeConstants.isEngineeringOnlyIngredient(entry.getKey(), false)) {
                return;
            } else if (RecipeConstants.isRecipeIngredient(entry.getKey())) {
                materialCard.setStyle("-fx-border-color: black; -fx-background-color: #53e5ea;-fx-font-weight: bold");

            } else {
                if (this.settings.getCheckBoxIrrelevant().selectedProperty().get()) {
                    return;
                }
                materialCard.setStyle("-fx-border-color: black; -fx-background-color: #05999d;-fx-font-weight: normal");
            }
            layout.add(materialCard, 4 + counter.get() % 2, 1 + (counter.getAndIncrement() / 2));
            GridPane.setMargin(materialCard, CARD_MARGIN);
        });
        this.unknownData.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach((entry) -> {

            final String name = entry.getKey() + " (unknown)";
            final MaterialCard materialCard = new MaterialCard(Data.UNKNOWN, name, entry.getValue().toString());
            materialCard.setStyle("-fx-border-color: black; -fx-background-color: #ba5efa;-fx-font-weight: normal");

            layout.add(materialCard, 4 + counter.get() % 2, 1 + (counter.getAndIncrement() / 2));
            GridPane.setMargin(materialCard, CARD_MARGIN);
        });
    }

    private void parseComponents(final Iterator<JsonNode> components) {
        components.forEachRemaining(componentNode ->
        {
            final String name = componentNode.get("Name").asText();
            final Asset asset = Asset.forName(name);
            if (Asset.UNKNOWN.equals(asset)) {
                System.out.println("Unknown Component detected: " + componentNode.toPrettyString());
            }
            final Integer currentAmount = this.assets.get(asset);
            this.assets.put(asset, currentAmount + componentNode.get("Count").asInt());

        });

    }

    private void parseDatas(final Iterator<JsonNode> datas) {
        datas.forEachRemaining(dataNode ->
        {
            final String name = dataNode.get("Name").asText();
            final Data data = Data.forName(name);
            if (Data.UNKNOWN.equals(data)) {
                System.out.println("Unknown Data detected: " + dataNode.toPrettyString());
                final String nameLocalised = dataNode.get("Name_Localised") != null ? dataNode.get("Name_Localised").asText() : name;
                final Integer currentAmount = this.unknownData.getOrDefault(name + ":" + nameLocalised, 0);
                this.unknownData.put(name + ":" + nameLocalised, currentAmount + dataNode.get("Count").asInt());
            } else {
                final Integer currentAmount = this.data.get(data);
                this.data.put(data, currentAmount + dataNode.get("Count").asInt());
            }

        });

    }

    private void parseGoods(final Iterator<JsonNode> items) {
        items.forEachRemaining(itemNode ->
        {
            final String name = itemNode.get("Name").asText();
            final Good good = Good.forName(name);
            if (Good.UNKNOWN.equals(good)) {
                System.out.println("Unknown Good detected: " + itemNode.toPrettyString());
            }
            final Integer currentAmount = this.goods.get(good);
            this.goods.put(good, currentAmount + itemNode.get("Count").asInt());

        });
    }

    private void updateGoods(final JsonNode item) {
        final Good good = Good.forName(item.get("Name").asText());
        if (Good.UNKNOWN.equals(good)) {
            System.out.println("Unknown Good detected: " + item.toPrettyString());
        }
        final Integer currentAmount = this.goods.get(good);
        final int newAmount = currentAmount + item.get("LockerNewCount").asInt() - item.get("LockerOldCount").asInt();
        this.goods.put(good, newAmount);
    }

    private void updateDatas(final JsonNode item) {
        final Data data = Data.forName(item.get("Name").asText());
        if (Data.UNKNOWN.equals(data)) {
            System.out.println("Unknown Data detected: " + item.toPrettyString());
        }
        final Integer currentAmount = this.data.get(data);
        final int newAmount = currentAmount + item.get("LockerNewCount").asInt() - item.get("LockerOldCount").asInt();
        this.data.put(data, newAmount);
    }

    private void updateComponents(final JsonNode item) {
        final Asset asset = Asset.forName(item.get("Name").asText());
        if (Asset.UNKNOWN.equals(asset)) {
            System.out.println("Unknown Asset detected: " + item.toPrettyString());
        }
        final Integer currentAmount = this.assets.get(asset);
        final int newAmount = currentAmount + item.get("LockerNewCount").asInt() - item.get("LockerOldCount").asInt();
        this.assets.put(asset, newAmount);
    }


    private void resetCounts() {
        this.assets.keySet().forEach(component -> this.assets.put(component, 0));
        this.data.keySet().forEach(data -> this.data.put(data, 0));
        this.goods.keySet().forEach(good -> this.goods.put(good, 0));
    }

    private void initCounts() {
        Arrays.stream(Asset.values()).forEach(component ->
                this.assets.put(component, 0)
        );
        Arrays.stream(Data.values()).forEach(data ->
                this.data.put(data, 0)
        );
        Arrays.stream(Good.values()).forEach(good ->
                this.goods.put(good, 0)
        );

    }


    public static void main(final String[] args) {
        launch(args);
    }
}
