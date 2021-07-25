package nl.jixxed.eliteodysseymaterials.templates;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.JournalProcessedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.WishlistChangedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.WishlistEvent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RecipeContent extends VBox {
    private final List<Ingredient> ingredients = new ArrayList<>();

    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private final List<EngineerModuleLabel> moduleEngineerLabels = new ArrayList<>();


    public RecipeContent(final Map.Entry<RecipeName, ? extends Recipe> recipe) {

        EventService.addListener(JournalProcessedEvent.class, (journalProcessedEvent) -> {
            Platform.runLater(() -> {
                this.updateIngredientsValues();
                this.updateIngredients();
                this.updateEngineerStyles();
            });
        });
        this.setStyle("-fx-padding: 5;");
        final List<Ingredient> ingredients = new ArrayList<>();
        ingredients.addAll(getRecipeIngredients(recipe, Good.class, StorageType.GOOD, APPLICATION_STATE.getGoods()));
        ingredients.addAll(getRecipeIngredients(recipe, Asset.class, StorageType.ASSET, APPLICATION_STATE.getAssets()));
        ingredients.addAll(getRecipeIngredients(recipe, Data.class, StorageType.DATA, APPLICATION_STATE.getData()));

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
        final Label descriptionTitle = new Label();
        descriptionTitle.textProperty().bind(LocaleService.getStringBinding("recipe.label.description"));
        descriptionTitle.getStyleClass().add("recipe-title-label");

        final Region descriptionRegion = new Region();
        HBox.setHgrow(descriptionRegion, Priority.ALWAYS);
        final HBox descriptionBox = new HBox(descriptionTitle, descriptionRegion);
        this.getChildren().add(descriptionBox);
        final Text description = new Text();
        description.setWrappingWidth(465);
        description.textProperty().bind(LocaleService.getStringBinding(recipe.getKey().getDescriptionLocalizationKey()));
        description.getStyleClass().add("recipe-description");

        this.getChildren().add(description);


        if (!(recipe.getValue() instanceof EngineerRecipe) || ingredients.stream().noneMatch(ingredient -> StorageType.OTHER.equals(ingredient.getType()))) {
            final Button addToWishlist = new Button();
            addToWishlist.textProperty().bind(LocaleService.getStringBinding("recipe.add.to.wishlist"));
            addToWishlist.getStyleClass().add("wishlist-button");
            addToWishlist.setOnAction(event -> {
                EventService.publish(new WishlistEvent(recipe.getKey(), Action.ADDED));
            });
            final long initialCount = APPLICATION_STATE.getWishlist().stream().filter(recipeName -> recipeName.equals(recipe.getKey())).count();
            final Label countLabel = new Label();
            if (initialCount > 0L) {
                countLabel.textProperty().bind(LocaleService.getStringBinding("recipe.on.wishlist", initialCount));
            }
            EventService.addListener(WishlistChangedEvent.class, wishlistEvent -> {
                final long count = APPLICATION_STATE.getWishlist().stream().filter(recipeName -> recipeName.equals(recipe.getKey())).count();
                if (count > 0L) {
                    countLabel.textProperty().bind(LocaleService.getStringBinding("recipe.on.wishlist", count));
                } else {
                    countLabel.textProperty().bind(LocaleService.getStringBinding(() -> ""));
                }
            });

            countLabel.getStyleClass().add("wishlist-count");
            final HBox box = new HBox(countLabel, addToWishlist);
            box.getStyleClass().add("wishlist-count-box");
            HBox.setHgrow(addToWishlist, Priority.ALWAYS);
            box.setAlignment(Pos.TOP_RIGHT);
            descriptionBox.getChildren().add(box);
            final Label materialHeader = new Label();
            materialHeader.textProperty().bind(LocaleService.getStringBinding("recipe.header.material"));
            materialHeader.getStyleClass().add("recipe-title-label");
            this.getChildren().add(materialHeader);
        } else {

            final Label materialHeader = new Label();
            materialHeader.textProperty().bind(LocaleService.getStringBinding("recipe.header.objective"));
            materialHeader.getStyleClass().add("recipe-title-label");
            this.getChildren().add(materialHeader);
        }

        this.getChildren().addAll(new FlowPane(ingredients.toArray(new Ingredient[0])));
        if (recipe.getValue() instanceof ModuleRecipe) {
            final Label engineerLabelHeader = new Label();
            engineerLabelHeader.textProperty().bind(LocaleService.getStringBinding("recipe.label.engineers"));
            engineerLabelHeader.getStyleClass().add("recipe-title-label");
            this.getChildren().addAll(engineerLabelHeader);
            final Label[] engineerLabels = ((ModuleRecipe) recipe.getValue()).getEngineers().stream()
                    .sorted(Comparator.comparing(Engineer::friendlyName))
                    .map(engineer -> {
                        final EngineerModuleLabel label = new EngineerModuleLabel(engineer);
                        this.moduleEngineerLabels.add(label);
                        return label;
                    }).toArray(Label[]::new);

            final FlowPane flowPane = new FlowPane(engineerLabels);
            flowPane.getStyleClass().add("engineerFlow");
            this.getChildren().addAll(flowPane);
        }


        final Map<Modifier, String> modifierMap = recipe.getValue().getModifiers();
        if (!modifierMap.isEmpty()) {
//            this.getChildren().add(new Separator(Orientation.HORIZONTAL));
            final Label modifierTitle = new Label();
            modifierTitle.textProperty().bind(LocaleService.getStringBinding("recipe.label.modifiers"));
            modifierTitle.getStyleClass().add("recipe-title-label");
//            modifierTitle.setStyle("-fx-font-size: 2.2em;-fx-min-height: 2.4em;");
            this.getChildren().add(modifierTitle);
            final List<HBox> modifiers = modifierMap.entrySet().stream().sorted(Map.Entry.comparingByKey()).map(modifierStringEntry -> {
                final Label modifier = new Label();
                modifier.getStyleClass().add("recipe-modifier-name");
                modifier.textProperty().bind(LocaleService.getStringBinding(modifierStringEntry.getKey().getLocalizationKey()));
                final Label value = new Label(modifierStringEntry.getValue());
                value.getStyleClass().add("recipe-modifier-value");
                final HBox modifierBox = new HBox(modifier, value);
                modifierBox.getStyleClass().add("recipe-modifier");
                HBox.setHgrow(value, Priority.ALWAYS);
                return modifierBox;
            }).collect(Collectors.toList());
            this.getChildren().addAll(modifiers);
        }
        this.setMaxHeight(Double.MAX_VALUE);
    }

    private List<Ingredient> getRecipeIngredients(final Map.Entry<RecipeName, ? extends Recipe> recipe, final Class<? extends Material> materialClass, final StorageType storageType, final Map<? extends Material, Storage> materialMap) {
        return recipe.getValue().getMaterialCollection(materialClass).entrySet().stream()
                .map(material ->
                        {
                            final Ingredient newIngredient = new Ingredient(storageType, material.getKey(), material.getValue(), materialMap.get(material.getKey()).getTotalValue());
                            this.ingredients.add(newIngredient);
                            return newIngredient;
                        }
                ).sorted(Comparator.comparing(Ingredient::getName))
                .collect(Collectors.toList());
    }

    public void updateIngredients() {
        this.ingredients.forEach(Ingredient::update);
    }

    public void updateIngredientsValues() {
        this.ingredients.forEach(ingredient -> {
            switch (ingredient.getType()) {
                case ASSET -> ingredient.setAmountAvailable(APPLICATION_STATE.getAssets().get(Asset.forName(ingredient.getCode())).getTotalValue());
                case GOOD -> ingredient.setAmountAvailable(APPLICATION_STATE.getGoods().get(Good.forName(ingredient.getCode())).getTotalValue());
                case DATA -> ingredient.setAmountAvailable(APPLICATION_STATE.getData().get(Data.forName(ingredient.getCode())).getTotalValue());
                case OTHER -> {
                }
            }
        });
    }

    public void updateEngineerStyles() {
        this.moduleEngineerLabels.forEach(label -> label.updateStyle(APPLICATION_STATE.isEngineerUnlocked(label.getEngineer())));
    }
}
