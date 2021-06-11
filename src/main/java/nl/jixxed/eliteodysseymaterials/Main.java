package nl.jixxed.eliteodysseymaterials;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.models.Container;
import nl.jixxed.eliteodysseymaterials.models.EngineerRecipe;
import nl.jixxed.eliteodysseymaterials.models.Recipe;
import nl.jixxed.eliteodysseymaterials.parser.ComponentParser;
import nl.jixxed.eliteodysseymaterials.parser.DataParser;
import nl.jixxed.eliteodysseymaterials.parser.GoodParser;
import nl.jixxed.eliteodysseymaterials.templates.*;
import nl.jixxed.eliteodysseymaterials.watchdog.GameStateWatcher;
import nl.jixxed.eliteodysseymaterials.watchdog.JournalWatcher;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Main extends Application {
    private static final Insets CARD_MARGIN = new Insets(2, 5, 2, 5);
    private int lineNumber = 0;
    private final ApplicationLayout applicationLayout = new ApplicationLayout();
    private final AnchorPane content = new AnchorPane();
    private final GridPane materialOverview = new GridPane();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<Good, Container> goods = new HashMap<>();
    private final Map<String, Container> unknownGoods = new HashMap<>();
    private final Map<Asset, Container> assets = new HashMap<>();
    private final Map<Data, Container> data = new HashMap<>();
    private final Map<String, Container> unknownData = new HashMap<>();
    private final List<Ingredient> ingredients = new ArrayList<>();
    private final List<EngineerTitledPane> engineerTitledPanes = new ArrayList<>();
    private final GameStateWatcher gameStateWatcher = new GameStateWatcher();
    private final JournalWatcher journalWatcher = new JournalWatcher();
    private final Settings settings = new Settings(this);
    ComponentParser componentParser = new ComponentParser();
    DataParser dataParser = new DataParser();
    GoodParser goodParser = new GoodParser();
    private final Legend legend = new Legend();
    final Label watchedFileLabel = new Label();
    final Label lastTimeStampLabel = new Label();
    private String search = "";

    @Override
    public void start(final Stage primaryStage) {
        initCounts();
        primaryStage.setTitle("ED Odyssey Materials Helper");
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/rocket.png")));
        final ScrollPane scrollPane = new ScrollPane();
        scrollPane.pannableProperty().set(true);
        final TextField textField = new TextField();
        textField.setAccessibleText("text");
        textField.getStyleClass().add("search");
        textField.setPromptText("Search");
        textField.setFocusTraversable(false);
        final Observable<String> searchChanged = Observable.create((ObservableEmitter<String> emitter) ->
                textField.textProperty().addListener((observable, oldValue, newValue) -> {
                    emitter.onNext(newValue);
                })
        ).debounce(500, TimeUnit.MILLISECONDS).observeOn(Schedulers.io());
        searchChanged.subscribe((newValue) -> {

                    this.search = newValue;//
                    Platform.runLater(() -> {
                        this.materialOverview.getChildren().clear();
                        showGoods(this.materialOverview);
                        showComponents(this.materialOverview);
                        showDatas(this.materialOverview);
                        updateTotals();
                    });
                }
        );
        final VBox value = new VBox(textField, this.materialOverview);
        scrollPane.setContent(value);
        setAnchor(this.content, 0.0, 25.0, 0.0, 0.0);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        this.content.getChildren().add(scrollPane);
        setAnchor(scrollPane, 0.0, 0.0, 372.0, 0.0);
        this.applicationLayout.getChildren().add(this.content);
        final HBox bottomBar = new HBox(this.watchedFileLabel, new Separator(Orientation.VERTICAL), this.lastTimeStampLabel);
        bottomBar.getStyleClass().add("bottom-bar");
        setAnchor(bottomBar, null, 0.0, 0.0, 0.0);
        this.applicationLayout.getChildren().add(bottomBar);

        this.applicationLayout.getStylesheets().add(getClass().getResource("/nl/jixxed/eliteodysseymaterials/style/style.css").toExternalForm());

        final String userprofile = System.getenv("USERPROFILE");
        final File watchedFolder = new File(userprofile + "\\Saved Games\\Frontier Developments\\Elite Dangerous");
        this.watchedFileLabel.setText("Watching: None - No Odyssey journals found at " + watchedFolder.getAbsolutePath());
        this.gameStateWatcher.watch(watchedFolder, this::processShipLockerBackPack, "ShipLocker.json");
        this.gameStateWatcher.watch(watchedFolder, this::processShipLockerBackPack, "Backpack.json");
        this.journalWatcher.watch(watchedFolder, this::process, this::resetAndProcess);
        for (int i = 0; i < 5; i++) {
            final ColumnConstraints column = new ColumnConstraints(250);
            this.materialOverview.getColumnConstraints().add(column);
        }
        this.materialOverview.getStyleClass().add("card-grid");

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
        this.settings.getHideIrrelevant().selectedProperty().addListener((observable, oldValue, newValue) -> {
            this.materialOverview.getChildren().clear();
            showGoods(this.materialOverview);
            showComponents(this.materialOverview);
            showDatas(this.materialOverview);
            updateTotals();
        });
        this.settings.getHideUnlocked().selectedProperty().addListener((observable, oldValue, newValue) -> {
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
        Platform.runLater(() -> {
            this.watchedFileLabel.setText("Watching: " + file.getAbsoluteFile());
        });
        try (final Scanner scanner = new Scanner(file, StandardCharsets.UTF_8)) {
            int cursor = 0;
            while (scanner.hasNextLine()) {
                final String line = scanner.nextLine();
                cursor++;
                if (cursor > this.lineNumber) {
                    this.lineNumber++;
                    System.out.println(line);
                    processJournalMessage(line);
                }
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
        updateGui();
    }

    private void updateGui() {
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

    protected void processShipLockerBackPack(final File file) {
        Platform.runLater(() -> {
            this.watchedFileLabel.setText("Watching: " + file.getAbsoluteFile());
        });
        try {
            final String shipLocker = Files.readString(file.toPath());
            System.out.println(shipLocker);
            processShipLockerBackPackMessage(shipLocker);
        } catch (final IOException e) {
            e.printStackTrace();
        }

        updateGui();
    }

    private void updateIngredients() {
        this.ingredients.forEach(ingredient -> {
            switch (ingredient.getType()) {
                case ASSET -> ingredient.setAmountAvailable(this.assets.get(Asset.forName(ingredient.getCode())).getTotalValue());
                case GOOD -> ingredient.setAmountAvailable(this.goods.get(Good.forName(ingredient.getCode())).getTotalValue());
                case DATA -> ingredient.setAmountAvailable(this.data.get(Data.forName(ingredient.getCode())).getTotalValue());
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
                .filter(data -> RecipeConstants.isBlueprintIngredient(data.getKey()))
                .map(entry -> entry.getValue().getTotalValue())
                .reduce(0, Integer::sum);
        final Integer nonRecipeDatas = this.data.entrySet().stream()
                .filter(data -> !RecipeConstants.isBlueprintIngredient(data.getKey()))
                .map(entry -> entry.getValue().getTotalValue())
                .reduce(0, Integer::sum);
        final Integer unknownDatas = this.unknownData.size();
        final MaterialCard datasLabel = new MaterialCard("Data (Blueprint: " + recipeDatas + " / Irrelevant: " + (nonRecipeDatas + unknownDatas) + " / Total: " + (recipeDatas + nonRecipeDatas + unknownDatas) + ")", null);
        datasLabel.getStyleClass().add("category-label");
        this.materialOverview.add(datasLabel, 4, 0, 2, 1);
        GridPane.setMargin(datasLabel, CARD_MARGIN);
    }

    private void updateTotalsAssets() {
        final Integer recipeAssets = this.assets.entrySet().stream()
                .filter(assetEntry -> RecipeConstants.isBlueprintIngredient(assetEntry.getKey()))
                .map(entry -> entry.getValue().getTotalValue())
                .reduce(0, Integer::sum);
        final Integer nonRecipeAssets = this.assets.entrySet().stream()
                .filter(assetEntry -> !RecipeConstants.isBlueprintIngredient(assetEntry.getKey()))
                .map(entry -> entry.getValue().getTotalValue())
                .reduce(0, Integer::sum);
        final MaterialCard assetsLabel = new MaterialCard("Assets (Blueprint: " + recipeAssets + " / Irrelevant: " + nonRecipeAssets + " / Total: " + (recipeAssets + nonRecipeAssets) + ")", null);
        assetsLabel.getStyleClass().add("category-label");
        this.materialOverview.add(assetsLabel, 2, 0, 2, 1);
        GridPane.setMargin(assetsLabel, CARD_MARGIN);
    }

    private void updateTotalsGoods() {
        final Integer recipeGoods = this.goods.entrySet().stream()
                .filter(good -> RecipeConstants.isBlueprintIngredient(good.getKey()))
                .map(entry -> entry.getValue().getTotalValue())
                .reduce(0, Integer::sum);
        final Integer nonRecipeGoods = this.goods.entrySet().stream()
                .filter(good -> !RecipeConstants.isBlueprintIngredient(good.getKey()))
                .map(entry -> entry.getValue().getTotalValue())
                .reduce(0, Integer::sum);
        final Integer unknownGoods = this.unknownGoods.size();
        final MaterialCard goodsLabel = new MaterialCard("Goods (Blueprint: " + recipeGoods + " / Irrelevant: " + (nonRecipeGoods + unknownGoods) + " / Total: " + (recipeGoods + nonRecipeGoods + unknownGoods) + ")", null);
        goodsLabel.getStyleClass().add("category-label");
        this.materialOverview.add(goodsLabel, 0, 0, 2, 1);
        GridPane.setMargin(goodsLabel, CARD_MARGIN);
    }

    private void processJournalMessage(final String message) {
        try {
            final JsonNode journalMessage = this.objectMapper.readTree(message);
            if ("EngineerProgress".equals(journalMessage.get("event").asText())) {
                processEngineerProgressMessage(journalMessage);
            }
        } catch (final JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void processShipLockerBackPackMessage(final String message) {
        try {
            final JsonNode messageJson = this.objectMapper.readTree(message);
            if (messageJson.get("event") != null) {
                switch (messageJson.get("event").asText()) {
                    case "ShipLocker" -> processShipLockerMaterialsMessage(messageJson, ContainerTarget.SHIPLOCKER);
                    case "Backpack" -> processShipLockerMaterialsMessage(messageJson, ContainerTarget.BACKPACK);
                }
            }
        } catch (final JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void processEngineerProgressMessage(final JsonNode journalMessage) {
        updateLastTimeStamp(journalMessage);
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
            updateEngineerStyles();
        });
    }

    private void updateEngineerStyles() {
        this.engineerTitledPanes.forEach(titledPane -> {
            if (titledPane.getEngineerRecipe().isCompleted() && !titledPane.getStyleClass().contains("completed")) {
                titledPane.getStyleClass().add("completed");
            }
        });
    }

    private void updateLastTimeStamp(final JsonNode journalMessage) {
        Platform.runLater(() -> {
            this.lastTimeStampLabel.setText("Latest observed relevant message: " + journalMessage.get("timestamp").asText() + " (" + journalMessage.get("event").asText() + ")");
        });
    }

    private void processShipLockerMaterialsMessage(final JsonNode journalMessage, final ContainerTarget containerTarget) {
        if (journalMessage.get("Items") == null || journalMessage.get("Components") == null || journalMessage.get("Data") == null) {
            return;
        }
        switch (containerTarget) {
            case SHIPLOCKER -> resetShipLockerCounts();
            case BACKPACK -> resetBackPackCounts();
        }
        updateLastTimeStamp(journalMessage);
        this.componentParser.parse(journalMessage.get("Components").elements(), containerTarget, this.assets, null);
        this.goodParser.parse(journalMessage.get("Items").elements(), containerTarget, this.goods, this.unknownGoods);
        this.dataParser.parse(journalMessage.get("Data").elements(), containerTarget, this.data, this.unknownData);
    }

    private void showRecipes() {
        final List<TitledPane> titledPanes = RecipeConstants.RECIPES.entrySet().stream()
                .map(this::createCategoryTitledPane)
                .sorted(Comparator.comparing(Labeled::getText))
                .collect(Collectors.toList());
        final TitledPane settings = new TitledPane("Settings", this.settings);
        titledPanes.add(settings);
        final TitledPane legend = new TitledPane("Legend", this.legend);
        titledPanes.add(legend);
        final Accordion categoryAccordion = new Accordion(titledPanes.toArray(new TitledPane[0]));
        categoryAccordion.setExpandedPane(settings);
        this.content.getChildren().add(categoryAccordion);
        setAnchor(categoryAccordion, 0.0, 0.0, 0.0, null);
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
        categoryTitledPane.getStyleClass().add("category-title-pane");
        return categoryTitledPane;
    }

    private TitledPane createRecipeTitledPane(final Map.Entry<String, ? extends Recipe> recipe) {
        final VBox content = new VBox();
        final List<Ingredient> ingredients = new ArrayList<>();
        ingredients.addAll(getRecipeIngredients(recipe, Asset.class, StorageType.ASSET, this.assets));
        ingredients.addAll(recipe.getValue().getMaterialCollection(Data.class).entrySet().stream()
                .map(data -> {
                            final Ingredient newIngredient = new Ingredient(StorageType.DATA, data.getKey(), data.getValue().toString(), this.data.get(data.getKey()).getTotalValue().toString());
                            this.ingredients.add(newIngredient);
                            return newIngredient;
                        }
                ).sorted(Comparator.comparing(Ingredient::getName))
                .collect(Collectors.toList()));
        ingredients.addAll(recipe.getValue().getMaterialCollection(Good.class).entrySet().stream()
                .map(good -> {
                            final Ingredient newIngredient = new Ingredient(StorageType.GOOD, good.getKey(), good.getValue().toString(), this.goods.get(good.getKey()).getTotalValue().toString());
                            this.ingredients.add(newIngredient);
                            return newIngredient;
                        }
                ).sorted(Comparator.comparing(Ingredient::getName))
                .collect(Collectors.toList()));
        if (recipe.getValue() instanceof EngineerRecipe) {
            ingredients.addAll(((EngineerRecipe) recipe.getValue()).getOther().stream()
                    .map(other -> {
                                final Ingredient newIngredient = new Ingredient(other);
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
        final TitledPane recipeTitledPane = createTitledPane(recipe, content);
        recipeTitledPane.getStyleClass().add("blueprint-title-pane");
        if (recipe.getValue() instanceof EngineerRecipe && ((EngineerRecipe) recipe.getValue()).isCompleted()) {
            recipeTitledPane.getStyleClass().add("completed");
        } else {
            recipeTitledPane.getStyleClass().add("regular");
        }
        recipeTitledPane.setPrefHeight(150);
        return recipeTitledPane;
    }

    private List<Ingredient> getRecipeIngredients(final Map.Entry<String, ? extends Recipe> recipe, final Class<? extends Material> materialClass, final StorageType storageType, final Map<? extends Material, Container> materialMap) {
        return recipe.getValue().getMaterialCollection(materialClass).entrySet().stream()
                .map(material ->
                        {
                            final Ingredient newIngredient = new Ingredient(storageType, material.getKey(), material.getValue().toString(), materialMap.get(material.getKey()).getTotalValue().toString());
                            this.ingredients.add(newIngredient);
                            return newIngredient;
                        }
                ).sorted(Comparator.comparing(Ingredient::getName))
                .collect(Collectors.toList());
    }

    private TitledPane createTitledPane(final Map.Entry<String, ? extends Recipe> recipe, final VBox content) {
        final TitledPane recipeTitledPane;
        if (recipe.getValue() instanceof EngineerRecipe) {
            recipeTitledPane = new EngineerTitledPane(recipe.getKey(), content, (EngineerRecipe) recipe.getValue());
            this.engineerTitledPanes.add((EngineerTitledPane) recipeTitledPane);
        } else {
            recipeTitledPane = new TitledPane(recipe.getKey(), content);
        }
        return recipeTitledPane;
    }

    private void showGoods(final GridPane layout) {
        final AtomicInteger counter = new AtomicInteger(0);
        this.goods.entrySet().stream().sorted(Comparator.comparing(o -> o.getKey().friendlyName())).forEach((entry) -> {
            final String name = entry.getKey().friendlyName();
            if ((Good.UNKNOWN.equals(entry.getKey()) && entry.getValue().getTotalValue().equals(0))
                    || (this.search.isBlank() && ((this.settings.hideIrrelevant() && RecipeConstants.isNotRelevantAndNotEngineeringIngredient(entry.getKey()))
                    || (this.settings.hideUnlocked() && RecipeConstants.isNotRelevantAndCompletedEngineeringIngredient(entry.getKey()))))
                    || (!this.search.isBlank() && (!name.toLowerCase().contains(this.search.toLowerCase())))) {
                return;
            }
            final MaterialCard materialCard = new MaterialCard(entry.getKey(), name, entry.getValue(), RecipeConstants.isEngineeringIngredient(entry.getKey()));
            layout.add(materialCard, counter.get() % 2, 1 + (counter.getAndIncrement() / 2));
            GridPane.setMargin(materialCard, CARD_MARGIN);
        });
        this.unknownGoods.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach((entry) -> {

            final String name = entry.getKey() + " (unknown)";
            final MaterialCard materialCard = new MaterialCard(Good.UNKNOWN, name, entry.getValue(), false);

            layout.add(materialCard, counter.get() % 2, 1 + (counter.getAndIncrement() / 2));
            GridPane.setMargin(materialCard, CARD_MARGIN);
        });
    }

    private void showComponents(final GridPane layout) {
        final AtomicInteger counter = new AtomicInteger(0);
        this.assets.entrySet().stream().sorted(
                Comparator.comparing((Map.Entry<Asset, Container> o) -> o.getKey().getType())
                        .thenComparing(o -> o.getKey().friendlyName()))
                .forEach((entry) -> {
                    final String name = entry.getKey().friendlyName();
                    if ((Asset.UNKNOWN.equals(entry.getKey()) && entry.getValue().getTotalValue().equals(0))
                            || (!this.search.isBlank() && (!name.toLowerCase().contains(this.search.toLowerCase())))) {
                        return;
                    }

                    final MaterialCard materialCard = new MaterialCard(entry.getKey(), name, entry.getValue());
                    layout.add(materialCard, 2 + counter.get() % 2, 1 + (counter.getAndIncrement() / 2));
                    GridPane.setMargin(materialCard, CARD_MARGIN);
                });
    }

    private void showDatas(final GridPane layout) {
        final AtomicInteger counter = new AtomicInteger(0);
        this.data.entrySet().stream().sorted(Comparator.comparing(o -> o.getKey().friendlyName())).forEach((entry) -> {
            final String name = entry.getKey().friendlyName();
            if ((Data.UNKNOWN.equals(entry.getKey()) && entry.getValue().getTotalValue().equals(0))
                    || (this.search.isBlank() && ((this.settings.hideIrrelevant() && RecipeConstants.isNotRelevantAndNotEngineeringIngredient(entry.getKey()))
                    || (this.settings.hideUnlocked() && RecipeConstants.isNotRelevantAndCompletedEngineeringIngredient(entry.getKey()))))
                    || (!this.search.isBlank() && (!name.toLowerCase().contains(this.search.toLowerCase())))) {
                return;
            }
            final MaterialCard materialCard = new MaterialCard(entry.getKey(), name, entry.getValue(), RecipeConstants.isEngineeringIngredient(entry.getKey()));
            layout.add(materialCard, 4 + counter.get() % 2, 1 + (counter.getAndIncrement() / 2));
            GridPane.setMargin(materialCard, CARD_MARGIN);
        });
        this.unknownData.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach((entry) -> {

            final String name = entry.getKey() + " (unknown)";
            final MaterialCard materialCard = new MaterialCard(Data.UNKNOWN, name, entry.getValue(), false);

            layout.add(materialCard, 4 + counter.get() % 2, 1 + (counter.getAndIncrement() / 2));
            GridPane.setMargin(materialCard, CARD_MARGIN);
        });
    }

    private void resetShipLockerCounts() {
        this.assets.values().forEach(value -> value.setValue(0, ContainerTarget.SHIPLOCKER));
        this.data.values().forEach(value -> value.setValue(0, ContainerTarget.SHIPLOCKER));
        this.goods.values().forEach(value -> value.setValue(0, ContainerTarget.SHIPLOCKER));
        this.unknownGoods.values().forEach(value -> value.setValue(0, ContainerTarget.SHIPLOCKER));
        this.unknownData.values().forEach(value -> value.setValue(0, ContainerTarget.SHIPLOCKER));
    }

    private void resetBackPackCounts() {
        this.assets.values().forEach(value -> value.setValue(0, ContainerTarget.BACKPACK));
        this.data.values().forEach(value -> value.setValue(0, ContainerTarget.BACKPACK));
        this.goods.values().forEach(value -> value.setValue(0, ContainerTarget.BACKPACK));
        this.unknownGoods.values().forEach(value -> value.setValue(0, ContainerTarget.BACKPACK));
        this.unknownData.values().forEach(value -> value.setValue(0, ContainerTarget.BACKPACK));
    }

    private void initCounts() {
        Arrays.stream(Asset.values()).forEach(component ->
                this.assets.put(component, new Container())
        );
        Arrays.stream(Data.values()).forEach(data ->
                this.data.put(data, new Container())
        );
        Arrays.stream(Good.values()).forEach(good ->
                this.goods.put(good, new Container())
        );

    }

    public static void main(final String[] args) {
        launch(args);
    }
}
