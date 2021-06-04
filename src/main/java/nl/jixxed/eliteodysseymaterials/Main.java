package nl.jixxed.eliteodysseymaterials;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Labeled;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.filewatcher.FileAdapter;
import nl.jixxed.eliteodysseymaterials.filewatcher.FileEvent;
import nl.jixxed.eliteodysseymaterials.filewatcher.FileWatcher;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Main extends Application {
    private static final Insets CARD_MARGIN = new Insets(2, 5, 2, 5);
    private static final String ENGINEER_UNLOCK = "E";
    private Optional<File> watchedFile = Optional.empty();
    private int lineNumber = 0;
    private final MainLayout layoutApp = new MainLayout();
    private final AnchorPane layoutMain = new AnchorPane();
    private final GridPane layout = new GridPane();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<Goods, Integer> goods = new HashMap<>();
    private final Map<Component, Integer> components = new HashMap<>();
    private final Map<Data, Integer> datas = new HashMap<>();
    private final List<Ingredient> ingredients = new ArrayList<>();
    private MaterialCard goodsLabel;
    private MaterialCard datasLabel;
    private MaterialCard componentsLabel;
    ;
    private Settings settings = new Settings();

    @Override
    public void start(Stage primaryStage) {
        initCounts();
        primaryStage.setTitle("ED Odyssey Materials Helper");
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/rocket.png")));
        ScrollPane scrollPane = new ScrollPane();
        layout.setMaxWidth(2000);
        layout.setMaxHeight(2000);
        scrollPane.setContent(layout);
        layoutMain.getChildren().add(scrollPane);
        AnchorPane.setTopAnchor(scrollPane, 0.0);
        AnchorPane.setBottomAnchor(scrollPane, 0.0);
        AnchorPane.setLeftAnchor(scrollPane, 372.0);
        AnchorPane.setRightAnchor(scrollPane, 0.0);
        layoutApp.getChildren().add(layoutMain);
        for (int i = 0; i < 5; i++) {
            ColumnConstraints column = new ColumnConstraints(250);
            layout.getColumnConstraints().add(column);
        }
        for (int i = 0; i < datas.size() / 3; i++) {
            RowConstraints row = new RowConstraints(30);
            layout.getRowConstraints().add(row);
        }
        String userprofile = System.getenv("USERPROFILE");
        watch(new File(userprofile + "\\Saved Games\\Frontier Developments\\Elite Dangerous"));

        showRecipes();
        primaryStage.setScene(new Scene(layoutApp, 1920, 1080));
        settings.getCheckBoxIrrelevant().selectedProperty().addListener((observable, oldValue, newValue) -> {
            layout.getChildren().clear();
            showGoods(layout);
            showComponents(layout);
            showDatas(layout);
            updateTotals();
        });
        settings.getCheckBoxUnlock().selectedProperty().addListener((observable, oldValue, newValue) -> {
            layout.getChildren().clear();
            showGoods(layout);
            showComponents(layout);
            showDatas(layout);
            updateTotals();
        });
        primaryStage.show();
    }

    protected void watch(File folder) {
        findLatestFile(folder);
        watchedFile.ifPresent(this::process);
        new FileWatcher(folder).addListener(new FileAdapter() {
            @Override
            public void onCreated(FileEvent event) {
                File file = event.getFile();
                if (file.isFile()) {
                    if (file.getName().startsWith("Journal.")) {
                        System.out.println("File created: " + file.getName());
                        watchedFile = Optional.of(file);
                        lineNumber = 0;
                        process(file);
                    }
                }
            }

            @Override
            public void onModified(FileEvent event) {
                File file = event.getFile();
                if (watchedFile.isPresent() && file.equals(watchedFile.get())) {
                    System.out.println("File modified: " + file.getName());
                    process(file);
                }
            }
        }).watch();
    }

    private void findLatestFile(File folder) {
        watchedFile = Arrays.stream(Objects.requireNonNull(folder.listFiles()))
                .filter(file -> file.getName().startsWith("Journal."))
                .max(Comparator.comparingLong(file -> Long.parseLong(file.getName().substring(8, 20))));

        System.out.println("Registered watched file: " + watchedFile.map(File::getName).orElse("No file"));
    }


    protected void process(File file) {
        try (Scanner scanner = new Scanner(file)) {
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
            updateIngredients();

            Platform.runLater(() -> {
                layout.getChildren().clear();
                showGoods(layout);
                showComponents(layout);
                showDatas(layout);

                this.ingredients.forEach(Ingredient::update);
                updateTotals();
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateIngredients() {
        this.ingredients.forEach(ingredient -> {
            switch (ingredient.getType()) {
                case COMPONENT -> ingredient.setAmountAvailable(this.components.get(Component.forName(ingredient.getCode())));
                case GOODS -> ingredient.setAmountAvailable(this.goods.get(Goods.forName(ingredient.getCode())));
                case DATA -> ingredient.setAmountAvailable(this.datas.get(Data.forName(ingredient.getCode())));
                case OTHER -> {
                }
                default -> System.out.println("ERROR");
            }


        });
    }

    private void updateTotals() {
        final Integer recipeGoods = goods.entrySet().stream()
                .filter(good -> RecipeConstants.isRecipeIngredient(good.getKey()))
                .map(Map.Entry::getValue)
                .reduce(0, Integer::sum);
        final Integer nonRecipeGoods = goods.entrySet().stream()
                .filter(good -> !RecipeConstants.isRecipeIngredient(good.getKey()))
                .map(Map.Entry::getValue)
                .reduce(0, Integer::sum);
        final Integer recipeComponents = components.entrySet().stream()
                .filter(component -> RecipeConstants.isRecipeIngredient(component.getKey()))
                .map(Map.Entry::getValue)
                .reduce(0, Integer::sum);
        final Integer nonRecipeComponents = components.entrySet().stream()
                .filter(component -> !RecipeConstants.isRecipeIngredient(component.getKey()))
                .map(Map.Entry::getValue)
                .reduce(0, Integer::sum);
        final Integer recipeDatas = datas.entrySet().stream()
                .filter(data -> RecipeConstants.isRecipeIngredient(data.getKey()))
                .map(Map.Entry::getValue)
                .reduce(0, Integer::sum);
        final Integer nonRecipeDatas = datas.entrySet().stream()
                .filter(data -> !RecipeConstants.isRecipeIngredient(data.getKey()))
                .map(Map.Entry::getValue)
                .reduce(0, Integer::sum);
        goodsLabel = new MaterialCard("Goods (Blueprint: " + recipeGoods + " / Irrelevant: " + nonRecipeGoods + " / Total: " + (recipeGoods + nonRecipeGoods) + ")", "");
        goodsLabel.setStyle("-fx-font-weight: bold");
        goodsLabel.getName().setStyle("-fx-pref-width: 400; -fx-label-padding: 2;");
        layout.add(goodsLabel, 0, 0, 2, 1);
        GridPane.setMargin(goodsLabel, CARD_MARGIN);
        componentsLabel = new MaterialCard("Component (Blueprint: " + recipeComponents + " / Irrelevant: " + nonRecipeComponents + " / Total: " + (recipeComponents + nonRecipeComponents) + ")", "");
        componentsLabel.setStyle("-fx-font-weight: bold");
        componentsLabel.getName().setStyle("-fx-pref-width: 400; -fx-label-padding: 2;");
        layout.add(componentsLabel, 2, 0, 2, 1);
        GridPane.setMargin(componentsLabel, CARD_MARGIN);
        datasLabel = new MaterialCard("Data (Blueprint: " + recipeDatas + " / Irrelevant: " + nonRecipeDatas + " / Total: " + (recipeDatas + nonRecipeDatas) + ")", "");
        datasLabel.setStyle("-fx-font-weight: bold");
        datasLabel.getName().setStyle("-fx-pref-width: 400; -fx-label-padding: 2;");
        layout.add(datasLabel, 4, 0, 2, 1);
        GridPane.setMargin(datasLabel, CARD_MARGIN);
//        layoutApp.setLabel("Goods (Recipe/Non-Recipe/Total): " + recipeGoods + "/" + nonRecipeGoods + "/" + (recipeGoods + nonRecipeGoods) + " | "
//                + "Component(Recipe/Non-Recipe/Total): " + recipeComponents + "/" + nonRecipeComponents + "/" + (recipeComponents + nonRecipeComponents) + " | "
//                + "Data(Recipe/Non-Recipe/Total): " + recipeDatas + "/" + nonRecipeDatas + "/" + (recipeDatas + nonRecipeDatas));
    }

    private void processMessage(final String message) {
        try {
            JsonNode journalMessage = objectMapper.readTree(message);
            if ("ShipLockerMaterials".equals(journalMessage.get("event").asText())) {
                resetCounts();
                parseGoods(journalMessage.get("Items").elements());
                parseComponents(journalMessage.get("Components").elements());
                parseDatas(journalMessage.get("Data").elements());

            } else if ("TransferMicroResources".equals(journalMessage.get("event").asText())) {

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
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void showRecipes() {
        final List<TitledPane> mainTitledPanes = new ArrayList<>();
        final List<TitledPane> collect = RecipeConstants.RECIPES.entrySet().stream().map((Map.Entry<String, Map<String, Ingredients>> recipesEntry) -> {

            Accordion accordion = new Accordion(recipesEntry.getValue().entrySet().stream()
                    .map((recipe) -> {
                        final List<Ingredient> ingredients = new ArrayList<>();
                        ingredients.addAll(recipe.getValue().getComponents().entrySet().stream()
                                .map(component ->
                                        {
                                            final Ingredient newIngredient = new Ingredient(Type.COMPONENT, component.getKey().toString(), component.getKey().friendlyName(), component.getValue().toString(), this.components.get(component.getKey()).toString());
                                            this.ingredients.add(newIngredient);
                                            return newIngredient;
                                        }
                                ).sorted(Comparator.comparing(Ingredient::getName))
                                .collect(Collectors.toList()));
                        ingredients.addAll(recipe.getValue().getData().entrySet().stream()
                                .map(data -> {
                                            final Ingredient newIngredient = new Ingredient(Type.DATA, data.getKey().toString(), data.getKey().friendlyName(), data.getValue().toString(), this.datas.get(data.getKey()).toString());
                                            this.ingredients.add(newIngredient);
                                            return newIngredient;
                                        }
                                ).sorted(Comparator.comparing(Ingredient::getName))
                                .collect(Collectors.toList()));
                        ingredients.addAll(recipe.getValue().getGoods().entrySet().stream()
                                .map(good -> {
                                            final Ingredient newIngredient = new Ingredient(Type.GOODS, good.getKey().toString(), good.getKey().friendlyName(), good.getValue().toString(), this.goods.get(good.getKey()).toString());
                                            this.ingredients.add(newIngredient);
                                            return newIngredient;
                                        }
                                ).sorted(Comparator.comparing(Ingredient::getName))
                                .collect(Collectors.toList()));
                        ingredients.addAll(recipe.getValue().getOther().stream()
                                .map(other -> {
                                            final Ingredient newIngredient = new Ingredient(Type.OTHER, other, other, "0", "0");
                                            this.ingredients.add(newIngredient);
                                            return newIngredient;
                                        }
                                ).sorted(Comparator.comparing(Ingredient::getName))
                                .collect(Collectors.toList()));
                        ingredients.forEach(ingredient -> {

                        });
                        final VBox content = new VBox();
                        content.getChildren().addAll(ingredients);
                        final TitledPane titledPane = new TitledPane(recipe.getKey(), content);
                        titledPane.setStyle("-fx-font-weight: normal");
                        titledPane.setPrefHeight(150);
                        return titledPane;
                    }).sorted(Comparator.comparing(Labeled::getText)).toArray(TitledPane[]::new));
            accordion.setPrefHeight(500);
            final TitledPane mainTitledPane = new TitledPane(recipesEntry.getKey(), accordion);
            mainTitledPane.setStyle("-fx-font-weight: bold");
            return mainTitledPane;
        }).sorted(Comparator.comparing(Labeled::getText)).collect(Collectors.toList());
        mainTitledPanes.addAll(collect);
        mainTitledPanes.add(new TitledPane("Settings", settings));
        Accordion categoryAccordion = new Accordion(mainTitledPanes.toArray(new TitledPane[0]));

        layoutMain.getChildren().add(categoryAccordion);
        AnchorPane.setTopAnchor(categoryAccordion, 0.0);
        AnchorPane.setBottomAnchor(categoryAccordion, 0.0);
        AnchorPane.setLeftAnchor(categoryAccordion, 0.0);
    }

    private void showGoods(GridPane layout) {
        final AtomicInteger counter = new AtomicInteger(0);
        this.goods.entrySet().stream().sorted(Comparator.comparing(o -> o.getKey().friendlyName())).forEach((entry) -> {
            if (Goods.UNKNOWN.equals(entry.getKey()) && entry.getValue() == 0) {
                return;
            }
            final String name = entry.getKey().friendlyName() + ((RecipeConstants.isEngineeringOnlyIngredient(entry.getKey())) ? " (" + ENGINEER_UNLOCK + ")" : "");
            final MaterialCard materialCard = new MaterialCard(entry.getKey(), name, entry.getValue().toString());
            if (RecipeConstants.isEngineeringOnlyIngredient(entry.getKey())) {
                if (settings.getCheckBoxUnlock().selectedProperty().get()) {
                    return;
                }
                materialCard.setStyle("-fx-border-color: black; -fx-background-color: #ffffff;-fx-font-weight: bold");

            } else if (RecipeConstants.isRecipeIngredient(entry.getKey())) {
                materialCard.setStyle("-fx-border-color: black; -fx-background-color: #cab951;-fx-font-weight: bold");

            } else {
                if (settings.getCheckBoxIrrelevant().selectedProperty().get()) {
                    return;
                }
                materialCard.setStyle("-fx-border-color: black; -fx-background-color: #9d8e05;-fx-font-weight: normal");
            }
            layout.add(materialCard, counter.get() % 2, 1 + (counter.getAndIncrement() / 2));
            GridPane.setMargin(materialCard, CARD_MARGIN);
        });
    }

    private void showComponents(GridPane layout) {
        final AtomicInteger counter = new AtomicInteger(0);
        this.components.entrySet().stream().sorted(
                Comparator.comparing((Map.Entry<Component, Integer> o) -> o.getKey().getType())
                        .thenComparing(o -> o.getKey().friendlyName()))
                .forEach((entry) -> {
                    if (Component.UNKNOWN.equals(entry.getKey()) && entry.getValue() == 0) {
                        return;
                    }
                    final String name = entry.getKey().friendlyName() + " (" + entry.getKey().getType().name().toLowerCase() + ")" + ((RecipeConstants.isEngineeringOnlyIngredient(entry.getKey())) ? " (" + ENGINEER_UNLOCK + ")" : "");
                    final MaterialCard materialCard = new MaterialCard(entry.getKey(), name, entry.getValue().toString());
                    if (RecipeConstants.isEngineeringOnlyIngredient(entry.getKey())) {
                        if (settings.getCheckBoxUnlock().selectedProperty().get()) {
                            return;
                        }
                        materialCard.setStyle("-fx-border-color: black; -fx-background-color: #ffffff;-fx-font-weight: bold");

                    } else if (RecipeConstants.isRecipeIngredient(entry.getKey())) {
                        if (entry.getKey().getType().equals(ComponentType.TECH)) {
                            materialCard.setStyle("-fx-border-color: black; -fx-background-color: #78efd7;-fx-font-weight: bold");
                        } else if (entry.getKey().getType().equals(ComponentType.CHEMICAL)) {
                            materialCard.setStyle("-fx-border-color: black; -fx-background-color: #98ef78;-fx-font-weight: bold");
                        } else if (entry.getKey().getType().equals(ComponentType.CIRCUIT)) {
                            materialCard.setStyle("-fx-border-color: black; -fx-background-color: #efa278;-fx-font-weight: bold");
                        } else {
                            materialCard.setStyle("-fx-border-color: black; -fx-background-color: #bd78ef;-fx-font-weight: bold");
                        }

                    } else {
                        if (settings.getCheckBoxIrrelevant().selectedProperty().get()) {
                            return;
                        }
                        if (entry.getKey().getType().equals(ComponentType.TECH)) {
                            materialCard.setStyle("-fx-border-color: black; -fx-background-color: #2c7062;-fx-font-weight: normal");
                        } else if (entry.getKey().getType().equals(ComponentType.CHEMICAL)) {
                            materialCard.setStyle("-fx-border-color: black; -fx-background-color: #488830;-fx-font-weight: normal");
                        } else if (entry.getKey().getType().equals(ComponentType.CIRCUIT)) {
                            materialCard.setStyle("-fx-border-color: black; -fx-background-color: #a0603c;-fx-font-weight: normal");
                        } else {
                            materialCard.setStyle("-fx-border-color: black; -fx-background-color: #b13ee9;-fx-font-weight: normal");
                        }
                    }
                    layout.add(materialCard, 2 + counter.get() % 2, 1 + (counter.getAndIncrement() / 2));
                    GridPane.setMargin(materialCard, CARD_MARGIN);
                });
    }

    private void showDatas(GridPane layout) {
        final AtomicInteger counter = new AtomicInteger(0);
        this.datas.entrySet().stream().sorted(Comparator.comparing(o -> o.getKey().friendlyName())).forEach((entry) -> {
            if (Data.UNKNOWN.equals(entry.getKey()) && entry.getValue() == 0) {
                return;
            }
            final String name = entry.getKey().friendlyName() + ((RecipeConstants.isEngineeringOnlyIngredient(entry.getKey())) ? " (" + ENGINEER_UNLOCK + ")" : "");
            final MaterialCard materialCard = new MaterialCard(entry.getKey(), name, entry.getValue().toString());
            if (RecipeConstants.isEngineeringOnlyIngredient(entry.getKey())) {
                if (settings.getCheckBoxUnlock().selectedProperty().get()) {
                    return;
                }
                materialCard.setStyle("-fx-border-color: black; -fx-background-color: #ffffff;-fx-font-weight: bold");

            } else if (RecipeConstants.isRecipeIngredient(entry.getKey())) {
                materialCard.setStyle("-fx-border-color: black; -fx-background-color: #53e5ea;-fx-font-weight: bold");

            } else {
                if (settings.getCheckBoxIrrelevant().selectedProperty().get()) {
                    return;
                }
                materialCard.setStyle("-fx-border-color: black; -fx-background-color: #05999d;-fx-font-weight: normal");
            }
            layout.add(materialCard, 4 + counter.get() % 2, 1 + (counter.getAndIncrement() / 2));
            GridPane.setMargin(materialCard, CARD_MARGIN);
        });
    }

    private void parseComponents(Iterator<JsonNode> components) {
        components.forEachRemaining(componentNode ->
        {
            final String name = componentNode.get("Name").asText();
            final Component component = Component.forName(name);
            if (Component.UNKNOWN.equals(component)) {
                System.out.println("Unknown Component detected: " + componentNode.toPrettyString());
            }
            final Integer currentAmount = this.components.get(component);
            this.components.put(component, currentAmount + componentNode.get("Count").asInt());

        });

    }

    private void parseDatas(Iterator<JsonNode> datas) {
        datas.forEachRemaining(dataNode ->
        {
            final String name = dataNode.get("Name").asText();
            final Data data = Data.forName(name);
            if (Data.UNKNOWN.equals(data)) {
                System.out.println("Unknown Data detected: " + dataNode.toPrettyString());
            }
            final Integer currentAmount = this.datas.get(data);
            this.datas.put(data, currentAmount + dataNode.get("Count").asInt());

        });

    }

    private void parseGoods(Iterator<JsonNode> items) {
        items.forEachRemaining(itemNode ->
        {
            final String name = itemNode.get("Name").asText();
            final Goods good = Goods.forName(name);
            if (Goods.UNKNOWN.equals(good)) {
                System.out.println("Unknown Good detected: " + itemNode.toPrettyString());
            }
            final Integer currentAmount = this.goods.get(good);
            this.goods.put(good, currentAmount + itemNode.get("Count").asInt());

        });
    }

    private void updateGoods(JsonNode item) {
        final Goods good = Goods.forName(item.get("Name").asText());
        if (Goods.UNKNOWN.equals(good)) {
            System.out.println("Unknown Good detected: " + item.toPrettyString());
        }
        final Integer currentAmount = this.goods.get(good);
        final int newAmount = currentAmount + item.get("LockerNewCount").asInt() - item.get("LockerOldCount").asInt();
        this.goods.put(good, newAmount);
    }

    private void updateDatas(JsonNode item) {
        final Data data = Data.forName(item.get("Name").asText());
        if (Data.UNKNOWN.equals(data)) {
            System.out.println("Unknown Data detected: " + item.toPrettyString());
        }
        final Integer currentAmount = this.datas.get(data);
        final int newAmount = currentAmount + item.get("LockerNewCount").asInt() - item.get("LockerOldCount").asInt();
        this.datas.put(data, newAmount);
    }

    private void updateComponents(JsonNode item) {
        final Component component = Component.forName(item.get("Name").asText());
        if (Component.UNKNOWN.equals(component)) {
            System.out.println("Unknown Component detected: " + item.toPrettyString());
        }
        final Integer currentAmount = this.components.get(component);
        final int newAmount = currentAmount + item.get("LockerNewCount").asInt() - item.get("LockerOldCount").asInt();
        this.components.put(component, newAmount);
    }


    private void resetCounts() {
        this.components.keySet().forEach(component -> this.components.put(component, 0));
        this.datas.keySet().forEach(data -> this.datas.put(data, 0));
        this.goods.keySet().forEach(good -> this.goods.put(good, 0));
    }

    private void initCounts() {
        Arrays.stream(Component.values()).forEach(component ->
                this.components.put(component, 0)
        );
        Arrays.stream(Data.values()).forEach(data ->
                this.datas.put(data, 0)
        );
        Arrays.stream(Goods.values()).forEach(good ->
                this.goods.put(good, 0)
        );

    }


    public static void main(String[] args) {
        launch(args);
    }
}
