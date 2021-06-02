package nl.jixxed.eliteodysseymaterials;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

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

    @Override
    public void start(Stage primaryStage) throws Exception {
        initCounts();
        primaryStage.setTitle("ED Odyssey Materials Helper");
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
        layoutApp.getCheckBoxIrrelevant().selectedProperty().addListener((observable, oldValue, newValue) -> {
            layout.getChildren().clear();
            showGoods(layout);
            showComponents(layout);
            showDatas(layout);
        });
        layoutApp.getCheckBoxUnlock().selectedProperty().addListener((observable, oldValue, newValue) -> {
            layout.getChildren().clear();
            showGoods(layout);
            showComponents(layout);
            showDatas(layout);
        });
        primaryStage.show();
    }

    protected void watch(File folder) {
        findLatestFile(folder);
        watchedFile.ifPresent(file -> process());
        new FileWatcher(folder).addListener(new FileAdapter() {
            @Override
            public void onCreated(FileEvent event) {
                File file = event.getFile();
                if (file.isFile()) {
                    if (file.getName().startsWith("Journal.")) {
                        System.out.println("File created: " + file.getName());
                        watchedFile = Optional.of(file);
                        lineNumber = 0;
                        process();
                    }
                }
            }

            @Override
            public void onModified(FileEvent event) {
                File file = event.getFile();
                if (watchedFile.isPresent() && file.equals(watchedFile.get())) {
                    System.out.println("File modified: " + file.getName());
                    process();
                }
            }
        }).watch();
    }

    private void findLatestFile(File folder) {
        final Optional<File> latest = Arrays.stream(Objects.requireNonNull(folder.listFiles()))
                .filter(file -> file.getName().startsWith("Journal."))
                .max(Comparator.comparingLong(file -> Long.parseLong(file.getName().substring(8, 20))));
        watchedFile = latest;

        System.out.println("Registered watched file: " + watchedFile.map(File::getName).orElse("No file"));
    }


    protected void process() {
        try (Scanner scanner = new Scanner(watchedFile.get())) {
            int cursor = 0;
            while (scanner.hasNextLine()) {
                final String line = scanner.nextLine();
                cursor++;
                if (cursor > this.lineNumber) {
                    this.lineNumber++;
                    //something
                    System.out.println("Read line: " + this.lineNumber);
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
                case COMPONENT:
                    final String amountAvailable = this.components.get(Component.forName(ingredient.getCode())).toString();
                    System.out.println("set " + ingredient.getCode() + "/" + ingredient.getName() + " to " + amountAvailable);
                    ingredient.setAmountAvailable(amountAvailable);
                    break;
                case GOODS:
                    final String amountAvailable2 = this.goods.get(Goods.forName(ingredient.getCode())).toString();
                    System.out.println("set " + ingredient.getCode() + "/" + ingredient.getName() + " to " + amountAvailable2);
                    ingredient.setAmountAvailable(amountAvailable2);
                    break;
                case DATA:
                    final String amountAvailable1 = this.datas.get(Data.forName(ingredient.getCode())).toString();
                    System.out.println("set " + ingredient.getCode() + "/" + ingredient.getName() + " to " + amountAvailable1);
                    ingredient.setAmountAvailable(amountAvailable1);
                    break;
                default:
                    System.out.println("ERROR");
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
        layoutApp.setLabel("Goods(Recipe/Non-Recipe/Total): " + recipeGoods + "/" + nonRecipeGoods + "/" + (recipeGoods + nonRecipeGoods) + " | "
                + "Component(Recipe/Non-Recipe/Total): " + recipeComponents + "/" + nonRecipeComponents + "/" + (recipeComponents + nonRecipeComponents) + " | "
                + "Data(Recipe/Non-Recipe/Total): " + recipeDatas + "/" + nonRecipeDatas + "/" + (recipeDatas + nonRecipeDatas));
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
                        case "Component":
                            updateComponents(item);
                            break;
                        case "Item":
                            updateGoods(item);
                            break;
                        case "Data":
                            updateDatas(item);
                            break;
                        default:
                            System.out.println("unsupported category: " + cat);
                    }
                });
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void showRecipes() {
//        layoutMain.getChildren().remove(this.accordion);
        final List<TitledPane> mainTitledPanes = new ArrayList<>();
        for (final Map.Entry<String, Map<String, Ingredients>> recipesEntry : RecipeConstants.RECIPES.entrySet()) {

            final List<TitledPane> titledPanes = recipesEntry.getValue().entrySet().stream()
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
                        ingredients.forEach(ingredient -> {

                        });
                        final VBox content = new VBox();
                        content.getChildren().addAll(ingredients);
                        final TitledPane titledPane = new TitledPane(recipe.getKey(), content);
                        titledPane.setStyle("-fx-font-weight: normal");
                        titledPane.setPrefHeight(150);
                        return titledPane;
                    }).sorted(Comparator.comparing(Labeled::getText))
                    .collect(Collectors.toList());
            Accordion accordion = new Accordion(titledPanes.toArray(new TitledPane[0]));
            accordion.setPrefHeight(500);
            final TitledPane mainTitledPane = new TitledPane(recipesEntry.getKey(), accordion);
            mainTitledPane.setStyle("-fx-font-weight: bold");
            mainTitledPanes.add(mainTitledPane);
        }

        Accordion accordion1 = new Accordion(mainTitledPanes.toArray(new TitledPane[0]));

        layoutMain.getChildren().add(accordion1);
        AnchorPane.setTopAnchor(accordion1, 0.0);
        AnchorPane.setBottomAnchor(accordion1, 0.0);
        AnchorPane.setLeftAnchor(accordion1, 0.0);
    }

    private void showGoods(GridPane layout) {
        final AtomicInteger counter = new AtomicInteger(0);
        this.goods.entrySet().stream().sorted(Comparator.comparing(o -> o.getKey().friendlyName())).forEach((entry) -> {
            if (Goods.UNKNOWN.equals(entry.getKey()) && entry.getValue() == 0) {
                return;
            }
            final String name = entry.getKey().friendlyName() + ((RecipeConstants.isEngineeringOnlyIngredient(entry.getKey())) ? " (" + ENGINEER_UNLOCK + ")" : "");
            final MaterialCard materialCard = new MaterialCard(name, entry.getValue().toString());
            if (RecipeConstants.isEngineeringOnlyIngredient(entry.getKey())) {
                if(layoutApp.getCheckBoxUnlock().selectedProperty().get()){
                    return;
                }
                materialCard.setStyle("-fx-border-color: black; -fx-background-color: #804491;-fx-font-weight: bold");

            } else if (RecipeConstants.isRecipeIngredient(entry.getKey())) {
                materialCard.setStyle("-fx-border-color: black; -fx-background-color: #cab951;-fx-font-weight: bold");

            } else  {
                if(layoutApp.getCheckBoxIrrelevant().selectedProperty().get()){
                   return;
                }
                materialCard.setStyle("-fx-border-color: black; -fx-background-color: #9d8e05;-fx-font-weight: normal");
            }
            layout.add(materialCard, 0 + counter.get() % 2, 1+ (counter.getAndIncrement() / 2));
            GridPane.setMargin(materialCard, CARD_MARGIN);
        });
        final MaterialCard label = new MaterialCard("Goods", "");
        label.setStyle("-fx-font-weight: bold");
        layout.add(label, 0,0);
        GridPane.setMargin(label, CARD_MARGIN);
    }

    private void showComponents(GridPane layout) {
        final AtomicInteger counter = new AtomicInteger(0);
        this.components.entrySet().stream().sorted(
                Comparator.comparing((Map.Entry<Component, Integer> o) -> o.getKey().type)
                        .thenComparing(o -> o.getKey().friendlyName()))
                .forEach((entry) -> {
                    if (Component.UNKNOWN.equals(entry.getKey()) && entry.getValue() == 0) {
                        return;
                    }
                    final String name = entry.getKey().friendlyName() + " (" + entry.getKey().type.name().toLowerCase() + ")" + ((RecipeConstants.isEngineeringOnlyIngredient(entry.getKey())) ? " (" + ENGINEER_UNLOCK + ")" : "");
                    final MaterialCard materialCard = new MaterialCard(name, entry.getValue().toString());
                    if (RecipeConstants.isEngineeringOnlyIngredient(entry.getKey())) {
                        if(layoutApp.getCheckBoxUnlock().selectedProperty().get()){
                            return;
                        }
                        materialCard.setStyle("-fx-border-color: black; -fx-background-color: #804491;-fx-font-weight: bold");

                    }else if (RecipeConstants.isRecipeIngredient(entry.getKey())) {
                        if (entry.getKey().type.equals(ComponentType.TECH)) {
                            materialCard.setStyle("-fx-border-color: black; -fx-background-color: #78efd7;-fx-font-weight: bold");
                        } else if (entry.getKey().type.equals(ComponentType.CHEMICAL)) {
                            materialCard.setStyle("-fx-border-color: black; -fx-background-color: #98ef78;-fx-font-weight: bold");
                        } else if (entry.getKey().type.equals(ComponentType.CIRCUIT)) {
                            materialCard.setStyle("-fx-border-color: black; -fx-background-color: #efa278;-fx-font-weight: bold");
                        } else {
                            materialCard.setStyle("-fx-border-color: black; -fx-background-color: #bd78ef;-fx-font-weight: bold");
                        }

                    } else {
                        if(layoutApp.getCheckBoxIrrelevant().selectedProperty().get()){
                            return;
                        }
                        if (entry.getKey().type.equals(ComponentType.TECH)) {
                            materialCard.setStyle("-fx-border-color: black; -fx-background-color: #2c7062;-fx-font-weight: bold");
                        } else if (entry.getKey().type.equals(ComponentType.CHEMICAL)) {
                            materialCard.setStyle("-fx-border-color: black; -fx-background-color: #488830;-fx-font-weight: bold");
                        } else if (entry.getKey().type.equals(ComponentType.CIRCUIT)) {
                            materialCard.setStyle("-fx-border-color: black; -fx-background-color: #a0603c;-fx-font-weight: bold");
                        } else {
                            materialCard.setStyle("-fx-border-color: black; -fx-background-color: #b13ee9;-fx-font-weight: bold");
                        }
                    }
                    layout.add(materialCard, 2 + counter.get() % 2, 1+ (counter.getAndIncrement() / 2));
                     GridPane.setMargin(materialCard, CARD_MARGIN);
                });
        final MaterialCard label = new MaterialCard("Components", "");
        label.setStyle("-fx-font-weight: bold");
        layout.add(label, 2,0);
        GridPane.setMargin(label, CARD_MARGIN);
    }

    private void showDatas(GridPane layout) {
        final AtomicInteger counter = new AtomicInteger(0);
        this.datas.entrySet().stream().sorted(Comparator.comparing(o -> o.getKey().friendlyName())).forEach((entry) -> {
            if (Data.UNKNOWN.equals(entry.getKey()) && entry.getValue() == 0) {
                return;
            }
            final String name = entry.getKey().friendlyName() + ((RecipeConstants.isEngineeringOnlyIngredient(entry.getKey())) ? " (" + ENGINEER_UNLOCK + ")" : "");
            final MaterialCard materialCard = new MaterialCard(name, entry.getValue().toString());
            if (RecipeConstants.isEngineeringOnlyIngredient(entry.getKey())) {
                if(layoutApp.getCheckBoxUnlock().selectedProperty().get()){
                    return;
                }
                materialCard.setStyle("-fx-border-color: black; -fx-background-color: #804491;-fx-font-weight: bold");

            }else if (RecipeConstants.isRecipeIngredient(entry.getKey())) {
                materialCard.setStyle("-fx-border-color: black; -fx-background-color: #53e5ea;-fx-font-weight: bold");

            } else  {
                if(layoutApp.getCheckBoxIrrelevant().selectedProperty().get()){
                    return;
                }
                materialCard.setStyle("-fx-border-color: black; -fx-background-color: #05999d;-fx-font-weight: normal");
            }
            layout.add(materialCard, 4 + counter.get() % 2, 1+ (counter.getAndIncrement() / 2));
            GridPane.setMargin(materialCard, CARD_MARGIN);
        });
        final MaterialCard label = new MaterialCard("Data", "");
        label.setStyle("-fx-font-weight: bold");
        layout.add(label, 4,0);
        GridPane.setMargin(label, CARD_MARGIN);
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
        final String name = item.get("Name").asText();
        final Direction direction = Direction.valueOf(item.get("Direction").asText());
        final Goods good = Goods.forName(name);
        if (Goods.UNKNOWN.equals(good)) {
            System.out.println("Unknown Good detected: " + item.toPrettyString());
        }
        final Integer currentAmount = this.goods.get(good);
        final int newAmount = currentAmount + item.get("LockerNewCount").asInt() - item.get("LockerOldCount").asInt();
        this.goods.put(good, newAmount);
    }

    private void updateDatas(JsonNode item) {
        final String name = item.get("Name").asText();
        final Direction direction = Direction.valueOf(item.get("Direction").asText());
        final Data data = Data.forName(name);
        if (Data.UNKNOWN.equals(data)) {
            System.out.println("Unknown Data detected: " + item.toPrettyString());
        }
        final Integer currentAmount = this.datas.get(data);
        final int newAmount = currentAmount + item.get("LockerNewCount").asInt() - item.get("LockerOldCount").asInt();
        this.datas.put(data, newAmount);
    }

    private void updateComponents(JsonNode item) {
        final String name = item.get("Name").asText();
        final Direction direction = Direction.valueOf(item.get("Direction").asText());
        final Component component = Component.forName(name);
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
